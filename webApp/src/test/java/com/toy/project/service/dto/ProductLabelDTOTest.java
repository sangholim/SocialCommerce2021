package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductLabelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductLabelDTO.class);
        ProductLabelDTO productLabelDTO1 = new ProductLabelDTO();
        productLabelDTO1.setId(1L);
        ProductLabelDTO productLabelDTO2 = new ProductLabelDTO();
        assertThat(productLabelDTO1).isNotEqualTo(productLabelDTO2);
        productLabelDTO2.setId(productLabelDTO1.getId());
        assertThat(productLabelDTO1).isEqualTo(productLabelDTO2);
        productLabelDTO2.setId(2L);
        assertThat(productLabelDTO1).isNotEqualTo(productLabelDTO2);
        productLabelDTO1.setId(null);
        assertThat(productLabelDTO1).isNotEqualTo(productLabelDTO2);
    }
}
