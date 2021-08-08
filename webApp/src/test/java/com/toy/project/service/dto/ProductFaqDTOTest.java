package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductFaqDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductFaqDTO.class);
        ProductFaqDTO productFaqDTO1 = new ProductFaqDTO();
        productFaqDTO1.setId(1L);
        ProductFaqDTO productFaqDTO2 = new ProductFaqDTO();
        assertThat(productFaqDTO1).isNotEqualTo(productFaqDTO2);
        productFaqDTO2.setId(productFaqDTO1.getId());
        assertThat(productFaqDTO1).isEqualTo(productFaqDTO2);
        productFaqDTO2.setId(2L);
        assertThat(productFaqDTO1).isNotEqualTo(productFaqDTO2);
        productFaqDTO1.setId(null);
        assertThat(productFaqDTO1).isNotEqualTo(productFaqDTO2);
    }
}
