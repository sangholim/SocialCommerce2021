package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductStoreRel;
import com.toy.project.domain.Store;
import com.toy.project.repository.StoreRepository;
import com.toy.project.service.criteria.StoreCriteria;
import com.toy.project.service.dto.StoreDTO;
import com.toy.project.service.mapper.StoreMapper;
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
 * Integration tests for the {@link StoreResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class StoreResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/stores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private StoreMapper storeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restStoreMockMvc;

    private Store store;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Store createEntity(EntityManager em) {
        Store store = new Store()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .calculation(DEFAULT_CALCULATION)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return store;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Store createUpdatedEntity(EntityManager em) {
        Store store = new Store()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .calculation(UPDATED_CALCULATION)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return store;
    }

    @BeforeEach
    public void initTest() {
        store = createEntity(em);
    }

    @Test
    @Transactional
    void createStore() throws Exception {
        int databaseSizeBeforeCreate = storeRepository.findAll().size();
        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);
        restStoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isCreated());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeCreate + 1);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testStore.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testStore.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testStore.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testStore.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testStore.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testStore.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testStore.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createStoreWithExistingId() throws Exception {
        // Create the Store with an existing ID
        store.setId(1L);
        StoreDTO storeDTO = storeMapper.toDto(store);

        int databaseSizeBeforeCreate = storeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restStoreMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllStores() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList
        restStoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get the store
        restStoreMockMvc
            .perform(get(ENTITY_API_URL_ID, store.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(store.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.calculation").value(DEFAULT_CALCULATION))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getStoresByIdFiltering() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        Long id = store.getId();

        defaultStoreShouldBeFound("id.equals=" + id);
        defaultStoreShouldNotBeFound("id.notEquals=" + id);

        defaultStoreShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultStoreShouldNotBeFound("id.greaterThan=" + id);

        defaultStoreShouldBeFound("id.lessThanOrEqual=" + id);
        defaultStoreShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllStoresByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name equals to DEFAULT_NAME
        defaultStoreShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the storeList where name equals to UPDATED_NAME
        defaultStoreShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoresByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name not equals to DEFAULT_NAME
        defaultStoreShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the storeList where name not equals to UPDATED_NAME
        defaultStoreShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoresByNameIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultStoreShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the storeList where name equals to UPDATED_NAME
        defaultStoreShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoresByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name is not null
        defaultStoreShouldBeFound("name.specified=true");

        // Get all the storeList where name is null
        defaultStoreShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByNameContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name contains DEFAULT_NAME
        defaultStoreShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the storeList where name contains UPDATED_NAME
        defaultStoreShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoresByNameNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where name does not contain DEFAULT_NAME
        defaultStoreShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the storeList where name does not contain UPDATED_NAME
        defaultStoreShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllStoresByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where type equals to DEFAULT_TYPE
        defaultStoreShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the storeList where type equals to UPDATED_TYPE
        defaultStoreShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllStoresByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where type not equals to DEFAULT_TYPE
        defaultStoreShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the storeList where type not equals to UPDATED_TYPE
        defaultStoreShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllStoresByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultStoreShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the storeList where type equals to UPDATED_TYPE
        defaultStoreShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllStoresByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where type is not null
        defaultStoreShouldBeFound("type.specified=true");

        // Get all the storeList where type is null
        defaultStoreShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByTypeContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where type contains DEFAULT_TYPE
        defaultStoreShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the storeList where type contains UPDATED_TYPE
        defaultStoreShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllStoresByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where type does not contain DEFAULT_TYPE
        defaultStoreShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the storeList where type does not contain UPDATED_TYPE
        defaultStoreShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllStoresByCalculationIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where calculation equals to DEFAULT_CALCULATION
        defaultStoreShouldBeFound("calculation.equals=" + DEFAULT_CALCULATION);

        // Get all the storeList where calculation equals to UPDATED_CALCULATION
        defaultStoreShouldNotBeFound("calculation.equals=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllStoresByCalculationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where calculation not equals to DEFAULT_CALCULATION
        defaultStoreShouldNotBeFound("calculation.notEquals=" + DEFAULT_CALCULATION);

        // Get all the storeList where calculation not equals to UPDATED_CALCULATION
        defaultStoreShouldBeFound("calculation.notEquals=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllStoresByCalculationIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where calculation in DEFAULT_CALCULATION or UPDATED_CALCULATION
        defaultStoreShouldBeFound("calculation.in=" + DEFAULT_CALCULATION + "," + UPDATED_CALCULATION);

        // Get all the storeList where calculation equals to UPDATED_CALCULATION
        defaultStoreShouldNotBeFound("calculation.in=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllStoresByCalculationIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where calculation is not null
        defaultStoreShouldBeFound("calculation.specified=true");

        // Get all the storeList where calculation is null
        defaultStoreShouldNotBeFound("calculation.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByCalculationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where calculation is greater than or equal to DEFAULT_CALCULATION
        defaultStoreShouldBeFound("calculation.greaterThanOrEqual=" + DEFAULT_CALCULATION);

        // Get all the storeList where calculation is greater than or equal to UPDATED_CALCULATION
        defaultStoreShouldNotBeFound("calculation.greaterThanOrEqual=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllStoresByCalculationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where calculation is less than or equal to DEFAULT_CALCULATION
        defaultStoreShouldBeFound("calculation.lessThanOrEqual=" + DEFAULT_CALCULATION);

        // Get all the storeList where calculation is less than or equal to SMALLER_CALCULATION
        defaultStoreShouldNotBeFound("calculation.lessThanOrEqual=" + SMALLER_CALCULATION);
    }

    @Test
    @Transactional
    void getAllStoresByCalculationIsLessThanSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where calculation is less than DEFAULT_CALCULATION
        defaultStoreShouldNotBeFound("calculation.lessThan=" + DEFAULT_CALCULATION);

        // Get all the storeList where calculation is less than UPDATED_CALCULATION
        defaultStoreShouldBeFound("calculation.lessThan=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllStoresByCalculationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where calculation is greater than DEFAULT_CALCULATION
        defaultStoreShouldNotBeFound("calculation.greaterThan=" + DEFAULT_CALCULATION);

        // Get all the storeList where calculation is greater than SMALLER_CALCULATION
        defaultStoreShouldBeFound("calculation.greaterThan=" + SMALLER_CALCULATION);
    }

    @Test
    @Transactional
    void getAllStoresByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where activated equals to DEFAULT_ACTIVATED
        defaultStoreShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the storeList where activated equals to UPDATED_ACTIVATED
        defaultStoreShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllStoresByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where activated not equals to DEFAULT_ACTIVATED
        defaultStoreShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the storeList where activated not equals to UPDATED_ACTIVATED
        defaultStoreShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllStoresByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultStoreShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the storeList where activated equals to UPDATED_ACTIVATED
        defaultStoreShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllStoresByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where activated is not null
        defaultStoreShouldBeFound("activated.specified=true");

        // Get all the storeList where activated is null
        defaultStoreShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where createdBy equals to DEFAULT_CREATED_BY
        defaultStoreShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the storeList where createdBy equals to UPDATED_CREATED_BY
        defaultStoreShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStoresByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where createdBy not equals to DEFAULT_CREATED_BY
        defaultStoreShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the storeList where createdBy not equals to UPDATED_CREATED_BY
        defaultStoreShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStoresByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultStoreShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the storeList where createdBy equals to UPDATED_CREATED_BY
        defaultStoreShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStoresByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where createdBy is not null
        defaultStoreShouldBeFound("createdBy.specified=true");

        // Get all the storeList where createdBy is null
        defaultStoreShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where createdBy contains DEFAULT_CREATED_BY
        defaultStoreShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the storeList where createdBy contains UPDATED_CREATED_BY
        defaultStoreShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStoresByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultStoreShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the storeList where createdBy does not contain UPDATED_CREATED_BY
        defaultStoreShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllStoresByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where createdDate equals to DEFAULT_CREATED_DATE
        defaultStoreShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the storeList where createdDate equals to UPDATED_CREATED_DATE
        defaultStoreShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllStoresByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultStoreShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the storeList where createdDate not equals to UPDATED_CREATED_DATE
        defaultStoreShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllStoresByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultStoreShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the storeList where createdDate equals to UPDATED_CREATED_DATE
        defaultStoreShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllStoresByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where createdDate is not null
        defaultStoreShouldBeFound("createdDate.specified=true");

        // Get all the storeList where createdDate is null
        defaultStoreShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultStoreShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the storeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultStoreShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllStoresByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultStoreShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the storeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultStoreShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllStoresByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultStoreShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the storeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultStoreShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllStoresByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where lastModifiedBy is not null
        defaultStoreShouldBeFound("lastModifiedBy.specified=true");

        // Get all the storeList where lastModifiedBy is null
        defaultStoreShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultStoreShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the storeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultStoreShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllStoresByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultStoreShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the storeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultStoreShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllStoresByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultStoreShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the storeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultStoreShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllStoresByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultStoreShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the storeList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultStoreShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllStoresByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultStoreShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the storeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultStoreShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllStoresByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        // Get all the storeList where lastModifiedDate is not null
        defaultStoreShouldBeFound("lastModifiedDate.specified=true");

        // Get all the storeList where lastModifiedDate is null
        defaultStoreShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllStoresByProductStoreRelIsEqualToSomething() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);
        ProductStoreRel productStoreRel = ProductStoreRelResourceIT.createEntity(em);
        em.persist(productStoreRel);
        em.flush();
        store.addProductStoreRel(productStoreRel);
        storeRepository.saveAndFlush(store);
        Long productStoreRelId = productStoreRel.getId();

        // Get all the storeList where productStoreRel equals to productStoreRelId
        defaultStoreShouldBeFound("productStoreRelId.equals=" + productStoreRelId);

        // Get all the storeList where productStoreRel equals to (productStoreRelId + 1)
        defaultStoreShouldNotBeFound("productStoreRelId.equals=" + (productStoreRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultStoreShouldBeFound(String filter) throws Exception {
        restStoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(store.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restStoreMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultStoreShouldNotBeFound(String filter) throws Exception {
        restStoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restStoreMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingStore() throws Exception {
        // Get the store
        restStoreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store
        Store updatedStore = storeRepository.findById(store.getId()).get();
        // Disconnect from session so that the updates on updatedStore are not directly saved in db
        em.detach(updatedStore);
        updatedStore
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .calculation(UPDATED_CALCULATION)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        StoreDTO storeDTO = storeMapper.toDto(updatedStore);

        restStoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
            .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStore.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testStore.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testStore.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testStore.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStore.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStore.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testStore.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, storeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateStoreWithPatch() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store using partial update
        Store partialUpdatedStore = new Store();
        partialUpdatedStore.setId(store.getId());

        partialUpdatedStore
            .name(UPDATED_NAME)
            .calculation(UPDATED_CALCULATION)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStore.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStore))
            )
            .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStore.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testStore.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testStore.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testStore.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStore.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStore.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testStore.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateStoreWithPatch() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeUpdate = storeRepository.findAll().size();

        // Update the store using partial update
        Store partialUpdatedStore = new Store();
        partialUpdatedStore.setId(store.getId());

        partialUpdatedStore
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .calculation(UPDATED_CALCULATION)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedStore.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedStore))
            )
            .andExpect(status().isOk());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
        Store testStore = storeList.get(storeList.size() - 1);
        assertThat(testStore.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testStore.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testStore.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testStore.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testStore.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testStore.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testStore.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testStore.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, storeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(storeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamStore() throws Exception {
        int databaseSizeBeforeUpdate = storeRepository.findAll().size();
        store.setId(count.incrementAndGet());

        // Create the Store
        StoreDTO storeDTO = storeMapper.toDto(store);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restStoreMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(storeDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Store in the database
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteStore() throws Exception {
        // Initialize the database
        storeRepository.saveAndFlush(store);

        int databaseSizeBeforeDelete = storeRepository.findAll().size();

        // Delete the store
        restStoreMockMvc
            .perform(delete(ENTITY_API_URL_ID, store.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Store> storeList = storeRepository.findAll();
        assertThat(storeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
