package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListCountries;
import fr.ippon.kompetitors.repository.ListCountriesRepository;
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
 * Integration tests for the {@link ListCountriesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListCountriesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListCountriesRepository listCountriesRepository;

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

    private MockMvc restListCountriesMockMvc;

    private ListCountries listCountries;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListCountriesResource listCountriesResource = new ListCountriesResource(listCountriesRepository);
        this.restListCountriesMockMvc = MockMvcBuilders.standaloneSetup(listCountriesResource)
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
    public static ListCountries createEntity(EntityManager em) {
        ListCountries listCountries = new ListCountries()
            .value(DEFAULT_VALUE);
        return listCountries;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListCountries createUpdatedEntity(EntityManager em) {
        ListCountries listCountries = new ListCountries()
            .value(UPDATED_VALUE);
        return listCountries;
    }

    @BeforeEach
    public void initTest() {
        listCountries = createEntity(em);
    }

    @Test
    @Transactional
    public void createListCountries() throws Exception {
        int databaseSizeBeforeCreate = listCountriesRepository.findAll().size();

        // Create the ListCountries
        restListCountriesMockMvc.perform(post("/api/list-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCountries)))
            .andExpect(status().isCreated());

        // Validate the ListCountries in the database
        List<ListCountries> listCountriesList = listCountriesRepository.findAll();
        assertThat(listCountriesList).hasSize(databaseSizeBeforeCreate + 1);
        ListCountries testListCountries = listCountriesList.get(listCountriesList.size() - 1);
        assertThat(testListCountries.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListCountriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listCountriesRepository.findAll().size();

        // Create the ListCountries with an existing ID
        listCountries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListCountriesMockMvc.perform(post("/api/list-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCountries)))
            .andExpect(status().isBadRequest());

        // Validate the ListCountries in the database
        List<ListCountries> listCountriesList = listCountriesRepository.findAll();
        assertThat(listCountriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listCountriesRepository.findAll().size();
        // set the field null
        listCountries.setValue(null);

        // Create the ListCountries, which fails.

        restListCountriesMockMvc.perform(post("/api/list-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCountries)))
            .andExpect(status().isBadRequest());

        List<ListCountries> listCountriesList = listCountriesRepository.findAll();
        assertThat(listCountriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListCountries() throws Exception {
        // Initialize the database
        listCountriesRepository.saveAndFlush(listCountries);

        // Get all the listCountriesList
        restListCountriesMockMvc.perform(get("/api/list-countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listCountries.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListCountries() throws Exception {
        // Initialize the database
        listCountriesRepository.saveAndFlush(listCountries);

        // Get the listCountries
        restListCountriesMockMvc.perform(get("/api/list-countries/{id}", listCountries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listCountries.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListCountries() throws Exception {
        // Get the listCountries
        restListCountriesMockMvc.perform(get("/api/list-countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListCountries() throws Exception {
        // Initialize the database
        listCountriesRepository.saveAndFlush(listCountries);

        int databaseSizeBeforeUpdate = listCountriesRepository.findAll().size();

        // Update the listCountries
        ListCountries updatedListCountries = listCountriesRepository.findById(listCountries.getId()).get();
        // Disconnect from session so that the updates on updatedListCountries are not directly saved in db
        em.detach(updatedListCountries);
        updatedListCountries
            .value(UPDATED_VALUE);

        restListCountriesMockMvc.perform(put("/api/list-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedListCountries)))
            .andExpect(status().isOk());

        // Validate the ListCountries in the database
        List<ListCountries> listCountriesList = listCountriesRepository.findAll();
        assertThat(listCountriesList).hasSize(databaseSizeBeforeUpdate);
        ListCountries testListCountries = listCountriesList.get(listCountriesList.size() - 1);
        assertThat(testListCountries.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListCountries() throws Exception {
        int databaseSizeBeforeUpdate = listCountriesRepository.findAll().size();

        // Create the ListCountries

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListCountriesMockMvc.perform(put("/api/list-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCountries)))
            .andExpect(status().isBadRequest());

        // Validate the ListCountries in the database
        List<ListCountries> listCountriesList = listCountriesRepository.findAll();
        assertThat(listCountriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListCountries() throws Exception {
        // Initialize the database
        listCountriesRepository.saveAndFlush(listCountries);

        int databaseSizeBeforeDelete = listCountriesRepository.findAll().size();

        // Delete the listCountries
        restListCountriesMockMvc.perform(delete("/api/list-countries/{id}", listCountries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListCountries> listCountriesList = listCountriesRepository.findAll();
        assertThat(listCountriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListCountries.class);
        ListCountries listCountries1 = new ListCountries();
        listCountries1.setId(1L);
        ListCountries listCountries2 = new ListCountries();
        listCountries2.setId(listCountries1.getId());
        assertThat(listCountries1).isEqualTo(listCountries2);
        listCountries2.setId(2L);
        assertThat(listCountries1).isNotEqualTo(listCountries2);
        listCountries1.setId(null);
        assertThat(listCountries1).isNotEqualTo(listCountries2);
    }
}
