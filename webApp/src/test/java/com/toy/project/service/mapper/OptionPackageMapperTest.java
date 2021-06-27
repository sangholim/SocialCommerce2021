package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OptionPackageMapperTest {

    private OptionPackageMapper optionPackageMapper;

    @BeforeEach
    public void setUp() {
        optionPackageMapper = new OptionPackageMapperImpl();
    }
}
