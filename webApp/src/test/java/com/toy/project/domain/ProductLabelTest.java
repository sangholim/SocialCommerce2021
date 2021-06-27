package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductLabelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductLabel.class);
        ProductLabel productLabel1 = new ProductLabel();
        productLabel1.setId(1L);
        ProductLabel productLabel2 = new ProductLabel();
        productLabel2.setId(productLabel1.getId());
        assertThat(productLabel1).isEqualTo(productLabel2);
        productLabel2.setId(2L);
        assertThat(productLabel1).isNotEqualTo(productLabel2);
        productLabel1.setId(null);
        assertThat(productLabel1).isNotEqualTo(productLabel2);
    }
}
