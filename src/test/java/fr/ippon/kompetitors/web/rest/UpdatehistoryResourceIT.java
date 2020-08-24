package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.Updatehistory;
import fr.ippon.kompetitors.repository.UpdatehistoryRepository;
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
 * Integration tests for the {@link UpdatehistoryResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class UpdatehistoryResourceIT {

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_SIREN = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    private static final String DEFAULT_RESPONCE = "AAAAAAAAAA";
    private static final String UPDATED_RESPONCE = "BBBBBBBBBB";

    @Autowired
    private UpdatehistoryRepository updatehistoryRepository;

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

    private MockMvc restUpdatehistoryMockMvc;

    private Updatehistory updatehistory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UpdatehistoryResource updatehistoryResource = new UpdatehistoryResource(updatehistoryRepository);
        this.restUpdatehistoryMockMvc = MockMvcBuilders.standaloneSetup(updatehistoryResource)
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
    public static Updatehistory createEntity(EntityManager em) {
        Updatehistory updatehistory = new Updatehistory()
            .type(DEFAULT_TYPE)
            .siren(DEFAULT_SIREN)
            .date(DEFAULT_DATE)
            .status(DEFAULT_STATUS)
            .responce(DEFAULT_RESPONCE);
        return updatehistory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Updatehistory createUpdatedEntity(EntityManager em) {
        Updatehistory updatehistory = new Updatehistory()
            .type(UPDATED_TYPE)
            .siren(UPDATED_SIREN)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS)
            .responce(UPDATED_RESPONCE);
        return updatehistory;
    }

    @BeforeEach
    public void initTest() {
        updatehistory = createEntity(em);
    }

    @Test
    @Transactional
    public void createUpdatehistory() throws Exception {
        int databaseSizeBeforeCreate = updatehistoryRepository.findAll().size();

        // Create the Updatehistory
        restUpdatehistoryMockMvc.perform(post("/api/updatehistories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatehistory)))
            .andExpect(status().isCreated());

        // Validate the Updatehistory in the database
        List<Updatehistory> updatehistoryList = updatehistoryRepository.findAll();
        assertThat(updatehistoryList).hasSize(databaseSizeBeforeCreate + 1);
        Updatehistory testUpdatehistory = updatehistoryList.get(updatehistoryList.size() - 1);
        assertThat(testUpdatehistory.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testUpdatehistory.getSiren()).isEqualTo(DEFAULT_SIREN);
        assertThat(testUpdatehistory.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testUpdatehistory.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUpdatehistory.getResponce()).isEqualTo(DEFAULT_RESPONCE);
    }

    @Test
    @Transactional
    public void createUpdatehistoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = updatehistoryRepository.findAll().size();

        // Create the Updatehistory with an existing ID
        updatehistory.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUpdatehistoryMockMvc.perform(post("/api/updatehistories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatehistory)))
            .andExpect(status().isBadRequest());

        // Validate the Updatehistory in the database
        List<Updatehistory> updatehistoryList = updatehistoryRepository.findAll();
        assertThat(updatehistoryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllUpdatehistories() throws Exception {
        // Initialize the database
        updatehistoryRepository.saveAndFlush(updatehistory);

        // Get all the updatehistoryList
        restUpdatehistoryMockMvc.perform(get("/api/updatehistories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(updatehistory.getId().intValue())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE)))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN)))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)))
            .andExpect(jsonPath("$.[*].responce").value(hasItem(DEFAULT_RESPONCE)));
    }
    
    @Test
    @Transactional
    public void getUpdatehistory() throws Exception {
        // Initialize the database
        updatehistoryRepository.saveAndFlush(updatehistory);

        // Get the updatehistory
        restUpdatehistoryMockMvc.perform(get("/api/updatehistories/{id}", updatehistory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(updatehistory.getId().intValue()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE))
            .andExpect(jsonPath("$.siren").value(DEFAULT_SIREN))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS))
            .andExpect(jsonPath("$.responce").value(DEFAULT_RESPONCE));
    }

    @Test
    @Transactional
    public void getNonExistingUpdatehistory() throws Exception {
        // Get the updatehistory
        restUpdatehistoryMockMvc.perform(get("/api/updatehistories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUpdatehistory() throws Exception {
        // Initialize the database
        updatehistoryRepository.saveAndFlush(updatehistory);

        int databaseSizeBeforeUpdate = updatehistoryRepository.findAll().size();

        // Update the updatehistory
        Updatehistory updatedUpdatehistory = updatehistoryRepository.findById(updatehistory.getId()).get();
        // Disconnect from session so that the updates on updatedUpdatehistory are not directly saved in db
        em.detach(updatedUpdatehistory);
        updatedUpdatehistory
            .type(UPDATED_TYPE)
            .siren(UPDATED_SIREN)
            .date(UPDATED_DATE)
            .status(UPDATED_STATUS)
            .responce(UPDATED_RESPONCE);

        restUpdatehistoryMockMvc.perform(put("/api/updatehistories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUpdatehistory)))
            .andExpect(status().isOk());

        // Validate the Updatehistory in the database
        List<Updatehistory> updatehistoryList = updatehistoryRepository.findAll();
        assertThat(updatehistoryList).hasSize(databaseSizeBeforeUpdate);
        Updatehistory testUpdatehistory = updatehistoryList.get(updatehistoryList.size() - 1);
        assertThat(testUpdatehistory.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testUpdatehistory.getSiren()).isEqualTo(UPDATED_SIREN);
        assertThat(testUpdatehistory.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testUpdatehistory.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUpdatehistory.getResponce()).isEqualTo(UPDATED_RESPONCE);
    }

    @Test
    @Transactional
    public void updateNonExistingUpdatehistory() throws Exception {
        int databaseSizeBeforeUpdate = updatehistoryRepository.findAll().size();

        // Create the Updatehistory

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUpdatehistoryMockMvc.perform(put("/api/updatehistories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatehistory)))
            .andExpect(status().isBadRequest());

        // Validate the Updatehistory in the database
        List<Updatehistory> updatehistoryList = updatehistoryRepository.findAll();
        assertThat(updatehistoryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUpdatehistory() throws Exception {
        // Initialize the database
        updatehistoryRepository.saveAndFlush(updatehistory);

        int databaseSizeBeforeDelete = updatehistoryRepository.findAll().size();

        // Delete the updatehistory
        restUpdatehistoryMockMvc.perform(delete("/api/updatehistories/{id}", updatehistory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Updatehistory> updatehistoryList = updatehistoryRepository.findAll();
        assertThat(updatehistoryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Updatehistory.class);
        Updatehistory updatehistory1 = new Updatehistory();
        updatehistory1.setId(1L);
        Updatehistory updatehistory2 = new Updatehistory();
        updatehistory2.setId(updatehistory1.getId());
        assertThat(updatehistory1).isEqualTo(updatehistory2);
        updatehistory2.setId(2L);
        assertThat(updatehistory1).isNotEqualTo(updatehistory2);
        updatehistory1.setId(null);
        assertThat(updatehistory1).isNotEqualTo(updatehistory2);
    }
}
