package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductInputOptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductInputOptionDTO.class);
        ProductInputOptionDTO productInputOptionDTO1 = new ProductInputOptionDTO();
        productInputOptionDTO1.setId(1L);
        ProductInputOptionDTO productInputOptionDTO2 = new ProductInputOptionDTO();
        assertThat(productInputOptionDTO1).isNotEqualTo(productInputOptionDTO2);
        productInputOptionDTO2.setId(productInputOptionDTO1.getId());
        assertThat(productInputOptionDTO1).isEqualTo(productInputOptionDTO2);
        productInputOptionDTO2.setId(2L);
        assertThat(productInputOptionDTO1).isNotEqualTo(productInputOptionDTO2);
        productInputOptionDTO1.setId(null);
        assertThat(productInputOptionDTO1).isNotEqualTo(productInputOptionDTO2);
    }
}
