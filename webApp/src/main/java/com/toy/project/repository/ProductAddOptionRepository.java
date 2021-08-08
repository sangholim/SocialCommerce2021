package com.toy.project.repository;

import com.toy.project.domain.ProductAddOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductAddOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAddOptionRepository extends JpaRepository<ProductAddOption, Long> {}
