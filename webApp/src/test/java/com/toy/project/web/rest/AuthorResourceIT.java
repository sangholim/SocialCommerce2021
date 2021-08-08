package com.toy.project.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.toy.project.IntegrationTest;
import com.toy.project.domain.Author;
import com.toy.project.repository.AuthorRepository;
import com.toy.project.service.dto.AuthorDTO;
import com.toy.project.service.mapper.AuthorMapper;
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
 * Integration tests for the {@link AuthorResource} REST controller.
 */
@IntegrationTest
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
class AuthorResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_CALCULATION = 1;
    private static final Integer UPDATED_CALCULATION = 2;

    private static final String DEFAULT_CALCULATION_UNIT = "AAAAAAAAAA";
    private static final String UPDATED_CALCULATION_UNIT = "BBBBBBBBBB";

    private static final Instant DEFAULT_CALCULATION_DATE_FROM = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATION_DATE_FROM = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_CALCULATION_DATE_TO = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_CALCULATION_DATE_TO = Instant.now().truncatedTo(ChronoUnit.MILLIS);

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

    private static final String ENTITY_API_URL = "/api/authors";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAuthorMockMvc;

    private Author author;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Author createEntity(EntityManager em) {
        Author author = new Author();
        author.setName(DEFAULT_NAME);
        author.setCalculation(DEFAULT_CALCULATION);
        author.setCalculationUnit(DEFAULT_CALCULATION_UNIT);
        author.setCalculationDateFrom(DEFAULT_CALCULATION_DATE_FROM);
        author.setCalculationDateTo(DEFAULT_CALCULATION_DATE_TO);
        author.setActivated(DEFAULT_ACTIVATED);
        author.setCreatedBy(DEFAULT_CREATED_BY);
        author.setCreatedDate(DEFAULT_CREATED_DATE);
        author.setLastModifiedBy(DEFAULT_LAST_MODIFIED_BY);
        author.setLastModifiedDate(DEFAULT_LAST_MODIFIED_DATE);
        return author;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Author createUpdatedEntity(EntityManager em) {
        Author author = new Author();
        author.setName(UPDATED_NAME);
        author.setCalculation(UPDATED_CALCULATION);
        author.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        author.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        author.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        author.setActivated(UPDATED_ACTIVATED);
        author.setCreatedBy(UPDATED_CREATED_BY);
        author.setCreatedDate(UPDATED_CREATED_DATE);
        author.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        author.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        return author;
    }

    @BeforeEach
    public void initTest() {
        author = createEntity(em);
    }

    @Test
    @Transactional
    void createAuthor() throws Exception {
        int databaseSizeBeforeCreate = authorRepository.findAll().size();
        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);
        restAuthorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorDTO)))
            .andExpect(status().isCreated());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeCreate + 1);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuthor.getCalculation()).isEqualTo(DEFAULT_CALCULATION);
        assertThat(testAuthor.getCalculationUnit()).isEqualTo(DEFAULT_CALCULATION_UNIT);
        assertThat(testAuthor.getCalculationDateFrom()).isEqualTo(DEFAULT_CALCULATION_DATE_FROM);
        assertThat(testAuthor.getCalculationDateTo()).isEqualTo(DEFAULT_CALCULATION_DATE_TO);
        assertThat(testAuthor.getActivated()).isEqualTo(DEFAULT_ACTIVATED);
        assertThat(testAuthor.getCreatedBy()).isEqualTo(DEFAULT_CREATED_BY);
        assertThat(testAuthor.getCreatedDate()).isEqualTo(DEFAULT_CREATED_DATE);
        assertThat(testAuthor.getLastModifiedBy()).isEqualTo(DEFAULT_LAST_MODIFIED_BY);
        assertThat(testAuthor.getLastModifiedDate()).isEqualTo(DEFAULT_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void createAuthorWithExistingId() throws Exception {
        // Create the Author with an existing ID
        author.setId(1L);
        AuthorDTO authorDTO = authorMapper.toDto(author);

        int databaseSizeBeforeCreate = authorRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuthorMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAuthors() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get all the authorList
        restAuthorMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(author.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].calculation").value(hasItem(DEFAULT_CALCULATION)))
            .andExpect(jsonPath("$.[*].calculationUnit").value(hasItem(DEFAULT_CALCULATION_UNIT)))
            .andExpect(jsonPath("$.[*].calculationDateFrom").value(hasItem(DEFAULT_CALCULATION_DATE_FROM.toString())))
            .andExpect(jsonPath("$.[*].calculationDateTo").value(hasItem(DEFAULT_CALCULATION_DATE_TO.toString())))
            .andExpect(jsonPath("$.[*].activated").value(hasItem(DEFAULT_ACTIVATED.booleanValue())))
            .andExpect(jsonPath("$.[*].createdBy").value(hasItem(DEFAULT_CREATED_BY)))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastModifiedBy").value(hasItem(DEFAULT_LAST_MODIFIED_BY)))
            .andExpect(jsonPath("$.[*].lastModifiedDate").value(hasItem(DEFAULT_LAST_MODIFIED_DATE.toString())));
    }

    @Test
    @Transactional
    void getAuthor() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        // Get the author
        restAuthorMockMvc
            .perform(get(ENTITY_API_URL_ID, author.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(author.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.calculation").value(DEFAULT_CALCULATION))
            .andExpect(jsonPath("$.calculationUnit").value(DEFAULT_CALCULATION_UNIT))
            .andExpect(jsonPath("$.calculationDateFrom").value(DEFAULT_CALCULATION_DATE_FROM.toString()))
            .andExpect(jsonPath("$.calculationDateTo").value(DEFAULT_CALCULATION_DATE_TO.toString()))
            .andExpect(jsonPath("$.activated").value(DEFAULT_ACTIVATED.booleanValue()))
            .andExpect(jsonPath("$.createdBy").value(DEFAULT_CREATED_BY))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()))
            .andExpect(jsonPath("$.lastModifiedBy").value(DEFAULT_LAST_MODIFIED_BY))
            .andExpect(jsonPath("$.lastModifiedDate").value(DEFAULT_LAST_MODIFIED_DATE.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAuthor() throws Exception {
        // Get the author
        restAuthorMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putNewAuthor() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Update the author
        Author updatedAuthor = authorRepository.findById(author.getId()).get();
        // Disconnect from session so that the updates on updatedAuthor are not directly saved in db
        em.detach(updatedAuthor);
        updatedAuthor.setName(UPDATED_NAME);
        updatedAuthor.setCalculation(UPDATED_CALCULATION);
        updatedAuthor.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        updatedAuthor.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        updatedAuthor.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        updatedAuthor.setActivated(UPDATED_ACTIVATED);
        updatedAuthor.setCreatedBy(UPDATED_CREATED_BY);
        updatedAuthor.setCreatedDate(UPDATED_CREATED_DATE);
        updatedAuthor.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        updatedAuthor.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        AuthorDTO authorDTO = authorMapper.toDto(updatedAuthor);

        restAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isOk());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();

        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuthor.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testAuthor.getCalculationUnit()).isEqualTo(UPDATED_CALCULATION_UNIT);
        assertThat(testAuthor.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testAuthor.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testAuthor.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testAuthor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAuthor.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAuthor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAuthor.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void putNonExistingAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, authorDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(authorDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAuthorWithPatch() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Update the author using partial update
        Author partialUpdatedAuthor = new Author();
        partialUpdatedAuthor.setId(author.getId());

        partialUpdatedAuthor.setName(UPDATED_NAME);
        partialUpdatedAuthor.setCalculation(UPDATED_CALCULATION);
        partialUpdatedAuthor.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        partialUpdatedAuthor.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        partialUpdatedAuthor.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        partialUpdatedAuthor.setActivated(UPDATED_ACTIVATED);
        partialUpdatedAuthor.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedAuthor.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedAuthor.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedAuthor.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthor))
            )
            .andExpect(status().isOk());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();

        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAuthor.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testAuthor.getCalculationUnit()).isEqualTo(UPDATED_CALCULATION_UNIT);
        assertThat(testAuthor.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testAuthor.getCalculationDateTo()).isEqualTo(DEFAULT_CALCULATION_DATE_TO);
        assertThat(testAuthor.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testAuthor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAuthor.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAuthor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAuthor.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void fullUpdateAuthorWithPatch() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeUpdate = authorRepository.findAll().size();

        // Update the author using partial update
        Author partialUpdatedAuthor = new Author();
        partialUpdatedAuthor.setId(author.getId());
        partialUpdatedAuthor.setName(UPDATED_NAME);
        partialUpdatedAuthor.setCalculation(UPDATED_CALCULATION);
        partialUpdatedAuthor.setCalculationUnit(UPDATED_CALCULATION_UNIT);
        partialUpdatedAuthor.setCalculationDateFrom(UPDATED_CALCULATION_DATE_FROM);
        partialUpdatedAuthor.setCalculationDateTo(UPDATED_CALCULATION_DATE_TO);
        partialUpdatedAuthor.setActivated(UPDATED_ACTIVATED);
        partialUpdatedAuthor.setCreatedBy(UPDATED_CREATED_BY);
        partialUpdatedAuthor.setCreatedDate(UPDATED_CREATED_DATE);
        partialUpdatedAuthor.setLastModifiedBy(UPDATED_LAST_MODIFIED_BY);
        partialUpdatedAuthor.setLastModifiedDate(UPDATED_LAST_MODIFIED_DATE);
        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAuthor.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedAuthor))
            )
            .andExpect(status().isOk());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();

        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
        Author testAuthor = authorList.get(authorList.size() - 1);
        assertThat(testAuthor.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAuthor.getCalculation()).isEqualTo(UPDATED_CALCULATION);
        assertThat(testAuthor.getCalculationUnit()).isEqualTo(UPDATED_CALCULATION_UNIT);
        assertThat(testAuthor.getCalculationDateFrom()).isEqualTo(UPDATED_CALCULATION_DATE_FROM);
        assertThat(testAuthor.getCalculationDateTo()).isEqualTo(UPDATED_CALCULATION_DATE_TO);
        assertThat(testAuthor.getActivated()).isEqualTo(UPDATED_ACTIVATED);
        assertThat(testAuthor.getCreatedBy()).isEqualTo(UPDATED_CREATED_BY);
        assertThat(testAuthor.getCreatedDate()).isEqualTo(UPDATED_CREATED_DATE);
        assertThat(testAuthor.getLastModifiedBy()).isEqualTo(UPDATED_LAST_MODIFIED_BY);
        assertThat(testAuthor.getLastModifiedDate()).isEqualTo(UPDATED_LAST_MODIFIED_DATE);
    }

    @Test
    @Transactional
    void patchNonExistingAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, authorDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAuthor() throws Exception {
        int databaseSizeBeforeUpdate = authorRepository.findAll().size();
        author.setId(count.incrementAndGet());

        // Create the Author
        AuthorDTO authorDTO = authorMapper.toDto(author);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAuthorMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(authorDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the Author in the database
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAuthor() throws Exception {
        // Initialize the database
        authorRepository.saveAndFlush(author);

        int databaseSizeBeforeDelete = authorRepository.findAll().size();

        // Delete the author
        restAuthorMockMvc
            .perform(delete(ENTITY_API_URL_ID, author.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Author> authorList = authorRepository.findAll();
        assertThat(authorList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
