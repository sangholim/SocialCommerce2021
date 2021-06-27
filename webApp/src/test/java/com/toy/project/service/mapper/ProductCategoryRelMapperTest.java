package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductCategoryRelMapperTest {

    private ProductCategoryRelMapper productCategoryRelMapper;

    @BeforeEach
    public void setUp() {
        productCategoryRelMapper = new ProductCategoryRelMapperImpl();
    }
}
