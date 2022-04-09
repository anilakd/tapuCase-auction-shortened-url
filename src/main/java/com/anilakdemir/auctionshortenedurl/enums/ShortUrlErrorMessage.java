package com.anilakdemir.auctionshortenedurl.enums;

/**
 * @author anilakdemir
 */
public enum ShortUrlErrorMessage implements BaseErrorMessage{
    URL_NOT_FOUND("URL not found"),
    ALREADY_EXISTS("URL is already exists");

    private String message;

    ShortUrlErrorMessage(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
