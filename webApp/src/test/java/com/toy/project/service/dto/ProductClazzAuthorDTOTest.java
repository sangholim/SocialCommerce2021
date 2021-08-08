package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductClazzAuthorDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductClazzAuthorDTO.class);
        ProductClazzAuthorDTO productClazzAuthorDTO1 = new ProductClazzAuthorDTO();
        productClazzAuthorDTO1.setId(1L);
        ProductClazzAuthorDTO productClazzAuthorDTO2 = new ProductClazzAuthorDTO();
        assertThat(productClazzAuthorDTO1).isNotEqualTo(productClazzAuthorDTO2);
        productClazzAuthorDTO2.setId(productClazzAuthorDTO1.getId());
        assertThat(productClazzAuthorDTO1).isEqualTo(productClazzAuthorDTO2);
        productClazzAuthorDTO2.setId(2L);
        assertThat(productClazzAuthorDTO1).isNotEqualTo(productClazzAuthorDTO2);
        productClazzAuthorDTO1.setId(null);
        assertThat(productClazzAuthorDTO1).isNotEqualTo(productClazzAuthorDTO2);
    }
}
