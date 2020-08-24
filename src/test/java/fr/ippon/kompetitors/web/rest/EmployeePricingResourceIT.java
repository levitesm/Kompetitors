package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.EmployeePricing;
import fr.ippon.kompetitors.repository.EmployeePricingRepository;
import fr.ippon.kompetitors.service.EmployeePricingService;
import fr.ippon.kompetitors.service.dto.EmployeePricingDTO;
import fr.ippon.kompetitors.service.mapper.EmployeePricingMapper;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static fr.ippon.kompetitors.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import fr.ippon.kompetitors.domain.enumeration.EmployeeLevel;
import fr.ippon.kompetitors.domain.enumeration.Currency;
import fr.ippon.kompetitors.domain.enumeration.PaymentType;
/**
 * Integration tests for the {@link EmployeePricingResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class EmployeePricingResourceIT {

    private static final EmployeeLevel DEFAULT_LEVEL = EmployeeLevel.JUNIOR;
    private static final EmployeeLevel UPDATED_LEVEL = EmployeeLevel.MIDDLE;

    private static final Long DEFAULT_PRICE = 1L;
    private static final Long UPDATED_PRICE = 2L;

    private static final Currency DEFAULT_CURRENCY = Currency.EUR;
    private static final Currency UPDATED_CURRENCY = Currency.USD;

    private static final PaymentType DEFAULT_PAYMENT_TYPE = PaymentType.PER_HOUR;
    private static final PaymentType UPDATED_PAYMENT_TYPE = PaymentType.PER_DAY;

    private static final Instant DEFAULT_MODIFIED = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_MODIFIED = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private EmployeePricingRepository employeePricingRepository;

    @Autowired
    private EmployeePricingMapper employeePricingMapper;

    @Autowired
    private EmployeePricingService employeePricingService;

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

    private MockMvc restEmployeePricingMockMvc;

    private EmployeePricing employeePricing;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeePricingResource employeePricingResource = new EmployeePricingResource(employeePricingService);
        this.restEmployeePricingMockMvc = MockMvcBuilders.standaloneSetup(employeePricingResource)
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
    public static EmployeePricing createEntity(EntityManager em) {
        EmployeePricing employeePricing = new EmployeePricing()
            .level(DEFAULT_LEVEL)
            .price(DEFAULT_PRICE)
            .currency(DEFAULT_CURRENCY)
            .paymentType(DEFAULT_PAYMENT_TYPE)
            .modified(DEFAULT_MODIFIED);
        return employeePricing;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static EmployeePricing createUpdatedEntity(EntityManager em) {
        EmployeePricing employeePricing = new EmployeePricing()
            .level(UPDATED_LEVEL)
            .price(UPDATED_PRICE)
            .currency(UPDATED_CURRENCY)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .modified(UPDATED_MODIFIED);
        return employeePricing;
    }

    @BeforeEach
    public void initTest() {
        employeePricing = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployeePricing() throws Exception {
        int databaseSizeBeforeCreate = employeePricingRepository.findAll().size();

        // Create the EmployeePricing
        EmployeePricingDTO employeePricingDTO = employeePricingMapper.toDto(employeePricing);
        restEmployeePricingMockMvc.perform(post("/api/employee-pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePricingDTO)))
            .andExpect(status().isCreated());

        // Validate the EmployeePricing in the database
        List<EmployeePricing> employeePricingList = employeePricingRepository.findAll();
        assertThat(employeePricingList).hasSize(databaseSizeBeforeCreate + 1);
        EmployeePricing testEmployeePricing = employeePricingList.get(employeePricingList.size() - 1);
        assertThat(testEmployeePricing.getLevel()).isEqualTo(DEFAULT_LEVEL);
        assertThat(testEmployeePricing.getPrice()).isEqualTo(DEFAULT_PRICE);
        assertThat(testEmployeePricing.getCurrency()).isEqualTo(DEFAULT_CURRENCY);
        assertThat(testEmployeePricing.getPaymentType()).isEqualTo(DEFAULT_PAYMENT_TYPE);
        assertThat(testEmployeePricing.getModified()).isEqualTo(DEFAULT_MODIFIED);
    }

    @Test
    @Transactional
    public void createEmployeePricingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeePricingRepository.findAll().size();

        // Create the EmployeePricing with an existing ID
        employeePricing.setId(1L);
        EmployeePricingDTO employeePricingDTO = employeePricingMapper.toDto(employeePricing);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeePricingMockMvc.perform(post("/api/employee-pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePricingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeePricing in the database
        List<EmployeePricing> employeePricingList = employeePricingRepository.findAll();
        assertThat(employeePricingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllEmployeePricings() throws Exception {
        // Initialize the database
        employeePricingRepository.saveAndFlush(employeePricing);

        // Get all the employeePricingList
        restEmployeePricingMockMvc.perform(get("/api/employee-pricings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employeePricing.getId().intValue())))
            .andExpect(jsonPath("$.[*].level").value(hasItem(DEFAULT_LEVEL.toString())))
            .andExpect(jsonPath("$.[*].price").value(hasItem(DEFAULT_PRICE.intValue())))
            .andExpect(jsonPath("$.[*].currency").value(hasItem(DEFAULT_CURRENCY.toString())))
            .andExpect(jsonPath("$.[*].paymentType").value(hasItem(DEFAULT_PAYMENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].modified").value(hasItem(DEFAULT_MODIFIED.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployeePricing() throws Exception {
        // Initialize the database
        employeePricingRepository.saveAndFlush(employeePricing);

        // Get the employeePricing
        restEmployeePricingMockMvc.perform(get("/api/employee-pricings/{id}", employeePricing.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employeePricing.getId().intValue()))
            .andExpect(jsonPath("$.level").value(DEFAULT_LEVEL.toString()))
            .andExpect(jsonPath("$.price").value(DEFAULT_PRICE.intValue()))
            .andExpect(jsonPath("$.currency").value(DEFAULT_CURRENCY.toString()))
            .andExpect(jsonPath("$.paymentType").value(DEFAULT_PAYMENT_TYPE.toString()))
            .andExpect(jsonPath("$.modified").value(DEFAULT_MODIFIED.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEmployeePricing() throws Exception {
        // Get the employeePricing
        restEmployeePricingMockMvc.perform(get("/api/employee-pricings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployeePricing() throws Exception {
        // Initialize the database
        employeePricingRepository.saveAndFlush(employeePricing);

        int databaseSizeBeforeUpdate = employeePricingRepository.findAll().size();

        // Update the employeePricing
        EmployeePricing updatedEmployeePricing = employeePricingRepository.findById(employeePricing.getId()).get();
        // Disconnect from session so that the updates on updatedEmployeePricing are not directly saved in db
        em.detach(updatedEmployeePricing);
        updatedEmployeePricing
            .level(UPDATED_LEVEL)
            .price(UPDATED_PRICE)
            .currency(UPDATED_CURRENCY)
            .paymentType(UPDATED_PAYMENT_TYPE)
            .modified(UPDATED_MODIFIED);
        EmployeePricingDTO employeePricingDTO = employeePricingMapper.toDto(updatedEmployeePricing);

        restEmployeePricingMockMvc.perform(put("/api/employee-pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePricingDTO)))
            .andExpect(status().isOk());

        // Validate the EmployeePricing in the database
        List<EmployeePricing> employeePricingList = employeePricingRepository.findAll();
        assertThat(employeePricingList).hasSize(databaseSizeBeforeUpdate);
        EmployeePricing testEmployeePricing = employeePricingList.get(employeePricingList.size() - 1);
        assertThat(testEmployeePricing.getLevel()).isEqualTo(UPDATED_LEVEL);
        assertThat(testEmployeePricing.getPrice()).isEqualTo(UPDATED_PRICE);
        assertThat(testEmployeePricing.getCurrency()).isEqualTo(UPDATED_CURRENCY);
        assertThat(testEmployeePricing.getPaymentType()).isEqualTo(UPDATED_PAYMENT_TYPE);
        assertThat(testEmployeePricing.getModified()).isEqualTo(UPDATED_MODIFIED);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployeePricing() throws Exception {
        int databaseSizeBeforeUpdate = employeePricingRepository.findAll().size();

        // Create the EmployeePricing
        EmployeePricingDTO employeePricingDTO = employeePricingMapper.toDto(employeePricing);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeePricingMockMvc.perform(put("/api/employee-pricings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeePricingDTO)))
            .andExpect(status().isBadRequest());

        // Validate the EmployeePricing in the database
        List<EmployeePricing> employeePricingList = employeePricingRepository.findAll();
        assertThat(employeePricingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteEmployeePricing() throws Exception {
        // Initialize the database
        employeePricingRepository.saveAndFlush(employeePricing);

        int databaseSizeBeforeDelete = employeePricingRepository.findAll().size();

        // Delete the employeePricing
        restEmployeePricingMockMvc.perform(delete("/api/employee-pricings/{id}", employeePricing.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<EmployeePricing> employeePricingList = employeePricingRepository.findAll();
        assertThat(employeePricingList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeePricing.class);
        EmployeePricing employeePricing1 = new EmployeePricing();
        employeePricing1.setId(1L);
        EmployeePricing employeePricing2 = new EmployeePricing();
        employeePricing2.setId(employeePricing1.getId());
        assertThat(employeePricing1).isEqualTo(employeePricing2);
        employeePricing2.setId(2L);
        assertThat(employeePricing1).isNotEqualTo(employeePricing2);
        employeePricing1.setId(null);
        assertThat(employeePricing1).isNotEqualTo(employeePricing2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeePricingDTO.class);
        EmployeePricingDTO employeePricingDTO1 = new EmployeePricingDTO();
        employeePricingDTO1.setId(1L);
        EmployeePricingDTO employeePricingDTO2 = new EmployeePricingDTO();
        assertThat(employeePricingDTO1).isNotEqualTo(employeePricingDTO2);
        employeePricingDTO2.setId(employeePricingDTO1.getId());
        assertThat(employeePricingDTO1).isEqualTo(employeePricingDTO2);
        employeePricingDTO2.setId(2L);
        assertThat(employeePricingDTO1).isNotEqualTo(employeePricingDTO2);
        employeePricingDTO1.setId(null);
        assertThat(employeePricingDTO1).isNotEqualTo(employeePricingDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeePricingMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeePricingMapper.fromId(null)).isNull();
    }
}
