package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductInputOptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInputOption.class);
        ProductInputOption productInputOption1 = new ProductInputOption();
        productInputOption1.setId(1L);
        ProductInputOption productInputOption2 = new ProductInputOption();
        productInputOption2.setId(productInputOption1.getId());
        assertThat(productInputOption1).isEqualTo(productInputOption2);
        productInputOption2.setId(2L);
        assertThat(productInputOption1).isNotEqualTo(productInputOption2);
        productInputOption1.setId(null);
        assertThat(productInputOption1).isNotEqualTo(productInputOption2);
    }
}
