package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductNoticeManageMapperTest {

    private ProductNoticeManageMapper productNoticeManageMapper;

    @BeforeEach
    public void setUp() {
        productNoticeManageMapper = new ProductNoticeManageMapperImpl();
    }
}
