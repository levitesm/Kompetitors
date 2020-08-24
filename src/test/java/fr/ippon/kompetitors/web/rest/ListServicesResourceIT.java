package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListServices;
import fr.ippon.kompetitors.repository.ListServicesRepository;
import fr.ippon.kompetitors.service.ListServicesService;
import fr.ippon.kompetitors.service.dto.ListServicesDTO;
import fr.ippon.kompetitors.service.mapper.ListServicesMapper;
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
 * Integration tests for the {@link ListServicesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListServicesResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    @Autowired
    private ListServicesRepository listServicesRepository;

    @Autowired
    private ListServicesMapper listServicesMapper;

    @Autowired
    private ListServicesService listServicesService;

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

    private MockMvc restListServicesMockMvc;

    private ListServices listServices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListServicesResource listServicesResource = new ListServicesResource(listServicesService);
        this.restListServicesMockMvc = MockMvcBuilders.standaloneSetup(listServicesResource)
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
    public static ListServices createEntity(EntityManager em) {
        ListServices listServices = new ListServices()
            .value(DEFAULT_VALUE);
        return listServices;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListServices createUpdatedEntity(EntityManager em) {
        ListServices listServices = new ListServices()
            .value(UPDATED_VALUE);
        return listServices;
    }

    @BeforeEach
    public void initTest() {
        listServices = createEntity(em);
    }

    @Test
    @Transactional
    public void createListServices() throws Exception {
        int databaseSizeBeforeCreate = listServicesRepository.findAll().size();

        // Create the ListServices
        ListServicesDTO listServicesDTO = listServicesMapper.toDto(listServices);
        restListServicesMockMvc.perform(post("/api/list-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listServicesDTO)))
            .andExpect(status().isCreated());

        // Validate the ListServices in the database
        List<ListServices> listServicesList = listServicesRepository.findAll();
        assertThat(listServicesList).hasSize(databaseSizeBeforeCreate + 1);
        ListServices testListServices = listServicesList.get(listServicesList.size() - 1);
        assertThat(testListServices.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createListServicesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listServicesRepository.findAll().size();

        // Create the ListServices with an existing ID
        listServices.setId(1L);
        ListServicesDTO listServicesDTO = listServicesMapper.toDto(listServices);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListServicesMockMvc.perform(post("/api/list-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listServicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListServices in the database
        List<ListServices> listServicesList = listServicesRepository.findAll();
        assertThat(listServicesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listServicesRepository.findAll().size();
        // set the field null
        listServices.setValue(null);

        // Create the ListServices, which fails.
        ListServicesDTO listServicesDTO = listServicesMapper.toDto(listServices);

        restListServicesMockMvc.perform(post("/api/list-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listServicesDTO)))
            .andExpect(status().isBadRequest());

        List<ListServices> listServicesList = listServicesRepository.findAll();
        assertThat(listServicesList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListServices() throws Exception {
        // Initialize the database
        listServicesRepository.saveAndFlush(listServices);

        // Get all the listServicesList
        restListServicesMockMvc.perform(get("/api/list-services?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listServices.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)));
    }
    
    @Test
    @Transactional
    public void getListServices() throws Exception {
        // Initialize the database
        listServicesRepository.saveAndFlush(listServices);

        // Get the listServices
        restListServicesMockMvc.perform(get("/api/list-services/{id}", listServices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listServices.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE));
    }

    @Test
    @Transactional
    public void getNonExistingListServices() throws Exception {
        // Get the listServices
        restListServicesMockMvc.perform(get("/api/list-services/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListServices() throws Exception {
        // Initialize the database
        listServicesRepository.saveAndFlush(listServices);

        int databaseSizeBeforeUpdate = listServicesRepository.findAll().size();

        // Update the listServices
        ListServices updatedListServices = listServicesRepository.findById(listServices.getId()).get();
        // Disconnect from session so that the updates on updatedListServices are not directly saved in db
        em.detach(updatedListServices);
        updatedListServices
            .value(UPDATED_VALUE);
        ListServicesDTO listServicesDTO = listServicesMapper.toDto(updatedListServices);

        restListServicesMockMvc.perform(put("/api/list-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listServicesDTO)))
            .andExpect(status().isOk());

        // Validate the ListServices in the database
        List<ListServices> listServicesList = listServicesRepository.findAll();
        assertThat(listServicesList).hasSize(databaseSizeBeforeUpdate);
        ListServices testListServices = listServicesList.get(listServicesList.size() - 1);
        assertThat(testListServices.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingListServices() throws Exception {
        int databaseSizeBeforeUpdate = listServicesRepository.findAll().size();

        // Create the ListServices
        ListServicesDTO listServicesDTO = listServicesMapper.toDto(listServices);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListServicesMockMvc.perform(put("/api/list-services")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listServicesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListServices in the database
        List<ListServices> listServicesList = listServicesRepository.findAll();
        assertThat(listServicesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListServices() throws Exception {
        // Initialize the database
        listServicesRepository.saveAndFlush(listServices);

        int databaseSizeBeforeDelete = listServicesRepository.findAll().size();

        // Delete the listServices
        restListServicesMockMvc.perform(delete("/api/list-services/{id}", listServices.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListServices> listServicesList = listServicesRepository.findAll();
        assertThat(listServicesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListServices.class);
        ListServices listServices1 = new ListServices();
        listServices1.setId(1L);
        ListServices listServices2 = new ListServices();
        listServices2.setId(listServices1.getId());
        assertThat(listServices1).isEqualTo(listServices2);
        listServices2.setId(2L);
        assertThat(listServices1).isNotEqualTo(listServices2);
        listServices1.setId(null);
        assertThat(listServices1).isNotEqualTo(listServices2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListServicesDTO.class);
        ListServicesDTO listServicesDTO1 = new ListServicesDTO();
        listServicesDTO1.setId(1L);
        ListServicesDTO listServicesDTO2 = new ListServicesDTO();
        assertThat(listServicesDTO1).isNotEqualTo(listServicesDTO2);
        listServicesDTO2.setId(listServicesDTO1.getId());
        assertThat(listServicesDTO1).isEqualTo(listServicesDTO2);
        listServicesDTO2.setId(2L);
        assertThat(listServicesDTO1).isNotEqualTo(listServicesDTO2);
        listServicesDTO1.setId(null);
        assertThat(listServicesDTO1).isNotEqualTo(listServicesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(listServicesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(listServicesMapper.fromId(null)).isNull();
    }
}
