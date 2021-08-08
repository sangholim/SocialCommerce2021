package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductNoticeManage;
import com.toy.project.repository.ProductNoticeManageRepository;
import com.toy.project.service.dto.ProductNoticeManageDTO;
import com.toy.project.service.mapper.ProductNoticeManageMapper;
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
 * Integration tests for the {@link ProductNoticeManageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductNoticeManageResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_FILE_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_PRIORITY_DISPLAY = false;
    private static final Boolean UPDATED_PRIORITY_DISPLAY = true;

    private static final Boolean DEFAULT_ALL_PRODUCT_DISPLAY = false;
    private static final Boolean UPDATED_ALL_PRODUCT_DISPLAY = true;

    private static final String DEFAULT_TARGET = "AAAAAAAAAA";
    private static final String UPDATED_TARGET = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE_DISPLAY_DATE = false;
    private static final Boolean UPDATED_ENABLE_DISPLAY_DATE = true;

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

    private static final String ENTITY_API_URL = "/api/product-notice-manages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductNoticeManageRepository productNoticeManageRepository;

    @Autowired
    private ProductNoticeManageMapper productNoticeManageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductNoticeManageMockMvc;

    private ProductNoticeManage productNoticeManage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductNoticeManage createEntity(EntityManager em) {
        ProductNoticeManage productNoticeManage = new ProductNoticeManage();
        productNoticeManage.setName(DEFAULT_NAME);
        productNoticeManage.setStatus(DEFAULT_STATUS);
        productNoticeManage.setType(DEFAULT_TYPE);
        productNoticeManage.setContentFileUrl(DEFAULT_CONTENT_FILE_URL);
        productNoticeManage.setPriorityDisplay(DEFAULT_PRIORITY_DISPLAY);
        productNoticeManage.setAllProductDisplay(DEFAULT_ALL_PRODUCT_DISPLAY);
        productNoticeManage.setTarget(DEFAULT_TARGET);
        productNoticeManage.setEnableDisplayDate(DEFAULT_ENABLE_DISPLAY_DATE);
        productNoticeManage.setDisplayDateFrom(DEFAULT_DISPLAY_DATE_FROM);
        productNoticeManage.setDisplayDateTo(DEFAULT_DISPLAY_DATE_TO);
        productNoticeManage.setActivated(DEFAULT_ACTIVATED);
        productNoticeManage.setCreatedBy(DEFAULT_CREATED_BY);
        productNoticeManage.setCreatedDate(DEFAULT_CREATED_DATE);
        productNoticeManage.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productNoticeManage.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productNoticeManage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductNoticeManage createUpdatedEntity(EntityManager em) {
        ProductNoticeManage productNoticeManage = new ProductNoticeManage();
        productNoticeManage.setName(UPDATED_NAME);
        productNoticeManage.setStatus(UPDATED_STATUS);
        productNoticeManage.setType(UPDATED_TYPE);
        productNoticeManage.setContentFileUrl(UPDATED_CONTENT_FILE_URL);
        productNoticeManage.setPriorityDisplay(UPDATED_PRIORITY_DISPLAY);
        productNoticeManage.setAllProductDisplay(UPDATED_ALL_PRODUCT_DISPLAY);
        productNoticeManage.setTarget(UPDATED_TARGET);
        productNoticeManage.setEnableDisplayDate(UPDATED_ENABLE_DISPLAY_DATE);
        productNoticeManage.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        productNoticeManage.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        productNoticeManage.setActivated(UPDATED_ACTIVATED);
        productNoticeManage.setCreatedBy(UPDATED_CREATED_BY);
        productNoticeManage.setCreatedDate(UPDATED_CREATED_DATE);
        productNoticeManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productNoticeManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productNoticeManage;
    }

    @BeforeEach
    public void initTest() {
        productNoticeManage = createEntity(em);
    }

    @Test
    @Transactional
    void createProductNoticeManage() throws Exception {
        int databaseSizeBeforeCreate = productNoticeManageRepository.findAll().size();
        // Create the ProductNoticeManage
        ProductNoticeManageDTO productNoticeManageDTO = productNoticeManageMapper.toDto(productNoticeManage);
        restProductNoticeManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeManageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductNoticeManage in the database
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeCreate + 1);
        ProductNoticeManage testProductNoticeManage = productNoticeManageList.get(productNoticeManageList.size() - 1);
        assertThat(testProductNoticeManage.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductNoticeManage.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductNoticeManage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductNoticeManage.getContentFileUrl()).isEqualTo(DEFAULT_CONTENT_FILE_URL);
        assertThat(testProductNoticeManage.getPriorityDisplay()).isEqualTo(DEFAULT_PRIORITY_DISPLAY);
        assertThat(testProductNoticeManage.getAllProductDisplay()).isEqualTo(DEFAULT_ALL_PRODUCT_DISPLAY);
        assertThat(testProductNoticeManage.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testProductNoticeManage.getEnableDisplayDate()).isEqualTo(DEFAULT_ENABLE_DISPLAY_DATE);
        assertThat(testProductNoticeManage.getDisplayDateFrom()).isEqualTo(DEFAULT_DISPLAY_DATE_FROM);
        assertThat(testProductNoticeManage.getDisplayDateTo()).isEqualTo(DEFAULT_DISPLAY_DATE_TO);
        assertThat(testProductNoticeManage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductNoticeManage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductNoticeManage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductNoticeManage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductNoticeManage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductNoticeManageWithExistingId() throws Exception {
        // Create the ProductNoticeManage with an existing ID
        productNoticeManage.setId(1L);
        ProductNoticeManageDTO productNoticeManageDTO = productNoticeManageMapper.toDto(productNoticeManage);

        int databaseSizeBeforeCreate = productNoticeManageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductNoticeManageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNoticeManage in the database
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductNoticeManages() throws Exception {
        // Initialize the database
        productNoticeManageRepository.saveAndFlush(productNoticeManage);

        // Get all the productNoticeManageList
        restProductNoticeManageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productNoticeManage.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].contentFileUrl").value(hasItem(DEFAULT_CONTENT_FILE_URL)))
            .andExpect(jsonPath("$.[*].priorityDisplay").value(hasItem(DEFAULT_PRIORITY_DISPLAY.booleanValue())))
            .andExpect(jsonPath("$.[*].allProductDisplay").value(hasItem(DEFAULT_ALL_PRODUCT_DISPLAY.booleanValue())))
            .andExpect(jsonPath("$.[*].target").value(hasItem(DEFAULT_TARGET)))
            .andExpect(jsonPath("$.[*].enableDisplayDate").value(hasItem(DEFAULT_ENABLE_DISPLAY_DATE.booleanValue())))
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
    void getProductNoticeManage() throws Exception {
        // Initialize the database
        productNoticeManageRepository.saveAndFlush(productNoticeManage);

        // Get the productNoticeManage
        restProductNoticeManageMockMvc
            .perform(get(ENTITY_API_URL_ID, productNoticeManage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productNoticeManage.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.contentFileUrl").value(DEFAULT_CONTENT_FILE_URL))
            .andExpect(jsonPath("$.priorityDisplay").value(DEFAULT_PRIORITY_DISPLAY.booleanValue()))
            .andExpect(jsonPath("$.allProductDisplay").value(DEFAULT_ALL_PRODUCT_DISPLAY.booleanValue()))
            .andExpect(jsonPath("$.target").value(DEFAULT_TARGET))
            .andExpect(jsonPath("$.enableDisplayDate").value(DEFAULT_ENABLE_DISPLAY_DATE.booleanValue()))
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
    void getNonExistingProductNoticeManage() throws Exception {
        // Get the productNoticeManage
        restProductNoticeManageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductNoticeManage() throws Exception {
        // Initialize the database
        productNoticeManageRepository.saveAndFlush(productNoticeManage);

        int databaseSizeBeforeUpdate = productNoticeManageRepository.findAll().size();

        // Update the productNoticeManage
        ProductNoticeManage updatedProductNoticeManage = productNoticeManageRepository.findById(productNoticeManage.getId()).get();
        // Disconnect from session so that the updates on updatedProductNoticeManage are not directly saved in db
        em.detach(updatedProductNoticeManage);
        updatedProductNoticeManage.setName(UPDATED_NAME);
        updatedProductNoticeManage.setStatus(UPDATED_STATUS);
        updatedProductNoticeManage.setType(UPDATED_TYPE);
        updatedProductNoticeManage.setContentFileUrl(UPDATED_CONTENT_FILE_URL);
        updatedProductNoticeManage.setPriorityDisplay(UPDATED_PRIORITY_DISPLAY);
        updatedProductNoticeManage.setAllProductDisplay(UPDATED_ALL_PRODUCT_DISPLAY);
        updatedProductNoticeManage.setTarget(UPDATED_TARGET);
        updatedProductNoticeManage.setEnableDisplayDate(UPDATED_ENABLE_DISPLAY_DATE);
        updatedProductNoticeManage.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        updatedProductNoticeManage.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        updatedProductNoticeManage.setActivated(UPDATED_ACTIVATED);
        updatedProductNoticeManage.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductNoticeManage.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductNoticeManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductNoticeManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductNoticeManageDTO productNoticeManageDTO = productNoticeManageMapper.toDto(updatedProductNoticeManage);

        restProductNoticeManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productNoticeManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeManageDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductNoticeManage in the database
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeUpdate);
        ProductNoticeManage testProductNoticeManage = productNoticeManageList.get(productNoticeManageList.size() - 1);
        assertThat(testProductNoticeManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductNoticeManage.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductNoticeManage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductNoticeManage.getContentFileUrl()).isEqualTo(UPDATED_CONTENT_FILE_URL);
        assertThat(testProductNoticeManage.getPriorityDisplay()).isEqualTo(UPDATED_PRIORITY_DISPLAY);
        assertThat(testProductNoticeManage.getAllProductDisplay()).isEqualTo(UPDATED_ALL_PRODUCT_DISPLAY);
        assertThat(testProductNoticeManage.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testProductNoticeManage.getEnableDisplayDate()).isEqualTo(UPDATED_ENABLE_DISPLAY_DATE);
        assertThat(testProductNoticeManage.getDisplayDateFrom()).isEqualTo(UPDATED_DISPLAY_DATE_FROM);
        assertThat(testProductNoticeManage.getDisplayDateTo()).isEqualTo(UPDATED_DISPLAY_DATE_TO);
        assertThat(testProductNoticeManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductNoticeManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductNoticeManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductNoticeManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductNoticeManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductNoticeManage() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeManageRepository.findAll().size();
        productNoticeManage.setId(count.incrementAndGet());

        // Create the ProductNoticeManage
        ProductNoticeManageDTO productNoticeManageDTO = productNoticeManageMapper.toDto(productNoticeManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductNoticeManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productNoticeManageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNoticeManage in the database
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductNoticeManage() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeManageRepository.findAll().size();
        productNoticeManage.setId(count.incrementAndGet());

        // Create the ProductNoticeManage
        ProductNoticeManageDTO productNoticeManageDTO = productNoticeManageMapper.toDto(productNoticeManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeManageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNoticeManage in the database
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductNoticeManage() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeManageRepository.findAll().size();
        productNoticeManage.setId(count.incrementAndGet());

        // Create the ProductNoticeManage
        ProductNoticeManageDTO productNoticeManageDTO = productNoticeManageMapper.toDto(productNoticeManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeManageMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductNoticeManage in the database
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductNoticeManageWithPatch() throws Exception {
        // Initialize the database
        productNoticeManageRepository.saveAndFlush(productNoticeManage);

        int databaseSizeBeforeUpdate = productNoticeManageRepository.findAll().size();

        // Update the productNoticeManage using partial update
        ProductNoticeManage partialUpdatedProductNoticeManage = new ProductNoticeManage();
        partialUpdatedProductNoticeManage.setId(productNoticeManage.getId());

        partialUpdatedProductNoticeManage.setName(UPDATED_NAME);
        partialUpdatedProductNoticeManage.setStatus(UPDATED_STATUS);
        partialUpdatedProductNoticeManage.setType(UPDATED_TYPE);
        partialUpdatedProductNoticeManage.setContentFileUrl(UPDATED_CONTENT_FILE_URL);
        partialUpdatedProductNoticeManage.setPriorityDisplay(UPDATED_PRIORITY_DISPLAY);
        partialUpdatedProductNoticeManage.setAllProductDisplay(UPDATED_ALL_PRODUCT_DISPLAY);
        partialUpdatedProductNoticeManage.setTarget(UPDATED_TARGET);
        partialUpdatedProductNoticeManage.setEnableDisplayDate(UPDATED_ENABLE_DISPLAY_DATE);
        partialUpdatedProductNoticeManage.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        partialUpdatedProductNoticeManage.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        partialUpdatedProductNoticeManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductNoticeManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductNoticeManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductNoticeManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductNoticeManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductNoticeManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductNoticeManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductNoticeManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductNoticeManage in the database
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeUpdate);
        ProductNoticeManage testProductNoticeManage = productNoticeManageList.get(productNoticeManageList.size() - 1);
        assertThat(testProductNoticeManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductNoticeManage.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductNoticeManage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testProductNoticeManage.getContentFileUrl()).isEqualTo(DEFAULT_CONTENT_FILE_URL);
        assertThat(testProductNoticeManage.getPriorityDisplay()).isEqualTo(UPDATED_PRIORITY_DISPLAY);
        assertThat(testProductNoticeManage.getAllProductDisplay()).isEqualTo(UPDATED_ALL_PRODUCT_DISPLAY);
        assertThat(testProductNoticeManage.getTarget()).isEqualTo(DEFAULT_TARGET);
        assertThat(testProductNoticeManage.getEnableDisplayDate()).isEqualTo(UPDATED_ENABLE_DISPLAY_DATE);
        assertThat(testProductNoticeManage.getDisplayDateFrom()).isEqualTo(DEFAULT_DISPLAY_DATE_FROM);
        assertThat(testProductNoticeManage.getDisplayDateTo()).isEqualTo(DEFAULT_DISPLAY_DATE_TO);
        assertThat(testProductNoticeManage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductNoticeManage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductNoticeManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductNoticeManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductNoticeManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductNoticeManageWithPatch() throws Exception {
        // Initialize the database
        productNoticeManageRepository.saveAndFlush(productNoticeManage);

        int databaseSizeBeforeUpdate = productNoticeManageRepository.findAll().size();

        // Update the productNoticeManage using partial update
        ProductNoticeManage partialUpdatedProductNoticeManage = new ProductNoticeManage();
        partialUpdatedProductNoticeManage.setId(productNoticeManage.getId());

        partialUpdatedProductNoticeManage.setName(UPDATED_NAME);
        partialUpdatedProductNoticeManage.setStatus(UPDATED_STATUS);
        partialUpdatedProductNoticeManage.setType(UPDATED_TYPE);
        partialUpdatedProductNoticeManage.setContentFileUrl(UPDATED_CONTENT_FILE_URL);
        partialUpdatedProductNoticeManage.setPriorityDisplay(UPDATED_PRIORITY_DISPLAY);
        partialUpdatedProductNoticeManage.setAllProductDisplay(UPDATED_ALL_PRODUCT_DISPLAY);
        partialUpdatedProductNoticeManage.setTarget(UPDATED_TARGET);
        partialUpdatedProductNoticeManage.setEnableDisplayDate(UPDATED_ENABLE_DISPLAY_DATE);
        partialUpdatedProductNoticeManage.setDisplayDateFrom(UPDATED_DISPLAY_DATE_FROM);
        partialUpdatedProductNoticeManage.setDisplayDateTo(UPDATED_DISPLAY_DATE_TO);
        partialUpdatedProductNoticeManage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductNoticeManage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductNoticeManage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductNoticeManage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductNoticeManage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductNoticeManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductNoticeManage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductNoticeManage))
            )
            .andExpect(status().isOk());

        // Validate the ProductNoticeManage in the database
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeUpdate);
        ProductNoticeManage testProductNoticeManage = productNoticeManageList.get(productNoticeManageList.size() - 1);
        assertThat(testProductNoticeManage.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductNoticeManage.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductNoticeManage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testProductNoticeManage.getContentFileUrl()).isEqualTo(UPDATED_CONTENT_FILE_URL);
        assertThat(testProductNoticeManage.getPriorityDisplay()).isEqualTo(UPDATED_PRIORITY_DISPLAY);
        assertThat(testProductNoticeManage.getAllProductDisplay()).isEqualTo(UPDATED_ALL_PRODUCT_DISPLAY);
        assertThat(testProductNoticeManage.getTarget()).isEqualTo(UPDATED_TARGET);
        assertThat(testProductNoticeManage.getEnableDisplayDate()).isEqualTo(UPDATED_ENABLE_DISPLAY_DATE);
        assertThat(testProductNoticeManage.getDisplayDateFrom()).isEqualTo(UPDATED_DISPLAY_DATE_FROM);
        assertThat(testProductNoticeManage.getDisplayDateTo()).isEqualTo(UPDATED_DISPLAY_DATE_TO);
        assertThat(testProductNoticeManage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductNoticeManage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductNoticeManage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductNoticeManage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductNoticeManage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductNoticeManage() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeManageRepository.findAll().size();
        productNoticeManage.setId(count.incrementAndGet());

        // Create the ProductNoticeManage
        ProductNoticeManageDTO productNoticeManageDTO = productNoticeManageMapper.toDto(productNoticeManage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductNoticeManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productNoticeManageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNoticeManage in the database
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductNoticeManage() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeManageRepository.findAll().size();
        productNoticeManage.setId(count.incrementAndGet());

        // Create the ProductNoticeManage
        ProductNoticeManageDTO productNoticeManageDTO = productNoticeManageMapper.toDto(productNoticeManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeManageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeManageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductNoticeManage in the database
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductNoticeManage() throws Exception {
        int databaseSizeBeforeUpdate = productNoticeManageRepository.findAll().size();
        productNoticeManage.setId(count.incrementAndGet());

        // Create the ProductNoticeManage
        ProductNoticeManageDTO productNoticeManageDTO = productNoticeManageMapper.toDto(productNoticeManage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductNoticeManageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productNoticeManageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductNoticeManage in the database
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductNoticeManage() throws Exception {
        // Initialize the database
        productNoticeManageRepository.saveAndFlush(productNoticeManage);

        int databaseSizeBeforeDelete = productNoticeManageRepository.findAll().size();

        // Delete the productNoticeManage
        restProductNoticeManageMockMvc
            .perform(delete(ENTITY_API_URL_ID, productNoticeManage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductNoticeManage> productNoticeManageList = productNoticeManageRepository.findAll();
        assertThat(productNoticeManageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
