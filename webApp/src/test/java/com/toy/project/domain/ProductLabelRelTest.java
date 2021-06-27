package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductLabelRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductLabelRel.class);
        ProductLabelRel productLabelRel1 = new ProductLabelRel();
        productLabelRel1.setId(1L);
        ProductLabelRel productLabelRel2 = new ProductLabelRel();
        productLabelRel2.setId(productLabelRel1.getId());
        assertThat(productLabelRel1).isEqualTo(productLabelRel2);
        productLabelRel2.setId(2L);
        assertThat(productLabelRel1).isNotEqualTo(productLabelRel2);
        productLabelRel1.setId(null);
        assertThat(productLabelRel1).isNotEqualTo(productLabelRel2);
    }
}
