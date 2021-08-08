package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductMappingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductMapping.class);
        ProductMapping productMapping1 = new ProductMapping();
        productMapping1.setId(1L);
        ProductMapping productMapping2 = new ProductMapping();
        productMapping2.setId(productMapping1.getId());
        assertThat(productMapping1).isEqualTo(productMapping2);
        productMapping2.setId(2L);
        assertThat(productMapping1).isNotEqualTo(productMapping2);
        productMapping1.setId(null);
        assertThat(productMapping1).isNotEqualTo(productMapping2);
    }
}
