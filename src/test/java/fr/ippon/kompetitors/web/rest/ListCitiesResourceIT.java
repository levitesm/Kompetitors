package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListCities;
import fr.ippon.kompetitors.repository.ListCitiesRepository;
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
 * Integration tests for the {@link ListCitiesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListCitiesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListCitiesRepository listCitiesRepository;

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

    private MockMvc restListCitiesMockMvc;

    private ListCities listCities;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListCitiesResource listCitiesResource = new ListCitiesResource(listCitiesRepository);
        this.restListCitiesMockMvc = MockMvcBuilders.standaloneSetup(listCitiesResource)
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
    public static ListCities createEntity(EntityManager em) {
        ListCities listCities = new ListCities()
            .value(DEFAULT_VALUE);
        return listCities;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListCities createUpdatedEntity(EntityManager em) {
        ListCities listCities = new ListCities()
            .value(UPDATED_VALUE);
        return listCities;
    }

    @BeforeEach
    public void initTest() {
        listCities = createEntity(em);
    }

    @Test
    @Transactional
    public void createListCities() throws Exception {
        int databaseSizeBeforeCreate = listCitiesRepository.findAll().size();

        // Create the ListCities
        restListCitiesMockMvc.perform(post("/api/list-cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCities)))
            .andExpect(status().isCreated());

        // Validate the ListCities in the database
        List<ListCities> listCitiesList = listCitiesRepository.findAll();
        assertThat(listCitiesList).hasSize(databaseSizeBeforeCreate + 1);
        ListCities testListCities = listCitiesList.get(listCitiesList.size() - 1);
        assertThat(testListCities.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListCitiesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listCitiesRepository.findAll().size();

        // Create the ListCities with an existing ID
        listCities.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListCitiesMockMvc.perform(post("/api/list-cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCities)))
            .andExpect(status().isBadRequest());

        // Validate the ListCities in the database
        List<ListCities> listCitiesList = listCitiesRepository.findAll();
        assertThat(listCitiesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listCitiesRepository.findAll().size();
        // set the field null
        listCities.setValue(null);

        // Create the ListCities, which fails.

        restListCitiesMockMvc.perform(post("/api/list-cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCities)))
            .andExpect(status().isBadRequest());

        List<ListCities> listCitiesList = listCitiesRepository.findAll();
        assertThat(listCitiesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListCities() throws Exception {
        // Initialize the database
        listCitiesRepository.saveAndFlush(listCities);

        // Get all the listCitiesList
        restListCitiesMockMvc.perform(get("/api/list-cities?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listCities.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListCities() throws Exception {
        // Initialize the database
        listCitiesRepository.saveAndFlush(listCities);

        // Get the listCities
        restListCitiesMockMvc.perform(get("/api/list-cities/{id}", listCities.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listCities.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListCities() throws Exception {
        // Get the listCities
        restListCitiesMockMvc.perform(get("/api/list-cities/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListCities() throws Exception {
        // Initialize the database
        listCitiesRepository.saveAndFlush(listCities);

        int databaseSizeBeforeUpdate = listCitiesRepository.findAll().size();

        // Update the listCities
        ListCities updatedListCities = listCitiesRepository.findById(listCities.getId()).get();
        // Disconnect from session so that the updates on updatedListCities are not directly saved in db
        em.detach(updatedListCities);
        updatedListCities
            .value(UPDATED_VALUE);

        restListCitiesMockMvc.perform(put("/api/list-cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedListCities)))
            .andExpect(status().isOk());

        // Validate the ListCities in the database
        List<ListCities> listCitiesList = listCitiesRepository.findAll();
        assertThat(listCitiesList).hasSize(databaseSizeBeforeUpdate);
        ListCities testListCities = listCitiesList.get(listCitiesList.size() - 1);
        assertThat(testListCities.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListCities() throws Exception {
        int databaseSizeBeforeUpdate = listCitiesRepository.findAll().size();

        // Create the ListCities

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListCitiesMockMvc.perform(put("/api/list-cities")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCities)))
            .andExpect(status().isBadRequest());

        // Validate the ListCities in the database
        List<ListCities> listCitiesList = listCitiesRepository.findAll();
        assertThat(listCitiesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListCities() throws Exception {
        // Initialize the database
        listCitiesRepository.saveAndFlush(listCities);

        int databaseSizeBeforeDelete = listCitiesRepository.findAll().size();

        // Delete the listCities
        restListCitiesMockMvc.perform(delete("/api/list-cities/{id}", listCities.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListCities> listCitiesList = listCitiesRepository.findAll();
        assertThat(listCitiesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListCities.class);
        ListCities listCities1 = new ListCities();
        listCities1.setId(1L);
        ListCities listCities2 = new ListCities();
        listCities2.setId(listCities1.getId());
        assertThat(listCities1).isEqualTo(listCities2);
        listCities2.setId(2L);
        assertThat(listCities1).isNotEqualTo(listCities2);
        listCities1.setId(null);
        assertThat(listCities1).isNotEqualTo(listCities2);
    }
}
