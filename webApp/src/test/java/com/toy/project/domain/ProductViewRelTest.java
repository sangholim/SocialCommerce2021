package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductViewRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductViewRel.class);
        ProductViewRel productViewRel1 = new ProductViewRel();
        productViewRel1.setId(1L);
        ProductViewRel productViewRel2 = new ProductViewRel();
        productViewRel2.setId(productViewRel1.getId());
        assertThat(productViewRel1).isEqualTo(productViewRel2);
        productViewRel2.setId(2L);
        assertThat(productViewRel1).isNotEqualTo(productViewRel2);
        productViewRel1.setId(null);
        assertThat(productViewRel1).isNotEqualTo(productViewRel2);
    }
}
