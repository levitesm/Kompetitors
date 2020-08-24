package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.Infogreffe;
import fr.ippon.kompetitors.repository.InfogreffeRepository;
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
 * Integration tests for the {@link InfogreffeResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class InfogreffeResourceIT {

    private static final String DEFAULT_DEPARTEMENT = "AAAAAAAAAA";
    private static final String UPDATED_DEPARTEMENT = "BBBBBBBBBB";

    private static final String DEFAULT_VILLE = "AAAAAAAAAA";
    private static final String UPDATED_VILLE = "BBBBBBBBBB";

    private static final String DEFAULT_NUM_DEPT = "AAAAAAAAAA";
    private static final String UPDATED_NUM_DEPT = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_GREFFE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_GREFFE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_IMMATRICULATION = "AAAAAAAAAA";
    private static final String UPDATED_DATE_IMMATRICULATION = "BBBBBBBBBB";

    private static final String DEFAULT_CA_1 = "AAAAAAAAAA";
    private static final String UPDATED_CA_1 = "BBBBBBBBBB";

    private static final String DEFAULT_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_SIREN = "BBBBBBBBBB";

    private static final String DEFAULT_CA_2 = "AAAAAAAAAA";
    private static final String UPDATED_CA_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FORME_JURIDIQUE = "AAAAAAAAAA";
    private static final String UPDATED_FORME_JURIDIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTAT_3 = "AAAAAAAAAA";
    private static final String UPDATED_RESULTAT_3 = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTAT_2 = "AAAAAAAAAA";
    private static final String UPDATED_RESULTAT_2 = "BBBBBBBBBB";

    private static final String DEFAULT_RESULTAT_1 = "AAAAAAAAAA";
    private static final String UPDATED_RESULTAT_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FICHEIDENTITE = "AAAAAAAAAA";
    private static final String UPDATED_FICHEIDENTITE = "BBBBBBBBBB";

    private static final String DEFAULT_DUREE_1 = "AAAAAAAAAA";
    private static final String UPDATED_DUREE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_DE_PUBLICATION = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DE_PUBLICATION = "BBBBBBBBBB";

    private static final String DEFAULT_STATUT = "AAAAAAAAAA";
    private static final String UPDATED_STATUT = "BBBBBBBBBB";

    private static final String DEFAULT_NIC = "AAAAAAAAAA";
    private static final String UPDATED_NIC = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_APE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_APE = "BBBBBBBBBB";

    private static final String DEFAULT_ADRESSE = "AAAAAAAAAA";
    private static final String UPDATED_ADRESSE = "BBBBBBBBBB";

    private static final String DEFAULT_TRANCHE_CA_MILLESIME_3 = "AAAAAAAAAA";
    private static final String UPDATED_TRANCHE_CA_MILLESIME_3 = "BBBBBBBBBB";

    private static final String DEFAULT_DENOMINATION = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINATION = "BBBBBBBBBB";

    private static final String DEFAULT_DUREE_2 = "AAAAAAAAAA";
    private static final String UPDATED_DUREE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_EFFECTIF_1 = "AAAAAAAAAA";
    private static final String UPDATED_EFFECTIF_1 = "BBBBBBBBBB";

    private static final String DEFAULT_EFFECTIF_3 = "AAAAAAAAAA";
    private static final String UPDATED_EFFECTIF_3 = "BBBBBBBBBB";

    private static final String DEFAULT_EFFECTIF_2 = "AAAAAAAAAA";
    private static final String UPDATED_EFFECTIF_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CA_3 = "AAAAAAAAAA";
    private static final String UPDATED_CA_3 = "BBBBBBBBBB";

    private static final String DEFAULT_TRANCHE_CA_MILLESIME_1 = "AAAAAAAAAA";
    private static final String UPDATED_TRANCHE_CA_MILLESIME_1 = "BBBBBBBBBB";

    private static final String DEFAULT_DUREE_3 = "AAAAAAAAAA";
    private static final String UPDATED_DUREE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_TRANCHE_CA_MILLESIME_2 = "AAAAAAAAAA";
    private static final String UPDATED_TRANCHE_CA_MILLESIME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_POSTAL = "AAAAAAAAAA";
    private static final String UPDATED_CODE_POSTAL = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_DE_CLOTURE_EXERCICE_1 = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DE_CLOTURE_EXERCICE_1 = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_DE_CLOTURE_EXERCICE_3 = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DE_CLOTURE_EXERCICE_3 = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_DE_CLOTURE_EXERCICE_2 = "AAAAAAAAAA";
    private static final String UPDATED_DATE_DE_CLOTURE_EXERCICE_2 = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_APE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_APE = "BBBBBBBBBB";

    private static final String DEFAULT_GREFFE = "AAAAAAAAAA";
    private static final String UPDATED_GREFFE = "BBBBBBBBBB";

    private static final String DEFAULT_MILLESIME_3 = "AAAAAAAAAA";
    private static final String UPDATED_MILLESIME_3 = "BBBBBBBBBB";

    private static final String DEFAULT_MILLESIME_2 = "AAAAAAAAAA";
    private static final String UPDATED_MILLESIME_2 = "BBBBBBBBBB";

    private static final String DEFAULT_MILLESIME_1 = "AAAAAAAAAA";
    private static final String UPDATED_MILLESIME_1 = "BBBBBBBBBB";

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    @Autowired
    private InfogreffeRepository infogreffeRepository;

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

    private MockMvc restInfogreffeMockMvc;

    private Infogreffe infogreffe;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InfogreffeResource infogreffeResource = new InfogreffeResource(infogreffeRepository);
        this.restInfogreffeMockMvc = MockMvcBuilders.standaloneSetup(infogreffeResource)
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
    public static Infogreffe createEntity(EntityManager em) {
        Infogreffe infogreffe = new Infogreffe()
            .departement(DEFAULT_DEPARTEMENT)
            .ville(DEFAULT_VILLE)
            .numDept(DEFAULT_NUM_DEPT)
            .codeGreffe(DEFAULT_CODE_GREFFE)
            .dateImmatriculation(DEFAULT_DATE_IMMATRICULATION)
            .ca1(DEFAULT_CA_1)
            .siren(DEFAULT_SIREN)
            .ca2(DEFAULT_CA_2)
            .formeJuridique(DEFAULT_FORME_JURIDIQUE)
            .resultat3(DEFAULT_RESULTAT_3)
            .resultat2(DEFAULT_RESULTAT_2)
            .resultat1(DEFAULT_RESULTAT_1)
            .ficheidentite(DEFAULT_FICHEIDENTITE)
            .duree1(DEFAULT_DUREE_1)
            .dateDePublication(DEFAULT_DATE_DE_PUBLICATION)
            .statut(DEFAULT_STATUT)
            .nic(DEFAULT_NIC)
            .codeApe(DEFAULT_CODE_APE)
            .adresse(DEFAULT_ADRESSE)
            .trancheCaMillesime3(DEFAULT_TRANCHE_CA_MILLESIME_3)
            .denomination(DEFAULT_DENOMINATION)
            .duree2(DEFAULT_DUREE_2)
            .effectif1(DEFAULT_EFFECTIF_1)
            .effectif3(DEFAULT_EFFECTIF_3)
            .effectif2(DEFAULT_EFFECTIF_2)
            .ca3(DEFAULT_CA_3)
            .trancheCaMillesime1(DEFAULT_TRANCHE_CA_MILLESIME_1)
            .duree3(DEFAULT_DUREE_3)
            .trancheCaMillesime2(DEFAULT_TRANCHE_CA_MILLESIME_2)
            .codePostal(DEFAULT_CODE_POSTAL)
            .dateDeClotureExercice1(DEFAULT_DATE_DE_CLOTURE_EXERCICE_1)
            .dateDeClotureExercice3(DEFAULT_DATE_DE_CLOTURE_EXERCICE_3)
            .dateDeClotureExercice2(DEFAULT_DATE_DE_CLOTURE_EXERCICE_2)
            .libelleApe(DEFAULT_LIBELLE_APE)
            .greffe(DEFAULT_GREFFE)
            .millesime3(DEFAULT_MILLESIME_3)
            .millesime2(DEFAULT_MILLESIME_2)
            .millesime1(DEFAULT_MILLESIME_1)
            .region(DEFAULT_REGION);
        return infogreffe;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Infogreffe createUpdatedEntity(EntityManager em) {
        Infogreffe infogreffe = new Infogreffe()
            .departement(UPDATED_DEPARTEMENT)
            .ville(UPDATED_VILLE)
            .numDept(UPDATED_NUM_DEPT)
            .codeGreffe(UPDATED_CODE_GREFFE)
            .dateImmatriculation(UPDATED_DATE_IMMATRICULATION)
            .ca1(UPDATED_CA_1)
            .siren(UPDATED_SIREN)
            .ca2(UPDATED_CA_2)
            .formeJuridique(UPDATED_FORME_JURIDIQUE)
            .resultat3(UPDATED_RESULTAT_3)
            .resultat2(UPDATED_RESULTAT_2)
            .resultat1(UPDATED_RESULTAT_1)
            .ficheidentite(UPDATED_FICHEIDENTITE)
            .duree1(UPDATED_DUREE_1)
            .dateDePublication(UPDATED_DATE_DE_PUBLICATION)
            .statut(UPDATED_STATUT)
            .nic(UPDATED_NIC)
            .codeApe(UPDATED_CODE_APE)
            .adresse(UPDATED_ADRESSE)
            .trancheCaMillesime3(UPDATED_TRANCHE_CA_MILLESIME_3)
            .denomination(UPDATED_DENOMINATION)
            .duree2(UPDATED_DUREE_2)
            .effectif1(UPDATED_EFFECTIF_1)
            .effectif3(UPDATED_EFFECTIF_3)
            .effectif2(UPDATED_EFFECTIF_2)
            .ca3(UPDATED_CA_3)
            .trancheCaMillesime1(UPDATED_TRANCHE_CA_MILLESIME_1)
            .duree3(UPDATED_DUREE_3)
            .trancheCaMillesime2(UPDATED_TRANCHE_CA_MILLESIME_2)
            .codePostal(UPDATED_CODE_POSTAL)
            .dateDeClotureExercice1(UPDATED_DATE_DE_CLOTURE_EXERCICE_1)
            .dateDeClotureExercice3(UPDATED_DATE_DE_CLOTURE_EXERCICE_3)
            .dateDeClotureExercice2(UPDATED_DATE_DE_CLOTURE_EXERCICE_2)
            .libelleApe(UPDATED_LIBELLE_APE)
            .greffe(UPDATED_GREFFE)
            .millesime3(UPDATED_MILLESIME_3)
            .millesime2(UPDATED_MILLESIME_2)
            .millesime1(UPDATED_MILLESIME_1)
            .region(UPDATED_REGION);
        return infogreffe;
    }

    @BeforeEach
    public void initTest() {
        infogreffe = createEntity(em);
    }

    @Test
    @Transactional
    public void createInfogreffe() throws Exception {
        int databaseSizeBeforeCreate = infogreffeRepository.findAll().size();

        // Create the Infogreffe
        restInfogreffeMockMvc.perform(post("/api/infogreffes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infogreffe)))
            .andExpect(status().isCreated());

        // Validate the Infogreffe in the database
        List<Infogreffe> infogreffeList = infogreffeRepository.findAll();
        assertThat(infogreffeList).hasSize(databaseSizeBeforeCreate + 1);
        Infogreffe testInfogreffe = infogreffeList.get(infogreffeList.size() - 1);
        assertThat(testInfogreffe.getDepartement()).isEqualTo(DEFAULT_DEPARTEMENT);
        assertThat(testInfogreffe.getVille()).isEqualTo(DEFAULT_VILLE);
        assertThat(testInfogreffe.getNumDept()).isEqualTo(DEFAULT_NUM_DEPT);
        assertThat(testInfogreffe.getCodeGreffe()).isEqualTo(DEFAULT_CODE_GREFFE);
        assertThat(testInfogreffe.getDateImmatriculation()).isEqualTo(DEFAULT_DATE_IMMATRICULATION);
        assertThat(testInfogreffe.getCa1()).isEqualTo(DEFAULT_CA_1);
        assertThat(testInfogreffe.getSiren()).isEqualTo(DEFAULT_SIREN);
        assertThat(testInfogreffe.getCa2()).isEqualTo(DEFAULT_CA_2);
        assertThat(testInfogreffe.getFormeJuridique()).isEqualTo(DEFAULT_FORME_JURIDIQUE);
        assertThat(testInfogreffe.getResultat3()).isEqualTo(DEFAULT_RESULTAT_3);
        assertThat(testInfogreffe.getResultat2()).isEqualTo(DEFAULT_RESULTAT_2);
        assertThat(testInfogreffe.getResultat1()).isEqualTo(DEFAULT_RESULTAT_1);
        assertThat(testInfogreffe.getFicheidentite()).isEqualTo(DEFAULT_FICHEIDENTITE);
        assertThat(testInfogreffe.getDuree1()).isEqualTo(DEFAULT_DUREE_1);
        assertThat(testInfogreffe.getDateDePublication()).isEqualTo(DEFAULT_DATE_DE_PUBLICATION);
        assertThat(testInfogreffe.getStatut()).isEqualTo(DEFAULT_STATUT);
        assertThat(testInfogreffe.getNic()).isEqualTo(DEFAULT_NIC);
        assertThat(testInfogreffe.getCodeApe()).isEqualTo(DEFAULT_CODE_APE);
        assertThat(testInfogreffe.getAdresse()).isEqualTo(DEFAULT_ADRESSE);
        assertThat(testInfogreffe.getTrancheCaMillesime3()).isEqualTo(DEFAULT_TRANCHE_CA_MILLESIME_3);
        assertThat(testInfogreffe.getDenomination()).isEqualTo(DEFAULT_DENOMINATION);
        assertThat(testInfogreffe.getDuree2()).isEqualTo(DEFAULT_DUREE_2);
        assertThat(testInfogreffe.getEffectif1()).isEqualTo(DEFAULT_EFFECTIF_1);
        assertThat(testInfogreffe.getEffectif3()).isEqualTo(DEFAULT_EFFECTIF_3);
        assertThat(testInfogreffe.getEffectif2()).isEqualTo(DEFAULT_EFFECTIF_2);
        assertThat(testInfogreffe.getCa3()).isEqualTo(DEFAULT_CA_3);
        assertThat(testInfogreffe.getTrancheCaMillesime1()).isEqualTo(DEFAULT_TRANCHE_CA_MILLESIME_1);
        assertThat(testInfogreffe.getDuree3()).isEqualTo(DEFAULT_DUREE_3);
        assertThat(testInfogreffe.getTrancheCaMillesime2()).isEqualTo(DEFAULT_TRANCHE_CA_MILLESIME_2);
        assertThat(testInfogreffe.getCodePostal()).isEqualTo(DEFAULT_CODE_POSTAL);
        assertThat(testInfogreffe.getDateDeClotureExercice1()).isEqualTo(DEFAULT_DATE_DE_CLOTURE_EXERCICE_1);
        assertThat(testInfogreffe.getDateDeClotureExercice3()).isEqualTo(DEFAULT_DATE_DE_CLOTURE_EXERCICE_3);
        assertThat(testInfogreffe.getDateDeClotureExercice2()).isEqualTo(DEFAULT_DATE_DE_CLOTURE_EXERCICE_2);
        assertThat(testInfogreffe.getLibelleApe()).isEqualTo(DEFAULT_LIBELLE_APE);
        assertThat(testInfogreffe.getGreffe()).isEqualTo(DEFAULT_GREFFE);
        assertThat(testInfogreffe.getMillesime3()).isEqualTo(DEFAULT_MILLESIME_3);
        assertThat(testInfogreffe.getMillesime2()).isEqualTo(DEFAULT_MILLESIME_2);
        assertThat(testInfogreffe.getMillesime1()).isEqualTo(DEFAULT_MILLESIME_1);
        assertThat(testInfogreffe.getRegion()).isEqualTo(DEFAULT_REGION);
    }

    @Test
    @Transactional
    public void createInfogreffeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = infogreffeRepository.findAll().size();

        // Create the Infogreffe with an existing ID
        infogreffe.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInfogreffeMockMvc.perform(post("/api/infogreffes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infogreffe)))
            .andExpect(status().isBadRequest());

        // Validate the Infogreffe in the database
        List<Infogreffe> infogreffeList = infogreffeRepository.findAll();
        assertThat(infogreffeList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllInfogreffes() throws Exception {
        // Initialize the database
        infogreffeRepository.saveAndFlush(infogreffe);

        // Get all the infogreffeList
        restInfogreffeMockMvc.perform(get("/api/infogreffes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(infogreffe.getId().intValue())))
            .andExpect(jsonPath("$.[*].departement").value(hasItem(DEFAULT_DEPARTEMENT)))
            .andExpect(jsonPath("$.[*].ville").value(hasItem(DEFAULT_VILLE)))
            .andExpect(jsonPath("$.[*].numDept").value(hasItem(DEFAULT_NUM_DEPT)))
            .andExpect(jsonPath("$.[*].codeGreffe").value(hasItem(DEFAULT_CODE_GREFFE)))
            .andExpect(jsonPath("$.[*].dateImmatriculation").value(hasItem(DEFAULT_DATE_IMMATRICULATION)))
            .andExpect(jsonPath("$.[*].ca1").value(hasItem(DEFAULT_CA_1)))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN)))
            .andExpect(jsonPath("$.[*].ca2").value(hasItem(DEFAULT_CA_2)))
            .andExpect(jsonPath("$.[*].formeJuridique").value(hasItem(DEFAULT_FORME_JURIDIQUE)))
            .andExpect(jsonPath("$.[*].resultat3").value(hasItem(DEFAULT_RESULTAT_3)))
            .andExpect(jsonPath("$.[*].resultat2").value(hasItem(DEFAULT_RESULTAT_2)))
            .andExpect(jsonPath("$.[*].resultat1").value(hasItem(DEFAULT_RESULTAT_1)))
            .andExpect(jsonPath("$.[*].ficheidentite").value(hasItem(DEFAULT_FICHEIDENTITE)))
            .andExpect(jsonPath("$.[*].duree1").value(hasItem(DEFAULT_DUREE_1)))
            .andExpect(jsonPath("$.[*].dateDePublication").value(hasItem(DEFAULT_DATE_DE_PUBLICATION)))
            .andExpect(jsonPath("$.[*].statut").value(hasItem(DEFAULT_STATUT)))
            .andExpect(jsonPath("$.[*].nic").value(hasItem(DEFAULT_NIC)))
            .andExpect(jsonPath("$.[*].codeApe").value(hasItem(DEFAULT_CODE_APE)))
            .andExpect(jsonPath("$.[*].adresse").value(hasItem(DEFAULT_ADRESSE)))
            .andExpect(jsonPath("$.[*].trancheCaMillesime3").value(hasItem(DEFAULT_TRANCHE_CA_MILLESIME_3)))
            .andExpect(jsonPath("$.[*].denomination").value(hasItem(DEFAULT_DENOMINATION)))
            .andExpect(jsonPath("$.[*].duree2").value(hasItem(DEFAULT_DUREE_2)))
            .andExpect(jsonPath("$.[*].effectif1").value(hasItem(DEFAULT_EFFECTIF_1)))
            .andExpect(jsonPath("$.[*].effectif3").value(hasItem(DEFAULT_EFFECTIF_3)))
            .andExpect(jsonPath("$.[*].effectif2").value(hasItem(DEFAULT_EFFECTIF_2)))
            .andExpect(jsonPath("$.[*].ca3").value(hasItem(DEFAULT_CA_3)))
            .andExpect(jsonPath("$.[*].trancheCaMillesime1").value(hasItem(DEFAULT_TRANCHE_CA_MILLESIME_1)))
            .andExpect(jsonPath("$.[*].duree3").value(hasItem(DEFAULT_DUREE_3)))
            .andExpect(jsonPath("$.[*].trancheCaMillesime2").value(hasItem(DEFAULT_TRANCHE_CA_MILLESIME_2)))
            .andExpect(jsonPath("$.[*].codePostal").value(hasItem(DEFAULT_CODE_POSTAL)))
            .andExpect(jsonPath("$.[*].dateDeClotureExercice1").value(hasItem(DEFAULT_DATE_DE_CLOTURE_EXERCICE_1)))
            .andExpect(jsonPath("$.[*].dateDeClotureExercice3").value(hasItem(DEFAULT_DATE_DE_CLOTURE_EXERCICE_3)))
            .andExpect(jsonPath("$.[*].dateDeClotureExercice2").value(hasItem(DEFAULT_DATE_DE_CLOTURE_EXERCICE_2)))
            .andExpect(jsonPath("$.[*].libelleApe").value(hasItem(DEFAULT_LIBELLE_APE)))
            .andExpect(jsonPath("$.[*].greffe").value(hasItem(DEFAULT_GREFFE)))
            .andExpect(jsonPath("$.[*].millesime3").value(hasItem(DEFAULT_MILLESIME_3)))
            .andExpect(jsonPath("$.[*].millesime2").value(hasItem(DEFAULT_MILLESIME_2)))
            .andExpect(jsonPath("$.[*].millesime1").value(hasItem(DEFAULT_MILLESIME_1)))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)));
    }
    
    @Test
    @Transactional
    public void getInfogreffe() throws Exception {
        // Initialize the database
        infogreffeRepository.saveAndFlush(infogreffe);

        // Get the infogreffe
        restInfogreffeMockMvc.perform(get("/api/infogreffes/{id}", infogreffe.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(infogreffe.getId().intValue()))
            .andExpect(jsonPath("$.departement").value(DEFAULT_DEPARTEMENT))
            .andExpect(jsonPath("$.ville").value(DEFAULT_VILLE))
            .andExpect(jsonPath("$.numDept").value(DEFAULT_NUM_DEPT))
            .andExpect(jsonPath("$.codeGreffe").value(DEFAULT_CODE_GREFFE))
            .andExpect(jsonPath("$.dateImmatriculation").value(DEFAULT_DATE_IMMATRICULATION))
            .andExpect(jsonPath("$.ca1").value(DEFAULT_CA_1))
            .andExpect(jsonPath("$.siren").value(DEFAULT_SIREN))
            .andExpect(jsonPath("$.ca2").value(DEFAULT_CA_2))
            .andExpect(jsonPath("$.formeJuridique").value(DEFAULT_FORME_JURIDIQUE))
            .andExpect(jsonPath("$.resultat3").value(DEFAULT_RESULTAT_3))
            .andExpect(jsonPath("$.resultat2").value(DEFAULT_RESULTAT_2))
            .andExpect(jsonPath("$.resultat1").value(DEFAULT_RESULTAT_1))
            .andExpect(jsonPath("$.ficheidentite").value(DEFAULT_FICHEIDENTITE))
            .andExpect(jsonPath("$.duree1").value(DEFAULT_DUREE_1))
            .andExpect(jsonPath("$.dateDePublication").value(DEFAULT_DATE_DE_PUBLICATION))
            .andExpect(jsonPath("$.statut").value(DEFAULT_STATUT))
            .andExpect(jsonPath("$.nic").value(DEFAULT_NIC))
            .andExpect(jsonPath("$.codeApe").value(DEFAULT_CODE_APE))
            .andExpect(jsonPath("$.adresse").value(DEFAULT_ADRESSE))
            .andExpect(jsonPath("$.trancheCaMillesime3").value(DEFAULT_TRANCHE_CA_MILLESIME_3))
            .andExpect(jsonPath("$.denomination").value(DEFAULT_DENOMINATION))
            .andExpect(jsonPath("$.duree2").value(DEFAULT_DUREE_2))
            .andExpect(jsonPath("$.effectif1").value(DEFAULT_EFFECTIF_1))
            .andExpect(jsonPath("$.effectif3").value(DEFAULT_EFFECTIF_3))
            .andExpect(jsonPath("$.effectif2").value(DEFAULT_EFFECTIF_2))
            .andExpect(jsonPath("$.ca3").value(DEFAULT_CA_3))
            .andExpect(jsonPath("$.trancheCaMillesime1").value(DEFAULT_TRANCHE_CA_MILLESIME_1))
            .andExpect(jsonPath("$.duree3").value(DEFAULT_DUREE_3))
            .andExpect(jsonPath("$.trancheCaMillesime2").value(DEFAULT_TRANCHE_CA_MILLESIME_2))
            .andExpect(jsonPath("$.codePostal").value(DEFAULT_CODE_POSTAL))
            .andExpect(jsonPath("$.dateDeClotureExercice1").value(DEFAULT_DATE_DE_CLOTURE_EXERCICE_1))
            .andExpect(jsonPath("$.dateDeClotureExercice3").value(DEFAULT_DATE_DE_CLOTURE_EXERCICE_3))
            .andExpect(jsonPath("$.dateDeClotureExercice2").value(DEFAULT_DATE_DE_CLOTURE_EXERCICE_2))
            .andExpect(jsonPath("$.libelleApe").value(DEFAULT_LIBELLE_APE))
            .andExpect(jsonPath("$.greffe").value(DEFAULT_GREFFE))
            .andExpect(jsonPath("$.millesime3").value(DEFAULT_MILLESIME_3))
            .andExpect(jsonPath("$.millesime2").value(DEFAULT_MILLESIME_2))
            .andExpect(jsonPath("$.millesime1").value(DEFAULT_MILLESIME_1))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION));
    }

    @Test
    @Transactional
    public void getNonExistingInfogreffe() throws Exception {
        // Get the infogreffe
        restInfogreffeMockMvc.perform(get("/api/infogreffes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInfogreffe() throws Exception {
        // Initialize the database
        infogreffeRepository.saveAndFlush(infogreffe);

        int databaseSizeBeforeUpdate = infogreffeRepository.findAll().size();

        // Update the infogreffe
        Infogreffe updatedInfogreffe = infogreffeRepository.findById(infogreffe.getId()).get();
        // Disconnect from session so that the updates on updatedInfogreffe are not directly saved in db
        em.detach(updatedInfogreffe);
        updatedInfogreffe
            .departement(UPDATED_DEPARTEMENT)
            .ville(UPDATED_VILLE)
            .numDept(UPDATED_NUM_DEPT)
            .codeGreffe(UPDATED_CODE_GREFFE)
            .dateImmatriculation(UPDATED_DATE_IMMATRICULATION)
            .ca1(UPDATED_CA_1)
            .siren(UPDATED_SIREN)
            .ca2(UPDATED_CA_2)
            .formeJuridique(UPDATED_FORME_JURIDIQUE)
            .resultat3(UPDATED_RESULTAT_3)
            .resultat2(UPDATED_RESULTAT_2)
            .resultat1(UPDATED_RESULTAT_1)
            .ficheidentite(UPDATED_FICHEIDENTITE)
            .duree1(UPDATED_DUREE_1)
            .dateDePublication(UPDATED_DATE_DE_PUBLICATION)
            .statut(UPDATED_STATUT)
            .nic(UPDATED_NIC)
            .codeApe(UPDATED_CODE_APE)
            .adresse(UPDATED_ADRESSE)
            .trancheCaMillesime3(UPDATED_TRANCHE_CA_MILLESIME_3)
            .denomination(UPDATED_DENOMINATION)
            .duree2(UPDATED_DUREE_2)
            .effectif1(UPDATED_EFFECTIF_1)
            .effectif3(UPDATED_EFFECTIF_3)
            .effectif2(UPDATED_EFFECTIF_2)
            .ca3(UPDATED_CA_3)
            .trancheCaMillesime1(UPDATED_TRANCHE_CA_MILLESIME_1)
            .duree3(UPDATED_DUREE_3)
            .trancheCaMillesime2(UPDATED_TRANCHE_CA_MILLESIME_2)
            .codePostal(UPDATED_CODE_POSTAL)
            .dateDeClotureExercice1(UPDATED_DATE_DE_CLOTURE_EXERCICE_1)
            .dateDeClotureExercice3(UPDATED_DATE_DE_CLOTURE_EXERCICE_3)
            .dateDeClotureExercice2(UPDATED_DATE_DE_CLOTURE_EXERCICE_2)
            .libelleApe(UPDATED_LIBELLE_APE)
            .greffe(UPDATED_GREFFE)
            .millesime3(UPDATED_MILLESIME_3)
            .millesime2(UPDATED_MILLESIME_2)
            .millesime1(UPDATED_MILLESIME_1)
            .region(UPDATED_REGION);

        restInfogreffeMockMvc.perform(put("/api/infogreffes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInfogreffe)))
            .andExpect(status().isOk());

        // Validate the Infogreffe in the database
        List<Infogreffe> infogreffeList = infogreffeRepository.findAll();
        assertThat(infogreffeList).hasSize(databaseSizeBeforeUpdate);
        Infogreffe testInfogreffe = infogreffeList.get(infogreffeList.size() - 1);
        assertThat(testInfogreffe.getDepartement()).isEqualTo(UPDATED_DEPARTEMENT);
        assertThat(testInfogreffe.getVille()).isEqualTo(UPDATED_VILLE);
        assertThat(testInfogreffe.getNumDept()).isEqualTo(UPDATED_NUM_DEPT);
        assertThat(testInfogreffe.getCodeGreffe()).isEqualTo(UPDATED_CODE_GREFFE);
        assertThat(testInfogreffe.getDateImmatriculation()).isEqualTo(UPDATED_DATE_IMMATRICULATION);
        assertThat(testInfogreffe.getCa1()).isEqualTo(UPDATED_CA_1);
        assertThat(testInfogreffe.getSiren()).isEqualTo(UPDATED_SIREN);
        assertThat(testInfogreffe.getCa2()).isEqualTo(UPDATED_CA_2);
        assertThat(testInfogreffe.getFormeJuridique()).isEqualTo(UPDATED_FORME_JURIDIQUE);
        assertThat(testInfogreffe.getResultat3()).isEqualTo(UPDATED_RESULTAT_3);
        assertThat(testInfogreffe.getResultat2()).isEqualTo(UPDATED_RESULTAT_2);
        assertThat(testInfogreffe.getResultat1()).isEqualTo(UPDATED_RESULTAT_1);
        assertThat(testInfogreffe.getFicheidentite()).isEqualTo(UPDATED_FICHEIDENTITE);
        assertThat(testInfogreffe.getDuree1()).isEqualTo(UPDATED_DUREE_1);
        assertThat(testInfogreffe.getDateDePublication()).isEqualTo(UPDATED_DATE_DE_PUBLICATION);
        assertThat(testInfogreffe.getStatut()).isEqualTo(UPDATED_STATUT);
        assertThat(testInfogreffe.getNic()).isEqualTo(UPDATED_NIC);
        assertThat(testInfogreffe.getCodeApe()).isEqualTo(UPDATED_CODE_APE);
        assertThat(testInfogreffe.getAdresse()).isEqualTo(UPDATED_ADRESSE);
        assertThat(testInfogreffe.getTrancheCaMillesime3()).isEqualTo(UPDATED_TRANCHE_CA_MILLESIME_3);
        assertThat(testInfogreffe.getDenomination()).isEqualTo(UPDATED_DENOMINATION);
        assertThat(testInfogreffe.getDuree2()).isEqualTo(UPDATED_DUREE_2);
        assertThat(testInfogreffe.getEffectif1()).isEqualTo(UPDATED_EFFECTIF_1);
        assertThat(testInfogreffe.getEffectif3()).isEqualTo(UPDATED_EFFECTIF_3);
        assertThat(testInfogreffe.getEffectif2()).isEqualTo(UPDATED_EFFECTIF_2);
        assertThat(testInfogreffe.getCa3()).isEqualTo(UPDATED_CA_3);
        assertThat(testInfogreffe.getTrancheCaMillesime1()).isEqualTo(UPDATED_TRANCHE_CA_MILLESIME_1);
        assertThat(testInfogreffe.getDuree3()).isEqualTo(UPDATED_DUREE_3);
        assertThat(testInfogreffe.getTrancheCaMillesime2()).isEqualTo(UPDATED_TRANCHE_CA_MILLESIME_2);
        assertThat(testInfogreffe.getCodePostal()).isEqualTo(UPDATED_CODE_POSTAL);
        assertThat(testInfogreffe.getDateDeClotureExercice1()).isEqualTo(UPDATED_DATE_DE_CLOTURE_EXERCICE_1);
        assertThat(testInfogreffe.getDateDeClotureExercice3()).isEqualTo(UPDATED_DATE_DE_CLOTURE_EXERCICE_3);
        assertThat(testInfogreffe.getDateDeClotureExercice2()).isEqualTo(UPDATED_DATE_DE_CLOTURE_EXERCICE_2);
        assertThat(testInfogreffe.getLibelleApe()).isEqualTo(UPDATED_LIBELLE_APE);
        assertThat(testInfogreffe.getGreffe()).isEqualTo(UPDATED_GREFFE);
        assertThat(testInfogreffe.getMillesime3()).isEqualTo(UPDATED_MILLESIME_3);
        assertThat(testInfogreffe.getMillesime2()).isEqualTo(UPDATED_MILLESIME_2);
        assertThat(testInfogreffe.getMillesime1()).isEqualTo(UPDATED_MILLESIME_1);
        assertThat(testInfogreffe.getRegion()).isEqualTo(UPDATED_REGION);
    }

    @Test
    @Transactional
    public void updateNonExistingInfogreffe() throws Exception {
        int databaseSizeBeforeUpdate = infogreffeRepository.findAll().size();

        // Create the Infogreffe

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restInfogreffeMockMvc.perform(put("/api/infogreffes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(infogreffe)))
            .andExpect(status().isBadRequest());

        // Validate the Infogreffe in the database
        List<Infogreffe> infogreffeList = infogreffeRepository.findAll();
        assertThat(infogreffeList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteInfogreffe() throws Exception {
        // Initialize the database
        infogreffeRepository.saveAndFlush(infogreffe);

        int databaseSizeBeforeDelete = infogreffeRepository.findAll().size();

        // Delete the infogreffe
        restInfogreffeMockMvc.perform(delete("/api/infogreffes/{id}", infogreffe.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Infogreffe> infogreffeList = infogreffeRepository.findAll();
        assertThat(infogreffeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Infogreffe.class);
        Infogreffe infogreffe1 = new Infogreffe();
        infogreffe1.setId(1L);
        Infogreffe infogreffe2 = new Infogreffe();
        infogreffe2.setId(infogreffe1.getId());
        assertThat(infogreffe1).isEqualTo(infogreffe2);
        infogreffe2.setId(2L);
        assertThat(infogreffe1).isNotEqualTo(infogreffe2);
        infogreffe1.setId(null);
        assertThat(infogreffe1).isNotEqualTo(infogreffe2);
    }
}
