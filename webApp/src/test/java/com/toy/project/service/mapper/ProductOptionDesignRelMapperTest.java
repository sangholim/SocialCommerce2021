package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductOptionDesignRelMapperTest {

    private ProductOptionDesignRelMapper productOptionDesignRelMapper;

    @BeforeEach
    public void setUp() {
        productOptionDesignRelMapper = new ProductOptionDesignRelMapperImpl();
    }
}
