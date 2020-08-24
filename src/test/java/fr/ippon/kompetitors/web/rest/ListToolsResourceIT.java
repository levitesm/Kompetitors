package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListTools;
import fr.ippon.kompetitors.repository.ListToolsRepository;
import fr.ippon.kompetitors.service.ListToolsService;
import fr.ippon.kompetitors.service.dto.ListToolsDTO;
import fr.ippon.kompetitors.service.mapper.ListToolsMapper;
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
 * Integration tests for the {@link ListToolsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListToolsResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListToolsRepository listToolsRepository;

    @Autowired
    private ListToolsMapper listToolsMapper;

    @Autowired
    private ListToolsService listToolsService;

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

    private MockMvc restListToolsMockMvc;

    private ListTools listTools;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListToolsResource listToolsResource = new ListToolsResource(listToolsService);
        this.restListToolsMockMvc = MockMvcBuilders.standaloneSetup(listToolsResource)
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
    public static ListTools createEntity(EntityManager em) {
        ListTools listTools = new ListTools()
            .value(DEFAULT_VALUE);
        return listTools;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListTools createUpdatedEntity(EntityManager em) {
        ListTools listTools = new ListTools()
            .value(UPDATED_VALUE);
        return listTools;
    }

    @BeforeEach
    public void initTest() {
        listTools = createEntity(em);
    }

    @Test
    @Transactional
    public void createListTools() throws Exception {
        int databaseSizeBeforeCreate = listToolsRepository.findAll().size();

        // Create the ListTools
        ListToolsDTO listToolsDTO = listToolsMapper.toDto(listTools);
        restListToolsMockMvc.perform(post("/api/list-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listToolsDTO)))
            .andExpect(status().isCreated());

        // Validate the ListTools in the database
        List<ListTools> listToolsList = listToolsRepository.findAll();
        assertThat(listToolsList).hasSize(databaseSizeBeforeCreate + 1);
        ListTools testListTools = listToolsList.get(listToolsList.size() - 1);
        assertThat(testListTools.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListToolsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listToolsRepository.findAll().size();

        // Create the ListTools with an existing ID
        listTools.setId(1L);
        ListToolsDTO listToolsDTO = listToolsMapper.toDto(listTools);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListToolsMockMvc.perform(post("/api/list-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listToolsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListTools in the database
        List<ListTools> listToolsList = listToolsRepository.findAll();
        assertThat(listToolsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listToolsRepository.findAll().size();
        // set the field null
        listTools.setValue(null);

        // Create the ListTools, which fails.
        ListToolsDTO listToolsDTO = listToolsMapper.toDto(listTools);

        restListToolsMockMvc.perform(post("/api/list-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listToolsDTO)))
            .andExpect(status().isBadRequest());

        List<ListTools> listToolsList = listToolsRepository.findAll();
        assertThat(listToolsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListTools() throws Exception {
        // Initialize the database
        listToolsRepository.saveAndFlush(listTools);

        // Get all the listToolsList
        restListToolsMockMvc.perform(get("/api/list-tools?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listTools.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListTools() throws Exception {
        // Initialize the database
        listToolsRepository.saveAndFlush(listTools);

        // Get the listTools
        restListToolsMockMvc.perform(get("/api/list-tools/{id}", listTools.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listTools.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListTools() throws Exception {
        // Get the listTools
        restListToolsMockMvc.perform(get("/api/list-tools/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListTools() throws Exception {
        // Initialize the database
        listToolsRepository.saveAndFlush(listTools);

        int databaseSizeBeforeUpdate = listToolsRepository.findAll().size();

        // Update the listTools
        ListTools updatedListTools = listToolsRepository.findById(listTools.getId()).get();
        // Disconnect from session so that the updates on updatedListTools are not directly saved in db
        em.detach(updatedListTools);
        updatedListTools
            .value(UPDATED_VALUE);
        ListToolsDTO listToolsDTO = listToolsMapper.toDto(updatedListTools);

        restListToolsMockMvc.perform(put("/api/list-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listToolsDTO)))
            .andExpect(status().isOk());

        // Validate the ListTools in the database
        List<ListTools> listToolsList = listToolsRepository.findAll();
        assertThat(listToolsList).hasSize(databaseSizeBeforeUpdate);
        ListTools testListTools = listToolsList.get(listToolsList.size() - 1);
        assertThat(testListTools.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListTools() throws Exception {
        int databaseSizeBeforeUpdate = listToolsRepository.findAll().size();

        // Create the ListTools
        ListToolsDTO listToolsDTO = listToolsMapper.toDto(listTools);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListToolsMockMvc.perform(put("/api/list-tools")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listToolsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListTools in the database
        List<ListTools> listToolsList = listToolsRepository.findAll();
        assertThat(listToolsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListTools() throws Exception {
        // Initialize the database
        listToolsRepository.saveAndFlush(listTools);

        int databaseSizeBeforeDelete = listToolsRepository.findAll().size();

        // Delete the listTools
        restListToolsMockMvc.perform(delete("/api/list-tools/{id}", listTools.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListTools> listToolsList = listToolsRepository.findAll();
        assertThat(listToolsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListTools.class);
        ListTools listTools1 = new ListTools();
        listTools1.setId(1L);
        ListTools listTools2 = new ListTools();
        listTools2.setId(listTools1.getId());
        assertThat(listTools1).isEqualTo(listTools2);
        listTools2.setId(2L);
        assertThat(listTools1).isNotEqualTo(listTools2);
        listTools1.setId(null);
        assertThat(listTools1).isNotEqualTo(listTools2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListToolsDTO.class);
        ListToolsDTO listToolsDTO1 = new ListToolsDTO();
        listToolsDTO1.setId(1L);
        ListToolsDTO listToolsDTO2 = new ListToolsDTO();
        assertThat(listToolsDTO1).isNotEqualTo(listToolsDTO2);
        listToolsDTO2.setId(listToolsDTO1.getId());
        assertThat(listToolsDTO1).isEqualTo(listToolsDTO2);
        listToolsDTO2.setId(2L);
        assertThat(listToolsDTO1).isNotEqualTo(listToolsDTO2);
        listToolsDTO1.setId(null);
        assertThat(listToolsDTO1).isNotEqualTo(listToolsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(listToolsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(listToolsMapper.fromId(null)).isNull();
    }
}
