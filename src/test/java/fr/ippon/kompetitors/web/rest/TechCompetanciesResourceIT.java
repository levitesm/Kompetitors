package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.TechCompetancies;
import fr.ippon.kompetitors.repository.TechCompetanciesRepository;
import fr.ippon.kompetitors.service.TechCompetanciesService;
import fr.ippon.kompetitors.service.dto.TechCompetanciesDTO;
import fr.ippon.kompetitors.service.mapper.TechCompetanciesMapper;
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
 * Integration tests for the {@link TechCompetanciesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class TechCompetanciesResourceIT {

    @Autowired
    private TechCompetanciesRepository techCompetanciesRepository;

    @Autowired
    private TechCompetanciesMapper techCompetanciesMapper;

    @Autowired
    private TechCompetanciesService techCompetanciesService;

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

    private MockMvc restTechCompetanciesMockMvc;

    private TechCompetancies techCompetancies;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TechCompetanciesResource techCompetanciesResource = new TechCompetanciesResource(techCompetanciesService);
        this.restTechCompetanciesMockMvc = MockMvcBuilders.standaloneSetup(techCompetanciesResource)
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
    public static TechCompetancies createEntity(EntityManager em) {
        TechCompetancies techCompetancies = new TechCompetancies();
        return techCompetancies;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechCompetancies createUpdatedEntity(EntityManager em) {
        TechCompetancies techCompetancies = new TechCompetancies();
        return techCompetancies;
    }

    @BeforeEach
    public void initTest() {
        techCompetancies = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechCompetancies() throws Exception {
        int databaseSizeBeforeCreate = techCompetanciesRepository.findAll().size();

        // Create the TechCompetancies
        TechCompetanciesDTO techCompetanciesDTO = techCompetanciesMapper.toDto(techCompetancies);
        restTechCompetanciesMockMvc.perform(post("/api/tech-competancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techCompetanciesDTO)))
            .andExpect(status().isCreated());

        // Validate the TechCompetancies in the database
        List<TechCompetancies> techCompetanciesList = techCompetanciesRepository.findAll();
        assertThat(techCompetanciesList).hasSize(databaseSizeBeforeCreate + 1);
        TechCompetancies testTechCompetancies = techCompetanciesList.get(techCompetanciesList.size() - 1);
    }

    @Test
    @Transactional
    public void createTechCompetanciesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = techCompetanciesRepository.findAll().size();

        // Create the TechCompetancies with an existing ID
        techCompetancies.setId(1L);
        TechCompetanciesDTO techCompetanciesDTO = techCompetanciesMapper.toDto(techCompetancies);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechCompetanciesMockMvc.perform(post("/api/tech-competancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techCompetanciesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechCompetancies in the database
        List<TechCompetancies> techCompetanciesList = techCompetanciesRepository.findAll();
        assertThat(techCompetanciesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTechCompetancies() throws Exception {
        // Initialize the database
        techCompetanciesRepository.saveAndFlush(techCompetancies);

        // Get all the techCompetanciesList
        restTechCompetanciesMockMvc.perform(get("/api/tech-competancies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(techCompetancies.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTechCompetancies() throws Exception {
        // Initialize the database
        techCompetanciesRepository.saveAndFlush(techCompetancies);

        // Get the techCompetancies
        restTechCompetanciesMockMvc.perform(get("/api/tech-competancies/{id}", techCompetancies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(techCompetancies.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTechCompetancies() throws Exception {
        // Get the techCompetancies
        restTechCompetanciesMockMvc.perform(get("/api/tech-competancies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechCompetancies() throws Exception {
        // Initialize the database
        techCompetanciesRepository.saveAndFlush(techCompetancies);

        int databaseSizeBeforeUpdate = techCompetanciesRepository.findAll().size();

        // Update the techCompetancies
        TechCompetancies updatedTechCompetancies = techCompetanciesRepository.findById(techCompetancies.getId()).get();
        // Disconnect from session so that the updates on updatedTechCompetancies are not directly saved in db
        em.detach(updatedTechCompetancies);
        TechCompetanciesDTO techCompetanciesDTO = techCompetanciesMapper.toDto(updatedTechCompetancies);

        restTechCompetanciesMockMvc.perform(put("/api/tech-competancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techCompetanciesDTO)))
            .andExpect(status().isOk());

        // Validate the TechCompetancies in the database
        List<TechCompetancies> techCompetanciesList = techCompetanciesRepository.findAll();
        assertThat(techCompetanciesList).hasSize(databaseSizeBeforeUpdate);
        TechCompetancies testTechCompetancies = techCompetanciesList.get(techCompetanciesList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTechCompetancies() throws Exception {
        int databaseSizeBeforeUpdate = techCompetanciesRepository.findAll().size();

        // Create the TechCompetancies
        TechCompetanciesDTO techCompetanciesDTO = techCompetanciesMapper.toDto(techCompetancies);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechCompetanciesMockMvc.perform(put("/api/tech-competancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techCompetanciesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechCompetancies in the database
        List<TechCompetancies> techCompetanciesList = techCompetanciesRepository.findAll();
        assertThat(techCompetanciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTechCompetancies() throws Exception {
        // Initialize the database
        techCompetanciesRepository.saveAndFlush(techCompetancies);

        int databaseSizeBeforeDelete = techCompetanciesRepository.findAll().size();

        // Delete the techCompetancies
        restTechCompetanciesMockMvc.perform(delete("/api/tech-competancies/{id}", techCompetancies.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TechCompetancies> techCompetanciesList = techCompetanciesRepository.findAll();
        assertThat(techCompetanciesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechCompetancies.class);
        TechCompetancies techCompetancies1 = new TechCompetancies();
        techCompetancies1.setId(1L);
        TechCompetancies techCompetancies2 = new TechCompetancies();
        techCompetancies2.setId(techCompetancies1.getId());
        assertThat(techCompetancies1).isEqualTo(techCompetancies2);
        techCompetancies2.setId(2L);
        assertThat(techCompetancies1).isNotEqualTo(techCompetancies2);
        techCompetancies1.setId(null);
        assertThat(techCompetancies1).isNotEqualTo(techCompetancies2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechCompetanciesDTO.class);
        TechCompetanciesDTO techCompetanciesDTO1 = new TechCompetanciesDTO();
        techCompetanciesDTO1.setId(1L);
        TechCompetanciesDTO techCompetanciesDTO2 = new TechCompetanciesDTO();
        assertThat(techCompetanciesDTO1).isNotEqualTo(techCompetanciesDTO2);
        techCompetanciesDTO2.setId(techCompetanciesDTO1.getId());
        assertThat(techCompetanciesDTO1).isEqualTo(techCompetanciesDTO2);
        techCompetanciesDTO2.setId(2L);
        assertThat(techCompetanciesDTO1).isNotEqualTo(techCompetanciesDTO2);
        techCompetanciesDTO1.setId(null);
        assertThat(techCompetanciesDTO1).isNotEqualTo(techCompetanciesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(techCompetanciesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(techCompetanciesMapper.fromId(null)).isNull();
    }
}
