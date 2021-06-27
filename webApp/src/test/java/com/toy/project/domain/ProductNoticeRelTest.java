package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductNoticeRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductNoticeRel.class);
        ProductNoticeRel productNoticeRel1 = new ProductNoticeRel();
        productNoticeRel1.setId(1L);
        ProductNoticeRel productNoticeRel2 = new ProductNoticeRel();
        productNoticeRel2.setId(productNoticeRel1.getId());
        assertThat(productNoticeRel1).isEqualTo(productNoticeRel2);
        productNoticeRel2.setId(2L);
        assertThat(productNoticeRel1).isNotEqualTo(productNoticeRel2);
        productNoticeRel1.setId(null);
        assertThat(productNoticeRel1).isNotEqualTo(productNoticeRel2);
    }
}
