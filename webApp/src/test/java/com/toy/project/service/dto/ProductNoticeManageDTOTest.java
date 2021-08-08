package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductNoticeManageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductNoticeManageDTO.class);
        ProductNoticeManageDTO productNoticeManageDTO1 = new ProductNoticeManageDTO();
        productNoticeManageDTO1.setId(1L);
        ProductNoticeManageDTO productNoticeManageDTO2 = new ProductNoticeManageDTO();
        assertThat(productNoticeManageDTO1).isNotEqualTo(productNoticeManageDTO2);
        productNoticeManageDTO2.setId(productNoticeManageDTO1.getId());
        assertThat(productNoticeManageDTO1).isEqualTo(productNoticeManageDTO2);
        productNoticeManageDTO2.setId(2L);
        assertThat(productNoticeManageDTO1).isNotEqualTo(productNoticeManageDTO2);
        productNoticeManageDTO1.setId(null);
        assertThat(productNoticeManageDTO1).isNotEqualTo(productNoticeManageDTO2);
    }
}
