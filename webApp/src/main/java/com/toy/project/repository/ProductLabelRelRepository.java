package com.toy.project.repository;

import com.toy.project.domain.ProductLabelRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductLabelRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductLabelRelRepository extends JpaRepository<ProductLabelRel, Long>, JpaSpecificationExecutor<ProductLabelRel> {}
