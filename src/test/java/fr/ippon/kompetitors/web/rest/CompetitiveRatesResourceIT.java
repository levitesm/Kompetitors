package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.CompetitiveRates;
import fr.ippon.kompetitors.repository.CompetitiveRatesRepository;
import fr.ippon.kompetitors.service.CompetitiveRateService;
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
import java.util.List;

import static fr.ippon.kompetitors.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CompetitiveRatesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class CompetitiveRatesResourceIT {

    private static final Double DEFAULT_TOTAL_RATE = 1D;
    private static final Double UPDATED_TOTAL_RATE = 2D;

    private static final Double DEFAULT_TECH_RATE = 1D;
    private static final Double UPDATED_TECH_RATE = 2D;

    private static final Double DEFAULT_FINANCE_RATE = 1D;
    private static final Double UPDATED_FINANCE_RATE = 2D;

    private static final Double DEFAULT_CLIENTS_RATE = 1D;
    private static final Double UPDATED_CLIENTS_RATE = 2D;

    private static final Double DEFAULT_HR_RATE = 1D;
    private static final Double UPDATED_HR_RATE = 2D;

    @Autowired
    private CompetitiveRatesRepository competitiveRatesRepository;

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

    private MockMvc restCompetitiveRatesMockMvc;

    private CompetitiveRates competitiveRates;
    private CompetitiveRateService competitiveRateService;
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitiveRatesResource competitiveRatesResource = new CompetitiveRatesResource( competitiveRatesRepository, competitiveRateService);
        this.restCompetitiveRatesMockMvc = MockMvcBuilders.standaloneSetup(competitiveRatesResource)
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
    public static CompetitiveRates createEntity(EntityManager em) {
        CompetitiveRates competitiveRates = new CompetitiveRates()
            .totalRate(DEFAULT_TOTAL_RATE)
            .techRate(DEFAULT_TECH_RATE)
            .financeRate(DEFAULT_FINANCE_RATE)
            .clientsRate(DEFAULT_CLIENTS_RATE)
            .hrRate(DEFAULT_HR_RATE);
        return competitiveRates;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitiveRates createUpdatedEntity(EntityManager em) {
        CompetitiveRates competitiveRates = new CompetitiveRates()
            .totalRate(UPDATED_TOTAL_RATE)
            .techRate(UPDATED_TECH_RATE)
            .financeRate(UPDATED_FINANCE_RATE)
            .clientsRate(UPDATED_CLIENTS_RATE)
            .hrRate(UPDATED_HR_RATE);
        return competitiveRates;
    }

    @BeforeEach
    public void initTest() {
        competitiveRates = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitiveRates() throws Exception {
        int databaseSizeBeforeCreate = competitiveRatesRepository.findAll().size();

        // Create the CompetitiveRates
        restCompetitiveRatesMockMvc.perform(post("/api/competitive-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitiveRates)))
            .andExpect(status().isCreated());

        // Validate the CompetitiveRates in the database
        List<CompetitiveRates> competitiveRatesList = competitiveRatesRepository.findAll();
        assertThat(competitiveRatesList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitiveRates testCompetitiveRates = competitiveRatesList.get(competitiveRatesList.size() - 1);
        assertThat(testCompetitiveRates.getTotalRate()).isEqualTo(DEFAULT_TOTAL_RATE);
        assertThat(testCompetitiveRates.getTechRate()).isEqualTo(DEFAULT_TECH_RATE);
        assertThat(testCompetitiveRates.getFinanceRate()).isEqualTo(DEFAULT_FINANCE_RATE);
        assertThat(testCompetitiveRates.getClientsRate()).isEqualTo(DEFAULT_CLIENTS_RATE);
        assertThat(testCompetitiveRates.getHrRate()).isEqualTo(DEFAULT_HR_RATE);
    }

    @Test
    @Transactional
    public void createCompetitiveRatesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitiveRatesRepository.findAll().size();

        // Create the CompetitiveRates with an existing ID
        competitiveRates.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitiveRatesMockMvc.perform(post("/api/competitive-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitiveRates)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitiveRates in the database
        List<CompetitiveRates> competitiveRatesList = competitiveRatesRepository.findAll();
        assertThat(competitiveRatesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompetitiveRates() throws Exception {
        // Initialize the database
        competitiveRatesRepository.saveAndFlush(competitiveRates);

        // Get all the competitiveRatesList
        restCompetitiveRatesMockMvc.perform(get("/api/competitive-rates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitiveRates.getId().intValue())))
            .andExpect(jsonPath("$.[*].totalRate").value(hasItem(DEFAULT_TOTAL_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].techRate").value(hasItem(DEFAULT_TECH_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].financeRate").value(hasItem(DEFAULT_FINANCE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].clientsRate").value(hasItem(DEFAULT_CLIENTS_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].hrRate").value(hasItem(DEFAULT_HR_RATE.doubleValue())));
    }

    @Test
    @Transactional
    public void getCompetitiveRates() throws Exception {
        // Initialize the database
        competitiveRatesRepository.saveAndFlush(competitiveRates);

        // Get the competitiveRates
        restCompetitiveRatesMockMvc.perform(get("/api/competitive-rates/{id}", competitiveRates.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitiveRates.getId().intValue()))
            .andExpect(jsonPath("$.totalRate").value(DEFAULT_TOTAL_RATE.doubleValue()))
            .andExpect(jsonPath("$.techRate").value(DEFAULT_TECH_RATE.doubleValue()))
            .andExpect(jsonPath("$.financeRate").value(DEFAULT_FINANCE_RATE.doubleValue()))
            .andExpect(jsonPath("$.clientsRate").value(DEFAULT_CLIENTS_RATE.doubleValue()))
            .andExpect(jsonPath("$.hrRate").value(DEFAULT_HR_RATE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitiveRates() throws Exception {
        // Get the competitiveRates
        restCompetitiveRatesMockMvc.perform(get("/api/competitive-rates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitiveRates() throws Exception {
        // Initialize the database
        competitiveRatesRepository.saveAndFlush(competitiveRates);

        int databaseSizeBeforeUpdate = competitiveRatesRepository.findAll().size();

        // Update the competitiveRates
        CompetitiveRates updatedCompetitiveRates = competitiveRatesRepository.findById(competitiveRates.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitiveRates are not directly saved in db
        em.detach(updatedCompetitiveRates);
        updatedCompetitiveRates
            .totalRate(UPDATED_TOTAL_RATE)
            .techRate(UPDATED_TECH_RATE)
            .financeRate(UPDATED_FINANCE_RATE)
            .clientsRate(UPDATED_CLIENTS_RATE)
            .hrRate(UPDATED_HR_RATE);

        restCompetitiveRatesMockMvc.perform(put("/api/competitive-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetitiveRates)))
            .andExpect(status().isOk());

        // Validate the CompetitiveRates in the database
        List<CompetitiveRates> competitiveRatesList = competitiveRatesRepository.findAll();
        assertThat(competitiveRatesList).hasSize(databaseSizeBeforeUpdate);
        CompetitiveRates testCompetitiveRates = competitiveRatesList.get(competitiveRatesList.size() - 1);
        assertThat(testCompetitiveRates.getTotalRate()).isEqualTo(UPDATED_TOTAL_RATE);
        assertThat(testCompetitiveRates.getTechRate()).isEqualTo(UPDATED_TECH_RATE);
        assertThat(testCompetitiveRates.getFinanceRate()).isEqualTo(UPDATED_FINANCE_RATE);
        assertThat(testCompetitiveRates.getClientsRate()).isEqualTo(UPDATED_CLIENTS_RATE);
        assertThat(testCompetitiveRates.getHrRate()).isEqualTo(UPDATED_HR_RATE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitiveRates() throws Exception {
        int databaseSizeBeforeUpdate = competitiveRatesRepository.findAll().size();

        // Create the CompetitiveRates

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitiveRatesMockMvc.perform(put("/api/competitive-rates")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitiveRates)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitiveRates in the database
        List<CompetitiveRates> competitiveRatesList = competitiveRatesRepository.findAll();
        assertThat(competitiveRatesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompetitiveRates() throws Exception {
        // Initialize the database
        competitiveRatesRepository.saveAndFlush(competitiveRates);

        int databaseSizeBeforeDelete = competitiveRatesRepository.findAll().size();

        // Delete the competitiveRates
        restCompetitiveRatesMockMvc.perform(delete("/api/competitive-rates/{id}", competitiveRates.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitiveRates> competitiveRatesList = competitiveRatesRepository.findAll();
        assertThat(competitiveRatesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitiveRates.class);
        CompetitiveRates competitiveRates1 = new CompetitiveRates();
        competitiveRates1.setId(1L);
        CompetitiveRates competitiveRates2 = new CompetitiveRates();
        competitiveRates2.setId(competitiveRates1.getId());
        assertThat(competitiveRates1).isEqualTo(competitiveRates2);
        competitiveRates2.setId(2L);
        assertThat(competitiveRates1).isNotEqualTo(competitiveRates2);
        competitiveRates1.setId(null);
        assertThat(competitiveRates1).isNotEqualTo(competitiveRates2);
    }
}
