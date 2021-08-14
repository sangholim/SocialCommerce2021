package com.toy.project.repository;

import com.toy.project.domain.PackageDescription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PackageDescription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackageDescriptionRepository extends JpaRepository<PackageDescription, Long> {}
