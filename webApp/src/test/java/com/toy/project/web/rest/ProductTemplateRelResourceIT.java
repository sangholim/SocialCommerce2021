package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.domain.ProductTemplate;
import com.toy.project.domain.ProductTemplateRel;
import com.toy.project.repository.ProductTemplateRelRepository;
import com.toy.project.service.criteria.ProductTemplateRelCriteria;
import com.toy.project.service.dto.ProductTemplateRelDTO;
import com.toy.project.service.mapper.ProductTemplateRelMapper;
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
 * Integration tests for the {@link ProductTemplateRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductTemplateRelResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-template-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductTemplateRelRepository productTemplateRelRepository;

    @Autowired
    private ProductTemplateRelMapper productTemplateRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductTemplateRelMockMvc;

    private ProductTemplateRel productTemplateRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTemplateRel createEntity(EntityManager em) {
        ProductTemplateRel productTemplateRel = new ProductTemplateRel()
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productTemplateRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTemplateRel createUpdatedEntity(EntityManager em) {
        ProductTemplateRel productTemplateRel = new ProductTemplateRel()
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productTemplateRel;
    }

    @BeforeEach
    public void initTest() {
        productTemplateRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductTemplateRel() throws Exception {
        int databaseSizeBeforeCreate = productTemplateRelRepository.findAll().size();
        // Create the ProductTemplateRel
        ProductTemplateRelDTO productTemplateRelDTO = productTemplateRelMapper.toDto(productTemplateRel);
        restProductTemplateRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductTemplateRel in the database
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductTemplateRel testProductTemplateRel = productTemplateRelList.get(productTemplateRelList.size() - 1);
        assertThat(testProductTemplateRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductTemplateRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductTemplateRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductTemplateRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductTemplateRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductTemplateRelWithExistingId() throws Exception {
        // Create the ProductTemplateRel with an existing ID
        productTemplateRel.setId(1L);
        ProductTemplateRelDTO productTemplateRelDTO = productTemplateRelMapper.toDto(productTemplateRel);

        int databaseSizeBeforeCreate = productTemplateRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTemplateRelMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplateRel in the database
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductTemplateRels() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList
        restProductTemplateRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTemplateRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductTemplateRel() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get the productTemplateRel
        restProductTemplateRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productTemplateRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productTemplateRel.getId().intValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductTemplateRelsByIdFiltering() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        Long id = productTemplateRel.getId();

        defaultProductTemplateRelShouldBeFound("id.equals=" + id);
        defaultProductTemplateRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductTemplateRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductTemplateRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductTemplateRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductTemplateRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductTemplateRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productTemplateRelList where activated equals to UPDATED_ACTIVATED
        defaultProductTemplateRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductTemplateRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productTemplateRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductTemplateRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductTemplateRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productTemplateRelList where activated equals to UPDATED_ACTIVATED
        defaultProductTemplateRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where activated is not null
        defaultProductTemplateRelShouldBeFound("activated.specified=true");

        // Get all the productTemplateRelList where activated is null
        defaultProductTemplateRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductTemplateRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productTemplateRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductTemplateRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductTemplateRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productTemplateRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductTemplateRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductTemplateRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productTemplateRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductTemplateRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where createdBy is not null
        defaultProductTemplateRelShouldBeFound("createdBy.specified=true");

        // Get all the productTemplateRelList where createdBy is null
        defaultProductTemplateRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductTemplateRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productTemplateRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductTemplateRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductTemplateRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productTemplateRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductTemplateRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductTemplateRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productTemplateRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductTemplateRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductTemplateRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productTemplateRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductTemplateRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductTemplateRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productTemplateRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductTemplateRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where createdDate is not null
        defaultProductTemplateRelShouldBeFound("createdDate.specified=true");

        // Get all the productTemplateRelList where createdDate is null
        defaultProductTemplateRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductTemplateRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTemplateRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductTemplateRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTemplateRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productTemplateRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where lastModifiedBy is not null
        defaultProductTemplateRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productTemplateRelList where lastModifiedBy is null
        defaultProductTemplateRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductTemplateRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTemplateRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductTemplateRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productTemplateRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductTemplateRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductTemplateRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productTemplateRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductTemplateRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductTemplateRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productTemplateRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductTemplateRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductTemplateRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productTemplateRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductTemplateRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        // Get all the productTemplateRelList where lastModifiedDate is not null
        defaultProductTemplateRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productTemplateRelList where lastModifiedDate is null
        defaultProductTemplateRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productTemplateRel.setProduct(product);
        productTemplateRelRepository.saveAndFlush(productTemplateRel);
        Long productId = product.getId();

        // Get all the productTemplateRelList where product equals to productId
        defaultProductTemplateRelShouldBeFound("productId.equals=" + productId);

        // Get all the productTemplateRelList where product equals to (productId + 1)
        defaultProductTemplateRelShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductTemplateRelsByProductTemplateIsEqualToSomething() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);
        ProductTemplate productTemplate = ProductTemplateResourceIT.createEntity(em);
        em.persist(productTemplate);
        em.flush();
        productTemplateRel.setProductTemplate(productTemplate);
        productTemplateRelRepository.saveAndFlush(productTemplateRel);
        Long productTemplateId = productTemplate.getId();

        // Get all the productTemplateRelList where productTemplate equals to productTemplateId
        defaultProductTemplateRelShouldBeFound("productTemplateId.equals=" + productTemplateId);

        // Get all the productTemplateRelList where productTemplate equals to (productTemplateId + 1)
        defaultProductTemplateRelShouldNotBeFound("productTemplateId.equals=" + (productTemplateId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductTemplateRelShouldBeFound(String filter) throws Exception {
        restProductTemplateRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTemplateRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductTemplateRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductTemplateRelShouldNotBeFound(String filter) throws Exception {
        restProductTemplateRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductTemplateRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductTemplateRel() throws Exception {
        // Get the productTemplateRel
        restProductTemplateRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductTemplateRel() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        int databaseSizeBeforeUpdate = productTemplateRelRepository.findAll().size();

        // Update the productTemplateRel
        ProductTemplateRel updatedProductTemplateRel = productTemplateRelRepository.findById(productTemplateRel.getId()).get();
        // Disconnect from session so that the updates on updatedProductTemplateRel are not directly saved in db
        em.detach(updatedProductTemplateRel);
        updatedProductTemplateRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductTemplateRelDTO productTemplateRelDTO = productTemplateRelMapper.toDto(updatedProductTemplateRel);

        restProductTemplateRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTemplateRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplateRel in the database
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplateRel testProductTemplateRel = productTemplateRelList.get(productTemplateRelList.size() - 1);
        assertThat(testProductTemplateRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductTemplateRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTemplateRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTemplateRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTemplateRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductTemplateRel() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRelRepository.findAll().size();
        productTemplateRel.setId(count.incrementAndGet());

        // Create the ProductTemplateRel
        ProductTemplateRelDTO productTemplateRelDTO = productTemplateRelMapper.toDto(productTemplateRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTemplateRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTemplateRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplateRel in the database
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductTemplateRel() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRelRepository.findAll().size();
        productTemplateRel.setId(count.incrementAndGet());

        // Create the ProductTemplateRel
        ProductTemplateRelDTO productTemplateRelDTO = productTemplateRelMapper.toDto(productTemplateRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplateRel in the database
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductTemplateRel() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRelRepository.findAll().size();
        productTemplateRel.setId(count.incrementAndGet());

        // Create the ProductTemplateRel
        ProductTemplateRelDTO productTemplateRelDTO = productTemplateRelMapper.toDto(productTemplateRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateRelMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTemplateRel in the database
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductTemplateRelWithPatch() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        int databaseSizeBeforeUpdate = productTemplateRelRepository.findAll().size();

        // Update the productTemplateRel using partial update
        ProductTemplateRel partialUpdatedProductTemplateRel = new ProductTemplateRel();
        partialUpdatedProductTemplateRel.setId(productTemplateRel.getId());

        partialUpdatedProductTemplateRel.createdBy(UPDATED_CREATED_BY).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restProductTemplateRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTemplateRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTemplateRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplateRel in the database
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplateRel testProductTemplateRel = productTemplateRelList.get(productTemplateRelList.size() - 1);
        assertThat(testProductTemplateRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductTemplateRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTemplateRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductTemplateRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTemplateRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductTemplateRelWithPatch() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        int databaseSizeBeforeUpdate = productTemplateRelRepository.findAll().size();

        // Update the productTemplateRel using partial update
        ProductTemplateRel partialUpdatedProductTemplateRel = new ProductTemplateRel();
        partialUpdatedProductTemplateRel.setId(productTemplateRel.getId());

        partialUpdatedProductTemplateRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductTemplateRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTemplateRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTemplateRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplateRel in the database
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplateRel testProductTemplateRel = productTemplateRelList.get(productTemplateRelList.size() - 1);
        assertThat(testProductTemplateRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductTemplateRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTemplateRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTemplateRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTemplateRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductTemplateRel() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRelRepository.findAll().size();
        productTemplateRel.setId(count.incrementAndGet());

        // Create the ProductTemplateRel
        ProductTemplateRelDTO productTemplateRelDTO = productTemplateRelMapper.toDto(productTemplateRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTemplateRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productTemplateRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplateRel in the database
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductTemplateRel() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRelRepository.findAll().size();
        productTemplateRel.setId(count.incrementAndGet());

        // Create the ProductTemplateRel
        ProductTemplateRelDTO productTemplateRelDTO = productTemplateRelMapper.toDto(productTemplateRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplateRel in the database
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductTemplateRel() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRelRepository.findAll().size();
        productTemplateRel.setId(count.incrementAndGet());

        // Create the ProductTemplateRel
        ProductTemplateRelDTO productTemplateRelDTO = productTemplateRelMapper.toDto(productTemplateRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTemplateRel in the database
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductTemplateRel() throws Exception {
        // Initialize the database
        productTemplateRelRepository.saveAndFlush(productTemplateRel);

        int databaseSizeBeforeDelete = productTemplateRelRepository.findAll().size();

        // Delete the productTemplateRel
        restProductTemplateRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productTemplateRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductTemplateRel> productTemplateRelList = productTemplateRelRepository.findAll();
        assertThat(productTemplateRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
