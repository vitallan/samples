package com.allanvital.shortener.domain.pojo;

/**
 * @author Allan Vital (https://allanvital.com)
 */
public class ShortenedUrlResponse {

    private String url;
    private String shortened;

    public ShortenedUrlResponse(String shortened, String url) {
        this.shortened = shortened;
        this.url = url;
    }

    public String getShortened() {
        return shortened;
    }

    public void setShortened(String shortened) {
        this.shortened = shortened;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "ShortenedUrlResponse {" +
                "shortened='" + shortened +
                "', url='" + url + '\'' +
                '}';
    }
}
