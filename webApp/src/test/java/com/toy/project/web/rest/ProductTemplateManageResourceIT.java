package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductTemplateManage;
import com.toy.project.repository.ProductTemplateManageRepository;
import com.toy.project.service.dto.ProductTemplateManageDTO;
import com.toy.project.service.mapper.ProductTemplateManageMapper;
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
 * Integration tests for the {@link ProductTemplateManageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductTemplateManageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ILLEGAL_USAGE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_ILLEGAL_USAGE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_ILLEGAL_USAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_ILLEGAL_USAGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_EXCHANGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_EXCHANGE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_SHIPPING_RELEASE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SHIPPING_RELEASE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_SEPARATE_SHIPPING_PRICE_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_BUNDLE_SHIPPING_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_BUNDLE_SHIPPING_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_DEFAULT_SHIPPING_PRICE = 1L;
    private static final Long UPDATED_DEFAULT_SHIPPING_PRICE = 2L;

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

    private static final String ENTITY_API_URL = "/api/product-template-manages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductTemplateManageRepository productTemplateManageRepository;

    @Autowired
    private ProductTemplateManageMapper productTemplateManageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductTemplateManageMockMvc;

    private ProductTemplateManage productTemplateManage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTemplateManage createEntity(EntityManager em) {
        ProductTemplateManage productTemplateManage = new ProductTemplateManage();
        productTemplateManage.setName(DEFAULT_NAME);
        productTemplateManage.setType(DEFAULT_TYPE);
        productTemplateManage.setIllegalUsageType(DEFAULT_ILLEGAL_USAGE_TYPE);
        productTemplateManage.setIllegalUsageUrl(DEFAULT_ILLEGAL_USAGE_URL);
        productTemplateManage.setExchangeType(DEFAULT_EXCHANGE_TYPE);
        productTemplateManage.setExchangeUrl(DEFAULT_EXCHANGE_URL);
        productTemplateManage.setShippingReleaseType(DEFAULT_SHIPPING_RELEASE_TYPE);
        productTemplateManage.setSeparateShippingPriceType(DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE);
        productTemplateManage.setBundleShippingType(DEFAULT_BUNDLE_SHIPPING_TYPE);
        productTemplateManage.setDefaultShippingPrice(DEFAULT_DEFAULT_SHIPPING_PRICE);
        productTemplateManage.setActivated(DEFAULT_ACTIVATED);
        productTemplateManage.setCreatedBy(DEFAULT_CREATED_BY);
        productTemplateManage.setCreatedDate(DEFAULT_CREATED_DATE);
        productTemplateManage.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productTemplateManage.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productTemplateManage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTemplateManage createUpdatedEntity(EntityManager em) {
        ProductTemplateManage productTemplateManage = new ProductTemplateManage();
        productTemplateManage.setName(UPDATED_NAME);
        productTemplateManage.setType(UPDATED_TYPE);
        productTemplateManage.setIllegalUsageType(UPDATED_ILLEGAL_USAGE_TYPE);
        productTemplateManage.setIllegalUsageUrl(UPDATED_ILLEGAL_USAGE_URL);
        productTemplateManage.setExchangeType(UPDATED_EXCHANGE_TYPE);
        productTemplateManage.setExchangeUrl(UPDATED_EXCHANGE_URL);
        productTemplateManage.setShippingReleaseType(UPDATED_SHIPPING_RELEASE_TYPE);
        productTemplateManage.setSeparateShippingPriceType(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
        productTemplateManage.setBundleShippingType(UPDATED_BUNDLE_SHIPPING_TYPE);
        productTemplateManage.setDefaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE);
        productTemplateManage.setActivated(UPDATED_ACTIVATED);
        productTemplateManage.setCreatedBy(UPDATED_CREATED_BY);
        productTemplateManage.setCreatedDate(UPDATED_CREATED_DATE);
        productTemplateManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productTemplateManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productTemplateManage;
    }

    @BeforeEach
    public void initTest() {
        productTemplateManage = createEntity(em);
    }

    @Test
    @Transactional
    void createProductTemplateManage() throws Exception {
        int databaseSizeBeforeCreate = productTemplateManageRepository.findAll().size();
        // Create the ProductTemplateManage
        ProductTemplateManageDTO productTemplateManageDTO = productTemplateManageMapper.toDto(productTemplateManage);
        restProductTemplateManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateManageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductTemplateManage in the database
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeCreate + 1);
        ProductTemplateManage testProductTemplateManage = productTemplateManageList.get(productTemplateManageList.size() - 1);
        assertThat(testProductTemplateManage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductTemplateManage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductTemplateManage.getIllegalUsageType()).isEqualTo(DEFAULT_ILLEGAL_USAGE_TYPE);
        assertThat(testProductTemplateManage.getIllegalUsageUrl()).isEqualTo(DEFAULT_ILLEGAL_USAGE_URL);
        assertThat(testProductTemplateManage.getExchangeType()).isEqualTo(DEFAULT_EXCHANGE_TYPE);
        assertThat(testProductTemplateManage.getExchangeUrl()).isEqualTo(DEFAULT_EXCHANGE_URL);
        assertThat(testProductTemplateManage.getShippingReleaseType()).isEqualTo(DEFAULT_SHIPPING_RELEASE_TYPE);
        assertThat(testProductTemplateManage.getSeparateShippingPriceType()).isEqualTo(DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE);
        assertThat(testProductTemplateManage.getBundleShippingType()).isEqualTo(DEFAULT_BUNDLE_SHIPPING_TYPE);
        assertThat(testProductTemplateManage.getDefaultShippingPrice()).isEqualTo(DEFAULT_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductTemplateManage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductTemplateManage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductTemplateManage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductTemplateManage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductTemplateManage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductTemplateManageWithExistingId() throws Exception {
        // Create the ProductTemplateManage with an existing ID
        productTemplateManage.setId(1L);
        ProductTemplateManageDTO productTemplateManageDTO = productTemplateManageMapper.toDto(productTemplateManage);

        int databaseSizeBeforeCreate = productTemplateManageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTemplateManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplateManage in the database
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductTemplateManages() throws Exception {
        // Initialize the database
        productTemplateManageRepository.saveAndFlush(productTemplateManage);

        // Get all the productTemplateManageList
        restProductTemplateManageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTemplateManage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].illegalUsageType").value(hasItem(DEFAULT_ILLEGAL_USAGE_TYPE)))
            .andExpect(jsonPath("$.[*].illegalUsageUrl").value(hasItem(DEFAULT_ILLEGAL_USAGE_URL)))
            .andExpect(jsonPath("$.[*].exchangeType").value(hasItem(DEFAULT_EXCHANGE_TYPE)))
            .andExpect(jsonPath("$.[*].exchangeUrl").value(hasItem(DEFAULT_EXCHANGE_URL)))
            .andExpect(jsonPath("$.[*].shippingReleaseType").value(hasItem(DEFAULT_SHIPPING_RELEASE_TYPE)))
            .andExpect(jsonPath("$.[*].separateShippingPriceType").value(hasItem(DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE)))
            .andExpect(jsonPath("$.[*].bundleShippingType").value(hasItem(DEFAULT_BUNDLE_SHIPPING_TYPE)))
            .andExpect(jsonPath("$.[*].defaultShippingPrice").value(hasItem(DEFAULT_DEFAULT_SHIPPING_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductTemplateManage() throws Exception {
        // Initialize the database
        productTemplateManageRepository.saveAndFlush(productTemplateManage);

        // Get the productTemplateManage
        restProductTemplateManageMockMvc
            .perform(get(ENTITY_API_URL_ID, productTemplateManage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productTemplateManage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.illegalUsageType").value(DEFAULT_ILLEGAL_USAGE_TYPE))
            .andExpect(jsonPath("$.illegalUsageUrl").value(DEFAULT_ILLEGAL_USAGE_URL))
            .andExpect(jsonPath("$.exchangeType").value(DEFAULT_EXCHANGE_TYPE))
            .andExpect(jsonPath("$.exchangeUrl").value(DEFAULT_EXCHANGE_URL))
            .andExpect(jsonPath("$.shippingReleaseType").value(DEFAULT_SHIPPING_RELEASE_TYPE))
            .andExpect(jsonPath("$.separateShippingPriceType").value(DEFAULT_SEPARATE_SHIPPING_PRICE_TYPE))
            .andExpect(jsonPath("$.bundleShippingType").value(DEFAULT_BUNDLE_SHIPPING_TYPE))
            .andExpect(jsonPath("$.defaultShippingPrice").value(DEFAULT_DEFAULT_SHIPPING_PRICE.intValue()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductTemplateManage() throws Exception {
        // Get the productTemplateManage
        restProductTemplateManageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductTemplateManage() throws Exception {
        // Initialize the database
        productTemplateManageRepository.saveAndFlush(productTemplateManage);

        int databaseSizeBeforeUpdate = productTemplateManageRepository.findAll().size();

        // Update the productTemplateManage
        ProductTemplateManage updatedProductTemplateManage = productTemplateManageRepository.findById(productTemplateManage.getId()).get();
        // Disconnect from session so that the updates on updatedProductTemplateManage are not directly saved in db
        em.detach(updatedProductTemplateManage);
        updatedProductTemplateManage.setName(UPDATED_NAME);
        updatedProductTemplateManage.setType(UPDATED_TYPE);
        updatedProductTemplateManage.setIllegalUsageType(UPDATED_ILLEGAL_USAGE_TYPE);
        updatedProductTemplateManage.setIllegalUsageUrl(UPDATED_ILLEGAL_USAGE_URL);
        updatedProductTemplateManage.setExchangeType(UPDATED_EXCHANGE_TYPE);
        updatedProductTemplateManage.setExchangeUrl(UPDATED_EXCHANGE_URL);
        updatedProductTemplateManage.setShippingReleaseType(UPDATED_SHIPPING_RELEASE_TYPE);
        updatedProductTemplateManage.setSeparateShippingPriceType(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
        updatedProductTemplateManage.setBundleShippingType(UPDATED_BUNDLE_SHIPPING_TYPE);
        updatedProductTemplateManage.setDefaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE);
        updatedProductTemplateManage.setActivated(UPDATED_ACTIVATED);
        updatedProductTemplateManage.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductTemplateManage.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductTemplateManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductTemplateManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductTemplateManageDTO productTemplateManageDTO = productTemplateManageMapper.toDto(updatedProductTemplateManage);

        restProductTemplateManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTemplateManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateManageDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplateManage in the database
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplateManage testProductTemplateManage = productTemplateManageList.get(productTemplateManageList.size() - 1);
        assertThat(testProductTemplateManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductTemplateManage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductTemplateManage.getIllegalUsageType()).isEqualTo(UPDATED_ILLEGAL_USAGE_TYPE);
        assertThat(testProductTemplateManage.getIllegalUsageUrl()).isEqualTo(UPDATED_ILLEGAL_USAGE_URL);
        assertThat(testProductTemplateManage.getExchangeType()).isEqualTo(UPDATED_EXCHANGE_TYPE);
        assertThat(testProductTemplateManage.getExchangeUrl()).isEqualTo(UPDATED_EXCHANGE_URL);
        assertThat(testProductTemplateManage.getShippingReleaseType()).isEqualTo(UPDATED_SHIPPING_RELEASE_TYPE);
        assertThat(testProductTemplateManage.getSeparateShippingPriceType()).isEqualTo(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
        assertThat(testProductTemplateManage.getBundleShippingType()).isEqualTo(UPDATED_BUNDLE_SHIPPING_TYPE);
        assertThat(testProductTemplateManage.getDefaultShippingPrice()).isEqualTo(UPDATED_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductTemplateManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductTemplateManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTemplateManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTemplateManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTemplateManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductTemplateManage() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateManageRepository.findAll().size();
        productTemplateManage.setId(count.incrementAndGet());

        // Create the ProductTemplateManage
        ProductTemplateManageDTO productTemplateManageDTO = productTemplateManageMapper.toDto(productTemplateManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTemplateManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTemplateManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplateManage in the database
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductTemplateManage() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateManageRepository.findAll().size();
        productTemplateManage.setId(count.incrementAndGet());

        // Create the ProductTemplateManage
        ProductTemplateManageDTO productTemplateManageDTO = productTemplateManageMapper.toDto(productTemplateManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplateManage in the database
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductTemplateManage() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateManageRepository.findAll().size();
        productTemplateManage.setId(count.incrementAndGet());

        // Create the ProductTemplateManage
        ProductTemplateManageDTO productTemplateManageDTO = productTemplateManageMapper.toDto(productTemplateManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateManageMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTemplateManage in the database
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductTemplateManageWithPatch() throws Exception {
        // Initialize the database
        productTemplateManageRepository.saveAndFlush(productTemplateManage);

        int databaseSizeBeforeUpdate = productTemplateManageRepository.findAll().size();

        // Update the productTemplateManage using partial update
        ProductTemplateManage partialUpdatedProductTemplateManage = new ProductTemplateManage();
        partialUpdatedProductTemplateManage.setId(productTemplateManage.getId());

        partialUpdatedProductTemplateManage.setName(UPDATED_NAME);
        partialUpdatedProductTemplateManage.setType(UPDATED_TYPE);
        partialUpdatedProductTemplateManage.setIllegalUsageType(UPDATED_ILLEGAL_USAGE_TYPE);
        partialUpdatedProductTemplateManage.setIllegalUsageUrl(UPDATED_ILLEGAL_USAGE_URL);
        partialUpdatedProductTemplateManage.setExchangeType(UPDATED_EXCHANGE_TYPE);
        partialUpdatedProductTemplateManage.setExchangeUrl(UPDATED_EXCHANGE_URL);
        partialUpdatedProductTemplateManage.setShippingReleaseType(UPDATED_SHIPPING_RELEASE_TYPE);
        partialUpdatedProductTemplateManage.setSeparateShippingPriceType(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
        partialUpdatedProductTemplateManage.setBundleShippingType(UPDATED_BUNDLE_SHIPPING_TYPE);
        partialUpdatedProductTemplateManage.setDefaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE);
        partialUpdatedProductTemplateManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductTemplateManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductTemplateManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductTemplateManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductTemplateManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductTemplateManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTemplateManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTemplateManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplateManage in the database
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplateManage testProductTemplateManage = productTemplateManageList.get(productTemplateManageList.size() - 1);
        assertThat(testProductTemplateManage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductTemplateManage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductTemplateManage.getIllegalUsageType()).isEqualTo(DEFAULT_ILLEGAL_USAGE_TYPE);
        assertThat(testProductTemplateManage.getIllegalUsageUrl()).isEqualTo(DEFAULT_ILLEGAL_USAGE_URL);
        assertThat(testProductTemplateManage.getExchangeType()).isEqualTo(DEFAULT_EXCHANGE_TYPE);
        assertThat(testProductTemplateManage.getExchangeUrl()).isEqualTo(UPDATED_EXCHANGE_URL);
        assertThat(testProductTemplateManage.getShippingReleaseType()).isEqualTo(UPDATED_SHIPPING_RELEASE_TYPE);
        assertThat(testProductTemplateManage.getSeparateShippingPriceType()).isEqualTo(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
        assertThat(testProductTemplateManage.getBundleShippingType()).isEqualTo(UPDATED_BUNDLE_SHIPPING_TYPE);
        assertThat(testProductTemplateManage.getDefaultShippingPrice()).isEqualTo(DEFAULT_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductTemplateManage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductTemplateManage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductTemplateManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTemplateManage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductTemplateManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductTemplateManageWithPatch() throws Exception {
        // Initialize the database
        productTemplateManageRepository.saveAndFlush(productTemplateManage);

        int databaseSizeBeforeUpdate = productTemplateManageRepository.findAll().size();

        // Update the productTemplateManage using partial update
        ProductTemplateManage partialUpdatedProductTemplateManage = new ProductTemplateManage();
        partialUpdatedProductTemplateManage.setId(productTemplateManage.getId());

        partialUpdatedProductTemplateManage.setName(UPDATED_NAME);
        partialUpdatedProductTemplateManage.setType(UPDATED_TYPE);
        partialUpdatedProductTemplateManage.setIllegalUsageType(UPDATED_ILLEGAL_USAGE_TYPE);
        partialUpdatedProductTemplateManage.setIllegalUsageUrl(UPDATED_ILLEGAL_USAGE_URL);
        partialUpdatedProductTemplateManage.setExchangeType(UPDATED_EXCHANGE_TYPE);
        partialUpdatedProductTemplateManage.setExchangeUrl(UPDATED_EXCHANGE_URL);
        partialUpdatedProductTemplateManage.setShippingReleaseType(UPDATED_SHIPPING_RELEASE_TYPE);
        partialUpdatedProductTemplateManage.setSeparateShippingPriceType(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
        partialUpdatedProductTemplateManage.setBundleShippingType(UPDATED_BUNDLE_SHIPPING_TYPE);
        partialUpdatedProductTemplateManage.setDefaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE);
        partialUpdatedProductTemplateManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductTemplateManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductTemplateManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductTemplateManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductTemplateManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductTemplateManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTemplateManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTemplateManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplateManage in the database
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplateManage testProductTemplateManage = productTemplateManageList.get(productTemplateManageList.size() - 1);
        assertThat(testProductTemplateManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductTemplateManage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductTemplateManage.getIllegalUsageType()).isEqualTo(UPDATED_ILLEGAL_USAGE_TYPE);
        assertThat(testProductTemplateManage.getIllegalUsageUrl()).isEqualTo(UPDATED_ILLEGAL_USAGE_URL);
        assertThat(testProductTemplateManage.getExchangeType()).isEqualTo(UPDATED_EXCHANGE_TYPE);
        assertThat(testProductTemplateManage.getExchangeUrl()).isEqualTo(UPDATED_EXCHANGE_URL);
        assertThat(testProductTemplateManage.getShippingReleaseType()).isEqualTo(UPDATED_SHIPPING_RELEASE_TYPE);
        assertThat(testProductTemplateManage.getSeparateShippingPriceType()).isEqualTo(UPDATED_SEPARATE_SHIPPING_PRICE_TYPE);
        assertThat(testProductTemplateManage.getBundleShippingType()).isEqualTo(UPDATED_BUNDLE_SHIPPING_TYPE);
        assertThat(testProductTemplateManage.getDefaultShippingPrice()).isEqualTo(UPDATED_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductTemplateManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductTemplateManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTemplateManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTemplateManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTemplateManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductTemplateManage() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateManageRepository.findAll().size();
        productTemplateManage.setId(count.incrementAndGet());

        // Create the ProductTemplateManage
        ProductTemplateManageDTO productTemplateManageDTO = productTemplateManageMapper.toDto(productTemplateManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTemplateManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productTemplateManageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplateManage in the database
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductTemplateManage() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateManageRepository.findAll().size();
        productTemplateManage.setId(count.incrementAndGet());

        // Create the ProductTemplateManage
        ProductTemplateManageDTO productTemplateManageDTO = productTemplateManageMapper.toDto(productTemplateManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplateManage in the database
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductTemplateManage() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateManageRepository.findAll().size();
        productTemplateManage.setId(count.incrementAndGet());

        // Create the ProductTemplateManage
        ProductTemplateManageDTO productTemplateManageDTO = productTemplateManageMapper.toDto(productTemplateManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateManageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTemplateManage in the database
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductTemplateManage() throws Exception {
        // Initialize the database
        productTemplateManageRepository.saveAndFlush(productTemplateManage);

        int databaseSizeBeforeDelete = productTemplateManageRepository.findAll().size();

        // Delete the productTemplateManage
        restProductTemplateManageMockMvc
            .perform(delete(ENTITY_API_URL_ID, productTemplateManage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductTemplateManage> productTemplateManageList = productTemplateManageRepository.findAll();
        assertThat(productTemplateManageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
