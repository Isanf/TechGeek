package com.epita.techgeek.service;

import com.epita.techgeek.domain.Framework;
import com.epita.techgeek.repository.FrameworkRepository;
import com.epita.techgeek.service.dto.FrameworkDTO;
import com.epita.techgeek.service.mapper.FrameworkMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Framework}.
 */
@Service
@Transactional
public class FrameworkService {

    private final Logger log = LoggerFactory.getLogger(FrameworkService.class);

    private final FrameworkRepository frameworkRepository;

    private final FrameworkMapper frameworkMapper;

    public FrameworkService(FrameworkRepository frameworkRepository, FrameworkMapper frameworkMapper) {
        this.frameworkRepository = frameworkRepository;
        this.frameworkMapper = frameworkMapper;
    }

    /**
     * Save a framework.
     *
     * @param frameworkDTO the entity to save.
     * @return the persisted entity.
     */
    public FrameworkDTO save(FrameworkDTO frameworkDTO) {
        log.debug("Request to save Framework : {}", frameworkDTO);
        Framework framework = frameworkMapper.toEntity(frameworkDTO);
        framework = frameworkRepository.save(framework);
        return frameworkMapper.toDto(framework);
    }

    /**
     * Get all the frameworks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FrameworkDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Frameworks");
        return frameworkRepository.findAll(pageable)
            .map(frameworkMapper::toDto);
    }

    /**
     * Get one framework by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FrameworkDTO> findOne(Long id) {
        log.debug("Request to get Framework : {}", id);
        return frameworkRepository.findById(id)
            .map(frameworkMapper::toDto);
    }

    /**
     * Delete the framework by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Framework : {}", id);
        frameworkRepository.deleteById(id);
    }
}
