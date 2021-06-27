package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductClazzRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductClazzRel.class);
        ProductClazzRel productClazzRel1 = new ProductClazzRel();
        productClazzRel1.setId(1L);
        ProductClazzRel productClazzRel2 = new ProductClazzRel();
        productClazzRel2.setId(productClazzRel1.getId());
        assertThat(productClazzRel1).isEqualTo(productClazzRel2);
        productClazzRel2.setId(2L);
        assertThat(productClazzRel1).isNotEqualTo(productClazzRel2);
        productClazzRel1.setId(null);
        assertThat(productClazzRel1).isNotEqualTo(productClazzRel2);
    }
}
