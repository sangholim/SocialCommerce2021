package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.PackageDescriptionImage;
import com.toy.project.repository.PackageDescriptionImageRepository;
import com.toy.project.service.dto.PackageDescriptionImageDTO;
import com.toy.project.service.mapper.PackageDescriptionImageMapper;
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
 * Integration tests for the {@link PackageDescriptionImageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PackageDescriptionImageResourceIT {

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

    private static final String ENTITY_API_URL = "/api/package-description-images";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PackageDescriptionImageRepository packageDescriptionImageRepository;

    @Autowired
    private PackageDescriptionImageMapper packageDescriptionImageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPackageDescriptionImageMockMvc;

    private PackageDescriptionImage packageDescriptionImage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackageDescriptionImage createEntity(EntityManager em) {
        PackageDescriptionImage packageDescriptionImage = new PackageDescriptionImage()
            .imageUrl(DEFAULT_IMAGE_URL)
            .activated(DEFAULT_ACTIVATED);
        return packageDescriptionImage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackageDescriptionImage createUpdatedEntity(EntityManager em) {
        PackageDescriptionImage packageDescriptionImage = new PackageDescriptionImage()
            .imageUrl(UPDATED_IMAGE_URL)
            .activated(UPDATED_ACTIVATED);
        return packageDescriptionImage;
    }

    @BeforeEach
    public void initTest() {
        packageDescriptionImage = createEntity(em);
    }

    @Test
    @Transactional
    void createPackageDescriptionImage() throws Exception {
        int databaseSizeBeforeCreate = packageDescriptionImageRepository.findAll().size();
        // Create the PackageDescriptionImage
        PackageDescriptionImageDTO packageDescriptionImageDTO = packageDescriptionImageMapper.toDto(packageDescriptionImage);
        restPackageDescriptionImageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionImageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PackageDescriptionImage in the database
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeCreate + 1);
        PackageDescriptionImage testPackageDescriptionImage = packageDescriptionImageList.get(packageDescriptionImageList.size() - 1);
        assertThat(testPackageDescriptionImage.getImageUrl()).isEqualTo(DEFAULT_IMAGE_URL);
        assertThat(testPackageDescriptionImage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testPackageDescriptionImage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPackageDescriptionImage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPackageDescriptionImage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPackageDescriptionImage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPackageDescriptionImageWithExistingId() throws Exception {
        // Create the PackageDescriptionImage with an existing ID
        packageDescriptionImage.setId(1L);
        PackageDescriptionImageDTO packageDescriptionImageDTO = packageDescriptionImageMapper.toDto(packageDescriptionImage);

        int databaseSizeBeforeCreate = packageDescriptionImageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackageDescriptionImageMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageDescriptionImage in the database
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPackageDescriptionImages() throws Exception {
        // Initialize the database
        packageDescriptionImageRepository.saveAndFlush(packageDescriptionImage);

        // Get all the packageDescriptionImageList
        restPackageDescriptionImageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packageDescriptionImage.getId().intValue())))
            .andExpect(jsonPath("$.[*].imageUrl").value(hasItem(DEFAULT_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPackageDescriptionImage() throws Exception {
        // Initialize the database
        packageDescriptionImageRepository.saveAndFlush(packageDescriptionImage);

        // Get the packageDescriptionImage
        restPackageDescriptionImageMockMvc
            .perform(get(ENTITY_API_URL_ID, packageDescriptionImage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(packageDescriptionImage.getId().intValue()))
            .andExpect(jsonPath("$.imageUrl").value(DEFAULT_IMAGE_URL))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPackageDescriptionImage() throws Exception {
        // Get the packageDescriptionImage
        restPackageDescriptionImageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPackageDescriptionImage() throws Exception {
        // Initialize the database
        packageDescriptionImageRepository.saveAndFlush(packageDescriptionImage);

        int databaseSizeBeforeUpdate = packageDescriptionImageRepository.findAll().size();

        // Update the packageDescriptionImage
        PackageDescriptionImage updatedPackageDescriptionImage = packageDescriptionImageRepository
            .findById(packageDescriptionImage.getId())
            .get();
        // Disconnect from session so that the updates on updatedPackageDescriptionImage are not directly saved in db
        em.detach(updatedPackageDescriptionImage);
        updatedPackageDescriptionImage.imageUrl(UPDATED_IMAGE_URL).activated(UPDATED_ACTIVATED);
        PackageDescriptionImageDTO packageDescriptionImageDTO = packageDescriptionImageMapper.toDto(updatedPackageDescriptionImage);

        restPackageDescriptionImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, packageDescriptionImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionImageDTO))
            )
            .andExpect(status().isOk());

        // Validate the PackageDescriptionImage in the database
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeUpdate);
        PackageDescriptionImage testPackageDescriptionImage = packageDescriptionImageList.get(packageDescriptionImageList.size() - 1);
        assertThat(testPackageDescriptionImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testPackageDescriptionImage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testPackageDescriptionImage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPackageDescriptionImage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPackageDescriptionImage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPackageDescriptionImage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPackageDescriptionImage() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionImageRepository.findAll().size();
        packageDescriptionImage.setId(count.incrementAndGet());

        // Create the PackageDescriptionImage
        PackageDescriptionImageDTO packageDescriptionImageDTO = packageDescriptionImageMapper.toDto(packageDescriptionImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackageDescriptionImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, packageDescriptionImageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageDescriptionImage in the database
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPackageDescriptionImage() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionImageRepository.findAll().size();
        packageDescriptionImage.setId(count.incrementAndGet());

        // Create the PackageDescriptionImage
        PackageDescriptionImageDTO packageDescriptionImageDTO = packageDescriptionImageMapper.toDto(packageDescriptionImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageDescriptionImageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageDescriptionImage in the database
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPackageDescriptionImage() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionImageRepository.findAll().size();
        packageDescriptionImage.setId(count.incrementAndGet());

        // Create the PackageDescriptionImage
        PackageDescriptionImageDTO packageDescriptionImageDTO = packageDescriptionImageMapper.toDto(packageDescriptionImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageDescriptionImageMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PackageDescriptionImage in the database
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePackageDescriptionImageWithPatch() throws Exception {
        // Initialize the database
        packageDescriptionImageRepository.saveAndFlush(packageDescriptionImage);

        int databaseSizeBeforeUpdate = packageDescriptionImageRepository.findAll().size();

        // Update the packageDescriptionImage using partial update
        PackageDescriptionImage partialUpdatedPackageDescriptionImage = new PackageDescriptionImage();
        partialUpdatedPackageDescriptionImage.setId(packageDescriptionImage.getId());

        partialUpdatedPackageDescriptionImage.imageUrl(UPDATED_IMAGE_URL);

        restPackageDescriptionImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPackageDescriptionImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPackageDescriptionImage))
            )
            .andExpect(status().isOk());

        // Validate the PackageDescriptionImage in the database
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeUpdate);
        PackageDescriptionImage testPackageDescriptionImage = packageDescriptionImageList.get(packageDescriptionImageList.size() - 1);
        assertThat(testPackageDescriptionImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testPackageDescriptionImage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testPackageDescriptionImage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPackageDescriptionImage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPackageDescriptionImage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPackageDescriptionImage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePackageDescriptionImageWithPatch() throws Exception {
        // Initialize the database
        packageDescriptionImageRepository.saveAndFlush(packageDescriptionImage);

        int databaseSizeBeforeUpdate = packageDescriptionImageRepository.findAll().size();

        // Update the packageDescriptionImage using partial update
        PackageDescriptionImage partialUpdatedPackageDescriptionImage = new PackageDescriptionImage();
        partialUpdatedPackageDescriptionImage.setId(packageDescriptionImage.getId());

        partialUpdatedPackageDescriptionImage.imageUrl(UPDATED_IMAGE_URL).activated(UPDATED_ACTIVATED);

        restPackageDescriptionImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPackageDescriptionImage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPackageDescriptionImage))
            )
            .andExpect(status().isOk());

        // Validate the PackageDescriptionImage in the database
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeUpdate);
        PackageDescriptionImage testPackageDescriptionImage = packageDescriptionImageList.get(packageDescriptionImageList.size() - 1);
        assertThat(testPackageDescriptionImage.getImageUrl()).isEqualTo(UPDATED_IMAGE_URL);
        assertThat(testPackageDescriptionImage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testPackageDescriptionImage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPackageDescriptionImage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPackageDescriptionImage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPackageDescriptionImage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPackageDescriptionImage() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionImageRepository.findAll().size();
        packageDescriptionImage.setId(count.incrementAndGet());

        // Create the PackageDescriptionImage
        PackageDescriptionImageDTO packageDescriptionImageDTO = packageDescriptionImageMapper.toDto(packageDescriptionImage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackageDescriptionImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, packageDescriptionImageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageDescriptionImage in the database
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPackageDescriptionImage() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionImageRepository.findAll().size();
        packageDescriptionImage.setId(count.incrementAndGet());

        // Create the PackageDescriptionImage
        PackageDescriptionImageDTO packageDescriptionImageDTO = packageDescriptionImageMapper.toDto(packageDescriptionImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageDescriptionImageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionImageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageDescriptionImage in the database
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPackageDescriptionImage() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionImageRepository.findAll().size();
        packageDescriptionImage.setId(count.incrementAndGet());

        // Create the PackageDescriptionImage
        PackageDescriptionImageDTO packageDescriptionImageDTO = packageDescriptionImageMapper.toDto(packageDescriptionImage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageDescriptionImageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionImageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PackageDescriptionImage in the database
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePackageDescriptionImage() throws Exception {
        // Initialize the database
        packageDescriptionImageRepository.saveAndFlush(packageDescriptionImage);

        int databaseSizeBeforeDelete = packageDescriptionImageRepository.findAll().size();

        // Delete the packageDescriptionImage
        restPackageDescriptionImageMockMvc
            .perform(delete(ENTITY_API_URL_ID, packageDescriptionImage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PackageDescriptionImage> packageDescriptionImageList = packageDescriptionImageRepository.findAll();
        assertThat(packageDescriptionImageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
