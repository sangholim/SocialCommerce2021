package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductClazzRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductClazzRelDTO.class);
        ProductClazzRelDTO productClazzRelDTO1 = new ProductClazzRelDTO();
        productClazzRelDTO1.setId(1L);
        ProductClazzRelDTO productClazzRelDTO2 = new ProductClazzRelDTO();
        assertThat(productClazzRelDTO1).isNotEqualTo(productClazzRelDTO2);
        productClazzRelDTO2.setId(productClazzRelDTO1.getId());
        assertThat(productClazzRelDTO1).isEqualTo(productClazzRelDTO2);
        productClazzRelDTO2.setId(2L);
        assertThat(productClazzRelDTO1).isNotEqualTo(productClazzRelDTO2);
        productClazzRelDTO1.setId(null);
        assertThat(productClazzRelDTO1).isNotEqualTo(productClazzRelDTO2);
    }
}
