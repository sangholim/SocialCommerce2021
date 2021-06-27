package com.toy.project.repository;

import com.toy.project.domain.Store;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the Store entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StoreRepository extends JpaRepository<Store, Long>, JpaSpecificationExecutor<Store> {}
