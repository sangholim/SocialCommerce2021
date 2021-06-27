package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductCategory;
import com.toy.project.domain.ProductCategoryRel;
import com.toy.project.repository.ProductCategoryRepository;
import com.toy.project.service.criteria.ProductCategoryCriteria;
import com.toy.project.service.dto.ProductCategoryDTO;
import com.toy.project.service.mapper.ProductCategoryMapper;
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
 * Integration tests for the {@link ProductCategoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN = "AAAAAAAAAA";
    private static final String UPDATED_MAIN = "BBBBBBBBBB";

    private static final String DEFAULT_SUB = "AAAAAAAAAA";
    private static final String UPDATED_SUB = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;
    private static final Integer SMALLER_SORT_ORDER = 1 - 1;

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

    private static final String ENTITY_API_URL = "/api/product-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductCategoryMockMvc;

    private ProductCategory productCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategory createEntity(EntityManager em) {
        ProductCategory productCategory = new ProductCategory()
            .name(DEFAULT_NAME)
            .main(DEFAULT_MAIN)
            .sub(DEFAULT_SUB)
            .description(DEFAULT_DESCRIPTION)
            .sortOrder(DEFAULT_SORT_ORDER)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductCategory createUpdatedEntity(EntityManager em) {
        ProductCategory productCategory = new ProductCategory()
            .name(UPDATED_NAME)
            .main(UPDATED_MAIN)
            .sub(UPDATED_SUB)
            .description(UPDATED_DESCRIPTION)
            .sortOrder(UPDATED_SORT_ORDER)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productCategory;
    }

    @BeforeEach
    public void initTest() {
        productCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createProductCategory() throws Exception {
        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();
        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);
        restProductCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isCreated());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductCategory.getMain()).isEqualTo(DEFAULT_MAIN);
        assertThat(testProductCategory.getSub()).isEqualTo(DEFAULT_SUB);
        assertThat(testProductCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testProductCategory.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testProductCategory.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductCategory.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductCategory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductCategory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductCategory.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductCategoryWithExistingId() throws Exception {
        // Create the ProductCategory with an existing ID
        productCategory.setId(1L);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        int databaseSizeBeforeCreate = productCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductCategoryMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductCategories() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList
        restProductCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].main").value(hasItem(DEFAULT_MAIN)))
            .andExpect(jsonPath("$.[*].sub").value(hasItem(DEFAULT_SUB)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get the productCategory
        restProductCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, productCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.main").value(DEFAULT_MAIN))
            .andExpect(jsonPath("$.sub").value(DEFAULT_SUB))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getProductCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        Long id = productCategory.getId();

        defaultProductCategoryShouldBeFound("id.equals=" + id);
        defaultProductCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultProductCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultProductCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultProductCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultProductCategoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name equals to DEFAULT_NAME
        defaultProductCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the productCategoryList where name equals to UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name not equals to DEFAULT_NAME
        defaultProductCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the productCategoryList where name not equals to UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the productCategoryList where name equals to UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name is not null
        defaultProductCategoryShouldBeFound("name.specified=true");

        // Get all the productCategoryList where name is null
        defaultProductCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name contains DEFAULT_NAME
        defaultProductCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the productCategoryList where name contains UPDATED_NAME
        defaultProductCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where name does not contain DEFAULT_NAME
        defaultProductCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the productCategoryList where name does not contain UPDATED_NAME
        defaultProductCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByMainIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where main equals to DEFAULT_MAIN
        defaultProductCategoryShouldBeFound("main.equals=" + DEFAULT_MAIN);

        // Get all the productCategoryList where main equals to UPDATED_MAIN
        defaultProductCategoryShouldNotBeFound("main.equals=" + UPDATED_MAIN);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByMainIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where main not equals to DEFAULT_MAIN
        defaultProductCategoryShouldNotBeFound("main.notEquals=" + DEFAULT_MAIN);

        // Get all the productCategoryList where main not equals to UPDATED_MAIN
        defaultProductCategoryShouldBeFound("main.notEquals=" + UPDATED_MAIN);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByMainIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where main in DEFAULT_MAIN or UPDATED_MAIN
        defaultProductCategoryShouldBeFound("main.in=" + DEFAULT_MAIN + "," + UPDATED_MAIN);

        // Get all the productCategoryList where main equals to UPDATED_MAIN
        defaultProductCategoryShouldNotBeFound("main.in=" + UPDATED_MAIN);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByMainIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where main is not null
        defaultProductCategoryShouldBeFound("main.specified=true");

        // Get all the productCategoryList where main is null
        defaultProductCategoryShouldNotBeFound("main.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoriesByMainContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where main contains DEFAULT_MAIN
        defaultProductCategoryShouldBeFound("main.contains=" + DEFAULT_MAIN);

        // Get all the productCategoryList where main contains UPDATED_MAIN
        defaultProductCategoryShouldNotBeFound("main.contains=" + UPDATED_MAIN);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByMainNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where main does not contain DEFAULT_MAIN
        defaultProductCategoryShouldNotBeFound("main.doesNotContain=" + DEFAULT_MAIN);

        // Get all the productCategoryList where main does not contain UPDATED_MAIN
        defaultProductCategoryShouldBeFound("main.doesNotContain=" + UPDATED_MAIN);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySubIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sub equals to DEFAULT_SUB
        defaultProductCategoryShouldBeFound("sub.equals=" + DEFAULT_SUB);

        // Get all the productCategoryList where sub equals to UPDATED_SUB
        defaultProductCategoryShouldNotBeFound("sub.equals=" + UPDATED_SUB);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySubIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sub not equals to DEFAULT_SUB
        defaultProductCategoryShouldNotBeFound("sub.notEquals=" + DEFAULT_SUB);

        // Get all the productCategoryList where sub not equals to UPDATED_SUB
        defaultProductCategoryShouldBeFound("sub.notEquals=" + UPDATED_SUB);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySubIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sub in DEFAULT_SUB or UPDATED_SUB
        defaultProductCategoryShouldBeFound("sub.in=" + DEFAULT_SUB + "," + UPDATED_SUB);

        // Get all the productCategoryList where sub equals to UPDATED_SUB
        defaultProductCategoryShouldNotBeFound("sub.in=" + UPDATED_SUB);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySubIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sub is not null
        defaultProductCategoryShouldBeFound("sub.specified=true");

        // Get all the productCategoryList where sub is null
        defaultProductCategoryShouldNotBeFound("sub.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySubContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sub contains DEFAULT_SUB
        defaultProductCategoryShouldBeFound("sub.contains=" + DEFAULT_SUB);

        // Get all the productCategoryList where sub contains UPDATED_SUB
        defaultProductCategoryShouldNotBeFound("sub.contains=" + UPDATED_SUB);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySubNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sub does not contain DEFAULT_SUB
        defaultProductCategoryShouldNotBeFound("sub.doesNotContain=" + DEFAULT_SUB);

        // Get all the productCategoryList where sub does not contain UPDATED_SUB
        defaultProductCategoryShouldBeFound("sub.doesNotContain=" + UPDATED_SUB);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySortOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder equals to DEFAULT_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.equals=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder equals to UPDATED_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.equals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySortOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder not equals to DEFAULT_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.notEquals=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder not equals to UPDATED_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.notEquals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySortOrderIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder in DEFAULT_SORT_ORDER or UPDATED_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.in=" + DEFAULT_SORT_ORDER + "," + UPDATED_SORT_ORDER);

        // Get all the productCategoryList where sortOrder equals to UPDATED_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.in=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySortOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder is not null
        defaultProductCategoryShouldBeFound("sortOrder.specified=true");

        // Get all the productCategoryList where sortOrder is null
        defaultProductCategoryShouldNotBeFound("sortOrder.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySortOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder is greater than or equal to DEFAULT_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.greaterThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder is greater than or equal to UPDATED_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.greaterThanOrEqual=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySortOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder is less than or equal to DEFAULT_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.lessThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder is less than or equal to SMALLER_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.lessThanOrEqual=" + SMALLER_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySortOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder is less than DEFAULT_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.lessThan=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder is less than UPDATED_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.lessThan=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllProductCategoriesBySortOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where sortOrder is greater than DEFAULT_SORT_ORDER
        defaultProductCategoryShouldNotBeFound("sortOrder.greaterThan=" + DEFAULT_SORT_ORDER);

        // Get all the productCategoryList where sortOrder is greater than SMALLER_SORT_ORDER
        defaultProductCategoryShouldBeFound("sortOrder.greaterThan=" + SMALLER_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activated equals to DEFAULT_ACTIVATED
        defaultProductCategoryShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the productCategoryList where activated equals to UPDATED_ACTIVATED
        defaultProductCategoryShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activated not equals to DEFAULT_ACTIVATED
        defaultProductCategoryShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the productCategoryList where activated not equals to UPDATED_ACTIVATED
        defaultProductCategoryShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultProductCategoryShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the productCategoryList where activated equals to UPDATED_ACTIVATED
        defaultProductCategoryShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where activated is not null
        defaultProductCategoryShouldBeFound("activated.specified=true");

        // Get all the productCategoryList where activated is null
        defaultProductCategoryShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoriesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where createdBy equals to DEFAULT_CREATED_BY
        defaultProductCategoryShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the productCategoryList where createdBy equals to UPDATED_CREATED_BY
        defaultProductCategoryShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where createdBy not equals to DEFAULT_CREATED_BY
        defaultProductCategoryShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the productCategoryList where createdBy not equals to UPDATED_CREATED_BY
        defaultProductCategoryShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultProductCategoryShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the productCategoryList where createdBy equals to UPDATED_CREATED_BY
        defaultProductCategoryShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where createdBy is not null
        defaultProductCategoryShouldBeFound("createdBy.specified=true");

        // Get all the productCategoryList where createdBy is null
        defaultProductCategoryShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoriesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where createdBy contains DEFAULT_CREATED_BY
        defaultProductCategoryShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the productCategoryList where createdBy contains UPDATED_CREATED_BY
        defaultProductCategoryShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where createdBy does not contain DEFAULT_CREATED_BY
        defaultProductCategoryShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the productCategoryList where createdBy does not contain UPDATED_CREATED_BY
        defaultProductCategoryShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where createdDate equals to DEFAULT_CREATED_DATE
        defaultProductCategoryShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the productCategoryList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductCategoryShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultProductCategoryShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the productCategoryList where createdDate not equals to UPDATED_CREATED_DATE
        defaultProductCategoryShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultProductCategoryShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the productCategoryList where createdDate equals to UPDATED_CREATED_DATE
        defaultProductCategoryShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where createdDate is not null
        defaultProductCategoryShouldBeFound("createdDate.specified=true");

        // Get all the productCategoryList where createdDate is null
        defaultProductCategoryShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoriesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductCategoryShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productCategoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultProductCategoryShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productCategoryList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the productCategoryList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where lastModifiedBy is not null
        defaultProductCategoryShouldBeFound("lastModifiedBy.specified=true");

        // Get all the productCategoryList where lastModifiedBy is null
        defaultProductCategoryShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoriesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultProductCategoryShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productCategoryList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultProductCategoryShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the productCategoryList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultProductCategoryShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductCategoryShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productCategoryList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductCategoryShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultProductCategoryShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the productCategoryList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductCategoryShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultProductCategoryShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the productCategoryList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultProductCategoryShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllProductCategoriesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        // Get all the productCategoryList where lastModifiedDate is not null
        defaultProductCategoryShouldBeFound("lastModifiedDate.specified=true");

        // Get all the productCategoryList where lastModifiedDate is null
        defaultProductCategoryShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllProductCategoriesByProductCategoryRelIsEqualToSomething() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);
        ProductCategoryRel productCategoryRel = ProductCategoryRelResourceIT.createEntity(em);
        em.persist(productCategoryRel);
        em.flush();
        productCategory.addProductCategoryRel(productCategoryRel);
        productCategoryRepository.saveAndFlush(productCategory);
        Long productCategoryRelId = productCategoryRel.getId();

        // Get all the productCategoryList where productCategoryRel equals to productCategoryRelId
        defaultProductCategoryShouldBeFound("productCategoryRelId.equals=" + productCategoryRelId);

        // Get all the productCategoryList where productCategoryRel equals to (productCategoryRelId + 1)
        defaultProductCategoryShouldNotBeFound("productCategoryRelId.equals=" + (productCategoryRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultProductCategoryShouldBeFound(String filter) throws Exception {
        restProductCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].main").value(hasItem(DEFAULT_MAIN)))
            .andExpect(jsonPath("$.[*].sub").value(hasItem(DEFAULT_SUB)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restProductCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultProductCategoryShouldNotBeFound(String filter) throws Exception {
        restProductCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restProductCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingProductCategory() throws Exception {
        // Get the productCategory
        restProductCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory
        ProductCategory updatedProductCategory = productCategoryRepository.findById(productCategory.getId()).get();
        // Disconnect from session so that the updates on updatedProductCategory are not directly saved in db
        em.detach(updatedProductCategory);
        updatedProductCategory
            .name(UPDATED_NAME)
            .main(UPDATED_MAIN)
            .sub(UPDATED_SUB)
            .description(UPDATED_DESCRIPTION)
            .sortOrder(UPDATED_SORT_ORDER)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(updatedProductCategory);

        restProductCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategory.getMain()).isEqualTo(UPDATED_MAIN);
        assertThat(testProductCategory.getSub()).isEqualTo(UPDATED_SUB);
        assertThat(testProductCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductCategory.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testProductCategory.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductCategory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductCategory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductCategory.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductCategoryWithPatch() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory using partial update
        ProductCategory partialUpdatedProductCategory = new ProductCategory();
        partialUpdatedProductCategory.setId(productCategory.getId());

        partialUpdatedProductCategory.description(UPDATED_DESCRIPTION).sortOrder(UPDATED_SORT_ORDER).createdBy(UPDATED_CREATED_BY);

        restProductCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductCategory))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testProductCategory.getMain()).isEqualTo(DEFAULT_MAIN);
        assertThat(testProductCategory.getSub()).isEqualTo(DEFAULT_SUB);
        assertThat(testProductCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductCategory.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testProductCategory.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductCategory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategory.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductCategory.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductCategory.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductCategoryWithPatch() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();

        // Update the productCategory using partial update
        ProductCategory partialUpdatedProductCategory = new ProductCategory();
        partialUpdatedProductCategory.setId(productCategory.getId());

        partialUpdatedProductCategory
            .name(UPDATED_NAME)
            .main(UPDATED_MAIN)
            .sub(UPDATED_SUB)
            .description(UPDATED_DESCRIPTION)
            .sortOrder(UPDATED_SORT_ORDER)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductCategory))
            )
            .andExpect(status().isOk());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
        ProductCategory testProductCategory = productCategoryList.get(productCategoryList.size() - 1);
        assertThat(testProductCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testProductCategory.getMain()).isEqualTo(UPDATED_MAIN);
        assertThat(testProductCategory.getSub()).isEqualTo(UPDATED_SUB);
        assertThat(testProductCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testProductCategory.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testProductCategory.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductCategory.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductCategory.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductCategory.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductCategory.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductCategory() throws Exception {
        int databaseSizeBeforeUpdate = productCategoryRepository.findAll().size();
        productCategory.setId(count.incrementAndGet());

        // Create the ProductCategory
        ProductCategoryDTO productCategoryDTO = productCategoryMapper.toDto(productCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductCategory in the database
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductCategory() throws Exception {
        // Initialize the database
        productCategoryRepository.saveAndFlush(productCategory);

        int databaseSizeBeforeDelete = productCategoryRepository.findAll().size();

        // Delete the productCategory
        restProductCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, productCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductCategory> productCategoryList = productCategoryRepository.findAll();
        assertThat(productCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
