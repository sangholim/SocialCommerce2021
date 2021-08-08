package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTemplate.class);
        ProductTemplate productTemplate1 = new ProductTemplate();
        productTemplate1.setId(1L);
        ProductTemplate productTemplate2 = new ProductTemplate();
        productTemplate2.setId(productTemplate1.getId());
        assertThat(productTemplate1).isEqualTo(productTemplate2);
        productTemplate2.setId(2L);
        assertThat(productTemplate1).isNotEqualTo(productTemplate2);
        productTemplate1.setId(null);
        assertThat(productTemplate1).isNotEqualTo(productTemplate2);
    }
}
