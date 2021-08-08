package com.toy.project.repository;

import com.toy.project.domain.ProductClazzAuthor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductClazzAuthor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductClazzAuthorRepository extends JpaRepository<ProductClazzAuthor, Long> {}
