package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductMappingManageMapperTest {

    private ProductMappingManageMapper productMappingManageMapper;

    @BeforeEach
    public void setUp() {
        productMappingManageMapper = new ProductMappingManageMapperImpl();
    }
}
