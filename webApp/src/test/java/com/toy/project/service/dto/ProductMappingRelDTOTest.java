package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductMappingRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductMappingRelDTO.class);
        ProductMappingRelDTO productMappingRelDTO1 = new ProductMappingRelDTO();
        productMappingRelDTO1.setId(1L);
        ProductMappingRelDTO productMappingRelDTO2 = new ProductMappingRelDTO();
        assertThat(productMappingRelDTO1).isNotEqualTo(productMappingRelDTO2);
        productMappingRelDTO2.setId(productMappingRelDTO1.getId());
        assertThat(productMappingRelDTO1).isEqualTo(productMappingRelDTO2);
        productMappingRelDTO2.setId(2L);
        assertThat(productMappingRelDTO1).isNotEqualTo(productMappingRelDTO2);
        productMappingRelDTO1.setId(null);
        assertThat(productMappingRelDTO1).isNotEqualTo(productMappingRelDTO2);
    }
}
