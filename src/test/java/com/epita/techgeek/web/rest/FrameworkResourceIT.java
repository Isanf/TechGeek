package com.epita.techgeek.web.rest;

import com.epita.techgeek.TechGeekApp;
import com.epita.techgeek.domain.Framework;
import com.epita.techgeek.repository.FrameworkRepository;
import com.epita.techgeek.service.FrameworkService;
import com.epita.techgeek.service.dto.FrameworkDTO;
import com.epita.techgeek.service.mapper.FrameworkMapper;

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
 * Integration tests for the {@link FrameworkResource} REST controller.
 */
@SpringBootTest(classes = TechGeekApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class FrameworkResourceIT {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FrameworkRepository frameworkRepository;

    @Autowired
    private FrameworkMapper frameworkMapper;

    @Autowired
    private FrameworkService frameworkService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFrameworkMockMvc;

    private Framework framework;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Framework createEntity(EntityManager em) {
        Framework framework = new Framework()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION);
        return framework;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Framework createUpdatedEntity(EntityManager em) {
        Framework framework = new Framework()
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        return framework;
    }

    @BeforeEach
    public void initTest() {
        framework = createEntity(em);
    }

    @Test
    @Transactional
    public void createFramework() throws Exception {
        int databaseSizeBeforeCreate = frameworkRepository.findAll().size();

        // Create the Framework
        FrameworkDTO frameworkDTO = frameworkMapper.toDto(framework);
        restFrameworkMockMvc.perform(post("/api/frameworks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(frameworkDTO)))
            .andExpect(status().isCreated());

        // Validate the Framework in the database
        List<Framework> frameworkList = frameworkRepository.findAll();
        assertThat(frameworkList).hasSize(databaseSizeBeforeCreate + 1);
        Framework testFramework = frameworkList.get(frameworkList.size() - 1);
        assertThat(testFramework.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testFramework.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createFrameworkWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = frameworkRepository.findAll().size();

        // Create the Framework with an existing ID
        framework.setId(1L);
        FrameworkDTO frameworkDTO = frameworkMapper.toDto(framework);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFrameworkMockMvc.perform(post("/api/frameworks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(frameworkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Framework in the database
        List<Framework> frameworkList = frameworkRepository.findAll();
        assertThat(frameworkList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllFrameworks() throws Exception {
        // Initialize the database
        frameworkRepository.saveAndFlush(framework);

        // Get all the frameworkList
        restFrameworkMockMvc.perform(get("/api/frameworks?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(framework.getId().intValue())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getFramework() throws Exception {
        // Initialize the database
        frameworkRepository.saveAndFlush(framework);

        // Get the framework
        restFrameworkMockMvc.perform(get("/api/frameworks/{id}", framework.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(framework.getId().intValue()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingFramework() throws Exception {
        // Get the framework
        restFrameworkMockMvc.perform(get("/api/frameworks/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFramework() throws Exception {
        // Initialize the database
        frameworkRepository.saveAndFlush(framework);

        int databaseSizeBeforeUpdate = frameworkRepository.findAll().size();

        // Update the framework
        Framework updatedFramework = frameworkRepository.findById(framework.getId()).get();
        // Disconnect from session so that the updates on updatedFramework are not directly saved in db
        em.detach(updatedFramework);
        updatedFramework
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION);
        FrameworkDTO frameworkDTO = frameworkMapper.toDto(updatedFramework);

        restFrameworkMockMvc.perform(put("/api/frameworks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(frameworkDTO)))
            .andExpect(status().isOk());

        // Validate the Framework in the database
        List<Framework> frameworkList = frameworkRepository.findAll();
        assertThat(frameworkList).hasSize(databaseSizeBeforeUpdate);
        Framework testFramework = frameworkList.get(frameworkList.size() - 1);
        assertThat(testFramework.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testFramework.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingFramework() throws Exception {
        int databaseSizeBeforeUpdate = frameworkRepository.findAll().size();

        // Create the Framework
        FrameworkDTO frameworkDTO = frameworkMapper.toDto(framework);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFrameworkMockMvc.perform(put("/api/frameworks").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(frameworkDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Framework in the database
        List<Framework> frameworkList = frameworkRepository.findAll();
        assertThat(frameworkList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteFramework() throws Exception {
        // Initialize the database
        frameworkRepository.saveAndFlush(framework);

        int databaseSizeBeforeDelete = frameworkRepository.findAll().size();

        // Delete the framework
        restFrameworkMockMvc.perform(delete("/api/frameworks/{id}", framework.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Framework> frameworkList = frameworkRepository.findAll();
        assertThat(frameworkList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
