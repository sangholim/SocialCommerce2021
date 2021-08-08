package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductStore;
import com.toy.project.repository.ProductStoreRepository;
import com.toy.project.service.dto.ProductStoreDTO;
import com.toy.project.service.mapper.ProductStoreMapper;
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
 * Integration tests for the {@link ProductStoreResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductStoreResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_CALCULATION = false;
    private static final Boolean UPDATED_USE_CALCULATION = true;

    private static final Integer DEFAULT_CALCULATION = 1;
    private static final Integer UPDATED_CALCULATION = 2;

    private static final String DEFAULT_CALCULATION_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_CALCULATION_UNIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_CALCULATION_DATE = false;
    private static final Boolean UPDATED_USE_CALCULATION_DATE = true;

    private static final Instant DEFAULT_CALCULATION_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATION_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CALCULATION_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATION_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/product-stores";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductStoreRepository productStoreRepository;

    @Autowired
    private ProductStoreMapper productStoreMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductStoreMockMvc;

    private ProductStore productStore;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStore createEntity(EntityManager em) {
        ProductStore productStore = new ProductStore();
        productStore.setType(DEFAULT_TYPE);
        productStore.setCalculation(DEFAULT_CALCULATION);
        productStore.setCalculationUnit(DEFAULT_CALCULATION_UNIT);
        productStore.setCalculationDateFrom(DEFAULT_CALCULATION_DATE_FROM);
        productStore.setCalculationDateTo(DEFAULT_CALCULATION_DATE_TO);
        productStore.setActivated(DEFAULT_ACTIVATED);
        productStore.setCreatedBy(DEFAULT_CREATED_BY);
        productStore.setCreatedDate(DEFAULT_CREATED_DATE);
        productStore.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productStore.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productStore;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductStore createUpdatedEntity(EntityManager em) {
        ProductStore productStore = new ProductStore();
        productStore.setType(UPDATED_TYPE);
        productStore.setCalculation(UPDATED_CALCULATION);
        productStore.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        productStore.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        productStore.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        productStore.setActivated(UPDATED_ACTIVATED);
        productStore.setCreatedBy(UPDATED_CREATED_BY);
        productStore.setCreatedDate(UPDATED_CREATED_DATE);
        productStore.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productStore.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productStore;
    }

    @BeforeEach
    public void initTest() {
        productStore = createEntity(em);
    }

    @Test
    @Transactional
    void createProductStore() throws Exception {
        int databaseSizeBeforeCreate = productStoreRepository.findAll().size();
        // Create the ProductStore
        ProductStoreDTO productStoreDTO = productStoreMapper.toDto(productStore);
        restProductStoreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productStoreDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeCreate + 1);
        ProductStore testProductStore = productStoreList.get(productStoreList.size() - 1);
        assertThat(testProductStore.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductStore.getUseCalculation()).isEqualTo(DEFAULT_USE_CALCULATION);
        assertThat(testProductStore.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testProductStore.getCalculationUnit()).isEqualTo(DEFAULT_CALCULATION_UNIT);
        assertThat(testProductStore.getUseCalculationDate()).isEqualTo(DEFAULT_USE_CALCULATION_DATE);
        assertThat(testProductStore.getCalculationDateFrom()).isEqualTo(DEFAULT_CALCULATION_DATE_FROM);
        assertThat(testProductStore.getCalculationDateTo()).isEqualTo(DEFAULT_CALCULATION_DATE_TO);
        assertThat(testProductStore.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductStore.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductStore.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductStore.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductStore.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductStoreWithExistingId() throws Exception {
        // Create the ProductStore with an existing ID
        productStore.setId(1L);
        ProductStoreDTO productStoreDTO = productStoreMapper.toDto(productStore);

        int databaseSizeBeforeCreate = productStoreRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductStoreMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productStoreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductStores() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get all the productStoreList
        restProductStoreMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productStore.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].calculationUnit").value(hasItem(DEFAULT_CALCULATION_UNIT)))
            .andExpect(jsonPath("$.[*].calculationDateFrom").value(hasItem(DEFAULT_CALCULATION_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].calculationDateTo").value(hasItem(DEFAULT_CALCULATION_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductStore() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        // Get the productStore
        restProductStoreMockMvc
            .perform(get(ENTITY_API_URL_ID, productStore.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productStore.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.calculation").value(DEFAULT_CALCULATION))
            .andExpect(jsonPath("$.calculationUnit").value(DEFAULT_CALCULATION_UNIT))
            .andExpect(jsonPath("$.calculationDateFrom").value(DEFAULT_CALCULATION_DATE_FROM.toString()))
            .andExpect(jsonPath("$.calculationDateTo").value(DEFAULT_CALCULATION_DATE_TO.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductStore() throws Exception {
        // Get the productStore
        restProductStoreMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductStore() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        int databaseSizeBeforeUpdate = productStoreRepository.findAll().size();

        // Update the productStore
        ProductStore updatedProductStore = productStoreRepository.findById(productStore.getId()).get();
        // Disconnect from session so that the updates on updatedProductStore are not directly saved in db
        em.detach(updatedProductStore);
        updatedProductStore.setType(UPDATED_TYPE);
        updatedProductStore.setCalculation(UPDATED_CALCULATION);
        updatedProductStore.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        updatedProductStore.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        updatedProductStore.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        updatedProductStore.setActivated(UPDATED_ACTIVATED);
        updatedProductStore.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductStore.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductStore.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductStore.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductStoreDTO productStoreDTO = productStoreMapper.toDto(updatedProductStore);

        restProductStoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productStoreDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productStoreDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeUpdate);
        ProductStore testProductStore = productStoreList.get(productStoreList.size() - 1);

        assertThat(testProductStore.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductStore.getUseCalculation()).isEqualTo(UPDATED_USE_CALCULATION);
        assertThat(testProductStore.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProductStore.getCalculationUnit()).isEqualTo(UPDATED_CALCULATION_UNIT);
        assertThat(testProductStore.getUseCalculationDate()).isEqualTo(UPDATED_USE_CALCULATION_DATE);
        assertThat(testProductStore.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testProductStore.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProductStore.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductStore.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductStore.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductStore.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductStore.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductStore() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRepository.findAll().size();
        productStore.setId(count.incrementAndGet());

        // Create the ProductStore
        ProductStoreDTO productStoreDTO = productStoreMapper.toDto(productStore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductStoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productStoreDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productStoreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductStore() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRepository.findAll().size();
        productStore.setId(count.incrementAndGet());

        // Create the ProductStore
        ProductStoreDTO productStoreDTO = productStoreMapper.toDto(productStore);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductStoreMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productStoreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductStore() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRepository.findAll().size();
        productStore.setId(count.incrementAndGet());

        // Create the ProductStore
        ProductStoreDTO productStoreDTO = productStoreMapper.toDto(productStore);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductStoreMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productStoreDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductStoreWithPatch() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        int databaseSizeBeforeUpdate = productStoreRepository.findAll().size();

        // Update the productStore using partial update
        ProductStore partialUpdatedProductStore = new ProductStore();
        partialUpdatedProductStore.setId(productStore.getId());

        partialUpdatedProductStore.setType(UPDATED_TYPE);
        partialUpdatedProductStore.setCalculation(UPDATED_CALCULATION);
        partialUpdatedProductStore.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        partialUpdatedProductStore.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        partialUpdatedProductStore.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        partialUpdatedProductStore.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductStore.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductStore.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductStore.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductStore.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductStore.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductStore))
            )
            .andExpect(status().isOk());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeUpdate);
        ProductStore testProductStore = productStoreList.get(productStoreList.size() - 1);

        assertThat(testProductStore.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductStore.getUseCalculation()).isEqualTo(UPDATED_USE_CALCULATION);
        assertThat(testProductStore.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProductStore.getCalculationUnit()).isEqualTo(UPDATED_CALCULATION_UNIT);
        assertThat(testProductStore.getUseCalculationDate()).isEqualTo(DEFAULT_USE_CALCULATION_DATE);
        assertThat(testProductStore.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testProductStore.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProductStore.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductStore.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductStore.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductStore.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductStore.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductStoreWithPatch() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        int databaseSizeBeforeUpdate = productStoreRepository.findAll().size();

        // Update the productStore using partial update
        ProductStore partialUpdatedProductStore = new ProductStore();
        partialUpdatedProductStore.setId(productStore.getId());

        partialUpdatedProductStore.setType(UPDATED_TYPE);
        partialUpdatedProductStore.setCalculation(UPDATED_CALCULATION);
        partialUpdatedProductStore.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        partialUpdatedProductStore.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        partialUpdatedProductStore.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        partialUpdatedProductStore.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductStore.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductStore.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductStore.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductStore.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductStore.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductStore))
            )
            .andExpect(status().isOk());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeUpdate);
        ProductStore testProductStore = productStoreList.get(productStoreList.size() - 1);

        assertThat(testProductStore.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductStore.getUseCalculation()).isEqualTo(UPDATED_USE_CALCULATION);
        assertThat(testProductStore.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProductStore.getCalculationUnit()).isEqualTo(UPDATED_CALCULATION_UNIT);
        assertThat(testProductStore.getUseCalculationDate()).isEqualTo(UPDATED_USE_CALCULATION_DATE);
        assertThat(testProductStore.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testProductStore.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProductStore.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductStore.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductStore.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductStore.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductStore.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductStore() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRepository.findAll().size();
        productStore.setId(count.incrementAndGet());

        // Create the ProductStore
        ProductStoreDTO productStoreDTO = productStoreMapper.toDto(productStore);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productStoreDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productStoreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductStore() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRepository.findAll().size();
        productStore.setId(count.incrementAndGet());

        // Create the ProductStore
        ProductStoreDTO productStoreDTO = productStoreMapper.toDto(productStore);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductStoreMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productStoreDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductStore() throws Exception {
        int databaseSizeBeforeUpdate = productStoreRepository.findAll().size();
        productStore.setId(count.incrementAndGet());

        // Create the ProductStore
        ProductStoreDTO productStoreDTO = productStoreMapper.toDto(productStore);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductStoreMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productStoreDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductStore in the database
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductStore() throws Exception {
        // Initialize the database
        productStoreRepository.saveAndFlush(productStore);

        int databaseSizeBeforeDelete = productStoreRepository.findAll().size();

        // Delete the productStore
        restProductStoreMockMvc
            .perform(delete(ENTITY_API_URL_ID, productStore.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductStore> productStoreList = productStoreRepository.findAll();
        assertThat(productStoreList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
