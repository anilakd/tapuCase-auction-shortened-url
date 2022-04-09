package com.anilakdemir.auctionshortenedurl.controller;

import com.anilakdemir.auctionshortenedurl.service.ShortUrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.net.URISyntaxException;

/**
 * @author anilakdemir
 */
@RestController
@RequestMapping("/s")
@RequiredArgsConstructor
public class ShortUrlController{

    private final ShortUrlService shortUrlService;

    @GetMapping("/{code}")
    public ResponseEntity<?> redirect(@Valid @NotEmpty @PathVariable(name = "code") String code) throws URISyntaxException{

        HttpHeaders httpHeaders = shortUrlService.redirect(code);

        return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).headers(httpHeaders).build();
    }
}
