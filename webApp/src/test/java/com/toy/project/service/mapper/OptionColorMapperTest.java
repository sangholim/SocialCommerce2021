package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OptionColorMapperTest {

    private OptionColorMapper optionColorMapper;

    @BeforeEach
    public void setUp() {
        optionColorMapper = new OptionColorMapperImpl();
    }
}
