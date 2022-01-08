package com.epita.techgeek.repository;

import com.epita.techgeek.domain.Live;

import com.epita.techgeek.service.dto.LiveDTO;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.Optional;

/**
 * Spring Data  repository for the Live entity.
 */
@SuppressWarnings("unused")
@Repository
public interface LiveRepository extends JpaRepository<Live, Long> {
    Optional<Live> findFirstByStartDateGreaterThan(ZonedDateTime date);
    Optional<Live> findFirstByStartDateIsBetween(ZonedDateTime dateStart,ZonedDateTime dateEnd);
}
