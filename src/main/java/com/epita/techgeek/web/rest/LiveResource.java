package com.epita.techgeek.web.rest;

import com.epita.techgeek.domain.Live;
import com.epita.techgeek.repository.LiveRepository;
import com.epita.techgeek.service.LiveService;
import com.epita.techgeek.web.rest.errors.BadRequestAlertException;
import com.epita.techgeek.service.dto.LiveDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.epita.techgeek.domain.Live}.
 */
@RestController
@RequestMapping("/api")
public class LiveResource {

    private final Logger log = LoggerFactory.getLogger(LiveResource.class);

    private static final String ENTITY_NAME = "live";
    private final LiveRepository liveRepository;
    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final LiveService liveService;

    public LiveResource(LiveRepository liveRepository, LiveService liveService) {
        this.liveRepository = liveRepository;
        this.liveService = liveService;
    }

    /**
     * {@code POST  /lives} : Create a new live.
     *
     * @param liveDTO the liveDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new liveDTO, or with status {@code 400 (Bad Request)} if the live has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/lives")
    public ResponseEntity<LiveDTO> createLive(@RequestBody LiveDTO liveDTO) throws URISyntaxException {
        log.debug("REST request to save Live : {}", liveDTO);
        if (liveDTO.getId() != null) {
            throw new BadRequestAlertException("A new live cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LiveDTO result = liveService.save(liveDTO);
        return ResponseEntity.created(new URI("/api/lives/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /lives} : Updates an existing live.
     *
     * @param liveDTO the liveDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated liveDTO,
     * or with status {@code 400 (Bad Request)} if the liveDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the liveDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/lives")
    public ResponseEntity<LiveDTO> updateLive(@RequestBody LiveDTO liveDTO) throws URISyntaxException {
        log.debug("REST request to update Live : {}", liveDTO);
        if (liveDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LiveDTO result = liveService.save(liveDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, liveDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /lives} : get all the lives.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of lives in body.
     */
    @GetMapping("/lives")
    public ResponseEntity<List<LiveDTO>> getAllLives(Pageable pageable) {
        log.debug("REST request to get a page of Lives");
        Page<LiveDTO> page = liveService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /lives/:id} : get the "id" live.
     *
     * @param id the id of the liveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the liveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lives/{id}")
    public ResponseEntity<LiveDTO> getLive(@PathVariable Long id) {
        log.debug("REST request to get Live : {}", id);
        Optional<LiveDTO> liveDTO = liveService.findOne(id);
        return ResponseUtil.wrapOrNotFound(liveDTO);
    }


    /**
     * {@code GET  Next Program
     *
     * @param id the id of the liveDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the liveDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/lives/Last")
    public ResponseEntity<LiveDTO> getLiveLast() {
        ZonedDateTime actual
            = ZonedDateTime.now();
        Optional<Live> live = liveRepository.findFirstByStartDateGreaterThan(actual);
        log.debug(" ***************************************** REST request to get Live : {}", live.get().getStartDate());
        Optional<LiveDTO> liveDTO = liveService.findOne(live.get().getId());

        return ResponseUtil.wrapOrNotFound(liveDTO);
    }

    @GetMapping("/lives/Today")
    public ResponseEntity<LiveDTO> TodayLast() {
        ZonedDateTime actual
            = ZonedDateTime.now();
        Optional<Live> live = liveRepository.findFirstByStartDateIsBetween(actual,actual.plusDays(1));
        log.debug(" ***************************************** REST request to get Live : {}", live.get().getStartDate());
        Optional<LiveDTO> liveDTO = liveService.findOne(live.get().getId());

        return ResponseUtil.wrapOrNotFound(liveDTO);
    }

    /**
     * {@code DELETE  /lives/:id} : delete the "id" live.
     *
     * @param id the id of the liveDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/lives/{id}")
    public ResponseEntity<Void> deleteLive(@PathVariable Long id) {
        log.debug("REST request to delete Live : {}", id);
        liveService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
