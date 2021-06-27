package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductOptionRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionRel.class);
        ProductOptionRel productOptionRel1 = new ProductOptionRel();
        productOptionRel1.setId(1L);
        ProductOptionRel productOptionRel2 = new ProductOptionRel();
        productOptionRel2.setId(productOptionRel1.getId());
        assertThat(productOptionRel1).isEqualTo(productOptionRel2);
        productOptionRel2.setId(2L);
        assertThat(productOptionRel1).isNotEqualTo(productOptionRel2);
        productOptionRel1.setId(null);
        assertThat(productOptionRel1).isNotEqualTo(productOptionRel2);
    }
}
