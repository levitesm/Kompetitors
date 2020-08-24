package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.DashboardFinance;
import fr.ippon.kompetitors.repository.DashboardFinanceRepository;
import fr.ippon.kompetitors.service.DashboardFinancePrepareService;
import fr.ippon.kompetitors.service.DashboardFinanceService;
import fr.ippon.kompetitors.service.dto.DashboardFinanceDTO;
import fr.ippon.kompetitors.service.mapper.DashboardFinanceMapper;
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
 * Integration tests for the {@link DashboardFinanceResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class DashboardFinanceResourceIT {

    private static final Long DEFAULT_GROSS_SALES = 1L;
    private static final Long UPDATED_GROSS_SALES = 2L;

    private static final Long DEFAULT_GROSS_SALES_PER_EMPLOYEE = 1L;
    private static final Long UPDATED_GROSS_SALES_PER_EMPLOYEE = 2L;

    private static final Long DEFAULT_EBIT = 1L;
    private static final Long UPDATED_EBIT = 2L;

    private static final Long DEFAULT_NET_RESULT = 1L;
    private static final Long UPDATED_NET_RESULT = 2L;

    private static final Long DEFAULT_WORKFORCE = 1L;
    private static final Long UPDATED_WORKFORCE = 2L;

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final Long DEFAULT_GROSS_SALES_PER_CONSULTANT = 1L;
    private static final Long UPDATED_GROSS_SALES_PER_CONSULTANT = 2L;

    private static final Long DEFAULT_AVERAGE_PAY = 1L;
    private static final Long UPDATED_AVERAGE_PAY = 2L;

    private static final Integer DEFAULT_NET_RESULT_PERCENT = 1;
    private static final Integer UPDATED_NET_RESULT_PERCENT = 2;

    @Autowired
    private DashboardFinanceRepository dashboardFinanceRepository;

    @Autowired
    private DashboardFinancePrepareService dashboardFinancePrepareService;

    @Autowired
    private DashboardFinanceMapper dashboardFinanceMapper;

    @Autowired
    private DashboardFinanceService dashboardFinanceService;

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

    private MockMvc restDashboardFinanceMockMvc;

    private DashboardFinance dashboardFinance;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DashboardFinanceResource dashboardFinanceResource = new DashboardFinanceResource(dashboardFinanceService, dashboardFinancePrepareService);
        this.restDashboardFinanceMockMvc = MockMvcBuilders.standaloneSetup(dashboardFinanceResource)
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
    public static DashboardFinance createEntity(EntityManager em) {
        DashboardFinance dashboardFinance = new DashboardFinance()
            .grossSales(DEFAULT_GROSS_SALES)
            .grossSalesPerEmployee(DEFAULT_GROSS_SALES_PER_EMPLOYEE)
            .ebit(DEFAULT_EBIT)
            .netResult(DEFAULT_NET_RESULT)
            .workforce(DEFAULT_WORKFORCE)
            .year(DEFAULT_YEAR)
            .grossSalesPerConsultant(DEFAULT_GROSS_SALES_PER_CONSULTANT)
            .averagePay(DEFAULT_AVERAGE_PAY)
            .netResultPercent(DEFAULT_NET_RESULT_PERCENT);
        return dashboardFinance;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DashboardFinance createUpdatedEntity(EntityManager em) {
        DashboardFinance dashboardFinance = new DashboardFinance()
            .grossSales(UPDATED_GROSS_SALES)
            .grossSalesPerEmployee(UPDATED_GROSS_SALES_PER_EMPLOYEE)
            .ebit(UPDATED_EBIT)
            .netResult(UPDATED_NET_RESULT)
            .workforce(UPDATED_WORKFORCE)
            .year(UPDATED_YEAR)
            .grossSalesPerConsultant(UPDATED_GROSS_SALES_PER_CONSULTANT)
            .averagePay(UPDATED_AVERAGE_PAY)
            .netResultPercent(UPDATED_NET_RESULT_PERCENT);
        return dashboardFinance;
    }

    @BeforeEach
    public void initTest() {
        dashboardFinance = createEntity(em);
    }

    @Test
    @Transactional
    public void createDashboardFinance() throws Exception {
        int databaseSizeBeforeCreate = dashboardFinanceRepository.findAll().size();

        // Create the DashboardFinance
        DashboardFinanceDTO dashboardFinanceDTO = dashboardFinanceMapper.toDto(dashboardFinance);
        restDashboardFinanceMockMvc.perform(post("/api/dashboard-finances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboardFinanceDTO)))
            .andExpect(status().isCreated());

        // Validate the DashboardFinance in the database
        List<DashboardFinance> dashboardFinanceList = dashboardFinanceRepository.findAll();
        assertThat(dashboardFinanceList).hasSize(databaseSizeBeforeCreate + 1);
        DashboardFinance testDashboardFinance = dashboardFinanceList.get(dashboardFinanceList.size() - 1);
        assertThat(testDashboardFinance.getGrossSales()).isEqualTo(DEFAULT_GROSS_SALES);
        assertThat(testDashboardFinance.getGrossSalesPerEmployee()).isEqualTo(DEFAULT_GROSS_SALES_PER_EMPLOYEE);
        assertThat(testDashboardFinance.getEbit()).isEqualTo(DEFAULT_EBIT);
        assertThat(testDashboardFinance.getNetResult()).isEqualTo(DEFAULT_NET_RESULT);
        assertThat(testDashboardFinance.getWorkforce()).isEqualTo(DEFAULT_WORKFORCE);
        assertThat(testDashboardFinance.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testDashboardFinance.getGrossSalesPerConsultant()).isEqualTo(DEFAULT_GROSS_SALES_PER_CONSULTANT);
        assertThat(testDashboardFinance.getAveragePay()).isEqualTo(DEFAULT_AVERAGE_PAY);
        assertThat(testDashboardFinance.getNetResultPercent()).isEqualTo(DEFAULT_NET_RESULT_PERCENT);
    }

    @Test
    @Transactional
    public void createDashboardFinanceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dashboardFinanceRepository.findAll().size();

        // Create the DashboardFinance with an existing ID
        dashboardFinance.setId(1L);
        DashboardFinanceDTO dashboardFinanceDTO = dashboardFinanceMapper.toDto(dashboardFinance);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDashboardFinanceMockMvc.perform(post("/api/dashboard-finances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboardFinanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DashboardFinance in the database
        List<DashboardFinance> dashboardFinanceList = dashboardFinanceRepository.findAll();
        assertThat(dashboardFinanceList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllDashboardFinances() throws Exception {
        // Initialize the database
        dashboardFinanceRepository.saveAndFlush(dashboardFinance);

        // Get all the dashboardFinanceList
        restDashboardFinanceMockMvc.perform(get("/api/dashboard-finances?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(dashboardFinance.getId().intValue())))
            .andExpect(jsonPath("$.[*].grossSales").value(hasItem(DEFAULT_GROSS_SALES.intValue())))
            .andExpect(jsonPath("$.[*].grossSalesPerEmployee").value(hasItem(DEFAULT_GROSS_SALES_PER_EMPLOYEE.intValue())))
            .andExpect(jsonPath("$.[*].ebit").value(hasItem(DEFAULT_EBIT.intValue())))
            .andExpect(jsonPath("$.[*].netResult").value(hasItem(DEFAULT_NET_RESULT.intValue())))
            .andExpect(jsonPath("$.[*].workforce").value(hasItem(DEFAULT_WORKFORCE.intValue())))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].grossSalesPerConsultant").value(hasItem(DEFAULT_GROSS_SALES_PER_CONSULTANT.intValue())))
            .andExpect(jsonPath("$.[*].averagePay").value(hasItem(DEFAULT_AVERAGE_PAY.intValue())))
            .andExpect(jsonPath("$.[*].netResultPercent").value(hasItem(DEFAULT_NET_RESULT_PERCENT)));
    }

    @Test
    @Transactional
    public void getDashboardFinance() throws Exception {
        // Initialize the database
        dashboardFinanceRepository.saveAndFlush(dashboardFinance);

        // Get the dashboardFinance
        restDashboardFinanceMockMvc.perform(get("/api/dashboard-finances/{id}", dashboardFinance.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(dashboardFinance.getId().intValue()))
            .andExpect(jsonPath("$.grossSales").value(DEFAULT_GROSS_SALES.intValue()))
            .andExpect(jsonPath("$.grossSalesPerEmployee").value(DEFAULT_GROSS_SALES_PER_EMPLOYEE.intValue()))
            .andExpect(jsonPath("$.ebit").value(DEFAULT_EBIT.intValue()))
            .andExpect(jsonPath("$.netResult").value(DEFAULT_NET_RESULT.intValue()))
            .andExpect(jsonPath("$.workforce").value(DEFAULT_WORKFORCE.intValue()))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.grossSalesPerConsultant").value(DEFAULT_GROSS_SALES_PER_CONSULTANT.intValue()))
            .andExpect(jsonPath("$.averagePay").value(DEFAULT_AVERAGE_PAY.intValue()))
            .andExpect(jsonPath("$.netResultPercent").value(DEFAULT_NET_RESULT_PERCENT));
    }

    @Test
    @Transactional
    public void getNonExistingDashboardFinance() throws Exception {
        // Get the dashboardFinance
        restDashboardFinanceMockMvc.perform(get("/api/dashboard-finances/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDashboardFinance() throws Exception {
        // Initialize the database
        dashboardFinanceRepository.saveAndFlush(dashboardFinance);

        int databaseSizeBeforeUpdate = dashboardFinanceRepository.findAll().size();

        // Update the dashboardFinance
        DashboardFinance updatedDashboardFinance = dashboardFinanceRepository.findById(dashboardFinance.getId()).get();
        // Disconnect from session so that the updates on updatedDashboardFinance are not directly saved in db
        em.detach(updatedDashboardFinance);
        updatedDashboardFinance
            .grossSales(UPDATED_GROSS_SALES)
            .grossSalesPerEmployee(UPDATED_GROSS_SALES_PER_EMPLOYEE)
            .ebit(UPDATED_EBIT)
            .netResult(UPDATED_NET_RESULT)
            .workforce(UPDATED_WORKFORCE)
            .year(UPDATED_YEAR)
            .grossSalesPerConsultant(UPDATED_GROSS_SALES_PER_CONSULTANT)
            .averagePay(UPDATED_AVERAGE_PAY)
            .netResultPercent(UPDATED_NET_RESULT_PERCENT);
        DashboardFinanceDTO dashboardFinanceDTO = dashboardFinanceMapper.toDto(updatedDashboardFinance);

        restDashboardFinanceMockMvc.perform(put("/api/dashboard-finances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboardFinanceDTO)))
            .andExpect(status().isOk());

        // Validate the DashboardFinance in the database
        List<DashboardFinance> dashboardFinanceList = dashboardFinanceRepository.findAll();
        assertThat(dashboardFinanceList).hasSize(databaseSizeBeforeUpdate);
        DashboardFinance testDashboardFinance = dashboardFinanceList.get(dashboardFinanceList.size() - 1);
        assertThat(testDashboardFinance.getGrossSales()).isEqualTo(UPDATED_GROSS_SALES);
        assertThat(testDashboardFinance.getGrossSalesPerEmployee()).isEqualTo(UPDATED_GROSS_SALES_PER_EMPLOYEE);
        assertThat(testDashboardFinance.getEbit()).isEqualTo(UPDATED_EBIT);
        assertThat(testDashboardFinance.getNetResult()).isEqualTo(UPDATED_NET_RESULT);
        assertThat(testDashboardFinance.getWorkforce()).isEqualTo(UPDATED_WORKFORCE);
        assertThat(testDashboardFinance.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testDashboardFinance.getGrossSalesPerConsultant()).isEqualTo(UPDATED_GROSS_SALES_PER_CONSULTANT);
        assertThat(testDashboardFinance.getAveragePay()).isEqualTo(UPDATED_AVERAGE_PAY);
        assertThat(testDashboardFinance.getNetResultPercent()).isEqualTo(UPDATED_NET_RESULT_PERCENT);
    }

    @Test
    @Transactional
    public void updateNonExistingDashboardFinance() throws Exception {
        int databaseSizeBeforeUpdate = dashboardFinanceRepository.findAll().size();

        // Create the DashboardFinance
        DashboardFinanceDTO dashboardFinanceDTO = dashboardFinanceMapper.toDto(dashboardFinance);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDashboardFinanceMockMvc.perform(put("/api/dashboard-finances")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dashboardFinanceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DashboardFinance in the database
        List<DashboardFinance> dashboardFinanceList = dashboardFinanceRepository.findAll();
        assertThat(dashboardFinanceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDashboardFinance() throws Exception {
        // Initialize the database
        dashboardFinanceRepository.saveAndFlush(dashboardFinance);

        int databaseSizeBeforeDelete = dashboardFinanceRepository.findAll().size();

        // Delete the dashboardFinance
        restDashboardFinanceMockMvc.perform(delete("/api/dashboard-finances/{id}", dashboardFinance.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DashboardFinance> dashboardFinanceList = dashboardFinanceRepository.findAll();
        assertThat(dashboardFinanceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DashboardFinance.class);
        DashboardFinance dashboardFinance1 = new DashboardFinance();
        dashboardFinance1.setId(1L);
        DashboardFinance dashboardFinance2 = new DashboardFinance();
        dashboardFinance2.setId(dashboardFinance1.getId());
        assertThat(dashboardFinance1).isEqualTo(dashboardFinance2);
        dashboardFinance2.setId(2L);
        assertThat(dashboardFinance1).isNotEqualTo(dashboardFinance2);
        dashboardFinance1.setId(null);
        assertThat(dashboardFinance1).isNotEqualTo(dashboardFinance2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DashboardFinanceDTO.class);
        DashboardFinanceDTO dashboardFinanceDTO1 = new DashboardFinanceDTO();
        dashboardFinanceDTO1.setId(1L);
        DashboardFinanceDTO dashboardFinanceDTO2 = new DashboardFinanceDTO();
        assertThat(dashboardFinanceDTO1).isNotEqualTo(dashboardFinanceDTO2);
        dashboardFinanceDTO2.setId(dashboardFinanceDTO1.getId());
        assertThat(dashboardFinanceDTO1).isEqualTo(dashboardFinanceDTO2);
        dashboardFinanceDTO2.setId(2L);
        assertThat(dashboardFinanceDTO1).isNotEqualTo(dashboardFinanceDTO2);
        dashboardFinanceDTO1.setId(null);
        assertThat(dashboardFinanceDTO1).isNotEqualTo(dashboardFinanceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(dashboardFinanceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(dashboardFinanceMapper.fromId(null)).isNull();
    }
}
