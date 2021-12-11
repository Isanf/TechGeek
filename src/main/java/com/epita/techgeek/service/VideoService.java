package com.epita.techgeek.service;

import com.epita.techgeek.domain.Video;
import com.epita.techgeek.repository.VideoRepository;
import com.epita.techgeek.service.dto.VideoDTO;
import com.epita.techgeek.service.mapper.VideoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Video}.
 */
@Service
@Transactional
public class VideoService {

    private final Logger log = LoggerFactory.getLogger(VideoService.class);

    private final VideoRepository videoRepository;

    private final VideoMapper videoMapper;

    public VideoService(VideoRepository videoRepository, VideoMapper videoMapper) {
        this.videoRepository = videoRepository;
        this.videoMapper = videoMapper;
    }

    /**
     * Save a video.
     *
     * @param videoDTO the entity to save.
     * @return the persisted entity.
     */
    public VideoDTO save(VideoDTO videoDTO) {
        log.debug("Request to save Video : {}", videoDTO);
        Video video = videoMapper.toEntity(videoDTO);
        video = videoRepository.save(video);
        return videoMapper.toDto(video);
    }

    /**
     * Get all the videos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<VideoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Videos");
        return videoRepository.findAll(pageable)
            .map(videoMapper::toDto);
    }

    /**
     * Get one video by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<VideoDTO> findOne(Long id) {
        log.debug("Request to get Video : {}", id);
        return videoRepository.findById(id)
            .map(videoMapper::toDto);
    }

    /**
     * Delete the video by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Video : {}", id);
        videoRepository.deleteById(id);
    }
}
