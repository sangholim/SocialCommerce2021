package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.domain.enumeration.DifficultType;
import com.toy.project.domain.enumeration.ProductType;
import com.toy.project.repository.ProductRepository;
import com.toy.project.service.criteria.ProductCriteria;
import com.toy.project.service.dto.ProductDTO;
import com.toy.project.service.mapper.ProductMapper;
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
 * Integration tests for the {@link ProductResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final DifficultType DEFAULT_DIFFICULTY = DifficultType.VERYEASY;
    private static final DifficultType UPDATED_DIFFICULTY = DifficultType.EASY;

    private static final String DEFAULT_THUMBNAIL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL = "BBBBBBBBBB";

    private static final String DEFAULT_OWNER = "AAAAAAAAAA";
    private static final String UPDATED_OWNER = "BBBBBBBBBB";

    private static final Instant DEFAULT_REGDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PRICE_REGULAR = 1;
    private static final Integer UPDATED_PRICE_REGULAR = 2;
    private static final Integer SMALLER_PRICE_REGULAR = 1 - 1;

    private static final Integer DEFAULT_IS_USE_DISCOUNT = 1;
    private static final Integer UPDATED_IS_USE_DISCOUNT = 2;
    private static final Integer SMALLER_IS_USE_DISCOUNT = 1 - 1;

    private static final String DEFAULT_DISCOUNT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_UNIT = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISCOUNT_VALUE = 1;
    private static final Integer UPDATED_DISCOUNT_VALUE = 2;
    private static final Integer SMALLER_DISCOUNT_VALUE = 1 - 1;

    private static final Instant DEFAULT_DISCOUNT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISCOUNT_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_DISCOUNT_INTERVAL = 1;
    private static final Integer UPDATED_DISCOUNT_INTERVAL = 2;
    private static final Integer SMALLER_DISCOUNT_INTERVAL = 1 - 1;

    private static final String DEFAULT_VIDEO = "AAAAAAAAAA";
    private static final String UPDATED_VIDEO = "BBBBBBBBBB";

    private static final Instant DEFAULT_STARTDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_STARTDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_PREPARE_RESOURCE = "AAAAAAAAAA";
    private static final String UPDATED_PREPARE_RESOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_INTRODUCE_RESOURCE = "AAAAAAAAAA";
    private static final String UPDATED_INTRODUCE_RESOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_RESOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_RESOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_REFUND_RESOURCE = "AAAAAAAAAA";
    private static final String UPDATED_REFUND_RESOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_CHANGE_RESOURCE = "AAAAAAAAAA";
    private static final String UPDATED_CHANGE_RESOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Integer DEFAULT_INSTALLMENT_MONTH = 1;
    private static final Integer UPDATED_INSTALLMENT_MONTH = 2;
    private static final Integer SMALLER_INSTALLMENT_MONTH = 1 - 1;

    private static final ProductType DEFAULT_TYPE = ProductType.CLASS;
    private static final ProductType UPDATED_TYPE = ProductType.CLASS_VIDEO;

    private static final String DEFAULT_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER = "BBBBBBBBBB";

    private static final Integer DEFAULT_POPULAR_COUNT = 1;
    private static final Integer UPDATED_POPULAR_COUNT = 2;
    private static final Integer SMALLER_POPULAR_COUNT = 1 - 1;

    private static final Integer DEFAULT_REVIEW_COUNT = 1;
    private static final Integer UPDATED_REVIEW_COUNT = 2;
    private static final Integer SMALLER_REVIEW_COUNT = 1 - 1;

    private static final String ENTITY_API_URL = "/api/products";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMockMvc;

    private Product product;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createEntity(EntityManager em) {
        Product product = new Product()
            .name(DEFAULT_NAME)
            .difficulty(DEFAULT_DIFFICULTY)
            .thumbnail(DEFAULT_THUMBNAIL)
            .owner(DEFAULT_OWNER)
            .regdate(DEFAULT_REGDATE)
            .priceRegular(DEFAULT_PRICE_REGULAR)
            .isUseDiscount(DEFAULT_IS_USE_DISCOUNT)
            .discountUnit(DEFAULT_DISCOUNT_UNIT)
            .discountValue(DEFAULT_DISCOUNT_VALUE)
            .discountStartdate(DEFAULT_DISCOUNT_STARTDATE)
            .discountInterval(DEFAULT_DISCOUNT_INTERVAL)
            .video(DEFAULT_VIDEO)
            .startdate(DEFAULT_STARTDATE)
            .prepareResource(DEFAULT_PREPARE_RESOURCE)
            .introduceResource(DEFAULT_INTRODUCE_RESOURCE)
            .shippingResource(DEFAULT_SHIPPING_RESOURCE)
            .refundResource(DEFAULT_REFUND_RESOURCE)
            .changeResource(DEFAULT_CHANGE_RESOURCE)
            .code(DEFAULT_CODE)
            .installmentMonth(DEFAULT_INSTALLMENT_MONTH)
            .type(DEFAULT_TYPE)
            .number(DEFAULT_NUMBER)
            .popularCount(DEFAULT_POPULAR_COUNT)
            .reviewCount(DEFAULT_REVIEW_COUNT);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product()
            .name(UPDATED_NAME)
            .difficulty(UPDATED_DIFFICULTY)
            .thumbnail(UPDATED_THUMBNAIL)
            .owner(UPDATED_OWNER)
            .regdate(UPDATED_REGDATE)
            .priceRegular(UPDATED_PRICE_REGULAR)
            .isUseDiscount(UPDATED_IS_USE_DISCOUNT)
            .discountUnit(UPDATED_DISCOUNT_UNIT)
            .discountValue(UPDATED_DISCOUNT_VALUE)
            .discountStartdate(UPDATED_DISCOUNT_STARTDATE)
            .discountInterval(UPDATED_DISCOUNT_INTERVAL)
            .video(UPDATED_VIDEO)
            .startdate(UPDATED_STARTDATE)
            .prepareResource(UPDATED_PREPARE_RESOURCE)
            .introduceResource(UPDATED_INTRODUCE_RESOURCE)
            .shippingResource(UPDATED_SHIPPING_RESOURCE)
            .refundResource(UPDATED_REFUND_RESOURCE)
            .changeResource(UPDATED_CHANGE_RESOURCE)
            .code(UPDATED_CODE)
            .installmentMonth(UPDATED_INSTALLMENT_MONTH)
            .type(UPDATED_TYPE)
            .number(UPDATED_NUMBER)
            .popularCount(UPDATED_POPULAR_COUNT)
            .reviewCount(UPDATED_REVIEW_COUNT);
        return product;
    }

    @BeforeEach
    public void initTest() {
        product = createEntity(em);
    }

    @Test
    @Transactional
    void createProduct() throws Exception {
        int databaseSizeBeforeCreate = productRepository.findAll().size();
        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isCreated());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate + 1);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testProduct.getThumbnail()).isEqualTo(DEFAULT_THUMBNAIL);
        assertThat(testProduct.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testProduct.getRegdate()).isEqualTo(DEFAULT_REGDATE);
        assertThat(testProduct.getPriceRegular()).isEqualTo(DEFAULT_PRICE_REGULAR);
        assertThat(testProduct.getIsUseDiscount()).isEqualTo(DEFAULT_IS_USE_DISCOUNT);
        assertThat(testProduct.getDiscountUnit()).isEqualTo(DEFAULT_DISCOUNT_UNIT);
        assertThat(testProduct.getDiscountValue()).isEqualTo(DEFAULT_DISCOUNT_VALUE);
        assertThat(testProduct.getDiscountStartdate()).isEqualTo(DEFAULT_DISCOUNT_STARTDATE);
        assertThat(testProduct.getDiscountInterval()).isEqualTo(DEFAULT_DISCOUNT_INTERVAL);
        assertThat(testProduct.getVideo()).isEqualTo(DEFAULT_VIDEO);
        assertThat(testProduct.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testProduct.getPrepareResource()).isEqualTo(DEFAULT_PREPARE_RESOURCE);
        assertThat(testProduct.getIntroduceResource()).isEqualTo(DEFAULT_INTRODUCE_RESOURCE);
        assertThat(testProduct.getShippingResource()).isEqualTo(DEFAULT_SHIPPING_RESOURCE);
        assertThat(testProduct.getRefundResource()).isEqualTo(DEFAULT_REFUND_RESOURCE);
        assertThat(testProduct.getChangeResource()).isEqualTo(DEFAULT_CHANGE_RESOURCE);
        assertThat(testProduct.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProduct.getInstallmentMonth()).isEqualTo(DEFAULT_INSTALLMENT_MONTH);
        assertThat(testProduct.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProduct.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testProduct.getPopularCount()).isEqualTo(DEFAULT_POPULAR_COUNT);
        assertThat(testProduct.getReviewCount()).isEqualTo(DEFAULT_REVIEW_COUNT);
    }

    @Test
    @Transactional
    void createProductWithExistingId() throws Exception {
        // Create the Product with an existing ID
        product.setId(1L);
        ProductDTO productDTO = productMapper.toDto(product);

        int databaseSizeBeforeCreate = productRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProducts() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.toString())))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].regdate").value(hasItem(DEFAULT_REGDATE.toString())))
            .andExpect(jsonPath("$.[*].priceRegular").value(hasItem(DEFAULT_PRICE_REGULAR)))
            .andExpect(jsonPath("$.[*].isUseDiscount").value(hasItem(DEFAULT_IS_USE_DISCOUNT)))
            .andExpect(jsonPath("$.[*].discountUnit").value(hasItem(DEFAULT_DISCOUNT_UNIT)))
            .andExpect(jsonPath("$.[*].discountValue").value(hasItem(DEFAULT_DISCOUNT_VALUE)))
            .andExpect(jsonPath("$.[*].discountStartdate").value(hasItem(DEFAULT_DISCOUNT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].discountInterval").value(hasItem(DEFAULT_DISCOUNT_INTERVAL)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].prepareResource").value(hasItem(DEFAULT_PREPARE_RESOURCE)))
            .andExpect(jsonPath("$.[*].introduceResource").value(hasItem(DEFAULT_INTRODUCE_RESOURCE)))
            .andExpect(jsonPath("$.[*].shippingResource").value(hasItem(DEFAULT_SHIPPING_RESOURCE)))
            .andExpect(jsonPath("$.[*].refundResource").value(hasItem(DEFAULT_REFUND_RESOURCE)))
            .andExpect(jsonPath("$.[*].changeResource").value(hasItem(DEFAULT_CHANGE_RESOURCE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].installmentMonth").value(hasItem(DEFAULT_INSTALLMENT_MONTH)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].popularCount").value(hasItem(DEFAULT_POPULAR_COUNT)))
            .andExpect(jsonPath("$.[*].reviewCount").value(hasItem(DEFAULT_REVIEW_COUNT)));
    }

    @Test
    @Transactional
    void getProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get the product
        restProductMockMvc
            .perform(get(ENTITY_API_URL_ID, product.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(product.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.difficulty").value(DEFAULT_DIFFICULTY.toString()))
            .andExpect(jsonPath("$.thumbnail").value(DEFAULT_THUMBNAIL))
            .andExpect(jsonPath("$.owner").value(DEFAULT_OWNER))
            .andExpect(jsonPath("$.regdate").value(DEFAULT_REGDATE.toString()))
            .andExpect(jsonPath("$.priceRegular").value(DEFAULT_PRICE_REGULAR))
            .andExpect(jsonPath("$.isUseDiscount").value(DEFAULT_IS_USE_DISCOUNT))
            .andExpect(jsonPath("$.discountUnit").value(DEFAULT_DISCOUNT_UNIT))
            .andExpect(jsonPath("$.discountValue").value(DEFAULT_DISCOUNT_VALUE))
            .andExpect(jsonPath("$.discountStartdate").value(DEFAULT_DISCOUNT_STARTDATE.toString()))
            .andExpect(jsonPath("$.discountInterval").value(DEFAULT_DISCOUNT_INTERVAL))
            .andExpect(jsonPath("$.video").value(DEFAULT_VIDEO))
            .andExpect(jsonPath("$.startdate").value(DEFAULT_STARTDATE.toString()))
            .andExpect(jsonPath("$.prepareResource").value(DEFAULT_PREPARE_RESOURCE))
            .andExpect(jsonPath("$.introduceResource").value(DEFAULT_INTRODUCE_RESOURCE))
            .andExpect(jsonPath("$.shippingResource").value(DEFAULT_SHIPPING_RESOURCE))
            .andExpect(jsonPath("$.refundResource").value(DEFAULT_REFUND_RESOURCE))
            .andExpect(jsonPath("$.changeResource").value(DEFAULT_CHANGE_RESOURCE))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.installmentMonth").value(DEFAULT_INSTALLMENT_MONTH))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.number").value(DEFAULT_NUMBER))
            .andExpect(jsonPath("$.popularCount").value(DEFAULT_POPULAR_COUNT))
            .andExpect(jsonPath("$.reviewCount").value(DEFAULT_REVIEW_COUNT));
    }

    @Test
    @Transactional
    void getProductsByIdFiltering() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        Long id = product.getId();

        defaultProductShouldBeFound("id.equals=" + id);
        defaultProductShouldNotBeFound("id.notEquals=" + id);

        defaultProductShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.greaterThan=" + id);

        defaultProductShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name equals to DEFAULT_NAME
        defaultProductShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productList where name equals to UPDATED_NAME
        defaultProductShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name not equals to DEFAULT_NAME
        defaultProductShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productList where name not equals to UPDATED_NAME
        defaultProductShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productList where name equals to UPDATED_NAME
        defaultProductShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name is not null
        defaultProductShouldBeFound("name.specified=true");

        // Get all the productList where name is null
        defaultProductShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByNameContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name contains DEFAULT_NAME
        defaultProductShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productList where name contains UPDATED_NAME
        defaultProductShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where name does not contain DEFAULT_NAME
        defaultProductShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productList where name does not contain UPDATED_NAME
        defaultProductShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductsByDifficultyIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficulty equals to DEFAULT_DIFFICULTY
        defaultProductShouldBeFound("difficulty.equals=" + DEFAULT_DIFFICULTY);

        // Get all the productList where difficulty equals to UPDATED_DIFFICULTY
        defaultProductShouldNotBeFound("difficulty.equals=" + UPDATED_DIFFICULTY);
    }

    @Test
    @Transactional
    void getAllProductsByDifficultyIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficulty not equals to DEFAULT_DIFFICULTY
        defaultProductShouldNotBeFound("difficulty.notEquals=" + DEFAULT_DIFFICULTY);

        // Get all the productList where difficulty not equals to UPDATED_DIFFICULTY
        defaultProductShouldBeFound("difficulty.notEquals=" + UPDATED_DIFFICULTY);
    }

    @Test
    @Transactional
    void getAllProductsByDifficultyIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficulty in DEFAULT_DIFFICULTY or UPDATED_DIFFICULTY
        defaultProductShouldBeFound("difficulty.in=" + DEFAULT_DIFFICULTY + "," + UPDATED_DIFFICULTY);

        // Get all the productList where difficulty equals to UPDATED_DIFFICULTY
        defaultProductShouldNotBeFound("difficulty.in=" + UPDATED_DIFFICULTY);
    }

    @Test
    @Transactional
    void getAllProductsByDifficultyIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficulty is not null
        defaultProductShouldBeFound("difficulty.specified=true");

        // Get all the productList where difficulty is null
        defaultProductShouldNotBeFound("difficulty.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByThumbnailIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where thumbnail equals to DEFAULT_THUMBNAIL
        defaultProductShouldBeFound("thumbnail.equals=" + DEFAULT_THUMBNAIL);

        // Get all the productList where thumbnail equals to UPDATED_THUMBNAIL
        defaultProductShouldNotBeFound("thumbnail.equals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllProductsByThumbnailIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where thumbnail not equals to DEFAULT_THUMBNAIL
        defaultProductShouldNotBeFound("thumbnail.notEquals=" + DEFAULT_THUMBNAIL);

        // Get all the productList where thumbnail not equals to UPDATED_THUMBNAIL
        defaultProductShouldBeFound("thumbnail.notEquals=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllProductsByThumbnailIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where thumbnail in DEFAULT_THUMBNAIL or UPDATED_THUMBNAIL
        defaultProductShouldBeFound("thumbnail.in=" + DEFAULT_THUMBNAIL + "," + UPDATED_THUMBNAIL);

        // Get all the productList where thumbnail equals to UPDATED_THUMBNAIL
        defaultProductShouldNotBeFound("thumbnail.in=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllProductsByThumbnailIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where thumbnail is not null
        defaultProductShouldBeFound("thumbnail.specified=true");

        // Get all the productList where thumbnail is null
        defaultProductShouldNotBeFound("thumbnail.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByThumbnailContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where thumbnail contains DEFAULT_THUMBNAIL
        defaultProductShouldBeFound("thumbnail.contains=" + DEFAULT_THUMBNAIL);

        // Get all the productList where thumbnail contains UPDATED_THUMBNAIL
        defaultProductShouldNotBeFound("thumbnail.contains=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllProductsByThumbnailNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where thumbnail does not contain DEFAULT_THUMBNAIL
        defaultProductShouldNotBeFound("thumbnail.doesNotContain=" + DEFAULT_THUMBNAIL);

        // Get all the productList where thumbnail does not contain UPDATED_THUMBNAIL
        defaultProductShouldBeFound("thumbnail.doesNotContain=" + UPDATED_THUMBNAIL);
    }

    @Test
    @Transactional
    void getAllProductsByOwnerIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where owner equals to DEFAULT_OWNER
        defaultProductShouldBeFound("owner.equals=" + DEFAULT_OWNER);

        // Get all the productList where owner equals to UPDATED_OWNER
        defaultProductShouldNotBeFound("owner.equals=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    void getAllProductsByOwnerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where owner not equals to DEFAULT_OWNER
        defaultProductShouldNotBeFound("owner.notEquals=" + DEFAULT_OWNER);

        // Get all the productList where owner not equals to UPDATED_OWNER
        defaultProductShouldBeFound("owner.notEquals=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    void getAllProductsByOwnerIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where owner in DEFAULT_OWNER or UPDATED_OWNER
        defaultProductShouldBeFound("owner.in=" + DEFAULT_OWNER + "," + UPDATED_OWNER);

        // Get all the productList where owner equals to UPDATED_OWNER
        defaultProductShouldNotBeFound("owner.in=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    void getAllProductsByOwnerIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where owner is not null
        defaultProductShouldBeFound("owner.specified=true");

        // Get all the productList where owner is null
        defaultProductShouldNotBeFound("owner.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByOwnerContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where owner contains DEFAULT_OWNER
        defaultProductShouldBeFound("owner.contains=" + DEFAULT_OWNER);

        // Get all the productList where owner contains UPDATED_OWNER
        defaultProductShouldNotBeFound("owner.contains=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    void getAllProductsByOwnerNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where owner does not contain DEFAULT_OWNER
        defaultProductShouldNotBeFound("owner.doesNotContain=" + DEFAULT_OWNER);

        // Get all the productList where owner does not contain UPDATED_OWNER
        defaultProductShouldBeFound("owner.doesNotContain=" + UPDATED_OWNER);
    }

    @Test
    @Transactional
    void getAllProductsByRegdateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where regdate equals to DEFAULT_REGDATE
        defaultProductShouldBeFound("regdate.equals=" + DEFAULT_REGDATE);

        // Get all the productList where regdate equals to UPDATED_REGDATE
        defaultProductShouldNotBeFound("regdate.equals=" + UPDATED_REGDATE);
    }

    @Test
    @Transactional
    void getAllProductsByRegdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where regdate not equals to DEFAULT_REGDATE
        defaultProductShouldNotBeFound("regdate.notEquals=" + DEFAULT_REGDATE);

        // Get all the productList where regdate not equals to UPDATED_REGDATE
        defaultProductShouldBeFound("regdate.notEquals=" + UPDATED_REGDATE);
    }

    @Test
    @Transactional
    void getAllProductsByRegdateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where regdate in DEFAULT_REGDATE or UPDATED_REGDATE
        defaultProductShouldBeFound("regdate.in=" + DEFAULT_REGDATE + "," + UPDATED_REGDATE);

        // Get all the productList where regdate equals to UPDATED_REGDATE
        defaultProductShouldNotBeFound("regdate.in=" + UPDATED_REGDATE);
    }

    @Test
    @Transactional
    void getAllProductsByRegdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where regdate is not null
        defaultProductShouldBeFound("regdate.specified=true");

        // Get all the productList where regdate is null
        defaultProductShouldNotBeFound("regdate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByPriceRegularIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where priceRegular equals to DEFAULT_PRICE_REGULAR
        defaultProductShouldBeFound("priceRegular.equals=" + DEFAULT_PRICE_REGULAR);

        // Get all the productList where priceRegular equals to UPDATED_PRICE_REGULAR
        defaultProductShouldNotBeFound("priceRegular.equals=" + UPDATED_PRICE_REGULAR);
    }

    @Test
    @Transactional
    void getAllProductsByPriceRegularIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where priceRegular not equals to DEFAULT_PRICE_REGULAR
        defaultProductShouldNotBeFound("priceRegular.notEquals=" + DEFAULT_PRICE_REGULAR);

        // Get all the productList where priceRegular not equals to UPDATED_PRICE_REGULAR
        defaultProductShouldBeFound("priceRegular.notEquals=" + UPDATED_PRICE_REGULAR);
    }

    @Test
    @Transactional
    void getAllProductsByPriceRegularIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where priceRegular in DEFAULT_PRICE_REGULAR or UPDATED_PRICE_REGULAR
        defaultProductShouldBeFound("priceRegular.in=" + DEFAULT_PRICE_REGULAR + "," + UPDATED_PRICE_REGULAR);

        // Get all the productList where priceRegular equals to UPDATED_PRICE_REGULAR
        defaultProductShouldNotBeFound("priceRegular.in=" + UPDATED_PRICE_REGULAR);
    }

    @Test
    @Transactional
    void getAllProductsByPriceRegularIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where priceRegular is not null
        defaultProductShouldBeFound("priceRegular.specified=true");

        // Get all the productList where priceRegular is null
        defaultProductShouldNotBeFound("priceRegular.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByPriceRegularIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where priceRegular is greater than or equal to DEFAULT_PRICE_REGULAR
        defaultProductShouldBeFound("priceRegular.greaterThanOrEqual=" + DEFAULT_PRICE_REGULAR);

        // Get all the productList where priceRegular is greater than or equal to UPDATED_PRICE_REGULAR
        defaultProductShouldNotBeFound("priceRegular.greaterThanOrEqual=" + UPDATED_PRICE_REGULAR);
    }

    @Test
    @Transactional
    void getAllProductsByPriceRegularIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where priceRegular is less than or equal to DEFAULT_PRICE_REGULAR
        defaultProductShouldBeFound("priceRegular.lessThanOrEqual=" + DEFAULT_PRICE_REGULAR);

        // Get all the productList where priceRegular is less than or equal to SMALLER_PRICE_REGULAR
        defaultProductShouldNotBeFound("priceRegular.lessThanOrEqual=" + SMALLER_PRICE_REGULAR);
    }

    @Test
    @Transactional
    void getAllProductsByPriceRegularIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where priceRegular is less than DEFAULT_PRICE_REGULAR
        defaultProductShouldNotBeFound("priceRegular.lessThan=" + DEFAULT_PRICE_REGULAR);

        // Get all the productList where priceRegular is less than UPDATED_PRICE_REGULAR
        defaultProductShouldBeFound("priceRegular.lessThan=" + UPDATED_PRICE_REGULAR);
    }

    @Test
    @Transactional
    void getAllProductsByPriceRegularIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where priceRegular is greater than DEFAULT_PRICE_REGULAR
        defaultProductShouldNotBeFound("priceRegular.greaterThan=" + DEFAULT_PRICE_REGULAR);

        // Get all the productList where priceRegular is greater than SMALLER_PRICE_REGULAR
        defaultProductShouldBeFound("priceRegular.greaterThan=" + SMALLER_PRICE_REGULAR);
    }

    @Test
    @Transactional
    void getAllProductsByIsUseDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isUseDiscount equals to DEFAULT_IS_USE_DISCOUNT
        defaultProductShouldBeFound("isUseDiscount.equals=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the productList where isUseDiscount equals to UPDATED_IS_USE_DISCOUNT
        defaultProductShouldNotBeFound("isUseDiscount.equals=" + UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByIsUseDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isUseDiscount not equals to DEFAULT_IS_USE_DISCOUNT
        defaultProductShouldNotBeFound("isUseDiscount.notEquals=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the productList where isUseDiscount not equals to UPDATED_IS_USE_DISCOUNT
        defaultProductShouldBeFound("isUseDiscount.notEquals=" + UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByIsUseDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isUseDiscount in DEFAULT_IS_USE_DISCOUNT or UPDATED_IS_USE_DISCOUNT
        defaultProductShouldBeFound("isUseDiscount.in=" + DEFAULT_IS_USE_DISCOUNT + "," + UPDATED_IS_USE_DISCOUNT);

        // Get all the productList where isUseDiscount equals to UPDATED_IS_USE_DISCOUNT
        defaultProductShouldNotBeFound("isUseDiscount.in=" + UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByIsUseDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isUseDiscount is not null
        defaultProductShouldBeFound("isUseDiscount.specified=true");

        // Get all the productList where isUseDiscount is null
        defaultProductShouldNotBeFound("isUseDiscount.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByIsUseDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isUseDiscount is greater than or equal to DEFAULT_IS_USE_DISCOUNT
        defaultProductShouldBeFound("isUseDiscount.greaterThanOrEqual=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the productList where isUseDiscount is greater than or equal to UPDATED_IS_USE_DISCOUNT
        defaultProductShouldNotBeFound("isUseDiscount.greaterThanOrEqual=" + UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByIsUseDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isUseDiscount is less than or equal to DEFAULT_IS_USE_DISCOUNT
        defaultProductShouldBeFound("isUseDiscount.lessThanOrEqual=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the productList where isUseDiscount is less than or equal to SMALLER_IS_USE_DISCOUNT
        defaultProductShouldNotBeFound("isUseDiscount.lessThanOrEqual=" + SMALLER_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByIsUseDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isUseDiscount is less than DEFAULT_IS_USE_DISCOUNT
        defaultProductShouldNotBeFound("isUseDiscount.lessThan=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the productList where isUseDiscount is less than UPDATED_IS_USE_DISCOUNT
        defaultProductShouldBeFound("isUseDiscount.lessThan=" + UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByIsUseDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isUseDiscount is greater than DEFAULT_IS_USE_DISCOUNT
        defaultProductShouldNotBeFound("isUseDiscount.greaterThan=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the productList where isUseDiscount is greater than SMALLER_IS_USE_DISCOUNT
        defaultProductShouldBeFound("isUseDiscount.greaterThan=" + SMALLER_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountUnit equals to DEFAULT_DISCOUNT_UNIT
        defaultProductShouldBeFound("discountUnit.equals=" + DEFAULT_DISCOUNT_UNIT);

        // Get all the productList where discountUnit equals to UPDATED_DISCOUNT_UNIT
        defaultProductShouldNotBeFound("discountUnit.equals=" + UPDATED_DISCOUNT_UNIT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountUnit not equals to DEFAULT_DISCOUNT_UNIT
        defaultProductShouldNotBeFound("discountUnit.notEquals=" + DEFAULT_DISCOUNT_UNIT);

        // Get all the productList where discountUnit not equals to UPDATED_DISCOUNT_UNIT
        defaultProductShouldBeFound("discountUnit.notEquals=" + UPDATED_DISCOUNT_UNIT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountUnitIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountUnit in DEFAULT_DISCOUNT_UNIT or UPDATED_DISCOUNT_UNIT
        defaultProductShouldBeFound("discountUnit.in=" + DEFAULT_DISCOUNT_UNIT + "," + UPDATED_DISCOUNT_UNIT);

        // Get all the productList where discountUnit equals to UPDATED_DISCOUNT_UNIT
        defaultProductShouldNotBeFound("discountUnit.in=" + UPDATED_DISCOUNT_UNIT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountUnit is not null
        defaultProductShouldBeFound("discountUnit.specified=true");

        // Get all the productList where discountUnit is null
        defaultProductShouldNotBeFound("discountUnit.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDiscountUnitContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountUnit contains DEFAULT_DISCOUNT_UNIT
        defaultProductShouldBeFound("discountUnit.contains=" + DEFAULT_DISCOUNT_UNIT);

        // Get all the productList where discountUnit contains UPDATED_DISCOUNT_UNIT
        defaultProductShouldNotBeFound("discountUnit.contains=" + UPDATED_DISCOUNT_UNIT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountUnitNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountUnit does not contain DEFAULT_DISCOUNT_UNIT
        defaultProductShouldNotBeFound("discountUnit.doesNotContain=" + DEFAULT_DISCOUNT_UNIT);

        // Get all the productList where discountUnit does not contain UPDATED_DISCOUNT_UNIT
        defaultProductShouldBeFound("discountUnit.doesNotContain=" + UPDATED_DISCOUNT_UNIT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountValueIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountValue equals to DEFAULT_DISCOUNT_VALUE
        defaultProductShouldBeFound("discountValue.equals=" + DEFAULT_DISCOUNT_VALUE);

        // Get all the productList where discountValue equals to UPDATED_DISCOUNT_VALUE
        defaultProductShouldNotBeFound("discountValue.equals=" + UPDATED_DISCOUNT_VALUE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountValue not equals to DEFAULT_DISCOUNT_VALUE
        defaultProductShouldNotBeFound("discountValue.notEquals=" + DEFAULT_DISCOUNT_VALUE);

        // Get all the productList where discountValue not equals to UPDATED_DISCOUNT_VALUE
        defaultProductShouldBeFound("discountValue.notEquals=" + UPDATED_DISCOUNT_VALUE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountValueIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountValue in DEFAULT_DISCOUNT_VALUE or UPDATED_DISCOUNT_VALUE
        defaultProductShouldBeFound("discountValue.in=" + DEFAULT_DISCOUNT_VALUE + "," + UPDATED_DISCOUNT_VALUE);

        // Get all the productList where discountValue equals to UPDATED_DISCOUNT_VALUE
        defaultProductShouldNotBeFound("discountValue.in=" + UPDATED_DISCOUNT_VALUE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountValue is not null
        defaultProductShouldBeFound("discountValue.specified=true");

        // Get all the productList where discountValue is null
        defaultProductShouldNotBeFound("discountValue.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDiscountValueIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountValue is greater than or equal to DEFAULT_DISCOUNT_VALUE
        defaultProductShouldBeFound("discountValue.greaterThanOrEqual=" + DEFAULT_DISCOUNT_VALUE);

        // Get all the productList where discountValue is greater than or equal to UPDATED_DISCOUNT_VALUE
        defaultProductShouldNotBeFound("discountValue.greaterThanOrEqual=" + UPDATED_DISCOUNT_VALUE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountValueIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountValue is less than or equal to DEFAULT_DISCOUNT_VALUE
        defaultProductShouldBeFound("discountValue.lessThanOrEqual=" + DEFAULT_DISCOUNT_VALUE);

        // Get all the productList where discountValue is less than or equal to SMALLER_DISCOUNT_VALUE
        defaultProductShouldNotBeFound("discountValue.lessThanOrEqual=" + SMALLER_DISCOUNT_VALUE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountValueIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountValue is less than DEFAULT_DISCOUNT_VALUE
        defaultProductShouldNotBeFound("discountValue.lessThan=" + DEFAULT_DISCOUNT_VALUE);

        // Get all the productList where discountValue is less than UPDATED_DISCOUNT_VALUE
        defaultProductShouldBeFound("discountValue.lessThan=" + UPDATED_DISCOUNT_VALUE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountValueIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountValue is greater than DEFAULT_DISCOUNT_VALUE
        defaultProductShouldNotBeFound("discountValue.greaterThan=" + DEFAULT_DISCOUNT_VALUE);

        // Get all the productList where discountValue is greater than SMALLER_DISCOUNT_VALUE
        defaultProductShouldBeFound("discountValue.greaterThan=" + SMALLER_DISCOUNT_VALUE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountStartdate equals to DEFAULT_DISCOUNT_STARTDATE
        defaultProductShouldBeFound("discountStartdate.equals=" + DEFAULT_DISCOUNT_STARTDATE);

        // Get all the productList where discountStartdate equals to UPDATED_DISCOUNT_STARTDATE
        defaultProductShouldNotBeFound("discountStartdate.equals=" + UPDATED_DISCOUNT_STARTDATE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountStartdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountStartdate not equals to DEFAULT_DISCOUNT_STARTDATE
        defaultProductShouldNotBeFound("discountStartdate.notEquals=" + DEFAULT_DISCOUNT_STARTDATE);

        // Get all the productList where discountStartdate not equals to UPDATED_DISCOUNT_STARTDATE
        defaultProductShouldBeFound("discountStartdate.notEquals=" + UPDATED_DISCOUNT_STARTDATE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountStartdate in DEFAULT_DISCOUNT_STARTDATE or UPDATED_DISCOUNT_STARTDATE
        defaultProductShouldBeFound("discountStartdate.in=" + DEFAULT_DISCOUNT_STARTDATE + "," + UPDATED_DISCOUNT_STARTDATE);

        // Get all the productList where discountStartdate equals to UPDATED_DISCOUNT_STARTDATE
        defaultProductShouldNotBeFound("discountStartdate.in=" + UPDATED_DISCOUNT_STARTDATE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountStartdate is not null
        defaultProductShouldBeFound("discountStartdate.specified=true");

        // Get all the productList where discountStartdate is null
        defaultProductShouldNotBeFound("discountStartdate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIntervalIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountInterval equals to DEFAULT_DISCOUNT_INTERVAL
        defaultProductShouldBeFound("discountInterval.equals=" + DEFAULT_DISCOUNT_INTERVAL);

        // Get all the productList where discountInterval equals to UPDATED_DISCOUNT_INTERVAL
        defaultProductShouldNotBeFound("discountInterval.equals=" + UPDATED_DISCOUNT_INTERVAL);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIntervalIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountInterval not equals to DEFAULT_DISCOUNT_INTERVAL
        defaultProductShouldNotBeFound("discountInterval.notEquals=" + DEFAULT_DISCOUNT_INTERVAL);

        // Get all the productList where discountInterval not equals to UPDATED_DISCOUNT_INTERVAL
        defaultProductShouldBeFound("discountInterval.notEquals=" + UPDATED_DISCOUNT_INTERVAL);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIntervalIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountInterval in DEFAULT_DISCOUNT_INTERVAL or UPDATED_DISCOUNT_INTERVAL
        defaultProductShouldBeFound("discountInterval.in=" + DEFAULT_DISCOUNT_INTERVAL + "," + UPDATED_DISCOUNT_INTERVAL);

        // Get all the productList where discountInterval equals to UPDATED_DISCOUNT_INTERVAL
        defaultProductShouldNotBeFound("discountInterval.in=" + UPDATED_DISCOUNT_INTERVAL);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIntervalIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountInterval is not null
        defaultProductShouldBeFound("discountInterval.specified=true");

        // Get all the productList where discountInterval is null
        defaultProductShouldNotBeFound("discountInterval.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIntervalIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountInterval is greater than or equal to DEFAULT_DISCOUNT_INTERVAL
        defaultProductShouldBeFound("discountInterval.greaterThanOrEqual=" + DEFAULT_DISCOUNT_INTERVAL);

        // Get all the productList where discountInterval is greater than or equal to UPDATED_DISCOUNT_INTERVAL
        defaultProductShouldNotBeFound("discountInterval.greaterThanOrEqual=" + UPDATED_DISCOUNT_INTERVAL);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIntervalIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountInterval is less than or equal to DEFAULT_DISCOUNT_INTERVAL
        defaultProductShouldBeFound("discountInterval.lessThanOrEqual=" + DEFAULT_DISCOUNT_INTERVAL);

        // Get all the productList where discountInterval is less than or equal to SMALLER_DISCOUNT_INTERVAL
        defaultProductShouldNotBeFound("discountInterval.lessThanOrEqual=" + SMALLER_DISCOUNT_INTERVAL);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIntervalIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountInterval is less than DEFAULT_DISCOUNT_INTERVAL
        defaultProductShouldNotBeFound("discountInterval.lessThan=" + DEFAULT_DISCOUNT_INTERVAL);

        // Get all the productList where discountInterval is less than UPDATED_DISCOUNT_INTERVAL
        defaultProductShouldBeFound("discountInterval.lessThan=" + UPDATED_DISCOUNT_INTERVAL);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIntervalIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountInterval is greater than DEFAULT_DISCOUNT_INTERVAL
        defaultProductShouldNotBeFound("discountInterval.greaterThan=" + DEFAULT_DISCOUNT_INTERVAL);

        // Get all the productList where discountInterval is greater than SMALLER_DISCOUNT_INTERVAL
        defaultProductShouldBeFound("discountInterval.greaterThan=" + SMALLER_DISCOUNT_INTERVAL);
    }

    @Test
    @Transactional
    void getAllProductsByVideoIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where video equals to DEFAULT_VIDEO
        defaultProductShouldBeFound("video.equals=" + DEFAULT_VIDEO);

        // Get all the productList where video equals to UPDATED_VIDEO
        defaultProductShouldNotBeFound("video.equals=" + UPDATED_VIDEO);
    }

    @Test
    @Transactional
    void getAllProductsByVideoIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where video not equals to DEFAULT_VIDEO
        defaultProductShouldNotBeFound("video.notEquals=" + DEFAULT_VIDEO);

        // Get all the productList where video not equals to UPDATED_VIDEO
        defaultProductShouldBeFound("video.notEquals=" + UPDATED_VIDEO);
    }

    @Test
    @Transactional
    void getAllProductsByVideoIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where video in DEFAULT_VIDEO or UPDATED_VIDEO
        defaultProductShouldBeFound("video.in=" + DEFAULT_VIDEO + "," + UPDATED_VIDEO);

        // Get all the productList where video equals to UPDATED_VIDEO
        defaultProductShouldNotBeFound("video.in=" + UPDATED_VIDEO);
    }

    @Test
    @Transactional
    void getAllProductsByVideoIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where video is not null
        defaultProductShouldBeFound("video.specified=true");

        // Get all the productList where video is null
        defaultProductShouldNotBeFound("video.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByVideoContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where video contains DEFAULT_VIDEO
        defaultProductShouldBeFound("video.contains=" + DEFAULT_VIDEO);

        // Get all the productList where video contains UPDATED_VIDEO
        defaultProductShouldNotBeFound("video.contains=" + UPDATED_VIDEO);
    }

    @Test
    @Transactional
    void getAllProductsByVideoNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where video does not contain DEFAULT_VIDEO
        defaultProductShouldNotBeFound("video.doesNotContain=" + DEFAULT_VIDEO);

        // Get all the productList where video does not contain UPDATED_VIDEO
        defaultProductShouldBeFound("video.doesNotContain=" + UPDATED_VIDEO);
    }

    @Test
    @Transactional
    void getAllProductsByStartdateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where startdate equals to DEFAULT_STARTDATE
        defaultProductShouldBeFound("startdate.equals=" + DEFAULT_STARTDATE);

        // Get all the productList where startdate equals to UPDATED_STARTDATE
        defaultProductShouldNotBeFound("startdate.equals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllProductsByStartdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where startdate not equals to DEFAULT_STARTDATE
        defaultProductShouldNotBeFound("startdate.notEquals=" + DEFAULT_STARTDATE);

        // Get all the productList where startdate not equals to UPDATED_STARTDATE
        defaultProductShouldBeFound("startdate.notEquals=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllProductsByStartdateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where startdate in DEFAULT_STARTDATE or UPDATED_STARTDATE
        defaultProductShouldBeFound("startdate.in=" + DEFAULT_STARTDATE + "," + UPDATED_STARTDATE);

        // Get all the productList where startdate equals to UPDATED_STARTDATE
        defaultProductShouldNotBeFound("startdate.in=" + UPDATED_STARTDATE);
    }

    @Test
    @Transactional
    void getAllProductsByStartdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where startdate is not null
        defaultProductShouldBeFound("startdate.specified=true");

        // Get all the productList where startdate is null
        defaultProductShouldNotBeFound("startdate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByPrepareResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepareResource equals to DEFAULT_PREPARE_RESOURCE
        defaultProductShouldBeFound("prepareResource.equals=" + DEFAULT_PREPARE_RESOURCE);

        // Get all the productList where prepareResource equals to UPDATED_PREPARE_RESOURCE
        defaultProductShouldNotBeFound("prepareResource.equals=" + UPDATED_PREPARE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByPrepareResourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepareResource not equals to DEFAULT_PREPARE_RESOURCE
        defaultProductShouldNotBeFound("prepareResource.notEquals=" + DEFAULT_PREPARE_RESOURCE);

        // Get all the productList where prepareResource not equals to UPDATED_PREPARE_RESOURCE
        defaultProductShouldBeFound("prepareResource.notEquals=" + UPDATED_PREPARE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByPrepareResourceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepareResource in DEFAULT_PREPARE_RESOURCE or UPDATED_PREPARE_RESOURCE
        defaultProductShouldBeFound("prepareResource.in=" + DEFAULT_PREPARE_RESOURCE + "," + UPDATED_PREPARE_RESOURCE);

        // Get all the productList where prepareResource equals to UPDATED_PREPARE_RESOURCE
        defaultProductShouldNotBeFound("prepareResource.in=" + UPDATED_PREPARE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByPrepareResourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepareResource is not null
        defaultProductShouldBeFound("prepareResource.specified=true");

        // Get all the productList where prepareResource is null
        defaultProductShouldNotBeFound("prepareResource.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByPrepareResourceContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepareResource contains DEFAULT_PREPARE_RESOURCE
        defaultProductShouldBeFound("prepareResource.contains=" + DEFAULT_PREPARE_RESOURCE);

        // Get all the productList where prepareResource contains UPDATED_PREPARE_RESOURCE
        defaultProductShouldNotBeFound("prepareResource.contains=" + UPDATED_PREPARE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByPrepareResourceNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where prepareResource does not contain DEFAULT_PREPARE_RESOURCE
        defaultProductShouldNotBeFound("prepareResource.doesNotContain=" + DEFAULT_PREPARE_RESOURCE);

        // Get all the productList where prepareResource does not contain UPDATED_PREPARE_RESOURCE
        defaultProductShouldBeFound("prepareResource.doesNotContain=" + UPDATED_PREPARE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByIntroduceResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introduceResource equals to DEFAULT_INTRODUCE_RESOURCE
        defaultProductShouldBeFound("introduceResource.equals=" + DEFAULT_INTRODUCE_RESOURCE);

        // Get all the productList where introduceResource equals to UPDATED_INTRODUCE_RESOURCE
        defaultProductShouldNotBeFound("introduceResource.equals=" + UPDATED_INTRODUCE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByIntroduceResourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introduceResource not equals to DEFAULT_INTRODUCE_RESOURCE
        defaultProductShouldNotBeFound("introduceResource.notEquals=" + DEFAULT_INTRODUCE_RESOURCE);

        // Get all the productList where introduceResource not equals to UPDATED_INTRODUCE_RESOURCE
        defaultProductShouldBeFound("introduceResource.notEquals=" + UPDATED_INTRODUCE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByIntroduceResourceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introduceResource in DEFAULT_INTRODUCE_RESOURCE or UPDATED_INTRODUCE_RESOURCE
        defaultProductShouldBeFound("introduceResource.in=" + DEFAULT_INTRODUCE_RESOURCE + "," + UPDATED_INTRODUCE_RESOURCE);

        // Get all the productList where introduceResource equals to UPDATED_INTRODUCE_RESOURCE
        defaultProductShouldNotBeFound("introduceResource.in=" + UPDATED_INTRODUCE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByIntroduceResourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introduceResource is not null
        defaultProductShouldBeFound("introduceResource.specified=true");

        // Get all the productList where introduceResource is null
        defaultProductShouldNotBeFound("introduceResource.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByIntroduceResourceContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introduceResource contains DEFAULT_INTRODUCE_RESOURCE
        defaultProductShouldBeFound("introduceResource.contains=" + DEFAULT_INTRODUCE_RESOURCE);

        // Get all the productList where introduceResource contains UPDATED_INTRODUCE_RESOURCE
        defaultProductShouldNotBeFound("introduceResource.contains=" + UPDATED_INTRODUCE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByIntroduceResourceNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where introduceResource does not contain DEFAULT_INTRODUCE_RESOURCE
        defaultProductShouldNotBeFound("introduceResource.doesNotContain=" + DEFAULT_INTRODUCE_RESOURCE);

        // Get all the productList where introduceResource does not contain UPDATED_INTRODUCE_RESOURCE
        defaultProductShouldBeFound("introduceResource.doesNotContain=" + UPDATED_INTRODUCE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByShippingResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingResource equals to DEFAULT_SHIPPING_RESOURCE
        defaultProductShouldBeFound("shippingResource.equals=" + DEFAULT_SHIPPING_RESOURCE);

        // Get all the productList where shippingResource equals to UPDATED_SHIPPING_RESOURCE
        defaultProductShouldNotBeFound("shippingResource.equals=" + UPDATED_SHIPPING_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByShippingResourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingResource not equals to DEFAULT_SHIPPING_RESOURCE
        defaultProductShouldNotBeFound("shippingResource.notEquals=" + DEFAULT_SHIPPING_RESOURCE);

        // Get all the productList where shippingResource not equals to UPDATED_SHIPPING_RESOURCE
        defaultProductShouldBeFound("shippingResource.notEquals=" + UPDATED_SHIPPING_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByShippingResourceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingResource in DEFAULT_SHIPPING_RESOURCE or UPDATED_SHIPPING_RESOURCE
        defaultProductShouldBeFound("shippingResource.in=" + DEFAULT_SHIPPING_RESOURCE + "," + UPDATED_SHIPPING_RESOURCE);

        // Get all the productList where shippingResource equals to UPDATED_SHIPPING_RESOURCE
        defaultProductShouldNotBeFound("shippingResource.in=" + UPDATED_SHIPPING_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByShippingResourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingResource is not null
        defaultProductShouldBeFound("shippingResource.specified=true");

        // Get all the productList where shippingResource is null
        defaultProductShouldNotBeFound("shippingResource.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByShippingResourceContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingResource contains DEFAULT_SHIPPING_RESOURCE
        defaultProductShouldBeFound("shippingResource.contains=" + DEFAULT_SHIPPING_RESOURCE);

        // Get all the productList where shippingResource contains UPDATED_SHIPPING_RESOURCE
        defaultProductShouldNotBeFound("shippingResource.contains=" + UPDATED_SHIPPING_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByShippingResourceNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingResource does not contain DEFAULT_SHIPPING_RESOURCE
        defaultProductShouldNotBeFound("shippingResource.doesNotContain=" + DEFAULT_SHIPPING_RESOURCE);

        // Get all the productList where shippingResource does not contain UPDATED_SHIPPING_RESOURCE
        defaultProductShouldBeFound("shippingResource.doesNotContain=" + UPDATED_SHIPPING_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundResource equals to DEFAULT_REFUND_RESOURCE
        defaultProductShouldBeFound("refundResource.equals=" + DEFAULT_REFUND_RESOURCE);

        // Get all the productList where refundResource equals to UPDATED_REFUND_RESOURCE
        defaultProductShouldNotBeFound("refundResource.equals=" + UPDATED_REFUND_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundResourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundResource not equals to DEFAULT_REFUND_RESOURCE
        defaultProductShouldNotBeFound("refundResource.notEquals=" + DEFAULT_REFUND_RESOURCE);

        // Get all the productList where refundResource not equals to UPDATED_REFUND_RESOURCE
        defaultProductShouldBeFound("refundResource.notEquals=" + UPDATED_REFUND_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundResourceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundResource in DEFAULT_REFUND_RESOURCE or UPDATED_REFUND_RESOURCE
        defaultProductShouldBeFound("refundResource.in=" + DEFAULT_REFUND_RESOURCE + "," + UPDATED_REFUND_RESOURCE);

        // Get all the productList where refundResource equals to UPDATED_REFUND_RESOURCE
        defaultProductShouldNotBeFound("refundResource.in=" + UPDATED_REFUND_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundResourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundResource is not null
        defaultProductShouldBeFound("refundResource.specified=true");

        // Get all the productList where refundResource is null
        defaultProductShouldNotBeFound("refundResource.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByRefundResourceContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundResource contains DEFAULT_REFUND_RESOURCE
        defaultProductShouldBeFound("refundResource.contains=" + DEFAULT_REFUND_RESOURCE);

        // Get all the productList where refundResource contains UPDATED_REFUND_RESOURCE
        defaultProductShouldNotBeFound("refundResource.contains=" + UPDATED_REFUND_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundResourceNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundResource does not contain DEFAULT_REFUND_RESOURCE
        defaultProductShouldNotBeFound("refundResource.doesNotContain=" + DEFAULT_REFUND_RESOURCE);

        // Get all the productList where refundResource does not contain UPDATED_REFUND_RESOURCE
        defaultProductShouldBeFound("refundResource.doesNotContain=" + UPDATED_REFUND_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByChangeResourceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeResource equals to DEFAULT_CHANGE_RESOURCE
        defaultProductShouldBeFound("changeResource.equals=" + DEFAULT_CHANGE_RESOURCE);

        // Get all the productList where changeResource equals to UPDATED_CHANGE_RESOURCE
        defaultProductShouldNotBeFound("changeResource.equals=" + UPDATED_CHANGE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByChangeResourceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeResource not equals to DEFAULT_CHANGE_RESOURCE
        defaultProductShouldNotBeFound("changeResource.notEquals=" + DEFAULT_CHANGE_RESOURCE);

        // Get all the productList where changeResource not equals to UPDATED_CHANGE_RESOURCE
        defaultProductShouldBeFound("changeResource.notEquals=" + UPDATED_CHANGE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByChangeResourceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeResource in DEFAULT_CHANGE_RESOURCE or UPDATED_CHANGE_RESOURCE
        defaultProductShouldBeFound("changeResource.in=" + DEFAULT_CHANGE_RESOURCE + "," + UPDATED_CHANGE_RESOURCE);

        // Get all the productList where changeResource equals to UPDATED_CHANGE_RESOURCE
        defaultProductShouldNotBeFound("changeResource.in=" + UPDATED_CHANGE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByChangeResourceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeResource is not null
        defaultProductShouldBeFound("changeResource.specified=true");

        // Get all the productList where changeResource is null
        defaultProductShouldNotBeFound("changeResource.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByChangeResourceContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeResource contains DEFAULT_CHANGE_RESOURCE
        defaultProductShouldBeFound("changeResource.contains=" + DEFAULT_CHANGE_RESOURCE);

        // Get all the productList where changeResource contains UPDATED_CHANGE_RESOURCE
        defaultProductShouldNotBeFound("changeResource.contains=" + UPDATED_CHANGE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByChangeResourceNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where changeResource does not contain DEFAULT_CHANGE_RESOURCE
        defaultProductShouldNotBeFound("changeResource.doesNotContain=" + DEFAULT_CHANGE_RESOURCE);

        // Get all the productList where changeResource does not contain UPDATED_CHANGE_RESOURCE
        defaultProductShouldBeFound("changeResource.doesNotContain=" + UPDATED_CHANGE_RESOURCE);
    }

    @Test
    @Transactional
    void getAllProductsByCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where code equals to DEFAULT_CODE
        defaultProductShouldBeFound("code.equals=" + DEFAULT_CODE);

        // Get all the productList where code equals to UPDATED_CODE
        defaultProductShouldNotBeFound("code.equals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByCodeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where code not equals to DEFAULT_CODE
        defaultProductShouldNotBeFound("code.notEquals=" + DEFAULT_CODE);

        // Get all the productList where code not equals to UPDATED_CODE
        defaultProductShouldBeFound("code.notEquals=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByCodeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where code in DEFAULT_CODE or UPDATED_CODE
        defaultProductShouldBeFound("code.in=" + DEFAULT_CODE + "," + UPDATED_CODE);

        // Get all the productList where code equals to UPDATED_CODE
        defaultProductShouldNotBeFound("code.in=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where code is not null
        defaultProductShouldBeFound("code.specified=true");

        // Get all the productList where code is null
        defaultProductShouldNotBeFound("code.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByCodeContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where code contains DEFAULT_CODE
        defaultProductShouldBeFound("code.contains=" + DEFAULT_CODE);

        // Get all the productList where code contains UPDATED_CODE
        defaultProductShouldNotBeFound("code.contains=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByCodeNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where code does not contain DEFAULT_CODE
        defaultProductShouldNotBeFound("code.doesNotContain=" + DEFAULT_CODE);

        // Get all the productList where code does not contain UPDATED_CODE
        defaultProductShouldBeFound("code.doesNotContain=" + UPDATED_CODE);
    }

    @Test
    @Transactional
    void getAllProductsByInstallmentMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where installmentMonth equals to DEFAULT_INSTALLMENT_MONTH
        defaultProductShouldBeFound("installmentMonth.equals=" + DEFAULT_INSTALLMENT_MONTH);

        // Get all the productList where installmentMonth equals to UPDATED_INSTALLMENT_MONTH
        defaultProductShouldNotBeFound("installmentMonth.equals=" + UPDATED_INSTALLMENT_MONTH);
    }

    @Test
    @Transactional
    void getAllProductsByInstallmentMonthIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where installmentMonth not equals to DEFAULT_INSTALLMENT_MONTH
        defaultProductShouldNotBeFound("installmentMonth.notEquals=" + DEFAULT_INSTALLMENT_MONTH);

        // Get all the productList where installmentMonth not equals to UPDATED_INSTALLMENT_MONTH
        defaultProductShouldBeFound("installmentMonth.notEquals=" + UPDATED_INSTALLMENT_MONTH);
    }

    @Test
    @Transactional
    void getAllProductsByInstallmentMonthIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where installmentMonth in DEFAULT_INSTALLMENT_MONTH or UPDATED_INSTALLMENT_MONTH
        defaultProductShouldBeFound("installmentMonth.in=" + DEFAULT_INSTALLMENT_MONTH + "," + UPDATED_INSTALLMENT_MONTH);

        // Get all the productList where installmentMonth equals to UPDATED_INSTALLMENT_MONTH
        defaultProductShouldNotBeFound("installmentMonth.in=" + UPDATED_INSTALLMENT_MONTH);
    }

    @Test
    @Transactional
    void getAllProductsByInstallmentMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where installmentMonth is not null
        defaultProductShouldBeFound("installmentMonth.specified=true");

        // Get all the productList where installmentMonth is null
        defaultProductShouldNotBeFound("installmentMonth.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByInstallmentMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where installmentMonth is greater than or equal to DEFAULT_INSTALLMENT_MONTH
        defaultProductShouldBeFound("installmentMonth.greaterThanOrEqual=" + DEFAULT_INSTALLMENT_MONTH);

        // Get all the productList where installmentMonth is greater than or equal to UPDATED_INSTALLMENT_MONTH
        defaultProductShouldNotBeFound("installmentMonth.greaterThanOrEqual=" + UPDATED_INSTALLMENT_MONTH);
    }

    @Test
    @Transactional
    void getAllProductsByInstallmentMonthIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where installmentMonth is less than or equal to DEFAULT_INSTALLMENT_MONTH
        defaultProductShouldBeFound("installmentMonth.lessThanOrEqual=" + DEFAULT_INSTALLMENT_MONTH);

        // Get all the productList where installmentMonth is less than or equal to SMALLER_INSTALLMENT_MONTH
        defaultProductShouldNotBeFound("installmentMonth.lessThanOrEqual=" + SMALLER_INSTALLMENT_MONTH);
    }

    @Test
    @Transactional
    void getAllProductsByInstallmentMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where installmentMonth is less than DEFAULT_INSTALLMENT_MONTH
        defaultProductShouldNotBeFound("installmentMonth.lessThan=" + DEFAULT_INSTALLMENT_MONTH);

        // Get all the productList where installmentMonth is less than UPDATED_INSTALLMENT_MONTH
        defaultProductShouldBeFound("installmentMonth.lessThan=" + UPDATED_INSTALLMENT_MONTH);
    }

    @Test
    @Transactional
    void getAllProductsByInstallmentMonthIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where installmentMonth is greater than DEFAULT_INSTALLMENT_MONTH
        defaultProductShouldNotBeFound("installmentMonth.greaterThan=" + DEFAULT_INSTALLMENT_MONTH);

        // Get all the productList where installmentMonth is greater than SMALLER_INSTALLMENT_MONTH
        defaultProductShouldBeFound("installmentMonth.greaterThan=" + SMALLER_INSTALLMENT_MONTH);
    }

    @Test
    @Transactional
    void getAllProductsByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where type equals to DEFAULT_TYPE
        defaultProductShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the productList where type equals to UPDATED_TYPE
        defaultProductShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where type not equals to DEFAULT_TYPE
        defaultProductShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the productList where type not equals to UPDATED_TYPE
        defaultProductShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultProductShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the productList where type equals to UPDATED_TYPE
        defaultProductShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where type is not null
        defaultProductShouldBeFound("type.specified=true");

        // Get all the productList where type is null
        defaultProductShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByNumberIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where number equals to DEFAULT_NUMBER
        defaultProductShouldBeFound("number.equals=" + DEFAULT_NUMBER);

        // Get all the productList where number equals to UPDATED_NUMBER
        defaultProductShouldNotBeFound("number.equals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllProductsByNumberIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where number not equals to DEFAULT_NUMBER
        defaultProductShouldNotBeFound("number.notEquals=" + DEFAULT_NUMBER);

        // Get all the productList where number not equals to UPDATED_NUMBER
        defaultProductShouldBeFound("number.notEquals=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllProductsByNumberIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where number in DEFAULT_NUMBER or UPDATED_NUMBER
        defaultProductShouldBeFound("number.in=" + DEFAULT_NUMBER + "," + UPDATED_NUMBER);

        // Get all the productList where number equals to UPDATED_NUMBER
        defaultProductShouldNotBeFound("number.in=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllProductsByNumberIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where number is not null
        defaultProductShouldBeFound("number.specified=true");

        // Get all the productList where number is null
        defaultProductShouldNotBeFound("number.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByNumberContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where number contains DEFAULT_NUMBER
        defaultProductShouldBeFound("number.contains=" + DEFAULT_NUMBER);

        // Get all the productList where number contains UPDATED_NUMBER
        defaultProductShouldNotBeFound("number.contains=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllProductsByNumberNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where number does not contain DEFAULT_NUMBER
        defaultProductShouldNotBeFound("number.doesNotContain=" + DEFAULT_NUMBER);

        // Get all the productList where number does not contain UPDATED_NUMBER
        defaultProductShouldBeFound("number.doesNotContain=" + UPDATED_NUMBER);
    }

    @Test
    @Transactional
    void getAllProductsByPopularCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where popularCount equals to DEFAULT_POPULAR_COUNT
        defaultProductShouldBeFound("popularCount.equals=" + DEFAULT_POPULAR_COUNT);

        // Get all the productList where popularCount equals to UPDATED_POPULAR_COUNT
        defaultProductShouldNotBeFound("popularCount.equals=" + UPDATED_POPULAR_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByPopularCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where popularCount not equals to DEFAULT_POPULAR_COUNT
        defaultProductShouldNotBeFound("popularCount.notEquals=" + DEFAULT_POPULAR_COUNT);

        // Get all the productList where popularCount not equals to UPDATED_POPULAR_COUNT
        defaultProductShouldBeFound("popularCount.notEquals=" + UPDATED_POPULAR_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByPopularCountIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where popularCount in DEFAULT_POPULAR_COUNT or UPDATED_POPULAR_COUNT
        defaultProductShouldBeFound("popularCount.in=" + DEFAULT_POPULAR_COUNT + "," + UPDATED_POPULAR_COUNT);

        // Get all the productList where popularCount equals to UPDATED_POPULAR_COUNT
        defaultProductShouldNotBeFound("popularCount.in=" + UPDATED_POPULAR_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByPopularCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where popularCount is not null
        defaultProductShouldBeFound("popularCount.specified=true");

        // Get all the productList where popularCount is null
        defaultProductShouldNotBeFound("popularCount.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByPopularCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where popularCount is greater than or equal to DEFAULT_POPULAR_COUNT
        defaultProductShouldBeFound("popularCount.greaterThanOrEqual=" + DEFAULT_POPULAR_COUNT);

        // Get all the productList where popularCount is greater than or equal to UPDATED_POPULAR_COUNT
        defaultProductShouldNotBeFound("popularCount.greaterThanOrEqual=" + UPDATED_POPULAR_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByPopularCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where popularCount is less than or equal to DEFAULT_POPULAR_COUNT
        defaultProductShouldBeFound("popularCount.lessThanOrEqual=" + DEFAULT_POPULAR_COUNT);

        // Get all the productList where popularCount is less than or equal to SMALLER_POPULAR_COUNT
        defaultProductShouldNotBeFound("popularCount.lessThanOrEqual=" + SMALLER_POPULAR_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByPopularCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where popularCount is less than DEFAULT_POPULAR_COUNT
        defaultProductShouldNotBeFound("popularCount.lessThan=" + DEFAULT_POPULAR_COUNT);

        // Get all the productList where popularCount is less than UPDATED_POPULAR_COUNT
        defaultProductShouldBeFound("popularCount.lessThan=" + UPDATED_POPULAR_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByPopularCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where popularCount is greater than DEFAULT_POPULAR_COUNT
        defaultProductShouldNotBeFound("popularCount.greaterThan=" + DEFAULT_POPULAR_COUNT);

        // Get all the productList where popularCount is greater than SMALLER_POPULAR_COUNT
        defaultProductShouldBeFound("popularCount.greaterThan=" + SMALLER_POPULAR_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByReviewCountIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reviewCount equals to DEFAULT_REVIEW_COUNT
        defaultProductShouldBeFound("reviewCount.equals=" + DEFAULT_REVIEW_COUNT);

        // Get all the productList where reviewCount equals to UPDATED_REVIEW_COUNT
        defaultProductShouldNotBeFound("reviewCount.equals=" + UPDATED_REVIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByReviewCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reviewCount not equals to DEFAULT_REVIEW_COUNT
        defaultProductShouldNotBeFound("reviewCount.notEquals=" + DEFAULT_REVIEW_COUNT);

        // Get all the productList where reviewCount not equals to UPDATED_REVIEW_COUNT
        defaultProductShouldBeFound("reviewCount.notEquals=" + UPDATED_REVIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByReviewCountIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reviewCount in DEFAULT_REVIEW_COUNT or UPDATED_REVIEW_COUNT
        defaultProductShouldBeFound("reviewCount.in=" + DEFAULT_REVIEW_COUNT + "," + UPDATED_REVIEW_COUNT);

        // Get all the productList where reviewCount equals to UPDATED_REVIEW_COUNT
        defaultProductShouldNotBeFound("reviewCount.in=" + UPDATED_REVIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByReviewCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reviewCount is not null
        defaultProductShouldBeFound("reviewCount.specified=true");

        // Get all the productList where reviewCount is null
        defaultProductShouldNotBeFound("reviewCount.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByReviewCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reviewCount is greater than or equal to DEFAULT_REVIEW_COUNT
        defaultProductShouldBeFound("reviewCount.greaterThanOrEqual=" + DEFAULT_REVIEW_COUNT);

        // Get all the productList where reviewCount is greater than or equal to UPDATED_REVIEW_COUNT
        defaultProductShouldNotBeFound("reviewCount.greaterThanOrEqual=" + UPDATED_REVIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByReviewCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reviewCount is less than or equal to DEFAULT_REVIEW_COUNT
        defaultProductShouldBeFound("reviewCount.lessThanOrEqual=" + DEFAULT_REVIEW_COUNT);

        // Get all the productList where reviewCount is less than or equal to SMALLER_REVIEW_COUNT
        defaultProductShouldNotBeFound("reviewCount.lessThanOrEqual=" + SMALLER_REVIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByReviewCountIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reviewCount is less than DEFAULT_REVIEW_COUNT
        defaultProductShouldNotBeFound("reviewCount.lessThan=" + DEFAULT_REVIEW_COUNT);

        // Get all the productList where reviewCount is less than UPDATED_REVIEW_COUNT
        defaultProductShouldBeFound("reviewCount.lessThan=" + UPDATED_REVIEW_COUNT);
    }

    @Test
    @Transactional
    void getAllProductsByReviewCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where reviewCount is greater than DEFAULT_REVIEW_COUNT
        defaultProductShouldNotBeFound("reviewCount.greaterThan=" + DEFAULT_REVIEW_COUNT);

        // Get all the productList where reviewCount is greater than SMALLER_REVIEW_COUNT
        defaultProductShouldBeFound("reviewCount.greaterThan=" + SMALLER_REVIEW_COUNT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductShouldBeFound(String filter) throws Exception {
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(product.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].difficulty").value(hasItem(DEFAULT_DIFFICULTY.toString())))
            .andExpect(jsonPath("$.[*].thumbnail").value(hasItem(DEFAULT_THUMBNAIL)))
            .andExpect(jsonPath("$.[*].owner").value(hasItem(DEFAULT_OWNER)))
            .andExpect(jsonPath("$.[*].regdate").value(hasItem(DEFAULT_REGDATE.toString())))
            .andExpect(jsonPath("$.[*].priceRegular").value(hasItem(DEFAULT_PRICE_REGULAR)))
            .andExpect(jsonPath("$.[*].isUseDiscount").value(hasItem(DEFAULT_IS_USE_DISCOUNT)))
            .andExpect(jsonPath("$.[*].discountUnit").value(hasItem(DEFAULT_DISCOUNT_UNIT)))
            .andExpect(jsonPath("$.[*].discountValue").value(hasItem(DEFAULT_DISCOUNT_VALUE)))
            .andExpect(jsonPath("$.[*].discountStartdate").value(hasItem(DEFAULT_DISCOUNT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].discountInterval").value(hasItem(DEFAULT_DISCOUNT_INTERVAL)))
            .andExpect(jsonPath("$.[*].video").value(hasItem(DEFAULT_VIDEO)))
            .andExpect(jsonPath("$.[*].startdate").value(hasItem(DEFAULT_STARTDATE.toString())))
            .andExpect(jsonPath("$.[*].prepareResource").value(hasItem(DEFAULT_PREPARE_RESOURCE)))
            .andExpect(jsonPath("$.[*].introduceResource").value(hasItem(DEFAULT_INTRODUCE_RESOURCE)))
            .andExpect(jsonPath("$.[*].shippingResource").value(hasItem(DEFAULT_SHIPPING_RESOURCE)))
            .andExpect(jsonPath("$.[*].refundResource").value(hasItem(DEFAULT_REFUND_RESOURCE)))
            .andExpect(jsonPath("$.[*].changeResource").value(hasItem(DEFAULT_CHANGE_RESOURCE)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].installmentMonth").value(hasItem(DEFAULT_INSTALLMENT_MONTH)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].number").value(hasItem(DEFAULT_NUMBER)))
            .andExpect(jsonPath("$.[*].popularCount").value(hasItem(DEFAULT_POPULAR_COUNT)))
            .andExpect(jsonPath("$.[*].reviewCount").value(hasItem(DEFAULT_REVIEW_COUNT)));

        // Check, that the count call also returns 1
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductShouldNotBeFound(String filter) throws Exception {
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProduct() throws Exception {
        // Get the product
        restProductMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product
        Product updatedProduct = productRepository.findById(product.getId()).get();
        // Disconnect from session so that the updates on updatedProduct are not directly saved in db
        em.detach(updatedProduct);
        updatedProduct
            .name(UPDATED_NAME)
            .difficulty(UPDATED_DIFFICULTY)
            .thumbnail(UPDATED_THUMBNAIL)
            .owner(UPDATED_OWNER)
            .regdate(UPDATED_REGDATE)
            .priceRegular(UPDATED_PRICE_REGULAR)
            .isUseDiscount(UPDATED_IS_USE_DISCOUNT)
            .discountUnit(UPDATED_DISCOUNT_UNIT)
            .discountValue(UPDATED_DISCOUNT_VALUE)
            .discountStartdate(UPDATED_DISCOUNT_STARTDATE)
            .discountInterval(UPDATED_DISCOUNT_INTERVAL)
            .video(UPDATED_VIDEO)
            .startdate(UPDATED_STARTDATE)
            .prepareResource(UPDATED_PREPARE_RESOURCE)
            .introduceResource(UPDATED_INTRODUCE_RESOURCE)
            .shippingResource(UPDATED_SHIPPING_RESOURCE)
            .refundResource(UPDATED_REFUND_RESOURCE)
            .changeResource(UPDATED_CHANGE_RESOURCE)
            .code(UPDATED_CODE)
            .installmentMonth(UPDATED_INSTALLMENT_MONTH)
            .type(UPDATED_TYPE)
            .number(UPDATED_NUMBER)
            .popularCount(UPDATED_POPULAR_COUNT)
            .reviewCount(UPDATED_REVIEW_COUNT);
        ProductDTO productDTO = productMapper.toDto(updatedProduct);

        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testProduct.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testProduct.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testProduct.getRegdate()).isEqualTo(UPDATED_REGDATE);
        assertThat(testProduct.getPriceRegular()).isEqualTo(UPDATED_PRICE_REGULAR);
        assertThat(testProduct.getIsUseDiscount()).isEqualTo(UPDATED_IS_USE_DISCOUNT);
        assertThat(testProduct.getDiscountUnit()).isEqualTo(UPDATED_DISCOUNT_UNIT);
        assertThat(testProduct.getDiscountValue()).isEqualTo(UPDATED_DISCOUNT_VALUE);
        assertThat(testProduct.getDiscountStartdate()).isEqualTo(UPDATED_DISCOUNT_STARTDATE);
        assertThat(testProduct.getDiscountInterval()).isEqualTo(UPDATED_DISCOUNT_INTERVAL);
        assertThat(testProduct.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testProduct.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testProduct.getPrepareResource()).isEqualTo(UPDATED_PREPARE_RESOURCE);
        assertThat(testProduct.getIntroduceResource()).isEqualTo(UPDATED_INTRODUCE_RESOURCE);
        assertThat(testProduct.getShippingResource()).isEqualTo(UPDATED_SHIPPING_RESOURCE);
        assertThat(testProduct.getRefundResource()).isEqualTo(UPDATED_REFUND_RESOURCE);
        assertThat(testProduct.getChangeResource()).isEqualTo(UPDATED_CHANGE_RESOURCE);
        assertThat(testProduct.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProduct.getInstallmentMonth()).isEqualTo(UPDATED_INSTALLMENT_MONTH);
        assertThat(testProduct.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProduct.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testProduct.getPopularCount()).isEqualTo(UPDATED_POPULAR_COUNT);
        assertThat(testProduct.getReviewCount()).isEqualTo(UPDATED_REVIEW_COUNT);
    }

    @Test
    @Transactional
    void putNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .thumbnail(UPDATED_THUMBNAIL)
            .regdate(UPDATED_REGDATE)
            .priceRegular(UPDATED_PRICE_REGULAR)
            .discountUnit(UPDATED_DISCOUNT_UNIT)
            .discountValue(UPDATED_DISCOUNT_VALUE)
            .discountStartdate(UPDATED_DISCOUNT_STARTDATE)
            .discountInterval(UPDATED_DISCOUNT_INTERVAL)
            .video(UPDATED_VIDEO)
            .introduceResource(UPDATED_INTRODUCE_RESOURCE)
            .shippingResource(UPDATED_SHIPPING_RESOURCE)
            .refundResource(UPDATED_REFUND_RESOURCE)
            .installmentMonth(UPDATED_INSTALLMENT_MONTH)
            .type(UPDATED_TYPE)
            .reviewCount(UPDATED_REVIEW_COUNT);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProduct.getDifficulty()).isEqualTo(DEFAULT_DIFFICULTY);
        assertThat(testProduct.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testProduct.getOwner()).isEqualTo(DEFAULT_OWNER);
        assertThat(testProduct.getRegdate()).isEqualTo(UPDATED_REGDATE);
        assertThat(testProduct.getPriceRegular()).isEqualTo(UPDATED_PRICE_REGULAR);
        assertThat(testProduct.getIsUseDiscount()).isEqualTo(DEFAULT_IS_USE_DISCOUNT);
        assertThat(testProduct.getDiscountUnit()).isEqualTo(UPDATED_DISCOUNT_UNIT);
        assertThat(testProduct.getDiscountValue()).isEqualTo(UPDATED_DISCOUNT_VALUE);
        assertThat(testProduct.getDiscountStartdate()).isEqualTo(UPDATED_DISCOUNT_STARTDATE);
        assertThat(testProduct.getDiscountInterval()).isEqualTo(UPDATED_DISCOUNT_INTERVAL);
        assertThat(testProduct.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testProduct.getStartdate()).isEqualTo(DEFAULT_STARTDATE);
        assertThat(testProduct.getPrepareResource()).isEqualTo(DEFAULT_PREPARE_RESOURCE);
        assertThat(testProduct.getIntroduceResource()).isEqualTo(UPDATED_INTRODUCE_RESOURCE);
        assertThat(testProduct.getShippingResource()).isEqualTo(UPDATED_SHIPPING_RESOURCE);
        assertThat(testProduct.getRefundResource()).isEqualTo(UPDATED_REFUND_RESOURCE);
        assertThat(testProduct.getChangeResource()).isEqualTo(DEFAULT_CHANGE_RESOURCE);
        assertThat(testProduct.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProduct.getInstallmentMonth()).isEqualTo(UPDATED_INSTALLMENT_MONTH);
        assertThat(testProduct.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProduct.getNumber()).isEqualTo(DEFAULT_NUMBER);
        assertThat(testProduct.getPopularCount()).isEqualTo(DEFAULT_POPULAR_COUNT);
        assertThat(testProduct.getReviewCount()).isEqualTo(UPDATED_REVIEW_COUNT);
    }

    @Test
    @Transactional
    void fullUpdateProductWithPatch() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeUpdate = productRepository.findAll().size();

        // Update the product using partial update
        Product partialUpdatedProduct = new Product();
        partialUpdatedProduct.setId(product.getId());

        partialUpdatedProduct
            .name(UPDATED_NAME)
            .difficulty(UPDATED_DIFFICULTY)
            .thumbnail(UPDATED_THUMBNAIL)
            .owner(UPDATED_OWNER)
            .regdate(UPDATED_REGDATE)
            .priceRegular(UPDATED_PRICE_REGULAR)
            .isUseDiscount(UPDATED_IS_USE_DISCOUNT)
            .discountUnit(UPDATED_DISCOUNT_UNIT)
            .discountValue(UPDATED_DISCOUNT_VALUE)
            .discountStartdate(UPDATED_DISCOUNT_STARTDATE)
            .discountInterval(UPDATED_DISCOUNT_INTERVAL)
            .video(UPDATED_VIDEO)
            .startdate(UPDATED_STARTDATE)
            .prepareResource(UPDATED_PREPARE_RESOURCE)
            .introduceResource(UPDATED_INTRODUCE_RESOURCE)
            .shippingResource(UPDATED_SHIPPING_RESOURCE)
            .refundResource(UPDATED_REFUND_RESOURCE)
            .changeResource(UPDATED_CHANGE_RESOURCE)
            .code(UPDATED_CODE)
            .installmentMonth(UPDATED_INSTALLMENT_MONTH)
            .type(UPDATED_TYPE)
            .number(UPDATED_NUMBER)
            .popularCount(UPDATED_POPULAR_COUNT)
            .reviewCount(UPDATED_REVIEW_COUNT);

        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProduct.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProduct))
            )
            .andExpect(status().isOk());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
        Product testProduct = productList.get(productList.size() - 1);
        assertThat(testProduct.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProduct.getDifficulty()).isEqualTo(UPDATED_DIFFICULTY);
        assertThat(testProduct.getThumbnail()).isEqualTo(UPDATED_THUMBNAIL);
        assertThat(testProduct.getOwner()).isEqualTo(UPDATED_OWNER);
        assertThat(testProduct.getRegdate()).isEqualTo(UPDATED_REGDATE);
        assertThat(testProduct.getPriceRegular()).isEqualTo(UPDATED_PRICE_REGULAR);
        assertThat(testProduct.getIsUseDiscount()).isEqualTo(UPDATED_IS_USE_DISCOUNT);
        assertThat(testProduct.getDiscountUnit()).isEqualTo(UPDATED_DISCOUNT_UNIT);
        assertThat(testProduct.getDiscountValue()).isEqualTo(UPDATED_DISCOUNT_VALUE);
        assertThat(testProduct.getDiscountStartdate()).isEqualTo(UPDATED_DISCOUNT_STARTDATE);
        assertThat(testProduct.getDiscountInterval()).isEqualTo(UPDATED_DISCOUNT_INTERVAL);
        assertThat(testProduct.getVideo()).isEqualTo(UPDATED_VIDEO);
        assertThat(testProduct.getStartdate()).isEqualTo(UPDATED_STARTDATE);
        assertThat(testProduct.getPrepareResource()).isEqualTo(UPDATED_PREPARE_RESOURCE);
        assertThat(testProduct.getIntroduceResource()).isEqualTo(UPDATED_INTRODUCE_RESOURCE);
        assertThat(testProduct.getShippingResource()).isEqualTo(UPDATED_SHIPPING_RESOURCE);
        assertThat(testProduct.getRefundResource()).isEqualTo(UPDATED_REFUND_RESOURCE);
        assertThat(testProduct.getChangeResource()).isEqualTo(UPDATED_CHANGE_RESOURCE);
        assertThat(testProduct.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProduct.getInstallmentMonth()).isEqualTo(UPDATED_INSTALLMENT_MONTH);
        assertThat(testProduct.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProduct.getNumber()).isEqualTo(UPDATED_NUMBER);
        assertThat(testProduct.getPopularCount()).isEqualTo(UPDATED_POPULAR_COUNT);
        assertThat(testProduct.getReviewCount()).isEqualTo(UPDATED_REVIEW_COUNT);
    }

    @Test
    @Transactional
    void patchNonExistingProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProduct() throws Exception {
        int databaseSizeBeforeUpdate = productRepository.findAll().size();
        product.setId(count.incrementAndGet());

        // Create the Product
        ProductDTO productDTO = productMapper.toDto(product);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Product in the database
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProduct() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        int databaseSizeBeforeDelete = productRepository.findAll().size();

        // Delete the product
        restProductMockMvc
            .perform(delete(ENTITY_API_URL_ID, product.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Product> productList = productRepository.findAll();
        assertThat(productList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
