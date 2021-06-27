package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductTemplateRelMapperTest {

    private ProductTemplateRelMapper productTemplateRelMapper;

    @BeforeEach
    public void setUp() {
        productTemplateRelMapper = new ProductTemplateRelMapperImpl();
    }
}
