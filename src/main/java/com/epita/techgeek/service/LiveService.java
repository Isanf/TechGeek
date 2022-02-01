package com.epita.techgeek.service;

import com.epita.techgeek.domain.Live;
import com.epita.techgeek.repository.LiveRepository;
import com.epita.techgeek.service.dto.LiveDTO;
import com.epita.techgeek.service.mapper.LiveMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Live}.
 */
@Service
@Transactional
public class LiveService {

    private final Logger log = LoggerFactory.getLogger(LiveService.class);

    private final LiveRepository liveRepository;

    private final LiveMapper liveMapper;

    public LiveService(LiveRepository liveRepository, LiveMapper liveMapper) {
        this.liveRepository = liveRepository;
        this.liveMapper = liveMapper;
    }

    /**
     * Save a live.
     *
     * @param liveDTO the entity to save.
     * @return the persisted entity.
     */
    public LiveDTO save(LiveDTO liveDTO) {
        log.debug("Request to save Live : {}", liveDTO);
        Live live = liveMapper.toEntity(liveDTO);
        live = liveRepository.save(live);
        return liveMapper.toDto(live);
    }

    /**
     * Get all the lives.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<LiveDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Lives");
        return liveRepository.findAll(pageable)
            .map(liveMapper::toDto);
    }

    /**
     * Get one live by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<LiveDTO> findOne(Long id) {
        log.debug("Request to get Live : {}", id);
        return liveRepository.findById(id)
            .map(liveMapper::toDto);
    }

    /**
     * Delete the live by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Live : {}", id);
        liveRepository.deleteById(id);
    }
}
