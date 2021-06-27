package com.toy.project.repository;

import com.toy.project.domain.OptionDesign;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OptionDesign entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionDesignRepository extends JpaRepository<OptionDesign, Long>, JpaSpecificationExecutor<OptionDesign> {}
