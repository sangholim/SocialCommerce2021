package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PCategory.class);
        PCategory pCategory1 = new PCategory();
        pCategory1.setId(1L);
        PCategory pCategory2 = new PCategory();
        pCategory2.setId(pCategory1.getId());
        assertThat(pCategory1).isEqualTo(pCategory2);
        pCategory2.setId(2L);
        assertThat(pCategory1).isNotEqualTo(pCategory2);
        pCategory1.setId(null);
        assertThat(pCategory1).isNotEqualTo(pCategory2);
    }
}
