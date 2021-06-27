package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductShippingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductShipping.class);
        ProductShipping productShipping1 = new ProductShipping();
        productShipping1.setId(1L);
        ProductShipping productShipping2 = new ProductShipping();
        productShipping2.setId(productShipping1.getId());
        assertThat(productShipping1).isEqualTo(productShipping2);
        productShipping2.setId(2L);
        assertThat(productShipping1).isNotEqualTo(productShipping2);
        productShipping1.setId(null);
        assertThat(productShipping1).isNotEqualTo(productShipping2);
    }
}
