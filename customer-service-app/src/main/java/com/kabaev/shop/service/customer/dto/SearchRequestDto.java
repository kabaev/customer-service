package com.kabaev.shop.service.customer.dto;

import java.util.List;

public class SearchRequestDto {

    private List<String> fields;
    private String SearchTerm;

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public String getSearchTerm() {
        return SearchTerm;
    }

    public void setSearchTerm(String searchTerm) {
        SearchTerm = searchTerm;
    }

}
