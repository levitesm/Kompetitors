package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.Competitors;
import fr.ippon.kompetitors.repository.CompetitorsRepository;
import fr.ippon.kompetitors.repository.LegalRepository;
import fr.ippon.kompetitors.service.AnnualAccountStatisticsFetchService;
import fr.ippon.kompetitors.service.CompetitorService;
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
 * Integration tests for the {@link CompetitorsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class CompetitorsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_WEB_SITE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY_PHONE = "BBBBBBBBBB";

    @Autowired
    private CompetitorsRepository competitorsRepository;

    @Autowired
    private CompetitorService competitorService;

    @Autowired
    private LegalRepository legalRepository;

    @Autowired
    private AnnualAccountStatisticsFetchService annualAccountStatisticsFetchService;

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

    private MockMvc restCompetitorsMockMvc;

    private Competitors competitors;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitorsResource competitorsResource = new CompetitorsResource(competitorsRepository, competitorService,
            legalRepository, annualAccountStatisticsFetchService);
        this.restCompetitorsMockMvc = MockMvcBuilders.standaloneSetup(competitorsResource)
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
    public static Competitors createEntity(EntityManager em) {
        Competitors competitors = new Competitors()
            .name(DEFAULT_NAME)
            .webSite(DEFAULT_WEB_SITE)
            .countryPhone(DEFAULT_COUNTRY_PHONE);
        return competitors;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Competitors createUpdatedEntity(EntityManager em) {
        Competitors competitors = new Competitors()
            .name(UPDATED_NAME)
            .webSite(UPDATED_WEB_SITE)
            .countryPhone(UPDATED_COUNTRY_PHONE);
        return competitors;
    }

    @BeforeEach
    public void initTest() {
        competitors = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitors() throws Exception {
        int databaseSizeBeforeCreate = competitorsRepository.findAll().size();

        // Create the Competitors
        restCompetitorsMockMvc.perform(post("/api/competitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitors)))
            .andExpect(status().isCreated());

        // Validate the Competitors in the database
        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeCreate + 1);
        Competitors testCompetitors = competitorsList.get(competitorsList.size() - 1);
        assertThat(testCompetitors.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCompetitors.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testCompetitors.getCountryPhone()).isEqualTo(DEFAULT_COUNTRY_PHONE);
    }

    @Test
    @Transactional
    public void createCompetitorsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitorsRepository.findAll().size();

        // Create the Competitors with an existing ID
        competitors.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitorsMockMvc.perform(post("/api/competitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitors)))
            .andExpect(status().isBadRequest());

        // Validate the Competitors in the database
        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompetitors() throws Exception {
        // Initialize the database
        competitorsRepository.saveAndFlush(competitors);

        // Get all the competitorsList
        restCompetitorsMockMvc.perform(get("/api/competitors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitors.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].countryPhone").value(hasItem(DEFAULT_COUNTRY_PHONE)));
    }

    @Test
    @Transactional
    public void getCompetitors() throws Exception {
        // Initialize the database
        competitorsRepository.saveAndFlush(competitors);

        // Get the competitors
        restCompetitorsMockMvc.perform(get("/api/competitors/{id}", competitors.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitors.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE))
            .andExpect(jsonPath("$.countryPhone").value(DEFAULT_COUNTRY_PHONE));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitors() throws Exception {
        // Get the competitors
        restCompetitorsMockMvc.perform(get("/api/competitors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitors() throws Exception {
        // Initialize the database
        competitorsRepository.saveAndFlush(competitors);

        int databaseSizeBeforeUpdate = competitorsRepository.findAll().size();

        // Update the competitors
        Competitors updatedCompetitors = competitorsRepository.findById(competitors.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitors are not directly saved in db
        em.detach(updatedCompetitors);
        updatedCompetitors
            .name(UPDATED_NAME)
            .webSite(UPDATED_WEB_SITE)
            .countryPhone(UPDATED_COUNTRY_PHONE);

        restCompetitorsMockMvc.perform(put("/api/competitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCompetitors)))
            .andExpect(status().isOk());

        // Validate the Competitors in the database
        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeUpdate);
        Competitors testCompetitors = competitorsList.get(competitorsList.size() - 1);
        assertThat(testCompetitors.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCompetitors.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testCompetitors.getCountryPhone()).isEqualTo(UPDATED_COUNTRY_PHONE);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitors() throws Exception {
        int databaseSizeBeforeUpdate = competitorsRepository.findAll().size();

        // Create the Competitors

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitorsMockMvc.perform(put("/api/competitors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitors)))
            .andExpect(status().isBadRequest());

        // Validate the Competitors in the database
        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompetitors() throws Exception {
        // Initialize the database
        competitorsRepository.saveAndFlush(competitors);

        int databaseSizeBeforeDelete = competitorsRepository.findAll().size();

        // Delete the competitors
        restCompetitorsMockMvc.perform(delete("/api/competitors/{id}", competitors.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Competitors> competitorsList = competitorsRepository.findAll();
        assertThat(competitorsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Competitors.class);
        Competitors competitors1 = new Competitors();
        competitors1.setId(1L);
        Competitors competitors2 = new Competitors();
        competitors2.setId(competitors1.getId());
        assertThat(competitors1).isEqualTo(competitors2);
        competitors2.setId(2L);
        assertThat(competitors1).isNotEqualTo(competitors2);
        competitors1.setId(null);
        assertThat(competitors1).isNotEqualTo(competitors2);
    }
}
