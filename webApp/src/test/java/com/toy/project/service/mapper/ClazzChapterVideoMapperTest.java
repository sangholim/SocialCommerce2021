package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClazzChapterVideoMapperTest {

    private ClazzChapterVideoMapper clazzChapterVideoMapper;

    @BeforeEach
    public void setUp() {
        clazzChapterVideoMapper = new ClazzChapterVideoMapperImpl();
    }
}
