package com.allanvital.shortener.api.async.endpoint.periodic;

import com.allanvital.shortener.api.GenericEndpoint;
import com.allanvital.shortener.api.assembler.ResourceAssembler;
import com.allanvital.shortener.cache.CacheManager;
import com.allanvital.shortener.domain.id.Base62Handler;
import com.allanvital.shortener.domain.model.UrlEntity;
import com.allanvital.shortener.domain.pojo.ShortenUrlRequest;
import com.allanvital.shortener.domain.pojo.ShortenedUrlResponse;
import com.allanvital.shortener.domain.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static com.allanvital.shortener.ShortenerApplication.PERIODIC_ENDPOINT;

/**
 * @author Allan Vital (https://allanvital.com)
 */
@RestController
@RequestMapping(PERIODIC_ENDPOINT)
public class PeriodicPersistenceEndpoint extends GenericEndpoint {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final CacheManager cacheManager;

    public PeriodicPersistenceEndpoint(ResourceAssembler assembler, Base62Handler base62Handler, UrlRepository urlRepository, CacheManager cacheManager) {
        super(assembler, base62Handler, urlRepository);
        this.cacheManager = cacheManager;
    }

    @PostMapping
    public ResponseEntity<ShortenedUrlResponse> createUrl(@RequestBody ShortenUrlRequest request) {
        UrlEntity entity = assembler.toEntity(request);
        ShortenedUrlResponse response = assembler.toResource(entity);
        cacheManager.put(response);
        log.info("request cached '{}'", request.getUrl());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShortenedUrlResponse> getUrl(@PathVariable("id") String base62EncodedId) {
        ShortenedUrlResponse cached = cacheManager.get(base62EncodedId);
        if (cached != null) {
            return ResponseEntity.ok(cached);
        }
        return super.getUrl(base62EncodedId);
    }

    @Scheduled(fixedRate = 10000)
    public void persistEntries() {
        Set<Map.Entry<String, ShortenedUrlResponse>> entries = cacheManager.getEntries();
        if (entries.isEmpty()) {
            return;
        }
        for (Map.Entry<String, ShortenedUrlResponse> entry : entries) {
            String base62EncodedId = entry.getKey();
            Long id = base62Handler.decode(base62EncodedId);
            Optional<UrlEntity> persisted = urlRepository.findById(id);
            if (persisted.isPresent()){
                continue;
            }
            log.info("persisting entry {}", base62EncodedId);
            urlRepository.save(assembler.toEntity(id, entry.getValue().getUrl()));
        }
    }

}
