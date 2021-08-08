package com.toy.project.repository;

import com.toy.project.domain.ProductStore;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductStore entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductStoreRepository extends JpaRepository<ProductStore, Long> {}
