package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Product;
import com.toy.project.domain.ProductCategoryRel;
import com.toy.project.domain.ProductClazzRel;
import com.toy.project.domain.ProductLabelRel;
import com.toy.project.domain.ProductMappingRel;
import com.toy.project.domain.ProductNoticeRel;
import com.toy.project.domain.ProductOptionRel;
import com.toy.project.domain.ProductShippingRel;
import com.toy.project.domain.ProductStoreRel;
import com.toy.project.domain.ProductTemplateRel;
import com.toy.project.domain.ProductViewRel;
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
import org.springframework.util.Base64Utils;

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

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CALCULATION = "AAAAAAAAAA";
    private static final String UPDATED_CALCULATION = "BBBBBBBBBB";

    private static final Instant DEFAULT_CALCULATION_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATION_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CALCULATION_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATION_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_PRICE = 1;
    private static final Integer UPDATED_PRICE = 2;
    private static final Integer SMALLER_PRICE = 1 - 1;

    private static final String DEFAULT_ALL_PRICE_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_ALL_PRICE_UNIT = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_PRICE = "BBBBBBBBBB";

    private static final String DEFAULT_DISCOUNT_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_UNIT = "BBBBBBBBBB";

    private static final Instant DEFAULT_DISCOUNT_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISCOUNT_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DISCOUNT_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISCOUNT_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_IS_INSTALLMENT = false;
    private static final Boolean UPDATED_IS_INSTALLMENT = true;

    private static final Integer DEFAULT_INSTALLMENT_MONTH = 1;
    private static final Integer UPDATED_INSTALLMENT_MONTH = 2;
    private static final Integer SMALLER_INSTALLMENT_MONTH = 1 - 1;

    private static final Boolean DEFAULT_IS_SELL = false;
    private static final Boolean UPDATED_IS_SELL = true;

    private static final Instant DEFAULT_SELL_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SELL_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_SELL_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_SELL_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_MIN_PURCHASE_AMOUNT = 1;
    private static final Integer UPDATED_MIN_PURCHASE_AMOUNT = 2;
    private static final Integer SMALLER_MIN_PURCHASE_AMOUNT = 1 - 1;

    private static final Integer DEFAULT_MAN_PURCHASE_AMOUNT = 1;
    private static final Integer UPDATED_MAN_PURCHASE_AMOUNT = 2;
    private static final Integer SMALLER_MAN_PURCHASE_AMOUNT = 1 - 1;

    private static final String DEFAULT_MAIN_IMAGE_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_IMAGE_FILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_ADD_IMAGE_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_ADD_IMAGE_FILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_VIDEO_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_VIDEO_FILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION_FILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SEPARATE_SHIPPING_PRICE_TYPE = "BBBBBBBBBB";

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

    private static final String DEFAULT_EXCHANGE_SHIPPING_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_SHIPPING_FILE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_VIEW = false;
    private static final Boolean UPDATED_IS_VIEW = true;

    private static final Instant DEFAULT_VIEW_RESERVATION_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_VIEW_RESERVATION_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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
        Product product = new Product()
            .name(DEFAULT_NAME)
            .code(DEFAULT_CODE)
            .calculation(DEFAULT_CALCULATION)
            .calculationDateFrom(DEFAULT_CALCULATION_DATE_FROM)
            .calculationDateTo(DEFAULT_CALCULATION_DATE_TO)
            .price(DEFAULT_PRICE)
            .allPriceUnit(DEFAULT_ALL_PRICE_UNIT)
            .discount(DEFAULT_DISCOUNT)
            .discountPrice(DEFAULT_DISCOUNT_PRICE)
            .discountUnit(DEFAULT_DISCOUNT_UNIT)
            .discountDateFrom(DEFAULT_DISCOUNT_DATE_FROM)
            .discountDateTo(DEFAULT_DISCOUNT_DATE_TO)
            .isInstallment(DEFAULT_IS_INSTALLMENT)
            .installmentMonth(DEFAULT_INSTALLMENT_MONTH)
            .isSell(DEFAULT_IS_SELL)
            .sellDateFrom(DEFAULT_SELL_DATE_FROM)
            .sellDateTo(DEFAULT_SELL_DATE_TO)
            .minPurchaseAmount(DEFAULT_MIN_PURCHASE_AMOUNT)
            .manPurchaseAmount(DEFAULT_MAN_PURCHASE_AMOUNT)
            .mainImageFileUrl(DEFAULT_MAIN_IMAGE_FILE_URL)
            .addImageFileUrl(DEFAULT_ADD_IMAGE_FILE_URL)
            .mainVideoFileUrl(DEFAULT_MAIN_VIDEO_FILE_URL)
            .descriptionFileUrl(DEFAULT_DESCRIPTION_FILE_URL)
            .shippingType(DEFAULT_SHIPPING_TYPE)
            .separateShippingPriceType(DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE)
            .defaultShippingPrice(DEFAULT_DEFAULT_SHIPPING_PRICE)
            .freeShippingPrice(DEFAULT_FREE_SHIPPING_PRICE)
            .jejuShippingPrice(DEFAULT_JEJU_SHIPPING_PRICE)
            .difficultShippingPrice(DEFAULT_DIFFICULT_SHIPPING_PRICE)
            .refundShippingPrice(DEFAULT_REFUND_SHIPPING_PRICE)
            .exchangeShippingPrice(DEFAULT_EXCHANGE_SHIPPING_PRICE)
            .exchangeShippingFileUrl(DEFAULT_EXCHANGE_SHIPPING_FILE_URL)
            .isView(DEFAULT_IS_VIEW)
            .viewReservationDate(DEFAULT_VIEW_RESERVATION_DATE)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
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
            .code(UPDATED_CODE)
            .calculation(UPDATED_CALCULATION)
            .calculationDateFrom(UPDATED_CALCULATION_DATE_FROM)
            .calculationDateTo(UPDATED_CALCULATION_DATE_TO)
            .price(UPDATED_PRICE)
            .allPriceUnit(UPDATED_ALL_PRICE_UNIT)
            .discount(UPDATED_DISCOUNT)
            .discountPrice(UPDATED_DISCOUNT_PRICE)
            .discountUnit(UPDATED_DISCOUNT_UNIT)
            .discountDateFrom(UPDATED_DISCOUNT_DATE_FROM)
            .discountDateTo(UPDATED_DISCOUNT_DATE_TO)
            .isInstallment(UPDATED_IS_INSTALLMENT)
            .installmentMonth(UPDATED_INSTALLMENT_MONTH)
            .isSell(UPDATED_IS_SELL)
            .sellDateFrom(UPDATED_SELL_DATE_FROM)
            .sellDateTo(UPDATED_SELL_DATE_TO)
            .minPurchaseAmount(UPDATED_MIN_PURCHASE_AMOUNT)
            .manPurchaseAmount(UPDATED_MAN_PURCHASE_AMOUNT)
            .mainImageFileUrl(UPDATED_MAIN_IMAGE_FILE_URL)
            .addImageFileUrl(UPDATED_ADD_IMAGE_FILE_URL)
            .mainVideoFileUrl(UPDATED_MAIN_VIDEO_FILE_URL)
            .descriptionFileUrl(UPDATED_DESCRIPTION_FILE_URL)
            .shippingType(UPDATED_SHIPPING_TYPE)
            .separateShippingPriceType(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE)
            .defaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE)
            .freeShippingPrice(UPDATED_FREE_SHIPPING_PRICE)
            .jejuShippingPrice(UPDATED_JEJU_SHIPPING_PRICE)
            .difficultShippingPrice(UPDATED_DIFFICULT_SHIPPING_PRICE)
            .refundShippingPrice(UPDATED_REFUND_SHIPPING_PRICE)
            .exchangeShippingPrice(UPDATED_EXCHANGE_SHIPPING_PRICE)
            .exchangeShippingFileUrl(UPDATED_EXCHANGE_SHIPPING_FILE_URL)
            .isView(UPDATED_IS_VIEW)
            .viewReservationDate(UPDATED_VIEW_RESERVATION_DATE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
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
        assertThat(testProduct.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProduct.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testProduct.getCalculationDateFrom()).isEqualTo(DEFAULT_CALCULATION_DATE_FROM);
        assertThat(testProduct.getCalculationDateTo()).isEqualTo(DEFAULT_CALCULATION_DATE_TO);
        assertThat(testProduct.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProduct.getAllPriceUnit()).isEqualTo(DEFAULT_ALL_PRICE_UNIT);
        assertThat(testProduct.getDiscount()).isEqualTo(DEFAULT_DISCOUNT);
        assertThat(testProduct.getDiscountPrice()).isEqualTo(DEFAULT_DISCOUNT_PRICE);
        assertThat(testProduct.getDiscountUnit()).isEqualTo(DEFAULT_DISCOUNT_UNIT);
        assertThat(testProduct.getDiscountDateFrom()).isEqualTo(DEFAULT_DISCOUNT_DATE_FROM);
        assertThat(testProduct.getDiscountDateTo()).isEqualTo(DEFAULT_DISCOUNT_DATE_TO);
        assertThat(testProduct.getIsInstallment()).isEqualTo(DEFAULT_IS_INSTALLMENT);
        assertThat(testProduct.getInstallmentMonth()).isEqualTo(DEFAULT_INSTALLMENT_MONTH);
        assertThat(testProduct.getIsSell()).isEqualTo(DEFAULT_IS_SELL);
        assertThat(testProduct.getSellDateFrom()).isEqualTo(DEFAULT_SELL_DATE_FROM);
        assertThat(testProduct.getSellDateTo()).isEqualTo(DEFAULT_SELL_DATE_TO);
        assertThat(testProduct.getMinPurchaseAmount()).isEqualTo(DEFAULT_MIN_PURCHASE_AMOUNT);
        assertThat(testProduct.getManPurchaseAmount()).isEqualTo(DEFAULT_MAN_PURCHASE_AMOUNT);
        assertThat(testProduct.getMainImageFileUrl()).isEqualTo(DEFAULT_MAIN_IMAGE_FILE_URL);
        assertThat(testProduct.getAddImageFileUrl()).isEqualTo(DEFAULT_ADD_IMAGE_FILE_URL);
        assertThat(testProduct.getMainVideoFileUrl()).isEqualTo(DEFAULT_MAIN_VIDEO_FILE_URL);
        assertThat(testProduct.getDescriptionFileUrl()).isEqualTo(DEFAULT_DESCRIPTION_FILE_URL);
        assertThat(testProduct.getShippingType()).isEqualTo(DEFAULT_SHIPPING_TYPE);
        assertThat(testProduct.getSeparateShippingPriceType()).isEqualTo(DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE);
        assertThat(testProduct.getDefaultShippingPrice()).isEqualTo(DEFAULT_DEFAULT_SHIPPING_PRICE);
        assertThat(testProduct.getFreeShippingPrice()).isEqualTo(DEFAULT_FREE_SHIPPING_PRICE);
        assertThat(testProduct.getJejuShippingPrice()).isEqualTo(DEFAULT_JEJU_SHIPPING_PRICE);
        assertThat(testProduct.getDifficultShippingPrice()).isEqualTo(DEFAULT_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProduct.getRefundShippingPrice()).isEqualTo(DEFAULT_REFUND_SHIPPING_PRICE);
        assertThat(testProduct.getExchangeShippingPrice()).isEqualTo(DEFAULT_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProduct.getExchangeShippingFileUrl()).isEqualTo(DEFAULT_EXCHANGE_SHIPPING_FILE_URL);
        assertThat(testProduct.getIsView()).isEqualTo(DEFAULT_IS_VIEW);
        assertThat(testProduct.getViewReservationDate()).isEqualTo(DEFAULT_VIEW_RESERVATION_DATE);
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
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].calculationDateFrom").value(hasItem(DEFAULT_CALCULATION_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].calculationDateTo").value(hasItem(DEFAULT_CALCULATION_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].allPriceUnit").value(hasItem(DEFAULT_ALL_PRICE_UNIT)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].discountPrice").value(hasItem(DEFAULT_DISCOUNT_PRICE)))
            .andExpect(jsonPath("$.[*].discountUnit").value(hasItem(DEFAULT_DISCOUNT_UNIT)))
            .andExpect(jsonPath("$.[*].discountDateFrom").value(hasItem(DEFAULT_DISCOUNT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].discountDateTo").value(hasItem(DEFAULT_DISCOUNT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].isInstallment").value(hasItem(DEFAULT_IS_INSTALLMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].installmentMonth").value(hasItem(DEFAULT_INSTALLMENT_MONTH)))
            .andExpect(jsonPath("$.[*].isSell").value(hasItem(DEFAULT_IS_SELL.booleanValue())))
            .andExpect(jsonPath("$.[*].sellDateFrom").value(hasItem(DEFAULT_SELL_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].sellDateTo").value(hasItem(DEFAULT_SELL_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].minPurchaseAmount").value(hasItem(DEFAULT_MIN_PURCHASE_AMOUNT)))
            .andExpect(jsonPath("$.[*].manPurchaseAmount").value(hasItem(DEFAULT_MAN_PURCHASE_AMOUNT)))
            .andExpect(jsonPath("$.[*].mainImageFileUrl").value(hasItem(DEFAULT_MAIN_IMAGE_FILE_URL)))
            .andExpect(jsonPath("$.[*].addImageFileUrl").value(hasItem(DEFAULT_ADD_IMAGE_FILE_URL.toString())))
            .andExpect(jsonPath("$.[*].mainVideoFileUrl").value(hasItem(DEFAULT_MAIN_VIDEO_FILE_URL)))
            .andExpect(jsonPath("$.[*].descriptionFileUrl").value(hasItem(DEFAULT_DESCRIPTION_FILE_URL)))
            .andExpect(jsonPath("$.[*].shippingType").value(hasItem(DEFAULT_SHIPPING_TYPE)))
            .andExpect(jsonPath("$.[*].separateShippingPriceType").value(hasItem(DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE)))
            .andExpect(jsonPath("$.[*].defaultShippingPrice").value(hasItem(DEFAULT_DEFAULT_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].freeShippingPrice").value(hasItem(DEFAULT_FREE_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].jejuShippingPrice").value(hasItem(DEFAULT_JEJU_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].difficultShippingPrice").value(hasItem(DEFAULT_DIFFICULT_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].refundShippingPrice").value(hasItem(DEFAULT_REFUND_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].exchangeShippingPrice").value(hasItem(DEFAULT_EXCHANGE_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].exchangeShippingFileUrl").value(hasItem(DEFAULT_EXCHANGE_SHIPPING_FILE_URL)))
            .andExpect(jsonPath("$.[*].isView").value(hasItem(DEFAULT_IS_VIEW.booleanValue())))
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
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.calculation").value(DEFAULT_CALCULATION))
            .andExpect(jsonPath("$.calculationDateFrom").value(DEFAULT_CALCULATION_DATE_FROM.toString()))
            .andExpect(jsonPath("$.calculationDateTo").value(DEFAULT_CALCULATION_DATE_TO.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.allPriceUnit").value(DEFAULT_ALL_PRICE_UNIT))
            .andExpect(jsonPath("$.discount").value(DEFAULT_DISCOUNT))
            .andExpect(jsonPath("$.discountPrice").value(DEFAULT_DISCOUNT_PRICE))
            .andExpect(jsonPath("$.discountUnit").value(DEFAULT_DISCOUNT_UNIT))
            .andExpect(jsonPath("$.discountDateFrom").value(DEFAULT_DISCOUNT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.discountDateTo").value(DEFAULT_DISCOUNT_DATE_TO.toString()))
            .andExpect(jsonPath("$.isInstallment").value(DEFAULT_IS_INSTALLMENT.booleanValue()))
            .andExpect(jsonPath("$.installmentMonth").value(DEFAULT_INSTALLMENT_MONTH))
            .andExpect(jsonPath("$.isSell").value(DEFAULT_IS_SELL.booleanValue()))
            .andExpect(jsonPath("$.sellDateFrom").value(DEFAULT_SELL_DATE_FROM.toString()))
            .andExpect(jsonPath("$.sellDateTo").value(DEFAULT_SELL_DATE_TO.toString()))
            .andExpect(jsonPath("$.minPurchaseAmount").value(DEFAULT_MIN_PURCHASE_AMOUNT))
            .andExpect(jsonPath("$.manPurchaseAmount").value(DEFAULT_MAN_PURCHASE_AMOUNT))
            .andExpect(jsonPath("$.mainImageFileUrl").value(DEFAULT_MAIN_IMAGE_FILE_URL))
            .andExpect(jsonPath("$.addImageFileUrl").value(DEFAULT_ADD_IMAGE_FILE_URL.toString()))
            .andExpect(jsonPath("$.mainVideoFileUrl").value(DEFAULT_MAIN_VIDEO_FILE_URL))
            .andExpect(jsonPath("$.descriptionFileUrl").value(DEFAULT_DESCRIPTION_FILE_URL))
            .andExpect(jsonPath("$.shippingType").value(DEFAULT_SHIPPING_TYPE))
            .andExpect(jsonPath("$.separateShippingPriceType").value(DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE))
            .andExpect(jsonPath("$.defaultShippingPrice").value(DEFAULT_DEFAULT_SHIPPING_PRICE))
            .andExpect(jsonPath("$.freeShippingPrice").value(DEFAULT_FREE_SHIPPING_PRICE))
            .andExpect(jsonPath("$.jejuShippingPrice").value(DEFAULT_JEJU_SHIPPING_PRICE))
            .andExpect(jsonPath("$.difficultShippingPrice").value(DEFAULT_DIFFICULT_SHIPPING_PRICE))
            .andExpect(jsonPath("$.refundShippingPrice").value(DEFAULT_REFUND_SHIPPING_PRICE))
            .andExpect(jsonPath("$.exchangeShippingPrice").value(DEFAULT_EXCHANGE_SHIPPING_PRICE))
            .andExpect(jsonPath("$.exchangeShippingFileUrl").value(DEFAULT_EXCHANGE_SHIPPING_FILE_URL))
            .andExpect(jsonPath("$.isView").value(DEFAULT_IS_VIEW.booleanValue()))
            .andExpect(jsonPath("$.viewReservationDate").value(DEFAULT_VIEW_RESERVATION_DATE.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
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
    void getAllProductsByCalculationIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculation equals to DEFAULT_CALCULATION
        defaultProductShouldBeFound("calculation.equals=" + DEFAULT_CALCULATION);

        // Get all the productList where calculation equals to UPDATED_CALCULATION
        defaultProductShouldNotBeFound("calculation.equals=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductsByCalculationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculation not equals to DEFAULT_CALCULATION
        defaultProductShouldNotBeFound("calculation.notEquals=" + DEFAULT_CALCULATION);

        // Get all the productList where calculation not equals to UPDATED_CALCULATION
        defaultProductShouldBeFound("calculation.notEquals=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductsByCalculationIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculation in DEFAULT_CALCULATION or UPDATED_CALCULATION
        defaultProductShouldBeFound("calculation.in=" + DEFAULT_CALCULATION + "," + UPDATED_CALCULATION);

        // Get all the productList where calculation equals to UPDATED_CALCULATION
        defaultProductShouldNotBeFound("calculation.in=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductsByCalculationIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculation is not null
        defaultProductShouldBeFound("calculation.specified=true");

        // Get all the productList where calculation is null
        defaultProductShouldNotBeFound("calculation.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByCalculationContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculation contains DEFAULT_CALCULATION
        defaultProductShouldBeFound("calculation.contains=" + DEFAULT_CALCULATION);

        // Get all the productList where calculation contains UPDATED_CALCULATION
        defaultProductShouldNotBeFound("calculation.contains=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductsByCalculationNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculation does not contain DEFAULT_CALCULATION
        defaultProductShouldNotBeFound("calculation.doesNotContain=" + DEFAULT_CALCULATION);

        // Get all the productList where calculation does not contain UPDATED_CALCULATION
        defaultProductShouldBeFound("calculation.doesNotContain=" + UPDATED_CALCULATION);
    }

    @Test
    @Transactional
    void getAllProductsByCalculationDateFromIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculationDateFrom equals to DEFAULT_CALCULATION_DATE_FROM
        defaultProductShouldBeFound("calculationDateFrom.equals=" + DEFAULT_CALCULATION_DATE_FROM);

        // Get all the productList where calculationDateFrom equals to UPDATED_CALCULATION_DATE_FROM
        defaultProductShouldNotBeFound("calculationDateFrom.equals=" + UPDATED_CALCULATION_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductsByCalculationDateFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculationDateFrom not equals to DEFAULT_CALCULATION_DATE_FROM
        defaultProductShouldNotBeFound("calculationDateFrom.notEquals=" + DEFAULT_CALCULATION_DATE_FROM);

        // Get all the productList where calculationDateFrom not equals to UPDATED_CALCULATION_DATE_FROM
        defaultProductShouldBeFound("calculationDateFrom.notEquals=" + UPDATED_CALCULATION_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductsByCalculationDateFromIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculationDateFrom in DEFAULT_CALCULATION_DATE_FROM or UPDATED_CALCULATION_DATE_FROM
        defaultProductShouldBeFound("calculationDateFrom.in=" + DEFAULT_CALCULATION_DATE_FROM + "," + UPDATED_CALCULATION_DATE_FROM);

        // Get all the productList where calculationDateFrom equals to UPDATED_CALCULATION_DATE_FROM
        defaultProductShouldNotBeFound("calculationDateFrom.in=" + UPDATED_CALCULATION_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductsByCalculationDateFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculationDateFrom is not null
        defaultProductShouldBeFound("calculationDateFrom.specified=true");

        // Get all the productList where calculationDateFrom is null
        defaultProductShouldNotBeFound("calculationDateFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByCalculationDateToIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculationDateTo equals to DEFAULT_CALCULATION_DATE_TO
        defaultProductShouldBeFound("calculationDateTo.equals=" + DEFAULT_CALCULATION_DATE_TO);

        // Get all the productList where calculationDateTo equals to UPDATED_CALCULATION_DATE_TO
        defaultProductShouldNotBeFound("calculationDateTo.equals=" + UPDATED_CALCULATION_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductsByCalculationDateToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculationDateTo not equals to DEFAULT_CALCULATION_DATE_TO
        defaultProductShouldNotBeFound("calculationDateTo.notEquals=" + DEFAULT_CALCULATION_DATE_TO);

        // Get all the productList where calculationDateTo not equals to UPDATED_CALCULATION_DATE_TO
        defaultProductShouldBeFound("calculationDateTo.notEquals=" + UPDATED_CALCULATION_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductsByCalculationDateToIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculationDateTo in DEFAULT_CALCULATION_DATE_TO or UPDATED_CALCULATION_DATE_TO
        defaultProductShouldBeFound("calculationDateTo.in=" + DEFAULT_CALCULATION_DATE_TO + "," + UPDATED_CALCULATION_DATE_TO);

        // Get all the productList where calculationDateTo equals to UPDATED_CALCULATION_DATE_TO
        defaultProductShouldNotBeFound("calculationDateTo.in=" + UPDATED_CALCULATION_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductsByCalculationDateToIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where calculationDateTo is not null
        defaultProductShouldBeFound("calculationDateTo.specified=true");

        // Get all the productList where calculationDateTo is null
        defaultProductShouldNotBeFound("calculationDateTo.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price equals to DEFAULT_PRICE
        defaultProductShouldBeFound("price.equals=" + DEFAULT_PRICE);

        // Get all the productList where price equals to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.equals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price not equals to DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.notEquals=" + DEFAULT_PRICE);

        // Get all the productList where price not equals to UPDATED_PRICE
        defaultProductShouldBeFound("price.notEquals=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price in DEFAULT_PRICE or UPDATED_PRICE
        defaultProductShouldBeFound("price.in=" + DEFAULT_PRICE + "," + UPDATED_PRICE);

        // Get all the productList where price equals to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.in=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is not null
        defaultProductShouldBeFound("price.specified=true");

        // Get all the productList where price is null
        defaultProductShouldNotBeFound("price.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is greater than or equal to DEFAULT_PRICE
        defaultProductShouldBeFound("price.greaterThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productList where price is greater than or equal to UPDATED_PRICE
        defaultProductShouldNotBeFound("price.greaterThanOrEqual=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is less than or equal to DEFAULT_PRICE
        defaultProductShouldBeFound("price.lessThanOrEqual=" + DEFAULT_PRICE);

        // Get all the productList where price is less than or equal to SMALLER_PRICE
        defaultProductShouldNotBeFound("price.lessThanOrEqual=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is less than DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.lessThan=" + DEFAULT_PRICE);

        // Get all the productList where price is less than UPDATED_PRICE
        defaultProductShouldBeFound("price.lessThan=" + UPDATED_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where price is greater than DEFAULT_PRICE
        defaultProductShouldNotBeFound("price.greaterThan=" + DEFAULT_PRICE);

        // Get all the productList where price is greater than SMALLER_PRICE
        defaultProductShouldBeFound("price.greaterThan=" + SMALLER_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByAllPriceUnitIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where allPriceUnit equals to DEFAULT_ALL_PRICE_UNIT
        defaultProductShouldBeFound("allPriceUnit.equals=" + DEFAULT_ALL_PRICE_UNIT);

        // Get all the productList where allPriceUnit equals to UPDATED_ALL_PRICE_UNIT
        defaultProductShouldNotBeFound("allPriceUnit.equals=" + UPDATED_ALL_PRICE_UNIT);
    }

    @Test
    @Transactional
    void getAllProductsByAllPriceUnitIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where allPriceUnit not equals to DEFAULT_ALL_PRICE_UNIT
        defaultProductShouldNotBeFound("allPriceUnit.notEquals=" + DEFAULT_ALL_PRICE_UNIT);

        // Get all the productList where allPriceUnit not equals to UPDATED_ALL_PRICE_UNIT
        defaultProductShouldBeFound("allPriceUnit.notEquals=" + UPDATED_ALL_PRICE_UNIT);
    }

    @Test
    @Transactional
    void getAllProductsByAllPriceUnitIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where allPriceUnit in DEFAULT_ALL_PRICE_UNIT or UPDATED_ALL_PRICE_UNIT
        defaultProductShouldBeFound("allPriceUnit.in=" + DEFAULT_ALL_PRICE_UNIT + "," + UPDATED_ALL_PRICE_UNIT);

        // Get all the productList where allPriceUnit equals to UPDATED_ALL_PRICE_UNIT
        defaultProductShouldNotBeFound("allPriceUnit.in=" + UPDATED_ALL_PRICE_UNIT);
    }

    @Test
    @Transactional
    void getAllProductsByAllPriceUnitIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where allPriceUnit is not null
        defaultProductShouldBeFound("allPriceUnit.specified=true");

        // Get all the productList where allPriceUnit is null
        defaultProductShouldNotBeFound("allPriceUnit.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByAllPriceUnitContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where allPriceUnit contains DEFAULT_ALL_PRICE_UNIT
        defaultProductShouldBeFound("allPriceUnit.contains=" + DEFAULT_ALL_PRICE_UNIT);

        // Get all the productList where allPriceUnit contains UPDATED_ALL_PRICE_UNIT
        defaultProductShouldNotBeFound("allPriceUnit.contains=" + UPDATED_ALL_PRICE_UNIT);
    }

    @Test
    @Transactional
    void getAllProductsByAllPriceUnitNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where allPriceUnit does not contain DEFAULT_ALL_PRICE_UNIT
        defaultProductShouldNotBeFound("allPriceUnit.doesNotContain=" + DEFAULT_ALL_PRICE_UNIT);

        // Get all the productList where allPriceUnit does not contain UPDATED_ALL_PRICE_UNIT
        defaultProductShouldBeFound("allPriceUnit.doesNotContain=" + UPDATED_ALL_PRICE_UNIT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discount equals to DEFAULT_DISCOUNT
        defaultProductShouldBeFound("discount.equals=" + DEFAULT_DISCOUNT);

        // Get all the productList where discount equals to UPDATED_DISCOUNT
        defaultProductShouldNotBeFound("discount.equals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discount not equals to DEFAULT_DISCOUNT
        defaultProductShouldNotBeFound("discount.notEquals=" + DEFAULT_DISCOUNT);

        // Get all the productList where discount not equals to UPDATED_DISCOUNT
        defaultProductShouldBeFound("discount.notEquals=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discount in DEFAULT_DISCOUNT or UPDATED_DISCOUNT
        defaultProductShouldBeFound("discount.in=" + DEFAULT_DISCOUNT + "," + UPDATED_DISCOUNT);

        // Get all the productList where discount equals to UPDATED_DISCOUNT
        defaultProductShouldNotBeFound("discount.in=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discount is not null
        defaultProductShouldBeFound("discount.specified=true");

        // Get all the productList where discount is null
        defaultProductShouldNotBeFound("discount.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDiscountContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discount contains DEFAULT_DISCOUNT
        defaultProductShouldBeFound("discount.contains=" + DEFAULT_DISCOUNT);

        // Get all the productList where discount contains UPDATED_DISCOUNT
        defaultProductShouldNotBeFound("discount.contains=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discount does not contain DEFAULT_DISCOUNT
        defaultProductShouldNotBeFound("discount.doesNotContain=" + DEFAULT_DISCOUNT);

        // Get all the productList where discount does not contain UPDATED_DISCOUNT
        defaultProductShouldBeFound("discount.doesNotContain=" + UPDATED_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountPrice equals to DEFAULT_DISCOUNT_PRICE
        defaultProductShouldBeFound("discountPrice.equals=" + DEFAULT_DISCOUNT_PRICE);

        // Get all the productList where discountPrice equals to UPDATED_DISCOUNT_PRICE
        defaultProductShouldNotBeFound("discountPrice.equals=" + UPDATED_DISCOUNT_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountPrice not equals to DEFAULT_DISCOUNT_PRICE
        defaultProductShouldNotBeFound("discountPrice.notEquals=" + DEFAULT_DISCOUNT_PRICE);

        // Get all the productList where discountPrice not equals to UPDATED_DISCOUNT_PRICE
        defaultProductShouldBeFound("discountPrice.notEquals=" + UPDATED_DISCOUNT_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountPrice in DEFAULT_DISCOUNT_PRICE or UPDATED_DISCOUNT_PRICE
        defaultProductShouldBeFound("discountPrice.in=" + DEFAULT_DISCOUNT_PRICE + "," + UPDATED_DISCOUNT_PRICE);

        // Get all the productList where discountPrice equals to UPDATED_DISCOUNT_PRICE
        defaultProductShouldNotBeFound("discountPrice.in=" + UPDATED_DISCOUNT_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountPrice is not null
        defaultProductShouldBeFound("discountPrice.specified=true");

        // Get all the productList where discountPrice is null
        defaultProductShouldNotBeFound("discountPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDiscountPriceContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountPrice contains DEFAULT_DISCOUNT_PRICE
        defaultProductShouldBeFound("discountPrice.contains=" + DEFAULT_DISCOUNT_PRICE);

        // Get all the productList where discountPrice contains UPDATED_DISCOUNT_PRICE
        defaultProductShouldNotBeFound("discountPrice.contains=" + UPDATED_DISCOUNT_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountPriceNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountPrice does not contain DEFAULT_DISCOUNT_PRICE
        defaultProductShouldNotBeFound("discountPrice.doesNotContain=" + DEFAULT_DISCOUNT_PRICE);

        // Get all the productList where discountPrice does not contain UPDATED_DISCOUNT_PRICE
        defaultProductShouldBeFound("discountPrice.doesNotContain=" + UPDATED_DISCOUNT_PRICE);
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
    void getAllProductsByDiscountDateFromIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountDateFrom equals to DEFAULT_DISCOUNT_DATE_FROM
        defaultProductShouldBeFound("discountDateFrom.equals=" + DEFAULT_DISCOUNT_DATE_FROM);

        // Get all the productList where discountDateFrom equals to UPDATED_DISCOUNT_DATE_FROM
        defaultProductShouldNotBeFound("discountDateFrom.equals=" + UPDATED_DISCOUNT_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountDateFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountDateFrom not equals to DEFAULT_DISCOUNT_DATE_FROM
        defaultProductShouldNotBeFound("discountDateFrom.notEquals=" + DEFAULT_DISCOUNT_DATE_FROM);

        // Get all the productList where discountDateFrom not equals to UPDATED_DISCOUNT_DATE_FROM
        defaultProductShouldBeFound("discountDateFrom.notEquals=" + UPDATED_DISCOUNT_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountDateFromIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountDateFrom in DEFAULT_DISCOUNT_DATE_FROM or UPDATED_DISCOUNT_DATE_FROM
        defaultProductShouldBeFound("discountDateFrom.in=" + DEFAULT_DISCOUNT_DATE_FROM + "," + UPDATED_DISCOUNT_DATE_FROM);

        // Get all the productList where discountDateFrom equals to UPDATED_DISCOUNT_DATE_FROM
        defaultProductShouldNotBeFound("discountDateFrom.in=" + UPDATED_DISCOUNT_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountDateFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountDateFrom is not null
        defaultProductShouldBeFound("discountDateFrom.specified=true");

        // Get all the productList where discountDateFrom is null
        defaultProductShouldNotBeFound("discountDateFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDiscountDateToIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountDateTo equals to DEFAULT_DISCOUNT_DATE_TO
        defaultProductShouldBeFound("discountDateTo.equals=" + DEFAULT_DISCOUNT_DATE_TO);

        // Get all the productList where discountDateTo equals to UPDATED_DISCOUNT_DATE_TO
        defaultProductShouldNotBeFound("discountDateTo.equals=" + UPDATED_DISCOUNT_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountDateToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountDateTo not equals to DEFAULT_DISCOUNT_DATE_TO
        defaultProductShouldNotBeFound("discountDateTo.notEquals=" + DEFAULT_DISCOUNT_DATE_TO);

        // Get all the productList where discountDateTo not equals to UPDATED_DISCOUNT_DATE_TO
        defaultProductShouldBeFound("discountDateTo.notEquals=" + UPDATED_DISCOUNT_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountDateToIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountDateTo in DEFAULT_DISCOUNT_DATE_TO or UPDATED_DISCOUNT_DATE_TO
        defaultProductShouldBeFound("discountDateTo.in=" + DEFAULT_DISCOUNT_DATE_TO + "," + UPDATED_DISCOUNT_DATE_TO);

        // Get all the productList where discountDateTo equals to UPDATED_DISCOUNT_DATE_TO
        defaultProductShouldNotBeFound("discountDateTo.in=" + UPDATED_DISCOUNT_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductsByDiscountDateToIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where discountDateTo is not null
        defaultProductShouldBeFound("discountDateTo.specified=true");

        // Get all the productList where discountDateTo is null
        defaultProductShouldNotBeFound("discountDateTo.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByIsInstallmentIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isInstallment equals to DEFAULT_IS_INSTALLMENT
        defaultProductShouldBeFound("isInstallment.equals=" + DEFAULT_IS_INSTALLMENT);

        // Get all the productList where isInstallment equals to UPDATED_IS_INSTALLMENT
        defaultProductShouldNotBeFound("isInstallment.equals=" + UPDATED_IS_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllProductsByIsInstallmentIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isInstallment not equals to DEFAULT_IS_INSTALLMENT
        defaultProductShouldNotBeFound("isInstallment.notEquals=" + DEFAULT_IS_INSTALLMENT);

        // Get all the productList where isInstallment not equals to UPDATED_IS_INSTALLMENT
        defaultProductShouldBeFound("isInstallment.notEquals=" + UPDATED_IS_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllProductsByIsInstallmentIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isInstallment in DEFAULT_IS_INSTALLMENT or UPDATED_IS_INSTALLMENT
        defaultProductShouldBeFound("isInstallment.in=" + DEFAULT_IS_INSTALLMENT + "," + UPDATED_IS_INSTALLMENT);

        // Get all the productList where isInstallment equals to UPDATED_IS_INSTALLMENT
        defaultProductShouldNotBeFound("isInstallment.in=" + UPDATED_IS_INSTALLMENT);
    }

    @Test
    @Transactional
    void getAllProductsByIsInstallmentIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isInstallment is not null
        defaultProductShouldBeFound("isInstallment.specified=true");

        // Get all the productList where isInstallment is null
        defaultProductShouldNotBeFound("isInstallment.specified=false");
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
    void getAllProductsByIsSellIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isSell equals to DEFAULT_IS_SELL
        defaultProductShouldBeFound("isSell.equals=" + DEFAULT_IS_SELL);

        // Get all the productList where isSell equals to UPDATED_IS_SELL
        defaultProductShouldNotBeFound("isSell.equals=" + UPDATED_IS_SELL);
    }

    @Test
    @Transactional
    void getAllProductsByIsSellIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isSell not equals to DEFAULT_IS_SELL
        defaultProductShouldNotBeFound("isSell.notEquals=" + DEFAULT_IS_SELL);

        // Get all the productList where isSell not equals to UPDATED_IS_SELL
        defaultProductShouldBeFound("isSell.notEquals=" + UPDATED_IS_SELL);
    }

    @Test
    @Transactional
    void getAllProductsByIsSellIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isSell in DEFAULT_IS_SELL or UPDATED_IS_SELL
        defaultProductShouldBeFound("isSell.in=" + DEFAULT_IS_SELL + "," + UPDATED_IS_SELL);

        // Get all the productList where isSell equals to UPDATED_IS_SELL
        defaultProductShouldNotBeFound("isSell.in=" + UPDATED_IS_SELL);
    }

    @Test
    @Transactional
    void getAllProductsByIsSellIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isSell is not null
        defaultProductShouldBeFound("isSell.specified=true");

        // Get all the productList where isSell is null
        defaultProductShouldNotBeFound("isSell.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsBySellDateFromIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sellDateFrom equals to DEFAULT_SELL_DATE_FROM
        defaultProductShouldBeFound("sellDateFrom.equals=" + DEFAULT_SELL_DATE_FROM);

        // Get all the productList where sellDateFrom equals to UPDATED_SELL_DATE_FROM
        defaultProductShouldNotBeFound("sellDateFrom.equals=" + UPDATED_SELL_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductsBySellDateFromIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sellDateFrom not equals to DEFAULT_SELL_DATE_FROM
        defaultProductShouldNotBeFound("sellDateFrom.notEquals=" + DEFAULT_SELL_DATE_FROM);

        // Get all the productList where sellDateFrom not equals to UPDATED_SELL_DATE_FROM
        defaultProductShouldBeFound("sellDateFrom.notEquals=" + UPDATED_SELL_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductsBySellDateFromIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sellDateFrom in DEFAULT_SELL_DATE_FROM or UPDATED_SELL_DATE_FROM
        defaultProductShouldBeFound("sellDateFrom.in=" + DEFAULT_SELL_DATE_FROM + "," + UPDATED_SELL_DATE_FROM);

        // Get all the productList where sellDateFrom equals to UPDATED_SELL_DATE_FROM
        defaultProductShouldNotBeFound("sellDateFrom.in=" + UPDATED_SELL_DATE_FROM);
    }

    @Test
    @Transactional
    void getAllProductsBySellDateFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sellDateFrom is not null
        defaultProductShouldBeFound("sellDateFrom.specified=true");

        // Get all the productList where sellDateFrom is null
        defaultProductShouldNotBeFound("sellDateFrom.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsBySellDateToIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sellDateTo equals to DEFAULT_SELL_DATE_TO
        defaultProductShouldBeFound("sellDateTo.equals=" + DEFAULT_SELL_DATE_TO);

        // Get all the productList where sellDateTo equals to UPDATED_SELL_DATE_TO
        defaultProductShouldNotBeFound("sellDateTo.equals=" + UPDATED_SELL_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductsBySellDateToIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sellDateTo not equals to DEFAULT_SELL_DATE_TO
        defaultProductShouldNotBeFound("sellDateTo.notEquals=" + DEFAULT_SELL_DATE_TO);

        // Get all the productList where sellDateTo not equals to UPDATED_SELL_DATE_TO
        defaultProductShouldBeFound("sellDateTo.notEquals=" + UPDATED_SELL_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductsBySellDateToIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sellDateTo in DEFAULT_SELL_DATE_TO or UPDATED_SELL_DATE_TO
        defaultProductShouldBeFound("sellDateTo.in=" + DEFAULT_SELL_DATE_TO + "," + UPDATED_SELL_DATE_TO);

        // Get all the productList where sellDateTo equals to UPDATED_SELL_DATE_TO
        defaultProductShouldNotBeFound("sellDateTo.in=" + UPDATED_SELL_DATE_TO);
    }

    @Test
    @Transactional
    void getAllProductsBySellDateToIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where sellDateTo is not null
        defaultProductShouldBeFound("sellDateTo.specified=true");

        // Get all the productList where sellDateTo is null
        defaultProductShouldNotBeFound("sellDateTo.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByMinPurchaseAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPurchaseAmount equals to DEFAULT_MIN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("minPurchaseAmount.equals=" + DEFAULT_MIN_PURCHASE_AMOUNT);

        // Get all the productList where minPurchaseAmount equals to UPDATED_MIN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("minPurchaseAmount.equals=" + UPDATED_MIN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinPurchaseAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPurchaseAmount not equals to DEFAULT_MIN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("minPurchaseAmount.notEquals=" + DEFAULT_MIN_PURCHASE_AMOUNT);

        // Get all the productList where minPurchaseAmount not equals to UPDATED_MIN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("minPurchaseAmount.notEquals=" + UPDATED_MIN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinPurchaseAmountIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPurchaseAmount in DEFAULT_MIN_PURCHASE_AMOUNT or UPDATED_MIN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("minPurchaseAmount.in=" + DEFAULT_MIN_PURCHASE_AMOUNT + "," + UPDATED_MIN_PURCHASE_AMOUNT);

        // Get all the productList where minPurchaseAmount equals to UPDATED_MIN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("minPurchaseAmount.in=" + UPDATED_MIN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinPurchaseAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPurchaseAmount is not null
        defaultProductShouldBeFound("minPurchaseAmount.specified=true");

        // Get all the productList where minPurchaseAmount is null
        defaultProductShouldNotBeFound("minPurchaseAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByMinPurchaseAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPurchaseAmount is greater than or equal to DEFAULT_MIN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("minPurchaseAmount.greaterThanOrEqual=" + DEFAULT_MIN_PURCHASE_AMOUNT);

        // Get all the productList where minPurchaseAmount is greater than or equal to UPDATED_MIN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("minPurchaseAmount.greaterThanOrEqual=" + UPDATED_MIN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinPurchaseAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPurchaseAmount is less than or equal to DEFAULT_MIN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("minPurchaseAmount.lessThanOrEqual=" + DEFAULT_MIN_PURCHASE_AMOUNT);

        // Get all the productList where minPurchaseAmount is less than or equal to SMALLER_MIN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("minPurchaseAmount.lessThanOrEqual=" + SMALLER_MIN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinPurchaseAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPurchaseAmount is less than DEFAULT_MIN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("minPurchaseAmount.lessThan=" + DEFAULT_MIN_PURCHASE_AMOUNT);

        // Get all the productList where minPurchaseAmount is less than UPDATED_MIN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("minPurchaseAmount.lessThan=" + UPDATED_MIN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMinPurchaseAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where minPurchaseAmount is greater than DEFAULT_MIN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("minPurchaseAmount.greaterThan=" + DEFAULT_MIN_PURCHASE_AMOUNT);

        // Get all the productList where minPurchaseAmount is greater than SMALLER_MIN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("minPurchaseAmount.greaterThan=" + SMALLER_MIN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByManPurchaseAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where manPurchaseAmount equals to DEFAULT_MAN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("manPurchaseAmount.equals=" + DEFAULT_MAN_PURCHASE_AMOUNT);

        // Get all the productList where manPurchaseAmount equals to UPDATED_MAN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("manPurchaseAmount.equals=" + UPDATED_MAN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByManPurchaseAmountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where manPurchaseAmount not equals to DEFAULT_MAN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("manPurchaseAmount.notEquals=" + DEFAULT_MAN_PURCHASE_AMOUNT);

        // Get all the productList where manPurchaseAmount not equals to UPDATED_MAN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("manPurchaseAmount.notEquals=" + UPDATED_MAN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByManPurchaseAmountIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where manPurchaseAmount in DEFAULT_MAN_PURCHASE_AMOUNT or UPDATED_MAN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("manPurchaseAmount.in=" + DEFAULT_MAN_PURCHASE_AMOUNT + "," + UPDATED_MAN_PURCHASE_AMOUNT);

        // Get all the productList where manPurchaseAmount equals to UPDATED_MAN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("manPurchaseAmount.in=" + UPDATED_MAN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByManPurchaseAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where manPurchaseAmount is not null
        defaultProductShouldBeFound("manPurchaseAmount.specified=true");

        // Get all the productList where manPurchaseAmount is null
        defaultProductShouldNotBeFound("manPurchaseAmount.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByManPurchaseAmountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where manPurchaseAmount is greater than or equal to DEFAULT_MAN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("manPurchaseAmount.greaterThanOrEqual=" + DEFAULT_MAN_PURCHASE_AMOUNT);

        // Get all the productList where manPurchaseAmount is greater than or equal to UPDATED_MAN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("manPurchaseAmount.greaterThanOrEqual=" + UPDATED_MAN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByManPurchaseAmountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where manPurchaseAmount is less than or equal to DEFAULT_MAN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("manPurchaseAmount.lessThanOrEqual=" + DEFAULT_MAN_PURCHASE_AMOUNT);

        // Get all the productList where manPurchaseAmount is less than or equal to SMALLER_MAN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("manPurchaseAmount.lessThanOrEqual=" + SMALLER_MAN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByManPurchaseAmountIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where manPurchaseAmount is less than DEFAULT_MAN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("manPurchaseAmount.lessThan=" + DEFAULT_MAN_PURCHASE_AMOUNT);

        // Get all the productList where manPurchaseAmount is less than UPDATED_MAN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("manPurchaseAmount.lessThan=" + UPDATED_MAN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByManPurchaseAmountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where manPurchaseAmount is greater than DEFAULT_MAN_PURCHASE_AMOUNT
        defaultProductShouldNotBeFound("manPurchaseAmount.greaterThan=" + DEFAULT_MAN_PURCHASE_AMOUNT);

        // Get all the productList where manPurchaseAmount is greater than SMALLER_MAN_PURCHASE_AMOUNT
        defaultProductShouldBeFound("manPurchaseAmount.greaterThan=" + SMALLER_MAN_PURCHASE_AMOUNT);
    }

    @Test
    @Transactional
    void getAllProductsByMainImageFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainImageFileUrl equals to DEFAULT_MAIN_IMAGE_FILE_URL
        defaultProductShouldBeFound("mainImageFileUrl.equals=" + DEFAULT_MAIN_IMAGE_FILE_URL);

        // Get all the productList where mainImageFileUrl equals to UPDATED_MAIN_IMAGE_FILE_URL
        defaultProductShouldNotBeFound("mainImageFileUrl.equals=" + UPDATED_MAIN_IMAGE_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByMainImageFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainImageFileUrl not equals to DEFAULT_MAIN_IMAGE_FILE_URL
        defaultProductShouldNotBeFound("mainImageFileUrl.notEquals=" + DEFAULT_MAIN_IMAGE_FILE_URL);

        // Get all the productList where mainImageFileUrl not equals to UPDATED_MAIN_IMAGE_FILE_URL
        defaultProductShouldBeFound("mainImageFileUrl.notEquals=" + UPDATED_MAIN_IMAGE_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByMainImageFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainImageFileUrl in DEFAULT_MAIN_IMAGE_FILE_URL or UPDATED_MAIN_IMAGE_FILE_URL
        defaultProductShouldBeFound("mainImageFileUrl.in=" + DEFAULT_MAIN_IMAGE_FILE_URL + "," + UPDATED_MAIN_IMAGE_FILE_URL);

        // Get all the productList where mainImageFileUrl equals to UPDATED_MAIN_IMAGE_FILE_URL
        defaultProductShouldNotBeFound("mainImageFileUrl.in=" + UPDATED_MAIN_IMAGE_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByMainImageFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainImageFileUrl is not null
        defaultProductShouldBeFound("mainImageFileUrl.specified=true");

        // Get all the productList where mainImageFileUrl is null
        defaultProductShouldNotBeFound("mainImageFileUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByMainImageFileUrlContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainImageFileUrl contains DEFAULT_MAIN_IMAGE_FILE_URL
        defaultProductShouldBeFound("mainImageFileUrl.contains=" + DEFAULT_MAIN_IMAGE_FILE_URL);

        // Get all the productList where mainImageFileUrl contains UPDATED_MAIN_IMAGE_FILE_URL
        defaultProductShouldNotBeFound("mainImageFileUrl.contains=" + UPDATED_MAIN_IMAGE_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByMainImageFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainImageFileUrl does not contain DEFAULT_MAIN_IMAGE_FILE_URL
        defaultProductShouldNotBeFound("mainImageFileUrl.doesNotContain=" + DEFAULT_MAIN_IMAGE_FILE_URL);

        // Get all the productList where mainImageFileUrl does not contain UPDATED_MAIN_IMAGE_FILE_URL
        defaultProductShouldBeFound("mainImageFileUrl.doesNotContain=" + UPDATED_MAIN_IMAGE_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByMainVideoFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainVideoFileUrl equals to DEFAULT_MAIN_VIDEO_FILE_URL
        defaultProductShouldBeFound("mainVideoFileUrl.equals=" + DEFAULT_MAIN_VIDEO_FILE_URL);

        // Get all the productList where mainVideoFileUrl equals to UPDATED_MAIN_VIDEO_FILE_URL
        defaultProductShouldNotBeFound("mainVideoFileUrl.equals=" + UPDATED_MAIN_VIDEO_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByMainVideoFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainVideoFileUrl not equals to DEFAULT_MAIN_VIDEO_FILE_URL
        defaultProductShouldNotBeFound("mainVideoFileUrl.notEquals=" + DEFAULT_MAIN_VIDEO_FILE_URL);

        // Get all the productList where mainVideoFileUrl not equals to UPDATED_MAIN_VIDEO_FILE_URL
        defaultProductShouldBeFound("mainVideoFileUrl.notEquals=" + UPDATED_MAIN_VIDEO_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByMainVideoFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainVideoFileUrl in DEFAULT_MAIN_VIDEO_FILE_URL or UPDATED_MAIN_VIDEO_FILE_URL
        defaultProductShouldBeFound("mainVideoFileUrl.in=" + DEFAULT_MAIN_VIDEO_FILE_URL + "," + UPDATED_MAIN_VIDEO_FILE_URL);

        // Get all the productList where mainVideoFileUrl equals to UPDATED_MAIN_VIDEO_FILE_URL
        defaultProductShouldNotBeFound("mainVideoFileUrl.in=" + UPDATED_MAIN_VIDEO_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByMainVideoFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainVideoFileUrl is not null
        defaultProductShouldBeFound("mainVideoFileUrl.specified=true");

        // Get all the productList where mainVideoFileUrl is null
        defaultProductShouldNotBeFound("mainVideoFileUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByMainVideoFileUrlContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainVideoFileUrl contains DEFAULT_MAIN_VIDEO_FILE_URL
        defaultProductShouldBeFound("mainVideoFileUrl.contains=" + DEFAULT_MAIN_VIDEO_FILE_URL);

        // Get all the productList where mainVideoFileUrl contains UPDATED_MAIN_VIDEO_FILE_URL
        defaultProductShouldNotBeFound("mainVideoFileUrl.contains=" + UPDATED_MAIN_VIDEO_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByMainVideoFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where mainVideoFileUrl does not contain DEFAULT_MAIN_VIDEO_FILE_URL
        defaultProductShouldNotBeFound("mainVideoFileUrl.doesNotContain=" + DEFAULT_MAIN_VIDEO_FILE_URL);

        // Get all the productList where mainVideoFileUrl does not contain UPDATED_MAIN_VIDEO_FILE_URL
        defaultProductShouldBeFound("mainVideoFileUrl.doesNotContain=" + UPDATED_MAIN_VIDEO_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByDescriptionFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where descriptionFileUrl equals to DEFAULT_DESCRIPTION_FILE_URL
        defaultProductShouldBeFound("descriptionFileUrl.equals=" + DEFAULT_DESCRIPTION_FILE_URL);

        // Get all the productList where descriptionFileUrl equals to UPDATED_DESCRIPTION_FILE_URL
        defaultProductShouldNotBeFound("descriptionFileUrl.equals=" + UPDATED_DESCRIPTION_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByDescriptionFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where descriptionFileUrl not equals to DEFAULT_DESCRIPTION_FILE_URL
        defaultProductShouldNotBeFound("descriptionFileUrl.notEquals=" + DEFAULT_DESCRIPTION_FILE_URL);

        // Get all the productList where descriptionFileUrl not equals to UPDATED_DESCRIPTION_FILE_URL
        defaultProductShouldBeFound("descriptionFileUrl.notEquals=" + UPDATED_DESCRIPTION_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByDescriptionFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where descriptionFileUrl in DEFAULT_DESCRIPTION_FILE_URL or UPDATED_DESCRIPTION_FILE_URL
        defaultProductShouldBeFound("descriptionFileUrl.in=" + DEFAULT_DESCRIPTION_FILE_URL + "," + UPDATED_DESCRIPTION_FILE_URL);

        // Get all the productList where descriptionFileUrl equals to UPDATED_DESCRIPTION_FILE_URL
        defaultProductShouldNotBeFound("descriptionFileUrl.in=" + UPDATED_DESCRIPTION_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByDescriptionFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where descriptionFileUrl is not null
        defaultProductShouldBeFound("descriptionFileUrl.specified=true");

        // Get all the productList where descriptionFileUrl is null
        defaultProductShouldNotBeFound("descriptionFileUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDescriptionFileUrlContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where descriptionFileUrl contains DEFAULT_DESCRIPTION_FILE_URL
        defaultProductShouldBeFound("descriptionFileUrl.contains=" + DEFAULT_DESCRIPTION_FILE_URL);

        // Get all the productList where descriptionFileUrl contains UPDATED_DESCRIPTION_FILE_URL
        defaultProductShouldNotBeFound("descriptionFileUrl.contains=" + UPDATED_DESCRIPTION_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByDescriptionFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where descriptionFileUrl does not contain DEFAULT_DESCRIPTION_FILE_URL
        defaultProductShouldNotBeFound("descriptionFileUrl.doesNotContain=" + DEFAULT_DESCRIPTION_FILE_URL);

        // Get all the productList where descriptionFileUrl does not contain UPDATED_DESCRIPTION_FILE_URL
        defaultProductShouldBeFound("descriptionFileUrl.doesNotContain=" + UPDATED_DESCRIPTION_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByShippingTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingType equals to DEFAULT_SHIPPING_TYPE
        defaultProductShouldBeFound("shippingType.equals=" + DEFAULT_SHIPPING_TYPE);

        // Get all the productList where shippingType equals to UPDATED_SHIPPING_TYPE
        defaultProductShouldNotBeFound("shippingType.equals=" + UPDATED_SHIPPING_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByShippingTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingType not equals to DEFAULT_SHIPPING_TYPE
        defaultProductShouldNotBeFound("shippingType.notEquals=" + DEFAULT_SHIPPING_TYPE);

        // Get all the productList where shippingType not equals to UPDATED_SHIPPING_TYPE
        defaultProductShouldBeFound("shippingType.notEquals=" + UPDATED_SHIPPING_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByShippingTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingType in DEFAULT_SHIPPING_TYPE or UPDATED_SHIPPING_TYPE
        defaultProductShouldBeFound("shippingType.in=" + DEFAULT_SHIPPING_TYPE + "," + UPDATED_SHIPPING_TYPE);

        // Get all the productList where shippingType equals to UPDATED_SHIPPING_TYPE
        defaultProductShouldNotBeFound("shippingType.in=" + UPDATED_SHIPPING_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByShippingTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingType is not null
        defaultProductShouldBeFound("shippingType.specified=true");

        // Get all the productList where shippingType is null
        defaultProductShouldNotBeFound("shippingType.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByShippingTypeContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingType contains DEFAULT_SHIPPING_TYPE
        defaultProductShouldBeFound("shippingType.contains=" + DEFAULT_SHIPPING_TYPE);

        // Get all the productList where shippingType contains UPDATED_SHIPPING_TYPE
        defaultProductShouldNotBeFound("shippingType.contains=" + UPDATED_SHIPPING_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByShippingTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where shippingType does not contain DEFAULT_SHIPPING_TYPE
        defaultProductShouldNotBeFound("shippingType.doesNotContain=" + DEFAULT_SHIPPING_TYPE);

        // Get all the productList where shippingType does not contain UPDATED_SHIPPING_TYPE
        defaultProductShouldBeFound("shippingType.doesNotContain=" + UPDATED_SHIPPING_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsBySeparateShippingPriceTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where separateShippingPriceType equals to DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE
        defaultProductShouldBeFound("separateShippingPriceType.equals=" + DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE);

        // Get all the productList where separateShippingPriceType equals to UPDATED_SEPARATE_SHIPPING_PRICE_TYPE
        defaultProductShouldNotBeFound("separateShippingPriceType.equals=" + UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsBySeparateShippingPriceTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where separateShippingPriceType not equals to DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE
        defaultProductShouldNotBeFound("separateShippingPriceType.notEquals=" + DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE);

        // Get all the productList where separateShippingPriceType not equals to UPDATED_SEPARATE_SHIPPING_PRICE_TYPE
        defaultProductShouldBeFound("separateShippingPriceType.notEquals=" + UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsBySeparateShippingPriceTypeIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where separateShippingPriceType in DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE or UPDATED_SEPARATE_SHIPPING_PRICE_TYPE
        defaultProductShouldBeFound(
            "separateShippingPriceType.in=" + DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE + "," + UPDATED_SEPARATE_SHIPPING_PRICE_TYPE
        );

        // Get all the productList where separateShippingPriceType equals to UPDATED_SEPARATE_SHIPPING_PRICE_TYPE
        defaultProductShouldNotBeFound("separateShippingPriceType.in=" + UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsBySeparateShippingPriceTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where separateShippingPriceType is not null
        defaultProductShouldBeFound("separateShippingPriceType.specified=true");

        // Get all the productList where separateShippingPriceType is null
        defaultProductShouldNotBeFound("separateShippingPriceType.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsBySeparateShippingPriceTypeContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where separateShippingPriceType contains DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE
        defaultProductShouldBeFound("separateShippingPriceType.contains=" + DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE);

        // Get all the productList where separateShippingPriceType contains UPDATED_SEPARATE_SHIPPING_PRICE_TYPE
        defaultProductShouldNotBeFound("separateShippingPriceType.contains=" + UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsBySeparateShippingPriceTypeNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where separateShippingPriceType does not contain DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE
        defaultProductShouldNotBeFound("separateShippingPriceType.doesNotContain=" + DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE);

        // Get all the productList where separateShippingPriceType does not contain UPDATED_SEPARATE_SHIPPING_PRICE_TYPE
        defaultProductShouldBeFound("separateShippingPriceType.doesNotContain=" + UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
    }

    @Test
    @Transactional
    void getAllProductsByDefaultShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where defaultShippingPrice equals to DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShouldBeFound("defaultShippingPrice.equals=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productList where defaultShippingPrice equals to UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("defaultShippingPrice.equals=" + UPDATED_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDefaultShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where defaultShippingPrice not equals to DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("defaultShippingPrice.notEquals=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productList where defaultShippingPrice not equals to UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShouldBeFound("defaultShippingPrice.notEquals=" + UPDATED_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDefaultShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where defaultShippingPrice in DEFAULT_DEFAULT_SHIPPING_PRICE or UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShouldBeFound("defaultShippingPrice.in=" + DEFAULT_DEFAULT_SHIPPING_PRICE + "," + UPDATED_DEFAULT_SHIPPING_PRICE);

        // Get all the productList where defaultShippingPrice equals to UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("defaultShippingPrice.in=" + UPDATED_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDefaultShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where defaultShippingPrice is not null
        defaultProductShouldBeFound("defaultShippingPrice.specified=true");

        // Get all the productList where defaultShippingPrice is null
        defaultProductShouldNotBeFound("defaultShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDefaultShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where defaultShippingPrice is greater than or equal to DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShouldBeFound("defaultShippingPrice.greaterThanOrEqual=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productList where defaultShippingPrice is greater than or equal to UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("defaultShippingPrice.greaterThanOrEqual=" + UPDATED_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDefaultShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where defaultShippingPrice is less than or equal to DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShouldBeFound("defaultShippingPrice.lessThanOrEqual=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productList where defaultShippingPrice is less than or equal to SMALLER_DEFAULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("defaultShippingPrice.lessThanOrEqual=" + SMALLER_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDefaultShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where defaultShippingPrice is less than DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("defaultShippingPrice.lessThan=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productList where defaultShippingPrice is less than UPDATED_DEFAULT_SHIPPING_PRICE
        defaultProductShouldBeFound("defaultShippingPrice.lessThan=" + UPDATED_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDefaultShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where defaultShippingPrice is greater than DEFAULT_DEFAULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("defaultShippingPrice.greaterThan=" + DEFAULT_DEFAULT_SHIPPING_PRICE);

        // Get all the productList where defaultShippingPrice is greater than SMALLER_DEFAULT_SHIPPING_PRICE
        defaultProductShouldBeFound("defaultShippingPrice.greaterThan=" + SMALLER_DEFAULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByFreeShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeShippingPrice equals to DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShouldBeFound("freeShippingPrice.equals=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productList where freeShippingPrice equals to UPDATED_FREE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("freeShippingPrice.equals=" + UPDATED_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByFreeShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeShippingPrice not equals to DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("freeShippingPrice.notEquals=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productList where freeShippingPrice not equals to UPDATED_FREE_SHIPPING_PRICE
        defaultProductShouldBeFound("freeShippingPrice.notEquals=" + UPDATED_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByFreeShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeShippingPrice in DEFAULT_FREE_SHIPPING_PRICE or UPDATED_FREE_SHIPPING_PRICE
        defaultProductShouldBeFound("freeShippingPrice.in=" + DEFAULT_FREE_SHIPPING_PRICE + "," + UPDATED_FREE_SHIPPING_PRICE);

        // Get all the productList where freeShippingPrice equals to UPDATED_FREE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("freeShippingPrice.in=" + UPDATED_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByFreeShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeShippingPrice is not null
        defaultProductShouldBeFound("freeShippingPrice.specified=true");

        // Get all the productList where freeShippingPrice is null
        defaultProductShouldNotBeFound("freeShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByFreeShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeShippingPrice is greater than or equal to DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShouldBeFound("freeShippingPrice.greaterThanOrEqual=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productList where freeShippingPrice is greater than or equal to UPDATED_FREE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("freeShippingPrice.greaterThanOrEqual=" + UPDATED_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByFreeShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeShippingPrice is less than or equal to DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShouldBeFound("freeShippingPrice.lessThanOrEqual=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productList where freeShippingPrice is less than or equal to SMALLER_FREE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("freeShippingPrice.lessThanOrEqual=" + SMALLER_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByFreeShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeShippingPrice is less than DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("freeShippingPrice.lessThan=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productList where freeShippingPrice is less than UPDATED_FREE_SHIPPING_PRICE
        defaultProductShouldBeFound("freeShippingPrice.lessThan=" + UPDATED_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByFreeShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where freeShippingPrice is greater than DEFAULT_FREE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("freeShippingPrice.greaterThan=" + DEFAULT_FREE_SHIPPING_PRICE);

        // Get all the productList where freeShippingPrice is greater than SMALLER_FREE_SHIPPING_PRICE
        defaultProductShouldBeFound("freeShippingPrice.greaterThan=" + SMALLER_FREE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByJejuShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where jejuShippingPrice equals to DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShouldBeFound("jejuShippingPrice.equals=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productList where jejuShippingPrice equals to UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShouldNotBeFound("jejuShippingPrice.equals=" + UPDATED_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByJejuShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where jejuShippingPrice not equals to DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShouldNotBeFound("jejuShippingPrice.notEquals=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productList where jejuShippingPrice not equals to UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShouldBeFound("jejuShippingPrice.notEquals=" + UPDATED_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByJejuShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where jejuShippingPrice in DEFAULT_JEJU_SHIPPING_PRICE or UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShouldBeFound("jejuShippingPrice.in=" + DEFAULT_JEJU_SHIPPING_PRICE + "," + UPDATED_JEJU_SHIPPING_PRICE);

        // Get all the productList where jejuShippingPrice equals to UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShouldNotBeFound("jejuShippingPrice.in=" + UPDATED_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByJejuShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where jejuShippingPrice is not null
        defaultProductShouldBeFound("jejuShippingPrice.specified=true");

        // Get all the productList where jejuShippingPrice is null
        defaultProductShouldNotBeFound("jejuShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByJejuShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where jejuShippingPrice is greater than or equal to DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShouldBeFound("jejuShippingPrice.greaterThanOrEqual=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productList where jejuShippingPrice is greater than or equal to UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShouldNotBeFound("jejuShippingPrice.greaterThanOrEqual=" + UPDATED_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByJejuShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where jejuShippingPrice is less than or equal to DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShouldBeFound("jejuShippingPrice.lessThanOrEqual=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productList where jejuShippingPrice is less than or equal to SMALLER_JEJU_SHIPPING_PRICE
        defaultProductShouldNotBeFound("jejuShippingPrice.lessThanOrEqual=" + SMALLER_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByJejuShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where jejuShippingPrice is less than DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShouldNotBeFound("jejuShippingPrice.lessThan=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productList where jejuShippingPrice is less than UPDATED_JEJU_SHIPPING_PRICE
        defaultProductShouldBeFound("jejuShippingPrice.lessThan=" + UPDATED_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByJejuShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where jejuShippingPrice is greater than DEFAULT_JEJU_SHIPPING_PRICE
        defaultProductShouldNotBeFound("jejuShippingPrice.greaterThan=" + DEFAULT_JEJU_SHIPPING_PRICE);

        // Get all the productList where jejuShippingPrice is greater than SMALLER_JEJU_SHIPPING_PRICE
        defaultProductShouldBeFound("jejuShippingPrice.greaterThan=" + SMALLER_JEJU_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDifficultShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficultShippingPrice equals to DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldBeFound("difficultShippingPrice.equals=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productList where difficultShippingPrice equals to UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("difficultShippingPrice.equals=" + UPDATED_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDifficultShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficultShippingPrice not equals to DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("difficultShippingPrice.notEquals=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productList where difficultShippingPrice not equals to UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldBeFound("difficultShippingPrice.notEquals=" + UPDATED_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDifficultShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficultShippingPrice in DEFAULT_DIFFICULT_SHIPPING_PRICE or UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldBeFound(
            "difficultShippingPrice.in=" + DEFAULT_DIFFICULT_SHIPPING_PRICE + "," + UPDATED_DIFFICULT_SHIPPING_PRICE
        );

        // Get all the productList where difficultShippingPrice equals to UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("difficultShippingPrice.in=" + UPDATED_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDifficultShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficultShippingPrice is not null
        defaultProductShouldBeFound("difficultShippingPrice.specified=true");

        // Get all the productList where difficultShippingPrice is null
        defaultProductShouldNotBeFound("difficultShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByDifficultShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficultShippingPrice is greater than or equal to DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldBeFound("difficultShippingPrice.greaterThanOrEqual=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productList where difficultShippingPrice is greater than or equal to UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("difficultShippingPrice.greaterThanOrEqual=" + UPDATED_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDifficultShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficultShippingPrice is less than or equal to DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldBeFound("difficultShippingPrice.lessThanOrEqual=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productList where difficultShippingPrice is less than or equal to SMALLER_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("difficultShippingPrice.lessThanOrEqual=" + SMALLER_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDifficultShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficultShippingPrice is less than DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("difficultShippingPrice.lessThan=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productList where difficultShippingPrice is less than UPDATED_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldBeFound("difficultShippingPrice.lessThan=" + UPDATED_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByDifficultShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where difficultShippingPrice is greater than DEFAULT_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldNotBeFound("difficultShippingPrice.greaterThan=" + DEFAULT_DIFFICULT_SHIPPING_PRICE);

        // Get all the productList where difficultShippingPrice is greater than SMALLER_DIFFICULT_SHIPPING_PRICE
        defaultProductShouldBeFound("difficultShippingPrice.greaterThan=" + SMALLER_DIFFICULT_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundShippingPrice equals to DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShouldBeFound("refundShippingPrice.equals=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productList where refundShippingPrice equals to UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShouldNotBeFound("refundShippingPrice.equals=" + UPDATED_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundShippingPrice not equals to DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShouldNotBeFound("refundShippingPrice.notEquals=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productList where refundShippingPrice not equals to UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShouldBeFound("refundShippingPrice.notEquals=" + UPDATED_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundShippingPrice in DEFAULT_REFUND_SHIPPING_PRICE or UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShouldBeFound("refundShippingPrice.in=" + DEFAULT_REFUND_SHIPPING_PRICE + "," + UPDATED_REFUND_SHIPPING_PRICE);

        // Get all the productList where refundShippingPrice equals to UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShouldNotBeFound("refundShippingPrice.in=" + UPDATED_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundShippingPrice is not null
        defaultProductShouldBeFound("refundShippingPrice.specified=true");

        // Get all the productList where refundShippingPrice is null
        defaultProductShouldNotBeFound("refundShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByRefundShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundShippingPrice is greater than or equal to DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShouldBeFound("refundShippingPrice.greaterThanOrEqual=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productList where refundShippingPrice is greater than or equal to UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShouldNotBeFound("refundShippingPrice.greaterThanOrEqual=" + UPDATED_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundShippingPrice is less than or equal to DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShouldBeFound("refundShippingPrice.lessThanOrEqual=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productList where refundShippingPrice is less than or equal to SMALLER_REFUND_SHIPPING_PRICE
        defaultProductShouldNotBeFound("refundShippingPrice.lessThanOrEqual=" + SMALLER_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundShippingPrice is less than DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShouldNotBeFound("refundShippingPrice.lessThan=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productList where refundShippingPrice is less than UPDATED_REFUND_SHIPPING_PRICE
        defaultProductShouldBeFound("refundShippingPrice.lessThan=" + UPDATED_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByRefundShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where refundShippingPrice is greater than DEFAULT_REFUND_SHIPPING_PRICE
        defaultProductShouldNotBeFound("refundShippingPrice.greaterThan=" + DEFAULT_REFUND_SHIPPING_PRICE);

        // Get all the productList where refundShippingPrice is greater than SMALLER_REFUND_SHIPPING_PRICE
        defaultProductShouldBeFound("refundShippingPrice.greaterThan=" + SMALLER_REFUND_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingPriceIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingPrice equals to DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldBeFound("exchangeShippingPrice.equals=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productList where exchangeShippingPrice equals to UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("exchangeShippingPrice.equals=" + UPDATED_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingPriceIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingPrice not equals to DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("exchangeShippingPrice.notEquals=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productList where exchangeShippingPrice not equals to UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldBeFound("exchangeShippingPrice.notEquals=" + UPDATED_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingPriceIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingPrice in DEFAULT_EXCHANGE_SHIPPING_PRICE or UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldBeFound("exchangeShippingPrice.in=" + DEFAULT_EXCHANGE_SHIPPING_PRICE + "," + UPDATED_EXCHANGE_SHIPPING_PRICE);

        // Get all the productList where exchangeShippingPrice equals to UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("exchangeShippingPrice.in=" + UPDATED_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingPriceIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingPrice is not null
        defaultProductShouldBeFound("exchangeShippingPrice.specified=true");

        // Get all the productList where exchangeShippingPrice is null
        defaultProductShouldNotBeFound("exchangeShippingPrice.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingPriceIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingPrice is greater than or equal to DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldBeFound("exchangeShippingPrice.greaterThanOrEqual=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productList where exchangeShippingPrice is greater than or equal to UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("exchangeShippingPrice.greaterThanOrEqual=" + UPDATED_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingPriceIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingPrice is less than or equal to DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldBeFound("exchangeShippingPrice.lessThanOrEqual=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productList where exchangeShippingPrice is less than or equal to SMALLER_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("exchangeShippingPrice.lessThanOrEqual=" + SMALLER_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingPriceIsLessThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingPrice is less than DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("exchangeShippingPrice.lessThan=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productList where exchangeShippingPrice is less than UPDATED_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldBeFound("exchangeShippingPrice.lessThan=" + UPDATED_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingPriceIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingPrice is greater than DEFAULT_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldNotBeFound("exchangeShippingPrice.greaterThan=" + DEFAULT_EXCHANGE_SHIPPING_PRICE);

        // Get all the productList where exchangeShippingPrice is greater than SMALLER_EXCHANGE_SHIPPING_PRICE
        defaultProductShouldBeFound("exchangeShippingPrice.greaterThan=" + SMALLER_EXCHANGE_SHIPPING_PRICE);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingFileUrl equals to DEFAULT_EXCHANGE_SHIPPING_FILE_URL
        defaultProductShouldBeFound("exchangeShippingFileUrl.equals=" + DEFAULT_EXCHANGE_SHIPPING_FILE_URL);

        // Get all the productList where exchangeShippingFileUrl equals to UPDATED_EXCHANGE_SHIPPING_FILE_URL
        defaultProductShouldNotBeFound("exchangeShippingFileUrl.equals=" + UPDATED_EXCHANGE_SHIPPING_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingFileUrl not equals to DEFAULT_EXCHANGE_SHIPPING_FILE_URL
        defaultProductShouldNotBeFound("exchangeShippingFileUrl.notEquals=" + DEFAULT_EXCHANGE_SHIPPING_FILE_URL);

        // Get all the productList where exchangeShippingFileUrl not equals to UPDATED_EXCHANGE_SHIPPING_FILE_URL
        defaultProductShouldBeFound("exchangeShippingFileUrl.notEquals=" + UPDATED_EXCHANGE_SHIPPING_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingFileUrl in DEFAULT_EXCHANGE_SHIPPING_FILE_URL or UPDATED_EXCHANGE_SHIPPING_FILE_URL
        defaultProductShouldBeFound(
            "exchangeShippingFileUrl.in=" + DEFAULT_EXCHANGE_SHIPPING_FILE_URL + "," + UPDATED_EXCHANGE_SHIPPING_FILE_URL
        );

        // Get all the productList where exchangeShippingFileUrl equals to UPDATED_EXCHANGE_SHIPPING_FILE_URL
        defaultProductShouldNotBeFound("exchangeShippingFileUrl.in=" + UPDATED_EXCHANGE_SHIPPING_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingFileUrl is not null
        defaultProductShouldBeFound("exchangeShippingFileUrl.specified=true");

        // Get all the productList where exchangeShippingFileUrl is null
        defaultProductShouldNotBeFound("exchangeShippingFileUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingFileUrlContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingFileUrl contains DEFAULT_EXCHANGE_SHIPPING_FILE_URL
        defaultProductShouldBeFound("exchangeShippingFileUrl.contains=" + DEFAULT_EXCHANGE_SHIPPING_FILE_URL);

        // Get all the productList where exchangeShippingFileUrl contains UPDATED_EXCHANGE_SHIPPING_FILE_URL
        defaultProductShouldNotBeFound("exchangeShippingFileUrl.contains=" + UPDATED_EXCHANGE_SHIPPING_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByExchangeShippingFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where exchangeShippingFileUrl does not contain DEFAULT_EXCHANGE_SHIPPING_FILE_URL
        defaultProductShouldNotBeFound("exchangeShippingFileUrl.doesNotContain=" + DEFAULT_EXCHANGE_SHIPPING_FILE_URL);

        // Get all the productList where exchangeShippingFileUrl does not contain UPDATED_EXCHANGE_SHIPPING_FILE_URL
        defaultProductShouldBeFound("exchangeShippingFileUrl.doesNotContain=" + UPDATED_EXCHANGE_SHIPPING_FILE_URL);
    }

    @Test
    @Transactional
    void getAllProductsByIsViewIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isView equals to DEFAULT_IS_VIEW
        defaultProductShouldBeFound("isView.equals=" + DEFAULT_IS_VIEW);

        // Get all the productList where isView equals to UPDATED_IS_VIEW
        defaultProductShouldNotBeFound("isView.equals=" + UPDATED_IS_VIEW);
    }

    @Test
    @Transactional
    void getAllProductsByIsViewIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isView not equals to DEFAULT_IS_VIEW
        defaultProductShouldNotBeFound("isView.notEquals=" + DEFAULT_IS_VIEW);

        // Get all the productList where isView not equals to UPDATED_IS_VIEW
        defaultProductShouldBeFound("isView.notEquals=" + UPDATED_IS_VIEW);
    }

    @Test
    @Transactional
    void getAllProductsByIsViewIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isView in DEFAULT_IS_VIEW or UPDATED_IS_VIEW
        defaultProductShouldBeFound("isView.in=" + DEFAULT_IS_VIEW + "," + UPDATED_IS_VIEW);

        // Get all the productList where isView equals to UPDATED_IS_VIEW
        defaultProductShouldNotBeFound("isView.in=" + UPDATED_IS_VIEW);
    }

    @Test
    @Transactional
    void getAllProductsByIsViewIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where isView is not null
        defaultProductShouldBeFound("isView.specified=true");

        // Get all the productList where isView is null
        defaultProductShouldNotBeFound("isView.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByViewReservationDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where viewReservationDate equals to DEFAULT_VIEW_RESERVATION_DATE
        defaultProductShouldBeFound("viewReservationDate.equals=" + DEFAULT_VIEW_RESERVATION_DATE);

        // Get all the productList where viewReservationDate equals to UPDATED_VIEW_RESERVATION_DATE
        defaultProductShouldNotBeFound("viewReservationDate.equals=" + UPDATED_VIEW_RESERVATION_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByViewReservationDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where viewReservationDate not equals to DEFAULT_VIEW_RESERVATION_DATE
        defaultProductShouldNotBeFound("viewReservationDate.notEquals=" + DEFAULT_VIEW_RESERVATION_DATE);

        // Get all the productList where viewReservationDate not equals to UPDATED_VIEW_RESERVATION_DATE
        defaultProductShouldBeFound("viewReservationDate.notEquals=" + UPDATED_VIEW_RESERVATION_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByViewReservationDateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where viewReservationDate in DEFAULT_VIEW_RESERVATION_DATE or UPDATED_VIEW_RESERVATION_DATE
        defaultProductShouldBeFound("viewReservationDate.in=" + DEFAULT_VIEW_RESERVATION_DATE + "," + UPDATED_VIEW_RESERVATION_DATE);

        // Get all the productList where viewReservationDate equals to UPDATED_VIEW_RESERVATION_DATE
        defaultProductShouldNotBeFound("viewReservationDate.in=" + UPDATED_VIEW_RESERVATION_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByViewReservationDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where viewReservationDate is not null
        defaultProductShouldBeFound("viewReservationDate.specified=true");

        // Get all the productList where viewReservationDate is null
        defaultProductShouldNotBeFound("viewReservationDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where activated equals to DEFAULT_ACTIVATED
        defaultProductShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productList where activated equals to UPDATED_ACTIVATED
        defaultProductShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductsByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where activated not equals to DEFAULT_ACTIVATED
        defaultProductShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productList where activated not equals to UPDATED_ACTIVATED
        defaultProductShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductsByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productList where activated equals to UPDATED_ACTIVATED
        defaultProductShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductsByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where activated is not null
        defaultProductShouldBeFound("activated.specified=true");

        // Get all the productList where activated is null
        defaultProductShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productList where createdBy equals to UPDATED_CREATED_BY
        defaultProductShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productList where createdBy equals to UPDATED_CREATED_BY
        defaultProductShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy is not null
        defaultProductShouldBeFound("createdBy.specified=true");

        // Get all the productList where createdBy is null
        defaultProductShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy contains DEFAULT_CREATED_BY
        defaultProductShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productList where createdBy contains UPDATED_CREATED_BY
        defaultProductShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where createdDate is not null
        defaultProductShouldBeFound("createdDate.specified=true");

        // Get all the productList where createdDate is null
        defaultProductShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedBy is not null
        defaultProductShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productList where lastModifiedBy is null
        defaultProductShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductsByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);

        // Get all the productList where lastModifiedDate is not null
        defaultProductShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productList where lastModifiedDate is null
        defaultProductShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductsByProductCategoryRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductCategoryRel productCategoryRel = ProductCategoryRelResourceIT.createEntity(em);
        em.persist(productCategoryRel);
        em.flush();
        product.addProductCategoryRel(productCategoryRel);
        productRepository.saveAndFlush(product);
        Long productCategoryRelId = productCategoryRel.getId();

        // Get all the productList where productCategoryRel equals to productCategoryRelId
        defaultProductShouldBeFound("productCategoryRelId.equals=" + productCategoryRelId);

        // Get all the productList where productCategoryRel equals to (productCategoryRelId + 1)
        defaultProductShouldNotBeFound("productCategoryRelId.equals=" + (productCategoryRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductLabelRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductLabelRel productLabelRel = ProductLabelRelResourceIT.createEntity(em);
        em.persist(productLabelRel);
        em.flush();
        product.addProductLabelRel(productLabelRel);
        productRepository.saveAndFlush(product);
        Long productLabelRelId = productLabelRel.getId();

        // Get all the productList where productLabelRel equals to productLabelRelId
        defaultProductShouldBeFound("productLabelRelId.equals=" + productLabelRelId);

        // Get all the productList where productLabelRel equals to (productLabelRelId + 1)
        defaultProductShouldNotBeFound("productLabelRelId.equals=" + (productLabelRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductMappingRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductMappingRel productMappingRel = ProductMappingRelResourceIT.createEntity(em);
        em.persist(productMappingRel);
        em.flush();
        product.addProductMappingRel(productMappingRel);
        productRepository.saveAndFlush(product);
        Long productMappingRelId = productMappingRel.getId();

        // Get all the productList where productMappingRel equals to productMappingRelId
        defaultProductShouldBeFound("productMappingRelId.equals=" + productMappingRelId);

        // Get all the productList where productMappingRel equals to (productMappingRelId + 1)
        defaultProductShouldNotBeFound("productMappingRelId.equals=" + (productMappingRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductViewRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductViewRel productViewRel = ProductViewRelResourceIT.createEntity(em);
        em.persist(productViewRel);
        em.flush();
        product.addProductViewRel(productViewRel);
        productRepository.saveAndFlush(product);
        Long productViewRelId = productViewRel.getId();

        // Get all the productList where productViewRel equals to productViewRelId
        defaultProductShouldBeFound("productViewRelId.equals=" + productViewRelId);

        // Get all the productList where productViewRel equals to (productViewRelId + 1)
        defaultProductShouldNotBeFound("productViewRelId.equals=" + (productViewRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductNoticeRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductNoticeRel productNoticeRel = ProductNoticeRelResourceIT.createEntity(em);
        em.persist(productNoticeRel);
        em.flush();
        product.addProductNoticeRel(productNoticeRel);
        productRepository.saveAndFlush(product);
        Long productNoticeRelId = productNoticeRel.getId();

        // Get all the productList where productNoticeRel equals to productNoticeRelId
        defaultProductShouldBeFound("productNoticeRelId.equals=" + productNoticeRelId);

        // Get all the productList where productNoticeRel equals to (productNoticeRelId + 1)
        defaultProductShouldNotBeFound("productNoticeRelId.equals=" + (productNoticeRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductShippingRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductShippingRel productShippingRel = ProductShippingRelResourceIT.createEntity(em);
        em.persist(productShippingRel);
        em.flush();
        product.addProductShippingRel(productShippingRel);
        productRepository.saveAndFlush(product);
        Long productShippingRelId = productShippingRel.getId();

        // Get all the productList where productShippingRel equals to productShippingRelId
        defaultProductShouldBeFound("productShippingRelId.equals=" + productShippingRelId);

        // Get all the productList where productShippingRel equals to (productShippingRelId + 1)
        defaultProductShouldNotBeFound("productShippingRelId.equals=" + (productShippingRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductTemplateRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductTemplateRel productTemplateRel = ProductTemplateRelResourceIT.createEntity(em);
        em.persist(productTemplateRel);
        em.flush();
        product.addProductTemplateRel(productTemplateRel);
        productRepository.saveAndFlush(product);
        Long productTemplateRelId = productTemplateRel.getId();

        // Get all the productList where productTemplateRel equals to productTemplateRelId
        defaultProductShouldBeFound("productTemplateRelId.equals=" + productTemplateRelId);

        // Get all the productList where productTemplateRel equals to (productTemplateRelId + 1)
        defaultProductShouldNotBeFound("productTemplateRelId.equals=" + (productTemplateRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductOptionRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductOptionRel productOptionRel = ProductOptionRelResourceIT.createEntity(em);
        em.persist(productOptionRel);
        em.flush();
        product.addProductOptionRel(productOptionRel);
        productRepository.saveAndFlush(product);
        Long productOptionRelId = productOptionRel.getId();

        // Get all the productList where productOptionRel equals to productOptionRelId
        defaultProductShouldBeFound("productOptionRelId.equals=" + productOptionRelId);

        // Get all the productList where productOptionRel equals to (productOptionRelId + 1)
        defaultProductShouldNotBeFound("productOptionRelId.equals=" + (productOptionRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductClazzRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductClazzRel productClazzRel = ProductClazzRelResourceIT.createEntity(em);
        em.persist(productClazzRel);
        em.flush();
        product.addProductClazzRel(productClazzRel);
        productRepository.saveAndFlush(product);
        Long productClazzRelId = productClazzRel.getId();

        // Get all the productList where productClazzRel equals to productClazzRelId
        defaultProductShouldBeFound("productClazzRelId.equals=" + productClazzRelId);

        // Get all the productList where productClazzRel equals to (productClazzRelId + 1)
        defaultProductShouldNotBeFound("productClazzRelId.equals=" + (productClazzRelId + 1));
    }

    @Test
    @Transactional
    void getAllProductsByProductStoreRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productRepository.saveAndFlush(product);
        ProductStoreRel productStoreRel = ProductStoreRelResourceIT.createEntity(em);
        em.persist(productStoreRel);
        em.flush();
        product.addProductStoreRel(productStoreRel);
        productRepository.saveAndFlush(product);
        Long productStoreRelId = productStoreRel.getId();

        // Get all the productList where productStoreRel equals to productStoreRelId
        defaultProductShouldBeFound("productStoreRelId.equals=" + productStoreRelId);

        // Get all the productList where productStoreRel equals to (productStoreRelId + 1)
        defaultProductShouldNotBeFound("productStoreRelId.equals=" + (productStoreRelId + 1));
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
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].calculationDateFrom").value(hasItem(DEFAULT_CALCULATION_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].calculationDateTo").value(hasItem(DEFAULT_CALCULATION_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].allPriceUnit").value(hasItem(DEFAULT_ALL_PRICE_UNIT)))
            .andExpect(jsonPath("$.[*].discount").value(hasItem(DEFAULT_DISCOUNT)))
            .andExpect(jsonPath("$.[*].discountPrice").value(hasItem(DEFAULT_DISCOUNT_PRICE)))
            .andExpect(jsonPath("$.[*].discountUnit").value(hasItem(DEFAULT_DISCOUNT_UNIT)))
            .andExpect(jsonPath("$.[*].discountDateFrom").value(hasItem(DEFAULT_DISCOUNT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].discountDateTo").value(hasItem(DEFAULT_DISCOUNT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].isInstallment").value(hasItem(DEFAULT_IS_INSTALLMENT.booleanValue())))
            .andExpect(jsonPath("$.[*].installmentMonth").value(hasItem(DEFAULT_INSTALLMENT_MONTH)))
            .andExpect(jsonPath("$.[*].isSell").value(hasItem(DEFAULT_IS_SELL.booleanValue())))
            .andExpect(jsonPath("$.[*].sellDateFrom").value(hasItem(DEFAULT_SELL_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].sellDateTo").value(hasItem(DEFAULT_SELL_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].minPurchaseAmount").value(hasItem(DEFAULT_MIN_PURCHASE_AMOUNT)))
            .andExpect(jsonPath("$.[*].manPurchaseAmount").value(hasItem(DEFAULT_MAN_PURCHASE_AMOUNT)))
            .andExpect(jsonPath("$.[*].mainImageFileUrl").value(hasItem(DEFAULT_MAIN_IMAGE_FILE_URL)))
            .andExpect(jsonPath("$.[*].addImageFileUrl").value(hasItem(DEFAULT_ADD_IMAGE_FILE_URL.toString())))
            .andExpect(jsonPath("$.[*].mainVideoFileUrl").value(hasItem(DEFAULT_MAIN_VIDEO_FILE_URL)))
            .andExpect(jsonPath("$.[*].descriptionFileUrl").value(hasItem(DEFAULT_DESCRIPTION_FILE_URL)))
            .andExpect(jsonPath("$.[*].shippingType").value(hasItem(DEFAULT_SHIPPING_TYPE)))
            .andExpect(jsonPath("$.[*].separateShippingPriceType").value(hasItem(DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE)))
            .andExpect(jsonPath("$.[*].defaultShippingPrice").value(hasItem(DEFAULT_DEFAULT_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].freeShippingPrice").value(hasItem(DEFAULT_FREE_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].jejuShippingPrice").value(hasItem(DEFAULT_JEJU_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].difficultShippingPrice").value(hasItem(DEFAULT_DIFFICULT_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].refundShippingPrice").value(hasItem(DEFAULT_REFUND_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].exchangeShippingPrice").value(hasItem(DEFAULT_EXCHANGE_SHIPPING_PRICE)))
            .andExpect(jsonPath("$.[*].exchangeShippingFileUrl").value(hasItem(DEFAULT_EXCHANGE_SHIPPING_FILE_URL)))
            .andExpect(jsonPath("$.[*].isView").value(hasItem(DEFAULT_IS_VIEW.booleanValue())))
            .andExpect(jsonPath("$.[*].viewReservationDate").value(hasItem(DEFAULT_VIEW_RESERVATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

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
            .code(UPDATED_CODE)
            .calculation(UPDATED_CALCULATION)
            .calculationDateFrom(UPDATED_CALCULATION_DATE_FROM)
            .calculationDateTo(UPDATED_CALCULATION_DATE_TO)
            .price(UPDATED_PRICE)
            .allPriceUnit(UPDATED_ALL_PRICE_UNIT)
            .discount(UPDATED_DISCOUNT)
            .discountPrice(UPDATED_DISCOUNT_PRICE)
            .discountUnit(UPDATED_DISCOUNT_UNIT)
            .discountDateFrom(UPDATED_DISCOUNT_DATE_FROM)
            .discountDateTo(UPDATED_DISCOUNT_DATE_TO)
            .isInstallment(UPDATED_IS_INSTALLMENT)
            .installmentMonth(UPDATED_INSTALLMENT_MONTH)
            .isSell(UPDATED_IS_SELL)
            .sellDateFrom(UPDATED_SELL_DATE_FROM)
            .sellDateTo(UPDATED_SELL_DATE_TO)
            .minPurchaseAmount(UPDATED_MIN_PURCHASE_AMOUNT)
            .manPurchaseAmount(UPDATED_MAN_PURCHASE_AMOUNT)
            .mainImageFileUrl(UPDATED_MAIN_IMAGE_FILE_URL)
            .addImageFileUrl(UPDATED_ADD_IMAGE_FILE_URL)
            .mainVideoFileUrl(UPDATED_MAIN_VIDEO_FILE_URL)
            .descriptionFileUrl(UPDATED_DESCRIPTION_FILE_URL)
            .shippingType(UPDATED_SHIPPING_TYPE)
            .separateShippingPriceType(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE)
            .defaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE)
            .freeShippingPrice(UPDATED_FREE_SHIPPING_PRICE)
            .jejuShippingPrice(UPDATED_JEJU_SHIPPING_PRICE)
            .difficultShippingPrice(UPDATED_DIFFICULT_SHIPPING_PRICE)
            .refundShippingPrice(UPDATED_REFUND_SHIPPING_PRICE)
            .exchangeShippingPrice(UPDATED_EXCHANGE_SHIPPING_PRICE)
            .exchangeShippingFileUrl(UPDATED_EXCHANGE_SHIPPING_FILE_URL)
            .isView(UPDATED_IS_VIEW)
            .viewReservationDate(UPDATED_VIEW_RESERVATION_DATE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
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
        assertThat(testProduct.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProduct.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProduct.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testProduct.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getAllPriceUnit()).isEqualTo(UPDATED_ALL_PRICE_UNIT);
        assertThat(testProduct.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testProduct.getDiscountPrice()).isEqualTo(UPDATED_DISCOUNT_PRICE);
        assertThat(testProduct.getDiscountUnit()).isEqualTo(UPDATED_DISCOUNT_UNIT);
        assertThat(testProduct.getDiscountDateFrom()).isEqualTo(UPDATED_DISCOUNT_DATE_FROM);
        assertThat(testProduct.getDiscountDateTo()).isEqualTo(UPDATED_DISCOUNT_DATE_TO);
        assertThat(testProduct.getIsInstallment()).isEqualTo(UPDATED_IS_INSTALLMENT);
        assertThat(testProduct.getInstallmentMonth()).isEqualTo(UPDATED_INSTALLMENT_MONTH);
        assertThat(testProduct.getIsSell()).isEqualTo(UPDATED_IS_SELL);
        assertThat(testProduct.getSellDateFrom()).isEqualTo(UPDATED_SELL_DATE_FROM);
        assertThat(testProduct.getSellDateTo()).isEqualTo(UPDATED_SELL_DATE_TO);
        assertThat(testProduct.getMinPurchaseAmount()).isEqualTo(UPDATED_MIN_PURCHASE_AMOUNT);
        assertThat(testProduct.getManPurchaseAmount()).isEqualTo(UPDATED_MAN_PURCHASE_AMOUNT);
        assertThat(testProduct.getMainImageFileUrl()).isEqualTo(UPDATED_MAIN_IMAGE_FILE_URL);
        assertThat(testProduct.getAddImageFileUrl()).isEqualTo(UPDATED_ADD_IMAGE_FILE_URL);
        assertThat(testProduct.getMainVideoFileUrl()).isEqualTo(UPDATED_MAIN_VIDEO_FILE_URL);
        assertThat(testProduct.getDescriptionFileUrl()).isEqualTo(UPDATED_DESCRIPTION_FILE_URL);
        assertThat(testProduct.getShippingType()).isEqualTo(UPDATED_SHIPPING_TYPE);
        assertThat(testProduct.getSeparateShippingPriceType()).isEqualTo(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
        assertThat(testProduct.getDefaultShippingPrice()).isEqualTo(UPDATED_DEFAULT_SHIPPING_PRICE);
        assertThat(testProduct.getFreeShippingPrice()).isEqualTo(UPDATED_FREE_SHIPPING_PRICE);
        assertThat(testProduct.getJejuShippingPrice()).isEqualTo(UPDATED_JEJU_SHIPPING_PRICE);
        assertThat(testProduct.getDifficultShippingPrice()).isEqualTo(UPDATED_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProduct.getRefundShippingPrice()).isEqualTo(UPDATED_REFUND_SHIPPING_PRICE);
        assertThat(testProduct.getExchangeShippingPrice()).isEqualTo(UPDATED_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProduct.getExchangeShippingFileUrl()).isEqualTo(UPDATED_EXCHANGE_SHIPPING_FILE_URL);
        assertThat(testProduct.getIsView()).isEqualTo(UPDATED_IS_VIEW);
        assertThat(testProduct.getViewReservationDate()).isEqualTo(UPDATED_VIEW_RESERVATION_DATE);
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

        partialUpdatedProduct
            .calculation(UPDATED_CALCULATION)
            .calculationDateTo(UPDATED_CALCULATION_DATE_TO)
            .price(UPDATED_PRICE)
            .discount(UPDATED_DISCOUNT)
            .discountPrice(UPDATED_DISCOUNT_PRICE)
            .discountUnit(UPDATED_DISCOUNT_UNIT)
            .discountDateFrom(UPDATED_DISCOUNT_DATE_FROM)
            .discountDateTo(UPDATED_DISCOUNT_DATE_TO)
            .isSell(UPDATED_IS_SELL)
            .sellDateFrom(UPDATED_SELL_DATE_FROM)
            .sellDateTo(UPDATED_SELL_DATE_TO)
            .mainImageFileUrl(UPDATED_MAIN_IMAGE_FILE_URL)
            .addImageFileUrl(UPDATED_ADD_IMAGE_FILE_URL)
            .shippingType(UPDATED_SHIPPING_TYPE)
            .separateShippingPriceType(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE)
            .defaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE)
            .freeShippingPrice(UPDATED_FREE_SHIPPING_PRICE)
            .jejuShippingPrice(UPDATED_JEJU_SHIPPING_PRICE)
            .refundShippingPrice(UPDATED_REFUND_SHIPPING_PRICE)
            .exchangeShippingFileUrl(UPDATED_EXCHANGE_SHIPPING_FILE_URL)
            .isView(UPDATED_IS_VIEW)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

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
        assertThat(testProduct.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testProduct.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProduct.getCalculationDateFrom()).isEqualTo(DEFAULT_CALCULATION_DATE_FROM);
        assertThat(testProduct.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getAllPriceUnit()).isEqualTo(DEFAULT_ALL_PRICE_UNIT);
        assertThat(testProduct.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testProduct.getDiscountPrice()).isEqualTo(UPDATED_DISCOUNT_PRICE);
        assertThat(testProduct.getDiscountUnit()).isEqualTo(UPDATED_DISCOUNT_UNIT);
        assertThat(testProduct.getDiscountDateFrom()).isEqualTo(UPDATED_DISCOUNT_DATE_FROM);
        assertThat(testProduct.getDiscountDateTo()).isEqualTo(UPDATED_DISCOUNT_DATE_TO);
        assertThat(testProduct.getIsInstallment()).isEqualTo(DEFAULT_IS_INSTALLMENT);
        assertThat(testProduct.getInstallmentMonth()).isEqualTo(DEFAULT_INSTALLMENT_MONTH);
        assertThat(testProduct.getIsSell()).isEqualTo(UPDATED_IS_SELL);
        assertThat(testProduct.getSellDateFrom()).isEqualTo(UPDATED_SELL_DATE_FROM);
        assertThat(testProduct.getSellDateTo()).isEqualTo(UPDATED_SELL_DATE_TO);
        assertThat(testProduct.getMinPurchaseAmount()).isEqualTo(DEFAULT_MIN_PURCHASE_AMOUNT);
        assertThat(testProduct.getManPurchaseAmount()).isEqualTo(DEFAULT_MAN_PURCHASE_AMOUNT);
        assertThat(testProduct.getMainImageFileUrl()).isEqualTo(UPDATED_MAIN_IMAGE_FILE_URL);
        assertThat(testProduct.getAddImageFileUrl()).isEqualTo(UPDATED_ADD_IMAGE_FILE_URL);
        assertThat(testProduct.getMainVideoFileUrl()).isEqualTo(DEFAULT_MAIN_VIDEO_FILE_URL);
        assertThat(testProduct.getDescriptionFileUrl()).isEqualTo(DEFAULT_DESCRIPTION_FILE_URL);
        assertThat(testProduct.getShippingType()).isEqualTo(UPDATED_SHIPPING_TYPE);
        assertThat(testProduct.getSeparateShippingPriceType()).isEqualTo(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
        assertThat(testProduct.getDefaultShippingPrice()).isEqualTo(UPDATED_DEFAULT_SHIPPING_PRICE);
        assertThat(testProduct.getFreeShippingPrice()).isEqualTo(UPDATED_FREE_SHIPPING_PRICE);
        assertThat(testProduct.getJejuShippingPrice()).isEqualTo(UPDATED_JEJU_SHIPPING_PRICE);
        assertThat(testProduct.getDifficultShippingPrice()).isEqualTo(DEFAULT_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProduct.getRefundShippingPrice()).isEqualTo(UPDATED_REFUND_SHIPPING_PRICE);
        assertThat(testProduct.getExchangeShippingPrice()).isEqualTo(DEFAULT_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProduct.getExchangeShippingFileUrl()).isEqualTo(UPDATED_EXCHANGE_SHIPPING_FILE_URL);
        assertThat(testProduct.getIsView()).isEqualTo(UPDATED_IS_VIEW);
        assertThat(testProduct.getViewReservationDate()).isEqualTo(DEFAULT_VIEW_RESERVATION_DATE);
        assertThat(testProduct.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProduct.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProduct.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProduct.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProduct.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
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
            .code(UPDATED_CODE)
            .calculation(UPDATED_CALCULATION)
            .calculationDateFrom(UPDATED_CALCULATION_DATE_FROM)
            .calculationDateTo(UPDATED_CALCULATION_DATE_TO)
            .price(UPDATED_PRICE)
            .allPriceUnit(UPDATED_ALL_PRICE_UNIT)
            .discount(UPDATED_DISCOUNT)
            .discountPrice(UPDATED_DISCOUNT_PRICE)
            .discountUnit(UPDATED_DISCOUNT_UNIT)
            .discountDateFrom(UPDATED_DISCOUNT_DATE_FROM)
            .discountDateTo(UPDATED_DISCOUNT_DATE_TO)
            .isInstallment(UPDATED_IS_INSTALLMENT)
            .installmentMonth(UPDATED_INSTALLMENT_MONTH)
            .isSell(UPDATED_IS_SELL)
            .sellDateFrom(UPDATED_SELL_DATE_FROM)
            .sellDateTo(UPDATED_SELL_DATE_TO)
            .minPurchaseAmount(UPDATED_MIN_PURCHASE_AMOUNT)
            .manPurchaseAmount(UPDATED_MAN_PURCHASE_AMOUNT)
            .mainImageFileUrl(UPDATED_MAIN_IMAGE_FILE_URL)
            .addImageFileUrl(UPDATED_ADD_IMAGE_FILE_URL)
            .mainVideoFileUrl(UPDATED_MAIN_VIDEO_FILE_URL)
            .descriptionFileUrl(UPDATED_DESCRIPTION_FILE_URL)
            .shippingType(UPDATED_SHIPPING_TYPE)
            .separateShippingPriceType(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE)
            .defaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE)
            .freeShippingPrice(UPDATED_FREE_SHIPPING_PRICE)
            .jejuShippingPrice(UPDATED_JEJU_SHIPPING_PRICE)
            .difficultShippingPrice(UPDATED_DIFFICULT_SHIPPING_PRICE)
            .refundShippingPrice(UPDATED_REFUND_SHIPPING_PRICE)
            .exchangeShippingPrice(UPDATED_EXCHANGE_SHIPPING_PRICE)
            .exchangeShippingFileUrl(UPDATED_EXCHANGE_SHIPPING_FILE_URL)
            .isView(UPDATED_IS_VIEW)
            .viewReservationDate(UPDATED_VIEW_RESERVATION_DATE)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

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
        assertThat(testProduct.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testProduct.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProduct.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testProduct.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProduct.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProduct.getAllPriceUnit()).isEqualTo(UPDATED_ALL_PRICE_UNIT);
        assertThat(testProduct.getDiscount()).isEqualTo(UPDATED_DISCOUNT);
        assertThat(testProduct.getDiscountPrice()).isEqualTo(UPDATED_DISCOUNT_PRICE);
        assertThat(testProduct.getDiscountUnit()).isEqualTo(UPDATED_DISCOUNT_UNIT);
        assertThat(testProduct.getDiscountDateFrom()).isEqualTo(UPDATED_DISCOUNT_DATE_FROM);
        assertThat(testProduct.getDiscountDateTo()).isEqualTo(UPDATED_DISCOUNT_DATE_TO);
        assertThat(testProduct.getIsInstallment()).isEqualTo(UPDATED_IS_INSTALLMENT);
        assertThat(testProduct.getInstallmentMonth()).isEqualTo(UPDATED_INSTALLMENT_MONTH);
        assertThat(testProduct.getIsSell()).isEqualTo(UPDATED_IS_SELL);
        assertThat(testProduct.getSellDateFrom()).isEqualTo(UPDATED_SELL_DATE_FROM);
        assertThat(testProduct.getSellDateTo()).isEqualTo(UPDATED_SELL_DATE_TO);
        assertThat(testProduct.getMinPurchaseAmount()).isEqualTo(UPDATED_MIN_PURCHASE_AMOUNT);
        assertThat(testProduct.getManPurchaseAmount()).isEqualTo(UPDATED_MAN_PURCHASE_AMOUNT);
        assertThat(testProduct.getMainImageFileUrl()).isEqualTo(UPDATED_MAIN_IMAGE_FILE_URL);
        assertThat(testProduct.getAddImageFileUrl()).isEqualTo(UPDATED_ADD_IMAGE_FILE_URL);
        assertThat(testProduct.getMainVideoFileUrl()).isEqualTo(UPDATED_MAIN_VIDEO_FILE_URL);
        assertThat(testProduct.getDescriptionFileUrl()).isEqualTo(UPDATED_DESCRIPTION_FILE_URL);
        assertThat(testProduct.getShippingType()).isEqualTo(UPDATED_SHIPPING_TYPE);
        assertThat(testProduct.getSeparateShippingPriceType()).isEqualTo(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
        assertThat(testProduct.getDefaultShippingPrice()).isEqualTo(UPDATED_DEFAULT_SHIPPING_PRICE);
        assertThat(testProduct.getFreeShippingPrice()).isEqualTo(UPDATED_FREE_SHIPPING_PRICE);
        assertThat(testProduct.getJejuShippingPrice()).isEqualTo(UPDATED_JEJU_SHIPPING_PRICE);
        assertThat(testProduct.getDifficultShippingPrice()).isEqualTo(UPDATED_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProduct.getRefundShippingPrice()).isEqualTo(UPDATED_REFUND_SHIPPING_PRICE);
        assertThat(testProduct.getExchangeShippingPrice()).isEqualTo(UPDATED_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProduct.getExchangeShippingFileUrl()).isEqualTo(UPDATED_EXCHANGE_SHIPPING_FILE_URL);
        assertThat(testProduct.getIsView()).isEqualTo(UPDATED_IS_VIEW);
        assertThat(testProduct.getViewReservationDate()).isEqualTo(UPDATED_VIEW_RESERVATION_DATE);
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
