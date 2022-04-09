package com.anilakdemir.auctionshortenedurl.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author anilakdemir
 */
@Data
public class UserSaveRequestDto{

    @NotNull(message = "Username can not be null")
    @NotBlank(message = "Username can not be blank")
    private String username;

    @NotNull(message = "Password can not be null")
    @NotBlank(message = "Password can not be blank")
    @Size(min = 4, max = 100)
    private String password;
}
