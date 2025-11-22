package com.allanvital.shortener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class ShortenerApplication {

    public static final String SYNC_ENDPOINT = "/sync";
    public static final String ASYNC_ENDPOINT = "/async";
    public static final String WRITE_CACHE_ENDPOINT = "/writecache";
    public static final String PERIODIC_ENDPOINT = "/periodic";

	public static void main(String[] args) {
		SpringApplication.run(ShortenerApplication.class, args);
	}

}
