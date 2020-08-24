package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.Representatives;
import fr.ippon.kompetitors.repository.RepresentativesRepository;
import fr.ippon.kompetitors.service.InfogreffeService;
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
 * Integration tests for the {@link RepresentativesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class RepresentativesResourceIT {

    private static final String DEFAULT_COMPETITOR_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITOR_SIREN = "BBBBBBBBBB";

    private static final String DEFAULT_QUALITE = "AAAAAAAAAA";
    private static final String UPDATED_QUALITE = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM = "AAAAAAAAAA";
    private static final String UPDATED_NOM = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_USAGE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_USAGE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_NAISSANCE = "BBBBBBBBBB";

    private static final String DEFAULT_DENOMINATION_PM = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINATION_PM = "BBBBBBBBBB";

    private static final String DEFAULT_SIREN_PM = "AAAAAAAAAA";
    private static final String UPDATED_SIREN_PM = "BBBBBBBBBB";

    private static final String DEFAULT_LINKED_IN_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_IN_URL = "BBBBBBBBBB";

    private static final Boolean DEFAULT_OLD = false;
    private static final Boolean UPDATED_OLD = true;

    @Autowired
    private RepresentativesRepository representativesRepository;

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

    private MockMvc restRepresentativesMockMvc;

    private Representatives representatives;

    private InfogreffeService infogreffeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RepresentativesResource representativesResource = new RepresentativesResource(representativesRepository, infogreffeService);
        this.restRepresentativesMockMvc = MockMvcBuilders.standaloneSetup(representativesResource)
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
    public static Representatives createEntity(EntityManager em) {
        Representatives representatives = new Representatives()
            .competitorSiren(DEFAULT_COMPETITOR_SIREN)
            .qualite(DEFAULT_QUALITE)
            .type(DEFAULT_TYPE)
            .nom(DEFAULT_NOM)
            .prenom(DEFAULT_PRENOM)
            .nomUsage(DEFAULT_NOM_USAGE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .denominationPM(DEFAULT_DENOMINATION_PM)
            .sirenPM(DEFAULT_SIREN_PM)
            .linkedInUrl(DEFAULT_LINKED_IN_URL)
            .old(DEFAULT_OLD);
        return representatives;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Representatives createUpdatedEntity(EntityManager em) {
        Representatives representatives = new Representatives()
            .competitorSiren(UPDATED_COMPETITOR_SIREN)
            .qualite(UPDATED_QUALITE)
            .type(UPDATED_TYPE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .nomUsage(UPDATED_NOM_USAGE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .denominationPM(UPDATED_DENOMINATION_PM)
            .sirenPM(UPDATED_SIREN_PM)
            .linkedInUrl(UPDATED_LINKED_IN_URL)
            .old(UPDATED_OLD);
        return representatives;
    }

    @BeforeEach
    public void initTest() {
        representatives = createEntity(em);
    }

    @Test
    @Transactional
    public void createRepresentatives() throws Exception {
        int databaseSizeBeforeCreate = representativesRepository.findAll().size();

        // Create the Representatives
        restRepresentativesMockMvc.perform(post("/api/representatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(representatives)))
            .andExpect(status().isCreated());

        // Validate the Representatives in the database
        List<Representatives> representativesList = representativesRepository.findAll();
        assertThat(representativesList).hasSize(databaseSizeBeforeCreate + 1);
        Representatives testRepresentatives = representativesList.get(representativesList.size() - 1);
        assertThat(testRepresentatives.getCompetitorSiren()).isEqualTo(DEFAULT_COMPETITOR_SIREN);
        assertThat(testRepresentatives.getQualite()).isEqualTo(DEFAULT_QUALITE);
        assertThat(testRepresentatives.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testRepresentatives.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testRepresentatives.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testRepresentatives.getNomUsage()).isEqualTo(DEFAULT_NOM_USAGE);
        assertThat(testRepresentatives.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testRepresentatives.getDenominationPM()).isEqualTo(DEFAULT_DENOMINATION_PM);
        assertThat(testRepresentatives.getSirenPM()).isEqualTo(DEFAULT_SIREN_PM);
        assertThat(testRepresentatives.getLinkedInUrl()).isEqualTo(DEFAULT_LINKED_IN_URL);
        assertThat(testRepresentatives.isOld()).isEqualTo(DEFAULT_OLD);
    }

    @Test
    @Transactional
    public void createRepresentativesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = representativesRepository.findAll().size();

        // Create the Representatives with an existing ID
        representatives.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRepresentativesMockMvc.perform(post("/api/representatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(representatives)))
            .andExpect(status().isBadRequest());

        // Validate the Representatives in the database
        List<Representatives> representativesList = representativesRepository.findAll();
        assertThat(representativesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRepresentatives() throws Exception {
        // Initialize the database
        representativesRepository.saveAndFlush(representatives);

        // Get all the representativesList
        restRepresentativesMockMvc.perform(get("/api/representatives?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(representatives.getId().intValue())))
            .andExpect(jsonPath("$.[*].competitorSiren").value(hasItem(DEFAULT_COMPETITOR_SIREN)))
            .andExpect(jsonPath("$.[*].qualite").value(hasItem(DEFAULT_QUALITE)))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].nomUsage").value(hasItem(DEFAULT_NOM_USAGE)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE)))
            .andExpect(jsonPath("$.[*].denominationPM").value(hasItem(DEFAULT_DENOMINATION_PM)))
            .andExpect(jsonPath("$.[*].sirenPM").value(hasItem(DEFAULT_SIREN_PM)))
            .andExpect(jsonPath("$.[*].linkedInUrl").value(hasItem(DEFAULT_LINKED_IN_URL)))
            .andExpect(jsonPath("$.[*].old").value(hasItem(DEFAULT_OLD.booleanValue())));
    }

    @Test
    @Transactional
    public void getRepresentatives() throws Exception {
        // Initialize the database
        representativesRepository.saveAndFlush(representatives);

        // Get the representatives
        restRepresentativesMockMvc.perform(get("/api/representatives/{id}", representatives.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(representatives.getId().intValue()))
            .andExpect(jsonPath("$.competitorSiren").value(DEFAULT_COMPETITOR_SIREN))
            .andExpect(jsonPath("$.qualite").value(DEFAULT_QUALITE))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.nom").value(DEFAULT_NOM))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.nomUsage").value(DEFAULT_NOM_USAGE))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE))
            .andExpect(jsonPath("$.denominationPM").value(DEFAULT_DENOMINATION_PM))
            .andExpect(jsonPath("$.sirenPM").value(DEFAULT_SIREN_PM))
            .andExpect(jsonPath("$.linkedInUrl").value(DEFAULT_LINKED_IN_URL))
            .andExpect(jsonPath("$.old").value(DEFAULT_OLD.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRepresentatives() throws Exception {
        // Get the representatives
        restRepresentativesMockMvc.perform(get("/api/representatives/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRepresentatives() throws Exception {
        // Initialize the database
        representativesRepository.saveAndFlush(representatives);

        int databaseSizeBeforeUpdate = representativesRepository.findAll().size();

        // Update the representatives
        Representatives updatedRepresentatives = representativesRepository.findById(representatives.getId()).get();
        // Disconnect from session so that the updates on updatedRepresentatives are not directly saved in db
        em.detach(updatedRepresentatives);
        updatedRepresentatives
            .competitorSiren(UPDATED_COMPETITOR_SIREN)
            .qualite(UPDATED_QUALITE)
            .type(UPDATED_TYPE)
            .nom(UPDATED_NOM)
            .prenom(UPDATED_PRENOM)
            .nomUsage(UPDATED_NOM_USAGE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .denominationPM(UPDATED_DENOMINATION_PM)
            .sirenPM(UPDATED_SIREN_PM)
            .linkedInUrl(UPDATED_LINKED_IN_URL)
            .old(UPDATED_OLD);

        restRepresentativesMockMvc.perform(put("/api/representatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRepresentatives)))
            .andExpect(status().isOk());

        // Validate the Representatives in the database
        List<Representatives> representativesList = representativesRepository.findAll();
        assertThat(representativesList).hasSize(databaseSizeBeforeUpdate);
        Representatives testRepresentatives = representativesList.get(representativesList.size() - 1);
        assertThat(testRepresentatives.getCompetitorSiren()).isEqualTo(UPDATED_COMPETITOR_SIREN);
        assertThat(testRepresentatives.getQualite()).isEqualTo(UPDATED_QUALITE);
        assertThat(testRepresentatives.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testRepresentatives.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testRepresentatives.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testRepresentatives.getNomUsage()).isEqualTo(UPDATED_NOM_USAGE);
        assertThat(testRepresentatives.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testRepresentatives.getDenominationPM()).isEqualTo(UPDATED_DENOMINATION_PM);
        assertThat(testRepresentatives.getSirenPM()).isEqualTo(UPDATED_SIREN_PM);
        assertThat(testRepresentatives.getLinkedInUrl()).isEqualTo(UPDATED_LINKED_IN_URL);
        assertThat(testRepresentatives.isOld()).isEqualTo(UPDATED_OLD);
    }

    @Test
    @Transactional
    public void updateNonExistingRepresentatives() throws Exception {
        int databaseSizeBeforeUpdate = representativesRepository.findAll().size();

        // Create the Representatives

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRepresentativesMockMvc.perform(put("/api/representatives")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(representatives)))
            .andExpect(status().isBadRequest());

        // Validate the Representatives in the database
        List<Representatives> representativesList = representativesRepository.findAll();
        assertThat(representativesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRepresentatives() throws Exception {
        // Initialize the database
        representativesRepository.saveAndFlush(representatives);

        int databaseSizeBeforeDelete = representativesRepository.findAll().size();

        // Delete the representatives
        restRepresentativesMockMvc.perform(delete("/api/representatives/{id}", representatives.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Representatives> representativesList = representativesRepository.findAll();
        assertThat(representativesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Representatives.class);
        Representatives representatives1 = new Representatives();
        representatives1.setId(1L);
        Representatives representatives2 = new Representatives();
        representatives2.setId(representatives1.getId());
        assertThat(representatives1).isEqualTo(representatives2);
        representatives2.setId(2L);
        assertThat(representatives1).isNotEqualTo(representatives2);
        representatives1.setId(null);
        assertThat(representatives1).isNotEqualTo(representatives2);
    }
}
