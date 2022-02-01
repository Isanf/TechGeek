package com.epita.techgeek.service;

import com.epita.techgeek.domain.Level;
import com.epita.techgeek.repository.LevelRepository;
import com.epita.techgeek.service.dto.LevelDTO;
import com.epita.techgeek.service.mapper.LevelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * Service Implementation for managing {@link Level}.
 */
@Service
@Transactional
public class LevelService {

    private final Logger log = LoggerFactory.getLogger(LevelService.class);

    private final LevelRepository levelRepository;

    private final LevelMapper levelMapper;

    public LevelService(LevelRepository levelRepository, LevelMapper levelMapper) {
        this.levelRepository = levelRepository;
        this.levelMapper = levelMapper;
    }

    /**
     * Save a level.
     *
     * @param levelDTO the entity to save.
     * @return the persisted entity.
     */
    public LevelDTO save(LevelDTO levelDTO) {
        log.debug("Request to save Level : {}", levelDTO);
        Level level = levelMapper.toEntity(levelDTO);
        level = levelRepository.save(level);
        return levelMapper.toDto(level);
    }

    /**
     * Get all the levels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LevelDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Levels");
        return levelRepository.findAll(pageable)
            .map(levelMapper::toDto);
    }


    /**
     *  Get all the levels where Student is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<LevelDTO> findAllWhereStudentIsNull() {
        log.debug("Request to get all levels where Student is null");
        return StreamSupport
            .stream(levelRepository.findAll().spliterator(), false)
            .filter(level -> level.getStudent() == null)
            .map(levelMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one level by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LevelDTO> findOne(Long id) {
        log.debug("Request to get Level : {}", id);
        return levelRepository.findById(id)
            .map(levelMapper::toDto);
    }

    /**
     * Delete the level by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Level : {}", id);
        levelRepository.deleteById(id);
    }
}
