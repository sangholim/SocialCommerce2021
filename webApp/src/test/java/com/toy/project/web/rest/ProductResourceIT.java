package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.repository.ProductRepository;
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

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_CLASS_OPTION = false;
    private static final Boolean UPDATED_USE_CLASS_OPTION = true;

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;

    private static final Boolean DEFAULT_USE_DISCOUNT_INSTANT = false;
    private static final Boolean UPDATED_USE_DISCOUNT_INSTANT = true;

    private static final Boolean DEFAULT_USE_INSTALLMENT = false;
    private static final Boolean UPDATED_USE_INSTALLMENT = true;

    private static final Integer DEFAULT_INSTALLMENT_MONTH = 1;
    private static final Integer UPDATED_INSTALLMENT_MONTH = 2;

    private static final Boolean DEFAULT_USE_SELL_DATE = false;
    private static final Boolean UPDATED_USE_SELL_DATE = true;

    private static final Instant DEFAULT_SELL_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SELL_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SELL_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SELL_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_QUANTITY = 1;
    private static final Integer UPDATED_QUANTITY = 2;

    private static final Boolean DEFAULT_USE_PRODUCT_OPTION = false;
    private static final Boolean UPDATED_USE_PRODUCT_OPTION = true;

    private static final Boolean DEFAULT_USE_PRODUCT_INPUT_OPTION = false;
    private static final Boolean UPDATED_USE_PRODUCT_INPUT_OPTION = true;

    private static final Boolean DEFAULT_USE_PRODUCT_ADD_OPTION = false;
    private static final Boolean UPDATED_USE_PRODUCT_ADD_OPTION = true;

    private static final Integer DEFAULT_MIN_PURCHASE_AMOUNT = 1;
    private static final Integer UPDATED_MIN_PURCHASE_AMOUNT = 2;

    private static final Integer DEFAULT_MAX_PURCHASE_AMOUNT_PER_COUNT = 1;
    private static final Integer UPDATED_MAX_PURCHASE_AMOUNT_PER_COUNT = 2;

    private static final Integer DEFAULT_MAX_PURCHASE_AMOUNT_PER_ONE = 1;
    private static final Integer UPDATED_MAX_PURCHASE_AMOUNT_PER_ONE = 2;

    private static final String DEFAULT_MAIN_IMAGE_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_IMAGE_FILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_VIDEO_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_VIDEO_FILE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_PRODUCT_ANNOUNCE = false;
    private static final Boolean UPDATED_USE_PRODUCT_ANNOUNCE = true;

    private static final Boolean DEFAULT_USE_PRODUCT_FAQ = false;
    private static final Boolean UPDATED_USE_PRODUCT_FAQ = true;

    private static final String DEFAULT_DESCRIPTION_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_FILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_RELEASE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_RELEASE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_STANDARD_START_TIME = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_STANDARD_START_TIME = "BBBBBBBBBB";

    private static final String DEFAULT_ETC_SHIPPING_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_ETC_SHIPPING_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SEPARATE_SHIPPING_PRICE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_BUNDLE_SHIPPING_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BUNDLE_SHIPPING_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_DEFAULT_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_DEFAULT_SHIPPING_PRICE = 2;

    private static final Integer DEFAULT_FREE_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_FREE_SHIPPING_PRICE = 2;

    private static final Integer DEFAULT_JEJU_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_JEJU_SHIPPING_PRICE = 2;

    private static final Integer DEFAULT_DIFFICULT_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_DIFFICULT_SHIPPING_PRICE = 2;

    private static final Integer DEFAULT_REFUND_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_REFUND_SHIPPING_PRICE = 2;

    private static final Integer DEFAULT_EXCHANGE_SHIPPING_PRICE = 1;
    private static final Integer UPDATED_EXCHANGE_SHIPPING_PRICE = 2;

    private static final String DEFAULT_EXCHANGE_SHIPPING_FILE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_SHIPPING_FILE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE_SHIPPING_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_SHIPPING_FILE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_VIEW = false;
    private static final Boolean UPDATED_USE_VIEW = true;

    private static final Boolean DEFAULT_USE_VIEW_RESERVATION = false;
    private static final Boolean UPDATED_USE_VIEW_RESERVATION = true;

    private static final Instant DEFAULT_VIEW_RESERVATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VIEW_RESERVATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_USE_PRODUCT_NOTICE = false;
    private static final Boolean UPDATED_USE_PRODUCT_NOTICE = true;

    private static final Boolean DEFAULT_USE_PRODUCT_ILLEGAL_USE = false;
    private static final Boolean UPDATED_USE_PRODUCT_ILLEGAL_USE = true;

    private static final Boolean DEFAULT_USE_PRODUCT_RECOMMEND = false;
    private static final Boolean UPDATED_USE_PRODUCT_RECOMMEND = true;

    private static final Boolean DEFAULT_USE_PRODUCT_MAPPING = false;
    private static final Boolean UPDATED_USE_PRODUCT_MAPPING = true;

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
        Product product = new Product();
        product.setName(DEFAULT_NAME);
        product.setType(DEFAULT_TYPE);
        product.setStatus(DEFAULT_STATUS);
        product.setCode(DEFAULT_CODE);
        product.setQuantity(DEFAULT_QUANTITY);
        product.setMinPurchaseAmount(DEFAULT_MIN_PURCHASE_AMOUNT);
        product.setMaxPurchaseAmountPerCount(DEFAULT_MAX_PURCHASE_AMOUNT_PER_COUNT);
        product.setMaxPurchaseAmountPerOne(DEFAULT_MAX_PURCHASE_AMOUNT_PER_ONE);
        product.setMainImageFileUrl(DEFAULT_MAIN_IMAGE_FILE_URL);
        product.setMainVideoFileUrl(DEFAULT_MAIN_VIDEO_FILE_URL);
        product.setDescriptionFileUrl(DEFAULT_DESCRIPTION_FILE_URL);
        product.setViewReservationDate(DEFAULT_VIEW_RESERVATION_DATE);
        product.setActivated(DEFAULT_ACTIVATED);
        product.setCreatedBy(DEFAULT_CREATED_BY);
        product.setCreatedDate(DEFAULT_CREATED_DATE);
        product.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        product.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return product;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Product createUpdatedEntity(EntityManager em) {
        Product product = new Product();
        product.setName(DEFAULT_NAME);
        product.setType(DEFAULT_TYPE);
        product.setStatus(DEFAULT_STATUS);
        product.setCode(DEFAULT_CODE);
        product.setQuantity(DEFAULT_QUANTITY);
        product.setMinPurchaseAmount(DEFAULT_MIN_PURCHASE_AMOUNT);
        product.setMaxPurchaseAmountPerCount(DEFAULT_MAX_PURCHASE_AMOUNT_PER_COUNT);
        product.setMaxPurchaseAmountPerOne(DEFAULT_MAX_PURCHASE_AMOUNT_PER_ONE);
        product.setMainImageFileUrl(DEFAULT_MAIN_IMAGE_FILE_URL);
        product.setMainVideoFileUrl(DEFAULT_MAIN_VIDEO_FILE_URL);
        product.setDescriptionFileUrl(DEFAULT_DESCRIPTION_FILE_URL);
        product.setViewReservationDate(DEFAULT_VIEW_RESERVATION_DATE);
        product.setActivated(DEFAULT_ACTIVATED);
        product.setCreatedBy(DEFAULT_CREATED_BY);
        product.setCreatedDate(DEFAULT_CREATED_DATE);
        product.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        product.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
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
        assertThat(testProduct.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProduct.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProduct.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProduct.getUseClassOption()).isEqualTo(DEFAULT_USE_CLASS_OPTION);
        assertThat(testProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduct.getUseProductOption()).isEqualTo(DEFAULT_USE_PRODUCT_OPTION);
        assertThat(testProduct.getUseProductInputOption()).isEqualTo(DEFAULT_USE_PRODUCT_INPUT_OPTION);
        assertThat(testProduct.getUseProductAddOption()).isEqualTo(DEFAULT_USE_PRODUCT_ADD_OPTION);
        assertThat(testProduct.getMinPurchaseAmount()).isEqualTo(DEFAULT_MIN_PURCHASE_AMOUNT);
        assertThat(testProduct.getMaxPurchaseAmountPerCount()).isEqualTo(DEFAULT_MAX_PURCHASE_AMOUNT_PER_COUNT);
        assertThat(testProduct.getMaxPurchaseAmountPerOne()).isEqualTo(DEFAULT_MAX_PURCHASE_AMOUNT_PER_ONE);
        assertThat(testProduct.getMainImageFileUrl()).isEqualTo(DEFAULT_MAIN_IMAGE_FILE_URL);
        assertThat(testProduct.getMainVideoFileUrl()).isEqualTo(DEFAULT_MAIN_VIDEO_FILE_URL);
        assertThat(testProduct.getUseProductAnnounce()).isEqualTo(DEFAULT_USE_PRODUCT_ANNOUNCE);
        assertThat(testProduct.getUseProductFaq()).isEqualTo(DEFAULT_USE_PRODUCT_FAQ);
        assertThat(testProduct.getDescriptionFileUrl()).isEqualTo(DEFAULT_DESCRIPTION_FILE_URL);
        assertThat(testProduct.getUseView()).isEqualTo(DEFAULT_USE_VIEW);
        assertThat(testProduct.getUseViewReservation()).isEqualTo(DEFAULT_USE_VIEW_RESERVATION);
        assertThat(testProduct.getViewReservationDate()).isEqualTo(DEFAULT_VIEW_RESERVATION_DATE);
        assertThat(testProduct.getUseProductNotice()).isEqualTo(DEFAULT_USE_PRODUCT_NOTICE);
        assertThat(testProduct.getUseProductIllegal()).isEqualTo(DEFAULT_USE_PRODUCT_ILLEGAL_USE);
        assertThat(testProduct.getUseProductRecommend()).isEqualTo(DEFAULT_USE_PRODUCT_RECOMMEND);
        assertThat(testProduct.getUseProductMapping()).isEqualTo(DEFAULT_USE_PRODUCT_MAPPING);
        assertThat(testProduct.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProduct.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProduct.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProduct.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProduct.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
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
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].installmentMonth").value(hasItem(DEFAULT_INSTALLMENT_MONTH)))
            .andExpect(jsonPath("$.[*].sellDateFrom").value(hasItem(DEFAULT_SELL_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].sellDateTo").value(hasItem(DEFAULT_SELL_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY)))
            .andExpect(jsonPath("$.[*].minPurchaseAmount").value(hasItem(DEFAULT_MIN_PURCHASE_AMOUNT)))
            .andExpect(jsonPath("$.[*].maxPurchaseAmountPerCount").value(hasItem(DEFAULT_MAX_PURCHASE_AMOUNT_PER_COUNT)))
            .andExpect(jsonPath("$.[*].maxPurchaseAmountPerOne").value(hasItem(DEFAULT_MAX_PURCHASE_AMOUNT_PER_ONE)))
            .andExpect(jsonPath("$.[*].mainImageFileUrl").value(hasItem(DEFAULT_MAIN_IMAGE_FILE_URL)))
            .andExpect(jsonPath("$.[*].mainVideoFileUrl").value(hasItem(DEFAULT_MAIN_VIDEO_FILE_URL)))
            .andExpect(jsonPath("$.[*].descriptionFileUrl").value(hasItem(DEFAULT_DESCRIPTION_FILE_URL)))
            .andExpect(jsonPath("$.[*].shippingReleaseType").value(hasItem(DEFAULT_SHIPPING_RELEASE_TYPE)))
            .andExpect(jsonPath("$.[*].shippingStandardStartTime").value(hasItem(DEFAULT_SHIPPING_STANDARD_START_TIME)))
            .andExpect(jsonPath("$.[*].etcShippingContent").value(hasItem(DEFAULT_ETC_SHIPPING_CONTENT)))
            .andExpect(jsonPath("$.[*].separateShippingPriceType").value(hasItem(DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE)))
            .andExpect(jsonPath("$.[*].bundleShippingType").value(hasItem(DEFAULT_BUNDLE_SHIPPING_TYPE)))
            .andExpect(jsonPath("$.[*].defaultShippingPrice").value(hasItem(DEFAULT_DEFAULT_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].freeShippingPrice").value(hasItem(DEFAULT_FREE_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].jejuShippingPrice").value(hasItem(DEFAULT_JEJU_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].difficultShippingPrice").value(hasItem(DEFAULT_DIFFICULT_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].refundShippingPrice").value(hasItem(DEFAULT_REFUND_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].exchangeShippingPrice").value(hasItem(DEFAULT_EXCHANGE_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].exchangeShippingFileType").value(hasItem(DEFAULT_EXCHANGE_SHIPPING_FILE_TYPE)))
            .andExpect(jsonPath("$.[*].exchangeShippingFileUrl").value(hasItem(DEFAULT_EXCHANGE_SHIPPING_FILE_URL)))
            .andExpect(jsonPath("$.[*].viewReservationDate").value(hasItem(DEFAULT_VIEW_RESERVATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
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
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.installmentMonth").value(DEFAULT_INSTALLMENT_MONTH))
            .andExpect(jsonPath("$.sellDateFrom").value(DEFAULT_SELL_DATE_FROM.toString()))
            .andExpect(jsonPath("$.sellDateTo").value(DEFAULT_SELL_DATE_TO.toString()))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY))
            .andExpect(jsonPath("$.minPurchaseAmount").value(DEFAULT_MIN_PURCHASE_AMOUNT))
            .andExpect(jsonPath("$.maxPurchaseAmountPerCount").value(DEFAULT_MAX_PURCHASE_AMOUNT_PER_COUNT))
            .andExpect(jsonPath("$.maxPurchaseAmountPerOne").value(DEFAULT_MAX_PURCHASE_AMOUNT_PER_ONE))
            .andExpect(jsonPath("$.mainImageFileUrl").value(DEFAULT_MAIN_IMAGE_FILE_URL))
            .andExpect(jsonPath("$.mainVideoFileUrl").value(DEFAULT_MAIN_VIDEO_FILE_URL))
            .andExpect(jsonPath("$.descriptionFileUrl").value(DEFAULT_DESCRIPTION_FILE_URL))
            .andExpect(jsonPath("$.shippingReleaseType").value(DEFAULT_SHIPPING_RELEASE_TYPE))
            .andExpect(jsonPath("$.shippingStandardStartTime").value(DEFAULT_SHIPPING_STANDARD_START_TIME))
            .andExpect(jsonPath("$.etcShippingContent").value(DEFAULT_ETC_SHIPPING_CONTENT))
            .andExpect(jsonPath("$.separateShippingPriceType").value(DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE))
            .andExpect(jsonPath("$.bundleShippingType").value(DEFAULT_BUNDLE_SHIPPING_TYPE))
            .andExpect(jsonPath("$.defaultShippingPrice").value(DEFAULT_DEFAULT_SHIPPING_PRICE))
            .andExpect(jsonPath("$.freeShippingPrice").value(DEFAULT_FREE_SHIPPING_PRICE))
            .andExpect(jsonPath("$.jejuShippingPrice").value(DEFAULT_JEJU_SHIPPING_PRICE))
            .andExpect(jsonPath("$.difficultShippingPrice").value(DEFAULT_DIFFICULT_SHIPPING_PRICE))
            .andExpect(jsonPath("$.refundShippingPrice").value(DEFAULT_REFUND_SHIPPING_PRICE))
            .andExpect(jsonPath("$.exchangeShippingPrice").value(DEFAULT_EXCHANGE_SHIPPING_PRICE))
            .andExpect(jsonPath("$.exchangeShippingFileType").value(DEFAULT_EXCHANGE_SHIPPING_FILE_TYPE))
            .andExpect(jsonPath("$.exchangeShippingFileUrl").value(DEFAULT_EXCHANGE_SHIPPING_FILE_URL))
            .andExpect(jsonPath("$.viewReservationDate").value(DEFAULT_VIEW_RESERVATION_DATE.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
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
        updatedProduct.setName(DEFAULT_NAME);
        updatedProduct.setType(DEFAULT_TYPE);
        updatedProduct.setStatus(DEFAULT_STATUS);
        updatedProduct.setCode(DEFAULT_CODE);
        updatedProduct.setQuantity(DEFAULT_QUANTITY);
        updatedProduct.setMinPurchaseAmount(DEFAULT_MIN_PURCHASE_AMOUNT);
        updatedProduct.setMaxPurchaseAmountPerCount(DEFAULT_MAX_PURCHASE_AMOUNT_PER_COUNT);
        updatedProduct.setMaxPurchaseAmountPerOne(DEFAULT_MAX_PURCHASE_AMOUNT_PER_ONE);
        updatedProduct.setMainImageFileUrl(DEFAULT_MAIN_IMAGE_FILE_URL);
        updatedProduct.setMainVideoFileUrl(DEFAULT_MAIN_VIDEO_FILE_URL);
        updatedProduct.setDescriptionFileUrl(DEFAULT_DESCRIPTION_FILE_URL);
        updatedProduct.setViewReservationDate(DEFAULT_VIEW_RESERVATION_DATE);
        updatedProduct.setActivated(DEFAULT_ACTIVATED);
        updatedProduct.setCreatedBy(DEFAULT_CREATED_BY);
        updatedProduct.setCreatedDate(DEFAULT_CREATED_DATE);
        updatedProduct.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        updatedProduct.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
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
        assertThat(testProduct.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProduct.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProduct.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProduct.getUseClassOption()).isEqualTo(UPDATED_USE_CLASS_OPTION);
        assertThat(testProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduct.getUseProductOption()).isEqualTo(UPDATED_USE_PRODUCT_OPTION);
        assertThat(testProduct.getUseProductInputOption()).isEqualTo(UPDATED_USE_PRODUCT_INPUT_OPTION);
        assertThat(testProduct.getUseProductAddOption()).isEqualTo(UPDATED_USE_PRODUCT_ADD_OPTION);
        assertThat(testProduct.getMinPurchaseAmount()).isEqualTo(UPDATED_MIN_PURCHASE_AMOUNT);
        assertThat(testProduct.getMaxPurchaseAmountPerCount()).isEqualTo(UPDATED_MAX_PURCHASE_AMOUNT_PER_COUNT);
        assertThat(testProduct.getMaxPurchaseAmountPerOne()).isEqualTo(UPDATED_MAX_PURCHASE_AMOUNT_PER_ONE);
        assertThat(testProduct.getMainImageFileUrl()).isEqualTo(UPDATED_MAIN_IMAGE_FILE_URL);
        assertThat(testProduct.getMainVideoFileUrl()).isEqualTo(UPDATED_MAIN_VIDEO_FILE_URL);
        assertThat(testProduct.getUseProductAnnounce()).isEqualTo(UPDATED_USE_PRODUCT_ANNOUNCE);
        assertThat(testProduct.getUseProductFaq()).isEqualTo(UPDATED_USE_PRODUCT_FAQ);
        assertThat(testProduct.getDescriptionFileUrl()).isEqualTo(UPDATED_DESCRIPTION_FILE_URL);
        assertThat(testProduct.getUseView()).isEqualTo(UPDATED_USE_VIEW);
        assertThat(testProduct.getUseViewReservation()).isEqualTo(UPDATED_USE_VIEW_RESERVATION);
        assertThat(testProduct.getViewReservationDate()).isEqualTo(UPDATED_VIEW_RESERVATION_DATE);
        assertThat(testProduct.getUseProductNotice()).isEqualTo(UPDATED_USE_PRODUCT_NOTICE);
        assertThat(testProduct.getUseProductIllegal()).isEqualTo(UPDATED_USE_PRODUCT_ILLEGAL_USE);
        assertThat(testProduct.getUseProductRecommend()).isEqualTo(UPDATED_USE_PRODUCT_RECOMMEND);
        assertThat(testProduct.getUseProductMapping()).isEqualTo(UPDATED_USE_PRODUCT_MAPPING);
        assertThat(testProduct.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProduct.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProduct.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProduct.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
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
        partialUpdatedProduct.setName(UPDATED_NAME);
        partialUpdatedProduct.setType(UPDATED_TYPE);
        partialUpdatedProduct.setStatus(UPDATED_STATUS);
        partialUpdatedProduct.setCode(UPDATED_CODE);
        partialUpdatedProduct.setQuantity(UPDATED_QUANTITY);
        partialUpdatedProduct.setMinPurchaseAmount(UPDATED_MIN_PURCHASE_AMOUNT);
        partialUpdatedProduct.setMaxPurchaseAmountPerCount(UPDATED_MAX_PURCHASE_AMOUNT_PER_COUNT);
        partialUpdatedProduct.setMaxPurchaseAmountPerOne(UPDATED_MAX_PURCHASE_AMOUNT_PER_ONE);
        partialUpdatedProduct.setMainImageFileUrl(UPDATED_MAIN_IMAGE_FILE_URL);
        partialUpdatedProduct.setMainVideoFileUrl(UPDATED_MAIN_VIDEO_FILE_URL);
        partialUpdatedProduct.setDescriptionFileUrl(UPDATED_DESCRIPTION_FILE_URL);
        partialUpdatedProduct.setViewReservationDate(UPDATED_VIEW_RESERVATION_DATE);
        partialUpdatedProduct.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProduct.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProduct.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProduct.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProduct.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

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
        assertThat(testProduct.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProduct.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProduct.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProduct.getUseClassOption()).isEqualTo(UPDATED_USE_CLASS_OPTION);
        assertThat(testProduct.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProduct.getUseProductOption()).isEqualTo(DEFAULT_USE_PRODUCT_OPTION);
        assertThat(testProduct.getUseProductInputOption()).isEqualTo(UPDATED_USE_PRODUCT_INPUT_OPTION);
        assertThat(testProduct.getUseProductAddOption()).isEqualTo(UPDATED_USE_PRODUCT_ADD_OPTION);
        assertThat(testProduct.getMinPurchaseAmount()).isEqualTo(UPDATED_MIN_PURCHASE_AMOUNT);
        assertThat(testProduct.getMaxPurchaseAmountPerCount()).isEqualTo(DEFAULT_MAX_PURCHASE_AMOUNT_PER_COUNT);
        assertThat(testProduct.getMaxPurchaseAmountPerOne()).isEqualTo(DEFAULT_MAX_PURCHASE_AMOUNT_PER_ONE);
        assertThat(testProduct.getMainImageFileUrl()).isEqualTo(UPDATED_MAIN_IMAGE_FILE_URL);
        assertThat(testProduct.getMainVideoFileUrl()).isEqualTo(UPDATED_MAIN_VIDEO_FILE_URL);
        assertThat(testProduct.getUseProductAnnounce()).isEqualTo(DEFAULT_USE_PRODUCT_ANNOUNCE);
        assertThat(testProduct.getUseProductFaq()).isEqualTo(DEFAULT_USE_PRODUCT_FAQ);
        assertThat(testProduct.getDescriptionFileUrl()).isEqualTo(UPDATED_DESCRIPTION_FILE_URL);
        assertThat(testProduct.getUseView()).isEqualTo(DEFAULT_USE_VIEW);
        assertThat(testProduct.getUseViewReservation()).isEqualTo(UPDATED_USE_VIEW_RESERVATION);
        assertThat(testProduct.getViewReservationDate()).isEqualTo(DEFAULT_VIEW_RESERVATION_DATE);
        assertThat(testProduct.getUseProductNotice()).isEqualTo(DEFAULT_USE_PRODUCT_NOTICE);
        assertThat(testProduct.getUseProductIllegal()).isEqualTo(DEFAULT_USE_PRODUCT_ILLEGAL_USE);
        assertThat(testProduct.getUseProductRecommend()).isEqualTo(DEFAULT_USE_PRODUCT_RECOMMEND);
        assertThat(testProduct.getUseProductMapping()).isEqualTo(UPDATED_USE_PRODUCT_MAPPING);
        assertThat(testProduct.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProduct.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProduct.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProduct.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
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

        partialUpdatedProduct.setName(UPDATED_NAME);
        partialUpdatedProduct.setType(UPDATED_TYPE);
        partialUpdatedProduct.setStatus(UPDATED_STATUS);
        partialUpdatedProduct.setCode(UPDATED_CODE);
        partialUpdatedProduct.setQuantity(UPDATED_QUANTITY);
        partialUpdatedProduct.setMinPurchaseAmount(UPDATED_MIN_PURCHASE_AMOUNT);
        partialUpdatedProduct.setMaxPurchaseAmountPerCount(UPDATED_MAX_PURCHASE_AMOUNT_PER_COUNT);
        partialUpdatedProduct.setMaxPurchaseAmountPerOne(UPDATED_MAX_PURCHASE_AMOUNT_PER_ONE);
        partialUpdatedProduct.setMainImageFileUrl(UPDATED_MAIN_IMAGE_FILE_URL);
        partialUpdatedProduct.setMainVideoFileUrl(UPDATED_MAIN_VIDEO_FILE_URL);
        partialUpdatedProduct.setDescriptionFileUrl(UPDATED_DESCRIPTION_FILE_URL);
        partialUpdatedProduct.setViewReservationDate(UPDATED_VIEW_RESERVATION_DATE);
        partialUpdatedProduct.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProduct.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProduct.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProduct.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProduct.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

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
        assertThat(testProduct.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProduct.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProduct.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProduct.getUseClassOption()).isEqualTo(UPDATED_USE_CLASS_OPTION);
        assertThat(testProduct.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProduct.getUseProductOption()).isEqualTo(UPDATED_USE_PRODUCT_OPTION);
        assertThat(testProduct.getUseProductInputOption()).isEqualTo(UPDATED_USE_PRODUCT_INPUT_OPTION);
        assertThat(testProduct.getUseProductAddOption()).isEqualTo(UPDATED_USE_PRODUCT_ADD_OPTION);
        assertThat(testProduct.getMinPurchaseAmount()).isEqualTo(UPDATED_MIN_PURCHASE_AMOUNT);
        assertThat(testProduct.getMaxPurchaseAmountPerCount()).isEqualTo(UPDATED_MAX_PURCHASE_AMOUNT_PER_COUNT);
        assertThat(testProduct.getMaxPurchaseAmountPerOne()).isEqualTo(UPDATED_MAX_PURCHASE_AMOUNT_PER_ONE);
        assertThat(testProduct.getMainImageFileUrl()).isEqualTo(UPDATED_MAIN_IMAGE_FILE_URL);
        assertThat(testProduct.getMainVideoFileUrl()).isEqualTo(UPDATED_MAIN_VIDEO_FILE_URL);
        assertThat(testProduct.getUseProductAnnounce()).isEqualTo(UPDATED_USE_PRODUCT_ANNOUNCE);
        assertThat(testProduct.getUseProductFaq()).isEqualTo(UPDATED_USE_PRODUCT_FAQ);
        assertThat(testProduct.getDescriptionFileUrl()).isEqualTo(UPDATED_DESCRIPTION_FILE_URL);
        assertThat(testProduct.getUseView()).isEqualTo(UPDATED_USE_VIEW);
        assertThat(testProduct.getUseViewReservation()).isEqualTo(UPDATED_USE_VIEW_RESERVATION);
        assertThat(testProduct.getViewReservationDate()).isEqualTo(UPDATED_VIEW_RESERVATION_DATE);
        assertThat(testProduct.getUseProductNotice()).isEqualTo(UPDATED_USE_PRODUCT_NOTICE);
        assertThat(testProduct.getUseProductIllegal()).isEqualTo(UPDATED_USE_PRODUCT_ILLEGAL_USE);
        assertThat(testProduct.getUseProductRecommend()).isEqualTo(UPDATED_USE_PRODUCT_RECOMMEND);
        assertThat(testProduct.getUseProductMapping()).isEqualTo(UPDATED_USE_PRODUCT_MAPPING);
        assertThat(testProduct.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProduct.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProduct.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProduct.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
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
