package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductOptionRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionRelDTO.class);
        ProductOptionRelDTO productOptionRelDTO1 = new ProductOptionRelDTO();
        productOptionRelDTO1.setId(1L);
        ProductOptionRelDTO productOptionRelDTO2 = new ProductOptionRelDTO();
        assertThat(productOptionRelDTO1).isNotEqualTo(productOptionRelDTO2);
        productOptionRelDTO2.setId(productOptionRelDTO1.getId());
        assertThat(productOptionRelDTO1).isEqualTo(productOptionRelDTO2);
        productOptionRelDTO2.setId(2L);
        assertThat(productOptionRelDTO1).isNotEqualTo(productOptionRelDTO2);
        productOptionRelDTO1.setId(null);
        assertThat(productOptionRelDTO1).isNotEqualTo(productOptionRelDTO2);
    }
}
