package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductCategoryRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategoryRel.class);
        ProductCategoryRel productCategoryRel1 = new ProductCategoryRel();
        productCategoryRel1.setId(1L);
        ProductCategoryRel productCategoryRel2 = new ProductCategoryRel();
        productCategoryRel2.setId(productCategoryRel1.getId());
        assertThat(productCategoryRel1).isEqualTo(productCategoryRel2);
        productCategoryRel2.setId(2L);
        assertThat(productCategoryRel1).isNotEqualTo(productCategoryRel2);
        productCategoryRel1.setId(null);
        assertThat(productCategoryRel1).isNotEqualTo(productCategoryRel2);
    }
}
