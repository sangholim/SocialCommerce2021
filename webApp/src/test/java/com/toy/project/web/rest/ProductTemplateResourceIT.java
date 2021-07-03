package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductTemplate;
import com.toy.project.domain.ProductTemplateRel;
import com.toy.project.repository.ProductTemplateRepository;
import com.toy.project.service.criteria.ProductTemplateCriteria;
import com.toy.project.service.dto.ProductTemplateDTO;
import com.toy.project.service.mapper.ProductTemplateMapper;
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
 * Integration tests for the {@link ProductTemplateResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductTemplateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_FILE_URL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductTemplateRepository productTemplateRepository;

    @Autowired
    private ProductTemplateMapper productTemplateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductTemplateMockMvc;

    private ProductTemplate productTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTemplate createEntity(EntityManager em) {
        ProductTemplate productTemplate = new ProductTemplate()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .contentFileUrl(DEFAULT_CONTENT_FILE_URL)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTemplate createUpdatedEntity(EntityManager em) {
        ProductTemplate productTemplate = new ProductTemplate()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .contentFileUrl(UPDATED_CONTENT_FILE_URL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productTemplate;
    }

    @BeforeEach
    public void initTest() {
        productTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createProductTemplate() throws Exception {
        int databaseSizeBeforeCreate = productTemplateRepository.findAll().size();
        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);
        restProductTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        ProductTemplate testProductTemplate = productTemplateList.get(productTemplateList.size() - 1);
        assertThat(testProductTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductTemplate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductTemplate.getContentFileUrl()).isEqualTo(DEFAULT_CONTENT_FILE_URL);
        assertThat(testProductTemplate.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductTemplate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductTemplate.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductTemplate.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductTemplateWithExistingId() throws Exception {
        // Create the ProductTemplate with an existing ID
        productTemplate.setId(1L);
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        int databaseSizeBeforeCreate = productTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductTemplates() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList
        restProductTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].contentFileUrl").value(hasItem(DEFAULT_CONTENT_FILE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductTemplate() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get the productTemplate
        restProductTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, productTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.contentFileUrl").value(DEFAULT_CONTENT_FILE_URL))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductTemplatesByIdFiltering() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        Long id = productTemplate.getId();

        defaultProductTemplateShouldBeFound("id.equals=" + id);
        defaultProductTemplateShouldNotBeFound("id.notEquals=" + id);

        defaultProductTemplateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductTemplateShouldNotBeFound("id.greaterThan=" + id);

        defaultProductTemplateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductTemplateShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where name equals to DEFAULT_NAME
        defaultProductTemplateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productTemplateList where name equals to UPDATED_NAME
        defaultProductTemplateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where name not equals to DEFAULT_NAME
        defaultProductTemplateShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productTemplateList where name not equals to UPDATED_NAME
        defaultProductTemplateShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductTemplateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productTemplateList where name equals to UPDATED_NAME
        defaultProductTemplateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where name is not null
        defaultProductTemplateShouldBeFound("name.specified=true");

        // Get all the productTemplateList where name is null
        defaultProductTemplateShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplatesByNameContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where name contains DEFAULT_NAME
        defaultProductTemplateShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productTemplateList where name contains UPDATED_NAME
        defaultProductTemplateShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where name does not contain DEFAULT_NAME
        defaultProductTemplateShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productTemplateList where name does not contain UPDATED_NAME
        defaultProductTemplateShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where type equals to DEFAULT_TYPE
        defaultProductTemplateShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the productTemplateList where type equals to UPDATED_TYPE
        defaultProductTemplateShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where type not equals to DEFAULT_TYPE
        defaultProductTemplateShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the productTemplateList where type not equals to UPDATED_TYPE
        defaultProductTemplateShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProductTemplateShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the productTemplateList where type equals to UPDATED_TYPE
        defaultProductTemplateShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where type is not null
        defaultProductTemplateShouldBeFound("type.specified=true");

        // Get all the productTemplateList where type is null
        defaultProductTemplateShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplatesByTypeContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where type contains DEFAULT_TYPE
        defaultProductTemplateShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the productTemplateList where type contains UPDATED_TYPE
        defaultProductTemplateShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where type does not contain DEFAULT_TYPE
        defaultProductTemplateShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the productTemplateList where type does not contain UPDATED_TYPE
        defaultProductTemplateShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByContentFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where contentFileUrl equals to DEFAULT_CONTENT_FILE_URL
        defaultProductTemplateShouldBeFound("contentFileUrl.equals=" + DEFAULT_CONTENT_FILE_URL);

        // Get all the productTemplateList where contentFileUrl equals to UPDATED_CONTENT_FILE_URL
        defaultProductTemplateShouldNotBeFound("contentFileUrl.equals=" + UPDATED_CONTENT_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByContentFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where contentFileUrl not equals to DEFAULT_CONTENT_FILE_URL
        defaultProductTemplateShouldNotBeFound("contentFileUrl.notEquals=" + DEFAULT_CONTENT_FILE_URL);

        // Get all the productTemplateList where contentFileUrl not equals to UPDATED_CONTENT_FILE_URL
        defaultProductTemplateShouldBeFound("contentFileUrl.notEquals=" + UPDATED_CONTENT_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByContentFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where contentFileUrl in DEFAULT_CONTENT_FILE_URL or UPDATED_CONTENT_FILE_URL
        defaultProductTemplateShouldBeFound("contentFileUrl.in=" + DEFAULT_CONTENT_FILE_URL + "," + UPDATED_CONTENT_FILE_URL);

        // Get all the productTemplateList where contentFileUrl equals to UPDATED_CONTENT_FILE_URL
        defaultProductTemplateShouldNotBeFound("contentFileUrl.in=" + UPDATED_CONTENT_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByContentFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where contentFileUrl is not null
        defaultProductTemplateShouldBeFound("contentFileUrl.specified=true");

        // Get all the productTemplateList where contentFileUrl is null
        defaultProductTemplateShouldNotBeFound("contentFileUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplatesByContentFileUrlContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where contentFileUrl contains DEFAULT_CONTENT_FILE_URL
        defaultProductTemplateShouldBeFound("contentFileUrl.contains=" + DEFAULT_CONTENT_FILE_URL);

        // Get all the productTemplateList where contentFileUrl contains UPDATED_CONTENT_FILE_URL
        defaultProductTemplateShouldNotBeFound("contentFileUrl.contains=" + UPDATED_CONTENT_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByContentFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where contentFileUrl does not contain DEFAULT_CONTENT_FILE_URL
        defaultProductTemplateShouldNotBeFound("contentFileUrl.doesNotContain=" + DEFAULT_CONTENT_FILE_URL);

        // Get all the productTemplateList where contentFileUrl does not contain UPDATED_CONTENT_FILE_URL
        defaultProductTemplateShouldBeFound("contentFileUrl.doesNotContain=" + UPDATED_CONTENT_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where activated equals to DEFAULT_ACTIVATED
        defaultProductTemplateShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productTemplateList where activated equals to UPDATED_ACTIVATED
        defaultProductTemplateShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where activated not equals to DEFAULT_ACTIVATED
        defaultProductTemplateShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productTemplateList where activated not equals to UPDATED_ACTIVATED
        defaultProductTemplateShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductTemplateShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productTemplateList where activated equals to UPDATED_ACTIVATED
        defaultProductTemplateShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where activated is not null
        defaultProductTemplateShouldBeFound("activated.specified=true");

        // Get all the productTemplateList where activated is null
        defaultProductTemplateShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplatesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductTemplateShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productTemplateList where createdBy equals to UPDATED_CREATED_BY
        defaultProductTemplateShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductTemplateShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productTemplateList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductTemplateShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductTemplateShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productTemplateList where createdBy equals to UPDATED_CREATED_BY
        defaultProductTemplateShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where createdBy is not null
        defaultProductTemplateShouldBeFound("createdBy.specified=true");

        // Get all the productTemplateList where createdBy is null
        defaultProductTemplateShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplatesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where createdBy contains DEFAULT_CREATED_BY
        defaultProductTemplateShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productTemplateList where createdBy contains UPDATED_CREATED_BY
        defaultProductTemplateShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductTemplateShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productTemplateList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductTemplateShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductTemplateShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productTemplateList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductTemplateShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductTemplateShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productTemplateList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductTemplateShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductTemplateShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productTemplateList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductTemplateShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where createdDate is not null
        defaultProductTemplateShouldBeFound("createdDate.specified=true");

        // Get all the productTemplateList where createdDate is null
        defaultProductTemplateShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplatesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductTemplateShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTemplateList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductTemplateShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTemplateList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productTemplateList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where lastModifiedBy is not null
        defaultProductTemplateShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productTemplateList where lastModifiedBy is null
        defaultProductTemplateShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplatesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductTemplateShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTemplateList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductTemplateShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTemplateList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductTemplateShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productTemplateList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductTemplateShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductTemplateShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productTemplateList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductTemplateShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductTemplateShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productTemplateList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductTemplateShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplatesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList where lastModifiedDate is not null
        defaultProductTemplateShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productTemplateList where lastModifiedDate is null
        defaultProductTemplateShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplatesByProductTemplateRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);
        ProductTemplateRel productTemplateRel = ProductTemplateRelResourceIT.createEntity(em);
        em.persist(productTemplateRel);
        em.flush();
        productTemplate.addProductTemplateRel(productTemplateRel);
        productTemplateRepository.saveAndFlush(productTemplate);
        Long productTemplateRelId = productTemplateRel.getId();

        // Get all the productTemplateList where productTemplateRel equals to productTemplateRelId
        defaultProductTemplateShouldBeFound("productTemplateRelId.equals=" + productTemplateRelId);

        // Get all the productTemplateList where productTemplateRel equals to (productTemplateRelId + 1)
        defaultProductTemplateShouldNotBeFound("productTemplateRelId.equals=" + (productTemplateRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductTemplateShouldBeFound(String filter) throws Exception {
        restProductTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].contentFileUrl").value(hasItem(DEFAULT_CONTENT_FILE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductTemplateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductTemplateShouldNotBeFound(String filter) throws Exception {
        restProductTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductTemplateMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductTemplate() throws Exception {
        // Get the productTemplate
        restProductTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductTemplate() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();

        // Update the productTemplate
        ProductTemplate updatedProductTemplate = productTemplateRepository.findById(productTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedProductTemplate are not directly saved in db
        em.detach(updatedProductTemplate);
        updatedProductTemplate
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .contentFileUrl(UPDATED_CONTENT_FILE_URL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(updatedProductTemplate);

        restProductTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplate testProductTemplate = productTemplateList.get(productTemplateList.size() - 1);
        assertThat(testProductTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductTemplate.getContentFileUrl()).isEqualTo(UPDATED_CONTENT_FILE_URL);
        assertThat(testProductTemplate.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTemplate.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductTemplateWithPatch() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();

        // Update the productTemplate using partial update
        ProductTemplate partialUpdatedProductTemplate = new ProductTemplate();
        partialUpdatedProductTemplate.setId(productTemplate.getId());

        partialUpdatedProductTemplate.activated(UPDATED_ACTIVATED).lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplate testProductTemplate = productTemplateList.get(productTemplateList.size() - 1);
        assertThat(testProductTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductTemplate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductTemplate.getContentFileUrl()).isEqualTo(DEFAULT_CONTENT_FILE_URL);
        assertThat(testProductTemplate.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductTemplate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductTemplate.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductTemplate.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductTemplateWithPatch() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();

        // Update the productTemplate using partial update
        ProductTemplate partialUpdatedProductTemplate = new ProductTemplate();
        partialUpdatedProductTemplate.setId(productTemplate.getId());

        partialUpdatedProductTemplate
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .contentFileUrl(UPDATED_CONTENT_FILE_URL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplate testProductTemplate = productTemplateList.get(productTemplateList.size() - 1);
        assertThat(testProductTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductTemplate.getContentFileUrl()).isEqualTo(UPDATED_CONTENT_FILE_URL);
        assertThat(testProductTemplate.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTemplate.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productTemplateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductTemplate() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        int databaseSizeBeforeDelete = productTemplateRepository.findAll().size();

        // Delete the productTemplate
        restProductTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, productTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
