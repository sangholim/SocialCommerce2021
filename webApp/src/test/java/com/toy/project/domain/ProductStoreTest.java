package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductStoreTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductStore.class);
        ProductStore productStore1 = new ProductStore();
        productStore1.setId(1L);
        ProductStore productStore2 = new ProductStore();
        productStore2.setId(productStore1.getId());
        assertThat(productStore1).isEqualTo(productStore2);
        productStore2.setId(2L);
        assertThat(productStore1).isNotEqualTo(productStore2);
        productStore1.setId(null);
        assertThat(productStore1).isNotEqualTo(productStore2);
    }
}
