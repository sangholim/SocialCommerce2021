package com.toy.project.repository;

import com.toy.project.domain.ProductTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductTemplateRepository extends JpaRepository<ProductTemplate, Long> {}
