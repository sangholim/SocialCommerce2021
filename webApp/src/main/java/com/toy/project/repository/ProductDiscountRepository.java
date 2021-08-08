package com.toy.project.repository;

import com.toy.project.domain.ProductDiscount;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductDiscount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductDiscountRepository extends JpaRepository<ProductDiscount, Long> {}
