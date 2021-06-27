package com.toy.project.repository;

import com.toy.project.domain.ProductLabel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductLabel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductLabelRepository extends JpaRepository<ProductLabel, Long>, JpaSpecificationExecutor<ProductLabel> {}
