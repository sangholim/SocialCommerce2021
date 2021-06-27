package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.OptionColor;
import com.toy.project.domain.ProductOption;
import com.toy.project.domain.ProductOptionColorRel;
import com.toy.project.repository.ProductOptionColorRelRepository;
import com.toy.project.service.criteria.ProductOptionColorRelCriteria;
import com.toy.project.service.dto.ProductOptionColorRelDTO;
import com.toy.project.service.mapper.ProductOptionColorRelMapper;
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
 * Integration tests for the {@link ProductOptionColorRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductOptionColorRelResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-option-color-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductOptionColorRelRepository productOptionColorRelRepository;

    @Autowired
    private ProductOptionColorRelMapper productOptionColorRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductOptionColorRelMockMvc;

    private ProductOptionColorRel productOptionColorRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOptionColorRel createEntity(EntityManager em) {
        ProductOptionColorRel productOptionColorRel = new ProductOptionColorRel()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productOptionColorRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOptionColorRel createUpdatedEntity(EntityManager em) {
        ProductOptionColorRel productOptionColorRel = new ProductOptionColorRel()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productOptionColorRel;
    }

    @BeforeEach
    public void initTest() {
        productOptionColorRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductOptionColorRel() throws Exception {
        int databaseSizeBeforeCreate = productOptionColorRelRepository.findAll().size();
        // Create the ProductOptionColorRel
        ProductOptionColorRelDTO productOptionColorRelDTO = productOptionColorRelMapper.toDto(productOptionColorRel);
        restProductOptionColorRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionColorRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductOptionColorRel in the database
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOptionColorRel testProductOptionColorRel = productOptionColorRelList.get(productOptionColorRelList.size() - 1);
        assertThat(testProductOptionColorRel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductOptionColorRel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductOptionColorRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductOptionColorRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductOptionColorRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductOptionColorRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductOptionColorRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductOptionColorRelWithExistingId() throws Exception {
        // Create the ProductOptionColorRel with an existing ID
        productOptionColorRel.setId(1L);
        ProductOptionColorRelDTO productOptionColorRelDTO = productOptionColorRelMapper.toDto(productOptionColorRel);

        int databaseSizeBeforeCreate = productOptionColorRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionColorRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionColorRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionColorRel in the database
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRels() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList
        restProductOptionColorRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionColorRel.getId().intValue())))
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
    void getProductOptionColorRel() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get the productOptionColorRel
        restProductOptionColorRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productOptionColorRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productOptionColorRel.getId().intValue()))
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
    void getProductOptionColorRelsByIdFiltering() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        Long id = productOptionColorRel.getId();

        defaultProductOptionColorRelShouldBeFound("id.equals=" + id);
        defaultProductOptionColorRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductOptionColorRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductOptionColorRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductOptionColorRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductOptionColorRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where name equals to DEFAULT_NAME
        defaultProductOptionColorRelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productOptionColorRelList where name equals to UPDATED_NAME
        defaultProductOptionColorRelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where name not equals to DEFAULT_NAME
        defaultProductOptionColorRelShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productOptionColorRelList where name not equals to UPDATED_NAME
        defaultProductOptionColorRelShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductOptionColorRelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productOptionColorRelList where name equals to UPDATED_NAME
        defaultProductOptionColorRelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where name is not null
        defaultProductOptionColorRelShouldBeFound("name.specified=true");

        // Get all the productOptionColorRelList where name is null
        defaultProductOptionColorRelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByNameContainsSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where name contains DEFAULT_NAME
        defaultProductOptionColorRelShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productOptionColorRelList where name contains UPDATED_NAME
        defaultProductOptionColorRelShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where name does not contain DEFAULT_NAME
        defaultProductOptionColorRelShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productOptionColorRelList where name does not contain UPDATED_NAME
        defaultProductOptionColorRelShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where type equals to DEFAULT_TYPE
        defaultProductOptionColorRelShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the productOptionColorRelList where type equals to UPDATED_TYPE
        defaultProductOptionColorRelShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where type not equals to DEFAULT_TYPE
        defaultProductOptionColorRelShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the productOptionColorRelList where type not equals to UPDATED_TYPE
        defaultProductOptionColorRelShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProductOptionColorRelShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the productOptionColorRelList where type equals to UPDATED_TYPE
        defaultProductOptionColorRelShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where type is not null
        defaultProductOptionColorRelShouldBeFound("type.specified=true");

        // Get all the productOptionColorRelList where type is null
        defaultProductOptionColorRelShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByTypeContainsSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where type contains DEFAULT_TYPE
        defaultProductOptionColorRelShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the productOptionColorRelList where type contains UPDATED_TYPE
        defaultProductOptionColorRelShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where type does not contain DEFAULT_TYPE
        defaultProductOptionColorRelShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the productOptionColorRelList where type does not contain UPDATED_TYPE
        defaultProductOptionColorRelShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductOptionColorRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productOptionColorRelList where activated equals to UPDATED_ACTIVATED
        defaultProductOptionColorRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductOptionColorRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productOptionColorRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductOptionColorRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductOptionColorRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productOptionColorRelList where activated equals to UPDATED_ACTIVATED
        defaultProductOptionColorRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where activated is not null
        defaultProductOptionColorRelShouldBeFound("activated.specified=true");

        // Get all the productOptionColorRelList where activated is null
        defaultProductOptionColorRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductOptionColorRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productOptionColorRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductOptionColorRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductOptionColorRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productOptionColorRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductOptionColorRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductOptionColorRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productOptionColorRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductOptionColorRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where createdBy is not null
        defaultProductOptionColorRelShouldBeFound("createdBy.specified=true");

        // Get all the productOptionColorRelList where createdBy is null
        defaultProductOptionColorRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductOptionColorRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productOptionColorRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductOptionColorRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductOptionColorRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productOptionColorRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductOptionColorRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductOptionColorRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productOptionColorRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductOptionColorRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductOptionColorRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productOptionColorRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductOptionColorRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductOptionColorRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productOptionColorRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductOptionColorRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where createdDate is not null
        defaultProductOptionColorRelShouldBeFound("createdDate.specified=true");

        // Get all the productOptionColorRelList where createdDate is null
        defaultProductOptionColorRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionColorRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionColorRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionColorRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionColorRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionColorRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionColorRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductOptionColorRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productOptionColorRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionColorRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where lastModifiedBy is not null
        defaultProductOptionColorRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productOptionColorRelList where lastModifiedBy is null
        defaultProductOptionColorRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionColorRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionColorRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductOptionColorRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionColorRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionColorRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductOptionColorRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductOptionColorRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productOptionColorRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionColorRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductOptionColorRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productOptionColorRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionColorRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionColorRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productOptionColorRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionColorRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        // Get all the productOptionColorRelList where lastModifiedDate is not null
        defaultProductOptionColorRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productOptionColorRelList where lastModifiedDate is null
        defaultProductOptionColorRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByProductOptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);
        ProductOption productOption = ProductOptionResourceIT.createEntity(em);
        em.persist(productOption);
        em.flush();
        productOptionColorRel.setProductOption(productOption);
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);
        Long productOptionId = productOption.getId();

        // Get all the productOptionColorRelList where productOption equals to productOptionId
        defaultProductOptionColorRelShouldBeFound("productOptionId.equals=" + productOptionId);

        // Get all the productOptionColorRelList where productOption equals to (productOptionId + 1)
        defaultProductOptionColorRelShouldNotBeFound("productOptionId.equals=" + (productOptionId + 1));
    }

    @Test
    @Transactional
    void getAllProductOptionColorRelsByOptionColorIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);
        OptionColor optionColor = OptionColorResourceIT.createEntity(em);
        em.persist(optionColor);
        em.flush();
        productOptionColorRel.setOptionColor(optionColor);
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);
        Long optionColorId = optionColor.getId();

        // Get all the productOptionColorRelList where optionColor equals to optionColorId
        defaultProductOptionColorRelShouldBeFound("optionColorId.equals=" + optionColorId);

        // Get all the productOptionColorRelList where optionColor equals to (optionColorId + 1)
        defaultProductOptionColorRelShouldNotBeFound("optionColorId.equals=" + (optionColorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductOptionColorRelShouldBeFound(String filter) throws Exception {
        restProductOptionColorRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionColorRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductOptionColorRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductOptionColorRelShouldNotBeFound(String filter) throws Exception {
        restProductOptionColorRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductOptionColorRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductOptionColorRel() throws Exception {
        // Get the productOptionColorRel
        restProductOptionColorRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductOptionColorRel() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        int databaseSizeBeforeUpdate = productOptionColorRelRepository.findAll().size();

        // Update the productOptionColorRel
        ProductOptionColorRel updatedProductOptionColorRel = productOptionColorRelRepository.findById(productOptionColorRel.getId()).get();
        // Disconnect from session so that the updates on updatedProductOptionColorRel are not directly saved in db
        em.detach(updatedProductOptionColorRel);
        updatedProductOptionColorRel
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductOptionColorRelDTO productOptionColorRelDTO = productOptionColorRelMapper.toDto(updatedProductOptionColorRel);

        restProductOptionColorRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionColorRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionColorRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionColorRel in the database
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionColorRel testProductOptionColorRel = productOptionColorRelList.get(productOptionColorRelList.size() - 1);
        assertThat(testProductOptionColorRel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOptionColorRel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductOptionColorRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOptionColorRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOptionColorRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOptionColorRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOptionColorRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductOptionColorRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionColorRelRepository.findAll().size();
        productOptionColorRel.setId(count.incrementAndGet());

        // Create the ProductOptionColorRel
        ProductOptionColorRelDTO productOptionColorRelDTO = productOptionColorRelMapper.toDto(productOptionColorRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionColorRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionColorRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionColorRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionColorRel in the database
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductOptionColorRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionColorRelRepository.findAll().size();
        productOptionColorRel.setId(count.incrementAndGet());

        // Create the ProductOptionColorRel
        ProductOptionColorRelDTO productOptionColorRelDTO = productOptionColorRelMapper.toDto(productOptionColorRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionColorRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionColorRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionColorRel in the database
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductOptionColorRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionColorRelRepository.findAll().size();
        productOptionColorRel.setId(count.incrementAndGet());

        // Create the ProductOptionColorRel
        ProductOptionColorRelDTO productOptionColorRelDTO = productOptionColorRelMapper.toDto(productOptionColorRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionColorRelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionColorRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOptionColorRel in the database
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductOptionColorRelWithPatch() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        int databaseSizeBeforeUpdate = productOptionColorRelRepository.findAll().size();

        // Update the productOptionColorRel using partial update
        ProductOptionColorRel partialUpdatedProductOptionColorRel = new ProductOptionColorRel();
        partialUpdatedProductOptionColorRel.setId(productOptionColorRel.getId());

        partialUpdatedProductOptionColorRel.name(UPDATED_NAME);

        restProductOptionColorRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOptionColorRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOptionColorRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionColorRel in the database
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionColorRel testProductOptionColorRel = productOptionColorRelList.get(productOptionColorRelList.size() - 1);
        assertThat(testProductOptionColorRel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOptionColorRel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductOptionColorRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductOptionColorRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductOptionColorRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductOptionColorRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductOptionColorRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductOptionColorRelWithPatch() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        int databaseSizeBeforeUpdate = productOptionColorRelRepository.findAll().size();

        // Update the productOptionColorRel using partial update
        ProductOptionColorRel partialUpdatedProductOptionColorRel = new ProductOptionColorRel();
        partialUpdatedProductOptionColorRel.setId(productOptionColorRel.getId());

        partialUpdatedProductOptionColorRel
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductOptionColorRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOptionColorRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOptionColorRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionColorRel in the database
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionColorRel testProductOptionColorRel = productOptionColorRelList.get(productOptionColorRelList.size() - 1);
        assertThat(testProductOptionColorRel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOptionColorRel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductOptionColorRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOptionColorRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOptionColorRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOptionColorRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOptionColorRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductOptionColorRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionColorRelRepository.findAll().size();
        productOptionColorRel.setId(count.incrementAndGet());

        // Create the ProductOptionColorRel
        ProductOptionColorRelDTO productOptionColorRelDTO = productOptionColorRelMapper.toDto(productOptionColorRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionColorRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productOptionColorRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionColorRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionColorRel in the database
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductOptionColorRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionColorRelRepository.findAll().size();
        productOptionColorRel.setId(count.incrementAndGet());

        // Create the ProductOptionColorRel
        ProductOptionColorRelDTO productOptionColorRelDTO = productOptionColorRelMapper.toDto(productOptionColorRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionColorRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionColorRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionColorRel in the database
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductOptionColorRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionColorRelRepository.findAll().size();
        productOptionColorRel.setId(count.incrementAndGet());

        // Create the ProductOptionColorRel
        ProductOptionColorRelDTO productOptionColorRelDTO = productOptionColorRelMapper.toDto(productOptionColorRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionColorRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionColorRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOptionColorRel in the database
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductOptionColorRel() throws Exception {
        // Initialize the database
        productOptionColorRelRepository.saveAndFlush(productOptionColorRel);

        int databaseSizeBeforeDelete = productOptionColorRelRepository.findAll().size();

        // Delete the productOptionColorRel
        restProductOptionColorRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productOptionColorRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOptionColorRel> productOptionColorRelList = productOptionColorRelRepository.findAll();
        assertThat(productOptionColorRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
