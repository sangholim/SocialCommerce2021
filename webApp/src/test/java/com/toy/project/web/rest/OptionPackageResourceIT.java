package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.OptionPackage;
import com.toy.project.domain.ProductOptionPackageRel;
import com.toy.project.repository.OptionPackageRepository;
import com.toy.project.service.criteria.OptionPackageCriteria;
import com.toy.project.service.dto.OptionPackageDTO;
import com.toy.project.service.mapper.OptionPackageMapper;
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
 * Integration tests for the {@link OptionPackageResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class OptionPackageResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DESCRIPTION_USAGE = false;
    private static final Boolean UPDATED_DESCRIPTION_USAGE = true;

    private static final Boolean DEFAULT_RECOMMEND_SHOW = false;
    private static final Boolean UPDATED_RECOMMEND_SHOW = true;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_THUMBNAIL_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_THUMBNAIL_FILE_URL = "BBBBBBBBBB";

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

    private static final String ENTITY_API_URL = "/api/option-packages";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private OptionPackageRepository optionPackageRepository;

    @Autowired
    private OptionPackageMapper optionPackageMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restOptionPackageMockMvc;

    private OptionPackage optionPackage;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionPackage createEntity(EntityManager em) {
        OptionPackage optionPackage = new OptionPackage()
            .type(DEFAULT_TYPE)
            .value(DEFAULT_VALUE)
            .descriptionUsage(DEFAULT_DESCRIPTION_USAGE)
            .recommendShow(DEFAULT_RECOMMEND_SHOW)
            .description(DEFAULT_DESCRIPTION)
            .thumbnailFileUrl(DEFAULT_THUMBNAIL_FILE_URL)
            .activated(DEFAULT_ACTIVATED)
            .createdBy(DEFAULT_CREATED_BY)
            .createdDate(DEFAULT_CREATED_DATE)
            .lastModifiedBy(DEFAULT_LAST_MODIFIED_BY)
            .lastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return optionPackage;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OptionPackage createUpdatedEntity(EntityManager em) {
        OptionPackage optionPackage = new OptionPackage()
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .descriptionUsage(UPDATED_DESCRIPTION_USAGE)
            .recommendShow(UPDATED_RECOMMEND_SHOW)
            .description(UPDATED_DESCRIPTION)
            .thumbnailFileUrl(UPDATED_THUMBNAIL_FILE_URL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return optionPackage;
    }

    @BeforeEach
    public void initTest() {
        optionPackage = createEntity(em);
    }

    @Test
    @Transactional
    void createOptionPackage() throws Exception {
        int databaseSizeBeforeCreate = optionPackageRepository.findAll().size();
        // Create the OptionPackage
        OptionPackageDTO optionPackageDTO = optionPackageMapper.toDto(optionPackage);
        restOptionPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionPackageDTO))
            )
            .andExpect(status().isCreated());

        // Validate the OptionPackage in the database
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeCreate + 1);
        OptionPackage testOptionPackage = optionPackageList.get(optionPackageList.size() - 1);
        assertThat(testOptionPackage.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testOptionPackage.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testOptionPackage.getDescriptionUsage()).isEqualTo(DEFAULT_DESCRIPTION_USAGE);
        assertThat(testOptionPackage.getRecommendShow()).isEqualTo(DEFAULT_RECOMMEND_SHOW);
        assertThat(testOptionPackage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOptionPackage.getThumbnailFileUrl()).isEqualTo(DEFAULT_THUMBNAIL_FILE_URL);
        assertThat(testOptionPackage.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testOptionPackage.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testOptionPackage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOptionPackage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOptionPackage.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createOptionPackageWithExistingId() throws Exception {
        // Create the OptionPackage with an existing ID
        optionPackage.setId(1L);
        OptionPackageDTO optionPackageDTO = optionPackageMapper.toDto(optionPackage);

        int databaseSizeBeforeCreate = optionPackageRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restOptionPackageMockMvc
            .perform(
                post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionPackage in the database
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllOptionPackages() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList
        restOptionPackageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].descriptionUsage").value(hasItem(DEFAULT_DESCRIPTION_USAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].recommendShow").value(hasItem(DEFAULT_RECOMMEND_SHOW.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].thumbnailFileUrl").value(hasItem(DEFAULT_THUMBNAIL_FILE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getOptionPackage() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get the optionPackage
        restOptionPackageMockMvc
            .perform(get(ENTITY_API_URL_ID, optionPackage.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(optionPackage.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.descriptionUsage").value(DEFAULT_DESCRIPTION_USAGE.booleanValue()))
            .andExpect(jsonPath("$.recommendShow").value(DEFAULT_RECOMMEND_SHOW.booleanValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.thumbnailFileUrl").value(DEFAULT_THUMBNAIL_FILE_URL))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getOptionPackagesByIdFiltering() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        Long id = optionPackage.getId();

        defaultOptionPackageShouldBeFound("id.equals=" + id);
        defaultOptionPackageShouldNotBeFound("id.notEquals=" + id);

        defaultOptionPackageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultOptionPackageShouldNotBeFound("id.greaterThan=" + id);

        defaultOptionPackageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultOptionPackageShouldNotBeFound("id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where type equals to DEFAULT_TYPE
        defaultOptionPackageShouldBeFound("type.equals=" + DEFAULT_TYPE);

        // Get all the optionPackageList where type equals to UPDATED_TYPE
        defaultOptionPackageShouldNotBeFound("type.equals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByTypeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where type not equals to DEFAULT_TYPE
        defaultOptionPackageShouldNotBeFound("type.notEquals=" + DEFAULT_TYPE);

        // Get all the optionPackageList where type not equals to UPDATED_TYPE
        defaultOptionPackageShouldBeFound("type.notEquals=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByTypeIsInShouldWork() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where type in DEFAULT_TYPE or UPDATED_TYPE
        defaultOptionPackageShouldBeFound("type.in=" + DEFAULT_TYPE + "," + UPDATED_TYPE);

        // Get all the optionPackageList where type equals to UPDATED_TYPE
        defaultOptionPackageShouldNotBeFound("type.in=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where type is not null
        defaultOptionPackageShouldBeFound("type.specified=true");

        // Get all the optionPackageList where type is null
        defaultOptionPackageShouldNotBeFound("type.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionPackagesByTypeContainsSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where type contains DEFAULT_TYPE
        defaultOptionPackageShouldBeFound("type.contains=" + DEFAULT_TYPE);

        // Get all the optionPackageList where type contains UPDATED_TYPE
        defaultOptionPackageShouldNotBeFound("type.contains=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByTypeNotContainsSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where type does not contain DEFAULT_TYPE
        defaultOptionPackageShouldNotBeFound("type.doesNotContain=" + DEFAULT_TYPE);

        // Get all the optionPackageList where type does not contain UPDATED_TYPE
        defaultOptionPackageShouldBeFound("type.doesNotContain=" + UPDATED_TYPE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByValueIsEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where value equals to DEFAULT_VALUE
        defaultOptionPackageShouldBeFound("value.equals=" + DEFAULT_VALUE);

        // Get all the optionPackageList where value equals to UPDATED_VALUE
        defaultOptionPackageShouldNotBeFound("value.equals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByValueIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where value not equals to DEFAULT_VALUE
        defaultOptionPackageShouldNotBeFound("value.notEquals=" + DEFAULT_VALUE);

        // Get all the optionPackageList where value not equals to UPDATED_VALUE
        defaultOptionPackageShouldBeFound("value.notEquals=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByValueIsInShouldWork() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where value in DEFAULT_VALUE or UPDATED_VALUE
        defaultOptionPackageShouldBeFound("value.in=" + DEFAULT_VALUE + "," + UPDATED_VALUE);

        // Get all the optionPackageList where value equals to UPDATED_VALUE
        defaultOptionPackageShouldNotBeFound("value.in=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where value is not null
        defaultOptionPackageShouldBeFound("value.specified=true");

        // Get all the optionPackageList where value is null
        defaultOptionPackageShouldNotBeFound("value.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionPackagesByValueContainsSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where value contains DEFAULT_VALUE
        defaultOptionPackageShouldBeFound("value.contains=" + DEFAULT_VALUE);

        // Get all the optionPackageList where value contains UPDATED_VALUE
        defaultOptionPackageShouldNotBeFound("value.contains=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByValueNotContainsSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where value does not contain DEFAULT_VALUE
        defaultOptionPackageShouldNotBeFound("value.doesNotContain=" + DEFAULT_VALUE);

        // Get all the optionPackageList where value does not contain UPDATED_VALUE
        defaultOptionPackageShouldBeFound("value.doesNotContain=" + UPDATED_VALUE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByDescriptionUsageIsEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where descriptionUsage equals to DEFAULT_DESCRIPTION_USAGE
        defaultOptionPackageShouldBeFound("descriptionUsage.equals=" + DEFAULT_DESCRIPTION_USAGE);

        // Get all the optionPackageList where descriptionUsage equals to UPDATED_DESCRIPTION_USAGE
        defaultOptionPackageShouldNotBeFound("descriptionUsage.equals=" + UPDATED_DESCRIPTION_USAGE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByDescriptionUsageIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where descriptionUsage not equals to DEFAULT_DESCRIPTION_USAGE
        defaultOptionPackageShouldNotBeFound("descriptionUsage.notEquals=" + DEFAULT_DESCRIPTION_USAGE);

        // Get all the optionPackageList where descriptionUsage not equals to UPDATED_DESCRIPTION_USAGE
        defaultOptionPackageShouldBeFound("descriptionUsage.notEquals=" + UPDATED_DESCRIPTION_USAGE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByDescriptionUsageIsInShouldWork() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where descriptionUsage in DEFAULT_DESCRIPTION_USAGE or UPDATED_DESCRIPTION_USAGE
        defaultOptionPackageShouldBeFound("descriptionUsage.in=" + DEFAULT_DESCRIPTION_USAGE + "," + UPDATED_DESCRIPTION_USAGE);

        // Get all the optionPackageList where descriptionUsage equals to UPDATED_DESCRIPTION_USAGE
        defaultOptionPackageShouldNotBeFound("descriptionUsage.in=" + UPDATED_DESCRIPTION_USAGE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByDescriptionUsageIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where descriptionUsage is not null
        defaultOptionPackageShouldBeFound("descriptionUsage.specified=true");

        // Get all the optionPackageList where descriptionUsage is null
        defaultOptionPackageShouldNotBeFound("descriptionUsage.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionPackagesByRecommendShowIsEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where recommendShow equals to DEFAULT_RECOMMEND_SHOW
        defaultOptionPackageShouldBeFound("recommendShow.equals=" + DEFAULT_RECOMMEND_SHOW);

        // Get all the optionPackageList where recommendShow equals to UPDATED_RECOMMEND_SHOW
        defaultOptionPackageShouldNotBeFound("recommendShow.equals=" + UPDATED_RECOMMEND_SHOW);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByRecommendShowIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where recommendShow not equals to DEFAULT_RECOMMEND_SHOW
        defaultOptionPackageShouldNotBeFound("recommendShow.notEquals=" + DEFAULT_RECOMMEND_SHOW);

        // Get all the optionPackageList where recommendShow not equals to UPDATED_RECOMMEND_SHOW
        defaultOptionPackageShouldBeFound("recommendShow.notEquals=" + UPDATED_RECOMMEND_SHOW);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByRecommendShowIsInShouldWork() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where recommendShow in DEFAULT_RECOMMEND_SHOW or UPDATED_RECOMMEND_SHOW
        defaultOptionPackageShouldBeFound("recommendShow.in=" + DEFAULT_RECOMMEND_SHOW + "," + UPDATED_RECOMMEND_SHOW);

        // Get all the optionPackageList where recommendShow equals to UPDATED_RECOMMEND_SHOW
        defaultOptionPackageShouldNotBeFound("recommendShow.in=" + UPDATED_RECOMMEND_SHOW);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByRecommendShowIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where recommendShow is not null
        defaultOptionPackageShouldBeFound("recommendShow.specified=true");

        // Get all the optionPackageList where recommendShow is null
        defaultOptionPackageShouldNotBeFound("recommendShow.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionPackagesByThumbnailFileUrlIsEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where thumbnailFileUrl equals to DEFAULT_THUMBNAIL_FILE_URL
        defaultOptionPackageShouldBeFound("thumbnailFileUrl.equals=" + DEFAULT_THUMBNAIL_FILE_URL);

        // Get all the optionPackageList where thumbnailFileUrl equals to UPDATED_THUMBNAIL_FILE_URL
        defaultOptionPackageShouldNotBeFound("thumbnailFileUrl.equals=" + UPDATED_THUMBNAIL_FILE_URL);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByThumbnailFileUrlIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where thumbnailFileUrl not equals to DEFAULT_THUMBNAIL_FILE_URL
        defaultOptionPackageShouldNotBeFound("thumbnailFileUrl.notEquals=" + DEFAULT_THUMBNAIL_FILE_URL);

        // Get all the optionPackageList where thumbnailFileUrl not equals to UPDATED_THUMBNAIL_FILE_URL
        defaultOptionPackageShouldBeFound("thumbnailFileUrl.notEquals=" + UPDATED_THUMBNAIL_FILE_URL);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByThumbnailFileUrlIsInShouldWork() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where thumbnailFileUrl in DEFAULT_THUMBNAIL_FILE_URL or UPDATED_THUMBNAIL_FILE_URL
        defaultOptionPackageShouldBeFound("thumbnailFileUrl.in=" + DEFAULT_THUMBNAIL_FILE_URL + "," + UPDATED_THUMBNAIL_FILE_URL);

        // Get all the optionPackageList where thumbnailFileUrl equals to UPDATED_THUMBNAIL_FILE_URL
        defaultOptionPackageShouldNotBeFound("thumbnailFileUrl.in=" + UPDATED_THUMBNAIL_FILE_URL);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByThumbnailFileUrlIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where thumbnailFileUrl is not null
        defaultOptionPackageShouldBeFound("thumbnailFileUrl.specified=true");

        // Get all the optionPackageList where thumbnailFileUrl is null
        defaultOptionPackageShouldNotBeFound("thumbnailFileUrl.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionPackagesByThumbnailFileUrlContainsSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where thumbnailFileUrl contains DEFAULT_THUMBNAIL_FILE_URL
        defaultOptionPackageShouldBeFound("thumbnailFileUrl.contains=" + DEFAULT_THUMBNAIL_FILE_URL);

        // Get all the optionPackageList where thumbnailFileUrl contains UPDATED_THUMBNAIL_FILE_URL
        defaultOptionPackageShouldNotBeFound("thumbnailFileUrl.contains=" + UPDATED_THUMBNAIL_FILE_URL);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByThumbnailFileUrlNotContainsSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where thumbnailFileUrl does not contain DEFAULT_THUMBNAIL_FILE_URL
        defaultOptionPackageShouldNotBeFound("thumbnailFileUrl.doesNotContain=" + DEFAULT_THUMBNAIL_FILE_URL);

        // Get all the optionPackageList where thumbnailFileUrl does not contain UPDATED_THUMBNAIL_FILE_URL
        defaultOptionPackageShouldBeFound("thumbnailFileUrl.doesNotContain=" + UPDATED_THUMBNAIL_FILE_URL);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByActivatedIsEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where activated equals to DEFAULT_ACTIVATED
        defaultOptionPackageShouldBeFound("activated.equals=" + DEFAULT_ACTIVATED);

        // Get all the optionPackageList where activated equals to UPDATED_ACTIVATED
        defaultOptionPackageShouldNotBeFound("activated.equals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByActivatedIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where activated not equals to DEFAULT_ACTIVATED
        defaultOptionPackageShouldNotBeFound("activated.notEquals=" + DEFAULT_ACTIVATED);

        // Get all the optionPackageList where activated not equals to UPDATED_ACTIVATED
        defaultOptionPackageShouldBeFound("activated.notEquals=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByActivatedIsInShouldWork() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where activated in DEFAULT_ACTIVATED or UPDATED_ACTIVATED
        defaultOptionPackageShouldBeFound("activated.in=" + DEFAULT_ACTIVATED + "," + UPDATED_ACTIVATED);

        // Get all the optionPackageList where activated equals to UPDATED_ACTIVATED
        defaultOptionPackageShouldNotBeFound("activated.in=" + UPDATED_ACTIVATED);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByActivatedIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where activated is not null
        defaultOptionPackageShouldBeFound("activated.specified=true");

        // Get all the optionPackageList where activated is null
        defaultOptionPackageShouldNotBeFound("activated.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionPackagesByCreatedByIsEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where createdBy equals to DEFAULT_CREATED_BY
        defaultOptionPackageShouldBeFound("createdBy.equals=" + DEFAULT_CREATED_BY);

        // Get all the optionPackageList where createdBy equals to UPDATED_CREATED_BY
        defaultOptionPackageShouldNotBeFound("createdBy.equals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByCreatedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where createdBy not equals to DEFAULT_CREATED_BY
        defaultOptionPackageShouldNotBeFound("createdBy.notEquals=" + DEFAULT_CREATED_BY);

        // Get all the optionPackageList where createdBy not equals to UPDATED_CREATED_BY
        defaultOptionPackageShouldBeFound("createdBy.notEquals=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByCreatedByIsInShouldWork() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where createdBy in DEFAULT_CREATED_BY or UPDATED_CREATED_BY
        defaultOptionPackageShouldBeFound("createdBy.in=" + DEFAULT_CREATED_BY + "," + UPDATED_CREATED_BY);

        // Get all the optionPackageList where createdBy equals to UPDATED_CREATED_BY
        defaultOptionPackageShouldNotBeFound("createdBy.in=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByCreatedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where createdBy is not null
        defaultOptionPackageShouldBeFound("createdBy.specified=true");

        // Get all the optionPackageList where createdBy is null
        defaultOptionPackageShouldNotBeFound("createdBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionPackagesByCreatedByContainsSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where createdBy contains DEFAULT_CREATED_BY
        defaultOptionPackageShouldBeFound("createdBy.contains=" + DEFAULT_CREATED_BY);

        // Get all the optionPackageList where createdBy contains UPDATED_CREATED_BY
        defaultOptionPackageShouldNotBeFound("createdBy.contains=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByCreatedByNotContainsSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where createdBy does not contain DEFAULT_CREATED_BY
        defaultOptionPackageShouldNotBeFound("createdBy.doesNotContain=" + DEFAULT_CREATED_BY);

        // Get all the optionPackageList where createdBy does not contain UPDATED_CREATED_BY
        defaultOptionPackageShouldBeFound("createdBy.doesNotContain=" + UPDATED_CREATED_BY);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where createdDate equals to DEFAULT_CREATED_DATE
        defaultOptionPackageShouldBeFound("createdDate.equals=" + DEFAULT_CREATED_DATE);

        // Get all the optionPackageList where createdDate equals to UPDATED_CREATED_DATE
        defaultOptionPackageShouldNotBeFound("createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByCreatedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where createdDate not equals to DEFAULT_CREATED_DATE
        defaultOptionPackageShouldNotBeFound("createdDate.notEquals=" + DEFAULT_CREATED_DATE);

        // Get all the optionPackageList where createdDate not equals to UPDATED_CREATED_DATE
        defaultOptionPackageShouldBeFound("createdDate.notEquals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where createdDate in DEFAULT_CREATED_DATE or UPDATED_CREATED_DATE
        defaultOptionPackageShouldBeFound("createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE);

        // Get all the optionPackageList where createdDate equals to UPDATED_CREATED_DATE
        defaultOptionPackageShouldNotBeFound("createdDate.in=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where createdDate is not null
        defaultOptionPackageShouldBeFound("createdDate.specified=true");

        // Get all the optionPackageList where createdDate is null
        defaultOptionPackageShouldNotBeFound("createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionPackagesByLastModifiedByIsEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where lastModifiedBy equals to DEFAULT_LAST_MODIFIED_BY
        defaultOptionPackageShouldBeFound("lastModifiedBy.equals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionPackageList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOptionPackageShouldNotBeFound("lastModifiedBy.equals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByLastModifiedByIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where lastModifiedBy not equals to DEFAULT_LAST_MODIFIED_BY
        defaultOptionPackageShouldNotBeFound("lastModifiedBy.notEquals=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionPackageList where lastModifiedBy not equals to UPDATED_LAST_MODIFIED_BY
        defaultOptionPackageShouldBeFound("lastModifiedBy.notEquals=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByLastModifiedByIsInShouldWork() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where lastModifiedBy in DEFAULT_LAST_MODIFIED_BY or UPDATED_LAST_MODIFIED_BY
        defaultOptionPackageShouldBeFound("lastModifiedBy.in=" + DEFAULT_LAST_MODIFIED_BY + "," + UPDATED_LAST_MODIFIED_BY);

        // Get all the optionPackageList where lastModifiedBy equals to UPDATED_LAST_MODIFIED_BY
        defaultOptionPackageShouldNotBeFound("lastModifiedBy.in=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByLastModifiedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where lastModifiedBy is not null
        defaultOptionPackageShouldBeFound("lastModifiedBy.specified=true");

        // Get all the optionPackageList where lastModifiedBy is null
        defaultOptionPackageShouldNotBeFound("lastModifiedBy.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionPackagesByLastModifiedByContainsSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where lastModifiedBy contains DEFAULT_LAST_MODIFIED_BY
        defaultOptionPackageShouldBeFound("lastModifiedBy.contains=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionPackageList where lastModifiedBy contains UPDATED_LAST_MODIFIED_BY
        defaultOptionPackageShouldNotBeFound("lastModifiedBy.contains=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByLastModifiedByNotContainsSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where lastModifiedBy does not contain DEFAULT_LAST_MODIFIED_BY
        defaultOptionPackageShouldNotBeFound("lastModifiedBy.doesNotContain=" + DEFAULT_LAST_MODIFIED_BY);

        // Get all the optionPackageList where lastModifiedBy does not contain UPDATED_LAST_MODIFIED_BY
        defaultOptionPackageShouldBeFound("lastModifiedBy.doesNotContain=" + UPDATED_LAST_MODIFIED_BY);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByLastModifiedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where lastModifiedDate equals to DEFAULT_LAST_MODIFIED_DATE
        defaultOptionPackageShouldBeFound("lastModifiedDate.equals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the optionPackageList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultOptionPackageShouldNotBeFound("lastModifiedDate.equals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByLastModifiedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where lastModifiedDate not equals to DEFAULT_LAST_MODIFIED_DATE
        defaultOptionPackageShouldNotBeFound("lastModifiedDate.notEquals=" + DEFAULT_LAST_MODIFIED_DATE);

        // Get all the optionPackageList where lastModifiedDate not equals to UPDATED_LAST_MODIFIED_DATE
        defaultOptionPackageShouldBeFound("lastModifiedDate.notEquals=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByLastModifiedDateIsInShouldWork() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where lastModifiedDate in DEFAULT_LAST_MODIFIED_DATE or UPDATED_LAST_MODIFIED_DATE
        defaultOptionPackageShouldBeFound("lastModifiedDate.in=" + DEFAULT_LAST_MODIFIED_DATE + "," + UPDATED_LAST_MODIFIED_DATE);

        // Get all the optionPackageList where lastModifiedDate equals to UPDATED_LAST_MODIFIED_DATE
        defaultOptionPackageShouldNotBeFound("lastModifiedDate.in=" + UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void getAllOptionPackagesByLastModifiedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        // Get all the optionPackageList where lastModifiedDate is not null
        defaultOptionPackageShouldBeFound("lastModifiedDate.specified=true");

        // Get all the optionPackageList where lastModifiedDate is null
        defaultOptionPackageShouldNotBeFound("lastModifiedDate.specified=false");
    }

    @Test
    @Transactional
    void getAllOptionPackagesByProductOptionPackageRelIsEqualToSomething() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);
        ProductOptionPackageRel productOptionPackageRel = ProductOptionPackageRelResourceIT.createEntity(em);
        em.persist(productOptionPackageRel);
        em.flush();
        optionPackageRepository.saveAndFlush(optionPackage);
        Long productOptionPackageRelId = productOptionPackageRel.getId();

        // Get all the optionPackageList where productOptionPackageRel equals to productOptionPackageRelId
        defaultOptionPackageShouldBeFound("productOptionPackageRelId.equals=" + productOptionPackageRelId);

        // Get all the optionPackageList where productOptionPackageRel equals to (productOptionPackageRelId + 1)
        defaultOptionPackageShouldNotBeFound("productOptionPackageRelId.equals=" + (productOptionPackageRelId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultOptionPackageShouldBeFound(String filter) throws Exception {
        restOptionPackageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(optionPackage.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].descriptionUsage").value(hasItem(DEFAULT_DESCRIPTION_USAGE.booleanValue())))
            .andExpect(jsonPath("$.[*].recommendShow").value(hasItem(DEFAULT_RECOMMEND_SHOW.booleanValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].thumbnailFileUrl").value(hasItem(DEFAULT_THUMBNAIL_FILE_URL)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));

        // Check, that the count call also returns 1
        restOptionPackageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultOptionPackageShouldNotBeFound(String filter) throws Exception {
        restOptionPackageMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restOptionPackageMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingOptionPackage() throws Exception {
        // Get the optionPackage
        restOptionPackageMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewOptionPackage() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        int databaseSizeBeforeUpdate = optionPackageRepository.findAll().size();

        // Update the optionPackage
        OptionPackage updatedOptionPackage = optionPackageRepository.findById(optionPackage.getId()).get();
        // Disconnect from session so that the updates on updatedOptionPackage are not directly saved in db
        em.detach(updatedOptionPackage);
        updatedOptionPackage
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .descriptionUsage(UPDATED_DESCRIPTION_USAGE)
            .recommendShow(UPDATED_RECOMMEND_SHOW)
            .description(UPDATED_DESCRIPTION)
            .thumbnailFileUrl(UPDATED_THUMBNAIL_FILE_URL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        OptionPackageDTO optionPackageDTO = optionPackageMapper.toDto(updatedOptionPackage);

        restOptionPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, optionPackageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionPackageDTO))
            )
            .andExpect(status().isOk());

        // Validate the OptionPackage in the database
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeUpdate);
        OptionPackage testOptionPackage = optionPackageList.get(optionPackageList.size() - 1);
        assertThat(testOptionPackage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOptionPackage.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOptionPackage.getDescriptionUsage()).isEqualTo(UPDATED_DESCRIPTION_USAGE);
        assertThat(testOptionPackage.getRecommendShow()).isEqualTo(UPDATED_RECOMMEND_SHOW);
        assertThat(testOptionPackage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOptionPackage.getThumbnailFileUrl()).isEqualTo(UPDATED_THUMBNAIL_FILE_URL);
        assertThat(testOptionPackage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testOptionPackage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOptionPackage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOptionPackage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOptionPackage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingOptionPackage() throws Exception {
        int databaseSizeBeforeUpdate = optionPackageRepository.findAll().size();
        optionPackage.setId(count.incrementAndGet());

        // Create the OptionPackage
        OptionPackageDTO optionPackageDTO = optionPackageMapper.toDto(optionPackage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, optionPackageDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionPackage in the database
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchOptionPackage() throws Exception {
        int databaseSizeBeforeUpdate = optionPackageRepository.findAll().size();
        optionPackage.setId(count.incrementAndGet());

        // Create the OptionPackage
        OptionPackageDTO optionPackageDTO = optionPackageMapper.toDto(optionPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionPackageMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(optionPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionPackage in the database
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamOptionPackage() throws Exception {
        int databaseSizeBeforeUpdate = optionPackageRepository.findAll().size();
        optionPackage.setId(count.incrementAndGet());

        // Create the OptionPackage
        OptionPackageDTO optionPackageDTO = optionPackageMapper.toDto(optionPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionPackageMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(optionPackageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OptionPackage in the database
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateOptionPackageWithPatch() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        int databaseSizeBeforeUpdate = optionPackageRepository.findAll().size();

        // Update the optionPackage using partial update
        OptionPackage partialUpdatedOptionPackage = new OptionPackage();
        partialUpdatedOptionPackage.setId(optionPackage.getId());

        partialUpdatedOptionPackage
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .recommendShow(UPDATED_RECOMMEND_SHOW)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restOptionPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionPackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionPackage))
            )
            .andExpect(status().isOk());

        // Validate the OptionPackage in the database
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeUpdate);
        OptionPackage testOptionPackage = optionPackageList.get(optionPackageList.size() - 1);
        assertThat(testOptionPackage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOptionPackage.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOptionPackage.getDescriptionUsage()).isEqualTo(DEFAULT_DESCRIPTION_USAGE);
        assertThat(testOptionPackage.getRecommendShow()).isEqualTo(UPDATED_RECOMMEND_SHOW);
        assertThat(testOptionPackage.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testOptionPackage.getThumbnailFileUrl()).isEqualTo(DEFAULT_THUMBNAIL_FILE_URL);
        assertThat(testOptionPackage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testOptionPackage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOptionPackage.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testOptionPackage.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testOptionPackage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateOptionPackageWithPatch() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        int databaseSizeBeforeUpdate = optionPackageRepository.findAll().size();

        // Update the optionPackage using partial update
        OptionPackage partialUpdatedOptionPackage = new OptionPackage();
        partialUpdatedOptionPackage.setId(optionPackage.getId());

        partialUpdatedOptionPackage
            .type(UPDATED_TYPE)
            .value(UPDATED_VALUE)
            .descriptionUsage(UPDATED_DESCRIPTION_USAGE)
            .recommendShow(UPDATED_RECOMMEND_SHOW)
            .description(UPDATED_DESCRIPTION)
            .thumbnailFileUrl(UPDATED_THUMBNAIL_FILE_URL)
            .activated(UPDATED_ACTIVATED)
            .createdBy(UPDATED_CREATED_BY)
            .createdDate(UPDATED_CREATED_DATE)
            .lastModifiedBy(UPDATED_LAST_MODIFIED_BY)
            .lastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restOptionPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedOptionPackage.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedOptionPackage))
            )
            .andExpect(status().isOk());

        // Validate the OptionPackage in the database
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeUpdate);
        OptionPackage testOptionPackage = optionPackageList.get(optionPackageList.size() - 1);
        assertThat(testOptionPackage.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testOptionPackage.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testOptionPackage.getDescriptionUsage()).isEqualTo(UPDATED_DESCRIPTION_USAGE);
        assertThat(testOptionPackage.getRecommendShow()).isEqualTo(UPDATED_RECOMMEND_SHOW);
        assertThat(testOptionPackage.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testOptionPackage.getThumbnailFileUrl()).isEqualTo(UPDATED_THUMBNAIL_FILE_URL);
        assertThat(testOptionPackage.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testOptionPackage.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testOptionPackage.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testOptionPackage.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testOptionPackage.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingOptionPackage() throws Exception {
        int databaseSizeBeforeUpdate = optionPackageRepository.findAll().size();
        optionPackage.setId(count.incrementAndGet());

        // Create the OptionPackage
        OptionPackageDTO optionPackageDTO = optionPackageMapper.toDto(optionPackage);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOptionPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, optionPackageDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionPackage in the database
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchOptionPackage() throws Exception {
        int databaseSizeBeforeUpdate = optionPackageRepository.findAll().size();
        optionPackage.setId(count.incrementAndGet());

        // Create the OptionPackage
        OptionPackageDTO optionPackageDTO = optionPackageMapper.toDto(optionPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionPackageMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionPackageDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the OptionPackage in the database
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamOptionPackage() throws Exception {
        int databaseSizeBeforeUpdate = optionPackageRepository.findAll().size();
        optionPackage.setId(count.incrementAndGet());

        // Create the OptionPackage
        OptionPackageDTO optionPackageDTO = optionPackageMapper.toDto(optionPackage);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restOptionPackageMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(optionPackageDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the OptionPackage in the database
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteOptionPackage() throws Exception {
        // Initialize the database
        optionPackageRepository.saveAndFlush(optionPackage);

        int databaseSizeBeforeDelete = optionPackageRepository.findAll().size();

        // Delete the optionPackage
        restOptionPackageMockMvc
            .perform(delete(ENTITY_API_URL_ID, optionPackage.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OptionPackage> optionPackageList = optionPackageRepository.findAll();
        assertThat(optionPackageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
