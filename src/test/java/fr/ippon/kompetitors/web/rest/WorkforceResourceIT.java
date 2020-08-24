package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.Workforce;
import fr.ippon.kompetitors.domain.Competitors;
import fr.ippon.kompetitors.repository.WorkforceRepository;
import fr.ippon.kompetitors.service.WorkforceService;
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
 * Integration tests for the {@link WorkforceResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class WorkforceResourceIT {

    private static final Integer DEFAULT_EMPLOYEE_NUMBER = 1;
    private static final Integer UPDATED_EMPLOYEE_NUMBER = 2;

    private static final LocalDate DEFAULT_YEAR = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_YEAR = LocalDate.now(ZoneId.systemDefault());

    @Autowired
    private WorkforceRepository workforceRepository;

    @Autowired
    private WorkforceService workforceService;

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

    private MockMvc restWorkforceMockMvc;

    private Workforce workforce;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final WorkforceResource workforceResource = new WorkforceResource(workforceService);
        this.restWorkforceMockMvc = MockMvcBuilders.standaloneSetup(workforceResource)
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
    public static Workforce createEntity(EntityManager em) {
        Workforce workforce = new Workforce()
            .employeeNumber(DEFAULT_EMPLOYEE_NUMBER)
            .year(DEFAULT_YEAR);
        // Add required entity
        Competitors competitors;
        if (TestUtil.findAll(em, Competitors.class).isEmpty()) {
            competitors = CompetitorsResourceIT.createEntity(em);
            em.persist(competitors);
            em.flush();
        } else {
            competitors = TestUtil.findAll(em, Competitors.class).get(0);
        }
        workforce.setCompetitor(competitors);
        return workforce;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workforce createUpdatedEntity(EntityManager em) {
        Workforce workforce = new Workforce()
            .employeeNumber(UPDATED_EMPLOYEE_NUMBER)
            .year(UPDATED_YEAR);
        // Add required entity
        Competitors competitors;
        if (TestUtil.findAll(em, Competitors.class).isEmpty()) {
            competitors = CompetitorsResourceIT.createUpdatedEntity(em);
            em.persist(competitors);
            em.flush();
        } else {
            competitors = TestUtil.findAll(em, Competitors.class).get(0);
        }
        workforce.setCompetitor(competitors);
        return workforce;
    }

    @BeforeEach
    public void initTest() {
        workforce = createEntity(em);
    }

    @Test
    @Transactional
    public void createWorkforce() throws Exception {
        int databaseSizeBeforeCreate = workforceRepository.findAll().size();

        // Create the Workforce
        restWorkforceMockMvc.perform(post("/api/workforces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workforce)))
            .andExpect(status().isCreated());

        // Validate the Workforce in the database
        List<Workforce> workforceList = workforceRepository.findAll();
        assertThat(workforceList).hasSize(databaseSizeBeforeCreate + 1);
        Workforce testWorkforce = workforceList.get(workforceList.size() - 1);
        assertThat(testWorkforce.getEmployeeNumber()).isEqualTo(DEFAULT_EMPLOYEE_NUMBER);
        assertThat(testWorkforce.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    public void createWorkforceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = workforceRepository.findAll().size();

        // Create the Workforce with an existing ID
        workforce.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkforceMockMvc.perform(post("/api/workforces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workforce)))
            .andExpect(status().isBadRequest());

        // Validate the Workforce in the database
        List<Workforce> workforceList = workforceRepository.findAll();
        assertThat(workforceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllWorkforces() throws Exception {
        // Initialize the database
        workforceRepository.saveAndFlush(workforce);

        // Get all the workforceList
        restWorkforceMockMvc.perform(get("/api/workforces?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workforce.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeNumber").value(hasItem(DEFAULT_EMPLOYEE_NUMBER)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())));
    }

    @Test
    @Transactional
    public void getWorkforcesByCompetitorId() throws Exception {
        // Initialize the database
        workforceRepository.saveAndFlush(workforce);

        // Get all the workforceList
        restWorkforceMockMvc.perform(get("/api/workforces/competitor/{competitorId}", this.workforce.getCompetitor().getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workforce.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeNumber").value(hasItem(DEFAULT_EMPLOYEE_NUMBER)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR.toString())));
    }

    @Test
    @Transactional
    public void getWorkforce() throws Exception {
        // Initialize the database
        workforceRepository.saveAndFlush(workforce);

        // Get the workforce
        restWorkforceMockMvc.perform(get("/api/workforces/{id}", workforce.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(workforce.getId().intValue()))
            .andExpect(jsonPath("$.employeeNumber").value(DEFAULT_EMPLOYEE_NUMBER))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingWorkforce() throws Exception {
        // Get the workforce
        restWorkforceMockMvc.perform(get("/api/workforces/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateWorkforce() throws Exception {
        // Initialize the database
        workforceService.save(workforce);

        int databaseSizeBeforeUpdate = workforceRepository.findAll().size();

        // Update the workforce
        Workforce updatedWorkforce = workforceRepository.findById(workforce.getId()).get();
        // Disconnect from session so that the updates on updatedWorkforce are not directly saved in db
        em.detach(updatedWorkforce);
        updatedWorkforce
            .employeeNumber(UPDATED_EMPLOYEE_NUMBER)
            .year(UPDATED_YEAR);

        restWorkforceMockMvc.perform(put("/api/workforces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedWorkforce)))
            .andExpect(status().isOk());

        // Validate the Workforce in the database
        List<Workforce> workforceList = workforceRepository.findAll();
        assertThat(workforceList).hasSize(databaseSizeBeforeUpdate);
        Workforce testWorkforce = workforceList.get(workforceList.size() - 1);
        assertThat(testWorkforce.getEmployeeNumber()).isEqualTo(UPDATED_EMPLOYEE_NUMBER);
        assertThat(testWorkforce.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingWorkforce() throws Exception {
        int databaseSizeBeforeUpdate = workforceRepository.findAll().size();

        // Create the Workforce

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkforceMockMvc.perform(put("/api/workforces")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(workforce)))
            .andExpect(status().isBadRequest());

        // Validate the Workforce in the database
        List<Workforce> workforceList = workforceRepository.findAll();
        assertThat(workforceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteWorkforce() throws Exception {
        // Initialize the database
        workforceService.save(workforce);

        int databaseSizeBeforeDelete = workforceRepository.findAll().size();

        // Delete the workforce
        restWorkforceMockMvc.perform(delete("/api/workforces/{id}", workforce.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Workforce> workforceList = workforceRepository.findAll();
        assertThat(workforceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Workforce.class);
        Workforce workforce1 = new Workforce();
        workforce1.setId(1L);
        Workforce workforce2 = new Workforce();
        workforce2.setId(workforce1.getId());
        assertThat(workforce1).isEqualTo(workforce2);
        workforce2.setId(2L);
        assertThat(workforce1).isNotEqualTo(workforce2);
        workforce1.setId(null);
        assertThat(workforce1).isNotEqualTo(workforce2);
    }
}
