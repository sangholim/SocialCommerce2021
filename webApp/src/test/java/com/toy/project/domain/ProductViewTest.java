package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductViewTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductView.class);
        ProductView productView1 = new ProductView();
        productView1.setId(1L);
        ProductView productView2 = new ProductView();
        productView2.setId(productView1.getId());
        assertThat(productView1).isEqualTo(productView2);
        productView2.setId(2L);
        assertThat(productView1).isNotEqualTo(productView2);
        productView1.setId(null);
        assertThat(productView1).isNotEqualTo(productView2);
    }
}
