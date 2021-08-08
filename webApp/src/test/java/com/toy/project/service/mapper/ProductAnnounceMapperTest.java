package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductAnnounceMapperTest {

    private ProductAnnounceMapper productAnnounceMapper;

    @BeforeEach
    public void setUp() {
        productAnnounceMapper = new ProductAnnounceMapperImpl();
    }
}
