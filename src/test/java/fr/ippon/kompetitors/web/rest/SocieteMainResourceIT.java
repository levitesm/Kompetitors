package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.SocieteMain;
import fr.ippon.kompetitors.repository.SocieteMainRepository;
import fr.ippon.kompetitors.service.AnnualAccountStatisticsFetchService;
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
 * Integration tests for the {@link SocieteMainResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class SocieteMainResourceIT {

    private static final String DEFAULT_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_SIREN = "BBBBBBBBBB";

    private static final String DEFAULT_DENO = "AAAAAAAAAA";
    private static final String UPDATED_DENO = "BBBBBBBBBB";

    private static final String DEFAULT_GREFFE = "AAAAAAAAAA";
    private static final String UPDATED_GREFFE = "BBBBBBBBBB";

    private static final String DEFAULT_ENSEIGNE = "AAAAAAAAAA";
    private static final String UPDATED_ENSEIGNE = "BBBBBBBBBB";

    private static final String DEFAULT_PSIRET = "AAAAAAAAAA";
    private static final String UPDATED_PSIRET = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_CODEPOSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODEPOSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_NORMCOMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_NORMCOMMUNE = "BBBBBBBBBB";

    private static final String DEFAULT_COMMUNE = "AAAAAAAAAA";
    private static final String UPDATED_COMMUNE = "BBBBBBBBBB";

    private static final String DEFAULT_APE = "AAAAAAAAAA";
    private static final String UPDATED_APE = "BBBBBBBBBB";

    private static final String DEFAULT_APETEXTE = "AAAAAAAAAA";
    private static final String UPDATED_APETEXTE = "BBBBBBBBBB";

    private static final String DEFAULT_DATEIMMAT = "AAAAAAAAAA";
    private static final String UPDATED_DATEIMMAT = "BBBBBBBBBB";

    private static final String DEFAULT_DCREN = "AAAAAAAAAA";
    private static final String UPDATED_DCREN = "BBBBBBBBBB";

    private static final String DEFAULT_NATIONALITE = "AAAAAAAAAA";
    private static final String UPDATED_NATIONALITE = "BBBBBBBBBB";

    private static final String DEFAULT_FORMEJUR = "AAAAAAAAAA";
    private static final String UPDATED_FORMEJUR = "BBBBBBBBBB";

    private static final String DEFAULT_CAPITAL = "AAAAAAAAAA";
    private static final String UPDATED_CAPITAL = "BBBBBBBBBB";

    private static final String DEFAULT_DEVISECAP = "AAAAAAAAAA";
    private static final String UPDATED_DEVISECAP = "BBBBBBBBBB";

    private static final String DEFAULT_TYPECAP = "AAAAAAAAAA";
    private static final String UPDATED_TYPECAP = "BBBBBBBBBB";

    private static final String DEFAULT_URL = "AAAAAAAAAA";
    private static final String UPDATED_URL = "BBBBBBBBBB";

    @Autowired
    private SocieteMainRepository societeMainRepository;

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

    private MockMvc restSocieteMainMockMvc;

    private SocieteMain societeMain;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SocieteMainResource societeMainResource = new SocieteMainResource(societeMainRepository);
        this.restSocieteMainMockMvc = MockMvcBuilders.standaloneSetup(societeMainResource)
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
    public static SocieteMain createEntity(EntityManager em) {
        SocieteMain societeMain = new SocieteMain()
            .siren(DEFAULT_SIREN)
            .deno(DEFAULT_DENO)
            .greffe(DEFAULT_GREFFE)
            .enseigne(DEFAULT_ENSEIGNE)
            .psiret(DEFAULT_PSIRET)
            .adresse(DEFAULT_ADRESSE)
            .codepostal(DEFAULT_CODEPOSTAL)
            .normcommune(DEFAULT_NORMCOMMUNE)
            .commune(DEFAULT_COMMUNE)
            .ape(DEFAULT_APE)
            .apetexte(DEFAULT_APETEXTE)
            .dateimmat(DEFAULT_DATEIMMAT)
            .dcren(DEFAULT_DCREN)
            .nationalite(DEFAULT_NATIONALITE)
            .formejur(DEFAULT_FORMEJUR)
            .capital(DEFAULT_CAPITAL)
            .devisecap(DEFAULT_DEVISECAP)
            .typecap(DEFAULT_TYPECAP)
            .url(DEFAULT_URL);
        return societeMain;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SocieteMain createUpdatedEntity(EntityManager em) {
        SocieteMain societeMain = new SocieteMain()
            .siren(UPDATED_SIREN)
            .deno(UPDATED_DENO)
            .greffe(UPDATED_GREFFE)
            .enseigne(UPDATED_ENSEIGNE)
            .psiret(UPDATED_PSIRET)
            .adresse(UPDATED_ADRESSE)
            .codepostal(UPDATED_CODEPOSTAL)
            .normcommune(UPDATED_NORMCOMMUNE)
            .commune(UPDATED_COMMUNE)
            .ape(UPDATED_APE)
            .apetexte(UPDATED_APETEXTE)
            .dateimmat(UPDATED_DATEIMMAT)
            .dcren(UPDATED_DCREN)
            .nationalite(UPDATED_NATIONALITE)
            .formejur(UPDATED_FORMEJUR)
            .capital(UPDATED_CAPITAL)
            .devisecap(UPDATED_DEVISECAP)
            .typecap(UPDATED_TYPECAP)
            .url(UPDATED_URL);
        return societeMain;
    }

    @BeforeEach
    public void initTest() {
        societeMain = createEntity(em);
    }

    @Test
    @Transactional
    public void createSocieteMain() throws Exception {
        int databaseSizeBeforeCreate = societeMainRepository.findAll().size();

        // Create the SocieteMain
        restSocieteMainMockMvc.perform(post("/api/societe-mains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(societeMain)))
            .andExpect(status().isCreated());

        // Validate the SocieteMain in the database
        List<SocieteMain> societeMainList = societeMainRepository.findAll();
        assertThat(societeMainList).hasSize(databaseSizeBeforeCreate + 1);
        SocieteMain testSocieteMain = societeMainList.get(societeMainList.size() - 1);
        assertThat(testSocieteMain.getSiren()).isEqualTo(DEFAULT_SIREN);
        assertThat(testSocieteMain.getDeno()).isEqualTo(DEFAULT_DENO);
        assertThat(testSocieteMain.getGreffe()).isEqualTo(DEFAULT_GREFFE);
        assertThat(testSocieteMain.getEnseigne()).isEqualTo(DEFAULT_ENSEIGNE);
        assertThat(testSocieteMain.getPsiret()).isEqualTo(DEFAULT_PSIRET);
        assertThat(testSocieteMain.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testSocieteMain.getCodepostal()).isEqualTo(DEFAULT_CODEPOSTAL);
        assertThat(testSocieteMain.getNormcommune()).isEqualTo(DEFAULT_NORMCOMMUNE);
        assertThat(testSocieteMain.getCommune()).isEqualTo(DEFAULT_COMMUNE);
        assertThat(testSocieteMain.getApe()).isEqualTo(DEFAULT_APE);
        assertThat(testSocieteMain.getApetexte()).isEqualTo(DEFAULT_APETEXTE);
        assertThat(testSocieteMain.getDateimmat()).isEqualTo(DEFAULT_DATEIMMAT);
        assertThat(testSocieteMain.getDcren()).isEqualTo(DEFAULT_DCREN);
        assertThat(testSocieteMain.getNationalite()).isEqualTo(DEFAULT_NATIONALITE);
        assertThat(testSocieteMain.getFormejur()).isEqualTo(DEFAULT_FORMEJUR);
        assertThat(testSocieteMain.getCapital()).isEqualTo(DEFAULT_CAPITAL);
        assertThat(testSocieteMain.getDevisecap()).isEqualTo(DEFAULT_DEVISECAP);
        assertThat(testSocieteMain.getTypecap()).isEqualTo(DEFAULT_TYPECAP);
        assertThat(testSocieteMain.getUrl()).isEqualTo(DEFAULT_URL);
    }

    @Test
    @Transactional
    public void createSocieteMainWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = societeMainRepository.findAll().size();

        // Create the SocieteMain with an existing ID
        societeMain.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSocieteMainMockMvc.perform(post("/api/societe-mains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(societeMain)))
            .andExpect(status().isBadRequest());

        // Validate the SocieteMain in the database
        List<SocieteMain> societeMainList = societeMainRepository.findAll();
        assertThat(societeMainList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSocieteMains() throws Exception {
        // Initialize the database
        societeMainRepository.saveAndFlush(societeMain);

        // Get all the societeMainList
        restSocieteMainMockMvc.perform(get("/api/societe-mains?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(societeMain.getId().intValue())))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN)))
            .andExpect(jsonPath("$.[*].deno").value(hasItem(DEFAULT_DENO)))
            .andExpect(jsonPath("$.[*].greffe").value(hasItem(DEFAULT_GREFFE)))
            .andExpect(jsonPath("$.[*].enseigne").value(hasItem(DEFAULT_ENSEIGNE)))
            .andExpect(jsonPath("$.[*].psiret").value(hasItem(DEFAULT_PSIRET)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].codepostal").value(hasItem(DEFAULT_CODEPOSTAL)))
            .andExpect(jsonPath("$.[*].normcommune").value(hasItem(DEFAULT_NORMCOMMUNE)))
            .andExpect(jsonPath("$.[*].commune").value(hasItem(DEFAULT_COMMUNE)))
            .andExpect(jsonPath("$.[*].ape").value(hasItem(DEFAULT_APE)))
            .andExpect(jsonPath("$.[*].apetexte").value(hasItem(DEFAULT_APETEXTE)))
            .andExpect(jsonPath("$.[*].dateimmat").value(hasItem(DEFAULT_DATEIMMAT)))
            .andExpect(jsonPath("$.[*].dcren").value(hasItem(DEFAULT_DCREN)))
            .andExpect(jsonPath("$.[*].nationalite").value(hasItem(DEFAULT_NATIONALITE)))
            .andExpect(jsonPath("$.[*].formejur").value(hasItem(DEFAULT_FORMEJUR)))
            .andExpect(jsonPath("$.[*].capital").value(hasItem(DEFAULT_CAPITAL)))
            .andExpect(jsonPath("$.[*].devisecap").value(hasItem(DEFAULT_DEVISECAP)))
            .andExpect(jsonPath("$.[*].typecap").value(hasItem(DEFAULT_TYPECAP)))
            .andExpect(jsonPath("$.[*].url").value(hasItem(DEFAULT_URL)));
    }

    @Test
    @Transactional
    public void getSocieteMain() throws Exception {
        // Initialize the database
        societeMainRepository.saveAndFlush(societeMain);

        // Get the societeMain
        restSocieteMainMockMvc.perform(get("/api/societe-mains/{id}", societeMain.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(societeMain.getId().intValue()))
            .andExpect(jsonPath("$.siren").value(DEFAULT_SIREN))
            .andExpect(jsonPath("$.deno").value(DEFAULT_DENO))
            .andExpect(jsonPath("$.greffe").value(DEFAULT_GREFFE))
            .andExpect(jsonPath("$.enseigne").value(DEFAULT_ENSEIGNE))
            .andExpect(jsonPath("$.psiret").value(DEFAULT_PSIRET))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.codepostal").value(DEFAULT_CODEPOSTAL))
            .andExpect(jsonPath("$.normcommune").value(DEFAULT_NORMCOMMUNE))
            .andExpect(jsonPath("$.commune").value(DEFAULT_COMMUNE))
            .andExpect(jsonPath("$.ape").value(DEFAULT_APE))
            .andExpect(jsonPath("$.apetexte").value(DEFAULT_APETEXTE))
            .andExpect(jsonPath("$.dateimmat").value(DEFAULT_DATEIMMAT))
            .andExpect(jsonPath("$.dcren").value(DEFAULT_DCREN))
            .andExpect(jsonPath("$.nationalite").value(DEFAULT_NATIONALITE))
            .andExpect(jsonPath("$.formejur").value(DEFAULT_FORMEJUR))
            .andExpect(jsonPath("$.capital").value(DEFAULT_CAPITAL))
            .andExpect(jsonPath("$.devisecap").value(DEFAULT_DEVISECAP))
            .andExpect(jsonPath("$.typecap").value(DEFAULT_TYPECAP))
            .andExpect(jsonPath("$.url").value(DEFAULT_URL));
    }

    @Test
    @Transactional
    public void getNonExistingSocieteMain() throws Exception {
        // Get the societeMain
        restSocieteMainMockMvc.perform(get("/api/societe-mains/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSocieteMain() throws Exception {
        // Initialize the database
        societeMainRepository.saveAndFlush(societeMain);

        int databaseSizeBeforeUpdate = societeMainRepository.findAll().size();

        // Update the societeMain
        SocieteMain updatedSocieteMain = societeMainRepository.findById(societeMain.getId()).get();
        // Disconnect from session so that the updates on updatedSocieteMain are not directly saved in db
        em.detach(updatedSocieteMain);
        updatedSocieteMain
            .siren(UPDATED_SIREN)
            .deno(UPDATED_DENO)
            .greffe(UPDATED_GREFFE)
            .enseigne(UPDATED_ENSEIGNE)
            .psiret(UPDATED_PSIRET)
            .adresse(UPDATED_ADRESSE)
            .codepostal(UPDATED_CODEPOSTAL)
            .normcommune(UPDATED_NORMCOMMUNE)
            .commune(UPDATED_COMMUNE)
            .ape(UPDATED_APE)
            .apetexte(UPDATED_APETEXTE)
            .dateimmat(UPDATED_DATEIMMAT)
            .dcren(UPDATED_DCREN)
            .nationalite(UPDATED_NATIONALITE)
            .formejur(UPDATED_FORMEJUR)
            .capital(UPDATED_CAPITAL)
            .devisecap(UPDATED_DEVISECAP)
            .typecap(UPDATED_TYPECAP)
            .url(UPDATED_URL);

        restSocieteMainMockMvc.perform(put("/api/societe-mains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSocieteMain)))
            .andExpect(status().isOk());

        // Validate the SocieteMain in the database
        List<SocieteMain> societeMainList = societeMainRepository.findAll();
        assertThat(societeMainList).hasSize(databaseSizeBeforeUpdate);
        SocieteMain testSocieteMain = societeMainList.get(societeMainList.size() - 1);
        assertThat(testSocieteMain.getSiren()).isEqualTo(UPDATED_SIREN);
        assertThat(testSocieteMain.getDeno()).isEqualTo(UPDATED_DENO);
        assertThat(testSocieteMain.getGreffe()).isEqualTo(UPDATED_GREFFE);
        assertThat(testSocieteMain.getEnseigne()).isEqualTo(UPDATED_ENSEIGNE);
        assertThat(testSocieteMain.getPsiret()).isEqualTo(UPDATED_PSIRET);
        assertThat(testSocieteMain.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testSocieteMain.getCodepostal()).isEqualTo(UPDATED_CODEPOSTAL);
        assertThat(testSocieteMain.getNormcommune()).isEqualTo(UPDATED_NORMCOMMUNE);
        assertThat(testSocieteMain.getCommune()).isEqualTo(UPDATED_COMMUNE);
        assertThat(testSocieteMain.getApe()).isEqualTo(UPDATED_APE);
        assertThat(testSocieteMain.getApetexte()).isEqualTo(UPDATED_APETEXTE);
        assertThat(testSocieteMain.getDateimmat()).isEqualTo(UPDATED_DATEIMMAT);
        assertThat(testSocieteMain.getDcren()).isEqualTo(UPDATED_DCREN);
        assertThat(testSocieteMain.getNationalite()).isEqualTo(UPDATED_NATIONALITE);
        assertThat(testSocieteMain.getFormejur()).isEqualTo(UPDATED_FORMEJUR);
        assertThat(testSocieteMain.getCapital()).isEqualTo(UPDATED_CAPITAL);
        assertThat(testSocieteMain.getDevisecap()).isEqualTo(UPDATED_DEVISECAP);
        assertThat(testSocieteMain.getTypecap()).isEqualTo(UPDATED_TYPECAP);
        assertThat(testSocieteMain.getUrl()).isEqualTo(UPDATED_URL);
    }

    @Test
    @Transactional
    public void updateNonExistingSocieteMain() throws Exception {
        int databaseSizeBeforeUpdate = societeMainRepository.findAll().size();

        // Create the SocieteMain

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSocieteMainMockMvc.perform(put("/api/societe-mains")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(societeMain)))
            .andExpect(status().isBadRequest());

        // Validate the SocieteMain in the database
        List<SocieteMain> societeMainList = societeMainRepository.findAll();
        assertThat(societeMainList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSocieteMain() throws Exception {
        // Initialize the database
        societeMainRepository.saveAndFlush(societeMain);

        int databaseSizeBeforeDelete = societeMainRepository.findAll().size();

        // Delete the societeMain
        restSocieteMainMockMvc.perform(delete("/api/societe-mains/{id}", societeMain.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SocieteMain> societeMainList = societeMainRepository.findAll();
        assertThat(societeMainList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SocieteMain.class);
        SocieteMain societeMain1 = new SocieteMain();
        societeMain1.setId(1L);
        SocieteMain societeMain2 = new SocieteMain();
        societeMain2.setId(societeMain1.getId());
        assertThat(societeMain1).isEqualTo(societeMain2);
        societeMain2.setId(2L);
        assertThat(societeMain1).isNotEqualTo(societeMain2);
        societeMain1.setId(null);
        assertThat(societeMain1).isNotEqualTo(societeMain2);
    }
}
