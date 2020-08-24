package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.GlobalGroups;
import fr.ippon.kompetitors.repository.GlobalGroupsRepository;
import fr.ippon.kompetitors.repository.OfficesRepository;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static fr.ippon.kompetitors.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link GlobalGroupsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class GlobalGroupsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_LOGO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGO = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LOGO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_WEB_SITE = "AAAAAAAAAA";
    private static final String UPDATED_WEB_SITE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_REFERENCE = false;
    private static final Boolean UPDATED_REFERENCE = true;

    @Autowired
    private GlobalGroupsRepository globalGroupsRepository;

    @Autowired
    private OfficesRepository officesRepository;

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

    private MockMvc restGlobalGroupsMockMvc;

    private GlobalGroups globalGroups;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final GlobalGroupsResource globalGroupsResource = new GlobalGroupsResource(globalGroupsRepository, officesRepository);
        this.restGlobalGroupsMockMvc = MockMvcBuilders.standaloneSetup(globalGroupsResource)
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
    public static GlobalGroups createEntity(EntityManager em) {
        GlobalGroups globalGroups = new GlobalGroups()
            .name(DEFAULT_NAME)
            .logo(DEFAULT_LOGO)
            .logoContentType(DEFAULT_LOGO_CONTENT_TYPE)
            .webSite(DEFAULT_WEB_SITE)
            .reference(DEFAULT_REFERENCE);
        return globalGroups;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static GlobalGroups createUpdatedEntity(EntityManager em) {
        GlobalGroups globalGroups = new GlobalGroups()
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .webSite(UPDATED_WEB_SITE)
            .reference(UPDATED_REFERENCE);
        return globalGroups;
    }

    @BeforeEach
    public void initTest() {
        globalGroups = createEntity(em);
    }

    @Test
    @Transactional
    public void createGlobalGroups() throws Exception {
        int databaseSizeBeforeCreate = globalGroupsRepository.findAll().size();

        // Create the GlobalGroups
        restGlobalGroupsMockMvc.perform(post("/api/global-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalGroups)))
            .andExpect(status().isCreated());

        // Validate the GlobalGroups in the database
        List<GlobalGroups> globalGroupsList = globalGroupsRepository.findAll();
        assertThat(globalGroupsList).hasSize(databaseSizeBeforeCreate + 1);
        GlobalGroups testGlobalGroups = globalGroupsList.get(globalGroupsList.size() - 1);
        assertThat(testGlobalGroups.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testGlobalGroups.getLogo()).isEqualTo(DEFAULT_LOGO);
        assertThat(testGlobalGroups.getLogoContentType()).isEqualTo(DEFAULT_LOGO_CONTENT_TYPE);
        assertThat(testGlobalGroups.getWebSite()).isEqualTo(DEFAULT_WEB_SITE);
        assertThat(testGlobalGroups.isReference()).isEqualTo(DEFAULT_REFERENCE);
    }

    @Test
    @Transactional
    public void createGlobalGroupsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = globalGroupsRepository.findAll().size();

        // Create the GlobalGroups with an existing ID
        globalGroups.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restGlobalGroupsMockMvc.perform(post("/api/global-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalGroups)))
            .andExpect(status().isBadRequest());

        // Validate the GlobalGroups in the database
        List<GlobalGroups> globalGroupsList = globalGroupsRepository.findAll();
        assertThat(globalGroupsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = globalGroupsRepository.findAll().size();
        // set the field null
        globalGroups.setName(null);

        // Create the GlobalGroups, which fails.

        restGlobalGroupsMockMvc.perform(post("/api/global-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalGroups)))
            .andExpect(status().isBadRequest());

        List<GlobalGroups> globalGroupsList = globalGroupsRepository.findAll();
        assertThat(globalGroupsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllGlobalGroups() throws Exception {
        // Initialize the database
        globalGroupsRepository.saveAndFlush(globalGroups);

        // Get all the globalGroupsList
        restGlobalGroupsMockMvc.perform(get("/api/global-groups?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(globalGroups.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].logoContentType").value(hasItem(DEFAULT_LOGO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].logo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGO))))
            .andExpect(jsonPath("$.[*].webSite").value(hasItem(DEFAULT_WEB_SITE)))
            .andExpect(jsonPath("$.[*].reference").value(hasItem(DEFAULT_REFERENCE.booleanValue())));
    }

    @Test
    @Transactional
    public void getGlobalGroups() throws Exception {
        // Initialize the database
        globalGroupsRepository.saveAndFlush(globalGroups);

        // Get the globalGroups
        restGlobalGroupsMockMvc.perform(get("/api/global-groups/{id}", globalGroups.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(globalGroups.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.logoContentType").value(DEFAULT_LOGO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logo").value(Base64Utils.encodeToString(DEFAULT_LOGO)))
            .andExpect(jsonPath("$.webSite").value(DEFAULT_WEB_SITE))
            .andExpect(jsonPath("$.reference").value(DEFAULT_REFERENCE.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingGlobalGroups() throws Exception {
        // Get the globalGroups
        restGlobalGroupsMockMvc.perform(get("/api/global-groups/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateGlobalGroups() throws Exception {
        // Initialize the database
        globalGroupsRepository.saveAndFlush(globalGroups);

        int databaseSizeBeforeUpdate = globalGroupsRepository.findAll().size();

        // Update the globalGroups
        GlobalGroups updatedGlobalGroups = globalGroupsRepository.findById(globalGroups.getId()).get();
        // Disconnect from session so that the updates on updatedGlobalGroups are not directly saved in db
        em.detach(updatedGlobalGroups);
        updatedGlobalGroups
            .name(UPDATED_NAME)
            .logo(UPDATED_LOGO)
            .logoContentType(UPDATED_LOGO_CONTENT_TYPE)
            .webSite(UPDATED_WEB_SITE)
            .reference(UPDATED_REFERENCE);

        restGlobalGroupsMockMvc.perform(put("/api/global-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedGlobalGroups)))
            .andExpect(status().isOk());

        // Validate the GlobalGroups in the database
        List<GlobalGroups> globalGroupsList = globalGroupsRepository.findAll();
        assertThat(globalGroupsList).hasSize(databaseSizeBeforeUpdate);
        GlobalGroups testGlobalGroups = globalGroupsList.get(globalGroupsList.size() - 1);
        assertThat(testGlobalGroups.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testGlobalGroups.getLogo()).isEqualTo(UPDATED_LOGO);
        assertThat(testGlobalGroups.getLogoContentType()).isEqualTo(UPDATED_LOGO_CONTENT_TYPE);
        assertThat(testGlobalGroups.getWebSite()).isEqualTo(UPDATED_WEB_SITE);
        assertThat(testGlobalGroups.isReference()).isEqualTo(UPDATED_REFERENCE);
    }

    @Test
    @Transactional
    public void updateNonExistingGlobalGroups() throws Exception {
        int databaseSizeBeforeUpdate = globalGroupsRepository.findAll().size();

        // Create the GlobalGroups

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restGlobalGroupsMockMvc.perform(put("/api/global-groups")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(globalGroups)))
            .andExpect(status().isBadRequest());

        // Validate the GlobalGroups in the database
        List<GlobalGroups> globalGroupsList = globalGroupsRepository.findAll();
        assertThat(globalGroupsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteGlobalGroups() throws Exception {
        // Initialize the database
        globalGroupsRepository.saveAndFlush(globalGroups);

        int databaseSizeBeforeDelete = globalGroupsRepository.findAll().size();

        // Delete the globalGroups
        restGlobalGroupsMockMvc.perform(delete("/api/global-groups/{id}", globalGroups.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<GlobalGroups> globalGroupsList = globalGroupsRepository.findAll();
        assertThat(globalGroupsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(GlobalGroups.class);
        GlobalGroups globalGroups1 = new GlobalGroups();
        globalGroups1.setId(1L);
        GlobalGroups globalGroups2 = new GlobalGroups();
        globalGroups2.setId(globalGroups1.getId());
        assertThat(globalGroups1).isEqualTo(globalGroups2);
        globalGroups2.setId(2L);
        assertThat(globalGroups1).isNotEqualTo(globalGroups2);
        globalGroups1.setId(null);
        assertThat(globalGroups1).isNotEqualTo(globalGroups2);
    }
}
