package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ClazzTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Clazz.class);
        Clazz clazz1 = new Clazz();
        clazz1.setId(1L);
        Clazz clazz2 = new Clazz();
        clazz2.setId(clazz1.getId());
        assertThat(clazz1).isEqualTo(clazz2);
        clazz2.setId(2L);
        assertThat(clazz1).isNotEqualTo(clazz2);
        clazz1.setId(null);
        assertThat(clazz1).isNotEqualTo(clazz2);
    }
}
