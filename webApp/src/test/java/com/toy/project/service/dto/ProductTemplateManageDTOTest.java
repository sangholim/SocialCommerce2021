package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTemplateManageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTemplateManageDTO.class);
        ProductTemplateManageDTO productTemplateManageDTO1 = new ProductTemplateManageDTO();
        productTemplateManageDTO1.setId(1L);
        ProductTemplateManageDTO productTemplateManageDTO2 = new ProductTemplateManageDTO();
        assertThat(productTemplateManageDTO1).isNotEqualTo(productTemplateManageDTO2);
        productTemplateManageDTO2.setId(productTemplateManageDTO1.getId());
        assertThat(productTemplateManageDTO1).isEqualTo(productTemplateManageDTO2);
        productTemplateManageDTO2.setId(2L);
        assertThat(productTemplateManageDTO1).isNotEqualTo(productTemplateManageDTO2);
        productTemplateManageDTO1.setId(null);
        assertThat(productTemplateManageDTO1).isNotEqualTo(productTemplateManageDTO2);
    }
}
