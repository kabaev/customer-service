package com.kabaev.shop.service.customer.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kabaev.shop.service.customer.domain.Product;
import com.kabaev.shop.service.customer.dto.ProductDto;
import com.kabaev.shop.service.customer.dto.SearchRequestDto;
import com.kabaev.shop.service.customer.helper.Indices;
import com.kabaev.shop.service.customer.utils.SearchUtil;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class SearchService {

    private final RestHighLevelClient client;
    private final ObjectMapper objectMapper;

    public SearchService(
            RestHighLevelClient client,
            ObjectMapper objectMapper) {
        this.client = client;
        this.objectMapper = objectMapper;
    }

    public List<ProductDto> search(final SearchRequestDto dto) {
        final SearchRequest request = SearchUtil.buildSearchRequest(
                Indices.PRODUCT_INDEX,
                dto
        );

        if (request == null) {
            log.error("Failed to build search request");
            return Collections.emptyList();
        }

        try {
            final SearchResponse response = client.search(request, RequestOptions.DEFAULT);
            final SearchHit[] searchHits = response.getHits().getHits();
            final List<ProductDto> products = new ArrayList<>(searchHits.length);
            for (SearchHit hit : searchHits) {
                Product product = objectMapper.readValue(hit.getSourceAsString(), Product.class);
                products.add(new ProductDto(product));
            }
            return products;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Collections.emptyList();
        }

    }

    public boolean index(final Product product) {
        try {
            final String vehicleAsString = objectMapper.writeValueAsString(product);
            final IndexRequest request = new IndexRequest(Indices.PRODUCT_INDEX);
            request.id(product.getCode());
            request.source(vehicleAsString, XContentType.JSON);
            final IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return response != null && response.status().equals(RestStatus.OK);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
            return false;
        }
    }

    public ProductDto getProductByCode(final String productCode) {
        try {
            final GetResponse documentFields = client.get(
                    new GetRequest(Indices.PRODUCT_INDEX, productCode),
                    RequestOptions.DEFAULT
            );

            if (documentFields == null || documentFields.isSourceEmpty()) {
                return null;
            }

            Product product = objectMapper.readValue(documentFields.getSourceAsString(), Product.class);
            return new ProductDto(product);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
