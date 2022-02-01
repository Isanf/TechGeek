package com.epita.techgeek.web.rest;

import com.epita.techgeek.service.FrameworkService;
import com.epita.techgeek.web.rest.errors.BadRequestAlertException;
import com.epita.techgeek.service.dto.FrameworkDTO;

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
 * REST controller for managing {@link com.epita.techgeek.domain.Framework}.
 */
@RestController
@RequestMapping("/api")
public class FrameworkResource {

    private final Logger log = LoggerFactory.getLogger(FrameworkResource.class);

    private static final String ENTITY_NAME = "framework";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FrameworkService frameworkService;

    public FrameworkResource(FrameworkService frameworkService) {
        this.frameworkService = frameworkService;
    }

    /**
     * {@code POST  /frameworks} : Create a new framework.
     *
     * @param frameworkDTO the frameworkDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new frameworkDTO, or with status {@code 400 (Bad Request)} if the framework has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/frameworks")
    public ResponseEntity<FrameworkDTO> createFramework(@RequestBody FrameworkDTO frameworkDTO) throws URISyntaxException {
        log.debug("REST request to save Framework : {}", frameworkDTO);
        if (frameworkDTO.getId() != null) {
            throw new BadRequestAlertException("A new framework cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FrameworkDTO result = frameworkService.save(frameworkDTO);
        return ResponseEntity.created(new URI("/api/frameworks/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /frameworks} : Updates an existing framework.
     *
     * @param frameworkDTO the frameworkDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated frameworkDTO,
     * or with status {@code 400 (Bad Request)} if the frameworkDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the frameworkDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/frameworks")
    public ResponseEntity<FrameworkDTO> updateFramework(@RequestBody FrameworkDTO frameworkDTO) throws URISyntaxException {
        log.debug("REST request to update Framework : {}", frameworkDTO);
        if (frameworkDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FrameworkDTO result = frameworkService.save(frameworkDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, frameworkDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /frameworks} : get all the frameworks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of frameworks in body.
     */
    @GetMapping("/frameworks")
    public ResponseEntity<List<FrameworkDTO>> getAllFrameworks(Pageable pageable) {
        log.debug("REST request to get a page of Frameworks");
        Page<FrameworkDTO> page = frameworkService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /frameworks/:id} : get the "id" framework.
     *
     * @param id the id of the frameworkDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the frameworkDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/frameworks/{id}")
    public ResponseEntity<FrameworkDTO> getFramework(@PathVariable Long id) {
        log.debug("REST request to get Framework : {}", id);
        Optional<FrameworkDTO> frameworkDTO = frameworkService.findOne(id);
        return ResponseUtil.wrapOrNotFound(frameworkDTO);
    }

    /**
     * {@code DELETE  /frameworks/:id} : delete the "id" framework.
     *
     * @param id the id of the frameworkDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/frameworks/{id}")
    public ResponseEntity<Void> deleteFramework(@PathVariable Long id) {
        log.debug("REST request to delete Framework : {}", id);
        frameworkService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
