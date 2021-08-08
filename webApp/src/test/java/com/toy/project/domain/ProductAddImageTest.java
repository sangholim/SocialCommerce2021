package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductAddImageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAddImage.class);
        ProductAddImage productAddImage1 = new ProductAddImage();
        productAddImage1.setId(1L);
        ProductAddImage productAddImage2 = new ProductAddImage();
        productAddImage2.setId(productAddImage1.getId());
        assertThat(productAddImage1).isEqualTo(productAddImage2);
        productAddImage2.setId(2L);
        assertThat(productAddImage1).isNotEqualTo(productAddImage2);
        productAddImage1.setId(null);
        assertThat(productAddImage1).isNotEqualTo(productAddImage2);
    }
}
