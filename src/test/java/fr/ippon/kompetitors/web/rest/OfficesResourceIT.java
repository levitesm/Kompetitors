package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.Offices;
import fr.ippon.kompetitors.repository.OfficesRepository;
import fr.ippon.kompetitors.service.GeoCodingService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static fr.ippon.kompetitors.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OfficesResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class OfficesResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE = "BBBBBBBBBB";

    private static final String DEFAULT_POST = "AAAAAAAAAA";
    private static final String UPDATED_POST = "BBBBBBBBBB";

    private static final String DEFAULT_CITY_AS_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_CITY_AS_TEXT = "BBBBBBBBBB";

    private static final Integer DEFAULT_NUMBER_EMPLOYEES = 1;
    private static final Integer UPDATED_NUMBER_EMPLOYEES = 2;

    private static final Integer DEFAULT_NUMBER_CONSULTANTS = 1;
    private static final Integer UPDATED_NUMBER_CONSULTANTS = 2;

    private static final Integer DEFAULT_NUMBER_TECHNICALS = 1;
    private static final Integer UPDATED_NUMBER_TECHNICALS = 2;

    private static final Integer DEFAULT_NUMBER_HR = 1;
    private static final Integer UPDATED_NUMBER_HR = 2;

    private static final Integer DEFAULT_NUMBER_CLIENTS = 1;
    private static final Integer UPDATED_NUMBER_CLIENTS = 2;

    private static final LocalDate DEFAULT_ESTABLISHED = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTABLISHED = LocalDate.now(ZoneId.systemDefault());

    private static final Boolean DEFAULT_IS_MAIN_OFFICE = false;
    private static final Boolean UPDATED_IS_MAIN_OFFICE = true;

    private static final Double DEFAULT_LATITUDE = 1D;
    private static final Double UPDATED_LATITUDE = 2D;

    private static final Double DEFAULT_LONGITUDE = 1D;
    private static final Double UPDATED_LONGITUDE = 2D;

    @Autowired
    private OfficesRepository officesRepository;

    @Autowired
    private GeoCodingService geoCodingService;

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

    private MockMvc restOfficesMockMvc;

    private Offices offices;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OfficesResource officesResource = new OfficesResource(officesRepository, geoCodingService);
        this.restOfficesMockMvc = MockMvcBuilders.standaloneSetup(officesResource)
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
    public static Offices createEntity(EntityManager em) {
        Offices offices = new Offices()
            .name(DEFAULT_NAME)
            .address(DEFAULT_ADDRESS)
            .phone(DEFAULT_PHONE)
            .post(DEFAULT_POST)
            .cityAsText(DEFAULT_CITY_AS_TEXT)
            .numberEmployees(DEFAULT_NUMBER_EMPLOYEES)
            .numberConsultants(DEFAULT_NUMBER_CONSULTANTS)
            .numberTechnicals(DEFAULT_NUMBER_TECHNICALS)
            .numberHR(DEFAULT_NUMBER_HR)
            .numberClients(DEFAULT_NUMBER_CLIENTS)
            .established(DEFAULT_ESTABLISHED)
            .mainOffice(DEFAULT_IS_MAIN_OFFICE)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE);
        return offices;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Offices createUpdatedEntity(EntityManager em) {
        Offices offices = new Offices()
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .post(UPDATED_POST)
            .cityAsText(UPDATED_CITY_AS_TEXT)
            .numberEmployees(UPDATED_NUMBER_EMPLOYEES)
            .numberConsultants(UPDATED_NUMBER_CONSULTANTS)
            .numberTechnicals(UPDATED_NUMBER_TECHNICALS)
            .numberHR(UPDATED_NUMBER_HR)
            .numberClients(UPDATED_NUMBER_CLIENTS)
            .established(UPDATED_ESTABLISHED)
            .mainOffice(UPDATED_IS_MAIN_OFFICE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);
        return offices;
    }

    @BeforeEach
    public void initTest() {
        offices = createEntity(em);
    }

    @Test
    @Transactional
    public void createOffices() throws Exception {
        int databaseSizeBeforeCreate = officesRepository.findAll().size();

        // Create the Offices
        restOfficesMockMvc.perform(post("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offices)))
            .andExpect(status().isCreated());

        // Validate the Offices in the database
        List<Offices> officesList = officesRepository.findAll();
        assertThat(officesList).hasSize(databaseSizeBeforeCreate + 1);
        Offices testOffices = officesList.get(officesList.size() - 1);
        assertThat(testOffices.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOffices.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testOffices.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testOffices.getPost()).isEqualTo(DEFAULT_POST);
        assertThat(testOffices.getCityAsText()).isEqualTo(DEFAULT_CITY_AS_TEXT);
        assertThat(testOffices.getNumberEmployees()).isEqualTo(DEFAULT_NUMBER_EMPLOYEES);
        assertThat(testOffices.getNumberConsultants()).isEqualTo(DEFAULT_NUMBER_CONSULTANTS);
        assertThat(testOffices.getNumberTechnicals()).isEqualTo(DEFAULT_NUMBER_TECHNICALS);
        assertThat(testOffices.getNumberHR()).isEqualTo(DEFAULT_NUMBER_HR);
        assertThat(testOffices.getNumberClients()).isEqualTo(DEFAULT_NUMBER_CLIENTS);
        assertThat(testOffices.getEstablished()).isEqualTo(DEFAULT_ESTABLISHED);
        assertThat(testOffices.getMainOffice()).isEqualTo(DEFAULT_IS_MAIN_OFFICE);
        assertThat(testOffices.getLatitude()).isEqualTo(DEFAULT_LATITUDE);
        assertThat(testOffices.getLongitude()).isEqualTo(DEFAULT_LONGITUDE);
    }

    @Test
    @Transactional
    public void createOfficesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = officesRepository.findAll().size();

        // Create the Offices with an existing ID
        offices.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOfficesMockMvc.perform(post("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offices)))
            .andExpect(status().isBadRequest());

        // Validate the Offices in the database
        List<Offices> officesList = officesRepository.findAll();
        assertThat(officesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOffices() throws Exception {
        // Initialize the database
        officesRepository.saveAndFlush(offices);

        // Get all the officesList
        restOfficesMockMvc.perform(get("/api/offices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(offices.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS)))
            .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE)))
            .andExpect(jsonPath("$.[*].post").value(hasItem(DEFAULT_POST)))
            .andExpect(jsonPath("$.[*].cityAsText").value(hasItem(DEFAULT_CITY_AS_TEXT)))
            .andExpect(jsonPath("$.[*].numberEmployees").value(hasItem(DEFAULT_NUMBER_EMPLOYEES)))
            .andExpect(jsonPath("$.[*].numberConsultants").value(hasItem(DEFAULT_NUMBER_CONSULTANTS)))
            .andExpect(jsonPath("$.[*].numberTechnicals").value(hasItem(DEFAULT_NUMBER_TECHNICALS)))
            .andExpect(jsonPath("$.[*].numberHR").value(hasItem(DEFAULT_NUMBER_HR)))
            .andExpect(jsonPath("$.[*].numberClients").value(hasItem(DEFAULT_NUMBER_CLIENTS)))
            .andExpect(jsonPath("$.[*].established").value(hasItem(DEFAULT_ESTABLISHED.toString())))
            .andExpect(jsonPath("$.[*].isMainOffice").value(hasItem(DEFAULT_IS_MAIN_OFFICE.booleanValue())))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE.doubleValue())))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE.doubleValue())));
    }

    @Test
    @Transactional
    public void getOffices() throws Exception {
        // Initialize the database
        officesRepository.saveAndFlush(offices);

        // Get the offices
        restOfficesMockMvc.perform(get("/api/offices/{id}", offices.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(offices.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE))
            .andExpect(jsonPath("$.post").value(DEFAULT_POST))
            .andExpect(jsonPath("$.cityAsText").value(DEFAULT_CITY_AS_TEXT))
            .andExpect(jsonPath("$.numberEmployees").value(DEFAULT_NUMBER_EMPLOYEES))
            .andExpect(jsonPath("$.numberConsultants").value(DEFAULT_NUMBER_CONSULTANTS))
            .andExpect(jsonPath("$.numberTechnicals").value(DEFAULT_NUMBER_TECHNICALS))
            .andExpect(jsonPath("$.numberHR").value(DEFAULT_NUMBER_HR))
            .andExpect(jsonPath("$.numberClients").value(DEFAULT_NUMBER_CLIENTS))
            .andExpect(jsonPath("$.established").value(DEFAULT_ESTABLISHED.toString()))
            .andExpect(jsonPath("$.isMainOffice").value(DEFAULT_IS_MAIN_OFFICE.booleanValue()))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE.doubleValue()))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOffices() throws Exception {
        // Get the offices
        restOfficesMockMvc.perform(get("/api/offices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOffices() throws Exception {
        // Initialize the database
        officesRepository.saveAndFlush(offices);

        int databaseSizeBeforeUpdate = officesRepository.findAll().size();

        // Update the offices
        Offices updatedOffices = officesRepository.findById(offices.getId()).get();
        // Disconnect from session so that the updates on updatedOffices are not directly saved in db
        em.detach(updatedOffices);
        updatedOffices
            .name(UPDATED_NAME)
            .address(UPDATED_ADDRESS)
            .phone(UPDATED_PHONE)
            .post(UPDATED_POST)
            .cityAsText(UPDATED_CITY_AS_TEXT)
            .numberEmployees(UPDATED_NUMBER_EMPLOYEES)
            .numberConsultants(UPDATED_NUMBER_CONSULTANTS)
            .numberTechnicals(UPDATED_NUMBER_TECHNICALS)
            .numberHR(UPDATED_NUMBER_HR)
            .numberClients(UPDATED_NUMBER_CLIENTS)
            .established(UPDATED_ESTABLISHED)
            .mainOffice(UPDATED_IS_MAIN_OFFICE)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE);

        restOfficesMockMvc.perform(put("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOffices)))
            .andExpect(status().isOk());

        // Validate the Offices in the database
        List<Offices> officesList = officesRepository.findAll();
        assertThat(officesList).hasSize(databaseSizeBeforeUpdate);
        Offices testOffices = officesList.get(officesList.size() - 1);
        assertThat(testOffices.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOffices.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testOffices.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testOffices.getPost()).isEqualTo(UPDATED_POST);
        assertThat(testOffices.getCityAsText()).isEqualTo(UPDATED_CITY_AS_TEXT);
        assertThat(testOffices.getNumberEmployees()).isEqualTo(UPDATED_NUMBER_EMPLOYEES);
        assertThat(testOffices.getNumberConsultants()).isEqualTo(UPDATED_NUMBER_CONSULTANTS);
        assertThat(testOffices.getNumberTechnicals()).isEqualTo(UPDATED_NUMBER_TECHNICALS);
        assertThat(testOffices.getNumberHR()).isEqualTo(UPDATED_NUMBER_HR);
        assertThat(testOffices.getNumberClients()).isEqualTo(UPDATED_NUMBER_CLIENTS);
        assertThat(testOffices.getEstablished()).isEqualTo(UPDATED_ESTABLISHED);
        assertThat(testOffices.getMainOffice()).isEqualTo(UPDATED_IS_MAIN_OFFICE);
        assertThat(testOffices.getLatitude()).isEqualTo(UPDATED_LATITUDE);
        assertThat(testOffices.getLongitude()).isEqualTo(UPDATED_LONGITUDE);
    }

    @Test
    @Transactional
    public void updateNonExistingOffices() throws Exception {
        int databaseSizeBeforeUpdate = officesRepository.findAll().size();

        // Create the Offices

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOfficesMockMvc.perform(put("/api/offices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(offices)))
            .andExpect(status().isBadRequest());

        // Validate the Offices in the database
        List<Offices> officesList = officesRepository.findAll();
        assertThat(officesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOffices() throws Exception {
        // Initialize the database
        officesRepository.saveAndFlush(offices);

        int databaseSizeBeforeDelete = officesRepository.findAll().size();

        // Delete the offices
        restOfficesMockMvc.perform(delete("/api/offices/{id}", offices.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Offices> officesList = officesRepository.findAll();
        assertThat(officesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Offices.class);
        Offices offices1 = new Offices();
        offices1.setId(1L);
        Offices offices2 = new Offices();
        offices2.setId(offices1.getId());
        assertThat(offices1).isEqualTo(offices2);
        offices2.setId(2L);
        assertThat(offices1).isNotEqualTo(offices2);
        offices1.setId(null);
        assertThat(offices1).isNotEqualTo(offices2);
    }
}
