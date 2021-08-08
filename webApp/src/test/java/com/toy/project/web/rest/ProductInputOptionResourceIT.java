package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductInputOption;
import com.toy.project.repository.ProductInputOptionRepository;
import com.toy.project.service.dto.ProductInputOptionDTO;
import com.toy.project.service.mapper.ProductInputOptionMapper;
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
 * Integration tests for the {@link ProductInputOptionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductInputOptionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-input-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductInputOptionRepository productInputOptionRepository;

    @Autowired
    private ProductInputOptionMapper productInputOptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductInputOptionMockMvc;

    private ProductInputOption productInputOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInputOption createEntity(EntityManager em) {
        ProductInputOption productInputOption = new ProductInputOption();
        productInputOption.setName(DEFAULT_NAME);
        productInputOption.setActivated(DEFAULT_ACTIVATED);
        productInputOption.setCreatedBy(DEFAULT_CREATED_BY);
        productInputOption.setCreatedDate(DEFAULT_CREATED_DATE);
        productInputOption.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productInputOption.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productInputOption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductInputOption createUpdatedEntity(EntityManager em) {
        ProductInputOption productInputOption = new ProductInputOption();
        productInputOption.setName(UPDATED_NAME);
        productInputOption.setActivated(UPDATED_ACTIVATED);
        productInputOption.setCreatedBy(UPDATED_CREATED_BY);
        productInputOption.setCreatedDate(UPDATED_CREATED_DATE);
        productInputOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productInputOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productInputOption;
    }

    @BeforeEach
    public void initTest() {
        productInputOption = createEntity(em);
    }

    @Test
    @Transactional
    void createProductInputOption() throws Exception {
        int databaseSizeBeforeCreate = productInputOptionRepository.findAll().size();
        // Create the ProductInputOption
        ProductInputOptionDTO productInputOptionDTO = productInputOptionMapper.toDto(productInputOption);
        restProductInputOptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInputOptionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductInputOption in the database
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductInputOption testProductInputOption = productInputOptionList.get(productInputOptionList.size() - 1);
        assertThat(testProductInputOption.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductInputOption.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductInputOption.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductInputOption.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductInputOption.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductInputOption.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductInputOptionWithExistingId() throws Exception {
        // Create the ProductInputOption with an existing ID
        productInputOption.setId(1L);
        ProductInputOptionDTO productInputOptionDTO = productInputOptionMapper.toDto(productInputOption);

        int databaseSizeBeforeCreate = productInputOptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductInputOptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInputOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInputOption in the database
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductInputOptions() throws Exception {
        // Initialize the database
        productInputOptionRepository.saveAndFlush(productInputOption);

        // Get all the productInputOptionList
        restProductInputOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productInputOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductInputOption() throws Exception {
        // Initialize the database
        productInputOptionRepository.saveAndFlush(productInputOption);

        // Get the productInputOption
        restProductInputOptionMockMvc
            .perform(get(ENTITY_API_URL_ID, productInputOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productInputOption.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductInputOption() throws Exception {
        // Get the productInputOption
        restProductInputOptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductInputOption() throws Exception {
        // Initialize the database
        productInputOptionRepository.saveAndFlush(productInputOption);

        int databaseSizeBeforeUpdate = productInputOptionRepository.findAll().size();

        // Update the productInputOption
        ProductInputOption updatedProductInputOption = productInputOptionRepository.findById(productInputOption.getId()).get();
        // Disconnect from session so that the updates on updatedProductInputOption are not directly saved in db
        em.detach(updatedProductInputOption);
        updatedProductInputOption.setName(UPDATED_NAME);
        updatedProductInputOption.setActivated(UPDATED_ACTIVATED);
        updatedProductInputOption.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductInputOption.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductInputOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductInputOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductInputOptionDTO productInputOptionDTO = productInputOptionMapper.toDto(updatedProductInputOption);

        restProductInputOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productInputOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInputOptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductInputOption in the database
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductInputOption testProductInputOption = productInputOptionList.get(productInputOptionList.size() - 1);
        assertThat(testProductInputOption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductInputOption.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductInputOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductInputOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductInputOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductInputOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductInputOption() throws Exception {
        int databaseSizeBeforeUpdate = productInputOptionRepository.findAll().size();
        productInputOption.setId(count.incrementAndGet());

        // Create the ProductInputOption
        ProductInputOptionDTO productInputOptionDTO = productInputOptionMapper.toDto(productInputOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInputOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productInputOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInputOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInputOption in the database
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductInputOption() throws Exception {
        int databaseSizeBeforeUpdate = productInputOptionRepository.findAll().size();
        productInputOption.setId(count.incrementAndGet());

        // Create the ProductInputOption
        ProductInputOptionDTO productInputOptionDTO = productInputOptionMapper.toDto(productInputOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInputOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInputOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInputOption in the database
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductInputOption() throws Exception {
        int databaseSizeBeforeUpdate = productInputOptionRepository.findAll().size();
        productInputOption.setId(count.incrementAndGet());

        // Create the ProductInputOption
        ProductInputOptionDTO productInputOptionDTO = productInputOptionMapper.toDto(productInputOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInputOptionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productInputOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductInputOption in the database
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductInputOptionWithPatch() throws Exception {
        // Initialize the database
        productInputOptionRepository.saveAndFlush(productInputOption);

        int databaseSizeBeforeUpdate = productInputOptionRepository.findAll().size();

        // Update the productInputOption using partial update
        ProductInputOption partialUpdatedProductInputOption = new ProductInputOption();
        partialUpdatedProductInputOption.setId(productInputOption.getId());

        partialUpdatedProductInputOption.setName(UPDATED_NAME);
        partialUpdatedProductInputOption.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductInputOption.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductInputOption.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductInputOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductInputOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductInputOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductInputOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductInputOption))
            )
            .andExpect(status().isOk());

        // Validate the ProductInputOption in the database
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductInputOption testProductInputOption = productInputOptionList.get(productInputOptionList.size() - 1);
        assertThat(testProductInputOption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductInputOption.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductInputOption.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductInputOption.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductInputOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductInputOption.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductInputOptionWithPatch() throws Exception {
        // Initialize the database
        productInputOptionRepository.saveAndFlush(productInputOption);

        int databaseSizeBeforeUpdate = productInputOptionRepository.findAll().size();

        // Update the productInputOption using partial update
        ProductInputOption partialUpdatedProductInputOption = new ProductInputOption();
        partialUpdatedProductInputOption.setId(productInputOption.getId());

        partialUpdatedProductInputOption.setName(UPDATED_NAME);
        partialUpdatedProductInputOption.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductInputOption.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductInputOption.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductInputOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductInputOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductInputOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductInputOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductInputOption))
            )
            .andExpect(status().isOk());

        // Validate the ProductInputOption in the database
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductInputOption testProductInputOption = productInputOptionList.get(productInputOptionList.size() - 1);
        assertThat(testProductInputOption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductInputOption.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductInputOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductInputOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductInputOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductInputOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductInputOption() throws Exception {
        int databaseSizeBeforeUpdate = productInputOptionRepository.findAll().size();
        productInputOption.setId(count.incrementAndGet());

        // Create the ProductInputOption
        ProductInputOptionDTO productInputOptionDTO = productInputOptionMapper.toDto(productInputOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductInputOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productInputOptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInputOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInputOption in the database
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductInputOption() throws Exception {
        int databaseSizeBeforeUpdate = productInputOptionRepository.findAll().size();
        productInputOption.setId(count.incrementAndGet());

        // Create the ProductInputOption
        ProductInputOptionDTO productInputOptionDTO = productInputOptionMapper.toDto(productInputOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInputOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInputOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductInputOption in the database
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductInputOption() throws Exception {
        int databaseSizeBeforeUpdate = productInputOptionRepository.findAll().size();
        productInputOption.setId(count.incrementAndGet());

        // Create the ProductInputOption
        ProductInputOptionDTO productInputOptionDTO = productInputOptionMapper.toDto(productInputOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductInputOptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productInputOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductInputOption in the database
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductInputOption() throws Exception {
        // Initialize the database
        productInputOptionRepository.saveAndFlush(productInputOption);

        int databaseSizeBeforeDelete = productInputOptionRepository.findAll().size();

        // Delete the productInputOption
        restProductInputOptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, productInputOption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductInputOption> productInputOptionList = productInputOptionRepository.findAll();
        assertThat(productInputOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
