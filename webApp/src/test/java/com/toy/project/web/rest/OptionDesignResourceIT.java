package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.OptionDesign;
import com.toy.project.domain.ProductOptionDesignRel;
import com.toy.project.repository.OptionDesignRepository;
import com.toy.project.service.criteria.OptionDesignCriteria;
import com.toy.project.service.dto.OptionDesignDTO;
import com.toy.project.service.mapper.OptionDesignMapper;
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
 * Integration tests for the {@link OptionDesignResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OptionDesignResourceIT {

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

    private static final String ENTITY_API_URL = "/api/option-designs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OptionDesignRepository optionDesignRepository;

    @Autowired
    private OptionDesignMapper optionDesignMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionDesignMockMvc;

    private OptionDesign optionDesign;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionDesign createEntity(EntityManager em) {
        OptionDesign optionDesign = new OptionDesign()
            .value(DEFAULT_VALUE)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return optionDesign;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionDesign createUpdatedEntity(EntityManager em) {
        OptionDesign optionDesign = new OptionDesign()
            .value(UPDATED_VALUE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return optionDesign;
    }

    @BeforeEach
    public void initTest() {
        optionDesign = createEntity(em);
    }

    @Test
    @Transactional
    void createOptionDesign() throws Exception {
        int databaseSizeBeforeCreate = optionDesignRepository.findAll().size();
        // Create the OptionDesign
        OptionDesignDTO optionDesignDTO = optionDesignMapper.toDto(optionDesign);
        restOptionDesignMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionDesignDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OptionDesign in the database
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeCreate + 1);
        OptionDesign testOptionDesign = optionDesignList.get(optionDesignList.size() - 1);
        assertThat(testOptionDesign.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOptionDesign.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testOptionDesign.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOptionDesign.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOptionDesign.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOptionDesign.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createOptionDesignWithExistingId() throws Exception {
        // Create the OptionDesign with an existing ID
        optionDesign.setId(1L);
        OptionDesignDTO optionDesignDTO = optionDesignMapper.toDto(optionDesign);

        int databaseSizeBeforeCreate = optionDesignRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionDesignMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionDesignDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionDesign in the database
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOptionDesigns() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList
        restOptionDesignMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionDesign.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOptionDesign() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get the optionDesign
        restOptionDesignMockMvc
            .perform(get(ENTITY_API_URL_ID, optionDesign.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optionDesign.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getOptionDesignsByIdFiltering() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        Long id = optionDesign.getId();

        defaultOptionDesignShouldBeFound("id.equals=" + id);
        defaultOptionDesignShouldNotBeFound("id.notEquals=" + id);

        defaultOptionDesignShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOptionDesignShouldNotBeFound("id.greaterThan=" + id);

        defaultOptionDesignShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOptionDesignShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where value equals to DEFAULT_VALUE
        defaultOptionDesignShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the optionDesignList where value equals to UPDATED_VALUE
        defaultOptionDesignShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where value not equals to DEFAULT_VALUE
        defaultOptionDesignShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the optionDesignList where value not equals to UPDATED_VALUE
        defaultOptionDesignShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByValueIsInShouldWork() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultOptionDesignShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the optionDesignList where value equals to UPDATED_VALUE
        defaultOptionDesignShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where value is not null
        defaultOptionDesignShouldBeFound("value.specified=true");

        // Get all the optionDesignList where value is null
        defaultOptionDesignShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionDesignsByValueContainsSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where value contains DEFAULT_VALUE
        defaultOptionDesignShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the optionDesignList where value contains UPDATED_VALUE
        defaultOptionDesignShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByValueNotContainsSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where value does not contain DEFAULT_VALUE
        defaultOptionDesignShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the optionDesignList where value does not contain UPDATED_VALUE
        defaultOptionDesignShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where activated equals to DEFAULT_ACTIVATED
        defaultOptionDesignShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the optionDesignList where activated equals to UPDATED_ACTIVATED
        defaultOptionDesignShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where activated not equals to DEFAULT_ACTIVATED
        defaultOptionDesignShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the optionDesignList where activated not equals to UPDATED_ACTIVATED
        defaultOptionDesignShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultOptionDesignShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the optionDesignList where activated equals to UPDATED_ACTIVATED
        defaultOptionDesignShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where activated is not null
        defaultOptionDesignShouldBeFound("activated.specified=true");

        // Get all the optionDesignList where activated is null
        defaultOptionDesignShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionDesignsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where createdBy equals to DEFAULT_CREATED_BY
        defaultOptionDesignShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the optionDesignList where createdBy equals to UPDATED_CREATED_BY
        defaultOptionDesignShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where createdBy not equals to DEFAULT_CREATED_BY
        defaultOptionDesignShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the optionDesignList where createdBy not equals to UPDATED_CREATED_BY
        defaultOptionDesignShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOptionDesignShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the optionDesignList where createdBy equals to UPDATED_CREATED_BY
        defaultOptionDesignShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where createdBy is not null
        defaultOptionDesignShouldBeFound("createdBy.specified=true");

        // Get all the optionDesignList where createdBy is null
        defaultOptionDesignShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionDesignsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where createdBy contains DEFAULT_CREATED_BY
        defaultOptionDesignShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the optionDesignList where createdBy contains UPDATED_CREATED_BY
        defaultOptionDesignShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where createdBy does not contain DEFAULT_CREATED_BY
        defaultOptionDesignShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the optionDesignList where createdBy does not contain UPDATED_CREATED_BY
        defaultOptionDesignShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where createdDate equals to DEFAULT_CREATED_DATE
        defaultOptionDesignShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the optionDesignList where createdDate equals to UPDATED_CREATED_DATE
        defaultOptionDesignShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultOptionDesignShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the optionDesignList where createdDate not equals to UPDATED_CREATED_DATE
        defaultOptionDesignShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultOptionDesignShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the optionDesignList where createdDate equals to UPDATED_CREATED_DATE
        defaultOptionDesignShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where createdDate is not null
        defaultOptionDesignShouldBeFound("createdDate.specified=true");

        // Get all the optionDesignList where createdDate is null
        defaultOptionDesignShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionDesignsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultOptionDesignShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionDesignList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOptionDesignShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultOptionDesignShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionDesignList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultOptionDesignShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultOptionDesignShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the optionDesignList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOptionDesignShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where lastModifiedBy is not null
        defaultOptionDesignShouldBeFound("lastModifiedBy.specified=true");

        // Get all the optionDesignList where lastModifiedBy is null
        defaultOptionDesignShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionDesignsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultOptionDesignShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionDesignList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultOptionDesignShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultOptionDesignShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionDesignList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultOptionDesignShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultOptionDesignShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the optionDesignList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultOptionDesignShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultOptionDesignShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the optionDesignList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultOptionDesignShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultOptionDesignShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the optionDesignList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultOptionDesignShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionDesignsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        // Get all the optionDesignList where lastModifiedDate is not null
        defaultOptionDesignShouldBeFound("lastModifiedDate.specified=true");

        // Get all the optionDesignList where lastModifiedDate is null
        defaultOptionDesignShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionDesignsByProductOptionDesignRelIsEqualToSomething() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);
        ProductOptionDesignRel productOptionDesignRel = ProductOptionDesignRelResourceIT.createEntity(em);
        em.persist(productOptionDesignRel);
        em.flush();
        optionDesign.addProductOptionDesignRel(productOptionDesignRel);
        optionDesignRepository.saveAndFlush(optionDesign);
        Long productOptionDesignRelId = productOptionDesignRel.getId();

        // Get all the optionDesignList where productOptionDesignRel equals to productOptionDesignRelId
        defaultOptionDesignShouldBeFound("productOptionDesignRelId.equals=" + productOptionDesignRelId);

        // Get all the optionDesignList where productOptionDesignRel equals to (productOptionDesignRelId + 1)
        defaultOptionDesignShouldNotBeFound("productOptionDesignRelId.equals=" + (productOptionDesignRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOptionDesignShouldBeFound(String filter) throws Exception {
        restOptionDesignMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionDesign.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restOptionDesignMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOptionDesignShouldNotBeFound(String filter) throws Exception {
        restOptionDesignMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOptionDesignMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOptionDesign() throws Exception {
        // Get the optionDesign
        restOptionDesignMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOptionDesign() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        int databaseSizeBeforeUpdate = optionDesignRepository.findAll().size();

        // Update the optionDesign
        OptionDesign updatedOptionDesign = optionDesignRepository.findById(optionDesign.getId()).get();
        // Disconnect from session so that the updates on updatedOptionDesign are not directly saved in db
        em.detach(updatedOptionDesign);
        updatedOptionDesign
            .value(UPDATED_VALUE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        OptionDesignDTO optionDesignDTO = optionDesignMapper.toDto(updatedOptionDesign);

        restOptionDesignMockMvc
            .perform(
                put(ENTITY_API_URL_ID, optionDesignDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionDesignDTO))
            )
            .andExpect(status().isOk());

        // Validate the OptionDesign in the database
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeUpdate);
        OptionDesign testOptionDesign = optionDesignList.get(optionDesignList.size() - 1);
        assertThat(testOptionDesign.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOptionDesign.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testOptionDesign.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOptionDesign.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOptionDesign.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOptionDesign.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOptionDesign() throws Exception {
        int databaseSizeBeforeUpdate = optionDesignRepository.findAll().size();
        optionDesign.setId(count.incrementAndGet());

        // Create the OptionDesign
        OptionDesignDTO optionDesignDTO = optionDesignMapper.toDto(optionDesign);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionDesignMockMvc
            .perform(
                put(ENTITY_API_URL_ID, optionDesignDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionDesignDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionDesign in the database
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOptionDesign() throws Exception {
        int databaseSizeBeforeUpdate = optionDesignRepository.findAll().size();
        optionDesign.setId(count.incrementAndGet());

        // Create the OptionDesign
        OptionDesignDTO optionDesignDTO = optionDesignMapper.toDto(optionDesign);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionDesignMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionDesignDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionDesign in the database
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOptionDesign() throws Exception {
        int databaseSizeBeforeUpdate = optionDesignRepository.findAll().size();
        optionDesign.setId(count.incrementAndGet());

        // Create the OptionDesign
        OptionDesignDTO optionDesignDTO = optionDesignMapper.toDto(optionDesign);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionDesignMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionDesignDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OptionDesign in the database
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOptionDesignWithPatch() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        int databaseSizeBeforeUpdate = optionDesignRepository.findAll().size();

        // Update the optionDesign using partial update
        OptionDesign partialUpdatedOptionDesign = new OptionDesign();
        partialUpdatedOptionDesign.setId(optionDesign.getId());

        partialUpdatedOptionDesign.value(UPDATED_VALUE).activated(UPDATED_ACTIVATED).createdDate(UPDATED_CREATED_DATE);

        restOptionDesignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionDesign.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionDesign))
            )
            .andExpect(status().isOk());

        // Validate the OptionDesign in the database
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeUpdate);
        OptionDesign testOptionDesign = optionDesignList.get(optionDesignList.size() - 1);
        assertThat(testOptionDesign.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOptionDesign.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testOptionDesign.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOptionDesign.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOptionDesign.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOptionDesign.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOptionDesignWithPatch() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        int databaseSizeBeforeUpdate = optionDesignRepository.findAll().size();

        // Update the optionDesign using partial update
        OptionDesign partialUpdatedOptionDesign = new OptionDesign();
        partialUpdatedOptionDesign.setId(optionDesign.getId());

        partialUpdatedOptionDesign
            .value(UPDATED_VALUE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restOptionDesignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionDesign.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionDesign))
            )
            .andExpect(status().isOk());

        // Validate the OptionDesign in the database
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeUpdate);
        OptionDesign testOptionDesign = optionDesignList.get(optionDesignList.size() - 1);
        assertThat(testOptionDesign.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOptionDesign.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testOptionDesign.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOptionDesign.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOptionDesign.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOptionDesign.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOptionDesign() throws Exception {
        int databaseSizeBeforeUpdate = optionDesignRepository.findAll().size();
        optionDesign.setId(count.incrementAndGet());

        // Create the OptionDesign
        OptionDesignDTO optionDesignDTO = optionDesignMapper.toDto(optionDesign);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionDesignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, optionDesignDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionDesignDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionDesign in the database
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOptionDesign() throws Exception {
        int databaseSizeBeforeUpdate = optionDesignRepository.findAll().size();
        optionDesign.setId(count.incrementAndGet());

        // Create the OptionDesign
        OptionDesignDTO optionDesignDTO = optionDesignMapper.toDto(optionDesign);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionDesignMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionDesignDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionDesign in the database
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOptionDesign() throws Exception {
        int databaseSizeBeforeUpdate = optionDesignRepository.findAll().size();
        optionDesign.setId(count.incrementAndGet());

        // Create the OptionDesign
        OptionDesignDTO optionDesignDTO = optionDesignMapper.toDto(optionDesign);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionDesignMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionDesignDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OptionDesign in the database
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOptionDesign() throws Exception {
        // Initialize the database
        optionDesignRepository.saveAndFlush(optionDesign);

        int databaseSizeBeforeDelete = optionDesignRepository.findAll().size();

        // Delete the optionDesign
        restOptionDesignMockMvc
            .perform(delete(ENTITY_API_URL_ID, optionDesign.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OptionDesign> optionDesignList = optionDesignRepository.findAll();
        assertThat(optionDesignList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
