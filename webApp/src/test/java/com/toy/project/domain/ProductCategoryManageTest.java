package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductCategoryManageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategoryManage.class);
        ProductCategoryManage productCategoryManage1 = new ProductCategoryManage();
        productCategoryManage1.setId(1L);
        ProductCategoryManage productCategoryManage2 = new ProductCategoryManage();
        productCategoryManage2.setId(productCategoryManage1.getId());
        assertThat(productCategoryManage1).isEqualTo(productCategoryManage2);
        productCategoryManage2.setId(2L);
        assertThat(productCategoryManage1).isNotEqualTo(productCategoryManage2);
        productCategoryManage1.setId(null);
        assertThat(productCategoryManage1).isNotEqualTo(productCategoryManage2);
    }
}
