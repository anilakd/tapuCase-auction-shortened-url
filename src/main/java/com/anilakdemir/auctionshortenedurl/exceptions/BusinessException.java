package com.anilakdemir.auctionshortenedurl.exceptions;

import com.anilakdemir.auctionshortenedurl.enums.BaseErrorMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author anilakdemir
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public abstract class BusinessException extends RuntimeException{

    private BaseErrorMessage baseErrorMessage;
}
