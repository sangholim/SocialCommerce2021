package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionDesignTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionDesign.class);
        OptionDesign optionDesign1 = new OptionDesign();
        optionDesign1.setId(1L);
        OptionDesign optionDesign2 = new OptionDesign();
        optionDesign2.setId(optionDesign1.getId());
        assertThat(optionDesign1).isEqualTo(optionDesign2);
        optionDesign2.setId(2L);
        assertThat(optionDesign1).isNotEqualTo(optionDesign2);
        optionDesign1.setId(null);
        assertThat(optionDesign1).isNotEqualTo(optionDesign2);
    }
}
