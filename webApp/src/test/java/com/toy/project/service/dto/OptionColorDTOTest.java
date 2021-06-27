package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionColorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionColorDTO.class);
        OptionColorDTO optionColorDTO1 = new OptionColorDTO();
        optionColorDTO1.setId(1L);
        OptionColorDTO optionColorDTO2 = new OptionColorDTO();
        assertThat(optionColorDTO1).isNotEqualTo(optionColorDTO2);
        optionColorDTO2.setId(optionColorDTO1.getId());
        assertThat(optionColorDTO1).isEqualTo(optionColorDTO2);
        optionColorDTO2.setId(2L);
        assertThat(optionColorDTO1).isNotEqualTo(optionColorDTO2);
        optionColorDTO1.setId(null);
        assertThat(optionColorDTO1).isNotEqualTo(optionColorDTO2);
    }
}
