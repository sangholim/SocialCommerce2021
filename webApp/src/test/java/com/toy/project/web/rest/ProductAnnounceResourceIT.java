package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductAnnounce;
import com.toy.project.repository.ProductAnnounceRepository;
import com.toy.project.service.dto.ProductAnnounceDTO;
import com.toy.project.service.mapper.ProductAnnounceMapper;
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
 * Integration tests for the {@link ProductAnnounceResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductAnnounceResourceIT {

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

    private static final String ENTITY_API_URL = "/api/product-announces";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductAnnounceRepository productAnnounceRepository;

    @Autowired
    private ProductAnnounceMapper productAnnounceMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductAnnounceMockMvc;

    private ProductAnnounce productAnnounce;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAnnounce createEntity(EntityManager em) {
        ProductAnnounce productAnnounce = new ProductAnnounce();
        productAnnounce.setName(DEFAULT_NAME);
        productAnnounce.setContent(DEFAULT_CONTENT);
        productAnnounce.setActivated(DEFAULT_ACTIVATED);
        productAnnounce.setCreatedBy(DEFAULT_CREATED_BY);
        productAnnounce.setCreatedDate(DEFAULT_CREATED_DATE);
        productAnnounce.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productAnnounce.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productAnnounce;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAnnounce createUpdatedEntity(EntityManager em) {
        ProductAnnounce productAnnounce = new ProductAnnounce();
        productAnnounce.setName(UPDATED_NAME);
        productAnnounce.setContent(UPDATED_CONTENT);
        productAnnounce.setActivated(UPDATED_ACTIVATED);
        productAnnounce.setCreatedBy(UPDATED_CREATED_BY);
        productAnnounce.setCreatedDate(UPDATED_CREATED_DATE);
        productAnnounce.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productAnnounce.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productAnnounce;
    }

    @BeforeEach
    public void initTest() {
        productAnnounce = createEntity(em);
    }

    @Test
    @Transactional
    void createProductAnnounce() throws Exception {
        int databaseSizeBeforeCreate = productAnnounceRepository.findAll().size();
        // Create the ProductAnnounce
        ProductAnnounceDTO productAnnounceDTO = productAnnounceMapper.toDto(productAnnounce);
        restProductAnnounceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAnnounceDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductAnnounce in the database
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAnnounce testProductAnnounce = productAnnounceList.get(productAnnounceList.size() - 1);
        assertThat(testProductAnnounce.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductAnnounce.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testProductAnnounce.getUseDetail()).isEqualTo(DEFAULT_USE_DETAIL);
        assertThat(testProductAnnounce.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductAnnounce.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductAnnounce.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductAnnounce.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductAnnounce.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductAnnounceWithExistingId() throws Exception {
        // Create the ProductAnnounce with an existing ID
        productAnnounce.setId(1L);
        ProductAnnounceDTO productAnnounceDTO = productAnnounceMapper.toDto(productAnnounce);

        int databaseSizeBeforeCreate = productAnnounceRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAnnounceMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAnnounceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAnnounce in the database
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductAnnounces() throws Exception {
        // Initialize the database
        productAnnounceRepository.saveAndFlush(productAnnounce);

        // Get all the productAnnounceList
        restProductAnnounceMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAnnounce.getId().intValue())))
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
    void getProductAnnounce() throws Exception {
        // Initialize the database
        productAnnounceRepository.saveAndFlush(productAnnounce);

        // Get the productAnnounce
        restProductAnnounceMockMvc
            .perform(get(ENTITY_API_URL_ID, productAnnounce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productAnnounce.getId().intValue()))
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
    void getNonExistingProductAnnounce() throws Exception {
        // Get the productAnnounce
        restProductAnnounceMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductAnnounce() throws Exception {
        // Initialize the database
        productAnnounceRepository.saveAndFlush(productAnnounce);

        int databaseSizeBeforeUpdate = productAnnounceRepository.findAll().size();

        // Update the productAnnounce
        ProductAnnounce updatedProductAnnounce = productAnnounceRepository.findById(productAnnounce.getId()).get();
        // Disconnect from session so that the updates on updatedProductAnnounce are not directly saved in db
        em.detach(updatedProductAnnounce);
        updatedProductAnnounce.setName(UPDATED_NAME);
        updatedProductAnnounce.setContent(UPDATED_CONTENT);
        updatedProductAnnounce.setActivated(UPDATED_ACTIVATED);
        updatedProductAnnounce.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductAnnounce.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductAnnounce.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductAnnounce.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductAnnounceDTO productAnnounceDTO = productAnnounceMapper.toDto(updatedProductAnnounce);

        restProductAnnounceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productAnnounceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductAnnounce in the database
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeUpdate);
        ProductAnnounce testProductAnnounce = productAnnounceList.get(productAnnounceList.size() - 1);
        assertThat(testProductAnnounce.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductAnnounce.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductAnnounce.getUseDetail()).isEqualTo(UPDATED_USE_DETAIL);
        assertThat(testProductAnnounce.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAnnounce.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductAnnounce.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductAnnounce.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductAnnounce.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductAnnounce() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceRepository.findAll().size();
        productAnnounce.setId(count.incrementAndGet());

        // Create the ProductAnnounce
        ProductAnnounceDTO productAnnounceDTO = productAnnounceMapper.toDto(productAnnounce);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAnnounceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productAnnounceDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAnnounce in the database
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductAnnounce() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceRepository.findAll().size();
        productAnnounce.setId(count.incrementAndGet());

        // Create the ProductAnnounce
        ProductAnnounceDTO productAnnounceDTO = productAnnounceMapper.toDto(productAnnounce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAnnounceMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAnnounce in the database
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductAnnounce() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceRepository.findAll().size();
        productAnnounce.setId(count.incrementAndGet());

        // Create the ProductAnnounce
        ProductAnnounceDTO productAnnounceDTO = productAnnounceMapper.toDto(productAnnounce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAnnounceMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAnnounceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductAnnounce in the database
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductAnnounceWithPatch() throws Exception {
        // Initialize the database
        productAnnounceRepository.saveAndFlush(productAnnounce);

        int databaseSizeBeforeUpdate = productAnnounceRepository.findAll().size();

        // Update the productAnnounce using partial update
        ProductAnnounce partialUpdatedProductAnnounce = new ProductAnnounce();
        partialUpdatedProductAnnounce.setId(productAnnounce.getId());

        partialUpdatedProductAnnounce.setName(UPDATED_NAME);
        partialUpdatedProductAnnounce.setContent(UPDATED_CONTENT);
        partialUpdatedProductAnnounce.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductAnnounce.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductAnnounce.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductAnnounce.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductAnnounce.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductAnnounceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductAnnounce.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductAnnounce))
            )
            .andExpect(status().isOk());

        // Validate the ProductAnnounce in the database
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeUpdate);
        ProductAnnounce testProductAnnounce = productAnnounceList.get(productAnnounceList.size() - 1);
        assertThat(testProductAnnounce.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductAnnounce.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductAnnounce.getUseDetail()).isEqualTo(UPDATED_USE_DETAIL);
        assertThat(testProductAnnounce.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAnnounce.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductAnnounce.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductAnnounce.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductAnnounce.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductAnnounceWithPatch() throws Exception {
        // Initialize the database
        productAnnounceRepository.saveAndFlush(productAnnounce);

        int databaseSizeBeforeUpdate = productAnnounceRepository.findAll().size();

        // Update the productAnnounce using partial update
        ProductAnnounce partialUpdatedProductAnnounce = new ProductAnnounce();
        partialUpdatedProductAnnounce.setId(productAnnounce.getId());

        partialUpdatedProductAnnounce.setName(UPDATED_NAME);
        partialUpdatedProductAnnounce.setContent(UPDATED_CONTENT);
        partialUpdatedProductAnnounce.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductAnnounce.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductAnnounce.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductAnnounce.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductAnnounce.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductAnnounceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductAnnounce.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductAnnounce))
            )
            .andExpect(status().isOk());

        // Validate the ProductAnnounce in the database
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeUpdate);
        ProductAnnounce testProductAnnounce = productAnnounceList.get(productAnnounceList.size() - 1);
        assertThat(testProductAnnounce.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductAnnounce.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testProductAnnounce.getUseDetail()).isEqualTo(UPDATED_USE_DETAIL);
        assertThat(testProductAnnounce.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAnnounce.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductAnnounce.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductAnnounce.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductAnnounce.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductAnnounce() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceRepository.findAll().size();
        productAnnounce.setId(count.incrementAndGet());

        // Create the ProductAnnounce
        ProductAnnounceDTO productAnnounceDTO = productAnnounceMapper.toDto(productAnnounce);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAnnounceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productAnnounceDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAnnounce in the database
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductAnnounce() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceRepository.findAll().size();
        productAnnounce.setId(count.incrementAndGet());

        // Create the ProductAnnounce
        ProductAnnounceDTO productAnnounceDTO = productAnnounceMapper.toDto(productAnnounce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAnnounceMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAnnounce in the database
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductAnnounce() throws Exception {
        int databaseSizeBeforeUpdate = productAnnounceRepository.findAll().size();
        productAnnounce.setId(count.incrementAndGet());

        // Create the ProductAnnounce
        ProductAnnounceDTO productAnnounceDTO = productAnnounceMapper.toDto(productAnnounce);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAnnounceMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAnnounceDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductAnnounce in the database
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductAnnounce() throws Exception {
        // Initialize the database
        productAnnounceRepository.saveAndFlush(productAnnounce);

        int databaseSizeBeforeDelete = productAnnounceRepository.findAll().size();

        // Delete the productAnnounce
        restProductAnnounceMockMvc
            .perform(delete(ENTITY_API_URL_ID, productAnnounce.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAnnounce> productAnnounceList = productAnnounceRepository.findAll();
        assertThat(productAnnounceList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
