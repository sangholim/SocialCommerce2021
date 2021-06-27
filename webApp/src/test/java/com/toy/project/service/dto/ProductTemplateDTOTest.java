package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTemplateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTemplateDTO.class);
        ProductTemplateDTO productTemplateDTO1 = new ProductTemplateDTO();
        productTemplateDTO1.setId(1L);
        ProductTemplateDTO productTemplateDTO2 = new ProductTemplateDTO();
        assertThat(productTemplateDTO1).isNotEqualTo(productTemplateDTO2);
        productTemplateDTO2.setId(productTemplateDTO1.getId());
        assertThat(productTemplateDTO1).isEqualTo(productTemplateDTO2);
        productTemplateDTO2.setId(2L);
        assertThat(productTemplateDTO1).isNotEqualTo(productTemplateDTO2);
        productTemplateDTO1.setId(null);
        assertThat(productTemplateDTO1).isNotEqualTo(productTemplateDTO2);
    }
}
