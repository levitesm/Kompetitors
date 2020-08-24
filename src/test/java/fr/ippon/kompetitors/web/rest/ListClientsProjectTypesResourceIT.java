package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListClientsProjectTypes;
import fr.ippon.kompetitors.repository.ListClientsProjectTypesRepository;
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
 * Integration tests for the {@link ListClientsProjectTypesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListClientsProjectTypesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListClientsProjectTypesRepository listClientsProjectTypesRepository;

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

    private MockMvc restListClientsProjectTypesMockMvc;

    private ListClientsProjectTypes listClientsProjectTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListClientsProjectTypesResource listClientsProjectTypesResource = new ListClientsProjectTypesResource(listClientsProjectTypesRepository);
        this.restListClientsProjectTypesMockMvc = MockMvcBuilders.standaloneSetup(listClientsProjectTypesResource)
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
    public static ListClientsProjectTypes createEntity(EntityManager em) {
        ListClientsProjectTypes listClientsProjectTypes = new ListClientsProjectTypes()
            .value(DEFAULT_VALUE);
        return listClientsProjectTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListClientsProjectTypes createUpdatedEntity(EntityManager em) {
        ListClientsProjectTypes listClientsProjectTypes = new ListClientsProjectTypes()
            .value(UPDATED_VALUE);
        return listClientsProjectTypes;
    }

    @BeforeEach
    public void initTest() {
        listClientsProjectTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createListClientsProjectTypes() throws Exception {
        int databaseSizeBeforeCreate = listClientsProjectTypesRepository.findAll().size();

        // Create the ListClientsProjectTypes
        restListClientsProjectTypesMockMvc.perform(post("/api/list-clients-project-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listClientsProjectTypes)))
            .andExpect(status().isCreated());

        // Validate the ListClientsProjectTypes in the database
        List<ListClientsProjectTypes> listClientsProjectTypesList = listClientsProjectTypesRepository.findAll();
        assertThat(listClientsProjectTypesList).hasSize(databaseSizeBeforeCreate + 1);
        ListClientsProjectTypes testListClientsProjectTypes = listClientsProjectTypesList.get(listClientsProjectTypesList.size() - 1);
        assertThat(testListClientsProjectTypes.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListClientsProjectTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listClientsProjectTypesRepository.findAll().size();

        // Create the ListClientsProjectTypes with an existing ID
        listClientsProjectTypes.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListClientsProjectTypesMockMvc.perform(post("/api/list-clients-project-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listClientsProjectTypes)))
            .andExpect(status().isBadRequest());

        // Validate the ListClientsProjectTypes in the database
        List<ListClientsProjectTypes> listClientsProjectTypesList = listClientsProjectTypesRepository.findAll();
        assertThat(listClientsProjectTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listClientsProjectTypesRepository.findAll().size();
        // set the field null
        listClientsProjectTypes.setValue(null);

        // Create the ListClientsProjectTypes, which fails.

        restListClientsProjectTypesMockMvc.perform(post("/api/list-clients-project-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listClientsProjectTypes)))
            .andExpect(status().isBadRequest());

        List<ListClientsProjectTypes> listClientsProjectTypesList = listClientsProjectTypesRepository.findAll();
        assertThat(listClientsProjectTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListClientsProjectTypes() throws Exception {
        // Initialize the database
        listClientsProjectTypesRepository.saveAndFlush(listClientsProjectTypes);

        // Get all the listClientsProjectTypesList
        restListClientsProjectTypesMockMvc.perform(get("/api/list-clients-project-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listClientsProjectTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListClientsProjectTypes() throws Exception {
        // Initialize the database
        listClientsProjectTypesRepository.saveAndFlush(listClientsProjectTypes);

        // Get the listClientsProjectTypes
        restListClientsProjectTypesMockMvc.perform(get("/api/list-clients-project-types/{id}", listClientsProjectTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listClientsProjectTypes.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListClientsProjectTypes() throws Exception {
        // Get the listClientsProjectTypes
        restListClientsProjectTypesMockMvc.perform(get("/api/list-clients-project-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListClientsProjectTypes() throws Exception {
        // Initialize the database
        listClientsProjectTypesRepository.saveAndFlush(listClientsProjectTypes);

        int databaseSizeBeforeUpdate = listClientsProjectTypesRepository.findAll().size();

        // Update the listClientsProjectTypes
        ListClientsProjectTypes updatedListClientsProjectTypes = listClientsProjectTypesRepository.findById(listClientsProjectTypes.getId()).get();
        // Disconnect from session so that the updates on updatedListClientsProjectTypes are not directly saved in db
        em.detach(updatedListClientsProjectTypes);
        updatedListClientsProjectTypes
            .value(UPDATED_VALUE);

        restListClientsProjectTypesMockMvc.perform(put("/api/list-clients-project-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedListClientsProjectTypes)))
            .andExpect(status().isOk());

        // Validate the ListClientsProjectTypes in the database
        List<ListClientsProjectTypes> listClientsProjectTypesList = listClientsProjectTypesRepository.findAll();
        assertThat(listClientsProjectTypesList).hasSize(databaseSizeBeforeUpdate);
        ListClientsProjectTypes testListClientsProjectTypes = listClientsProjectTypesList.get(listClientsProjectTypesList.size() - 1);
        assertThat(testListClientsProjectTypes.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListClientsProjectTypes() throws Exception {
        int databaseSizeBeforeUpdate = listClientsProjectTypesRepository.findAll().size();

        // Create the ListClientsProjectTypes

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListClientsProjectTypesMockMvc.perform(put("/api/list-clients-project-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listClientsProjectTypes)))
            .andExpect(status().isBadRequest());

        // Validate the ListClientsProjectTypes in the database
        List<ListClientsProjectTypes> listClientsProjectTypesList = listClientsProjectTypesRepository.findAll();
        assertThat(listClientsProjectTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListClientsProjectTypes() throws Exception {
        // Initialize the database
        listClientsProjectTypesRepository.saveAndFlush(listClientsProjectTypes);

        int databaseSizeBeforeDelete = listClientsProjectTypesRepository.findAll().size();

        // Delete the listClientsProjectTypes
        restListClientsProjectTypesMockMvc.perform(delete("/api/list-clients-project-types/{id}", listClientsProjectTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListClientsProjectTypes> listClientsProjectTypesList = listClientsProjectTypesRepository.findAll();
        assertThat(listClientsProjectTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListClientsProjectTypes.class);
        ListClientsProjectTypes listClientsProjectTypes1 = new ListClientsProjectTypes();
        listClientsProjectTypes1.setId(1L);
        ListClientsProjectTypes listClientsProjectTypes2 = new ListClientsProjectTypes();
        listClientsProjectTypes2.setId(listClientsProjectTypes1.getId());
        assertThat(listClientsProjectTypes1).isEqualTo(listClientsProjectTypes2);
        listClientsProjectTypes2.setId(2L);
        assertThat(listClientsProjectTypes1).isNotEqualTo(listClientsProjectTypes2);
        listClientsProjectTypes1.setId(null);
        assertThat(listClientsProjectTypes1).isNotEqualTo(listClientsProjectTypes2);
    }
}
