package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductShippingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductShippingDTO.class);
        ProductShippingDTO productShippingDTO1 = new ProductShippingDTO();
        productShippingDTO1.setId(1L);
        ProductShippingDTO productShippingDTO2 = new ProductShippingDTO();
        assertThat(productShippingDTO1).isNotEqualTo(productShippingDTO2);
        productShippingDTO2.setId(productShippingDTO1.getId());
        assertThat(productShippingDTO1).isEqualTo(productShippingDTO2);
        productShippingDTO2.setId(2L);
        assertThat(productShippingDTO1).isNotEqualTo(productShippingDTO2);
        productShippingDTO1.setId(null);
        assertThat(productShippingDTO1).isNotEqualTo(productShippingDTO2);
    }
}
