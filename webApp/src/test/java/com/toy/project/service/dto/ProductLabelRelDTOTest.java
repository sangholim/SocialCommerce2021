package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductLabelRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductLabelRelDTO.class);
        ProductLabelRelDTO productLabelRelDTO1 = new ProductLabelRelDTO();
        productLabelRelDTO1.setId(1L);
        ProductLabelRelDTO productLabelRelDTO2 = new ProductLabelRelDTO();
        assertThat(productLabelRelDTO1).isNotEqualTo(productLabelRelDTO2);
        productLabelRelDTO2.setId(productLabelRelDTO1.getId());
        assertThat(productLabelRelDTO1).isEqualTo(productLabelRelDTO2);
        productLabelRelDTO2.setId(2L);
        assertThat(productLabelRelDTO1).isNotEqualTo(productLabelRelDTO2);
        productLabelRelDTO1.setId(null);
        assertThat(productLabelRelDTO1).isNotEqualTo(productLabelRelDTO2);
    }
}
