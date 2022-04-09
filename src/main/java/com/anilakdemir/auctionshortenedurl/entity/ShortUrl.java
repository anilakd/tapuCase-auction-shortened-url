package com.anilakdemir.auctionshortenedurl.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author anilakdemir
 */
@Entity
@Table(name = "URL_SHORT")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortUrl extends BaseEntity{

    @Id
    @SequenceGenerator(name = "ShortUrl", sequenceName = "SHORT_URL_ID_SEQ")
    @GeneratedValue(generator = "ShortUrl")
    private Long id;

    @Column(name = "URL", nullable = false, unique = true)
    private String url;

    @Column(name = "CODE", unique = true)
    private String code;

    @Column(name = "ID_USER", nullable = false)
    private Long userId;
}
