package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductAnnounceTemplateMapperTest {

    private ProductAnnounceTemplateMapper productAnnounceTemplateMapper;

    @BeforeEach
    public void setUp() {
        productAnnounceTemplateMapper = new ProductAnnounceTemplateMapperImpl();
    }
}
