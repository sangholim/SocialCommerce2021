package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductShippingRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductShippingRel.class);
        ProductShippingRel productShippingRel1 = new ProductShippingRel();
        productShippingRel1.setId(1L);
        ProductShippingRel productShippingRel2 = new ProductShippingRel();
        productShippingRel2.setId(productShippingRel1.getId());
        assertThat(productShippingRel1).isEqualTo(productShippingRel2);
        productShippingRel2.setId(2L);
        assertThat(productShippingRel1).isNotEqualTo(productShippingRel2);
        productShippingRel1.setId(null);
        assertThat(productShippingRel1).isNotEqualTo(productShippingRel2);
    }
}
