package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductLabelManageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductLabelManage.class);
        ProductLabelManage productLabelManage1 = new ProductLabelManage();
        productLabelManage1.setId(1L);
        ProductLabelManage productLabelManage2 = new ProductLabelManage();
        productLabelManage2.setId(productLabelManage1.getId());
        assertThat(productLabelManage1).isEqualTo(productLabelManage2);
        productLabelManage2.setId(2L);
        assertThat(productLabelManage1).isNotEqualTo(productLabelManage2);
        productLabelManage1.setId(null);
        assertThat(productLabelManage1).isNotEqualTo(productLabelManage2);
    }
}
