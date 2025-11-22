package com.allanvital.shortener.domain.model;

import jakarta.persistence.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author Allan Vital (https://allanvital.com)
 */
@Entity
@Table(name = "urls")
public class UrlEntity implements Serializable {

    @Serial
    @Transient
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    @Column(nullable = false)
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UrlEntity {" +
                "id=" + id +
                ", url='" + url + '\'' +
                '}';
    }
}
