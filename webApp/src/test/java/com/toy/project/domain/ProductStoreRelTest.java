package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductStoreRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductStoreRel.class);
        ProductStoreRel productStoreRel1 = new ProductStoreRel();
        productStoreRel1.setId(1L);
        ProductStoreRel productStoreRel2 = new ProductStoreRel();
        productStoreRel2.setId(productStoreRel1.getId());
        assertThat(productStoreRel1).isEqualTo(productStoreRel2);
        productStoreRel2.setId(2L);
        assertThat(productStoreRel1).isNotEqualTo(productStoreRel2);
        productStoreRel1.setId(null);
        assertThat(productStoreRel1).isNotEqualTo(productStoreRel2);
    }
}
