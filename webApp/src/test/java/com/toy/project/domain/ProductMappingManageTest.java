package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductMappingManageTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductMappingManage.class);
        ProductMappingManage productMappingManage1 = new ProductMappingManage();
        productMappingManage1.setId(1L);
        ProductMappingManage productMappingManage2 = new ProductMappingManage();
        productMappingManage2.setId(productMappingManage1.getId());
        assertThat(productMappingManage1).isEqualTo(productMappingManage2);
        productMappingManage2.setId(2L);
        assertThat(productMappingManage1).isNotEqualTo(productMappingManage2);
        productMappingManage1.setId(null);
        assertThat(productMappingManage1).isNotEqualTo(productMappingManage2);
    }
}
