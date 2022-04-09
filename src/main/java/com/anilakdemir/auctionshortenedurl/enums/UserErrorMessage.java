package com.anilakdemir.auctionshortenedurl.enums;

/**
 * @author anilakdemir
 */
public enum UserErrorMessage implements BaseErrorMessage{
    USER_NOT_FOUND("User not found"),
    USERNAME_ALREADY_EXIST("Username is already exist"),
    ;

    private String message;

    UserErrorMessage(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
