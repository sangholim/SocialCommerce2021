package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductViewContentMapperTest {

    private ProductViewContentMapper productViewContentMapper;

    @BeforeEach
    public void setUp() {
        productViewContentMapper = new ProductViewContentMapperImpl();
    }
}
