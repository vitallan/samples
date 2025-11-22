package com.allanvital.shortener.api.async.endpoint.writecache;

import com.allanvital.shortener.api.assembler.ResourceAssembler;
import com.allanvital.shortener.api.async.endpoint.eventual.AsyncEndpoint;
import com.allanvital.shortener.api.async.service.AsyncQueueService;
import com.allanvital.shortener.cache.CacheManager;
import com.allanvital.shortener.domain.id.Base62Handler;
import com.allanvital.shortener.domain.pojo.ShortenUrlRequest;
import com.allanvital.shortener.domain.pojo.ShortenedUrlResponse;
import com.allanvital.shortener.domain.repository.UrlRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.allanvital.shortener.ShortenerApplication.WRITE_CACHE_ENDPOINT;

/**
 * @author Allan Vital (https://allanvital.com)
 */
@RestController
@RequestMapping(WRITE_CACHE_ENDPOINT)
public class WriteCacheEndpoint extends AsyncEndpoint {

    private final CacheManager cacheManager;

    public WriteCacheEndpoint(ResourceAssembler assembler,
                              Base62Handler base62Handler,
                              UrlRepository urlRepository,
                              AsyncQueueService asyncQueueService,
                              CacheManager cacheManager) {

        super(assembler, base62Handler, urlRepository, asyncQueueService);
        this.cacheManager = cacheManager;
    }

    @Override
    public ResponseEntity<ShortenedUrlResponse> createUrl(@RequestBody ShortenUrlRequest request) {
        ResponseEntity<ShortenedUrlResponse> response = super.createUrl(request);
        cacheManager.put(response.getBody());
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShortenedUrlResponse> getUrl(@PathVariable("id") String base62EncodedId) {
        ShortenedUrlResponse cached = cacheManager.get(base62EncodedId);
        if (cached != null) {
            return ResponseEntity.ok(cached);
        }
        return super.getUrl(base62EncodedId);
    }

}
