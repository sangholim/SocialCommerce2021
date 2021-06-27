package com.toy.project.repository;

import com.toy.project.domain.ProductShipping;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductShipping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductShippingRepository extends JpaRepository<ProductShipping, Long>, JpaSpecificationExecutor<ProductShipping> {}
