package com.anilakdemir.auctionshortenedurl.controller;

import com.anilakdemir.auctionshortenedurl.dto.*;
import com.anilakdemir.auctionshortenedurl.service.ShortUrlService;
import com.anilakdemir.auctionshortenedurl.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author anilakdemir
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController{

    private final UserService userService;
    private final ShortUrlService shortUrlService;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody UserSaveRequestDto userSaveRequestDto){

        UserDto userDto = userService.save(userSaveRequestDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(RestResponse.of(userDto));
    }

    @PostMapping("/{userId}/url/create")
    public ResponseEntity<?> createShortUrl(@PathVariable Long userId,
                                            @RequestBody ShortUrlSaveRequestDto shortUrlSaveRequestDto){

        ShortUrlDto shortUrlDto = shortUrlService.createShortUrl(shortUrlSaveRequestDto, userId);

        return ResponseEntity.ok(RestResponse.of(shortUrlDto));
    }

    @GetMapping("/{userId}/url/list")
    public ResponseEntity<?> getAllUrlListByUserId(@PathVariable Long userId){

        List<ShortUrlDto> shortUrlDtoList = shortUrlService.findAllByUserId(userId);

        return ResponseEntity.ok(RestResponse.of(shortUrlDtoList));
    }

    @GetMapping("/{userId}/url/detail/{urlId}")
    public ResponseEntity<?> getUrlDetailByUserId(@PathVariable Long userId, @PathVariable Long urlId){

        ShortUrlDetailDto shortUrlDetailDto = shortUrlService.findByIdAndUserId(urlId, userId);

        return ResponseEntity.ok(RestResponse.of(shortUrlDetailDto));
    }

    @DeleteMapping("/{userId}/url/detail/{urlId}")
    public ResponseEntity<?> deleteUrlByIdAndUserId(@PathVariable Long userId, @PathVariable Long urlId){
        shortUrlService.deleteUrlByIdAndUserId(urlId, userId);
        return ResponseEntity.ok(RestResponse.empty());
    }
}
