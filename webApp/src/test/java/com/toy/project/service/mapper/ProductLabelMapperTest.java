package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductLabelMapperTest {

    private ProductLabelMapper productLabelMapper;

    @BeforeEach
    public void setUp() {
        productLabelMapper = new ProductLabelMapperImpl();
    }
}
