package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductShippingManageMapperTest {

    private ProductShippingManageMapper productShippingManageMapper;

    @BeforeEach
    public void setUp() {
        productShippingManageMapper = new ProductShippingManageMapperImpl();
    }
}
