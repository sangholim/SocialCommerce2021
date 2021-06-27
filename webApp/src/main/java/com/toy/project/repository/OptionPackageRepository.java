package com.toy.project.repository;

import com.toy.project.domain.OptionPackage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OptionPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionPackageRepository extends JpaRepository<OptionPackage, Long>, JpaSpecificationExecutor<OptionPackage> {}
