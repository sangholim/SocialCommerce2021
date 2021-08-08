package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductAddOptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAddOptionDTO.class);
        ProductAddOptionDTO productAddOptionDTO1 = new ProductAddOptionDTO();
        productAddOptionDTO1.setId(1L);
        ProductAddOptionDTO productAddOptionDTO2 = new ProductAddOptionDTO();
        assertThat(productAddOptionDTO1).isNotEqualTo(productAddOptionDTO2);
        productAddOptionDTO2.setId(productAddOptionDTO1.getId());
        assertThat(productAddOptionDTO1).isEqualTo(productAddOptionDTO2);
        productAddOptionDTO2.setId(2L);
        assertThat(productAddOptionDTO1).isNotEqualTo(productAddOptionDTO2);
        productAddOptionDTO1.setId(null);
        assertThat(productAddOptionDTO1).isNotEqualTo(productAddOptionDTO2);
    }
}
