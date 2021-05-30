package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PCategoryMapperTest {

    private PCategoryMapper pCategoryMapper;

    @BeforeEach
    public void setUp() {
        pCategoryMapper = new PCategoryMapperImpl();
    }
}
