package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListActivities;
import fr.ippon.kompetitors.repository.ListActivitiesRepository;
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
 * Integration tests for the {@link ListActivitiesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListActivitiesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListActivitiesRepository listActivitiesRepository;

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

    private MockMvc restListActivitiesMockMvc;

    private ListActivities listActivities;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListActivitiesResource listActivitiesResource = new ListActivitiesResource(listActivitiesRepository);
        this.restListActivitiesMockMvc = MockMvcBuilders.standaloneSetup(listActivitiesResource)
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
    public static ListActivities createEntity(EntityManager em) {
        ListActivities listActivities = new ListActivities()
            .value(DEFAULT_VALUE);
        return listActivities;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListActivities createUpdatedEntity(EntityManager em) {
        ListActivities listActivities = new ListActivities()
            .value(UPDATED_VALUE);
        return listActivities;
    }

    @BeforeEach
    public void initTest() {
        listActivities = createEntity(em);
    }

    @Test
    @Transactional
    public void createListActivities() throws Exception {
        int databaseSizeBeforeCreate = listActivitiesRepository.findAll().size();

        // Create the ListActivities
        restListActivitiesMockMvc.perform(post("/api/list-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listActivities)))
            .andExpect(status().isCreated());

        // Validate the ListActivities in the database
        List<ListActivities> listActivitiesList = listActivitiesRepository.findAll();
        assertThat(listActivitiesList).hasSize(databaseSizeBeforeCreate + 1);
        ListActivities testListActivities = listActivitiesList.get(listActivitiesList.size() - 1);
        assertThat(testListActivities.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListActivitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listActivitiesRepository.findAll().size();

        // Create the ListActivities with an existing ID
        listActivities.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListActivitiesMockMvc.perform(post("/api/list-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listActivities)))
            .andExpect(status().isBadRequest());

        // Validate the ListActivities in the database
        List<ListActivities> listActivitiesList = listActivitiesRepository.findAll();
        assertThat(listActivitiesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listActivitiesRepository.findAll().size();
        // set the field null
        listActivities.setValue(null);

        // Create the ListActivities, which fails.

        restListActivitiesMockMvc.perform(post("/api/list-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listActivities)))
            .andExpect(status().isBadRequest());

        List<ListActivities> listActivitiesList = listActivitiesRepository.findAll();
        assertThat(listActivitiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListActivities() throws Exception {
        // Initialize the database
        listActivitiesRepository.saveAndFlush(listActivities);

        // Get all the listActivitiesList
        restListActivitiesMockMvc.perform(get("/api/list-activities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listActivities.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListActivities() throws Exception {
        // Initialize the database
        listActivitiesRepository.saveAndFlush(listActivities);

        // Get the listActivities
        restListActivitiesMockMvc.perform(get("/api/list-activities/{id}", listActivities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listActivities.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListActivities() throws Exception {
        // Get the listActivities
        restListActivitiesMockMvc.perform(get("/api/list-activities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListActivities() throws Exception {
        // Initialize the database
        listActivitiesRepository.saveAndFlush(listActivities);

        int databaseSizeBeforeUpdate = listActivitiesRepository.findAll().size();

        // Update the listActivities
        ListActivities updatedListActivities = listActivitiesRepository.findById(listActivities.getId()).get();
        // Disconnect from session so that the updates on updatedListActivities are not directly saved in db
        em.detach(updatedListActivities);
        updatedListActivities
            .value(UPDATED_VALUE);

        restListActivitiesMockMvc.perform(put("/api/list-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedListActivities)))
            .andExpect(status().isOk());

        // Validate the ListActivities in the database
        List<ListActivities> listActivitiesList = listActivitiesRepository.findAll();
        assertThat(listActivitiesList).hasSize(databaseSizeBeforeUpdate);
        ListActivities testListActivities = listActivitiesList.get(listActivitiesList.size() - 1);
        assertThat(testListActivities.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListActivities() throws Exception {
        int databaseSizeBeforeUpdate = listActivitiesRepository.findAll().size();

        // Create the ListActivities

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListActivitiesMockMvc.perform(put("/api/list-activities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listActivities)))
            .andExpect(status().isBadRequest());

        // Validate the ListActivities in the database
        List<ListActivities> listActivitiesList = listActivitiesRepository.findAll();
        assertThat(listActivitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListActivities() throws Exception {
        // Initialize the database
        listActivitiesRepository.saveAndFlush(listActivities);

        int databaseSizeBeforeDelete = listActivitiesRepository.findAll().size();

        // Delete the listActivities
        restListActivitiesMockMvc.perform(delete("/api/list-activities/{id}", listActivities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListActivities> listActivitiesList = listActivitiesRepository.findAll();
        assertThat(listActivitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListActivities.class);
        ListActivities listActivities1 = new ListActivities();
        listActivities1.setId(1L);
        ListActivities listActivities2 = new ListActivities();
        listActivities2.setId(listActivities1.getId());
        assertThat(listActivities1).isEqualTo(listActivities2);
        listActivities2.setId(2L);
        assertThat(listActivities1).isNotEqualTo(listActivities2);
        listActivities1.setId(null);
        assertThat(listActivities1).isNotEqualTo(listActivities2);
    }
}
