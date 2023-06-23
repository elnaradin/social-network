package ru.itgroup.intouch.service;

import lombok.extern.slf4j.Slf4j;
import model.Friend;
import model.account.Account;
import model.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itgroup.intouch.aspect.CheckAndGetAuthUser;
import ru.itgroup.intouch.aspect.ValidateParams;
import ru.itgroup.intouch.dto.FriendDto;
import ru.itgroup.intouch.dto.FriendSearchDto;
import ru.itgroup.intouch.dto.FriendSearchPageableDto;
import ru.itgroup.intouch.exceptions.FriendServiceException;
import ru.itgroup.intouch.exceptions.UserNotFoundException;
import ru.itgroup.intouch.mapper.FriendMapper;
import ru.itgroup.intouch.repository.AccountRepository;
import ru.itgroup.intouch.repository.FriendRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FriendsServiceImpl implements FriendsService {

    private static final String EX_MSG_ID_NOT_FOUND = "There is no record with ID: ";
    private static final String EX_MSG_DATA_NOT_FOUND = "Data is missing in the database";
    private static final String EX_MSG_INVALID_STATUS_CODE = "Invalid status code value";
    private static final String EX_MSG_BAD_REQUEST = "No data was found for a request with such parameters";
    private static final int LIMIT_RECOMMENDATION = 10;
    private final FriendRepository friendRepository;
    private final AccountRepository accountRepository;
    private final FriendMapper friendMapper;
    private final NotificationsSender notificationsSender;

    @Autowired
    public FriendsServiceImpl(FriendRepository friendRepository, AccountRepository accountRepository,
                              FriendMapper friendMapper, NotificationsSender notificationsSender) {
        this.friendRepository = friendRepository;
        this.accountRepository = accountRepository;
        this.friendMapper = friendMapper;
        this.notificationsSender = notificationsSender;
    }

    /**
     * Метод одобрения входящей заявки в друзья. Происходит проверка значений предыдущих статусов и если их значения
     * равны REQUEST_FROM и REQUEST_TO, то происходит их обновление на статус FRIEND.
     *
     * @param id - id друга
     * @return FriendDto
     * @throws UserNotFoundException  - Ошибка 404 Пользователь не найден
     * @throws FriendServiceException - Ошибка 400 Данные по запросу дружбы (friendTo или friendFrom) отсутствуют
     *                                или их значения недопустимы
     */
    @ValidateParams
    @CheckAndGetAuthUser
    @Transactional
    public FriendDto approveFriendById(Long id, Account accountFrom) throws UserNotFoundException,
            FriendServiceException {
        Account accountTo = getAccountByUserId(id);
        Friend friendTo = getFriendByAccount(accountTo, accountFrom);
        Friend friendFrom = getFriendByAccount(accountFrom, accountTo);
        Status previousStatusCode = friendTo.getEnumStatusCode();
        if (isNotRequestFriendship(friendFrom, friendTo)) {
            throw new FriendServiceException(EX_MSG_BAD_REQUEST);
        }
        friendTo.setEnumStatusCode(Status.FRIEND);
        friendFrom.setEnumStatusCode(Status.FRIEND);
        saveFriendship(friendFrom, friendTo);
        return friendMapper.toFriendDto(friendTo, accountTo, previousStatusCode.getStatus());
    }

    /**
     * Метод блокировки или разблокировки друга. Происходит проверка значений предыдущих статусов и если их значения
     * равны FRIEND или BLOCKED, то происходит инверсия статуса friendTo со значения FRIEND на BLOCKED или наоборот
     *
     * @param id - id друга
     * @return FriendDto
     * @throws UserNotFoundException  - Ошибка 404 друг не найден
     * @throws FriendServiceException - Ошибка 400 состояние статусов и отношения между friend не соответствуют запросу
     */
    @ValidateParams
    @CheckAndGetAuthUser
    public FriendDto blockFriendById(Long id, Account accountFrom) throws UserNotFoundException,
            FriendServiceException {
        Account accountTo = getAccountByUserId(id);
        Friend friendTo = getFriendByAccount(accountTo, accountFrom);
        Friend friendFrom = getFriendByAccount(accountFrom, accountTo);
        Status previousStatusCode = friendTo.getEnumStatusCode();
        if (isNotFriendship(friendFrom, friendTo)) {
            throw new FriendServiceException(EX_MSG_BAD_REQUEST);
        }
        switch (friendFrom.getEnumStatusCode()) {
            case FRIEND:
                friendFrom.setEnumStatusCode(Status.BLOCKED);
                break;
            case BLOCKED:
                friendFrom.setEnumStatusCode(Status.FRIEND);
                break;
            default:
                throw new FriendServiceException(EX_MSG_INVALID_STATUS_CODE);
        }
        friendRepository.save(friendFrom);
        return friendMapper.toFriendDto(friendTo, accountFrom, previousStatusCode.getStatus());
    }

    /**
     * Метод запроса дружбы.
     * Проверяется наличие записей friendFrom и friendTo. Если записей нет, то создаются две записи friendFrom и
     * friendTo со статусами REQUEST_FROM и REQUEST_TO соответственно.
     * В случае если обе найденные записи находятся в статусе FRIEND или в статусах BLOCKED изменения в БД не вносятся.
     *
     * @param id - id друга
     * @return FriendDto
     * @throws UserNotFoundException - Ошибка 404 - Друг не найден
     */
    @ValidateParams
    @CheckAndGetAuthUser
    @Transactional
    public FriendDto requestOnFriendById(Long id, Account accountFrom) throws UserNotFoundException {
        Account accountTo = getAccountByUserId(id);
        Friend friendFrom = getOrCreateFriendByAccount(accountFrom, accountTo, Status.REQUEST_TO);
        Friend friendTo = getOrCreateFriendByAccount(accountTo, accountFrom, Status.REQUEST_FROM);
        Status previousStatusCode = friendTo.getEnumStatusCode() ==
                Status.REQUEST_TO ? Status.NONE : friendTo.getEnumStatusCode();
        if (isNotFriendship(friendFrom, friendTo)) {
            friendFrom.setEnumStatusCode(Status.REQUEST_TO);
            friendTo.setEnumStatusCode(Status.REQUEST_FROM);
            saveFriendship(friendFrom, friendTo);
        }
        notificationsSender.send(accountFrom.getId(), accountTo.getId());
        return friendMapper.toFriendDto(friendTo, accountTo, previousStatusCode.getStatus());
    }

    /**
     * Метод отправки подписки.
     * Проверяется наличие записей friendFrom и friendTo. Если записей нет, то создаются две записи friendFrom и
     * friendTo со статусами SUBSCRIBED и NONE соответственно.
     * В случае если обе найденные записи находятся в статусе FRIEND или в статусах BLOCKED, REQUEST_FROM,
     * REQUEST_TO, изменения в БД не вносятся.
     *
     * @param id - id друга
     * @return FriendDto
     * @throws UserNotFoundException - Ошибка 404 - Друг не найден
     */
    @ValidateParams
    @CheckAndGetAuthUser
    @Transactional
    public FriendDto subscribeOnFriendById(Long id, Account accountFrom) throws UserNotFoundException {
        Account accountTo = getAccountByUserId(id);
        Friend friendFrom = getOrCreateFriendByAccount(accountFrom, accountTo, Status.SUBSCRIBED);
        Friend friendTo = getOrCreateFriendByAccount(accountTo, accountFrom);
        if (isNotFriendship(friendFrom, friendTo) && isNotRequestFriendship(friendFrom, friendTo)) {
            saveFriendship(friendFrom, friendTo);
        }
        return friendMapper.toFriendDto(friendTo, accountTo, friendTo.getEnumStatusCode().getStatus());
    }

    /**
     * Метод получения друзей по различным запросам.
     * !!! временно реализовано получение всех записей по Status с ограничением по Pagination.size !!!
     *
     * @param friendSearchPageableDto - параметры запроса
     * @return Page<FriendDto> - возвращается Page FriendDto
     */
    @ValidateParams
    @CheckAndGetAuthUser
    public Page<FriendDto> getFriendsByRequest(FriendSearchPageableDto friendSearchPageableDto,
                                               Account accountFrom) {
        List<Friend> friends = friendRepository.getAllByUserIdFrom(accountFrom);
        return new PageImpl<>(friends.stream()
                .filter(friend -> friend.getStatusCode().equals(friendSearchPageableDto.getStatusCode()))
                .map(friend -> friendMapper.toFriendDto(friend, friend.getUserIdTo(), Status.NONE.getStatus()))
                .limit(friendSearchPageableDto.getSize())
                .collect(Collectors.toList()));
    }

    /**
     * Метод получения друга по его id.
     *
     * @param id - id друга
     * @return FriendDto
     * @throws UserNotFoundException  - Ошибка 404 - пользователь с таким id не найден
     * @throws FriendServiceException - Ошибка 400 - связь между текущим и запрашиваемым пользователем не найдена
     */
    @ValidateParams
    @CheckAndGetAuthUser
    public FriendDto getFriendById(Long id, Account accountFrom)
            throws UserNotFoundException, FriendServiceException {
        Account account = getAccountByUserId(id);
        Friend friend = getFriendByAccount(accountFrom, account);
        return friendMapper.toFriendDto(friend, account, friend.getEnumStatusCode().getStatus());
    }

    /**
     * Метод удаления дружбы по id
     *
     * @param id - id друга
     * @return FriendDto
     * @throws UserNotFoundException  - Ошибка 404 - пользователь с таким id не найден
     * @throws FriendServiceException - Ошибка 400 - связь между текущим и запрашиваемым пользователем не найдена
     */
    @ValidateParams
    @CheckAndGetAuthUser
    @Transactional
    public FriendDto deleteFriendById(Long id, Account accountFrom)
            throws UserNotFoundException, FriendServiceException {
        Account accountTo = getAccountByUserId(id);
        Friend friendFrom = getFriendByAccount(accountFrom, accountTo);
        Friend friendTo = getFriendByAccount(accountTo, accountFrom);

        friendRepository.delete(friendFrom);
        friendRepository.delete(friendTo);
        return friendMapper.toFriendDto(friendTo, accountTo, friendTo.getStatusCode());
    }

    /**
     * Метод получения рекомендаций.
     * Метод получает всех друзей, которые имеются в друзьях пользователя. Друзья которые уже добавлены - исключаются
     * Полученный результат рандомно сортируется
     * В результат попадают первые n рекомендаций, где n - LIMIT_RECOMMENDATION
     *
     * @param friendSearchDto - параметры запроса
     * @return Page<FriendDto> - возвращается Page FriendDto
     */
    @ValidateParams
    @CheckAndGetAuthUser
    public Page<FriendDto> getRecommendations(FriendSearchDto friendSearchDto, Account accountFrom) {

        //TODO: добавить получение друзей в зависимости от параметров объекта friendSearchDto

        List<Friend> friends = friendRepository.getAllByUserIdFrom(accountFrom);
        List<Friend> result = new ArrayList<>();
        for (Friend friend : friends) {
            List<Friend> friendsOfFriend = friendRepository
                    .getAllByUserIdFromAndUserIdToIsNot(friend.getUserIdTo(), accountFrom);
            if (!friendsOfFriend.isEmpty()) {
                result.addAll(friendsOfFriend);
            }
        }
        Collections.shuffle(result);
        return new PageImpl<>(result.stream()
                .filter(friend -> friend.getStatusCode().equals(friendSearchDto.getStatusCode()))
                .map(friend -> friendMapper.toFriendDto(friend, friend.getUserIdTo(), Status.NONE.getStatus()))
                .limit(LIMIT_RECOMMENDATION)
                .collect(Collectors.toList()));
    }

    /**
     * Метод получения id всех друзей, которые находятся в статусе FRIEND
     *
     * @return List<Long> - список ID всех друзей
     */
    @CheckAndGetAuthUser
    public List<Long> getFriendIds(Account accountFrom) {
        List<Friend> friends = friendRepository.getAllByUserIdFromAndStatusCode(accountFrom, Status.FRIEND.getStatus());
        return friends.stream().map(Friend::getUserIdTo).map(Account::getId).collect(Collectors.toList());
    }

    /**
     * Метод получения количества входящих заявок в друзья (количество записей со статусом REQUEST_FROM)
     *
     * @return Integer - количество заявок в друзья
     */
    @CheckAndGetAuthUser
    public Integer getCountRequest(Account accountFrom) {
        List<Friend> friends = friendRepository
                .getAllByUserIdFromAndStatusCode(accountFrom, Status.REQUEST_FROM.getStatus());
        return friends == null ? 0 : friends.size();
    }

    /**
     * Метод получения id заблокированных пользователей. Находит все записи friend в поле userTo которых стоит
     * текущий id пользователя и статус BLOCKED
     *
     * @return FriendDto
     */
    @CheckAndGetAuthUser
    public List<Long> getBlockFriendId(Account accountFrom) {
        List<Friend> friends = friendRepository.getAllByUserIdToAndStatusCode(accountFrom, Status.BLOCKED.getStatus());
        return friends.stream().map(Friend::getUserIdFrom).map(Account::getId).collect(Collectors.toList());
    }

    public void saveFriendship(Friend friendFrom, Friend friendTo) {
        friendRepository.save(friendFrom);
        friendRepository.save(friendTo);
    }

    /**
     * Метод проверки дружбы между двумя записями Friend
     *
     * @return false - если обе записи ссылаются на одинаковые id и находятся в статусе FRIEND, BLOCKED
     */
    private boolean isNotFriendship(Friend friendFrom, Friend friendTo) {
        return !Objects.equals(friendFrom.getUserIdFrom().getId(), friendTo.getUserIdTo().getId()) ||
                !Objects.equals(friendFrom.getUserIdTo().getId(), friendTo.getUserIdFrom().getId()) ||
                (friendFrom.getEnumStatusCode() != Status.FRIEND && friendFrom.getEnumStatusCode() != Status.BLOCKED) ||
                (friendTo.getEnumStatusCode() != Status.FRIEND && friendTo.getEnumStatusCode() != Status.BLOCKED);
    }

    /**
     * Метод проверки отношения запроса дружбы между Friend
     *
     * @return false - если обе записи ссылаются на одинаковые id и находятся в статусах REQUEST_FROM и REQUEST_TO
     */
    private boolean isNotRequestFriendship(Friend friendFrom, Friend friendTo) {
        return !Objects.equals(friendFrom.getUserIdFrom().getId(), friendTo.getUserIdTo().getId()) ||
                !Objects.equals(friendFrom.getUserIdTo().getId(), friendTo.getUserIdFrom().getId()) ||
                (friendFrom.getEnumStatusCode() != Status.REQUEST_FROM &&
                        friendFrom.getEnumStatusCode() != Status.REQUEST_TO) ||
                (friendTo.getEnumStatusCode() != Status.REQUEST_TO &&
                        friendTo.getEnumStatusCode() != Status.REQUEST_FROM);
    }

    private Friend getFriendByAccount(Account from, Account to) throws FriendServiceException {
        Optional<Friend> friend = friendRepository.getByUserIdFromAndUserIdTo(from, to);
        if (!friend.isPresent()) {
            throw new FriendServiceException(EX_MSG_DATA_NOT_FOUND);
        }
        return friend.get();
    }

    private Friend getOrCreateFriendByAccount(Account from, Account to) {
        return getOrCreateFriendByAccount(from, to, Status.NONE);
    }

    private Friend getOrCreateFriendByAccount(Account from, Account to, Status statusCode) {
        return friendRepository.getByUserIdFromAndUserIdTo(from, to)
                .orElse(Friend.builder()
                        .userIdFrom(from)
                        .userIdTo(to)
                        .statusCodeE(statusCode)
                        .build());
    }

    private Account getAccountByUserId(Long id) throws UserNotFoundException {
        Optional<Account> account = accountRepository.findById(id);
        if (account.isPresent()) {
            return account.get();
        }
        throw new UserNotFoundException(EX_MSG_ID_NOT_FOUND + id);
    }
}
