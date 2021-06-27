package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.domain.ProductCategory;
import com.toy.project.domain.ProductCategoryRel;
import com.toy.project.repository.ProductCategoryRelRepository;
import com.toy.project.service.criteria.ProductCategoryRelCriteria;
import com.toy.project.service.dto.ProductCategoryRelDTO;
import com.toy.project.service.mapper.ProductCategoryRelMapper;
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
 * Integration tests for the {@link ProductCategoryRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductCategoryRelResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-category-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductCategoryRelRepository productCategoryRelRepository;

    @Autowired
    private ProductCategoryRelMapper productCategoryRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductCategoryRelMockMvc;

    private ProductCategoryRel productCategoryRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryRel createEntity(EntityManager em) {
        ProductCategoryRel productCategoryRel = new ProductCategoryRel()
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productCategoryRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryRel createUpdatedEntity(EntityManager em) {
        ProductCategoryRel productCategoryRel = new ProductCategoryRel()
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productCategoryRel;
    }

    @BeforeEach
    public void initTest() {
        productCategoryRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductCategoryRel() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRelRepository.findAll().size();
        // Create the ProductCategoryRel
        ProductCategoryRelDTO productCategoryRelDTO = productCategoryRelMapper.toDto(productCategoryRel);
        restProductCategoryRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductCategoryRel in the database
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategoryRel testProductCategoryRel = productCategoryRelList.get(productCategoryRelList.size() - 1);
        assertThat(testProductCategoryRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductCategoryRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductCategoryRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductCategoryRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductCategoryRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductCategoryRelWithExistingId() throws Exception {
        // Create the ProductCategoryRel with an existing ID
        productCategoryRel.setId(1L);
        ProductCategoryRelDTO productCategoryRelDTO = productCategoryRelMapper.toDto(productCategoryRel);

        int databaseSizeBeforeCreate = productCategoryRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryRel in the database
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductCategoryRels() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList
        restProductCategoryRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategoryRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductCategoryRel() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get the productCategoryRel
        restProductCategoryRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productCategoryRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productCategoryRel.getId().intValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductCategoryRelsByIdFiltering() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        Long id = productCategoryRel.getId();

        defaultProductCategoryRelShouldBeFound("id.equals=" + id);
        defaultProductCategoryRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductCategoryRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductCategoryRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductCategoryRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductCategoryRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductCategoryRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productCategoryRelList where activated equals to UPDATED_ACTIVATED
        defaultProductCategoryRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductCategoryRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productCategoryRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductCategoryRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductCategoryRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productCategoryRelList where activated equals to UPDATED_ACTIVATED
        defaultProductCategoryRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where activated is not null
        defaultProductCategoryRelShouldBeFound("activated.specified=true");

        // Get all the productCategoryRelList where activated is null
        defaultProductCategoryRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductCategoryRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productCategoryRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductCategoryRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductCategoryRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productCategoryRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductCategoryRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductCategoryRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productCategoryRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductCategoryRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where createdBy is not null
        defaultProductCategoryRelShouldBeFound("createdBy.specified=true");

        // Get all the productCategoryRelList where createdBy is null
        defaultProductCategoryRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductCategoryRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productCategoryRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductCategoryRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductCategoryRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productCategoryRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductCategoryRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductCategoryRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productCategoryRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductCategoryRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductCategoryRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productCategoryRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductCategoryRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductCategoryRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productCategoryRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductCategoryRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where createdDate is not null
        defaultProductCategoryRelShouldBeFound("createdDate.specified=true");

        // Get all the productCategoryRelList where createdDate is null
        defaultProductCategoryRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductCategoryRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productCategoryRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductCategoryRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productCategoryRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productCategoryRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where lastModifiedBy is not null
        defaultProductCategoryRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productCategoryRelList where lastModifiedBy is null
        defaultProductCategoryRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductCategoryRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productCategoryRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductCategoryRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productCategoryRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductCategoryRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productCategoryRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductCategoryRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductCategoryRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productCategoryRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductCategoryRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductCategoryRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productCategoryRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductCategoryRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        // Get all the productCategoryRelList where lastModifiedDate is not null
        defaultProductCategoryRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productCategoryRelList where lastModifiedDate is null
        defaultProductCategoryRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productCategoryRel.setProduct(product);
        productCategoryRelRepository.saveAndFlush(productCategoryRel);
        Long productId = product.getId();

        // Get all the productCategoryRelList where product equals to productId
        defaultProductCategoryRelShouldBeFound("productId.equals=" + productId);

        // Get all the productCategoryRelList where product equals to (productId + 1)
        defaultProductCategoryRelShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductCategoryRelsByProductCategoryIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);
        ProductCategory productCategory = ProductCategoryResourceIT.createEntity(em);
        em.persist(productCategory);
        em.flush();
        productCategoryRel.setProductCategory(productCategory);
        productCategoryRelRepository.saveAndFlush(productCategoryRel);
        Long productCategoryId = productCategory.getId();

        // Get all the productCategoryRelList where productCategory equals to productCategoryId
        defaultProductCategoryRelShouldBeFound("productCategoryId.equals=" + productCategoryId);

        // Get all the productCategoryRelList where productCategory equals to (productCategoryId + 1)
        defaultProductCategoryRelShouldNotBeFound("productCategoryId.equals=" + (productCategoryId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductCategoryRelShouldBeFound(String filter) throws Exception {
        restProductCategoryRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategoryRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductCategoryRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductCategoryRelShouldNotBeFound(String filter) throws Exception {
        restProductCategoryRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductCategoryRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductCategoryRel() throws Exception {
        // Get the productCategoryRel
        restProductCategoryRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductCategoryRel() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        int databaseSizeBeforeUpdate = productCategoryRelRepository.findAll().size();

        // Update the productCategoryRel
        ProductCategoryRel updatedProductCategoryRel = productCategoryRelRepository.findById(productCategoryRel.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategoryRel are not directly saved in db
        em.detach(updatedProductCategoryRel);
        updatedProductCategoryRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductCategoryRelDTO productCategoryRelDTO = productCategoryRelMapper.toDto(updatedProductCategoryRel);

        restProductCategoryRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productCategoryRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategoryRel in the database
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeUpdate);
        ProductCategoryRel testProductCategoryRel = productCategoryRelList.get(productCategoryRelList.size() - 1);
        assertThat(testProductCategoryRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductCategoryRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategoryRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductCategoryRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductCategoryRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductCategoryRel() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRelRepository.findAll().size();
        productCategoryRel.setId(count.incrementAndGet());

        // Create the ProductCategoryRel
        ProductCategoryRelDTO productCategoryRelDTO = productCategoryRelMapper.toDto(productCategoryRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productCategoryRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryRel in the database
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductCategoryRel() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRelRepository.findAll().size();
        productCategoryRel.setId(count.incrementAndGet());

        // Create the ProductCategoryRel
        ProductCategoryRelDTO productCategoryRelDTO = productCategoryRelMapper.toDto(productCategoryRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryRel in the database
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductCategoryRel() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRelRepository.findAll().size();
        productCategoryRel.setId(count.incrementAndGet());

        // Create the ProductCategoryRel
        ProductCategoryRelDTO productCategoryRelDTO = productCategoryRelMapper.toDto(productCategoryRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryRelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductCategoryRel in the database
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductCategoryRelWithPatch() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        int databaseSizeBeforeUpdate = productCategoryRelRepository.findAll().size();

        // Update the productCategoryRel using partial update
        ProductCategoryRel partialUpdatedProductCategoryRel = new ProductCategoryRel();
        partialUpdatedProductCategoryRel.setId(productCategoryRel.getId());

        partialUpdatedProductCategoryRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductCategoryRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductCategoryRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductCategoryRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategoryRel in the database
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeUpdate);
        ProductCategoryRel testProductCategoryRel = productCategoryRelList.get(productCategoryRelList.size() - 1);
        assertThat(testProductCategoryRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductCategoryRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategoryRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductCategoryRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductCategoryRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductCategoryRelWithPatch() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        int databaseSizeBeforeUpdate = productCategoryRelRepository.findAll().size();

        // Update the productCategoryRel using partial update
        ProductCategoryRel partialUpdatedProductCategoryRel = new ProductCategoryRel();
        partialUpdatedProductCategoryRel.setId(productCategoryRel.getId());

        partialUpdatedProductCategoryRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductCategoryRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductCategoryRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductCategoryRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategoryRel in the database
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeUpdate);
        ProductCategoryRel testProductCategoryRel = productCategoryRelList.get(productCategoryRelList.size() - 1);
        assertThat(testProductCategoryRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductCategoryRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategoryRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductCategoryRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductCategoryRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductCategoryRel() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRelRepository.findAll().size();
        productCategoryRel.setId(count.incrementAndGet());

        // Create the ProductCategoryRel
        ProductCategoryRelDTO productCategoryRelDTO = productCategoryRelMapper.toDto(productCategoryRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productCategoryRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryRel in the database
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductCategoryRel() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRelRepository.findAll().size();
        productCategoryRel.setId(count.incrementAndGet());

        // Create the ProductCategoryRel
        ProductCategoryRelDTO productCategoryRelDTO = productCategoryRelMapper.toDto(productCategoryRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryRel in the database
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductCategoryRel() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRelRepository.findAll().size();
        productCategoryRel.setId(count.incrementAndGet());

        // Create the ProductCategoryRel
        ProductCategoryRelDTO productCategoryRelDTO = productCategoryRelMapper.toDto(productCategoryRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductCategoryRel in the database
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductCategoryRel() throws Exception {
        // Initialize the database
        productCategoryRelRepository.saveAndFlush(productCategoryRel);

        int databaseSizeBeforeDelete = productCategoryRelRepository.findAll().size();

        // Delete the productCategoryRel
        restProductCategoryRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productCategoryRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCategoryRel> productCategoryRelList = productCategoryRelRepository.findAll();
        assertThat(productCategoryRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
