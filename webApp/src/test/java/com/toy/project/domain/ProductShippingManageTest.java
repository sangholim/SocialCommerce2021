package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductShippingManageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductShippingManage.class);
        ProductShippingManage productShippingManage1 = new ProductShippingManage();
        productShippingManage1.setId(1L);
        ProductShippingManage productShippingManage2 = new ProductShippingManage();
        productShippingManage2.setId(productShippingManage1.getId());
        assertThat(productShippingManage1).isEqualTo(productShippingManage2);
        productShippingManage2.setId(2L);
        assertThat(productShippingManage1).isNotEqualTo(productShippingManage2);
        productShippingManage1.setId(null);
        assertThat(productShippingManage1).isNotEqualTo(productShippingManage2);
    }
}
