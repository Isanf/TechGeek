package com.epita.techgeek.repository;

import com.epita.techgeek.domain.Framework;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Framework entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FrameworkRepository extends JpaRepository<Framework, Long> {
}
