package com.toy.project.repository;

import com.toy.project.domain.ProductAnnounceTemplate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ProductAnnounceTemplate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ProductAnnounceTemplateRepository extends JpaRepository<ProductAnnounceTemplate, Long> {}
