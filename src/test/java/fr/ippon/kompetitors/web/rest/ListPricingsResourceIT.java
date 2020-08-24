package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListPricings;
import fr.ippon.kompetitors.repository.ListPricingsRepository;
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
 * Integration tests for the {@link ListPricingsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListPricingsResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListPricingsRepository listPricingsRepository;

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

    private MockMvc restListPricingsMockMvc;

    private ListPricings listPricings;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListPricingsResource listPricingsResource = new ListPricingsResource(listPricingsRepository);
        this.restListPricingsMockMvc = MockMvcBuilders.standaloneSetup(listPricingsResource)
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
    public static ListPricings createEntity(EntityManager em) {
        ListPricings listPricings = new ListPricings()
            .value(DEFAULT_VALUE);
        return listPricings;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListPricings createUpdatedEntity(EntityManager em) {
        ListPricings listPricings = new ListPricings()
            .value(UPDATED_VALUE);
        return listPricings;
    }

    @BeforeEach
    public void initTest() {
        listPricings = createEntity(em);
    }

    @Test
    @Transactional
    public void createListPricings() throws Exception {
        int databaseSizeBeforeCreate = listPricingsRepository.findAll().size();

        // Create the ListPricings
        restListPricingsMockMvc.perform(post("/api/list-pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listPricings)))
            .andExpect(status().isCreated());

        // Validate the ListPricings in the database
        List<ListPricings> listPricingsList = listPricingsRepository.findAll();
        assertThat(listPricingsList).hasSize(databaseSizeBeforeCreate + 1);
        ListPricings testListPricings = listPricingsList.get(listPricingsList.size() - 1);
        assertThat(testListPricings.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListPricingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listPricingsRepository.findAll().size();

        // Create the ListPricings with an existing ID
        listPricings.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListPricingsMockMvc.perform(post("/api/list-pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listPricings)))
            .andExpect(status().isBadRequest());

        // Validate the ListPricings in the database
        List<ListPricings> listPricingsList = listPricingsRepository.findAll();
        assertThat(listPricingsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listPricingsRepository.findAll().size();
        // set the field null
        listPricings.setValue(null);

        // Create the ListPricings, which fails.

        restListPricingsMockMvc.perform(post("/api/list-pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listPricings)))
            .andExpect(status().isBadRequest());

        List<ListPricings> listPricingsList = listPricingsRepository.findAll();
        assertThat(listPricingsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListPricings() throws Exception {
        // Initialize the database
        listPricingsRepository.saveAndFlush(listPricings);

        // Get all the listPricingsList
        restListPricingsMockMvc.perform(get("/api/list-pricings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listPricings.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListPricings() throws Exception {
        // Initialize the database
        listPricingsRepository.saveAndFlush(listPricings);

        // Get the listPricings
        restListPricingsMockMvc.perform(get("/api/list-pricings/{id}", listPricings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listPricings.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListPricings() throws Exception {
        // Get the listPricings
        restListPricingsMockMvc.perform(get("/api/list-pricings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListPricings() throws Exception {
        // Initialize the database
        listPricingsRepository.saveAndFlush(listPricings);

        int databaseSizeBeforeUpdate = listPricingsRepository.findAll().size();

        // Update the listPricings
        ListPricings updatedListPricings = listPricingsRepository.findById(listPricings.getId()).get();
        // Disconnect from session so that the updates on updatedListPricings are not directly saved in db
        em.detach(updatedListPricings);
        updatedListPricings
            .value(UPDATED_VALUE);

        restListPricingsMockMvc.perform(put("/api/list-pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedListPricings)))
            .andExpect(status().isOk());

        // Validate the ListPricings in the database
        List<ListPricings> listPricingsList = listPricingsRepository.findAll();
        assertThat(listPricingsList).hasSize(databaseSizeBeforeUpdate);
        ListPricings testListPricings = listPricingsList.get(listPricingsList.size() - 1);
        assertThat(testListPricings.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListPricings() throws Exception {
        int databaseSizeBeforeUpdate = listPricingsRepository.findAll().size();

        // Create the ListPricings

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListPricingsMockMvc.perform(put("/api/list-pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listPricings)))
            .andExpect(status().isBadRequest());

        // Validate the ListPricings in the database
        List<ListPricings> listPricingsList = listPricingsRepository.findAll();
        assertThat(listPricingsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListPricings() throws Exception {
        // Initialize the database
        listPricingsRepository.saveAndFlush(listPricings);

        int databaseSizeBeforeDelete = listPricingsRepository.findAll().size();

        // Delete the listPricings
        restListPricingsMockMvc.perform(delete("/api/list-pricings/{id}", listPricings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListPricings> listPricingsList = listPricingsRepository.findAll();
        assertThat(listPricingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListPricings.class);
        ListPricings listPricings1 = new ListPricings();
        listPricings1.setId(1L);
        ListPricings listPricings2 = new ListPricings();
        listPricings2.setId(listPricings1.getId());
        assertThat(listPricings1).isEqualTo(listPricings2);
        listPricings2.setId(2L);
        assertThat(listPricings1).isNotEqualTo(listPricings2);
        listPricings1.setId(null);
        assertThat(listPricings1).isNotEqualTo(listPricings2);
    }
}
