package com.toy.project.repository;

import com.toy.project.domain.ProductView;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductView entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductViewRepository extends JpaRepository<ProductView, Long>, JpaSpecificationExecutor<ProductView> {}
