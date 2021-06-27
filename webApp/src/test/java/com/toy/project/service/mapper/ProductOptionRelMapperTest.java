package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductOptionRelMapperTest {

    private ProductOptionRelMapper productOptionRelMapper;

    @BeforeEach
    public void setUp() {
        productOptionRelMapper = new ProductOptionRelMapperImpl();
    }
}
