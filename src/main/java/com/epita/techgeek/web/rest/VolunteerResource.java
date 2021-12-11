package com.epita.techgeek.web.rest;

import com.epita.techgeek.service.VolunteerService;
import com.epita.techgeek.web.rest.errors.BadRequestAlertException;
import com.epita.techgeek.service.dto.VolunteerDTO;

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
 * REST controller for managing {@link com.epita.techgeek.domain.Volunteer}.
 */
@RestController
@RequestMapping("/api")
public class VolunteerResource {

    private final Logger log = LoggerFactory.getLogger(VolunteerResource.class);

    private static final String ENTITY_NAME = "volunteer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VolunteerService volunteerService;

    public VolunteerResource(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    /**
     * {@code POST  /volunteers} : Create a new volunteer.
     *
     * @param volunteerDTO the volunteerDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new volunteerDTO, or with status {@code 400 (Bad Request)} if the volunteer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/volunteers")
    public ResponseEntity<VolunteerDTO> createVolunteer(@RequestBody VolunteerDTO volunteerDTO) throws URISyntaxException {
        log.debug("REST request to save Volunteer : {}", volunteerDTO);
        if (volunteerDTO.getId() != null) {
            throw new BadRequestAlertException("A new volunteer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VolunteerDTO result = volunteerService.save(volunteerDTO);
        return ResponseEntity.created(new URI("/api/volunteers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /volunteers} : Updates an existing volunteer.
     *
     * @param volunteerDTO the volunteerDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated volunteerDTO,
     * or with status {@code 400 (Bad Request)} if the volunteerDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the volunteerDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/volunteers")
    public ResponseEntity<VolunteerDTO> updateVolunteer(@RequestBody VolunteerDTO volunteerDTO) throws URISyntaxException {
        log.debug("REST request to update Volunteer : {}", volunteerDTO);
        if (volunteerDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VolunteerDTO result = volunteerService.save(volunteerDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, volunteerDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /volunteers} : get all the volunteers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of volunteers in body.
     */
    @GetMapping("/volunteers")
    public ResponseEntity<List<VolunteerDTO>> getAllVolunteers(Pageable pageable) {
        log.debug("REST request to get a page of Volunteers");
        Page<VolunteerDTO> page = volunteerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /volunteers/:id} : get the "id" volunteer.
     *
     * @param id the id of the volunteerDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the volunteerDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/volunteers/{id}")
    public ResponseEntity<VolunteerDTO> getVolunteer(@PathVariable Long id) {
        log.debug("REST request to get Volunteer : {}", id);
        Optional<VolunteerDTO> volunteerDTO = volunteerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(volunteerDTO);
    }

    /**
     * {@code DELETE  /volunteers/:id} : delete the "id" volunteer.
     *
     * @param id the id of the volunteerDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/volunteers/{id}")
    public ResponseEntity<Void> deleteVolunteer(@PathVariable Long id) {
        log.debug("REST request to delete Volunteer : {}", id);
        volunteerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
