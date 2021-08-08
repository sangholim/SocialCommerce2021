package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClazzChapterVideoDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClazzChapterVideoDTO.class);
        ClazzChapterVideoDTO clazzChapterVideoDTO1 = new ClazzChapterVideoDTO();
        clazzChapterVideoDTO1.setId(1L);
        ClazzChapterVideoDTO clazzChapterVideoDTO2 = new ClazzChapterVideoDTO();
        assertThat(clazzChapterVideoDTO1).isNotEqualTo(clazzChapterVideoDTO2);
        clazzChapterVideoDTO2.setId(clazzChapterVideoDTO1.getId());
        assertThat(clazzChapterVideoDTO1).isEqualTo(clazzChapterVideoDTO2);
        clazzChapterVideoDTO2.setId(2L);
        assertThat(clazzChapterVideoDTO1).isNotEqualTo(clazzChapterVideoDTO2);
        clazzChapterVideoDTO1.setId(null);
        assertThat(clazzChapterVideoDTO1).isNotEqualTo(clazzChapterVideoDTO2);
    }
}
