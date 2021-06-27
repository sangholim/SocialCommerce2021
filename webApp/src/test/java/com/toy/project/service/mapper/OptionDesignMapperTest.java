package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OptionDesignMapperTest {

    private OptionDesignMapper optionDesignMapper;

    @BeforeEach
    public void setUp() {
        optionDesignMapper = new OptionDesignMapperImpl();
    }
}
