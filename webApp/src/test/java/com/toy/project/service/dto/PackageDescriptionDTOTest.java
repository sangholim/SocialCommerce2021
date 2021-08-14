package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PackageDescriptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PackageDescriptionDTO.class);
        PackageDescriptionDTO packageDescriptionDTO1 = new PackageDescriptionDTO();
        packageDescriptionDTO1.setId(1L);
        PackageDescriptionDTO packageDescriptionDTO2 = new PackageDescriptionDTO();
        assertThat(packageDescriptionDTO1).isNotEqualTo(packageDescriptionDTO2);
        packageDescriptionDTO2.setId(packageDescriptionDTO1.getId());
        assertThat(packageDescriptionDTO1).isEqualTo(packageDescriptionDTO2);
        packageDescriptionDTO2.setId(2L);
        assertThat(packageDescriptionDTO1).isNotEqualTo(packageDescriptionDTO2);
        packageDescriptionDTO1.setId(null);
        assertThat(packageDescriptionDTO1).isNotEqualTo(packageDescriptionDTO2);
    }
}
