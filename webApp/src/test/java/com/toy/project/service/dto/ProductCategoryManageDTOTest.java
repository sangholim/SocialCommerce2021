package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductCategoryManageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategoryManageDTO.class);
        ProductCategoryManageDTO productCategoryManageDTO1 = new ProductCategoryManageDTO();
        productCategoryManageDTO1.setId(1L);
        ProductCategoryManageDTO productCategoryManageDTO2 = new ProductCategoryManageDTO();
        assertThat(productCategoryManageDTO1).isNotEqualTo(productCategoryManageDTO2);
        productCategoryManageDTO2.setId(productCategoryManageDTO1.getId());
        assertThat(productCategoryManageDTO1).isEqualTo(productCategoryManageDTO2);
        productCategoryManageDTO2.setId(2L);
        assertThat(productCategoryManageDTO1).isNotEqualTo(productCategoryManageDTO2);
        productCategoryManageDTO1.setId(null);
        assertThat(productCategoryManageDTO1).isNotEqualTo(productCategoryManageDTO2);
    }
}
