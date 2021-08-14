package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PackageDescriptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageDescription.class);
        PackageDescription packageDescription1 = new PackageDescription();
        packageDescription1.setId(1L);
        PackageDescription packageDescription2 = new PackageDescription();
        packageDescription2.setId(packageDescription1.getId());
        assertThat(packageDescription1).isEqualTo(packageDescription2);
        packageDescription2.setId(2L);
        assertThat(packageDescription1).isNotEqualTo(packageDescription2);
        packageDescription1.setId(null);
        assertThat(packageDescription1).isNotEqualTo(packageDescription2);
    }
}
