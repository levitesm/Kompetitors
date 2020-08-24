package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.EmployeesTypology;
import fr.ippon.kompetitors.repository.EmployeesTypologyRepository;
import fr.ippon.kompetitors.service.EmployeesTypologyService;
import fr.ippon.kompetitors.service.dto.EmployeesTypologyDTO;
import fr.ippon.kompetitors.service.mapper.EmployeesTypologyMapper;
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
 * Integration tests for the {@link EmployeesTypologyResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class EmployeesTypologyResourceIT {

    private static final Integer DEFAULT_VALUE = 1;
    private static final Integer UPDATED_VALUE = 2;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    @Autowired
    private EmployeesTypologyRepository employeesTypologyRepository;

    @Autowired
    private EmployeesTypologyMapper employeesTypologyMapper;

    @Autowired
    private EmployeesTypologyService employeesTypologyService;

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

    private MockMvc restEmployeesTypologyMockMvc;

    private EmployeesTypology employeesTypology;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeesTypologyResource employeesTypologyResource = new EmployeesTypologyResource(employeesTypologyService);
        this.restEmployeesTypologyMockMvc = MockMvcBuilders.standaloneSetup(employeesTypologyResource)
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
    public static EmployeesTypology createEntity(EntityManager em) {
        EmployeesTypology employeesTypology = new EmployeesTypology()
            .value(DEFAULT_VALUE)
            .year(DEFAULT_YEAR);
        return employeesTypology;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeesTypology createUpdatedEntity(EntityManager em) {
        EmployeesTypology employeesTypology = new EmployeesTypology()
            .value(UPDATED_VALUE)
            .year(UPDATED_YEAR);
        return employeesTypology;
    }

    @BeforeEach
    public void initTest() {
        employeesTypology = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeesTypology() throws Exception {
        int databaseSizeBeforeCreate = employeesTypologyRepository.findAll().size();

        // Create the EmployeesTypology
        EmployeesTypologyDTO employeesTypologyDTO = employeesTypologyMapper.toDto(employeesTypology);
        restEmployeesTypologyMockMvc.perform(post("/api/employees-typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesTypologyDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeesTypology in the database
        List<EmployeesTypology> employeesTypologyList = employeesTypologyRepository.findAll();
        assertThat(employeesTypologyList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeesTypology testEmployeesTypology = employeesTypologyList.get(employeesTypologyList.size() - 1);
        assertThat(testEmployeesTypology.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testEmployeesTypology.getYear()).isEqualTo(DEFAULT_YEAR);
    }

    @Test
    @Transactional
    public void createEmployeesTypologyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeesTypologyRepository.findAll().size();

        // Create the EmployeesTypology with an existing ID
        employeesTypology.setId(1L);
        EmployeesTypologyDTO employeesTypologyDTO = employeesTypologyMapper.toDto(employeesTypology);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeesTypologyMockMvc.perform(post("/api/employees-typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesTypologyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeesTypology in the database
        List<EmployeesTypology> employeesTypologyList = employeesTypologyRepository.findAll();
        assertThat(employeesTypologyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeesTypologies() throws Exception {
        // Initialize the database
        employeesTypologyRepository.saveAndFlush(employeesTypology);

        // Get all the employeesTypologyList
        restEmployeesTypologyMockMvc.perform(get("/api/employees-typologies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeesTypology.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)));
    }
    
    @Test
    @Transactional
    public void getEmployeesTypology() throws Exception {
        // Initialize the database
        employeesTypologyRepository.saveAndFlush(employeesTypology);

        // Get the employeesTypology
        restEmployeesTypologyMockMvc.perform(get("/api/employees-typologies/{id}", employeesTypology.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeesTypology.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeesTypology() throws Exception {
        // Get the employeesTypology
        restEmployeesTypologyMockMvc.perform(get("/api/employees-typologies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeesTypology() throws Exception {
        // Initialize the database
        employeesTypologyRepository.saveAndFlush(employeesTypology);

        int databaseSizeBeforeUpdate = employeesTypologyRepository.findAll().size();

        // Update the employeesTypology
        EmployeesTypology updatedEmployeesTypology = employeesTypologyRepository.findById(employeesTypology.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeesTypology are not directly saved in db
        em.detach(updatedEmployeesTypology);
        updatedEmployeesTypology
            .value(UPDATED_VALUE)
            .year(UPDATED_YEAR);
        EmployeesTypologyDTO employeesTypologyDTO = employeesTypologyMapper.toDto(updatedEmployeesTypology);

        restEmployeesTypologyMockMvc.perform(put("/api/employees-typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesTypologyDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeesTypology in the database
        List<EmployeesTypology> employeesTypologyList = employeesTypologyRepository.findAll();
        assertThat(employeesTypologyList).hasSize(databaseSizeBeforeUpdate);
        EmployeesTypology testEmployeesTypology = employeesTypologyList.get(employeesTypologyList.size() - 1);
        assertThat(testEmployeesTypology.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testEmployeesTypology.getYear()).isEqualTo(UPDATED_YEAR);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeesTypology() throws Exception {
        int databaseSizeBeforeUpdate = employeesTypologyRepository.findAll().size();

        // Create the EmployeesTypology
        EmployeesTypologyDTO employeesTypologyDTO = employeesTypologyMapper.toDto(employeesTypology);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeesTypologyMockMvc.perform(put("/api/employees-typologies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeesTypologyDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeesTypology in the database
        List<EmployeesTypology> employeesTypologyList = employeesTypologyRepository.findAll();
        assertThat(employeesTypologyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeesTypology() throws Exception {
        // Initialize the database
        employeesTypologyRepository.saveAndFlush(employeesTypology);

        int databaseSizeBeforeDelete = employeesTypologyRepository.findAll().size();

        // Delete the employeesTypology
        restEmployeesTypologyMockMvc.perform(delete("/api/employees-typologies/{id}", employeesTypology.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeesTypology> employeesTypologyList = employeesTypologyRepository.findAll();
        assertThat(employeesTypologyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeesTypology.class);
        EmployeesTypology employeesTypology1 = new EmployeesTypology();
        employeesTypology1.setId(1L);
        EmployeesTypology employeesTypology2 = new EmployeesTypology();
        employeesTypology2.setId(employeesTypology1.getId());
        assertThat(employeesTypology1).isEqualTo(employeesTypology2);
        employeesTypology2.setId(2L);
        assertThat(employeesTypology1).isNotEqualTo(employeesTypology2);
        employeesTypology1.setId(null);
        assertThat(employeesTypology1).isNotEqualTo(employeesTypology2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeesTypologyDTO.class);
        EmployeesTypologyDTO employeesTypologyDTO1 = new EmployeesTypologyDTO();
        employeesTypologyDTO1.setId(1L);
        EmployeesTypologyDTO employeesTypologyDTO2 = new EmployeesTypologyDTO();
        assertThat(employeesTypologyDTO1).isNotEqualTo(employeesTypologyDTO2);
        employeesTypologyDTO2.setId(employeesTypologyDTO1.getId());
        assertThat(employeesTypologyDTO1).isEqualTo(employeesTypologyDTO2);
        employeesTypologyDTO2.setId(2L);
        assertThat(employeesTypologyDTO1).isNotEqualTo(employeesTypologyDTO2);
        employeesTypologyDTO1.setId(null);
        assertThat(employeesTypologyDTO1).isNotEqualTo(employeesTypologyDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeesTypologyMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeesTypologyMapper.fromId(null)).isNull();
    }
}
