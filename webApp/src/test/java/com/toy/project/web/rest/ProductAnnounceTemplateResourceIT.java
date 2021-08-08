package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductAnnounceTemplate;
import com.toy.project.repository.ProductAnnounceTemplateRepository;
import com.toy.project.service.dto.ProductAnnounceTemplateDTO;
import com.toy.project.service.mapper.ProductAnnounceTemplateMapper;
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
 * Integration tests for the {@link ProductAnnounceTemplateResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductAnnounceTemplateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_DETAIL = false;
    private static final Boolean UPDATED_USE_DETAIL = true;

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

    private static final String ENTITY_API_URL = "/api/product-announce-templates";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductAnnounceTemplateRepository productAnnounceTemplateRepository;

    @Autowired
    private ProductAnnounceTemplateMapper productAnnounceTemplateMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductAnnounceTemplateMockMvc;

    private ProductAnnounceTemplate productAnnounceTemplate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAnnounceTemplate createEntity(EntityManager em) {
        ProductAnnounceTemplate productAnnounceTemplate = new ProductAnnounceTemplate();
        productAnnounceTemplate.setName(DEFAULT_NAME);
        productAnnounceTemplate.setContent(DEFAULT_CONTENT);
        productAnnounceTemplate.setActivated(DEFAULT_ACTIVATED);
        productAnnounceTemplate.setCreatedBy(DEFAULT_CREATED_BY);
        productAnnounceTemplate.setCreatedDate(DEFAULT_CREATED_DATE);
        productAnnounceTemplate.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productAnnounceTemplate.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productAnnounceTemplate;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAnnounceTemplate createUpdatedEntity(EntityManager em) {
        ProductAnnounceTemplate productAnnounceTemplate = new ProductAnnounceTemplate();
        productAnnounceTemplate.setName(UPDATED_NAME);
        productAnnounceTemplate.setContent(UPDATED_CONTENT);
        productAnnounceTemplate.setActivated(UPDATED_ACTIVATED);
        productAnnounceTemplate.setCreatedBy(UPDATED_CREATED_BY);
        productAnnounceTemplate.setCreatedDate(UPDATED_CREATED_DATE);
        productAnnounceTemplate.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productAnnounceTemplate.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productAnnounceTemplate;
    }

    @BeforeEach
    public void initTest() {
        productAnnounceTemplate = createEntity(em);
    }

    @Test
    @Transactional
    void createProductAnnounceTemplate() throws Exception {
        int databaseSizeBeforeCreate = productAnnounceTemplateRepository.findAll().size();
        // Create the ProductAnnounceTemplate
        ProductAnnounceTemplateDTO productAnnounceTemplateDTO = productAnnounceTemplateMapper.toDto(productAnnounceTemplate);
        restProductAnnounceTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceTemplateDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductAnnounceTemplate in the database
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAnnounceTemplate testProductAnnounceTemplate = productAnnounceTemplateList.get(productAnnounceTemplateList.size() - 1);
        assertThat(testProductAnnounceTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductAnnounceTemplate.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProductAnnounceTemplate.getUseDetail()).isEqualTo(DEFAULT_USE_DETAIL);
        assertThat(testProductAnnounceTemplate.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductAnnounceTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductAnnounceTemplate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductAnnounceTemplate.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductAnnounceTemplate.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductAnnounceTemplateWithExistingId() throws Exception {
        // Create the ProductAnnounceTemplate with an existing ID
        productAnnounceTemplate.setId(1L);
        ProductAnnounceTemplateDTO productAnnounceTemplateDTO = productAnnounceTemplateMapper.toDto(productAnnounceTemplate);

        int databaseSizeBeforeCreate = productAnnounceTemplateRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAnnounceTemplateMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAnnounceTemplate in the database
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductAnnounceTemplates() throws Exception {
        // Initialize the database
        productAnnounceTemplateRepository.saveAndFlush(productAnnounceTemplate);

        // Get all the productAnnounceTemplateList
        restProductAnnounceTemplateMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAnnounceTemplate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductAnnounceTemplate() throws Exception {
        // Initialize the database
        productAnnounceTemplateRepository.saveAndFlush(productAnnounceTemplate);

        // Get the productAnnounceTemplate
        restProductAnnounceTemplateMockMvc
            .perform(get(ENTITY_API_URL_ID, productAnnounceTemplate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productAnnounceTemplate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductAnnounceTemplate() throws Exception {
        // Get the productAnnounceTemplate
        restProductAnnounceTemplateMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductAnnounceTemplate() throws Exception {
        // Initialize the database
        productAnnounceTemplateRepository.saveAndFlush(productAnnounceTemplate);

        int databaseSizeBeforeUpdate = productAnnounceTemplateRepository.findAll().size();

        // Update the productAnnounceTemplate
        ProductAnnounceTemplate updatedProductAnnounceTemplate = productAnnounceTemplateRepository
            .findById(productAnnounceTemplate.getId())
            .get();
        // Disconnect from session so that the updates on updatedProductAnnounceTemplate are not directly saved in db
        em.detach(updatedProductAnnounceTemplate);
        updatedProductAnnounceTemplate.setName(UPDATED_NAME);
        updatedProductAnnounceTemplate.setContent(UPDATED_CONTENT);
        updatedProductAnnounceTemplate.setActivated(UPDATED_ACTIVATED);
        updatedProductAnnounceTemplate.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductAnnounceTemplate.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductAnnounceTemplate.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductAnnounceTemplate.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductAnnounceTemplateDTO productAnnounceTemplateDTO = productAnnounceTemplateMapper.toDto(updatedProductAnnounceTemplate);

        restProductAnnounceTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productAnnounceTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceTemplateDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductAnnounceTemplate in the database
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProductAnnounceTemplate testProductAnnounceTemplate = productAnnounceTemplateList.get(productAnnounceTemplateList.size() - 1);
        assertThat(testProductAnnounceTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductAnnounceTemplate.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductAnnounceTemplate.getUseDetail()).isEqualTo(UPDATED_USE_DETAIL);
        assertThat(testProductAnnounceTemplate.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAnnounceTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductAnnounceTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductAnnounceTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductAnnounceTemplate.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductAnnounceTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceTemplateRepository.findAll().size();
        productAnnounceTemplate.setId(count.incrementAndGet());

        // Create the ProductAnnounceTemplate
        ProductAnnounceTemplateDTO productAnnounceTemplateDTO = productAnnounceTemplateMapper.toDto(productAnnounceTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAnnounceTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productAnnounceTemplateDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAnnounceTemplate in the database
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductAnnounceTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceTemplateRepository.findAll().size();
        productAnnounceTemplate.setId(count.incrementAndGet());

        // Create the ProductAnnounceTemplate
        ProductAnnounceTemplateDTO productAnnounceTemplateDTO = productAnnounceTemplateMapper.toDto(productAnnounceTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAnnounceTemplateMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAnnounceTemplate in the database
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductAnnounceTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceTemplateRepository.findAll().size();
        productAnnounceTemplate.setId(count.incrementAndGet());

        // Create the ProductAnnounceTemplate
        ProductAnnounceTemplateDTO productAnnounceTemplateDTO = productAnnounceTemplateMapper.toDto(productAnnounceTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAnnounceTemplateMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductAnnounceTemplate in the database
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductAnnounceTemplateWithPatch() throws Exception {
        // Initialize the database
        productAnnounceTemplateRepository.saveAndFlush(productAnnounceTemplate);

        int databaseSizeBeforeUpdate = productAnnounceTemplateRepository.findAll().size();

        // Update the productAnnounceTemplate using partial update
        ProductAnnounceTemplate partialUpdatedProductAnnounceTemplate = new ProductAnnounceTemplate();
        partialUpdatedProductAnnounceTemplate.setId(productAnnounceTemplate.getId());

        partialUpdatedProductAnnounceTemplate.setName(UPDATED_NAME);
        partialUpdatedProductAnnounceTemplate.setContent(UPDATED_CONTENT);
        partialUpdatedProductAnnounceTemplate.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductAnnounceTemplate.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductAnnounceTemplate.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductAnnounceTemplate.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductAnnounceTemplate.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductAnnounceTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductAnnounceTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductAnnounceTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ProductAnnounceTemplate in the database
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProductAnnounceTemplate testProductAnnounceTemplate = productAnnounceTemplateList.get(productAnnounceTemplateList.size() - 1);
        assertThat(testProductAnnounceTemplate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductAnnounceTemplate.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductAnnounceTemplate.getUseDetail()).isEqualTo(DEFAULT_USE_DETAIL);
        assertThat(testProductAnnounceTemplate.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAnnounceTemplate.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductAnnounceTemplate.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductAnnounceTemplate.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductAnnounceTemplate.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductAnnounceTemplateWithPatch() throws Exception {
        // Initialize the database
        productAnnounceTemplateRepository.saveAndFlush(productAnnounceTemplate);

        int databaseSizeBeforeUpdate = productAnnounceTemplateRepository.findAll().size();

        // Update the productAnnounceTemplate using partial update
        ProductAnnounceTemplate partialUpdatedProductAnnounceTemplate = new ProductAnnounceTemplate();
        partialUpdatedProductAnnounceTemplate.setId(productAnnounceTemplate.getId());

        partialUpdatedProductAnnounceTemplate.setName(UPDATED_NAME);
        partialUpdatedProductAnnounceTemplate.setContent(UPDATED_CONTENT);
        partialUpdatedProductAnnounceTemplate.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductAnnounceTemplate.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductAnnounceTemplate.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductAnnounceTemplate.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductAnnounceTemplate.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductAnnounceTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductAnnounceTemplate.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductAnnounceTemplate))
            )
            .andExpect(status().isOk());

        // Validate the ProductAnnounceTemplate in the database
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeUpdate);
        ProductAnnounceTemplate testProductAnnounceTemplate = productAnnounceTemplateList.get(productAnnounceTemplateList.size() - 1);
        assertThat(testProductAnnounceTemplate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductAnnounceTemplate.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductAnnounceTemplate.getUseDetail()).isEqualTo(UPDATED_USE_DETAIL);
        assertThat(testProductAnnounceTemplate.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAnnounceTemplate.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductAnnounceTemplate.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductAnnounceTemplate.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductAnnounceTemplate.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductAnnounceTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceTemplateRepository.findAll().size();
        productAnnounceTemplate.setId(count.incrementAndGet());

        // Create the ProductAnnounceTemplate
        ProductAnnounceTemplateDTO productAnnounceTemplateDTO = productAnnounceTemplateMapper.toDto(productAnnounceTemplate);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAnnounceTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productAnnounceTemplateDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAnnounceTemplate in the database
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductAnnounceTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceTemplateRepository.findAll().size();
        productAnnounceTemplate.setId(count.incrementAndGet());

        // Create the ProductAnnounceTemplate
        ProductAnnounceTemplateDTO productAnnounceTemplateDTO = productAnnounceTemplateMapper.toDto(productAnnounceTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAnnounceTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceTemplateDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAnnounceTemplate in the database
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductAnnounceTemplate() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceTemplateRepository.findAll().size();
        productAnnounceTemplate.setId(count.incrementAndGet());

        // Create the ProductAnnounceTemplate
        ProductAnnounceTemplateDTO productAnnounceTemplateDTO = productAnnounceTemplateMapper.toDto(productAnnounceTemplate);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAnnounceTemplateMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceTemplateDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductAnnounceTemplate in the database
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductAnnounceTemplate() throws Exception {
        // Initialize the database
        productAnnounceTemplateRepository.saveAndFlush(productAnnounceTemplate);

        int databaseSizeBeforeDelete = productAnnounceTemplateRepository.findAll().size();

        // Delete the productAnnounceTemplate
        restProductAnnounceTemplateMockMvc
            .perform(delete(ENTITY_API_URL_ID, productAnnounceTemplate.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAnnounceTemplate> productAnnounceTemplateList = productAnnounceTemplateRepository.findAll();
        assertThat(productAnnounceTemplateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
