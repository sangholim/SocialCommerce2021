package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTemplateManageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductTemplateManage.class);
        ProductTemplateManage productTemplateManage1 = new ProductTemplateManage();
        productTemplateManage1.setId(1L);
        ProductTemplateManage productTemplateManage2 = new ProductTemplateManage();
        productTemplateManage2.setId(productTemplateManage1.getId());
        assertThat(productTemplateManage1).isEqualTo(productTemplateManage2);
        productTemplateManage2.setId(2L);
        assertThat(productTemplateManage1).isNotEqualTo(productTemplateManage2);
        productTemplateManage1.setId(null);
        assertThat(productTemplateManage1).isNotEqualTo(productTemplateManage2);
    }
}
