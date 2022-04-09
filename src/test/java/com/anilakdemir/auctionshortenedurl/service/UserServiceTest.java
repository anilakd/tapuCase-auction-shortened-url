package com.anilakdemir.auctionshortenedurl.service;

import com.anilakdemir.auctionshortenedurl.dto.UserDto;
import com.anilakdemir.auctionshortenedurl.dto.UserSaveRequestDto;
import com.anilakdemir.auctionshortenedurl.entity.User;
import com.anilakdemir.auctionshortenedurl.exceptions.DuplicateException;
import com.anilakdemir.auctionshortenedurl.service.entityService.UserEntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author anilakdemir
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest{

    @InjectMocks
    private UserService userService;

    @Mock
    private UserEntityService userEntityService;

    @Test
    void shouldSave(){

        UserSaveRequestDto userSaveRequestDto = mock(UserSaveRequestDto.class);

        when(userSaveRequestDto.getUsername()).thenReturn("dummyUsername");

        when(userEntityService.existsByUsername(userSaveRequestDto.getUsername())).thenReturn(false);

        User user = mock(User.class);
        user.setId(1L);

        when(userEntityService.save(any())).thenReturn(user);

        UserDto userDto = userService.save(userSaveRequestDto);

        assertEquals(user.getId(), userDto.getUserId());
    }

    @Test
    void shouldNotSave_whenUsernameIsAlreadyExists(){

        UserSaveRequestDto userSaveRequestDto = mock(UserSaveRequestDto.class);

        when(userSaveRequestDto.getUsername()).thenReturn("dummyUsername");

        when(userEntityService.existsByUsername(userSaveRequestDto.getUsername())).thenReturn(true);

        assertThrows(DuplicateException.class, () -> userService.save(userSaveRequestDto));
    }

    @Test
    void shouldNotSave_whenUsernameIsNull(){

        assertThrows(NullPointerException.class, () -> userService.save(null));
    }
}