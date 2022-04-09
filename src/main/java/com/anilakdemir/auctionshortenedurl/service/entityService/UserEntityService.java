package com.anilakdemir.auctionshortenedurl.service.entityService;

import com.anilakdemir.auctionshortenedurl.dao.UserDao;
import com.anilakdemir.auctionshortenedurl.entity.User;
import org.springframework.stereotype.Service;

/**
 * @author anilakdemir
 */
@Service
public class UserEntityService extends BaseEntityService<User, UserDao>{


    public UserEntityService(UserDao dao){
        super(dao);
    }

    public boolean existsById(Long userId){
        return getDao().existsById(userId);
    }

    public boolean existsByUsername(String username){
        return getDao().existsByUsername(username);
    }
}
