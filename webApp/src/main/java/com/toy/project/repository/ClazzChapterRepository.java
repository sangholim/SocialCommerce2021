package com.toy.project.repository;

import com.toy.project.domain.ClazzChapter;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data SQL repository for the ClazzChapter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ClazzChapterRepository extends JpaRepository<ClazzChapter, Long> {}
