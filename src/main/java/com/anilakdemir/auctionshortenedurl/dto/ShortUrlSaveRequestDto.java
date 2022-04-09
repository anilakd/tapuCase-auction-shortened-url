package com.anilakdemir.auctionshortenedurl.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author anilakdemir
 */
@Data
public class ShortUrlSaveRequestDto{

    @NotNull(message = "URL can not be null")
    @NotBlank(message = "URL can not be blank")
    private String url;
}
