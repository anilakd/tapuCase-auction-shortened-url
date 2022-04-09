package com.anilakdemir.auctionshortenedurl.service.entityService;

import com.anilakdemir.auctionshortenedurl.entity.BaseAdditionalFields;
import com.anilakdemir.auctionshortenedurl.entity.BaseEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author anilakdemir
 */
@Service
@RequiredArgsConstructor
public abstract class BaseEntityService<E extends BaseEntity, D extends JpaRepository<E, Long>>{

    private final D dao;

    public E save(E entity){
        setAdditionalFields(entity);
        entity = dao.save(entity);
        return entity;
    }

    private void setAdditionalFields(E entity){

        BaseAdditionalFields baseAdditionalFields = entity.getBaseAdditionalFields();

        if(baseAdditionalFields == null){
            baseAdditionalFields = new BaseAdditionalFields();
            entity.setBaseAdditionalFields(baseAdditionalFields);
        }

        if(entity.getId() == null){
            baseAdditionalFields.setCreateDate(new Date());
        }

        baseAdditionalFields.setUpdateDate(new Date());
    }

    public D getDao(){
        return dao;
    }

    public void delete(E e){
        dao.delete(e);
    }
}
