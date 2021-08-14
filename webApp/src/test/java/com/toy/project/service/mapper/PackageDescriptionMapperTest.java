package com.toy.project.service.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PackageDescriptionMapperTest {

    private PackageDescriptionMapper packageDescriptionMapper;

    @BeforeEach
    public void setUp() {
        packageDescriptionMapper = new PackageDescriptionMapperImpl();
    }
}
