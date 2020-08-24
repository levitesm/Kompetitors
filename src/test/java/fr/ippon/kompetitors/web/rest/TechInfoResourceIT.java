package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.TechInfo;
import fr.ippon.kompetitors.repository.TechInfoRepository;
import fr.ippon.kompetitors.service.TechInfoService;
import fr.ippon.kompetitors.service.dto.TechInfoDTO;
import fr.ippon.kompetitors.service.mapper.TechInfoMapper;
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
 * Integration tests for the {@link TechInfoResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class TechInfoResourceIT {

    private static final Integer DEFAULT_TECH_SPECIALISTS_NUMBER = 1;
    private static final Integer UPDATED_TECH_SPECIALISTS_NUMBER = 2;

    @Autowired
    private TechInfoRepository techInfoRepository;

    @Autowired
    private TechInfoMapper techInfoMapper;

    @Autowired
    private TechInfoService techInfoService;

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

    private MockMvc restTechInfoMockMvc;

    private TechInfo techInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TechInfoResource techInfoResource = new TechInfoResource(techInfoService);
        this.restTechInfoMockMvc = MockMvcBuilders.standaloneSetup(techInfoResource)
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
    public static TechInfo createEntity(EntityManager em) {
        TechInfo techInfo = new TechInfo()
            .techSpecialistsNumber(DEFAULT_TECH_SPECIALISTS_NUMBER);
        return techInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TechInfo createUpdatedEntity(EntityManager em) {
        TechInfo techInfo = new TechInfo()
            .techSpecialistsNumber(UPDATED_TECH_SPECIALISTS_NUMBER);
        return techInfo;
    }

    @BeforeEach
    public void initTest() {
        techInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTechInfo() throws Exception {
        int databaseSizeBeforeCreate = techInfoRepository.findAll().size();

        // Create the TechInfo
        TechInfoDTO techInfoDTO = techInfoMapper.toDto(techInfo);
        restTechInfoMockMvc.perform(post("/api/tech-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the TechInfo in the database
        List<TechInfo> techInfoList = techInfoRepository.findAll();
        assertThat(techInfoList).hasSize(databaseSizeBeforeCreate + 1);
        TechInfo testTechInfo = techInfoList.get(techInfoList.size() - 1);
        assertThat(testTechInfo.getTechSpecialistsNumber()).isEqualTo(DEFAULT_TECH_SPECIALISTS_NUMBER);
    }

    @Test
    @Transactional
    public void createTechInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = techInfoRepository.findAll().size();

        // Create the TechInfo with an existing ID
        techInfo.setId(1L);
        TechInfoDTO techInfoDTO = techInfoMapper.toDto(techInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTechInfoMockMvc.perform(post("/api/tech-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechInfo in the database
        List<TechInfo> techInfoList = techInfoRepository.findAll();
        assertThat(techInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTechInfos() throws Exception {
        // Initialize the database
        techInfoRepository.saveAndFlush(techInfo);

        // Get all the techInfoList
        restTechInfoMockMvc.perform(get("/api/tech-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(techInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].techSpecialistsNumber").value(hasItem(DEFAULT_TECH_SPECIALISTS_NUMBER)));
    }
    
    @Test
    @Transactional
    public void getTechInfo() throws Exception {
        // Initialize the database
        techInfoRepository.saveAndFlush(techInfo);

        // Get the techInfo
        restTechInfoMockMvc.perform(get("/api/tech-infos/{id}", techInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(techInfo.getId().intValue()))
            .andExpect(jsonPath("$.techSpecialistsNumber").value(DEFAULT_TECH_SPECIALISTS_NUMBER));
    }

    @Test
    @Transactional
    public void getNonExistingTechInfo() throws Exception {
        // Get the techInfo
        restTechInfoMockMvc.perform(get("/api/tech-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTechInfo() throws Exception {
        // Initialize the database
        techInfoRepository.saveAndFlush(techInfo);

        int databaseSizeBeforeUpdate = techInfoRepository.findAll().size();

        // Update the techInfo
        TechInfo updatedTechInfo = techInfoRepository.findById(techInfo.getId()).get();
        // Disconnect from session so that the updates on updatedTechInfo are not directly saved in db
        em.detach(updatedTechInfo);
        updatedTechInfo
            .techSpecialistsNumber(UPDATED_TECH_SPECIALISTS_NUMBER);
        TechInfoDTO techInfoDTO = techInfoMapper.toDto(updatedTechInfo);

        restTechInfoMockMvc.perform(put("/api/tech-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techInfoDTO)))
            .andExpect(status().isOk());

        // Validate the TechInfo in the database
        List<TechInfo> techInfoList = techInfoRepository.findAll();
        assertThat(techInfoList).hasSize(databaseSizeBeforeUpdate);
        TechInfo testTechInfo = techInfoList.get(techInfoList.size() - 1);
        assertThat(testTechInfo.getTechSpecialistsNumber()).isEqualTo(UPDATED_TECH_SPECIALISTS_NUMBER);
    }

    @Test
    @Transactional
    public void updateNonExistingTechInfo() throws Exception {
        int databaseSizeBeforeUpdate = techInfoRepository.findAll().size();

        // Create the TechInfo
        TechInfoDTO techInfoDTO = techInfoMapper.toDto(techInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTechInfoMockMvc.perform(put("/api/tech-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(techInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TechInfo in the database
        List<TechInfo> techInfoList = techInfoRepository.findAll();
        assertThat(techInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTechInfo() throws Exception {
        // Initialize the database
        techInfoRepository.saveAndFlush(techInfo);

        int databaseSizeBeforeDelete = techInfoRepository.findAll().size();

        // Delete the techInfo
        restTechInfoMockMvc.perform(delete("/api/tech-infos/{id}", techInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TechInfo> techInfoList = techInfoRepository.findAll();
        assertThat(techInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechInfo.class);
        TechInfo techInfo1 = new TechInfo();
        techInfo1.setId(1L);
        TechInfo techInfo2 = new TechInfo();
        techInfo2.setId(techInfo1.getId());
        assertThat(techInfo1).isEqualTo(techInfo2);
        techInfo2.setId(2L);
        assertThat(techInfo1).isNotEqualTo(techInfo2);
        techInfo1.setId(null);
        assertThat(techInfo1).isNotEqualTo(techInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TechInfoDTO.class);
        TechInfoDTO techInfoDTO1 = new TechInfoDTO();
        techInfoDTO1.setId(1L);
        TechInfoDTO techInfoDTO2 = new TechInfoDTO();
        assertThat(techInfoDTO1).isNotEqualTo(techInfoDTO2);
        techInfoDTO2.setId(techInfoDTO1.getId());
        assertThat(techInfoDTO1).isEqualTo(techInfoDTO2);
        techInfoDTO2.setId(2L);
        assertThat(techInfoDTO1).isNotEqualTo(techInfoDTO2);
        techInfoDTO1.setId(null);
        assertThat(techInfoDTO1).isNotEqualTo(techInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(techInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(techInfoMapper.fromId(null)).isNull();
    }
}
