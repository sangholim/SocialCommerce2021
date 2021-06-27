package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductNoticeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductNoticeDTO.class);
        ProductNoticeDTO productNoticeDTO1 = new ProductNoticeDTO();
        productNoticeDTO1.setId(1L);
        ProductNoticeDTO productNoticeDTO2 = new ProductNoticeDTO();
        assertThat(productNoticeDTO1).isNotEqualTo(productNoticeDTO2);
        productNoticeDTO2.setId(productNoticeDTO1.getId());
        assertThat(productNoticeDTO1).isEqualTo(productNoticeDTO2);
        productNoticeDTO2.setId(2L);
        assertThat(productNoticeDTO1).isNotEqualTo(productNoticeDTO2);
        productNoticeDTO1.setId(null);
        assertThat(productNoticeDTO1).isNotEqualTo(productNoticeDTO2);
    }
}
