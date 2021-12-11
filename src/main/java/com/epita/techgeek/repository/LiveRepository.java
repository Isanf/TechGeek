package com.epita.techgeek.repository;

import com.epita.techgeek.domain.Live;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Live entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LiveRepository extends JpaRepository<Live, Long> {
}
