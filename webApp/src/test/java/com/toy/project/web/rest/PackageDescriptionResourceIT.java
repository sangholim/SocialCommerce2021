package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.PackageDescription;
import com.toy.project.repository.PackageDescriptionRepository;
import com.toy.project.service.dto.PackageDescriptionDTO;
import com.toy.project.service.mapper.PackageDescriptionMapper;
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
 * Integration tests for the {@link PackageDescriptionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PackageDescriptionResourceIT {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/package-descriptions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PackageDescriptionRepository packageDescriptionRepository;

    @Autowired
    private PackageDescriptionMapper packageDescriptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPackageDescriptionMockMvc;

    private PackageDescription packageDescription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackageDescription createEntity(EntityManager em) {
        PackageDescription packageDescription = new PackageDescription()
            .subject(DEFAULT_SUBJECT)
            .content(DEFAULT_CONTENT)
            .activated(DEFAULT_ACTIVATED);
        return packageDescription;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PackageDescription createUpdatedEntity(EntityManager em) {
        PackageDescription packageDescription = new PackageDescription()
            .subject(UPDATED_SUBJECT)
            .content(UPDATED_CONTENT)
            .activated(UPDATED_ACTIVATED);
        return packageDescription;
    }

    @BeforeEach
    public void initTest() {
        packageDescription = createEntity(em);
    }

    @Test
    @Transactional
    void createPackageDescription() throws Exception {
        int databaseSizeBeforeCreate = packageDescriptionRepository.findAll().size();
        // Create the PackageDescription
        PackageDescriptionDTO packageDescriptionDTO = packageDescriptionMapper.toDto(packageDescription);
        restPackageDescriptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PackageDescription in the database
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        PackageDescription testPackageDescription = packageDescriptionList.get(packageDescriptionList.size() - 1);
        assertThat(testPackageDescription.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testPackageDescription.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testPackageDescription.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testPackageDescription.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPackageDescription.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPackageDescription.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPackageDescription.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createPackageDescriptionWithExistingId() throws Exception {
        // Create the PackageDescription with an existing ID
        packageDescription.setId(1L);
        PackageDescriptionDTO packageDescriptionDTO = packageDescriptionMapper.toDto(packageDescription);

        int databaseSizeBeforeCreate = packageDescriptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPackageDescriptionMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageDescription in the database
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPackageDescriptions() throws Exception {
        // Initialize the database
        packageDescriptionRepository.saveAndFlush(packageDescription);

        // Get all the packageDescriptionList
        restPackageDescriptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(packageDescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT)))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getPackageDescription() throws Exception {
        // Initialize the database
        packageDescriptionRepository.saveAndFlush(packageDescription);

        // Get the packageDescription
        restPackageDescriptionMockMvc
            .perform(get(ENTITY_API_URL_ID, packageDescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(packageDescription.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPackageDescription() throws Exception {
        // Get the packageDescription
        restPackageDescriptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPackageDescription() throws Exception {
        // Initialize the database
        packageDescriptionRepository.saveAndFlush(packageDescription);

        int databaseSizeBeforeUpdate = packageDescriptionRepository.findAll().size();

        // Update the packageDescription
        PackageDescription updatedPackageDescription = packageDescriptionRepository.findById(packageDescription.getId()).get();
        // Disconnect from session so that the updates on updatedPackageDescription are not directly saved in db
        em.detach(updatedPackageDescription);
        updatedPackageDescription.subject(UPDATED_SUBJECT).content(UPDATED_CONTENT).activated(UPDATED_ACTIVATED);
        PackageDescriptionDTO packageDescriptionDTO = packageDescriptionMapper.toDto(updatedPackageDescription);

        restPackageDescriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, packageDescriptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the PackageDescription in the database
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeUpdate);
        PackageDescription testPackageDescription = packageDescriptionList.get(packageDescriptionList.size() - 1);
        assertThat(testPackageDescription.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testPackageDescription.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPackageDescription.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testPackageDescription.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPackageDescription.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPackageDescription.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPackageDescription.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingPackageDescription() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionRepository.findAll().size();
        packageDescription.setId(count.incrementAndGet());

        // Create the PackageDescription
        PackageDescriptionDTO packageDescriptionDTO = packageDescriptionMapper.toDto(packageDescription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackageDescriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, packageDescriptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageDescription in the database
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPackageDescription() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionRepository.findAll().size();
        packageDescription.setId(count.incrementAndGet());

        // Create the PackageDescription
        PackageDescriptionDTO packageDescriptionDTO = packageDescriptionMapper.toDto(packageDescription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageDescriptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageDescription in the database
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPackageDescription() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionRepository.findAll().size();
        packageDescription.setId(count.incrementAndGet());

        // Create the PackageDescription
        PackageDescriptionDTO packageDescriptionDTO = packageDescriptionMapper.toDto(packageDescription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageDescriptionMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PackageDescription in the database
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePackageDescriptionWithPatch() throws Exception {
        // Initialize the database
        packageDescriptionRepository.saveAndFlush(packageDescription);

        int databaseSizeBeforeUpdate = packageDescriptionRepository.findAll().size();

        // Update the packageDescription using partial update
        PackageDescription partialUpdatedPackageDescription = new PackageDescription();
        partialUpdatedPackageDescription.setId(packageDescription.getId());

        partialUpdatedPackageDescription.subject(UPDATED_SUBJECT).content(UPDATED_CONTENT);

        restPackageDescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPackageDescription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPackageDescription))
            )
            .andExpect(status().isOk());

        // Validate the PackageDescription in the database
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeUpdate);
        PackageDescription testPackageDescription = packageDescriptionList.get(packageDescriptionList.size() - 1);
        assertThat(testPackageDescription.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testPackageDescription.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPackageDescription.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testPackageDescription.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testPackageDescription.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testPackageDescription.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testPackageDescription.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdatePackageDescriptionWithPatch() throws Exception {
        // Initialize the database
        packageDescriptionRepository.saveAndFlush(packageDescription);

        int databaseSizeBeforeUpdate = packageDescriptionRepository.findAll().size();

        // Update the packageDescription using partial update
        PackageDescription partialUpdatedPackageDescription = new PackageDescription();
        partialUpdatedPackageDescription.setId(packageDescription.getId());

        partialUpdatedPackageDescription.subject(UPDATED_SUBJECT).content(UPDATED_CONTENT).activated(UPDATED_ACTIVATED);

        restPackageDescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPackageDescription.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPackageDescription))
            )
            .andExpect(status().isOk());

        // Validate the PackageDescription in the database
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeUpdate);
        PackageDescription testPackageDescription = packageDescriptionList.get(packageDescriptionList.size() - 1);
        assertThat(testPackageDescription.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testPackageDescription.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testPackageDescription.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testPackageDescription.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testPackageDescription.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testPackageDescription.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testPackageDescription.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingPackageDescription() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionRepository.findAll().size();
        packageDescription.setId(count.incrementAndGet());

        // Create the PackageDescription
        PackageDescriptionDTO packageDescriptionDTO = packageDescriptionMapper.toDto(packageDescription);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPackageDescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, packageDescriptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageDescription in the database
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPackageDescription() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionRepository.findAll().size();
        packageDescription.setId(count.incrementAndGet());

        // Create the PackageDescription
        PackageDescriptionDTO packageDescriptionDTO = packageDescriptionMapper.toDto(packageDescription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageDescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PackageDescription in the database
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPackageDescription() throws Exception {
        int databaseSizeBeforeUpdate = packageDescriptionRepository.findAll().size();
        packageDescription.setId(count.incrementAndGet());

        // Create the PackageDescription
        PackageDescriptionDTO packageDescriptionDTO = packageDescriptionMapper.toDto(packageDescription);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPackageDescriptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(packageDescriptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PackageDescription in the database
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePackageDescription() throws Exception {
        // Initialize the database
        packageDescriptionRepository.saveAndFlush(packageDescription);

        int databaseSizeBeforeDelete = packageDescriptionRepository.findAll().size();

        // Delete the packageDescription
        restPackageDescriptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, packageDescription.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PackageDescription> packageDescriptionList = packageDescriptionRepository.findAll();
        assertThat(packageDescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
