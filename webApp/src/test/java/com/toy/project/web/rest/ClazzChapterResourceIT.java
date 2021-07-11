package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Clazz;
import com.toy.project.domain.ClazzChapter;
import com.toy.project.domain.ClazzChapterVideo;
import com.toy.project.repository.ClazzChapterRepository;
import com.toy.project.service.criteria.ClazzChapterCriteria;
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
        ClazzChapter clazzChapter = new ClazzChapter()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION)
            .fileUrl(DEFAULT_FILE_URL)
            .order(DEFAULT_ORDER)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return clazzChapter;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClazzChapter createUpdatedEntity(EntityManager em) {
        ClazzChapter clazzChapter = new ClazzChapter()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .fileUrl(UPDATED_FILE_URL)
            .order(UPDATED_ORDER)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
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
        assertThat(testClazzChapter.getOrder()).isEqualTo(DEFAULT_ORDER);
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
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
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
            .andExpect(jsonPath("$.order").value(DEFAULT_ORDER))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getClazzChaptersByIdFiltering() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        Long id = clazzChapter.getId();

        defaultClazzChapterShouldBeFound("id.equals=" + id);
        defaultClazzChapterShouldNotBeFound("id.notEquals=" + id);

        defaultClazzChapterShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClazzChapterShouldNotBeFound("id.greaterThan=" + id);

        defaultClazzChapterShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClazzChapterShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where name equals to DEFAULT_NAME
        defaultClazzChapterShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the clazzChapterList where name equals to UPDATED_NAME
        defaultClazzChapterShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where name not equals to DEFAULT_NAME
        defaultClazzChapterShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the clazzChapterList where name not equals to UPDATED_NAME
        defaultClazzChapterShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByNameIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where name in DEFAULT_NAME or UPDATED_NAME
        defaultClazzChapterShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the clazzChapterList where name equals to UPDATED_NAME
        defaultClazzChapterShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where name is not null
        defaultClazzChapterShouldBeFound("name.specified=true");

        // Get all the clazzChapterList where name is null
        defaultClazzChapterShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChaptersByNameContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where name contains DEFAULT_NAME
        defaultClazzChapterShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the clazzChapterList where name contains UPDATED_NAME
        defaultClazzChapterShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByNameNotContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where name does not contain DEFAULT_NAME
        defaultClazzChapterShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the clazzChapterList where name does not contain UPDATED_NAME
        defaultClazzChapterShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where fileUrl equals to DEFAULT_FILE_URL
        defaultClazzChapterShouldBeFound("fileUrl.equals=" + DEFAULT_FILE_URL);

        // Get all the clazzChapterList where fileUrl equals to UPDATED_FILE_URL
        defaultClazzChapterShouldNotBeFound("fileUrl.equals=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where fileUrl not equals to DEFAULT_FILE_URL
        defaultClazzChapterShouldNotBeFound("fileUrl.notEquals=" + DEFAULT_FILE_URL);

        // Get all the clazzChapterList where fileUrl not equals to UPDATED_FILE_URL
        defaultClazzChapterShouldBeFound("fileUrl.notEquals=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where fileUrl in DEFAULT_FILE_URL or UPDATED_FILE_URL
        defaultClazzChapterShouldBeFound("fileUrl.in=" + DEFAULT_FILE_URL + "," + UPDATED_FILE_URL);

        // Get all the clazzChapterList where fileUrl equals to UPDATED_FILE_URL
        defaultClazzChapterShouldNotBeFound("fileUrl.in=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where fileUrl is not null
        defaultClazzChapterShouldBeFound("fileUrl.specified=true");

        // Get all the clazzChapterList where fileUrl is null
        defaultClazzChapterShouldNotBeFound("fileUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChaptersByFileUrlContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where fileUrl contains DEFAULT_FILE_URL
        defaultClazzChapterShouldBeFound("fileUrl.contains=" + DEFAULT_FILE_URL);

        // Get all the clazzChapterList where fileUrl contains UPDATED_FILE_URL
        defaultClazzChapterShouldNotBeFound("fileUrl.contains=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where fileUrl does not contain DEFAULT_FILE_URL
        defaultClazzChapterShouldNotBeFound("fileUrl.doesNotContain=" + DEFAULT_FILE_URL);

        // Get all the clazzChapterList where fileUrl does not contain UPDATED_FILE_URL
        defaultClazzChapterShouldBeFound("fileUrl.doesNotContain=" + UPDATED_FILE_URL);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where order equals to DEFAULT_ORDER
        defaultClazzChapterShouldBeFound("order.equals=" + DEFAULT_ORDER);

        // Get all the clazzChapterList where order equals to UPDATED_ORDER
        defaultClazzChapterShouldNotBeFound("order.equals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where order not equals to DEFAULT_ORDER
        defaultClazzChapterShouldNotBeFound("order.notEquals=" + DEFAULT_ORDER);

        // Get all the clazzChapterList where order not equals to UPDATED_ORDER
        defaultClazzChapterShouldBeFound("order.notEquals=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByOrderIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where order in DEFAULT_ORDER or UPDATED_ORDER
        defaultClazzChapterShouldBeFound("order.in=" + DEFAULT_ORDER + "," + UPDATED_ORDER);

        // Get all the clazzChapterList where order equals to UPDATED_ORDER
        defaultClazzChapterShouldNotBeFound("order.in=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where order is not null
        defaultClazzChapterShouldBeFound("order.specified=true");

        // Get all the clazzChapterList where order is null
        defaultClazzChapterShouldNotBeFound("order.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChaptersByOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where order is greater than or equal to DEFAULT_ORDER
        defaultClazzChapterShouldBeFound("order.greaterThanOrEqual=" + DEFAULT_ORDER);

        // Get all the clazzChapterList where order is greater than or equal to UPDATED_ORDER
        defaultClazzChapterShouldNotBeFound("order.greaterThanOrEqual=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where order is less than or equal to DEFAULT_ORDER
        defaultClazzChapterShouldBeFound("order.lessThanOrEqual=" + DEFAULT_ORDER);

        // Get all the clazzChapterList where order is less than or equal to SMALLER_ORDER
        defaultClazzChapterShouldNotBeFound("order.lessThanOrEqual=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where order is less than DEFAULT_ORDER
        defaultClazzChapterShouldNotBeFound("order.lessThan=" + DEFAULT_ORDER);

        // Get all the clazzChapterList where order is less than UPDATED_ORDER
        defaultClazzChapterShouldBeFound("order.lessThan=" + UPDATED_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where order is greater than DEFAULT_ORDER
        defaultClazzChapterShouldNotBeFound("order.greaterThan=" + DEFAULT_ORDER);

        // Get all the clazzChapterList where order is greater than SMALLER_ORDER
        defaultClazzChapterShouldBeFound("order.greaterThan=" + SMALLER_ORDER);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where activated equals to DEFAULT_ACTIVATED
        defaultClazzChapterShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the clazzChapterList where activated equals to UPDATED_ACTIVATED
        defaultClazzChapterShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where activated not equals to DEFAULT_ACTIVATED
        defaultClazzChapterShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the clazzChapterList where activated not equals to UPDATED_ACTIVATED
        defaultClazzChapterShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultClazzChapterShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the clazzChapterList where activated equals to UPDATED_ACTIVATED
        defaultClazzChapterShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where activated is not null
        defaultClazzChapterShouldBeFound("activated.specified=true");

        // Get all the clazzChapterList where activated is null
        defaultClazzChapterShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChaptersByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where createdBy equals to DEFAULT_CREATED_BY
        defaultClazzChapterShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the clazzChapterList where createdBy equals to UPDATED_CREATED_BY
        defaultClazzChapterShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where createdBy not equals to DEFAULT_CREATED_BY
        defaultClazzChapterShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the clazzChapterList where createdBy not equals to UPDATED_CREATED_BY
        defaultClazzChapterShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultClazzChapterShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the clazzChapterList where createdBy equals to UPDATED_CREATED_BY
        defaultClazzChapterShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where createdBy is not null
        defaultClazzChapterShouldBeFound("createdBy.specified=true");

        // Get all the clazzChapterList where createdBy is null
        defaultClazzChapterShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChaptersByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where createdBy contains DEFAULT_CREATED_BY
        defaultClazzChapterShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the clazzChapterList where createdBy contains UPDATED_CREATED_BY
        defaultClazzChapterShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where createdBy does not contain DEFAULT_CREATED_BY
        defaultClazzChapterShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the clazzChapterList where createdBy does not contain UPDATED_CREATED_BY
        defaultClazzChapterShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where createdDate equals to DEFAULT_CREATED_DATE
        defaultClazzChapterShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the clazzChapterList where createdDate equals to UPDATED_CREATED_DATE
        defaultClazzChapterShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultClazzChapterShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the clazzChapterList where createdDate not equals to UPDATED_CREATED_DATE
        defaultClazzChapterShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultClazzChapterShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the clazzChapterList where createdDate equals to UPDATED_CREATED_DATE
        defaultClazzChapterShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where createdDate is not null
        defaultClazzChapterShouldBeFound("createdDate.specified=true");

        // Get all the clazzChapterList where createdDate is null
        defaultClazzChapterShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChaptersByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultClazzChapterShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzChapterList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultClazzChapterShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzChapterList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the clazzChapterList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where lastModifiedBy is not null
        defaultClazzChapterShouldBeFound("lastModifiedBy.specified=true");

        // Get all the clazzChapterList where lastModifiedBy is null
        defaultClazzChapterShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChaptersByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultClazzChapterShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzChapterList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultClazzChapterShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzChapterList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultClazzChapterShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultClazzChapterShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the clazzChapterList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultClazzChapterShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultClazzChapterShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the clazzChapterList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultClazzChapterShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultClazzChapterShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the clazzChapterList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultClazzChapterShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzChaptersByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);

        // Get all the clazzChapterList where lastModifiedDate is not null
        defaultClazzChapterShouldBeFound("lastModifiedDate.specified=true");

        // Get all the clazzChapterList where lastModifiedDate is null
        defaultClazzChapterShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzChaptersByClazzChapterVideoIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);
        ClazzChapterVideo clazzChapterVideo = ClazzChapterVideoResourceIT.createEntity(em);
        em.persist(clazzChapterVideo);
        em.flush();
        clazzChapter.addClazzChapterVideo(clazzChapterVideo);
        clazzChapterRepository.saveAndFlush(clazzChapter);
        Long clazzChapterVideoId = clazzChapterVideo.getId();

        // Get all the clazzChapterList where clazzChapterVideo equals to clazzChapterVideoId
        defaultClazzChapterShouldBeFound("clazzChapterVideoId.equals=" + clazzChapterVideoId);

        // Get all the clazzChapterList where clazzChapterVideo equals to (clazzChapterVideoId + 1)
        defaultClazzChapterShouldNotBeFound("clazzChapterVideoId.equals=" + (clazzChapterVideoId + 1));
    }

    @Test
    @Transactional
    void getAllClazzChaptersByClazzIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzChapterRepository.saveAndFlush(clazzChapter);
        Clazz clazz = ClazzResourceIT.createEntity(em);
        em.persist(clazz);
        em.flush();
        clazzChapter.setClazz(clazz);
        clazzChapterRepository.saveAndFlush(clazzChapter);
        Long clazzId = clazz.getId();

        // Get all the clazzChapterList where clazz equals to clazzId
        defaultClazzChapterShouldBeFound("clazzId.equals=" + clazzId);

        // Get all the clazzChapterList where clazz equals to (clazzId + 1)
        defaultClazzChapterShouldNotBeFound("clazzId.equals=" + (clazzId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClazzChapterShouldBeFound(String filter) throws Exception {
        restClazzChapterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clazzChapter.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].fileUrl").value(hasItem(DEFAULT_FILE_URL)))
            .andExpect(jsonPath("$.[*].order").value(hasItem(DEFAULT_ORDER)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restClazzChapterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClazzChapterShouldNotBeFound(String filter) throws Exception {
        restClazzChapterMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClazzChapterMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
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
        updatedClazzChapter
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .fileUrl(UPDATED_FILE_URL)
            .order(UPDATED_ORDER)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
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
        assertThat(testClazzChapter.getOrder()).isEqualTo(UPDATED_ORDER);
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

        partialUpdatedClazzChapter
            .fileUrl(UPDATED_FILE_URL)
            .order(UPDATED_ORDER)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY);

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
        assertThat(testClazzChapter.getOrder()).isEqualTo(UPDATED_ORDER);
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

        partialUpdatedClazzChapter
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION)
            .fileUrl(UPDATED_FILE_URL)
            .order(UPDATED_ORDER)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

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
        assertThat(testClazzChapter.getOrder()).isEqualTo(UPDATED_ORDER);
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
