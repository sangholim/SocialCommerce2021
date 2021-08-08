package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductShippingManageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductShippingManageDTO.class);
        ProductShippingManageDTO productShippingManageDTO1 = new ProductShippingManageDTO();
        productShippingManageDTO1.setId(1L);
        ProductShippingManageDTO productShippingManageDTO2 = new ProductShippingManageDTO();
        assertThat(productShippingManageDTO1).isNotEqualTo(productShippingManageDTO2);
        productShippingManageDTO2.setId(productShippingManageDTO1.getId());
        assertThat(productShippingManageDTO1).isEqualTo(productShippingManageDTO2);
        productShippingManageDTO2.setId(2L);
        assertThat(productShippingManageDTO1).isNotEqualTo(productShippingManageDTO2);
        productShippingManageDTO1.setId(null);
        assertThat(productShippingManageDTO1).isNotEqualTo(productShippingManageDTO2);
    }
}
