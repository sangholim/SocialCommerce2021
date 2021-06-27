package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductOptionColorRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionColorRel.class);
        ProductOptionColorRel productOptionColorRel1 = new ProductOptionColorRel();
        productOptionColorRel1.setId(1L);
        ProductOptionColorRel productOptionColorRel2 = new ProductOptionColorRel();
        productOptionColorRel2.setId(productOptionColorRel1.getId());
        assertThat(productOptionColorRel1).isEqualTo(productOptionColorRel2);
        productOptionColorRel2.setId(2L);
        assertThat(productOptionColorRel1).isNotEqualTo(productOptionColorRel2);
        productOptionColorRel1.setId(null);
        assertThat(productOptionColorRel1).isNotEqualTo(productOptionColorRel2);
    }
}
