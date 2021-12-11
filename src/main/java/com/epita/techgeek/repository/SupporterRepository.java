package com.epita.techgeek.repository;

import com.epita.techgeek.domain.Supporter;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Supporter entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SupporterRepository extends JpaRepository<Supporter, Long> {
}
