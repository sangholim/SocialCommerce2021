package com.toy.project.repository;

import com.toy.project.domain.PCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PCategoryRepository extends JpaRepository<PCategory, Long>, JpaSpecificationExecutor<PCategory> {}
