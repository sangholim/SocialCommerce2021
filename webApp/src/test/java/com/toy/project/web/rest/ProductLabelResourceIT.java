package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductLabel;
import com.toy.project.domain.ProductLabelRel;
import com.toy.project.repository.ProductLabelRepository;
import com.toy.project.service.criteria.ProductLabelCriteria;
import com.toy.project.service.dto.ProductLabelDTO;
import com.toy.project.service.mapper.ProductLabelMapper;
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
 * Integration tests for the {@link ProductLabelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductLabelResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-labels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductLabelRepository productLabelRepository;

    @Autowired
    private ProductLabelMapper productLabelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductLabelMockMvc;

    private ProductLabel productLabel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductLabel createEntity(EntityManager em) {
        ProductLabel productLabel = new ProductLabel()
            .name(DEFAULT_NAME)
            .color(DEFAULT_COLOR)
            .content(DEFAULT_CONTENT)
            .type(DEFAULT_TYPE)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productLabel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductLabel createUpdatedEntity(EntityManager em) {
        ProductLabel productLabel = new ProductLabel()
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR)
            .content(UPDATED_CONTENT)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productLabel;
    }

    @BeforeEach
    public void initTest() {
        productLabel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductLabel() throws Exception {
        int databaseSizeBeforeCreate = productLabelRepository.findAll().size();
        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);
        restProductLabelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductLabel testProductLabel = productLabelList.get(productLabelList.size() - 1);
        assertThat(testProductLabel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductLabel.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testProductLabel.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProductLabel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductLabel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductLabel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductLabel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductLabel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductLabel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductLabelWithExistingId() throws Exception {
        // Create the ProductLabel with an existing ID
        productLabel.setId(1L);
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        int databaseSizeBeforeCreate = productLabelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductLabelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductLabels() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList
        restProductLabelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductLabel() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get the productLabel
        restProductLabelMockMvc
            .perform(get(ENTITY_API_URL_ID, productLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productLabel.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductLabelsByIdFiltering() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        Long id = productLabel.getId();

        defaultProductLabelShouldBeFound("id.equals=" + id);
        defaultProductLabelShouldNotBeFound("id.notEquals=" + id);

        defaultProductLabelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductLabelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductLabelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductLabelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductLabelsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where name equals to DEFAULT_NAME
        defaultProductLabelShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productLabelList where name equals to UPDATED_NAME
        defaultProductLabelShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductLabelsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where name not equals to DEFAULT_NAME
        defaultProductLabelShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productLabelList where name not equals to UPDATED_NAME
        defaultProductLabelShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductLabelsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductLabelShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productLabelList where name equals to UPDATED_NAME
        defaultProductLabelShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductLabelsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where name is not null
        defaultProductLabelShouldBeFound("name.specified=true");

        // Get all the productLabelList where name is null
        defaultProductLabelShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelsByNameContainsSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where name contains DEFAULT_NAME
        defaultProductLabelShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productLabelList where name contains UPDATED_NAME
        defaultProductLabelShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductLabelsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where name does not contain DEFAULT_NAME
        defaultProductLabelShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productLabelList where name does not contain UPDATED_NAME
        defaultProductLabelShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductLabelsByColorIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where color equals to DEFAULT_COLOR
        defaultProductLabelShouldBeFound("color.equals=" + DEFAULT_COLOR);

        // Get all the productLabelList where color equals to UPDATED_COLOR
        defaultProductLabelShouldNotBeFound("color.equals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllProductLabelsByColorIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where color not equals to DEFAULT_COLOR
        defaultProductLabelShouldNotBeFound("color.notEquals=" + DEFAULT_COLOR);

        // Get all the productLabelList where color not equals to UPDATED_COLOR
        defaultProductLabelShouldBeFound("color.notEquals=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllProductLabelsByColorIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where color in DEFAULT_COLOR or UPDATED_COLOR
        defaultProductLabelShouldBeFound("color.in=" + DEFAULT_COLOR + "," + UPDATED_COLOR);

        // Get all the productLabelList where color equals to UPDATED_COLOR
        defaultProductLabelShouldNotBeFound("color.in=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllProductLabelsByColorIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where color is not null
        defaultProductLabelShouldBeFound("color.specified=true");

        // Get all the productLabelList where color is null
        defaultProductLabelShouldNotBeFound("color.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelsByColorContainsSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where color contains DEFAULT_COLOR
        defaultProductLabelShouldBeFound("color.contains=" + DEFAULT_COLOR);

        // Get all the productLabelList where color contains UPDATED_COLOR
        defaultProductLabelShouldNotBeFound("color.contains=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllProductLabelsByColorNotContainsSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where color does not contain DEFAULT_COLOR
        defaultProductLabelShouldNotBeFound("color.doesNotContain=" + DEFAULT_COLOR);

        // Get all the productLabelList where color does not contain UPDATED_COLOR
        defaultProductLabelShouldBeFound("color.doesNotContain=" + UPDATED_COLOR);
    }

    @Test
    @Transactional
    void getAllProductLabelsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where type equals to DEFAULT_TYPE
        defaultProductLabelShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the productLabelList where type equals to UPDATED_TYPE
        defaultProductLabelShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductLabelsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where type not equals to DEFAULT_TYPE
        defaultProductLabelShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the productLabelList where type not equals to UPDATED_TYPE
        defaultProductLabelShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductLabelsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProductLabelShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the productLabelList where type equals to UPDATED_TYPE
        defaultProductLabelShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductLabelsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where type is not null
        defaultProductLabelShouldBeFound("type.specified=true");

        // Get all the productLabelList where type is null
        defaultProductLabelShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelsByTypeContainsSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where type contains DEFAULT_TYPE
        defaultProductLabelShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the productLabelList where type contains UPDATED_TYPE
        defaultProductLabelShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductLabelsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where type does not contain DEFAULT_TYPE
        defaultProductLabelShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the productLabelList where type does not contain UPDATED_TYPE
        defaultProductLabelShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductLabelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where activated equals to DEFAULT_ACTIVATED
        defaultProductLabelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productLabelList where activated equals to UPDATED_ACTIVATED
        defaultProductLabelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductLabelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductLabelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productLabelList where activated not equals to UPDATED_ACTIVATED
        defaultProductLabelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductLabelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductLabelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productLabelList where activated equals to UPDATED_ACTIVATED
        defaultProductLabelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductLabelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where activated is not null
        defaultProductLabelShouldBeFound("activated.specified=true");

        // Get all the productLabelList where activated is null
        defaultProductLabelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductLabelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productLabelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductLabelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductLabelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productLabelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductLabelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductLabelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productLabelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductLabelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where createdBy is not null
        defaultProductLabelShouldBeFound("createdBy.specified=true");

        // Get all the productLabelList where createdBy is null
        defaultProductLabelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductLabelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productLabelList where createdBy contains UPDATED_CREATED_BY
        defaultProductLabelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductLabelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productLabelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductLabelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductLabelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productLabelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductLabelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductLabelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productLabelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductLabelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductLabelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productLabelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductLabelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where createdDate is not null
        defaultProductLabelShouldBeFound("createdDate.specified=true");

        // Get all the productLabelList where createdDate is null
        defaultProductLabelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductLabelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productLabelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductLabelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductLabelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productLabelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductLabelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductLabelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productLabelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductLabelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where lastModifiedBy is not null
        defaultProductLabelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productLabelList where lastModifiedBy is null
        defaultProductLabelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductLabelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productLabelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductLabelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductLabelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productLabelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductLabelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductLabelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productLabelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductLabelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductLabelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productLabelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductLabelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductLabelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productLabelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductLabelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList where lastModifiedDate is not null
        defaultProductLabelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productLabelList where lastModifiedDate is null
        defaultProductLabelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelsByProductLabelRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);
        ProductLabelRel productLabelRel = ProductLabelRelResourceIT.createEntity(em);
        em.persist(productLabelRel);
        em.flush();
        productLabel.addProductLabelRel(productLabelRel);
        productLabelRepository.saveAndFlush(productLabel);
        Long productLabelRelId = productLabelRel.getId();

        // Get all the productLabelList where productLabelRel equals to productLabelRelId
        defaultProductLabelShouldBeFound("productLabelRelId.equals=" + productLabelRelId);

        // Get all the productLabelList where productLabelRel equals to (productLabelRelId + 1)
        defaultProductLabelShouldNotBeFound("productLabelRelId.equals=" + (productLabelRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductLabelShouldBeFound(String filter) throws Exception {
        restProductLabelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductLabelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductLabelShouldNotBeFound(String filter) throws Exception {
        restProductLabelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductLabelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductLabel() throws Exception {
        // Get the productLabel
        restProductLabelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductLabel() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();

        // Update the productLabel
        ProductLabel updatedProductLabel = productLabelRepository.findById(productLabel.getId()).get();
        // Disconnect from session so that the updates on updatedProductLabel are not directly saved in db
        em.detach(updatedProductLabel);
        updatedProductLabel
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR)
            .content(UPDATED_CONTENT)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(updatedProductLabel);

        restProductLabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productLabelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
        ProductLabel testProductLabel = productLabelList.get(productLabelList.size() - 1);
        assertThat(testProductLabel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductLabel.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProductLabel.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductLabel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductLabel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductLabel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductLabel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductLabel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productLabelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductLabelWithPatch() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();

        // Update the productLabel using partial update
        ProductLabel partialUpdatedProductLabel = new ProductLabel();
        partialUpdatedProductLabel.setId(productLabel.getId());

        partialUpdatedProductLabel.createdBy(UPDATED_CREATED_BY).lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductLabel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductLabel))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
        ProductLabel testProductLabel = productLabelList.get(productLabelList.size() - 1);
        assertThat(testProductLabel.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductLabel.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testProductLabel.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProductLabel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductLabel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductLabel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductLabel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductLabel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductLabelWithPatch() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();

        // Update the productLabel using partial update
        ProductLabel partialUpdatedProductLabel = new ProductLabel();
        partialUpdatedProductLabel.setId(productLabel.getId());

        partialUpdatedProductLabel
            .name(UPDATED_NAME)
            .color(UPDATED_COLOR)
            .content(UPDATED_CONTENT)
            .type(UPDATED_TYPE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductLabel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductLabel))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
        ProductLabel testProductLabel = productLabelList.get(productLabelList.size() - 1);
        assertThat(testProductLabel.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductLabel.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProductLabel.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductLabel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductLabel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductLabel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductLabel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductLabel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productLabelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductLabel() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        int databaseSizeBeforeDelete = productLabelRepository.findAll().size();

        // Delete the productLabel
        restProductLabelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productLabel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
