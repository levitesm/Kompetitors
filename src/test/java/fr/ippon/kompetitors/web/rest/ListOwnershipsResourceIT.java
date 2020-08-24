package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListOwnerships;
import fr.ippon.kompetitors.repository.ListOwnershipsRepository;
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
 * Integration tests for the {@link ListOwnershipsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListOwnershipsResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListOwnershipsRepository listOwnershipsRepository;

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

    private MockMvc restListOwnershipsMockMvc;

    private ListOwnerships listOwnerships;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListOwnershipsResource listOwnershipsResource = new ListOwnershipsResource(listOwnershipsRepository);
        this.restListOwnershipsMockMvc = MockMvcBuilders.standaloneSetup(listOwnershipsResource)
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
    public static ListOwnerships createEntity(EntityManager em) {
        ListOwnerships listOwnerships = new ListOwnerships()
            .value(DEFAULT_VALUE);
        return listOwnerships;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListOwnerships createUpdatedEntity(EntityManager em) {
        ListOwnerships listOwnerships = new ListOwnerships()
            .value(UPDATED_VALUE);
        return listOwnerships;
    }

    @BeforeEach
    public void initTest() {
        listOwnerships = createEntity(em);
    }

    @Test
    @Transactional
    public void createListOwnerships() throws Exception {
        int databaseSizeBeforeCreate = listOwnershipsRepository.findAll().size();

        // Create the ListOwnerships
        restListOwnershipsMockMvc.perform(post("/api/list-ownerships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listOwnerships)))
            .andExpect(status().isCreated());

        // Validate the ListOwnerships in the database
        List<ListOwnerships> listOwnershipsList = listOwnershipsRepository.findAll();
        assertThat(listOwnershipsList).hasSize(databaseSizeBeforeCreate + 1);
        ListOwnerships testListOwnerships = listOwnershipsList.get(listOwnershipsList.size() - 1);
        assertThat(testListOwnerships.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListOwnershipsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listOwnershipsRepository.findAll().size();

        // Create the ListOwnerships with an existing ID
        listOwnerships.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListOwnershipsMockMvc.perform(post("/api/list-ownerships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listOwnerships)))
            .andExpect(status().isBadRequest());

        // Validate the ListOwnerships in the database
        List<ListOwnerships> listOwnershipsList = listOwnershipsRepository.findAll();
        assertThat(listOwnershipsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listOwnershipsRepository.findAll().size();
        // set the field null
        listOwnerships.setValue(null);

        // Create the ListOwnerships, which fails.

        restListOwnershipsMockMvc.perform(post("/api/list-ownerships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listOwnerships)))
            .andExpect(status().isBadRequest());

        List<ListOwnerships> listOwnershipsList = listOwnershipsRepository.findAll();
        assertThat(listOwnershipsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListOwnerships() throws Exception {
        // Initialize the database
        listOwnershipsRepository.saveAndFlush(listOwnerships);

        // Get all the listOwnershipsList
        restListOwnershipsMockMvc.perform(get("/api/list-ownerships?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listOwnerships.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListOwnerships() throws Exception {
        // Initialize the database
        listOwnershipsRepository.saveAndFlush(listOwnerships);

        // Get the listOwnerships
        restListOwnershipsMockMvc.perform(get("/api/list-ownerships/{id}", listOwnerships.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listOwnerships.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListOwnerships() throws Exception {
        // Get the listOwnerships
        restListOwnershipsMockMvc.perform(get("/api/list-ownerships/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListOwnerships() throws Exception {
        // Initialize the database
        listOwnershipsRepository.saveAndFlush(listOwnerships);

        int databaseSizeBeforeUpdate = listOwnershipsRepository.findAll().size();

        // Update the listOwnerships
        ListOwnerships updatedListOwnerships = listOwnershipsRepository.findById(listOwnerships.getId()).get();
        // Disconnect from session so that the updates on updatedListOwnerships are not directly saved in db
        em.detach(updatedListOwnerships);
        updatedListOwnerships
            .value(UPDATED_VALUE);

        restListOwnershipsMockMvc.perform(put("/api/list-ownerships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedListOwnerships)))
            .andExpect(status().isOk());

        // Validate the ListOwnerships in the database
        List<ListOwnerships> listOwnershipsList = listOwnershipsRepository.findAll();
        assertThat(listOwnershipsList).hasSize(databaseSizeBeforeUpdate);
        ListOwnerships testListOwnerships = listOwnershipsList.get(listOwnershipsList.size() - 1);
        assertThat(testListOwnerships.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListOwnerships() throws Exception {
        int databaseSizeBeforeUpdate = listOwnershipsRepository.findAll().size();

        // Create the ListOwnerships

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListOwnershipsMockMvc.perform(put("/api/list-ownerships")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listOwnerships)))
            .andExpect(status().isBadRequest());

        // Validate the ListOwnerships in the database
        List<ListOwnerships> listOwnershipsList = listOwnershipsRepository.findAll();
        assertThat(listOwnershipsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListOwnerships() throws Exception {
        // Initialize the database
        listOwnershipsRepository.saveAndFlush(listOwnerships);

        int databaseSizeBeforeDelete = listOwnershipsRepository.findAll().size();

        // Delete the listOwnerships
        restListOwnershipsMockMvc.perform(delete("/api/list-ownerships/{id}", listOwnerships.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListOwnerships> listOwnershipsList = listOwnershipsRepository.findAll();
        assertThat(listOwnershipsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListOwnerships.class);
        ListOwnerships listOwnerships1 = new ListOwnerships();
        listOwnerships1.setId(1L);
        ListOwnerships listOwnerships2 = new ListOwnerships();
        listOwnerships2.setId(listOwnerships1.getId());
        assertThat(listOwnerships1).isEqualTo(listOwnerships2);
        listOwnerships2.setId(2L);
        assertThat(listOwnerships1).isNotEqualTo(listOwnerships2);
        listOwnerships1.setId(null);
        assertThat(listOwnerships1).isNotEqualTo(listOwnerships2);
    }
}
