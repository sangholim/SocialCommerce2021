package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductInputOptionMapperTest {

    private ProductInputOptionMapper productInputOptionMapper;

    @BeforeEach
    public void setUp() {
        productInputOptionMapper = new ProductInputOptionMapperImpl();
    }
}
