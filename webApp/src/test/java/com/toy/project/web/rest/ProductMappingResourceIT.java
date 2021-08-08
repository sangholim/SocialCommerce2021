package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductMapping;
import com.toy.project.repository.ProductMappingRepository;
import com.toy.project.service.dto.ProductMappingDTO;
import com.toy.project.service.mapper.ProductMappingMapper;
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
 * Integration tests for the {@link ProductMappingResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductMappingResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-mappings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductMappingRepository productMappingRepository;

    @Autowired
    private ProductMappingMapper productMappingMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMappingMockMvc;

    private ProductMapping productMapping;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMapping createEntity(EntityManager em) {
        ProductMapping productMapping = new ProductMapping();
        productMapping.setType(DEFAULT_TYPE);
        productMapping.setActivated(DEFAULT_ACTIVATED);
        productMapping.setCreatedBy(DEFAULT_CREATED_BY);
        productMapping.setCreatedDate(DEFAULT_CREATED_DATE);
        productMapping.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productMapping.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productMapping;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMapping createUpdatedEntity(EntityManager em) {
        ProductMapping productMapping = new ProductMapping();
        productMapping.setType(UPDATED_TYPE);
        productMapping.setActivated(UPDATED_ACTIVATED);
        productMapping.setCreatedBy(UPDATED_CREATED_BY);
        productMapping.setCreatedDate(UPDATED_CREATED_DATE);
        productMapping.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productMapping.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productMapping;
    }

    @BeforeEach
    public void initTest() {
        productMapping = createEntity(em);
    }

    @Test
    @Transactional
    void createProductMapping() throws Exception {
        int databaseSizeBeforeCreate = productMappingRepository.findAll().size();
        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);
        restProductMappingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeCreate + 1);
        ProductMapping testProductMapping = productMappingList.get(productMappingList.size() - 1);
        assertThat(testProductMapping.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductMapping.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductMapping.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductMapping.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductMapping.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductMapping.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductMappingWithExistingId() throws Exception {
        // Create the ProductMapping with an existing ID
        productMapping.setId(1L);
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        int databaseSizeBeforeCreate = productMappingRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMappingMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductMappings() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get all the productMappingList
        restProductMappingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productMapping.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductMapping() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        // Get the productMapping
        restProductMappingMockMvc
            .perform(get(ENTITY_API_URL_ID, productMapping.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productMapping.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductMapping() throws Exception {
        // Get the productMapping
        restProductMappingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductMapping() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();

        // Update the productMapping
        ProductMapping updatedProductMapping = productMappingRepository.findById(productMapping.getId()).get();
        // Disconnect from session so that the updates on updatedProductMapping are not directly saved in db
        em.detach(updatedProductMapping);
        updatedProductMapping.setType(UPDATED_TYPE);
        updatedProductMapping.setActivated(UPDATED_ACTIVATED);
        updatedProductMapping.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductMapping.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductMapping.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductMapping.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(updatedProductMapping);

        restProductMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
        ProductMapping testProductMapping = productMappingList.get(productMappingList.size() - 1);
        assertThat(testProductMapping.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductMapping.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductMapping.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductMapping.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductMapping.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productMappingDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductMappingWithPatch() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();

        // Update the productMapping using partial update
        ProductMapping partialUpdatedProductMapping = new ProductMapping();
        partialUpdatedProductMapping.setId(productMapping.getId());

        partialUpdatedProductMapping.setType(UPDATED_TYPE);
        partialUpdatedProductMapping.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductMapping.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductMapping.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductMapping.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductMapping.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductMapping))
            )
            .andExpect(status().isOk());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
        ProductMapping testProductMapping = productMappingList.get(productMappingList.size() - 1);
        assertThat(testProductMapping.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductMapping.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductMapping.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductMapping.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductMapping.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductMappingWithPatch() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();

        // Update the productMapping using partial update
        ProductMapping partialUpdatedProductMapping = new ProductMapping();
        partialUpdatedProductMapping.setId(productMapping.getId());

        partialUpdatedProductMapping.setType(UPDATED_TYPE);
        partialUpdatedProductMapping.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductMapping.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductMapping.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductMapping.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductMapping.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductMapping.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductMapping))
            )
            .andExpect(status().isOk());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
        ProductMapping testProductMapping = productMappingList.get(productMappingList.size() - 1);
        assertThat(testProductMapping.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductMapping.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductMapping.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductMapping.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductMapping.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductMapping.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productMappingDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductMapping() throws Exception {
        int databaseSizeBeforeUpdate = productMappingRepository.findAll().size();
        productMapping.setId(count.incrementAndGet());

        // Create the ProductMapping
        ProductMappingDTO productMappingDTO = productMappingMapper.toDto(productMapping);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductMapping in the database
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductMapping() throws Exception {
        // Initialize the database
        productMappingRepository.saveAndFlush(productMapping);

        int databaseSizeBeforeDelete = productMappingRepository.findAll().size();

        // Delete the productMapping
        restProductMappingMockMvc
            .perform(delete(ENTITY_API_URL_ID, productMapping.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductMapping> productMappingList = productMappingRepository.findAll();
        assertThat(productMappingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
