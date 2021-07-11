package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ClazzChapter;
import com.toy.project.domain.ClazzChapterVideo;
import com.toy.project.repository.ClazzChapterVideoRepository;
import com.toy.project.service.criteria.ClazzChapterVideoCriteria;
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

    private static final Integer DEFAULT_ORDER = 1;
    private static final Integer UPDATED_ORDER = 2;
    private static final Integer SMALLER_ORDER = 1 - 1;

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
        ClazzChapterVideo clazzChapterVideo = new ClazzChapterVideo()
            .name(DEFAULT_NAME)
            .thumbFileUrl(DEFAULT_THUMB_FILE_URL)
            .originalLinkUrl(DEFAULT_ORIGINAL_LINK_URL)
            .time(DEFAULT_TIME)
            .order(DEFAULT_ORDER)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return clazzChapterVideo;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClazzChapterVideo createUpdatedEntity(EntityManager em) {
        ClazzChapterVideo clazzChapterVideo = new ClazzChapterVideo()
            .name(UPDATED_NAME)
            .thumbFileUrl(UPDATED_THUMB_FILE_URL)
            .originalLinkUrl(UPDATED_ORIGINAL_LINK_URL)
            .time(UPDATED_TIME)
            .order(UPDATED_ORDER)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
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
        assertThat(testClazzChapterVideo.getOrder()).isEqualTo(DEFAULT_ORDER);
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
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
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
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getClazzChapterVideosByIdFiltering() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        Long id = clazzChapterVideo.getId();

        defaultClazzChapterVideoShouldBeFound("id.equals=" + id);
        defaultClazzChapterVideoShouldNotBeFound("id.notEquals=" + id);

        defaultClazzChapterVideoShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClazzChapterVideoShouldNotBeFound("id.greaterThan=" + id);

        defaultClazzChapterVideoShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClazzChapterVideoShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where name equals to DEFAULT_NAME
        defaultClazzChapterVideoShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the clazzChapterVideoList where name equals to UPDATED_NAME
        defaultClazzChapterVideoShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where name not equals to DEFAULT_NAME
        defaultClazzChapterVideoShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the clazzChapterVideoList where name not equals to UPDATED_NAME
        defaultClazzChapterVideoShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByNameIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where name in DEFAULT_NAME or UPDATED_NAME
        defaultClazzChapterVideoShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the clazzChapterVideoList where name equals to UPDATED_NAME
        defaultClazzChapterVideoShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where name is not null
        defaultClazzChapterVideoShouldBeFound("name.specified=true");

        // Get all the clazzChapterVideoList where name is null
        defaultClazzChapterVideoShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByNameContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where name contains DEFAULT_NAME
        defaultClazzChapterVideoShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the clazzChapterVideoList where name contains UPDATED_NAME
        defaultClazzChapterVideoShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByNameNotContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where name does not contain DEFAULT_NAME
        defaultClazzChapterVideoShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the clazzChapterVideoList where name does not contain UPDATED_NAME
        defaultClazzChapterVideoShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByThumbFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where thumbFileUrl equals to DEFAULT_THUMB_FILE_URL
        defaultClazzChapterVideoShouldBeFound("thumbFileUrl.equals=" + DEFAULT_THUMB_FILE_URL);

        // Get all the clazzChapterVideoList where thumbFileUrl equals to UPDATED_THUMB_FILE_URL
        defaultClazzChapterVideoShouldNotBeFound("thumbFileUrl.equals=" + UPDATED_THUMB_FILE_URL);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByThumbFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where thumbFileUrl not equals to DEFAULT_THUMB_FILE_URL
        defaultClazzChapterVideoShouldNotBeFound("thumbFileUrl.notEquals=" + DEFAULT_THUMB_FILE_URL);

        // Get all the clazzChapterVideoList where thumbFileUrl not equals to UPDATED_THUMB_FILE_URL
        defaultClazzChapterVideoShouldBeFound("thumbFileUrl.notEquals=" + UPDATED_THUMB_FILE_URL);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByThumbFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where thumbFileUrl in DEFAULT_THUMB_FILE_URL or UPDATED_THUMB_FILE_URL
        defaultClazzChapterVideoShouldBeFound("thumbFileUrl.in=" + DEFAULT_THUMB_FILE_URL + "," + UPDATED_THUMB_FILE_URL);

        // Get all the clazzChapterVideoList where thumbFileUrl equals to UPDATED_THUMB_FILE_URL
        defaultClazzChapterVideoShouldNotBeFound("thumbFileUrl.in=" + UPDATED_THUMB_FILE_URL);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByThumbFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where thumbFileUrl is not null
        defaultClazzChapterVideoShouldBeFound("thumbFileUrl.specified=true");

        // Get all the clazzChapterVideoList where thumbFileUrl is null
        defaultClazzChapterVideoShouldNotBeFound("thumbFileUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByThumbFileUrlContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where thumbFileUrl contains DEFAULT_THUMB_FILE_URL
        defaultClazzChapterVideoShouldBeFound("thumbFileUrl.contains=" + DEFAULT_THUMB_FILE_URL);

        // Get all the clazzChapterVideoList where thumbFileUrl contains UPDATED_THUMB_FILE_URL
        defaultClazzChapterVideoShouldNotBeFound("thumbFileUrl.contains=" + UPDATED_THUMB_FILE_URL);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByThumbFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where thumbFileUrl does not contain DEFAULT_THUMB_FILE_URL
        defaultClazzChapterVideoShouldNotBeFound("thumbFileUrl.doesNotContain=" + DEFAULT_THUMB_FILE_URL);

        // Get all the clazzChapterVideoList where thumbFileUrl does not contain UPDATED_THUMB_FILE_URL
        defaultClazzChapterVideoShouldBeFound("thumbFileUrl.doesNotContain=" + UPDATED_THUMB_FILE_URL);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOriginalLinkUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where originalLinkUrl equals to DEFAULT_ORIGINAL_LINK_URL
        defaultClazzChapterVideoShouldBeFound("originalLinkUrl.equals=" + DEFAULT_ORIGINAL_LINK_URL);

        // Get all the clazzChapterVideoList where originalLinkUrl equals to UPDATED_ORIGINAL_LINK_URL
        defaultClazzChapterVideoShouldNotBeFound("originalLinkUrl.equals=" + UPDATED_ORIGINAL_LINK_URL);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOriginalLinkUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where originalLinkUrl not equals to DEFAULT_ORIGINAL_LINK_URL
        defaultClazzChapterVideoShouldNotBeFound("originalLinkUrl.notEquals=" + DEFAULT_ORIGINAL_LINK_URL);

        // Get all the clazzChapterVideoList where originalLinkUrl not equals to UPDATED_ORIGINAL_LINK_URL
        defaultClazzChapterVideoShouldBeFound("originalLinkUrl.notEquals=" + UPDATED_ORIGINAL_LINK_URL);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOriginalLinkUrlIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where originalLinkUrl in DEFAULT_ORIGINAL_LINK_URL or UPDATED_ORIGINAL_LINK_URL
        defaultClazzChapterVideoShouldBeFound("originalLinkUrl.in=" + DEFAULT_ORIGINAL_LINK_URL + "," + UPDATED_ORIGINAL_LINK_URL);

        // Get all the clazzChapterVideoList where originalLinkUrl equals to UPDATED_ORIGINAL_LINK_URL
        defaultClazzChapterVideoShouldNotBeFound("originalLinkUrl.in=" + UPDATED_ORIGINAL_LINK_URL);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOriginalLinkUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where originalLinkUrl is not null
        defaultClazzChapterVideoShouldBeFound("originalLinkUrl.specified=true");

        // Get all the clazzChapterVideoList where originalLinkUrl is null
        defaultClazzChapterVideoShouldNotBeFound("originalLinkUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOriginalLinkUrlContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where originalLinkUrl contains DEFAULT_ORIGINAL_LINK_URL
        defaultClazzChapterVideoShouldBeFound("originalLinkUrl.contains=" + DEFAULT_ORIGINAL_LINK_URL);

        // Get all the clazzChapterVideoList where originalLinkUrl contains UPDATED_ORIGINAL_LINK_URL
        defaultClazzChapterVideoShouldNotBeFound("originalLinkUrl.contains=" + UPDATED_ORIGINAL_LINK_URL);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOriginalLinkUrlNotContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where originalLinkUrl does not contain DEFAULT_ORIGINAL_LINK_URL
        defaultClazzChapterVideoShouldNotBeFound("originalLinkUrl.doesNotContain=" + DEFAULT_ORIGINAL_LINK_URL);

        // Get all the clazzChapterVideoList where originalLinkUrl does not contain UPDATED_ORIGINAL_LINK_URL
        defaultClazzChapterVideoShouldBeFound("originalLinkUrl.doesNotContain=" + UPDATED_ORIGINAL_LINK_URL);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByTimeIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where time equals to DEFAULT_TIME
        defaultClazzChapterVideoShouldBeFound("time.equals=" + DEFAULT_TIME);

        // Get all the clazzChapterVideoList where time equals to UPDATED_TIME
        defaultClazzChapterVideoShouldNotBeFound("time.equals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByTimeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where time not equals to DEFAULT_TIME
        defaultClazzChapterVideoShouldNotBeFound("time.notEquals=" + DEFAULT_TIME);

        // Get all the clazzChapterVideoList where time not equals to UPDATED_TIME
        defaultClazzChapterVideoShouldBeFound("time.notEquals=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByTimeIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where time in DEFAULT_TIME or UPDATED_TIME
        defaultClazzChapterVideoShouldBeFound("time.in=" + DEFAULT_TIME + "," + UPDATED_TIME);

        // Get all the clazzChapterVideoList where time equals to UPDATED_TIME
        defaultClazzChapterVideoShouldNotBeFound("time.in=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByTimeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where time is not null
        defaultClazzChapterVideoShouldBeFound("time.specified=true");

        // Get all the clazzChapterVideoList where time is null
        defaultClazzChapterVideoShouldNotBeFound("time.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByTimeContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where time contains DEFAULT_TIME
        defaultClazzChapterVideoShouldBeFound("time.contains=" + DEFAULT_TIME);

        // Get all the clazzChapterVideoList where time contains UPDATED_TIME
        defaultClazzChapterVideoShouldNotBeFound("time.contains=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByTimeNotContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where time does not contain DEFAULT_TIME
        defaultClazzChapterVideoShouldNotBeFound("time.doesNotContain=" + DEFAULT_TIME);

        // Get all the clazzChapterVideoList where time does not contain UPDATED_TIME
        defaultClazzChapterVideoShouldBeFound("time.doesNotContain=" + UPDATED_TIME);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where order equals to DEFAULT_ORDER
        defaultClazzChapterVideoShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the clazzChapterVideoList where order equals to UPDATED_ORDER
        defaultClazzChapterVideoShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where order not equals to DEFAULT_ORDER
        defaultClazzChapterVideoShouldNotBeFound("order.notEquals=" + DEFAULT_ORDER);

        // Get all the clazzChapterVideoList where order not equals to UPDATED_ORDER
        defaultClazzChapterVideoShouldBeFound("order.notEquals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultClazzChapterVideoShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the clazzChapterVideoList where order equals to UPDATED_ORDER
        defaultClazzChapterVideoShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where order is not null
        defaultClazzChapterVideoShouldBeFound("order.specified=true");

        // Get all the clazzChapterVideoList where order is null
        defaultClazzChapterVideoShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where order is greater than or equal to DEFAULT_ORDER
        defaultClazzChapterVideoShouldBeFound("order.greaterThanOrEqual=" + DEFAULT_ORDER);

        // Get all the clazzChapterVideoList where order is greater than or equal to UPDATED_ORDER
        defaultClazzChapterVideoShouldNotBeFound("order.greaterThanOrEqual=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where order is less than or equal to DEFAULT_ORDER
        defaultClazzChapterVideoShouldBeFound("order.lessThanOrEqual=" + DEFAULT_ORDER);

        // Get all the clazzChapterVideoList where order is less than or equal to SMALLER_ORDER
        defaultClazzChapterVideoShouldNotBeFound("order.lessThanOrEqual=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where order is less than DEFAULT_ORDER
        defaultClazzChapterVideoShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the clazzChapterVideoList where order is less than UPDATED_ORDER
        defaultClazzChapterVideoShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where order is greater than DEFAULT_ORDER
        defaultClazzChapterVideoShouldNotBeFound("order.greaterThan=" + DEFAULT_ORDER);

        // Get all the clazzChapterVideoList where order is greater than SMALLER_ORDER
        defaultClazzChapterVideoShouldBeFound("order.greaterThan=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where activated equals to DEFAULT_ACTIVATED
        defaultClazzChapterVideoShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the clazzChapterVideoList where activated equals to UPDATED_ACTIVATED
        defaultClazzChapterVideoShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where activated not equals to DEFAULT_ACTIVATED
        defaultClazzChapterVideoShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the clazzChapterVideoList where activated not equals to UPDATED_ACTIVATED
        defaultClazzChapterVideoShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultClazzChapterVideoShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the clazzChapterVideoList where activated equals to UPDATED_ACTIVATED
        defaultClazzChapterVideoShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where activated is not null
        defaultClazzChapterVideoShouldBeFound("activated.specified=true");

        // Get all the clazzChapterVideoList where activated is null
        defaultClazzChapterVideoShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where createdBy equals to DEFAULT_CREATED_BY
        defaultClazzChapterVideoShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the clazzChapterVideoList where createdBy equals to UPDATED_CREATED_BY
        defaultClazzChapterVideoShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where createdBy not equals to DEFAULT_CREATED_BY
        defaultClazzChapterVideoShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the clazzChapterVideoList where createdBy not equals to UPDATED_CREATED_BY
        defaultClazzChapterVideoShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultClazzChapterVideoShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the clazzChapterVideoList where createdBy equals to UPDATED_CREATED_BY
        defaultClazzChapterVideoShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where createdBy is not null
        defaultClazzChapterVideoShouldBeFound("createdBy.specified=true");

        // Get all the clazzChapterVideoList where createdBy is null
        defaultClazzChapterVideoShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where createdBy contains DEFAULT_CREATED_BY
        defaultClazzChapterVideoShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the clazzChapterVideoList where createdBy contains UPDATED_CREATED_BY
        defaultClazzChapterVideoShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where createdBy does not contain DEFAULT_CREATED_BY
        defaultClazzChapterVideoShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the clazzChapterVideoList where createdBy does not contain UPDATED_CREATED_BY
        defaultClazzChapterVideoShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where createdDate equals to DEFAULT_CREATED_DATE
        defaultClazzChapterVideoShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the clazzChapterVideoList where createdDate equals to UPDATED_CREATED_DATE
        defaultClazzChapterVideoShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultClazzChapterVideoShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the clazzChapterVideoList where createdDate not equals to UPDATED_CREATED_DATE
        defaultClazzChapterVideoShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultClazzChapterVideoShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the clazzChapterVideoList where createdDate equals to UPDATED_CREATED_DATE
        defaultClazzChapterVideoShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where createdDate is not null
        defaultClazzChapterVideoShouldBeFound("createdDate.specified=true");

        // Get all the clazzChapterVideoList where createdDate is null
        defaultClazzChapterVideoShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultClazzChapterVideoShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzChapterVideoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterVideoShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultClazzChapterVideoShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzChapterVideoList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterVideoShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterVideoShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the clazzChapterVideoList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterVideoShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where lastModifiedBy is not null
        defaultClazzChapterVideoShouldBeFound("lastModifiedBy.specified=true");

        // Get all the clazzChapterVideoList where lastModifiedBy is null
        defaultClazzChapterVideoShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultClazzChapterVideoShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzChapterVideoList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterVideoShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultClazzChapterVideoShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzChapterVideoList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterVideoShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultClazzChapterVideoShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the clazzChapterVideoList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultClazzChapterVideoShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultClazzChapterVideoShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the clazzChapterVideoList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultClazzChapterVideoShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultClazzChapterVideoShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the clazzChapterVideoList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultClazzChapterVideoShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);

        // Get all the clazzChapterVideoList where lastModifiedDate is not null
        defaultClazzChapterVideoShouldBeFound("lastModifiedDate.specified=true");

        // Get all the clazzChapterVideoList where lastModifiedDate is null
        defaultClazzChapterVideoShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChapterVideosByClazzChapterIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);
        ClazzChapter clazzChapter = ClazzChapterResourceIT.createEntity(em);
        em.persist(clazzChapter);
        em.flush();
        clazzChapterVideo.setClazzChapter(clazzChapter);
        clazzChapterVideoRepository.saveAndFlush(clazzChapterVideo);
        Long clazzChapterId = clazzChapter.getId();

        // Get all the clazzChapterVideoList where clazzChapter equals to clazzChapterId
        defaultClazzChapterVideoShouldBeFound("clazzChapterId.equals=" + clazzChapterId);

        // Get all the clazzChapterVideoList where clazzChapter equals to (clazzChapterId + 1)
        defaultClazzChapterVideoShouldNotBeFound("clazzChapterId.equals=" + (clazzChapterId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClazzChapterVideoShouldBeFound(String filter) throws Exception {
        restClazzChapterVideoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clazzChapterVideo.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].thumbFileUrl").value(hasItem(DEFAULT_THUMB_FILE_URL)))
            .andExpect(jsonPath("$.[*].originalLinkUrl").value(hasItem(DEFAULT_ORIGINAL_LINK_URL)))
            .andExpect(jsonPath("$.[*].time").value(hasItem(DEFAULT_TIME)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restClazzChapterVideoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClazzChapterVideoShouldNotBeFound(String filter) throws Exception {
        restClazzChapterVideoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClazzChapterVideoMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        updatedClazzChapterVideo
            .name(UPDATED_NAME)
            .thumbFileUrl(UPDATED_THUMB_FILE_URL)
            .originalLinkUrl(UPDATED_ORIGINAL_LINK_URL)
            .time(UPDATED_TIME)
            .order(UPDATED_ORDER)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
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
        assertThat(testClazzChapterVideo.getOrder()).isEqualTo(UPDATED_ORDER);
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

        partialUpdatedClazzChapterVideo
            .originalLinkUrl(UPDATED_ORIGINAL_LINK_URL)
            .order(UPDATED_ORDER)
            .activated(UPDATED_ACTIVATED)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

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
        assertThat(testClazzChapterVideo.getOrder()).isEqualTo(UPDATED_ORDER);
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

        partialUpdatedClazzChapterVideo
            .name(UPDATED_NAME)
            .thumbFileUrl(UPDATED_THUMB_FILE_URL)
            .originalLinkUrl(UPDATED_ORIGINAL_LINK_URL)
            .time(UPDATED_TIME)
            .order(UPDATED_ORDER)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

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
        assertThat(testClazzChapterVideo.getOrder()).isEqualTo(UPDATED_ORDER);
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
