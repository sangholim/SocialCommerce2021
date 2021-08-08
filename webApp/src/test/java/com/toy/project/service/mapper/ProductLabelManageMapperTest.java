package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductLabelManageMapperTest {

    private ProductLabelManageMapper productLabelManageMapper;

    @BeforeEach
    public void setUp() {
        productLabelManageMapper = new ProductLabelManageMapperImpl();
    }
}
