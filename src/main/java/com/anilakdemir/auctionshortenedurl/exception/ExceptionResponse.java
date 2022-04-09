package com.anilakdemir.auctionshortenedurl.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

/**
 * @author anilakdemir
 */
@Data
@AllArgsConstructor
public class ExceptionResponse{

    private Date errorDate;
    private String detail;
}
