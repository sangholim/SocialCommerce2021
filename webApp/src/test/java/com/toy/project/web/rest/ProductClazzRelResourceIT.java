package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Clazz;
import com.toy.project.domain.Product;
import com.toy.project.domain.ProductClazzRel;
import com.toy.project.repository.ProductClazzRelRepository;
import com.toy.project.service.criteria.ProductClazzRelCriteria;
import com.toy.project.service.dto.ProductClazzRelDTO;
import com.toy.project.service.mapper.ProductClazzRelMapper;
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
 * Integration tests for the {@link ProductClazzRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductClazzRelResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-clazz-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductClazzRelRepository productClazzRelRepository;

    @Autowired
    private ProductClazzRelMapper productClazzRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductClazzRelMockMvc;

    private ProductClazzRel productClazzRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductClazzRel createEntity(EntityManager em) {
        ProductClazzRel productClazzRel = new ProductClazzRel()
            .useCalculation(DEFAULT_USE_CALCULATION)
            .calculation(DEFAULT_CALCULATION)
            .calculationDateFrom(DEFAULT_CALCULATION_DATE_FROM)
            .calculationDateTo(DEFAULT_CALCULATION_DATE_TO)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productClazzRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductClazzRel createUpdatedEntity(EntityManager em) {
        ProductClazzRel productClazzRel = new ProductClazzRel()
            .useCalculation(UPDATED_USE_CALCULATION)
            .calculation(UPDATED_CALCULATION)
            .calculationDateFrom(UPDATED_CALCULATION_DATE_FROM)
            .calculationDateTo(UPDATED_CALCULATION_DATE_TO)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productClazzRel;
    }

    @BeforeEach
    public void initTest() {
        productClazzRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductClazzRel() throws Exception {
        int databaseSizeBeforeCreate = productClazzRelRepository.findAll().size();
        // Create the ProductClazzRel
        ProductClazzRelDTO productClazzRelDTO = productClazzRelMapper.toDto(productClazzRel);
        restProductClazzRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productClazzRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductClazzRel in the database
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductClazzRel testProductClazzRel = productClazzRelList.get(productClazzRelList.size() - 1);
        assertThat(testProductClazzRel.getUseCalculation()).isEqualTo(DEFAULT_USE_CALCULATION);
        assertThat(testProductClazzRel.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testProductClazzRel.getCalculationDateFrom()).isEqualTo(DEFAULT_CALCULATION_DATE_FROM);
        assertThat(testProductClazzRel.getCalculationDateTo()).isEqualTo(DEFAULT_CALCULATION_DATE_TO);
        assertThat(testProductClazzRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductClazzRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductClazzRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductClazzRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductClazzRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductClazzRelWithExistingId() throws Exception {
        // Create the ProductClazzRel with an existing ID
        productClazzRel.setId(1L);
        ProductClazzRelDTO productClazzRelDTO = productClazzRelMapper.toDto(productClazzRel);

        int databaseSizeBeforeCreate = productClazzRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductClazzRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productClazzRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductClazzRel in the database
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductClazzRels() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList
        restProductClazzRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productClazzRel.getId().intValue())))
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
    void getProductClazzRel() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get the productClazzRel
        restProductClazzRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productClazzRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productClazzRel.getId().intValue()))
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
    void getProductClazzRelsByIdFiltering() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        Long id = productClazzRel.getId();

        defaultProductClazzRelShouldBeFound("id.equals=" + id);
        defaultProductClazzRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductClazzRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductClazzRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductClazzRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductClazzRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByUseCalculationIsEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where useCalculation equals to DEFAULT_USE_CALCULATION
        defaultProductClazzRelShouldBeFound("useCalculation.equals=" + DEFAULT_USE_CALCULATION);

        // Get all the productClazzRelList where useCalculation equals to UPDATED_USE_CALCULATION
        defaultProductClazzRelShouldNotBeFound("useCalculation.equals=" + UPDATED_USE_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByUseCalculationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where useCalculation not equals to DEFAULT_USE_CALCULATION
        defaultProductClazzRelShouldNotBeFound("useCalculation.notEquals=" + DEFAULT_USE_CALCULATION);

        // Get all the productClazzRelList where useCalculation not equals to UPDATED_USE_CALCULATION
        defaultProductClazzRelShouldBeFound("useCalculation.notEquals=" + UPDATED_USE_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByUseCalculationIsInShouldWork() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where useCalculation in DEFAULT_USE_CALCULATION or UPDATED_USE_CALCULATION
        defaultProductClazzRelShouldBeFound("useCalculation.in=" + DEFAULT_USE_CALCULATION + "," + UPDATED_USE_CALCULATION);

        // Get all the productClazzRelList where useCalculation equals to UPDATED_USE_CALCULATION
        defaultProductClazzRelShouldNotBeFound("useCalculation.in=" + UPDATED_USE_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByUseCalculationIsNullOrNotNull() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where useCalculation is not null
        defaultProductClazzRelShouldBeFound("useCalculation.specified=true");

        // Get all the productClazzRelList where useCalculation is null
        defaultProductClazzRelShouldNotBeFound("useCalculation.specified=false");
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationIsEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculation equals to DEFAULT_CALCULATION
        defaultProductClazzRelShouldBeFound("calculation.equals=" + DEFAULT_CALCULATION);

        // Get all the productClazzRelList where calculation equals to UPDATED_CALCULATION
        defaultProductClazzRelShouldNotBeFound("calculation.equals=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculation not equals to DEFAULT_CALCULATION
        defaultProductClazzRelShouldNotBeFound("calculation.notEquals=" + DEFAULT_CALCULATION);

        // Get all the productClazzRelList where calculation not equals to UPDATED_CALCULATION
        defaultProductClazzRelShouldBeFound("calculation.notEquals=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationIsInShouldWork() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculation in DEFAULT_CALCULATION or UPDATED_CALCULATION
        defaultProductClazzRelShouldBeFound("calculation.in=" + DEFAULT_CALCULATION + "," + UPDATED_CALCULATION);

        // Get all the productClazzRelList where calculation equals to UPDATED_CALCULATION
        defaultProductClazzRelShouldNotBeFound("calculation.in=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationIsNullOrNotNull() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculation is not null
        defaultProductClazzRelShouldBeFound("calculation.specified=true");

        // Get all the productClazzRelList where calculation is null
        defaultProductClazzRelShouldNotBeFound("calculation.specified=false");
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculation is greater than or equal to DEFAULT_CALCULATION
        defaultProductClazzRelShouldBeFound("calculation.greaterThanOrEqual=" + DEFAULT_CALCULATION);

        // Get all the productClazzRelList where calculation is greater than or equal to UPDATED_CALCULATION
        defaultProductClazzRelShouldNotBeFound("calculation.greaterThanOrEqual=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculation is less than or equal to DEFAULT_CALCULATION
        defaultProductClazzRelShouldBeFound("calculation.lessThanOrEqual=" + DEFAULT_CALCULATION);

        // Get all the productClazzRelList where calculation is less than or equal to SMALLER_CALCULATION
        defaultProductClazzRelShouldNotBeFound("calculation.lessThanOrEqual=" + SMALLER_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationIsLessThanSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculation is less than DEFAULT_CALCULATION
        defaultProductClazzRelShouldNotBeFound("calculation.lessThan=" + DEFAULT_CALCULATION);

        // Get all the productClazzRelList where calculation is less than UPDATED_CALCULATION
        defaultProductClazzRelShouldBeFound("calculation.lessThan=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculation is greater than DEFAULT_CALCULATION
        defaultProductClazzRelShouldNotBeFound("calculation.greaterThan=" + DEFAULT_CALCULATION);

        // Get all the productClazzRelList where calculation is greater than SMALLER_CALCULATION
        defaultProductClazzRelShouldBeFound("calculation.greaterThan=" + SMALLER_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationDateFromIsEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculationDateFrom equals to DEFAULT_CALCULATION_DATE_FROM
        defaultProductClazzRelShouldBeFound("calculationDateFrom.equals=" + DEFAULT_CALCULATION_DATE_FROM);

        // Get all the productClazzRelList where calculationDateFrom equals to UPDATED_CALCULATION_DATE_FROM
        defaultProductClazzRelShouldNotBeFound("calculationDateFrom.equals=" + UPDATED_CALCULATION_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationDateFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculationDateFrom not equals to DEFAULT_CALCULATION_DATE_FROM
        defaultProductClazzRelShouldNotBeFound("calculationDateFrom.notEquals=" + DEFAULT_CALCULATION_DATE_FROM);

        // Get all the productClazzRelList where calculationDateFrom not equals to UPDATED_CALCULATION_DATE_FROM
        defaultProductClazzRelShouldBeFound("calculationDateFrom.notEquals=" + UPDATED_CALCULATION_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationDateFromIsInShouldWork() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculationDateFrom in DEFAULT_CALCULATION_DATE_FROM or UPDATED_CALCULATION_DATE_FROM
        defaultProductClazzRelShouldBeFound(
            "calculationDateFrom.in=" + DEFAULT_CALCULATION_DATE_FROM + "," + UPDATED_CALCULATION_DATE_FROM
        );

        // Get all the productClazzRelList where calculationDateFrom equals to UPDATED_CALCULATION_DATE_FROM
        defaultProductClazzRelShouldNotBeFound("calculationDateFrom.in=" + UPDATED_CALCULATION_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationDateFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculationDateFrom is not null
        defaultProductClazzRelShouldBeFound("calculationDateFrom.specified=true");

        // Get all the productClazzRelList where calculationDateFrom is null
        defaultProductClazzRelShouldNotBeFound("calculationDateFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationDateToIsEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculationDateTo equals to DEFAULT_CALCULATION_DATE_TO
        defaultProductClazzRelShouldBeFound("calculationDateTo.equals=" + DEFAULT_CALCULATION_DATE_TO);

        // Get all the productClazzRelList where calculationDateTo equals to UPDATED_CALCULATION_DATE_TO
        defaultProductClazzRelShouldNotBeFound("calculationDateTo.equals=" + UPDATED_CALCULATION_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationDateToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculationDateTo not equals to DEFAULT_CALCULATION_DATE_TO
        defaultProductClazzRelShouldNotBeFound("calculationDateTo.notEquals=" + DEFAULT_CALCULATION_DATE_TO);

        // Get all the productClazzRelList where calculationDateTo not equals to UPDATED_CALCULATION_DATE_TO
        defaultProductClazzRelShouldBeFound("calculationDateTo.notEquals=" + UPDATED_CALCULATION_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationDateToIsInShouldWork() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculationDateTo in DEFAULT_CALCULATION_DATE_TO or UPDATED_CALCULATION_DATE_TO
        defaultProductClazzRelShouldBeFound("calculationDateTo.in=" + DEFAULT_CALCULATION_DATE_TO + "," + UPDATED_CALCULATION_DATE_TO);

        // Get all the productClazzRelList where calculationDateTo equals to UPDATED_CALCULATION_DATE_TO
        defaultProductClazzRelShouldNotBeFound("calculationDateTo.in=" + UPDATED_CALCULATION_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCalculationDateToIsNullOrNotNull() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where calculationDateTo is not null
        defaultProductClazzRelShouldBeFound("calculationDateTo.specified=true");

        // Get all the productClazzRelList where calculationDateTo is null
        defaultProductClazzRelShouldNotBeFound("calculationDateTo.specified=false");
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductClazzRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productClazzRelList where activated equals to UPDATED_ACTIVATED
        defaultProductClazzRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductClazzRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productClazzRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductClazzRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductClazzRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productClazzRelList where activated equals to UPDATED_ACTIVATED
        defaultProductClazzRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where activated is not null
        defaultProductClazzRelShouldBeFound("activated.specified=true");

        // Get all the productClazzRelList where activated is null
        defaultProductClazzRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductClazzRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productClazzRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductClazzRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductClazzRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productClazzRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductClazzRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductClazzRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productClazzRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductClazzRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where createdBy is not null
        defaultProductClazzRelShouldBeFound("createdBy.specified=true");

        // Get all the productClazzRelList where createdBy is null
        defaultProductClazzRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductClazzRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productClazzRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductClazzRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductClazzRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productClazzRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductClazzRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductClazzRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productClazzRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductClazzRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductClazzRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productClazzRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductClazzRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductClazzRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productClazzRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductClazzRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where createdDate is not null
        defaultProductClazzRelShouldBeFound("createdDate.specified=true");

        // Get all the productClazzRelList where createdDate is null
        defaultProductClazzRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductClazzRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productClazzRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductClazzRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductClazzRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productClazzRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductClazzRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductClazzRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productClazzRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductClazzRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where lastModifiedBy is not null
        defaultProductClazzRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productClazzRelList where lastModifiedBy is null
        defaultProductClazzRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductClazzRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productClazzRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductClazzRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductClazzRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productClazzRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductClazzRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductClazzRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productClazzRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductClazzRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductClazzRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productClazzRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductClazzRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductClazzRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productClazzRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductClazzRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        // Get all the productClazzRelList where lastModifiedDate is not null
        defaultProductClazzRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productClazzRelList where lastModifiedDate is null
        defaultProductClazzRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productClazzRel.setProduct(product);
        productClazzRelRepository.saveAndFlush(productClazzRel);
        Long productId = product.getId();

        // Get all the productClazzRelList where product equals to productId
        defaultProductClazzRelShouldBeFound("productId.equals=" + productId);

        // Get all the productClazzRelList where product equals to (productId + 1)
        defaultProductClazzRelShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductClazzRelsByClazzIsEqualToSomething() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);
        Clazz clazz = ClazzResourceIT.createEntity(em);
        em.persist(clazz);
        em.flush();
        productClazzRel.setClazz(clazz);
        productClazzRelRepository.saveAndFlush(productClazzRel);
        Long clazzId = clazz.getId();

        // Get all the productClazzRelList where clazz equals to clazzId
        defaultProductClazzRelShouldBeFound("clazzId.equals=" + clazzId);

        // Get all the productClazzRelList where clazz equals to (clazzId + 1)
        defaultProductClazzRelShouldNotBeFound("clazzId.equals=" + (clazzId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductClazzRelShouldBeFound(String filter) throws Exception {
        restProductClazzRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productClazzRel.getId().intValue())))
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
        restProductClazzRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductClazzRelShouldNotBeFound(String filter) throws Exception {
        restProductClazzRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductClazzRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductClazzRel() throws Exception {
        // Get the productClazzRel
        restProductClazzRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductClazzRel() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        int databaseSizeBeforeUpdate = productClazzRelRepository.findAll().size();

        // Update the productClazzRel
        ProductClazzRel updatedProductClazzRel = productClazzRelRepository.findById(productClazzRel.getId()).get();
        // Disconnect from session so that the updates on updatedProductClazzRel are not directly saved in db
        em.detach(updatedProductClazzRel);
        updatedProductClazzRel
            .useCalculation(UPDATED_USE_CALCULATION)
            .calculation(UPDATED_CALCULATION)
            .calculationDateFrom(UPDATED_CALCULATION_DATE_FROM)
            .calculationDateTo(UPDATED_CALCULATION_DATE_TO)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductClazzRelDTO productClazzRelDTO = productClazzRelMapper.toDto(updatedProductClazzRel);

        restProductClazzRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productClazzRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productClazzRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductClazzRel in the database
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeUpdate);
        ProductClazzRel testProductClazzRel = productClazzRelList.get(productClazzRelList.size() - 1);
        assertThat(testProductClazzRel.getUseCalculation()).isEqualTo(UPDATED_USE_CALCULATION);
        assertThat(testProductClazzRel.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProductClazzRel.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testProductClazzRel.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProductClazzRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductClazzRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductClazzRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductClazzRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductClazzRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductClazzRel() throws Exception {
        int databaseSizeBeforeUpdate = productClazzRelRepository.findAll().size();
        productClazzRel.setId(count.incrementAndGet());

        // Create the ProductClazzRel
        ProductClazzRelDTO productClazzRelDTO = productClazzRelMapper.toDto(productClazzRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductClazzRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productClazzRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productClazzRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductClazzRel in the database
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductClazzRel() throws Exception {
        int databaseSizeBeforeUpdate = productClazzRelRepository.findAll().size();
        productClazzRel.setId(count.incrementAndGet());

        // Create the ProductClazzRel
        ProductClazzRelDTO productClazzRelDTO = productClazzRelMapper.toDto(productClazzRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductClazzRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productClazzRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductClazzRel in the database
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductClazzRel() throws Exception {
        int databaseSizeBeforeUpdate = productClazzRelRepository.findAll().size();
        productClazzRel.setId(count.incrementAndGet());

        // Create the ProductClazzRel
        ProductClazzRelDTO productClazzRelDTO = productClazzRelMapper.toDto(productClazzRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductClazzRelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productClazzRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductClazzRel in the database
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductClazzRelWithPatch() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        int databaseSizeBeforeUpdate = productClazzRelRepository.findAll().size();

        // Update the productClazzRel using partial update
        ProductClazzRel partialUpdatedProductClazzRel = new ProductClazzRel();
        partialUpdatedProductClazzRel.setId(productClazzRel.getId());

        partialUpdatedProductClazzRel
            .calculation(UPDATED_CALCULATION)
            .calculationDateTo(UPDATED_CALCULATION_DATE_TO)
            .createdDate(UPDATED_CREATED_DATE);

        restProductClazzRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductClazzRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductClazzRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductClazzRel in the database
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeUpdate);
        ProductClazzRel testProductClazzRel = productClazzRelList.get(productClazzRelList.size() - 1);
        assertThat(testProductClazzRel.getUseCalculation()).isEqualTo(DEFAULT_USE_CALCULATION);
        assertThat(testProductClazzRel.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProductClazzRel.getCalculationDateFrom()).isEqualTo(DEFAULT_CALCULATION_DATE_FROM);
        assertThat(testProductClazzRel.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProductClazzRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductClazzRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductClazzRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductClazzRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductClazzRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductClazzRelWithPatch() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        int databaseSizeBeforeUpdate = productClazzRelRepository.findAll().size();

        // Update the productClazzRel using partial update
        ProductClazzRel partialUpdatedProductClazzRel = new ProductClazzRel();
        partialUpdatedProductClazzRel.setId(productClazzRel.getId());

        partialUpdatedProductClazzRel
            .useCalculation(UPDATED_USE_CALCULATION)
            .calculation(UPDATED_CALCULATION)
            .calculationDateFrom(UPDATED_CALCULATION_DATE_FROM)
            .calculationDateTo(UPDATED_CALCULATION_DATE_TO)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductClazzRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductClazzRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductClazzRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductClazzRel in the database
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeUpdate);
        ProductClazzRel testProductClazzRel = productClazzRelList.get(productClazzRelList.size() - 1);
        assertThat(testProductClazzRel.getUseCalculation()).isEqualTo(UPDATED_USE_CALCULATION);
        assertThat(testProductClazzRel.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProductClazzRel.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testProductClazzRel.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProductClazzRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductClazzRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductClazzRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductClazzRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductClazzRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductClazzRel() throws Exception {
        int databaseSizeBeforeUpdate = productClazzRelRepository.findAll().size();
        productClazzRel.setId(count.incrementAndGet());

        // Create the ProductClazzRel
        ProductClazzRelDTO productClazzRelDTO = productClazzRelMapper.toDto(productClazzRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductClazzRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productClazzRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productClazzRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductClazzRel in the database
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductClazzRel() throws Exception {
        int databaseSizeBeforeUpdate = productClazzRelRepository.findAll().size();
        productClazzRel.setId(count.incrementAndGet());

        // Create the ProductClazzRel
        ProductClazzRelDTO productClazzRelDTO = productClazzRelMapper.toDto(productClazzRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductClazzRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productClazzRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductClazzRel in the database
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductClazzRel() throws Exception {
        int databaseSizeBeforeUpdate = productClazzRelRepository.findAll().size();
        productClazzRel.setId(count.incrementAndGet());

        // Create the ProductClazzRel
        ProductClazzRelDTO productClazzRelDTO = productClazzRelMapper.toDto(productClazzRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductClazzRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productClazzRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductClazzRel in the database
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductClazzRel() throws Exception {
        // Initialize the database
        productClazzRelRepository.saveAndFlush(productClazzRel);

        int databaseSizeBeforeDelete = productClazzRelRepository.findAll().size();

        // Delete the productClazzRel
        restProductClazzRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productClazzRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductClazzRel> productClazzRelList = productClazzRelRepository.findAll();
        assertThat(productClazzRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
