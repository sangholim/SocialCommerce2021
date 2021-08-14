package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PackageDescriptionImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageDescriptionImage.class);
        PackageDescriptionImage packageDescriptionImage1 = new PackageDescriptionImage();
        packageDescriptionImage1.setId(1L);
        PackageDescriptionImage packageDescriptionImage2 = new PackageDescriptionImage();
        packageDescriptionImage2.setId(packageDescriptionImage1.getId());
        assertThat(packageDescriptionImage1).isEqualTo(packageDescriptionImage2);
        packageDescriptionImage2.setId(2L);
        assertThat(packageDescriptionImage1).isNotEqualTo(packageDescriptionImage2);
        packageDescriptionImage1.setId(null);
        assertThat(packageDescriptionImage1).isNotEqualTo(packageDescriptionImage2);
    }
}
