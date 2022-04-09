package com.anilakdemir.auctionshortenedurl.service.entityService;

import com.anilakdemir.auctionshortenedurl.dao.ShortUrlDao;
import com.anilakdemir.auctionshortenedurl.entity.ShortUrl;
import com.anilakdemir.auctionshortenedurl.enums.ShortUrlErrorMessage;
import com.anilakdemir.auctionshortenedurl.exceptions.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author anilakdemir
 */
@Service
public class ShortUrlEntityService extends BaseEntityService<ShortUrl, ShortUrlDao>{

    public ShortUrlEntityService(ShortUrlDao dao){
        super(dao);
    }

    public boolean existByCode(String code){
        return getDao().existsByCode(code);
    }

    public ShortUrl findByCode(String code){

        return getDao().findByCode(code).orElseThrow(() -> new EntityNotFoundException(ShortUrlErrorMessage.URL_NOT_FOUND));
    }

    public List<ShortUrl> findAllByUserId(Long userId){

        return getDao().findAllByUserId(userId);
    }

    public ShortUrl findByIdAndUserId(Long urlId, Long userId){

        return getDao().findByIdAndUserId(urlId, userId).orElseThrow(() -> new EntityNotFoundException(ShortUrlErrorMessage.URL_NOT_FOUND));
    }

    public boolean existsByUrlAndUserId(String url, Long userId){

        return getDao().existsByUrlAndUserId(url, userId);
    }
}
