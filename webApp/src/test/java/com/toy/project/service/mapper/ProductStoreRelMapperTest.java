package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductStoreRelMapperTest {

    private ProductStoreRelMapper productStoreRelMapper;

    @BeforeEach
    public void setUp() {
        productStoreRelMapper = new ProductStoreRelMapperImpl();
    }
}
