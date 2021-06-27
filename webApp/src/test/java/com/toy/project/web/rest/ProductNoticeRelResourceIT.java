package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.domain.ProductNotice;
import com.toy.project.domain.ProductNoticeRel;
import com.toy.project.repository.ProductNoticeRelRepository;
import com.toy.project.service.criteria.ProductNoticeRelCriteria;
import com.toy.project.service.dto.ProductNoticeRelDTO;
import com.toy.project.service.mapper.ProductNoticeRelMapper;
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
 * Integration tests for the {@link ProductNoticeRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductNoticeRelResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-notice-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductNoticeRelRepository productNoticeRelRepository;

    @Autowired
    private ProductNoticeRelMapper productNoticeRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductNoticeRelMockMvc;

    private ProductNoticeRel productNoticeRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductNoticeRel createEntity(EntityManager em) {
        ProductNoticeRel productNoticeRel = new ProductNoticeRel()
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productNoticeRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductNoticeRel createUpdatedEntity(EntityManager em) {
        ProductNoticeRel productNoticeRel = new ProductNoticeRel()
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productNoticeRel;
    }

    @BeforeEach
    public void initTest() {
        productNoticeRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductNoticeRel() throws Exception {
        int databaseSizeBeforeCreate = productNoticeRelRepository.findAll().size();
        // Create the ProductNoticeRel
        ProductNoticeRelDTO productNoticeRelDTO = productNoticeRelMapper.toDto(productNoticeRel);
        restProductNoticeRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productNoticeRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductNoticeRel in the database
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductNoticeRel testProductNoticeRel = productNoticeRelList.get(productNoticeRelList.size() - 1);
        assertThat(testProductNoticeRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductNoticeRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductNoticeRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductNoticeRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductNoticeRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductNoticeRelWithExistingId() throws Exception {
        // Create the ProductNoticeRel with an existing ID
        productNoticeRel.setId(1L);
        ProductNoticeRelDTO productNoticeRelDTO = productNoticeRelMapper.toDto(productNoticeRel);

        int databaseSizeBeforeCreate = productNoticeRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductNoticeRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productNoticeRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNoticeRel in the database
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductNoticeRels() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList
        restProductNoticeRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productNoticeRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductNoticeRel() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get the productNoticeRel
        restProductNoticeRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productNoticeRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productNoticeRel.getId().intValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductNoticeRelsByIdFiltering() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        Long id = productNoticeRel.getId();

        defaultProductNoticeRelShouldBeFound("id.equals=" + id);
        defaultProductNoticeRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductNoticeRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductNoticeRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductNoticeRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductNoticeRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductNoticeRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productNoticeRelList where activated equals to UPDATED_ACTIVATED
        defaultProductNoticeRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductNoticeRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productNoticeRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductNoticeRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductNoticeRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productNoticeRelList where activated equals to UPDATED_ACTIVATED
        defaultProductNoticeRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where activated is not null
        defaultProductNoticeRelShouldBeFound("activated.specified=true");

        // Get all the productNoticeRelList where activated is null
        defaultProductNoticeRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductNoticeRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productNoticeRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductNoticeRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductNoticeRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productNoticeRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductNoticeRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductNoticeRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productNoticeRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductNoticeRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where createdBy is not null
        defaultProductNoticeRelShouldBeFound("createdBy.specified=true");

        // Get all the productNoticeRelList where createdBy is null
        defaultProductNoticeRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductNoticeRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productNoticeRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductNoticeRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductNoticeRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productNoticeRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductNoticeRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductNoticeRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productNoticeRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductNoticeRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductNoticeRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productNoticeRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductNoticeRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductNoticeRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productNoticeRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductNoticeRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where createdDate is not null
        defaultProductNoticeRelShouldBeFound("createdDate.specified=true");

        // Get all the productNoticeRelList where createdDate is null
        defaultProductNoticeRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductNoticeRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productNoticeRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductNoticeRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productNoticeRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productNoticeRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where lastModifiedBy is not null
        defaultProductNoticeRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productNoticeRelList where lastModifiedBy is null
        defaultProductNoticeRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductNoticeRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productNoticeRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductNoticeRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productNoticeRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductNoticeRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductNoticeRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productNoticeRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductNoticeRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductNoticeRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productNoticeRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductNoticeRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductNoticeRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productNoticeRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductNoticeRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        // Get all the productNoticeRelList where lastModifiedDate is not null
        defaultProductNoticeRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productNoticeRelList where lastModifiedDate is null
        defaultProductNoticeRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productNoticeRel.setProduct(product);
        productNoticeRelRepository.saveAndFlush(productNoticeRel);
        Long productId = product.getId();

        // Get all the productNoticeRelList where product equals to productId
        defaultProductNoticeRelShouldBeFound("productId.equals=" + productId);

        // Get all the productNoticeRelList where product equals to (productId + 1)
        defaultProductNoticeRelShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductNoticeRelsByProductNoticeIsEqualToSomething() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);
        ProductNotice productNotice = ProductNoticeResourceIT.createEntity(em);
        em.persist(productNotice);
        em.flush();
        productNoticeRel.setProductNotice(productNotice);
        productNoticeRelRepository.saveAndFlush(productNoticeRel);
        Long productNoticeId = productNotice.getId();

        // Get all the productNoticeRelList where productNotice equals to productNoticeId
        defaultProductNoticeRelShouldBeFound("productNoticeId.equals=" + productNoticeId);

        // Get all the productNoticeRelList where productNotice equals to (productNoticeId + 1)
        defaultProductNoticeRelShouldNotBeFound("productNoticeId.equals=" + (productNoticeId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductNoticeRelShouldBeFound(String filter) throws Exception {
        restProductNoticeRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productNoticeRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductNoticeRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductNoticeRelShouldNotBeFound(String filter) throws Exception {
        restProductNoticeRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductNoticeRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductNoticeRel() throws Exception {
        // Get the productNoticeRel
        restProductNoticeRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductNoticeRel() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        int databaseSizeBeforeUpdate = productNoticeRelRepository.findAll().size();

        // Update the productNoticeRel
        ProductNoticeRel updatedProductNoticeRel = productNoticeRelRepository.findById(productNoticeRel.getId()).get();
        // Disconnect from session so that the updates on updatedProductNoticeRel are not directly saved in db
        em.detach(updatedProductNoticeRel);
        updatedProductNoticeRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductNoticeRelDTO productNoticeRelDTO = productNoticeRelMapper.toDto(updatedProductNoticeRel);

        restProductNoticeRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productNoticeRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductNoticeRel in the database
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeUpdate);
        ProductNoticeRel testProductNoticeRel = productNoticeRelList.get(productNoticeRelList.size() - 1);
        assertThat(testProductNoticeRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductNoticeRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductNoticeRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductNoticeRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductNoticeRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductNoticeRel() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRelRepository.findAll().size();
        productNoticeRel.setId(count.incrementAndGet());

        // Create the ProductNoticeRel
        ProductNoticeRelDTO productNoticeRelDTO = productNoticeRelMapper.toDto(productNoticeRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductNoticeRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productNoticeRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNoticeRel in the database
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductNoticeRel() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRelRepository.findAll().size();
        productNoticeRel.setId(count.incrementAndGet());

        // Create the ProductNoticeRel
        ProductNoticeRelDTO productNoticeRelDTO = productNoticeRelMapper.toDto(productNoticeRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNoticeRel in the database
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductNoticeRel() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRelRepository.findAll().size();
        productNoticeRel.setId(count.incrementAndGet());

        // Create the ProductNoticeRel
        ProductNoticeRelDTO productNoticeRelDTO = productNoticeRelMapper.toDto(productNoticeRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeRelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productNoticeRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductNoticeRel in the database
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductNoticeRelWithPatch() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        int databaseSizeBeforeUpdate = productNoticeRelRepository.findAll().size();

        // Update the productNoticeRel using partial update
        ProductNoticeRel partialUpdatedProductNoticeRel = new ProductNoticeRel();
        partialUpdatedProductNoticeRel.setId(productNoticeRel.getId());

        partialUpdatedProductNoticeRel
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductNoticeRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductNoticeRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductNoticeRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductNoticeRel in the database
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeUpdate);
        ProductNoticeRel testProductNoticeRel = productNoticeRelList.get(productNoticeRelList.size() - 1);
        assertThat(testProductNoticeRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductNoticeRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductNoticeRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductNoticeRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductNoticeRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductNoticeRelWithPatch() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        int databaseSizeBeforeUpdate = productNoticeRelRepository.findAll().size();

        // Update the productNoticeRel using partial update
        ProductNoticeRel partialUpdatedProductNoticeRel = new ProductNoticeRel();
        partialUpdatedProductNoticeRel.setId(productNoticeRel.getId());

        partialUpdatedProductNoticeRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductNoticeRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductNoticeRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductNoticeRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductNoticeRel in the database
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeUpdate);
        ProductNoticeRel testProductNoticeRel = productNoticeRelList.get(productNoticeRelList.size() - 1);
        assertThat(testProductNoticeRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductNoticeRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductNoticeRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductNoticeRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductNoticeRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductNoticeRel() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRelRepository.findAll().size();
        productNoticeRel.setId(count.incrementAndGet());

        // Create the ProductNoticeRel
        ProductNoticeRelDTO productNoticeRelDTO = productNoticeRelMapper.toDto(productNoticeRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductNoticeRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productNoticeRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNoticeRel in the database
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductNoticeRel() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRelRepository.findAll().size();
        productNoticeRel.setId(count.incrementAndGet());

        // Create the ProductNoticeRel
        ProductNoticeRelDTO productNoticeRelDTO = productNoticeRelMapper.toDto(productNoticeRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNoticeRel in the database
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductNoticeRel() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeRelRepository.findAll().size();
        productNoticeRel.setId(count.incrementAndGet());

        // Create the ProductNoticeRel
        ProductNoticeRelDTO productNoticeRelDTO = productNoticeRelMapper.toDto(productNoticeRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductNoticeRel in the database
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductNoticeRel() throws Exception {
        // Initialize the database
        productNoticeRelRepository.saveAndFlush(productNoticeRel);

        int databaseSizeBeforeDelete = productNoticeRelRepository.findAll().size();

        // Delete the productNoticeRel
        restProductNoticeRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productNoticeRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductNoticeRel> productNoticeRelList = productNoticeRelRepository.findAll();
        assertThat(productNoticeRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
