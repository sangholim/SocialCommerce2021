package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductCategoryManage;
import com.toy.project.repository.ProductCategoryManageRepository;
import com.toy.project.service.dto.ProductCategoryManageDTO;
import com.toy.project.service.mapper.ProductCategoryManageMapper;
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
 * Integration tests for the {@link ProductCategoryManageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductCategoryManageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN = "AAAAAAAAAA";
    private static final String UPDATED_MAIN = "BBBBBBBBBB";

    private static final String DEFAULT_SUB = "AAAAAAAAAA";
    private static final String UPDATED_SUB = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

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

    private static final String ENTITY_API_URL = "/api/product-category-manages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductCategoryManageRepository productCategoryManageRepository;

    @Autowired
    private ProductCategoryManageMapper productCategoryManageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductCategoryManageMockMvc;

    private ProductCategoryManage productCategoryManage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryManage createEntity(EntityManager em) {
        ProductCategoryManage productCategoryManage = new ProductCategoryManage();
        productCategoryManage.setName(DEFAULT_NAME);
        productCategoryManage.setType(DEFAULT_TYPE);
        productCategoryManage.setMain(DEFAULT_MAIN);
        productCategoryManage.setSub(DEFAULT_SUB);
        productCategoryManage.setDescription(DEFAULT_DESCRIPTION);
        productCategoryManage.setSequence(DEFAULT_SEQUENCE);
        productCategoryManage.setActivated(DEFAULT_ACTIVATED);
        productCategoryManage.setCreatedBy(DEFAULT_CREATED_BY);
        productCategoryManage.setCreatedDate(DEFAULT_CREATED_DATE);
        productCategoryManage.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productCategoryManage.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productCategoryManage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategoryManage createUpdatedEntity(EntityManager em) {
        ProductCategoryManage productCategoryManage = new ProductCategoryManage();
        productCategoryManage.setName(UPDATED_NAME);
        productCategoryManage.setType(UPDATED_TYPE);
        productCategoryManage.setMain(UPDATED_MAIN);
        productCategoryManage.setSub(UPDATED_SUB);
        productCategoryManage.setDescription(UPDATED_DESCRIPTION);
        productCategoryManage.setSequence(UPDATED_SEQUENCE);
        productCategoryManage.setActivated(UPDATED_ACTIVATED);
        productCategoryManage.setCreatedBy(UPDATED_CREATED_BY);
        productCategoryManage.setCreatedDate(UPDATED_CREATED_DATE);
        productCategoryManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productCategoryManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productCategoryManage;
    }

    @BeforeEach
    public void initTest() {
        productCategoryManage = createEntity(em);
    }

    @Test
    @Transactional
    void createProductCategoryManage() throws Exception {
        int databaseSizeBeforeCreate = productCategoryManageRepository.findAll().size();
        // Create the ProductCategoryManage
        ProductCategoryManageDTO productCategoryManageDTO = productCategoryManageMapper.toDto(productCategoryManage);
        restProductCategoryManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryManageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductCategoryManage in the database
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategoryManage testProductCategoryManage = productCategoryManageList.get(productCategoryManageList.size() - 1);
        assertThat(testProductCategoryManage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductCategoryManage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductCategoryManage.getMain()).isEqualTo(DEFAULT_MAIN);
        assertThat(testProductCategoryManage.getSub()).isEqualTo(DEFAULT_SUB);
        assertThat(testProductCategoryManage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductCategoryManage.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testProductCategoryManage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductCategoryManage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductCategoryManage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductCategoryManage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductCategoryManage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductCategoryManageWithExistingId() throws Exception {
        // Create the ProductCategoryManage with an existing ID
        productCategoryManage.setId(1L);
        ProductCategoryManageDTO productCategoryManageDTO = productCategoryManageMapper.toDto(productCategoryManage);

        int databaseSizeBeforeCreate = productCategoryManageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryManage in the database
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductCategoryManages() throws Exception {
        // Initialize the database
        productCategoryManageRepository.saveAndFlush(productCategoryManage);

        // Get all the productCategoryManageList
        restProductCategoryManageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategoryManage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].main").value(hasItem(DEFAULT_MAIN)))
            .andExpect(jsonPath("$.[*].sub").value(hasItem(DEFAULT_SUB)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductCategoryManage() throws Exception {
        // Initialize the database
        productCategoryManageRepository.saveAndFlush(productCategoryManage);

        // Get the productCategoryManage
        restProductCategoryManageMockMvc
            .perform(get(ENTITY_API_URL_ID, productCategoryManage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productCategoryManage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.main").value(DEFAULT_MAIN))
            .andExpect(jsonPath("$.sub").value(DEFAULT_SUB))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductCategoryManage() throws Exception {
        // Get the productCategoryManage
        restProductCategoryManageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductCategoryManage() throws Exception {
        // Initialize the database
        productCategoryManageRepository.saveAndFlush(productCategoryManage);

        int databaseSizeBeforeUpdate = productCategoryManageRepository.findAll().size();

        // Update the productCategoryManage
        ProductCategoryManage updatedProductCategoryManage = productCategoryManageRepository.findById(productCategoryManage.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategoryManage are not directly saved in db
        em.detach(updatedProductCategoryManage);
        updatedProductCategoryManage.setName(UPDATED_NAME);
        updatedProductCategoryManage.setType(UPDATED_TYPE);
        updatedProductCategoryManage.setMain(UPDATED_MAIN);
        updatedProductCategoryManage.setSub(UPDATED_SUB);
        updatedProductCategoryManage.setDescription(UPDATED_DESCRIPTION);
        updatedProductCategoryManage.setSequence(UPDATED_SEQUENCE);
        updatedProductCategoryManage.setActivated(UPDATED_ACTIVATED);
        updatedProductCategoryManage.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductCategoryManage.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductCategoryManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductCategoryManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductCategoryManageDTO productCategoryManageDTO = productCategoryManageMapper.toDto(updatedProductCategoryManage);

        restProductCategoryManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productCategoryManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryManageDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategoryManage in the database
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeUpdate);
        ProductCategoryManage testProductCategoryManage = productCategoryManageList.get(productCategoryManageList.size() - 1);
        assertThat(testProductCategoryManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategoryManage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductCategoryManage.getMain()).isEqualTo(UPDATED_MAIN);
        assertThat(testProductCategoryManage.getSub()).isEqualTo(UPDATED_SUB);
        assertThat(testProductCategoryManage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductCategoryManage.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testProductCategoryManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductCategoryManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategoryManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductCategoryManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductCategoryManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductCategoryManage() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryManageRepository.findAll().size();
        productCategoryManage.setId(count.incrementAndGet());

        // Create the ProductCategoryManage
        ProductCategoryManageDTO productCategoryManageDTO = productCategoryManageMapper.toDto(productCategoryManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productCategoryManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryManage in the database
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductCategoryManage() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryManageRepository.findAll().size();
        productCategoryManage.setId(count.incrementAndGet());

        // Create the ProductCategoryManage
        ProductCategoryManageDTO productCategoryManageDTO = productCategoryManageMapper.toDto(productCategoryManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryManage in the database
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductCategoryManage() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryManageRepository.findAll().size();
        productCategoryManage.setId(count.incrementAndGet());

        // Create the ProductCategoryManage
        ProductCategoryManageDTO productCategoryManageDTO = productCategoryManageMapper.toDto(productCategoryManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryManageMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductCategoryManage in the database
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductCategoryManageWithPatch() throws Exception {
        // Initialize the database
        productCategoryManageRepository.saveAndFlush(productCategoryManage);

        int databaseSizeBeforeUpdate = productCategoryManageRepository.findAll().size();

        // Update the productCategoryManage using partial update
        ProductCategoryManage partialUpdatedProductCategoryManage = new ProductCategoryManage();
        partialUpdatedProductCategoryManage.setId(productCategoryManage.getId());

        partialUpdatedProductCategoryManage.setName(UPDATED_NAME);
        partialUpdatedProductCategoryManage.setType(UPDATED_TYPE);
        partialUpdatedProductCategoryManage.setMain(UPDATED_MAIN);
        partialUpdatedProductCategoryManage.setSub(UPDATED_SUB);
        partialUpdatedProductCategoryManage.setDescription(UPDATED_DESCRIPTION);
        partialUpdatedProductCategoryManage.setSequence(UPDATED_SEQUENCE);
        partialUpdatedProductCategoryManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductCategoryManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductCategoryManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductCategoryManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductCategoryManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductCategoryManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductCategoryManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductCategoryManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategoryManage in the database
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeUpdate);
        ProductCategoryManage testProductCategoryManage = productCategoryManageList.get(productCategoryManageList.size() - 1);
        assertThat(testProductCategoryManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategoryManage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductCategoryManage.getMain()).isEqualTo(DEFAULT_MAIN);
        assertThat(testProductCategoryManage.getSub()).isEqualTo(DEFAULT_SUB);
        assertThat(testProductCategoryManage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductCategoryManage.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testProductCategoryManage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductCategoryManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategoryManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductCategoryManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductCategoryManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductCategoryManageWithPatch() throws Exception {
        // Initialize the database
        productCategoryManageRepository.saveAndFlush(productCategoryManage);

        int databaseSizeBeforeUpdate = productCategoryManageRepository.findAll().size();

        // Update the productCategoryManage using partial update
        ProductCategoryManage partialUpdatedProductCategoryManage = new ProductCategoryManage();
        partialUpdatedProductCategoryManage.setId(productCategoryManage.getId());

        partialUpdatedProductCategoryManage.setName(UPDATED_NAME);
        partialUpdatedProductCategoryManage.setType(UPDATED_TYPE);
        partialUpdatedProductCategoryManage.setMain(UPDATED_MAIN);
        partialUpdatedProductCategoryManage.setSub(UPDATED_SUB);
        partialUpdatedProductCategoryManage.setDescription(UPDATED_DESCRIPTION);
        partialUpdatedProductCategoryManage.setSequence(UPDATED_SEQUENCE);
        partialUpdatedProductCategoryManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductCategoryManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductCategoryManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductCategoryManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductCategoryManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductCategoryManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductCategoryManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductCategoryManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategoryManage in the database
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeUpdate);
        ProductCategoryManage testProductCategoryManage = productCategoryManageList.get(productCategoryManageList.size() - 1);
        assertThat(testProductCategoryManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategoryManage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductCategoryManage.getMain()).isEqualTo(UPDATED_MAIN);
        assertThat(testProductCategoryManage.getSub()).isEqualTo(UPDATED_SUB);
        assertThat(testProductCategoryManage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductCategoryManage.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testProductCategoryManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductCategoryManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategoryManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductCategoryManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductCategoryManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductCategoryManage() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryManageRepository.findAll().size();
        productCategoryManage.setId(count.incrementAndGet());

        // Create the ProductCategoryManage
        ProductCategoryManageDTO productCategoryManageDTO = productCategoryManageMapper.toDto(productCategoryManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productCategoryManageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryManage in the database
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductCategoryManage() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryManageRepository.findAll().size();
        productCategoryManage.setId(count.incrementAndGet());

        // Create the ProductCategoryManage
        ProductCategoryManageDTO productCategoryManageDTO = productCategoryManageMapper.toDto(productCategoryManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategoryManage in the database
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductCategoryManage() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryManageRepository.findAll().size();
        productCategoryManage.setId(count.incrementAndGet());

        // Create the ProductCategoryManage
        ProductCategoryManageDTO productCategoryManageDTO = productCategoryManageMapper.toDto(productCategoryManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryManageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductCategoryManage in the database
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductCategoryManage() throws Exception {
        // Initialize the database
        productCategoryManageRepository.saveAndFlush(productCategoryManage);

        int databaseSizeBeforeDelete = productCategoryManageRepository.findAll().size();

        // Delete the productCategoryManage
        restProductCategoryManageMockMvc
            .perform(delete(ENTITY_API_URL_ID, productCategoryManage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCategoryManage> productCategoryManageList = productCategoryManageRepository.findAll();
        assertThat(productCategoryManageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
