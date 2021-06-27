package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductShippingMapperTest {

    private ProductShippingMapper productShippingMapper;

    @BeforeEach
    public void setUp() {
        productShippingMapper = new ProductShippingMapperImpl();
    }
}
