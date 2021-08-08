package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductNoticeManageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductNoticeManage.class);
        ProductNoticeManage productNoticeManage1 = new ProductNoticeManage();
        productNoticeManage1.setId(1L);
        ProductNoticeManage productNoticeManage2 = new ProductNoticeManage();
        productNoticeManage2.setId(productNoticeManage1.getId());
        assertThat(productNoticeManage1).isEqualTo(productNoticeManage2);
        productNoticeManage2.setId(2L);
        assertThat(productNoticeManage1).isNotEqualTo(productNoticeManage2);
        productNoticeManage1.setId(null);
        assertThat(productNoticeManage1).isNotEqualTo(productNoticeManage2);
    }
}
