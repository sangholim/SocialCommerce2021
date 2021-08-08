package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductTemplate;
import com.toy.project.repository.ProductTemplateRepository;
import com.toy.project.service.dto.ProductTemplateDTO;
import com.toy.project.service.mapper.ProductTemplateMapper;
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
 * Integration tests for the {@link ProductTemplateResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductTemplateResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductTemplateRepository productTemplateRepository;

    @Autowired
    private ProductTemplateMapper productTemplateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductTemplateMockMvc;

    private ProductTemplate productTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTemplate createEntity(EntityManager em) {
        ProductTemplate productTemplate = new ProductTemplate();
        productTemplate.setType(DEFAULT_TYPE);
        productTemplate.setActivated(DEFAULT_ACTIVATED);
        productTemplate.setCreatedBy(DEFAULT_CREATED_BY);
        productTemplate.setCreatedDate(DEFAULT_CREATED_DATE);
        productTemplate.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productTemplate.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductTemplate createUpdatedEntity(EntityManager em) {
        ProductTemplate productTemplate = new ProductTemplate();
        productTemplate.setType(UPDATED_TYPE);
        productTemplate.setActivated(UPDATED_ACTIVATED);
        productTemplate.setCreatedBy(UPDATED_CREATED_BY);
        productTemplate.setCreatedDate(UPDATED_CREATED_DATE);
        productTemplate.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productTemplate.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productTemplate;
    }

    @BeforeEach
    public void initTest() {
        productTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createProductTemplate() throws Exception {
        int databaseSizeBeforeCreate = productTemplateRepository.findAll().size();
        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);
        restProductTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        ProductTemplate testProductTemplate = productTemplateList.get(productTemplateList.size() - 1);
        assertThat(testProductTemplate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductTemplate.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductTemplate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductTemplate.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductTemplate.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductTemplateWithExistingId() throws Exception {
        // Create the ProductTemplate with an existing ID
        productTemplate.setId(1L);
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        int databaseSizeBeforeCreate = productTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductTemplateMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductTemplates() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get all the productTemplateList
        restProductTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductTemplate() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        // Get the productTemplate
        restProductTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, productTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productTemplate.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductTemplate() throws Exception {
        // Get the productTemplate
        restProductTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductTemplate() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();

        // Update the productTemplate
        ProductTemplate updatedProductTemplate = productTemplateRepository.findById(productTemplate.getId()).get();
        // Disconnect from session so that the updates on updatedProductTemplate are not directly saved in db
        em.detach(updatedProductTemplate);
        updatedProductTemplate.setType(UPDATED_TYPE);
        updatedProductTemplate.setActivated(UPDATED_ACTIVATED);
        updatedProductTemplate.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductTemplate.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductTemplate.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductTemplate.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(updatedProductTemplate);

        restProductTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplate testProductTemplate = productTemplateList.get(productTemplateList.size() - 1);
        assertThat(testProductTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductTemplate.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTemplate.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductTemplateWithPatch() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();

        // Update the productTemplate using partial update
        ProductTemplate partialUpdatedProductTemplate = new ProductTemplate();
        partialUpdatedProductTemplate.setId(productTemplate.getId());

        partialUpdatedProductTemplate.setType(UPDATED_TYPE);
        partialUpdatedProductTemplate.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductTemplate.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductTemplate.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductTemplate.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductTemplate.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplate testProductTemplate = productTemplateList.get(productTemplateList.size() - 1);
        assertThat(testProductTemplate.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductTemplate.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTemplate.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductTemplate.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductTemplateWithPatch() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();

        // Update the productTemplate using partial update
        ProductTemplate partialUpdatedProductTemplate = new ProductTemplate();
        partialUpdatedProductTemplate.setId(productTemplate.getId());

        partialUpdatedProductTemplate.setType(UPDATED_TYPE);
        partialUpdatedProductTemplate.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductTemplate.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductTemplate.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductTemplate.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductTemplate.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProductTemplate testProductTemplate = productTemplateList.get(productTemplateList.size() - 1);
        assertThat(testProductTemplate.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductTemplate.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductTemplate.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productTemplateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productTemplateRepository.findAll().size();
        productTemplate.setId(count.incrementAndGet());

        // Create the ProductTemplate
        ProductTemplateDTO productTemplateDTO = productTemplateMapper.toDto(productTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductTemplate in the database
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductTemplate() throws Exception {
        // Initialize the database
        productTemplateRepository.saveAndFlush(productTemplate);

        int databaseSizeBeforeDelete = productTemplateRepository.findAll().size();

        // Delete the productTemplate
        restProductTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, productTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductTemplate> productTemplateList = productTemplateRepository.findAll();
        assertThat(productTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
