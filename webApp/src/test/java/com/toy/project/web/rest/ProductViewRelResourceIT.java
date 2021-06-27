package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.domain.ProductView;
import com.toy.project.domain.ProductViewRel;
import com.toy.project.repository.ProductViewRelRepository;
import com.toy.project.service.criteria.ProductViewRelCriteria;
import com.toy.project.service.dto.ProductViewRelDTO;
import com.toy.project.service.mapper.ProductViewRelMapper;
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
 * Integration tests for the {@link ProductViewRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductViewRelResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-view-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductViewRelRepository productViewRelRepository;

    @Autowired
    private ProductViewRelMapper productViewRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductViewRelMockMvc;

    private ProductViewRel productViewRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductViewRel createEntity(EntityManager em) {
        ProductViewRel productViewRel = new ProductViewRel()
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productViewRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductViewRel createUpdatedEntity(EntityManager em) {
        ProductViewRel productViewRel = new ProductViewRel()
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productViewRel;
    }

    @BeforeEach
    public void initTest() {
        productViewRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductViewRel() throws Exception {
        int databaseSizeBeforeCreate = productViewRelRepository.findAll().size();
        // Create the ProductViewRel
        ProductViewRelDTO productViewRelDTO = productViewRelMapper.toDto(productViewRel);
        restProductViewRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productViewRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductViewRel in the database
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductViewRel testProductViewRel = productViewRelList.get(productViewRelList.size() - 1);
        assertThat(testProductViewRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductViewRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductViewRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductViewRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductViewRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductViewRelWithExistingId() throws Exception {
        // Create the ProductViewRel with an existing ID
        productViewRel.setId(1L);
        ProductViewRelDTO productViewRelDTO = productViewRelMapper.toDto(productViewRel);

        int databaseSizeBeforeCreate = productViewRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductViewRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productViewRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductViewRel in the database
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductViewRels() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList
        restProductViewRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productViewRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductViewRel() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get the productViewRel
        restProductViewRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productViewRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productViewRel.getId().intValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductViewRelsByIdFiltering() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        Long id = productViewRel.getId();

        defaultProductViewRelShouldBeFound("id.equals=" + id);
        defaultProductViewRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductViewRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductViewRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductViewRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductViewRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductViewRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productViewRelList where activated equals to UPDATED_ACTIVATED
        defaultProductViewRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductViewRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productViewRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductViewRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductViewRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productViewRelList where activated equals to UPDATED_ACTIVATED
        defaultProductViewRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where activated is not null
        defaultProductViewRelShouldBeFound("activated.specified=true");

        // Get all the productViewRelList where activated is null
        defaultProductViewRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductViewRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productViewRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductViewRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductViewRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productViewRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductViewRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductViewRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productViewRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductViewRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where createdBy is not null
        defaultProductViewRelShouldBeFound("createdBy.specified=true");

        // Get all the productViewRelList where createdBy is null
        defaultProductViewRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductViewRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productViewRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductViewRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductViewRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productViewRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductViewRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductViewRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productViewRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductViewRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductViewRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productViewRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductViewRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductViewRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productViewRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductViewRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where createdDate is not null
        defaultProductViewRelShouldBeFound("createdDate.specified=true");

        // Get all the productViewRelList where createdDate is null
        defaultProductViewRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductViewRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductViewRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductViewRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductViewRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductViewRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productViewRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductViewRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where lastModifiedBy is not null
        defaultProductViewRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productViewRelList where lastModifiedBy is null
        defaultProductViewRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductViewRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductViewRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductViewRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productViewRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductViewRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductViewRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productViewRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductViewRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductViewRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productViewRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductViewRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductViewRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productViewRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductViewRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductViewRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        // Get all the productViewRelList where lastModifiedDate is not null
        defaultProductViewRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productViewRelList where lastModifiedDate is null
        defaultProductViewRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductViewRelsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productViewRel.setProduct(product);
        productViewRelRepository.saveAndFlush(productViewRel);
        Long productId = product.getId();

        // Get all the productViewRelList where product equals to productId
        defaultProductViewRelShouldBeFound("productId.equals=" + productId);

        // Get all the productViewRelList where product equals to (productId + 1)
        defaultProductViewRelShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductViewRelsByProductViewIsEqualToSomething() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);
        ProductView productView = ProductViewResourceIT.createEntity(em);
        em.persist(productView);
        em.flush();
        productViewRel.setProductView(productView);
        productViewRelRepository.saveAndFlush(productViewRel);
        Long productViewId = productView.getId();

        // Get all the productViewRelList where productView equals to productViewId
        defaultProductViewRelShouldBeFound("productViewId.equals=" + productViewId);

        // Get all the productViewRelList where productView equals to (productViewId + 1)
        defaultProductViewRelShouldNotBeFound("productViewId.equals=" + (productViewId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductViewRelShouldBeFound(String filter) throws Exception {
        restProductViewRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productViewRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductViewRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductViewRelShouldNotBeFound(String filter) throws Exception {
        restProductViewRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductViewRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductViewRel() throws Exception {
        // Get the productViewRel
        restProductViewRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductViewRel() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        int databaseSizeBeforeUpdate = productViewRelRepository.findAll().size();

        // Update the productViewRel
        ProductViewRel updatedProductViewRel = productViewRelRepository.findById(productViewRel.getId()).get();
        // Disconnect from session so that the updates on updatedProductViewRel are not directly saved in db
        em.detach(updatedProductViewRel);
        updatedProductViewRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductViewRelDTO productViewRelDTO = productViewRelMapper.toDto(updatedProductViewRel);

        restProductViewRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productViewRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductViewRel in the database
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeUpdate);
        ProductViewRel testProductViewRel = productViewRelList.get(productViewRelList.size() - 1);
        assertThat(testProductViewRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductViewRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductViewRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductViewRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductViewRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductViewRel() throws Exception {
        int databaseSizeBeforeUpdate = productViewRelRepository.findAll().size();
        productViewRel.setId(count.incrementAndGet());

        // Create the ProductViewRel
        ProductViewRelDTO productViewRelDTO = productViewRelMapper.toDto(productViewRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductViewRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productViewRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductViewRel in the database
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductViewRel() throws Exception {
        int databaseSizeBeforeUpdate = productViewRelRepository.findAll().size();
        productViewRel.setId(count.incrementAndGet());

        // Create the ProductViewRel
        ProductViewRelDTO productViewRelDTO = productViewRelMapper.toDto(productViewRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productViewRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductViewRel in the database
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductViewRel() throws Exception {
        int databaseSizeBeforeUpdate = productViewRelRepository.findAll().size();
        productViewRel.setId(count.incrementAndGet());

        // Create the ProductViewRel
        ProductViewRelDTO productViewRelDTO = productViewRelMapper.toDto(productViewRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewRelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productViewRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductViewRel in the database
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductViewRelWithPatch() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        int databaseSizeBeforeUpdate = productViewRelRepository.findAll().size();

        // Update the productViewRel using partial update
        ProductViewRel partialUpdatedProductViewRel = new ProductViewRel();
        partialUpdatedProductViewRel.setId(productViewRel.getId());

        partialUpdatedProductViewRel.activated(UPDATED_ACTIVATED).lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductViewRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductViewRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductViewRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductViewRel in the database
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeUpdate);
        ProductViewRel testProductViewRel = productViewRelList.get(productViewRelList.size() - 1);
        assertThat(testProductViewRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductViewRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductViewRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductViewRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductViewRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductViewRelWithPatch() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        int databaseSizeBeforeUpdate = productViewRelRepository.findAll().size();

        // Update the productViewRel using partial update
        ProductViewRel partialUpdatedProductViewRel = new ProductViewRel();
        partialUpdatedProductViewRel.setId(productViewRel.getId());

        partialUpdatedProductViewRel
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductViewRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductViewRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductViewRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductViewRel in the database
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeUpdate);
        ProductViewRel testProductViewRel = productViewRelList.get(productViewRelList.size() - 1);
        assertThat(testProductViewRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductViewRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductViewRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductViewRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductViewRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductViewRel() throws Exception {
        int databaseSizeBeforeUpdate = productViewRelRepository.findAll().size();
        productViewRel.setId(count.incrementAndGet());

        // Create the ProductViewRel
        ProductViewRelDTO productViewRelDTO = productViewRelMapper.toDto(productViewRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductViewRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productViewRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productViewRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductViewRel in the database
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductViewRel() throws Exception {
        int databaseSizeBeforeUpdate = productViewRelRepository.findAll().size();
        productViewRel.setId(count.incrementAndGet());

        // Create the ProductViewRel
        ProductViewRelDTO productViewRelDTO = productViewRelMapper.toDto(productViewRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productViewRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductViewRel in the database
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductViewRel() throws Exception {
        int databaseSizeBeforeUpdate = productViewRelRepository.findAll().size();
        productViewRel.setId(count.incrementAndGet());

        // Create the ProductViewRel
        ProductViewRelDTO productViewRelDTO = productViewRelMapper.toDto(productViewRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductViewRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productViewRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductViewRel in the database
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductViewRel() throws Exception {
        // Initialize the database
        productViewRelRepository.saveAndFlush(productViewRel);

        int databaseSizeBeforeDelete = productViewRelRepository.findAll().size();

        // Delete the productViewRel
        restProductViewRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productViewRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductViewRel> productViewRelList = productViewRelRepository.findAll();
        assertThat(productViewRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
