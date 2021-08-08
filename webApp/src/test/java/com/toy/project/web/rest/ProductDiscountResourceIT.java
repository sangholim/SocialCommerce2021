package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductDiscount;
import com.toy.project.repository.ProductDiscountRepository;
import com.toy.project.service.dto.ProductDiscountDTO;
import com.toy.project.service.mapper.ProductDiscountMapper;
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
 * Integration tests for the {@link ProductDiscountResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductDiscountResourceIT {

    private static final String DEFAULT_DISCOUNT_DEVICE = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_DEVICE = "BBBBBBBBBB";

    private static final Long DEFAULT_DISCOUNT_PRICE = 1L;
    private static final Long UPDATED_DISCOUNT_PRICE = 2L;

    private static final String DEFAULT_DISCOUNT_PRICE_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_PRICE_UNIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_DISCOUNT_DATE = false;
    private static final Boolean UPDATED_USE_DISCOUNT_DATE = true;

    private static final Instant DEFAULT_DISCOUNT_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISCOUNT_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DISCOUNT_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISCOUNT_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_CREATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_REDUCE_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_REDUCE_PRICE = "BBBBBBBBBB";

    private static final Instant DEFAULT_CREATED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CREATED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_LAST_MODIFIED_BY = "AAAAAAAAAA";
    private static final String UPDATED_LAST_MODIFIED_BY = "BBBBBBBBBB";

    private static final Instant DEFAULT_LAST_MODIFIED_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LAST_MODIFIED_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String ENTITY_API_URL = "/api/product-discounts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductDiscountRepository productDiscountRepository;

    @Autowired
    private ProductDiscountMapper productDiscountMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductDiscountMockMvc;

    private ProductDiscount productDiscount;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDiscount createEntity(EntityManager em) {
        ProductDiscount productDiscount = new ProductDiscount();
        productDiscount.setDiscountDevice(DEFAULT_DISCOUNT_DEVICE);
        productDiscount.setDiscountPrice(DEFAULT_DISCOUNT_PRICE);
        productDiscount.setDiscountPriceUnit(DEFAULT_DISCOUNT_PRICE_UNIT);
        productDiscount.setDiscountDateFrom(DEFAULT_DISCOUNT_DATE_FROM);
        productDiscount.setDiscountDateTo(DEFAULT_DISCOUNT_DATE_TO);
        productDiscount.setActivated(true);
        productDiscount.setCreatedBy(DEFAULT_CREATED_BY);
        productDiscount.setReducePrice(DEFAULT_REDUCE_PRICE);
        productDiscount.setCreatedDate(DEFAULT_CREATED_DATE);
        productDiscount.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productDiscount.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productDiscount;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductDiscount createUpdatedEntity(EntityManager em) {
        ProductDiscount productDiscount = new ProductDiscount();
        productDiscount.setDiscountDevice(UPDATED_DISCOUNT_DEVICE);
        productDiscount.setDiscountPrice(UPDATED_DISCOUNT_PRICE);
        productDiscount.setDiscountPriceUnit(UPDATED_DISCOUNT_PRICE_UNIT);
        productDiscount.setDiscountDateFrom(UPDATED_DISCOUNT_DATE_FROM);
        productDiscount.setDiscountDateTo(UPDATED_DISCOUNT_DATE_TO);
        productDiscount.setCreatedBy(UPDATED_CREATED_BY);
        productDiscount.setReducePrice(UPDATED_REDUCE_PRICE);
        productDiscount.setActivated(true);
        productDiscount.setCreatedDate(UPDATED_CREATED_DATE);
        productDiscount.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productDiscount.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productDiscount;
    }

    @BeforeEach
    public void initTest() {
        productDiscount = createEntity(em);
    }

    @Test
    @Transactional
    void createProductDiscount() throws Exception {
        int databaseSizeBeforeCreate = productDiscountRepository.findAll().size();
        // Create the ProductDiscount
        ProductDiscountDTO productDiscountDTO = productDiscountMapper.toDto(productDiscount);
        restProductDiscountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDiscountDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductDiscount in the database
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeCreate + 1);
        ProductDiscount testProductDiscount = productDiscountList.get(productDiscountList.size() - 1);
        assertThat(testProductDiscount.getDiscountDevice()).isEqualTo(DEFAULT_DISCOUNT_DEVICE);
        assertThat(testProductDiscount.getDiscountPrice()).isEqualTo(DEFAULT_DISCOUNT_PRICE);
        assertThat(testProductDiscount.getDiscountPriceUnit()).isEqualTo(DEFAULT_DISCOUNT_PRICE_UNIT);
        assertThat(testProductDiscount.getUseDiscountDate()).isEqualTo(DEFAULT_USE_DISCOUNT_DATE);
        assertThat(testProductDiscount.getDiscountDateFrom()).isEqualTo(DEFAULT_DISCOUNT_DATE_FROM);
        assertThat(testProductDiscount.getDiscountDateTo()).isEqualTo(DEFAULT_DISCOUNT_DATE_TO);
        assertThat(testProductDiscount.getActivated()).isEqualTo(true);
        assertThat(testProductDiscount.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductDiscount.getReducePrice()).isEqualTo(DEFAULT_REDUCE_PRICE);
        assertThat(testProductDiscount.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductDiscount.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductDiscount.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductDiscountWithExistingId() throws Exception {
        // Create the ProductDiscount with an existing ID
        productDiscount.setId(1L);
        ProductDiscountDTO productDiscountDTO = productDiscountMapper.toDto(productDiscount);

        int databaseSizeBeforeCreate = productDiscountRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductDiscountMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDiscountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDiscount in the database
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductDiscounts() throws Exception {
        // Initialize the database
        productDiscountRepository.saveAndFlush(productDiscount);

        // Get all the productDiscountList
        restProductDiscountMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productDiscount.getId().intValue())))
            .andExpect(jsonPath("$.[*].discountDevice").value(hasItem(DEFAULT_DISCOUNT_DEVICE)))
            .andExpect(jsonPath("$.[*].discountPrice").value(hasItem(DEFAULT_DISCOUNT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].discountPriceUnit").value(hasItem(DEFAULT_DISCOUNT_PRICE_UNIT)))
            .andExpect(jsonPath("$.[*].discountDateFrom").value(hasItem(DEFAULT_DISCOUNT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].discountDateTo").value(hasItem(DEFAULT_DISCOUNT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(true)))
            .andExpect(jsonPath("$.[*].reducePrice").value(hasItem(DEFAULT_REDUCE_PRICE)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductDiscount() throws Exception {
        // Initialize the database
        productDiscountRepository.saveAndFlush(productDiscount);

        // Get the productDiscount
        restProductDiscountMockMvc
            .perform(get(ENTITY_API_URL_ID, productDiscount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productDiscount.getId().intValue()))
            .andExpect(jsonPath("$.discountDevice").value(DEFAULT_DISCOUNT_DEVICE))
            .andExpect(jsonPath("$.discountPrice").value(DEFAULT_DISCOUNT_PRICE.intValue()))
            .andExpect(jsonPath("$.discountPriceUnit").value(DEFAULT_DISCOUNT_PRICE_UNIT))
            .andExpect(jsonPath("$.discountDateFrom").value(DEFAULT_DISCOUNT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.discountDateTo").value(DEFAULT_DISCOUNT_DATE_TO.toString()))
            .andExpect(jsonPath("$.activated").value(true))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.reducePrice").value(DEFAULT_REDUCE_PRICE))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductDiscount() throws Exception {
        // Get the productDiscount
        restProductDiscountMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductDiscount() throws Exception {
        // Initialize the database
        productDiscountRepository.saveAndFlush(productDiscount);

        int databaseSizeBeforeUpdate = productDiscountRepository.findAll().size();

        // Update the productDiscount
        ProductDiscount updatedProductDiscount = productDiscountRepository.findById(productDiscount.getId()).get();
        // Disconnect from session so that the updates on updatedProductDiscount are not directly saved in db
        em.detach(updatedProductDiscount);
        updatedProductDiscount.setDiscountDevice(UPDATED_DISCOUNT_DEVICE);
        updatedProductDiscount.setDiscountPrice(UPDATED_DISCOUNT_PRICE);
        updatedProductDiscount.setDiscountPriceUnit(UPDATED_DISCOUNT_PRICE_UNIT);
        updatedProductDiscount.setDiscountDateFrom(UPDATED_DISCOUNT_DATE_FROM);
        updatedProductDiscount.setDiscountDateTo(UPDATED_DISCOUNT_DATE_TO);
        updatedProductDiscount.setActivated(true);
        updatedProductDiscount.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductDiscount.setReducePrice(UPDATED_REDUCE_PRICE);
        updatedProductDiscount.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductDiscount.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductDiscount.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductDiscountDTO productDiscountDTO = productDiscountMapper.toDto(updatedProductDiscount);

        restProductDiscountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDiscountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDiscountDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductDiscount in the database
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeUpdate);
        ProductDiscount testProductDiscount = productDiscountList.get(productDiscountList.size() - 1);
        assertThat(testProductDiscount.getDiscountDevice()).isEqualTo(UPDATED_DISCOUNT_DEVICE);
        assertThat(testProductDiscount.getDiscountPrice()).isEqualTo(UPDATED_DISCOUNT_PRICE);
        assertThat(testProductDiscount.getDiscountPriceUnit()).isEqualTo(UPDATED_DISCOUNT_PRICE_UNIT);
        assertThat(testProductDiscount.getUseDiscountDate()).isEqualTo(UPDATED_USE_DISCOUNT_DATE);
        assertThat(testProductDiscount.getDiscountDateFrom()).isEqualTo(UPDATED_DISCOUNT_DATE_FROM);
        assertThat(testProductDiscount.getDiscountDateTo()).isEqualTo(UPDATED_DISCOUNT_DATE_TO);
        assertThat(testProductDiscount.getActivated()).isEqualTo(true);
        assertThat(testProductDiscount.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductDiscount.getReducePrice()).isEqualTo(UPDATED_REDUCE_PRICE);
        assertThat(testProductDiscount.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductDiscount.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductDiscount.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductDiscount() throws Exception {
        int databaseSizeBeforeUpdate = productDiscountRepository.findAll().size();
        productDiscount.setId(count.incrementAndGet());

        // Create the ProductDiscount
        ProductDiscountDTO productDiscountDTO = productDiscountMapper.toDto(productDiscount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDiscountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productDiscountDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDiscountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDiscount in the database
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductDiscount() throws Exception {
        int databaseSizeBeforeUpdate = productDiscountRepository.findAll().size();
        productDiscount.setId(count.incrementAndGet());

        // Create the ProductDiscount
        ProductDiscountDTO productDiscountDTO = productDiscountMapper.toDto(productDiscount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDiscountMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productDiscountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDiscount in the database
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductDiscount() throws Exception {
        int databaseSizeBeforeUpdate = productDiscountRepository.findAll().size();
        productDiscount.setId(count.incrementAndGet());

        // Create the ProductDiscount
        ProductDiscountDTO productDiscountDTO = productDiscountMapper.toDto(productDiscount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDiscountMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productDiscountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductDiscount in the database
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductDiscountWithPatch() throws Exception {
        // Initialize the database
        productDiscountRepository.saveAndFlush(productDiscount);

        int databaseSizeBeforeUpdate = productDiscountRepository.findAll().size();

        // Update the productDiscount using partial update
        ProductDiscount partialUpdatedProductDiscount = new ProductDiscount();
        partialUpdatedProductDiscount.setId(productDiscount.getId());

        partialUpdatedProductDiscount.setDiscountDevice(UPDATED_DISCOUNT_DEVICE);
        partialUpdatedProductDiscount.setDiscountPrice(UPDATED_DISCOUNT_PRICE);
        partialUpdatedProductDiscount.setDiscountPriceUnit(UPDATED_DISCOUNT_PRICE_UNIT);
        partialUpdatedProductDiscount.setDiscountDateFrom(UPDATED_DISCOUNT_DATE_FROM);
        partialUpdatedProductDiscount.setDiscountDateTo(UPDATED_DISCOUNT_DATE_TO);
        partialUpdatedProductDiscount.setActivated(true);
        partialUpdatedProductDiscount.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductDiscount.setReducePrice(UPDATED_REDUCE_PRICE);
        partialUpdatedProductDiscount.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductDiscount.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductDiscount.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductDiscount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductDiscount))
            )
            .andExpect(status().isOk());

        // Validate the ProductDiscount in the database
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeUpdate);
        ProductDiscount testProductDiscount = productDiscountList.get(productDiscountList.size() - 1);
        assertThat(testProductDiscount.getDiscountDevice()).isEqualTo(DEFAULT_DISCOUNT_DEVICE);
        assertThat(testProductDiscount.getDiscountPrice()).isEqualTo(DEFAULT_DISCOUNT_PRICE);
        assertThat(testProductDiscount.getDiscountPriceUnit()).isEqualTo(UPDATED_DISCOUNT_PRICE_UNIT);
        assertThat(testProductDiscount.getUseDiscountDate()).isEqualTo(UPDATED_USE_DISCOUNT_DATE);
        assertThat(testProductDiscount.getDiscountDateFrom()).isEqualTo(DEFAULT_DISCOUNT_DATE_FROM);
        assertThat(testProductDiscount.getDiscountDateTo()).isEqualTo(DEFAULT_DISCOUNT_DATE_TO);
        assertThat(testProductDiscount.getActivated()).isEqualTo(true);
        assertThat(testProductDiscount.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductDiscount.getReducePrice()).isEqualTo(UPDATED_REDUCE_PRICE);
        assertThat(testProductDiscount.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductDiscount.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductDiscount.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductDiscountWithPatch() throws Exception {
        // Initialize the database
        productDiscountRepository.saveAndFlush(productDiscount);

        int databaseSizeBeforeUpdate = productDiscountRepository.findAll().size();

        // Update the productDiscount using partial update
        ProductDiscount partialUpdatedProductDiscount = new ProductDiscount();
        partialUpdatedProductDiscount.setId(productDiscount.getId());

        partialUpdatedProductDiscount.setDiscountDevice(UPDATED_DISCOUNT_DEVICE);
        partialUpdatedProductDiscount.setDiscountPrice(UPDATED_DISCOUNT_PRICE);
        partialUpdatedProductDiscount.setDiscountPriceUnit(UPDATED_DISCOUNT_PRICE_UNIT);
        partialUpdatedProductDiscount.setDiscountDateFrom(UPDATED_DISCOUNT_DATE_FROM);
        partialUpdatedProductDiscount.setDiscountDateTo(UPDATED_DISCOUNT_DATE_TO);
        partialUpdatedProductDiscount.setActivated(true);
        partialUpdatedProductDiscount.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductDiscount.setReducePrice(UPDATED_REDUCE_PRICE);
        partialUpdatedProductDiscount.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductDiscount.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductDiscount.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductDiscount.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductDiscount))
            )
            .andExpect(status().isOk());

        // Validate the ProductDiscount in the database
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeUpdate);
        ProductDiscount testProductDiscount = productDiscountList.get(productDiscountList.size() - 1);
        assertThat(testProductDiscount.getDiscountDevice()).isEqualTo(UPDATED_DISCOUNT_DEVICE);
        assertThat(testProductDiscount.getDiscountPrice()).isEqualTo(UPDATED_DISCOUNT_PRICE);
        assertThat(testProductDiscount.getDiscountPriceUnit()).isEqualTo(UPDATED_DISCOUNT_PRICE_UNIT);
        assertThat(testProductDiscount.getUseDiscountDate()).isEqualTo(UPDATED_USE_DISCOUNT_DATE);
        assertThat(testProductDiscount.getDiscountDateFrom()).isEqualTo(UPDATED_DISCOUNT_DATE_FROM);
        assertThat(testProductDiscount.getDiscountDateTo()).isEqualTo(UPDATED_DISCOUNT_DATE_TO);
        assertThat(testProductDiscount.getActivated()).isEqualTo(true);
        assertThat(testProductDiscount.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductDiscount.getReducePrice()).isEqualTo(UPDATED_REDUCE_PRICE);
        assertThat(testProductDiscount.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductDiscount.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductDiscount.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductDiscount() throws Exception {
        int databaseSizeBeforeUpdate = productDiscountRepository.findAll().size();
        productDiscount.setId(count.incrementAndGet());

        // Create the ProductDiscount
        ProductDiscountDTO productDiscountDTO = productDiscountMapper.toDto(productDiscount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productDiscountDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDiscountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDiscount in the database
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductDiscount() throws Exception {
        int databaseSizeBeforeUpdate = productDiscountRepository.findAll().size();
        productDiscount.setId(count.incrementAndGet());

        // Create the ProductDiscount
        ProductDiscountDTO productDiscountDTO = productDiscountMapper.toDto(productDiscount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDiscountDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductDiscount in the database
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductDiscount() throws Exception {
        int databaseSizeBeforeUpdate = productDiscountRepository.findAll().size();
        productDiscount.setId(count.incrementAndGet());

        // Create the ProductDiscount
        ProductDiscountDTO productDiscountDTO = productDiscountMapper.toDto(productDiscount);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductDiscountMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productDiscountDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductDiscount in the database
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductDiscount() throws Exception {
        // Initialize the database
        productDiscountRepository.saveAndFlush(productDiscount);
        int databaseSizeBeforeDelete = productDiscountRepository.findAll().size();

        // Delete the productDiscount
        restProductDiscountMockMvc
            .perform(delete(ENTITY_API_URL_ID, productDiscount.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductDiscount> productDiscountList = productDiscountRepository.findAll();
        assertThat(productDiscountList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
