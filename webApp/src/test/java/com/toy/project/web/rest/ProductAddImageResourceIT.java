package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductAddImage;
import com.toy.project.repository.ProductAddImageRepository;
import com.toy.project.service.dto.ProductAddImageDTO;
import com.toy.project.service.mapper.ProductAddImageMapper;
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
 * Integration tests for the {@link ProductAddImageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductAddImageResourceIT {

    private static final String DEFAULT_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_URL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-add-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductAddImageRepository productAddImageRepository;

    @Autowired
    private ProductAddImageMapper productAddImageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductAddImageMockMvc;

    private ProductAddImage productAddImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAddImage createEntity(EntityManager em) {
        ProductAddImage productAddImage = new ProductAddImage();
        productAddImage.setImageUrl(DEFAULT_IMAGE_URL);
        productAddImage.setActivated(DEFAULT_ACTIVATED);
        productAddImage.setCreatedBy(DEFAULT_CREATED_BY);
        productAddImage.setCreatedDate(DEFAULT_CREATED_DATE);
        productAddImage.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productAddImage.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productAddImage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAddImage createUpdatedEntity(EntityManager em) {
        ProductAddImage productAddImage = new ProductAddImage();
        productAddImage.setImageUrl(UPDATED_IMAGE_URL);
        productAddImage.setActivated(UPDATED_ACTIVATED);
        productAddImage.setCreatedBy(UPDATED_CREATED_BY);
        productAddImage.setCreatedDate(UPDATED_CREATED_DATE);
        productAddImage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productAddImage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productAddImage;
    }

    @BeforeEach
    public void initTest() {
        productAddImage = createEntity(em);
    }

    @Test
    @Transactional
    void createProductAddImage() throws Exception {
        int databaseSizeBeforeCreate = productAddImageRepository.findAll().size();
        // Create the ProductAddImage
        ProductAddImageDTO productAddImageDTO = productAddImageMapper.toDto(productAddImage);
        restProductAddImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAddImageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductAddImage in the database
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAddImage testProductAddImage = productAddImageList.get(productAddImageList.size() - 1);
        assertThat(testProductAddImage.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testProductAddImage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductAddImage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductAddImage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductAddImage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductAddImage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductAddImageWithExistingId() throws Exception {
        // Create the ProductAddImage with an existing ID
        productAddImage.setId(1L);
        ProductAddImageDTO productAddImageDTO = productAddImageMapper.toDto(productAddImage);

        int databaseSizeBeforeCreate = productAddImageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAddImageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAddImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAddImage in the database
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductAddImages() throws Exception {
        // Initialize the database
        productAddImageRepository.saveAndFlush(productAddImage);

        // Get all the productAddImageList
        restProductAddImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAddImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductAddImage() throws Exception {
        // Initialize the database
        productAddImageRepository.saveAndFlush(productAddImage);

        // Get the productAddImage
        restProductAddImageMockMvc
            .perform(get(ENTITY_API_URL_ID, productAddImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productAddImage.getId().intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductAddImage() throws Exception {
        // Get the productAddImage
        restProductAddImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductAddImage() throws Exception {
        // Initialize the database
        productAddImageRepository.saveAndFlush(productAddImage);

        int databaseSizeBeforeUpdate = productAddImageRepository.findAll().size();

        // Update the productAddImage
        ProductAddImage updatedProductAddImage = productAddImageRepository.findById(productAddImage.getId()).get();
        // Disconnect from session so that the updates on updatedProductAddImage are not directly saved in db
        em.detach(updatedProductAddImage);
        updatedProductAddImage.setImageUrl(UPDATED_IMAGE_URL);
        updatedProductAddImage.setActivated(UPDATED_ACTIVATED);
        updatedProductAddImage.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductAddImage.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductAddImage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductAddImage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductAddImageDTO productAddImageDTO = productAddImageMapper.toDto(updatedProductAddImage);

        restProductAddImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productAddImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAddImageDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductAddImage in the database
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeUpdate);
        ProductAddImage testProductAddImage = productAddImageList.get(productAddImageList.size() - 1);
        assertThat(testProductAddImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProductAddImage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAddImage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductAddImage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductAddImage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductAddImage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductAddImage() throws Exception {
        int databaseSizeBeforeUpdate = productAddImageRepository.findAll().size();
        productAddImage.setId(count.incrementAndGet());

        // Create the ProductAddImage
        ProductAddImageDTO productAddImageDTO = productAddImageMapper.toDto(productAddImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAddImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productAddImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAddImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAddImage in the database
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductAddImage() throws Exception {
        int databaseSizeBeforeUpdate = productAddImageRepository.findAll().size();
        productAddImage.setId(count.incrementAndGet());

        // Create the ProductAddImage
        ProductAddImageDTO productAddImageDTO = productAddImageMapper.toDto(productAddImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAddImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAddImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAddImage in the database
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductAddImage() throws Exception {
        int databaseSizeBeforeUpdate = productAddImageRepository.findAll().size();
        productAddImage.setId(count.incrementAndGet());

        // Create the ProductAddImage
        ProductAddImageDTO productAddImageDTO = productAddImageMapper.toDto(productAddImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAddImageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAddImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductAddImage in the database
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductAddImageWithPatch() throws Exception {
        // Initialize the database
        productAddImageRepository.saveAndFlush(productAddImage);

        int databaseSizeBeforeUpdate = productAddImageRepository.findAll().size();

        // Update the productAddImage using partial update
        ProductAddImage partialUpdatedProductAddImage = new ProductAddImage();
        partialUpdatedProductAddImage.setId(productAddImage.getId());

        partialUpdatedProductAddImage.setImageUrl(UPDATED_IMAGE_URL);
        partialUpdatedProductAddImage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductAddImage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductAddImage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductAddImage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductAddImage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductAddImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductAddImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductAddImage))
            )
            .andExpect(status().isOk());

        // Validate the ProductAddImage in the database
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeUpdate);
        ProductAddImage testProductAddImage = productAddImageList.get(productAddImageList.size() - 1);
        assertThat(testProductAddImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProductAddImage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAddImage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductAddImage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductAddImage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductAddImage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductAddImageWithPatch() throws Exception {
        // Initialize the database
        productAddImageRepository.saveAndFlush(productAddImage);

        int databaseSizeBeforeUpdate = productAddImageRepository.findAll().size();

        // Update the productAddImage using partial update
        ProductAddImage partialUpdatedProductAddImage = new ProductAddImage();
        partialUpdatedProductAddImage.setId(productAddImage.getId());

        partialUpdatedProductAddImage.setImageUrl(UPDATED_IMAGE_URL);
        partialUpdatedProductAddImage.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductAddImage.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductAddImage.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductAddImage.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductAddImage.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductAddImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductAddImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductAddImage))
            )
            .andExpect(status().isOk());

        // Validate the ProductAddImage in the database
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeUpdate);
        ProductAddImage testProductAddImage = productAddImageList.get(productAddImageList.size() - 1);
        assertThat(testProductAddImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testProductAddImage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAddImage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductAddImage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductAddImage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductAddImage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductAddImage() throws Exception {
        int databaseSizeBeforeUpdate = productAddImageRepository.findAll().size();
        productAddImage.setId(count.incrementAndGet());

        // Create the ProductAddImage
        ProductAddImageDTO productAddImageDTO = productAddImageMapper.toDto(productAddImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAddImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productAddImageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAddImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAddImage in the database
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductAddImage() throws Exception {
        int databaseSizeBeforeUpdate = productAddImageRepository.findAll().size();
        productAddImage.setId(count.incrementAndGet());

        // Create the ProductAddImage
        ProductAddImageDTO productAddImageDTO = productAddImageMapper.toDto(productAddImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAddImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAddImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAddImage in the database
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductAddImage() throws Exception {
        int databaseSizeBeforeUpdate = productAddImageRepository.findAll().size();
        productAddImage.setId(count.incrementAndGet());

        // Create the ProductAddImage
        ProductAddImageDTO productAddImageDTO = productAddImageMapper.toDto(productAddImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAddImageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAddImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductAddImage in the database
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductAddImage() throws Exception {
        // Initialize the database
        productAddImageRepository.saveAndFlush(productAddImage);

        int databaseSizeBeforeDelete = productAddImageRepository.findAll().size();

        // Delete the productAddImage
        restProductAddImageMockMvc
            .perform(delete(ENTITY_API_URL_ID, productAddImage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAddImage> productAddImageList = productAddImageRepository.findAll();
        assertThat(productAddImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
