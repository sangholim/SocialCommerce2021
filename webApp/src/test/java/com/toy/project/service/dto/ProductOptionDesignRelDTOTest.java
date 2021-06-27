package com.toy.project.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import com.toy.project.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductOptionDesignRelDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ProductOptionDesignRelDTO.class);
        ProductOptionDesignRelDTO productOptionDesignRelDTO1 = new ProductOptionDesignRelDTO();
        productOptionDesignRelDTO1.setId(1L);
        ProductOptionDesignRelDTO productOptionDesignRelDTO2 = new ProductOptionDesignRelDTO();
        assertThat(productOptionDesignRelDTO1).isNotEqualTo(productOptionDesignRelDTO2);
        productOptionDesignRelDTO2.setId(productOptionDesignRelDTO1.getId());
        assertThat(productOptionDesignRelDTO1).isEqualTo(productOptionDesignRelDTO2);
        productOptionDesignRelDTO2.setId(2L);
        assertThat(productOptionDesignRelDTO1).isNotEqualTo(productOptionDesignRelDTO2);
        productOptionDesignRelDTO1.setId(null);
        assertThat(productOptionDesignRelDTO1).isNotEqualTo(productOptionDesignRelDTO2);
    }
}
