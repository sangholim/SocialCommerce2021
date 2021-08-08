package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductMappingManageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductMappingManageDTO.class);
        ProductMappingManageDTO productMappingManageDTO1 = new ProductMappingManageDTO();
        productMappingManageDTO1.setId(1L);
        ProductMappingManageDTO productMappingManageDTO2 = new ProductMappingManageDTO();
        assertThat(productMappingManageDTO1).isNotEqualTo(productMappingManageDTO2);
        productMappingManageDTO2.setId(productMappingManageDTO1.getId());
        assertThat(productMappingManageDTO1).isEqualTo(productMappingManageDTO2);
        productMappingManageDTO2.setId(2L);
        assertThat(productMappingManageDTO1).isNotEqualTo(productMappingManageDTO2);
        productMappingManageDTO1.setId(null);
        assertThat(productMappingManageDTO1).isNotEqualTo(productMappingManageDTO2);
    }
}
