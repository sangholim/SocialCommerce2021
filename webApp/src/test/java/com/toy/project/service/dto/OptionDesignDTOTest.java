package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionDesignDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionDesignDTO.class);
        OptionDesignDTO optionDesignDTO1 = new OptionDesignDTO();
        optionDesignDTO1.setId(1L);
        OptionDesignDTO optionDesignDTO2 = new OptionDesignDTO();
        assertThat(optionDesignDTO1).isNotEqualTo(optionDesignDTO2);
        optionDesignDTO2.setId(optionDesignDTO1.getId());
        assertThat(optionDesignDTO1).isEqualTo(optionDesignDTO2);
        optionDesignDTO2.setId(2L);
        assertThat(optionDesignDTO1).isNotEqualTo(optionDesignDTO2);
        optionDesignDTO1.setId(null);
        assertThat(optionDesignDTO1).isNotEqualTo(optionDesignDTO2);
    }
}
