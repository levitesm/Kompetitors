package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListIndustries;
import fr.ippon.kompetitors.repository.ListIndustriesRepository;
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
 * Integration tests for the {@link ListIndustriesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListIndustriesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListIndustriesRepository listIndustriesRepository;

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

    private MockMvc restListIndustriesMockMvc;

    private ListIndustries listIndustries;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListIndustriesResource listIndustriesResource = new ListIndustriesResource(listIndustriesRepository);
        this.restListIndustriesMockMvc = MockMvcBuilders.standaloneSetup(listIndustriesResource)
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
    public static ListIndustries createEntity(EntityManager em) {
        ListIndustries listIndustries = new ListIndustries()
            .value(DEFAULT_VALUE);
        return listIndustries;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListIndustries createUpdatedEntity(EntityManager em) {
        ListIndustries listIndustries = new ListIndustries()
            .value(UPDATED_VALUE);
        return listIndustries;
    }

    @BeforeEach
    public void initTest() {
        listIndustries = createEntity(em);
    }

    @Test
    @Transactional
    public void createListIndustries() throws Exception {
        int databaseSizeBeforeCreate = listIndustriesRepository.findAll().size();

        // Create the ListIndustries
        restListIndustriesMockMvc.perform(post("/api/list-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listIndustries)))
            .andExpect(status().isCreated());

        // Validate the ListIndustries in the database
        List<ListIndustries> listIndustriesList = listIndustriesRepository.findAll();
        assertThat(listIndustriesList).hasSize(databaseSizeBeforeCreate + 1);
        ListIndustries testListIndustries = listIndustriesList.get(listIndustriesList.size() - 1);
        assertThat(testListIndustries.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListIndustriesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listIndustriesRepository.findAll().size();

        // Create the ListIndustries with an existing ID
        listIndustries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListIndustriesMockMvc.perform(post("/api/list-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listIndustries)))
            .andExpect(status().isBadRequest());

        // Validate the ListIndustries in the database
        List<ListIndustries> listIndustriesList = listIndustriesRepository.findAll();
        assertThat(listIndustriesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listIndustriesRepository.findAll().size();
        // set the field null
        listIndustries.setValue(null);

        // Create the ListIndustries, which fails.

        restListIndustriesMockMvc.perform(post("/api/list-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listIndustries)))
            .andExpect(status().isBadRequest());

        List<ListIndustries> listIndustriesList = listIndustriesRepository.findAll();
        assertThat(listIndustriesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListIndustries() throws Exception {
        // Initialize the database
        listIndustriesRepository.saveAndFlush(listIndustries);

        // Get all the listIndustriesList
        restListIndustriesMockMvc.perform(get("/api/list-industries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listIndustries.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListIndustries() throws Exception {
        // Initialize the database
        listIndustriesRepository.saveAndFlush(listIndustries);

        // Get the listIndustries
        restListIndustriesMockMvc.perform(get("/api/list-industries/{id}", listIndustries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listIndustries.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListIndustries() throws Exception {
        // Get the listIndustries
        restListIndustriesMockMvc.perform(get("/api/list-industries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListIndustries() throws Exception {
        // Initialize the database
        listIndustriesRepository.saveAndFlush(listIndustries);

        int databaseSizeBeforeUpdate = listIndustriesRepository.findAll().size();

        // Update the listIndustries
        ListIndustries updatedListIndustries = listIndustriesRepository.findById(listIndustries.getId()).get();
        // Disconnect from session so that the updates on updatedListIndustries are not directly saved in db
        em.detach(updatedListIndustries);
        updatedListIndustries
            .value(UPDATED_VALUE);

        restListIndustriesMockMvc.perform(put("/api/list-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedListIndustries)))
            .andExpect(status().isOk());

        // Validate the ListIndustries in the database
        List<ListIndustries> listIndustriesList = listIndustriesRepository.findAll();
        assertThat(listIndustriesList).hasSize(databaseSizeBeforeUpdate);
        ListIndustries testListIndustries = listIndustriesList.get(listIndustriesList.size() - 1);
        assertThat(testListIndustries.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListIndustries() throws Exception {
        int databaseSizeBeforeUpdate = listIndustriesRepository.findAll().size();

        // Create the ListIndustries

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListIndustriesMockMvc.perform(put("/api/list-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listIndustries)))
            .andExpect(status().isBadRequest());

        // Validate the ListIndustries in the database
        List<ListIndustries> listIndustriesList = listIndustriesRepository.findAll();
        assertThat(listIndustriesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListIndustries() throws Exception {
        // Initialize the database
        listIndustriesRepository.saveAndFlush(listIndustries);

        int databaseSizeBeforeDelete = listIndustriesRepository.findAll().size();

        // Delete the listIndustries
        restListIndustriesMockMvc.perform(delete("/api/list-industries/{id}", listIndustries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListIndustries> listIndustriesList = listIndustriesRepository.findAll();
        assertThat(listIndustriesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListIndustries.class);
        ListIndustries listIndustries1 = new ListIndustries();
        listIndustries1.setId(1L);
        ListIndustries listIndustries2 = new ListIndustries();
        listIndustries2.setId(listIndustries1.getId());
        assertThat(listIndustries1).isEqualTo(listIndustries2);
        listIndustries2.setId(2L);
        assertThat(listIndustries1).isNotEqualTo(listIndustries2);
        listIndustries1.setId(null);
        assertThat(listIndustries1).isNotEqualTo(listIndustries2);
    }
}
