package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClazzChapterMapperTest {

    private ClazzChapterMapper clazzChapterMapper;

    @BeforeEach
    public void setUp() {
        clazzChapterMapper = new ClazzChapterMapperImpl();
    }
}
