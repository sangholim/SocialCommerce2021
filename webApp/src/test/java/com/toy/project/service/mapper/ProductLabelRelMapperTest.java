package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductLabelRelMapperTest {

    private ProductLabelRelMapper productLabelRelMapper;

    @BeforeEach
    public void setUp() {
        productLabelRelMapper = new ProductLabelRelMapperImpl();
    }
}
