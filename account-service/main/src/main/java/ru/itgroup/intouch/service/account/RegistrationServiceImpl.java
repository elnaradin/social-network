package ru.itgroup.intouch.service.account;

import com.cloudinary.Transformation;
import com.github.cage.GCage;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import model.account.Account;
import model.account.User;
import org.springframework.stereotype.Service;
import ru.itgroup.intouch.client.StorageServiceClient;
import ru.itgroup.intouch.dto.CaptchaDto;
import ru.itgroup.intouch.dto.ImageDTO;
import ru.itgroup.intouch.dto.RegistrationDto;
import ru.itgroup.intouch.dto.UploadPhotoDto;
import ru.itgroup.intouch.exceptions.CaptchaNotValidException;
import ru.itgroup.intouch.exceptions.ServiceUnavailableException;
import ru.itgroup.intouch.exceptions.UserAlreadyRegisteredException;
import ru.itgroup.intouch.mapper.UserMapper;
import ru.itgroup.intouch.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final StorageServiceClient storageServiceClient;

    @Transactional
    public void registerNewUser(RegistrationDto registrationDto) {
        if (!Objects.equals(registrationDto.getCaptchaCode(), registrationDto.getCaptchaSecret())) {
            throw new CaptchaNotValidException("Капча введена неверно.");
        }
        Optional<User> firstByEmail = userRepository
                .findFirstByEmailEqualsAndIsDeletedEquals(registrationDto.getEmail(), false);
        if (firstByEmail.isPresent() && !firstByEmail.get().isDeleted()) {
            throw new UserAlreadyRegisteredException("Пользователь с адресом \"" +
                    registrationDto.getEmail() + "\" уже зарегистрирован.");
        }
        Account accountEntity = userMapper.registrationDto2AccountEntity(registrationDto);
        accountEntity.setCreatedOn(LocalDateTime.now());
        accountEntity.setRegDate(LocalDateTime.now());
        accountEntity.setUpdateOn(LocalDateTime.now());
        userRepository.save(accountEntity);
    }


    public CaptchaDto generateCaptcha() {
        GCage gCage = new GCage();
        String token = gCage.getTokenGenerator().next();
        byte[] image = gCage.draw(token);
        String transformation = new Transformation().width(150).height(50).generate();
        UploadPhotoDto uploadPhotoDto = new UploadPhotoDto(image, transformation);
        ImageDTO imageDTO;
        try {
            imageDTO = storageServiceClient.feignUploadPhoto(uploadPhotoDto);
        } catch (Exception e){
            throw new ServiceUnavailableException("Не удалось получить капчу");
        }

        return new CaptchaDto(token, imageDTO.getPhotoPath());
    }
}

