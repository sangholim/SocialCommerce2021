package com.toy.project.repository;

import com.toy.project.domain.OptionColor;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the OptionColor entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OptionColorRepository extends JpaRepository<OptionColor, Long>, JpaSpecificationExecutor<OptionColor> {}
