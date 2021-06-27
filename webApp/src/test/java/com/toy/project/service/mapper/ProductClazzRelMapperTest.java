package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductClazzRelMapperTest {

    private ProductClazzRelMapper productClazzRelMapper;

    @BeforeEach
    public void setUp() {
        productClazzRelMapper = new ProductClazzRelMapperImpl();
    }
}
