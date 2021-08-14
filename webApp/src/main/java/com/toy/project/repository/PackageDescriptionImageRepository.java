package com.toy.project.repository;

import com.toy.project.domain.PackageDescriptionImage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the PackageDescriptionImage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PackageDescriptionImageRepository extends JpaRepository<PackageDescriptionImage, Long> {}
