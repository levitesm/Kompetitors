package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.TechTools;
import fr.ippon.kompetitors.repository.TechToolsRepository;
import fr.ippon.kompetitors.service.TechToolsService;
import fr.ippon.kompetitors.service.dto.TechToolsDTO;
import fr.ippon.kompetitors.service.mapper.TechToolsMapper;
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
 * Integration tests for the {@link TechToolsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class TechToolsResourceIT {

    @Autowired
    private TechToolsRepository techToolsRepository;

    @Autowired
    private TechToolsMapper techToolsMapper;

    @Autowired
    private TechToolsService techToolsService;

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

    private MockMvc restTechToolsMockMvc;

    private TechTools techTools;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TechToolsResource techToolsResource = new TechToolsResource(techToolsService);
        this.restTechToolsMockMvc = MockMvcBuilders.standaloneSetup(techToolsResource)
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
    public static TechTools createEntity(EntityManager em) {
        TechTools techTools = new TechTools();
        return techTools;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechTools createUpdatedEntity(EntityManager em) {
        TechTools techTools = new TechTools();
        return techTools;
    }

    @BeforeEach
    public void initTest() {
        techTools = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechTools() throws Exception {
        int databaseSizeBeforeCreate = techToolsRepository.findAll().size();

        // Create the TechTools
        TechToolsDTO techToolsDTO = techToolsMapper.toDto(techTools);
        restTechToolsMockMvc.perform(post("/api/tech-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techToolsDTO)))
            .andExpect(status().isCreated());

        // Validate the TechTools in the database
        List<TechTools> techToolsList = techToolsRepository.findAll();
        assertThat(techToolsList).hasSize(databaseSizeBeforeCreate + 1);
        TechTools testTechTools = techToolsList.get(techToolsList.size() - 1);
    }

    @Test
    @Transactional
    public void createTechToolsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = techToolsRepository.findAll().size();

        // Create the TechTools with an existing ID
        techTools.setId(1L);
        TechToolsDTO techToolsDTO = techToolsMapper.toDto(techTools);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechToolsMockMvc.perform(post("/api/tech-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techToolsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechTools in the database
        List<TechTools> techToolsList = techToolsRepository.findAll();
        assertThat(techToolsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTechTools() throws Exception {
        // Initialize the database
        techToolsRepository.saveAndFlush(techTools);

        // Get all the techToolsList
        restTechToolsMockMvc.perform(get("/api/tech-tools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(techTools.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTechTools() throws Exception {
        // Initialize the database
        techToolsRepository.saveAndFlush(techTools);

        // Get the techTools
        restTechToolsMockMvc.perform(get("/api/tech-tools/{id}", techTools.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(techTools.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTechTools() throws Exception {
        // Get the techTools
        restTechToolsMockMvc.perform(get("/api/tech-tools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechTools() throws Exception {
        // Initialize the database
        techToolsRepository.saveAndFlush(techTools);

        int databaseSizeBeforeUpdate = techToolsRepository.findAll().size();

        // Update the techTools
        TechTools updatedTechTools = techToolsRepository.findById(techTools.getId()).get();
        // Disconnect from session so that the updates on updatedTechTools are not directly saved in db
        em.detach(updatedTechTools);
        TechToolsDTO techToolsDTO = techToolsMapper.toDto(updatedTechTools);

        restTechToolsMockMvc.perform(put("/api/tech-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techToolsDTO)))
            .andExpect(status().isOk());

        // Validate the TechTools in the database
        List<TechTools> techToolsList = techToolsRepository.findAll();
        assertThat(techToolsList).hasSize(databaseSizeBeforeUpdate);
        TechTools testTechTools = techToolsList.get(techToolsList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTechTools() throws Exception {
        int databaseSizeBeforeUpdate = techToolsRepository.findAll().size();

        // Create the TechTools
        TechToolsDTO techToolsDTO = techToolsMapper.toDto(techTools);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechToolsMockMvc.perform(put("/api/tech-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techToolsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechTools in the database
        List<TechTools> techToolsList = techToolsRepository.findAll();
        assertThat(techToolsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTechTools() throws Exception {
        // Initialize the database
        techToolsRepository.saveAndFlush(techTools);

        int databaseSizeBeforeDelete = techToolsRepository.findAll().size();

        // Delete the techTools
        restTechToolsMockMvc.perform(delete("/api/tech-tools/{id}", techTools.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TechTools> techToolsList = techToolsRepository.findAll();
        assertThat(techToolsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechTools.class);
        TechTools techTools1 = new TechTools();
        techTools1.setId(1L);
        TechTools techTools2 = new TechTools();
        techTools2.setId(techTools1.getId());
        assertThat(techTools1).isEqualTo(techTools2);
        techTools2.setId(2L);
        assertThat(techTools1).isNotEqualTo(techTools2);
        techTools1.setId(null);
        assertThat(techTools1).isNotEqualTo(techTools2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechToolsDTO.class);
        TechToolsDTO techToolsDTO1 = new TechToolsDTO();
        techToolsDTO1.setId(1L);
        TechToolsDTO techToolsDTO2 = new TechToolsDTO();
        assertThat(techToolsDTO1).isNotEqualTo(techToolsDTO2);
        techToolsDTO2.setId(techToolsDTO1.getId());
        assertThat(techToolsDTO1).isEqualTo(techToolsDTO2);
        techToolsDTO2.setId(2L);
        assertThat(techToolsDTO1).isNotEqualTo(techToolsDTO2);
        techToolsDTO1.setId(null);
        assertThat(techToolsDTO1).isNotEqualTo(techToolsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(techToolsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(techToolsMapper.fromId(null)).isNull();
    }
}
