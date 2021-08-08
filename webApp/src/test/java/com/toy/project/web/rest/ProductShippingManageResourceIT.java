package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductShippingManage;
import com.toy.project.repository.ProductShippingManageRepository;
import com.toy.project.service.dto.ProductShippingManageDTO;
import com.toy.project.service.mapper.ProductShippingManageMapper;
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
 * Integration tests for the {@link ProductShippingManageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductShippingManageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_GROUP = false;
    private static final Boolean UPDATED_USE_GROUP = true;

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

    private static final String ENTITY_API_URL = "/api/product-shipping-manages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductShippingManageRepository productShippingManageRepository;

    @Autowired
    private ProductShippingManageMapper productShippingManageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductShippingManageMockMvc;

    private ProductShippingManage productShippingManage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductShippingManage createEntity(EntityManager em) {
        ProductShippingManage productShippingManage = new ProductShippingManage();
        productShippingManage.setName(DEFAULT_NAME);
        productShippingManage.setDefaultShippingPrice(DEFAULT_DEFAULT_SHIPPING_PRICE);
        productShippingManage.setFreeShippingPrice(DEFAULT_FREE_SHIPPING_PRICE);
        productShippingManage.setJejuShippingPrice(DEFAULT_JEJU_SHIPPING_PRICE);
        productShippingManage.setDifficultShippingPrice(DEFAULT_DIFFICULT_SHIPPING_PRICE);
        productShippingManage.setRefundShippingPrice(DEFAULT_REFUND_SHIPPING_PRICE);
        productShippingManage.setExchangeShippingPrice(DEFAULT_EXCHANGE_SHIPPING_PRICE);
        productShippingManage.setActivated(DEFAULT_ACTIVATED);
        productShippingManage.setCreatedBy(DEFAULT_CREATED_BY);
        productShippingManage.setCreatedDate(DEFAULT_CREATED_DATE);
        productShippingManage.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productShippingManage.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productShippingManage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductShippingManage createUpdatedEntity(EntityManager em) {
        ProductShippingManage productShippingManage = new ProductShippingManage();
        productShippingManage.setName(UPDATED_NAME);
        productShippingManage.setDefaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE);
        productShippingManage.setFreeShippingPrice(UPDATED_FREE_SHIPPING_PRICE);
        productShippingManage.setJejuShippingPrice(UPDATED_JEJU_SHIPPING_PRICE);
        productShippingManage.setDifficultShippingPrice(UPDATED_DIFFICULT_SHIPPING_PRICE);
        productShippingManage.setRefundShippingPrice(UPDATED_REFUND_SHIPPING_PRICE);
        productShippingManage.setExchangeShippingPrice(UPDATED_EXCHANGE_SHIPPING_PRICE);
        productShippingManage.setActivated(UPDATED_ACTIVATED);
        productShippingManage.setCreatedBy(UPDATED_CREATED_BY);
        productShippingManage.setCreatedDate(UPDATED_CREATED_DATE);
        productShippingManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productShippingManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productShippingManage;
    }

    @BeforeEach
    public void initTest() {
        productShippingManage = createEntity(em);
    }

    @Test
    @Transactional
    void createProductShippingManage() throws Exception {
        int databaseSizeBeforeCreate = productShippingManageRepository.findAll().size();
        // Create the ProductShippingManage
        ProductShippingManageDTO productShippingManageDTO = productShippingManageMapper.toDto(productShippingManage);
        restProductShippingManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingManageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductShippingManage in the database
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeCreate + 1);
        ProductShippingManage testProductShippingManage = productShippingManageList.get(productShippingManageList.size() - 1);
        assertThat(testProductShippingManage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductShippingManage.getUseGroup()).isEqualTo(DEFAULT_USE_GROUP);
        assertThat(testProductShippingManage.getDefaultShippingPrice()).isEqualTo(DEFAULT_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getFreeShippingPrice()).isEqualTo(DEFAULT_FREE_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getJejuShippingPrice()).isEqualTo(DEFAULT_JEJU_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getDifficultShippingPrice()).isEqualTo(DEFAULT_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getRefundShippingPrice()).isEqualTo(DEFAULT_REFUND_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getExchangeShippingPrice()).isEqualTo(DEFAULT_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductShippingManage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductShippingManage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductShippingManage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductShippingManage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductShippingManageWithExistingId() throws Exception {
        // Create the ProductShippingManage with an existing ID
        productShippingManage.setId(1L);
        ProductShippingManageDTO productShippingManageDTO = productShippingManageMapper.toDto(productShippingManage);

        int databaseSizeBeforeCreate = productShippingManageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductShippingManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShippingManage in the database
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductShippingManages() throws Exception {
        // Initialize the database
        productShippingManageRepository.saveAndFlush(productShippingManage);

        // Get all the productShippingManageList
        restProductShippingManageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productShippingManage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
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
    void getProductShippingManage() throws Exception {
        // Initialize the database
        productShippingManageRepository.saveAndFlush(productShippingManage);

        // Get the productShippingManage
        restProductShippingManageMockMvc
            .perform(get(ENTITY_API_URL_ID, productShippingManage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productShippingManage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
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
    void getNonExistingProductShippingManage() throws Exception {
        // Get the productShippingManage
        restProductShippingManageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductShippingManage() throws Exception {
        // Initialize the database
        productShippingManageRepository.saveAndFlush(productShippingManage);

        int databaseSizeBeforeUpdate = productShippingManageRepository.findAll().size();

        // Update the productShippingManage
        ProductShippingManage updatedProductShippingManage = productShippingManageRepository.findById(productShippingManage.getId()).get();
        // Disconnect from session so that the updates on updatedProductShippingManage are not directly saved in db
        em.detach(updatedProductShippingManage);
        updatedProductShippingManage.setName(UPDATED_NAME);
        updatedProductShippingManage.setDefaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE);
        updatedProductShippingManage.setFreeShippingPrice(UPDATED_FREE_SHIPPING_PRICE);
        updatedProductShippingManage.setJejuShippingPrice(UPDATED_JEJU_SHIPPING_PRICE);
        updatedProductShippingManage.setDifficultShippingPrice(UPDATED_DIFFICULT_SHIPPING_PRICE);
        updatedProductShippingManage.setRefundShippingPrice(UPDATED_REFUND_SHIPPING_PRICE);
        updatedProductShippingManage.setExchangeShippingPrice(UPDATED_EXCHANGE_SHIPPING_PRICE);
        updatedProductShippingManage.setActivated(UPDATED_ACTIVATED);
        updatedProductShippingManage.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductShippingManage.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductShippingManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductShippingManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductShippingManageDTO productShippingManageDTO = productShippingManageMapper.toDto(updatedProductShippingManage);

        restProductShippingManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productShippingManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingManageDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductShippingManage in the database
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeUpdate);
        ProductShippingManage testProductShippingManage = productShippingManageList.get(productShippingManageList.size() - 1);
        assertThat(testProductShippingManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductShippingManage.getUseGroup()).isEqualTo(UPDATED_USE_GROUP);
        assertThat(testProductShippingManage.getDefaultShippingPrice()).isEqualTo(UPDATED_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getFreeShippingPrice()).isEqualTo(UPDATED_FREE_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getJejuShippingPrice()).isEqualTo(UPDATED_JEJU_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getDifficultShippingPrice()).isEqualTo(UPDATED_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getRefundShippingPrice()).isEqualTo(UPDATED_REFUND_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getExchangeShippingPrice()).isEqualTo(UPDATED_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductShippingManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductShippingManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductShippingManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductShippingManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductShippingManage() throws Exception {
        int databaseSizeBeforeUpdate = productShippingManageRepository.findAll().size();
        productShippingManage.setId(count.incrementAndGet());

        // Create the ProductShippingManage
        ProductShippingManageDTO productShippingManageDTO = productShippingManageMapper.toDto(productShippingManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductShippingManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productShippingManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShippingManage in the database
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductShippingManage() throws Exception {
        int databaseSizeBeforeUpdate = productShippingManageRepository.findAll().size();
        productShippingManage.setId(count.incrementAndGet());

        // Create the ProductShippingManage
        ProductShippingManageDTO productShippingManageDTO = productShippingManageMapper.toDto(productShippingManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShippingManage in the database
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductShippingManage() throws Exception {
        int databaseSizeBeforeUpdate = productShippingManageRepository.findAll().size();
        productShippingManage.setId(count.incrementAndGet());

        // Create the ProductShippingManage
        ProductShippingManageDTO productShippingManageDTO = productShippingManageMapper.toDto(productShippingManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingManageMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productShippingManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductShippingManage in the database
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductShippingManageWithPatch() throws Exception {
        // Initialize the database
        productShippingManageRepository.saveAndFlush(productShippingManage);

        int databaseSizeBeforeUpdate = productShippingManageRepository.findAll().size();

        // Update the productShippingManage using partial update
        ProductShippingManage partialUpdatedProductShippingManage = new ProductShippingManage();
        partialUpdatedProductShippingManage.setId(productShippingManage.getId());
        partialUpdatedProductShippingManage.setName(UPDATED_NAME);
        partialUpdatedProductShippingManage.setDefaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setFreeShippingPrice(UPDATED_FREE_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setJejuShippingPrice(UPDATED_JEJU_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setDifficultShippingPrice(UPDATED_DIFFICULT_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setRefundShippingPrice(UPDATED_REFUND_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setExchangeShippingPrice(UPDATED_EXCHANGE_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductShippingManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductShippingManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductShippingManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductShippingManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductShippingManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductShippingManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductShippingManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductShippingManage in the database
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeUpdate);
        ProductShippingManage testProductShippingManage = productShippingManageList.get(productShippingManageList.size() - 1);
        assertThat(testProductShippingManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductShippingManage.getUseGroup()).isEqualTo(UPDATED_USE_GROUP);
        assertThat(testProductShippingManage.getDefaultShippingPrice()).isEqualTo(UPDATED_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getFreeShippingPrice()).isEqualTo(UPDATED_FREE_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getJejuShippingPrice()).isEqualTo(DEFAULT_JEJU_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getDifficultShippingPrice()).isEqualTo(DEFAULT_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getRefundShippingPrice()).isEqualTo(DEFAULT_REFUND_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getExchangeShippingPrice()).isEqualTo(UPDATED_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductShippingManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductShippingManage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductShippingManage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductShippingManage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductShippingManageWithPatch() throws Exception {
        // Initialize the database
        productShippingManageRepository.saveAndFlush(productShippingManage);

        int databaseSizeBeforeUpdate = productShippingManageRepository.findAll().size();

        // Update the productShippingManage using partial update
        ProductShippingManage partialUpdatedProductShippingManage = new ProductShippingManage();
        partialUpdatedProductShippingManage.setId(productShippingManage.getId());
        partialUpdatedProductShippingManage.setName(UPDATED_NAME);
        partialUpdatedProductShippingManage.setDefaultShippingPrice(UPDATED_DEFAULT_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setFreeShippingPrice(UPDATED_FREE_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setJejuShippingPrice(UPDATED_JEJU_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setDifficultShippingPrice(UPDATED_DIFFICULT_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setRefundShippingPrice(UPDATED_REFUND_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setExchangeShippingPrice(UPDATED_EXCHANGE_SHIPPING_PRICE);
        partialUpdatedProductShippingManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductShippingManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductShippingManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductShippingManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductShippingManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductShippingManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductShippingManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductShippingManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductShippingManage in the database
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeUpdate);
        ProductShippingManage testProductShippingManage = productShippingManageList.get(productShippingManageList.size() - 1);
        assertThat(testProductShippingManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductShippingManage.getUseGroup()).isEqualTo(UPDATED_USE_GROUP);
        assertThat(testProductShippingManage.getDefaultShippingPrice()).isEqualTo(UPDATED_DEFAULT_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getFreeShippingPrice()).isEqualTo(UPDATED_FREE_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getJejuShippingPrice()).isEqualTo(UPDATED_JEJU_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getDifficultShippingPrice()).isEqualTo(UPDATED_DIFFICULT_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getRefundShippingPrice()).isEqualTo(UPDATED_REFUND_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getExchangeShippingPrice()).isEqualTo(UPDATED_EXCHANGE_SHIPPING_PRICE);
        assertThat(testProductShippingManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductShippingManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductShippingManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductShippingManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductShippingManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductShippingManage() throws Exception {
        int databaseSizeBeforeUpdate = productShippingManageRepository.findAll().size();
        productShippingManage.setId(count.incrementAndGet());

        // Create the ProductShippingManage
        ProductShippingManageDTO productShippingManageDTO = productShippingManageMapper.toDto(productShippingManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductShippingManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productShippingManageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productShippingManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShippingManage in the database
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductShippingManage() throws Exception {
        int databaseSizeBeforeUpdate = productShippingManageRepository.findAll().size();
        productShippingManage.setId(count.incrementAndGet());

        // Create the ProductShippingManage
        ProductShippingManageDTO productShippingManageDTO = productShippingManageMapper.toDto(productShippingManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productShippingManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductShippingManage in the database
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductShippingManage() throws Exception {
        int databaseSizeBeforeUpdate = productShippingManageRepository.findAll().size();
        productShippingManage.setId(count.incrementAndGet());

        // Create the ProductShippingManage
        ProductShippingManageDTO productShippingManageDTO = productShippingManageMapper.toDto(productShippingManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductShippingManageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productShippingManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductShippingManage in the database
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductShippingManage() throws Exception {
        // Initialize the database
        productShippingManageRepository.saveAndFlush(productShippingManage);

        int databaseSizeBeforeDelete = productShippingManageRepository.findAll().size();

        // Delete the productShippingManage
        restProductShippingManageMockMvc
            .perform(delete(ENTITY_API_URL_ID, productShippingManage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductShippingManage> productShippingManageList = productShippingManageRepository.findAll();
        assertThat(productShippingManageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
