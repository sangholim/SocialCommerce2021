package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductNotice;
import com.toy.project.domain.ProductNoticeRel;
import com.toy.project.repository.ProductNoticeRepository;
import com.toy.project.service.criteria.ProductNoticeCriteria;
import com.toy.project.service.dto.ProductNoticeDTO;
import com.toy.project.service.mapper.ProductNoticeMapper;
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
 * Integration tests for the {@link ProductNoticeResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductNoticeResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-notices";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductNoticeRepository productNoticeRepository;

    @Autowired
    private ProductNoticeMapper productNoticeMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductNoticeMockMvc;

    private ProductNotice productNotice;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductNotice createEntity(EntityManager em) {
        ProductNotice productNotice = new ProductNotice()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .content(DEFAULT_CONTENT)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productNotice;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductNotice createUpdatedEntity(EntityManager em) {
        ProductNotice productNotice = new ProductNotice()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productNotice;
    }

    @BeforeEach
    public void initTest() {
        productNotice = createEntity(em);
    }

    @Test
    @Transactional
    void createProductNotice() throws Exception {
        int databaseSizeBeforeCreate = productNoticeRepository.findAll().size();
        // Create the ProductNotice
        ProductNoticeDTO productNoticeDTO = productNoticeMapper.toDto(productNotice);
        restProductNoticeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productNoticeDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductNotice in the database
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeCreate + 1);
        ProductNotice testProductNotice = productNoticeList.get(productNoticeList.size() - 1);
        assertThat(testProductNotice.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductNotice.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductNotice.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProductNotice.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductNotice.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductNotice.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductNotice.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductNotice.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductNoticeWithExistingId() throws Exception {
        // Create the ProductNotice with an existing ID
        productNotice.setId(1L);
        ProductNoticeDTO productNoticeDTO = productNoticeMapper.toDto(productNotice);

        int databaseSizeBeforeCreate = productNoticeRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductNoticeMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNotice in the database
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductNotices() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList
        restProductNoticeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productNotice.getId().intValue())))
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
    void getProductNotice() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get the productNotice
        restProductNoticeMockMvc
            .perform(get(ENTITY_API_URL_ID, productNotice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productNotice.getId().intValue()))
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
    void getProductNoticesByIdFiltering() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        Long id = productNotice.getId();

        defaultProductNoticeShouldBeFound("id.equals=" + id);
        defaultProductNoticeShouldNotBeFound("id.notEquals=" + id);

        defaultProductNoticeShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductNoticeShouldNotBeFound("id.greaterThan=" + id);

        defaultProductNoticeShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductNoticeShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductNoticesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where name equals to DEFAULT_NAME
        defaultProductNoticeShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productNoticeList where name equals to UPDATED_NAME
        defaultProductNoticeShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductNoticesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where name not equals to DEFAULT_NAME
        defaultProductNoticeShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productNoticeList where name not equals to UPDATED_NAME
        defaultProductNoticeShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductNoticesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductNoticeShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productNoticeList where name equals to UPDATED_NAME
        defaultProductNoticeShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductNoticesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where name is not null
        defaultProductNoticeShouldBeFound("name.specified=true");

        // Get all the productNoticeList where name is null
        defaultProductNoticeShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticesByNameContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where name contains DEFAULT_NAME
        defaultProductNoticeShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productNoticeList where name contains UPDATED_NAME
        defaultProductNoticeShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductNoticesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where name does not contain DEFAULT_NAME
        defaultProductNoticeShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productNoticeList where name does not contain UPDATED_NAME
        defaultProductNoticeShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductNoticesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where type equals to DEFAULT_TYPE
        defaultProductNoticeShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the productNoticeList where type equals to UPDATED_TYPE
        defaultProductNoticeShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductNoticesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where type not equals to DEFAULT_TYPE
        defaultProductNoticeShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the productNoticeList where type not equals to UPDATED_TYPE
        defaultProductNoticeShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductNoticesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProductNoticeShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the productNoticeList where type equals to UPDATED_TYPE
        defaultProductNoticeShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductNoticesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where type is not null
        defaultProductNoticeShouldBeFound("type.specified=true");

        // Get all the productNoticeList where type is null
        defaultProductNoticeShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticesByTypeContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where type contains DEFAULT_TYPE
        defaultProductNoticeShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the productNoticeList where type contains UPDATED_TYPE
        defaultProductNoticeShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductNoticesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where type does not contain DEFAULT_TYPE
        defaultProductNoticeShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the productNoticeList where type does not contain UPDATED_TYPE
        defaultProductNoticeShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductNoticesByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where activated equals to DEFAULT_ACTIVATED
        defaultProductNoticeShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productNoticeList where activated equals to UPDATED_ACTIVATED
        defaultProductNoticeShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductNoticesByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where activated not equals to DEFAULT_ACTIVATED
        defaultProductNoticeShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productNoticeList where activated not equals to UPDATED_ACTIVATED
        defaultProductNoticeShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductNoticesByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductNoticeShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productNoticeList where activated equals to UPDATED_ACTIVATED
        defaultProductNoticeShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductNoticesByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where activated is not null
        defaultProductNoticeShouldBeFound("activated.specified=true");

        // Get all the productNoticeList where activated is null
        defaultProductNoticeShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductNoticeShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productNoticeList where createdBy equals to UPDATED_CREATED_BY
        defaultProductNoticeShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductNoticeShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productNoticeList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductNoticeShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductNoticeShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productNoticeList where createdBy equals to UPDATED_CREATED_BY
        defaultProductNoticeShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where createdBy is not null
        defaultProductNoticeShouldBeFound("createdBy.specified=true");

        // Get all the productNoticeList where createdBy is null
        defaultProductNoticeShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where createdBy contains DEFAULT_CREATED_BY
        defaultProductNoticeShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productNoticeList where createdBy contains UPDATED_CREATED_BY
        defaultProductNoticeShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductNoticeShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productNoticeList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductNoticeShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductNoticeShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productNoticeList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductNoticeShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductNoticeShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productNoticeList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductNoticeShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductNoticeShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productNoticeList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductNoticeShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where createdDate is not null
        defaultProductNoticeShouldBeFound("createdDate.specified=true");

        // Get all the productNoticeList where createdDate is null
        defaultProductNoticeShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductNoticeShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productNoticeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductNoticeShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productNoticeList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productNoticeList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where lastModifiedBy is not null
        defaultProductNoticeShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productNoticeList where lastModifiedBy is null
        defaultProductNoticeShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductNoticeShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productNoticeList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductNoticeShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productNoticeList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductNoticeShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productNoticeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductNoticeShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductNoticeShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productNoticeList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductNoticeShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductNoticeShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productNoticeList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductNoticeShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        // Get all the productNoticeList where lastModifiedDate is not null
        defaultProductNoticeShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productNoticeList where lastModifiedDate is null
        defaultProductNoticeShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticesByProductNoticeRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);
        ProductNoticeRel productNoticeRel = ProductNoticeRelResourceIT.createEntity(em);
        em.persist(productNoticeRel);
        em.flush();
        productNotice.addProductNoticeRel(productNoticeRel);
        productNoticeRepository.saveAndFlush(productNotice);
        Long productNoticeRelId = productNoticeRel.getId();

        // Get all the productNoticeList where productNoticeRel equals to productNoticeRelId
        defaultProductNoticeShouldBeFound("productNoticeRelId.equals=" + productNoticeRelId);

        // Get all the productNoticeList where productNoticeRel equals to (productNoticeRelId + 1)
        defaultProductNoticeShouldNotBeFound("productNoticeRelId.equals=" + (productNoticeRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductNoticeShouldBeFound(String filter) throws Exception {
        restProductNoticeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productNotice.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductNoticeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductNoticeShouldNotBeFound(String filter) throws Exception {
        restProductNoticeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductNoticeMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductNotice() throws Exception {
        // Get the productNotice
        restProductNoticeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductNotice() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        int databaseSizeBeforeUpdate = productNoticeRepository.findAll().size();

        // Update the productNotice
        ProductNotice updatedProductNotice = productNoticeRepository.findById(productNotice.getId()).get();
        // Disconnect from session so that the updates on updatedProductNotice are not directly saved in db
        em.detach(updatedProductNotice);
        updatedProductNotice
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductNoticeDTO productNoticeDTO = productNoticeMapper.toDto(updatedProductNotice);

        restProductNoticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productNoticeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductNotice in the database
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeUpdate);
        ProductNotice testProductNotice = productNoticeList.get(productNoticeList.size() - 1);
        assertThat(testProductNotice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductNotice.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductNotice.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductNotice.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductNotice.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductNotice.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductNotice.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductNotice.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductNotice() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRepository.findAll().size();
        productNotice.setId(count.incrementAndGet());

        // Create the ProductNotice
        ProductNoticeDTO productNoticeDTO = productNoticeMapper.toDto(productNotice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductNoticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productNoticeDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNotice in the database
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductNotice() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRepository.findAll().size();
        productNotice.setId(count.incrementAndGet());

        // Create the ProductNotice
        ProductNoticeDTO productNoticeDTO = productNoticeMapper.toDto(productNotice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNotice in the database
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductNotice() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRepository.findAll().size();
        productNotice.setId(count.incrementAndGet());

        // Create the ProductNotice
        ProductNoticeDTO productNoticeDTO = productNoticeMapper.toDto(productNotice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productNoticeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductNotice in the database
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductNoticeWithPatch() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        int databaseSizeBeforeUpdate = productNoticeRepository.findAll().size();

        // Update the productNotice using partial update
        ProductNotice partialUpdatedProductNotice = new ProductNotice();
        partialUpdatedProductNotice.setId(productNotice.getId());

        partialUpdatedProductNotice
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductNotice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductNotice))
            )
            .andExpect(status().isOk());

        // Validate the ProductNotice in the database
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeUpdate);
        ProductNotice testProductNotice = productNoticeList.get(productNoticeList.size() - 1);
        assertThat(testProductNotice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductNotice.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductNotice.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProductNotice.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductNotice.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductNotice.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductNotice.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductNotice.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductNoticeWithPatch() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        int databaseSizeBeforeUpdate = productNoticeRepository.findAll().size();

        // Update the productNotice using partial update
        ProductNotice partialUpdatedProductNotice = new ProductNotice();
        partialUpdatedProductNotice.setId(productNotice.getId());

        partialUpdatedProductNotice
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .content(UPDATED_CONTENT)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductNotice.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductNotice))
            )
            .andExpect(status().isOk());

        // Validate the ProductNotice in the database
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeUpdate);
        ProductNotice testProductNotice = productNoticeList.get(productNoticeList.size() - 1);
        assertThat(testProductNotice.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductNotice.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductNotice.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductNotice.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductNotice.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductNotice.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductNotice.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductNotice.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductNotice() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRepository.findAll().size();
        productNotice.setId(count.incrementAndGet());

        // Create the ProductNotice
        ProductNoticeDTO productNoticeDTO = productNoticeMapper.toDto(productNotice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productNoticeDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNotice in the database
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductNotice() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRepository.findAll().size();
        productNotice.setId(count.incrementAndGet());

        // Create the ProductNotice
        ProductNoticeDTO productNoticeDTO = productNoticeMapper.toDto(productNotice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNotice in the database
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductNotice() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRepository.findAll().size();
        productNotice.setId(count.incrementAndGet());

        // Create the ProductNotice
        ProductNoticeDTO productNoticeDTO = productNoticeMapper.toDto(productNotice);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductNotice in the database
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductNotice() throws Exception {
        // Initialize the database
        productNoticeRepository.saveAndFlush(productNotice);

        int databaseSizeBeforeDelete = productNoticeRepository.findAll().size();

        // Delete the productNotice
        restProductNoticeMockMvc
            .perform(delete(ENTITY_API_URL_ID, productNotice.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductNotice> productNoticeList = productNoticeRepository.findAll();
        assertThat(productNoticeList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
