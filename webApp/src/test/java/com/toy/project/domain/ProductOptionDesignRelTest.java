package com.toy.project.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductOptionDesignRelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionDesignRel.class);
        ProductOptionDesignRel productOptionDesignRel1 = new ProductOptionDesignRel();
        productOptionDesignRel1.setId(1L);
        ProductOptionDesignRel productOptionDesignRel2 = new ProductOptionDesignRel();
        productOptionDesignRel2.setId(productOptionDesignRel1.getId());
        assertThat(productOptionDesignRel1).isEqualTo(productOptionDesignRel2);
        productOptionDesignRel2.setId(2L);
        assertThat(productOptionDesignRel1).isNotEqualTo(productOptionDesignRel2);
        productOptionDesignRel1.setId(null);
        assertThat(productOptionDesignRel1).isNotEqualTo(productOptionDesignRel2);
    }
}
