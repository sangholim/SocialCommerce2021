package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.domain.ProductStoreRel;
import com.toy.project.domain.Store;
import com.toy.project.repository.ProductStoreRelRepository;
import com.toy.project.service.criteria.ProductStoreRelCriteria;
import com.toy.project.service.dto.ProductStoreRelDTO;
import com.toy.project.service.mapper.ProductStoreRelMapper;
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
 * Integration tests for the {@link ProductStoreRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductStoreRelResourceIT {

    private static final Boolean DEFAULT_USE_CALCULATION = false;
    private static final Boolean UPDATED_USE_CALCULATION = true;

    private static final Integer DEFAULT_CALCULATION = 1;
    private static final Integer UPDATED_CALCULATION = 2;
    private static final Integer SMALLER_CALCULATION = 1 - 1;

    private static final Instant DEFAULT_CALCULATION_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATION_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CALCULATION_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATION_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/product-store-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductStoreRelRepository productStoreRelRepository;

    @Autowired
    private ProductStoreRelMapper productStoreRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductStoreRelMockMvc;

    private ProductStoreRel productStoreRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStoreRel createEntity(EntityManager em) {
        ProductStoreRel productStoreRel = new ProductStoreRel()
            .useCalculation(DEFAULT_USE_CALCULATION)
            .calculation(DEFAULT_CALCULATION)
            .calculationDateFrom(DEFAULT_CALCULATION_DATE_FROM)
            .calculationDateTo(DEFAULT_CALCULATION_DATE_TO)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productStoreRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStoreRel createUpdatedEntity(EntityManager em) {
        ProductStoreRel productStoreRel = new ProductStoreRel()
            .useCalculation(UPDATED_USE_CALCULATION)
            .calculation(UPDATED_CALCULATION)
            .calculationDateFrom(UPDATED_CALCULATION_DATE_FROM)
            .calculationDateTo(UPDATED_CALCULATION_DATE_TO)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productStoreRel;
    }

    @BeforeEach
    public void initTest() {
        productStoreRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductStoreRel() throws Exception {
        int databaseSizeBeforeCreate = productStoreRelRepository.findAll().size();
        // Create the ProductStoreRel
        ProductStoreRelDTO productStoreRelDTO = productStoreRelMapper.toDto(productStoreRel);
        restProductStoreRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productStoreRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductStoreRel in the database
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductStoreRel testProductStoreRel = productStoreRelList.get(productStoreRelList.size() - 1);
        assertThat(testProductStoreRel.getUseCalculation()).isEqualTo(DEFAULT_USE_CALCULATION);
        assertThat(testProductStoreRel.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testProductStoreRel.getCalculationDateFrom()).isEqualTo(DEFAULT_CALCULATION_DATE_FROM);
        assertThat(testProductStoreRel.getCalculationDateTo()).isEqualTo(DEFAULT_CALCULATION_DATE_TO);
        assertThat(testProductStoreRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductStoreRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductStoreRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductStoreRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductStoreRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductStoreRelWithExistingId() throws Exception {
        // Create the ProductStoreRel with an existing ID
        productStoreRel.setId(1L);
        ProductStoreRelDTO productStoreRelDTO = productStoreRelMapper.toDto(productStoreRel);

        int databaseSizeBeforeCreate = productStoreRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductStoreRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productStoreRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreRel in the database
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductStoreRels() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList
        restProductStoreRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStoreRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].useCalculation").value(hasItem(DEFAULT_USE_CALCULATION.booleanValue())))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].calculationDateFrom").value(hasItem(DEFAULT_CALCULATION_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].calculationDateTo").value(hasItem(DEFAULT_CALCULATION_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductStoreRel() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get the productStoreRel
        restProductStoreRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productStoreRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productStoreRel.getId().intValue()))
            .andExpect(jsonPath("$.useCalculation").value(DEFAULT_USE_CALCULATION.booleanValue()))
            .andExpect(jsonPath("$.calculation").value(DEFAULT_CALCULATION))
            .andExpect(jsonPath("$.calculationDateFrom").value(DEFAULT_CALCULATION_DATE_FROM.toString()))
            .andExpect(jsonPath("$.calculationDateTo").value(DEFAULT_CALCULATION_DATE_TO.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductStoreRelsByIdFiltering() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        Long id = productStoreRel.getId();

        defaultProductStoreRelShouldBeFound("id.equals=" + id);
        defaultProductStoreRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductStoreRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductStoreRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductStoreRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductStoreRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByUseCalculationIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where useCalculation equals to DEFAULT_USE_CALCULATION
        defaultProductStoreRelShouldBeFound("useCalculation.equals=" + DEFAULT_USE_CALCULATION);

        // Get all the productStoreRelList where useCalculation equals to UPDATED_USE_CALCULATION
        defaultProductStoreRelShouldNotBeFound("useCalculation.equals=" + UPDATED_USE_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByUseCalculationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where useCalculation not equals to DEFAULT_USE_CALCULATION
        defaultProductStoreRelShouldNotBeFound("useCalculation.notEquals=" + DEFAULT_USE_CALCULATION);

        // Get all the productStoreRelList where useCalculation not equals to UPDATED_USE_CALCULATION
        defaultProductStoreRelShouldBeFound("useCalculation.notEquals=" + UPDATED_USE_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByUseCalculationIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where useCalculation in DEFAULT_USE_CALCULATION or UPDATED_USE_CALCULATION
        defaultProductStoreRelShouldBeFound("useCalculation.in=" + DEFAULT_USE_CALCULATION + "," + UPDATED_USE_CALCULATION);

        // Get all the productStoreRelList where useCalculation equals to UPDATED_USE_CALCULATION
        defaultProductStoreRelShouldNotBeFound("useCalculation.in=" + UPDATED_USE_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByUseCalculationIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where useCalculation is not null
        defaultProductStoreRelShouldBeFound("useCalculation.specified=true");

        // Get all the productStoreRelList where useCalculation is null
        defaultProductStoreRelShouldNotBeFound("useCalculation.specified=false");
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculation equals to DEFAULT_CALCULATION
        defaultProductStoreRelShouldBeFound("calculation.equals=" + DEFAULT_CALCULATION);

        // Get all the productStoreRelList where calculation equals to UPDATED_CALCULATION
        defaultProductStoreRelShouldNotBeFound("calculation.equals=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculation not equals to DEFAULT_CALCULATION
        defaultProductStoreRelShouldNotBeFound("calculation.notEquals=" + DEFAULT_CALCULATION);

        // Get all the productStoreRelList where calculation not equals to UPDATED_CALCULATION
        defaultProductStoreRelShouldBeFound("calculation.notEquals=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculation in DEFAULT_CALCULATION or UPDATED_CALCULATION
        defaultProductStoreRelShouldBeFound("calculation.in=" + DEFAULT_CALCULATION + "," + UPDATED_CALCULATION);

        // Get all the productStoreRelList where calculation equals to UPDATED_CALCULATION
        defaultProductStoreRelShouldNotBeFound("calculation.in=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculation is not null
        defaultProductStoreRelShouldBeFound("calculation.specified=true");

        // Get all the productStoreRelList where calculation is null
        defaultProductStoreRelShouldNotBeFound("calculation.specified=false");
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculation is greater than or equal to DEFAULT_CALCULATION
        defaultProductStoreRelShouldBeFound("calculation.greaterThanOrEqual=" + DEFAULT_CALCULATION);

        // Get all the productStoreRelList where calculation is greater than or equal to UPDATED_CALCULATION
        defaultProductStoreRelShouldNotBeFound("calculation.greaterThanOrEqual=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculation is less than or equal to DEFAULT_CALCULATION
        defaultProductStoreRelShouldBeFound("calculation.lessThanOrEqual=" + DEFAULT_CALCULATION);

        // Get all the productStoreRelList where calculation is less than or equal to SMALLER_CALCULATION
        defaultProductStoreRelShouldNotBeFound("calculation.lessThanOrEqual=" + SMALLER_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationIsLessThanSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculation is less than DEFAULT_CALCULATION
        defaultProductStoreRelShouldNotBeFound("calculation.lessThan=" + DEFAULT_CALCULATION);

        // Get all the productStoreRelList where calculation is less than UPDATED_CALCULATION
        defaultProductStoreRelShouldBeFound("calculation.lessThan=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculation is greater than DEFAULT_CALCULATION
        defaultProductStoreRelShouldNotBeFound("calculation.greaterThan=" + DEFAULT_CALCULATION);

        // Get all the productStoreRelList where calculation is greater than SMALLER_CALCULATION
        defaultProductStoreRelShouldBeFound("calculation.greaterThan=" + SMALLER_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationDateFromIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculationDateFrom equals to DEFAULT_CALCULATION_DATE_FROM
        defaultProductStoreRelShouldBeFound("calculationDateFrom.equals=" + DEFAULT_CALCULATION_DATE_FROM);

        // Get all the productStoreRelList where calculationDateFrom equals to UPDATED_CALCULATION_DATE_FROM
        defaultProductStoreRelShouldNotBeFound("calculationDateFrom.equals=" + UPDATED_CALCULATION_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationDateFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculationDateFrom not equals to DEFAULT_CALCULATION_DATE_FROM
        defaultProductStoreRelShouldNotBeFound("calculationDateFrom.notEquals=" + DEFAULT_CALCULATION_DATE_FROM);

        // Get all the productStoreRelList where calculationDateFrom not equals to UPDATED_CALCULATION_DATE_FROM
        defaultProductStoreRelShouldBeFound("calculationDateFrom.notEquals=" + UPDATED_CALCULATION_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationDateFromIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculationDateFrom in DEFAULT_CALCULATION_DATE_FROM or UPDATED_CALCULATION_DATE_FROM
        defaultProductStoreRelShouldBeFound(
            "calculationDateFrom.in=" + DEFAULT_CALCULATION_DATE_FROM + "," + UPDATED_CALCULATION_DATE_FROM
        );

        // Get all the productStoreRelList where calculationDateFrom equals to UPDATED_CALCULATION_DATE_FROM
        defaultProductStoreRelShouldNotBeFound("calculationDateFrom.in=" + UPDATED_CALCULATION_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationDateFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculationDateFrom is not null
        defaultProductStoreRelShouldBeFound("calculationDateFrom.specified=true");

        // Get all the productStoreRelList where calculationDateFrom is null
        defaultProductStoreRelShouldNotBeFound("calculationDateFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationDateToIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculationDateTo equals to DEFAULT_CALCULATION_DATE_TO
        defaultProductStoreRelShouldBeFound("calculationDateTo.equals=" + DEFAULT_CALCULATION_DATE_TO);

        // Get all the productStoreRelList where calculationDateTo equals to UPDATED_CALCULATION_DATE_TO
        defaultProductStoreRelShouldNotBeFound("calculationDateTo.equals=" + UPDATED_CALCULATION_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationDateToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculationDateTo not equals to DEFAULT_CALCULATION_DATE_TO
        defaultProductStoreRelShouldNotBeFound("calculationDateTo.notEquals=" + DEFAULT_CALCULATION_DATE_TO);

        // Get all the productStoreRelList where calculationDateTo not equals to UPDATED_CALCULATION_DATE_TO
        defaultProductStoreRelShouldBeFound("calculationDateTo.notEquals=" + UPDATED_CALCULATION_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationDateToIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculationDateTo in DEFAULT_CALCULATION_DATE_TO or UPDATED_CALCULATION_DATE_TO
        defaultProductStoreRelShouldBeFound("calculationDateTo.in=" + DEFAULT_CALCULATION_DATE_TO + "," + UPDATED_CALCULATION_DATE_TO);

        // Get all the productStoreRelList where calculationDateTo equals to UPDATED_CALCULATION_DATE_TO
        defaultProductStoreRelShouldNotBeFound("calculationDateTo.in=" + UPDATED_CALCULATION_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCalculationDateToIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where calculationDateTo is not null
        defaultProductStoreRelShouldBeFound("calculationDateTo.specified=true");

        // Get all the productStoreRelList where calculationDateTo is null
        defaultProductStoreRelShouldNotBeFound("calculationDateTo.specified=false");
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductStoreRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productStoreRelList where activated equals to UPDATED_ACTIVATED
        defaultProductStoreRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductStoreRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productStoreRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductStoreRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductStoreRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productStoreRelList where activated equals to UPDATED_ACTIVATED
        defaultProductStoreRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where activated is not null
        defaultProductStoreRelShouldBeFound("activated.specified=true");

        // Get all the productStoreRelList where activated is null
        defaultProductStoreRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductStoreRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productStoreRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductStoreRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductStoreRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productStoreRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductStoreRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductStoreRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productStoreRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductStoreRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where createdBy is not null
        defaultProductStoreRelShouldBeFound("createdBy.specified=true");

        // Get all the productStoreRelList where createdBy is null
        defaultProductStoreRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductStoreRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productStoreRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductStoreRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductStoreRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productStoreRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductStoreRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductStoreRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productStoreRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductStoreRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductStoreRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productStoreRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductStoreRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductStoreRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productStoreRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductStoreRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where createdDate is not null
        defaultProductStoreRelShouldBeFound("createdDate.specified=true");

        // Get all the productStoreRelList where createdDate is null
        defaultProductStoreRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductStoreRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productStoreRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductStoreRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductStoreRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productStoreRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductStoreRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductStoreRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productStoreRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductStoreRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where lastModifiedBy is not null
        defaultProductStoreRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productStoreRelList where lastModifiedBy is null
        defaultProductStoreRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductStoreRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productStoreRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductStoreRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductStoreRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productStoreRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductStoreRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductStoreRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productStoreRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductStoreRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductStoreRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productStoreRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductStoreRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductStoreRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productStoreRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductStoreRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        // Get all the productStoreRelList where lastModifiedDate is not null
        defaultProductStoreRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productStoreRelList where lastModifiedDate is null
        defaultProductStoreRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productStoreRel.setProduct(product);
        productStoreRelRepository.saveAndFlush(productStoreRel);
        Long productId = product.getId();

        // Get all the productStoreRelList where product equals to productId
        defaultProductStoreRelShouldBeFound("productId.equals=" + productId);

        // Get all the productStoreRelList where product equals to (productId + 1)
        defaultProductStoreRelShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductStoreRelsByStoreIsEqualToSomething() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);
        Store store = StoreResourceIT.createEntity(em);
        em.persist(store);
        em.flush();
        productStoreRel.setStore(store);
        productStoreRelRepository.saveAndFlush(productStoreRel);
        Long storeId = store.getId();

        // Get all the productStoreRelList where store equals to storeId
        defaultProductStoreRelShouldBeFound("storeId.equals=" + storeId);

        // Get all the productStoreRelList where store equals to (storeId + 1)
        defaultProductStoreRelShouldNotBeFound("storeId.equals=" + (storeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductStoreRelShouldBeFound(String filter) throws Exception {
        restProductStoreRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStoreRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].useCalculation").value(hasItem(DEFAULT_USE_CALCULATION.booleanValue())))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].calculationDateFrom").value(hasItem(DEFAULT_CALCULATION_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].calculationDateTo").value(hasItem(DEFAULT_CALCULATION_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductStoreRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductStoreRelShouldNotBeFound(String filter) throws Exception {
        restProductStoreRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductStoreRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductStoreRel() throws Exception {
        // Get the productStoreRel
        restProductStoreRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductStoreRel() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        int databaseSizeBeforeUpdate = productStoreRelRepository.findAll().size();

        // Update the productStoreRel
        ProductStoreRel updatedProductStoreRel = productStoreRelRepository.findById(productStoreRel.getId()).get();
        // Disconnect from session so that the updates on updatedProductStoreRel are not directly saved in db
        em.detach(updatedProductStoreRel);
        updatedProductStoreRel
            .useCalculation(UPDATED_USE_CALCULATION)
            .calculation(UPDATED_CALCULATION)
            .calculationDateFrom(UPDATED_CALCULATION_DATE_FROM)
            .calculationDateTo(UPDATED_CALCULATION_DATE_TO)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductStoreRelDTO productStoreRelDTO = productStoreRelMapper.toDto(updatedProductStoreRel);

        restProductStoreRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productStoreRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productStoreRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductStoreRel in the database
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeUpdate);
        ProductStoreRel testProductStoreRel = productStoreRelList.get(productStoreRelList.size() - 1);
        assertThat(testProductStoreRel.getUseCalculation()).isEqualTo(UPDATED_USE_CALCULATION);
        assertThat(testProductStoreRel.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProductStoreRel.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testProductStoreRel.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProductStoreRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductStoreRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductStoreRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductStoreRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductStoreRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductStoreRel() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRelRepository.findAll().size();
        productStoreRel.setId(count.incrementAndGet());

        // Create the ProductStoreRel
        ProductStoreRelDTO productStoreRelDTO = productStoreRelMapper.toDto(productStoreRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductStoreRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productStoreRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productStoreRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreRel in the database
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductStoreRel() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRelRepository.findAll().size();
        productStoreRel.setId(count.incrementAndGet());

        // Create the ProductStoreRel
        ProductStoreRelDTO productStoreRelDTO = productStoreRelMapper.toDto(productStoreRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductStoreRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productStoreRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreRel in the database
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductStoreRel() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRelRepository.findAll().size();
        productStoreRel.setId(count.incrementAndGet());

        // Create the ProductStoreRel
        ProductStoreRelDTO productStoreRelDTO = productStoreRelMapper.toDto(productStoreRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductStoreRelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productStoreRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductStoreRel in the database
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductStoreRelWithPatch() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        int databaseSizeBeforeUpdate = productStoreRelRepository.findAll().size();

        // Update the productStoreRel using partial update
        ProductStoreRel partialUpdatedProductStoreRel = new ProductStoreRel();
        partialUpdatedProductStoreRel.setId(productStoreRel.getId());

        partialUpdatedProductStoreRel
            .useCalculation(UPDATED_USE_CALCULATION)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restProductStoreRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductStoreRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductStoreRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductStoreRel in the database
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeUpdate);
        ProductStoreRel testProductStoreRel = productStoreRelList.get(productStoreRelList.size() - 1);
        assertThat(testProductStoreRel.getUseCalculation()).isEqualTo(UPDATED_USE_CALCULATION);
        assertThat(testProductStoreRel.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testProductStoreRel.getCalculationDateFrom()).isEqualTo(DEFAULT_CALCULATION_DATE_FROM);
        assertThat(testProductStoreRel.getCalculationDateTo()).isEqualTo(DEFAULT_CALCULATION_DATE_TO);
        assertThat(testProductStoreRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductStoreRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductStoreRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductStoreRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductStoreRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductStoreRelWithPatch() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        int databaseSizeBeforeUpdate = productStoreRelRepository.findAll().size();

        // Update the productStoreRel using partial update
        ProductStoreRel partialUpdatedProductStoreRel = new ProductStoreRel();
        partialUpdatedProductStoreRel.setId(productStoreRel.getId());

        partialUpdatedProductStoreRel
            .useCalculation(UPDATED_USE_CALCULATION)
            .calculation(UPDATED_CALCULATION)
            .calculationDateFrom(UPDATED_CALCULATION_DATE_FROM)
            .calculationDateTo(UPDATED_CALCULATION_DATE_TO)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductStoreRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductStoreRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductStoreRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductStoreRel in the database
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeUpdate);
        ProductStoreRel testProductStoreRel = productStoreRelList.get(productStoreRelList.size() - 1);
        assertThat(testProductStoreRel.getUseCalculation()).isEqualTo(UPDATED_USE_CALCULATION);
        assertThat(testProductStoreRel.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProductStoreRel.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testProductStoreRel.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProductStoreRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductStoreRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductStoreRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductStoreRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductStoreRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductStoreRel() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRelRepository.findAll().size();
        productStoreRel.setId(count.incrementAndGet());

        // Create the ProductStoreRel
        ProductStoreRelDTO productStoreRelDTO = productStoreRelMapper.toDto(productStoreRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductStoreRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productStoreRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productStoreRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreRel in the database
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductStoreRel() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRelRepository.findAll().size();
        productStoreRel.setId(count.incrementAndGet());

        // Create the ProductStoreRel
        ProductStoreRelDTO productStoreRelDTO = productStoreRelMapper.toDto(productStoreRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductStoreRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productStoreRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductStoreRel in the database
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductStoreRel() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRelRepository.findAll().size();
        productStoreRel.setId(count.incrementAndGet());

        // Create the ProductStoreRel
        ProductStoreRelDTO productStoreRelDTO = productStoreRelMapper.toDto(productStoreRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductStoreRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productStoreRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductStoreRel in the database
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductStoreRel() throws Exception {
        // Initialize the database
        productStoreRelRepository.saveAndFlush(productStoreRel);

        int databaseSizeBeforeDelete = productStoreRelRepository.findAll().size();

        // Delete the productStoreRel
        restProductStoreRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productStoreRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductStoreRel> productStoreRelList = productStoreRelRepository.findAll();
        assertThat(productStoreRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
