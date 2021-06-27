package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTemplateRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTemplateRelDTO.class);
        ProductTemplateRelDTO productTemplateRelDTO1 = new ProductTemplateRelDTO();
        productTemplateRelDTO1.setId(1L);
        ProductTemplateRelDTO productTemplateRelDTO2 = new ProductTemplateRelDTO();
        assertThat(productTemplateRelDTO1).isNotEqualTo(productTemplateRelDTO2);
        productTemplateRelDTO2.setId(productTemplateRelDTO1.getId());
        assertThat(productTemplateRelDTO1).isEqualTo(productTemplateRelDTO2);
        productTemplateRelDTO2.setId(2L);
        assertThat(productTemplateRelDTO1).isNotEqualTo(productTemplateRelDTO2);
        productTemplateRelDTO1.setId(null);
        assertThat(productTemplateRelDTO1).isNotEqualTo(productTemplateRelDTO2);
    }
}
