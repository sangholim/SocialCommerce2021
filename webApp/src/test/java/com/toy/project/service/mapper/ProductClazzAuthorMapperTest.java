package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductClazzAuthorMapperTest {

    private ProductClazzAuthorMapper productClazzAuthorMapper;

    @BeforeEach
    public void setUp() {
        productClazzAuthorMapper = new ProductClazzAuthorMapperImpl();
    }
}
