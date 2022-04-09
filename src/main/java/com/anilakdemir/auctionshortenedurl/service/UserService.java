package com.anilakdemir.auctionshortenedurl.service;

import com.anilakdemir.auctionshortenedurl.dto.UserDto;
import com.anilakdemir.auctionshortenedurl.dto.UserSaveRequestDto;
import com.anilakdemir.auctionshortenedurl.entity.User;
import com.anilakdemir.auctionshortenedurl.enums.UserErrorMessage;
import com.anilakdemir.auctionshortenedurl.exceptions.DuplicateException;
import com.anilakdemir.auctionshortenedurl.mapper.UserMapper;
import com.anilakdemir.auctionshortenedurl.service.entityService.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author anilakdemir
 */
@Service
@RequiredArgsConstructor
public class UserService{

    private final UserEntityService userEntityService;
    private final UserMapper userMapper;

    public UserDto save(UserSaveRequestDto userSaveRequestDto){

        String username = userSaveRequestDto.getUsername();

        boolean isUsernameExist = userEntityService.existsByUsername(username);

        if(isUsernameExist){
            throw new DuplicateException(UserErrorMessage.USERNAME_ALREADY_EXIST);
        }

        User user = userMapper.INSTANCE.convertToUsrUser(userSaveRequestDto);

        user = userEntityService.save(user);

        UserDto userDto = userMapper.INSTANCE.convertToUsrUserDto(user);

        return userDto;
    }
}
