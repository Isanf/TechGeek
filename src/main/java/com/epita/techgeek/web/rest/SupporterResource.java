package com.epita.techgeek.web.rest;

import com.epita.techgeek.service.SupporterService;
import com.epita.techgeek.web.rest.errors.BadRequestAlertException;
import com.epita.techgeek.service.dto.SupporterDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.epita.techgeek.domain.Supporter}.
 */
@RestController
@RequestMapping("/api")
public class SupporterResource {

    private final Logger log = LoggerFactory.getLogger(SupporterResource.class);

    private static final String ENTITY_NAME = "supporter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SupporterService supporterService;

    public SupporterResource(SupporterService supporterService) {
        this.supporterService = supporterService;
    }

    /**
     * {@code POST  /supporters} : Create a new supporter.
     *
     * @param supporterDTO the supporterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new supporterDTO, or with status {@code 400 (Bad Request)} if the supporter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/supporters")
    public ResponseEntity<SupporterDTO> createSupporter(@RequestBody SupporterDTO supporterDTO) throws URISyntaxException {
        log.debug("REST request to save Supporter : {}", supporterDTO);
        if (supporterDTO.getId() != null) {
            throw new BadRequestAlertException("A new supporter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SupporterDTO result = supporterService.save(supporterDTO);
        return ResponseEntity.created(new URI("/api/supporters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /supporters} : Updates an existing supporter.
     *
     * @param supporterDTO the supporterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated supporterDTO,
     * or with status {@code 400 (Bad Request)} if the supporterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the supporterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/supporters")
    public ResponseEntity<SupporterDTO> updateSupporter(@RequestBody SupporterDTO supporterDTO) throws URISyntaxException {
        log.debug("REST request to update Supporter : {}", supporterDTO);
        if (supporterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SupporterDTO result = supporterService.save(supporterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, supporterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /supporters} : get all the supporters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of supporters in body.
     */
    @GetMapping("/supporters")
    public ResponseEntity<List<SupporterDTO>> getAllSupporters(Pageable pageable) {
        log.debug("REST request to get a page of Supporters");
        Page<SupporterDTO> page = supporterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /supporters/:id} : get the "id" supporter.
     *
     * @param id the id of the supporterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the supporterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/supporters/{id}")
    public ResponseEntity<SupporterDTO> getSupporter(@PathVariable Long id) {
        log.debug("REST request to get Supporter : {}", id);
        Optional<SupporterDTO> supporterDTO = supporterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(supporterDTO);
    }

    /**
     * {@code DELETE  /supporters/:id} : delete the "id" supporter.
     *
     * @param id the id of the supporterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/supporters/{id}")
    public ResponseEntity<Void> deleteSupporter(@PathVariable Long id) {
        log.debug("REST request to delete Supporter : {}", id);
        supporterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
