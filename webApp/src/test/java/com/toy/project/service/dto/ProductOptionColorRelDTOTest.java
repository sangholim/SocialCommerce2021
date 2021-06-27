package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductOptionColorRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionColorRelDTO.class);
        ProductOptionColorRelDTO productOptionColorRelDTO1 = new ProductOptionColorRelDTO();
        productOptionColorRelDTO1.setId(1L);
        ProductOptionColorRelDTO productOptionColorRelDTO2 = new ProductOptionColorRelDTO();
        assertThat(productOptionColorRelDTO1).isNotEqualTo(productOptionColorRelDTO2);
        productOptionColorRelDTO2.setId(productOptionColorRelDTO1.getId());
        assertThat(productOptionColorRelDTO1).isEqualTo(productOptionColorRelDTO2);
        productOptionColorRelDTO2.setId(2L);
        assertThat(productOptionColorRelDTO1).isNotEqualTo(productOptionColorRelDTO2);
        productOptionColorRelDTO1.setId(null);
        assertThat(productOptionColorRelDTO1).isNotEqualTo(productOptionColorRelDTO2);
    }
}
