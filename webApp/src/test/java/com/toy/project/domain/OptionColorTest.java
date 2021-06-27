package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionColorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionColor.class);
        OptionColor optionColor1 = new OptionColor();
        optionColor1.setId(1L);
        OptionColor optionColor2 = new OptionColor();
        optionColor2.setId(optionColor1.getId());
        assertThat(optionColor1).isEqualTo(optionColor2);
        optionColor2.setId(2L);
        assertThat(optionColor1).isNotEqualTo(optionColor2);
        optionColor1.setId(null);
        assertThat(optionColor1).isNotEqualTo(optionColor2);
    }
}
