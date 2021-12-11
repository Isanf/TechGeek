package com.epita.techgeek.web.rest;

import com.epita.techgeek.TechGeekApp;
import com.epita.techgeek.domain.Volunteer;
import com.epita.techgeek.repository.VolunteerRepository;
import com.epita.techgeek.service.VolunteerService;
import com.epita.techgeek.service.dto.VolunteerDTO;
import com.epita.techgeek.service.mapper.VolunteerMapper;

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
 * Integration tests for the {@link VolunteerResource} REST controller.
 */
@SpringBootTest(classes = TechGeekApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class VolunteerResourceIT {

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
    private VolunteerRepository volunteerRepository;

    @Autowired
    private VolunteerMapper volunteerMapper;

    @Autowired
    private VolunteerService volunteerService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restVolunteerMockMvc;

    private Volunteer volunteer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Volunteer createEntity(EntityManager em) {
        Volunteer volunteer = new Volunteer()
            .firstname(DEFAULT_FIRSTNAME)
            .lastname(DEFAULT_LASTNAME)
            .address(DEFAULT_ADDRESS)
            .email(DEFAULT_EMAIL)
            .phone(DEFAULT_PHONE);
        return volunteer;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Volunteer createUpdatedEntity(EntityManager em) {
        Volunteer volunteer = new Volunteer()
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        return volunteer;
    }

    @BeforeEach
    public void initTest() {
        volunteer = createEntity(em);
    }

    @Test
    @Transactional
    public void createVolunteer() throws Exception {
        int databaseSizeBeforeCreate = volunteerRepository.findAll().size();

        // Create the Volunteer
        VolunteerDTO volunteerDTO = volunteerMapper.toDto(volunteer);
        restVolunteerMockMvc.perform(post("/api/volunteers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(volunteerDTO)))
            .andExpect(status().isCreated());

        // Validate the Volunteer in the database
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList).hasSize(databaseSizeBeforeCreate + 1);
        Volunteer testVolunteer = volunteerList.get(volunteerList.size() - 1);
        assertThat(testVolunteer.getFirstname()).isEqualTo(DEFAULT_FIRSTNAME);
        assertThat(testVolunteer.getLastname()).isEqualTo(DEFAULT_LASTNAME);
        assertThat(testVolunteer.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testVolunteer.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testVolunteer.getPhone()).isEqualTo(DEFAULT_PHONE);
    }

    @Test
    @Transactional
    public void createVolunteerWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = volunteerRepository.findAll().size();

        // Create the Volunteer with an existing ID
        volunteer.setId(1L);
        VolunteerDTO volunteerDTO = volunteerMapper.toDto(volunteer);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVolunteerMockMvc.perform(post("/api/volunteers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(volunteerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Volunteer in the database
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVolunteers() throws Exception {
        // Initialize the database
        volunteerRepository.saveAndFlush(volunteer);

        // Get all the volunteerList
        restVolunteerMockMvc.perform(get("/api/volunteers?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(volunteer.getId().intValue())))
            .andExpect(jsonPath("$.[*].firstname").value(hasItem(DEFAULT_FIRSTNAME)))
            .andExpect(jsonPath("$.[*].lastname").value(hasItem(DEFAULT_LASTNAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)));
    }
    
    @Test
    @Transactional
    public void getVolunteer() throws Exception {
        // Initialize the database
        volunteerRepository.saveAndFlush(volunteer);

        // Get the volunteer
        restVolunteerMockMvc.perform(get("/api/volunteers/{id}", volunteer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(volunteer.getId().intValue()))
            .andExpect(jsonPath("$.firstname").value(DEFAULT_FIRSTNAME))
            .andExpect(jsonPath("$.lastname").value(DEFAULT_LASTNAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE));
    }

    @Test
    @Transactional
    public void getNonExistingVolunteer() throws Exception {
        // Get the volunteer
        restVolunteerMockMvc.perform(get("/api/volunteers/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVolunteer() throws Exception {
        // Initialize the database
        volunteerRepository.saveAndFlush(volunteer);

        int databaseSizeBeforeUpdate = volunteerRepository.findAll().size();

        // Update the volunteer
        Volunteer updatedVolunteer = volunteerRepository.findById(volunteer.getId()).get();
        // Disconnect from session so that the updates on updatedVolunteer are not directly saved in db
        em.detach(updatedVolunteer);
        updatedVolunteer
            .firstname(UPDATED_FIRSTNAME)
            .lastname(UPDATED_LASTNAME)
            .address(UPDATED_ADDRESS)
            .email(UPDATED_EMAIL)
            .phone(UPDATED_PHONE);
        VolunteerDTO volunteerDTO = volunteerMapper.toDto(updatedVolunteer);

        restVolunteerMockMvc.perform(put("/api/volunteers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(volunteerDTO)))
            .andExpect(status().isOk());

        // Validate the Volunteer in the database
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList).hasSize(databaseSizeBeforeUpdate);
        Volunteer testVolunteer = volunteerList.get(volunteerList.size() - 1);
        assertThat(testVolunteer.getFirstname()).isEqualTo(UPDATED_FIRSTNAME);
        assertThat(testVolunteer.getLastname()).isEqualTo(UPDATED_LASTNAME);
        assertThat(testVolunteer.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testVolunteer.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testVolunteer.getPhone()).isEqualTo(UPDATED_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingVolunteer() throws Exception {
        int databaseSizeBeforeUpdate = volunteerRepository.findAll().size();

        // Create the Volunteer
        VolunteerDTO volunteerDTO = volunteerMapper.toDto(volunteer);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVolunteerMockMvc.perform(put("/api/volunteers").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(volunteerDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Volunteer in the database
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVolunteer() throws Exception {
        // Initialize the database
        volunteerRepository.saveAndFlush(volunteer);

        int databaseSizeBeforeDelete = volunteerRepository.findAll().size();

        // Delete the volunteer
        restVolunteerMockMvc.perform(delete("/api/volunteers/{id}", volunteer.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Volunteer> volunteerList = volunteerRepository.findAll();
        assertThat(volunteerList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
