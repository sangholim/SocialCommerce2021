package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClazzChapterDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClazzChapterDTO.class);
        ClazzChapterDTO clazzChapterDTO1 = new ClazzChapterDTO();
        clazzChapterDTO1.setId(1L);
        ClazzChapterDTO clazzChapterDTO2 = new ClazzChapterDTO();
        assertThat(clazzChapterDTO1).isNotEqualTo(clazzChapterDTO2);
        clazzChapterDTO2.setId(clazzChapterDTO1.getId());
        assertThat(clazzChapterDTO1).isEqualTo(clazzChapterDTO2);
        clazzChapterDTO2.setId(2L);
        assertThat(clazzChapterDTO1).isNotEqualTo(clazzChapterDTO2);
        clazzChapterDTO1.setId(null);
        assertThat(clazzChapterDTO1).isNotEqualTo(clazzChapterDTO2);
    }
}
