package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductMappingDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductMappingDTO.class);
        ProductMappingDTO productMappingDTO1 = new ProductMappingDTO();
        productMappingDTO1.setId(1L);
        ProductMappingDTO productMappingDTO2 = new ProductMappingDTO();
        assertThat(productMappingDTO1).isNotEqualTo(productMappingDTO2);
        productMappingDTO2.setId(productMappingDTO1.getId());
        assertThat(productMappingDTO1).isEqualTo(productMappingDTO2);
        productMappingDTO2.setId(2L);
        assertThat(productMappingDTO1).isNotEqualTo(productMappingDTO2);
        productMappingDTO1.setId(null);
        assertThat(productMappingDTO1).isNotEqualTo(productMappingDTO2);
    }
}
