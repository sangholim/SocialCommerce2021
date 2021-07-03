package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductViewContentDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductViewContentDTO.class);
        ProductViewContentDTO productViewContentDTO1 = new ProductViewContentDTO();
        productViewContentDTO1.setId(1L);
        ProductViewContentDTO productViewContentDTO2 = new ProductViewContentDTO();
        assertThat(productViewContentDTO1).isNotEqualTo(productViewContentDTO2);
        productViewContentDTO2.setId(productViewContentDTO1.getId());
        assertThat(productViewContentDTO1).isEqualTo(productViewContentDTO2);
        productViewContentDTO2.setId(2L);
        assertThat(productViewContentDTO1).isNotEqualTo(productViewContentDTO2);
        productViewContentDTO1.setId(null);
        assertThat(productViewContentDTO1).isNotEqualTo(productViewContentDTO2);
    }
}
