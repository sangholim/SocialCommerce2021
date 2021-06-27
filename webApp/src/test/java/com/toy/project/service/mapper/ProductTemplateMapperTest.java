package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTemplateMapperTest {

    private ProductTemplateMapper productTemplateMapper;

    @BeforeEach
    public void setUp() {
        productTemplateMapper = new ProductTemplateMapperImpl();
    }
}
