package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductAddOptionMapperTest {

    private ProductAddOptionMapper productAddOptionMapper;

    @BeforeEach
    public void setUp() {
        productAddOptionMapper = new ProductAddOptionMapperImpl();
    }
}
