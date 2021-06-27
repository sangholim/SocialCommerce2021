package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionPackageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionPackageDTO.class);
        OptionPackageDTO optionPackageDTO1 = new OptionPackageDTO();
        optionPackageDTO1.setId(1L);
        OptionPackageDTO optionPackageDTO2 = new OptionPackageDTO();
        assertThat(optionPackageDTO1).isNotEqualTo(optionPackageDTO2);
        optionPackageDTO2.setId(optionPackageDTO1.getId());
        assertThat(optionPackageDTO1).isEqualTo(optionPackageDTO2);
        optionPackageDTO2.setId(2L);
        assertThat(optionPackageDTO1).isNotEqualTo(optionPackageDTO2);
        optionPackageDTO1.setId(null);
        assertThat(optionPackageDTO1).isNotEqualTo(optionPackageDTO2);
    }
}
