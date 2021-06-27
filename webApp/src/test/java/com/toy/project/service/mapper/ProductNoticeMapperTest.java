package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductNoticeMapperTest {

    private ProductNoticeMapper productNoticeMapper;

    @BeforeEach
    public void setUp() {
        productNoticeMapper = new ProductNoticeMapperImpl();
    }
}
