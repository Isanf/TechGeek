package com.epita.techgeek.service;

import com.epita.techgeek.service.dto.UploadfileDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link com.epita.techgeek.domain.Uploadfile}.
 */
public interface UploadfileService {

    /**
     * Save a uploadfile.
     *
     * @param uploadfileDTO the entity to save.
     * @return the persisted entity.
     */
    UploadfileDTO save(UploadfileDTO uploadfileDTO);

    /**
     * Get all the uploadfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<UploadfileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" uploadfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<UploadfileDTO> findOne(Long id);

    /**
     * Delete the "id" uploadfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
