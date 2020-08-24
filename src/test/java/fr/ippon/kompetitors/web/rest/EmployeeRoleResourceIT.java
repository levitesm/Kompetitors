package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.EmployeeRole;
import fr.ippon.kompetitors.repository.EmployeeRoleRepository;
import fr.ippon.kompetitors.service.EmployeeRoleService;
import fr.ippon.kompetitors.service.dto.EmployeeRoleDTO;
import fr.ippon.kompetitors.service.mapper.EmployeeRoleMapper;
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
 * Integration tests for the {@link EmployeeRoleResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class EmployeeRoleResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    @Autowired
    private EmployeeRoleRepository employeeRoleRepository;

    @Autowired
    private EmployeeRoleMapper employeeRoleMapper;

    @Autowired
    private EmployeeRoleService employeeRoleService;

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

    private MockMvc restEmployeeRoleMockMvc;

    private EmployeeRole employeeRole;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeRoleResource employeeRoleResource = new EmployeeRoleResource(employeeRoleService);
        this.restEmployeeRoleMockMvc = MockMvcBuilders.standaloneSetup(employeeRoleResource)
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
    public static EmployeeRole createEntity(EntityManager em) {
        EmployeeRole employeeRole = new EmployeeRole()
            .name(DEFAULT_NAME);
        return employeeRole;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeeRole createUpdatedEntity(EntityManager em) {
        EmployeeRole employeeRole = new EmployeeRole()
            .name(UPDATED_NAME);
        return employeeRole;
    }

    @BeforeEach
    public void initTest() {
        employeeRole = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeeRole() throws Exception {
        int databaseSizeBeforeCreate = employeeRoleRepository.findAll().size();

        // Create the EmployeeRole
        EmployeeRoleDTO employeeRoleDTO = employeeRoleMapper.toDto(employeeRole);
        restEmployeeRoleMockMvc.perform(post("/api/employee-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeRoleDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeeRole in the database
        List<EmployeeRole> employeeRoleList = employeeRoleRepository.findAll();
        assertThat(employeeRoleList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeeRole testEmployeeRole = employeeRoleList.get(employeeRoleList.size() - 1);
        assertThat(testEmployeeRole.getName()).isEqualTo(DEFAULT_NAME);
    }

    @Test
    @Transactional
    public void createEmployeeRoleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRoleRepository.findAll().size();

        // Create the EmployeeRole with an existing ID
        employeeRole.setId(1L);
        EmployeeRoleDTO employeeRoleDTO = employeeRoleMapper.toDto(employeeRole);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeRoleMockMvc.perform(post("/api/employee-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRole in the database
        List<EmployeeRole> employeeRoleList = employeeRoleRepository.findAll();
        assertThat(employeeRoleList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRoleRepository.findAll().size();
        // set the field null
        employeeRole.setName(null);

        // Create the EmployeeRole, which fails.
        EmployeeRoleDTO employeeRoleDTO = employeeRoleMapper.toDto(employeeRole);

        restEmployeeRoleMockMvc.perform(post("/api/employee-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeRoleDTO)))
            .andExpect(status().isBadRequest());

        List<EmployeeRole> employeeRoleList = employeeRoleRepository.findAll();
        assertThat(employeeRoleList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployeeRoles() throws Exception {
        // Initialize the database
        employeeRoleRepository.saveAndFlush(employeeRole);

        // Get all the employeeRoleList
        restEmployeeRoleMockMvc.perform(get("/api/employee-roles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeeRole.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)));
    }
    
    @Test
    @Transactional
    public void getEmployeeRole() throws Exception {
        // Initialize the database
        employeeRoleRepository.saveAndFlush(employeeRole);

        // Get the employeeRole
        restEmployeeRoleMockMvc.perform(get("/api/employee-roles/{id}", employeeRole.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeeRole.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeeRole() throws Exception {
        // Get the employeeRole
        restEmployeeRoleMockMvc.perform(get("/api/employee-roles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeeRole() throws Exception {
        // Initialize the database
        employeeRoleRepository.saveAndFlush(employeeRole);

        int databaseSizeBeforeUpdate = employeeRoleRepository.findAll().size();

        // Update the employeeRole
        EmployeeRole updatedEmployeeRole = employeeRoleRepository.findById(employeeRole.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeeRole are not directly saved in db
        em.detach(updatedEmployeeRole);
        updatedEmployeeRole
            .name(UPDATED_NAME);
        EmployeeRoleDTO employeeRoleDTO = employeeRoleMapper.toDto(updatedEmployeeRole);

        restEmployeeRoleMockMvc.perform(put("/api/employee-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeRoleDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeeRole in the database
        List<EmployeeRole> employeeRoleList = employeeRoleRepository.findAll();
        assertThat(employeeRoleList).hasSize(databaseSizeBeforeUpdate);
        EmployeeRole testEmployeeRole = employeeRoleList.get(employeeRoleList.size() - 1);
        assertThat(testEmployeeRole.getName()).isEqualTo(UPDATED_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeeRole() throws Exception {
        int databaseSizeBeforeUpdate = employeeRoleRepository.findAll().size();

        // Create the EmployeeRole
        EmployeeRoleDTO employeeRoleDTO = employeeRoleMapper.toDto(employeeRole);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeRoleMockMvc.perform(put("/api/employee-roles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeRoleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeeRole in the database
        List<EmployeeRole> employeeRoleList = employeeRoleRepository.findAll();
        assertThat(employeeRoleList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeeRole() throws Exception {
        // Initialize the database
        employeeRoleRepository.saveAndFlush(employeeRole);

        int databaseSizeBeforeDelete = employeeRoleRepository.findAll().size();

        // Delete the employeeRole
        restEmployeeRoleMockMvc.perform(delete("/api/employee-roles/{id}", employeeRole.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeeRole> employeeRoleList = employeeRoleRepository.findAll();
        assertThat(employeeRoleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeRole.class);
        EmployeeRole employeeRole1 = new EmployeeRole();
        employeeRole1.setId(1L);
        EmployeeRole employeeRole2 = new EmployeeRole();
        employeeRole2.setId(employeeRole1.getId());
        assertThat(employeeRole1).isEqualTo(employeeRole2);
        employeeRole2.setId(2L);
        assertThat(employeeRole1).isNotEqualTo(employeeRole2);
        employeeRole1.setId(null);
        assertThat(employeeRole1).isNotEqualTo(employeeRole2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeRoleDTO.class);
        EmployeeRoleDTO employeeRoleDTO1 = new EmployeeRoleDTO();
        employeeRoleDTO1.setId(1L);
        EmployeeRoleDTO employeeRoleDTO2 = new EmployeeRoleDTO();
        assertThat(employeeRoleDTO1).isNotEqualTo(employeeRoleDTO2);
        employeeRoleDTO2.setId(employeeRoleDTO1.getId());
        assertThat(employeeRoleDTO1).isEqualTo(employeeRoleDTO2);
        employeeRoleDTO2.setId(2L);
        assertThat(employeeRoleDTO1).isNotEqualTo(employeeRoleDTO2);
        employeeRoleDTO1.setId(null);
        assertThat(employeeRoleDTO1).isNotEqualTo(employeeRoleDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeeRoleMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeeRoleMapper.fromId(null)).isNull();
    }
}
