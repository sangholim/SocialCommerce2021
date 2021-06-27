package com.toy.project.repository;

import com.toy.project.domain.Clazz;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Clazz entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClazzRepository extends JpaRepository<Clazz, Long>, JpaSpecificationExecutor<Clazz> {}
