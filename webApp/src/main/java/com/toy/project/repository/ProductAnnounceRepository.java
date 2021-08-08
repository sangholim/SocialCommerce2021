package com.toy.project.repository;

import com.toy.project.domain.ProductAnnounce;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductAnnounce entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAnnounceRepository extends JpaRepository<ProductAnnounce, Long> {}
