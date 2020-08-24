package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.TechServices;
import fr.ippon.kompetitors.repository.TechServicesRepository;
import fr.ippon.kompetitors.service.TechServicesService;
import fr.ippon.kompetitors.service.dto.TechServicesDTO;
import fr.ippon.kompetitors.service.mapper.TechServicesMapper;
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
 * Integration tests for the {@link TechServicesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class TechServicesResourceIT {

    @Autowired
    private TechServicesRepository techServicesRepository;

    @Autowired
    private TechServicesMapper techServicesMapper;

    @Autowired
    private TechServicesService techServicesService;

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

    private MockMvc restTechServicesMockMvc;

    private TechServices techServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TechServicesResource techServicesResource = new TechServicesResource(techServicesService);
        this.restTechServicesMockMvc = MockMvcBuilders.standaloneSetup(techServicesResource)
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
    public static TechServices createEntity(EntityManager em) {
        TechServices techServices = new TechServices();
        return techServices;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechServices createUpdatedEntity(EntityManager em) {
        TechServices techServices = new TechServices();
        return techServices;
    }

    @BeforeEach
    public void initTest() {
        techServices = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechServices() throws Exception {
        int databaseSizeBeforeCreate = techServicesRepository.findAll().size();

        // Create the TechServices
        TechServicesDTO techServicesDTO = techServicesMapper.toDto(techServices);
        restTechServicesMockMvc.perform(post("/api/tech-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techServicesDTO)))
            .andExpect(status().isCreated());

        // Validate the TechServices in the database
        List<TechServices> techServicesList = techServicesRepository.findAll();
        assertThat(techServicesList).hasSize(databaseSizeBeforeCreate + 1);
        TechServices testTechServices = techServicesList.get(techServicesList.size() - 1);
    }

    @Test
    @Transactional
    public void createTechServicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = techServicesRepository.findAll().size();

        // Create the TechServices with an existing ID
        techServices.setId(1L);
        TechServicesDTO techServicesDTO = techServicesMapper.toDto(techServices);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechServicesMockMvc.perform(post("/api/tech-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techServicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechServices in the database
        List<TechServices> techServicesList = techServicesRepository.findAll();
        assertThat(techServicesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTechServices() throws Exception {
        // Initialize the database
        techServicesRepository.saveAndFlush(techServices);

        // Get all the techServicesList
        restTechServicesMockMvc.perform(get("/api/tech-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(techServices.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTechServices() throws Exception {
        // Initialize the database
        techServicesRepository.saveAndFlush(techServices);

        // Get the techServices
        restTechServicesMockMvc.perform(get("/api/tech-services/{id}", techServices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(techServices.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTechServices() throws Exception {
        // Get the techServices
        restTechServicesMockMvc.perform(get("/api/tech-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechServices() throws Exception {
        // Initialize the database
        techServicesRepository.saveAndFlush(techServices);

        int databaseSizeBeforeUpdate = techServicesRepository.findAll().size();

        // Update the techServices
        TechServices updatedTechServices = techServicesRepository.findById(techServices.getId()).get();
        // Disconnect from session so that the updates on updatedTechServices are not directly saved in db
        em.detach(updatedTechServices);
        TechServicesDTO techServicesDTO = techServicesMapper.toDto(updatedTechServices);

        restTechServicesMockMvc.perform(put("/api/tech-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techServicesDTO)))
            .andExpect(status().isOk());

        // Validate the TechServices in the database
        List<TechServices> techServicesList = techServicesRepository.findAll();
        assertThat(techServicesList).hasSize(databaseSizeBeforeUpdate);
        TechServices testTechServices = techServicesList.get(techServicesList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTechServices() throws Exception {
        int databaseSizeBeforeUpdate = techServicesRepository.findAll().size();

        // Create the TechServices
        TechServicesDTO techServicesDTO = techServicesMapper.toDto(techServices);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechServicesMockMvc.perform(put("/api/tech-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techServicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechServices in the database
        List<TechServices> techServicesList = techServicesRepository.findAll();
        assertThat(techServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTechServices() throws Exception {
        // Initialize the database
        techServicesRepository.saveAndFlush(techServices);

        int databaseSizeBeforeDelete = techServicesRepository.findAll().size();

        // Delete the techServices
        restTechServicesMockMvc.perform(delete("/api/tech-services/{id}", techServices.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TechServices> techServicesList = techServicesRepository.findAll();
        assertThat(techServicesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechServices.class);
        TechServices techServices1 = new TechServices();
        techServices1.setId(1L);
        TechServices techServices2 = new TechServices();
        techServices2.setId(techServices1.getId());
        assertThat(techServices1).isEqualTo(techServices2);
        techServices2.setId(2L);
        assertThat(techServices1).isNotEqualTo(techServices2);
        techServices1.setId(null);
        assertThat(techServices1).isNotEqualTo(techServices2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechServicesDTO.class);
        TechServicesDTO techServicesDTO1 = new TechServicesDTO();
        techServicesDTO1.setId(1L);
        TechServicesDTO techServicesDTO2 = new TechServicesDTO();
        assertThat(techServicesDTO1).isNotEqualTo(techServicesDTO2);
        techServicesDTO2.setId(techServicesDTO1.getId());
        assertThat(techServicesDTO1).isEqualTo(techServicesDTO2);
        techServicesDTO2.setId(2L);
        assertThat(techServicesDTO1).isNotEqualTo(techServicesDTO2);
        techServicesDTO1.setId(null);
        assertThat(techServicesDTO1).isNotEqualTo(techServicesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(techServicesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(techServicesMapper.fromId(null)).isNull();
    }
}
