package com.allanvital.shortener.api.async;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Allan Vital (https://allanvital.com)
 */
@Configuration
@EnableRabbit
public class AsyncBeans {

    public static final String ASYNC_QUEUE_NAME = "async.queue";
    public static final String ASYNC_EXCHANGE_NAME = "async.exchange";
    public static final String ASYNC_ROUTING_KEY = "async.routing-key";

    @Bean
    public Queue asyncQueue() {
        return QueueBuilder.durable(ASYNC_QUEUE_NAME).build();
    }

    @Bean
    public TopicExchange asyncExchange() {
        return new TopicExchange(ASYNC_EXCHANGE_NAME);
    }

    @Bean
    public Binding asyncBinding(Queue demoQueue, TopicExchange demoExchange) {
        return BindingBuilder
                .bind(demoQueue)
                .to(demoExchange)
                .with(ASYNC_ROUTING_KEY);
    }

}
