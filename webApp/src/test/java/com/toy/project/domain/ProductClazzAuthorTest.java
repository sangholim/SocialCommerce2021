package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductClazzAuthorTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductClazzAuthor.class);
        ProductClazzAuthor productClazzAuthor1 = new ProductClazzAuthor();
        productClazzAuthor1.setId(1L);
        ProductClazzAuthor productClazzAuthor2 = new ProductClazzAuthor();
        productClazzAuthor2.setId(productClazzAuthor1.getId());
        assertThat(productClazzAuthor1).isEqualTo(productClazzAuthor2);
        productClazzAuthor2.setId(2L);
        assertThat(productClazzAuthor1).isNotEqualTo(productClazzAuthor2);
        productClazzAuthor1.setId(null);
        assertThat(productClazzAuthor1).isNotEqualTo(productClazzAuthor2);
    }
}
