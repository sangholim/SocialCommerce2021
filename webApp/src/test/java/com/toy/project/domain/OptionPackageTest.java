package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class OptionPackageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OptionPackage.class);
        OptionPackage optionPackage1 = new OptionPackage();
        optionPackage1.setId(1L);
        OptionPackage optionPackage2 = new OptionPackage();
        optionPackage2.setId(optionPackage1.getId());
        assertThat(optionPackage1).isEqualTo(optionPackage2);
        optionPackage2.setId(2L);
        assertThat(optionPackage1).isNotEqualTo(optionPackage2);
        optionPackage1.setId(null);
        assertThat(optionPackage1).isNotEqualTo(optionPackage2);
    }
}
