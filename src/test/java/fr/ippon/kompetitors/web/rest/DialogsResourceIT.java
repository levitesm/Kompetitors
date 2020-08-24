package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.Dialogs;
import fr.ippon.kompetitors.repository.DialogsRepository;
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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import static fr.ippon.kompetitors.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DialogsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class DialogsResourceIT {

    private static final String DEFAULT_SECTION = "AAAAAAAAAA";
    private static final String UPDATED_SECTION = "BBBBBBBBBB";

    private static final String DEFAULT_TOPIC = "AAAAAAAAAA";
    private static final String UPDATED_TOPIC = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final String DEFAULT_AUTHOR = "AAAAAAAAAA";
    private static final String UPDATED_AUTHOR = "BBBBBBBBBB";

    private static final LocalDateTime DEFAULT_DATE = LocalDateTime.now();
    private static final LocalDateTime UPDATED_DATE = LocalDateTime.now(ZoneId.systemDefault());

    @Autowired
    private DialogsRepository dialogsRepository;

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

    private MockMvc restDialogsMockMvc;

    private Dialogs dialogs;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DialogsResource dialogsResource = new DialogsResource(dialogsRepository);
        this.restDialogsMockMvc = MockMvcBuilders.standaloneSetup(dialogsResource)
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
    public static Dialogs createEntity(EntityManager em) {
        Dialogs dialogs = new Dialogs()
            .section(DEFAULT_SECTION)
            .topic(DEFAULT_TOPIC)
            .message(DEFAULT_MESSAGE)
            .author(DEFAULT_AUTHOR)
            .date(DEFAULT_DATE);
        return dialogs;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Dialogs createUpdatedEntity(EntityManager em) {
        Dialogs dialogs = new Dialogs()
            .section(UPDATED_SECTION)
            .topic(UPDATED_TOPIC)
            .message(UPDATED_MESSAGE)
            .author(UPDATED_AUTHOR)
            .date(UPDATED_DATE);
        return dialogs;
    }

    @BeforeEach
    public void initTest() {
        dialogs = createEntity(em);
    }

    @Test
    @Transactional
    public void createDialogs() throws Exception {
        int databaseSizeBeforeCreate = dialogsRepository.findAll().size();

        // Create the Dialogs
        restDialogsMockMvc.perform(post("/api/dialogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialogs)))
            .andExpect(status().isCreated());

        // Validate the Dialogs in the database
        List<Dialogs> dialogsList = dialogsRepository.findAll();
        assertThat(dialogsList).hasSize(databaseSizeBeforeCreate + 1);
        Dialogs testDialogs = dialogsList.get(dialogsList.size() - 1);
        assertThat(testDialogs.getSection()).isEqualTo(DEFAULT_SECTION);
        assertThat(testDialogs.getTopic()).isEqualTo(DEFAULT_TOPIC);
        assertThat(testDialogs.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testDialogs.getAuthor()).isEqualTo(DEFAULT_AUTHOR);
        assertThat(testDialogs.getDate()).isEqualTo(DEFAULT_DATE);
    }

    @Test
    @Transactional
    public void createDialogsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dialogsRepository.findAll().size();

        // Create the Dialogs with an existing ID
        dialogs.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDialogsMockMvc.perform(post("/api/dialogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialogs)))
            .andExpect(status().isBadRequest());

        // Validate the Dialogs in the database
        List<Dialogs> dialogsList = dialogsRepository.findAll();
        assertThat(dialogsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSectionIsRequired() throws Exception {
        int databaseSizeBeforeTest = dialogsRepository.findAll().size();
        // set the field null
        dialogs.setSection(null);

        // Create the Dialogs, which fails.

        restDialogsMockMvc.perform(post("/api/dialogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialogs)))
            .andExpect(status().isBadRequest());

        List<Dialogs> dialogsList = dialogsRepository.findAll();
        assertThat(dialogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTopicIsRequired() throws Exception {
        int databaseSizeBeforeTest = dialogsRepository.findAll().size();
        // set the field null
        dialogs.setTopic(null);

        // Create the Dialogs, which fails.

        restDialogsMockMvc.perform(post("/api/dialogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialogs)))
            .andExpect(status().isBadRequest());

        List<Dialogs> dialogsList = dialogsRepository.findAll();
        assertThat(dialogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkMessageIsRequired() throws Exception {
        int databaseSizeBeforeTest = dialogsRepository.findAll().size();
        // set the field null
        dialogs.setMessage(null);

        // Create the Dialogs, which fails.

        restDialogsMockMvc.perform(post("/api/dialogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialogs)))
            .andExpect(status().isBadRequest());

        List<Dialogs> dialogsList = dialogsRepository.findAll();
        assertThat(dialogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAuthorIsRequired() throws Exception {
        int databaseSizeBeforeTest = dialogsRepository.findAll().size();
        // set the field null
        dialogs.setAuthor(null);

        // Create the Dialogs, which fails.

        restDialogsMockMvc.perform(post("/api/dialogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialogs)))
            .andExpect(status().isBadRequest());

        List<Dialogs> dialogsList = dialogsRepository.findAll();
        assertThat(dialogsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDialogs() throws Exception {
        // Initialize the database
        dialogsRepository.saveAndFlush(dialogs);

        // Get all the dialogsList
        restDialogsMockMvc.perform(get("/api/dialogs?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dialogs.getId().intValue())))
            .andExpect(jsonPath("$.[*].section").value(hasItem(DEFAULT_SECTION)))
            .andExpect(jsonPath("$.[*].topic").value(hasItem(DEFAULT_TOPIC)))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE)))
            .andExpect(jsonPath("$.[*].author").value(hasItem(DEFAULT_AUTHOR)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())));
    }

    @Test
    @Transactional
    public void getDialogs() throws Exception {
        // Initialize the database
        dialogsRepository.saveAndFlush(dialogs);

        // Get the dialogs
        restDialogsMockMvc.perform(get("/api/dialogs/{id}", dialogs.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dialogs.getId().intValue()))
            .andExpect(jsonPath("$.section").value(DEFAULT_SECTION))
            .andExpect(jsonPath("$.topic").value(DEFAULT_TOPIC))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE))
            .andExpect(jsonPath("$.author").value(DEFAULT_AUTHOR))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDialogs() throws Exception {
        // Get the dialogs
        restDialogsMockMvc.perform(get("/api/dialogs/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDialogs() throws Exception {
        // Initialize the database
        dialogsRepository.saveAndFlush(dialogs);

        int databaseSizeBeforeUpdate = dialogsRepository.findAll().size();

        // Update the dialogs
        Dialogs updatedDialogs = dialogsRepository.findById(dialogs.getId()).get();
        // Disconnect from session so that the updates on updatedDialogs are not directly saved in db
        em.detach(updatedDialogs);
        updatedDialogs
            .section(UPDATED_SECTION)
            .topic(UPDATED_TOPIC)
            .message(UPDATED_MESSAGE)
            .author(UPDATED_AUTHOR)
            .date(UPDATED_DATE);

        restDialogsMockMvc.perform(put("/api/dialogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedDialogs)))
            .andExpect(status().isOk());

        // Validate the Dialogs in the database
        List<Dialogs> dialogsList = dialogsRepository.findAll();
        assertThat(dialogsList).hasSize(databaseSizeBeforeUpdate);
        Dialogs testDialogs = dialogsList.get(dialogsList.size() - 1);
        assertThat(testDialogs.getSection()).isEqualTo(UPDATED_SECTION);
        assertThat(testDialogs.getTopic()).isEqualTo(UPDATED_TOPIC);
        assertThat(testDialogs.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testDialogs.getAuthor()).isEqualTo(UPDATED_AUTHOR);
        assertThat(testDialogs.getDate()).isEqualTo(UPDATED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDialogs() throws Exception {
        int databaseSizeBeforeUpdate = dialogsRepository.findAll().size();

        // Create the Dialogs

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDialogsMockMvc.perform(put("/api/dialogs")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dialogs)))
            .andExpect(status().isBadRequest());

        // Validate the Dialogs in the database
        List<Dialogs> dialogsList = dialogsRepository.findAll();
        assertThat(dialogsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDialogs() throws Exception {
        // Initialize the database
        dialogsRepository.saveAndFlush(dialogs);

        int databaseSizeBeforeDelete = dialogsRepository.findAll().size();

        // Delete the dialogs
        restDialogsMockMvc.perform(delete("/api/dialogs/{id}", dialogs.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Dialogs> dialogsList = dialogsRepository.findAll();
        assertThat(dialogsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Dialogs.class);
        Dialogs dialogs1 = new Dialogs();
        dialogs1.setId(1L);
        Dialogs dialogs2 = new Dialogs();
        dialogs2.setId(dialogs1.getId());
        assertThat(dialogs1).isEqualTo(dialogs2);
        dialogs2.setId(2L);
        assertThat(dialogs1).isNotEqualTo(dialogs2);
        dialogs1.setId(null);
        assertThat(dialogs1).isNotEqualTo(dialogs2);
    }
}
