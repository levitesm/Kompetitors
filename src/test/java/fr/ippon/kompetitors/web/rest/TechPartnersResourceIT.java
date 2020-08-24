package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.TechPartners;
import fr.ippon.kompetitors.repository.TechPartnersRepository;
import fr.ippon.kompetitors.service.TechPartnersService;
import fr.ippon.kompetitors.service.dto.TechPartnersDTO;
import fr.ippon.kompetitors.service.mapper.TechPartnersMapper;
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
 * Integration tests for the {@link TechPartnersResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class TechPartnersResourceIT {

    @Autowired
    private TechPartnersRepository techPartnersRepository;

    @Autowired
    private TechPartnersMapper techPartnersMapper;

    @Autowired
    private TechPartnersService techPartnersService;

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

    private MockMvc restTechPartnersMockMvc;

    private TechPartners techPartners;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TechPartnersResource techPartnersResource = new TechPartnersResource(techPartnersService);
        this.restTechPartnersMockMvc = MockMvcBuilders.standaloneSetup(techPartnersResource)
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
    public static TechPartners createEntity(EntityManager em) {
        TechPartners techPartners = new TechPartners();
        return techPartners;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechPartners createUpdatedEntity(EntityManager em) {
        TechPartners techPartners = new TechPartners();
        return techPartners;
    }

    @BeforeEach
    public void initTest() {
        techPartners = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechPartners() throws Exception {
        int databaseSizeBeforeCreate = techPartnersRepository.findAll().size();

        // Create the TechPartners
        TechPartnersDTO techPartnersDTO = techPartnersMapper.toDto(techPartners);
        restTechPartnersMockMvc.perform(post("/api/tech-partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techPartnersDTO)))
            .andExpect(status().isCreated());

        // Validate the TechPartners in the database
        List<TechPartners> techPartnersList = techPartnersRepository.findAll();
        assertThat(techPartnersList).hasSize(databaseSizeBeforeCreate + 1);
        TechPartners testTechPartners = techPartnersList.get(techPartnersList.size() - 1);
    }

    @Test
    @Transactional
    public void createTechPartnersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = techPartnersRepository.findAll().size();

        // Create the TechPartners with an existing ID
        techPartners.setId(1L);
        TechPartnersDTO techPartnersDTO = techPartnersMapper.toDto(techPartners);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechPartnersMockMvc.perform(post("/api/tech-partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techPartnersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechPartners in the database
        List<TechPartners> techPartnersList = techPartnersRepository.findAll();
        assertThat(techPartnersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTechPartners() throws Exception {
        // Initialize the database
        techPartnersRepository.saveAndFlush(techPartners);

        // Get all the techPartnersList
        restTechPartnersMockMvc.perform(get("/api/tech-partners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(techPartners.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getTechPartners() throws Exception {
        // Initialize the database
        techPartnersRepository.saveAndFlush(techPartners);

        // Get the techPartners
        restTechPartnersMockMvc.perform(get("/api/tech-partners/{id}", techPartners.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(techPartners.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTechPartners() throws Exception {
        // Get the techPartners
        restTechPartnersMockMvc.perform(get("/api/tech-partners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechPartners() throws Exception {
        // Initialize the database
        techPartnersRepository.saveAndFlush(techPartners);

        int databaseSizeBeforeUpdate = techPartnersRepository.findAll().size();

        // Update the techPartners
        TechPartners updatedTechPartners = techPartnersRepository.findById(techPartners.getId()).get();
        // Disconnect from session so that the updates on updatedTechPartners are not directly saved in db
        em.detach(updatedTechPartners);
        TechPartnersDTO techPartnersDTO = techPartnersMapper.toDto(updatedTechPartners);

        restTechPartnersMockMvc.perform(put("/api/tech-partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techPartnersDTO)))
            .andExpect(status().isOk());

        // Validate the TechPartners in the database
        List<TechPartners> techPartnersList = techPartnersRepository.findAll();
        assertThat(techPartnersList).hasSize(databaseSizeBeforeUpdate);
        TechPartners testTechPartners = techPartnersList.get(techPartnersList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingTechPartners() throws Exception {
        int databaseSizeBeforeUpdate = techPartnersRepository.findAll().size();

        // Create the TechPartners
        TechPartnersDTO techPartnersDTO = techPartnersMapper.toDto(techPartners);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechPartnersMockMvc.perform(put("/api/tech-partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techPartnersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechPartners in the database
        List<TechPartners> techPartnersList = techPartnersRepository.findAll();
        assertThat(techPartnersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTechPartners() throws Exception {
        // Initialize the database
        techPartnersRepository.saveAndFlush(techPartners);

        int databaseSizeBeforeDelete = techPartnersRepository.findAll().size();

        // Delete the techPartners
        restTechPartnersMockMvc.perform(delete("/api/tech-partners/{id}", techPartners.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TechPartners> techPartnersList = techPartnersRepository.findAll();
        assertThat(techPartnersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechPartners.class);
        TechPartners techPartners1 = new TechPartners();
        techPartners1.setId(1L);
        TechPartners techPartners2 = new TechPartners();
        techPartners2.setId(techPartners1.getId());
        assertThat(techPartners1).isEqualTo(techPartners2);
        techPartners2.setId(2L);
        assertThat(techPartners1).isNotEqualTo(techPartners2);
        techPartners1.setId(null);
        assertThat(techPartners1).isNotEqualTo(techPartners2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechPartnersDTO.class);
        TechPartnersDTO techPartnersDTO1 = new TechPartnersDTO();
        techPartnersDTO1.setId(1L);
        TechPartnersDTO techPartnersDTO2 = new TechPartnersDTO();
        assertThat(techPartnersDTO1).isNotEqualTo(techPartnersDTO2);
        techPartnersDTO2.setId(techPartnersDTO1.getId());
        assertThat(techPartnersDTO1).isEqualTo(techPartnersDTO2);
        techPartnersDTO2.setId(2L);
        assertThat(techPartnersDTO1).isNotEqualTo(techPartnersDTO2);
        techPartnersDTO1.setId(null);
        assertThat(techPartnersDTO1).isNotEqualTo(techPartnersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(techPartnersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(techPartnersMapper.fromId(null)).isNull();
    }
}
