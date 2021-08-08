package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Clazz;
import com.toy.project.repository.ClazzRepository;
import com.toy.project.service.dto.ClazzDTO;
import com.toy.project.service.mapper.ClazzMapper;
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
 * Integration tests for the {@link ClazzResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ClazzResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_MAIN_IMAGE_FILE_URL = "AAAAAAAAAA";
    private static final String UPDATED_MAIN_IMAGE_FILE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LEVEL = "AAAAAAAAAA";
    private static final String UPDATED_LEVEL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_ENABLE_LECTURE = false;
    private static final Boolean UPDATED_ENABLE_LECTURE = true;

    private static final Boolean DEFAULT_FREE_LECTURE = false;
    private static final Boolean UPDATED_FREE_LECTURE = true;

    private static final Long DEFAULT_PRICE_LECTURE = 1L;
    private static final Long UPDATED_PRICE_LECTURE = 2L;

    private static final String DEFAULT_PRICE_UNIT_LECTURE = "AAAAAAAAAA";
    private static final String UPDATED_PRICE_UNIT_LECTURE = "BBBBBBBBBB";

    private static final Instant DEFAULT_LECTURE_START_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_LECTURE_START_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Integer DEFAULT_LECTURE_INTERVAL = 1;
    private static final Integer UPDATED_LECTURE_INTERVAL = 2;

    private static final Integer DEFAULT_CALCULATION = 1;
    private static final Integer UPDATED_CALCULATION = 2;

    private static final String DEFAULT_CALCULATION_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_CALCULATION_UNIT = "BBBBBBBBBB";

    private static final Boolean DEFAULT_USE_VIEW = false;
    private static final Boolean UPDATED_USE_VIEW = true;

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

    private static final String ENTITY_API_URL = "/api/clazzes";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ClazzRepository clazzRepository;

    @Autowired
    private ClazzMapper clazzMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restClazzMockMvc;

    private Clazz clazz;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clazz createEntity(EntityManager em) {
        Clazz clazz = new Clazz();
        clazz.setName(DEFAULT_NAME);
        clazz.setStatus(DEFAULT_STATUS);
        clazz.setType(DEFAULT_TYPE);
        clazz.setMainImageFileUrl(DEFAULT_MAIN_IMAGE_FILE_URL);
        clazz.setLevel(DEFAULT_LEVEL);
        clazz.setEnableLecture(DEFAULT_ENABLE_LECTURE);
        clazz.setFreeLecture(DEFAULT_FREE_LECTURE);
        clazz.setPriceLecture(DEFAULT_PRICE_LECTURE);
        clazz.setPriceUnitLecture(DEFAULT_PRICE_UNIT_LECTURE);
        clazz.setLectureStartDateFrom(DEFAULT_LECTURE_START_DATE_FROM);
        clazz.setLectureInterval(DEFAULT_LECTURE_INTERVAL);
        clazz.setCalculation(DEFAULT_CALCULATION);
        clazz.setCalculationUnit(DEFAULT_CALCULATION_UNIT);
        clazz.setActivated(DEFAULT_ACTIVATED);
        clazz.setCreatedBy(DEFAULT_CREATED_BY);
        clazz.setCreatedDate(DEFAULT_CREATED_DATE);
        clazz.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        clazz.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return clazz;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Clazz createUpdatedEntity(EntityManager em) {
        Clazz clazz = new Clazz();
        clazz.setName(UPDATED_NAME);
        clazz.setStatus(UPDATED_STATUS);
        clazz.setType(UPDATED_TYPE);
        clazz.setMainImageFileUrl(UPDATED_MAIN_IMAGE_FILE_URL);
        clazz.setLevel(UPDATED_LEVEL);
        clazz.setEnableLecture(UPDATED_ENABLE_LECTURE);
        clazz.setFreeLecture(UPDATED_FREE_LECTURE);
        clazz.setPriceLecture(UPDATED_PRICE_LECTURE);
        clazz.setPriceUnitLecture(UPDATED_PRICE_UNIT_LECTURE);
        clazz.setLectureStartDateFrom(UPDATED_LECTURE_START_DATE_FROM);
        clazz.setLectureInterval(UPDATED_LECTURE_INTERVAL);
        clazz.setCalculation(UPDATED_CALCULATION);
        clazz.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        clazz.setActivated(UPDATED_ACTIVATED);
        clazz.setCreatedBy(UPDATED_CREATED_BY);
        clazz.setCreatedDate(UPDATED_CREATED_DATE);
        clazz.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        clazz.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return clazz;
    }

    @BeforeEach
    public void initTest() {
        clazz = createEntity(em);
    }

    @Test
    @Transactional
    void createClazz() throws Exception {
        int databaseSizeBeforeCreate = clazzRepository.findAll().size();
        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);
        restClazzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clazzDTO)))
            .andExpect(status().isCreated());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeCreate + 1);
        Clazz testClazz = clazzList.get(clazzList.size() - 1);
        assertThat(testClazz.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testClazz.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClazz.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testClazz.getMainImageFileUrl()).isEqualTo(DEFAULT_MAIN_IMAGE_FILE_URL);
        assertThat(testClazz.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testClazz.getEnableLecture()).isEqualTo(DEFAULT_ENABLE_LECTURE);
        assertThat(testClazz.getFreeLecture()).isEqualTo(DEFAULT_FREE_LECTURE);
        assertThat(testClazz.getPriceLecture()).isEqualTo(DEFAULT_PRICE_LECTURE);
        assertThat(testClazz.getPriceUnitLecture()).isEqualTo(DEFAULT_PRICE_UNIT_LECTURE);
        assertThat(testClazz.getLectureStartDateFrom()).isEqualTo(DEFAULT_LECTURE_START_DATE_FROM);
        assertThat(testClazz.getLectureInterval()).isEqualTo(DEFAULT_LECTURE_INTERVAL);
        assertThat(testClazz.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testClazz.getCalculationUnit()).isEqualTo(DEFAULT_CALCULATION_UNIT);
        assertThat(testClazz.getUseView()).isEqualTo(DEFAULT_USE_VIEW);
        assertThat(testClazz.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testClazz.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClazz.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testClazz.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testClazz.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createClazzWithExistingId() throws Exception {
        // Create the Clazz with an existing ID
        clazz.setId(1L);
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        int databaseSizeBeforeCreate = clazzRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restClazzMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clazzDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllClazzes() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get all the clazzList
        restClazzMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clazz.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].mainImageFileUrl").value(hasItem(DEFAULT_MAIN_IMAGE_FILE_URL)))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL)))
            .andExpect(jsonPath("$.[*].enableLecture").value(hasItem(DEFAULT_ENABLE_LECTURE.booleanValue())))
            .andExpect(jsonPath("$.[*].freeLecture").value(hasItem(DEFAULT_FREE_LECTURE.booleanValue())))
            .andExpect(jsonPath("$.[*].priceLecture").value(hasItem(DEFAULT_PRICE_LECTURE.intValue())))
            .andExpect(jsonPath("$.[*].priceUnitLecture").value(hasItem(DEFAULT_PRICE_UNIT_LECTURE)))
            .andExpect(jsonPath("$.[*].lectureStartDateFrom").value(hasItem(DEFAULT_LECTURE_START_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].lectureInterval").value(hasItem(DEFAULT_LECTURE_INTERVAL)))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].calculationUnit").value(hasItem(DEFAULT_CALCULATION_UNIT)))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getClazz() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        // Get the clazz
        restClazzMockMvc
            .perform(get(ENTITY_API_URL_ID, clazz.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(clazz.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.mainImageFileUrl").value(DEFAULT_MAIN_IMAGE_FILE_URL))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL))
            .andExpect(jsonPath("$.enableLecture").value(DEFAULT_ENABLE_LECTURE.booleanValue()))
            .andExpect(jsonPath("$.freeLecture").value(DEFAULT_FREE_LECTURE.booleanValue()))
            .andExpect(jsonPath("$.priceLecture").value(DEFAULT_PRICE_LECTURE.intValue()))
            .andExpect(jsonPath("$.priceUnitLecture").value(DEFAULT_PRICE_UNIT_LECTURE))
            .andExpect(jsonPath("$.lectureStartDateFrom").value(DEFAULT_LECTURE_START_DATE_FROM.toString()))
            .andExpect(jsonPath("$.lectureInterval").value(DEFAULT_LECTURE_INTERVAL))
            .andExpect(jsonPath("$.calculation").value(DEFAULT_CALCULATION))
            .andExpect(jsonPath("$.calculationUnit").value(DEFAULT_CALCULATION_UNIT))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingClazz() throws Exception {
        // Get the clazz
        restClazzMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewClazz() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();

        // Update the clazz
        Clazz updatedClazz = clazzRepository.findById(clazz.getId()).get();
        // Disconnect from session so that the updates on updatedClazz are not directly saved in db
        em.detach(updatedClazz);
        updatedClazz.setName(UPDATED_NAME);
        updatedClazz.setStatus(UPDATED_STATUS);
        updatedClazz.setType(UPDATED_TYPE);
        updatedClazz.setMainImageFileUrl(UPDATED_MAIN_IMAGE_FILE_URL);
        updatedClazz.setLevel(UPDATED_LEVEL);
        updatedClazz.setEnableLecture(UPDATED_ENABLE_LECTURE);
        updatedClazz.setFreeLecture(UPDATED_FREE_LECTURE);
        updatedClazz.setPriceLecture(UPDATED_PRICE_LECTURE);
        updatedClazz.setPriceUnitLecture(UPDATED_PRICE_UNIT_LECTURE);
        updatedClazz.setLectureStartDateFrom(UPDATED_LECTURE_START_DATE_FROM);
        updatedClazz.setLectureInterval(UPDATED_LECTURE_INTERVAL);
        updatedClazz.setCalculation(UPDATED_CALCULATION);
        updatedClazz.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        updatedClazz.setActivated(UPDATED_ACTIVATED);
        updatedClazz.setCreatedBy(UPDATED_CREATED_BY);
        updatedClazz.setCreatedDate(UPDATED_CREATED_DATE);
        updatedClazz.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedClazz.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ClazzDTO clazzDTO = clazzMapper.toDto(updatedClazz);

        restClazzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clazzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzDTO))
            )
            .andExpect(status().isOk());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
        Clazz testClazz = clazzList.get(clazzList.size() - 1);
        assertThat(testClazz.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClazz.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClazz.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testClazz.getMainImageFileUrl()).isEqualTo(UPDATED_MAIN_IMAGE_FILE_URL);
        assertThat(testClazz.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testClazz.getEnableLecture()).isEqualTo(UPDATED_ENABLE_LECTURE);
        assertThat(testClazz.getFreeLecture()).isEqualTo(UPDATED_FREE_LECTURE);
        assertThat(testClazz.getPriceLecture()).isEqualTo(UPDATED_PRICE_LECTURE);
        assertThat(testClazz.getPriceUnitLecture()).isEqualTo(UPDATED_PRICE_UNIT_LECTURE);
        assertThat(testClazz.getLectureStartDateFrom()).isEqualTo(UPDATED_LECTURE_START_DATE_FROM);
        assertThat(testClazz.getLectureInterval()).isEqualTo(UPDATED_LECTURE_INTERVAL);
        assertThat(testClazz.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testClazz.getCalculationUnit()).isEqualTo(UPDATED_CALCULATION_UNIT);
        assertThat(testClazz.getUseView()).isEqualTo(UPDATED_USE_VIEW);
        assertThat(testClazz.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazz.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClazz.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClazz.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testClazz.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, clazzDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(clazzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(clazzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateClazzWithPatch() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();

        // Update the clazz using partial update
        Clazz partialUpdatedClazz = new Clazz();
        partialUpdatedClazz.setId(clazz.getId());

        partialUpdatedClazz.setName(UPDATED_NAME);
        partialUpdatedClazz.setStatus(UPDATED_STATUS);
        partialUpdatedClazz.setType(UPDATED_TYPE);
        partialUpdatedClazz.setMainImageFileUrl(UPDATED_MAIN_IMAGE_FILE_URL);
        partialUpdatedClazz.setLevel(UPDATED_LEVEL);
        partialUpdatedClazz.setEnableLecture(UPDATED_ENABLE_LECTURE);
        partialUpdatedClazz.setFreeLecture(UPDATED_FREE_LECTURE);
        partialUpdatedClazz.setPriceLecture(UPDATED_PRICE_LECTURE);
        partialUpdatedClazz.setPriceUnitLecture(UPDATED_PRICE_UNIT_LECTURE);
        partialUpdatedClazz.setLectureStartDateFrom(UPDATED_LECTURE_START_DATE_FROM);
        partialUpdatedClazz.setLectureInterval(UPDATED_LECTURE_INTERVAL);
        partialUpdatedClazz.setCalculation(UPDATED_CALCULATION);
        partialUpdatedClazz.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        partialUpdatedClazz.setActivated(UPDATED_ACTIVATED);
        partialUpdatedClazz.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedClazz.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedClazz.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedClazz.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restClazzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClazz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClazz))
            )
            .andExpect(status().isOk());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
        Clazz testClazz = clazzList.get(clazzList.size() - 1);
        assertThat(testClazz.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClazz.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testClazz.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testClazz.getMainImageFileUrl()).isEqualTo(UPDATED_MAIN_IMAGE_FILE_URL);
        assertThat(testClazz.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testClazz.getEnableLecture()).isEqualTo(DEFAULT_ENABLE_LECTURE);
        assertThat(testClazz.getFreeLecture()).isEqualTo(UPDATED_FREE_LECTURE);
        assertThat(testClazz.getPriceLecture()).isEqualTo(UPDATED_PRICE_LECTURE);
        assertThat(testClazz.getPriceUnitLecture()).isEqualTo(DEFAULT_PRICE_UNIT_LECTURE);
        assertThat(testClazz.getLectureStartDateFrom()).isEqualTo(DEFAULT_LECTURE_START_DATE_FROM);
        assertThat(testClazz.getLectureInterval()).isEqualTo(UPDATED_LECTURE_INTERVAL);
        assertThat(testClazz.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testClazz.getCalculationUnit()).isEqualTo(UPDATED_CALCULATION_UNIT);
        assertThat(testClazz.getUseView()).isEqualTo(DEFAULT_USE_VIEW);
        assertThat(testClazz.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazz.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testClazz.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testClazz.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testClazz.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateClazzWithPatch() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();

        // Update the clazz using partial update
        Clazz partialUpdatedClazz = new Clazz();
        partialUpdatedClazz.setId(clazz.getId());

        partialUpdatedClazz.setName(UPDATED_NAME);
        partialUpdatedClazz.setStatus(UPDATED_STATUS);
        partialUpdatedClazz.setType(UPDATED_TYPE);
        partialUpdatedClazz.setMainImageFileUrl(UPDATED_MAIN_IMAGE_FILE_URL);
        partialUpdatedClazz.setLevel(UPDATED_LEVEL);
        partialUpdatedClazz.setEnableLecture(UPDATED_ENABLE_LECTURE);
        partialUpdatedClazz.setFreeLecture(UPDATED_FREE_LECTURE);
        partialUpdatedClazz.setPriceLecture(UPDATED_PRICE_LECTURE);
        partialUpdatedClazz.setPriceUnitLecture(UPDATED_PRICE_UNIT_LECTURE);
        partialUpdatedClazz.setLectureStartDateFrom(UPDATED_LECTURE_START_DATE_FROM);
        partialUpdatedClazz.setLectureInterval(UPDATED_LECTURE_INTERVAL);
        partialUpdatedClazz.setCalculation(UPDATED_CALCULATION);
        partialUpdatedClazz.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        partialUpdatedClazz.setActivated(UPDATED_ACTIVATED);
        partialUpdatedClazz.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedClazz.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedClazz.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedClazz.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restClazzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedClazz.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedClazz))
            )
            .andExpect(status().isOk());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
        Clazz testClazz = clazzList.get(clazzList.size() - 1);
        assertThat(testClazz.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testClazz.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testClazz.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testClazz.getMainImageFileUrl()).isEqualTo(UPDATED_MAIN_IMAGE_FILE_URL);
        assertThat(testClazz.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testClazz.getEnableLecture()).isEqualTo(UPDATED_ENABLE_LECTURE);
        assertThat(testClazz.getFreeLecture()).isEqualTo(UPDATED_FREE_LECTURE);
        assertThat(testClazz.getPriceLecture()).isEqualTo(UPDATED_PRICE_LECTURE);
        assertThat(testClazz.getPriceUnitLecture()).isEqualTo(UPDATED_PRICE_UNIT_LECTURE);
        assertThat(testClazz.getLectureStartDateFrom()).isEqualTo(UPDATED_LECTURE_START_DATE_FROM);
        assertThat(testClazz.getLectureInterval()).isEqualTo(UPDATED_LECTURE_INTERVAL);
        assertThat(testClazz.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testClazz.getCalculationUnit()).isEqualTo(UPDATED_CALCULATION_UNIT);
        assertThat(testClazz.getUseView()).isEqualTo(UPDATED_USE_VIEW);
        assertThat(testClazz.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testClazz.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testClazz.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testClazz.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testClazz.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, clazzDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clazzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(clazzDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamClazz() throws Exception {
        int databaseSizeBeforeUpdate = clazzRepository.findAll().size();
        clazz.setId(count.incrementAndGet());

        // Create the Clazz
        ClazzDTO clazzDTO = clazzMapper.toDto(clazz);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restClazzMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(clazzDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Clazz in the database
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteClazz() throws Exception {
        // Initialize the database
        clazzRepository.saveAndFlush(clazz);

        int databaseSizeBeforeDelete = clazzRepository.findAll().size();

        // Delete the clazz
        restClazzMockMvc
            .perform(delete(ENTITY_API_URL_ID, clazz.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Clazz> clazzList = clazzRepository.findAll();
        assertThat(clazzList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
