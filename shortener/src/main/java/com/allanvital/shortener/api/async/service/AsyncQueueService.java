package com.allanvital.shortener.api.async.service;

import com.allanvital.shortener.api.assembler.ResourceAssembler;
import com.allanvital.shortener.domain.model.UrlEntity;
import com.allanvital.shortener.domain.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import static com.allanvital.shortener.api.async.AsyncBeans.ASYNC_QUEUE_NAME;

/**
 * @author Allan Vital (https://allanvital.com)
 */
@Service
public class AsyncQueueService {

    private static final Logger log = LoggerFactory.getLogger(AsyncQueueService.class);

    private final UrlRepository urlRepository;
    private final ResourceAssembler assembler;
    private final RabbitTemplate rabbitTemplate;

    public AsyncQueueService(UrlRepository urlRepository, ResourceAssembler assembler, RabbitTemplate rabbitTemplate) {
        this.urlRepository = urlRepository;
        this.assembler = assembler;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String exchangeName, String routingKey, UrlEntity urlEntity) {
        rabbitTemplate.convertAndSend(exchangeName, routingKey, urlEntity);
    }

    @RabbitListener(queues = ASYNC_QUEUE_NAME)
    public void receive(UrlEntity urlEntity) {
        log.info("persisting {} async", urlEntity);
        urlRepository.save(urlEntity);
    }

}
