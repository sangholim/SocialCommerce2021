package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Clazz;
import com.toy.project.domain.ProductClazzRel;
import com.toy.project.repository.ClazzRepository;
import com.toy.project.service.criteria.ClazzCriteria;
import com.toy.project.service.dto.ClazzDTO;
import com.toy.project.service.mapper.ClazzMapper;
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
 * Integration tests for the {@link ClazzResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClazzResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE_LECTURE = false;
    private static final Boolean UPDATED_ENABLE_LECTURE = true;

    private static final String DEFAULT_LECTURER = "AAAAAAAAAA";
    private static final String UPDATED_LECTURER = "BBBBBBBBBB";

    private static final Integer DEFAULT_CALCULATION = 1;
    private static final Integer UPDATED_CALCULATION = 2;
    private static final Integer SMALLER_CALCULATION = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/clazzes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClazzRepository clazzRepository;

    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClazzMockMvc;

    private Clazz clazz;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clazz createEntity(EntityManager em) {
        Clazz clazz = new Clazz()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .level(DEFAULT_LEVEL)
            .enableLecture(DEFAULT_ENABLE_LECTURE)
            .lecturer(DEFAULT_LECTURER)
            .calculation(DEFAULT_CALCULATION)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return clazz;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clazz createUpdatedEntity(EntityManager em) {
        Clazz clazz = new Clazz()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .level(UPDATED_LEVEL)
            .enableLecture(UPDATED_ENABLE_LECTURE)
            .lecturer(UPDATED_LECTURER)
            .calculation(UPDATED_CALCULATION)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return clazz;
    }

    @BeforeEach
    public void initTest() {
        clazz = createEntity(em);
    }

    @Test
    @Transactional
    void createClazz() throws Exception {
        int databaseSizeBeforeCreate = clazzRepository.findAll().size();
        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);
        restClazzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clazzDTO)))
            .andExpect(status().isCreated());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeCreate + 1);
        Clazz testClazz = clazzList.get(clazzList.size() - 1);
        assertThat(testClazz.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClazz.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testClazz.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testClazz.getEnableLecture()).isEqualTo(DEFAULT_ENABLE_LECTURE);
        assertThat(testClazz.getLecturer()).isEqualTo(DEFAULT_LECTURER);
        assertThat(testClazz.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testClazz.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testClazz.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClazz.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testClazz.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testClazz.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createClazzWithExistingId() throws Exception {
        // Create the Clazz with an existing ID
        clazz.setId(1L);
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        int databaseSizeBeforeCreate = clazzRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClazzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clazzDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClazzes() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList
        restClazzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clazz.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].enableLecture").value(hasItem(DEFAULT_ENABLE_LECTURE.booleanValue())))
            .andExpect(jsonPath("$.[*].lecturer").value(hasItem(DEFAULT_LECTURER)))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getClazz() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get the clazz
        restClazzMockMvc
            .perform(get(ENTITY_API_URL_ID, clazz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clazz.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.enableLecture").value(DEFAULT_ENABLE_LECTURE.booleanValue()))
            .andExpect(jsonPath("$.lecturer").value(DEFAULT_LECTURER))
            .andExpect(jsonPath("$.calculation").value(DEFAULT_CALCULATION))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getClazzesByIdFiltering() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        Long id = clazz.getId();

        defaultClazzShouldBeFound("id.equals=" + id);
        defaultClazzShouldNotBeFound("id.notEquals=" + id);

        defaultClazzShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultClazzShouldNotBeFound("id.greaterThan=" + id);

        defaultClazzShouldBeFound("id.lessThanOrEqual=" + id);
        defaultClazzShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllClazzesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where name equals to DEFAULT_NAME
        defaultClazzShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the clazzList where name equals to UPDATED_NAME
        defaultClazzShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where name not equals to DEFAULT_NAME
        defaultClazzShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the clazzList where name not equals to UPDATED_NAME
        defaultClazzShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where name in DEFAULT_NAME or UPDATED_NAME
        defaultClazzShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the clazzList where name equals to UPDATED_NAME
        defaultClazzShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where name is not null
        defaultClazzShouldBeFound("name.specified=true");

        // Get all the clazzList where name is null
        defaultClazzShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzesByNameContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where name contains DEFAULT_NAME
        defaultClazzShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the clazzList where name contains UPDATED_NAME
        defaultClazzShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where name does not contain DEFAULT_NAME
        defaultClazzShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the clazzList where name does not contain UPDATED_NAME
        defaultClazzShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllClazzesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where type equals to DEFAULT_TYPE
        defaultClazzShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the clazzList where type equals to UPDATED_TYPE
        defaultClazzShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllClazzesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where type not equals to DEFAULT_TYPE
        defaultClazzShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the clazzList where type not equals to UPDATED_TYPE
        defaultClazzShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllClazzesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultClazzShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the clazzList where type equals to UPDATED_TYPE
        defaultClazzShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllClazzesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where type is not null
        defaultClazzShouldBeFound("type.specified=true");

        // Get all the clazzList where type is null
        defaultClazzShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzesByTypeContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where type contains DEFAULT_TYPE
        defaultClazzShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the clazzList where type contains UPDATED_TYPE
        defaultClazzShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllClazzesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where type does not contain DEFAULT_TYPE
        defaultClazzShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the clazzList where type does not contain UPDATED_TYPE
        defaultClazzShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllClazzesByLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where level equals to DEFAULT_LEVEL
        defaultClazzShouldBeFound("level.equals=" + DEFAULT_LEVEL);

        // Get all the clazzList where level equals to UPDATED_LEVEL
        defaultClazzShouldNotBeFound("level.equals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllClazzesByLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where level not equals to DEFAULT_LEVEL
        defaultClazzShouldNotBeFound("level.notEquals=" + DEFAULT_LEVEL);

        // Get all the clazzList where level not equals to UPDATED_LEVEL
        defaultClazzShouldBeFound("level.notEquals=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllClazzesByLevelIsInShouldWork() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where level in DEFAULT_LEVEL or UPDATED_LEVEL
        defaultClazzShouldBeFound("level.in=" + DEFAULT_LEVEL + "," + UPDATED_LEVEL);

        // Get all the clazzList where level equals to UPDATED_LEVEL
        defaultClazzShouldNotBeFound("level.in=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllClazzesByLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where level is not null
        defaultClazzShouldBeFound("level.specified=true");

        // Get all the clazzList where level is null
        defaultClazzShouldNotBeFound("level.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzesByLevelContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where level contains DEFAULT_LEVEL
        defaultClazzShouldBeFound("level.contains=" + DEFAULT_LEVEL);

        // Get all the clazzList where level contains UPDATED_LEVEL
        defaultClazzShouldNotBeFound("level.contains=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllClazzesByLevelNotContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where level does not contain DEFAULT_LEVEL
        defaultClazzShouldNotBeFound("level.doesNotContain=" + DEFAULT_LEVEL);

        // Get all the clazzList where level does not contain UPDATED_LEVEL
        defaultClazzShouldBeFound("level.doesNotContain=" + UPDATED_LEVEL);
    }

    @Test
    @Transactional
    void getAllClazzesByEnableLectureIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where enableLecture equals to DEFAULT_ENABLE_LECTURE
        defaultClazzShouldBeFound("enableLecture.equals=" + DEFAULT_ENABLE_LECTURE);

        // Get all the clazzList where enableLecture equals to UPDATED_ENABLE_LECTURE
        defaultClazzShouldNotBeFound("enableLecture.equals=" + UPDATED_ENABLE_LECTURE);
    }

    @Test
    @Transactional
    void getAllClazzesByEnableLectureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where enableLecture not equals to DEFAULT_ENABLE_LECTURE
        defaultClazzShouldNotBeFound("enableLecture.notEquals=" + DEFAULT_ENABLE_LECTURE);

        // Get all the clazzList where enableLecture not equals to UPDATED_ENABLE_LECTURE
        defaultClazzShouldBeFound("enableLecture.notEquals=" + UPDATED_ENABLE_LECTURE);
    }

    @Test
    @Transactional
    void getAllClazzesByEnableLectureIsInShouldWork() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where enableLecture in DEFAULT_ENABLE_LECTURE or UPDATED_ENABLE_LECTURE
        defaultClazzShouldBeFound("enableLecture.in=" + DEFAULT_ENABLE_LECTURE + "," + UPDATED_ENABLE_LECTURE);

        // Get all the clazzList where enableLecture equals to UPDATED_ENABLE_LECTURE
        defaultClazzShouldNotBeFound("enableLecture.in=" + UPDATED_ENABLE_LECTURE);
    }

    @Test
    @Transactional
    void getAllClazzesByEnableLectureIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where enableLecture is not null
        defaultClazzShouldBeFound("enableLecture.specified=true");

        // Get all the clazzList where enableLecture is null
        defaultClazzShouldNotBeFound("enableLecture.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzesByLecturerIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lecturer equals to DEFAULT_LECTURER
        defaultClazzShouldBeFound("lecturer.equals=" + DEFAULT_LECTURER);

        // Get all the clazzList where lecturer equals to UPDATED_LECTURER
        defaultClazzShouldNotBeFound("lecturer.equals=" + UPDATED_LECTURER);
    }

    @Test
    @Transactional
    void getAllClazzesByLecturerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lecturer not equals to DEFAULT_LECTURER
        defaultClazzShouldNotBeFound("lecturer.notEquals=" + DEFAULT_LECTURER);

        // Get all the clazzList where lecturer not equals to UPDATED_LECTURER
        defaultClazzShouldBeFound("lecturer.notEquals=" + UPDATED_LECTURER);
    }

    @Test
    @Transactional
    void getAllClazzesByLecturerIsInShouldWork() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lecturer in DEFAULT_LECTURER or UPDATED_LECTURER
        defaultClazzShouldBeFound("lecturer.in=" + DEFAULT_LECTURER + "," + UPDATED_LECTURER);

        // Get all the clazzList where lecturer equals to UPDATED_LECTURER
        defaultClazzShouldNotBeFound("lecturer.in=" + UPDATED_LECTURER);
    }

    @Test
    @Transactional
    void getAllClazzesByLecturerIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lecturer is not null
        defaultClazzShouldBeFound("lecturer.specified=true");

        // Get all the clazzList where lecturer is null
        defaultClazzShouldNotBeFound("lecturer.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzesByLecturerContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lecturer contains DEFAULT_LECTURER
        defaultClazzShouldBeFound("lecturer.contains=" + DEFAULT_LECTURER);

        // Get all the clazzList where lecturer contains UPDATED_LECTURER
        defaultClazzShouldNotBeFound("lecturer.contains=" + UPDATED_LECTURER);
    }

    @Test
    @Transactional
    void getAllClazzesByLecturerNotContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lecturer does not contain DEFAULT_LECTURER
        defaultClazzShouldNotBeFound("lecturer.doesNotContain=" + DEFAULT_LECTURER);

        // Get all the clazzList where lecturer does not contain UPDATED_LECTURER
        defaultClazzShouldBeFound("lecturer.doesNotContain=" + UPDATED_LECTURER);
    }

    @Test
    @Transactional
    void getAllClazzesByCalculationIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where calculation equals to DEFAULT_CALCULATION
        defaultClazzShouldBeFound("calculation.equals=" + DEFAULT_CALCULATION);

        // Get all the clazzList where calculation equals to UPDATED_CALCULATION
        defaultClazzShouldNotBeFound("calculation.equals=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllClazzesByCalculationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where calculation not equals to DEFAULT_CALCULATION
        defaultClazzShouldNotBeFound("calculation.notEquals=" + DEFAULT_CALCULATION);

        // Get all the clazzList where calculation not equals to UPDATED_CALCULATION
        defaultClazzShouldBeFound("calculation.notEquals=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllClazzesByCalculationIsInShouldWork() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where calculation in DEFAULT_CALCULATION or UPDATED_CALCULATION
        defaultClazzShouldBeFound("calculation.in=" + DEFAULT_CALCULATION + "," + UPDATED_CALCULATION);

        // Get all the clazzList where calculation equals to UPDATED_CALCULATION
        defaultClazzShouldNotBeFound("calculation.in=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllClazzesByCalculationIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where calculation is not null
        defaultClazzShouldBeFound("calculation.specified=true");

        // Get all the clazzList where calculation is null
        defaultClazzShouldNotBeFound("calculation.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzesByCalculationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where calculation is greater than or equal to DEFAULT_CALCULATION
        defaultClazzShouldBeFound("calculation.greaterThanOrEqual=" + DEFAULT_CALCULATION);

        // Get all the clazzList where calculation is greater than or equal to UPDATED_CALCULATION
        defaultClazzShouldNotBeFound("calculation.greaterThanOrEqual=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllClazzesByCalculationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where calculation is less than or equal to DEFAULT_CALCULATION
        defaultClazzShouldBeFound("calculation.lessThanOrEqual=" + DEFAULT_CALCULATION);

        // Get all the clazzList where calculation is less than or equal to SMALLER_CALCULATION
        defaultClazzShouldNotBeFound("calculation.lessThanOrEqual=" + SMALLER_CALCULATION);
    }

    @Test
    @Transactional
    void getAllClazzesByCalculationIsLessThanSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where calculation is less than DEFAULT_CALCULATION
        defaultClazzShouldNotBeFound("calculation.lessThan=" + DEFAULT_CALCULATION);

        // Get all the clazzList where calculation is less than UPDATED_CALCULATION
        defaultClazzShouldBeFound("calculation.lessThan=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllClazzesByCalculationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where calculation is greater than DEFAULT_CALCULATION
        defaultClazzShouldNotBeFound("calculation.greaterThan=" + DEFAULT_CALCULATION);

        // Get all the clazzList where calculation is greater than SMALLER_CALCULATION
        defaultClazzShouldBeFound("calculation.greaterThan=" + SMALLER_CALCULATION);
    }

    @Test
    @Transactional
    void getAllClazzesByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where activated equals to DEFAULT_ACTIVATED
        defaultClazzShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the clazzList where activated equals to UPDATED_ACTIVATED
        defaultClazzShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllClazzesByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where activated not equals to DEFAULT_ACTIVATED
        defaultClazzShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the clazzList where activated not equals to UPDATED_ACTIVATED
        defaultClazzShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllClazzesByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultClazzShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the clazzList where activated equals to UPDATED_ACTIVATED
        defaultClazzShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllClazzesByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where activated is not null
        defaultClazzShouldBeFound("activated.specified=true");

        // Get all the clazzList where activated is null
        defaultClazzShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where createdBy equals to DEFAULT_CREATED_BY
        defaultClazzShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the clazzList where createdBy equals to UPDATED_CREATED_BY
        defaultClazzShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where createdBy not equals to DEFAULT_CREATED_BY
        defaultClazzShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the clazzList where createdBy not equals to UPDATED_CREATED_BY
        defaultClazzShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultClazzShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the clazzList where createdBy equals to UPDATED_CREATED_BY
        defaultClazzShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where createdBy is not null
        defaultClazzShouldBeFound("createdBy.specified=true");

        // Get all the clazzList where createdBy is null
        defaultClazzShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where createdBy contains DEFAULT_CREATED_BY
        defaultClazzShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the clazzList where createdBy contains UPDATED_CREATED_BY
        defaultClazzShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where createdBy does not contain DEFAULT_CREATED_BY
        defaultClazzShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the clazzList where createdBy does not contain UPDATED_CREATED_BY
        defaultClazzShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllClazzesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where createdDate equals to DEFAULT_CREATED_DATE
        defaultClazzShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the clazzList where createdDate equals to UPDATED_CREATED_DATE
        defaultClazzShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultClazzShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the clazzList where createdDate not equals to UPDATED_CREATED_DATE
        defaultClazzShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultClazzShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the clazzList where createdDate equals to UPDATED_CREATED_DATE
        defaultClazzShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where createdDate is not null
        defaultClazzShouldBeFound("createdDate.specified=true");

        // Get all the clazzList where createdDate is null
        defaultClazzShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultClazzShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultClazzShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultClazzShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultClazzShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultClazzShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the clazzList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultClazzShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lastModifiedBy is not null
        defaultClazzShouldBeFound("lastModifiedBy.specified=true");

        // Get all the clazzList where lastModifiedBy is null
        defaultClazzShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultClazzShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultClazzShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultClazzShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the clazzList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultClazzShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllClazzesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultClazzShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the clazzList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultClazzShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultClazzShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the clazzList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultClazzShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultClazzShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the clazzList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultClazzShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllClazzesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList where lastModifiedDate is not null
        defaultClazzShouldBeFound("lastModifiedDate.specified=true");

        // Get all the clazzList where lastModifiedDate is null
        defaultClazzShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllClazzesByProductClazzRelIsEqualToSomething() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);
        ProductClazzRel productClazzRel = ProductClazzRelResourceIT.createEntity(em);
        em.persist(productClazzRel);
        em.flush();
        clazz.addProductClazzRel(productClazzRel);
        clazzRepository.saveAndFlush(clazz);
        Long productClazzRelId = productClazzRel.getId();

        // Get all the clazzList where productClazzRel equals to productClazzRelId
        defaultClazzShouldBeFound("productClazzRelId.equals=" + productClazzRelId);

        // Get all the clazzList where productClazzRel equals to (productClazzRelId + 1)
        defaultClazzShouldNotBeFound("productClazzRelId.equals=" + (productClazzRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultClazzShouldBeFound(String filter) throws Exception {
        restClazzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clazz.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].enableLecture").value(hasItem(DEFAULT_ENABLE_LECTURE.booleanValue())))
            .andExpect(jsonPath("$.[*].lecturer").value(hasItem(DEFAULT_LECTURER)))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restClazzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultClazzShouldNotBeFound(String filter) throws Exception {
        restClazzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restClazzMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingClazz() throws Exception {
        // Get the clazz
        restClazzMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClazz() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();

        // Update the clazz
        Clazz updatedClazz = clazzRepository.findById(clazz.getId()).get();
        // Disconnect from session so that the updates on updatedClazz are not directly saved in db
        em.detach(updatedClazz);
        updatedClazz
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .level(UPDATED_LEVEL)
            .enableLecture(UPDATED_ENABLE_LECTURE)
            .lecturer(UPDATED_LECTURER)
            .calculation(UPDATED_CALCULATION)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ClazzDTO clazzDTO = clazzMapper.toDto(updatedClazz);

        restClazzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clazzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzDTO))
            )
            .andExpect(status().isOk());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
        Clazz testClazz = clazzList.get(clazzList.size() - 1);
        assertThat(testClazz.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClazz.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testClazz.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testClazz.getEnableLecture()).isEqualTo(UPDATED_ENABLE_LECTURE);
        assertThat(testClazz.getLecturer()).isEqualTo(UPDATED_LECTURER);
        assertThat(testClazz.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testClazz.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazz.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClazz.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClazz.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testClazz.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clazzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clazzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClazzWithPatch() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();

        // Update the clazz using partial update
        Clazz partialUpdatedClazz = new Clazz();
        partialUpdatedClazz.setId(clazz.getId());

        partialUpdatedClazz
            .name(UPDATED_NAME)
            .level(UPDATED_LEVEL)
            .enableLecture(UPDATED_ENABLE_LECTURE)
            .lecturer(UPDATED_LECTURER)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restClazzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClazz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClazz))
            )
            .andExpect(status().isOk());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
        Clazz testClazz = clazzList.get(clazzList.size() - 1);
        assertThat(testClazz.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClazz.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testClazz.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testClazz.getEnableLecture()).isEqualTo(UPDATED_ENABLE_LECTURE);
        assertThat(testClazz.getLecturer()).isEqualTo(UPDATED_LECTURER);
        assertThat(testClazz.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testClazz.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazz.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClazz.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testClazz.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testClazz.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateClazzWithPatch() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();

        // Update the clazz using partial update
        Clazz partialUpdatedClazz = new Clazz();
        partialUpdatedClazz.setId(clazz.getId());

        partialUpdatedClazz
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .level(UPDATED_LEVEL)
            .enableLecture(UPDATED_ENABLE_LECTURE)
            .lecturer(UPDATED_LECTURER)
            .calculation(UPDATED_CALCULATION)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restClazzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClazz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClazz))
            )
            .andExpect(status().isOk());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
        Clazz testClazz = clazzList.get(clazzList.size() - 1);
        assertThat(testClazz.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClazz.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testClazz.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testClazz.getEnableLecture()).isEqualTo(UPDATED_ENABLE_LECTURE);
        assertThat(testClazz.getLecturer()).isEqualTo(UPDATED_LECTURER);
        assertThat(testClazz.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testClazz.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazz.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClazz.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClazz.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testClazz.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clazzDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clazzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clazzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clazzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClazz() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        int databaseSizeBeforeDelete = clazzRepository.findAll().size();

        // Delete the clazz
        restClazzMockMvc
            .perform(delete(ENTITY_API_URL_ID, clazz.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
