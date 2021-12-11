package com.epita.techgeek.web.rest;

import com.epita.techgeek.TechGeekApp;
import com.epita.techgeek.domain.Supporter;
import com.epita.techgeek.repository.SupporterRepository;
import com.epita.techgeek.service.SupporterService;
import com.epita.techgeek.service.dto.SupporterDTO;
import com.epita.techgeek.service.mapper.SupporterMapper;

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
 * Integration tests for the {@link SupporterResource} REST controller.
 */
@SpringBootTest(classes = TechGeekApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class SupporterResourceIT {

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
    private SupporterRepository supporterRepository;

    @Autowired
    private SupporterMapper supporterMapper;

    @Autowired
    private SupporterService supporterService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSupporterMockMvc;

    private Supporter supporter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supporter createEntity(EntityManager em) {
        Supporter supporter = new Supporter()
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .address(DEFAULT_ADDRESS)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        return supporter;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Supporter createUpdatedEntity(EntityManager em) {
        Supporter supporter = new Supporter()
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        return supporter;
    }

    @BeforeEach
    public void initTest() {
        supporter = createEntity(em);
    }

    @Test
    @Transactional
    public void createSupporter() throws Exception {
        int databaseSizeBeforeCreate = supporterRepository.findAll().size();

        // Create the Supporter
        SupporterDTO supporterDTO = supporterMapper.toDto(supporter);
        restSupporterMockMvc.perform(post("/api/supporters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supporterDTO)))
            .andExpect(status().isCreated());

        // Validate the Supporter in the database
        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeCreate + 1);
        Supporter testSupporter = supporterList.get(supporterList.size() - 1);
        assertThat(testSupporter.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testSupporter.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testSupporter.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testSupporter.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testSupporter.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createSupporterWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = supporterRepository.findAll().size();

        // Create the Supporter with an existing ID
        supporter.setId(1L);
        SupporterDTO supporterDTO = supporterMapper.toDto(supporter);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSupporterMockMvc.perform(post("/api/supporters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supporterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Supporter in the database
        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSupporters() throws Exception {
        // Initialize the database
        supporterRepository.saveAndFlush(supporter);

        // Get all the supporterList
        restSupporterMockMvc.perform(get("/api/supporters?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(supporter.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getSupporter() throws Exception {
        // Initialize the database
        supporterRepository.saveAndFlush(supporter);

        // Get the supporter
        restSupporterMockMvc.perform(get("/api/supporters/{id}", supporter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(supporter.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    public void getNonExistingSupporter() throws Exception {
        // Get the supporter
        restSupporterMockMvc.perform(get("/api/supporters/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSupporter() throws Exception {
        // Initialize the database
        supporterRepository.saveAndFlush(supporter);

        int databaseSizeBeforeUpdate = supporterRepository.findAll().size();

        // Update the supporter
        Supporter updatedSupporter = supporterRepository.findById(supporter.getId()).get();
        // Disconnect from session so that the updates on updatedSupporter are not directly saved in db
        em.detach(updatedSupporter);
        updatedSupporter
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        SupporterDTO supporterDTO = supporterMapper.toDto(updatedSupporter);

        restSupporterMockMvc.perform(put("/api/supporters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supporterDTO)))
            .andExpect(status().isOk());

        // Validate the Supporter in the database
        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeUpdate);
        Supporter testSupporter = supporterList.get(supporterList.size() - 1);
        assertThat(testSupporter.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testSupporter.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testSupporter.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testSupporter.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testSupporter.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingSupporter() throws Exception {
        int databaseSizeBeforeUpdate = supporterRepository.findAll().size();

        // Create the Supporter
        SupporterDTO supporterDTO = supporterMapper.toDto(supporter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSupporterMockMvc.perform(put("/api/supporters").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(supporterDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Supporter in the database
        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSupporter() throws Exception {
        // Initialize the database
        supporterRepository.saveAndFlush(supporter);

        int databaseSizeBeforeDelete = supporterRepository.findAll().size();

        // Delete the supporter
        restSupporterMockMvc.perform(delete("/api/supporters/{id}", supporter.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Supporter> supporterList = supporterRepository.findAll();
        assertThat(supporterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
