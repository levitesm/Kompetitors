package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.TechProjects;
import fr.ippon.kompetitors.repository.TechProjectsRepository;
import fr.ippon.kompetitors.service.TechProjectsService;
import fr.ippon.kompetitors.service.dto.TechProjectsDTO;
import fr.ippon.kompetitors.service.mapper.TechProjectsMapper;
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
 * Integration tests for the {@link TechProjectsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class TechProjectsResourceIT {

    @Autowired
    private TechProjectsRepository techProjectsRepository;

    @Autowired
    private TechProjectsMapper techProjectsMapper;

    @Autowired
    private TechProjectsService techProjectsService;

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

    private MockMvc restTechProjectsMockMvc;

    private TechProjects techProjects;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TechProjectsResource techProjectsResource = new TechProjectsResource(techProjectsService);
        this.restTechProjectsMockMvc = MockMvcBuilders.standaloneSetup(techProjectsResource)
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
    public static TechProjects createEntity(EntityManager em) {
        TechProjects techProjects = new TechProjects();
        return techProjects;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechProjects createUpdatedEntity(EntityManager em) {
        TechProjects techProjects = new TechProjects();
        return techProjects;
    }

    @BeforeEach
    public void initTest() {
        techProjects = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechProjects() throws Exception {
        int databaseSizeBeforeCreate = techProjectsRepository.findAll().size();

        // Create the TechProjects
        TechProjectsDTO techProjectsDTO = techProjectsMapper.toDto(techProjects);
        restTechProjectsMockMvc.perform(post("/api/tech-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techProjectsDTO)))
            .andExpect(status().isCreated());

        // Validate the TechProjects in the database
        List<TechProjects> techProjectsList = techProjectsRepository.findAll();
        assertThat(techProjectsList).hasSize(databaseSizeBeforeCreate + 1);
        TechProjects testTechProjects = techProjectsList.get(techProjectsList.size() - 1);
    }

    @Test
    @Transactional
    public void createTechProjectsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = techProjectsRepository.findAll().size();

        // Create the TechProjects with an existing ID
        techProjects.setId(1L);
        TechProjectsDTO techProjectsDTO = techProjectsMapper.toDto(techProjects);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechProjectsMockMvc.perform(post("/api/tech-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techProjectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechProjects in the database
        List<TechProjects> techProjectsList = techProjectsRepository.findAll();
        assertThat(techProjectsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTechProjects() throws Exception {
        // Initialize the database
        techProjectsRepository.saveAndFlush(techProjects);

        // Get all the techProjectsList
        restTechProjectsMockMvc.perform(get("/api/tech-projects?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(techProjects.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTechProjects() throws Exception {
        // Initialize the database
        techProjectsRepository.saveAndFlush(techProjects);

        // Get the techProjects
        restTechProjectsMockMvc.perform(get("/api/tech-projects/{id}", techProjects.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(techProjects.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTechProjects() throws Exception {
        // Get the techProjects
        restTechProjectsMockMvc.perform(get("/api/tech-projects/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechProjects() throws Exception {
        // Initialize the database
        techProjectsRepository.saveAndFlush(techProjects);

        int databaseSizeBeforeUpdate = techProjectsRepository.findAll().size();

        // Update the techProjects
        TechProjects updatedTechProjects = techProjectsRepository.findById(techProjects.getId()).get();
        // Disconnect from session so that the updates on updatedTechProjects are not directly saved in db
        em.detach(updatedTechProjects);
        TechProjectsDTO techProjectsDTO = techProjectsMapper.toDto(updatedTechProjects);

        restTechProjectsMockMvc.perform(put("/api/tech-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techProjectsDTO)))
            .andExpect(status().isOk());

        // Validate the TechProjects in the database
        List<TechProjects> techProjectsList = techProjectsRepository.findAll();
        assertThat(techProjectsList).hasSize(databaseSizeBeforeUpdate);
        TechProjects testTechProjects = techProjectsList.get(techProjectsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTechProjects() throws Exception {
        int databaseSizeBeforeUpdate = techProjectsRepository.findAll().size();

        // Create the TechProjects
        TechProjectsDTO techProjectsDTO = techProjectsMapper.toDto(techProjects);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechProjectsMockMvc.perform(put("/api/tech-projects")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techProjectsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechProjects in the database
        List<TechProjects> techProjectsList = techProjectsRepository.findAll();
        assertThat(techProjectsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTechProjects() throws Exception {
        // Initialize the database
        techProjectsRepository.saveAndFlush(techProjects);

        int databaseSizeBeforeDelete = techProjectsRepository.findAll().size();

        // Delete the techProjects
        restTechProjectsMockMvc.perform(delete("/api/tech-projects/{id}", techProjects.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TechProjects> techProjectsList = techProjectsRepository.findAll();
        assertThat(techProjectsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechProjects.class);
        TechProjects techProjects1 = new TechProjects();
        techProjects1.setId(1L);
        TechProjects techProjects2 = new TechProjects();
        techProjects2.setId(techProjects1.getId());
        assertThat(techProjects1).isEqualTo(techProjects2);
        techProjects2.setId(2L);
        assertThat(techProjects1).isNotEqualTo(techProjects2);
        techProjects1.setId(null);
        assertThat(techProjects1).isNotEqualTo(techProjects2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechProjectsDTO.class);
        TechProjectsDTO techProjectsDTO1 = new TechProjectsDTO();
        techProjectsDTO1.setId(1L);
        TechProjectsDTO techProjectsDTO2 = new TechProjectsDTO();
        assertThat(techProjectsDTO1).isNotEqualTo(techProjectsDTO2);
        techProjectsDTO2.setId(techProjectsDTO1.getId());
        assertThat(techProjectsDTO1).isEqualTo(techProjectsDTO2);
        techProjectsDTO2.setId(2L);
        assertThat(techProjectsDTO1).isNotEqualTo(techProjectsDTO2);
        techProjectsDTO1.setId(null);
        assertThat(techProjectsDTO1).isNotEqualTo(techProjectsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(techProjectsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(techProjectsMapper.fromId(null)).isNull();
    }
}
