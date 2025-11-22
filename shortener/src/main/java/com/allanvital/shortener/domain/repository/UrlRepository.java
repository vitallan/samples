package com.allanvital.shortener.domain.repository;

import com.allanvital.shortener.domain.model.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Allan Vital (https://allanvital.com)
 */
public interface UrlRepository extends JpaRepository<UrlEntity, Long> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE urls", nativeQuery = true)
    void truncate();

    UrlEntity findTopByOrderByIdDesc();

}
