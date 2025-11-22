package com.allanvital.shortener.domain.pojo;

/**
 * @author Allan Vital (https://allanvital.com)
 */
public class ShortenUrlRequest {

    private String url;

    public ShortenUrlRequest() {

    }

    public ShortenUrlRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
