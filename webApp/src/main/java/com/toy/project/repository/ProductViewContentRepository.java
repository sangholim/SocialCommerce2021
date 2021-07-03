package com.toy.project.repository;

import com.toy.project.domain.ProductViewContent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductViewContent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductViewContentRepository
    extends JpaRepository<ProductViewContent, Long>, JpaSpecificationExecutor<ProductViewContent> {}
