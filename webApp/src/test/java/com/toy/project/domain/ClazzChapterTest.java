package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClazzChapterTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClazzChapter.class);
        ClazzChapter clazzChapter1 = new ClazzChapter();
        clazzChapter1.setId(1L);
        ClazzChapter clazzChapter2 = new ClazzChapter();
        clazzChapter2.setId(clazzChapter1.getId());
        assertThat(clazzChapter1).isEqualTo(clazzChapter2);
        clazzChapter2.setId(2L);
        assertThat(clazzChapter1).isNotEqualTo(clazzChapter2);
        clazzChapter1.setId(null);
        assertThat(clazzChapter1).isNotEqualTo(clazzChapter2);
    }
}
