package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListCompetancies;
import fr.ippon.kompetitors.repository.ListCompetanciesRepository;
import fr.ippon.kompetitors.service.ListCompetanciesService;
import fr.ippon.kompetitors.service.dto.ListCompetanciesDTO;
import fr.ippon.kompetitors.service.mapper.ListCompetanciesMapper;
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
 * Integration tests for the {@link ListCompetanciesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListCompetanciesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListCompetanciesRepository listCompetanciesRepository;

    @Autowired
    private ListCompetanciesMapper listCompetanciesMapper;

    @Autowired
    private ListCompetanciesService listCompetanciesService;

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

    private MockMvc restListCompetanciesMockMvc;

    private ListCompetancies listCompetancies;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListCompetanciesResource listCompetanciesResource = new ListCompetanciesResource(listCompetanciesService);
        this.restListCompetanciesMockMvc = MockMvcBuilders.standaloneSetup(listCompetanciesResource)
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
    public static ListCompetancies createEntity(EntityManager em) {
        ListCompetancies listCompetancies = new ListCompetancies()
            .value(DEFAULT_VALUE);
        return listCompetancies;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListCompetancies createUpdatedEntity(EntityManager em) {
        ListCompetancies listCompetancies = new ListCompetancies()
            .value(UPDATED_VALUE);
        return listCompetancies;
    }

    @BeforeEach
    public void initTest() {
        listCompetancies = createEntity(em);
    }

    @Test
    @Transactional
    public void createListCompetancies() throws Exception {
        int databaseSizeBeforeCreate = listCompetanciesRepository.findAll().size();

        // Create the ListCompetancies
        ListCompetanciesDTO listCompetanciesDTO = listCompetanciesMapper.toDto(listCompetancies);
        restListCompetanciesMockMvc.perform(post("/api/list-competancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCompetanciesDTO)))
            .andExpect(status().isCreated());

        // Validate the ListCompetancies in the database
        List<ListCompetancies> listCompetanciesList = listCompetanciesRepository.findAll();
        assertThat(listCompetanciesList).hasSize(databaseSizeBeforeCreate + 1);
        ListCompetancies testListCompetancies = listCompetanciesList.get(listCompetanciesList.size() - 1);
        assertThat(testListCompetancies.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListCompetanciesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listCompetanciesRepository.findAll().size();

        // Create the ListCompetancies with an existing ID
        listCompetancies.setId(1L);
        ListCompetanciesDTO listCompetanciesDTO = listCompetanciesMapper.toDto(listCompetancies);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListCompetanciesMockMvc.perform(post("/api/list-competancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCompetanciesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListCompetancies in the database
        List<ListCompetancies> listCompetanciesList = listCompetanciesRepository.findAll();
        assertThat(listCompetanciesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listCompetanciesRepository.findAll().size();
        // set the field null
        listCompetancies.setValue(null);

        // Create the ListCompetancies, which fails.
        ListCompetanciesDTO listCompetanciesDTO = listCompetanciesMapper.toDto(listCompetancies);

        restListCompetanciesMockMvc.perform(post("/api/list-competancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCompetanciesDTO)))
            .andExpect(status().isBadRequest());

        List<ListCompetancies> listCompetanciesList = listCompetanciesRepository.findAll();
        assertThat(listCompetanciesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListCompetancies() throws Exception {
        // Initialize the database
        listCompetanciesRepository.saveAndFlush(listCompetancies);

        // Get all the listCompetanciesList
        restListCompetanciesMockMvc.perform(get("/api/list-competancies?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listCompetancies.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListCompetancies() throws Exception {
        // Initialize the database
        listCompetanciesRepository.saveAndFlush(listCompetancies);

        // Get the listCompetancies
        restListCompetanciesMockMvc.perform(get("/api/list-competancies/{id}", listCompetancies.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listCompetancies.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListCompetancies() throws Exception {
        // Get the listCompetancies
        restListCompetanciesMockMvc.perform(get("/api/list-competancies/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListCompetancies() throws Exception {
        // Initialize the database
        listCompetanciesRepository.saveAndFlush(listCompetancies);

        int databaseSizeBeforeUpdate = listCompetanciesRepository.findAll().size();

        // Update the listCompetancies
        ListCompetancies updatedListCompetancies = listCompetanciesRepository.findById(listCompetancies.getId()).get();
        // Disconnect from session so that the updates on updatedListCompetancies are not directly saved in db
        em.detach(updatedListCompetancies);
        updatedListCompetancies
            .value(UPDATED_VALUE);
        ListCompetanciesDTO listCompetanciesDTO = listCompetanciesMapper.toDto(updatedListCompetancies);

        restListCompetanciesMockMvc.perform(put("/api/list-competancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCompetanciesDTO)))
            .andExpect(status().isOk());

        // Validate the ListCompetancies in the database
        List<ListCompetancies> listCompetanciesList = listCompetanciesRepository.findAll();
        assertThat(listCompetanciesList).hasSize(databaseSizeBeforeUpdate);
        ListCompetancies testListCompetancies = listCompetanciesList.get(listCompetanciesList.size() - 1);
        assertThat(testListCompetancies.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListCompetancies() throws Exception {
        int databaseSizeBeforeUpdate = listCompetanciesRepository.findAll().size();

        // Create the ListCompetancies
        ListCompetanciesDTO listCompetanciesDTO = listCompetanciesMapper.toDto(listCompetancies);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListCompetanciesMockMvc.perform(put("/api/list-competancies")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listCompetanciesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListCompetancies in the database
        List<ListCompetancies> listCompetanciesList = listCompetanciesRepository.findAll();
        assertThat(listCompetanciesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListCompetancies() throws Exception {
        // Initialize the database
        listCompetanciesRepository.saveAndFlush(listCompetancies);

        int databaseSizeBeforeDelete = listCompetanciesRepository.findAll().size();

        // Delete the listCompetancies
        restListCompetanciesMockMvc.perform(delete("/api/list-competancies/{id}", listCompetancies.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListCompetancies> listCompetanciesList = listCompetanciesRepository.findAll();
        assertThat(listCompetanciesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListCompetancies.class);
        ListCompetancies listCompetancies1 = new ListCompetancies();
        listCompetancies1.setId(1L);
        ListCompetancies listCompetancies2 = new ListCompetancies();
        listCompetancies2.setId(listCompetancies1.getId());
        assertThat(listCompetancies1).isEqualTo(listCompetancies2);
        listCompetancies2.setId(2L);
        assertThat(listCompetancies1).isNotEqualTo(listCompetancies2);
        listCompetancies1.setId(null);
        assertThat(listCompetancies1).isNotEqualTo(listCompetancies2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListCompetanciesDTO.class);
        ListCompetanciesDTO listCompetanciesDTO1 = new ListCompetanciesDTO();
        listCompetanciesDTO1.setId(1L);
        ListCompetanciesDTO listCompetanciesDTO2 = new ListCompetanciesDTO();
        assertThat(listCompetanciesDTO1).isNotEqualTo(listCompetanciesDTO2);
        listCompetanciesDTO2.setId(listCompetanciesDTO1.getId());
        assertThat(listCompetanciesDTO1).isEqualTo(listCompetanciesDTO2);
        listCompetanciesDTO2.setId(2L);
        assertThat(listCompetanciesDTO1).isNotEqualTo(listCompetanciesDTO2);
        listCompetanciesDTO1.setId(null);
        assertThat(listCompetanciesDTO1).isNotEqualTo(listCompetanciesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(listCompetanciesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(listCompetanciesMapper.fromId(null)).isNull();
    }
}
