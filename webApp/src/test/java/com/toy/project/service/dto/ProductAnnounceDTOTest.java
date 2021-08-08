package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductAnnounceDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAnnounceDTO.class);
        ProductAnnounceDTO productAnnounceDTO1 = new ProductAnnounceDTO();
        productAnnounceDTO1.setId(1L);
        ProductAnnounceDTO productAnnounceDTO2 = new ProductAnnounceDTO();
        assertThat(productAnnounceDTO1).isNotEqualTo(productAnnounceDTO2);
        productAnnounceDTO2.setId(productAnnounceDTO1.getId());
        assertThat(productAnnounceDTO1).isEqualTo(productAnnounceDTO2);
        productAnnounceDTO2.setId(2L);
        assertThat(productAnnounceDTO1).isNotEqualTo(productAnnounceDTO2);
        productAnnounceDTO1.setId(null);
        assertThat(productAnnounceDTO1).isNotEqualTo(productAnnounceDTO2);
    }
}
