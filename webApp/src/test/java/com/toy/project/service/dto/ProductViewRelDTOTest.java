package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductViewRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductViewRelDTO.class);
        ProductViewRelDTO productViewRelDTO1 = new ProductViewRelDTO();
        productViewRelDTO1.setId(1L);
        ProductViewRelDTO productViewRelDTO2 = new ProductViewRelDTO();
        assertThat(productViewRelDTO1).isNotEqualTo(productViewRelDTO2);
        productViewRelDTO2.setId(productViewRelDTO1.getId());
        assertThat(productViewRelDTO1).isEqualTo(productViewRelDTO2);
        productViewRelDTO2.setId(2L);
        assertThat(productViewRelDTO1).isNotEqualTo(productViewRelDTO2);
        productViewRelDTO1.setId(null);
        assertThat(productViewRelDTO1).isNotEqualTo(productViewRelDTO2);
    }
}
