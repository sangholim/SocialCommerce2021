package com.toy.project.repository;

import com.toy.project.domain.ProductTemplateRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductTemplateRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductTemplateRelRepository
    extends JpaRepository<ProductTemplateRel, Long>, JpaSpecificationExecutor<ProductTemplateRel> {}
