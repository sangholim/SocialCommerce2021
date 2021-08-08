package com.toy.project.repository;

import com.toy.project.domain.ProductMappingManage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductMappingManage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductMappingManageRepository extends JpaRepository<ProductMappingManage, Long> {}
