package com.kabaev.shop.service.customer.domain;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Product {

    private Long id;
    private String code;
    private String name;
    private String description;
    private BigDecimal price;
    private List<Image> images;

    public void addImageToProduct(Image image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
    }

    public List<String> getImageUriList() {
        return images == null
                ? new ArrayList<>()
                : images.stream().map(Image::getUri).toList();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", images=" + images +
                '}';
    }

}
