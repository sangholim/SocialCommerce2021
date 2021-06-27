package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductMapping;
import com.toy.project.domain.ProductMappingRel;
import com.toy.project.repository.ProductMappingRepository;
import com.toy.project.service.criteria.ProductMappingCriteria;
import com.toy.project.service.dto.ProductMappingDTO;
import com.toy.project.service.mapper.ProductMappingMapper;
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
 * Integration tests for the {@link ProductMappingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductMappingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductMappingRepository productMappingRepository;

    @Autowired
    private ProductMappingMapper productMappingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMappingMockMvc;

    private ProductMapping productMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMapping createEntity(EntityManager em) {
        ProductMapping productMapping = new ProductMapping()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .content(DEFAULT_CONTENT)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productMapping;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMapping createUpdatedEntity(EntityManager em) {
        ProductMapping productMapping = new ProductMapping()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productMapping;
    }

    @BeforeEach
    public void initTest() {
        productMapping = createEntity(em);
    }

    @Test
    @Transactional
    void createProductMapping() throws Exception {
        int databaseSizeBeforeCreate = productMappingRepository.findAll().size();
        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);
        restProductMappingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeCreate + 1);
        ProductMapping testProductMapping = productMappingList.get(productMappingList.size() - 1);
        assertThat(testProductMapping.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductMapping.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductMapping.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProductMapping.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductMapping.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductMapping.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductMapping.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductMapping.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductMappingWithExistingId() throws Exception {
        // Create the ProductMapping with an existing ID
        productMapping.setId(1L);
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        int databaseSizeBeforeCreate = productMappingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMappingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductMappings() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList
        restProductMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductMapping() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get the productMapping
        restProductMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, productMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productMapping.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductMappingsByIdFiltering() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        Long id = productMapping.getId();

        defaultProductMappingShouldBeFound("id.equals=" + id);
        defaultProductMappingShouldNotBeFound("id.notEquals=" + id);

        defaultProductMappingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductMappingShouldNotBeFound("id.greaterThan=" + id);

        defaultProductMappingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductMappingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductMappingsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where name equals to DEFAULT_NAME
        defaultProductMappingShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productMappingList where name equals to UPDATED_NAME
        defaultProductMappingShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductMappingsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where name not equals to DEFAULT_NAME
        defaultProductMappingShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productMappingList where name not equals to UPDATED_NAME
        defaultProductMappingShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductMappingsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductMappingShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productMappingList where name equals to UPDATED_NAME
        defaultProductMappingShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductMappingsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where name is not null
        defaultProductMappingShouldBeFound("name.specified=true");

        // Get all the productMappingList where name is null
        defaultProductMappingShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingsByNameContainsSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where name contains DEFAULT_NAME
        defaultProductMappingShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productMappingList where name contains UPDATED_NAME
        defaultProductMappingShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductMappingsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where name does not contain DEFAULT_NAME
        defaultProductMappingShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productMappingList where name does not contain UPDATED_NAME
        defaultProductMappingShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductMappingsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where type equals to DEFAULT_TYPE
        defaultProductMappingShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the productMappingList where type equals to UPDATED_TYPE
        defaultProductMappingShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductMappingsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where type not equals to DEFAULT_TYPE
        defaultProductMappingShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the productMappingList where type not equals to UPDATED_TYPE
        defaultProductMappingShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductMappingsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProductMappingShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the productMappingList where type equals to UPDATED_TYPE
        defaultProductMappingShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductMappingsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where type is not null
        defaultProductMappingShouldBeFound("type.specified=true");

        // Get all the productMappingList where type is null
        defaultProductMappingShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingsByTypeContainsSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where type contains DEFAULT_TYPE
        defaultProductMappingShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the productMappingList where type contains UPDATED_TYPE
        defaultProductMappingShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductMappingsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where type does not contain DEFAULT_TYPE
        defaultProductMappingShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the productMappingList where type does not contain UPDATED_TYPE
        defaultProductMappingShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductMappingsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where activated equals to DEFAULT_ACTIVATED
        defaultProductMappingShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productMappingList where activated equals to UPDATED_ACTIVATED
        defaultProductMappingShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductMappingsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where activated not equals to DEFAULT_ACTIVATED
        defaultProductMappingShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productMappingList where activated not equals to UPDATED_ACTIVATED
        defaultProductMappingShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductMappingsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductMappingShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productMappingList where activated equals to UPDATED_ACTIVATED
        defaultProductMappingShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductMappingsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where activated is not null
        defaultProductMappingShouldBeFound("activated.specified=true");

        // Get all the productMappingList where activated is null
        defaultProductMappingShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductMappingShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productMappingList where createdBy equals to UPDATED_CREATED_BY
        defaultProductMappingShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductMappingShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productMappingList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductMappingShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductMappingShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productMappingList where createdBy equals to UPDATED_CREATED_BY
        defaultProductMappingShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where createdBy is not null
        defaultProductMappingShouldBeFound("createdBy.specified=true");

        // Get all the productMappingList where createdBy is null
        defaultProductMappingShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where createdBy contains DEFAULT_CREATED_BY
        defaultProductMappingShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productMappingList where createdBy contains UPDATED_CREATED_BY
        defaultProductMappingShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductMappingShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productMappingList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductMappingShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductMappingShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productMappingList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductMappingShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductMappingShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productMappingList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductMappingShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductMappingShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productMappingList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductMappingShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where createdDate is not null
        defaultProductMappingShouldBeFound("createdDate.specified=true");

        // Get all the productMappingList where createdDate is null
        defaultProductMappingShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductMappingShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productMappingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductMappingShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductMappingShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productMappingList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductMappingShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductMappingShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productMappingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductMappingShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where lastModifiedBy is not null
        defaultProductMappingShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productMappingList where lastModifiedBy is null
        defaultProductMappingShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductMappingShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productMappingList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductMappingShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductMappingShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productMappingList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductMappingShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductMappingShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productMappingList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductMappingShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductMappingShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productMappingList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductMappingShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductMappingShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productMappingList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductMappingShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList where lastModifiedDate is not null
        defaultProductMappingShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productMappingList where lastModifiedDate is null
        defaultProductMappingShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingsByProductMappingRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);
        ProductMappingRel productMappingRel = ProductMappingRelResourceIT.createEntity(em);
        em.persist(productMappingRel);
        em.flush();
        productMapping.addProductMappingRel(productMappingRel);
        productMappingRepository.saveAndFlush(productMapping);
        Long productMappingRelId = productMappingRel.getId();

        // Get all the productMappingList where productMappingRel equals to productMappingRelId
        defaultProductMappingShouldBeFound("productMappingRelId.equals=" + productMappingRelId);

        // Get all the productMappingList where productMappingRel equals to (productMappingRelId + 1)
        defaultProductMappingShouldNotBeFound("productMappingRelId.equals=" + (productMappingRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductMappingShouldBeFound(String filter) throws Exception {
        restProductMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductMappingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductMappingShouldNotBeFound(String filter) throws Exception {
        restProductMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductMappingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductMapping() throws Exception {
        // Get the productMapping
        restProductMappingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductMapping() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();

        // Update the productMapping
        ProductMapping updatedProductMapping = productMappingRepository.findById(productMapping.getId()).get();
        // Disconnect from session so that the updates on updatedProductMapping are not directly saved in db
        em.detach(updatedProductMapping);
        updatedProductMapping
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(updatedProductMapping);

        restProductMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
        ProductMapping testProductMapping = productMappingList.get(productMappingList.size() - 1);
        assertThat(testProductMapping.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductMapping.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductMapping.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductMapping.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductMapping.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductMapping.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductMapping.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductMappingWithPatch() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();

        // Update the productMapping using partial update
        ProductMapping partialUpdatedProductMapping = new ProductMapping();
        partialUpdatedProductMapping.setId(productMapping.getId());

        partialUpdatedProductMapping
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE);

        restProductMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductMapping))
            )
            .andExpect(status().isOk());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
        ProductMapping testProductMapping = productMappingList.get(productMappingList.size() - 1);
        assertThat(testProductMapping.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductMapping.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductMapping.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductMapping.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductMapping.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductMapping.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductMapping.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductMappingWithPatch() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();

        // Update the productMapping using partial update
        ProductMapping partialUpdatedProductMapping = new ProductMapping();
        partialUpdatedProductMapping.setId(productMapping.getId());

        partialUpdatedProductMapping
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductMapping))
            )
            .andExpect(status().isOk());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
        ProductMapping testProductMapping = productMappingList.get(productMappingList.size() - 1);
        assertThat(testProductMapping.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductMapping.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductMapping.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductMapping.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductMapping.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductMapping.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductMapping.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productMappingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductMapping() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        int databaseSizeBeforeDelete = productMappingRepository.findAll().size();

        // Delete the productMapping
        restProductMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, productMapping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
