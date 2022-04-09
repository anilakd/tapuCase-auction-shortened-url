package com.anilakdemir.auctionshortenedurl.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author anilakdemir
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResponse<T> implements Serializable{

    private T data;
    private Date responseDate;
    private boolean isSuccess;
    private String message;

    public RestResponse(T data, boolean isSuccess){
        this.data = data;
        this.isSuccess = isSuccess;
        responseDate = new Date();
    }

    public static <T> RestResponse<T> of(T t){
        return new RestResponse<>(t, true);
    }

    public static <T> RestResponse<T> error(T t){
        return new RestResponse<>(t, false);
    }

    public static <T> RestResponse<T> empty(){
        return new RestResponse<>(null, true);
    }

    public void setMessage(String message){
        this.message = message;
    }
}
