package com.anilakdemir.auctionshortenedurl.dao;

import com.anilakdemir.auctionshortenedurl.entity.ShortUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author anilakdemir
 */
@Repository
public interface ShortUrlDao extends JpaRepository<ShortUrl, Long>{

    boolean existsByCode(String code);

    Optional<ShortUrl> findByCode(String code);

    List<ShortUrl> findAllByUserId(Long userId);

    Optional<ShortUrl> findByIdAndUserId(Long id, Long userId);

    boolean existsByUrlAndUserId(String url, Long userId);
}
