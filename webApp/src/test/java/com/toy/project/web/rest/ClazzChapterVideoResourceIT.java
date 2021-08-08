package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ClazzChapterVideo;
import com.toy.project.repository.ClazzChapterVideoRepository;
import com.toy.project.service.dto.ClazzChapterVideoDTO;
import com.toy.project.service.mapper.ClazzChapterVideoMapper;
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

/**
 * Integration tests for the {@link ClazzChapterVideoResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClazzChapterVideoResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_THUMB_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMB_FILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ORIGINAL_LINK_URL = "AAAAAAAAAA";
    private static final String UPDATED_ORIGINAL_LINK_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_TIME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/clazz-chapter-videos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClazzChapterVideoRepository clazzChapterVideoRepository;

    @Autowired
    private ClazzChapterVideoMapper clazzChapterVideoMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClazzChapterVideoMockMvc;

    private ClazzChapterVideo clazzChapterVideo;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClazzChapterVideo createEntity(EntityManager em) {
        ClazzChapterVideo clazzChapterVideo = new ClazzChapterVideo();
        clazzChapterVideo.setName(DEFAULT_NAME);
        clazzChapterVideo.setThumbFileUrl(DEFAULT_THUMB_FILE_URL);
        clazzChapterVideo.setOriginalLinkUrl(DEFAULT_ORIGINAL_LINK_URL);
        clazzChapterVideo.setTime(DEFAULT_TIME);
        clazzChapterVideo.setSequence(DEFAULT_SEQUENCE);
        clazzChapterVideo.setActivated(DEFAULT_ACTIVATED);
        clazzChapterVideo.setCreatedBy(DEFAULT_CREATED_BY);
        clazzChapterVideo.setCreatedDate(DEFAULT_CREATED_DATE);
        clazzChapterVideo.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        clazzChapterVideo.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return clazzChapterVideo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClazzChapterVideo createUpdatedEntity(EntityManager em) {
        ClazzChapterVideo clazzChapterVideo = new ClazzChapterVideo();
        clazzChapterVideo.setName(UPDATED_NAME);
        clazzChapterVideo.setThumbFileUrl(UPDATED_THUMB_FILE_URL);
        clazzChapterVideo.setOriginalLinkUrl(UPDATED_ORIGINAL_LINK_URL);
        clazzChapterVideo.setTime(UPDATED_TIME);
        clazzChapterVideo.setSequence(UPDATED_SEQUENCE);
        clazzChapterVideo.setActivated(UPDATED_ACTIVATED);
        clazzChapterVideo.setCreatedBy(UPDATED_CREATED_BY);
        clazzChapterVideo.setCreatedDate(UPDATED_CREATED_DATE);
        clazzChapterVideo.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        clazzChapterVideo.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return clazzChapterVideo;
    }

    @BeforeEach
    public void initTest() {
        clazzChapterVideo = createEntity(em);
    }

    @Test
    @Transactional
    void createClazzChapterVideo() throws Exception {
        int databaseSizeBeforeCreate = clazzChapterVideoRepository.findAll().size();
        // Create the ClazzChapterVideo
        ClazzChapterVideoDTO clazzChapterVideoDTO = clazzChapterVideoMapper.toDto(clazzChapterVideo);
        restClazzChapterVideoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterVideoDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ClazzChapterVideo in the database
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeCreate + 1);
        ClazzChapterVideo testClazzChapterVideo = clazzChapterVideoList.get(clazzChapterVideoList.size() - 1);
        assertThat(testClazzChapterVideo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClazzChapterVideo.getThumbFileUrl()).isEqualTo(DEFAULT_THUMB_FILE_URL);
        assertThat(testClazzChapterVideo.getOriginalLinkUrl()).isEqualTo(DEFAULT_ORIGINAL_LINK_URL);
        assertThat(testClazzChapterVideo.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testClazzChapterVideo.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testClazzChapterVideo.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testClazzChapterVideo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClazzChapterVideo.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testClazzChapterVideo.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testClazzChapterVideo.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createClazzChapterVideoWithExistingId() throws Exception {
        // Create the ClazzChapterVideo with an existing ID
        clazzChapterVideo.setId(1L);
        ClazzChapterVideoDTO clazzChapterVideoDTO = clazzChapterVideoMapper.toDto(clazzChapterVideo);

        int databaseSizeBeforeCreate = clazzChapterVideoRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClazzChapterVideoMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClazzChapterVideo in the database
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideos() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList
        restClazzChapterVideoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clazzChapterVideo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].thumbFileUrl").value(hasItem(DEFAULT_THUMB_FILE_URL)))
            .andExpect(jsonPath("$.[*].originalLinkUrl").value(hasItem(DEFAULT_ORIGINAL_LINK_URL)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getClazzChapterVideo() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get the clazzChapterVideo
        restClazzChapterVideoMockMvc
            .perform(get(ENTITY_API_URL_ID, clazzChapterVideo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clazzChapterVideo.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.thumbFileUrl").value(DEFAULT_THUMB_FILE_URL))
            .andExpect(jsonPath("$.originalLinkUrl").value(DEFAULT_ORIGINAL_LINK_URL))
            .andExpect(jsonPath("$.time").value(DEFAULT_TIME))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingClazzChapterVideo() throws Exception {
        // Get the clazzChapterVideo
        restClazzChapterVideoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClazzChapterVideo() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        int databaseSizeBeforeUpdate = clazzChapterVideoRepository.findAll().size();

        // Update the clazzChapterVideo
        ClazzChapterVideo updatedClazzChapterVideo = clazzChapterVideoRepository.findById(clazzChapterVideo.getId()).get();
        // Disconnect from session so that the updates on updatedClazzChapterVideo are not directly saved in db
        em.detach(updatedClazzChapterVideo);
        updatedClazzChapterVideo.setName(UPDATED_NAME);
        updatedClazzChapterVideo.setThumbFileUrl(UPDATED_THUMB_FILE_URL);
        updatedClazzChapterVideo.setOriginalLinkUrl(UPDATED_ORIGINAL_LINK_URL);
        updatedClazzChapterVideo.setTime(UPDATED_TIME);
        updatedClazzChapterVideo.setSequence(UPDATED_SEQUENCE);
        updatedClazzChapterVideo.setActivated(UPDATED_ACTIVATED);
        updatedClazzChapterVideo.setCreatedBy(UPDATED_CREATED_BY);
        updatedClazzChapterVideo.setCreatedDate(UPDATED_CREATED_DATE);
        updatedClazzChapterVideo.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedClazzChapterVideo.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        ClazzChapterVideoDTO clazzChapterVideoDTO = clazzChapterVideoMapper.toDto(updatedClazzChapterVideo);

        restClazzChapterVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clazzChapterVideoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterVideoDTO))
            )
            .andExpect(status().isOk());

        // Validate the ClazzChapterVideo in the database
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeUpdate);
        ClazzChapterVideo testClazzChapterVideo = clazzChapterVideoList.get(clazzChapterVideoList.size() - 1);
        assertThat(testClazzChapterVideo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClazzChapterVideo.getThumbFileUrl()).isEqualTo(UPDATED_THUMB_FILE_URL);
        assertThat(testClazzChapterVideo.getOriginalLinkUrl()).isEqualTo(UPDATED_ORIGINAL_LINK_URL);
        assertThat(testClazzChapterVideo.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testClazzChapterVideo.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testClazzChapterVideo.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazzChapterVideo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClazzChapterVideo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClazzChapterVideo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testClazzChapterVideo.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingClazzChapterVideo() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterVideoRepository.findAll().size();
        clazzChapterVideo.setId(count.incrementAndGet());

        // Create the ClazzChapterVideo
        ClazzChapterVideoDTO clazzChapterVideoDTO = clazzChapterVideoMapper.toDto(clazzChapterVideo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClazzChapterVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clazzChapterVideoDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClazzChapterVideo in the database
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClazzChapterVideo() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterVideoRepository.findAll().size();
        clazzChapterVideo.setId(count.incrementAndGet());

        // Create the ClazzChapterVideo
        ClazzChapterVideoDTO clazzChapterVideoDTO = clazzChapterVideoMapper.toDto(clazzChapterVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzChapterVideoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClazzChapterVideo in the database
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClazzChapterVideo() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterVideoRepository.findAll().size();
        clazzChapterVideo.setId(count.incrementAndGet());

        // Create the ClazzChapterVideo
        ClazzChapterVideoDTO clazzChapterVideoDTO = clazzChapterVideoMapper.toDto(clazzChapterVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzChapterVideoMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clazzChapterVideoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClazzChapterVideo in the database
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClazzChapterVideoWithPatch() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        int databaseSizeBeforeUpdate = clazzChapterVideoRepository.findAll().size();

        // Update the clazzChapterVideo using partial update
        ClazzChapterVideo partialUpdatedClazzChapterVideo = new ClazzChapterVideo();
        partialUpdatedClazzChapterVideo.setId(clazzChapterVideo.getId());

        partialUpdatedClazzChapterVideo.setName(UPDATED_NAME);
        partialUpdatedClazzChapterVideo.setThumbFileUrl(UPDATED_THUMB_FILE_URL);
        partialUpdatedClazzChapterVideo.setOriginalLinkUrl(UPDATED_ORIGINAL_LINK_URL);
        partialUpdatedClazzChapterVideo.setTime(UPDATED_TIME);
        partialUpdatedClazzChapterVideo.setSequence(UPDATED_SEQUENCE);
        partialUpdatedClazzChapterVideo.setActivated(UPDATED_ACTIVATED);
        partialUpdatedClazzChapterVideo.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedClazzChapterVideo.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedClazzChapterVideo.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedClazzChapterVideo.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restClazzChapterVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClazzChapterVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClazzChapterVideo))
            )
            .andExpect(status().isOk());

        // Validate the ClazzChapterVideo in the database
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeUpdate);
        ClazzChapterVideo testClazzChapterVideo = clazzChapterVideoList.get(clazzChapterVideoList.size() - 1);
        assertThat(testClazzChapterVideo.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClazzChapterVideo.getThumbFileUrl()).isEqualTo(DEFAULT_THUMB_FILE_URL);
        assertThat(testClazzChapterVideo.getOriginalLinkUrl()).isEqualTo(UPDATED_ORIGINAL_LINK_URL);
        assertThat(testClazzChapterVideo.getTime()).isEqualTo(DEFAULT_TIME);
        assertThat(testClazzChapterVideo.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testClazzChapterVideo.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazzChapterVideo.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClazzChapterVideo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClazzChapterVideo.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testClazzChapterVideo.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateClazzChapterVideoWithPatch() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        int databaseSizeBeforeUpdate = clazzChapterVideoRepository.findAll().size();

        // Update the clazzChapterVideo using partial update
        ClazzChapterVideo partialUpdatedClazzChapterVideo = new ClazzChapterVideo();
        partialUpdatedClazzChapterVideo.setId(clazzChapterVideo.getId());

        partialUpdatedClazzChapterVideo.setName(UPDATED_NAME);
        partialUpdatedClazzChapterVideo.setThumbFileUrl(UPDATED_THUMB_FILE_URL);
        partialUpdatedClazzChapterVideo.setOriginalLinkUrl(UPDATED_ORIGINAL_LINK_URL);
        partialUpdatedClazzChapterVideo.setTime(UPDATED_TIME);
        partialUpdatedClazzChapterVideo.setSequence(UPDATED_SEQUENCE);
        partialUpdatedClazzChapterVideo.setActivated(UPDATED_ACTIVATED);
        partialUpdatedClazzChapterVideo.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedClazzChapterVideo.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedClazzChapterVideo.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedClazzChapterVideo.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restClazzChapterVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClazzChapterVideo.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClazzChapterVideo))
            )
            .andExpect(status().isOk());

        // Validate the ClazzChapterVideo in the database
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeUpdate);
        ClazzChapterVideo testClazzChapterVideo = clazzChapterVideoList.get(clazzChapterVideoList.size() - 1);
        assertThat(testClazzChapterVideo.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClazzChapterVideo.getThumbFileUrl()).isEqualTo(UPDATED_THUMB_FILE_URL);
        assertThat(testClazzChapterVideo.getOriginalLinkUrl()).isEqualTo(UPDATED_ORIGINAL_LINK_URL);
        assertThat(testClazzChapterVideo.getTime()).isEqualTo(UPDATED_TIME);
        assertThat(testClazzChapterVideo.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testClazzChapterVideo.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazzChapterVideo.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClazzChapterVideo.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClazzChapterVideo.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testClazzChapterVideo.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingClazzChapterVideo() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterVideoRepository.findAll().size();
        clazzChapterVideo.setId(count.incrementAndGet());

        // Create the ClazzChapterVideo
        ClazzChapterVideoDTO clazzChapterVideoDTO = clazzChapterVideoMapper.toDto(clazzChapterVideo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClazzChapterVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clazzChapterVideoDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClazzChapterVideo in the database
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClazzChapterVideo() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterVideoRepository.findAll().size();
        clazzChapterVideo.setId(count.incrementAndGet());

        // Create the ClazzChapterVideo
        ClazzChapterVideoDTO clazzChapterVideoDTO = clazzChapterVideoMapper.toDto(clazzChapterVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzChapterVideoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterVideoDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ClazzChapterVideo in the database
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClazzChapterVideo() throws Exception {
        int databaseSizeBeforeUpdate = clazzChapterVideoRepository.findAll().size();
        clazzChapterVideo.setId(count.incrementAndGet());

        // Create the ClazzChapterVideo
        ClazzChapterVideoDTO clazzChapterVideoDTO = clazzChapterVideoMapper.toDto(clazzChapterVideo);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzChapterVideoMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clazzChapterVideoDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ClazzChapterVideo in the database
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClazzChapterVideo() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        int databaseSizeBeforeDelete = clazzChapterVideoRepository.findAll().size();

        // Delete the clazzChapterVideo
        restClazzChapterVideoMockMvc
            .perform(delete(ENTITY_API_URL_ID, clazzChapterVideo.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClazzChapterVideo> clazzChapterVideoList = clazzChapterVideoRepository.findAll();
        assertThat(clazzChapterVideoList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
