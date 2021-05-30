package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PCategoryDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PCategoryDTO.class);
        PCategoryDTO pCategoryDTO1 = new PCategoryDTO();
        pCategoryDTO1.setId(1L);
        PCategoryDTO pCategoryDTO2 = new PCategoryDTO();
        assertThat(pCategoryDTO1).isNotEqualTo(pCategoryDTO2);
        pCategoryDTO2.setId(pCategoryDTO1.getId());
        assertThat(pCategoryDTO1).isEqualTo(pCategoryDTO2);
        pCategoryDTO2.setId(2L);
        assertThat(pCategoryDTO1).isNotEqualTo(pCategoryDTO2);
        pCategoryDTO1.setId(null);
        assertThat(pCategoryDTO1).isNotEqualTo(pCategoryDTO2);
    }
}
