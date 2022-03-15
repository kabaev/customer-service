package com.kabaev.shop.service.customer.domain;

import com.kabaev.shop.service.customer.dto.ProductDto;
import com.kabaev.shop.service.customer.helper.Indices;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.math.BigDecimal;
import java.util.List;

@Document(indexName = Indices.PRODUCT_INDEX)
@Setting(settingPath = "static/es-settings.json")
public class Product {

    @Id
    @Field(type = FieldType.Keyword)
    private String code;
    @Field(type = FieldType.Text)
    private String name;
    @Field(type = FieldType.Text)
    private String description;
    @Field(type = FieldType.Long)
    private BigDecimal price;
    @Field(type = FieldType.Boolean)
    private boolean deleted;
    @Field(type = FieldType.Text)
    private List<String> images;

    public Product() {
    }

    public Product(ProductDto productDto) {
        this.code = productDto.code();
        this.name = productDto.name();
        this.description = productDto.description();
        this.price = productDto.price();
        this.deleted = productDto.deleted();
        this.images = productDto.imageUriList();
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

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Product{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", deleted=" + deleted +
                ", images=" + images +
                '}';
    }

}
