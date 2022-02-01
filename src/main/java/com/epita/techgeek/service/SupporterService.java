package com.epita.techgeek.service;

import com.epita.techgeek.domain.Supporter;
import com.epita.techgeek.repository.SupporterRepository;
import com.epita.techgeek.service.dto.SupporterDTO;
import com.epita.techgeek.service.mapper.SupporterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Supporter}.
 */
@Service
@Transactional
public class SupporterService {

    private final Logger log = LoggerFactory.getLogger(SupporterService.class);

    private final SupporterRepository supporterRepository;

    private final SupporterMapper supporterMapper;

    public SupporterService(SupporterRepository supporterRepository, SupporterMapper supporterMapper) {
        this.supporterRepository = supporterRepository;
        this.supporterMapper = supporterMapper;
    }

    /**
     * Save a supporter.
     *
     * @param supporterDTO the entity to save.
     * @return the persisted entity.
     */
    public SupporterDTO save(SupporterDTO supporterDTO) {
        log.debug("Request to save Supporter : {}", supporterDTO);
        Supporter supporter = supporterMapper.toEntity(supporterDTO);
        supporter = supporterRepository.save(supporter);
        return supporterMapper.toDto(supporter);
    }

    /**
     * Get all the supporters.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<SupporterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Supporters");
        return supporterRepository.findAll(pageable)
            .map(supporterMapper::toDto);
    }

    /**
     * Get one supporter by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<SupporterDTO> findOne(Long id) {
        log.debug("Request to get Supporter : {}", id);
        return supporterRepository.findById(id)
            .map(supporterMapper::toDto);
    }

    /**
     * Delete the supporter by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Supporter : {}", id);
        supporterRepository.deleteById(id);
    }
}
