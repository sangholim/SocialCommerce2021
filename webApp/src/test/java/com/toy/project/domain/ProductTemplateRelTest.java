package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTemplateRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTemplateRel.class);
        ProductTemplateRel productTemplateRel1 = new ProductTemplateRel();
        productTemplateRel1.setId(1L);
        ProductTemplateRel productTemplateRel2 = new ProductTemplateRel();
        productTemplateRel2.setId(productTemplateRel1.getId());
        assertThat(productTemplateRel1).isEqualTo(productTemplateRel2);
        productTemplateRel2.setId(2L);
        assertThat(productTemplateRel1).isNotEqualTo(productTemplateRel2);
        productTemplateRel1.setId(null);
        assertThat(productTemplateRel1).isNotEqualTo(productTemplateRel2);
    }
}
