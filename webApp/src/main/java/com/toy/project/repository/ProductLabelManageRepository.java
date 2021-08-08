package com.toy.project.repository;

import com.toy.project.domain.ProductLabelManage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductLabelManage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductLabelManageRepository extends JpaRepository<ProductLabelManage, Long> {}
