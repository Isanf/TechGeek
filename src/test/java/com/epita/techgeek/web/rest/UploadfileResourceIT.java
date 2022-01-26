package com.epita.techgeek.web.rest;

import com.epita.techgeek.TechGeekApp;
import com.epita.techgeek.domain.Uploadfile;
import com.epita.techgeek.repository.UploadfileRepository;
import com.epita.techgeek.service.UploadfileService;
import com.epita.techgeek.service.dto.UploadfileDTO;
import com.epita.techgeek.service.mapper.UploadfileMapper;
import com.epita.techgeek.service.dto.UploadfileCriteria;
import com.epita.techgeek.service.UploadfileQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link UploadfileResource} REST controller.
 */
@SpringBootTest(classes = TechGeekApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class UploadfileResourceIT {

    private static final byte[] DEFAULT_MYFILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_MYFILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_MYFILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_MYFILE_CONTENT_TYPE = "image/png";

    @Autowired
    private UploadfileRepository uploadfileRepository;

    @Autowired
    private UploadfileMapper uploadfileMapper;

    @Autowired
    private UploadfileService uploadfileService;

    @Autowired
    private UploadfileQueryService uploadfileQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restUploadfileMockMvc;

    private Uploadfile uploadfile;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uploadfile createEntity(EntityManager em) {
        Uploadfile uploadfile = new Uploadfile()
            .myfile(DEFAULT_MYFILE)
            .myfileContentType(DEFAULT_MYFILE_CONTENT_TYPE);
        return uploadfile;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Uploadfile createUpdatedEntity(EntityManager em) {
        Uploadfile uploadfile = new Uploadfile()
            .myfile(UPDATED_MYFILE)
            .myfileContentType(UPDATED_MYFILE_CONTENT_TYPE);
        return uploadfile;
    }

    @BeforeEach
    public void initTest() {
        uploadfile = createEntity(em);
    }

    @Test
    @Transactional
    public void createUploadfile() throws Exception {
        int databaseSizeBeforeCreate = uploadfileRepository.findAll().size();

        // Create the Uploadfile
        UploadfileDTO uploadfileDTO = uploadfileMapper.toDto(uploadfile);
        restUploadfileMockMvc.perform(post("/api/uploadfiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadfileDTO)))
            .andExpect(status().isCreated());

        // Validate the Uploadfile in the database
        List<Uploadfile> uploadfileList = uploadfileRepository.findAll();
        assertThat(uploadfileList).hasSize(databaseSizeBeforeCreate + 1);
        Uploadfile testUploadfile = uploadfileList.get(uploadfileList.size() - 1);
        assertThat(testUploadfile.getMyfile()).isEqualTo(DEFAULT_MYFILE);
        assertThat(testUploadfile.getMyfileContentType()).isEqualTo(DEFAULT_MYFILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createUploadfileWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uploadfileRepository.findAll().size();

        // Create the Uploadfile with an existing ID
        uploadfile.setId(1L);
        UploadfileDTO uploadfileDTO = uploadfileMapper.toDto(uploadfile);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUploadfileMockMvc.perform(post("/api/uploadfiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Uploadfile in the database
        List<Uploadfile> uploadfileList = uploadfileRepository.findAll();
        assertThat(uploadfileList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUploadfiles() throws Exception {
        // Initialize the database
        uploadfileRepository.saveAndFlush(uploadfile);

        // Get all the uploadfileList
        restUploadfileMockMvc.perform(get("/api/uploadfiles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].myfileContentType").value(hasItem(DEFAULT_MYFILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].myfile").value(hasItem(Base64Utils.encodeToString(DEFAULT_MYFILE))));
    }
    
    @Test
    @Transactional
    public void getUploadfile() throws Exception {
        // Initialize the database
        uploadfileRepository.saveAndFlush(uploadfile);

        // Get the uploadfile
        restUploadfileMockMvc.perform(get("/api/uploadfiles/{id}", uploadfile.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(uploadfile.getId().intValue()))
            .andExpect(jsonPath("$.myfileContentType").value(DEFAULT_MYFILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.myfile").value(Base64Utils.encodeToString(DEFAULT_MYFILE)));
    }


    @Test
    @Transactional
    public void getUploadfilesByIdFiltering() throws Exception {
        // Initialize the database
        uploadfileRepository.saveAndFlush(uploadfile);

        Long id = uploadfile.getId();

        defaultUploadfileShouldBeFound("id.equals=" + id);
        defaultUploadfileShouldNotBeFound("id.notEquals=" + id);

        defaultUploadfileShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultUploadfileShouldNotBeFound("id.greaterThan=" + id);

        defaultUploadfileShouldBeFound("id.lessThanOrEqual=" + id);
        defaultUploadfileShouldNotBeFound("id.lessThan=" + id);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultUploadfileShouldBeFound(String filter) throws Exception {
        restUploadfileMockMvc.perform(get("/api/uploadfiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uploadfile.getId().intValue())))
            .andExpect(jsonPath("$.[*].myfileContentType").value(hasItem(DEFAULT_MYFILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].myfile").value(hasItem(Base64Utils.encodeToString(DEFAULT_MYFILE))));

        // Check, that the count call also returns 1
        restUploadfileMockMvc.perform(get("/api/uploadfiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultUploadfileShouldNotBeFound(String filter) throws Exception {
        restUploadfileMockMvc.perform(get("/api/uploadfiles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restUploadfileMockMvc.perform(get("/api/uploadfiles/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingUploadfile() throws Exception {
        // Get the uploadfile
        restUploadfileMockMvc.perform(get("/api/uploadfiles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUploadfile() throws Exception {
        // Initialize the database
        uploadfileRepository.saveAndFlush(uploadfile);

        int databaseSizeBeforeUpdate = uploadfileRepository.findAll().size();

        // Update the uploadfile
        Uploadfile updatedUploadfile = uploadfileRepository.findById(uploadfile.getId()).get();
        // Disconnect from session so that the updates on updatedUploadfile are not directly saved in db
        em.detach(updatedUploadfile);
        updatedUploadfile
            .myfile(UPDATED_MYFILE)
            .myfileContentType(UPDATED_MYFILE_CONTENT_TYPE);
        UploadfileDTO uploadfileDTO = uploadfileMapper.toDto(updatedUploadfile);

        restUploadfileMockMvc.perform(put("/api/uploadfiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadfileDTO)))
            .andExpect(status().isOk());

        // Validate the Uploadfile in the database
        List<Uploadfile> uploadfileList = uploadfileRepository.findAll();
        assertThat(uploadfileList).hasSize(databaseSizeBeforeUpdate);
        Uploadfile testUploadfile = uploadfileList.get(uploadfileList.size() - 1);
        assertThat(testUploadfile.getMyfile()).isEqualTo(UPDATED_MYFILE);
        assertThat(testUploadfile.getMyfileContentType()).isEqualTo(UPDATED_MYFILE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingUploadfile() throws Exception {
        int databaseSizeBeforeUpdate = uploadfileRepository.findAll().size();

        // Create the Uploadfile
        UploadfileDTO uploadfileDTO = uploadfileMapper.toDto(uploadfile);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUploadfileMockMvc.perform(put("/api/uploadfiles").with(csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(uploadfileDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Uploadfile in the database
        List<Uploadfile> uploadfileList = uploadfileRepository.findAll();
        assertThat(uploadfileList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUploadfile() throws Exception {
        // Initialize the database
        uploadfileRepository.saveAndFlush(uploadfile);

        int databaseSizeBeforeDelete = uploadfileRepository.findAll().size();

        // Delete the uploadfile
        restUploadfileMockMvc.perform(delete("/api/uploadfiles/{id}", uploadfile.getId()).with(csrf())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Uploadfile> uploadfileList = uploadfileRepository.findAll();
        assertThat(uploadfileList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
