package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductLabelManageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductLabelManageDTO.class);
        ProductLabelManageDTO productLabelManageDTO1 = new ProductLabelManageDTO();
        productLabelManageDTO1.setId(1L);
        ProductLabelManageDTO productLabelManageDTO2 = new ProductLabelManageDTO();
        assertThat(productLabelManageDTO1).isNotEqualTo(productLabelManageDTO2);
        productLabelManageDTO2.setId(productLabelManageDTO1.getId());
        assertThat(productLabelManageDTO1).isEqualTo(productLabelManageDTO2);
        productLabelManageDTO2.setId(2L);
        assertThat(productLabelManageDTO1).isNotEqualTo(productLabelManageDTO2);
        productLabelManageDTO1.setId(null);
        assertThat(productLabelManageDTO1).isNotEqualTo(productLabelManageDTO2);
    }
}
