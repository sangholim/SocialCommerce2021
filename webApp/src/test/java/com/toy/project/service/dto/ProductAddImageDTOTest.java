package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductAddImageDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductAddImageDTO.class);
        ProductAddImageDTO productAddImageDTO1 = new ProductAddImageDTO();
        productAddImageDTO1.setId(1L);
        ProductAddImageDTO productAddImageDTO2 = new ProductAddImageDTO();
        assertThat(productAddImageDTO1).isNotEqualTo(productAddImageDTO2);
        productAddImageDTO2.setId(productAddImageDTO1.getId());
        assertThat(productAddImageDTO1).isEqualTo(productAddImageDTO2);
        productAddImageDTO2.setId(2L);
        assertThat(productAddImageDTO1).isNotEqualTo(productAddImageDTO2);
        productAddImageDTO1.setId(null);
        assertThat(productAddImageDTO1).isNotEqualTo(productAddImageDTO2);
    }
}
