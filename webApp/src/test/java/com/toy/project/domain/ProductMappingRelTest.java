package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductMappingRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductMappingRel.class);
        ProductMappingRel productMappingRel1 = new ProductMappingRel();
        productMappingRel1.setId(1L);
        ProductMappingRel productMappingRel2 = new ProductMappingRel();
        productMappingRel2.setId(productMappingRel1.getId());
        assertThat(productMappingRel1).isEqualTo(productMappingRel2);
        productMappingRel2.setId(2L);
        assertThat(productMappingRel1).isNotEqualTo(productMappingRel2);
        productMappingRel1.setId(null);
        assertThat(productMappingRel1).isNotEqualTo(productMappingRel2);
    }
}
