package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.OptionColor;
import com.toy.project.domain.ProductOptionColorRel;
import com.toy.project.repository.OptionColorRepository;
import com.toy.project.service.criteria.OptionColorCriteria;
import com.toy.project.service.dto.OptionColorDTO;
import com.toy.project.service.mapper.OptionColorMapper;
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
 * Integration tests for the {@link OptionColorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OptionColorResourceIT {

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/option-colors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OptionColorRepository optionColorRepository;

    @Autowired
    private OptionColorMapper optionColorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionColorMockMvc;

    private OptionColor optionColor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionColor createEntity(EntityManager em) {
        OptionColor optionColor = new OptionColor()
            .code(DEFAULT_CODE)
            .value(DEFAULT_VALUE)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return optionColor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionColor createUpdatedEntity(EntityManager em) {
        OptionColor optionColor = new OptionColor()
            .code(UPDATED_CODE)
            .value(UPDATED_VALUE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return optionColor;
    }

    @BeforeEach
    public void initTest() {
        optionColor = createEntity(em);
    }

    @Test
    @Transactional
    void createOptionColor() throws Exception {
        int databaseSizeBeforeCreate = optionColorRepository.findAll().size();
        // Create the OptionColor
        OptionColorDTO optionColorDTO = optionColorMapper.toDto(optionColor);
        restOptionColorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionColorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OptionColor in the database
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeCreate + 1);
        OptionColor testOptionColor = optionColorList.get(optionColorList.size() - 1);
        assertThat(testOptionColor.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOptionColor.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOptionColor.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testOptionColor.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOptionColor.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOptionColor.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOptionColor.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createOptionColorWithExistingId() throws Exception {
        // Create the OptionColor with an existing ID
        optionColor.setId(1L);
        OptionColorDTO optionColorDTO = optionColorMapper.toDto(optionColor);

        int databaseSizeBeforeCreate = optionColorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionColorMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionColorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionColor in the database
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOptionColors() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList
        restOptionColorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionColor.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOptionColor() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get the optionColor
        restOptionColorMockMvc
            .perform(get(ENTITY_API_URL_ID, optionColor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optionColor.getId().intValue()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getOptionColorsByIdFiltering() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        Long id = optionColor.getId();

        defaultOptionColorShouldBeFound("id.equals=" + id);
        defaultOptionColorShouldNotBeFound("id.notEquals=" + id);

        defaultOptionColorShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOptionColorShouldNotBeFound("id.greaterThan=" + id);

        defaultOptionColorShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOptionColorShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where code equals to DEFAULT_CODE
        defaultOptionColorShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the optionColorList where code equals to UPDATED_CODE
        defaultOptionColorShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where code not equals to DEFAULT_CODE
        defaultOptionColorShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the optionColorList where code not equals to UPDATED_CODE
        defaultOptionColorShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where code in DEFAULT_CODE or UPDATED_CODE
        defaultOptionColorShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the optionColorList where code equals to UPDATED_CODE
        defaultOptionColorShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where code is not null
        defaultOptionColorShouldBeFound("code.specified=true");

        // Get all the optionColorList where code is null
        defaultOptionColorShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionColorsByCodeContainsSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where code contains DEFAULT_CODE
        defaultOptionColorShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the optionColorList where code contains UPDATED_CODE
        defaultOptionColorShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where code does not contain DEFAULT_CODE
        defaultOptionColorShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the optionColorList where code does not contain UPDATED_CODE
        defaultOptionColorShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where value equals to DEFAULT_VALUE
        defaultOptionColorShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the optionColorList where value equals to UPDATED_VALUE
        defaultOptionColorShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where value not equals to DEFAULT_VALUE
        defaultOptionColorShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the optionColorList where value not equals to UPDATED_VALUE
        defaultOptionColorShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultOptionColorShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the optionColorList where value equals to UPDATED_VALUE
        defaultOptionColorShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where value is not null
        defaultOptionColorShouldBeFound("value.specified=true");

        // Get all the optionColorList where value is null
        defaultOptionColorShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionColorsByValueContainsSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where value contains DEFAULT_VALUE
        defaultOptionColorShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the optionColorList where value contains UPDATED_VALUE
        defaultOptionColorShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where value does not contain DEFAULT_VALUE
        defaultOptionColorShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the optionColorList where value does not contain UPDATED_VALUE
        defaultOptionColorShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where activated equals to DEFAULT_ACTIVATED
        defaultOptionColorShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the optionColorList where activated equals to UPDATED_ACTIVATED
        defaultOptionColorShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOptionColorsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where activated not equals to DEFAULT_ACTIVATED
        defaultOptionColorShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the optionColorList where activated not equals to UPDATED_ACTIVATED
        defaultOptionColorShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOptionColorsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultOptionColorShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the optionColorList where activated equals to UPDATED_ACTIVATED
        defaultOptionColorShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOptionColorsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where activated is not null
        defaultOptionColorShouldBeFound("activated.specified=true");

        // Get all the optionColorList where activated is null
        defaultOptionColorShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionColorsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where createdBy equals to DEFAULT_CREATED_BY
        defaultOptionColorShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the optionColorList where createdBy equals to UPDATED_CREATED_BY
        defaultOptionColorShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where createdBy not equals to DEFAULT_CREATED_BY
        defaultOptionColorShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the optionColorList where createdBy not equals to UPDATED_CREATED_BY
        defaultOptionColorShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOptionColorShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the optionColorList where createdBy equals to UPDATED_CREATED_BY
        defaultOptionColorShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where createdBy is not null
        defaultOptionColorShouldBeFound("createdBy.specified=true");

        // Get all the optionColorList where createdBy is null
        defaultOptionColorShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionColorsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where createdBy contains DEFAULT_CREATED_BY
        defaultOptionColorShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the optionColorList where createdBy contains UPDATED_CREATED_BY
        defaultOptionColorShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where createdBy does not contain DEFAULT_CREATED_BY
        defaultOptionColorShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the optionColorList where createdBy does not contain UPDATED_CREATED_BY
        defaultOptionColorShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where createdDate equals to DEFAULT_CREATED_DATE
        defaultOptionColorShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the optionColorList where createdDate equals to UPDATED_CREATED_DATE
        defaultOptionColorShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultOptionColorShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the optionColorList where createdDate not equals to UPDATED_CREATED_DATE
        defaultOptionColorShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultOptionColorShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the optionColorList where createdDate equals to UPDATED_CREATED_DATE
        defaultOptionColorShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where createdDate is not null
        defaultOptionColorShouldBeFound("createdDate.specified=true");

        // Get all the optionColorList where createdDate is null
        defaultOptionColorShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionColorsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultOptionColorShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionColorList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOptionColorShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionColorsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultOptionColorShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionColorList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultOptionColorShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionColorsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultOptionColorShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the optionColorList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOptionColorShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionColorsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where lastModifiedBy is not null
        defaultOptionColorShouldBeFound("lastModifiedBy.specified=true");

        // Get all the optionColorList where lastModifiedBy is null
        defaultOptionColorShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionColorsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultOptionColorShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionColorList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultOptionColorShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionColorsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultOptionColorShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionColorList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultOptionColorShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionColorsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultOptionColorShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the optionColorList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultOptionColorShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultOptionColorShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the optionColorList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultOptionColorShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultOptionColorShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the optionColorList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultOptionColorShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionColorsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        // Get all the optionColorList where lastModifiedDate is not null
        defaultOptionColorShouldBeFound("lastModifiedDate.specified=true");

        // Get all the optionColorList where lastModifiedDate is null
        defaultOptionColorShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionColorsByProductOptionColorRelIsEqualToSomething() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);
        ProductOptionColorRel productOptionColorRel = ProductOptionColorRelResourceIT.createEntity(em);
        em.persist(productOptionColorRel);
        em.flush();
        optionColor.addProductOptionColorRel(productOptionColorRel);
        optionColorRepository.saveAndFlush(optionColor);
        Long productOptionColorRelId = productOptionColorRel.getId();

        // Get all the optionColorList where productOptionColorRel equals to productOptionColorRelId
        defaultOptionColorShouldBeFound("productOptionColorRelId.equals=" + productOptionColorRelId);

        // Get all the optionColorList where productOptionColorRel equals to (productOptionColorRelId + 1)
        defaultOptionColorShouldNotBeFound("productOptionColorRelId.equals=" + (productOptionColorRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOptionColorShouldBeFound(String filter) throws Exception {
        restOptionColorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionColor.getId().intValue())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restOptionColorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOptionColorShouldNotBeFound(String filter) throws Exception {
        restOptionColorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOptionColorMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOptionColor() throws Exception {
        // Get the optionColor
        restOptionColorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOptionColor() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        int databaseSizeBeforeUpdate = optionColorRepository.findAll().size();

        // Update the optionColor
        OptionColor updatedOptionColor = optionColorRepository.findById(optionColor.getId()).get();
        // Disconnect from session so that the updates on updatedOptionColor are not directly saved in db
        em.detach(updatedOptionColor);
        updatedOptionColor
            .code(UPDATED_CODE)
            .value(UPDATED_VALUE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        OptionColorDTO optionColorDTO = optionColorMapper.toDto(updatedOptionColor);

        restOptionColorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, optionColorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionColorDTO))
            )
            .andExpect(status().isOk());

        // Validate the OptionColor in the database
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeUpdate);
        OptionColor testOptionColor = optionColorList.get(optionColorList.size() - 1);
        assertThat(testOptionColor.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOptionColor.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOptionColor.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testOptionColor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOptionColor.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOptionColor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOptionColor.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOptionColor() throws Exception {
        int databaseSizeBeforeUpdate = optionColorRepository.findAll().size();
        optionColor.setId(count.incrementAndGet());

        // Create the OptionColor
        OptionColorDTO optionColorDTO = optionColorMapper.toDto(optionColor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionColorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, optionColorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionColorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionColor in the database
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOptionColor() throws Exception {
        int databaseSizeBeforeUpdate = optionColorRepository.findAll().size();
        optionColor.setId(count.incrementAndGet());

        // Create the OptionColor
        OptionColorDTO optionColorDTO = optionColorMapper.toDto(optionColor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionColorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionColorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionColor in the database
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOptionColor() throws Exception {
        int databaseSizeBeforeUpdate = optionColorRepository.findAll().size();
        optionColor.setId(count.incrementAndGet());

        // Create the OptionColor
        OptionColorDTO optionColorDTO = optionColorMapper.toDto(optionColor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionColorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionColorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the OptionColor in the database
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOptionColorWithPatch() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        int databaseSizeBeforeUpdate = optionColorRepository.findAll().size();

        // Update the optionColor using partial update
        OptionColor partialUpdatedOptionColor = new OptionColor();
        partialUpdatedOptionColor.setId(optionColor.getId());

        partialUpdatedOptionColor.value(UPDATED_VALUE).activated(UPDATED_ACTIVATED).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restOptionColorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionColor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionColor))
            )
            .andExpect(status().isOk());

        // Validate the OptionColor in the database
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeUpdate);
        OptionColor testOptionColor = optionColorList.get(optionColorList.size() - 1);
        assertThat(testOptionColor.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testOptionColor.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOptionColor.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testOptionColor.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOptionColor.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOptionColor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOptionColor.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOptionColorWithPatch() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        int databaseSizeBeforeUpdate = optionColorRepository.findAll().size();

        // Update the optionColor using partial update
        OptionColor partialUpdatedOptionColor = new OptionColor();
        partialUpdatedOptionColor.setId(optionColor.getId());

        partialUpdatedOptionColor
            .code(UPDATED_CODE)
            .value(UPDATED_VALUE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restOptionColorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionColor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionColor))
            )
            .andExpect(status().isOk());

        // Validate the OptionColor in the database
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeUpdate);
        OptionColor testOptionColor = optionColorList.get(optionColorList.size() - 1);
        assertThat(testOptionColor.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testOptionColor.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOptionColor.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testOptionColor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOptionColor.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOptionColor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOptionColor.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOptionColor() throws Exception {
        int databaseSizeBeforeUpdate = optionColorRepository.findAll().size();
        optionColor.setId(count.incrementAndGet());

        // Create the OptionColor
        OptionColorDTO optionColorDTO = optionColorMapper.toDto(optionColor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionColorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, optionColorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionColorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionColor in the database
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOptionColor() throws Exception {
        int databaseSizeBeforeUpdate = optionColorRepository.findAll().size();
        optionColor.setId(count.incrementAndGet());

        // Create the OptionColor
        OptionColorDTO optionColorDTO = optionColorMapper.toDto(optionColor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionColorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionColorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionColor in the database
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOptionColor() throws Exception {
        int databaseSizeBeforeUpdate = optionColorRepository.findAll().size();
        optionColor.setId(count.incrementAndGet());

        // Create the OptionColor
        OptionColorDTO optionColorDTO = optionColorMapper.toDto(optionColor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionColorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(optionColorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OptionColor in the database
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOptionColor() throws Exception {
        // Initialize the database
        optionColorRepository.saveAndFlush(optionColor);

        int databaseSizeBeforeDelete = optionColorRepository.findAll().size();

        // Delete the optionColor
        restOptionColorMockMvc
            .perform(delete(ENTITY_API_URL_ID, optionColor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OptionColor> optionColorList = optionColorRepository.findAll();
        assertThat(optionColorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
