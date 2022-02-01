package com.epita.techgeek.web.rest;

import com.epita.techgeek.service.InfluencerService;
import com.epita.techgeek.web.rest.errors.BadRequestAlertException;
import com.epita.techgeek.service.dto.InfluencerDTO;

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
 * REST controller for managing {@link com.epita.techgeek.domain.Influencer}.
 */
@RestController
@RequestMapping("/api")
public class InfluencerResource {

    private final Logger log = LoggerFactory.getLogger(InfluencerResource.class);

    private static final String ENTITY_NAME = "influencer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final InfluencerService influencerService;

    public InfluencerResource(InfluencerService influencerService) {
        this.influencerService = influencerService;
    }

    /**
     * {@code POST  /influencers} : Create a new influencer.
     *
     * @param influencerDTO the influencerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new influencerDTO, or with status {@code 400 (Bad Request)} if the influencer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/influencers")
    public ResponseEntity<InfluencerDTO> createInfluencer(@RequestBody InfluencerDTO influencerDTO) throws URISyntaxException {
        log.debug("REST request to save Influencer : {}", influencerDTO);
        if (influencerDTO.getId() != null) {
            throw new BadRequestAlertException("A new influencer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InfluencerDTO result = influencerService.save(influencerDTO);
        return ResponseEntity.created(new URI("/api/influencers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /influencers} : Updates an existing influencer.
     *
     * @param influencerDTO the influencerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated influencerDTO,
     * or with status {@code 400 (Bad Request)} if the influencerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the influencerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/influencers")
    public ResponseEntity<InfluencerDTO> updateInfluencer(@RequestBody InfluencerDTO influencerDTO) throws URISyntaxException {
        log.debug("REST request to update Influencer : {}", influencerDTO);
        if (influencerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        InfluencerDTO result = influencerService.save(influencerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, influencerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /influencers} : get all the influencers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of influencers in body.
     */
    @GetMapping("/influencers")
    public ResponseEntity<List<InfluencerDTO>> getAllInfluencers(Pageable pageable) {
        log.debug("REST request to get a page of Influencers");
        Page<InfluencerDTO> page = influencerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /influencers/:id} : get the "id" influencer.
     *
     * @param id the id of the influencerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the influencerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/influencers/{id}")
    public ResponseEntity<InfluencerDTO> getInfluencer(@PathVariable Long id) {
        log.debug("REST request to get Influencer : {}", id);
        Optional<InfluencerDTO> influencerDTO = influencerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(influencerDTO);
    }

    /**
     * {@code DELETE  /influencers/:id} : delete the "id" influencer.
     *
     * @param id the id of the influencerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/influencers/{id}")
    public ResponseEntity<Void> deleteInfluencer(@PathVariable Long id) {
        log.debug("REST request to delete Influencer : {}", id);
        influencerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
