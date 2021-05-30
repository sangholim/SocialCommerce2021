package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.PCategory;
import com.toy.project.domain.enumeration.ProductCategoryType;
import com.toy.project.repository.PCategoryRepository;
import com.toy.project.service.criteria.PCategoryCriteria;
import com.toy.project.service.dto.PCategoryDTO;
import com.toy.project.service.mapper.PCategoryMapper;
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
 * Integration tests for the {@link PCategoryResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class PCategoryResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final ProductCategoryType DEFAULT_TYPE = ProductCategoryType.PRODUCT;
    private static final ProductCategoryType UPDATED_TYPE = ProductCategoryType.SERVICE;

    private static final String DEFAULT_CATEGORY_MAIN = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_MAIN = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_SUB = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_SUB = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Integer DEFAULT_IS_USE = 1;
    private static final Integer UPDATED_IS_USE = 2;
    private static final Integer SMALLER_IS_USE = 1 - 1;

    private static final Integer DEFAULT_SORT_ORDER = 1;
    private static final Integer UPDATED_SORT_ORDER = 2;
    private static final Integer SMALLER_SORT_ORDER = 1 - 1;

    private static final Instant DEFAULT_REGDATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REGDATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_IS_USE_DISCOUNT = 1;
    private static final Integer UPDATED_IS_USE_DISCOUNT = 2;
    private static final Integer SMALLER_IS_USE_DISCOUNT = 1 - 1;

    private static final String ENTITY_API_URL = "/api/p-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PCategoryRepository pCategoryRepository;

    @Autowired
    private PCategoryMapper pCategoryMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPCategoryMockMvc;

    private PCategory pCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PCategory createEntity(EntityManager em) {
        PCategory pCategory = new PCategory()
            .name(DEFAULT_NAME)
            .type(DEFAULT_TYPE)
            .categoryMain(DEFAULT_CATEGORY_MAIN)
            .categorySub(DEFAULT_CATEGORY_SUB)
            .description(DEFAULT_DESCRIPTION)
            .isUse(DEFAULT_IS_USE)
            .sortOrder(DEFAULT_SORT_ORDER)
            .regdate(DEFAULT_REGDATE)
            .isUseDiscount(DEFAULT_IS_USE_DISCOUNT);
        return pCategory;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PCategory createUpdatedEntity(EntityManager em) {
        PCategory pCategory = new PCategory()
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .categoryMain(UPDATED_CATEGORY_MAIN)
            .categorySub(UPDATED_CATEGORY_SUB)
            .description(UPDATED_DESCRIPTION)
            .isUse(UPDATED_IS_USE)
            .sortOrder(UPDATED_SORT_ORDER)
            .regdate(UPDATED_REGDATE)
            .isUseDiscount(UPDATED_IS_USE_DISCOUNT);
        return pCategory;
    }

    @BeforeEach
    public void initTest() {
        pCategory = createEntity(em);
    }

    @Test
    @Transactional
    void createPCategory() throws Exception {
        int databaseSizeBeforeCreate = pCategoryRepository.findAll().size();
        // Create the PCategory
        PCategoryDTO pCategoryDTO = pCategoryMapper.toDto(pCategory);
        restPCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the PCategory in the database
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        PCategory testPCategory = pCategoryList.get(pCategoryList.size() - 1);
        assertThat(testPCategory.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPCategory.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testPCategory.getCategoryMain()).isEqualTo(DEFAULT_CATEGORY_MAIN);
        assertThat(testPCategory.getCategorySub()).isEqualTo(DEFAULT_CATEGORY_SUB);
        assertThat(testPCategory.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testPCategory.getIsUse()).isEqualTo(DEFAULT_IS_USE);
        assertThat(testPCategory.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testPCategory.getRegdate()).isEqualTo(DEFAULT_REGDATE);
        assertThat(testPCategory.getIsUseDiscount()).isEqualTo(DEFAULT_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void createPCategoryWithExistingId() throws Exception {
        // Create the PCategory with an existing ID
        pCategory.setId(1L);
        PCategoryDTO pCategoryDTO = pCategoryMapper.toDto(pCategory);

        int databaseSizeBeforeCreate = pCategoryRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PCategory in the database
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPCategories() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList
        restPCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].categoryMain").value(hasItem(DEFAULT_CATEGORY_MAIN)))
            .andExpect(jsonPath("$.[*].categorySub").value(hasItem(DEFAULT_CATEGORY_SUB)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isUse").value(hasItem(DEFAULT_IS_USE)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].regdate").value(hasItem(DEFAULT_REGDATE.toString())))
            .andExpect(jsonPath("$.[*].isUseDiscount").value(hasItem(DEFAULT_IS_USE_DISCOUNT)));
    }

    @Test
    @Transactional
    void getPCategory() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get the pCategory
        restPCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, pCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(pCategory.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.categoryMain").value(DEFAULT_CATEGORY_MAIN))
            .andExpect(jsonPath("$.categorySub").value(DEFAULT_CATEGORY_SUB))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION))
            .andExpect(jsonPath("$.isUse").value(DEFAULT_IS_USE))
            .andExpect(jsonPath("$.sortOrder").value(DEFAULT_SORT_ORDER))
            .andExpect(jsonPath("$.regdate").value(DEFAULT_REGDATE.toString()))
            .andExpect(jsonPath("$.isUseDiscount").value(DEFAULT_IS_USE_DISCOUNT));
    }

    @Test
    @Transactional
    void getPCategoriesByIdFiltering() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        Long id = pCategory.getId();

        defaultPCategoryShouldBeFound("id.equals=" + id);
        defaultPCategoryShouldNotBeFound("id.notEquals=" + id);

        defaultPCategoryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPCategoryShouldNotBeFound("id.greaterThan=" + id);

        defaultPCategoryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPCategoryShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllPCategoriesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where name equals to DEFAULT_NAME
        defaultPCategoryShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the pCategoryList where name equals to UPDATED_NAME
        defaultPCategoryShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPCategoriesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where name not equals to DEFAULT_NAME
        defaultPCategoryShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the pCategoryList where name not equals to UPDATED_NAME
        defaultPCategoryShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPCategoriesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPCategoryShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the pCategoryList where name equals to UPDATED_NAME
        defaultPCategoryShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPCategoriesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where name is not null
        defaultPCategoryShouldBeFound("name.specified=true");

        // Get all the pCategoryList where name is null
        defaultPCategoryShouldNotBeFound("name.specified=false");
    }

    @Test
    @Transactional
    void getAllPCategoriesByNameContainsSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where name contains DEFAULT_NAME
        defaultPCategoryShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the pCategoryList where name contains UPDATED_NAME
        defaultPCategoryShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPCategoriesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where name does not contain DEFAULT_NAME
        defaultPCategoryShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the pCategoryList where name does not contain UPDATED_NAME
        defaultPCategoryShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    void getAllPCategoriesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where type equals to DEFAULT_TYPE
        defaultPCategoryShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the pCategoryList where type equals to UPDATED_TYPE
        defaultPCategoryShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where type not equals to DEFAULT_TYPE
        defaultPCategoryShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the pCategoryList where type not equals to UPDATED_TYPE
        defaultPCategoryShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultPCategoryShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the pCategoryList where type equals to UPDATED_TYPE
        defaultPCategoryShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where type is not null
        defaultPCategoryShouldBeFound("type.specified=true");

        // Get all the pCategoryList where type is null
        defaultPCategoryShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategoryMainIsEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categoryMain equals to DEFAULT_CATEGORY_MAIN
        defaultPCategoryShouldBeFound("categoryMain.equals=" + DEFAULT_CATEGORY_MAIN);

        // Get all the pCategoryList where categoryMain equals to UPDATED_CATEGORY_MAIN
        defaultPCategoryShouldNotBeFound("categoryMain.equals=" + UPDATED_CATEGORY_MAIN);
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategoryMainIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categoryMain not equals to DEFAULT_CATEGORY_MAIN
        defaultPCategoryShouldNotBeFound("categoryMain.notEquals=" + DEFAULT_CATEGORY_MAIN);

        // Get all the pCategoryList where categoryMain not equals to UPDATED_CATEGORY_MAIN
        defaultPCategoryShouldBeFound("categoryMain.notEquals=" + UPDATED_CATEGORY_MAIN);
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategoryMainIsInShouldWork() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categoryMain in DEFAULT_CATEGORY_MAIN or UPDATED_CATEGORY_MAIN
        defaultPCategoryShouldBeFound("categoryMain.in=" + DEFAULT_CATEGORY_MAIN + "," + UPDATED_CATEGORY_MAIN);

        // Get all the pCategoryList where categoryMain equals to UPDATED_CATEGORY_MAIN
        defaultPCategoryShouldNotBeFound("categoryMain.in=" + UPDATED_CATEGORY_MAIN);
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategoryMainIsNullOrNotNull() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categoryMain is not null
        defaultPCategoryShouldBeFound("categoryMain.specified=true");

        // Get all the pCategoryList where categoryMain is null
        defaultPCategoryShouldNotBeFound("categoryMain.specified=false");
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategoryMainContainsSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categoryMain contains DEFAULT_CATEGORY_MAIN
        defaultPCategoryShouldBeFound("categoryMain.contains=" + DEFAULT_CATEGORY_MAIN);

        // Get all the pCategoryList where categoryMain contains UPDATED_CATEGORY_MAIN
        defaultPCategoryShouldNotBeFound("categoryMain.contains=" + UPDATED_CATEGORY_MAIN);
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategoryMainNotContainsSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categoryMain does not contain DEFAULT_CATEGORY_MAIN
        defaultPCategoryShouldNotBeFound("categoryMain.doesNotContain=" + DEFAULT_CATEGORY_MAIN);

        // Get all the pCategoryList where categoryMain does not contain UPDATED_CATEGORY_MAIN
        defaultPCategoryShouldBeFound("categoryMain.doesNotContain=" + UPDATED_CATEGORY_MAIN);
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategorySubIsEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categorySub equals to DEFAULT_CATEGORY_SUB
        defaultPCategoryShouldBeFound("categorySub.equals=" + DEFAULT_CATEGORY_SUB);

        // Get all the pCategoryList where categorySub equals to UPDATED_CATEGORY_SUB
        defaultPCategoryShouldNotBeFound("categorySub.equals=" + UPDATED_CATEGORY_SUB);
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategorySubIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categorySub not equals to DEFAULT_CATEGORY_SUB
        defaultPCategoryShouldNotBeFound("categorySub.notEquals=" + DEFAULT_CATEGORY_SUB);

        // Get all the pCategoryList where categorySub not equals to UPDATED_CATEGORY_SUB
        defaultPCategoryShouldBeFound("categorySub.notEquals=" + UPDATED_CATEGORY_SUB);
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategorySubIsInShouldWork() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categorySub in DEFAULT_CATEGORY_SUB or UPDATED_CATEGORY_SUB
        defaultPCategoryShouldBeFound("categorySub.in=" + DEFAULT_CATEGORY_SUB + "," + UPDATED_CATEGORY_SUB);

        // Get all the pCategoryList where categorySub equals to UPDATED_CATEGORY_SUB
        defaultPCategoryShouldNotBeFound("categorySub.in=" + UPDATED_CATEGORY_SUB);
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategorySubIsNullOrNotNull() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categorySub is not null
        defaultPCategoryShouldBeFound("categorySub.specified=true");

        // Get all the pCategoryList where categorySub is null
        defaultPCategoryShouldNotBeFound("categorySub.specified=false");
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategorySubContainsSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categorySub contains DEFAULT_CATEGORY_SUB
        defaultPCategoryShouldBeFound("categorySub.contains=" + DEFAULT_CATEGORY_SUB);

        // Get all the pCategoryList where categorySub contains UPDATED_CATEGORY_SUB
        defaultPCategoryShouldNotBeFound("categorySub.contains=" + UPDATED_CATEGORY_SUB);
    }

    @Test
    @Transactional
    void getAllPCategoriesByCategorySubNotContainsSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where categorySub does not contain DEFAULT_CATEGORY_SUB
        defaultPCategoryShouldNotBeFound("categorySub.doesNotContain=" + DEFAULT_CATEGORY_SUB);

        // Get all the pCategoryList where categorySub does not contain UPDATED_CATEGORY_SUB
        defaultPCategoryShouldBeFound("categorySub.doesNotContain=" + UPDATED_CATEGORY_SUB);
    }

    @Test
    @Transactional
    void getAllPCategoriesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where description equals to DEFAULT_DESCRIPTION
        defaultPCategoryShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the pCategoryList where description equals to UPDATED_DESCRIPTION
        defaultPCategoryShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPCategoriesByDescriptionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where description not equals to DEFAULT_DESCRIPTION
        defaultPCategoryShouldNotBeFound("description.notEquals=" + DEFAULT_DESCRIPTION);

        // Get all the pCategoryList where description not equals to UPDATED_DESCRIPTION
        defaultPCategoryShouldBeFound("description.notEquals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPCategoriesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultPCategoryShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the pCategoryList where description equals to UPDATED_DESCRIPTION
        defaultPCategoryShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPCategoriesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where description is not null
        defaultPCategoryShouldBeFound("description.specified=true");

        // Get all the pCategoryList where description is null
        defaultPCategoryShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    void getAllPCategoriesByDescriptionContainsSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where description contains DEFAULT_DESCRIPTION
        defaultPCategoryShouldBeFound("description.contains=" + DEFAULT_DESCRIPTION);

        // Get all the pCategoryList where description contains UPDATED_DESCRIPTION
        defaultPCategoryShouldNotBeFound("description.contains=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPCategoriesByDescriptionNotContainsSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where description does not contain DEFAULT_DESCRIPTION
        defaultPCategoryShouldNotBeFound("description.doesNotContain=" + DEFAULT_DESCRIPTION);

        // Get all the pCategoryList where description does not contain UPDATED_DESCRIPTION
        defaultPCategoryShouldBeFound("description.doesNotContain=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseIsEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUse equals to DEFAULT_IS_USE
        defaultPCategoryShouldBeFound("isUse.equals=" + DEFAULT_IS_USE);

        // Get all the pCategoryList where isUse equals to UPDATED_IS_USE
        defaultPCategoryShouldNotBeFound("isUse.equals=" + UPDATED_IS_USE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUse not equals to DEFAULT_IS_USE
        defaultPCategoryShouldNotBeFound("isUse.notEquals=" + DEFAULT_IS_USE);

        // Get all the pCategoryList where isUse not equals to UPDATED_IS_USE
        defaultPCategoryShouldBeFound("isUse.notEquals=" + UPDATED_IS_USE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseIsInShouldWork() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUse in DEFAULT_IS_USE or UPDATED_IS_USE
        defaultPCategoryShouldBeFound("isUse.in=" + DEFAULT_IS_USE + "," + UPDATED_IS_USE);

        // Get all the pCategoryList where isUse equals to UPDATED_IS_USE
        defaultPCategoryShouldNotBeFound("isUse.in=" + UPDATED_IS_USE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseIsNullOrNotNull() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUse is not null
        defaultPCategoryShouldBeFound("isUse.specified=true");

        // Get all the pCategoryList where isUse is null
        defaultPCategoryShouldNotBeFound("isUse.specified=false");
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUse is greater than or equal to DEFAULT_IS_USE
        defaultPCategoryShouldBeFound("isUse.greaterThanOrEqual=" + DEFAULT_IS_USE);

        // Get all the pCategoryList where isUse is greater than or equal to UPDATED_IS_USE
        defaultPCategoryShouldNotBeFound("isUse.greaterThanOrEqual=" + UPDATED_IS_USE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUse is less than or equal to DEFAULT_IS_USE
        defaultPCategoryShouldBeFound("isUse.lessThanOrEqual=" + DEFAULT_IS_USE);

        // Get all the pCategoryList where isUse is less than or equal to SMALLER_IS_USE
        defaultPCategoryShouldNotBeFound("isUse.lessThanOrEqual=" + SMALLER_IS_USE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseIsLessThanSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUse is less than DEFAULT_IS_USE
        defaultPCategoryShouldNotBeFound("isUse.lessThan=" + DEFAULT_IS_USE);

        // Get all the pCategoryList where isUse is less than UPDATED_IS_USE
        defaultPCategoryShouldBeFound("isUse.lessThan=" + UPDATED_IS_USE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUse is greater than DEFAULT_IS_USE
        defaultPCategoryShouldNotBeFound("isUse.greaterThan=" + DEFAULT_IS_USE);

        // Get all the pCategoryList where isUse is greater than SMALLER_IS_USE
        defaultPCategoryShouldBeFound("isUse.greaterThan=" + SMALLER_IS_USE);
    }

    @Test
    @Transactional
    void getAllPCategoriesBySortOrderIsEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where sortOrder equals to DEFAULT_SORT_ORDER
        defaultPCategoryShouldBeFound("sortOrder.equals=" + DEFAULT_SORT_ORDER);

        // Get all the pCategoryList where sortOrder equals to UPDATED_SORT_ORDER
        defaultPCategoryShouldNotBeFound("sortOrder.equals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllPCategoriesBySortOrderIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where sortOrder not equals to DEFAULT_SORT_ORDER
        defaultPCategoryShouldNotBeFound("sortOrder.notEquals=" + DEFAULT_SORT_ORDER);

        // Get all the pCategoryList where sortOrder not equals to UPDATED_SORT_ORDER
        defaultPCategoryShouldBeFound("sortOrder.notEquals=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllPCategoriesBySortOrderIsInShouldWork() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where sortOrder in DEFAULT_SORT_ORDER or UPDATED_SORT_ORDER
        defaultPCategoryShouldBeFound("sortOrder.in=" + DEFAULT_SORT_ORDER + "," + UPDATED_SORT_ORDER);

        // Get all the pCategoryList where sortOrder equals to UPDATED_SORT_ORDER
        defaultPCategoryShouldNotBeFound("sortOrder.in=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllPCategoriesBySortOrderIsNullOrNotNull() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where sortOrder is not null
        defaultPCategoryShouldBeFound("sortOrder.specified=true");

        // Get all the pCategoryList where sortOrder is null
        defaultPCategoryShouldNotBeFound("sortOrder.specified=false");
    }

    @Test
    @Transactional
    void getAllPCategoriesBySortOrderIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where sortOrder is greater than or equal to DEFAULT_SORT_ORDER
        defaultPCategoryShouldBeFound("sortOrder.greaterThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the pCategoryList where sortOrder is greater than or equal to UPDATED_SORT_ORDER
        defaultPCategoryShouldNotBeFound("sortOrder.greaterThanOrEqual=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllPCategoriesBySortOrderIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where sortOrder is less than or equal to DEFAULT_SORT_ORDER
        defaultPCategoryShouldBeFound("sortOrder.lessThanOrEqual=" + DEFAULT_SORT_ORDER);

        // Get all the pCategoryList where sortOrder is less than or equal to SMALLER_SORT_ORDER
        defaultPCategoryShouldNotBeFound("sortOrder.lessThanOrEqual=" + SMALLER_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllPCategoriesBySortOrderIsLessThanSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where sortOrder is less than DEFAULT_SORT_ORDER
        defaultPCategoryShouldNotBeFound("sortOrder.lessThan=" + DEFAULT_SORT_ORDER);

        // Get all the pCategoryList where sortOrder is less than UPDATED_SORT_ORDER
        defaultPCategoryShouldBeFound("sortOrder.lessThan=" + UPDATED_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllPCategoriesBySortOrderIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where sortOrder is greater than DEFAULT_SORT_ORDER
        defaultPCategoryShouldNotBeFound("sortOrder.greaterThan=" + DEFAULT_SORT_ORDER);

        // Get all the pCategoryList where sortOrder is greater than SMALLER_SORT_ORDER
        defaultPCategoryShouldBeFound("sortOrder.greaterThan=" + SMALLER_SORT_ORDER);
    }

    @Test
    @Transactional
    void getAllPCategoriesByRegdateIsEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where regdate equals to DEFAULT_REGDATE
        defaultPCategoryShouldBeFound("regdate.equals=" + DEFAULT_REGDATE);

        // Get all the pCategoryList where regdate equals to UPDATED_REGDATE
        defaultPCategoryShouldNotBeFound("regdate.equals=" + UPDATED_REGDATE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByRegdateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where regdate not equals to DEFAULT_REGDATE
        defaultPCategoryShouldNotBeFound("regdate.notEquals=" + DEFAULT_REGDATE);

        // Get all the pCategoryList where regdate not equals to UPDATED_REGDATE
        defaultPCategoryShouldBeFound("regdate.notEquals=" + UPDATED_REGDATE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByRegdateIsInShouldWork() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where regdate in DEFAULT_REGDATE or UPDATED_REGDATE
        defaultPCategoryShouldBeFound("regdate.in=" + DEFAULT_REGDATE + "," + UPDATED_REGDATE);

        // Get all the pCategoryList where regdate equals to UPDATED_REGDATE
        defaultPCategoryShouldNotBeFound("regdate.in=" + UPDATED_REGDATE);
    }

    @Test
    @Transactional
    void getAllPCategoriesByRegdateIsNullOrNotNull() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where regdate is not null
        defaultPCategoryShouldBeFound("regdate.specified=true");

        // Get all the pCategoryList where regdate is null
        defaultPCategoryShouldNotBeFound("regdate.specified=false");
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseDiscountIsEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUseDiscount equals to DEFAULT_IS_USE_DISCOUNT
        defaultPCategoryShouldBeFound("isUseDiscount.equals=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the pCategoryList where isUseDiscount equals to UPDATED_IS_USE_DISCOUNT
        defaultPCategoryShouldNotBeFound("isUseDiscount.equals=" + UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseDiscountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUseDiscount not equals to DEFAULT_IS_USE_DISCOUNT
        defaultPCategoryShouldNotBeFound("isUseDiscount.notEquals=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the pCategoryList where isUseDiscount not equals to UPDATED_IS_USE_DISCOUNT
        defaultPCategoryShouldBeFound("isUseDiscount.notEquals=" + UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseDiscountIsInShouldWork() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUseDiscount in DEFAULT_IS_USE_DISCOUNT or UPDATED_IS_USE_DISCOUNT
        defaultPCategoryShouldBeFound("isUseDiscount.in=" + DEFAULT_IS_USE_DISCOUNT + "," + UPDATED_IS_USE_DISCOUNT);

        // Get all the pCategoryList where isUseDiscount equals to UPDATED_IS_USE_DISCOUNT
        defaultPCategoryShouldNotBeFound("isUseDiscount.in=" + UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseDiscountIsNullOrNotNull() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUseDiscount is not null
        defaultPCategoryShouldBeFound("isUseDiscount.specified=true");

        // Get all the pCategoryList where isUseDiscount is null
        defaultPCategoryShouldNotBeFound("isUseDiscount.specified=false");
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseDiscountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUseDiscount is greater than or equal to DEFAULT_IS_USE_DISCOUNT
        defaultPCategoryShouldBeFound("isUseDiscount.greaterThanOrEqual=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the pCategoryList where isUseDiscount is greater than or equal to UPDATED_IS_USE_DISCOUNT
        defaultPCategoryShouldNotBeFound("isUseDiscount.greaterThanOrEqual=" + UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseDiscountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUseDiscount is less than or equal to DEFAULT_IS_USE_DISCOUNT
        defaultPCategoryShouldBeFound("isUseDiscount.lessThanOrEqual=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the pCategoryList where isUseDiscount is less than or equal to SMALLER_IS_USE_DISCOUNT
        defaultPCategoryShouldNotBeFound("isUseDiscount.lessThanOrEqual=" + SMALLER_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseDiscountIsLessThanSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUseDiscount is less than DEFAULT_IS_USE_DISCOUNT
        defaultPCategoryShouldNotBeFound("isUseDiscount.lessThan=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the pCategoryList where isUseDiscount is less than UPDATED_IS_USE_DISCOUNT
        defaultPCategoryShouldBeFound("isUseDiscount.lessThan=" + UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void getAllPCategoriesByIsUseDiscountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        // Get all the pCategoryList where isUseDiscount is greater than DEFAULT_IS_USE_DISCOUNT
        defaultPCategoryShouldNotBeFound("isUseDiscount.greaterThan=" + DEFAULT_IS_USE_DISCOUNT);

        // Get all the pCategoryList where isUseDiscount is greater than SMALLER_IS_USE_DISCOUNT
        defaultPCategoryShouldBeFound("isUseDiscount.greaterThan=" + SMALLER_IS_USE_DISCOUNT);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPCategoryShouldBeFound(String filter) throws Exception {
        restPCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(pCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].categoryMain").value(hasItem(DEFAULT_CATEGORY_MAIN)))
            .andExpect(jsonPath("$.[*].categorySub").value(hasItem(DEFAULT_CATEGORY_SUB)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isUse").value(hasItem(DEFAULT_IS_USE)))
            .andExpect(jsonPath("$.[*].sortOrder").value(hasItem(DEFAULT_SORT_ORDER)))
            .andExpect(jsonPath("$.[*].regdate").value(hasItem(DEFAULT_REGDATE.toString())))
            .andExpect(jsonPath("$.[*].isUseDiscount").value(hasItem(DEFAULT_IS_USE_DISCOUNT)));

        // Check, that the count call also returns 1
        restPCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPCategoryShouldNotBeFound(String filter) throws Exception {
        restPCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPCategoryMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingPCategory() throws Exception {
        // Get the pCategory
        restPCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewPCategory() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        int databaseSizeBeforeUpdate = pCategoryRepository.findAll().size();

        // Update the pCategory
        PCategory updatedPCategory = pCategoryRepository.findById(pCategory.getId()).get();
        // Disconnect from session so that the updates on updatedPCategory are not directly saved in db
        em.detach(updatedPCategory);
        updatedPCategory
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .categoryMain(UPDATED_CATEGORY_MAIN)
            .categorySub(UPDATED_CATEGORY_SUB)
            .description(UPDATED_DESCRIPTION)
            .isUse(UPDATED_IS_USE)
            .sortOrder(UPDATED_SORT_ORDER)
            .regdate(UPDATED_REGDATE)
            .isUseDiscount(UPDATED_IS_USE_DISCOUNT);
        PCategoryDTO pCategoryDTO = pCategoryMapper.toDto(updatedPCategory);

        restPCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pCategoryDTO))
            )
            .andExpect(status().isOk());

        // Validate the PCategory in the database
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeUpdate);
        PCategory testPCategory = pCategoryList.get(pCategoryList.size() - 1);
        assertThat(testPCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPCategory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPCategory.getCategoryMain()).isEqualTo(UPDATED_CATEGORY_MAIN);
        assertThat(testPCategory.getCategorySub()).isEqualTo(UPDATED_CATEGORY_SUB);
        assertThat(testPCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPCategory.getIsUse()).isEqualTo(UPDATED_IS_USE);
        assertThat(testPCategory.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testPCategory.getRegdate()).isEqualTo(UPDATED_REGDATE);
        assertThat(testPCategory.getIsUseDiscount()).isEqualTo(UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void putNonExistingPCategory() throws Exception {
        int databaseSizeBeforeUpdate = pCategoryRepository.findAll().size();
        pCategory.setId(count.incrementAndGet());

        // Create the PCategory
        PCategoryDTO pCategoryDTO = pCategoryMapper.toDto(pCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, pCategoryDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PCategory in the database
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPCategory() throws Exception {
        int databaseSizeBeforeUpdate = pCategoryRepository.findAll().size();
        pCategory.setId(count.incrementAndGet());

        // Create the PCategory
        PCategoryDTO pCategoryDTO = pCategoryMapper.toDto(pCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(pCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PCategory in the database
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPCategory() throws Exception {
        int databaseSizeBeforeUpdate = pCategoryRepository.findAll().size();
        pCategory.setId(count.incrementAndGet());

        // Create the PCategory
        PCategoryDTO pCategoryDTO = pCategoryMapper.toDto(pCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(pCategoryDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PCategory in the database
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePCategoryWithPatch() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        int databaseSizeBeforeUpdate = pCategoryRepository.findAll().size();

        // Update the pCategory using partial update
        PCategory partialUpdatedPCategory = new PCategory();
        partialUpdatedPCategory.setId(pCategory.getId());

        partialUpdatedPCategory
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .categoryMain(UPDATED_CATEGORY_MAIN)
            .categorySub(UPDATED_CATEGORY_SUB)
            .description(UPDATED_DESCRIPTION)
            .isUse(UPDATED_IS_USE)
            .regdate(UPDATED_REGDATE);

        restPCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPCategory))
            )
            .andExpect(status().isOk());

        // Validate the PCategory in the database
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeUpdate);
        PCategory testPCategory = pCategoryList.get(pCategoryList.size() - 1);
        assertThat(testPCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPCategory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPCategory.getCategoryMain()).isEqualTo(UPDATED_CATEGORY_MAIN);
        assertThat(testPCategory.getCategorySub()).isEqualTo(UPDATED_CATEGORY_SUB);
        assertThat(testPCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPCategory.getIsUse()).isEqualTo(UPDATED_IS_USE);
        assertThat(testPCategory.getSortOrder()).isEqualTo(DEFAULT_SORT_ORDER);
        assertThat(testPCategory.getRegdate()).isEqualTo(UPDATED_REGDATE);
        assertThat(testPCategory.getIsUseDiscount()).isEqualTo(DEFAULT_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void fullUpdatePCategoryWithPatch() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        int databaseSizeBeforeUpdate = pCategoryRepository.findAll().size();

        // Update the pCategory using partial update
        PCategory partialUpdatedPCategory = new PCategory();
        partialUpdatedPCategory.setId(pCategory.getId());

        partialUpdatedPCategory
            .name(UPDATED_NAME)
            .type(UPDATED_TYPE)
            .categoryMain(UPDATED_CATEGORY_MAIN)
            .categorySub(UPDATED_CATEGORY_SUB)
            .description(UPDATED_DESCRIPTION)
            .isUse(UPDATED_IS_USE)
            .sortOrder(UPDATED_SORT_ORDER)
            .regdate(UPDATED_REGDATE)
            .isUseDiscount(UPDATED_IS_USE_DISCOUNT);

        restPCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPCategory))
            )
            .andExpect(status().isOk());

        // Validate the PCategory in the database
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeUpdate);
        PCategory testPCategory = pCategoryList.get(pCategoryList.size() - 1);
        assertThat(testPCategory.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPCategory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testPCategory.getCategoryMain()).isEqualTo(UPDATED_CATEGORY_MAIN);
        assertThat(testPCategory.getCategorySub()).isEqualTo(UPDATED_CATEGORY_SUB);
        assertThat(testPCategory.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testPCategory.getIsUse()).isEqualTo(UPDATED_IS_USE);
        assertThat(testPCategory.getSortOrder()).isEqualTo(UPDATED_SORT_ORDER);
        assertThat(testPCategory.getRegdate()).isEqualTo(UPDATED_REGDATE);
        assertThat(testPCategory.getIsUseDiscount()).isEqualTo(UPDATED_IS_USE_DISCOUNT);
    }

    @Test
    @Transactional
    void patchNonExistingPCategory() throws Exception {
        int databaseSizeBeforeUpdate = pCategoryRepository.findAll().size();
        pCategory.setId(count.incrementAndGet());

        // Create the PCategory
        PCategoryDTO pCategoryDTO = pCategoryMapper.toDto(pCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, pCategoryDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PCategory in the database
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPCategory() throws Exception {
        int databaseSizeBeforeUpdate = pCategoryRepository.findAll().size();
        pCategory.setId(count.incrementAndGet());

        // Create the PCategory
        PCategoryDTO pCategoryDTO = pCategoryMapper.toDto(pCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(pCategoryDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PCategory in the database
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPCategory() throws Exception {
        int databaseSizeBeforeUpdate = pCategoryRepository.findAll().size();
        pCategory.setId(count.incrementAndGet());

        // Create the PCategory
        PCategoryDTO pCategoryDTO = pCategoryMapper.toDto(pCategory);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(pCategoryDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PCategory in the database
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePCategory() throws Exception {
        // Initialize the database
        pCategoryRepository.saveAndFlush(pCategory);

        int databaseSizeBeforeDelete = pCategoryRepository.findAll().size();

        // Delete the pCategory
        restPCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, pCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PCategory> pCategoryList = pCategoryRepository.findAll();
        assertThat(pCategoryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
