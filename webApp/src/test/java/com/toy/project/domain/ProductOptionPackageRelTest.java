package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductOptionPackageRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionPackageRel.class);
        ProductOptionPackageRel productOptionPackageRel1 = new ProductOptionPackageRel();
        productOptionPackageRel1.setId(1L);
        ProductOptionPackageRel productOptionPackageRel2 = new ProductOptionPackageRel();
        productOptionPackageRel2.setId(productOptionPackageRel1.getId());
        assertThat(productOptionPackageRel1).isEqualTo(productOptionPackageRel2);
        productOptionPackageRel2.setId(2L);
        assertThat(productOptionPackageRel1).isNotEqualTo(productOptionPackageRel2);
        productOptionPackageRel1.setId(null);
        assertThat(productOptionPackageRel1).isNotEqualTo(productOptionPackageRel2);
    }
}
