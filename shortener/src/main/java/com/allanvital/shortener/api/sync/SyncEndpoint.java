package com.allanvital.shortener.api.sync;

import com.allanvital.shortener.api.GenericEndpoint;
import com.allanvital.shortener.api.assembler.ResourceAssembler;
import com.allanvital.shortener.domain.id.Base62Handler;
import com.allanvital.shortener.domain.model.UrlEntity;
import com.allanvital.shortener.domain.pojo.ShortenUrlRequest;
import com.allanvital.shortener.domain.pojo.ShortenedUrlResponse;
import com.allanvital.shortener.domain.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.allanvital.shortener.ShortenerApplication.SYNC_ENDPOINT;

/**
 * @author Allan Vital (https://allanvital.com)
 */
@RestController
@RequestMapping(SYNC_ENDPOINT)
public class SyncEndpoint extends GenericEndpoint {

    private static final Logger log = LoggerFactory.getLogger(SyncEndpoint.class);

    public SyncEndpoint(ResourceAssembler assembler, Base62Handler base62Handler, UrlRepository urlRepository) {
        super(assembler, base62Handler, urlRepository);
    }

    @Override
    public ResponseEntity<ShortenedUrlResponse> createUrl(@RequestBody ShortenUrlRequest request) {
        UrlEntity persisted = createAndPersistEntity(request);
        ShortenedUrlResponse resource = assembler.toResource(persisted);
        log.info("persisted {}", resource);
        return ResponseEntity.ok(resource);
    }

}
