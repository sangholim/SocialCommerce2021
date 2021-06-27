package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.OptionPackage;
import com.toy.project.domain.ProductOption;
import com.toy.project.domain.ProductOptionPackageRel;
import com.toy.project.repository.ProductOptionPackageRelRepository;
import com.toy.project.service.criteria.ProductOptionPackageRelCriteria;
import com.toy.project.service.dto.ProductOptionPackageRelDTO;
import com.toy.project.service.mapper.ProductOptionPackageRelMapper;
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
 * Integration tests for the {@link ProductOptionPackageRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductOptionPackageRelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-option-package-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductOptionPackageRelRepository productOptionPackageRelRepository;

    @Autowired
    private ProductOptionPackageRelMapper productOptionPackageRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductOptionPackageRelMockMvc;

    private ProductOptionPackageRel productOptionPackageRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOptionPackageRel createEntity(EntityManager em) {
        ProductOptionPackageRel productOptionPackageRel = new ProductOptionPackageRel()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productOptionPackageRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOptionPackageRel createUpdatedEntity(EntityManager em) {
        ProductOptionPackageRel productOptionPackageRel = new ProductOptionPackageRel()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productOptionPackageRel;
    }

    @BeforeEach
    public void initTest() {
        productOptionPackageRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductOptionPackageRel() throws Exception {
        int databaseSizeBeforeCreate = productOptionPackageRelRepository.findAll().size();
        // Create the ProductOptionPackageRel
        ProductOptionPackageRelDTO productOptionPackageRelDTO = productOptionPackageRelMapper.toDto(productOptionPackageRel);
        restProductOptionPackageRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionPackageRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductOptionPackageRel in the database
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOptionPackageRel testProductOptionPackageRel = productOptionPackageRelList.get(productOptionPackageRelList.size() - 1);
        assertThat(testProductOptionPackageRel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductOptionPackageRel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductOptionPackageRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductOptionPackageRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductOptionPackageRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductOptionPackageRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductOptionPackageRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductOptionPackageRelWithExistingId() throws Exception {
        // Create the ProductOptionPackageRel with an existing ID
        productOptionPackageRel.setId(1L);
        ProductOptionPackageRelDTO productOptionPackageRelDTO = productOptionPackageRelMapper.toDto(productOptionPackageRel);

        int databaseSizeBeforeCreate = productOptionPackageRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionPackageRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionPackageRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionPackageRel in the database
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRels() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList
        restProductOptionPackageRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionPackageRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductOptionPackageRel() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get the productOptionPackageRel
        restProductOptionPackageRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productOptionPackageRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productOptionPackageRel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductOptionPackageRelsByIdFiltering() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        Long id = productOptionPackageRel.getId();

        defaultProductOptionPackageRelShouldBeFound("id.equals=" + id);
        defaultProductOptionPackageRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductOptionPackageRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductOptionPackageRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductOptionPackageRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductOptionPackageRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where name equals to DEFAULT_NAME
        defaultProductOptionPackageRelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productOptionPackageRelList where name equals to UPDATED_NAME
        defaultProductOptionPackageRelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where name not equals to DEFAULT_NAME
        defaultProductOptionPackageRelShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productOptionPackageRelList where name not equals to UPDATED_NAME
        defaultProductOptionPackageRelShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductOptionPackageRelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productOptionPackageRelList where name equals to UPDATED_NAME
        defaultProductOptionPackageRelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where name is not null
        defaultProductOptionPackageRelShouldBeFound("name.specified=true");

        // Get all the productOptionPackageRelList where name is null
        defaultProductOptionPackageRelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByNameContainsSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where name contains DEFAULT_NAME
        defaultProductOptionPackageRelShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productOptionPackageRelList where name contains UPDATED_NAME
        defaultProductOptionPackageRelShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where name does not contain DEFAULT_NAME
        defaultProductOptionPackageRelShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productOptionPackageRelList where name does not contain UPDATED_NAME
        defaultProductOptionPackageRelShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where type equals to DEFAULT_TYPE
        defaultProductOptionPackageRelShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the productOptionPackageRelList where type equals to UPDATED_TYPE
        defaultProductOptionPackageRelShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where type not equals to DEFAULT_TYPE
        defaultProductOptionPackageRelShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the productOptionPackageRelList where type not equals to UPDATED_TYPE
        defaultProductOptionPackageRelShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProductOptionPackageRelShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the productOptionPackageRelList where type equals to UPDATED_TYPE
        defaultProductOptionPackageRelShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where type is not null
        defaultProductOptionPackageRelShouldBeFound("type.specified=true");

        // Get all the productOptionPackageRelList where type is null
        defaultProductOptionPackageRelShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByTypeContainsSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where type contains DEFAULT_TYPE
        defaultProductOptionPackageRelShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the productOptionPackageRelList where type contains UPDATED_TYPE
        defaultProductOptionPackageRelShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where type does not contain DEFAULT_TYPE
        defaultProductOptionPackageRelShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the productOptionPackageRelList where type does not contain UPDATED_TYPE
        defaultProductOptionPackageRelShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductOptionPackageRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productOptionPackageRelList where activated equals to UPDATED_ACTIVATED
        defaultProductOptionPackageRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductOptionPackageRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productOptionPackageRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductOptionPackageRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductOptionPackageRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productOptionPackageRelList where activated equals to UPDATED_ACTIVATED
        defaultProductOptionPackageRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where activated is not null
        defaultProductOptionPackageRelShouldBeFound("activated.specified=true");

        // Get all the productOptionPackageRelList where activated is null
        defaultProductOptionPackageRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductOptionPackageRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productOptionPackageRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductOptionPackageRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductOptionPackageRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productOptionPackageRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductOptionPackageRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductOptionPackageRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productOptionPackageRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductOptionPackageRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where createdBy is not null
        defaultProductOptionPackageRelShouldBeFound("createdBy.specified=true");

        // Get all the productOptionPackageRelList where createdBy is null
        defaultProductOptionPackageRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductOptionPackageRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productOptionPackageRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductOptionPackageRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductOptionPackageRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productOptionPackageRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductOptionPackageRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductOptionPackageRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productOptionPackageRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductOptionPackageRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductOptionPackageRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productOptionPackageRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductOptionPackageRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductOptionPackageRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productOptionPackageRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductOptionPackageRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where createdDate is not null
        defaultProductOptionPackageRelShouldBeFound("createdDate.specified=true");

        // Get all the productOptionPackageRelList where createdDate is null
        defaultProductOptionPackageRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionPackageRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionPackageRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionPackageRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionPackageRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionPackageRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionPackageRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductOptionPackageRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productOptionPackageRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionPackageRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where lastModifiedBy is not null
        defaultProductOptionPackageRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productOptionPackageRelList where lastModifiedBy is null
        defaultProductOptionPackageRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionPackageRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionPackageRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductOptionPackageRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionPackageRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionPackageRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductOptionPackageRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductOptionPackageRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productOptionPackageRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionPackageRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductOptionPackageRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productOptionPackageRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionPackageRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionPackageRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productOptionPackageRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionPackageRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        // Get all the productOptionPackageRelList where lastModifiedDate is not null
        defaultProductOptionPackageRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productOptionPackageRelList where lastModifiedDate is null
        defaultProductOptionPackageRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByProductOptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);
        ProductOption productOption = ProductOptionResourceIT.createEntity(em);
        em.persist(productOption);
        em.flush();
        productOptionPackageRel.setProductOption(productOption);
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);
        Long productOptionId = productOption.getId();

        // Get all the productOptionPackageRelList where productOption equals to productOptionId
        defaultProductOptionPackageRelShouldBeFound("productOptionId.equals=" + productOptionId);

        // Get all the productOptionPackageRelList where productOption equals to (productOptionId + 1)
        defaultProductOptionPackageRelShouldNotBeFound("productOptionId.equals=" + (productOptionId + 1));
    }

    @Test
    @Transactional
    void getAllProductOptionPackageRelsByOptionPackageIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);
        OptionPackage optionPackage = OptionPackageResourceIT.createEntity(em);
        em.persist(optionPackage);
        em.flush();
        productOptionPackageRel.setOptionPackage(optionPackage);
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);
        Long optionPackageId = optionPackage.getId();

        // Get all the productOptionPackageRelList where optionPackage equals to optionPackageId
        defaultProductOptionPackageRelShouldBeFound("optionPackageId.equals=" + optionPackageId);

        // Get all the productOptionPackageRelList where optionPackage equals to (optionPackageId + 1)
        defaultProductOptionPackageRelShouldNotBeFound("optionPackageId.equals=" + (optionPackageId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductOptionPackageRelShouldBeFound(String filter) throws Exception {
        restProductOptionPackageRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionPackageRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductOptionPackageRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductOptionPackageRelShouldNotBeFound(String filter) throws Exception {
        restProductOptionPackageRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductOptionPackageRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductOptionPackageRel() throws Exception {
        // Get the productOptionPackageRel
        restProductOptionPackageRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductOptionPackageRel() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        int databaseSizeBeforeUpdate = productOptionPackageRelRepository.findAll().size();

        // Update the productOptionPackageRel
        ProductOptionPackageRel updatedProductOptionPackageRel = productOptionPackageRelRepository
            .findById(productOptionPackageRel.getId())
            .get();
        // Disconnect from session so that the updates on updatedProductOptionPackageRel are not directly saved in db
        em.detach(updatedProductOptionPackageRel);
        updatedProductOptionPackageRel
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductOptionPackageRelDTO productOptionPackageRelDTO = productOptionPackageRelMapper.toDto(updatedProductOptionPackageRel);

        restProductOptionPackageRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionPackageRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionPackageRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionPackageRel in the database
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionPackageRel testProductOptionPackageRel = productOptionPackageRelList.get(productOptionPackageRelList.size() - 1);
        assertThat(testProductOptionPackageRel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOptionPackageRel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductOptionPackageRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOptionPackageRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOptionPackageRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOptionPackageRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOptionPackageRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductOptionPackageRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionPackageRelRepository.findAll().size();
        productOptionPackageRel.setId(count.incrementAndGet());

        // Create the ProductOptionPackageRel
        ProductOptionPackageRelDTO productOptionPackageRelDTO = productOptionPackageRelMapper.toDto(productOptionPackageRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionPackageRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionPackageRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionPackageRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionPackageRel in the database
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductOptionPackageRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionPackageRelRepository.findAll().size();
        productOptionPackageRel.setId(count.incrementAndGet());

        // Create the ProductOptionPackageRel
        ProductOptionPackageRelDTO productOptionPackageRelDTO = productOptionPackageRelMapper.toDto(productOptionPackageRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionPackageRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionPackageRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionPackageRel in the database
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductOptionPackageRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionPackageRelRepository.findAll().size();
        productOptionPackageRel.setId(count.incrementAndGet());

        // Create the ProductOptionPackageRel
        ProductOptionPackageRelDTO productOptionPackageRelDTO = productOptionPackageRelMapper.toDto(productOptionPackageRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionPackageRelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionPackageRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOptionPackageRel in the database
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductOptionPackageRelWithPatch() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        int databaseSizeBeforeUpdate = productOptionPackageRelRepository.findAll().size();

        // Update the productOptionPackageRel using partial update
        ProductOptionPackageRel partialUpdatedProductOptionPackageRel = new ProductOptionPackageRel();
        partialUpdatedProductOptionPackageRel.setId(productOptionPackageRel.getId());

        partialUpdatedProductOptionPackageRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductOptionPackageRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOptionPackageRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOptionPackageRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionPackageRel in the database
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionPackageRel testProductOptionPackageRel = productOptionPackageRelList.get(productOptionPackageRelList.size() - 1);
        assertThat(testProductOptionPackageRel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductOptionPackageRel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductOptionPackageRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOptionPackageRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOptionPackageRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOptionPackageRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductOptionPackageRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductOptionPackageRelWithPatch() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        int databaseSizeBeforeUpdate = productOptionPackageRelRepository.findAll().size();

        // Update the productOptionPackageRel using partial update
        ProductOptionPackageRel partialUpdatedProductOptionPackageRel = new ProductOptionPackageRel();
        partialUpdatedProductOptionPackageRel.setId(productOptionPackageRel.getId());

        partialUpdatedProductOptionPackageRel
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductOptionPackageRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOptionPackageRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOptionPackageRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionPackageRel in the database
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionPackageRel testProductOptionPackageRel = productOptionPackageRelList.get(productOptionPackageRelList.size() - 1);
        assertThat(testProductOptionPackageRel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOptionPackageRel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductOptionPackageRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOptionPackageRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOptionPackageRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOptionPackageRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOptionPackageRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductOptionPackageRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionPackageRelRepository.findAll().size();
        productOptionPackageRel.setId(count.incrementAndGet());

        // Create the ProductOptionPackageRel
        ProductOptionPackageRelDTO productOptionPackageRelDTO = productOptionPackageRelMapper.toDto(productOptionPackageRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionPackageRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productOptionPackageRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionPackageRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionPackageRel in the database
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductOptionPackageRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionPackageRelRepository.findAll().size();
        productOptionPackageRel.setId(count.incrementAndGet());

        // Create the ProductOptionPackageRel
        ProductOptionPackageRelDTO productOptionPackageRelDTO = productOptionPackageRelMapper.toDto(productOptionPackageRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionPackageRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionPackageRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionPackageRel in the database
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductOptionPackageRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionPackageRelRepository.findAll().size();
        productOptionPackageRel.setId(count.incrementAndGet());

        // Create the ProductOptionPackageRel
        ProductOptionPackageRelDTO productOptionPackageRelDTO = productOptionPackageRelMapper.toDto(productOptionPackageRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionPackageRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionPackageRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOptionPackageRel in the database
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductOptionPackageRel() throws Exception {
        // Initialize the database
        productOptionPackageRelRepository.saveAndFlush(productOptionPackageRel);

        int databaseSizeBeforeDelete = productOptionPackageRelRepository.findAll().size();

        // Delete the productOptionPackageRel
        restProductOptionPackageRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productOptionPackageRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOptionPackageRel> productOptionPackageRelList = productOptionPackageRelRepository.findAll();
        assertThat(productOptionPackageRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
