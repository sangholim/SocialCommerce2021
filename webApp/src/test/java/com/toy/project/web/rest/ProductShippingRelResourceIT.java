package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.domain.ProductShipping;
import com.toy.project.domain.ProductShippingRel;
import com.toy.project.repository.ProductShippingRelRepository;
import com.toy.project.service.criteria.ProductShippingRelCriteria;
import com.toy.project.service.dto.ProductShippingRelDTO;
import com.toy.project.service.mapper.ProductShippingRelMapper;
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
 * Integration tests for the {@link ProductShippingRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductShippingRelResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-shipping-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductShippingRelRepository productShippingRelRepository;

    @Autowired
    private ProductShippingRelMapper productShippingRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductShippingRelMockMvc;

    private ProductShippingRel productShippingRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductShippingRel createEntity(EntityManager em) {
        ProductShippingRel productShippingRel = new ProductShippingRel()
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productShippingRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductShippingRel createUpdatedEntity(EntityManager em) {
        ProductShippingRel productShippingRel = new ProductShippingRel()
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productShippingRel;
    }

    @BeforeEach
    public void initTest() {
        productShippingRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductShippingRel() throws Exception {
        int databaseSizeBeforeCreate = productShippingRelRepository.findAll().size();
        // Create the ProductShippingRel
        ProductShippingRelDTO productShippingRelDTO = productShippingRelMapper.toDto(productShippingRel);
        restProductShippingRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductShippingRel in the database
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductShippingRel testProductShippingRel = productShippingRelList.get(productShippingRelList.size() - 1);
        assertThat(testProductShippingRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductShippingRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductShippingRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductShippingRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductShippingRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductShippingRelWithExistingId() throws Exception {
        // Create the ProductShippingRel with an existing ID
        productShippingRel.setId(1L);
        ProductShippingRelDTO productShippingRelDTO = productShippingRelMapper.toDto(productShippingRel);

        int databaseSizeBeforeCreate = productShippingRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductShippingRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShippingRel in the database
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductShippingRels() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList
        restProductShippingRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productShippingRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductShippingRel() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get the productShippingRel
        restProductShippingRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productShippingRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productShippingRel.getId().intValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductShippingRelsByIdFiltering() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        Long id = productShippingRel.getId();

        defaultProductShippingRelShouldBeFound("id.equals=" + id);
        defaultProductShippingRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductShippingRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductShippingRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductShippingRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductShippingRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductShippingRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productShippingRelList where activated equals to UPDATED_ACTIVATED
        defaultProductShippingRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductShippingRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productShippingRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductShippingRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductShippingRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productShippingRelList where activated equals to UPDATED_ACTIVATED
        defaultProductShippingRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where activated is not null
        defaultProductShippingRelShouldBeFound("activated.specified=true");

        // Get all the productShippingRelList where activated is null
        defaultProductShippingRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductShippingRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productShippingRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductShippingRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductShippingRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productShippingRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductShippingRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductShippingRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productShippingRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductShippingRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where createdBy is not null
        defaultProductShippingRelShouldBeFound("createdBy.specified=true");

        // Get all the productShippingRelList where createdBy is null
        defaultProductShippingRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductShippingRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productShippingRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductShippingRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductShippingRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productShippingRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductShippingRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductShippingRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productShippingRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductShippingRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductShippingRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productShippingRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductShippingRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductShippingRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productShippingRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductShippingRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where createdDate is not null
        defaultProductShippingRelShouldBeFound("createdDate.specified=true");

        // Get all the productShippingRelList where createdDate is null
        defaultProductShippingRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductShippingRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productShippingRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductShippingRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductShippingRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productShippingRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductShippingRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductShippingRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productShippingRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductShippingRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where lastModifiedBy is not null
        defaultProductShippingRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productShippingRelList where lastModifiedBy is null
        defaultProductShippingRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductShippingRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productShippingRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductShippingRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductShippingRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productShippingRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductShippingRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductShippingRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productShippingRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductShippingRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductShippingRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productShippingRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductShippingRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductShippingRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productShippingRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductShippingRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        // Get all the productShippingRelList where lastModifiedDate is not null
        defaultProductShippingRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productShippingRelList where lastModifiedDate is null
        defaultProductShippingRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productShippingRel.setProduct(product);
        productShippingRelRepository.saveAndFlush(productShippingRel);
        Long productId = product.getId();

        // Get all the productShippingRelList where product equals to productId
        defaultProductShippingRelShouldBeFound("productId.equals=" + productId);

        // Get all the productShippingRelList where product equals to (productId + 1)
        defaultProductShippingRelShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductShippingRelsByProductShippingIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);
        ProductShipping productShipping = ProductShippingResourceIT.createEntity(em);
        em.persist(productShipping);
        em.flush();
        productShippingRel.setProductShipping(productShipping);
        productShippingRelRepository.saveAndFlush(productShippingRel);
        Long productShippingId = productShipping.getId();

        // Get all the productShippingRelList where productShipping equals to productShippingId
        defaultProductShippingRelShouldBeFound("productShippingId.equals=" + productShippingId);

        // Get all the productShippingRelList where productShipping equals to (productShippingId + 1)
        defaultProductShippingRelShouldNotBeFound("productShippingId.equals=" + (productShippingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductShippingRelShouldBeFound(String filter) throws Exception {
        restProductShippingRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productShippingRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductShippingRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductShippingRelShouldNotBeFound(String filter) throws Exception {
        restProductShippingRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductShippingRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductShippingRel() throws Exception {
        // Get the productShippingRel
        restProductShippingRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductShippingRel() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        int databaseSizeBeforeUpdate = productShippingRelRepository.findAll().size();

        // Update the productShippingRel
        ProductShippingRel updatedProductShippingRel = productShippingRelRepository.findById(productShippingRel.getId()).get();
        // Disconnect from session so that the updates on updatedProductShippingRel are not directly saved in db
        em.detach(updatedProductShippingRel);
        updatedProductShippingRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductShippingRelDTO productShippingRelDTO = productShippingRelMapper.toDto(updatedProductShippingRel);

        restProductShippingRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productShippingRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductShippingRel in the database
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeUpdate);
        ProductShippingRel testProductShippingRel = productShippingRelList.get(productShippingRelList.size() - 1);
        assertThat(testProductShippingRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductShippingRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductShippingRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductShippingRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductShippingRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductShippingRel() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRelRepository.findAll().size();
        productShippingRel.setId(count.incrementAndGet());

        // Create the ProductShippingRel
        ProductShippingRelDTO productShippingRelDTO = productShippingRelMapper.toDto(productShippingRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductShippingRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productShippingRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShippingRel in the database
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductShippingRel() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRelRepository.findAll().size();
        productShippingRel.setId(count.incrementAndGet());

        // Create the ProductShippingRel
        ProductShippingRelDTO productShippingRelDTO = productShippingRelMapper.toDto(productShippingRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShippingRel in the database
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductShippingRel() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRelRepository.findAll().size();
        productShippingRel.setId(count.incrementAndGet());

        // Create the ProductShippingRel
        ProductShippingRelDTO productShippingRelDTO = productShippingRelMapper.toDto(productShippingRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingRelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductShippingRel in the database
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductShippingRelWithPatch() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        int databaseSizeBeforeUpdate = productShippingRelRepository.findAll().size();

        // Update the productShippingRel using partial update
        ProductShippingRel partialUpdatedProductShippingRel = new ProductShippingRel();
        partialUpdatedProductShippingRel.setId(productShippingRel.getId());

        partialUpdatedProductShippingRel
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductShippingRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductShippingRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductShippingRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductShippingRel in the database
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeUpdate);
        ProductShippingRel testProductShippingRel = productShippingRelList.get(productShippingRelList.size() - 1);
        assertThat(testProductShippingRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductShippingRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductShippingRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductShippingRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductShippingRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductShippingRelWithPatch() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        int databaseSizeBeforeUpdate = productShippingRelRepository.findAll().size();

        // Update the productShippingRel using partial update
        ProductShippingRel partialUpdatedProductShippingRel = new ProductShippingRel();
        partialUpdatedProductShippingRel.setId(productShippingRel.getId());

        partialUpdatedProductShippingRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductShippingRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductShippingRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductShippingRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductShippingRel in the database
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeUpdate);
        ProductShippingRel testProductShippingRel = productShippingRelList.get(productShippingRelList.size() - 1);
        assertThat(testProductShippingRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductShippingRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductShippingRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductShippingRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductShippingRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductShippingRel() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRelRepository.findAll().size();
        productShippingRel.setId(count.incrementAndGet());

        // Create the ProductShippingRel
        ProductShippingRelDTO productShippingRelDTO = productShippingRelMapper.toDto(productShippingRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductShippingRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productShippingRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productShippingRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShippingRel in the database
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductShippingRel() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRelRepository.findAll().size();
        productShippingRel.setId(count.incrementAndGet());

        // Create the ProductShippingRel
        ProductShippingRelDTO productShippingRelDTO = productShippingRelMapper.toDto(productShippingRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productShippingRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShippingRel in the database
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductShippingRel() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRelRepository.findAll().size();
        productShippingRel.setId(count.incrementAndGet());

        // Create the ProductShippingRel
        ProductShippingRelDTO productShippingRelDTO = productShippingRelMapper.toDto(productShippingRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productShippingRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductShippingRel in the database
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductShippingRel() throws Exception {
        // Initialize the database
        productShippingRelRepository.saveAndFlush(productShippingRel);

        int databaseSizeBeforeDelete = productShippingRelRepository.findAll().size();

        // Delete the productShippingRel
        restProductShippingRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productShippingRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductShippingRel> productShippingRelList = productShippingRelRepository.findAll();
        assertThat(productShippingRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
