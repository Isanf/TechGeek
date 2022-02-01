package com.epita.techgeek.repository;

import com.epita.techgeek.domain.Influencer;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the Influencer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InfluencerRepository extends JpaRepository<Influencer, Long> {
}
