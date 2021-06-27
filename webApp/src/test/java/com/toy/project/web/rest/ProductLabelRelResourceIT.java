package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.domain.ProductLabel;
import com.toy.project.domain.ProductLabelRel;
import com.toy.project.repository.ProductLabelRelRepository;
import com.toy.project.service.criteria.ProductLabelRelCriteria;
import com.toy.project.service.dto.ProductLabelRelDTO;
import com.toy.project.service.mapper.ProductLabelRelMapper;
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
 * Integration tests for the {@link ProductLabelRelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductLabelRelResourceIT {

    private static final Boolean DEFAULT_IS_DISPLAY_DATE = false;
    private static final Boolean UPDATED_IS_DISPLAY_DATE = true;

    private static final Instant DEFAULT_DISPLAY_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISPLAY_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DISPLAY_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISPLAY_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/product-label-rels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductLabelRelRepository productLabelRelRepository;

    @Autowired
    private ProductLabelRelMapper productLabelRelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductLabelRelMockMvc;

    private ProductLabelRel productLabelRel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductLabelRel createEntity(EntityManager em) {
        ProductLabelRel productLabelRel = new ProductLabelRel()
            .isDisplayDate(DEFAULT_IS_DISPLAY_DATE)
            .displayDateFrom(DEFAULT_DISPLAY_DATE_FROM)
            .displayDateTo(DEFAULT_DISPLAY_DATE_TO)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productLabelRel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductLabelRel createUpdatedEntity(EntityManager em) {
        ProductLabelRel productLabelRel = new ProductLabelRel()
            .isDisplayDate(UPDATED_IS_DISPLAY_DATE)
            .displayDateFrom(UPDATED_DISPLAY_DATE_FROM)
            .displayDateTo(UPDATED_DISPLAY_DATE_TO)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productLabelRel;
    }

    @BeforeEach
    public void initTest() {
        productLabelRel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductLabelRel() throws Exception {
        int databaseSizeBeforeCreate = productLabelRelRepository.findAll().size();
        // Create the ProductLabelRel
        ProductLabelRelDTO productLabelRelDTO = productLabelRelMapper.toDto(productLabelRel);
        restProductLabelRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productLabelRelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductLabelRel in the database
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductLabelRel testProductLabelRel = productLabelRelList.get(productLabelRelList.size() - 1);
        assertThat(testProductLabelRel.getIsDisplayDate()).isEqualTo(DEFAULT_IS_DISPLAY_DATE);
        assertThat(testProductLabelRel.getDisplayDateFrom()).isEqualTo(DEFAULT_DISPLAY_DATE_FROM);
        assertThat(testProductLabelRel.getDisplayDateTo()).isEqualTo(DEFAULT_DISPLAY_DATE_TO);
        assertThat(testProductLabelRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductLabelRel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductLabelRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductLabelRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductLabelRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductLabelRelWithExistingId() throws Exception {
        // Create the ProductLabelRel with an existing ID
        productLabelRel.setId(1L);
        ProductLabelRelDTO productLabelRelDTO = productLabelRelMapper.toDto(productLabelRel);

        int databaseSizeBeforeCreate = productLabelRelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductLabelRelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productLabelRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabelRel in the database
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductLabelRels() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList
        restProductLabelRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productLabelRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].isDisplayDate").value(hasItem(DEFAULT_IS_DISPLAY_DATE.booleanValue())))
            .andExpect(jsonPath("$.[*].displayDateFrom").value(hasItem(DEFAULT_DISPLAY_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].displayDateTo").value(hasItem(DEFAULT_DISPLAY_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductLabelRel() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get the productLabelRel
        restProductLabelRelMockMvc
            .perform(get(ENTITY_API_URL_ID, productLabelRel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productLabelRel.getId().intValue()))
            .andExpect(jsonPath("$.isDisplayDate").value(DEFAULT_IS_DISPLAY_DATE.booleanValue()))
            .andExpect(jsonPath("$.displayDateFrom").value(DEFAULT_DISPLAY_DATE_FROM.toString()))
            .andExpect(jsonPath("$.displayDateTo").value(DEFAULT_DISPLAY_DATE_TO.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductLabelRelsByIdFiltering() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        Long id = productLabelRel.getId();

        defaultProductLabelRelShouldBeFound("id.equals=" + id);
        defaultProductLabelRelShouldNotBeFound("id.notEquals=" + id);

        defaultProductLabelRelShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductLabelRelShouldNotBeFound("id.greaterThan=" + id);

        defaultProductLabelRelShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductLabelRelShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByIsDisplayDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where isDisplayDate equals to DEFAULT_IS_DISPLAY_DATE
        defaultProductLabelRelShouldBeFound("isDisplayDate.equals=" + DEFAULT_IS_DISPLAY_DATE);

        // Get all the productLabelRelList where isDisplayDate equals to UPDATED_IS_DISPLAY_DATE
        defaultProductLabelRelShouldNotBeFound("isDisplayDate.equals=" + UPDATED_IS_DISPLAY_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByIsDisplayDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where isDisplayDate not equals to DEFAULT_IS_DISPLAY_DATE
        defaultProductLabelRelShouldNotBeFound("isDisplayDate.notEquals=" + DEFAULT_IS_DISPLAY_DATE);

        // Get all the productLabelRelList where isDisplayDate not equals to UPDATED_IS_DISPLAY_DATE
        defaultProductLabelRelShouldBeFound("isDisplayDate.notEquals=" + UPDATED_IS_DISPLAY_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByIsDisplayDateIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where isDisplayDate in DEFAULT_IS_DISPLAY_DATE or UPDATED_IS_DISPLAY_DATE
        defaultProductLabelRelShouldBeFound("isDisplayDate.in=" + DEFAULT_IS_DISPLAY_DATE + "," + UPDATED_IS_DISPLAY_DATE);

        // Get all the productLabelRelList where isDisplayDate equals to UPDATED_IS_DISPLAY_DATE
        defaultProductLabelRelShouldNotBeFound("isDisplayDate.in=" + UPDATED_IS_DISPLAY_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByIsDisplayDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where isDisplayDate is not null
        defaultProductLabelRelShouldBeFound("isDisplayDate.specified=true");

        // Get all the productLabelRelList where isDisplayDate is null
        defaultProductLabelRelShouldNotBeFound("isDisplayDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByDisplayDateFromIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where displayDateFrom equals to DEFAULT_DISPLAY_DATE_FROM
        defaultProductLabelRelShouldBeFound("displayDateFrom.equals=" + DEFAULT_DISPLAY_DATE_FROM);

        // Get all the productLabelRelList where displayDateFrom equals to UPDATED_DISPLAY_DATE_FROM
        defaultProductLabelRelShouldNotBeFound("displayDateFrom.equals=" + UPDATED_DISPLAY_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByDisplayDateFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where displayDateFrom not equals to DEFAULT_DISPLAY_DATE_FROM
        defaultProductLabelRelShouldNotBeFound("displayDateFrom.notEquals=" + DEFAULT_DISPLAY_DATE_FROM);

        // Get all the productLabelRelList where displayDateFrom not equals to UPDATED_DISPLAY_DATE_FROM
        defaultProductLabelRelShouldBeFound("displayDateFrom.notEquals=" + UPDATED_DISPLAY_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByDisplayDateFromIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where displayDateFrom in DEFAULT_DISPLAY_DATE_FROM or UPDATED_DISPLAY_DATE_FROM
        defaultProductLabelRelShouldBeFound("displayDateFrom.in=" + DEFAULT_DISPLAY_DATE_FROM + "," + UPDATED_DISPLAY_DATE_FROM);

        // Get all the productLabelRelList where displayDateFrom equals to UPDATED_DISPLAY_DATE_FROM
        defaultProductLabelRelShouldNotBeFound("displayDateFrom.in=" + UPDATED_DISPLAY_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByDisplayDateFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where displayDateFrom is not null
        defaultProductLabelRelShouldBeFound("displayDateFrom.specified=true");

        // Get all the productLabelRelList where displayDateFrom is null
        defaultProductLabelRelShouldNotBeFound("displayDateFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByDisplayDateToIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where displayDateTo equals to DEFAULT_DISPLAY_DATE_TO
        defaultProductLabelRelShouldBeFound("displayDateTo.equals=" + DEFAULT_DISPLAY_DATE_TO);

        // Get all the productLabelRelList where displayDateTo equals to UPDATED_DISPLAY_DATE_TO
        defaultProductLabelRelShouldNotBeFound("displayDateTo.equals=" + UPDATED_DISPLAY_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByDisplayDateToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where displayDateTo not equals to DEFAULT_DISPLAY_DATE_TO
        defaultProductLabelRelShouldNotBeFound("displayDateTo.notEquals=" + DEFAULT_DISPLAY_DATE_TO);

        // Get all the productLabelRelList where displayDateTo not equals to UPDATED_DISPLAY_DATE_TO
        defaultProductLabelRelShouldBeFound("displayDateTo.notEquals=" + UPDATED_DISPLAY_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByDisplayDateToIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where displayDateTo in DEFAULT_DISPLAY_DATE_TO or UPDATED_DISPLAY_DATE_TO
        defaultProductLabelRelShouldBeFound("displayDateTo.in=" + DEFAULT_DISPLAY_DATE_TO + "," + UPDATED_DISPLAY_DATE_TO);

        // Get all the productLabelRelList where displayDateTo equals to UPDATED_DISPLAY_DATE_TO
        defaultProductLabelRelShouldNotBeFound("displayDateTo.in=" + UPDATED_DISPLAY_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByDisplayDateToIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where displayDateTo is not null
        defaultProductLabelRelShouldBeFound("displayDateTo.specified=true");

        // Get all the productLabelRelList where displayDateTo is null
        defaultProductLabelRelShouldNotBeFound("displayDateTo.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where activated equals to DEFAULT_ACTIVATED
        defaultProductLabelRelShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productLabelRelList where activated equals to UPDATED_ACTIVATED
        defaultProductLabelRelShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where activated not equals to DEFAULT_ACTIVATED
        defaultProductLabelRelShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productLabelRelList where activated not equals to UPDATED_ACTIVATED
        defaultProductLabelRelShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductLabelRelShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productLabelRelList where activated equals to UPDATED_ACTIVATED
        defaultProductLabelRelShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where activated is not null
        defaultProductLabelRelShouldBeFound("activated.specified=true");

        // Get all the productLabelRelList where activated is null
        defaultProductLabelRelShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductLabelRelShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productLabelRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductLabelRelShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductLabelRelShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productLabelRelList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductLabelRelShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductLabelRelShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productLabelRelList where createdBy equals to UPDATED_CREATED_BY
        defaultProductLabelRelShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where createdBy is not null
        defaultProductLabelRelShouldBeFound("createdBy.specified=true");

        // Get all the productLabelRelList where createdBy is null
        defaultProductLabelRelShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where createdBy contains DEFAULT_CREATED_BY
        defaultProductLabelRelShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productLabelRelList where createdBy contains UPDATED_CREATED_BY
        defaultProductLabelRelShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductLabelRelShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productLabelRelList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductLabelRelShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductLabelRelShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productLabelRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductLabelRelShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductLabelRelShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productLabelRelList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductLabelRelShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductLabelRelShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productLabelRelList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductLabelRelShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where createdDate is not null
        defaultProductLabelRelShouldBeFound("createdDate.specified=true");

        // Get all the productLabelRelList where createdDate is null
        defaultProductLabelRelShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductLabelRelShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productLabelRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductLabelRelShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductLabelRelShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productLabelRelList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductLabelRelShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductLabelRelShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productLabelRelList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductLabelRelShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where lastModifiedBy is not null
        defaultProductLabelRelShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productLabelRelList where lastModifiedBy is null
        defaultProductLabelRelShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductLabelRelShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productLabelRelList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductLabelRelShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductLabelRelShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productLabelRelList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductLabelRelShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductLabelRelShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productLabelRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductLabelRelShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductLabelRelShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productLabelRelList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductLabelRelShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductLabelRelShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productLabelRelList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductLabelRelShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        // Get all the productLabelRelList where lastModifiedDate is not null
        defaultProductLabelRelShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productLabelRelList where lastModifiedDate is null
        defaultProductLabelRelShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByProductIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);
        Product product = ProductResourceIT.createEntity(em);
        em.persist(product);
        em.flush();
        productLabelRel.setProduct(product);
        productLabelRelRepository.saveAndFlush(productLabelRel);
        Long productId = product.getId();

        // Get all the productLabelRelList where product equals to productId
        defaultProductLabelRelShouldBeFound("productId.equals=" + productId);

        // Get all the productLabelRelList where product equals to (productId + 1)
        defaultProductLabelRelShouldNotBeFound("productId.equals=" + (productId + 1));
    }

    @Test
    @Transactional
    void getAllProductLabelRelsByProductLabelIsEqualToSomething() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);
        ProductLabel productLabel = ProductLabelResourceIT.createEntity(em);
        em.persist(productLabel);
        em.flush();
        productLabelRel.setProductLabel(productLabel);
        productLabelRelRepository.saveAndFlush(productLabelRel);
        Long productLabelId = productLabel.getId();

        // Get all the productLabelRelList where productLabel equals to productLabelId
        defaultProductLabelRelShouldBeFound("productLabelId.equals=" + productLabelId);

        // Get all the productLabelRelList where productLabel equals to (productLabelId + 1)
        defaultProductLabelRelShouldNotBeFound("productLabelId.equals=" + (productLabelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductLabelRelShouldBeFound(String filter) throws Exception {
        restProductLabelRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productLabelRel.getId().intValue())))
            .andExpect(jsonPath("$.[*].isDisplayDate").value(hasItem(DEFAULT_IS_DISPLAY_DATE.booleanValue())))
            .andExpect(jsonPath("$.[*].displayDateFrom").value(hasItem(DEFAULT_DISPLAY_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].displayDateTo").value(hasItem(DEFAULT_DISPLAY_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductLabelRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductLabelRelShouldNotBeFound(String filter) throws Exception {
        restProductLabelRelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductLabelRelMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductLabelRel() throws Exception {
        // Get the productLabelRel
        restProductLabelRelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductLabelRel() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        int databaseSizeBeforeUpdate = productLabelRelRepository.findAll().size();

        // Update the productLabelRel
        ProductLabelRel updatedProductLabelRel = productLabelRelRepository.findById(productLabelRel.getId()).get();
        // Disconnect from session so that the updates on updatedProductLabelRel are not directly saved in db
        em.detach(updatedProductLabelRel);
        updatedProductLabelRel
            .isDisplayDate(UPDATED_IS_DISPLAY_DATE)
            .displayDateFrom(UPDATED_DISPLAY_DATE_FROM)
            .displayDateTo(UPDATED_DISPLAY_DATE_TO)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductLabelRelDTO productLabelRelDTO = productLabelRelMapper.toDto(updatedProductLabelRel);

        restProductLabelRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productLabelRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelRelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabelRel in the database
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeUpdate);
        ProductLabelRel testProductLabelRel = productLabelRelList.get(productLabelRelList.size() - 1);
        assertThat(testProductLabelRel.getIsDisplayDate()).isEqualTo(UPDATED_IS_DISPLAY_DATE);
        assertThat(testProductLabelRel.getDisplayDateFrom()).isEqualTo(UPDATED_DISPLAY_DATE_FROM);
        assertThat(testProductLabelRel.getDisplayDateTo()).isEqualTo(UPDATED_DISPLAY_DATE_TO);
        assertThat(testProductLabelRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductLabelRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabelRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductLabelRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductLabelRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductLabelRel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRelRepository.findAll().size();
        productLabelRel.setId(count.incrementAndGet());

        // Create the ProductLabelRel
        ProductLabelRelDTO productLabelRelDTO = productLabelRelMapper.toDto(productLabelRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductLabelRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productLabelRelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabelRel in the database
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductLabelRel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRelRepository.findAll().size();
        productLabelRel.setId(count.incrementAndGet());

        // Create the ProductLabelRel
        ProductLabelRelDTO productLabelRelDTO = productLabelRelMapper.toDto(productLabelRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelRelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabelRel in the database
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductLabelRel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRelRepository.findAll().size();
        productLabelRel.setId(count.incrementAndGet());

        // Create the ProductLabelRel
        ProductLabelRelDTO productLabelRelDTO = productLabelRelMapper.toDto(productLabelRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelRelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productLabelRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductLabelRel in the database
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductLabelRelWithPatch() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        int databaseSizeBeforeUpdate = productLabelRelRepository.findAll().size();

        // Update the productLabelRel using partial update
        ProductLabelRel partialUpdatedProductLabelRel = new ProductLabelRel();
        partialUpdatedProductLabelRel.setId(productLabelRel.getId());

        partialUpdatedProductLabelRel
            .displayDateFrom(UPDATED_DISPLAY_DATE_FROM)
            .displayDateTo(UPDATED_DISPLAY_DATE_TO)
            .createdBy(UPDATED_CREATED_BY);

        restProductLabelRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductLabelRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductLabelRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabelRel in the database
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeUpdate);
        ProductLabelRel testProductLabelRel = productLabelRelList.get(productLabelRelList.size() - 1);
        assertThat(testProductLabelRel.getIsDisplayDate()).isEqualTo(DEFAULT_IS_DISPLAY_DATE);
        assertThat(testProductLabelRel.getDisplayDateFrom()).isEqualTo(UPDATED_DISPLAY_DATE_FROM);
        assertThat(testProductLabelRel.getDisplayDateTo()).isEqualTo(UPDATED_DISPLAY_DATE_TO);
        assertThat(testProductLabelRel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductLabelRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabelRel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductLabelRel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductLabelRel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductLabelRelWithPatch() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        int databaseSizeBeforeUpdate = productLabelRelRepository.findAll().size();

        // Update the productLabelRel using partial update
        ProductLabelRel partialUpdatedProductLabelRel = new ProductLabelRel();
        partialUpdatedProductLabelRel.setId(productLabelRel.getId());

        partialUpdatedProductLabelRel
            .isDisplayDate(UPDATED_IS_DISPLAY_DATE)
            .displayDateFrom(UPDATED_DISPLAY_DATE_FROM)
            .displayDateTo(UPDATED_DISPLAY_DATE_TO)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductLabelRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductLabelRel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductLabelRel))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabelRel in the database
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeUpdate);
        ProductLabelRel testProductLabelRel = productLabelRelList.get(productLabelRelList.size() - 1);
        assertThat(testProductLabelRel.getIsDisplayDate()).isEqualTo(UPDATED_IS_DISPLAY_DATE);
        assertThat(testProductLabelRel.getDisplayDateFrom()).isEqualTo(UPDATED_DISPLAY_DATE_FROM);
        assertThat(testProductLabelRel.getDisplayDateTo()).isEqualTo(UPDATED_DISPLAY_DATE_TO);
        assertThat(testProductLabelRel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductLabelRel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabelRel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductLabelRel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductLabelRel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductLabelRel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRelRepository.findAll().size();
        productLabelRel.setId(count.incrementAndGet());

        // Create the ProductLabelRel
        ProductLabelRelDTO productLabelRelDTO = productLabelRelMapper.toDto(productLabelRel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductLabelRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productLabelRelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabelRel in the database
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductLabelRel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRelRepository.findAll().size();
        productLabelRel.setId(count.incrementAndGet());

        // Create the ProductLabelRel
        ProductLabelRelDTO productLabelRelDTO = productLabelRelMapper.toDto(productLabelRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelRelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelRelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabelRel in the database
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductLabelRel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRelRepository.findAll().size();
        productLabelRel.setId(count.incrementAndGet());

        // Create the ProductLabelRel
        ProductLabelRelDTO productLabelRelDTO = productLabelRelMapper.toDto(productLabelRel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelRelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelRelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductLabelRel in the database
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductLabelRel() throws Exception {
        // Initialize the database
        productLabelRelRepository.saveAndFlush(productLabelRel);

        int databaseSizeBeforeDelete = productLabelRelRepository.findAll().size();

        // Delete the productLabelRel
        restProductLabelRelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productLabelRel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductLabelRel> productLabelRelList = productLabelRelRepository.findAll();
        assertThat(productLabelRelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
