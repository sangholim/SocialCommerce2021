package com.toy.project.repository;

import com.toy.project.domain.ProductAddImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductAddImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAddImageRepository extends JpaRepository<ProductAddImage, Long> {}
