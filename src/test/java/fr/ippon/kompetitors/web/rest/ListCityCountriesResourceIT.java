package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListCityCountries;
import fr.ippon.kompetitors.repository.ListCityCountriesRepository;
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
 * Integration tests for the {@link ListCityCountriesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListCityCountriesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListCityCountriesRepository listCityCountriesRepository;

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

    private MockMvc restListCityCountriesMockMvc;

    private ListCityCountries listCityCountries;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListCityCountriesResource listCityCountriesResource = new ListCityCountriesResource(listCityCountriesRepository);
        this.restListCityCountriesMockMvc = MockMvcBuilders.standaloneSetup(listCityCountriesResource)
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
    public static ListCityCountries createEntity(EntityManager em) {
        ListCityCountries listCityCountries = new ListCityCountries()
            .value(DEFAULT_VALUE);
        return listCityCountries;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListCityCountries createUpdatedEntity(EntityManager em) {
        ListCityCountries listCityCountries = new ListCityCountries()
            .value(UPDATED_VALUE);
        return listCityCountries;
    }

    @BeforeEach
    public void initTest() {
        listCityCountries = createEntity(em);
    }

    @Test
    @Transactional
    public void createListCityCountries() throws Exception {
        int databaseSizeBeforeCreate = listCityCountriesRepository.findAll().size();

        // Create the ListCityCountries
        restListCityCountriesMockMvc.perform(post("/api/list-city-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCityCountries)))
            .andExpect(status().isCreated());

        // Validate the ListCityCountries in the database
        List<ListCityCountries> listCityCountriesList = listCityCountriesRepository.findAll();
        assertThat(listCityCountriesList).hasSize(databaseSizeBeforeCreate + 1);
        ListCityCountries testListCityCountries = listCityCountriesList.get(listCityCountriesList.size() - 1);
        assertThat(testListCityCountries.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListCityCountriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listCityCountriesRepository.findAll().size();

        // Create the ListCityCountries with an existing ID
        listCityCountries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListCityCountriesMockMvc.perform(post("/api/list-city-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCityCountries)))
            .andExpect(status().isBadRequest());

        // Validate the ListCityCountries in the database
        List<ListCityCountries> listCityCountriesList = listCityCountriesRepository.findAll();
        assertThat(listCityCountriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listCityCountriesRepository.findAll().size();
        // set the field null
        listCityCountries.setValue(null);

        // Create the ListCityCountries, which fails.

        restListCityCountriesMockMvc.perform(post("/api/list-city-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCityCountries)))
            .andExpect(status().isBadRequest());

        List<ListCityCountries> listCityCountriesList = listCityCountriesRepository.findAll();
        assertThat(listCityCountriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListCityCountries() throws Exception {
        // Initialize the database
        listCityCountriesRepository.saveAndFlush(listCityCountries);

        // Get all the listCityCountriesList
        restListCityCountriesMockMvc.perform(get("/api/list-city-countries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listCityCountries.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListCityCountries() throws Exception {
        // Initialize the database
        listCityCountriesRepository.saveAndFlush(listCityCountries);

        // Get the listCityCountries
        restListCityCountriesMockMvc.perform(get("/api/list-city-countries/{id}", listCityCountries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listCityCountries.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListCityCountries() throws Exception {
        // Get the listCityCountries
        restListCityCountriesMockMvc.perform(get("/api/list-city-countries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListCityCountries() throws Exception {
        // Initialize the database
        listCityCountriesRepository.saveAndFlush(listCityCountries);

        int databaseSizeBeforeUpdate = listCityCountriesRepository.findAll().size();

        // Update the listCityCountries
        ListCityCountries updatedListCityCountries = listCityCountriesRepository.findById(listCityCountries.getId()).get();
        // Disconnect from session so that the updates on updatedListCityCountries are not directly saved in db
        em.detach(updatedListCityCountries);
        updatedListCityCountries
            .value(UPDATED_VALUE);

        restListCityCountriesMockMvc.perform(put("/api/list-city-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedListCityCountries)))
            .andExpect(status().isOk());

        // Validate the ListCityCountries in the database
        List<ListCityCountries> listCityCountriesList = listCityCountriesRepository.findAll();
        assertThat(listCityCountriesList).hasSize(databaseSizeBeforeUpdate);
        ListCityCountries testListCityCountries = listCityCountriesList.get(listCityCountriesList.size() - 1);
        assertThat(testListCityCountries.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListCityCountries() throws Exception {
        int databaseSizeBeforeUpdate = listCityCountriesRepository.findAll().size();

        // Create the ListCityCountries

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListCityCountriesMockMvc.perform(put("/api/list-city-countries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCityCountries)))
            .andExpect(status().isBadRequest());

        // Validate the ListCityCountries in the database
        List<ListCityCountries> listCityCountriesList = listCityCountriesRepository.findAll();
        assertThat(listCityCountriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListCityCountries() throws Exception {
        // Initialize the database
        listCityCountriesRepository.saveAndFlush(listCityCountries);

        int databaseSizeBeforeDelete = listCityCountriesRepository.findAll().size();

        // Delete the listCityCountries
        restListCityCountriesMockMvc.perform(delete("/api/list-city-countries/{id}", listCityCountries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListCityCountries> listCityCountriesList = listCityCountriesRepository.findAll();
        assertThat(listCityCountriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListCityCountries.class);
        ListCityCountries listCityCountries1 = new ListCityCountries();
        listCityCountries1.setId(1L);
        ListCityCountries listCityCountries2 = new ListCityCountries();
        listCityCountries2.setId(listCityCountries1.getId());
        assertThat(listCityCountries1).isEqualTo(listCityCountries2);
        listCityCountries2.setId(2L);
        assertThat(listCityCountries1).isNotEqualTo(listCityCountries2);
        listCityCountries1.setId(null);
        assertThat(listCityCountries1).isNotEqualTo(listCityCountries2);
    }
}
