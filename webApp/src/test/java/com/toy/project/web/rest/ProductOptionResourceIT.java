package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductOption;
import com.toy.project.repository.ProductOptionRepository;
import com.toy.project.service.dto.ProductOptionDTO;
import com.toy.project.service.mapper.ProductOptionMapper;
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
 * Integration tests for the {@link ProductOptionResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductOptionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PACKAGE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESIGN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DESIGN_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_CODE = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR_NAME = "AAAAAAAAAA";
    private static final String UPDATED_COLOR_NAME = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_PACKAGE_DESCRIPTION = false;
    private static final Boolean UPDATED_USE_PACKAGE_DESCRIPTION = true;

    private static final String DEFAULT_PACKAGE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DISPLAY_RECOMMEND_PACKAGE = false;
    private static final Boolean UPDATED_DISPLAY_RECOMMEND_PACKAGE = true;

    private static final String DEFAULT_PACKAGE_THUMBNAIL_URL = "AAAAAAAAAA";
    private static final String UPDATED_PACKAGE_THUMBNAIL_URL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/product-options";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductOptionMapper productOptionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductOptionMockMvc;

    private ProductOption productOption;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOption createEntity(EntityManager em) {
        ProductOption productOption = new ProductOption();
        productOption.setName(DEFAULT_NAME);
        productOption.setPackageName(DEFAULT_PACKAGE_NAME);
        productOption.setDesignName(DEFAULT_DESIGN_NAME);
        productOption.setColorCode(DEFAULT_COLOR_CODE);
        productOption.setColorName(DEFAULT_COLOR_NAME);
        productOption.setUsePackageDescription(DEFAULT_USE_PACKAGE_DESCRIPTION);
        productOption.setPackageDescription(DEFAULT_PACKAGE_DESCRIPTION);
        productOption.setDisplayRecommendPackage(DEFAULT_DISPLAY_RECOMMEND_PACKAGE);
        productOption.setPackageThumbnailUrl(DEFAULT_PACKAGE_THUMBNAIL_URL);
        productOption.setPrice(DEFAULT_PRICE);
        productOption.setQuantity(DEFAULT_QUANTITY);
        productOption.setStatus(DEFAULT_STATUS);
        productOption.setOptionCode(DEFAULT_OPTION_CODE);
        productOption.setActivated(DEFAULT_ACTIVATED);
        productOption.setCreatedBy(DEFAULT_CREATED_BY);
        productOption.setCreatedDate(DEFAULT_CREATED_DATE);
        productOption.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productOption.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productOption;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductOption createUpdatedEntity(EntityManager em) {
        ProductOption productOption = new ProductOption();
        productOption.setName(UPDATED_NAME);
        productOption.setPackageName(UPDATED_PACKAGE_NAME);
        productOption.setDesignName(UPDATED_DESIGN_NAME);
        productOption.setColorCode(UPDATED_COLOR_CODE);
        productOption.setColorName(UPDATED_COLOR_NAME);
        productOption.setUsePackageDescription(UPDATED_USE_PACKAGE_DESCRIPTION);
        productOption.setPackageDescription(UPDATED_PACKAGE_DESCRIPTION);
        productOption.setDisplayRecommendPackage(UPDATED_DISPLAY_RECOMMEND_PACKAGE);
        productOption.setPackageThumbnailUrl(UPDATED_PACKAGE_THUMBNAIL_URL);
        productOption.setPrice(UPDATED_PRICE);
        productOption.setQuantity(UPDATED_QUANTITY);
        productOption.setStatus(UPDATED_STATUS);
        productOption.setOptionCode(UPDATED_OPTION_CODE);
        productOption.setActivated(UPDATED_ACTIVATED);
        productOption.setCreatedBy(UPDATED_CREATED_BY);
        productOption.setCreatedDate(UPDATED_CREATED_DATE);
        productOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productOption;
    }

    @BeforeEach
    public void initTest() {
        productOption = createEntity(em);
    }

    @Test
    @Transactional
    void createProductOption() throws Exception {
        int databaseSizeBeforeCreate = productOptionRepository.findAll().size();
        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);
        restProductOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeCreate + 1);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductOption.getPackageName()).isEqualTo(DEFAULT_PACKAGE_NAME);
        assertThat(testProductOption.getDesignName()).isEqualTo(DEFAULT_DESIGN_NAME);
        assertThat(testProductOption.getColorCode()).isEqualTo(DEFAULT_COLOR_CODE);
        assertThat(testProductOption.getColorName()).isEqualTo(DEFAULT_COLOR_NAME);
        assertThat(testProductOption.getUsePackageDescription()).isEqualTo(DEFAULT_USE_PACKAGE_DESCRIPTION);
        assertThat(testProductOption.getPackageDescription()).isEqualTo(DEFAULT_PACKAGE_DESCRIPTION);
        assertThat(testProductOption.getDisplayRecommendPackage()).isEqualTo(DEFAULT_DISPLAY_RECOMMEND_PACKAGE);
        assertThat(testProductOption.getPackageThumbnailUrl()).isEqualTo(DEFAULT_PACKAGE_THUMBNAIL_URL);
        assertThat(testProductOption.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testProductOption.getQuantity()).isEqualTo(DEFAULT_QUANTITY);
        assertThat(testProductOption.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductOption.getOptionCode()).isEqualTo(DEFAULT_OPTION_CODE);
        assertThat(testProductOption.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductOption.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductOption.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductOption.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductOption.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductOptionWithExistingId() throws Exception {
        // Create the ProductOption with an existing ID
        productOption.setId(1L);
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        int databaseSizeBeforeCreate = productOptionRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductOptionMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductOptions() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get all the productOptionList
        restProductOptionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productOption.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].packageName").value(hasItem(DEFAULT_PACKAGE_NAME)))
            .andExpect(jsonPath("$.[*].designName").value(hasItem(DEFAULT_DESIGN_NAME)))
            .andExpect(jsonPath("$.[*].colorCode").value(hasItem(DEFAULT_COLOR_CODE)))
            .andExpect(jsonPath("$.[*].colorName").value(hasItem(DEFAULT_COLOR_NAME)))
            .andExpect(jsonPath("$.[*].usePackageDescription").value(hasItem(DEFAULT_USE_PACKAGE_DESCRIPTION.booleanValue())))
            .andExpect(jsonPath("$.[*].packageDescription").value(hasItem(DEFAULT_PACKAGE_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].displayRecommendPackage").value(hasItem(DEFAULT_DISPLAY_RECOMMEND_PACKAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].packageThumbnailUrl").value(hasItem(DEFAULT_PACKAGE_THUMBNAIL_URL)))
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
    void getProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        // Get the productOption
        restProductOptionMockMvc
            .perform(get(ENTITY_API_URL_ID, productOption.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productOption.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.packageName").value(DEFAULT_PACKAGE_NAME))
            .andExpect(jsonPath("$.designName").value(DEFAULT_DESIGN_NAME))
            .andExpect(jsonPath("$.colorCode").value(DEFAULT_COLOR_CODE))
            .andExpect(jsonPath("$.colorName").value(DEFAULT_COLOR_NAME))
            .andExpect(jsonPath("$.usePackageDescription").value(DEFAULT_USE_PACKAGE_DESCRIPTION.booleanValue()))
            .andExpect(jsonPath("$.packageDescription").value(DEFAULT_PACKAGE_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.displayRecommendPackage").value(DEFAULT_DISPLAY_RECOMMEND_PACKAGE.booleanValue()))
            .andExpect(jsonPath("$.packageThumbnailUrl").value(DEFAULT_PACKAGE_THUMBNAIL_URL))
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
    void getNonExistingProductOption() throws Exception {
        // Get the productOption
        restProductOptionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Update the productOption
        ProductOption updatedProductOption = productOptionRepository.findById(productOption.getId()).get();
        // Disconnect from session so that the updates on updatedProductOption are not directly saved in db
        em.detach(updatedProductOption);
        updatedProductOption.setName(UPDATED_NAME);
        updatedProductOption.setPackageName(UPDATED_PACKAGE_NAME);
        updatedProductOption.setDesignName(UPDATED_DESIGN_NAME);
        updatedProductOption.setColorCode(UPDATED_COLOR_CODE);
        updatedProductOption.setColorName(UPDATED_COLOR_NAME);
        updatedProductOption.setUsePackageDescription(UPDATED_USE_PACKAGE_DESCRIPTION);
        updatedProductOption.setPackageDescription(UPDATED_PACKAGE_DESCRIPTION);
        updatedProductOption.setDisplayRecommendPackage(UPDATED_DISPLAY_RECOMMEND_PACKAGE);
        updatedProductOption.setPackageThumbnailUrl(UPDATED_PACKAGE_THUMBNAIL_URL);
        updatedProductOption.setPrice(UPDATED_PRICE);
        updatedProductOption.setQuantity(UPDATED_QUANTITY);
        updatedProductOption.setStatus(UPDATED_STATUS);
        updatedProductOption.setOptionCode(UPDATED_OPTION_CODE);
        updatedProductOption.setActivated(UPDATED_ACTIVATED);
        updatedProductOption.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductOption.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(updatedProductOption);

        restProductOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOption.getPackageName()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testProductOption.getDesignName()).isEqualTo(UPDATED_DESIGN_NAME);
        assertThat(testProductOption.getColorCode()).isEqualTo(UPDATED_COLOR_CODE);
        assertThat(testProductOption.getColorName()).isEqualTo(UPDATED_COLOR_NAME);
        assertThat(testProductOption.getUsePackageDescription()).isEqualTo(UPDATED_USE_PACKAGE_DESCRIPTION);
        assertThat(testProductOption.getPackageDescription()).isEqualTo(UPDATED_PACKAGE_DESCRIPTION);
        assertThat(testProductOption.getDisplayRecommendPackage()).isEqualTo(UPDATED_DISPLAY_RECOMMEND_PACKAGE);
        assertThat(testProductOption.getPackageThumbnailUrl()).isEqualTo(UPDATED_PACKAGE_THUMBNAIL_URL);
        assertThat(testProductOption.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductOption.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProductOption.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductOption.getOptionCode()).isEqualTo(UPDATED_OPTION_CODE);
        assertThat(testProductOption.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productOptionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductOptionWithPatch() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Update the productOption using partial update
        ProductOption partialUpdatedProductOption = new ProductOption();
        partialUpdatedProductOption.setId(productOption.getId());

        partialUpdatedProductOption.setName(UPDATED_NAME);
        partialUpdatedProductOption.setPackageName(UPDATED_PACKAGE_NAME);
        partialUpdatedProductOption.setDesignName(UPDATED_DESIGN_NAME);
        partialUpdatedProductOption.setColorCode(UPDATED_COLOR_CODE);
        partialUpdatedProductOption.setColorName(UPDATED_COLOR_NAME);
        partialUpdatedProductOption.setUsePackageDescription(UPDATED_USE_PACKAGE_DESCRIPTION);
        partialUpdatedProductOption.setPackageDescription(UPDATED_PACKAGE_DESCRIPTION);
        partialUpdatedProductOption.setDisplayRecommendPackage(UPDATED_DISPLAY_RECOMMEND_PACKAGE);
        partialUpdatedProductOption.setPackageThumbnailUrl(UPDATED_PACKAGE_THUMBNAIL_URL);
        partialUpdatedProductOption.setPrice(UPDATED_PRICE);
        partialUpdatedProductOption.setQuantity(UPDATED_QUANTITY);
        partialUpdatedProductOption.setStatus(UPDATED_STATUS);
        partialUpdatedProductOption.setOptionCode(UPDATED_OPTION_CODE);
        partialUpdatedProductOption.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductOption.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductOption.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOption))
            )
            .andExpect(status().isOk());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductOption.getPackageName()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testProductOption.getDesignName()).isEqualTo(UPDATED_DESIGN_NAME);
        assertThat(testProductOption.getColorCode()).isEqualTo(DEFAULT_COLOR_CODE);
        assertThat(testProductOption.getColorName()).isEqualTo(UPDATED_COLOR_NAME);
        assertThat(testProductOption.getUsePackageDescription()).isEqualTo(UPDATED_USE_PACKAGE_DESCRIPTION);
        assertThat(testProductOption.getPackageDescription()).isEqualTo(DEFAULT_PACKAGE_DESCRIPTION);
        assertThat(testProductOption.getDisplayRecommendPackage()).isEqualTo(UPDATED_DISPLAY_RECOMMEND_PACKAGE);
        assertThat(testProductOption.getPackageThumbnailUrl()).isEqualTo(UPDATED_PACKAGE_THUMBNAIL_URL);
        assertThat(testProductOption.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductOption.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProductOption.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testProductOption.getOptionCode()).isEqualTo(DEFAULT_OPTION_CODE);
        assertThat(testProductOption.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductOption.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductOption.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductOption.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductOption.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductOptionWithPatch() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();

        // Update the productOption using partial update
        ProductOption partialUpdatedProductOption = new ProductOption();
        partialUpdatedProductOption.setId(productOption.getId());

        partialUpdatedProductOption.setName(UPDATED_NAME);
        partialUpdatedProductOption.setPackageName(UPDATED_PACKAGE_NAME);
        partialUpdatedProductOption.setDesignName(UPDATED_DESIGN_NAME);
        partialUpdatedProductOption.setColorCode(UPDATED_COLOR_CODE);
        partialUpdatedProductOption.setColorName(UPDATED_COLOR_NAME);
        partialUpdatedProductOption.setUsePackageDescription(UPDATED_USE_PACKAGE_DESCRIPTION);
        partialUpdatedProductOption.setPackageDescription(UPDATED_PACKAGE_DESCRIPTION);
        partialUpdatedProductOption.setDisplayRecommendPackage(UPDATED_DISPLAY_RECOMMEND_PACKAGE);
        partialUpdatedProductOption.setPackageThumbnailUrl(UPDATED_PACKAGE_THUMBNAIL_URL);
        partialUpdatedProductOption.setPrice(UPDATED_PRICE);
        partialUpdatedProductOption.setQuantity(UPDATED_QUANTITY);
        partialUpdatedProductOption.setStatus(UPDATED_STATUS);
        partialUpdatedProductOption.setOptionCode(UPDATED_OPTION_CODE);
        partialUpdatedProductOption.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductOption.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductOption.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductOption.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductOption.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductOption.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductOption))
            )
            .andExpect(status().isOk());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
        ProductOption testProductOption = productOptionList.get(productOptionList.size() - 1);
        assertThat(testProductOption.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductOption.getPackageName()).isEqualTo(UPDATED_PACKAGE_NAME);
        assertThat(testProductOption.getDesignName()).isEqualTo(UPDATED_DESIGN_NAME);
        assertThat(testProductOption.getColorCode()).isEqualTo(UPDATED_COLOR_CODE);
        assertThat(testProductOption.getColorName()).isEqualTo(UPDATED_COLOR_NAME);
        assertThat(testProductOption.getUsePackageDescription()).isEqualTo(UPDATED_USE_PACKAGE_DESCRIPTION);
        assertThat(testProductOption.getPackageDescription()).isEqualTo(UPDATED_PACKAGE_DESCRIPTION);
        assertThat(testProductOption.getDisplayRecommendPackage()).isEqualTo(UPDATED_DISPLAY_RECOMMEND_PACKAGE);
        assertThat(testProductOption.getPackageThumbnailUrl()).isEqualTo(UPDATED_PACKAGE_THUMBNAIL_URL);
        assertThat(testProductOption.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testProductOption.getQuantity()).isEqualTo(UPDATED_QUANTITY);
        assertThat(testProductOption.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testProductOption.getOptionCode()).isEqualTo(UPDATED_OPTION_CODE);
        assertThat(testProductOption.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductOption.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductOption.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductOption.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductOption.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productOptionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductOption() throws Exception {
        int databaseSizeBeforeUpdate = productOptionRepository.findAll().size();
        productOption.setId(count.incrementAndGet());

        // Create the ProductOption
        ProductOptionDTO productOptionDTO = productOptionMapper.toDto(productOption);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductOptionMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productOptionDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductOption in the database
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductOption() throws Exception {
        // Initialize the database
        productOptionRepository.saveAndFlush(productOption);

        int databaseSizeBeforeDelete = productOptionRepository.findAll().size();

        // Delete the productOption
        restProductOptionMockMvc
            .perform(delete(ENTITY_API_URL_ID, productOption.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductOption> productOptionList = productOptionRepository.findAll();
        assertThat(productOptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
