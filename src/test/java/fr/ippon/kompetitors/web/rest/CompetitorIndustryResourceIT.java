package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.CompetitorIndustry;
import fr.ippon.kompetitors.domain.Competitors;
import fr.ippon.kompetitors.domain.ListIndustries;
import fr.ippon.kompetitors.repository.CompetitorIndustryRepository;
import fr.ippon.kompetitors.service.CompetitorIndustryService;
import fr.ippon.kompetitors.service.dto.CompetitorIndustryDTO;
import fr.ippon.kompetitors.service.mapper.CompetitorIndustryMapper;
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
 * Integration tests for the {@link CompetitorIndustryResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class CompetitorIndustryResourceIT {

    @Autowired
    private CompetitorIndustryRepository competitorIndustryRepository;

    @Autowired
    private CompetitorIndustryMapper competitorIndustryMapper;

    @Autowired
    private CompetitorIndustryService competitorIndustryService;

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

    private MockMvc restCompetitorIndustryMockMvc;

    private CompetitorIndustry competitorIndustry;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CompetitorIndustryResource competitorIndustryResource = new CompetitorIndustryResource(competitorIndustryService);
        this.restCompetitorIndustryMockMvc = MockMvcBuilders.standaloneSetup(competitorIndustryResource)
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
    public static CompetitorIndustry createEntity(EntityManager em) {
        CompetitorIndustry competitorIndustry = new CompetitorIndustry();
        // Add required entity
        Competitors competitors;
        if (TestUtil.findAll(em, Competitors.class).isEmpty()) {
            competitors = CompetitorsResourceIT.createEntity(em);
            em.persist(competitors);
            em.flush();
        } else {
            competitors = TestUtil.findAll(em, Competitors.class).get(0);
        }
        competitorIndustry.setCompetitor(competitors);
        // Add required entity
        ListIndustries listIndustries;
        if (TestUtil.findAll(em, ListIndustries.class).isEmpty()) {
            listIndustries = ListIndustriesResourceIT.createEntity(em);
            em.persist(listIndustries);
            em.flush();
        } else {
            listIndustries = TestUtil.findAll(em, ListIndustries.class).get(0);
        }
        competitorIndustry.setIndustry(listIndustries);
        return competitorIndustry;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CompetitorIndustry createUpdatedEntity(EntityManager em) {
        CompetitorIndustry competitorIndustry = new CompetitorIndustry();
        // Add required entity
        Competitors competitors;
        if (TestUtil.findAll(em, Competitors.class).isEmpty()) {
            competitors = CompetitorsResourceIT.createUpdatedEntity(em);
            em.persist(competitors);
            em.flush();
        } else {
            competitors = TestUtil.findAll(em, Competitors.class).get(0);
        }
        competitorIndustry.setCompetitor(competitors);
        // Add required entity
        ListIndustries listIndustries;
        if (TestUtil.findAll(em, ListIndustries.class).isEmpty()) {
            listIndustries = ListIndustriesResourceIT.createUpdatedEntity(em);
            em.persist(listIndustries);
            em.flush();
        } else {
            listIndustries = TestUtil.findAll(em, ListIndustries.class).get(0);
        }
        competitorIndustry.setIndustry(listIndustries);
        return competitorIndustry;
    }

    @BeforeEach
    public void initTest() {
        competitorIndustry = createEntity(em);
    }

    @Test
    @Transactional
    public void createCompetitorIndustry() throws Exception {
        int databaseSizeBeforeCreate = competitorIndustryRepository.findAll().size();

        // Create the CompetitorIndustry
        CompetitorIndustryDTO competitorIndustryDTO = competitorIndustryMapper.toDto(competitorIndustry);
        restCompetitorIndustryMockMvc.perform(post("/api/competitor-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitorIndustryDTO)))
            .andExpect(status().isCreated());

        // Validate the CompetitorIndustry in the database
        List<CompetitorIndustry> competitorIndustryList = competitorIndustryRepository.findAll();
        assertThat(competitorIndustryList).hasSize(databaseSizeBeforeCreate + 1);
        CompetitorIndustry testCompetitorIndustry = competitorIndustryList.get(competitorIndustryList.size() - 1);
    }

    @Test
    @Transactional
    public void createCompetitorIndustryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = competitorIndustryRepository.findAll().size();

        // Create the CompetitorIndustry with an existing ID
        competitorIndustry.setId(1L);
        CompetitorIndustryDTO competitorIndustryDTO = competitorIndustryMapper.toDto(competitorIndustry);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCompetitorIndustryMockMvc.perform(post("/api/competitor-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitorIndustryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitorIndustry in the database
        List<CompetitorIndustry> competitorIndustryList = competitorIndustryRepository.findAll();
        assertThat(competitorIndustryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCompetitorIndustries() throws Exception {
        // Initialize the database
        competitorIndustryRepository.saveAndFlush(competitorIndustry);

        // Get all the competitorIndustryList
        restCompetitorIndustryMockMvc.perform(get("/api/competitor-industries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(competitorIndustry.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getCompetitorIndustry() throws Exception {
        // Initialize the database
        competitorIndustryRepository.saveAndFlush(competitorIndustry);

        // Get the competitorIndustry
        restCompetitorIndustryMockMvc.perform(get("/api/competitor-industries/{id}", competitorIndustry.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(competitorIndustry.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCompetitorIndustry() throws Exception {
        // Get the competitorIndustry
        restCompetitorIndustryMockMvc.perform(get("/api/competitor-industries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCompetitorIndustry() throws Exception {
        // Initialize the database
        competitorIndustryRepository.saveAndFlush(competitorIndustry);

        int databaseSizeBeforeUpdate = competitorIndustryRepository.findAll().size();

        // Update the competitorIndustry
        CompetitorIndustry updatedCompetitorIndustry = competitorIndustryRepository.findById(competitorIndustry.getId()).get();
        // Disconnect from session so that the updates on updatedCompetitorIndustry are not directly saved in db
        em.detach(updatedCompetitorIndustry);
        CompetitorIndustryDTO competitorIndustryDTO = competitorIndustryMapper.toDto(updatedCompetitorIndustry);

        restCompetitorIndustryMockMvc.perform(put("/api/competitor-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitorIndustryDTO)))
            .andExpect(status().isOk());

        // Validate the CompetitorIndustry in the database
        List<CompetitorIndustry> competitorIndustryList = competitorIndustryRepository.findAll();
        assertThat(competitorIndustryList).hasSize(databaseSizeBeforeUpdate);
        CompetitorIndustry testCompetitorIndustry = competitorIndustryList.get(competitorIndustryList.size() - 1);
    }

    @Test
    @Transactional
    public void updateNonExistingCompetitorIndustry() throws Exception {
        int databaseSizeBeforeUpdate = competitorIndustryRepository.findAll().size();

        // Create the CompetitorIndustry
        CompetitorIndustryDTO competitorIndustryDTO = competitorIndustryMapper.toDto(competitorIndustry);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCompetitorIndustryMockMvc.perform(put("/api/competitor-industries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(competitorIndustryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CompetitorIndustry in the database
        List<CompetitorIndustry> competitorIndustryList = competitorIndustryRepository.findAll();
        assertThat(competitorIndustryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCompetitorIndustry() throws Exception {
        // Initialize the database
        competitorIndustryRepository.saveAndFlush(competitorIndustry);

        int databaseSizeBeforeDelete = competitorIndustryRepository.findAll().size();

        // Delete the competitorIndustry
        restCompetitorIndustryMockMvc.perform(delete("/api/competitor-industries/{id}", competitorIndustry.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<CompetitorIndustry> competitorIndustryList = competitorIndustryRepository.findAll();
        assertThat(competitorIndustryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitorIndustry.class);
        CompetitorIndustry competitorIndustry1 = new CompetitorIndustry();
        competitorIndustry1.setId(1L);
        CompetitorIndustry competitorIndustry2 = new CompetitorIndustry();
        competitorIndustry2.setId(competitorIndustry1.getId());
        assertThat(competitorIndustry1).isEqualTo(competitorIndustry2);
        competitorIndustry2.setId(2L);
        assertThat(competitorIndustry1).isNotEqualTo(competitorIndustry2);
        competitorIndustry1.setId(null);
        assertThat(competitorIndustry1).isNotEqualTo(competitorIndustry2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CompetitorIndustryDTO.class);
        CompetitorIndustryDTO competitorIndustryDTO1 = new CompetitorIndustryDTO();
        competitorIndustryDTO1.setId(1L);
        CompetitorIndustryDTO competitorIndustryDTO2 = new CompetitorIndustryDTO();
        assertThat(competitorIndustryDTO1).isNotEqualTo(competitorIndustryDTO2);
        competitorIndustryDTO2.setId(competitorIndustryDTO1.getId());
        assertThat(competitorIndustryDTO1).isEqualTo(competitorIndustryDTO2);
        competitorIndustryDTO2.setId(2L);
        assertThat(competitorIndustryDTO1).isNotEqualTo(competitorIndustryDTO2);
        competitorIndustryDTO1.setId(null);
        assertThat(competitorIndustryDTO1).isNotEqualTo(competitorIndustryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(competitorIndustryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(competitorIndustryMapper.fromId(null)).isNull();
    }
}
