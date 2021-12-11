package com.epita.techgeek.web.rest;

import com.epita.techgeek.TechGeekApp;
import com.epita.techgeek.domain.Influencer;
import com.epita.techgeek.repository.InfluencerRepository;
import com.epita.techgeek.service.InfluencerService;
import com.epita.techgeek.service.dto.InfluencerDTO;
import com.epita.techgeek.service.mapper.InfluencerMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link InfluencerResource} REST controller.
 */
@SpringBootTest(classes = TechGeekApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class InfluencerResourceIT {

    private static final String DEFAULT_FIRSTNAME = "AAAAAAAAAA";
    private static final String UPDATED_FIRSTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_LASTNAME = "AAAAAAAAAA";
    private static final String UPDATED_LASTNAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    @Autowired
    private InfluencerRepository influencerRepository;

    @Autowired
    private InfluencerMapper influencerMapper;

    @Autowired
    private InfluencerService influencerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restInfluencerMockMvc;

    private Influencer influencer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Influencer createEntity(EntityManager em) {
        Influencer influencer = new Influencer()
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .address(DEFAULT_ADDRESS)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        return influencer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Influencer createUpdatedEntity(EntityManager em) {
        Influencer influencer = new Influencer()
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        return influencer;
    }

    @BeforeEach
    public void initTest() {
        influencer = createEntity(em);
    }

    @Test
    @Transactional
    public void createInfluencer() throws Exception {
        int databaseSizeBeforeCreate = influencerRepository.findAll().size();

        // Create the Influencer
        InfluencerDTO influencerDTO = influencerMapper.toDto(influencer);
        restInfluencerMockMvc.perform(post("/api/influencers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(influencerDTO)))
            .andExpect(status().isCreated());

        // Validate the Influencer in the database
        List<Influencer> influencerList = influencerRepository.findAll();
        assertThat(influencerList).hasSize(databaseSizeBeforeCreate + 1);
        Influencer testInfluencer = influencerList.get(influencerList.size() - 1);
        assertThat(testInfluencer.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testInfluencer.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testInfluencer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testInfluencer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testInfluencer.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createInfluencerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = influencerRepository.findAll().size();

        // Create the Influencer with an existing ID
        influencer.setId(1L);
        InfluencerDTO influencerDTO = influencerMapper.toDto(influencer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfluencerMockMvc.perform(post("/api/influencers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(influencerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Influencer in the database
        List<Influencer> influencerList = influencerRepository.findAll();
        assertThat(influencerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInfluencers() throws Exception {
        // Initialize the database
        influencerRepository.saveAndFlush(influencer);

        // Get all the influencerList
        restInfluencerMockMvc.perform(get("/api/influencers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(influencer.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getInfluencer() throws Exception {
        // Initialize the database
        influencerRepository.saveAndFlush(influencer);

        // Get the influencer
        restInfluencerMockMvc.perform(get("/api/influencers/{id}", influencer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(influencer.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    public void getNonExistingInfluencer() throws Exception {
        // Get the influencer
        restInfluencerMockMvc.perform(get("/api/influencers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInfluencer() throws Exception {
        // Initialize the database
        influencerRepository.saveAndFlush(influencer);

        int databaseSizeBeforeUpdate = influencerRepository.findAll().size();

        // Update the influencer
        Influencer updatedInfluencer = influencerRepository.findById(influencer.getId()).get();
        // Disconnect from session so that the updates on updatedInfluencer are not directly saved in db
        em.detach(updatedInfluencer);
        updatedInfluencer
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        InfluencerDTO influencerDTO = influencerMapper.toDto(updatedInfluencer);

        restInfluencerMockMvc.perform(put("/api/influencers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(influencerDTO)))
            .andExpect(status().isOk());

        // Validate the Influencer in the database
        List<Influencer> influencerList = influencerRepository.findAll();
        assertThat(influencerList).hasSize(databaseSizeBeforeUpdate);
        Influencer testInfluencer = influencerList.get(influencerList.size() - 1);
        assertThat(testInfluencer.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testInfluencer.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testInfluencer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testInfluencer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testInfluencer.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingInfluencer() throws Exception {
        int databaseSizeBeforeUpdate = influencerRepository.findAll().size();

        // Create the Influencer
        InfluencerDTO influencerDTO = influencerMapper.toDto(influencer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfluencerMockMvc.perform(put("/api/influencers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(influencerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Influencer in the database
        List<Influencer> influencerList = influencerRepository.findAll();
        assertThat(influencerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInfluencer() throws Exception {
        // Initialize the database
        influencerRepository.saveAndFlush(influencer);

        int databaseSizeBeforeDelete = influencerRepository.findAll().size();

        // Delete the influencer
        restInfluencerMockMvc.perform(delete("/api/influencers/{id}", influencer.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Influencer> influencerList = influencerRepository.findAll();
        assertThat(influencerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
