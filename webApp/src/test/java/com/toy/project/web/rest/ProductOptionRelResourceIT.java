package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.domain.ProductOption;
import com.toy.project.domain.ProductOptionRel;
import com.toy.project.repository.ProductOptionRelRepository;
import com.toy.project.service.criteria.ProductOptionRelCriteria;
import com.toy.project.service.dto.ProductOptionRelDTO;
import com.toy.project.service.mapper.ProductOptionRelMapper;
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
 * Integration tests for the {@link ProductOptionRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductOptionRelResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-option-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductOptionRelRepository productOptionRelRepository;

    @Autowired
    private ProductOptionRelMapper productOptionRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductOptionRelMockMvc;

    private ProductOptionRel productOptionRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOptionRel createEntity(EntityManager em) {
        ProductOptionRel productOptionRel = new ProductOptionRel()
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productOptionRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOptionRel createUpdatedEntity(EntityManager em) {
        ProductOptionRel productOptionRel = new ProductOptionRel()
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productOptionRel;
    }

    @BeforeEach
    public void initTest() {
        productOptionRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductOptionRel() throws Exception {
        int databaseSizeBeforeCreate = productOptionRelRepository.findAll().size();
        // Create the ProductOptionRel
        ProductOptionRelDTO productOptionRelDTO = productOptionRelMapper.toDto(productOptionRel);
        restProductOptionRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOptionRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductOptionRel in the database
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOptionRel testProductOptionRel = productOptionRelList.get(productOptionRelList.size() - 1);
        assertThat(testProductOptionRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductOptionRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductOptionRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductOptionRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductOptionRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductOptionRelWithExistingId() throws Exception {
        // Create the ProductOptionRel with an existing ID
        productOptionRel.setId(1L);
        ProductOptionRelDTO productOptionRelDTO = productOptionRelMapper.toDto(productOptionRel);

        int databaseSizeBeforeCreate = productOptionRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOptionRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionRel in the database
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductOptionRels() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList
        restProductOptionRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductOptionRel() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get the productOptionRel
        restProductOptionRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productOptionRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productOptionRel.getId().intValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductOptionRelsByIdFiltering() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        Long id = productOptionRel.getId();

        defaultProductOptionRelShouldBeFound("id.equals=" + id);
        defaultProductOptionRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductOptionRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductOptionRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductOptionRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductOptionRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductOptionRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productOptionRelList where activated equals to UPDATED_ACTIVATED
        defaultProductOptionRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductOptionRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productOptionRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductOptionRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductOptionRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productOptionRelList where activated equals to UPDATED_ACTIVATED
        defaultProductOptionRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where activated is not null
        defaultProductOptionRelShouldBeFound("activated.specified=true");

        // Get all the productOptionRelList where activated is null
        defaultProductOptionRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductOptionRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productOptionRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductOptionRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductOptionRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productOptionRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductOptionRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductOptionRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productOptionRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductOptionRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where createdBy is not null
        defaultProductOptionRelShouldBeFound("createdBy.specified=true");

        // Get all the productOptionRelList where createdBy is null
        defaultProductOptionRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductOptionRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productOptionRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductOptionRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductOptionRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productOptionRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductOptionRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductOptionRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productOptionRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductOptionRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductOptionRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productOptionRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductOptionRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductOptionRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productOptionRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductOptionRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where createdDate is not null
        defaultProductOptionRelShouldBeFound("createdDate.specified=true");

        // Get all the productOptionRelList where createdDate is null
        defaultProductOptionRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductOptionRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productOptionRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where lastModifiedBy is not null
        defaultProductOptionRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productOptionRelList where lastModifiedBy is null
        defaultProductOptionRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductOptionRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductOptionRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductOptionRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productOptionRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductOptionRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productOptionRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productOptionRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        // Get all the productOptionRelList where lastModifiedDate is not null
        defaultProductOptionRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productOptionRelList where lastModifiedDate is null
        defaultProductOptionRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productOptionRel.setProduct(product);
        productOptionRelRepository.saveAndFlush(productOptionRel);
        Long productId = product.getId();

        // Get all the productOptionRelList where product equals to productId
        defaultProductOptionRelShouldBeFound("productId.equals=" + productId);

        // Get all the productOptionRelList where product equals to (productId + 1)
        defaultProductOptionRelShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductOptionRelsByProductOptionIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);
        ProductOption productOption = ProductOptionResourceIT.createEntity(em);
        em.persist(productOption);
        em.flush();
        productOptionRel.setProductOption(productOption);
        productOptionRelRepository.saveAndFlush(productOptionRel);
        Long productOptionId = productOption.getId();

        // Get all the productOptionRelList where productOption equals to productOptionId
        defaultProductOptionRelShouldBeFound("productOptionId.equals=" + productOptionId);

        // Get all the productOptionRelList where productOption equals to (productOptionId + 1)
        defaultProductOptionRelShouldNotBeFound("productOptionId.equals=" + (productOptionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductOptionRelShouldBeFound(String filter) throws Exception {
        restProductOptionRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOptionRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductOptionRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductOptionRelShouldNotBeFound(String filter) throws Exception {
        restProductOptionRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductOptionRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductOptionRel() throws Exception {
        // Get the productOptionRel
        restProductOptionRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductOptionRel() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        int databaseSizeBeforeUpdate = productOptionRelRepository.findAll().size();

        // Update the productOptionRel
        ProductOptionRel updatedProductOptionRel = productOptionRelRepository.findById(productOptionRel.getId()).get();
        // Disconnect from session so that the updates on updatedProductOptionRel are not directly saved in db
        em.detach(updatedProductOptionRel);
        updatedProductOptionRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductOptionRelDTO productOptionRelDTO = productOptionRelMapper.toDto(updatedProductOptionRel);

        restProductOptionRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionRel in the database
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionRel testProductOptionRel = productOptionRelList.get(productOptionRelList.size() - 1);
        assertThat(testProductOptionRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOptionRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOptionRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOptionRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOptionRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductOptionRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRelRepository.findAll().size();
        productOptionRel.setId(count.incrementAndGet());

        // Create the ProductOptionRel
        ProductOptionRelDTO productOptionRelDTO = productOptionRelMapper.toDto(productOptionRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionRel in the database
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductOptionRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRelRepository.findAll().size();
        productOptionRel.setId(count.incrementAndGet());

        // Create the ProductOptionRel
        ProductOptionRelDTO productOptionRelDTO = productOptionRelMapper.toDto(productOptionRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionRel in the database
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductOptionRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRelRepository.findAll().size();
        productOptionRel.setId(count.incrementAndGet());

        // Create the ProductOptionRel
        ProductOptionRelDTO productOptionRelDTO = productOptionRelMapper.toDto(productOptionRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionRelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOptionRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOptionRel in the database
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductOptionRelWithPatch() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        int databaseSizeBeforeUpdate = productOptionRelRepository.findAll().size();

        // Update the productOptionRel using partial update
        ProductOptionRel partialUpdatedProductOptionRel = new ProductOptionRel();
        partialUpdatedProductOptionRel.setId(productOptionRel.getId());

        partialUpdatedProductOptionRel.activated(UPDATED_ACTIVATED).createdBy(UPDATED_CREATED_BY).lastModifiedBy(UPDATED_LAST_MODIFIED_BY);

        restProductOptionRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOptionRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOptionRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionRel in the database
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionRel testProductOptionRel = productOptionRelList.get(productOptionRelList.size() - 1);
        assertThat(testProductOptionRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOptionRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOptionRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductOptionRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOptionRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductOptionRelWithPatch() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        int databaseSizeBeforeUpdate = productOptionRelRepository.findAll().size();

        // Update the productOptionRel using partial update
        ProductOptionRel partialUpdatedProductOptionRel = new ProductOptionRel();
        partialUpdatedProductOptionRel.setId(productOptionRel.getId());

        partialUpdatedProductOptionRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductOptionRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOptionRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOptionRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductOptionRel in the database
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeUpdate);
        ProductOptionRel testProductOptionRel = productOptionRelList.get(productOptionRelList.size() - 1);
        assertThat(testProductOptionRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOptionRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOptionRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOptionRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOptionRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductOptionRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRelRepository.findAll().size();
        productOptionRel.setId(count.incrementAndGet());

        // Create the ProductOptionRel
        ProductOptionRelDTO productOptionRelDTO = productOptionRelMapper.toDto(productOptionRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productOptionRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionRel in the database
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductOptionRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRelRepository.findAll().size();
        productOptionRel.setId(count.incrementAndGet());

        // Create the ProductOptionRel
        ProductOptionRelDTO productOptionRelDTO = productOptionRelMapper.toDto(productOptionRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOptionRel in the database
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductOptionRel() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRelRepository.findAll().size();
        productOptionRel.setId(count.incrementAndGet());

        // Create the ProductOptionRel
        ProductOptionRelDTO productOptionRelDTO = productOptionRelMapper.toDto(productOptionRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOptionRel in the database
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductOptionRel() throws Exception {
        // Initialize the database
        productOptionRelRepository.saveAndFlush(productOptionRel);

        int databaseSizeBeforeDelete = productOptionRelRepository.findAll().size();

        // Delete the productOptionRel
        restProductOptionRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productOptionRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOptionRel> productOptionRelList = productOptionRelRepository.findAll();
        assertThat(productOptionRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
