package com.allanvital.shortener.api;

import com.allanvital.shortener.api.assembler.ResourceAssembler;
import com.allanvital.shortener.domain.id.Base62Handler;
import com.allanvital.shortener.domain.model.UrlEntity;
import com.allanvital.shortener.domain.pojo.ShortenUrlRequest;
import com.allanvital.shortener.domain.pojo.ShortenedUrlResponse;
import com.allanvital.shortener.domain.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

/**
 * @author Allan Vital (https://allanvital.com)
 */
public abstract class GenericEndpoint {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    protected final ResourceAssembler assembler;
    protected final Base62Handler base62Handler;
    protected final UrlRepository urlRepository;

    public GenericEndpoint(ResourceAssembler assembler, Base62Handler base62Handler, UrlRepository urlRepository) {
        this.assembler = assembler;
        this.base62Handler = base62Handler;
        this.urlRepository = urlRepository;
    }

    @PostMapping
    public abstract ResponseEntity<ShortenedUrlResponse> createUrl(@RequestBody ShortenUrlRequest request);

    @GetMapping("/{id}")
    public ResponseEntity<ShortenedUrlResponse> getUrl(@PathVariable("id") String base62EncodedId) {
        Long id = base62Handler.decode(base62EncodedId);
        Optional<UrlEntity> entity = urlRepository.findById(id);
        log.info("queried id:{}", id);
        if (entity.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toResource(entity.get()));
    }

    protected UrlEntity createAndPersistEntity(ShortenUrlRequest request) {
        UrlEntity entity = assembler.toEntity(request);
        return urlRepository.save(entity);
    }

}
