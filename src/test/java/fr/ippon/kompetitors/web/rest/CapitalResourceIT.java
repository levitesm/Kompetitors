package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.Capital;
import fr.ippon.kompetitors.repository.CapitalRepository;
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
 * Integration tests for the {@link CapitalResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class CapitalResourceIT {

    private static final String DEFAULT_COMPETITOR_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITOR_SIREN = "BBBBBBBBBB";

    private static final Double DEFAULT_MONTANT = 1D;
    private static final Double UPDATED_MONTANT = 2D;

    private static final String DEFAULT_DEVISE = "AAAAAAAAAA";
    private static final String UPDATED_DEVISE = "BBBBBBBBBB";

    private static final Long DEFAULT_NBR_PARTS = 1L;
    private static final Long UPDATED_NBR_PARTS = 2L;

    private static final Double DEFAULT_POURCENTAGE_DETENTION_PP = 1D;
    private static final Double UPDATED_POURCENTAGE_DETENTION_PP = 2D;

    private static final Double DEFAULT_POURCENTAGE_DETENTION_PM = 1D;
    private static final Double UPDATED_POURCENTAGE_DETENTION_PM = 2D;

    private static final Boolean DEFAULT_LISTED = false;
    private static final Boolean UPDATED_LISTED = true;

    private static final Boolean DEFAULT_PRIVATE_CAPITAL = false;
    private static final Boolean UPDATED_PRIVATE_CAPITAL = true;

    private static final Boolean DEFAULT_INDEPENDENT_C = false;
    private static final Boolean UPDATED_INDEPENDENT_C = true;

    private static final Boolean DEFAULT_INDEPENDENT_E = false;
    private static final Boolean UPDATED_INDEPENDENT_E = true;

    private static final Boolean DEFAULT_OLD = false;
    private static final Boolean UPDATED_OLD = true;

    @Autowired
    private CapitalRepository capitalRepository;

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

    private MockMvc restCapitalMockMvc;

    private Capital capital;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CapitalResource capitalResource = new CapitalResource(capitalRepository);
        this.restCapitalMockMvc = MockMvcBuilders.standaloneSetup(capitalResource)
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
    public static Capital createEntity(EntityManager em) {
        Capital capital = new Capital()
            .competitorSiren(DEFAULT_COMPETITOR_SIREN)
            .montant(DEFAULT_MONTANT)
            .devise(DEFAULT_DEVISE)
            .nbrParts(DEFAULT_NBR_PARTS)
            .pourcentageDetentionPP(DEFAULT_POURCENTAGE_DETENTION_PP)
            .pourcentageDetentionPM(DEFAULT_POURCENTAGE_DETENTION_PM)
            .listed(DEFAULT_LISTED)
            .privateCapital(DEFAULT_PRIVATE_CAPITAL)
            .independentC(DEFAULT_INDEPENDENT_C)
            .independentE(DEFAULT_INDEPENDENT_E)
            .old(DEFAULT_OLD);
        return capital;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Capital createUpdatedEntity(EntityManager em) {
        Capital capital = new Capital()
            .competitorSiren(UPDATED_COMPETITOR_SIREN)
            .montant(UPDATED_MONTANT)
            .devise(UPDATED_DEVISE)
            .nbrParts(UPDATED_NBR_PARTS)
            .pourcentageDetentionPP(UPDATED_POURCENTAGE_DETENTION_PP)
            .pourcentageDetentionPM(UPDATED_POURCENTAGE_DETENTION_PM)
            .listed(UPDATED_LISTED)
            .privateCapital(UPDATED_PRIVATE_CAPITAL)
            .independentC(UPDATED_INDEPENDENT_C)
            .independentE(UPDATED_INDEPENDENT_E)
            .old(UPDATED_OLD);
        return capital;
    }

    @BeforeEach
    public void initTest() {
        capital = createEntity(em);
    }

    @Test
    @Transactional
    public void createCapital() throws Exception {
        int databaseSizeBeforeCreate = capitalRepository.findAll().size();

        // Create the Capital
        restCapitalMockMvc.perform(post("/api/capitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capital)))
            .andExpect(status().isCreated());

        // Validate the Capital in the database
        List<Capital> capitalList = capitalRepository.findAll();
        assertThat(capitalList).hasSize(databaseSizeBeforeCreate + 1);
        Capital testCapital = capitalList.get(capitalList.size() - 1);
        assertThat(testCapital.getCompetitorSiren()).isEqualTo(DEFAULT_COMPETITOR_SIREN);
        assertThat(testCapital.getMontant()).isEqualTo(DEFAULT_MONTANT);
        assertThat(testCapital.getDevise()).isEqualTo(DEFAULT_DEVISE);
        assertThat(testCapital.getNbrParts()).isEqualTo(DEFAULT_NBR_PARTS);
        assertThat(testCapital.getPourcentageDetentionPP()).isEqualTo(DEFAULT_POURCENTAGE_DETENTION_PP);
        assertThat(testCapital.getPourcentageDetentionPM()).isEqualTo(DEFAULT_POURCENTAGE_DETENTION_PM);
        assertThat(testCapital.isListed()).isEqualTo(DEFAULT_LISTED);
        assertThat(testCapital.isPrivateCapital()).isEqualTo(DEFAULT_PRIVATE_CAPITAL);
        assertThat(testCapital.isIndependentC()).isEqualTo(DEFAULT_INDEPENDENT_C);
        assertThat(testCapital.isIndependentE()).isEqualTo(DEFAULT_INDEPENDENT_E);
        assertThat(testCapital.isOld()).isEqualTo(DEFAULT_OLD);
    }

    @Test
    @Transactional
    public void createCapitalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = capitalRepository.findAll().size();

        // Create the Capital with an existing ID
        capital.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapitalMockMvc.perform(post("/api/capitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capital)))
            .andExpect(status().isBadRequest());

        // Validate the Capital in the database
        List<Capital> capitalList = capitalRepository.findAll();
        assertThat(capitalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCapitals() throws Exception {
        // Initialize the database
        capitalRepository.saveAndFlush(capital);

        // Get all the capitalList
        restCapitalMockMvc.perform(get("/api/capitals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capital.getId().intValue())))
            .andExpect(jsonPath("$.[*].competitorSiren").value(hasItem(DEFAULT_COMPETITOR_SIREN)))
            .andExpect(jsonPath("$.[*].montant").value(hasItem(DEFAULT_MONTANT.doubleValue())))
            .andExpect(jsonPath("$.[*].devise").value(hasItem(DEFAULT_DEVISE)))
            .andExpect(jsonPath("$.[*].nbrParts").value(hasItem(DEFAULT_NBR_PARTS.intValue())))
            .andExpect(jsonPath("$.[*].pourcentageDetentionPP").value(hasItem(DEFAULT_POURCENTAGE_DETENTION_PP.doubleValue())))
            .andExpect(jsonPath("$.[*].pourcentageDetentionPM").value(hasItem(DEFAULT_POURCENTAGE_DETENTION_PM.doubleValue())))
            .andExpect(jsonPath("$.[*].listed").value(hasItem(DEFAULT_LISTED.booleanValue())))
            .andExpect(jsonPath("$.[*].privateCapital").value(hasItem(DEFAULT_PRIVATE_CAPITAL.booleanValue())))
            .andExpect(jsonPath("$.[*].independentC").value(hasItem(DEFAULT_INDEPENDENT_C.booleanValue())))
            .andExpect(jsonPath("$.[*].independentE").value(hasItem(DEFAULT_INDEPENDENT_E.booleanValue())))
            .andExpect(jsonPath("$.[*].old").value(hasItem(DEFAULT_OLD.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getCapital() throws Exception {
        // Initialize the database
        capitalRepository.saveAndFlush(capital);

        // Get the capital
        restCapitalMockMvc.perform(get("/api/capitals/{id}", capital.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(capital.getId().intValue()))
            .andExpect(jsonPath("$.competitorSiren").value(DEFAULT_COMPETITOR_SIREN))
            .andExpect(jsonPath("$.montant").value(DEFAULT_MONTANT.doubleValue()))
            .andExpect(jsonPath("$.devise").value(DEFAULT_DEVISE))
            .andExpect(jsonPath("$.nbrParts").value(DEFAULT_NBR_PARTS.intValue()))
            .andExpect(jsonPath("$.pourcentageDetentionPP").value(DEFAULT_POURCENTAGE_DETENTION_PP.doubleValue()))
            .andExpect(jsonPath("$.pourcentageDetentionPM").value(DEFAULT_POURCENTAGE_DETENTION_PM.doubleValue()))
            .andExpect(jsonPath("$.listed").value(DEFAULT_LISTED.booleanValue()))
            .andExpect(jsonPath("$.privateCapital").value(DEFAULT_PRIVATE_CAPITAL.booleanValue()))
            .andExpect(jsonPath("$.independentC").value(DEFAULT_INDEPENDENT_C.booleanValue()))
            .andExpect(jsonPath("$.independentE").value(DEFAULT_INDEPENDENT_E.booleanValue()))
            .andExpect(jsonPath("$.old").value(DEFAULT_OLD.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCapital() throws Exception {
        // Get the capital
        restCapitalMockMvc.perform(get("/api/capitals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCapital() throws Exception {
        // Initialize the database
        capitalRepository.saveAndFlush(capital);

        int databaseSizeBeforeUpdate = capitalRepository.findAll().size();

        // Update the capital
        Capital updatedCapital = capitalRepository.findById(capital.getId()).get();
        // Disconnect from session so that the updates on updatedCapital are not directly saved in db
        em.detach(updatedCapital);
        updatedCapital
            .competitorSiren(UPDATED_COMPETITOR_SIREN)
            .montant(UPDATED_MONTANT)
            .devise(UPDATED_DEVISE)
            .nbrParts(UPDATED_NBR_PARTS)
            .pourcentageDetentionPP(UPDATED_POURCENTAGE_DETENTION_PP)
            .pourcentageDetentionPM(UPDATED_POURCENTAGE_DETENTION_PM)
            .listed(UPDATED_LISTED)
            .privateCapital(UPDATED_PRIVATE_CAPITAL)
            .independentC(UPDATED_INDEPENDENT_C)
            .independentE(UPDATED_INDEPENDENT_E)
            .old(UPDATED_OLD);

        restCapitalMockMvc.perform(put("/api/capitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCapital)))
            .andExpect(status().isOk());

        // Validate the Capital in the database
        List<Capital> capitalList = capitalRepository.findAll();
        assertThat(capitalList).hasSize(databaseSizeBeforeUpdate);
        Capital testCapital = capitalList.get(capitalList.size() - 1);
        assertThat(testCapital.getCompetitorSiren()).isEqualTo(UPDATED_COMPETITOR_SIREN);
        assertThat(testCapital.getMontant()).isEqualTo(UPDATED_MONTANT);
        assertThat(testCapital.getDevise()).isEqualTo(UPDATED_DEVISE);
        assertThat(testCapital.getNbrParts()).isEqualTo(UPDATED_NBR_PARTS);
        assertThat(testCapital.getPourcentageDetentionPP()).isEqualTo(UPDATED_POURCENTAGE_DETENTION_PP);
        assertThat(testCapital.getPourcentageDetentionPM()).isEqualTo(UPDATED_POURCENTAGE_DETENTION_PM);
        assertThat(testCapital.isListed()).isEqualTo(UPDATED_LISTED);
        assertThat(testCapital.isPrivateCapital()).isEqualTo(UPDATED_PRIVATE_CAPITAL);
        assertThat(testCapital.isIndependentC()).isEqualTo(UPDATED_INDEPENDENT_C);
        assertThat(testCapital.isIndependentE()).isEqualTo(UPDATED_INDEPENDENT_E);
        assertThat(testCapital.isOld()).isEqualTo(UPDATED_OLD);
    }

    @Test
    @Transactional
    public void updateNonExistingCapital() throws Exception {
        int databaseSizeBeforeUpdate = capitalRepository.findAll().size();

        // Create the Capital

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapitalMockMvc.perform(put("/api/capitals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capital)))
            .andExpect(status().isBadRequest());

        // Validate the Capital in the database
        List<Capital> capitalList = capitalRepository.findAll();
        assertThat(capitalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCapital() throws Exception {
        // Initialize the database
        capitalRepository.saveAndFlush(capital);

        int databaseSizeBeforeDelete = capitalRepository.findAll().size();

        // Delete the capital
        restCapitalMockMvc.perform(delete("/api/capitals/{id}", capital.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Capital> capitalList = capitalRepository.findAll();
        assertThat(capitalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Capital.class);
        Capital capital1 = new Capital();
        capital1.setId(1L);
        Capital capital2 = new Capital();
        capital2.setId(capital1.getId());
        assertThat(capital1).isEqualTo(capital2);
        capital2.setId(2L);
        assertThat(capital1).isNotEqualTo(capital2);
        capital1.setId(null);
        assertThat(capital1).isNotEqualTo(capital2);
    }
}
