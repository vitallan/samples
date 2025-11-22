package com.allanvital.shortener.domain.id;

import com.allanvital.shortener.domain.model.UrlEntity;
import com.allanvital.shortener.domain.repository.UrlRepository;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Allan Vital (https://allanvital.com)
 */
@Component
public class LongAutoIncrementIdGenerator implements IdGenerator {

    private AtomicLong CURRENT_ID = new AtomicLong(0);

    public LongAutoIncrementIdGenerator(UrlRepository urlRepository) {
        UrlEntity urlEntity = urlRepository.findTopByOrderByIdDesc();
        if (urlEntity != null) {
            CURRENT_ID = new AtomicLong(urlEntity.getId());
        }
    }

    @Override
    public Long getNextId() {
        return CURRENT_ID.incrementAndGet();
    }

}
