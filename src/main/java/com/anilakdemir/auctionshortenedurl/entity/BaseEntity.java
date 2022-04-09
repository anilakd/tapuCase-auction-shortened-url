package com.anilakdemir.auctionshortenedurl.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * @author anilakdemir
 */
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity implements BaseModel, Cloneable, Serializable{

    private static final long serialVersionUID = 1L;

    @Embedded
    private BaseAdditionalFields baseAdditionalFields;
}
