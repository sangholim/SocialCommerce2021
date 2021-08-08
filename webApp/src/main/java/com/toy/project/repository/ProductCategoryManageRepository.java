package com.toy.project.repository;

import com.toy.project.domain.ProductCategoryManage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductCategoryManage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductCategoryManageRepository extends JpaRepository<ProductCategoryManage, Long> {}
