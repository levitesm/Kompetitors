package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.AnnualAccountStatistics;
import fr.ippon.kompetitors.repository.AnnualAccountStatisticsRepository;
import fr.ippon.kompetitors.service.AnnualAccountStatisticsFetchService;
import fr.ippon.kompetitors.service.AnnualAccountStatisticsService;
import fr.ippon.kompetitors.service.dto.AnnualAccountStatisticsDTO;
import fr.ippon.kompetitors.service.mapper.AnnualAccountStatisticsMapper;
import fr.ippon.kompetitors.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static fr.ippon.kompetitors.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link AnnualAccountStatisticsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class AnnualAccountStatisticsResourceIT {

    private static final String DEFAULT_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_SIREN = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Integer DEFAULT_CODE = 1;
    private static final Integer UPDATED_CODE = 2;

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Instant DEFAULT_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private AnnualAccountStatisticsRepository annualAccountStatisticsRepository;

    @Autowired
    private AnnualAccountStatisticsMapper annualAccountStatisticsMapper;

    @Autowired
    private AnnualAccountStatisticsService annualAccountStatisticsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAnnualAccountStatisticsMockMvc;

    private AnnualAccountStatistics annualAccountStatistics;
    private AnnualAccountStatisticsFetchService annualAccountStatisticsFetchService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnnualAccountStatisticsResource annualAccountStatisticsResource = new AnnualAccountStatisticsResource(annualAccountStatisticsService, annualAccountStatisticsFetchService);
        this.restAnnualAccountStatisticsMockMvc = MockMvcBuilders.standaloneSetup(annualAccountStatisticsResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnnualAccountStatistics createEntity(EntityManager em) {
        AnnualAccountStatistics annualAccountStatistics = new AnnualAccountStatistics()
            .siren(DEFAULT_SIREN)
            .year(DEFAULT_YEAR)
            .code(DEFAULT_CODE)
            .message(DEFAULT_MESSAGE)
            .modified(DEFAULT_MODIFIED);
        return annualAccountStatistics;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnnualAccountStatistics createUpdatedEntity(EntityManager em) {
        AnnualAccountStatistics annualAccountStatistics = new AnnualAccountStatistics()
            .siren(UPDATED_SIREN)
            .year(UPDATED_YEAR)
            .code(UPDATED_CODE)
            .message(UPDATED_MESSAGE)
            .modified(UPDATED_MODIFIED);
        return annualAccountStatistics;
    }

    @BeforeEach
    public void initTest() {
        annualAccountStatistics = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnualAccountStatistics() throws Exception {
        int databaseSizeBeforeCreate = annualAccountStatisticsRepository.findAll().size();

        // Create the AnnualAccountStatistics
        AnnualAccountStatisticsDTO annualAccountStatisticsDTO = annualAccountStatisticsMapper.toDto(annualAccountStatistics);
        restAnnualAccountStatisticsMockMvc.perform(post("/api/annual-account-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountStatisticsDTO)))
            .andExpect(status().isCreated());

        // Validate the AnnualAccountStatistics in the database
        List<AnnualAccountStatistics> annualAccountStatisticsList = annualAccountStatisticsRepository.findAll();
        assertThat(annualAccountStatisticsList).hasSize(databaseSizeBeforeCreate + 1);
        AnnualAccountStatistics testAnnualAccountStatistics = annualAccountStatisticsList.get(annualAccountStatisticsList.size() - 1);
        assertThat(testAnnualAccountStatistics.getSiren()).isEqualTo(DEFAULT_SIREN);
        assertThat(testAnnualAccountStatistics.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testAnnualAccountStatistics.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAnnualAccountStatistics.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testAnnualAccountStatistics.getModified()).isEqualTo(DEFAULT_MODIFIED);
    }

    @Test
    @Transactional
    public void createAnnualAccountStatisticsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = annualAccountStatisticsRepository.findAll().size();

        // Create the AnnualAccountStatistics with an existing ID
        annualAccountStatistics.setId(1L);
        AnnualAccountStatisticsDTO annualAccountStatisticsDTO = annualAccountStatisticsMapper.toDto(annualAccountStatistics);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnualAccountStatisticsMockMvc.perform(post("/api/annual-account-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountStatisticsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnnualAccountStatistics in the database
        List<AnnualAccountStatistics> annualAccountStatisticsList = annualAccountStatisticsRepository.findAll();
        assertThat(annualAccountStatisticsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSirenIsRequired() throws Exception {
        int databaseSizeBeforeTest = annualAccountStatisticsRepository.findAll().size();
        // set the field null
        annualAccountStatistics.setSiren(null);

        // Create the AnnualAccountStatistics, which fails.
        AnnualAccountStatisticsDTO annualAccountStatisticsDTO = annualAccountStatisticsMapper.toDto(annualAccountStatistics);

        restAnnualAccountStatisticsMockMvc.perform(post("/api/annual-account-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountStatisticsDTO)))
            .andExpect(status().isBadRequest());

        List<AnnualAccountStatistics> annualAccountStatisticsList = annualAccountStatisticsRepository.findAll();
        assertThat(annualAccountStatisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = annualAccountStatisticsRepository.findAll().size();
        // set the field null
        annualAccountStatistics.setYear(null);

        // Create the AnnualAccountStatistics, which fails.
        AnnualAccountStatisticsDTO annualAccountStatisticsDTO = annualAccountStatisticsMapper.toDto(annualAccountStatistics);

        restAnnualAccountStatisticsMockMvc.perform(post("/api/annual-account-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountStatisticsDTO)))
            .andExpect(status().isBadRequest());

        List<AnnualAccountStatistics> annualAccountStatisticsList = annualAccountStatisticsRepository.findAll();
        assertThat(annualAccountStatisticsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnnualAccountStatistics() throws Exception {
        // Initialize the database
        annualAccountStatisticsRepository.saveAndFlush(annualAccountStatistics);

        // Get all the annualAccountStatisticsList
        restAnnualAccountStatisticsMockMvc.perform(get("/api/annual-account-statistics?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annualAccountStatistics.getId().intValue())))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.toString())));
    }

    @Test
    @Transactional
    public void getAnnualAccountStatistics() throws Exception {
        // Initialize the database
        annualAccountStatisticsRepository.saveAndFlush(annualAccountStatistics);

        // Get the annualAccountStatistics
        restAnnualAccountStatisticsMockMvc.perform(get("/api/annual-account-statistics/{id}", annualAccountStatistics.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(annualAccountStatistics.getId().intValue()))
            .andExpect(jsonPath("$.siren").value(DEFAULT_SIREN))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.modified").value(DEFAULT_MODIFIED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAnnualAccountStatistics() throws Exception {
        // Get the annualAccountStatistics
        restAnnualAccountStatisticsMockMvc.perform(get("/api/annual-account-statistics/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnualAccountStatistics() throws Exception {
        // Initialize the database
        annualAccountStatisticsRepository.saveAndFlush(annualAccountStatistics);

        int databaseSizeBeforeUpdate = annualAccountStatisticsRepository.findAll().size();

        // Update the annualAccountStatistics
        AnnualAccountStatistics updatedAnnualAccountStatistics = annualAccountStatisticsRepository.findById(annualAccountStatistics.getId()).get();
        // Disconnect from session so that the updates on updatedAnnualAccountStatistics are not directly saved in db
        em.detach(updatedAnnualAccountStatistics);
        updatedAnnualAccountStatistics
            .siren(UPDATED_SIREN)
            .year(UPDATED_YEAR)
            .code(UPDATED_CODE)
            .message(UPDATED_MESSAGE)
            .modified(UPDATED_MODIFIED);
        AnnualAccountStatisticsDTO annualAccountStatisticsDTO = annualAccountStatisticsMapper.toDto(updatedAnnualAccountStatistics);

        restAnnualAccountStatisticsMockMvc.perform(put("/api/annual-account-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountStatisticsDTO)))
            .andExpect(status().isOk());

        // Validate the AnnualAccountStatistics in the database
        List<AnnualAccountStatistics> annualAccountStatisticsList = annualAccountStatisticsRepository.findAll();
        assertThat(annualAccountStatisticsList).hasSize(databaseSizeBeforeUpdate);
        AnnualAccountStatistics testAnnualAccountStatistics = annualAccountStatisticsList.get(annualAccountStatisticsList.size() - 1);
        assertThat(testAnnualAccountStatistics.getSiren()).isEqualTo(UPDATED_SIREN);
        assertThat(testAnnualAccountStatistics.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAnnualAccountStatistics.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAnnualAccountStatistics.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testAnnualAccountStatistics.getModified()).isEqualTo(UPDATED_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnualAccountStatistics() throws Exception {
        int databaseSizeBeforeUpdate = annualAccountStatisticsRepository.findAll().size();

        // Create the AnnualAccountStatistics
        AnnualAccountStatisticsDTO annualAccountStatisticsDTO = annualAccountStatisticsMapper.toDto(annualAccountStatistics);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnualAccountStatisticsMockMvc.perform(put("/api/annual-account-statistics")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountStatisticsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnnualAccountStatistics in the database
        List<AnnualAccountStatistics> annualAccountStatisticsList = annualAccountStatisticsRepository.findAll();
        assertThat(annualAccountStatisticsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnnualAccountStatistics() throws Exception {
        // Initialize the database
        annualAccountStatisticsRepository.saveAndFlush(annualAccountStatistics);

        int databaseSizeBeforeDelete = annualAccountStatisticsRepository.findAll().size();

        // Delete the annualAccountStatistics
        restAnnualAccountStatisticsMockMvc.perform(delete("/api/annual-account-statistics/{id}", annualAccountStatistics.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnnualAccountStatistics> annualAccountStatisticsList = annualAccountStatisticsRepository.findAll();
        assertThat(annualAccountStatisticsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnualAccountStatistics.class);
        AnnualAccountStatistics annualAccountStatistics1 = new AnnualAccountStatistics();
        annualAccountStatistics1.setId(1L);
        AnnualAccountStatistics annualAccountStatistics2 = new AnnualAccountStatistics();
        annualAccountStatistics2.setId(annualAccountStatistics1.getId());
        assertThat(annualAccountStatistics1).isEqualTo(annualAccountStatistics2);
        annualAccountStatistics2.setId(2L);
        assertThat(annualAccountStatistics1).isNotEqualTo(annualAccountStatistics2);
        annualAccountStatistics1.setId(null);
        assertThat(annualAccountStatistics1).isNotEqualTo(annualAccountStatistics2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnualAccountStatisticsDTO.class);
        AnnualAccountStatisticsDTO annualAccountStatisticsDTO1 = new AnnualAccountStatisticsDTO();
        annualAccountStatisticsDTO1.setId(1L);
        AnnualAccountStatisticsDTO annualAccountStatisticsDTO2 = new AnnualAccountStatisticsDTO();
        assertThat(annualAccountStatisticsDTO1).isNotEqualTo(annualAccountStatisticsDTO2);
        annualAccountStatisticsDTO2.setId(annualAccountStatisticsDTO1.getId());
        assertThat(annualAccountStatisticsDTO1).isEqualTo(annualAccountStatisticsDTO2);
        annualAccountStatisticsDTO2.setId(2L);
        assertThat(annualAccountStatisticsDTO1).isNotEqualTo(annualAccountStatisticsDTO2);
        annualAccountStatisticsDTO1.setId(null);
        assertThat(annualAccountStatisticsDTO1).isNotEqualTo(annualAccountStatisticsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(annualAccountStatisticsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(annualAccountStatisticsMapper.fromId(null)).isNull();
    }
}
