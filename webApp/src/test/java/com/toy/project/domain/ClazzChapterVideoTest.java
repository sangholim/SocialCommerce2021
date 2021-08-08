package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClazzChapterVideoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClazzChapterVideo.class);
        ClazzChapterVideo clazzChapterVideo1 = new ClazzChapterVideo();
        clazzChapterVideo1.setId(1L);
        ClazzChapterVideo clazzChapterVideo2 = new ClazzChapterVideo();
        clazzChapterVideo2.setId(clazzChapterVideo1.getId());
        assertThat(clazzChapterVideo1).isEqualTo(clazzChapterVideo2);
        clazzChapterVideo2.setId(2L);
        assertThat(clazzChapterVideo1).isNotEqualTo(clazzChapterVideo2);
        clazzChapterVideo1.setId(null);
        assertThat(clazzChapterVideo1).isNotEqualTo(clazzChapterVideo2);
    }
}
