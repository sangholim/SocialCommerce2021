package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductClazzAuthor;
import com.toy.project.repository.ProductClazzAuthorRepository;
import com.toy.project.service.dto.ProductClazzAuthorDTO;
import com.toy.project.service.mapper.ProductClazzAuthorMapper;
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
 * Integration tests for the {@link ProductClazzAuthorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductClazzAuthorResourceIT {

    private static final String DEFAULT_CLASS_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CLASS_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_CALCULATION = false;
    private static final Boolean UPDATED_USE_CALCULATION = true;

    private static final Integer DEFAULT_CALCULATION = 1;
    private static final Integer UPDATED_CALCULATION = 2;

    private static final String DEFAULT_CALCULATION_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_CALCULATION_UNIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_CALCULATION_DATE = false;
    private static final Boolean UPDATED_USE_CALCULATION_DATE = true;

    private static final Instant DEFAULT_CALCULATION_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATION_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CALCULATION_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATION_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_USE_DISCOUNT = false;
    private static final Boolean UPDATED_USE_DISCOUNT = true;

    private static final Long DEFAULT_DISCOUNT_PRICE = 1L;
    private static final Long UPDATED_DISCOUNT_PRICE = 2L;

    private static final String DEFAULT_DISCOUNT_PRICE_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_DISCOUNT_PRICE_UNIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_DISCOUNT_DATE = false;
    private static final Boolean UPDATED_USE_DISCOUNT_DATE = true;

    private static final Instant DEFAULT_DISCOUNT_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISCOUNT_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_DISCOUNT_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_DISCOUNT_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/product-clazz-authors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductClazzAuthorRepository productClazzAuthorRepository;

    @Autowired
    private ProductClazzAuthorMapper productClazzAuthorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductClazzAuthorMockMvc;

    private ProductClazzAuthor productClazzAuthor;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductClazzAuthor createEntity(EntityManager em) {
        ProductClazzAuthor productClazzAuthor = new ProductClazzAuthor();
        productClazzAuthor.setClassType(DEFAULT_CLASS_TYPE);
        productClazzAuthor.setCalculation(DEFAULT_CALCULATION);
        productClazzAuthor.setCalculationUnit(DEFAULT_CALCULATION_UNIT);
        productClazzAuthor.setCalculationDateFrom(DEFAULT_CALCULATION_DATE_FROM);
        productClazzAuthor.setCalculationDateTo(DEFAULT_CALCULATION_DATE_TO);
        productClazzAuthor.setDiscountPrice(DEFAULT_DISCOUNT_PRICE);
        productClazzAuthor.setDiscountPriceUnit(DEFAULT_DISCOUNT_PRICE_UNIT);
        productClazzAuthor.setDiscountDateFrom(DEFAULT_DISCOUNT_DATE_FROM);
        productClazzAuthor.setDiscountDateTo(DEFAULT_DISCOUNT_DATE_TO);
        productClazzAuthor.setActivated(DEFAULT_ACTIVATED);
        productClazzAuthor.setCreatedBy(DEFAULT_CREATED_BY);
        productClazzAuthor.setCreatedDate(DEFAULT_CREATED_DATE);
        productClazzAuthor.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productClazzAuthor.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productClazzAuthor;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductClazzAuthor createUpdatedEntity(EntityManager em) {
        ProductClazzAuthor productClazzAuthor = new ProductClazzAuthor();
        productClazzAuthor.setClassType(UPDATED_CLASS_TYPE);
        productClazzAuthor.setCalculation(UPDATED_CALCULATION);
        productClazzAuthor.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        productClazzAuthor.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        productClazzAuthor.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        productClazzAuthor.setDiscountPrice(UPDATED_DISCOUNT_PRICE);
        productClazzAuthor.setDiscountPriceUnit(UPDATED_DISCOUNT_PRICE_UNIT);
        productClazzAuthor.setDiscountDateFrom(UPDATED_DISCOUNT_DATE_FROM);
        productClazzAuthor.setDiscountDateTo(UPDATED_DISCOUNT_DATE_TO);
        productClazzAuthor.setActivated(UPDATED_ACTIVATED);
        productClazzAuthor.setCreatedBy(UPDATED_CREATED_BY);
        productClazzAuthor.setCreatedDate(UPDATED_CREATED_DATE);
        productClazzAuthor.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productClazzAuthor.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productClazzAuthor;
    }

    @BeforeEach
    public void initTest() {
        productClazzAuthor = createEntity(em);
    }

    @Test
    @Transactional
    void createProductClazzAuthor() throws Exception {
        int databaseSizeBeforeCreate = productClazzAuthorRepository.findAll().size();
        // Create the ProductClazzAuthor
        ProductClazzAuthorDTO productClazzAuthorDTO = productClazzAuthorMapper.toDto(productClazzAuthor);
        restProductClazzAuthorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productClazzAuthorDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductClazzAuthor in the database
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeCreate + 1);
        ProductClazzAuthor testProductClazzAuthor = productClazzAuthorList.get(productClazzAuthorList.size() - 1);
        assertThat(testProductClazzAuthor.getClassType()).isEqualTo(DEFAULT_CLASS_TYPE);
        assertThat(testProductClazzAuthor.getUseCalculation()).isEqualTo(DEFAULT_USE_CALCULATION);
        assertThat(testProductClazzAuthor.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testProductClazzAuthor.getCalculationUnit()).isEqualTo(DEFAULT_CALCULATION_UNIT);
        assertThat(testProductClazzAuthor.getUseCalculationDate()).isEqualTo(DEFAULT_USE_CALCULATION_DATE);
        assertThat(testProductClazzAuthor.getCalculationDateFrom()).isEqualTo(DEFAULT_CALCULATION_DATE_FROM);
        assertThat(testProductClazzAuthor.getCalculationDateTo()).isEqualTo(DEFAULT_CALCULATION_DATE_TO);
        assertThat(testProductClazzAuthor.getUseDiscount()).isEqualTo(DEFAULT_USE_DISCOUNT);
        assertThat(testProductClazzAuthor.getDiscountPrice()).isEqualTo(DEFAULT_DISCOUNT_PRICE);
        assertThat(testProductClazzAuthor.getDiscountPriceUnit()).isEqualTo(DEFAULT_DISCOUNT_PRICE_UNIT);
        assertThat(testProductClazzAuthor.getUseDiscountDate()).isEqualTo(DEFAULT_USE_DISCOUNT_DATE);
        assertThat(testProductClazzAuthor.getDiscountDateFrom()).isEqualTo(DEFAULT_DISCOUNT_DATE_FROM);
        assertThat(testProductClazzAuthor.getDiscountDateTo()).isEqualTo(DEFAULT_DISCOUNT_DATE_TO);
        assertThat(testProductClazzAuthor.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductClazzAuthor.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductClazzAuthor.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductClazzAuthor.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductClazzAuthor.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductClazzAuthorWithExistingId() throws Exception {
        // Create the ProductClazzAuthor with an existing ID
        productClazzAuthor.setId(1L);
        ProductClazzAuthorDTO productClazzAuthorDTO = productClazzAuthorMapper.toDto(productClazzAuthor);

        int databaseSizeBeforeCreate = productClazzAuthorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductClazzAuthorMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productClazzAuthorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductClazzAuthor in the database
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductClazzAuthors() throws Exception {
        // Initialize the database
        productClazzAuthorRepository.saveAndFlush(productClazzAuthor);

        // Get all the productClazzAuthorList
        restProductClazzAuthorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productClazzAuthor.getId().intValue())))
            .andExpect(jsonPath("$.[*].classType").value(hasItem(DEFAULT_CLASS_TYPE)))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].calculationUnit").value(hasItem(DEFAULT_CALCULATION_UNIT)))
            .andExpect(jsonPath("$.[*].calculationDateFrom").value(hasItem(DEFAULT_CALCULATION_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].calculationDateTo").value(hasItem(DEFAULT_CALCULATION_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].discountPrice").value(hasItem(DEFAULT_DISCOUNT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].discountPriceUnit").value(hasItem(DEFAULT_DISCOUNT_PRICE_UNIT)))
            .andExpect(jsonPath("$.[*].discountDateFrom").value(hasItem(DEFAULT_DISCOUNT_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].discountDateTo").value(hasItem(DEFAULT_DISCOUNT_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductClazzAuthor() throws Exception {
        // Initialize the database
        productClazzAuthorRepository.saveAndFlush(productClazzAuthor);

        // Get the productClazzAuthor
        restProductClazzAuthorMockMvc
            .perform(get(ENTITY_API_URL_ID, productClazzAuthor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productClazzAuthor.getId().intValue()))
            .andExpect(jsonPath("$.classType").value(DEFAULT_CLASS_TYPE))
            .andExpect(jsonPath("$.calculation").value(DEFAULT_CALCULATION))
            .andExpect(jsonPath("$.calculationUnit").value(DEFAULT_CALCULATION_UNIT))
            .andExpect(jsonPath("$.calculationDateFrom").value(DEFAULT_CALCULATION_DATE_FROM.toString()))
            .andExpect(jsonPath("$.calculationDateTo").value(DEFAULT_CALCULATION_DATE_TO.toString()))
            .andExpect(jsonPath("$.discountPrice").value(DEFAULT_DISCOUNT_PRICE.intValue()))
            .andExpect(jsonPath("$.discountPriceUnit").value(DEFAULT_DISCOUNT_PRICE_UNIT))
            .andExpect(jsonPath("$.discountDateFrom").value(DEFAULT_DISCOUNT_DATE_FROM.toString()))
            .andExpect(jsonPath("$.discountDateTo").value(DEFAULT_DISCOUNT_DATE_TO.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductClazzAuthor() throws Exception {
        // Get the productClazzAuthor
        restProductClazzAuthorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductClazzAuthor() throws Exception {
        // Initialize the database
        productClazzAuthorRepository.saveAndFlush(productClazzAuthor);

        int databaseSizeBeforeUpdate = productClazzAuthorRepository.findAll().size();

        // Update the productClazzAuthor
        ProductClazzAuthor updatedProductClazzAuthor = productClazzAuthorRepository.findById(productClazzAuthor.getId()).get();
        // Disconnect from session so that the updates on updatedProductClazzAuthor are not directly saved in db
        em.detach(updatedProductClazzAuthor);
        updatedProductClazzAuthor.setClassType(UPDATED_CLASS_TYPE);
        updatedProductClazzAuthor.setCalculation(UPDATED_CALCULATION);
        updatedProductClazzAuthor.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        updatedProductClazzAuthor.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        updatedProductClazzAuthor.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        updatedProductClazzAuthor.setDiscountPrice(UPDATED_DISCOUNT_PRICE);
        updatedProductClazzAuthor.setDiscountPriceUnit(UPDATED_DISCOUNT_PRICE_UNIT);
        updatedProductClazzAuthor.setDiscountDateFrom(UPDATED_DISCOUNT_DATE_FROM);
        updatedProductClazzAuthor.setDiscountDateTo(UPDATED_DISCOUNT_DATE_TO);
        updatedProductClazzAuthor.setActivated(UPDATED_ACTIVATED);
        updatedProductClazzAuthor.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductClazzAuthor.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductClazzAuthor.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductClazzAuthor.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductClazzAuthorDTO productClazzAuthorDTO = productClazzAuthorMapper.toDto(updatedProductClazzAuthor);

        restProductClazzAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productClazzAuthorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productClazzAuthorDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductClazzAuthor in the database
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeUpdate);
        ProductClazzAuthor testProductClazzAuthor = productClazzAuthorList.get(productClazzAuthorList.size() - 1);
        assertThat(testProductClazzAuthor.getClassType()).isEqualTo(UPDATED_CLASS_TYPE);
        assertThat(testProductClazzAuthor.getUseCalculation()).isEqualTo(UPDATED_USE_CALCULATION);
        assertThat(testProductClazzAuthor.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProductClazzAuthor.getCalculationUnit()).isEqualTo(UPDATED_CALCULATION_UNIT);
        assertThat(testProductClazzAuthor.getUseCalculationDate()).isEqualTo(UPDATED_USE_CALCULATION_DATE);
        assertThat(testProductClazzAuthor.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testProductClazzAuthor.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProductClazzAuthor.getUseDiscount()).isEqualTo(UPDATED_USE_DISCOUNT);
        assertThat(testProductClazzAuthor.getDiscountPrice()).isEqualTo(UPDATED_DISCOUNT_PRICE);
        assertThat(testProductClazzAuthor.getDiscountPriceUnit()).isEqualTo(UPDATED_DISCOUNT_PRICE_UNIT);
        assertThat(testProductClazzAuthor.getUseDiscountDate()).isEqualTo(UPDATED_USE_DISCOUNT_DATE);
        assertThat(testProductClazzAuthor.getDiscountDateFrom()).isEqualTo(UPDATED_DISCOUNT_DATE_FROM);
        assertThat(testProductClazzAuthor.getDiscountDateTo()).isEqualTo(UPDATED_DISCOUNT_DATE_TO);
        assertThat(testProductClazzAuthor.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductClazzAuthor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductClazzAuthor.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductClazzAuthor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductClazzAuthor.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductClazzAuthor() throws Exception {
        int databaseSizeBeforeUpdate = productClazzAuthorRepository.findAll().size();
        productClazzAuthor.setId(count.incrementAndGet());

        // Create the ProductClazzAuthor
        ProductClazzAuthorDTO productClazzAuthorDTO = productClazzAuthorMapper.toDto(productClazzAuthor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductClazzAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productClazzAuthorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productClazzAuthorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductClazzAuthor in the database
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductClazzAuthor() throws Exception {
        int databaseSizeBeforeUpdate = productClazzAuthorRepository.findAll().size();
        productClazzAuthor.setId(count.incrementAndGet());

        // Create the ProductClazzAuthor
        ProductClazzAuthorDTO productClazzAuthorDTO = productClazzAuthorMapper.toDto(productClazzAuthor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductClazzAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productClazzAuthorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductClazzAuthor in the database
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductClazzAuthor() throws Exception {
        int databaseSizeBeforeUpdate = productClazzAuthorRepository.findAll().size();
        productClazzAuthor.setId(count.incrementAndGet());

        // Create the ProductClazzAuthor
        ProductClazzAuthorDTO productClazzAuthorDTO = productClazzAuthorMapper.toDto(productClazzAuthor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductClazzAuthorMockMvc
            .perform(
                put(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productClazzAuthorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductClazzAuthor in the database
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductClazzAuthorWithPatch() throws Exception {
        // Initialize the database
        productClazzAuthorRepository.saveAndFlush(productClazzAuthor);

        int databaseSizeBeforeUpdate = productClazzAuthorRepository.findAll().size();

        // Update the productClazzAuthor using partial update
        ProductClazzAuthor partialUpdatedProductClazzAuthor = new ProductClazzAuthor();
        partialUpdatedProductClazzAuthor.setId(productClazzAuthor.getId());

        partialUpdatedProductClazzAuthor.setClassType(UPDATED_CLASS_TYPE);
        partialUpdatedProductClazzAuthor.setCalculation(UPDATED_CALCULATION);
        partialUpdatedProductClazzAuthor.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        partialUpdatedProductClazzAuthor.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        partialUpdatedProductClazzAuthor.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        partialUpdatedProductClazzAuthor.setDiscountPrice(UPDATED_DISCOUNT_PRICE);
        partialUpdatedProductClazzAuthor.setDiscountPriceUnit(UPDATED_DISCOUNT_PRICE_UNIT);
        partialUpdatedProductClazzAuthor.setDiscountDateFrom(UPDATED_DISCOUNT_DATE_FROM);
        partialUpdatedProductClazzAuthor.setDiscountDateTo(UPDATED_DISCOUNT_DATE_TO);
        partialUpdatedProductClazzAuthor.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductClazzAuthor.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductClazzAuthor.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductClazzAuthor.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductClazzAuthor.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductClazzAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductClazzAuthor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductClazzAuthor))
            )
            .andExpect(status().isOk());

        // Validate the ProductClazzAuthor in the database
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeUpdate);
        ProductClazzAuthor testProductClazzAuthor = productClazzAuthorList.get(productClazzAuthorList.size() - 1);
        assertThat(testProductClazzAuthor.getClassType()).isEqualTo(UPDATED_CLASS_TYPE);
        assertThat(testProductClazzAuthor.getUseCalculation()).isEqualTo(DEFAULT_USE_CALCULATION);
        assertThat(testProductClazzAuthor.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProductClazzAuthor.getCalculationUnit()).isEqualTo(DEFAULT_CALCULATION_UNIT);
        assertThat(testProductClazzAuthor.getUseCalculationDate()).isEqualTo(DEFAULT_USE_CALCULATION_DATE);
        assertThat(testProductClazzAuthor.getCalculationDateFrom()).isEqualTo(DEFAULT_CALCULATION_DATE_FROM);
        assertThat(testProductClazzAuthor.getCalculationDateTo()).isEqualTo(DEFAULT_CALCULATION_DATE_TO);
        assertThat(testProductClazzAuthor.getUseDiscount()).isEqualTo(UPDATED_USE_DISCOUNT);
        assertThat(testProductClazzAuthor.getDiscountPrice()).isEqualTo(DEFAULT_DISCOUNT_PRICE);
        assertThat(testProductClazzAuthor.getDiscountPriceUnit()).isEqualTo(UPDATED_DISCOUNT_PRICE_UNIT);
        assertThat(testProductClazzAuthor.getUseDiscountDate()).isEqualTo(DEFAULT_USE_DISCOUNT_DATE);
        assertThat(testProductClazzAuthor.getDiscountDateFrom()).isEqualTo(UPDATED_DISCOUNT_DATE_FROM);
        assertThat(testProductClazzAuthor.getDiscountDateTo()).isEqualTo(UPDATED_DISCOUNT_DATE_TO);
        assertThat(testProductClazzAuthor.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductClazzAuthor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductClazzAuthor.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductClazzAuthor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductClazzAuthor.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductClazzAuthorWithPatch() throws Exception {
        // Initialize the database
        productClazzAuthorRepository.saveAndFlush(productClazzAuthor);

        int databaseSizeBeforeUpdate = productClazzAuthorRepository.findAll().size();

        // Update the productClazzAuthor using partial update
        ProductClazzAuthor partialUpdatedProductClazzAuthor = new ProductClazzAuthor();
        partialUpdatedProductClazzAuthor.setId(productClazzAuthor.getId());

        partialUpdatedProductClazzAuthor.setClassType(UPDATED_CLASS_TYPE);
        partialUpdatedProductClazzAuthor.setCalculation(UPDATED_CALCULATION);
        partialUpdatedProductClazzAuthor.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        partialUpdatedProductClazzAuthor.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        partialUpdatedProductClazzAuthor.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        partialUpdatedProductClazzAuthor.setDiscountPrice(UPDATED_DISCOUNT_PRICE);
        partialUpdatedProductClazzAuthor.setDiscountPriceUnit(UPDATED_DISCOUNT_PRICE_UNIT);
        partialUpdatedProductClazzAuthor.setDiscountDateFrom(UPDATED_DISCOUNT_DATE_FROM);
        partialUpdatedProductClazzAuthor.setDiscountDateTo(UPDATED_DISCOUNT_DATE_TO);
        partialUpdatedProductClazzAuthor.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductClazzAuthor.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductClazzAuthor.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductClazzAuthor.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductClazzAuthor.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductClazzAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductClazzAuthor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductClazzAuthor))
            )
            .andExpect(status().isOk());

        // Validate the ProductClazzAuthor in the database
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeUpdate);
        ProductClazzAuthor testProductClazzAuthor = productClazzAuthorList.get(productClazzAuthorList.size() - 1);
        assertThat(testProductClazzAuthor.getClassType()).isEqualTo(UPDATED_CLASS_TYPE);
        assertThat(testProductClazzAuthor.getUseCalculation()).isEqualTo(UPDATED_USE_CALCULATION);
        assertThat(testProductClazzAuthor.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testProductClazzAuthor.getCalculationUnit()).isEqualTo(UPDATED_CALCULATION_UNIT);
        assertThat(testProductClazzAuthor.getUseCalculationDate()).isEqualTo(UPDATED_USE_CALCULATION_DATE);
        assertThat(testProductClazzAuthor.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testProductClazzAuthor.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testProductClazzAuthor.getUseDiscount()).isEqualTo(UPDATED_USE_DISCOUNT);
        assertThat(testProductClazzAuthor.getDiscountPrice()).isEqualTo(UPDATED_DISCOUNT_PRICE);
        assertThat(testProductClazzAuthor.getDiscountPriceUnit()).isEqualTo(UPDATED_DISCOUNT_PRICE_UNIT);
        assertThat(testProductClazzAuthor.getUseDiscountDate()).isEqualTo(UPDATED_USE_DISCOUNT_DATE);
        assertThat(testProductClazzAuthor.getDiscountDateFrom()).isEqualTo(UPDATED_DISCOUNT_DATE_FROM);
        assertThat(testProductClazzAuthor.getDiscountDateTo()).isEqualTo(UPDATED_DISCOUNT_DATE_TO);
        assertThat(testProductClazzAuthor.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductClazzAuthor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductClazzAuthor.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductClazzAuthor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductClazzAuthor.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductClazzAuthor() throws Exception {
        int databaseSizeBeforeUpdate = productClazzAuthorRepository.findAll().size();
        productClazzAuthor.setId(count.incrementAndGet());

        // Create the ProductClazzAuthor
        ProductClazzAuthorDTO productClazzAuthorDTO = productClazzAuthorMapper.toDto(productClazzAuthor);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductClazzAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productClazzAuthorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productClazzAuthorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductClazzAuthor in the database
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductClazzAuthor() throws Exception {
        int databaseSizeBeforeUpdate = productClazzAuthorRepository.findAll().size();
        productClazzAuthor.setId(count.incrementAndGet());

        // Create the ProductClazzAuthor
        ProductClazzAuthorDTO productClazzAuthorDTO = productClazzAuthorMapper.toDto(productClazzAuthor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductClazzAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productClazzAuthorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductClazzAuthor in the database
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductClazzAuthor() throws Exception {
        int databaseSizeBeforeUpdate = productClazzAuthorRepository.findAll().size();
        productClazzAuthor.setId(count.incrementAndGet());

        // Create the ProductClazzAuthor
        ProductClazzAuthorDTO productClazzAuthorDTO = productClazzAuthorMapper.toDto(productClazzAuthor);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductClazzAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productClazzAuthorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductClazzAuthor in the database
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductClazzAuthor() throws Exception {
        // Initialize the database
        productClazzAuthorRepository.saveAndFlush(productClazzAuthor);

        int databaseSizeBeforeDelete = productClazzAuthorRepository.findAll().size();

        // Delete the productClazzAuthor
        restProductClazzAuthorMockMvc
            .perform(delete(ENTITY_API_URL_ID, productClazzAuthor.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductClazzAuthor> productClazzAuthorList = productClazzAuthorRepository.findAll();
        assertThat(productClazzAuthorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
