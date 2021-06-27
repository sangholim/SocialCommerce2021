package com.toy.project.repository;

import com.toy.project.domain.ProductShippingRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductShippingRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductShippingRelRepository
    extends JpaRepository<ProductShippingRel, Long>, JpaSpecificationExecutor<ProductShippingRel> {}
