package com.toy.project.repository;

import com.toy.project.domain.ProductFaq;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductFaq entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductFaqRepository extends JpaRepository<ProductFaq, Long> {}
