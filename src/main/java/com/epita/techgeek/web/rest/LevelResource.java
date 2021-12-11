package com.epita.techgeek.web.rest;

import com.epita.techgeek.service.LevelService;
import com.epita.techgeek.web.rest.errors.BadRequestAlertException;
import com.epita.techgeek.service.dto.LevelDTO;

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
import java.util.stream.StreamSupport;

/**
 * REST controller for managing {@link com.epita.techgeek.domain.Level}.
 */
@RestController
@RequestMapping("/api")
public class LevelResource {

    private final Logger log = LoggerFactory.getLogger(LevelResource.class);

    private static final String ENTITY_NAME = "level";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LevelService levelService;

    public LevelResource(LevelService levelService) {
        this.levelService = levelService;
    }

    /**
     * {@code POST  /levels} : Create a new level.
     *
     * @param levelDTO the levelDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new levelDTO, or with status {@code 400 (Bad Request)} if the level has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/levels")
    public ResponseEntity<LevelDTO> createLevel(@RequestBody LevelDTO levelDTO) throws URISyntaxException {
        log.debug("REST request to save Level : {}", levelDTO);
        if (levelDTO.getId() != null) {
            throw new BadRequestAlertException("A new level cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LevelDTO result = levelService.save(levelDTO);
        return ResponseEntity.created(new URI("/api/levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /levels} : Updates an existing level.
     *
     * @param levelDTO the levelDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated levelDTO,
     * or with status {@code 400 (Bad Request)} if the levelDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the levelDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/levels")
    public ResponseEntity<LevelDTO> updateLevel(@RequestBody LevelDTO levelDTO) throws URISyntaxException {
        log.debug("REST request to update Level : {}", levelDTO);
        if (levelDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LevelDTO result = levelService.save(levelDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, levelDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /levels} : get all the levels.
     *
     * @param pageable the pagination information.
     * @param filter the filter of the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of levels in body.
     */
    @GetMapping("/levels")
    public ResponseEntity<List<LevelDTO>> getAllLevels(Pageable pageable, @RequestParam(required = false) String filter) {
        if ("student-is-null".equals(filter)) {
            log.debug("REST request to get all Levels where student is null");
            return new ResponseEntity<>(levelService.findAllWhereStudentIsNull(),
                    HttpStatus.OK);
        }
        log.debug("REST request to get a page of Levels");
        Page<LevelDTO> page = levelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /levels/:id} : get the "id" level.
     *
     * @param id the id of the levelDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the levelDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/levels/{id}")
    public ResponseEntity<LevelDTO> getLevel(@PathVariable Long id) {
        log.debug("REST request to get Level : {}", id);
        Optional<LevelDTO> levelDTO = levelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(levelDTO);
    }

    /**
     * {@code DELETE  /levels/:id} : delete the "id" level.
     *
     * @param id the id of the levelDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/levels/{id}")
    public ResponseEntity<Void> deleteLevel(@PathVariable Long id) {
        log.debug("REST request to delete Level : {}", id);
        levelService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
