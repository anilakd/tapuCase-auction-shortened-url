package com.anilakdemir.auctionshortenedurl.dao;

import com.anilakdemir.auctionshortenedurl.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author anilakdemir
 */
@Repository
public interface UserDao extends JpaRepository<User, Long>{

    boolean existsByUsername(String username);
}
