package com.anilakdemir.auctionshortenedurl.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author anilakdemir
 */
@Entity
@Table(name = "APP_USER")
@Data
public class User extends BaseEntity{

    @Id
    @SequenceGenerator(name = "User", sequenceName = "USER_ID_SEQ")
    @GeneratedValue(generator = "User")
    private Long id;

    @Column(name = "USERNAME", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "PASSWORD", length = 100, nullable = false)
    private String password;
}
