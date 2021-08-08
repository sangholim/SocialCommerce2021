package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductAddOption;
import com.toy.project.repository.ProductAddOptionRepository;
import com.toy.project.service.dto.ProductAddOptionDTO;
import com.toy.project.service.mapper.ProductAddOptionMapper;
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
 * Integration tests for the {@link ProductAddOptionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductAddOptionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final String DEFAULT_PRICE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE = "BBBBBBBBBB";

    private static final Long DEFAULT_QUANTITY = 1L;
    private static final Long UPDATED_QUANTITY = 2L;

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_OPTION_CODE = "AAAAAAAAAA";
    private static final String UPDATED_OPTION_CODE = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-add-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductAddOptionRepository productAddOptionRepository;

    @Autowired
    private ProductAddOptionMapper productAddOptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductAddOptionMockMvc;

    private ProductAddOption productAddOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAddOption createEntity(EntityManager em) {
        ProductAddOption productAddOption = new ProductAddOption();
        productAddOption.setName(DEFAULT_NAME);
        productAddOption.setValue(DEFAULT_VALUE);
        productAddOption.setPrice(DEFAULT_PRICE);
        productAddOption.setQuantity(DEFAULT_QUANTITY);
        productAddOption.setStatus(DEFAULT_STATUS);
        productAddOption.setOptionCode(DEFAULT_OPTION_CODE);
        productAddOption.setActivated(DEFAULT_ACTIVATED);
        productAddOption.setCreatedBy(DEFAULT_CREATED_BY);
        productAddOption.setCreatedDate(DEFAULT_CREATED_DATE);
        productAddOption.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productAddOption.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productAddOption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductAddOption createUpdatedEntity(EntityManager em) {
        ProductAddOption productAddOption = new ProductAddOption();
        productAddOption.setName(UPDATED_NAME);
        productAddOption.setValue(UPDATED_VALUE);
        productAddOption.setPrice(UPDATED_PRICE);
        productAddOption.setQuantity(UPDATED_QUANTITY);
        productAddOption.setStatus(UPDATED_STATUS);
        productAddOption.setOptionCode(UPDATED_OPTION_CODE);
        productAddOption.setActivated(UPDATED_ACTIVATED);
        productAddOption.setCreatedBy(UPDATED_CREATED_BY);
        productAddOption.setCreatedDate(UPDATED_CREATED_DATE);
        productAddOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productAddOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productAddOption;
    }

    @BeforeEach
    public void initTest() {
        productAddOption = createEntity(em);
    }

    @Test
    @Transactional
    void createProductAddOption() throws Exception {
        int databaseSizeBeforeCreate = productAddOptionRepository.findAll().size();
        // Create the ProductAddOption
        ProductAddOptionDTO productAddOptionDTO = productAddOptionMapper.toDto(productAddOption);
        restProductAddOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAddOptionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductAddOption in the database
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductAddOption testProductAddOption = productAddOptionList.get(productAddOptionList.size() - 1);
        assertThat(testProductAddOption.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductAddOption.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testProductAddOption.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProductAddOption.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProductAddOption.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductAddOption.getOptionCode()).isEqualTo(DEFAULT_OPTION_CODE);
        assertThat(testProductAddOption.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductAddOption.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductAddOption.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductAddOption.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductAddOption.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductAddOptionWithExistingId() throws Exception {
        // Create the ProductAddOption with an existing ID
        productAddOption.setId(1L);
        ProductAddOptionDTO productAddOptionDTO = productAddOptionMapper.toDto(productAddOption);

        int databaseSizeBeforeCreate = productAddOptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductAddOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAddOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAddOption in the database
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductAddOptions() throws Exception {
        // Initialize the database
        productAddOptionRepository.saveAndFlush(productAddOption);

        // Get all the productAddOptionList
        restProductAddOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productAddOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE)))
            .andExpect(jsonPath("$.[*].quantity").value(hasItem(DEFAULT_QUANTITY.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].optionCode").value(hasItem(DEFAULT_OPTION_CODE)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductAddOption() throws Exception {
        // Initialize the database
        productAddOptionRepository.saveAndFlush(productAddOption);

        // Get the productAddOption
        restProductAddOptionMockMvc
            .perform(get(ENTITY_API_URL_ID, productAddOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productAddOption.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE))
            .andExpect(jsonPath("$.quantity").value(DEFAULT_QUANTITY.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.optionCode").value(DEFAULT_OPTION_CODE))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductAddOption() throws Exception {
        // Get the productAddOption
        restProductAddOptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductAddOption() throws Exception {
        // Initialize the database
        productAddOptionRepository.saveAndFlush(productAddOption);

        int databaseSizeBeforeUpdate = productAddOptionRepository.findAll().size();

        // Update the productAddOption
        ProductAddOption updatedProductAddOption = productAddOptionRepository.findById(productAddOption.getId()).get();
        // Disconnect from session so that the updates on updatedProductAddOption are not directly saved in db
        em.detach(updatedProductAddOption);
        updatedProductAddOption.setName(UPDATED_NAME);
        updatedProductAddOption.setValue(UPDATED_VALUE);
        updatedProductAddOption.setPrice(UPDATED_PRICE);
        updatedProductAddOption.setQuantity(UPDATED_QUANTITY);
        updatedProductAddOption.setStatus(UPDATED_STATUS);
        updatedProductAddOption.setOptionCode(UPDATED_OPTION_CODE);
        updatedProductAddOption.setActivated(UPDATED_ACTIVATED);
        updatedProductAddOption.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductAddOption.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductAddOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductAddOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductAddOptionDTO productAddOptionDTO = productAddOptionMapper.toDto(updatedProductAddOption);

        restProductAddOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productAddOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAddOptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductAddOption in the database
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductAddOption testProductAddOption = productAddOptionList.get(productAddOptionList.size() - 1);
        assertThat(testProductAddOption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductAddOption.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testProductAddOption.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductAddOption.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProductAddOption.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductAddOption.getOptionCode()).isEqualTo(UPDATED_OPTION_CODE);
        assertThat(testProductAddOption.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAddOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductAddOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductAddOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductAddOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductAddOption() throws Exception {
        int databaseSizeBeforeUpdate = productAddOptionRepository.findAll().size();
        productAddOption.setId(count.incrementAndGet());

        // Create the ProductAddOption
        ProductAddOptionDTO productAddOptionDTO = productAddOptionMapper.toDto(productAddOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAddOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productAddOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAddOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAddOption in the database
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductAddOption() throws Exception {
        int databaseSizeBeforeUpdate = productAddOptionRepository.findAll().size();
        productAddOption.setId(count.incrementAndGet());

        // Create the ProductAddOption
        ProductAddOptionDTO productAddOptionDTO = productAddOptionMapper.toDto(productAddOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAddOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productAddOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAddOption in the database
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductAddOption() throws Exception {
        int databaseSizeBeforeUpdate = productAddOptionRepository.findAll().size();
        productAddOption.setId(count.incrementAndGet());

        // Create the ProductAddOption
        ProductAddOptionDTO productAddOptionDTO = productAddOptionMapper.toDto(productAddOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAddOptionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productAddOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductAddOption in the database
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductAddOptionWithPatch() throws Exception {
        // Initialize the database
        productAddOptionRepository.saveAndFlush(productAddOption);

        int databaseSizeBeforeUpdate = productAddOptionRepository.findAll().size();

        // Update the productAddOption using partial update
        ProductAddOption partialUpdatedProductAddOption = new ProductAddOption();
        partialUpdatedProductAddOption.setId(productAddOption.getId());

        partialUpdatedProductAddOption.setName(UPDATED_NAME);
        partialUpdatedProductAddOption.setValue(UPDATED_VALUE);
        partialUpdatedProductAddOption.setPrice(UPDATED_PRICE);
        partialUpdatedProductAddOption.setQuantity(UPDATED_QUANTITY);
        partialUpdatedProductAddOption.setStatus(UPDATED_STATUS);
        partialUpdatedProductAddOption.setOptionCode(UPDATED_OPTION_CODE);
        partialUpdatedProductAddOption.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductAddOption.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductAddOption.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductAddOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductAddOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductAddOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductAddOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductAddOption))
            )
            .andExpect(status().isOk());

        // Validate the ProductAddOption in the database
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductAddOption testProductAddOption = productAddOptionList.get(productAddOptionList.size() - 1);
        assertThat(testProductAddOption.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductAddOption.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testProductAddOption.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProductAddOption.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProductAddOption.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductAddOption.getOptionCode()).isEqualTo(DEFAULT_OPTION_CODE);
        assertThat(testProductAddOption.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAddOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductAddOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductAddOption.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductAddOption.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductAddOptionWithPatch() throws Exception {
        // Initialize the database
        productAddOptionRepository.saveAndFlush(productAddOption);

        int databaseSizeBeforeUpdate = productAddOptionRepository.findAll().size();

        // Update the productAddOption using partial update
        ProductAddOption partialUpdatedProductAddOption = new ProductAddOption();
        partialUpdatedProductAddOption.setId(productAddOption.getId());

        partialUpdatedProductAddOption.setName(UPDATED_NAME);
        partialUpdatedProductAddOption.setValue(UPDATED_VALUE);
        partialUpdatedProductAddOption.setPrice(UPDATED_PRICE);
        partialUpdatedProductAddOption.setQuantity(UPDATED_QUANTITY);
        partialUpdatedProductAddOption.setStatus(UPDATED_STATUS);
        partialUpdatedProductAddOption.setOptionCode(UPDATED_OPTION_CODE);
        partialUpdatedProductAddOption.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductAddOption.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductAddOption.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductAddOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductAddOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductAddOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductAddOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductAddOption))
            )
            .andExpect(status().isOk());

        // Validate the ProductAddOption in the database
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductAddOption testProductAddOption = productAddOptionList.get(productAddOptionList.size() - 1);
        assertThat(testProductAddOption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductAddOption.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testProductAddOption.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductAddOption.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProductAddOption.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductAddOption.getOptionCode()).isEqualTo(UPDATED_OPTION_CODE);
        assertThat(testProductAddOption.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductAddOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductAddOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductAddOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductAddOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductAddOption() throws Exception {
        int databaseSizeBeforeUpdate = productAddOptionRepository.findAll().size();
        productAddOption.setId(count.incrementAndGet());

        // Create the ProductAddOption
        ProductAddOptionDTO productAddOptionDTO = productAddOptionMapper.toDto(productAddOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductAddOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productAddOptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAddOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAddOption in the database
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductAddOption() throws Exception {
        int databaseSizeBeforeUpdate = productAddOptionRepository.findAll().size();
        productAddOption.setId(count.incrementAndGet());

        // Create the ProductAddOption
        ProductAddOptionDTO productAddOptionDTO = productAddOptionMapper.toDto(productAddOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAddOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAddOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductAddOption in the database
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductAddOption() throws Exception {
        int databaseSizeBeforeUpdate = productAddOptionRepository.findAll().size();
        productAddOption.setId(count.incrementAndGet());

        // Create the ProductAddOption
        ProductAddOptionDTO productAddOptionDTO = productAddOptionMapper.toDto(productAddOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductAddOptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productAddOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductAddOption in the database
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductAddOption() throws Exception {
        // Initialize the database
        productAddOptionRepository.saveAndFlush(productAddOption);

        int databaseSizeBeforeDelete = productAddOptionRepository.findAll().size();

        // Delete the productAddOption
        restProductAddOptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, productAddOption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductAddOption> productAddOptionList = productAddOptionRepository.findAll();
        assertThat(productAddOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
