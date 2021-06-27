package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductView;
import com.toy.project.domain.ProductViewRel;
import com.toy.project.repository.ProductViewRepository;
import com.toy.project.service.criteria.ProductViewCriteria;
import com.toy.project.service.dto.ProductViewDTO;
import com.toy.project.service.mapper.ProductViewMapper;
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
 * Integration tests for the {@link ProductViewResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductViewResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_DETAIL = false;
    private static final Boolean UPDATED_IS_DETAIL = true;

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

    private static final String ENTITY_API_URL = "/api/product-views";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductViewRepository productViewRepository;

    @Autowired
    private ProductViewMapper productViewMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductViewMockMvc;

    private ProductView productView;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductView createEntity(EntityManager em) {
        ProductView productView = new ProductView()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .content(DEFAULT_CONTENT)
            .isDetail(DEFAULT_IS_DETAIL)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productView;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductView createUpdatedEntity(EntityManager em) {
        ProductView productView = new ProductView()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .isDetail(UPDATED_IS_DETAIL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productView;
    }

    @BeforeEach
    public void initTest() {
        productView = createEntity(em);
    }

    @Test
    @Transactional
    void createProductView() throws Exception {
        int databaseSizeBeforeCreate = productViewRepository.findAll().size();
        // Create the ProductView
        ProductViewDTO productViewDTO = productViewMapper.toDto(productView);
        restProductViewMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productViewDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductView in the database
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeCreate + 1);
        ProductView testProductView = productViewList.get(productViewList.size() - 1);
        assertThat(testProductView.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductView.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductView.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProductView.getIsDetail()).isEqualTo(DEFAULT_IS_DETAIL);
        assertThat(testProductView.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductView.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductView.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductView.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductView.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductViewWithExistingId() throws Exception {
        // Create the ProductView with an existing ID
        productView.setId(1L);
        ProductViewDTO productViewDTO = productViewMapper.toDto(productView);

        int databaseSizeBeforeCreate = productViewRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductViewMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductView in the database
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductViews() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList
        restProductViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productView.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].isDetail").value(hasItem(DEFAULT_IS_DETAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductView() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get the productView
        restProductViewMockMvc
            .perform(get(ENTITY_API_URL_ID, productView.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productView.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.isDetail").value(DEFAULT_IS_DETAIL.booleanValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductViewsByIdFiltering() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        Long id = productView.getId();

        defaultProductViewShouldBeFound("id.equals=" + id);
        defaultProductViewShouldNotBeFound("id.notEquals=" + id);

        defaultProductViewShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductViewShouldNotBeFound("id.greaterThan=" + id);

        defaultProductViewShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductViewShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductViewsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where name equals to DEFAULT_NAME
        defaultProductViewShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productViewList where name equals to UPDATED_NAME
        defaultProductViewShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductViewsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where name not equals to DEFAULT_NAME
        defaultProductViewShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productViewList where name not equals to UPDATED_NAME
        defaultProductViewShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductViewsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductViewShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productViewList where name equals to UPDATED_NAME
        defaultProductViewShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductViewsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where name is not null
        defaultProductViewShouldBeFound("name.specified=true");

        // Get all the productViewList where name is null
        defaultProductViewShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewsByNameContainsSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where name contains DEFAULT_NAME
        defaultProductViewShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productViewList where name contains UPDATED_NAME
        defaultProductViewShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductViewsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where name does not contain DEFAULT_NAME
        defaultProductViewShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productViewList where name does not contain UPDATED_NAME
        defaultProductViewShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductViewsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where type equals to DEFAULT_TYPE
        defaultProductViewShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the productViewList where type equals to UPDATED_TYPE
        defaultProductViewShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductViewsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where type not equals to DEFAULT_TYPE
        defaultProductViewShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the productViewList where type not equals to UPDATED_TYPE
        defaultProductViewShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductViewsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProductViewShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the productViewList where type equals to UPDATED_TYPE
        defaultProductViewShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductViewsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where type is not null
        defaultProductViewShouldBeFound("type.specified=true");

        // Get all the productViewList where type is null
        defaultProductViewShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewsByTypeContainsSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where type contains DEFAULT_TYPE
        defaultProductViewShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the productViewList where type contains UPDATED_TYPE
        defaultProductViewShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductViewsByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where type does not contain DEFAULT_TYPE
        defaultProductViewShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the productViewList where type does not contain UPDATED_TYPE
        defaultProductViewShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductViewsByIsDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where isDetail equals to DEFAULT_IS_DETAIL
        defaultProductViewShouldBeFound("isDetail.equals=" + DEFAULT_IS_DETAIL);

        // Get all the productViewList where isDetail equals to UPDATED_IS_DETAIL
        defaultProductViewShouldNotBeFound("isDetail.equals=" + UPDATED_IS_DETAIL);
    }

    @Test
    @Transactional
    void getAllProductViewsByIsDetailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where isDetail not equals to DEFAULT_IS_DETAIL
        defaultProductViewShouldNotBeFound("isDetail.notEquals=" + DEFAULT_IS_DETAIL);

        // Get all the productViewList where isDetail not equals to UPDATED_IS_DETAIL
        defaultProductViewShouldBeFound("isDetail.notEquals=" + UPDATED_IS_DETAIL);
    }

    @Test
    @Transactional
    void getAllProductViewsByIsDetailIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where isDetail in DEFAULT_IS_DETAIL or UPDATED_IS_DETAIL
        defaultProductViewShouldBeFound("isDetail.in=" + DEFAULT_IS_DETAIL + "," + UPDATED_IS_DETAIL);

        // Get all the productViewList where isDetail equals to UPDATED_IS_DETAIL
        defaultProductViewShouldNotBeFound("isDetail.in=" + UPDATED_IS_DETAIL);
    }

    @Test
    @Transactional
    void getAllProductViewsByIsDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where isDetail is not null
        defaultProductViewShouldBeFound("isDetail.specified=true");

        // Get all the productViewList where isDetail is null
        defaultProductViewShouldNotBeFound("isDetail.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where activated equals to DEFAULT_ACTIVATED
        defaultProductViewShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productViewList where activated equals to UPDATED_ACTIVATED
        defaultProductViewShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductViewsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where activated not equals to DEFAULT_ACTIVATED
        defaultProductViewShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productViewList where activated not equals to UPDATED_ACTIVATED
        defaultProductViewShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductViewsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductViewShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productViewList where activated equals to UPDATED_ACTIVATED
        defaultProductViewShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductViewsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where activated is not null
        defaultProductViewShouldBeFound("activated.specified=true");

        // Get all the productViewList where activated is null
        defaultProductViewShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductViewShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productViewList where createdBy equals to UPDATED_CREATED_BY
        defaultProductViewShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductViewShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productViewList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductViewShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductViewShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productViewList where createdBy equals to UPDATED_CREATED_BY
        defaultProductViewShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where createdBy is not null
        defaultProductViewShouldBeFound("createdBy.specified=true");

        // Get all the productViewList where createdBy is null
        defaultProductViewShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where createdBy contains DEFAULT_CREATED_BY
        defaultProductViewShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productViewList where createdBy contains UPDATED_CREATED_BY
        defaultProductViewShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductViewShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productViewList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductViewShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductViewShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productViewList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductViewShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductViewShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productViewList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductViewShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductViewShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productViewList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductViewShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where createdDate is not null
        defaultProductViewShouldBeFound("createdDate.specified=true");

        // Get all the productViewList where createdDate is null
        defaultProductViewShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductViewShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductViewShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductViewShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductViewShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductViewShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productViewList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductViewShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where lastModifiedBy is not null
        defaultProductViewShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productViewList where lastModifiedBy is null
        defaultProductViewShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductViewShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductViewShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductViewShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductViewShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductViewShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productViewList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductViewShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductViewShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productViewList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductViewShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductViewShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productViewList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductViewShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        // Get all the productViewList where lastModifiedDate is not null
        defaultProductViewShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productViewList where lastModifiedDate is null
        defaultProductViewShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewsByProductViewRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);
        ProductViewRel productViewRel = ProductViewRelResourceIT.createEntity(em);
        em.persist(productViewRel);
        em.flush();
        productView.addProductViewRel(productViewRel);
        productViewRepository.saveAndFlush(productView);
        Long productViewRelId = productViewRel.getId();

        // Get all the productViewList where productViewRel equals to productViewRelId
        defaultProductViewShouldBeFound("productViewRelId.equals=" + productViewRelId);

        // Get all the productViewList where productViewRel equals to (productViewRelId + 1)
        defaultProductViewShouldNotBeFound("productViewRelId.equals=" + (productViewRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductViewShouldBeFound(String filter) throws Exception {
        restProductViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productView.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].isDetail").value(hasItem(DEFAULT_IS_DETAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductViewShouldNotBeFound(String filter) throws Exception {
        restProductViewMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductViewMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductView() throws Exception {
        // Get the productView
        restProductViewMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductView() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        int databaseSizeBeforeUpdate = productViewRepository.findAll().size();

        // Update the productView
        ProductView updatedProductView = productViewRepository.findById(productView.getId()).get();
        // Disconnect from session so that the updates on updatedProductView are not directly saved in db
        em.detach(updatedProductView);
        updatedProductView
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .isDetail(UPDATED_IS_DETAIL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductViewDTO productViewDTO = productViewMapper.toDto(updatedProductView);

        restProductViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productViewDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductView in the database
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeUpdate);
        ProductView testProductView = productViewList.get(productViewList.size() - 1);
        assertThat(testProductView.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductView.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductView.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductView.getIsDetail()).isEqualTo(UPDATED_IS_DETAIL);
        assertThat(testProductView.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductView.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductView.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductView.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductView() throws Exception {
        int databaseSizeBeforeUpdate = productViewRepository.findAll().size();
        productView.setId(count.incrementAndGet());

        // Create the ProductView
        ProductViewDTO productViewDTO = productViewMapper.toDto(productView);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productViewDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductView in the database
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductView() throws Exception {
        int databaseSizeBeforeUpdate = productViewRepository.findAll().size();
        productView.setId(count.incrementAndGet());

        // Create the ProductView
        ProductViewDTO productViewDTO = productViewMapper.toDto(productView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductView in the database
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductView() throws Exception {
        int databaseSizeBeforeUpdate = productViewRepository.findAll().size();
        productView.setId(count.incrementAndGet());

        // Create the ProductView
        ProductViewDTO productViewDTO = productViewMapper.toDto(productView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productViewDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductView in the database
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductViewWithPatch() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        int databaseSizeBeforeUpdate = productViewRepository.findAll().size();

        // Update the productView using partial update
        ProductView partialUpdatedProductView = new ProductView();
        partialUpdatedProductView.setId(productView.getId());

        partialUpdatedProductView
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .isDetail(UPDATED_IS_DETAIL)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductView))
            )
            .andExpect(status().isOk());

        // Validate the ProductView in the database
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeUpdate);
        ProductView testProductView = productViewList.get(productViewList.size() - 1);
        assertThat(testProductView.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductView.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductView.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductView.getIsDetail()).isEqualTo(UPDATED_IS_DETAIL);
        assertThat(testProductView.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductView.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductView.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductView.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductViewWithPatch() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        int databaseSizeBeforeUpdate = productViewRepository.findAll().size();

        // Update the productView using partial update
        ProductView partialUpdatedProductView = new ProductView();
        partialUpdatedProductView.setId(productView.getId());

        partialUpdatedProductView
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .isDetail(UPDATED_IS_DETAIL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductView.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductView))
            )
            .andExpect(status().isOk());

        // Validate the ProductView in the database
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeUpdate);
        ProductView testProductView = productViewList.get(productViewList.size() - 1);
        assertThat(testProductView.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductView.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductView.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductView.getIsDetail()).isEqualTo(UPDATED_IS_DETAIL);
        assertThat(testProductView.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductView.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductView.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductView.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductView.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductView() throws Exception {
        int databaseSizeBeforeUpdate = productViewRepository.findAll().size();
        productView.setId(count.incrementAndGet());

        // Create the ProductView
        ProductViewDTO productViewDTO = productViewMapper.toDto(productView);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productViewDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductView in the database
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductView() throws Exception {
        int databaseSizeBeforeUpdate = productViewRepository.findAll().size();
        productView.setId(count.incrementAndGet());

        // Create the ProductView
        ProductViewDTO productViewDTO = productViewMapper.toDto(productView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productViewDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductView in the database
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductView() throws Exception {
        int databaseSizeBeforeUpdate = productViewRepository.findAll().size();
        productView.setId(count.incrementAndGet());

        // Create the ProductView
        ProductViewDTO productViewDTO = productViewMapper.toDto(productView);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productViewDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductView in the database
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductView() throws Exception {
        // Initialize the database
        productViewRepository.saveAndFlush(productView);

        int databaseSizeBeforeDelete = productViewRepository.findAll().size();

        // Delete the productView
        restProductViewMockMvc
            .perform(delete(ENTITY_API_URL_ID, productView.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductView> productViewList = productViewRepository.findAll();
        assertThat(productViewList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
