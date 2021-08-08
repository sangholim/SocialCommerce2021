package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductLabel;
import com.toy.project.repository.ProductLabelRepository;
import com.toy.project.service.dto.ProductLabelDTO;
import com.toy.project.service.mapper.ProductLabelMapper;
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
 * Integration tests for the {@link ProductLabelResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductLabelResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_DISPLAY_DATE = false;
    private static final Boolean UPDATED_USE_DISPLAY_DATE = true;

    private static final Instant DEFAULT_DISPLAY_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISPLAY_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DISPLAY_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISPLAY_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/product-labels";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductLabelRepository productLabelRepository;

    @Autowired
    private ProductLabelMapper productLabelMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductLabelMockMvc;

    private ProductLabel productLabel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductLabel createEntity(EntityManager em) {
        ProductLabel productLabel = new ProductLabel();
        productLabel.setType(DEFAULT_TYPE);
        productLabel.setDisplayDateFrom(DEFAULT_DISPLAY_DATE_FROM);
        productLabel.setDisplayDateTo(DEFAULT_DISPLAY_DATE_TO);
        productLabel.setActivated(DEFAULT_ACTIVATED);
        productLabel.setCreatedBy(DEFAULT_CREATED_BY);
        productLabel.setCreatedDate(DEFAULT_CREATED_DATE);
        productLabel.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productLabel.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productLabel;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductLabel createUpdatedEntity(EntityManager em) {
        ProductLabel productLabel = new ProductLabel();
        productLabel.setType(UPDATED_TYPE);
        productLabel.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        productLabel.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        productLabel.setActivated(UPDATED_ACTIVATED);
        productLabel.setCreatedBy(UPDATED_CREATED_BY);
        productLabel.setCreatedDate(UPDATED_CREATED_DATE);
        productLabel.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productLabel.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productLabel;
    }

    @BeforeEach
    public void initTest() {
        productLabel = createEntity(em);
    }

    @Test
    @Transactional
    void createProductLabel() throws Exception {
        int databaseSizeBeforeCreate = productLabelRepository.findAll().size();
        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);
        restProductLabelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeCreate + 1);
        ProductLabel testProductLabel = productLabelList.get(productLabelList.size() - 1);
        assertThat(testProductLabel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductLabel.getUseDisplayDate()).isEqualTo(DEFAULT_USE_DISPLAY_DATE);
        assertThat(testProductLabel.getDisplayDateFrom()).isEqualTo(DEFAULT_DISPLAY_DATE_FROM);
        assertThat(testProductLabel.getDisplayDateTo()).isEqualTo(DEFAULT_DISPLAY_DATE_TO);
        assertThat(testProductLabel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductLabel.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductLabel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductLabel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductLabel.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductLabelWithExistingId() throws Exception {
        // Create the ProductLabel with an existing ID
        productLabel.setId(1L);
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        int databaseSizeBeforeCreate = productLabelRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductLabelMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductLabels() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get all the productLabelList
        restProductLabelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productLabel.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].displayDateFrom").value(hasItem(DEFAULT_DISPLAY_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].displayDateTo").value(hasItem(DEFAULT_DISPLAY_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductLabel() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        // Get the productLabel
        restProductLabelMockMvc
            .perform(get(ENTITY_API_URL_ID, productLabel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productLabel.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.displayDateFrom").value(DEFAULT_DISPLAY_DATE_FROM.toString()))
            .andExpect(jsonPath("$.displayDateTo").value(DEFAULT_DISPLAY_DATE_TO.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductLabel() throws Exception {
        // Get the productLabel
        restProductLabelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductLabel() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();

        // Update the productLabel
        ProductLabel updatedProductLabel = productLabelRepository.findById(productLabel.getId()).get();
        // Disconnect from session so that the updates on updatedProductLabel are not directly saved in db
        em.detach(updatedProductLabel);
        updatedProductLabel.setType(UPDATED_TYPE);
        updatedProductLabel.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        updatedProductLabel.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        updatedProductLabel.setActivated(UPDATED_ACTIVATED);
        updatedProductLabel.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductLabel.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductLabel.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductLabel.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(updatedProductLabel);

        restProductLabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productLabelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
        ProductLabel testProductLabel = productLabelList.get(productLabelList.size() - 1);
        assertThat(testProductLabel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductLabel.getUseDisplayDate()).isEqualTo(UPDATED_USE_DISPLAY_DATE);
        assertThat(testProductLabel.getDisplayDateFrom()).isEqualTo(UPDATED_DISPLAY_DATE_FROM);
        assertThat(testProductLabel.getDisplayDateTo()).isEqualTo(UPDATED_DISPLAY_DATE_TO);
        assertThat(testProductLabel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductLabel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductLabel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductLabel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productLabelDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductLabelWithPatch() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();

        // Update the productLabel using partial update
        ProductLabel partialUpdatedProductLabel = new ProductLabel();
        partialUpdatedProductLabel.setId(productLabel.getId());

        partialUpdatedProductLabel.setType(UPDATED_TYPE);
        partialUpdatedProductLabel.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        partialUpdatedProductLabel.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        partialUpdatedProductLabel.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductLabel.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductLabel.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductLabel.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductLabel.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductLabel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductLabel))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
        ProductLabel testProductLabel = productLabelList.get(productLabelList.size() - 1);
        assertThat(testProductLabel.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductLabel.getUseDisplayDate()).isEqualTo(DEFAULT_USE_DISPLAY_DATE);
        assertThat(testProductLabel.getDisplayDateFrom()).isEqualTo(DEFAULT_DISPLAY_DATE_FROM);
        assertThat(testProductLabel.getDisplayDateTo()).isEqualTo(DEFAULT_DISPLAY_DATE_TO);
        assertThat(testProductLabel.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductLabel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabel.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductLabel.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductLabel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductLabelWithPatch() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();

        // Update the productLabel using partial update
        ProductLabel partialUpdatedProductLabel = new ProductLabel();
        partialUpdatedProductLabel.setId(productLabel.getId());

        partialUpdatedProductLabel.setType(UPDATED_TYPE);
        partialUpdatedProductLabel.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        partialUpdatedProductLabel.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        partialUpdatedProductLabel.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductLabel.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductLabel.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductLabel.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductLabel.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductLabel.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductLabel))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
        ProductLabel testProductLabel = productLabelList.get(productLabelList.size() - 1);
        assertThat(testProductLabel.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductLabel.getUseDisplayDate()).isEqualTo(UPDATED_USE_DISPLAY_DATE);
        assertThat(testProductLabel.getDisplayDateFrom()).isEqualTo(UPDATED_DISPLAY_DATE_FROM);
        assertThat(testProductLabel.getDisplayDateTo()).isEqualTo(UPDATED_DISPLAY_DATE_TO);
        assertThat(testProductLabel.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductLabel.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabel.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductLabel.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductLabel.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productLabelDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductLabel() throws Exception {
        int databaseSizeBeforeUpdate = productLabelRepository.findAll().size();
        productLabel.setId(count.incrementAndGet());

        // Create the ProductLabel
        ProductLabelDTO productLabelDTO = productLabelMapper.toDto(productLabel);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductLabel in the database
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductLabel() throws Exception {
        // Initialize the database
        productLabelRepository.saveAndFlush(productLabel);

        int databaseSizeBeforeDelete = productLabelRepository.findAll().size();

        // Delete the productLabel
        restProductLabelMockMvc
            .perform(delete(ENTITY_API_URL_ID, productLabel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductLabel> productLabelList = productLabelRepository.findAll();
        assertThat(productLabelList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
