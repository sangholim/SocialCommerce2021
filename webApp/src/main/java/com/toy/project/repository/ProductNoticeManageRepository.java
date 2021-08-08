package com.toy.project.repository;

import com.toy.project.domain.ProductNoticeManage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductNoticeManage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductNoticeManageRepository extends JpaRepository<ProductNoticeManage, Long> {}
