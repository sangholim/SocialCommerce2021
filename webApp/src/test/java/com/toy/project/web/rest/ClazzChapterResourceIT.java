package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ClazzChapter;
import com.toy.project.repository.ClazzChapterRepository;
import com.toy.project.service.dto.ClazzChapterDTO;
import com.toy.project.service.mapper.ClazzChapterMapper;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

/**
 * Integration tests for the {@link ClazzChapterResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClazzChapterResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_FILE_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final Boolean DEFAULT_ACTIVATED = false;
    private static final Boolean UPDATED_ACTIVATED = true;

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/clazz-chapters";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClazzChapterRepository clazzChapterRepository;

    @Autowired
    private ClazzChapterMapper clazzChapterMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClazzChapterMockMvc;

    private ClazzChapter clazzChapter;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClazzChapter createEntity(EntityManager em) {
        ClazzChapter clazzChapter = new ClazzChapter();
        clazzChapter.setName(DEFAULT_NAME);
        clazzChapter.setDescription(DEFAULT_DESCRIPTION);
        clazzChapter.setFileUrl(DEFAULT_FILE_URL);
        clazzChapter.setSequence(DEFAULT_SEQUENCE);
        clazzChapter.setActivated(DEFAULT_ACTIVATED);
        clazzChapter.setCreatedBy(DEFAULT_CREATED_BY);
        clazzChapter.setCreatedDate(DEFAULT_CREATED_DATE);
        clazzChapter.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        clazzChapter.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return clazzChapter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClazzChapter createUpdatedEntity(EntityManager em) {
        ClazzChapter clazzChapter = new ClazzChapter();
        clazzChapter.setName(UPDATED_NAME);
        clazzChapter.setDescription(UPDATED_DESCRIPTION);
        clazzChapter.setFileUrl(UPDATED_FILE_URL);
        clazzChapter.setSequence(UPDATED_SEQUENCE);
        clazzChapter.setActivated(UPDATED_ACTIVATED);
        clazzChapter.setCreatedBy(UPDATED_CREATED_BY);
        clazzChapter.setCreatedDate(UPDATED_CREATED_DATE);
        clazzChapter.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        clazzChapter.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return clazzChapter;
    }

    @BeforeEach
    public void initTest() {
        clazzChapter = createEntity(em);
    }

    @Test
    @Transactional
    void createClazzChapter() throws Exception {
        int databaseSizeBeforeCreate = clazzChapterRepository.findAll().size();
        // Create the ClazzChapter
        ClazzChapterDTO clazzChapterDTO = clazzChapterMapper.toDto(clazzChapter);
        restClazzChapterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clazzChapterDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ClazzChapter in the database
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeCreate + 1);
        ClazzChapter testClazzChapter = clazzChapterList.get(clazzChapterList.size() - 1);
        assertThat(testClazzChapter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClazzChapter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClazzChapter.getFileUrl()).isEqualTo(DEFAULT_FILE_URL);
        assertThat(testClazzChapter.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testClazzChapter.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testClazzChapter.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClazzChapter.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testClazzChapter.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testClazzChapter.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createClazzChapterWithExistingId() throws Exception {
        // Create the ClazzChapter with an existing ID
        clazzChapter.setId(1L);
        ClazzChapterDTO clazzChapterDTO = clazzChapterMapper.toDto(clazzChapter);

        int databaseSizeBeforeCreate = clazzChapterRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClazzChapterMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clazzChapterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClazzChapter in the database
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClazzChapters() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList
        restClazzChapterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clazzChapter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getClazzChapter() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get the clazzChapter
        restClazzChapterMockMvc
            .perform(get(ENTITY_API_URL_ID, clazzChapter.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clazzChapter.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.fileUrl").value(DEFAULT_FILE_URL))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingClazzChapter() throws Exception {
        // Get the clazzChapter
        restClazzChapterMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClazzChapter() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        int databaseSizeBeforeUpdate = clazzChapterRepository.findAll().size();

        // Update the clazzChapter
        ClazzChapter updatedClazzChapter = clazzChapterRepository.findById(clazzChapter.getId()).get();
        // Disconnect from session so that the updates on updatedClazzChapter are not directly saved in db
        em.detach(updatedClazzChapter);
        updatedClazzChapter.setName(UPDATED_NAME);
        updatedClazzChapter.setDescription(UPDATED_DESCRIPTION);
        updatedClazzChapter.setFileUrl(UPDATED_FILE_URL);
        updatedClazzChapter.setSequence(UPDATED_SEQUENCE);
        updatedClazzChapter.setActivated(UPDATED_ACTIVATED);
        updatedClazzChapter.setCreatedBy(UPDATED_CREATED_BY);
        updatedClazzChapter.setCreatedDate(UPDATED_CREATED_DATE);
        updatedClazzChapter.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedClazzChapter.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ClazzChapterDTO clazzChapterDTO = clazzChapterMapper.toDto(updatedClazzChapter);

        restClazzChapterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clazzChapterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClazzChapter in the database
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeUpdate);
        ClazzChapter testClazzChapter = clazzChapterList.get(clazzChapterList.size() - 1);
        assertThat(testClazzChapter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClazzChapter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClazzChapter.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
        assertThat(testClazzChapter.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testClazzChapter.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazzChapter.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClazzChapter.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClazzChapter.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testClazzChapter.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingClazzChapter() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterRepository.findAll().size();
        clazzChapter.setId(count.incrementAndGet());

        // Create the ClazzChapter
        ClazzChapterDTO clazzChapterDTO = clazzChapterMapper.toDto(clazzChapter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClazzChapterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clazzChapterDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClazzChapter in the database
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClazzChapter() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterRepository.findAll().size();
        clazzChapter.setId(count.incrementAndGet());

        // Create the ClazzChapter
        ClazzChapterDTO clazzChapterDTO = clazzChapterMapper.toDto(clazzChapter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzChapterMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClazzChapter in the database
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClazzChapter() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterRepository.findAll().size();
        clazzChapter.setId(count.incrementAndGet());

        // Create the ClazzChapter
        ClazzChapterDTO clazzChapterDTO = clazzChapterMapper.toDto(clazzChapter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzChapterMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clazzChapterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClazzChapter in the database
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClazzChapterWithPatch() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        int databaseSizeBeforeUpdate = clazzChapterRepository.findAll().size();

        // Update the clazzChapter using partial update
        ClazzChapter partialUpdatedClazzChapter = new ClazzChapter();
        partialUpdatedClazzChapter.setId(clazzChapter.getId());
        partialUpdatedClazzChapter.setName(UPDATED_NAME);
        partialUpdatedClazzChapter.setDescription(UPDATED_DESCRIPTION);
        partialUpdatedClazzChapter.setFileUrl(UPDATED_FILE_URL);
        partialUpdatedClazzChapter.setSequence(UPDATED_SEQUENCE);
        partialUpdatedClazzChapter.setActivated(UPDATED_ACTIVATED);
        partialUpdatedClazzChapter.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedClazzChapter.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedClazzChapter.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedClazzChapter.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restClazzChapterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClazzChapter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClazzChapter))
            )
            .andExpect(status().isOk());

        // Validate the ClazzChapter in the database
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeUpdate);
        ClazzChapter testClazzChapter = clazzChapterList.get(clazzChapterList.size() - 1);
        assertThat(testClazzChapter.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClazzChapter.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testClazzChapter.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
        assertThat(testClazzChapter.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testClazzChapter.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazzChapter.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClazzChapter.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testClazzChapter.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testClazzChapter.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateClazzChapterWithPatch() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        int databaseSizeBeforeUpdate = clazzChapterRepository.findAll().size();

        // Update the clazzChapter using partial update
        ClazzChapter partialUpdatedClazzChapter = new ClazzChapter();
        partialUpdatedClazzChapter.setId(clazzChapter.getId());

        partialUpdatedClazzChapter.setName(UPDATED_NAME);
        partialUpdatedClazzChapter.setDescription(UPDATED_DESCRIPTION);
        partialUpdatedClazzChapter.setFileUrl(UPDATED_FILE_URL);
        partialUpdatedClazzChapter.setSequence(UPDATED_SEQUENCE);
        partialUpdatedClazzChapter.setActivated(UPDATED_ACTIVATED);
        partialUpdatedClazzChapter.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedClazzChapter.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedClazzChapter.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedClazzChapter.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restClazzChapterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClazzChapter.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClazzChapter))
            )
            .andExpect(status().isOk());

        // Validate the ClazzChapter in the database
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeUpdate);
        ClazzChapter testClazzChapter = clazzChapterList.get(clazzChapterList.size() - 1);
        assertThat(testClazzChapter.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClazzChapter.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testClazzChapter.getFileUrl()).isEqualTo(UPDATED_FILE_URL);
        assertThat(testClazzChapter.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testClazzChapter.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazzChapter.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClazzChapter.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClazzChapter.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testClazzChapter.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingClazzChapter() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterRepository.findAll().size();
        clazzChapter.setId(count.incrementAndGet());

        // Create the ClazzChapter
        ClazzChapterDTO clazzChapterDTO = clazzChapterMapper.toDto(clazzChapter);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClazzChapterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clazzChapterDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClazzChapter in the database
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClazzChapter() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterRepository.findAll().size();
        clazzChapter.setId(count.incrementAndGet());

        // Create the ClazzChapter
        ClazzChapterDTO clazzChapterDTO = clazzChapterMapper.toDto(clazzChapter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzChapterMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClazzChapter in the database
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClazzChapter() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterRepository.findAll().size();
        clazzChapter.setId(count.incrementAndGet());

        // Create the ClazzChapter
        ClazzChapterDTO clazzChapterDTO = clazzChapterMapper.toDto(clazzChapter);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzChapterMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClazzChapter in the database
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClazzChapter() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        int databaseSizeBeforeDelete = clazzChapterRepository.findAll().size();

        // Delete the clazzChapter
        restClazzChapterMockMvc
            .perform(delete(ENTITY_API_URL_ID, clazzChapter.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClazzChapter> clazzChapterList = clazzChapterRepository.findAll();
        assertThat(clazzChapterList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
