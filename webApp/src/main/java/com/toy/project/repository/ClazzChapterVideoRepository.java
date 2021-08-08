package com.toy.project.repository;

import com.toy.project.domain.ClazzChapterVideo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClazzChapterVideo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClazzChapterVideoRepository extends JpaRepository<ClazzChapterVideo, Long> {}
