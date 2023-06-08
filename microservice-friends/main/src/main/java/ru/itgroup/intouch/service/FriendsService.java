package ru.itgroup.intouch.service;

import model.account.Account;
import org.springframework.data.domain.Page;
import ru.itgroup.intouch.dto.FriendDto;
import ru.itgroup.intouch.dto.FriendSearchDto;
import ru.itgroup.intouch.dto.FriendSearchPageableDto;

import java.util.List;

public interface FriendsService {
    FriendDto getFriendById(Long id, Account account) throws Exception;

    FriendDto approveFriendById(Long id, Account account) throws Exception;

    FriendDto blockFriendById(Long id, Account account) throws Exception;

    FriendDto requestOnFriendById(Long id, Account account) throws Exception;

    FriendDto subscribeOnFriendById(Long id, Account account) throws Exception;

    Page<FriendDto> getFriendsByRequest(FriendSearchPageableDto friendSearchPageableDto, Account account);

    FriendDto deleteFriendById(Long id, Account account) throws Exception;

    Page<FriendDto> getRecommendations(FriendSearchDto friendSearchDto, Account account);

    List<Long> getFriendIds(Account account);

    Integer getCountRequest(Account account);

    List<Long> getBlockFriendId(Account account);
}
