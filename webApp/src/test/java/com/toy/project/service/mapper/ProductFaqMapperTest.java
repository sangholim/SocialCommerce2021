package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductFaqMapperTest {

    private ProductFaqMapper productFaqMapper;

    @BeforeEach
    public void setUp() {
        productFaqMapper = new ProductFaqMapperImpl();
    }
}
