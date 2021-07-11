package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductOption;
import com.toy.project.domain.ProductOptionColorRel;
import com.toy.project.domain.ProductOptionDesignRel;
import com.toy.project.domain.ProductOptionPackageRel;
import com.toy.project.domain.ProductOptionRel;
import com.toy.project.repository.ProductOptionRepository;
import com.toy.project.service.criteria.ProductOptionCriteria;
import com.toy.project.service.dto.ProductOptionDTO;
import com.toy.project.service.mapper.ProductOptionMapper;
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
 * Integration tests for the {@link ProductOptionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductOptionResourceIT {

    private static final String DEFAULT_PRICE_SIGN = "AAAAAAAAAA";
    private static final String UPDATED_PRICE_SIGN = "BBBBBBBBBB";

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;
    private static final Integer SMALLER_PRICE = 1 - 1;

    private static final Integer DEFAULT_STOCK = 1;
    private static final Integer UPDATED_STOCK = 2;
    private static final Integer SMALLER_STOCK = 1 - 1;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductOptionMapper productOptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductOptionMockMvc;

    private ProductOption productOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOption createEntity(EntityManager em) {
        ProductOption productOption = new ProductOption()
            .priceSign(DEFAULT_PRICE_SIGN)
            .price(DEFAULT_PRICE)
            .stock(DEFAULT_STOCK)
            .status(DEFAULT_STATUS)
            .code(DEFAULT_CODE)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productOption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOption createUpdatedEntity(EntityManager em) {
        ProductOption productOption = new ProductOption()
            .priceSign(UPDATED_PRICE_SIGN)
            .price(UPDATED_PRICE)
            .stock(UPDATED_STOCK)
            .status(UPDATED_STATUS)
            .code(UPDATED_CODE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productOption;
    }

    @BeforeEach
    public void initTest() {
        productOption = createEntity(em);
    }

    @Test
    @Transactional
    void createProductOption() throws Exception {
        int databaseSizeBeforeCreate = productOptionRepository.findAll().size();
        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);
        restProductOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getPriceSign()).isEqualTo(DEFAULT_PRICE_SIGN);
        assertThat(testProductOption.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProductOption.getStock()).isEqualTo(DEFAULT_STOCK);
        assertThat(testProductOption.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductOption.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProductOption.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductOption.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductOption.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductOption.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductOption.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductOptionWithExistingId() throws Exception {
        // Create the ProductOption with an existing ID
        productOption.setId(1L);
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        int databaseSizeBeforeCreate = productOptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductOptions() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList
        restProductOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].priceSign").value(hasItem(DEFAULT_PRICE_SIGN)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get the productOption
        restProductOptionMockMvc
            .perform(get(ENTITY_API_URL_ID, productOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productOption.getId().intValue()))
            .andExpect(jsonPath("$.priceSign").value(DEFAULT_PRICE_SIGN))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.stock").value(DEFAULT_STOCK))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductOptionsByIdFiltering() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        Long id = productOption.getId();

        defaultProductOptionShouldBeFound("id.equals=" + id);
        defaultProductOptionShouldNotBeFound("id.notEquals=" + id);

        defaultProductOptionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductOptionShouldNotBeFound("id.greaterThan=" + id);

        defaultProductOptionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductOptionShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceSignIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where priceSign equals to DEFAULT_PRICE_SIGN
        defaultProductOptionShouldBeFound("priceSign.equals=" + DEFAULT_PRICE_SIGN);

        // Get all the productOptionList where priceSign equals to UPDATED_PRICE_SIGN
        defaultProductOptionShouldNotBeFound("priceSign.equals=" + UPDATED_PRICE_SIGN);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceSignIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where priceSign not equals to DEFAULT_PRICE_SIGN
        defaultProductOptionShouldNotBeFound("priceSign.notEquals=" + DEFAULT_PRICE_SIGN);

        // Get all the productOptionList where priceSign not equals to UPDATED_PRICE_SIGN
        defaultProductOptionShouldBeFound("priceSign.notEquals=" + UPDATED_PRICE_SIGN);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceSignIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where priceSign in DEFAULT_PRICE_SIGN or UPDATED_PRICE_SIGN
        defaultProductOptionShouldBeFound("priceSign.in=" + DEFAULT_PRICE_SIGN + "," + UPDATED_PRICE_SIGN);

        // Get all the productOptionList where priceSign equals to UPDATED_PRICE_SIGN
        defaultProductOptionShouldNotBeFound("priceSign.in=" + UPDATED_PRICE_SIGN);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceSignIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where priceSign is not null
        defaultProductOptionShouldBeFound("priceSign.specified=true");

        // Get all the productOptionList where priceSign is null
        defaultProductOptionShouldNotBeFound("priceSign.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceSignContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where priceSign contains DEFAULT_PRICE_SIGN
        defaultProductOptionShouldBeFound("priceSign.contains=" + DEFAULT_PRICE_SIGN);

        // Get all the productOptionList where priceSign contains UPDATED_PRICE_SIGN
        defaultProductOptionShouldNotBeFound("priceSign.contains=" + UPDATED_PRICE_SIGN);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceSignNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where priceSign does not contain DEFAULT_PRICE_SIGN
        defaultProductOptionShouldNotBeFound("priceSign.doesNotContain=" + DEFAULT_PRICE_SIGN);

        // Get all the productOptionList where priceSign does not contain UPDATED_PRICE_SIGN
        defaultProductOptionShouldBeFound("priceSign.doesNotContain=" + UPDATED_PRICE_SIGN);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where price equals to DEFAULT_PRICE
        defaultProductOptionShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the productOptionList where price equals to UPDATED_PRICE
        defaultProductOptionShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where price not equals to DEFAULT_PRICE
        defaultProductOptionShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the productOptionList where price not equals to UPDATED_PRICE
        defaultProductOptionShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultProductOptionShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the productOptionList where price equals to UPDATED_PRICE
        defaultProductOptionShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where price is not null
        defaultProductOptionShouldBeFound("price.specified=true");

        // Get all the productOptionList where price is null
        defaultProductOptionShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where price is greater than or equal to DEFAULT_PRICE
        defaultProductOptionShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productOptionList where price is greater than or equal to UPDATED_PRICE
        defaultProductOptionShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where price is less than or equal to DEFAULT_PRICE
        defaultProductOptionShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productOptionList where price is less than or equal to SMALLER_PRICE
        defaultProductOptionShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where price is less than DEFAULT_PRICE
        defaultProductOptionShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the productOptionList where price is less than UPDATED_PRICE
        defaultProductOptionShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where price is greater than DEFAULT_PRICE
        defaultProductOptionShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the productOptionList where price is greater than SMALLER_PRICE
        defaultProductOptionShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStockIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where stock equals to DEFAULT_STOCK
        defaultProductOptionShouldBeFound("stock.equals=" + DEFAULT_STOCK);

        // Get all the productOptionList where stock equals to UPDATED_STOCK
        defaultProductOptionShouldNotBeFound("stock.equals=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStockIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where stock not equals to DEFAULT_STOCK
        defaultProductOptionShouldNotBeFound("stock.notEquals=" + DEFAULT_STOCK);

        // Get all the productOptionList where stock not equals to UPDATED_STOCK
        defaultProductOptionShouldBeFound("stock.notEquals=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStockIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where stock in DEFAULT_STOCK or UPDATED_STOCK
        defaultProductOptionShouldBeFound("stock.in=" + DEFAULT_STOCK + "," + UPDATED_STOCK);

        // Get all the productOptionList where stock equals to UPDATED_STOCK
        defaultProductOptionShouldNotBeFound("stock.in=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStockIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where stock is not null
        defaultProductOptionShouldBeFound("stock.specified=true");

        // Get all the productOptionList where stock is null
        defaultProductOptionShouldNotBeFound("stock.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionsByStockIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where stock is greater than or equal to DEFAULT_STOCK
        defaultProductOptionShouldBeFound("stock.greaterThanOrEqual=" + DEFAULT_STOCK);

        // Get all the productOptionList where stock is greater than or equal to UPDATED_STOCK
        defaultProductOptionShouldNotBeFound("stock.greaterThanOrEqual=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStockIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where stock is less than or equal to DEFAULT_STOCK
        defaultProductOptionShouldBeFound("stock.lessThanOrEqual=" + DEFAULT_STOCK);

        // Get all the productOptionList where stock is less than or equal to SMALLER_STOCK
        defaultProductOptionShouldNotBeFound("stock.lessThanOrEqual=" + SMALLER_STOCK);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStockIsLessThanSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where stock is less than DEFAULT_STOCK
        defaultProductOptionShouldNotBeFound("stock.lessThan=" + DEFAULT_STOCK);

        // Get all the productOptionList where stock is less than UPDATED_STOCK
        defaultProductOptionShouldBeFound("stock.lessThan=" + UPDATED_STOCK);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStockIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where stock is greater than DEFAULT_STOCK
        defaultProductOptionShouldNotBeFound("stock.greaterThan=" + DEFAULT_STOCK);

        // Get all the productOptionList where stock is greater than SMALLER_STOCK
        defaultProductOptionShouldBeFound("stock.greaterThan=" + SMALLER_STOCK);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where status equals to DEFAULT_STATUS
        defaultProductOptionShouldBeFound("status.equals=" + DEFAULT_STATUS);

        // Get all the productOptionList where status equals to UPDATED_STATUS
        defaultProductOptionShouldNotBeFound("status.equals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStatusIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where status not equals to DEFAULT_STATUS
        defaultProductOptionShouldNotBeFound("status.notEquals=" + DEFAULT_STATUS);

        // Get all the productOptionList where status not equals to UPDATED_STATUS
        defaultProductOptionShouldBeFound("status.notEquals=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStatusIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where status in DEFAULT_STATUS or UPDATED_STATUS
        defaultProductOptionShouldBeFound("status.in=" + DEFAULT_STATUS + "," + UPDATED_STATUS);

        // Get all the productOptionList where status equals to UPDATED_STATUS
        defaultProductOptionShouldNotBeFound("status.in=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where status is not null
        defaultProductOptionShouldBeFound("status.specified=true");

        // Get all the productOptionList where status is null
        defaultProductOptionShouldNotBeFound("status.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionsByStatusContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where status contains DEFAULT_STATUS
        defaultProductOptionShouldBeFound("status.contains=" + DEFAULT_STATUS);

        // Get all the productOptionList where status contains UPDATED_STATUS
        defaultProductOptionShouldNotBeFound("status.contains=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllProductOptionsByStatusNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where status does not contain DEFAULT_STATUS
        defaultProductOptionShouldNotBeFound("status.doesNotContain=" + DEFAULT_STATUS);

        // Get all the productOptionList where status does not contain UPDATED_STATUS
        defaultProductOptionShouldBeFound("status.doesNotContain=" + UPDATED_STATUS);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where code equals to DEFAULT_CODE
        defaultProductOptionShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the productOptionList where code equals to UPDATED_CODE
        defaultProductOptionShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where code not equals to DEFAULT_CODE
        defaultProductOptionShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the productOptionList where code not equals to UPDATED_CODE
        defaultProductOptionShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProductOptionShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the productOptionList where code equals to UPDATED_CODE
        defaultProductOptionShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where code is not null
        defaultProductOptionShouldBeFound("code.specified=true");

        // Get all the productOptionList where code is null
        defaultProductOptionShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionsByCodeContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where code contains DEFAULT_CODE
        defaultProductOptionShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the productOptionList where code contains UPDATED_CODE
        defaultProductOptionShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where code does not contain DEFAULT_CODE
        defaultProductOptionShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the productOptionList where code does not contain UPDATED_CODE
        defaultProductOptionShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where activated equals to DEFAULT_ACTIVATED
        defaultProductOptionShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productOptionList where activated equals to UPDATED_ACTIVATED
        defaultProductOptionShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where activated not equals to DEFAULT_ACTIVATED
        defaultProductOptionShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productOptionList where activated not equals to UPDATED_ACTIVATED
        defaultProductOptionShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductOptionShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productOptionList where activated equals to UPDATED_ACTIVATED
        defaultProductOptionShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductOptionsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where activated is not null
        defaultProductOptionShouldBeFound("activated.specified=true");

        // Get all the productOptionList where activated is null
        defaultProductOptionShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductOptionShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productOptionList where createdBy equals to UPDATED_CREATED_BY
        defaultProductOptionShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductOptionShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productOptionList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductOptionShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductOptionShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productOptionList where createdBy equals to UPDATED_CREATED_BY
        defaultProductOptionShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where createdBy is not null
        defaultProductOptionShouldBeFound("createdBy.specified=true");

        // Get all the productOptionList where createdBy is null
        defaultProductOptionShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where createdBy contains DEFAULT_CREATED_BY
        defaultProductOptionShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productOptionList where createdBy contains UPDATED_CREATED_BY
        defaultProductOptionShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductOptionShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productOptionList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductOptionShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductOptionShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productOptionList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductOptionShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductOptionShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productOptionList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductOptionShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductOptionShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productOptionList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductOptionShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where createdDate is not null
        defaultProductOptionShouldBeFound("createdDate.specified=true");

        // Get all the productOptionList where createdDate is null
        defaultProductOptionShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductOptionShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productOptionList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductOptionShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where lastModifiedBy is not null
        defaultProductOptionShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productOptionList where lastModifiedBy is null
        defaultProductOptionShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductOptionShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductOptionShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productOptionList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductOptionShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductOptionsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductOptionShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productOptionList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductOptionShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productOptionList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productOptionList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductOptionShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductOptionsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList where lastModifiedDate is not null
        defaultProductOptionShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productOptionList where lastModifiedDate is null
        defaultProductOptionShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductOptionsByProductOptionRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);
        ProductOptionRel productOptionRel = ProductOptionRelResourceIT.createEntity(em);
        em.persist(productOptionRel);
        em.flush();
        productOptionRepository.saveAndFlush(productOption);
        Long productOptionRelId = productOptionRel.getId();

        // Get all the productOptionList where productOptionRel equals to productOptionRelId
        defaultProductOptionShouldBeFound("productOptionRelId.equals=" + productOptionRelId);

        // Get all the productOptionList where productOptionRel equals to (productOptionRelId + 1)
        defaultProductOptionShouldNotBeFound("productOptionRelId.equals=" + (productOptionRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductOptionsByProductOptionPackageRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);
        ProductOptionPackageRel productOptionPackageRel = ProductOptionPackageRelResourceIT.createEntity(em);
        em.persist(productOptionPackageRel);
        em.flush();
        productOptionRepository.saveAndFlush(productOption);
        Long productOptionPackageRelId = productOptionPackageRel.getId();

        // Get all the productOptionList where productOptionPackageRel equals to productOptionPackageRelId
        defaultProductOptionShouldBeFound("productOptionPackageRelId.equals=" + productOptionPackageRelId);

        // Get all the productOptionList where productOptionPackageRel equals to (productOptionPackageRelId + 1)
        defaultProductOptionShouldNotBeFound("productOptionPackageRelId.equals=" + (productOptionPackageRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductOptionsByProductOptionColorRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);
        ProductOptionColorRel productOptionColorRel = ProductOptionColorRelResourceIT.createEntity(em);
        em.persist(productOptionColorRel);
        em.flush();
        productOptionRepository.saveAndFlush(productOption);
        Long productOptionColorRelId = productOptionColorRel.getId();

        // Get all the productOptionList where productOptionColorRel equals to productOptionColorRelId
        defaultProductOptionShouldBeFound("productOptionColorRelId.equals=" + productOptionColorRelId);

        // Get all the productOptionList where productOptionColorRel equals to (productOptionColorRelId + 1)
        defaultProductOptionShouldNotBeFound("productOptionColorRelId.equals=" + (productOptionColorRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductOptionsByProductOptionDesignRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);
        ProductOptionDesignRel productOptionDesignRel = ProductOptionDesignRelResourceIT.createEntity(em);
        em.persist(productOptionDesignRel);
        em.flush();
        productOptionRepository.saveAndFlush(productOption);
        Long productOptionDesignRelId = productOptionDesignRel.getId();

        // Get all the productOptionList where productOptionDesignRel equals to productOptionDesignRelId
        defaultProductOptionShouldBeFound("productOptionDesignRelId.equals=" + productOptionDesignRelId);

        // Get all the productOptionList where productOptionDesignRel equals to (productOptionDesignRelId + 1)
        defaultProductOptionShouldNotBeFound("productOptionDesignRelId.equals=" + (productOptionDesignRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductOptionShouldBeFound(String filter) throws Exception {
        restProductOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].priceSign").value(hasItem(DEFAULT_PRICE_SIGN)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].stock").value(hasItem(DEFAULT_STOCK)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductOptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductOptionShouldNotBeFound(String filter) throws Exception {
        restProductOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductOptionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductOption() throws Exception {
        // Get the productOption
        restProductOptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Update the productOption
        ProductOption updatedProductOption = productOptionRepository.findById(productOption.getId()).get();
        // Disconnect from session so that the updates on updatedProductOption are not directly saved in db
        em.detach(updatedProductOption);
        updatedProductOption
            .priceSign(UPDATED_PRICE_SIGN)
            .price(UPDATED_PRICE)
            .stock(UPDATED_STOCK)
            .status(UPDATED_STATUS)
            .code(UPDATED_CODE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(updatedProductOption);

        restProductOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getPriceSign()).isEqualTo(UPDATED_PRICE_SIGN);
        assertThat(testProductOption.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductOption.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testProductOption.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductOption.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductOption.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductOptionWithPatch() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Update the productOption using partial update
        ProductOption partialUpdatedProductOption = new ProductOption();
        partialUpdatedProductOption.setId(productOption.getId());

        partialUpdatedProductOption
            .price(UPDATED_PRICE)
            .stock(UPDATED_STOCK)
            .code(UPDATED_CODE)
            .activated(UPDATED_ACTIVATED)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOption))
            )
            .andExpect(status().isOk());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getPriceSign()).isEqualTo(DEFAULT_PRICE_SIGN);
        assertThat(testProductOption.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductOption.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testProductOption.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductOption.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductOption.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOption.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductOptionWithPatch() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Update the productOption using partial update
        ProductOption partialUpdatedProductOption = new ProductOption();
        partialUpdatedProductOption.setId(productOption.getId());

        partialUpdatedProductOption
            .priceSign(UPDATED_PRICE_SIGN)
            .price(UPDATED_PRICE)
            .stock(UPDATED_STOCK)
            .status(UPDATED_STATUS)
            .code(UPDATED_CODE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOption))
            )
            .andExpect(status().isOk());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getPriceSign()).isEqualTo(UPDATED_PRICE_SIGN);
        assertThat(testProductOption.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductOption.getStock()).isEqualTo(UPDATED_STOCK);
        assertThat(testProductOption.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductOption.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProductOption.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productOptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeDelete = productOptionRepository.findAll().size();

        // Delete the productOption
        restProductOptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, productOption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
