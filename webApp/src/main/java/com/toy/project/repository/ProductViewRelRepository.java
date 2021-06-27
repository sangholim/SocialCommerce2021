package com.toy.project.repository;

import com.toy.project.domain.ProductViewRel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductViewRel entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductViewRelRepository extends JpaRepository<ProductViewRel, Long>, JpaSpecificationExecutor<ProductViewRel> {}
