package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductLabelManage;
import com.toy.project.repository.ProductLabelManageRepository;
import com.toy.project.service.dto.ProductLabelManageDTO;
import com.toy.project.service.mapper.ProductLabelManageMapper;
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
 * Integration tests for the {@link ProductLabelManageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductLabelManageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-label-manages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductLabelManageRepository productLabelManageRepository;

    @Autowired
    private ProductLabelManageMapper productLabelManageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductLabelManageMockMvc;

    private ProductLabelManage productLabelManage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductLabelManage createEntity(EntityManager em) {
        ProductLabelManage productLabelManage = new ProductLabelManage();
        productLabelManage.setName(DEFAULT_NAME);
        productLabelManage.setColor(DEFAULT_COLOR);
        productLabelManage.setContent(DEFAULT_CONTENT);
        productLabelManage.setType(DEFAULT_TYPE);
        productLabelManage.setActivated(DEFAULT_ACTIVATED);
        productLabelManage.setCreatedBy(DEFAULT_CREATED_BY);
        productLabelManage.setCreatedDate(DEFAULT_CREATED_DATE);
        productLabelManage.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productLabelManage.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productLabelManage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductLabelManage createUpdatedEntity(EntityManager em) {
        ProductLabelManage productLabelManage = new ProductLabelManage();
        productLabelManage.setName(UPDATED_NAME);
        productLabelManage.setColor(UPDATED_COLOR);
        productLabelManage.setContent(UPDATED_CONTENT);
        productLabelManage.setType(UPDATED_TYPE);
        productLabelManage.setActivated(UPDATED_ACTIVATED);
        productLabelManage.setCreatedBy(UPDATED_CREATED_BY);
        productLabelManage.setCreatedDate(UPDATED_CREATED_DATE);
        productLabelManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productLabelManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productLabelManage;
    }

    @BeforeEach
    public void initTest() {
        productLabelManage = createEntity(em);
    }

    @Test
    @Transactional
    void createProductLabelManage() throws Exception {
        int databaseSizeBeforeCreate = productLabelManageRepository.findAll().size();
        // Create the ProductLabelManage
        ProductLabelManageDTO productLabelManageDTO = productLabelManageMapper.toDto(productLabelManage);
        restProductLabelManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelManageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductLabelManage in the database
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeCreate + 1);
        ProductLabelManage testProductLabelManage = productLabelManageList.get(productLabelManageList.size() - 1);
        assertThat(testProductLabelManage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductLabelManage.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testProductLabelManage.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProductLabelManage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductLabelManage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductLabelManage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductLabelManage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductLabelManage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductLabelManage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductLabelManageWithExistingId() throws Exception {
        // Create the ProductLabelManage with an existing ID
        productLabelManage.setId(1L);
        ProductLabelManageDTO productLabelManageDTO = productLabelManageMapper.toDto(productLabelManage);

        int databaseSizeBeforeCreate = productLabelManageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductLabelManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabelManage in the database
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductLabelManages() throws Exception {
        // Initialize the database
        productLabelManageRepository.saveAndFlush(productLabelManage);

        // Get all the productLabelManageList
        restProductLabelManageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productLabelManage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductLabelManage() throws Exception {
        // Initialize the database
        productLabelManageRepository.saveAndFlush(productLabelManage);

        // Get the productLabelManage
        restProductLabelManageMockMvc
            .perform(get(ENTITY_API_URL_ID, productLabelManage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productLabelManage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductLabelManage() throws Exception {
        // Get the productLabelManage
        restProductLabelManageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductLabelManage() throws Exception {
        // Initialize the database
        productLabelManageRepository.saveAndFlush(productLabelManage);

        int databaseSizeBeforeUpdate = productLabelManageRepository.findAll().size();

        // Update the productLabelManage
        ProductLabelManage updatedProductLabelManage = productLabelManageRepository.findById(productLabelManage.getId()).get();
        // Disconnect from session so that the updates on updatedProductLabelManage are not directly saved in db
        em.detach(updatedProductLabelManage);
        updatedProductLabelManage.setName(UPDATED_NAME);
        updatedProductLabelManage.setColor(UPDATED_COLOR);
        updatedProductLabelManage.setContent(UPDATED_CONTENT);
        updatedProductLabelManage.setType(UPDATED_TYPE);
        updatedProductLabelManage.setActivated(UPDATED_ACTIVATED);
        updatedProductLabelManage.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductLabelManage.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductLabelManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductLabelManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductLabelManageDTO productLabelManageDTO = productLabelManageMapper.toDto(updatedProductLabelManage);

        restProductLabelManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productLabelManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelManageDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabelManage in the database
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeUpdate);
        ProductLabelManage testProductLabelManage = productLabelManageList.get(productLabelManageList.size() - 1);
        assertThat(testProductLabelManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductLabelManage.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProductLabelManage.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductLabelManage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductLabelManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductLabelManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabelManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductLabelManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductLabelManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductLabelManage() throws Exception {
        int databaseSizeBeforeUpdate = productLabelManageRepository.findAll().size();
        productLabelManage.setId(count.incrementAndGet());

        // Create the ProductLabelManage
        ProductLabelManageDTO productLabelManageDTO = productLabelManageMapper.toDto(productLabelManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductLabelManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productLabelManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabelManage in the database
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductLabelManage() throws Exception {
        int databaseSizeBeforeUpdate = productLabelManageRepository.findAll().size();
        productLabelManage.setId(count.incrementAndGet());

        // Create the ProductLabelManage
        ProductLabelManageDTO productLabelManageDTO = productLabelManageMapper.toDto(productLabelManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabelManage in the database
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductLabelManage() throws Exception {
        int databaseSizeBeforeUpdate = productLabelManageRepository.findAll().size();
        productLabelManage.setId(count.incrementAndGet());

        // Create the ProductLabelManage
        ProductLabelManageDTO productLabelManageDTO = productLabelManageMapper.toDto(productLabelManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelManageMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productLabelManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductLabelManage in the database
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductLabelManageWithPatch() throws Exception {
        // Initialize the database
        productLabelManageRepository.saveAndFlush(productLabelManage);

        int databaseSizeBeforeUpdate = productLabelManageRepository.findAll().size();

        // Update the productLabelManage using partial update
        ProductLabelManage partialUpdatedProductLabelManage = new ProductLabelManage();
        partialUpdatedProductLabelManage.setId(productLabelManage.getId());

        partialUpdatedProductLabelManage.setName(UPDATED_NAME);
        partialUpdatedProductLabelManage.setColor(UPDATED_COLOR);
        partialUpdatedProductLabelManage.setContent(UPDATED_CONTENT);
        partialUpdatedProductLabelManage.setType(UPDATED_TYPE);
        partialUpdatedProductLabelManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductLabelManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductLabelManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductLabelManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductLabelManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductLabelManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductLabelManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductLabelManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabelManage in the database
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeUpdate);
        ProductLabelManage testProductLabelManage = productLabelManageList.get(productLabelManageList.size() - 1);
        assertThat(testProductLabelManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductLabelManage.getColor()).isEqualTo(DEFAULT_COLOR);
        assertThat(testProductLabelManage.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProductLabelManage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductLabelManage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductLabelManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabelManage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductLabelManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductLabelManage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductLabelManageWithPatch() throws Exception {
        // Initialize the database
        productLabelManageRepository.saveAndFlush(productLabelManage);

        int databaseSizeBeforeUpdate = productLabelManageRepository.findAll().size();

        // Update the productLabelManage using partial update
        ProductLabelManage partialUpdatedProductLabelManage = new ProductLabelManage();
        partialUpdatedProductLabelManage.setId(productLabelManage.getId());
        partialUpdatedProductLabelManage.setName(UPDATED_NAME);
        partialUpdatedProductLabelManage.setColor(UPDATED_COLOR);
        partialUpdatedProductLabelManage.setContent(UPDATED_CONTENT);
        partialUpdatedProductLabelManage.setType(UPDATED_TYPE);
        partialUpdatedProductLabelManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductLabelManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductLabelManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductLabelManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductLabelManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductLabelManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductLabelManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductLabelManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductLabelManage in the database
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeUpdate);
        ProductLabelManage testProductLabelManage = productLabelManageList.get(productLabelManageList.size() - 1);
        assertThat(testProductLabelManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductLabelManage.getColor()).isEqualTo(UPDATED_COLOR);
        assertThat(testProductLabelManage.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductLabelManage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductLabelManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductLabelManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductLabelManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductLabelManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductLabelManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductLabelManage() throws Exception {
        int databaseSizeBeforeUpdate = productLabelManageRepository.findAll().size();
        productLabelManage.setId(count.incrementAndGet());

        // Create the ProductLabelManage
        ProductLabelManageDTO productLabelManageDTO = productLabelManageMapper.toDto(productLabelManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductLabelManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productLabelManageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabelManage in the database
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductLabelManage() throws Exception {
        int databaseSizeBeforeUpdate = productLabelManageRepository.findAll().size();
        productLabelManage.setId(count.incrementAndGet());

        // Create the ProductLabelManage
        ProductLabelManageDTO productLabelManageDTO = productLabelManageMapper.toDto(productLabelManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductLabelManage in the database
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductLabelManage() throws Exception {
        int databaseSizeBeforeUpdate = productLabelManageRepository.findAll().size();
        productLabelManage.setId(count.incrementAndGet());

        // Create the ProductLabelManage
        ProductLabelManageDTO productLabelManageDTO = productLabelManageMapper.toDto(productLabelManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductLabelManageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productLabelManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductLabelManage in the database
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductLabelManage() throws Exception {
        // Initialize the database
        productLabelManageRepository.saveAndFlush(productLabelManage);

        int databaseSizeBeforeDelete = productLabelManageRepository.findAll().size();

        // Delete the productLabelManage
        restProductLabelManageMockMvc
            .perform(delete(ENTITY_API_URL_ID, productLabelManage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductLabelManage> productLabelManageList = productLabelManageRepository.findAll();
        assertThat(productLabelManageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
