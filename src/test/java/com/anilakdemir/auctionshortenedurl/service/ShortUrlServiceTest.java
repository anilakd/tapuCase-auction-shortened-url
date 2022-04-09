package com.anilakdemir.auctionshortenedurl.service;

import com.anilakdemir.auctionshortenedurl.dto.ShortUrlDetailDto;
import com.anilakdemir.auctionshortenedurl.dto.ShortUrlDto;
import com.anilakdemir.auctionshortenedurl.dto.ShortUrlSaveRequestDto;
import com.anilakdemir.auctionshortenedurl.entity.ShortUrl;
import com.anilakdemir.auctionshortenedurl.entity.User;
import com.anilakdemir.auctionshortenedurl.exceptions.DuplicateException;
import com.anilakdemir.auctionshortenedurl.exceptions.EntityNotFoundException;
import com.anilakdemir.auctionshortenedurl.service.entityService.ShortUrlEntityService;
import com.anilakdemir.auctionshortenedurl.service.entityService.UserEntityService;
import com.anilakdemir.auctionshortenedurl.util.RandomStringGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 * @author anilakdemir
 */
@ExtendWith(MockitoExtension.class)
class ShortUrlServiceTest{

    @InjectMocks
    private ShortUrlService shortUrlService;

    @Mock
    private ShortUrlEntityService shortUrlEntityService;

    @Mock
    private UserEntityService userEntityService;

    @Mock
    private RandomStringGenerator randomStringGenerator;


    @Test
    void shouldCreateShortUrl(){
        ShortUrlSaveRequestDto shortUrlSaveRequestDto = mock(ShortUrlSaveRequestDto.class);

        Long userId = 1L;

        when(shortUrlSaveRequestDto.getUrl()).thenReturn("tapu.com");

        when(userEntityService.existsById(anyLong())).thenReturn(true);

        when(shortUrlEntityService.existsByUrlAndUserId(shortUrlSaveRequestDto.getUrl(), userId)).thenReturn(false);

        ShortUrl shortUrl = mock(ShortUrl.class);
        shortUrl.setId(1L);
        shortUrl.setUrl(shortUrlSaveRequestDto.getUrl());
        shortUrl.setUserId(userId);

        when(randomStringGenerator.generateRandomString()).thenReturn("DummyCode");

        when(shortUrlEntityService.save(any())).thenReturn(shortUrl);

        ShortUrlDto result = shortUrlService.createShortUrl(shortUrlSaveRequestDto, userId);

        assertEquals(shortUrl.getId(), result.getId());
    }

    @Test
    void shouldNotCreateShortUrl_whenUserNotFound(){

        ShortUrlSaveRequestDto shortUrlSaveRequestDto = mock(ShortUrlSaveRequestDto.class);

        Long userId = 1L;

        when(userEntityService.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> shortUrlService.createShortUrl(shortUrlSaveRequestDto, userId));
    }

    @Test
    void shouldNotCreateShortUrl_whenUrlDuplicated(){

        ShortUrlSaveRequestDto shortUrlSaveRequestDto = mock(ShortUrlSaveRequestDto.class);
        when(shortUrlSaveRequestDto.getUrl()).thenReturn("tapu.com");

        Long userId = 1L;

        when(userEntityService.existsById(anyLong())).thenReturn(true);
        when(shortUrlEntityService.existsByUrlAndUserId(shortUrlSaveRequestDto.getUrl(), userId)).thenReturn(true);

        assertThrows(DuplicateException.class, () -> shortUrlService.createShortUrl(shortUrlSaveRequestDto, userId));
    }

    @Test
    void shouldNotCreateShortUrl_whenSaveRequestIsNull(){
        when(userEntityService.existsById(anyLong())).thenReturn(true);

        assertThrows(NullPointerException.class, () -> shortUrlService.createShortUrl(null, anyLong()));
    }

    @Test
    void shouldRedirect() throws URISyntaxException{

        String url = "tapu.com";

        ShortUrl shortUrl = mock(ShortUrl.class);
        when(shortUrl.getUrl()).thenReturn(url);

        when(shortUrlEntityService.findByCode(anyString())).thenReturn(shortUrl);

        HttpHeaders httpHeaders = shortUrlService.redirect(anyString());

        assertEquals(httpHeaders.getLocation(), new URI("http://" + url));
    }

    @Test
    void shouldNotRedirect(){

        when(shortUrlEntityService.findByCode(anyString())).thenThrow(EntityNotFoundException.class);

        assertThrows(EntityNotFoundException.class, () -> shortUrlService.redirect(anyString()));
    }

    @Test
    void shouldFindAllByUserId(){

        User user = mock(User.class);
        user.setId(1L);

        ShortUrl shortUrl = mock(ShortUrl.class);

        when(userEntityService.existsById(user.getId())).thenReturn(true);

        List<ShortUrl> shortUrlList = new ArrayList<>();
        shortUrlList.add(shortUrl);

        when(shortUrlEntityService.findAllByUserId(anyLong())).thenReturn(shortUrlList);

        List<ShortUrlDto> shortUrlDtoList = shortUrlService.findAllByUserId(anyLong());

        assertEquals(shortUrlList.size(), shortUrlDtoList.size());
    }

    @Test
    void shouldNotFindAllByUserId_whenUserNotFound(){

        when(userEntityService.existsById(anyLong())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> shortUrlService.findAllByUserId(anyLong()));
    }

    @Test
    void shouldFindByIdAndUserId(){

        ShortUrl shortUrl = mock(ShortUrl.class);
        shortUrl.setId(1L);

        when(shortUrlEntityService.findByIdAndUserId(anyLong(), anyLong())).thenReturn(shortUrl);

        ShortUrlDetailDto shortUrlDetailDto = shortUrlService.findByIdAndUserId(anyLong(), anyLong());

        assertEquals(shortUrlDetailDto.getUrl(), shortUrl.getUrl());
    }

    @Test
    void shouldNotFindByIdAndUserId_whenShortUrlNotFound(){
        when(shortUrlEntityService.findByIdAndUserId(anyLong(), anyLong())).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> shortUrlEntityService.findByIdAndUserId(anyLong(), anyLong()));
    }

    @Test
    void shouldDeleteUrlByIdAndUserId(){

        ShortUrl shortUrl = mock(ShortUrl.class);

        when(shortUrlEntityService.findByIdAndUserId(anyLong(), anyLong())).thenReturn(shortUrl);

        shortUrlService.deleteUrlByIdAndUserId(anyLong(), anyLong());

        verify(shortUrlEntityService).delete(shortUrl);

    }

    @Test
    void shouldNotDeleteUrlByIdAndUserId_whenShortUrlNotFound(){

        when(shortUrlEntityService.findByIdAndUserId(anyLong(), anyLong())).thenThrow(EntityNotFoundException.class);
        assertThrows(EntityNotFoundException.class, () -> shortUrlEntityService.findByIdAndUserId(anyLong(), anyLong()));
    }
}