package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.domain.ProductMapping;
import com.toy.project.domain.ProductMappingRel;
import com.toy.project.repository.ProductMappingRelRepository;
import com.toy.project.service.criteria.ProductMappingRelCriteria;
import com.toy.project.service.dto.ProductMappingRelDTO;
import com.toy.project.service.mapper.ProductMappingRelMapper;
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
 * Integration tests for the {@link ProductMappingRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductMappingRelResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-mapping-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductMappingRelRepository productMappingRelRepository;

    @Autowired
    private ProductMappingRelMapper productMappingRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMappingRelMockMvc;

    private ProductMappingRel productMappingRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMappingRel createEntity(EntityManager em) {
        ProductMappingRel productMappingRel = new ProductMappingRel()
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productMappingRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMappingRel createUpdatedEntity(EntityManager em) {
        ProductMappingRel productMappingRel = new ProductMappingRel()
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productMappingRel;
    }

    @BeforeEach
    public void initTest() {
        productMappingRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductMappingRel() throws Exception {
        int databaseSizeBeforeCreate = productMappingRelRepository.findAll().size();
        // Create the ProductMappingRel
        ProductMappingRelDTO productMappingRelDTO = productMappingRelMapper.toDto(productMappingRel);
        restProductMappingRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductMappingRel in the database
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductMappingRel testProductMappingRel = productMappingRelList.get(productMappingRelList.size() - 1);
        assertThat(testProductMappingRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductMappingRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductMappingRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductMappingRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductMappingRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductMappingRelWithExistingId() throws Exception {
        // Create the ProductMappingRel with an existing ID
        productMappingRel.setId(1L);
        ProductMappingRelDTO productMappingRelDTO = productMappingRelMapper.toDto(productMappingRel);

        int databaseSizeBeforeCreate = productMappingRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMappingRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMappingRel in the database
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductMappingRels() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList
        restProductMappingRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productMappingRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductMappingRel() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get the productMappingRel
        restProductMappingRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productMappingRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productMappingRel.getId().intValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductMappingRelsByIdFiltering() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        Long id = productMappingRel.getId();

        defaultProductMappingRelShouldBeFound("id.equals=" + id);
        defaultProductMappingRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductMappingRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductMappingRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductMappingRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductMappingRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductMappingRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productMappingRelList where activated equals to UPDATED_ACTIVATED
        defaultProductMappingRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductMappingRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productMappingRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductMappingRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductMappingRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productMappingRelList where activated equals to UPDATED_ACTIVATED
        defaultProductMappingRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where activated is not null
        defaultProductMappingRelShouldBeFound("activated.specified=true");

        // Get all the productMappingRelList where activated is null
        defaultProductMappingRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductMappingRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productMappingRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductMappingRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductMappingRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productMappingRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductMappingRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductMappingRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productMappingRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductMappingRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where createdBy is not null
        defaultProductMappingRelShouldBeFound("createdBy.specified=true");

        // Get all the productMappingRelList where createdBy is null
        defaultProductMappingRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductMappingRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productMappingRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductMappingRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductMappingRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productMappingRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductMappingRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductMappingRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productMappingRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductMappingRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductMappingRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productMappingRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductMappingRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductMappingRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productMappingRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductMappingRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where createdDate is not null
        defaultProductMappingRelShouldBeFound("createdDate.specified=true");

        // Get all the productMappingRelList where createdDate is null
        defaultProductMappingRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductMappingRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productMappingRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductMappingRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductMappingRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productMappingRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductMappingRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductMappingRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productMappingRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductMappingRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where lastModifiedBy is not null
        defaultProductMappingRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productMappingRelList where lastModifiedBy is null
        defaultProductMappingRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductMappingRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productMappingRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductMappingRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductMappingRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productMappingRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductMappingRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductMappingRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productMappingRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductMappingRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductMappingRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productMappingRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductMappingRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductMappingRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productMappingRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductMappingRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        // Get all the productMappingRelList where lastModifiedDate is not null
        defaultProductMappingRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productMappingRelList where lastModifiedDate is null
        defaultProductMappingRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productMappingRel.setProduct(product);
        productMappingRelRepository.saveAndFlush(productMappingRel);
        Long productId = product.getId();

        // Get all the productMappingRelList where product equals to productId
        defaultProductMappingRelShouldBeFound("productId.equals=" + productId);

        // Get all the productMappingRelList where product equals to (productId + 1)
        defaultProductMappingRelShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductMappingRelsByProductMappingIsEqualToSomething() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);
        ProductMapping productMapping = ProductMappingResourceIT.createEntity(em);
        em.persist(productMapping);
        em.flush();
        productMappingRel.setProductMapping(productMapping);
        productMappingRelRepository.saveAndFlush(productMappingRel);
        Long productMappingId = productMapping.getId();

        // Get all the productMappingRelList where productMapping equals to productMappingId
        defaultProductMappingRelShouldBeFound("productMappingId.equals=" + productMappingId);

        // Get all the productMappingRelList where productMapping equals to (productMappingId + 1)
        defaultProductMappingRelShouldNotBeFound("productMappingId.equals=" + (productMappingId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductMappingRelShouldBeFound(String filter) throws Exception {
        restProductMappingRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productMappingRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductMappingRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductMappingRelShouldNotBeFound(String filter) throws Exception {
        restProductMappingRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductMappingRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductMappingRel() throws Exception {
        // Get the productMappingRel
        restProductMappingRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductMappingRel() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        int databaseSizeBeforeUpdate = productMappingRelRepository.findAll().size();

        // Update the productMappingRel
        ProductMappingRel updatedProductMappingRel = productMappingRelRepository.findById(productMappingRel.getId()).get();
        // Disconnect from session so that the updates on updatedProductMappingRel are not directly saved in db
        em.detach(updatedProductMappingRel);
        updatedProductMappingRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductMappingRelDTO productMappingRelDTO = productMappingRelMapper.toDto(updatedProductMappingRel);

        restProductMappingRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productMappingRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductMappingRel in the database
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeUpdate);
        ProductMappingRel testProductMappingRel = productMappingRelList.get(productMappingRelList.size() - 1);
        assertThat(testProductMappingRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductMappingRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductMappingRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductMappingRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductMappingRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductMappingRel() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRelRepository.findAll().size();
        productMappingRel.setId(count.incrementAndGet());

        // Create the ProductMappingRel
        ProductMappingRelDTO productMappingRelDTO = productMappingRelMapper.toDto(productMappingRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMappingRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productMappingRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMappingRel in the database
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductMappingRel() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRelRepository.findAll().size();
        productMappingRel.setId(count.incrementAndGet());

        // Create the ProductMappingRel
        ProductMappingRelDTO productMappingRelDTO = productMappingRelMapper.toDto(productMappingRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMappingRel in the database
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductMappingRel() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRelRepository.findAll().size();
        productMappingRel.setId(count.incrementAndGet());

        // Create the ProductMappingRel
        ProductMappingRelDTO productMappingRelDTO = productMappingRelMapper.toDto(productMappingRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingRelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productMappingRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductMappingRel in the database
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductMappingRelWithPatch() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        int databaseSizeBeforeUpdate = productMappingRelRepository.findAll().size();

        // Update the productMappingRel using partial update
        ProductMappingRel partialUpdatedProductMappingRel = new ProductMappingRel();
        partialUpdatedProductMappingRel.setId(productMappingRel.getId());

        partialUpdatedProductMappingRel.activated(UPDATED_ACTIVATED);

        restProductMappingRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductMappingRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductMappingRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductMappingRel in the database
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeUpdate);
        ProductMappingRel testProductMappingRel = productMappingRelList.get(productMappingRelList.size() - 1);
        assertThat(testProductMappingRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductMappingRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductMappingRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductMappingRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductMappingRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductMappingRelWithPatch() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        int databaseSizeBeforeUpdate = productMappingRelRepository.findAll().size();

        // Update the productMappingRel using partial update
        ProductMappingRel partialUpdatedProductMappingRel = new ProductMappingRel();
        partialUpdatedProductMappingRel.setId(productMappingRel.getId());

        partialUpdatedProductMappingRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductMappingRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductMappingRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductMappingRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductMappingRel in the database
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeUpdate);
        ProductMappingRel testProductMappingRel = productMappingRelList.get(productMappingRelList.size() - 1);
        assertThat(testProductMappingRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductMappingRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductMappingRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductMappingRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductMappingRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductMappingRel() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRelRepository.findAll().size();
        productMappingRel.setId(count.incrementAndGet());

        // Create the ProductMappingRel
        ProductMappingRelDTO productMappingRelDTO = productMappingRelMapper.toDto(productMappingRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMappingRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productMappingRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMappingRel in the database
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductMappingRel() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRelRepository.findAll().size();
        productMappingRel.setId(count.incrementAndGet());

        // Create the ProductMappingRel
        ProductMappingRelDTO productMappingRelDTO = productMappingRelMapper.toDto(productMappingRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMappingRel in the database
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductMappingRel() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRelRepository.findAll().size();
        productMappingRel.setId(count.incrementAndGet());

        // Create the ProductMappingRel
        ProductMappingRelDTO productMappingRelDTO = productMappingRelMapper.toDto(productMappingRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductMappingRel in the database
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductMappingRel() throws Exception {
        // Initialize the database
        productMappingRelRepository.saveAndFlush(productMappingRel);

        int databaseSizeBeforeDelete = productMappingRelRepository.findAll().size();

        // Delete the productMappingRel
        restProductMappingRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productMappingRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductMappingRel> productMappingRelList = productMappingRelRepository.findAll();
        assertThat(productMappingRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
