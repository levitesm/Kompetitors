package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.EmployeeSalaries;
import fr.ippon.kompetitors.repository.EmployeeSalariesRepository;
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
 * Integration tests for the {@link EmployeeSalariesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class EmployeeSalariesResourceIT {

    private static final String DEFAULT_SENIORITY = "AAAAAAAAAA";
    private static final String UPDATED_SENIORITY = "BBBBBBBBBB";

    private static final Double DEFAULT_SALARY = 1D;
    private static final Double UPDATED_SALARY = 2D;

    private static final LocalDate DEFAULT_UPDATE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_UPDATE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_UPDATED_BY = "AAAAAAAAAA";
    private static final String UPDATED_UPDATED_BY = "BBBBBBBBBB";

    @Autowired
    private EmployeeSalariesRepository employeeSalariesRepository;

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

    private MockMvc restEmployeeSalariesMockMvc;

    private EmployeeSalaries employeeSalaries;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeSalariesResource employeeSalariesResource = new EmployeeSalariesResource(employeeSalariesRepository);
        this.restEmployeeSalariesMockMvc = MockMvcBuilders.standaloneSetup(employeeSalariesResource)
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
    public static EmployeeSalaries createEntity(EntityManager em) {
        EmployeeSalaries employeeSalaries = new EmployeeSalaries()
            .seniority(DEFAULT_SENIORITY)
            .salary(DEFAULT_SALARY)
            .updateDate(DEFAULT_UPDATE_DATE)
            .updatedBy(DEFAULT_UPDATED_BY);
        return employeeSalaries;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeSalaries createUpdatedEntity(EntityManager em) {
        EmployeeSalaries employeeSalaries = new EmployeeSalaries()
            .seniority(UPDATED_SENIORITY)
            .salary(UPDATED_SALARY)
            .updateDate(UPDATED_UPDATE_DATE)
            .updatedBy(UPDATED_UPDATED_BY);
        return employeeSalaries;
    }

    @BeforeEach
    public void initTest() {
        employeeSalaries = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeSalaries() throws Exception {
        int databaseSizeBeforeCreate = employeeSalariesRepository.findAll().size();

        // Create the EmployeeSalaries
        restEmployeeSalariesMockMvc.perform(post("/api/employee-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalaries)))
            .andExpect(status().isCreated());

        // Validate the EmployeeSalaries in the database
        List<EmployeeSalaries> employeeSalariesList = employeeSalariesRepository.findAll();
        assertThat(employeeSalariesList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeSalaries testEmployeeSalaries = employeeSalariesList.get(employeeSalariesList.size() - 1);
        assertThat(testEmployeeSalaries.getSeniority()).isEqualTo(DEFAULT_SENIORITY);
        assertThat(testEmployeeSalaries.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testEmployeeSalaries.getUpdateDate()).isEqualTo(DEFAULT_UPDATE_DATE);
        assertThat(testEmployeeSalaries.getUpdatedBy()).isEqualTo(DEFAULT_UPDATED_BY);
    }

    @Test
    @Transactional
    public void createEmployeeSalariesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeSalariesRepository.findAll().size();

        // Create the EmployeeSalaries with an existing ID
        employeeSalaries.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeSalariesMockMvc.perform(post("/api/employee-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalaries)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSalaries in the database
        List<EmployeeSalaries> employeeSalariesList = employeeSalariesRepository.findAll();
        assertThat(employeeSalariesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeeSalaries() throws Exception {
        // Initialize the database
        employeeSalariesRepository.saveAndFlush(employeeSalaries);

        // Get all the employeeSalariesList
        restEmployeeSalariesMockMvc.perform(get("/api/employee-salaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeSalaries.getId().intValue())))
            .andExpect(jsonPath("$.[*].seniority").value(hasItem(DEFAULT_SENIORITY)))
            .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
            .andExpect(jsonPath("$.[*].updateDate").value(hasItem(DEFAULT_UPDATE_DATE.toString())))
            .andExpect(jsonPath("$.[*].updatedBy").value(hasItem(DEFAULT_UPDATED_BY)));
    }
    
    @Test
    @Transactional
    public void getEmployeeSalaries() throws Exception {
        // Initialize the database
        employeeSalariesRepository.saveAndFlush(employeeSalaries);

        // Get the employeeSalaries
        restEmployeeSalariesMockMvc.perform(get("/api/employee-salaries/{id}", employeeSalaries.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeSalaries.getId().intValue()))
            .andExpect(jsonPath("$.seniority").value(DEFAULT_SENIORITY))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()))
            .andExpect(jsonPath("$.updateDate").value(DEFAULT_UPDATE_DATE.toString()))
            .andExpect(jsonPath("$.updatedBy").value(DEFAULT_UPDATED_BY));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeSalaries() throws Exception {
        // Get the employeeSalaries
        restEmployeeSalariesMockMvc.perform(get("/api/employee-salaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeSalaries() throws Exception {
        // Initialize the database
        employeeSalariesRepository.saveAndFlush(employeeSalaries);

        int databaseSizeBeforeUpdate = employeeSalariesRepository.findAll().size();

        // Update the employeeSalaries
        EmployeeSalaries updatedEmployeeSalaries = employeeSalariesRepository.findById(employeeSalaries.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeSalaries are not directly saved in db
        em.detach(updatedEmployeeSalaries);
        updatedEmployeeSalaries
            .seniority(UPDATED_SENIORITY)
            .salary(UPDATED_SALARY)
            .updateDate(UPDATED_UPDATE_DATE)
            .updatedBy(UPDATED_UPDATED_BY);

        restEmployeeSalariesMockMvc.perform(put("/api/employee-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEmployeeSalaries)))
            .andExpect(status().isOk());

        // Validate the EmployeeSalaries in the database
        List<EmployeeSalaries> employeeSalariesList = employeeSalariesRepository.findAll();
        assertThat(employeeSalariesList).hasSize(databaseSizeBeforeUpdate);
        EmployeeSalaries testEmployeeSalaries = employeeSalariesList.get(employeeSalariesList.size() - 1);
        assertThat(testEmployeeSalaries.getSeniority()).isEqualTo(UPDATED_SENIORITY);
        assertThat(testEmployeeSalaries.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testEmployeeSalaries.getUpdateDate()).isEqualTo(UPDATED_UPDATE_DATE);
        assertThat(testEmployeeSalaries.getUpdatedBy()).isEqualTo(UPDATED_UPDATED_BY);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeSalaries() throws Exception {
        int databaseSizeBeforeUpdate = employeeSalariesRepository.findAll().size();

        // Create the EmployeeSalaries

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeSalariesMockMvc.perform(put("/api/employee-salaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeSalaries)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeSalaries in the database
        List<EmployeeSalaries> employeeSalariesList = employeeSalariesRepository.findAll();
        assertThat(employeeSalariesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeSalaries() throws Exception {
        // Initialize the database
        employeeSalariesRepository.saveAndFlush(employeeSalaries);

        int databaseSizeBeforeDelete = employeeSalariesRepository.findAll().size();

        // Delete the employeeSalaries
        restEmployeeSalariesMockMvc.perform(delete("/api/employee-salaries/{id}", employeeSalaries.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeSalaries> employeeSalariesList = employeeSalariesRepository.findAll();
        assertThat(employeeSalariesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeSalaries.class);
        EmployeeSalaries employeeSalaries1 = new EmployeeSalaries();
        employeeSalaries1.setId(1L);
        EmployeeSalaries employeeSalaries2 = new EmployeeSalaries();
        employeeSalaries2.setId(employeeSalaries1.getId());
        assertThat(employeeSalaries1).isEqualTo(employeeSalaries2);
        employeeSalaries2.setId(2L);
        assertThat(employeeSalaries1).isNotEqualTo(employeeSalaries2);
        employeeSalaries1.setId(null);
        assertThat(employeeSalaries1).isNotEqualTo(employeeSalaries2);
    }
}
