package com.toy.project.repository;

import com.toy.project.domain.ProductNotice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductNotice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductNoticeRepository extends JpaRepository<ProductNotice, Long>, JpaSpecificationExecutor<ProductNotice> {}
