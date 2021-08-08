package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductAddImageMapperTest {

    private ProductAddImageMapper productAddImageMapper;

    @BeforeEach
    public void setUp() {
        productAddImageMapper = new ProductAddImageMapperImpl();
    }
}
