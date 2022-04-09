package com.anilakdemir.auctionshortenedurl.service;

import com.anilakdemir.auctionshortenedurl.dto.ShortUrlDetailDto;
import com.anilakdemir.auctionshortenedurl.dto.ShortUrlDto;
import com.anilakdemir.auctionshortenedurl.dto.ShortUrlSaveRequestDto;
import com.anilakdemir.auctionshortenedurl.entity.ShortUrl;
import com.anilakdemir.auctionshortenedurl.enums.ShortUrlErrorMessage;
import com.anilakdemir.auctionshortenedurl.enums.UserErrorMessage;
import com.anilakdemir.auctionshortenedurl.exceptions.DuplicateException;
import com.anilakdemir.auctionshortenedurl.exceptions.EntityNotFoundException;
import com.anilakdemir.auctionshortenedurl.mapper.ShortUrlMapper;
import com.anilakdemir.auctionshortenedurl.service.entityService.ShortUrlEntityService;
import com.anilakdemir.auctionshortenedurl.service.entityService.UserEntityService;
import com.anilakdemir.auctionshortenedurl.util.RandomStringGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

/**
 * @author anilakdemir
 */
@Service
@RequiredArgsConstructor
public class ShortUrlService{

    private final ShortUrlEntityService shortUrlEntityService;
    private final RandomStringGenerator randomStringGenerator;
    private final UserEntityService userEntityService;
    private final ShortUrlMapper shortUrlMapper;

    @Value("${auction.shortened.base.shortened.url}")
    private String baseShortenedUrl;

    public ShortUrlDto createShortUrl(ShortUrlSaveRequestDto shortUrlSaveRequestDto, Long userId){

        boolean isUserExists = userEntityService.existsById(userId);

        if(!isUserExists){
            throw new EntityNotFoundException(UserErrorMessage.USER_NOT_FOUND);
        }

        String url = shortUrlSaveRequestDto.getUrl();

        boolean isUrlExists = shortUrlEntityService.existsByUrlAndUserId(url, userId);

        if(isUrlExists){
            throw new DuplicateException(ShortUrlErrorMessage.ALREADY_EXISTS);
        }

        String code = generateCode();

        ShortUrl shortUrl = ShortUrl.builder()
                .url(url)
                .code(code)
                .userId(userId)
                .build();

        shortUrl = shortUrlEntityService.save(shortUrl);

        ShortUrlDto shortUrlDto = shortUrlMapper.INSTANCE.convertToShortUrlDto(shortUrl);
        shortUrlDto.setShortened(baseShortenedUrl + code);

        return shortUrlDto;
    }

    public HttpHeaders redirect(String code) throws URISyntaxException{

        ShortUrl shortUrl = shortUrlEntityService.findByCode(code);
        String url = shortUrl.getUrl();

        url = url.startsWith("https://") || url.startsWith("http://") ? url : ("http://" + url);

        URI uri = new URI(url);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setLocation(uri);

        return httpHeaders;
    }

    public List<ShortUrlDto> findAllByUserId(Long userId){

        boolean isUserExists = userEntityService.existsById(userId);

        if(!isUserExists){
            throw new EntityNotFoundException(UserErrorMessage.USER_NOT_FOUND);
        }

        List<ShortUrl> shortUrlList = shortUrlEntityService.findAllByUserId(userId);

        List<ShortUrlDto> shortUrlDtoList = shortUrlMapper.INSTANCE.convertToShortUrlDtoList(shortUrlList);

        shortUrlDtoList.forEach(url -> url.setShortened(baseShortenedUrl + url.getShortened()));

        return shortUrlDtoList;
    }

    public ShortUrlDetailDto findByIdAndUserId(Long urlId, Long userId){

        ShortUrl shortUrl = shortUrlEntityService.findByIdAndUserId(urlId, userId);

        ShortUrlDetailDto shortUrlDetailDto = shortUrlMapper.INSTANCE.convertToShortUrlDetailDto(shortUrl);
        shortUrlDetailDto.setShortened(baseShortenedUrl + shortUrl.getCode());

        return shortUrlDetailDto;
    }

    public void deleteUrlByIdAndUserId(Long id, Long userId){

        ShortUrl shortUrl = shortUrlEntityService.findByIdAndUserId(id, userId);

        shortUrlEntityService.delete(shortUrl);
    }

    private String generateCode(){

        String code;

        do{

            code = randomStringGenerator.generateRandomString();

        }while(shortUrlEntityService.existByCode(code));

        return code;
    }
}