package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductMappingManage;
import com.toy.project.repository.ProductMappingManageRepository;
import com.toy.project.service.dto.ProductMappingManageDTO;
import com.toy.project.service.mapper.ProductMappingManageMapper;
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
 * Integration tests for the {@link ProductMappingManageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductMappingManageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Integer DEFAULT_COUNT = 1;
    private static final Integer UPDATED_COUNT = 2;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-mapping-manages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductMappingManageRepository productMappingManageRepository;

    @Autowired
    private ProductMappingManageMapper productMappingManageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductMappingManageMockMvc;

    private ProductMappingManage productMappingManage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMappingManage createEntity(EntityManager em) {
        ProductMappingManage productMappingManage = new ProductMappingManage();
        productMappingManage.setName(DEFAULT_NAME);
        productMappingManage.setType(DEFAULT_TYPE);
        productMappingManage.setCount(DEFAULT_COUNT);
        productMappingManage.setStatus(DEFAULT_STATUS);
        productMappingManage.setDisplayDateFrom(DEFAULT_DISPLAY_DATE_FROM);
        productMappingManage.setDisplayDateTo(DEFAULT_DISPLAY_DATE_TO);
        productMappingManage.setActivated(DEFAULT_ACTIVATED);
        productMappingManage.setCreatedBy(DEFAULT_CREATED_BY);
        productMappingManage.setCreatedDate(DEFAULT_CREATED_DATE);
        productMappingManage.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productMappingManage.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productMappingManage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductMappingManage createUpdatedEntity(EntityManager em) {
        ProductMappingManage productMappingManage = new ProductMappingManage();
        productMappingManage.setName(UPDATED_NAME);
        productMappingManage.setType(UPDATED_TYPE);
        productMappingManage.setCount(UPDATED_COUNT);
        productMappingManage.setStatus(UPDATED_STATUS);
        productMappingManage.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        productMappingManage.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        productMappingManage.setActivated(UPDATED_ACTIVATED);
        productMappingManage.setCreatedBy(UPDATED_CREATED_BY);
        productMappingManage.setCreatedDate(UPDATED_CREATED_DATE);
        productMappingManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productMappingManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productMappingManage;
    }

    @BeforeEach
    public void initTest() {
        productMappingManage = createEntity(em);
    }

    @Test
    @Transactional
    void createProductMappingManage() throws Exception {
        int databaseSizeBeforeCreate = productMappingManageRepository.findAll().size();
        // Create the ProductMappingManage
        ProductMappingManageDTO productMappingManageDTO = productMappingManageMapper.toDto(productMappingManage);
        restProductMappingManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingManageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductMappingManage in the database
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeCreate + 1);
        ProductMappingManage testProductMappingManage = productMappingManageList.get(productMappingManageList.size() - 1);
        assertThat(testProductMappingManage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductMappingManage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductMappingManage.getCount()).isEqualTo(DEFAULT_COUNT);
        assertThat(testProductMappingManage.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductMappingManage.getDisplayDateFrom()).isEqualTo(DEFAULT_DISPLAY_DATE_FROM);
        assertThat(testProductMappingManage.getDisplayDateTo()).isEqualTo(DEFAULT_DISPLAY_DATE_TO);
        assertThat(testProductMappingManage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductMappingManage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductMappingManage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductMappingManage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductMappingManage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductMappingManageWithExistingId() throws Exception {
        // Create the ProductMappingManage with an existing ID
        productMappingManage.setId(1L);
        ProductMappingManageDTO productMappingManageDTO = productMappingManageMapper.toDto(productMappingManage);

        int databaseSizeBeforeCreate = productMappingManageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductMappingManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMappingManage in the database
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductMappingManages() throws Exception {
        // Initialize the database
        productMappingManageRepository.saveAndFlush(productMappingManage);

        // Get all the productMappingManageList
        restProductMappingManageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productMappingManage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].count").value(hasItem(DEFAULT_COUNT)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
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
    void getProductMappingManage() throws Exception {
        // Initialize the database
        productMappingManageRepository.saveAndFlush(productMappingManage);

        // Get the productMappingManage
        restProductMappingManageMockMvc
            .perform(get(ENTITY_API_URL_ID, productMappingManage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productMappingManage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.count").value(DEFAULT_COUNT))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
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
    void getNonExistingProductMappingManage() throws Exception {
        // Get the productMappingManage
        restProductMappingManageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductMappingManage() throws Exception {
        // Initialize the database
        productMappingManageRepository.saveAndFlush(productMappingManage);

        int databaseSizeBeforeUpdate = productMappingManageRepository.findAll().size();

        // Update the productMappingManage
        ProductMappingManage updatedProductMappingManage = productMappingManageRepository.findById(productMappingManage.getId()).get();
        // Disconnect from session so that the updates on updatedProductMappingManage are not directly saved in db
        em.detach(updatedProductMappingManage);
        updatedProductMappingManage.setName(UPDATED_NAME);
        updatedProductMappingManage.setType(UPDATED_TYPE);
        updatedProductMappingManage.setCount(UPDATED_COUNT);
        updatedProductMappingManage.setStatus(UPDATED_STATUS);
        updatedProductMappingManage.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        updatedProductMappingManage.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        updatedProductMappingManage.setActivated(UPDATED_ACTIVATED);
        updatedProductMappingManage.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductMappingManage.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductMappingManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductMappingManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductMappingManageDTO productMappingManageDTO = productMappingManageMapper.toDto(updatedProductMappingManage);

        restProductMappingManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productMappingManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingManageDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductMappingManage in the database
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeUpdate);
        ProductMappingManage testProductMappingManage = productMappingManageList.get(productMappingManageList.size() - 1);
        assertThat(testProductMappingManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductMappingManage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductMappingManage.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testProductMappingManage.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductMappingManage.getDisplayDateFrom()).isEqualTo(UPDATED_DISPLAY_DATE_FROM);
        assertThat(testProductMappingManage.getDisplayDateTo()).isEqualTo(UPDATED_DISPLAY_DATE_TO);
        assertThat(testProductMappingManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductMappingManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductMappingManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductMappingManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductMappingManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductMappingManage() throws Exception {
        int databaseSizeBeforeUpdate = productMappingManageRepository.findAll().size();
        productMappingManage.setId(count.incrementAndGet());

        // Create the ProductMappingManage
        ProductMappingManageDTO productMappingManageDTO = productMappingManageMapper.toDto(productMappingManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMappingManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productMappingManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMappingManage in the database
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductMappingManage() throws Exception {
        int databaseSizeBeforeUpdate = productMappingManageRepository.findAll().size();
        productMappingManage.setId(count.incrementAndGet());

        // Create the ProductMappingManage
        ProductMappingManageDTO productMappingManageDTO = productMappingManageMapper.toDto(productMappingManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMappingManage in the database
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductMappingManage() throws Exception {
        int databaseSizeBeforeUpdate = productMappingManageRepository.findAll().size();
        productMappingManage.setId(count.incrementAndGet());

        // Create the ProductMappingManage
        ProductMappingManageDTO productMappingManageDTO = productMappingManageMapper.toDto(productMappingManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingManageMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productMappingManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductMappingManage in the database
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductMappingManageWithPatch() throws Exception {
        // Initialize the database
        productMappingManageRepository.saveAndFlush(productMappingManage);

        int databaseSizeBeforeUpdate = productMappingManageRepository.findAll().size();

        // Update the productMappingManage using partial update
        ProductMappingManage partialUpdatedProductMappingManage = new ProductMappingManage();
        partialUpdatedProductMappingManage.setId(productMappingManage.getId());

        partialUpdatedProductMappingManage.setName(UPDATED_NAME);
        partialUpdatedProductMappingManage.setType(UPDATED_TYPE);
        partialUpdatedProductMappingManage.setCount(UPDATED_COUNT);
        partialUpdatedProductMappingManage.setStatus(UPDATED_STATUS);
        partialUpdatedProductMappingManage.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        partialUpdatedProductMappingManage.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        partialUpdatedProductMappingManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductMappingManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductMappingManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductMappingManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductMappingManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductMappingManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductMappingManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductMappingManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductMappingManage in the database
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeUpdate);
        ProductMappingManage testProductMappingManage = productMappingManageList.get(productMappingManageList.size() - 1);
        assertThat(testProductMappingManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductMappingManage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductMappingManage.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testProductMappingManage.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductMappingManage.getDisplayDateFrom()).isEqualTo(UPDATED_DISPLAY_DATE_FROM);
        assertThat(testProductMappingManage.getDisplayDateTo()).isEqualTo(DEFAULT_DISPLAY_DATE_TO);
        assertThat(testProductMappingManage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductMappingManage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductMappingManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductMappingManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductMappingManage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductMappingManageWithPatch() throws Exception {
        // Initialize the database
        productMappingManageRepository.saveAndFlush(productMappingManage);

        int databaseSizeBeforeUpdate = productMappingManageRepository.findAll().size();

        // Update the productMappingManage using partial update
        ProductMappingManage partialUpdatedProductMappingManage = new ProductMappingManage();
        partialUpdatedProductMappingManage.setId(productMappingManage.getId());

        partialUpdatedProductMappingManage.setName(UPDATED_NAME);
        partialUpdatedProductMappingManage.setType(UPDATED_TYPE);
        partialUpdatedProductMappingManage.setCount(UPDATED_COUNT);
        partialUpdatedProductMappingManage.setStatus(UPDATED_STATUS);
        partialUpdatedProductMappingManage.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        partialUpdatedProductMappingManage.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        partialUpdatedProductMappingManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductMappingManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductMappingManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductMappingManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductMappingManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductMappingManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductMappingManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductMappingManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductMappingManage in the database
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeUpdate);
        ProductMappingManage testProductMappingManage = productMappingManageList.get(productMappingManageList.size() - 1);
        assertThat(testProductMappingManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductMappingManage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductMappingManage.getCount()).isEqualTo(UPDATED_COUNT);
        assertThat(testProductMappingManage.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductMappingManage.getDisplayDateFrom()).isEqualTo(UPDATED_DISPLAY_DATE_FROM);
        assertThat(testProductMappingManage.getDisplayDateTo()).isEqualTo(UPDATED_DISPLAY_DATE_TO);
        assertThat(testProductMappingManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductMappingManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductMappingManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductMappingManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductMappingManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductMappingManage() throws Exception {
        int databaseSizeBeforeUpdate = productMappingManageRepository.findAll().size();
        productMappingManage.setId(count.incrementAndGet());

        // Create the ProductMappingManage
        ProductMappingManageDTO productMappingManageDTO = productMappingManageMapper.toDto(productMappingManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductMappingManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productMappingManageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMappingManage in the database
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductMappingManage() throws Exception {
        int databaseSizeBeforeUpdate = productMappingManageRepository.findAll().size();
        productMappingManage.setId(count.incrementAndGet());

        // Create the ProductMappingManage
        ProductMappingManageDTO productMappingManageDTO = productMappingManageMapper.toDto(productMappingManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductMappingManage in the database
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductMappingManage() throws Exception {
        int databaseSizeBeforeUpdate = productMappingManageRepository.findAll().size();
        productMappingManage.setId(count.incrementAndGet());

        // Create the ProductMappingManage
        ProductMappingManageDTO productMappingManageDTO = productMappingManageMapper.toDto(productMappingManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductMappingManageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productMappingManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductMappingManage in the database
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductMappingManage() throws Exception {
        // Initialize the database
        productMappingManageRepository.saveAndFlush(productMappingManage);

        int databaseSizeBeforeDelete = productMappingManageRepository.findAll().size();

        // Delete the productMappingManage
        restProductMappingManageMockMvc
            .perform(delete(ENTITY_API_URL_ID, productMappingManage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductMappingManage> productMappingManageList = productMappingManageRepository.findAll();
        assertThat(productMappingManageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
