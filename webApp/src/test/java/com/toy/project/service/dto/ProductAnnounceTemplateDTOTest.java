package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductAnnounceTemplateDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAnnounceTemplateDTO.class);
        ProductAnnounceTemplateDTO productAnnounceTemplateDTO1 = new ProductAnnounceTemplateDTO();
        productAnnounceTemplateDTO1.setId(1L);
        ProductAnnounceTemplateDTO productAnnounceTemplateDTO2 = new ProductAnnounceTemplateDTO();
        assertThat(productAnnounceTemplateDTO1).isNotEqualTo(productAnnounceTemplateDTO2);
        productAnnounceTemplateDTO2.setId(productAnnounceTemplateDTO1.getId());
        assertThat(productAnnounceTemplateDTO1).isEqualTo(productAnnounceTemplateDTO2);
        productAnnounceTemplateDTO2.setId(2L);
        assertThat(productAnnounceTemplateDTO1).isNotEqualTo(productAnnounceTemplateDTO2);
        productAnnounceTemplateDTO1.setId(null);
        assertThat(productAnnounceTemplateDTO1).isNotEqualTo(productAnnounceTemplateDTO2);
    }
}
