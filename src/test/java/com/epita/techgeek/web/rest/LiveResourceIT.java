package com.epita.techgeek.web.rest;

import com.epita.techgeek.TechGeekApp;
import com.epita.techgeek.domain.Live;
import com.epita.techgeek.repository.LiveRepository;
import com.epita.techgeek.service.LiveService;
import com.epita.techgeek.service.dto.LiveDTO;
import com.epita.techgeek.service.mapper.LiveMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.epita.techgeek.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LiveResource} REST controller.
 */
@SpringBootTest(classes = TechGeekApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class LiveResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final ZonedDateTime DEFAULT_START_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_START_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final ZonedDateTime DEFAULT_END_DATE = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_END_DATE = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);

    private static final Boolean DEFAULT_PREMIUM = false;
    private static final Boolean UPDATED_PREMIUM = true;

    @Autowired
    private LiveRepository liveRepository;

    @Autowired
    private LiveMapper liveMapper;

    @Autowired
    private LiveService liveService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLiveMockMvc;

    private Live live;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Live createEntity(EntityManager em) {
        Live live = new Live()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .source(DEFAULT_SOURCE)
            .startDate(DEFAULT_START_DATE)
            .endDate(DEFAULT_END_DATE)
            .premium(DEFAULT_PREMIUM);
        return live;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Live createUpdatedEntity(EntityManager em) {
        Live live = new Live()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .source(UPDATED_SOURCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .premium(UPDATED_PREMIUM);
        return live;
    }

    @BeforeEach
    public void initTest() {
        live = createEntity(em);
    }

    @Test
    @Transactional
    public void createLive() throws Exception {
        int databaseSizeBeforeCreate = liveRepository.findAll().size();

        // Create the Live
        LiveDTO liveDTO = liveMapper.toDto(live);
        restLiveMockMvc.perform(post("/api/lives").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(liveDTO)))
            .andExpect(status().isCreated());

        // Validate the Live in the database
        List<Live> liveList = liveRepository.findAll();
        assertThat(liveList).hasSize(databaseSizeBeforeCreate + 1);
        Live testLive = liveList.get(liveList.size() - 1);
        assertThat(testLive.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testLive.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testLive.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testLive.getStartDate()).isEqualTo(DEFAULT_START_DATE);
        assertThat(testLive.getEndDate()).isEqualTo(DEFAULT_END_DATE);
        assertThat(testLive.isPremium()).isEqualTo(DEFAULT_PREMIUM);
    }

    @Test
    @Transactional
    public void createLiveWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = liveRepository.findAll().size();

        // Create the Live with an existing ID
        live.setId(1L);
        LiveDTO liveDTO = liveMapper.toDto(live);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLiveMockMvc.perform(post("/api/lives").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(liveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Live in the database
        List<Live> liveList = liveRepository.findAll();
        assertThat(liveList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLives() throws Exception {
        // Initialize the database
        liveRepository.saveAndFlush(live);

        // Get all the liveList
        restLiveMockMvc.perform(get("/api/lives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(live.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE)))
            .andExpect(jsonPath("$.[*].startDate").value(hasItem(sameInstant(DEFAULT_START_DATE))))
            .andExpect(jsonPath("$.[*].endDate").value(hasItem(sameInstant(DEFAULT_END_DATE))))
            .andExpect(jsonPath("$.[*].premium").value(hasItem(DEFAULT_PREMIUM.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getLive() throws Exception {
        // Initialize the database
        liveRepository.saveAndFlush(live);

        // Get the live
        restLiveMockMvc.perform(get("/api/lives/{id}", live.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(live.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE))
            .andExpect(jsonPath("$.startDate").value(sameInstant(DEFAULT_START_DATE)))
            .andExpect(jsonPath("$.endDate").value(sameInstant(DEFAULT_END_DATE)))
            .andExpect(jsonPath("$.premium").value(DEFAULT_PREMIUM.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingLive() throws Exception {
        // Get the live
        restLiveMockMvc.perform(get("/api/lives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLive() throws Exception {
        // Initialize the database
        liveRepository.saveAndFlush(live);

        int databaseSizeBeforeUpdate = liveRepository.findAll().size();

        // Update the live
        Live updatedLive = liveRepository.findById(live.getId()).get();
        // Disconnect from session so that the updates on updatedLive are not directly saved in db
        em.detach(updatedLive);
        updatedLive
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .source(UPDATED_SOURCE)
            .startDate(UPDATED_START_DATE)
            .endDate(UPDATED_END_DATE)
            .premium(UPDATED_PREMIUM);
        LiveDTO liveDTO = liveMapper.toDto(updatedLive);

        restLiveMockMvc.perform(put("/api/lives").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(liveDTO)))
            .andExpect(status().isOk());

        // Validate the Live in the database
        List<Live> liveList = liveRepository.findAll();
        assertThat(liveList).hasSize(databaseSizeBeforeUpdate);
        Live testLive = liveList.get(liveList.size() - 1);
        assertThat(testLive.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testLive.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testLive.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testLive.getStartDate()).isEqualTo(UPDATED_START_DATE);
        assertThat(testLive.getEndDate()).isEqualTo(UPDATED_END_DATE);
        assertThat(testLive.isPremium()).isEqualTo(UPDATED_PREMIUM);
    }

    @Test
    @Transactional
    public void updateNonExistingLive() throws Exception {
        int databaseSizeBeforeUpdate = liveRepository.findAll().size();

        // Create the Live
        LiveDTO liveDTO = liveMapper.toDto(live);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLiveMockMvc.perform(put("/api/lives").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(liveDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Live in the database
        List<Live> liveList = liveRepository.findAll();
        assertThat(liveList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLive() throws Exception {
        // Initialize the database
        liveRepository.saveAndFlush(live);

        int databaseSizeBeforeDelete = liveRepository.findAll().size();

        // Delete the live
        restLiveMockMvc.perform(delete("/api/lives/{id}", live.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Live> liveList = liveRepository.findAll();
        assertThat(liveList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
