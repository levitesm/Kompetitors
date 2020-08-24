package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.Legal;
import fr.ippon.kompetitors.repository.LegalRepository;
import fr.ippon.kompetitors.service.SocieteService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static fr.ippon.kompetitors.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link LegalResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class LegalResourceIT {

    private static final String DEFAULT_LEGAL_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_SIREN = "BBBBBBBBBB";

    private static final String DEFAULT_GREFFE = "AAAAAAAAAA";
    private static final String UPDATED_GREFFE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_FOUNDED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FOUNDED = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_LEGAL_FORM = "AAAAAAAAAA";
    private static final String UPDATED_LEGAL_FORM = "BBBBBBBBBB";

    @Autowired
    private LegalRepository legalRepository;

    @Autowired
    private SocieteService societeService;

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

    private MockMvc restLegalMockMvc;

    private Legal legal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final LegalResource legalResource = new LegalResource(legalRepository, societeService);
        this.restLegalMockMvc = MockMvcBuilders.standaloneSetup(legalResource)
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
    public static Legal createEntity(EntityManager em) {
        Legal legal = new Legal()
            .legalAddress(DEFAULT_LEGAL_ADDRESS)
            .siren(DEFAULT_SIREN)
            .greffe(DEFAULT_GREFFE)
            .founded(DEFAULT_FOUNDED)
            .updateDate(DEFAULT_UPDATE_DATE)
            .legalForm(DEFAULT_LEGAL_FORM);
        return legal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Legal createUpdatedEntity(EntityManager em) {
        Legal legal = new Legal()
            .legalAddress(UPDATED_LEGAL_ADDRESS)
            .siren(UPDATED_SIREN)
            .greffe(UPDATED_GREFFE)
            .founded(UPDATED_FOUNDED)
            .updateDate(UPDATED_UPDATE_DATE)
            .legalForm(UPDATED_LEGAL_FORM);
        return legal;
    }

    @BeforeEach
    public void initTest() {
        legal = createEntity(em);
    }

    @Test
    @Transactional
    public void createLegal() throws Exception {
        int databaseSizeBeforeCreate = legalRepository.findAll().size();

        // Create the Legal
        restLegalMockMvc.perform(post("/api/legals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legal)))
            .andExpect(status().isCreated());

        // Validate the Legal in the database
        List<Legal> legalList = legalRepository.findAll();
        assertThat(legalList).hasSize(databaseSizeBeforeCreate + 1);
        Legal testLegal = legalList.get(legalList.size() - 1);
        assertThat(testLegal.getLegalAddress()).isEqualTo(DEFAULT_LEGAL_ADDRESS);
        assertThat(testLegal.getSiren()).isEqualTo(DEFAULT_SIREN);
        assertThat(testLegal.getGreffe()).isEqualTo(DEFAULT_GREFFE);
        assertThat(testLegal.getFounded()).isEqualTo(DEFAULT_FOUNDED);
        assertThat(testLegal.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testLegal.getLegalForm()).isEqualTo(DEFAULT_LEGAL_FORM);
    }

    @Test
    @Transactional
    public void createLegalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = legalRepository.findAll().size();

        // Create the Legal with an existing ID
        legal.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLegalMockMvc.perform(post("/api/legals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legal)))
            .andExpect(status().isBadRequest());

        // Validate the Legal in the database
        List<Legal> legalList = legalRepository.findAll();
        assertThat(legalList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLegals() throws Exception {
        // Initialize the database
        legalRepository.saveAndFlush(legal);

        // Get all the legalList
        restLegalMockMvc.perform(get("/api/legals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(legal.getId().intValue())))
            .andExpect(jsonPath("$.[*].legalAddress").value(hasItem(DEFAULT_LEGAL_ADDRESS)))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN)))
            .andExpect(jsonPath("$.[*].greffe").value(hasItem(DEFAULT_GREFFE)))
            .andExpect(jsonPath("$.[*].founded").value(hasItem(DEFAULT_FOUNDED.toString())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].legalForm").value(hasItem(DEFAULT_LEGAL_FORM)));
    }
    
    @Test
    @Transactional
    public void getLegal() throws Exception {
        // Initialize the database
        legalRepository.saveAndFlush(legal);

        // Get the legal
        restLegalMockMvc.perform(get("/api/legals/{id}", legal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(legal.getId().intValue()))
            .andExpect(jsonPath("$.legalAddress").value(DEFAULT_LEGAL_ADDRESS))
            .andExpect(jsonPath("$.siren").value(DEFAULT_SIREN))
            .andExpect(jsonPath("$.greffe").value(DEFAULT_GREFFE))
            .andExpect(jsonPath("$.founded").value(DEFAULT_FOUNDED.toString()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.legalForm").value(DEFAULT_LEGAL_FORM));
    }

    @Test
    @Transactional
    public void getNonExistingLegal() throws Exception {
        // Get the legal
        restLegalMockMvc.perform(get("/api/legals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLegal() throws Exception {
        // Initialize the database
        legalRepository.saveAndFlush(legal);

        int databaseSizeBeforeUpdate = legalRepository.findAll().size();

        // Update the legal
        Legal updatedLegal = legalRepository.findById(legal.getId()).get();
        // Disconnect from session so that the updates on updatedLegal are not directly saved in db
        em.detach(updatedLegal);
        updatedLegal
            .legalAddress(UPDATED_LEGAL_ADDRESS)
            .siren(UPDATED_SIREN)
            .greffe(UPDATED_GREFFE)
            .founded(UPDATED_FOUNDED)
            .updateDate(UPDATED_UPDATE_DATE)
            .legalForm(UPDATED_LEGAL_FORM);

        restLegalMockMvc.perform(put("/api/legals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedLegal)))
            .andExpect(status().isOk());

        // Validate the Legal in the database
        List<Legal> legalList = legalRepository.findAll();
        assertThat(legalList).hasSize(databaseSizeBeforeUpdate);
        Legal testLegal = legalList.get(legalList.size() - 1);
        assertThat(testLegal.getLegalAddress()).isEqualTo(UPDATED_LEGAL_ADDRESS);
        assertThat(testLegal.getSiren()).isEqualTo(UPDATED_SIREN);
        assertThat(testLegal.getGreffe()).isEqualTo(UPDATED_GREFFE);
        assertThat(testLegal.getFounded()).isEqualTo(UPDATED_FOUNDED);
        assertThat(testLegal.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testLegal.getLegalForm()).isEqualTo(UPDATED_LEGAL_FORM);
    }

    @Test
    @Transactional
    public void updateNonExistingLegal() throws Exception {
        int databaseSizeBeforeUpdate = legalRepository.findAll().size();

        // Create the Legal

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLegalMockMvc.perform(put("/api/legals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(legal)))
            .andExpect(status().isBadRequest());

        // Validate the Legal in the database
        List<Legal> legalList = legalRepository.findAll();
        assertThat(legalList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLegal() throws Exception {
        // Initialize the database
        legalRepository.saveAndFlush(legal);

        int databaseSizeBeforeDelete = legalRepository.findAll().size();

        // Delete the legal
        restLegalMockMvc.perform(delete("/api/legals/{id}", legal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Legal> legalList = legalRepository.findAll();
        assertThat(legalList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Legal.class);
        Legal legal1 = new Legal();
        legal1.setId(1L);
        Legal legal2 = new Legal();
        legal2.setId(legal1.getId());
        assertThat(legal1).isEqualTo(legal2);
        legal2.setId(2L);
        assertThat(legal1).isNotEqualTo(legal2);
        legal1.setId(null);
        assertThat(legal1).isNotEqualTo(legal2);
    }
}
