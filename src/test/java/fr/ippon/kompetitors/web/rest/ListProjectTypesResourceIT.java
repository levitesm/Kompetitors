package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListProjectTypes;
import fr.ippon.kompetitors.repository.ListProjectTypesRepository;
import fr.ippon.kompetitors.service.ListProjectTypesService;
import fr.ippon.kompetitors.service.dto.ListProjectTypesDTO;
import fr.ippon.kompetitors.service.mapper.ListProjectTypesMapper;
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
 * Integration tests for the {@link ListProjectTypesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListProjectTypesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListProjectTypesRepository listProjectTypesRepository;

    @Autowired
    private ListProjectTypesMapper listProjectTypesMapper;

    @Autowired
    private ListProjectTypesService listProjectTypesService;

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

    private MockMvc restListProjectTypesMockMvc;

    private ListProjectTypes listProjectTypes;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListProjectTypesResource listProjectTypesResource = new ListProjectTypesResource(listProjectTypesService);
        this.restListProjectTypesMockMvc = MockMvcBuilders.standaloneSetup(listProjectTypesResource)
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
    public static ListProjectTypes createEntity(EntityManager em) {
        ListProjectTypes listProjectTypes = new ListProjectTypes()
            .value(DEFAULT_VALUE);
        return listProjectTypes;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListProjectTypes createUpdatedEntity(EntityManager em) {
        ListProjectTypes listProjectTypes = new ListProjectTypes()
            .value(UPDATED_VALUE);
        return listProjectTypes;
    }

    @BeforeEach
    public void initTest() {
        listProjectTypes = createEntity(em);
    }

    @Test
    @Transactional
    public void createListProjectTypes() throws Exception {
        int databaseSizeBeforeCreate = listProjectTypesRepository.findAll().size();

        // Create the ListProjectTypes
        ListProjectTypesDTO listProjectTypesDTO = listProjectTypesMapper.toDto(listProjectTypes);
        restListProjectTypesMockMvc.perform(post("/api/list-project-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listProjectTypesDTO)))
            .andExpect(status().isCreated());

        // Validate the ListProjectTypes in the database
        List<ListProjectTypes> listProjectTypesList = listProjectTypesRepository.findAll();
        assertThat(listProjectTypesList).hasSize(databaseSizeBeforeCreate + 1);
        ListProjectTypes testListProjectTypes = listProjectTypesList.get(listProjectTypesList.size() - 1);
        assertThat(testListProjectTypes.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListProjectTypesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listProjectTypesRepository.findAll().size();

        // Create the ListProjectTypes with an existing ID
        listProjectTypes.setId(1L);
        ListProjectTypesDTO listProjectTypesDTO = listProjectTypesMapper.toDto(listProjectTypes);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListProjectTypesMockMvc.perform(post("/api/list-project-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listProjectTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListProjectTypes in the database
        List<ListProjectTypes> listProjectTypesList = listProjectTypesRepository.findAll();
        assertThat(listProjectTypesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listProjectTypesRepository.findAll().size();
        // set the field null
        listProjectTypes.setValue(null);

        // Create the ListProjectTypes, which fails.
        ListProjectTypesDTO listProjectTypesDTO = listProjectTypesMapper.toDto(listProjectTypes);

        restListProjectTypesMockMvc.perform(post("/api/list-project-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listProjectTypesDTO)))
            .andExpect(status().isBadRequest());

        List<ListProjectTypes> listProjectTypesList = listProjectTypesRepository.findAll();
        assertThat(listProjectTypesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListProjectTypes() throws Exception {
        // Initialize the database
        listProjectTypesRepository.saveAndFlush(listProjectTypes);

        // Get all the listProjectTypesList
        restListProjectTypesMockMvc.perform(get("/api/list-project-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listProjectTypes.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListProjectTypes() throws Exception {
        // Initialize the database
        listProjectTypesRepository.saveAndFlush(listProjectTypes);

        // Get the listProjectTypes
        restListProjectTypesMockMvc.perform(get("/api/list-project-types/{id}", listProjectTypes.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listProjectTypes.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListProjectTypes() throws Exception {
        // Get the listProjectTypes
        restListProjectTypesMockMvc.perform(get("/api/list-project-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListProjectTypes() throws Exception {
        // Initialize the database
        listProjectTypesRepository.saveAndFlush(listProjectTypes);

        int databaseSizeBeforeUpdate = listProjectTypesRepository.findAll().size();

        // Update the listProjectTypes
        ListProjectTypes updatedListProjectTypes = listProjectTypesRepository.findById(listProjectTypes.getId()).get();
        // Disconnect from session so that the updates on updatedListProjectTypes are not directly saved in db
        em.detach(updatedListProjectTypes);
        updatedListProjectTypes
            .value(UPDATED_VALUE);
        ListProjectTypesDTO listProjectTypesDTO = listProjectTypesMapper.toDto(updatedListProjectTypes);

        restListProjectTypesMockMvc.perform(put("/api/list-project-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listProjectTypesDTO)))
            .andExpect(status().isOk());

        // Validate the ListProjectTypes in the database
        List<ListProjectTypes> listProjectTypesList = listProjectTypesRepository.findAll();
        assertThat(listProjectTypesList).hasSize(databaseSizeBeforeUpdate);
        ListProjectTypes testListProjectTypes = listProjectTypesList.get(listProjectTypesList.size() - 1);
        assertThat(testListProjectTypes.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListProjectTypes() throws Exception {
        int databaseSizeBeforeUpdate = listProjectTypesRepository.findAll().size();

        // Create the ListProjectTypes
        ListProjectTypesDTO listProjectTypesDTO = listProjectTypesMapper.toDto(listProjectTypes);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListProjectTypesMockMvc.perform(put("/api/list-project-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listProjectTypesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListProjectTypes in the database
        List<ListProjectTypes> listProjectTypesList = listProjectTypesRepository.findAll();
        assertThat(listProjectTypesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListProjectTypes() throws Exception {
        // Initialize the database
        listProjectTypesRepository.saveAndFlush(listProjectTypes);

        int databaseSizeBeforeDelete = listProjectTypesRepository.findAll().size();

        // Delete the listProjectTypes
        restListProjectTypesMockMvc.perform(delete("/api/list-project-types/{id}", listProjectTypes.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListProjectTypes> listProjectTypesList = listProjectTypesRepository.findAll();
        assertThat(listProjectTypesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListProjectTypes.class);
        ListProjectTypes listProjectTypes1 = new ListProjectTypes();
        listProjectTypes1.setId(1L);
        ListProjectTypes listProjectTypes2 = new ListProjectTypes();
        listProjectTypes2.setId(listProjectTypes1.getId());
        assertThat(listProjectTypes1).isEqualTo(listProjectTypes2);
        listProjectTypes2.setId(2L);
        assertThat(listProjectTypes1).isNotEqualTo(listProjectTypes2);
        listProjectTypes1.setId(null);
        assertThat(listProjectTypes1).isNotEqualTo(listProjectTypes2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListProjectTypesDTO.class);
        ListProjectTypesDTO listProjectTypesDTO1 = new ListProjectTypesDTO();
        listProjectTypesDTO1.setId(1L);
        ListProjectTypesDTO listProjectTypesDTO2 = new ListProjectTypesDTO();
        assertThat(listProjectTypesDTO1).isNotEqualTo(listProjectTypesDTO2);
        listProjectTypesDTO2.setId(listProjectTypesDTO1.getId());
        assertThat(listProjectTypesDTO1).isEqualTo(listProjectTypesDTO2);
        listProjectTypesDTO2.setId(2L);
        assertThat(listProjectTypesDTO1).isNotEqualTo(listProjectTypesDTO2);
        listProjectTypesDTO1.setId(null);
        assertThat(listProjectTypesDTO1).isNotEqualTo(listProjectTypesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(listProjectTypesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(listProjectTypesMapper.fromId(null)).isNull();
    }
}
