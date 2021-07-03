package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductViewContentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductViewContent.class);
        ProductViewContent productViewContent1 = new ProductViewContent();
        productViewContent1.setId(1L);
        ProductViewContent productViewContent2 = new ProductViewContent();
        productViewContent2.setId(productViewContent1.getId());
        assertThat(productViewContent1).isEqualTo(productViewContent2);
        productViewContent2.setId(2L);
        assertThat(productViewContent1).isNotEqualTo(productViewContent2);
        productViewContent1.setId(null);
        assertThat(productViewContent1).isNotEqualTo(productViewContent2);
    }
}
