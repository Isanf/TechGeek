package com.epita.techgeek.service;

import com.epita.techgeek.domain.Module;
import com.epita.techgeek.repository.ModuleRepository;
import com.epita.techgeek.service.dto.ModuleDTO;
import com.epita.techgeek.service.mapper.ModuleMapper;
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
 * Service Implementation for managing {@link Module}.
 */
@Service
@Transactional
public class ModuleService {

    private final Logger log = LoggerFactory.getLogger(ModuleService.class);

    private final ModuleRepository moduleRepository;

    private final ModuleMapper moduleMapper;

    public ModuleService(ModuleRepository moduleRepository, ModuleMapper moduleMapper) {
        this.moduleRepository = moduleRepository;
        this.moduleMapper = moduleMapper;
    }

    /**
     * Save a module.
     *
     * @param moduleDTO the entity to save.
     * @return the persisted entity.
     */
    public ModuleDTO save(ModuleDTO moduleDTO) {
        log.debug("Request to save Module : {}", moduleDTO);
        Module module = moduleMapper.toEntity(moduleDTO);
        module = moduleRepository.save(module);
        return moduleMapper.toDto(module);
    }

    /**
     * Get all the modules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ModuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Modules");
        return moduleRepository.findAll(pageable)
            .map(moduleMapper::toDto);
    }


    /**
     *  Get all the modules where Live is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ModuleDTO> findAllWhereLiveIsNull() {
        log.debug("Request to get all modules where Live is null");
        return StreamSupport
            .stream(moduleRepository.findAll().spliterator(), false)
            .filter(module -> module.getLive() == null)
            .map(moduleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     *  Get all the modules where Video is {@code null}.
     *  @return the list of entities.
     */
    @Transactional(readOnly = true) 
    public List<ModuleDTO> findAllWhereVideoIsNull() {
        log.debug("Request to get all modules where Video is null");
        return StreamSupport
            .stream(moduleRepository.findAll().spliterator(), false)
            .filter(module -> module.getVideo() == null)
            .map(moduleMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one module by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ModuleDTO> findOne(Long id) {
        log.debug("Request to get Module : {}", id);
        return moduleRepository.findById(id)
            .map(moduleMapper::toDto);
    }

    /**
     * Delete the module by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Module : {}", id);
        moduleRepository.deleteById(id);
    }
}
