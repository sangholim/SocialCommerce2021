package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductViewRelMapperTest {

    private ProductViewRelMapper productViewRelMapper;

    @BeforeEach
    public void setUp() {
        productViewRelMapper = new ProductViewRelMapperImpl();
    }
}
