package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductAddOptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAddOption.class);
        ProductAddOption productAddOption1 = new ProductAddOption();
        productAddOption1.setId(1L);
        ProductAddOption productAddOption2 = new ProductAddOption();
        productAddOption2.setId(productAddOption1.getId());
        assertThat(productAddOption1).isEqualTo(productAddOption2);
        productAddOption2.setId(2L);
        assertThat(productAddOption1).isNotEqualTo(productAddOption2);
        productAddOption1.setId(null);
        assertThat(productAddOption1).isNotEqualTo(productAddOption2);
    }
}
