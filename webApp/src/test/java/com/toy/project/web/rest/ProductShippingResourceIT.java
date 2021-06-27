package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductShipping;
import com.toy.project.domain.ProductShippingRel;
import com.toy.project.repository.ProductShippingRepository;
import com.toy.project.service.criteria.ProductShippingCriteria;
import com.toy.project.service.dto.ProductShippingDTO;
import com.toy.project.service.mapper.ProductShippingMapper;
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
 * Integration tests for the {@link ProductShippingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductShippingResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_GROUP = false;
    private static final Boolean UPDATED_IS_GROUP = true;

    private static final Integer DEFAULT_DEFAULT_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_DEFAULT_SHIPPING_PRICE = 2;
    private static final Integer SMALLER_DEFAULT_SHIPPING_PRICE = 1 - 1;

    private static final Integer DEFAULT_FREE_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_FREE_SHIPPING_PRICE = 2;
    private static final Integer SMALLER_FREE_SHIPPING_PRICE = 1 - 1;

    private static final Integer DEFAULT_JEJU_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_JEJU_SHIPPING_PRICE = 2;
    private static final Integer SMALLER_JEJU_SHIPPING_PRICE = 1 - 1;

    private static final Integer DEFAULT_DIFFICULT_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_DIFFICULT_SHIPPING_PRICE = 2;
    private static final Integer SMALLER_DIFFICULT_SHIPPING_PRICE = 1 - 1;

    private static final Integer DEFAULT_REFUND_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_REFUND_SHIPPING_PRICE = 2;
    private static final Integer SMALLER_REFUND_SHIPPING_PRICE = 1 - 1;

    private static final Integer DEFAULT_EXCHANGE_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_EXCHANGE_SHIPPING_PRICE = 2;
    private static final Integer SMALLER_EXCHANGE_SHIPPING_PRICE = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/product-shippings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductShippingRepository productShippingRepository;

    @Autowired
    private ProductShippingMapper productShippingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductShippingMockMvc;

    private ProductShipping productShipping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductShipping createEntity(EntityManager em) {
        ProductShipping productShipping = new ProductShipping()
            .name(DEFAULT_NAME)
            .isGroup(DEFAULT_IS_GROUP)
            .defaultShippingPrice(DEFAULT_DEFAULT_SHIPPING_PRICE)
            .freeShippingPrice(DEFAULT_FREE_SHIPPING_PRICE)
            .jejuShippingPrice(DEFAULT_JEJU_SHIPPING_PRICE)
            .difficultShippingPrice(DEFAULT_DIFFICULT_SHIPPING_PRICE)
            .refundShippingPrice(DEFAULT_REFUND_SHIPPING_PRICE)
            .exchangeShippingPrice(DEFAULT_EXCHANGE_SHIPPING_PRICE)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productShipping;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductShipping createUpdatedEntity(EntityManager em) {
        ProductShipping productShipping = new ProductShipping()
            .name(UPDATED_NAME)
            .isGroup(UPDATED_IS_GROUP)
            .defaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE)
            .freeShippingPrice(UPDATED_FREE_SHIPPING_PRICE)
            .jejuShippingPrice(UPDATED_JEJU_SHIPPING_PRICE)
            .difficultShippingPrice(UPDATED_DIFFICULT_SHIPPING_PRICE)
            .refundShippingPrice(UPDATED_REFUND_SHIPPING_PRICE)
            .exchangeShippingPrice(UPDATED_EXCHANGE_SHIPPING_PRICE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productShipping;
    }

    @BeforeEach
    public void initTest() {
        productShipping = createEntity(em);
    }

    @Test
    @Transactional
    void createProductShipping() throws Exception {
        int databaseSizeBeforeCreate = productShippingRepository.findAll().size();
        // Create the ProductShipping
        ProductShippingDTO productShippingDTO = productShippingMapper.toDto(productShipping);
        restProductShippingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productShippingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductShipping in the database
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeCreate + 1);
        ProductShipping testProductShipping = productShippingList.get(productShippingList.size() - 1);
        assertThat(testProductShipping.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductShipping.getIsGroup()).isEqualTo(DEFAULT_IS_GROUP);
        assertThat(testProductShipping.getDefaultShippingPrice()).isEqualTo(DEFAULT_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductShipping.getFreeShippingPrice()).isEqualTo(DEFAULT_FREE_SHIPPING_PRICE);
        assertThat(testProductShipping.getJejuShippingPrice()).isEqualTo(DEFAULT_JEJU_SHIPPING_PRICE);
        assertThat(testProductShipping.getDifficultShippingPrice()).isEqualTo(DEFAULT_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProductShipping.getRefundShippingPrice()).isEqualTo(DEFAULT_REFUND_SHIPPING_PRICE);
        assertThat(testProductShipping.getExchangeShippingPrice()).isEqualTo(DEFAULT_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProductShipping.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductShipping.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductShipping.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductShipping.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductShipping.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductShippingWithExistingId() throws Exception {
        // Create the ProductShipping with an existing ID
        productShipping.setId(1L);
        ProductShippingDTO productShippingDTO = productShippingMapper.toDto(productShipping);

        int databaseSizeBeforeCreate = productShippingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductShippingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productShippingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShipping in the database
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductShippings() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList
        restProductShippingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productShipping.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isGroup").value(hasItem(DEFAULT_IS_GROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultShippingPrice").value(hasItem(DEFAULT_DEFAULT_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].freeShippingPrice").value(hasItem(DEFAULT_FREE_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].jejuShippingPrice").value(hasItem(DEFAULT_JEJU_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].difficultShippingPrice").value(hasItem(DEFAULT_DIFFICULT_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].refundShippingPrice").value(hasItem(DEFAULT_REFUND_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].exchangeShippingPrice").value(hasItem(DEFAULT_EXCHANGE_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductShipping() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get the productShipping
        restProductShippingMockMvc
            .perform(get(ENTITY_API_URL_ID, productShipping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productShipping.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.isGroup").value(DEFAULT_IS_GROUP.booleanValue()))
            .andExpect(jsonPath("$.defaultShippingPrice").value(DEFAULT_DEFAULT_SHIPPING_PRICE))
            .andExpect(jsonPath("$.freeShippingPrice").value(DEFAULT_FREE_SHIPPING_PRICE))
            .andExpect(jsonPath("$.jejuShippingPrice").value(DEFAULT_JEJU_SHIPPING_PRICE))
            .andExpect(jsonPath("$.difficultShippingPrice").value(DEFAULT_DIFFICULT_SHIPPING_PRICE))
            .andExpect(jsonPath("$.refundShippingPrice").value(DEFAULT_REFUND_SHIPPING_PRICE))
            .andExpect(jsonPath("$.exchangeShippingPrice").value(DEFAULT_EXCHANGE_SHIPPING_PRICE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductShippingsByIdFiltering() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        Long id = productShipping.getId();

        defaultProductShippingShouldBeFound("id.equals=" + id);
        defaultProductShippingShouldNotBeFound("id.notEquals=" + id);

        defaultProductShippingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductShippingShouldNotBeFound("id.greaterThan=" + id);

        defaultProductShippingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductShippingShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductShippingsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where name equals to DEFAULT_NAME
        defaultProductShippingShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productShippingList where name equals to UPDATED_NAME
        defaultProductShippingShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductShippingsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where name not equals to DEFAULT_NAME
        defaultProductShippingShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productShippingList where name not equals to UPDATED_NAME
        defaultProductShippingShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductShippingsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductShippingShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productShippingList where name equals to UPDATED_NAME
        defaultProductShippingShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductShippingsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where name is not null
        defaultProductShippingShouldBeFound("name.specified=true");

        // Get all the productShippingList where name is null
        defaultProductShippingShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByNameContainsSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where name contains DEFAULT_NAME
        defaultProductShippingShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productShippingList where name contains UPDATED_NAME
        defaultProductShippingShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductShippingsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where name does not contain DEFAULT_NAME
        defaultProductShippingShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productShippingList where name does not contain UPDATED_NAME
        defaultProductShippingShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductShippingsByIsGroupIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where isGroup equals to DEFAULT_IS_GROUP
        defaultProductShippingShouldBeFound("isGroup.equals=" + DEFAULT_IS_GROUP);

        // Get all the productShippingList where isGroup equals to UPDATED_IS_GROUP
        defaultProductShippingShouldNotBeFound("isGroup.equals=" + UPDATED_IS_GROUP);
    }

    @Test
    @Transactional
    void getAllProductShippingsByIsGroupIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where isGroup not equals to DEFAULT_IS_GROUP
        defaultProductShippingShouldNotBeFound("isGroup.notEquals=" + DEFAULT_IS_GROUP);

        // Get all the productShippingList where isGroup not equals to UPDATED_IS_GROUP
        defaultProductShippingShouldBeFound("isGroup.notEquals=" + UPDATED_IS_GROUP);
    }

    @Test
    @Transactional
    void getAllProductShippingsByIsGroupIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where isGroup in DEFAULT_IS_GROUP or UPDATED_IS_GROUP
        defaultProductShippingShouldBeFound("isGroup.in=" + DEFAULT_IS_GROUP + "," + UPDATED_IS_GROUP);

        // Get all the productShippingList where isGroup equals to UPDATED_IS_GROUP
        defaultProductShippingShouldNotBeFound("isGroup.in=" + UPDATED_IS_GROUP);
    }

    @Test
    @Transactional
    void getAllProductShippingsByIsGroupIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where isGroup is not null
        defaultProductShippingShouldBeFound("isGroup.specified=true");

        // Get all the productShippingList where isGroup is null
        defaultProductShippingShouldNotBeFound("isGroup.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByDefaultShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where defaultShippingPrice equals to DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("defaultShippingPrice.equals=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productShippingList where defaultShippingPrice equals to UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("defaultShippingPrice.equals=" + UPDATED_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDefaultShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where defaultShippingPrice not equals to DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("defaultShippingPrice.notEquals=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productShippingList where defaultShippingPrice not equals to UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("defaultShippingPrice.notEquals=" + UPDATED_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDefaultShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where defaultShippingPrice in DEFAULT_DEFAULT_SHIPPING_PRICE or UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound(
            "defaultShippingPrice.in=" + DEFAULT_DEFAULT_SHIPPING_PRICE + "," + UPDATED_DEFAULT_SHIPPING_PRICE
        );

        // Get all the productShippingList where defaultShippingPrice equals to UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("defaultShippingPrice.in=" + UPDATED_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDefaultShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where defaultShippingPrice is not null
        defaultProductShippingShouldBeFound("defaultShippingPrice.specified=true");

        // Get all the productShippingList where defaultShippingPrice is null
        defaultProductShippingShouldNotBeFound("defaultShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByDefaultShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where defaultShippingPrice is greater than or equal to DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("defaultShippingPrice.greaterThanOrEqual=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productShippingList where defaultShippingPrice is greater than or equal to UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("defaultShippingPrice.greaterThanOrEqual=" + UPDATED_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDefaultShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where defaultShippingPrice is less than or equal to DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("defaultShippingPrice.lessThanOrEqual=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productShippingList where defaultShippingPrice is less than or equal to SMALLER_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("defaultShippingPrice.lessThanOrEqual=" + SMALLER_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDefaultShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where defaultShippingPrice is less than DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("defaultShippingPrice.lessThan=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productShippingList where defaultShippingPrice is less than UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("defaultShippingPrice.lessThan=" + UPDATED_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDefaultShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where defaultShippingPrice is greater than DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("defaultShippingPrice.greaterThan=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productShippingList where defaultShippingPrice is greater than SMALLER_DEFAULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("defaultShippingPrice.greaterThan=" + SMALLER_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByFreeShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where freeShippingPrice equals to DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("freeShippingPrice.equals=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productShippingList where freeShippingPrice equals to UPDATED_FREE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("freeShippingPrice.equals=" + UPDATED_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByFreeShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where freeShippingPrice not equals to DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("freeShippingPrice.notEquals=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productShippingList where freeShippingPrice not equals to UPDATED_FREE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("freeShippingPrice.notEquals=" + UPDATED_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByFreeShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where freeShippingPrice in DEFAULT_FREE_SHIPPING_PRICE or UPDATED_FREE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("freeShippingPrice.in=" + DEFAULT_FREE_SHIPPING_PRICE + "," + UPDATED_FREE_SHIPPING_PRICE);

        // Get all the productShippingList where freeShippingPrice equals to UPDATED_FREE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("freeShippingPrice.in=" + UPDATED_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByFreeShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where freeShippingPrice is not null
        defaultProductShippingShouldBeFound("freeShippingPrice.specified=true");

        // Get all the productShippingList where freeShippingPrice is null
        defaultProductShippingShouldNotBeFound("freeShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByFreeShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where freeShippingPrice is greater than or equal to DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("freeShippingPrice.greaterThanOrEqual=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productShippingList where freeShippingPrice is greater than or equal to UPDATED_FREE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("freeShippingPrice.greaterThanOrEqual=" + UPDATED_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByFreeShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where freeShippingPrice is less than or equal to DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("freeShippingPrice.lessThanOrEqual=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productShippingList where freeShippingPrice is less than or equal to SMALLER_FREE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("freeShippingPrice.lessThanOrEqual=" + SMALLER_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByFreeShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where freeShippingPrice is less than DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("freeShippingPrice.lessThan=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productShippingList where freeShippingPrice is less than UPDATED_FREE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("freeShippingPrice.lessThan=" + UPDATED_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByFreeShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where freeShippingPrice is greater than DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("freeShippingPrice.greaterThan=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productShippingList where freeShippingPrice is greater than SMALLER_FREE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("freeShippingPrice.greaterThan=" + SMALLER_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByJejuShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where jejuShippingPrice equals to DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("jejuShippingPrice.equals=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productShippingList where jejuShippingPrice equals to UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("jejuShippingPrice.equals=" + UPDATED_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByJejuShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where jejuShippingPrice not equals to DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("jejuShippingPrice.notEquals=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productShippingList where jejuShippingPrice not equals to UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("jejuShippingPrice.notEquals=" + UPDATED_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByJejuShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where jejuShippingPrice in DEFAULT_JEJU_SHIPPING_PRICE or UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("jejuShippingPrice.in=" + DEFAULT_JEJU_SHIPPING_PRICE + "," + UPDATED_JEJU_SHIPPING_PRICE);

        // Get all the productShippingList where jejuShippingPrice equals to UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("jejuShippingPrice.in=" + UPDATED_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByJejuShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where jejuShippingPrice is not null
        defaultProductShippingShouldBeFound("jejuShippingPrice.specified=true");

        // Get all the productShippingList where jejuShippingPrice is null
        defaultProductShippingShouldNotBeFound("jejuShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByJejuShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where jejuShippingPrice is greater than or equal to DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("jejuShippingPrice.greaterThanOrEqual=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productShippingList where jejuShippingPrice is greater than or equal to UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("jejuShippingPrice.greaterThanOrEqual=" + UPDATED_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByJejuShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where jejuShippingPrice is less than or equal to DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("jejuShippingPrice.lessThanOrEqual=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productShippingList where jejuShippingPrice is less than or equal to SMALLER_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("jejuShippingPrice.lessThanOrEqual=" + SMALLER_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByJejuShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where jejuShippingPrice is less than DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("jejuShippingPrice.lessThan=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productShippingList where jejuShippingPrice is less than UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("jejuShippingPrice.lessThan=" + UPDATED_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByJejuShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where jejuShippingPrice is greater than DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("jejuShippingPrice.greaterThan=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productShippingList where jejuShippingPrice is greater than SMALLER_JEJU_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("jejuShippingPrice.greaterThan=" + SMALLER_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDifficultShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where difficultShippingPrice equals to DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("difficultShippingPrice.equals=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productShippingList where difficultShippingPrice equals to UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("difficultShippingPrice.equals=" + UPDATED_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDifficultShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where difficultShippingPrice not equals to DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("difficultShippingPrice.notEquals=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productShippingList where difficultShippingPrice not equals to UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("difficultShippingPrice.notEquals=" + UPDATED_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDifficultShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where difficultShippingPrice in DEFAULT_DIFFICULT_SHIPPING_PRICE or UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound(
            "difficultShippingPrice.in=" + DEFAULT_DIFFICULT_SHIPPING_PRICE + "," + UPDATED_DIFFICULT_SHIPPING_PRICE
        );

        // Get all the productShippingList where difficultShippingPrice equals to UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("difficultShippingPrice.in=" + UPDATED_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDifficultShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where difficultShippingPrice is not null
        defaultProductShippingShouldBeFound("difficultShippingPrice.specified=true");

        // Get all the productShippingList where difficultShippingPrice is null
        defaultProductShippingShouldNotBeFound("difficultShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByDifficultShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where difficultShippingPrice is greater than or equal to DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("difficultShippingPrice.greaterThanOrEqual=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productShippingList where difficultShippingPrice is greater than or equal to UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("difficultShippingPrice.greaterThanOrEqual=" + UPDATED_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDifficultShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where difficultShippingPrice is less than or equal to DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("difficultShippingPrice.lessThanOrEqual=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productShippingList where difficultShippingPrice is less than or equal to SMALLER_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("difficultShippingPrice.lessThanOrEqual=" + SMALLER_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDifficultShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where difficultShippingPrice is less than DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("difficultShippingPrice.lessThan=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productShippingList where difficultShippingPrice is less than UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("difficultShippingPrice.lessThan=" + UPDATED_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByDifficultShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where difficultShippingPrice is greater than DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("difficultShippingPrice.greaterThan=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productShippingList where difficultShippingPrice is greater than SMALLER_DIFFICULT_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("difficultShippingPrice.greaterThan=" + SMALLER_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByRefundShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where refundShippingPrice equals to DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("refundShippingPrice.equals=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productShippingList where refundShippingPrice equals to UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("refundShippingPrice.equals=" + UPDATED_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByRefundShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where refundShippingPrice not equals to DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("refundShippingPrice.notEquals=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productShippingList where refundShippingPrice not equals to UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("refundShippingPrice.notEquals=" + UPDATED_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByRefundShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where refundShippingPrice in DEFAULT_REFUND_SHIPPING_PRICE or UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldBeFound(
            "refundShippingPrice.in=" + DEFAULT_REFUND_SHIPPING_PRICE + "," + UPDATED_REFUND_SHIPPING_PRICE
        );

        // Get all the productShippingList where refundShippingPrice equals to UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("refundShippingPrice.in=" + UPDATED_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByRefundShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where refundShippingPrice is not null
        defaultProductShippingShouldBeFound("refundShippingPrice.specified=true");

        // Get all the productShippingList where refundShippingPrice is null
        defaultProductShippingShouldNotBeFound("refundShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByRefundShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where refundShippingPrice is greater than or equal to DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("refundShippingPrice.greaterThanOrEqual=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productShippingList where refundShippingPrice is greater than or equal to UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("refundShippingPrice.greaterThanOrEqual=" + UPDATED_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByRefundShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where refundShippingPrice is less than or equal to DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("refundShippingPrice.lessThanOrEqual=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productShippingList where refundShippingPrice is less than or equal to SMALLER_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("refundShippingPrice.lessThanOrEqual=" + SMALLER_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByRefundShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where refundShippingPrice is less than DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("refundShippingPrice.lessThan=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productShippingList where refundShippingPrice is less than UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("refundShippingPrice.lessThan=" + UPDATED_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByRefundShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where refundShippingPrice is greater than DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("refundShippingPrice.greaterThan=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productShippingList where refundShippingPrice is greater than SMALLER_REFUND_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("refundShippingPrice.greaterThan=" + SMALLER_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByExchangeShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where exchangeShippingPrice equals to DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("exchangeShippingPrice.equals=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productShippingList where exchangeShippingPrice equals to UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("exchangeShippingPrice.equals=" + UPDATED_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByExchangeShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where exchangeShippingPrice not equals to DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("exchangeShippingPrice.notEquals=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productShippingList where exchangeShippingPrice not equals to UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("exchangeShippingPrice.notEquals=" + UPDATED_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByExchangeShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where exchangeShippingPrice in DEFAULT_EXCHANGE_SHIPPING_PRICE or UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound(
            "exchangeShippingPrice.in=" + DEFAULT_EXCHANGE_SHIPPING_PRICE + "," + UPDATED_EXCHANGE_SHIPPING_PRICE
        );

        // Get all the productShippingList where exchangeShippingPrice equals to UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("exchangeShippingPrice.in=" + UPDATED_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByExchangeShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where exchangeShippingPrice is not null
        defaultProductShippingShouldBeFound("exchangeShippingPrice.specified=true");

        // Get all the productShippingList where exchangeShippingPrice is null
        defaultProductShippingShouldNotBeFound("exchangeShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByExchangeShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where exchangeShippingPrice is greater than or equal to DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("exchangeShippingPrice.greaterThanOrEqual=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productShippingList where exchangeShippingPrice is greater than or equal to UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("exchangeShippingPrice.greaterThanOrEqual=" + UPDATED_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByExchangeShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where exchangeShippingPrice is less than or equal to DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("exchangeShippingPrice.lessThanOrEqual=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productShippingList where exchangeShippingPrice is less than or equal to SMALLER_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("exchangeShippingPrice.lessThanOrEqual=" + SMALLER_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByExchangeShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where exchangeShippingPrice is less than DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("exchangeShippingPrice.lessThan=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productShippingList where exchangeShippingPrice is less than UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("exchangeShippingPrice.lessThan=" + UPDATED_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByExchangeShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where exchangeShippingPrice is greater than DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldNotBeFound("exchangeShippingPrice.greaterThan=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productShippingList where exchangeShippingPrice is greater than SMALLER_EXCHANGE_SHIPPING_PRICE
        defaultProductShippingShouldBeFound("exchangeShippingPrice.greaterThan=" + SMALLER_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where activated equals to DEFAULT_ACTIVATED
        defaultProductShippingShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productShippingList where activated equals to UPDATED_ACTIVATED
        defaultProductShippingShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductShippingsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where activated not equals to DEFAULT_ACTIVATED
        defaultProductShippingShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productShippingList where activated not equals to UPDATED_ACTIVATED
        defaultProductShippingShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductShippingsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductShippingShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productShippingList where activated equals to UPDATED_ACTIVATED
        defaultProductShippingShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductShippingsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where activated is not null
        defaultProductShippingShouldBeFound("activated.specified=true");

        // Get all the productShippingList where activated is null
        defaultProductShippingShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductShippingShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productShippingList where createdBy equals to UPDATED_CREATED_BY
        defaultProductShippingShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductShippingShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productShippingList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductShippingShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductShippingShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productShippingList where createdBy equals to UPDATED_CREATED_BY
        defaultProductShippingShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where createdBy is not null
        defaultProductShippingShouldBeFound("createdBy.specified=true");

        // Get all the productShippingList where createdBy is null
        defaultProductShippingShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where createdBy contains DEFAULT_CREATED_BY
        defaultProductShippingShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productShippingList where createdBy contains UPDATED_CREATED_BY
        defaultProductShippingShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductShippingShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productShippingList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductShippingShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductShippingShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productShippingList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductShippingShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductShippingShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productShippingList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductShippingShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductShippingShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productShippingList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductShippingShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where createdDate is not null
        defaultProductShippingShouldBeFound("createdDate.specified=true");

        // Get all the productShippingList where createdDate is null
        defaultProductShippingShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductShippingShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productShippingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductShippingShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductShippingShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productShippingList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductShippingShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductShippingShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productShippingList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductShippingShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where lastModifiedBy is not null
        defaultProductShippingShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productShippingList where lastModifiedBy is null
        defaultProductShippingShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductShippingShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productShippingList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductShippingShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductShippingShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productShippingList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductShippingShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductShippingsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductShippingShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productShippingList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductShippingShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductShippingShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productShippingList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductShippingShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductShippingShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productShippingList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductShippingShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductShippingsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        // Get all the productShippingList where lastModifiedDate is not null
        defaultProductShippingShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productShippingList where lastModifiedDate is null
        defaultProductShippingShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductShippingsByProductShippingRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);
        ProductShippingRel productShippingRel = ProductShippingRelResourceIT.createEntity(em);
        em.persist(productShippingRel);
        em.flush();
        productShipping.addProductShippingRel(productShippingRel);
        productShippingRepository.saveAndFlush(productShipping);
        Long productShippingRelId = productShippingRel.getId();

        // Get all the productShippingList where productShippingRel equals to productShippingRelId
        defaultProductShippingShouldBeFound("productShippingRelId.equals=" + productShippingRelId);

        // Get all the productShippingList where productShippingRel equals to (productShippingRelId + 1)
        defaultProductShippingShouldNotBeFound("productShippingRelId.equals=" + (productShippingRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductShippingShouldBeFound(String filter) throws Exception {
        restProductShippingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productShipping.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].isGroup").value(hasItem(DEFAULT_IS_GROUP.booleanValue())))
            .andExpect(jsonPath("$.[*].defaultShippingPrice").value(hasItem(DEFAULT_DEFAULT_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].freeShippingPrice").value(hasItem(DEFAULT_FREE_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].jejuShippingPrice").value(hasItem(DEFAULT_JEJU_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].difficultShippingPrice").value(hasItem(DEFAULT_DIFFICULT_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].refundShippingPrice").value(hasItem(DEFAULT_REFUND_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].exchangeShippingPrice").value(hasItem(DEFAULT_EXCHANGE_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductShippingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductShippingShouldNotBeFound(String filter) throws Exception {
        restProductShippingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductShippingMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductShipping() throws Exception {
        // Get the productShipping
        restProductShippingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductShipping() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        int databaseSizeBeforeUpdate = productShippingRepository.findAll().size();

        // Update the productShipping
        ProductShipping updatedProductShipping = productShippingRepository.findById(productShipping.getId()).get();
        // Disconnect from session so that the updates on updatedProductShipping are not directly saved in db
        em.detach(updatedProductShipping);
        updatedProductShipping
            .name(UPDATED_NAME)
            .isGroup(UPDATED_IS_GROUP)
            .defaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE)
            .freeShippingPrice(UPDATED_FREE_SHIPPING_PRICE)
            .jejuShippingPrice(UPDATED_JEJU_SHIPPING_PRICE)
            .difficultShippingPrice(UPDATED_DIFFICULT_SHIPPING_PRICE)
            .refundShippingPrice(UPDATED_REFUND_SHIPPING_PRICE)
            .exchangeShippingPrice(UPDATED_EXCHANGE_SHIPPING_PRICE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductShippingDTO productShippingDTO = productShippingMapper.toDto(updatedProductShipping);

        restProductShippingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productShippingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductShipping in the database
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeUpdate);
        ProductShipping testProductShipping = productShippingList.get(productShippingList.size() - 1);
        assertThat(testProductShipping.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductShipping.getIsGroup()).isEqualTo(UPDATED_IS_GROUP);
        assertThat(testProductShipping.getDefaultShippingPrice()).isEqualTo(UPDATED_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductShipping.getFreeShippingPrice()).isEqualTo(UPDATED_FREE_SHIPPING_PRICE);
        assertThat(testProductShipping.getJejuShippingPrice()).isEqualTo(UPDATED_JEJU_SHIPPING_PRICE);
        assertThat(testProductShipping.getDifficultShippingPrice()).isEqualTo(UPDATED_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProductShipping.getRefundShippingPrice()).isEqualTo(UPDATED_REFUND_SHIPPING_PRICE);
        assertThat(testProductShipping.getExchangeShippingPrice()).isEqualTo(UPDATED_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProductShipping.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductShipping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductShipping.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductShipping.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductShipping.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductShipping() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRepository.findAll().size();
        productShipping.setId(count.incrementAndGet());

        // Create the ProductShipping
        ProductShippingDTO productShippingDTO = productShippingMapper.toDto(productShipping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductShippingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productShippingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShipping in the database
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductShipping() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRepository.findAll().size();
        productShipping.setId(count.incrementAndGet());

        // Create the ProductShipping
        ProductShippingDTO productShippingDTO = productShippingMapper.toDto(productShipping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShipping in the database
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductShipping() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRepository.findAll().size();
        productShipping.setId(count.incrementAndGet());

        // Create the ProductShipping
        ProductShippingDTO productShippingDTO = productShippingMapper.toDto(productShipping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productShippingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductShipping in the database
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductShippingWithPatch() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        int databaseSizeBeforeUpdate = productShippingRepository.findAll().size();

        // Update the productShipping using partial update
        ProductShipping partialUpdatedProductShipping = new ProductShipping();
        partialUpdatedProductShipping.setId(productShipping.getId());

        partialUpdatedProductShipping
            .isGroup(UPDATED_IS_GROUP)
            .defaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductShippingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductShipping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductShipping))
            )
            .andExpect(status().isOk());

        // Validate the ProductShipping in the database
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeUpdate);
        ProductShipping testProductShipping = productShippingList.get(productShippingList.size() - 1);
        assertThat(testProductShipping.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductShipping.getIsGroup()).isEqualTo(UPDATED_IS_GROUP);
        assertThat(testProductShipping.getDefaultShippingPrice()).isEqualTo(UPDATED_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductShipping.getFreeShippingPrice()).isEqualTo(DEFAULT_FREE_SHIPPING_PRICE);
        assertThat(testProductShipping.getJejuShippingPrice()).isEqualTo(DEFAULT_JEJU_SHIPPING_PRICE);
        assertThat(testProductShipping.getDifficultShippingPrice()).isEqualTo(DEFAULT_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProductShipping.getRefundShippingPrice()).isEqualTo(DEFAULT_REFUND_SHIPPING_PRICE);
        assertThat(testProductShipping.getExchangeShippingPrice()).isEqualTo(DEFAULT_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProductShipping.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductShipping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductShipping.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductShipping.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductShipping.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductShippingWithPatch() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        int databaseSizeBeforeUpdate = productShippingRepository.findAll().size();

        // Update the productShipping using partial update
        ProductShipping partialUpdatedProductShipping = new ProductShipping();
        partialUpdatedProductShipping.setId(productShipping.getId());

        partialUpdatedProductShipping
            .name(UPDATED_NAME)
            .isGroup(UPDATED_IS_GROUP)
            .defaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE)
            .freeShippingPrice(UPDATED_FREE_SHIPPING_PRICE)
            .jejuShippingPrice(UPDATED_JEJU_SHIPPING_PRICE)
            .difficultShippingPrice(UPDATED_DIFFICULT_SHIPPING_PRICE)
            .refundShippingPrice(UPDATED_REFUND_SHIPPING_PRICE)
            .exchangeShippingPrice(UPDATED_EXCHANGE_SHIPPING_PRICE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductShippingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductShipping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductShipping))
            )
            .andExpect(status().isOk());

        // Validate the ProductShipping in the database
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeUpdate);
        ProductShipping testProductShipping = productShippingList.get(productShippingList.size() - 1);
        assertThat(testProductShipping.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductShipping.getIsGroup()).isEqualTo(UPDATED_IS_GROUP);
        assertThat(testProductShipping.getDefaultShippingPrice()).isEqualTo(UPDATED_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductShipping.getFreeShippingPrice()).isEqualTo(UPDATED_FREE_SHIPPING_PRICE);
        assertThat(testProductShipping.getJejuShippingPrice()).isEqualTo(UPDATED_JEJU_SHIPPING_PRICE);
        assertThat(testProductShipping.getDifficultShippingPrice()).isEqualTo(UPDATED_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProductShipping.getRefundShippingPrice()).isEqualTo(UPDATED_REFUND_SHIPPING_PRICE);
        assertThat(testProductShipping.getExchangeShippingPrice()).isEqualTo(UPDATED_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProductShipping.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductShipping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductShipping.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductShipping.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductShipping.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductShipping() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRepository.findAll().size();
        productShipping.setId(count.incrementAndGet());

        // Create the ProductShipping
        ProductShippingDTO productShippingDTO = productShippingMapper.toDto(productShipping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductShippingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productShippingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productShippingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShipping in the database
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductShipping() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRepository.findAll().size();
        productShipping.setId(count.incrementAndGet());

        // Create the ProductShipping
        ProductShippingDTO productShippingDTO = productShippingMapper.toDto(productShipping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productShippingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShipping in the database
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductShipping() throws Exception {
        int databaseSizeBeforeUpdate = productShippingRepository.findAll().size();
        productShipping.setId(count.incrementAndGet());

        // Create the ProductShipping
        ProductShippingDTO productShippingDTO = productShippingMapper.toDto(productShipping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productShippingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductShipping in the database
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductShipping() throws Exception {
        // Initialize the database
        productShippingRepository.saveAndFlush(productShipping);

        int databaseSizeBeforeDelete = productShippingRepository.findAll().size();

        // Delete the productShipping
        restProductShippingMockMvc
            .perform(delete(ENTITY_API_URL_ID, productShipping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductShipping> productShippingList = productShippingRepository.findAll();
        assertThat(productShippingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
