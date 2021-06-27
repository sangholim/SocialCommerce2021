package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductCategoryRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductCategoryRelDTO.class);
        ProductCategoryRelDTO productCategoryRelDTO1 = new ProductCategoryRelDTO();
        productCategoryRelDTO1.setId(1L);
        ProductCategoryRelDTO productCategoryRelDTO2 = new ProductCategoryRelDTO();
        assertThat(productCategoryRelDTO1).isNotEqualTo(productCategoryRelDTO2);
        productCategoryRelDTO2.setId(productCategoryRelDTO1.getId());
        assertThat(productCategoryRelDTO1).isEqualTo(productCategoryRelDTO2);
        productCategoryRelDTO2.setId(2L);
        assertThat(productCategoryRelDTO1).isNotEqualTo(productCategoryRelDTO2);
        productCategoryRelDTO1.setId(null);
        assertThat(productCategoryRelDTO1).isNotEqualTo(productCategoryRelDTO2);
    }
}
