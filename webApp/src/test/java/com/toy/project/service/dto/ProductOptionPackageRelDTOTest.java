package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductOptionPackageRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionPackageRelDTO.class);
        ProductOptionPackageRelDTO productOptionPackageRelDTO1 = new ProductOptionPackageRelDTO();
        productOptionPackageRelDTO1.setId(1L);
        ProductOptionPackageRelDTO productOptionPackageRelDTO2 = new ProductOptionPackageRelDTO();
        assertThat(productOptionPackageRelDTO1).isNotEqualTo(productOptionPackageRelDTO2);
        productOptionPackageRelDTO2.setId(productOptionPackageRelDTO1.getId());
        assertThat(productOptionPackageRelDTO1).isEqualTo(productOptionPackageRelDTO2);
        productOptionPackageRelDTO2.setId(2L);
        assertThat(productOptionPackageRelDTO1).isNotEqualTo(productOptionPackageRelDTO2);
        productOptionPackageRelDTO1.setId(null);
        assertThat(productOptionPackageRelDTO1).isNotEqualTo(productOptionPackageRelDTO2);
    }
}
