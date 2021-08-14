package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PackageDescriptionImageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageDescriptionImageDTO.class);
        PackageDescriptionImageDTO packageDescriptionImageDTO1 = new PackageDescriptionImageDTO();
        packageDescriptionImageDTO1.setId(1L);
        PackageDescriptionImageDTO packageDescriptionImageDTO2 = new PackageDescriptionImageDTO();
        assertThat(packageDescriptionImageDTO1).isNotEqualTo(packageDescriptionImageDTO2);
        packageDescriptionImageDTO2.setId(packageDescriptionImageDTO1.getId());
        assertThat(packageDescriptionImageDTO1).isEqualTo(packageDescriptionImageDTO2);
        packageDescriptionImageDTO2.setId(2L);
        assertThat(packageDescriptionImageDTO1).isNotEqualTo(packageDescriptionImageDTO2);
        packageDescriptionImageDTO1.setId(null);
        assertThat(packageDescriptionImageDTO1).isNotEqualTo(packageDescriptionImageDTO2);
    }
}
