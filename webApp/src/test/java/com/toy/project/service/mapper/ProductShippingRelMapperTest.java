package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductShippingRelMapperTest {

    private ProductShippingRelMapper productShippingRelMapper;

    @BeforeEach
    public void setUp() {
        productShippingRelMapper = new ProductShippingRelMapperImpl();
    }
}
