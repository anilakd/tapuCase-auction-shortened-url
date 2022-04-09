package com.anilakdemir.auctionshortenedurl.dto;

import lombok.Data;

/**
 * @author anilakdemir
 */
@Data
public class ShortUrlDetailDto{

    private Long id;

    private String url;

    private String shortened;
}
