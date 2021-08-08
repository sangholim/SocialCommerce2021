package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductMappingMapperTest {

    private ProductMappingMapper productMappingMapper;

    @BeforeEach
    public void setUp() {
        productMappingMapper = new ProductMappingMapperImpl();
    }
}
