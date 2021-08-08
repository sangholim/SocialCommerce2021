package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.ProductFaq;
import com.toy.project.repository.ProductFaqRepository;
import com.toy.project.service.dto.ProductFaqDTO;
import com.toy.project.service.mapper.ProductFaqMapper;
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
 * Integration tests for the {@link ProductFaqResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class ProductFaqResourceIT {

    private static final Integer DEFAULT_SEQUENCE = 1;
    private static final Integer UPDATED_SEQUENCE = 2;

    private static final String DEFAULT_QUESTION = "AAAAAAAAAA";
    private static final String UPDATED_QUESTION = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    private static final Instant DEFAULT_ANSWER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ANSWER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/product-faqs";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ProductFaqRepository productFaqRepository;

    @Autowired
    private ProductFaqMapper productFaqMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restProductFaqMockMvc;

    private ProductFaq productFaq;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductFaq createEntity(EntityManager em) {
        ProductFaq productFaq = new ProductFaq();
        productFaq.setSequence(DEFAULT_SEQUENCE);
        productFaq.setQuestion(DEFAULT_QUESTION);
        productFaq.setAnswer(DEFAULT_ANSWER);
        productFaq.setAnswerDate(DEFAULT_ANSWER_DATE);
        productFaq.setActivated(DEFAULT_ACTIVATED);
        productFaq.setCreatedBy(DEFAULT_CREATED_BY);
        productFaq.setCreatedDate(DEFAULT_CREATED_DATE);
        productFaq.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        productFaq.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return productFaq;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ProductFaq createUpdatedEntity(EntityManager em) {
        ProductFaq productFaq = new ProductFaq();
        productFaq.setSequence(UPDATED_SEQUENCE);
        productFaq.setQuestion(UPDATED_QUESTION);
        productFaq.setAnswer(UPDATED_ANSWER);
        productFaq.setAnswerDate(UPDATED_ANSWER_DATE);
        productFaq.setActivated(UPDATED_ACTIVATED);
        productFaq.setCreatedBy(UPDATED_CREATED_BY);
        productFaq.setCreatedDate(UPDATED_CREATED_DATE);
        productFaq.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        productFaq.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return productFaq;
    }

    @BeforeEach
    public void initTest() {
        productFaq = createEntity(em);
    }

    @Test
    @Transactional
    void createProductFaq() throws Exception {
        int databaseSizeBeforeCreate = productFaqRepository.findAll().size();
        // Create the ProductFaq
        ProductFaqDTO productFaqDTO = productFaqMapper.toDto(productFaq);
        restProductFaqMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productFaqDTO)))
            .andExpect(status().isCreated());

        // Validate the ProductFaq in the database
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeCreate + 1);
        ProductFaq testProductFaq = productFaqList.get(productFaqList.size() - 1);
        assertThat(testProductFaq.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testProductFaq.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testProductFaq.getAnswer()).isEqualTo(DEFAULT_ANSWER);
        assertThat(testProductFaq.getAnswerDate()).isEqualTo(DEFAULT_ANSWER_DATE);
        assertThat(testProductFaq.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductFaq.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductFaq.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductFaq.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testProductFaq.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createProductFaqWithExistingId() throws Exception {
        // Create the ProductFaq with an existing ID
        productFaq.setId(1L);
        ProductFaqDTO productFaqDTO = productFaqMapper.toDto(productFaq);

        int databaseSizeBeforeCreate = productFaqRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductFaqMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productFaqDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ProductFaq in the database
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllProductFaqs() throws Exception {
        // Initialize the database
        productFaqRepository.saveAndFlush(productFaq);

        // Get all the productFaqList
        restProductFaqMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(productFaq.getId().intValue())))
            .andExpect(jsonPath("$.[*].sequence").value(hasItem(DEFAULT_SEQUENCE)))
            .andExpect(jsonPath("$.[*].question").value(hasItem(DEFAULT_QUESTION)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)))
            .andExpect(jsonPath("$.[*].answerDate").value(hasItem(DEFAULT_ANSWER_DATE.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getProductFaq() throws Exception {
        // Initialize the database
        productFaqRepository.saveAndFlush(productFaq);

        // Get the productFaq
        restProductFaqMockMvc
            .perform(get(ENTITY_API_URL_ID, productFaq.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(productFaq.getId().intValue()))
            .andExpect(jsonPath("$.sequence").value(DEFAULT_SEQUENCE))
            .andExpect(jsonPath("$.question").value(DEFAULT_QUESTION))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER))
            .andExpect(jsonPath("$.answerDate").value(DEFAULT_ANSWER_DATE.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingProductFaq() throws Exception {
        // Get the productFaq
        restProductFaqMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewProductFaq() throws Exception {
        // Initialize the database
        productFaqRepository.saveAndFlush(productFaq);

        int databaseSizeBeforeUpdate = productFaqRepository.findAll().size();

        // Update the productFaq
        ProductFaq updatedProductFaq = productFaqRepository.findById(productFaq.getId()).get();
        // Disconnect from session so that the updates on updatedProductFaq are not directly saved in db
        em.detach(updatedProductFaq);
        updatedProductFaq.setSequence(UPDATED_SEQUENCE);
        updatedProductFaq.setQuestion(UPDATED_QUESTION);
        updatedProductFaq.setAnswer(UPDATED_ANSWER);
        updatedProductFaq.setAnswerDate(UPDATED_ANSWER_DATE);
        updatedProductFaq.setActivated(UPDATED_ACTIVATED);
        updatedProductFaq.setCreatedBy(UPDATED_CREATED_BY);
        updatedProductFaq.setCreatedDate(UPDATED_CREATED_DATE);
        updatedProductFaq.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedProductFaq.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        ProductFaqDTO productFaqDTO = productFaqMapper.toDto(updatedProductFaq);

        restProductFaqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productFaqDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productFaqDTO))
            )
            .andExpect(status().isOk());

        // Validate the ProductFaq in the database
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeUpdate);
        ProductFaq testProductFaq = productFaqList.get(productFaqList.size() - 1);
        assertThat(testProductFaq.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testProductFaq.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testProductFaq.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testProductFaq.getAnswerDate()).isEqualTo(UPDATED_ANSWER_DATE);
        assertThat(testProductFaq.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductFaq.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductFaq.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductFaq.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductFaq.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingProductFaq() throws Exception {
        int databaseSizeBeforeUpdate = productFaqRepository.findAll().size();
        productFaq.setId(count.incrementAndGet());

        // Create the ProductFaq
        ProductFaqDTO productFaqDTO = productFaqMapper.toDto(productFaq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductFaqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, productFaqDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productFaqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductFaq in the database
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchProductFaq() throws Exception {
        int databaseSizeBeforeUpdate = productFaqRepository.findAll().size();
        productFaq.setId(count.incrementAndGet());

        // Create the ProductFaq
        ProductFaqDTO productFaqDTO = productFaqMapper.toDto(productFaq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductFaqMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(productFaqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductFaq in the database
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamProductFaq() throws Exception {
        int databaseSizeBeforeUpdate = productFaqRepository.findAll().size();
        productFaq.setId(count.incrementAndGet());

        // Create the ProductFaq
        ProductFaqDTO productFaqDTO = productFaqMapper.toDto(productFaq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductFaqMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(productFaqDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductFaq in the database
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateProductFaqWithPatch() throws Exception {
        // Initialize the database
        productFaqRepository.saveAndFlush(productFaq);

        int databaseSizeBeforeUpdate = productFaqRepository.findAll().size();

        // Update the productFaq using partial update
        ProductFaq partialUpdatedProductFaq = new ProductFaq();
        partialUpdatedProductFaq.setId(productFaq.getId());

        partialUpdatedProductFaq.setSequence(UPDATED_SEQUENCE);
        partialUpdatedProductFaq.setQuestion(UPDATED_QUESTION);
        partialUpdatedProductFaq.setAnswer(UPDATED_ANSWER);
        partialUpdatedProductFaq.setAnswerDate(UPDATED_ANSWER_DATE);
        partialUpdatedProductFaq.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductFaq.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductFaq.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductFaq.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductFaq.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restProductFaqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductFaq.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductFaq))
            )
            .andExpect(status().isOk());

        // Validate the ProductFaq in the database
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeUpdate);
        ProductFaq testProductFaq = productFaqList.get(productFaqList.size() - 1);
        assertThat(testProductFaq.getSequence()).isEqualTo(DEFAULT_SEQUENCE);
        assertThat(testProductFaq.getQuestion()).isEqualTo(DEFAULT_QUESTION);
        assertThat(testProductFaq.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testProductFaq.getAnswerDate()).isEqualTo(UPDATED_ANSWER_DATE);
        assertThat(testProductFaq.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testProductFaq.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testProductFaq.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testProductFaq.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductFaq.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateProductFaqWithPatch() throws Exception {
        // Initialize the database
        productFaqRepository.saveAndFlush(productFaq);

        int databaseSizeBeforeUpdate = productFaqRepository.findAll().size();

        // Update the productFaq using partial update
        ProductFaq partialUpdatedProductFaq = new ProductFaq();
        partialUpdatedProductFaq.setId(productFaq.getId());

        partialUpdatedProductFaq.setSequence(UPDATED_SEQUENCE);
        partialUpdatedProductFaq.setQuestion(UPDATED_QUESTION);
        partialUpdatedProductFaq.setAnswer(UPDATED_ANSWER);
        partialUpdatedProductFaq.setAnswerDate(UPDATED_ANSWER_DATE);
        partialUpdatedProductFaq.setActivated(UPDATED_ACTIVATED);
        partialUpdatedProductFaq.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedProductFaq.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedProductFaq.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedProductFaq.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);

        restProductFaqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedProductFaq.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedProductFaq))
            )
            .andExpect(status().isOk());

        // Validate the ProductFaq in the database
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeUpdate);
        ProductFaq testProductFaq = productFaqList.get(productFaqList.size() - 1);
        assertThat(testProductFaq.getSequence()).isEqualTo(UPDATED_SEQUENCE);
        assertThat(testProductFaq.getQuestion()).isEqualTo(UPDATED_QUESTION);
        assertThat(testProductFaq.getAnswer()).isEqualTo(UPDATED_ANSWER);
        assertThat(testProductFaq.getAnswerDate()).isEqualTo(UPDATED_ANSWER_DATE);
        assertThat(testProductFaq.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testProductFaq.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testProductFaq.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testProductFaq.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testProductFaq.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingProductFaq() throws Exception {
        int databaseSizeBeforeUpdate = productFaqRepository.findAll().size();
        productFaq.setId(count.incrementAndGet());

        // Create the ProductFaq
        ProductFaqDTO productFaqDTO = productFaqMapper.toDto(productFaq);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductFaqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, productFaqDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productFaqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductFaq in the database
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchProductFaq() throws Exception {
        int databaseSizeBeforeUpdate = productFaqRepository.findAll().size();
        productFaq.setId(count.incrementAndGet());

        // Create the ProductFaq
        ProductFaqDTO productFaqDTO = productFaqMapper.toDto(productFaq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductFaqMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(productFaqDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the ProductFaq in the database
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamProductFaq() throws Exception {
        int databaseSizeBeforeUpdate = productFaqRepository.findAll().size();
        productFaq.setId(count.incrementAndGet());

        // Create the ProductFaq
        ProductFaqDTO productFaqDTO = productFaqMapper.toDto(productFaq);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restProductFaqMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(productFaqDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the ProductFaq in the database
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteProductFaq() throws Exception {
        // Initialize the database
        productFaqRepository.saveAndFlush(productFaq);

        int databaseSizeBeforeDelete = productFaqRepository.findAll().size();

        // Delete the productFaq
        restProductFaqMockMvc
            .perform(delete(ENTITY_API_URL_ID, productFaq.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ProductFaq> productFaqList = productFaqRepository.findAll();
        assertThat(productFaqList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
