package com.epita.techgeek.service.impl;

import com.epita.techgeek.service.UploadfileService;
import com.epita.techgeek.domain.Uploadfile;
import com.epita.techgeek.repository.UploadfileRepository;
import com.epita.techgeek.service.dto.UploadfileDTO;
import com.epita.techgeek.service.mapper.UploadfileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Uploadfile}.
 */
@Service
@Transactional
public class UploadfileServiceImpl implements UploadfileService {

    private final Logger log = LoggerFactory.getLogger(UploadfileServiceImpl.class);

    private final UploadfileRepository uploadfileRepository;

    private final UploadfileMapper uploadfileMapper;

    public UploadfileServiceImpl(UploadfileRepository uploadfileRepository, UploadfileMapper uploadfileMapper) {
        this.uploadfileRepository = uploadfileRepository;
        this.uploadfileMapper = uploadfileMapper;
    }

    /**
     * Save a uploadfile.
     *
     * @param uploadfileDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public UploadfileDTO save(UploadfileDTO uploadfileDTO) {
        log.debug("Request to save Uploadfile : {}", uploadfileDTO);
        Uploadfile uploadfile = uploadfileMapper.toEntity(uploadfileDTO);
        uploadfile = uploadfileRepository.save(uploadfile);
        return uploadfileMapper.toDto(uploadfile);
    }

    /**
     * Get all the uploadfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UploadfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Uploadfiles");
        return uploadfileRepository.findAll(pageable)
            .map(uploadfileMapper::toDto);
    }

    /**
     * Get one uploadfile by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UploadfileDTO> findOne(Long id) {
        log.debug("Request to get Uploadfile : {}", id);
        return uploadfileRepository.findById(id)
            .map(uploadfileMapper::toDto);
    }

    /**
     * Delete the uploadfile by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Uploadfile : {}", id);
        uploadfileRepository.deleteById(id);
    }
}
