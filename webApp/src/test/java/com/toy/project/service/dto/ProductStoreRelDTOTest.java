package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductStoreRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductStoreRelDTO.class);
        ProductStoreRelDTO productStoreRelDTO1 = new ProductStoreRelDTO();
        productStoreRelDTO1.setId(1L);
        ProductStoreRelDTO productStoreRelDTO2 = new ProductStoreRelDTO();
        assertThat(productStoreRelDTO1).isNotEqualTo(productStoreRelDTO2);
        productStoreRelDTO2.setId(productStoreRelDTO1.getId());
        assertThat(productStoreRelDTO1).isEqualTo(productStoreRelDTO2);
        productStoreRelDTO2.setId(2L);
        assertThat(productStoreRelDTO1).isNotEqualTo(productStoreRelDTO2);
        productStoreRelDTO1.setId(null);
        assertThat(productStoreRelDTO1).isNotEqualTo(productStoreRelDTO2);
    }
}
