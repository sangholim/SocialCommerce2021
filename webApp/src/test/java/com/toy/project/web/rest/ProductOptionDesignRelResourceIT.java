package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.OptionDesign;
import com.toy.project.domain.ProductOption;
import com.toy.project.domain.ProductOptionDesignRel;
import com.toy.project.repository.ProductOptionDesignRelRepository;
import com.toy.project.service.criteria.ProductOptionDesignRelCriteria;
import com.toy.project.service.dto.ProductOptionDesignRelDTO;
import com.toy.project.service.mapper.ProductOptionDesignRelMapper;
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
 * Integration tests for the {@link ProductOptionDesignRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductOptionDesignRelResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-option-design-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductOptionDesignRelRepository productOptionDesignRelRepository;

    @Autowired
    private ProductOptionDesignRelMapper productOptionDesignRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductOptionDesignRelMockMvc;

    private ProductOptionDesignRel productOptionDesignRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOptionDesignRel createEntity(EntityManager em) {
        ProductOptionDesignRel productOptionDesignRel = new ProductOptionDesignRel()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productOptionDesignRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOptionDesignRel createUpdatedEntity(EntityManager em) {
        ProductOptionDesignRel productOptionDesignRel = new ProductOptionDesignRel()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productOptionDesignRel;
    }

    @BeforeEach
    public void initTest() {
        productOptionDesignRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductOptionDesignRel() throws Exception {
        int databaseSizeBeforeCreate = productOptionDesignRelRepository.findAll().size();
        // Create the ProductOptionDesignRel
        ProductOptionDesignRelDTO productOptionDesignRelDTO = productOptionDesignRelMapper.toDto(productOptionDesignRel);
        restProductOptionDesignRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDesignRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductOptionDesignRel in the database
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOptionDesignRel testProductOptionDesignRel = productOptionDesignRelList.get(productOptionDesignRelList.size() - 1);
        assertThat(testProductOptionDesignRel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductOptionDesignRel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductOptionDesignRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductOptionDesignRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductOptionDesignRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductOptionDesignRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductOptionDesignRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductOptionDesignRelWithExistingId() throws Exception {
        // Create the ProductOptionDesignRel with an existing ID
        productOptionDesignRel.setId(1L);
        ProductOptionDesignRelDTO productOptionDesignRelDTO = productOptionDesignRelMapper.toDto(productOptionDesignRel);

        int databaseSizeBeforeCreate = productOptionDesignRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionDesignRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDesignRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionDesignRel in the database
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRels() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList
        restProductOptionDesignRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionDesignRel.getId().intValue())))
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
    void getProductOptionDesignRel() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get the productOptionDesignRel
        restProductOptionDesignRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productOptionDesignRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productOptionDesignRel.getId().intValue()))
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
    void getProductOptionDesignRelsByIdFiltering() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        Long id = productOptionDesignRel.getId();

        defaultProductOptionDesignRelShouldBeFound("id.equals=" + id);
        defaultProductOptionDesignRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductOptionDesignRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductOptionDesignRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductOptionDesignRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductOptionDesignRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where name equals to DEFAULT_NAME
        defaultProductOptionDesignRelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productOptionDesignRelList where name equals to UPDATED_NAME
        defaultProductOptionDesignRelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where name not equals to DEFAULT_NAME
        defaultProductOptionDesignRelShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productOptionDesignRelList where name not equals to UPDATED_NAME
        defaultProductOptionDesignRelShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductOptionDesignRelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productOptionDesignRelList where name equals to UPDATED_NAME
        defaultProductOptionDesignRelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where name is not null
        defaultProductOptionDesignRelShouldBeFound("name.specified=true");

        // Get all the productOptionDesignRelList where name is null
        defaultProductOptionDesignRelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByNameContainsSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where name contains DEFAULT_NAME
        defaultProductOptionDesignRelShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productOptionDesignRelList where name contains UPDATED_NAME
        defaultProductOptionDesignRelShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where name does not contain DEFAULT_NAME
        defaultProductOptionDesignRelShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productOptionDesignRelList where name does not contain UPDATED_NAME
        defaultProductOptionDesignRelShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where type equals to DEFAULT_TYPE
        defaultProductOptionDesignRelShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the productOptionDesignRelList where type equals to UPDATED_TYPE
        defaultProductOptionDesignRelShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where type not equals to DEFAULT_TYPE
        defaultProductOptionDesignRelShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the productOptionDesignRelList where type not equals to UPDATED_TYPE
        defaultProductOptionDesignRelShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProductOptionDesignRelShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the productOptionDesignRelList where type equals to UPDATED_TYPE
        defaultProductOptionDesignRelShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where type is not null
        defaultProductOptionDesignRelShouldBeFound("type.specified=true");

        // Get all the productOptionDesignRelList where type is null
        defaultProductOptionDesignRelShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByTypeContainsSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where type contains DEFAULT_TYPE
        defaultProductOptionDesignRelShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the productOptionDesignRelList where type contains UPDATED_TYPE
        defaultProductOptionDesignRelShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where type does not contain DEFAULT_TYPE
        defaultProductOptionDesignRelShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the productOptionDesignRelList where type does not contain UPDATED_TYPE
        defaultProductOptionDesignRelShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductOptionDesignRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productOptionDesignRelList where activated equals to UPDATED_ACTIVATED
        defaultProductOptionDesignRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductOptionDesignRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productOptionDesignRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductOptionDesignRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductOptionDesignRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productOptionDesignRelList where activated equals to UPDATED_ACTIVATED
        defaultProductOptionDesignRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where activated is not null
        defaultProductOptionDesignRelShouldBeFound("activated.specified=true");

        // Get all the productOptionDesignRelList where activated is null
        defaultProductOptionDesignRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductOptionDesignRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productOptionDesignRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductOptionDesignRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductOptionDesignRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productOptionDesignRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductOptionDesignRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductOptionDesignRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productOptionDesignRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductOptionDesignRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where createdBy is not null
        defaultProductOptionDesignRelShouldBeFound("createdBy.specified=true");

        // Get all the productOptionDesignRelList where createdBy is null
        defaultProductOptionDesignRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductOptionDesignRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productOptionDesignRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductOptionDesignRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductOptionDesignRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productOptionDesignRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductOptionDesignRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductOptionDesignRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productOptionDesignRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductOptionDesignRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductOptionDesignRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productOptionDesignRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductOptionDesignRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductOptionDesignRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productOptionDesignRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductOptionDesignRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where createdDate is not null
        defaultProductOptionDesignRelShouldBeFound("createdDate.specified=true");

        // Get all the productOptionDesignRelList where createdDate is null
        defaultProductOptionDesignRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionDesignRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionDesignRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionDesignRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionDesignRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionDesignRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionDesignRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductOptionDesignRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productOptionDesignRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionDesignRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where lastModifiedBy is not null
        defaultProductOptionDesignRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productOptionDesignRelList where lastModifiedBy is null
        defaultProductOptionDesignRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionDesignRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionDesignRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductOptionDesignRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionDesignRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionDesignRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductOptionDesignRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductOptionDesignRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productOptionDesignRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionDesignRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductOptionDesignRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productOptionDesignRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionDesignRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionDesignRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productOptionDesignRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionDesignRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        // Get all the productOptionDesignRelList where lastModifiedDate is not null
        defaultProductOptionDesignRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productOptionDesignRelList where lastModifiedDate is null
        defaultProductOptionDesignRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByProductOptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);
        ProductOption productOption = ProductOptionResourceIT.createEntity(em);
        em.persist(productOption);
        em.flush();
        productOptionDesignRel.setProductOption(productOption);
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);
        Long productOptionId = productOption.getId();

        // Get all the productOptionDesignRelList where productOption equals to productOptionId
        defaultProductOptionDesignRelShouldBeFound("productOptionId.equals=" + productOptionId);

        // Get all the productOptionDesignRelList where productOption equals to (productOptionId + 1)
        defaultProductOptionDesignRelShouldNotBeFound("productOptionId.equals=" + (productOptionId + 1));
    }

    @Test
    @Transactional
    void getAllProductOptionDesignRelsByOptionDesignIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);
        OptionDesign optionDesign = OptionDesignResourceIT.createEntity(em);
        em.persist(optionDesign);
        em.flush();
        productOptionDesignRel.setOptionDesign(optionDesign);
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);
        Long optionDesignId = optionDesign.getId();

        // Get all the productOptionDesignRelList where optionDesign equals to optionDesignId
        defaultProductOptionDesignRelShouldBeFound("optionDesignId.equals=" + optionDesignId);

        // Get all the productOptionDesignRelList where optionDesign equals to (optionDesignId + 1)
        defaultProductOptionDesignRelShouldNotBeFound("optionDesignId.equals=" + (optionDesignId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductOptionDesignRelShouldBeFound(String filter) throws Exception {
        restProductOptionDesignRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionDesignRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductOptionDesignRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductOptionDesignRelShouldNotBeFound(String filter) throws Exception {
        restProductOptionDesignRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductOptionDesignRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductOptionDesignRel() throws Exception {
        // Get the productOptionDesignRel
        restProductOptionDesignRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductOptionDesignRel() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        int databaseSizeBeforeUpdate = productOptionDesignRelRepository.findAll().size();

        // Update the productOptionDesignRel
        ProductOptionDesignRel updatedProductOptionDesignRel = productOptionDesignRelRepository
            .findById(productOptionDesignRel.getId())
            .get();
        // Disconnect from session so that the updates on updatedProductOptionDesignRel are not directly saved in db
        em.detach(updatedProductOptionDesignRel);
        updatedProductOptionDesignRel
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductOptionDesignRelDTO productOptionDesignRelDTO = productOptionDesignRelMapper.toDto(updatedProductOptionDesignRel);

        restProductOptionDesignRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionDesignRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDesignRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionDesignRel in the database
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionDesignRel testProductOptionDesignRel = productOptionDesignRelList.get(productOptionDesignRelList.size() - 1);
        assertThat(testProductOptionDesignRel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOptionDesignRel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductOptionDesignRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOptionDesignRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOptionDesignRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOptionDesignRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOptionDesignRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductOptionDesignRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionDesignRelRepository.findAll().size();
        productOptionDesignRel.setId(count.incrementAndGet());

        // Create the ProductOptionDesignRel
        ProductOptionDesignRelDTO productOptionDesignRelDTO = productOptionDesignRelMapper.toDto(productOptionDesignRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionDesignRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionDesignRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDesignRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionDesignRel in the database
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductOptionDesignRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionDesignRelRepository.findAll().size();
        productOptionDesignRel.setId(count.incrementAndGet());

        // Create the ProductOptionDesignRel
        ProductOptionDesignRelDTO productOptionDesignRelDTO = productOptionDesignRelMapper.toDto(productOptionDesignRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionDesignRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDesignRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionDesignRel in the database
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductOptionDesignRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionDesignRelRepository.findAll().size();
        productOptionDesignRel.setId(count.incrementAndGet());

        // Create the ProductOptionDesignRel
        ProductOptionDesignRelDTO productOptionDesignRelDTO = productOptionDesignRelMapper.toDto(productOptionDesignRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionDesignRelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDesignRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOptionDesignRel in the database
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductOptionDesignRelWithPatch() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        int databaseSizeBeforeUpdate = productOptionDesignRelRepository.findAll().size();

        // Update the productOptionDesignRel using partial update
        ProductOptionDesignRel partialUpdatedProductOptionDesignRel = new ProductOptionDesignRel();
        partialUpdatedProductOptionDesignRel.setId(productOptionDesignRel.getId());

        partialUpdatedProductOptionDesignRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductOptionDesignRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOptionDesignRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOptionDesignRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionDesignRel in the database
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionDesignRel testProductOptionDesignRel = productOptionDesignRelList.get(productOptionDesignRelList.size() - 1);
        assertThat(testProductOptionDesignRel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductOptionDesignRel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductOptionDesignRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOptionDesignRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOptionDesignRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductOptionDesignRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductOptionDesignRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductOptionDesignRelWithPatch() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        int databaseSizeBeforeUpdate = productOptionDesignRelRepository.findAll().size();

        // Update the productOptionDesignRel using partial update
        ProductOptionDesignRel partialUpdatedProductOptionDesignRel = new ProductOptionDesignRel();
        partialUpdatedProductOptionDesignRel.setId(productOptionDesignRel.getId());

        partialUpdatedProductOptionDesignRel
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductOptionDesignRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOptionDesignRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOptionDesignRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionDesignRel in the database
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionDesignRel testProductOptionDesignRel = productOptionDesignRelList.get(productOptionDesignRelList.size() - 1);
        assertThat(testProductOptionDesignRel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOptionDesignRel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductOptionDesignRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOptionDesignRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOptionDesignRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOptionDesignRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOptionDesignRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductOptionDesignRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionDesignRelRepository.findAll().size();
        productOptionDesignRel.setId(count.incrementAndGet());

        // Create the ProductOptionDesignRel
        ProductOptionDesignRelDTO productOptionDesignRelDTO = productOptionDesignRelMapper.toDto(productOptionDesignRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionDesignRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productOptionDesignRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDesignRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionDesignRel in the database
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductOptionDesignRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionDesignRelRepository.findAll().size();
        productOptionDesignRel.setId(count.incrementAndGet());

        // Create the ProductOptionDesignRel
        ProductOptionDesignRelDTO productOptionDesignRelDTO = productOptionDesignRelMapper.toDto(productOptionDesignRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionDesignRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDesignRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionDesignRel in the database
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductOptionDesignRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionDesignRelRepository.findAll().size();
        productOptionDesignRel.setId(count.incrementAndGet());

        // Create the ProductOptionDesignRel
        ProductOptionDesignRelDTO productOptionDesignRelDTO = productOptionDesignRelMapper.toDto(productOptionDesignRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionDesignRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDesignRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOptionDesignRel in the database
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductOptionDesignRel() throws Exception {
        // Initialize the database
        productOptionDesignRelRepository.saveAndFlush(productOptionDesignRel);

        int databaseSizeBeforeDelete = productOptionDesignRelRepository.findAll().size();

        // Delete the productOptionDesignRel
        restProductOptionDesignRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productOptionDesignRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOptionDesignRel> productOptionDesignRelList = productOptionDesignRelRepository.findAll();
        assertThat(productOptionDesignRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
