package com.toy.project.repository;

import com.toy.project.domain.ProductNoticeRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductNoticeRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductNoticeRelRepository extends JpaRepository<ProductNoticeRel, Long>, JpaSpecificationExecutor<ProductNoticeRel> {}
