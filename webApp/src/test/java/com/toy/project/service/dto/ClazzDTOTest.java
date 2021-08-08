package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClazzDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClazzDTO.class);
        ClazzDTO clazzDTO1 = new ClazzDTO();
        clazzDTO1.setId(1L);
        ClazzDTO clazzDTO2 = new ClazzDTO();
        assertThat(clazzDTO1).isNotEqualTo(clazzDTO2);
        clazzDTO2.setId(clazzDTO1.getId());
        assertThat(clazzDTO1).isEqualTo(clazzDTO2);
        clazzDTO2.setId(2L);
        assertThat(clazzDTO1).isNotEqualTo(clazzDTO2);
        clazzDTO1.setId(null);
        assertThat(clazzDTO1).isNotEqualTo(clazzDTO2);
    }
}
