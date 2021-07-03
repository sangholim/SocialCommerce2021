package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductView;
import com.toy.project.domain.ProductViewContent;
import com.toy.project.repository.ProductViewContentRepository;
import com.toy.project.service.criteria.ProductViewContentCriteria;
import com.toy.project.service.dto.ProductViewContentDTO;
import com.toy.project.service.mapper.ProductViewContentMapper;
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
 * Integration tests for the {@link ProductViewContentResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductViewContentResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-view-contents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductViewContentRepository productViewContentRepository;

    @Autowired
    private ProductViewContentMapper productViewContentMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductViewContentMockMvc;

    private ProductViewContent productViewContent;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductViewContent createEntity(EntityManager em) {
        ProductViewContent productViewContent = new ProductViewContent()
            .name(DEFAULT_NAME)
            .content(DEFAULT_CONTENT)
            .isDetail(DEFAULT_IS_DETAIL)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productViewContent;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductViewContent createUpdatedEntity(EntityManager em) {
        ProductViewContent productViewContent = new ProductViewContent()
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .isDetail(UPDATED_IS_DETAIL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productViewContent;
    }

    @BeforeEach
    public void initTest() {
        productViewContent = createEntity(em);
    }

    @Test
    @Transactional
    void createProductViewContent() throws Exception {
        int databaseSizeBeforeCreate = productViewContentRepository.findAll().size();
        // Create the ProductViewContent
        ProductViewContentDTO productViewContentDTO = productViewContentMapper.toDto(productViewContent);
        restProductViewContentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewContentDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductViewContent in the database
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeCreate + 1);
        ProductViewContent testProductViewContent = productViewContentList.get(productViewContentList.size() - 1);
        assertThat(testProductViewContent.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductViewContent.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProductViewContent.getIsDetail()).isEqualTo(DEFAULT_IS_DETAIL);
        assertThat(testProductViewContent.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductViewContent.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductViewContent.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductViewContent.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductViewContent.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductViewContentWithExistingId() throws Exception {
        // Create the ProductViewContent with an existing ID
        productViewContent.setId(1L);
        ProductViewContentDTO productViewContentDTO = productViewContentMapper.toDto(productViewContent);

        int databaseSizeBeforeCreate = productViewContentRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductViewContentMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductViewContent in the database
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductViewContents() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList
        restProductViewContentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productViewContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
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
    void getProductViewContent() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get the productViewContent
        restProductViewContentMockMvc
            .perform(get(ENTITY_API_URL_ID, productViewContent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productViewContent.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
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
    void getProductViewContentsByIdFiltering() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        Long id = productViewContent.getId();

        defaultProductViewContentShouldBeFound("id.equals=" + id);
        defaultProductViewContentShouldNotBeFound("id.notEquals=" + id);

        defaultProductViewContentShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductViewContentShouldNotBeFound("id.greaterThan=" + id);

        defaultProductViewContentShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductViewContentShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where name equals to DEFAULT_NAME
        defaultProductViewContentShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productViewContentList where name equals to UPDATED_NAME
        defaultProductViewContentShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where name not equals to DEFAULT_NAME
        defaultProductViewContentShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productViewContentList where name not equals to UPDATED_NAME
        defaultProductViewContentShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductViewContentShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productViewContentList where name equals to UPDATED_NAME
        defaultProductViewContentShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where name is not null
        defaultProductViewContentShouldBeFound("name.specified=true");

        // Get all the productViewContentList where name is null
        defaultProductViewContentShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewContentsByNameContainsSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where name contains DEFAULT_NAME
        defaultProductViewContentShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productViewContentList where name contains UPDATED_NAME
        defaultProductViewContentShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where name does not contain DEFAULT_NAME
        defaultProductViewContentShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productViewContentList where name does not contain UPDATED_NAME
        defaultProductViewContentShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByIsDetailIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where isDetail equals to DEFAULT_IS_DETAIL
        defaultProductViewContentShouldBeFound("isDetail.equals=" + DEFAULT_IS_DETAIL);

        // Get all the productViewContentList where isDetail equals to UPDATED_IS_DETAIL
        defaultProductViewContentShouldNotBeFound("isDetail.equals=" + UPDATED_IS_DETAIL);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByIsDetailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where isDetail not equals to DEFAULT_IS_DETAIL
        defaultProductViewContentShouldNotBeFound("isDetail.notEquals=" + DEFAULT_IS_DETAIL);

        // Get all the productViewContentList where isDetail not equals to UPDATED_IS_DETAIL
        defaultProductViewContentShouldBeFound("isDetail.notEquals=" + UPDATED_IS_DETAIL);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByIsDetailIsInShouldWork() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where isDetail in DEFAULT_IS_DETAIL or UPDATED_IS_DETAIL
        defaultProductViewContentShouldBeFound("isDetail.in=" + DEFAULT_IS_DETAIL + "," + UPDATED_IS_DETAIL);

        // Get all the productViewContentList where isDetail equals to UPDATED_IS_DETAIL
        defaultProductViewContentShouldNotBeFound("isDetail.in=" + UPDATED_IS_DETAIL);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByIsDetailIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where isDetail is not null
        defaultProductViewContentShouldBeFound("isDetail.specified=true");

        // Get all the productViewContentList where isDetail is null
        defaultProductViewContentShouldNotBeFound("isDetail.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewContentsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where activated equals to DEFAULT_ACTIVATED
        defaultProductViewContentShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productViewContentList where activated equals to UPDATED_ACTIVATED
        defaultProductViewContentShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where activated not equals to DEFAULT_ACTIVATED
        defaultProductViewContentShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productViewContentList where activated not equals to UPDATED_ACTIVATED
        defaultProductViewContentShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductViewContentShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productViewContentList where activated equals to UPDATED_ACTIVATED
        defaultProductViewContentShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where activated is not null
        defaultProductViewContentShouldBeFound("activated.specified=true");

        // Get all the productViewContentList where activated is null
        defaultProductViewContentShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewContentsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductViewContentShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productViewContentList where createdBy equals to UPDATED_CREATED_BY
        defaultProductViewContentShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductViewContentShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productViewContentList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductViewContentShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductViewContentShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productViewContentList where createdBy equals to UPDATED_CREATED_BY
        defaultProductViewContentShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where createdBy is not null
        defaultProductViewContentShouldBeFound("createdBy.specified=true");

        // Get all the productViewContentList where createdBy is null
        defaultProductViewContentShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewContentsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where createdBy contains DEFAULT_CREATED_BY
        defaultProductViewContentShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productViewContentList where createdBy contains UPDATED_CREATED_BY
        defaultProductViewContentShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductViewContentShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productViewContentList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductViewContentShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductViewContentShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productViewContentList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductViewContentShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductViewContentShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productViewContentList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductViewContentShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductViewContentShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productViewContentList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductViewContentShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where createdDate is not null
        defaultProductViewContentShouldBeFound("createdDate.specified=true");

        // Get all the productViewContentList where createdDate is null
        defaultProductViewContentShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewContentsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductViewContentShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewContentList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductViewContentShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductViewContentShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewContentList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductViewContentShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductViewContentShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productViewContentList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductViewContentShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where lastModifiedBy is not null
        defaultProductViewContentShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productViewContentList where lastModifiedBy is null
        defaultProductViewContentShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewContentsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductViewContentShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewContentList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductViewContentShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductViewContentShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewContentList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductViewContentShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductViewContentShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productViewContentList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductViewContentShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductViewContentShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productViewContentList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductViewContentShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductViewContentShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productViewContentList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductViewContentShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewContentsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        // Get all the productViewContentList where lastModifiedDate is not null
        defaultProductViewContentShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productViewContentList where lastModifiedDate is null
        defaultProductViewContentShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewContentsByProductViewIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);
        ProductView productView = ProductViewResourceIT.createEntity(em);
        em.persist(productView);
        em.flush();
        productViewContent.setProductView(productView);
        productViewContentRepository.saveAndFlush(productViewContent);
        Long productViewId = productView.getId();

        // Get all the productViewContentList where productView equals to productViewId
        defaultProductViewContentShouldBeFound("productViewId.equals=" + productViewId);

        // Get all the productViewContentList where productView equals to (productViewId + 1)
        defaultProductViewContentShouldNotBeFound("productViewId.equals=" + (productViewId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductViewContentShouldBeFound(String filter) throws Exception {
        restProductViewContentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productViewContent.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].isDetail").value(hasItem(DEFAULT_IS_DETAIL.booleanValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductViewContentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductViewContentShouldNotBeFound(String filter) throws Exception {
        restProductViewContentMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductViewContentMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductViewContent() throws Exception {
        // Get the productViewContent
        restProductViewContentMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductViewContent() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        int databaseSizeBeforeUpdate = productViewContentRepository.findAll().size();

        // Update the productViewContent
        ProductViewContent updatedProductViewContent = productViewContentRepository.findById(productViewContent.getId()).get();
        // Disconnect from session so that the updates on updatedProductViewContent are not directly saved in db
        em.detach(updatedProductViewContent);
        updatedProductViewContent
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .isDetail(UPDATED_IS_DETAIL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductViewContentDTO productViewContentDTO = productViewContentMapper.toDto(updatedProductViewContent);

        restProductViewContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productViewContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewContentDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductViewContent in the database
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeUpdate);
        ProductViewContent testProductViewContent = productViewContentList.get(productViewContentList.size() - 1);
        assertThat(testProductViewContent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductViewContent.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductViewContent.getIsDetail()).isEqualTo(UPDATED_IS_DETAIL);
        assertThat(testProductViewContent.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductViewContent.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductViewContent.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductViewContent.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductViewContent.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductViewContent() throws Exception {
        int databaseSizeBeforeUpdate = productViewContentRepository.findAll().size();
        productViewContent.setId(count.incrementAndGet());

        // Create the ProductViewContent
        ProductViewContentDTO productViewContentDTO = productViewContentMapper.toDto(productViewContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductViewContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productViewContentDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductViewContent in the database
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductViewContent() throws Exception {
        int databaseSizeBeforeUpdate = productViewContentRepository.findAll().size();
        productViewContent.setId(count.incrementAndGet());

        // Create the ProductViewContent
        ProductViewContentDTO productViewContentDTO = productViewContentMapper.toDto(productViewContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewContentMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductViewContent in the database
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductViewContent() throws Exception {
        int databaseSizeBeforeUpdate = productViewContentRepository.findAll().size();
        productViewContent.setId(count.incrementAndGet());

        // Create the ProductViewContent
        ProductViewContentDTO productViewContentDTO = productViewContentMapper.toDto(productViewContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewContentMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewContentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductViewContent in the database
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductViewContentWithPatch() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        int databaseSizeBeforeUpdate = productViewContentRepository.findAll().size();

        // Update the productViewContent using partial update
        ProductViewContent partialUpdatedProductViewContent = new ProductViewContent();
        partialUpdatedProductViewContent.setId(productViewContent.getId());

        partialUpdatedProductViewContent
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductViewContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductViewContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductViewContent))
            )
            .andExpect(status().isOk());

        // Validate the ProductViewContent in the database
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeUpdate);
        ProductViewContent testProductViewContent = productViewContentList.get(productViewContentList.size() - 1);
        assertThat(testProductViewContent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductViewContent.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductViewContent.getIsDetail()).isEqualTo(DEFAULT_IS_DETAIL);
        assertThat(testProductViewContent.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductViewContent.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductViewContent.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductViewContent.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductViewContent.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductViewContentWithPatch() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        int databaseSizeBeforeUpdate = productViewContentRepository.findAll().size();

        // Update the productViewContent using partial update
        ProductViewContent partialUpdatedProductViewContent = new ProductViewContent();
        partialUpdatedProductViewContent.setId(productViewContent.getId());

        partialUpdatedProductViewContent
            .name(UPDATED_NAME)
            .content(UPDATED_CONTENT)
            .isDetail(UPDATED_IS_DETAIL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductViewContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductViewContent.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductViewContent))
            )
            .andExpect(status().isOk());

        // Validate the ProductViewContent in the database
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeUpdate);
        ProductViewContent testProductViewContent = productViewContentList.get(productViewContentList.size() - 1);
        assertThat(testProductViewContent.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductViewContent.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductViewContent.getIsDetail()).isEqualTo(UPDATED_IS_DETAIL);
        assertThat(testProductViewContent.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductViewContent.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductViewContent.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductViewContent.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductViewContent.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductViewContent() throws Exception {
        int databaseSizeBeforeUpdate = productViewContentRepository.findAll().size();
        productViewContent.setId(count.incrementAndGet());

        // Create the ProductViewContent
        ProductViewContentDTO productViewContentDTO = productViewContentMapper.toDto(productViewContent);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductViewContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productViewContentDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productViewContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductViewContent in the database
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductViewContent() throws Exception {
        int databaseSizeBeforeUpdate = productViewContentRepository.findAll().size();
        productViewContent.setId(count.incrementAndGet());

        // Create the ProductViewContent
        ProductViewContentDTO productViewContentDTO = productViewContentMapper.toDto(productViewContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewContentMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productViewContentDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductViewContent in the database
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductViewContent() throws Exception {
        int databaseSizeBeforeUpdate = productViewContentRepository.findAll().size();
        productViewContent.setId(count.incrementAndGet());

        // Create the ProductViewContent
        ProductViewContentDTO productViewContentDTO = productViewContentMapper.toDto(productViewContent);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewContentMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productViewContentDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductViewContent in the database
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductViewContent() throws Exception {
        // Initialize the database
        productViewContentRepository.saveAndFlush(productViewContent);

        int databaseSizeBeforeDelete = productViewContentRepository.findAll().size();

        // Delete the productViewContent
        restProductViewContentMockMvc
            .perform(delete(ENTITY_API_URL_ID, productViewContent.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductViewContent> productViewContentList = productViewContentRepository.findAll();
        assertThat(productViewContentList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
