package com.epita.techgeek.service;

import com.epita.techgeek.domain.Influencer;
import com.epita.techgeek.repository.InfluencerRepository;
import com.epita.techgeek.service.dto.InfluencerDTO;
import com.epita.techgeek.service.mapper.InfluencerMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Influencer}.
 */
@Service
@Transactional
public class InfluencerService {

    private final Logger log = LoggerFactory.getLogger(InfluencerService.class);

    private final InfluencerRepository influencerRepository;

    private final InfluencerMapper influencerMapper;

    public InfluencerService(InfluencerRepository influencerRepository, InfluencerMapper influencerMapper) {
        this.influencerRepository = influencerRepository;
        this.influencerMapper = influencerMapper;
    }

    /**
     * Save a influencer.
     *
     * @param influencerDTO the entity to save.
     * @return the persisted entity.
     */
    public InfluencerDTO save(InfluencerDTO influencerDTO) {
        log.debug("Request to save Influencer : {}", influencerDTO);
        Influencer influencer = influencerMapper.toEntity(influencerDTO);
        influencer = influencerRepository.save(influencer);
        return influencerMapper.toDto(influencer);
    }

    /**
     * Get all the influencers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<InfluencerDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Influencers");
        return influencerRepository.findAll(pageable)
            .map(influencerMapper::toDto);
    }

    /**
     * Get one influencer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<InfluencerDTO> findOne(Long id) {
        log.debug("Request to get Influencer : {}", id);
        return influencerRepository.findById(id)
            .map(influencerMapper::toDto);
    }

    /**
     * Delete the influencer by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Influencer : {}", id);
        influencerRepository.deleteById(id);
    }
}
