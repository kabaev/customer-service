package com.kabaev.shop.service.customer.domain;

public class Image {

    private Long id;
    private String uri;
    private String key;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", uri='" + uri + '\'' +
                ", key='" + key + '\'' +
                '}';
    }

}
