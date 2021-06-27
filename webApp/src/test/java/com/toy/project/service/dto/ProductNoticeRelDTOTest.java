package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductNoticeRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductNoticeRelDTO.class);
        ProductNoticeRelDTO productNoticeRelDTO1 = new ProductNoticeRelDTO();
        productNoticeRelDTO1.setId(1L);
        ProductNoticeRelDTO productNoticeRelDTO2 = new ProductNoticeRelDTO();
        assertThat(productNoticeRelDTO1).isNotEqualTo(productNoticeRelDTO2);
        productNoticeRelDTO2.setId(productNoticeRelDTO1.getId());
        assertThat(productNoticeRelDTO1).isEqualTo(productNoticeRelDTO2);
        productNoticeRelDTO2.setId(2L);
        assertThat(productNoticeRelDTO1).isNotEqualTo(productNoticeRelDTO2);
        productNoticeRelDTO1.setId(null);
        assertThat(productNoticeRelDTO1).isNotEqualTo(productNoticeRelDTO2);
    }
}
