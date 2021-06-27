package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductShippingRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductShippingRelDTO.class);
        ProductShippingRelDTO productShippingRelDTO1 = new ProductShippingRelDTO();
        productShippingRelDTO1.setId(1L);
        ProductShippingRelDTO productShippingRelDTO2 = new ProductShippingRelDTO();
        assertThat(productShippingRelDTO1).isNotEqualTo(productShippingRelDTO2);
        productShippingRelDTO2.setId(productShippingRelDTO1.getId());
        assertThat(productShippingRelDTO1).isEqualTo(productShippingRelDTO2);
        productShippingRelDTO2.setId(2L);
        assertThat(productShippingRelDTO1).isNotEqualTo(productShippingRelDTO2);
        productShippingRelDTO1.setId(null);
        assertThat(productShippingRelDTO1).isNotEqualTo(productShippingRelDTO2);
    }
}
