package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PackageDescriptionImageMapperTest {

    private PackageDescriptionImageMapper packageDescriptionImageMapper;

    @BeforeEach
    public void setUp() {
        packageDescriptionImageMapper = new PackageDescriptionImageMapperImpl();
    }
}
