package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductOptionColorRelMapperTest {

    private ProductOptionColorRelMapper productOptionColorRelMapper;

    @BeforeEach
    public void setUp() {
        productOptionColorRelMapper = new ProductOptionColorRelMapperImpl();
    }
}
