package com.toy.project.repository;

import com.toy.project.domain.ProductClazzRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductClazzRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductClazzRelRepository extends JpaRepository<ProductClazzRel, Long>, JpaSpecificationExecutor<ProductClazzRel> {}
