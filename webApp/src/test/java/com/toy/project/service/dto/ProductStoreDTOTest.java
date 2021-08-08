package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductStoreDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductStoreDTO.class);
        ProductStoreDTO productStoreDTO1 = new ProductStoreDTO();
        productStoreDTO1.setId(1L);
        ProductStoreDTO productStoreDTO2 = new ProductStoreDTO();
        assertThat(productStoreDTO1).isNotEqualTo(productStoreDTO2);
        productStoreDTO2.setId(productStoreDTO1.getId());
        assertThat(productStoreDTO1).isEqualTo(productStoreDTO2);
        productStoreDTO2.setId(2L);
        assertThat(productStoreDTO1).isNotEqualTo(productStoreDTO2);
        productStoreDTO1.setId(null);
        assertThat(productStoreDTO1).isNotEqualTo(productStoreDTO2);
    }
}
