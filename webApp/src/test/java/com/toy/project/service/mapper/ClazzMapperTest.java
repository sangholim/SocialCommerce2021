package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClazzMapperTest {

    private ClazzMapper clazzMapper;

    @BeforeEach
    public void setUp() {
        clazzMapper = new ClazzMapperImpl();
    }
}
