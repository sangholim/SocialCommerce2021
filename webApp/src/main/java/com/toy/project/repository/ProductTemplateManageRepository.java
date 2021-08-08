package com.toy.project.repository;

import com.toy.project.domain.ProductTemplateManage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductTemplateManage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductTemplateManageRepository extends JpaRepository<ProductTemplateManage, Long> {}
