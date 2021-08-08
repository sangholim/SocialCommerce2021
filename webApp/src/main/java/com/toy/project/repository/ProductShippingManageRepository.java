package com.toy.project.repository;

import com.toy.project.domain.ProductShippingManage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductShippingManage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductShippingManageRepository extends JpaRepository<ProductShippingManage, Long> {}
