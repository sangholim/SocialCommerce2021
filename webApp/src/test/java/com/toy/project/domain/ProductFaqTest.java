package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductFaqTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductFaq.class);
        ProductFaq productFaq1 = new ProductFaq();
        productFaq1.setId(1L);
        ProductFaq productFaq2 = new ProductFaq();
        productFaq2.setId(productFaq1.getId());
        assertThat(productFaq1).isEqualTo(productFaq2);
        productFaq2.setId(2L);
        assertThat(productFaq1).isNotEqualTo(productFaq2);
        productFaq1.setId(null);
        assertThat(productFaq1).isNotEqualTo(productFaq2);
    }
}
