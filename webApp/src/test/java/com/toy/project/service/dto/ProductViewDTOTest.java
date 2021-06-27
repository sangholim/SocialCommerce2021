package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductViewDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductViewDTO.class);
        ProductViewDTO productViewDTO1 = new ProductViewDTO();
        productViewDTO1.setId(1L);
        ProductViewDTO productViewDTO2 = new ProductViewDTO();
        assertThat(productViewDTO1).isNotEqualTo(productViewDTO2);
        productViewDTO2.setId(productViewDTO1.getId());
        assertThat(productViewDTO1).isEqualTo(productViewDTO2);
        productViewDTO2.setId(2L);
        assertThat(productViewDTO1).isNotEqualTo(productViewDTO2);
        productViewDTO1.setId(null);
        assertThat(productViewDTO1).isNotEqualTo(productViewDTO2);
    }
}
