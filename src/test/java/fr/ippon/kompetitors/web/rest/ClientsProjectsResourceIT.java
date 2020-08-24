package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ClientsProjects;
import fr.ippon.kompetitors.repository.ClientsProjectsRepository;
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
 * Integration tests for the {@link ClientsProjectsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ClientsProjectsResourceIT {

    private static final String DEFAULT_STATUS = "AAAAAAAAAA";
    private static final String UPDATED_STATUS = "BBBBBBBBBB";

    @Autowired
    private ClientsProjectsRepository clientsProjectsRepository;

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

    private MockMvc restClientsProjectsMockMvc;

    private ClientsProjects clientsProjects;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ClientsProjectsResource clientsProjectsResource = new ClientsProjectsResource(clientsProjectsRepository);
        this.restClientsProjectsMockMvc = MockMvcBuilders.standaloneSetup(clientsProjectsResource)
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
    public static ClientsProjects createEntity(EntityManager em) {
        ClientsProjects clientsProjects = new ClientsProjects()
            .status(DEFAULT_STATUS);
        return clientsProjects;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ClientsProjects createUpdatedEntity(EntityManager em) {
        ClientsProjects clientsProjects = new ClientsProjects()
            .status(UPDATED_STATUS);
        return clientsProjects;
    }

    @BeforeEach
    public void initTest() {
        clientsProjects = createEntity(em);
    }

    @Test
    @Transactional
    public void createClientsProjects() throws Exception {
        int databaseSizeBeforeCreate = clientsProjectsRepository.findAll().size();

        // Create the ClientsProjects
        restClientsProjectsMockMvc.perform(post("/api/clients-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsProjects)))
            .andExpect(status().isCreated());

        // Validate the ClientsProjects in the database
        List<ClientsProjects> clientsProjectsList = clientsProjectsRepository.findAll();
        assertThat(clientsProjectsList).hasSize(databaseSizeBeforeCreate + 1);
        ClientsProjects testClientsProjects = clientsProjectsList.get(clientsProjectsList.size() - 1);
        assertThat(testClientsProjects.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createClientsProjectsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = clientsProjectsRepository.findAll().size();

        // Create the ClientsProjects with an existing ID
        clientsProjects.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restClientsProjectsMockMvc.perform(post("/api/clients-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsProjects)))
            .andExpect(status().isBadRequest());

        // Validate the ClientsProjects in the database
        List<ClientsProjects> clientsProjectsList = clientsProjectsRepository.findAll();
        assertThat(clientsProjectsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllClientsProjects() throws Exception {
        // Initialize the database
        clientsProjectsRepository.saveAndFlush(clientsProjects);

        // Get all the clientsProjectsList
        restClientsProjectsMockMvc.perform(get("/api/clients-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(clientsProjects.getId().intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS)));
    }
    
    @Test
    @Transactional
    public void getClientsProjects() throws Exception {
        // Initialize the database
        clientsProjectsRepository.saveAndFlush(clientsProjects);

        // Get the clientsProjects
        restClientsProjectsMockMvc.perform(get("/api/clients-projects/{id}", clientsProjects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(clientsProjects.getId().intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS));
    }

    @Test
    @Transactional
    public void getNonExistingClientsProjects() throws Exception {
        // Get the clientsProjects
        restClientsProjectsMockMvc.perform(get("/api/clients-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateClientsProjects() throws Exception {
        // Initialize the database
        clientsProjectsRepository.saveAndFlush(clientsProjects);

        int databaseSizeBeforeUpdate = clientsProjectsRepository.findAll().size();

        // Update the clientsProjects
        ClientsProjects updatedClientsProjects = clientsProjectsRepository.findById(clientsProjects.getId()).get();
        // Disconnect from session so that the updates on updatedClientsProjects are not directly saved in db
        em.detach(updatedClientsProjects);
        updatedClientsProjects
            .status(UPDATED_STATUS);

        restClientsProjectsMockMvc.perform(put("/api/clients-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedClientsProjects)))
            .andExpect(status().isOk());

        // Validate the ClientsProjects in the database
        List<ClientsProjects> clientsProjectsList = clientsProjectsRepository.findAll();
        assertThat(clientsProjectsList).hasSize(databaseSizeBeforeUpdate);
        ClientsProjects testClientsProjects = clientsProjectsList.get(clientsProjectsList.size() - 1);
        assertThat(testClientsProjects.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingClientsProjects() throws Exception {
        int databaseSizeBeforeUpdate = clientsProjectsRepository.findAll().size();

        // Create the ClientsProjects

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restClientsProjectsMockMvc.perform(put("/api/clients-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(clientsProjects)))
            .andExpect(status().isBadRequest());

        // Validate the ClientsProjects in the database
        List<ClientsProjects> clientsProjectsList = clientsProjectsRepository.findAll();
        assertThat(clientsProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteClientsProjects() throws Exception {
        // Initialize the database
        clientsProjectsRepository.saveAndFlush(clientsProjects);

        int databaseSizeBeforeDelete = clientsProjectsRepository.findAll().size();

        // Delete the clientsProjects
        restClientsProjectsMockMvc.perform(delete("/api/clients-projects/{id}", clientsProjects.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ClientsProjects> clientsProjectsList = clientsProjectsRepository.findAll();
        assertThat(clientsProjectsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ClientsProjects.class);
        ClientsProjects clientsProjects1 = new ClientsProjects();
        clientsProjects1.setId(1L);
        ClientsProjects clientsProjects2 = new ClientsProjects();
        clientsProjects2.setId(clientsProjects1.getId());
        assertThat(clientsProjects1).isEqualTo(clientsProjects2);
        clientsProjects2.setId(2L);
        assertThat(clientsProjects1).isNotEqualTo(clientsProjects2);
        clientsProjects1.setId(null);
        assertThat(clientsProjects1).isNotEqualTo(clientsProjects2);
    }
}
