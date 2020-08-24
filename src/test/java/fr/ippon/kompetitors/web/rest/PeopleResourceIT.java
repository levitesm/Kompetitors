package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.People;
import fr.ippon.kompetitors.repository.PeopleRepository;
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
 * Integration tests for the {@link PeopleResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class PeopleResourceIT {

    private static final String DEFAULT_F_NAME = "AAAAAAAAAA";
    private static final String UPDATED_F_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_L_NAME = "AAAAAAAAAA";
    private static final String UPDATED_L_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_LINKED_PAGE = "AAAAAAAAAA";
    private static final String UPDATED_LINKED_PAGE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_KEY = false;
    private static final Boolean UPDATED_IS_KEY = true;

    @Autowired
    private PeopleRepository peopleRepository;

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

    private MockMvc restPeopleMockMvc;

    private People people;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PeopleResource peopleResource = new PeopleResource(peopleRepository);
        this.restPeopleMockMvc = MockMvcBuilders.standaloneSetup(peopleResource)
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
    public static People createEntity(EntityManager em) {
        People people = new People()
            .fName(DEFAULT_F_NAME)
            .lName(DEFAULT_L_NAME)
            .title(DEFAULT_TITLE)
            .linkedPage(DEFAULT_LINKED_PAGE)
            .isKey(DEFAULT_IS_KEY);
        return people;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static People createUpdatedEntity(EntityManager em) {
        People people = new People()
            .fName(UPDATED_F_NAME)
            .lName(UPDATED_L_NAME)
            .title(UPDATED_TITLE)
            .linkedPage(UPDATED_LINKED_PAGE)
            .isKey(UPDATED_IS_KEY);
        return people;
    }

    @BeforeEach
    public void initTest() {
        people = createEntity(em);
    }

    @Test
    @Transactional
    public void createPeople() throws Exception {
        int databaseSizeBeforeCreate = peopleRepository.findAll().size();

        // Create the People
        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isCreated());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeCreate + 1);
        People testPeople = peopleList.get(peopleList.size() - 1);
        assertThat(testPeople.getfName()).isEqualTo(DEFAULT_F_NAME);
        assertThat(testPeople.getlName()).isEqualTo(DEFAULT_L_NAME);
        assertThat(testPeople.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testPeople.getLinkedPage()).isEqualTo(DEFAULT_LINKED_PAGE);
        assertThat(testPeople.isIsKey()).isEqualTo(DEFAULT_IS_KEY);
    }

    @Test
    @Transactional
    public void createPeopleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = peopleRepository.findAll().size();

        // Create the People with an existing ID
        people.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkfNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setfName(null);

        // Create the People, which fails.

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checklNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setlName(null);

        // Create the People, which fails.

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = peopleRepository.findAll().size();
        // set the field null
        people.setTitle(null);

        // Create the People, which fails.

        restPeopleMockMvc.perform(post("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isBadRequest());

        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get all the peopleList
        restPeopleMockMvc.perform(get("/api/people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(people.getId().intValue())))
            .andExpect(jsonPath("$.[*].fName").value(hasItem(DEFAULT_F_NAME)))
            .andExpect(jsonPath("$.[*].lName").value(hasItem(DEFAULT_L_NAME)))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE)))
            .andExpect(jsonPath("$.[*].linkedPage").value(hasItem(DEFAULT_LINKED_PAGE)))
            .andExpect(jsonPath("$.[*].isKey").value(hasItem(DEFAULT_IS_KEY.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getPeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        // Get the people
        restPeopleMockMvc.perform(get("/api/people/{id}", people.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(people.getId().intValue()))
            .andExpect(jsonPath("$.fName").value(DEFAULT_F_NAME))
            .andExpect(jsonPath("$.lName").value(DEFAULT_L_NAME))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE))
            .andExpect(jsonPath("$.linkedPage").value(DEFAULT_LINKED_PAGE))
            .andExpect(jsonPath("$.isKey").value(DEFAULT_IS_KEY.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPeople() throws Exception {
        // Get the people
        restPeopleMockMvc.perform(get("/api/people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        int databaseSizeBeforeUpdate = peopleRepository.findAll().size();

        // Update the people
        People updatedPeople = peopleRepository.findById(people.getId()).get();
        // Disconnect from session so that the updates on updatedPeople are not directly saved in db
        em.detach(updatedPeople);
        updatedPeople
            .fName(UPDATED_F_NAME)
            .lName(UPDATED_L_NAME)
            .title(UPDATED_TITLE)
            .linkedPage(UPDATED_LINKED_PAGE)
            .isKey(UPDATED_IS_KEY);

        restPeopleMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPeople)))
            .andExpect(status().isOk());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeUpdate);
        People testPeople = peopleList.get(peopleList.size() - 1);
        assertThat(testPeople.getfName()).isEqualTo(UPDATED_F_NAME);
        assertThat(testPeople.getlName()).isEqualTo(UPDATED_L_NAME);
        assertThat(testPeople.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testPeople.getLinkedPage()).isEqualTo(UPDATED_LINKED_PAGE);
        assertThat(testPeople.isIsKey()).isEqualTo(UPDATED_IS_KEY);
    }

    @Test
    @Transactional
    public void updateNonExistingPeople() throws Exception {
        int databaseSizeBeforeUpdate = peopleRepository.findAll().size();

        // Create the People

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPeopleMockMvc.perform(put("/api/people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(people)))
            .andExpect(status().isBadRequest());

        // Validate the People in the database
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePeople() throws Exception {
        // Initialize the database
        peopleRepository.saveAndFlush(people);

        int databaseSizeBeforeDelete = peopleRepository.findAll().size();

        // Delete the people
        restPeopleMockMvc.perform(delete("/api/people/{id}", people.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<People> peopleList = peopleRepository.findAll();
        assertThat(peopleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(People.class);
        People people1 = new People();
        people1.setId(1L);
        People people2 = new People();
        people2.setId(people1.getId());
        assertThat(people1).isEqualTo(people2);
        people2.setId(2L);
        assertThat(people1).isNotEqualTo(people2);
        people1.setId(null);
        assertThat(people1).isNotEqualTo(people2);
    }
}
