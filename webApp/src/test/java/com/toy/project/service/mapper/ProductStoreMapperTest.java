package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductStoreMapperTest {

    private ProductStoreMapper productStoreMapper;

    @BeforeEach
    public void setUp() {
        productStoreMapper = new ProductStoreMapperImpl();
    }
}
