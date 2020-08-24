package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ShareHolders;
import fr.ippon.kompetitors.repository.ShareHoldersRepository;
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
 * Integration tests for the {@link ShareHoldersResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ShareHoldersResourceIT {

    private static final String DEFAULT_COMPETITOR_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_COMPETITOR_SIREN = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE_PERSONNE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE_PERSONNE = "BBBBBBBBBB";

    private static final String DEFAULT_DENOMINATION = "AAAAAAAAAA";
    private static final String UPDATED_DENOMINATION = "BBBBBBBBBB";

    private static final String DEFAULT_CIVILITE = "AAAAAAAAAA";
    private static final String UPDATED_CIVILITE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_PATRONYMIQUE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_PATRONYMIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_NOM_USAGE = "AAAAAAAAAA";
    private static final String UPDATED_NOM_USAGE = "BBBBBBBBBB";

    private static final String DEFAULT_PRENOM = "AAAAAAAAAA";
    private static final String UPDATED_PRENOM = "BBBBBBBBBB";

    private static final String DEFAULT_LIBELLE_FORME_JURIDIQUE = "AAAAAAAAAA";
    private static final String UPDATED_LIBELLE_FORME_JURIDIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_FORME_JURIDIQUE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_FORME_JURIDIQUE = "BBBBBBBBBB";

    private static final String DEFAULT_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_SIREN = "BBBBBBBBBB";

    private static final String DEFAULT_CODE_APE = "AAAAAAAAAA";
    private static final String UPDATED_CODE_APE = "BBBBBBBBBB";

    private static final String DEFAULT_DATE_NAISSANCE = "AAAAAAAAAA";
    private static final String UPDATED_DATE_NAISSANCE = "BBBBBBBBBB";

    private static final Long DEFAULT_NBR_PARTS = 1L;
    private static final Long UPDATED_NBR_PARTS = 2L;

    private static final Double DEFAULT_POURCENTAGE_DETENTION = 1D;
    private static final Double UPDATED_POURCENTAGE_DETENTION = 2D;

    private static final Boolean DEFAULT_OLD = false;
    private static final Boolean UPDATED_OLD = true;

    @Autowired
    private ShareHoldersRepository shareHoldersRepository;

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

    private MockMvc restShareHoldersMockMvc;

    private ShareHolders shareHolders;

    private InfogreffeService infogreffeService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ShareHoldersResource shareHoldersResource = new ShareHoldersResource(shareHoldersRepository, infogreffeService);
        this.restShareHoldersMockMvc = MockMvcBuilders.standaloneSetup(shareHoldersResource)
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
    public static ShareHolders createEntity(EntityManager em) {
        ShareHolders shareHolders = new ShareHolders()
            .competitorSiren(DEFAULT_COMPETITOR_SIREN)
            .typePersonne(DEFAULT_TYPE_PERSONNE)
            .denomination(DEFAULT_DENOMINATION)
            .civilite(DEFAULT_CIVILITE)
            .nomPatronymique(DEFAULT_NOM_PATRONYMIQUE)
            .nomUsage(DEFAULT_NOM_USAGE)
            .prenom(DEFAULT_PRENOM)
            .libelleFormeJuridique(DEFAULT_LIBELLE_FORME_JURIDIQUE)
            .codeFormeJuridique(DEFAULT_CODE_FORME_JURIDIQUE)
            .siren(DEFAULT_SIREN)
            .codeApe(DEFAULT_CODE_APE)
            .dateNaissance(DEFAULT_DATE_NAISSANCE)
            .nbrParts(DEFAULT_NBR_PARTS)
            .pourcentageDetention(DEFAULT_POURCENTAGE_DETENTION)
            .old(DEFAULT_OLD);
        return shareHolders;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ShareHolders createUpdatedEntity(EntityManager em) {
        ShareHolders shareHolders = new ShareHolders()
            .competitorSiren(UPDATED_COMPETITOR_SIREN)
            .typePersonne(UPDATED_TYPE_PERSONNE)
            .denomination(UPDATED_DENOMINATION)
            .civilite(UPDATED_CIVILITE)
            .nomPatronymique(UPDATED_NOM_PATRONYMIQUE)
            .nomUsage(UPDATED_NOM_USAGE)
            .prenom(UPDATED_PRENOM)
            .libelleFormeJuridique(UPDATED_LIBELLE_FORME_JURIDIQUE)
            .codeFormeJuridique(UPDATED_CODE_FORME_JURIDIQUE)
            .siren(UPDATED_SIREN)
            .codeApe(UPDATED_CODE_APE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .nbrParts(UPDATED_NBR_PARTS)
            .pourcentageDetention(UPDATED_POURCENTAGE_DETENTION)
            .old(UPDATED_OLD);
        return shareHolders;
    }

    @BeforeEach
    public void initTest() {
        shareHolders = createEntity(em);
    }

    @Test
    @Transactional
    public void createShareHolders() throws Exception {
        int databaseSizeBeforeCreate = shareHoldersRepository.findAll().size();

        // Create the ShareHolders
        restShareHoldersMockMvc.perform(post("/api/share-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shareHolders)))
            .andExpect(status().isCreated());

        // Validate the ShareHolders in the database
        List<ShareHolders> shareHoldersList = shareHoldersRepository.findAll();
        assertThat(shareHoldersList).hasSize(databaseSizeBeforeCreate + 1);
        ShareHolders testShareHolders = shareHoldersList.get(shareHoldersList.size() - 1);
        assertThat(testShareHolders.getCompetitorSiren()).isEqualTo(DEFAULT_COMPETITOR_SIREN);
        assertThat(testShareHolders.getTypePersonne()).isEqualTo(DEFAULT_TYPE_PERSONNE);
        assertThat(testShareHolders.getDenomination()).isEqualTo(DEFAULT_DENOMINATION);
        assertThat(testShareHolders.getCivilite()).isEqualTo(DEFAULT_CIVILITE);
        assertThat(testShareHolders.getNomPatronymique()).isEqualTo(DEFAULT_NOM_PATRONYMIQUE);
        assertThat(testShareHolders.getNomUsage()).isEqualTo(DEFAULT_NOM_USAGE);
        assertThat(testShareHolders.getPrenom()).isEqualTo(DEFAULT_PRENOM);
        assertThat(testShareHolders.getLibelleFormeJuridique()).isEqualTo(DEFAULT_LIBELLE_FORME_JURIDIQUE);
        assertThat(testShareHolders.getCodeFormeJuridique()).isEqualTo(DEFAULT_CODE_FORME_JURIDIQUE);
        assertThat(testShareHolders.getSiren()).isEqualTo(DEFAULT_SIREN);
        assertThat(testShareHolders.getCodeApe()).isEqualTo(DEFAULT_CODE_APE);
        assertThat(testShareHolders.getDateNaissance()).isEqualTo(DEFAULT_DATE_NAISSANCE);
        assertThat(testShareHolders.getNbrParts()).isEqualTo(DEFAULT_NBR_PARTS);
        assertThat(testShareHolders.getPourcentageDetention()).isEqualTo(DEFAULT_POURCENTAGE_DETENTION);
        assertThat(testShareHolders.isOld()).isEqualTo(DEFAULT_OLD);
    }

    @Test
    @Transactional
    public void createShareHoldersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = shareHoldersRepository.findAll().size();

        // Create the ShareHolders with an existing ID
        shareHolders.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restShareHoldersMockMvc.perform(post("/api/share-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shareHolders)))
            .andExpect(status().isBadRequest());

        // Validate the ShareHolders in the database
        List<ShareHolders> shareHoldersList = shareHoldersRepository.findAll();
        assertThat(shareHoldersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllShareHolders() throws Exception {
        // Initialize the database
        shareHoldersRepository.saveAndFlush(shareHolders);

        // Get all the shareHoldersList
        restShareHoldersMockMvc.perform(get("/api/share-holders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(shareHolders.getId().intValue())))
            .andExpect(jsonPath("$.[*].competitorSiren").value(hasItem(DEFAULT_COMPETITOR_SIREN)))
            .andExpect(jsonPath("$.[*].typePersonne").value(hasItem(DEFAULT_TYPE_PERSONNE)))
            .andExpect(jsonPath("$.[*].denomination").value(hasItem(DEFAULT_DENOMINATION)))
            .andExpect(jsonPath("$.[*].civilite").value(hasItem(DEFAULT_CIVILITE)))
            .andExpect(jsonPath("$.[*].nomPatronymique").value(hasItem(DEFAULT_NOM_PATRONYMIQUE)))
            .andExpect(jsonPath("$.[*].nomUsage").value(hasItem(DEFAULT_NOM_USAGE)))
            .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM)))
            .andExpect(jsonPath("$.[*].libelleFormeJuridique").value(hasItem(DEFAULT_LIBELLE_FORME_JURIDIQUE)))
            .andExpect(jsonPath("$.[*].codeFormeJuridique").value(hasItem(DEFAULT_CODE_FORME_JURIDIQUE)))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN)))
            .andExpect(jsonPath("$.[*].codeApe").value(hasItem(DEFAULT_CODE_APE)))
            .andExpect(jsonPath("$.[*].dateNaissance").value(hasItem(DEFAULT_DATE_NAISSANCE)))
            .andExpect(jsonPath("$.[*].nbrParts").value(hasItem(DEFAULT_NBR_PARTS.intValue())))
            .andExpect(jsonPath("$.[*].pourcentageDetention").value(hasItem(DEFAULT_POURCENTAGE_DETENTION.doubleValue())))
            .andExpect(jsonPath("$.[*].old").value(hasItem(DEFAULT_OLD.booleanValue())));
    }

    @Test
    @Transactional
    public void getShareHolders() throws Exception {
        // Initialize the database
        shareHoldersRepository.saveAndFlush(shareHolders);

        // Get the shareHolders
        restShareHoldersMockMvc.perform(get("/api/share-holders/{id}", shareHolders.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(shareHolders.getId().intValue()))
            .andExpect(jsonPath("$.competitorSiren").value(DEFAULT_COMPETITOR_SIREN))
            .andExpect(jsonPath("$.typePersonne").value(DEFAULT_TYPE_PERSONNE))
            .andExpect(jsonPath("$.denomination").value(DEFAULT_DENOMINATION))
            .andExpect(jsonPath("$.civilite").value(DEFAULT_CIVILITE))
            .andExpect(jsonPath("$.nomPatronymique").value(DEFAULT_NOM_PATRONYMIQUE))
            .andExpect(jsonPath("$.nomUsage").value(DEFAULT_NOM_USAGE))
            .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM))
            .andExpect(jsonPath("$.libelleFormeJuridique").value(DEFAULT_LIBELLE_FORME_JURIDIQUE))
            .andExpect(jsonPath("$.codeFormeJuridique").value(DEFAULT_CODE_FORME_JURIDIQUE))
            .andExpect(jsonPath("$.siren").value(DEFAULT_SIREN))
            .andExpect(jsonPath("$.codeApe").value(DEFAULT_CODE_APE))
            .andExpect(jsonPath("$.dateNaissance").value(DEFAULT_DATE_NAISSANCE))
            .andExpect(jsonPath("$.nbrParts").value(DEFAULT_NBR_PARTS.intValue()))
            .andExpect(jsonPath("$.pourcentageDetention").value(DEFAULT_POURCENTAGE_DETENTION.doubleValue()))
            .andExpect(jsonPath("$.old").value(DEFAULT_OLD.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingShareHolders() throws Exception {
        // Get the shareHolders
        restShareHoldersMockMvc.perform(get("/api/share-holders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateShareHolders() throws Exception {
        // Initialize the database
        shareHoldersRepository.saveAndFlush(shareHolders);

        int databaseSizeBeforeUpdate = shareHoldersRepository.findAll().size();

        // Update the shareHolders
        ShareHolders updatedShareHolders = shareHoldersRepository.findById(shareHolders.getId()).get();
        // Disconnect from session so that the updates on updatedShareHolders are not directly saved in db
        em.detach(updatedShareHolders);
        updatedShareHolders
            .competitorSiren(UPDATED_COMPETITOR_SIREN)
            .typePersonne(UPDATED_TYPE_PERSONNE)
            .denomination(UPDATED_DENOMINATION)
            .civilite(UPDATED_CIVILITE)
            .nomPatronymique(UPDATED_NOM_PATRONYMIQUE)
            .nomUsage(UPDATED_NOM_USAGE)
            .prenom(UPDATED_PRENOM)
            .libelleFormeJuridique(UPDATED_LIBELLE_FORME_JURIDIQUE)
            .codeFormeJuridique(UPDATED_CODE_FORME_JURIDIQUE)
            .siren(UPDATED_SIREN)
            .codeApe(UPDATED_CODE_APE)
            .dateNaissance(UPDATED_DATE_NAISSANCE)
            .nbrParts(UPDATED_NBR_PARTS)
            .pourcentageDetention(UPDATED_POURCENTAGE_DETENTION)
            .old(UPDATED_OLD);

        restShareHoldersMockMvc.perform(put("/api/share-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedShareHolders)))
            .andExpect(status().isOk());

        // Validate the ShareHolders in the database
        List<ShareHolders> shareHoldersList = shareHoldersRepository.findAll();
        assertThat(shareHoldersList).hasSize(databaseSizeBeforeUpdate);
        ShareHolders testShareHolders = shareHoldersList.get(shareHoldersList.size() - 1);
        assertThat(testShareHolders.getCompetitorSiren()).isEqualTo(UPDATED_COMPETITOR_SIREN);
        assertThat(testShareHolders.getTypePersonne()).isEqualTo(UPDATED_TYPE_PERSONNE);
        assertThat(testShareHolders.getDenomination()).isEqualTo(UPDATED_DENOMINATION);
        assertThat(testShareHolders.getCivilite()).isEqualTo(UPDATED_CIVILITE);
        assertThat(testShareHolders.getNomPatronymique()).isEqualTo(UPDATED_NOM_PATRONYMIQUE);
        assertThat(testShareHolders.getNomUsage()).isEqualTo(UPDATED_NOM_USAGE);
        assertThat(testShareHolders.getPrenom()).isEqualTo(UPDATED_PRENOM);
        assertThat(testShareHolders.getLibelleFormeJuridique()).isEqualTo(UPDATED_LIBELLE_FORME_JURIDIQUE);
        assertThat(testShareHolders.getCodeFormeJuridique()).isEqualTo(UPDATED_CODE_FORME_JURIDIQUE);
        assertThat(testShareHolders.getSiren()).isEqualTo(UPDATED_SIREN);
        assertThat(testShareHolders.getCodeApe()).isEqualTo(UPDATED_CODE_APE);
        assertThat(testShareHolders.getDateNaissance()).isEqualTo(UPDATED_DATE_NAISSANCE);
        assertThat(testShareHolders.getNbrParts()).isEqualTo(UPDATED_NBR_PARTS);
        assertThat(testShareHolders.getPourcentageDetention()).isEqualTo(UPDATED_POURCENTAGE_DETENTION);
        assertThat(testShareHolders.isOld()).isEqualTo(UPDATED_OLD);
    }

    @Test
    @Transactional
    public void updateNonExistingShareHolders() throws Exception {
        int databaseSizeBeforeUpdate = shareHoldersRepository.findAll().size();

        // Create the ShareHolders

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restShareHoldersMockMvc.perform(put("/api/share-holders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(shareHolders)))
            .andExpect(status().isBadRequest());

        // Validate the ShareHolders in the database
        List<ShareHolders> shareHoldersList = shareHoldersRepository.findAll();
        assertThat(shareHoldersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteShareHolders() throws Exception {
        // Initialize the database
        shareHoldersRepository.saveAndFlush(shareHolders);

        int databaseSizeBeforeDelete = shareHoldersRepository.findAll().size();

        // Delete the shareHolders
        restShareHoldersMockMvc.perform(delete("/api/share-holders/{id}", shareHolders.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ShareHolders> shareHoldersList = shareHoldersRepository.findAll();
        assertThat(shareHoldersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ShareHolders.class);
        ShareHolders shareHolders1 = new ShareHolders();
        shareHolders1.setId(1L);
        ShareHolders shareHolders2 = new ShareHolders();
        shareHolders2.setId(shareHolders1.getId());
        assertThat(shareHolders1).isEqualTo(shareHolders2);
        shareHolders2.setId(2L);
        assertThat(shareHolders1).isNotEqualTo(shareHolders2);
        shareHolders1.setId(null);
        assertThat(shareHolders1).isNotEqualTo(shareHolders2);
    }
}
