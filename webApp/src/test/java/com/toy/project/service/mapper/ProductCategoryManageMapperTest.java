package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductCategoryManageMapperTest {

    private ProductCategoryManageMapper productCategoryManageMapper;

    @BeforeEach
    public void setUp() {
        productCategoryManageMapper = new ProductCategoryManageMapperImpl();
    }
}
