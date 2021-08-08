package com.toy.project.repository;

import com.toy.project.domain.ProductInputOption;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductInputOption entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductInputOptionRepository extends JpaRepository<ProductInputOption, Long> {}
