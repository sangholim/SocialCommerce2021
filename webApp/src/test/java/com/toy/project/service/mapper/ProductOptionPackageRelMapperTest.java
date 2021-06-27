package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductOptionPackageRelMapperTest {

    private ProductOptionPackageRelMapper productOptionPackageRelMapper;

    @BeforeEach
    public void setUp() {
        productOptionPackageRelMapper = new ProductOptionPackageRelMapperImpl();
    }
}
