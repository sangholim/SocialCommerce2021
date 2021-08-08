package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductAnnounceTemplateTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAnnounceTemplate.class);
        ProductAnnounceTemplate productAnnounceTemplate1 = new ProductAnnounceTemplate();
        productAnnounceTemplate1.setId(1L);
        ProductAnnounceTemplate productAnnounceTemplate2 = new ProductAnnounceTemplate();
        productAnnounceTemplate2.setId(productAnnounceTemplate1.getId());
        assertThat(productAnnounceTemplate1).isEqualTo(productAnnounceTemplate2);
        productAnnounceTemplate2.setId(2L);
        assertThat(productAnnounceTemplate1).isNotEqualTo(productAnnounceTemplate2);
        productAnnounceTemplate1.setId(null);
        assertThat(productAnnounceTemplate1).isNotEqualTo(productAnnounceTemplate2);
    }
}
