package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductNoticeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductNotice.class);
        ProductNotice productNotice1 = new ProductNotice();
        productNotice1.setId(1L);
        ProductNotice productNotice2 = new ProductNotice();
        productNotice2.setId(productNotice1.getId());
        assertThat(productNotice1).isEqualTo(productNotice2);
        productNotice2.setId(2L);
        assertThat(productNotice1).isNotEqualTo(productNotice2);
        productNotice1.setId(null);
        assertThat(productNotice1).isNotEqualTo(productNotice2);
    }
}
