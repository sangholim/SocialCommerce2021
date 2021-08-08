package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductAnnounceTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAnnounce.class);
        ProductAnnounce productAnnounce1 = new ProductAnnounce();
        productAnnounce1.setId(1L);
        ProductAnnounce productAnnounce2 = new ProductAnnounce();
        productAnnounce2.setId(productAnnounce1.getId());
        assertThat(productAnnounce1).isEqualTo(productAnnounce2);
        productAnnounce2.setId(2L);
        assertThat(productAnnounce1).isNotEqualTo(productAnnounce2);
        productAnnounce1.setId(null);
        assertThat(productAnnounce1).isNotEqualTo(productAnnounce2);
    }
}
