package com.allanvital.shortener.api.assembler;

import com.allanvital.shortener.domain.id.Base62Handler;
import com.allanvital.shortener.domain.id.IdGenerator;
import com.allanvital.shortener.domain.model.UrlEntity;
import com.allanvital.shortener.domain.pojo.ShortenUrlRequest;
import com.allanvital.shortener.domain.pojo.ShortenedUrlResponse;
import org.springframework.stereotype.Component;

/**
 * @author Allan Vital (https://allanvital.com)
 */
@Component
public class ResourceAssembler {

    private final IdGenerator idGenerator;
    private final Base62Handler handler;

    public ResourceAssembler(IdGenerator idGenerator, Base62Handler handler) {
        this.idGenerator = idGenerator;
        this.handler = handler;
    }

    public UrlEntity toEntity(Long id, String url) {
        UrlEntity entity = new UrlEntity();
        entity.setId(id);
        entity.setUrl(url);
        return entity;
    }

    public UrlEntity toEntity(ShortenUrlRequest request) {
        return toEntity(idGenerator.getNextId(), request.getUrl());
    }

    public ShortenedUrlResponse toResource(UrlEntity entity) {
        String shortened = handler.encode(entity.getId());
        String url = entity.getUrl();
        return new ShortenedUrlResponse(shortened, url);
    }

}
