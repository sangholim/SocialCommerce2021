package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductNoticeRelMapperTest {

    private ProductNoticeRelMapper productNoticeRelMapper;

    @BeforeEach
    public void setUp() {
        productNoticeRelMapper = new ProductNoticeRelMapperImpl();
    }
}
