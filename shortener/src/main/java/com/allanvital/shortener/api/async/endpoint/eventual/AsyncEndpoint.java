package com.allanvital.shortener.api.async.endpoint.eventual;

import com.allanvital.shortener.api.GenericEndpoint;
import com.allanvital.shortener.api.assembler.ResourceAssembler;
import com.allanvital.shortener.api.async.service.AsyncQueueService;
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

import static com.allanvital.shortener.ShortenerApplication.ASYNC_ENDPOINT;
import static com.allanvital.shortener.api.async.AsyncBeans.ASYNC_EXCHANGE_NAME;
import static com.allanvital.shortener.api.async.AsyncBeans.ASYNC_ROUTING_KEY;

/**
 * @author Allan Vital (https://allanvital.com)
 */
@RestController
@RequestMapping(ASYNC_ENDPOINT)
public class AsyncEndpoint extends GenericEndpoint {

    private static final Logger log = LoggerFactory.getLogger(AsyncEndpoint.class);

    private final AsyncQueueService asyncQueueService;

    public AsyncEndpoint(ResourceAssembler assembler, Base62Handler base62Handler, UrlRepository urlRepository, AsyncQueueService asyncQueueService) {
        super(assembler, base62Handler, urlRepository);
        this.asyncQueueService = asyncQueueService;
    }

    @Override
    public ResponseEntity<ShortenedUrlResponse> createUrl(@RequestBody ShortenUrlRequest request) {
        UrlEntity entity = assembler.toEntity(request);
        asyncQueueService.send(ASYNC_EXCHANGE_NAME, ASYNC_ROUTING_KEY, entity);
        ShortenedUrlResponse response = assembler.toResource(entity);
        log.info("request queued '{}'", request.getUrl());
        return ResponseEntity.ok(response);
    }

}
