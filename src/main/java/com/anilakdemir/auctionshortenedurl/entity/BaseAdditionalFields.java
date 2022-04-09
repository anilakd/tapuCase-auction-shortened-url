package com.anilakdemir.auctionshortenedurl.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Date;

/**
 * @author anilakdemir
 */
@Embeddable
@Data
public class BaseAdditionalFields{

    @Column(name = "CREATE_DATE", updatable = false)
    @CreatedDate
    private Date createDate;

    @Column(name = "UPDATE_DATE")
    @LastModifiedDate
    private Date updateDate;
}
