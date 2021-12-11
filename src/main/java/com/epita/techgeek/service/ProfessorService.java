package com.epita.techgeek.service;

import com.epita.techgeek.domain.Professor;
import com.epita.techgeek.repository.ProfessorRepository;
import com.epita.techgeek.service.dto.ProfessorDTO;
import com.epita.techgeek.service.mapper.ProfessorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Professor}.
 */
@Service
@Transactional
public class ProfessorService {

    private final Logger log = LoggerFactory.getLogger(ProfessorService.class);

    private final ProfessorRepository professorRepository;

    private final ProfessorMapper professorMapper;

    public ProfessorService(ProfessorRepository professorRepository, ProfessorMapper professorMapper) {
        this.professorRepository = professorRepository;
        this.professorMapper = professorMapper;
    }

    /**
     * Save a professor.
     *
     * @param professorDTO the entity to save.
     * @return the persisted entity.
     */
    public ProfessorDTO save(ProfessorDTO professorDTO) {
        log.debug("Request to save Professor : {}", professorDTO);
        Professor professor = professorMapper.toEntity(professorDTO);
        professor = professorRepository.save(professor);
        return professorMapper.toDto(professor);
    }

    /**
     * Get all the professors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ProfessorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Professors");
        return professorRepository.findAll(pageable)
            .map(professorMapper::toDto);
    }

    /**
     * Get one professor by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ProfessorDTO> findOne(Long id) {
        log.debug("Request to get Professor : {}", id);
        return professorRepository.findById(id)
            .map(professorMapper::toDto);
    }

    /**
     * Delete the professor by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Professor : {}", id);
        professorRepository.deleteById(id);
    }
}
