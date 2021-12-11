package com.epita.techgeek.service;

import com.epita.techgeek.domain.Volunteer;
import com.epita.techgeek.repository.VolunteerRepository;
import com.epita.techgeek.service.dto.VolunteerDTO;
import com.epita.techgeek.service.mapper.VolunteerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Volunteer}.
 */
@Service
@Transactional
public class VolunteerService {

    private final Logger log = LoggerFactory.getLogger(VolunteerService.class);

    private final VolunteerRepository volunteerRepository;

    private final VolunteerMapper volunteerMapper;

    public VolunteerService(VolunteerRepository volunteerRepository, VolunteerMapper volunteerMapper) {
        this.volunteerRepository = volunteerRepository;
        this.volunteerMapper = volunteerMapper;
    }

    /**
     * Save a volunteer.
     *
     * @param volunteerDTO the entity to save.
     * @return the persisted entity.
     */
    public VolunteerDTO save(VolunteerDTO volunteerDTO) {
        log.debug("Request to save Volunteer : {}", volunteerDTO);
        Volunteer volunteer = volunteerMapper.toEntity(volunteerDTO);
        volunteer = volunteerRepository.save(volunteer);
        return volunteerMapper.toDto(volunteer);
    }

    /**
     * Get all the volunteers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VolunteerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Volunteers");
        return volunteerRepository.findAll(pageable)
            .map(volunteerMapper::toDto);
    }

    /**
     * Get one volunteer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VolunteerDTO> findOne(Long id) {
        log.debug("Request to get Volunteer : {}", id);
        return volunteerRepository.findById(id)
            .map(volunteerMapper::toDto);
    }

    /**
     * Delete the volunteer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Volunteer : {}", id);
        volunteerRepository.deleteById(id);
    }
}
