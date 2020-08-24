package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.HrInfo;
import fr.ippon.kompetitors.repository.HrInfoRepository;
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
 * Integration tests for the {@link HrInfoResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class HrInfoResourceIT {

    private static final Integer DEFAULT_INTERVIEWS_NUMBER = 1;
    private static final Integer UPDATED_INTERVIEWS_NUMBER = 2;

    private static final String DEFAULT_RECRUTMENT_TIME = "AAAAAAAAAA";
    private static final String UPDATED_RECRUTMENT_TIME = "BBBBBBBBBB";

    private static final Double DEFAULT_REVIEWED_CV_PERCENT = 1D;
    private static final Double UPDATED_REVIEWED_CV_PERCENT = 2D;

    private static final String DEFAULT_HR_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_HR_DETAILS = "BBBBBBBBBB";

    private static final String DEFAULT_VACANCIES_URL = "AAAAAAAAAA";
    private static final String UPDATED_VACANCIES_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_HR_SPECIALISTS_NUMBER = 1;
    private static final Integer UPDATED_HR_SPECIALISTS_NUMBER = 2;

    private static final Float DEFAULT_GLASSDOOR_RATE = 1F;
    private static final Float UPDATED_GLASSDOOR_RATE = 2F;

    private static final Float DEFAULT_VIADEO_RATE = 1F;
    private static final Float UPDATED_VIADEO_RATE = 2F;

    private static final String DEFAULT_GLASSDOOR_URL = "AAAAAAAAAA";
    private static final String UPDATED_GLASSDOOR_URL = "BBBBBBBBBB";

    private static final String DEFAULT_VIADEO_URL = "AAAAAAAAAA";
    private static final String UPDATED_VIADEO_URL = "BBBBBBBBBB";

    private static final Integer DEFAULT_COOPTATION_PREMIUM_AMOUNT = 1;
    private static final Integer UPDATED_COOPTATION_PREMIUM_AMOUNT = 2;

    private static final Integer DEFAULT_JUNIOR_SALARY = 1;
    private static final Integer UPDATED_JUNIOR_SALARY = 2;

    private static final Integer DEFAULT_AVERAGE_SALARY = 1;
    private static final Integer UPDATED_AVERAGE_SALARY = 2;

    private static final String DEFAULT_SIGNING_INCENTIVES = "AAAAAAAAAA";
    private static final String UPDATED_SIGNING_INCENTIVES = "BBBBBBBBBB";

    private static final Integer DEFAULT_AVERAGE_CONTRACT_DURATION = 1;
    private static final Integer UPDATED_AVERAGE_CONTRACT_DURATION = 2;

    @Autowired
    private HrInfoRepository hrInfoRepository;

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

    private MockMvc restHrInfoMockMvc;

    private HrInfo hrInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final HrInfoResource hrInfoResource = new HrInfoResource(hrInfoRepository);
        this.restHrInfoMockMvc = MockMvcBuilders.standaloneSetup(hrInfoResource)
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
    public static HrInfo createEntity(EntityManager em) {
        HrInfo hrInfo = new HrInfo()
            .interviewsNumber(DEFAULT_INTERVIEWS_NUMBER)
            .recrutmentTime(DEFAULT_RECRUTMENT_TIME)
            .reviewedCvPercent(DEFAULT_REVIEWED_CV_PERCENT)
            .hrDetails(DEFAULT_HR_DETAILS)
            .vacanciesUrl(DEFAULT_VACANCIES_URL)
            .hrSpecialistsNumber(DEFAULT_HR_SPECIALISTS_NUMBER)
            .glassdoorRate(DEFAULT_GLASSDOOR_RATE)
            .viadeoRate(DEFAULT_VIADEO_RATE)
            .glassdoorUrl(DEFAULT_GLASSDOOR_URL)
            .viadeoUrl(DEFAULT_VIADEO_URL)
            .cooptationPremiumAmount(DEFAULT_COOPTATION_PREMIUM_AMOUNT)
            .juniorSalary(DEFAULT_JUNIOR_SALARY)
            .averageSalary(DEFAULT_AVERAGE_SALARY)
            .signingIncentives(DEFAULT_SIGNING_INCENTIVES)
            .averageContractDuration(DEFAULT_AVERAGE_CONTRACT_DURATION);
        return hrInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static HrInfo createUpdatedEntity(EntityManager em) {
        HrInfo hrInfo = new HrInfo()
            .interviewsNumber(UPDATED_INTERVIEWS_NUMBER)
            .recrutmentTime(UPDATED_RECRUTMENT_TIME)
            .reviewedCvPercent(UPDATED_REVIEWED_CV_PERCENT)
            .hrDetails(UPDATED_HR_DETAILS)
            .vacanciesUrl(UPDATED_VACANCIES_URL)
            .hrSpecialistsNumber(UPDATED_HR_SPECIALISTS_NUMBER)
            .glassdoorRate(UPDATED_GLASSDOOR_RATE)
            .viadeoRate(UPDATED_VIADEO_RATE)
            .glassdoorUrl(UPDATED_GLASSDOOR_URL)
            .viadeoUrl(UPDATED_VIADEO_URL)
            .cooptationPremiumAmount(UPDATED_COOPTATION_PREMIUM_AMOUNT)
            .juniorSalary(UPDATED_JUNIOR_SALARY)
            .averageSalary(UPDATED_AVERAGE_SALARY)
            .signingIncentives(UPDATED_SIGNING_INCENTIVES)
            .averageContractDuration(UPDATED_AVERAGE_CONTRACT_DURATION);
        return hrInfo;
    }

    @BeforeEach
    public void initTest() {
        hrInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createHrInfo() throws Exception {
        int databaseSizeBeforeCreate = hrInfoRepository.findAll().size();

        // Create the HrInfo
        restHrInfoMockMvc.perform(post("/api/hr-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hrInfo)))
            .andExpect(status().isCreated());

        // Validate the HrInfo in the database
        List<HrInfo> hrInfoList = hrInfoRepository.findAll();
        assertThat(hrInfoList).hasSize(databaseSizeBeforeCreate + 1);
        HrInfo testHrInfo = hrInfoList.get(hrInfoList.size() - 1);
        assertThat(testHrInfo.getInterviewsNumber()).isEqualTo(DEFAULT_INTERVIEWS_NUMBER);
        assertThat(testHrInfo.getRecrutmentTime()).isEqualTo(DEFAULT_RECRUTMENT_TIME);
        assertThat(testHrInfo.getReviewedCvPercent()).isEqualTo(DEFAULT_REVIEWED_CV_PERCENT);
        assertThat(testHrInfo.getHrDetails()).isEqualTo(DEFAULT_HR_DETAILS);
        assertThat(testHrInfo.getVacanciesUrl()).isEqualTo(DEFAULT_VACANCIES_URL);
        assertThat(testHrInfo.getHrSpecialistsNumber()).isEqualTo(DEFAULT_HR_SPECIALISTS_NUMBER);
        assertThat(testHrInfo.getGlassdoorRate()).isEqualTo(DEFAULT_GLASSDOOR_RATE);
        assertThat(testHrInfo.getViadeoRate()).isEqualTo(DEFAULT_VIADEO_RATE);
        assertThat(testHrInfo.getGlassdoorUrl()).isEqualTo(DEFAULT_GLASSDOOR_URL);
        assertThat(testHrInfo.getViadeoUrl()).isEqualTo(DEFAULT_VIADEO_URL);
        assertThat(testHrInfo.getCooptationPremiumAmount()).isEqualTo(DEFAULT_COOPTATION_PREMIUM_AMOUNT);
        assertThat(testHrInfo.getJuniorSalary()).isEqualTo(DEFAULT_JUNIOR_SALARY);
        assertThat(testHrInfo.getAverageSalary()).isEqualTo(DEFAULT_AVERAGE_SALARY);
        assertThat(testHrInfo.getSigningIncentives()).isEqualTo(DEFAULT_SIGNING_INCENTIVES);
        assertThat(testHrInfo.getAverageContractDuration()).isEqualTo(DEFAULT_AVERAGE_CONTRACT_DURATION);
    }

    @Test
    @Transactional
    public void createHrInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = hrInfoRepository.findAll().size();

        // Create the HrInfo with an existing ID
        hrInfo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restHrInfoMockMvc.perform(post("/api/hr-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hrInfo)))
            .andExpect(status().isBadRequest());

        // Validate the HrInfo in the database
        List<HrInfo> hrInfoList = hrInfoRepository.findAll();
        assertThat(hrInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllHrInfos() throws Exception {
        // Initialize the database
        hrInfoRepository.saveAndFlush(hrInfo);

        // Get all the hrInfoList
        restHrInfoMockMvc.perform(get("/api/hr-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(hrInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].interviewsNumber").value(hasItem(DEFAULT_INTERVIEWS_NUMBER)))
            .andExpect(jsonPath("$.[*].recrutmentTime").value(hasItem(DEFAULT_RECRUTMENT_TIME)))
            .andExpect(jsonPath("$.[*].reviewedCvPercent").value(hasItem(DEFAULT_REVIEWED_CV_PERCENT.doubleValue())))
            .andExpect(jsonPath("$.[*].hrDetails").value(hasItem(DEFAULT_HR_DETAILS)))
            .andExpect(jsonPath("$.[*].vacanciesUrl").value(hasItem(DEFAULT_VACANCIES_URL)))
            .andExpect(jsonPath("$.[*].hrSpecialistsNumber").value(hasItem(DEFAULT_HR_SPECIALISTS_NUMBER)))
            .andExpect(jsonPath("$.[*].glassdoorRate").value(hasItem(DEFAULT_GLASSDOOR_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].viadeoRate").value(hasItem(DEFAULT_VIADEO_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].glassdoorUrl").value(hasItem(DEFAULT_GLASSDOOR_URL)))
            .andExpect(jsonPath("$.[*].viadeoUrl").value(hasItem(DEFAULT_VIADEO_URL)))
            .andExpect(jsonPath("$.[*].cooptationPremiumAmount").value(hasItem(DEFAULT_COOPTATION_PREMIUM_AMOUNT)))
            .andExpect(jsonPath("$.[*].juniorSalary").value(hasItem(DEFAULT_JUNIOR_SALARY)))
            .andExpect(jsonPath("$.[*].averageSalary").value(hasItem(DEFAULT_AVERAGE_SALARY)))
            .andExpect(jsonPath("$.[*].signingIncentives").value(hasItem(DEFAULT_SIGNING_INCENTIVES)))
            .andExpect(jsonPath("$.[*].averageContractDuration").value(hasItem(DEFAULT_AVERAGE_CONTRACT_DURATION)));
    }
    
    @Test
    @Transactional
    public void getHrInfo() throws Exception {
        // Initialize the database
        hrInfoRepository.saveAndFlush(hrInfo);

        // Get the hrInfo
        restHrInfoMockMvc.perform(get("/api/hr-infos/{id}", hrInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(hrInfo.getId().intValue()))
            .andExpect(jsonPath("$.interviewsNumber").value(DEFAULT_INTERVIEWS_NUMBER))
            .andExpect(jsonPath("$.recrutmentTime").value(DEFAULT_RECRUTMENT_TIME))
            .andExpect(jsonPath("$.reviewedCvPercent").value(DEFAULT_REVIEWED_CV_PERCENT.doubleValue()))
            .andExpect(jsonPath("$.hrDetails").value(DEFAULT_HR_DETAILS))
            .andExpect(jsonPath("$.vacanciesUrl").value(DEFAULT_VACANCIES_URL))
            .andExpect(jsonPath("$.hrSpecialistsNumber").value(DEFAULT_HR_SPECIALISTS_NUMBER))
            .andExpect(jsonPath("$.glassdoorRate").value(DEFAULT_GLASSDOOR_RATE.doubleValue()))
            .andExpect(jsonPath("$.viadeoRate").value(DEFAULT_VIADEO_RATE.doubleValue()))
            .andExpect(jsonPath("$.glassdoorUrl").value(DEFAULT_GLASSDOOR_URL))
            .andExpect(jsonPath("$.viadeoUrl").value(DEFAULT_VIADEO_URL))
            .andExpect(jsonPath("$.cooptationPremiumAmount").value(DEFAULT_COOPTATION_PREMIUM_AMOUNT))
            .andExpect(jsonPath("$.juniorSalary").value(DEFAULT_JUNIOR_SALARY))
            .andExpect(jsonPath("$.averageSalary").value(DEFAULT_AVERAGE_SALARY))
            .andExpect(jsonPath("$.signingIncentives").value(DEFAULT_SIGNING_INCENTIVES))
            .andExpect(jsonPath("$.averageContractDuration").value(DEFAULT_AVERAGE_CONTRACT_DURATION));
    }

    @Test
    @Transactional
    public void getNonExistingHrInfo() throws Exception {
        // Get the hrInfo
        restHrInfoMockMvc.perform(get("/api/hr-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateHrInfo() throws Exception {
        // Initialize the database
        hrInfoRepository.saveAndFlush(hrInfo);

        int databaseSizeBeforeUpdate = hrInfoRepository.findAll().size();

        // Update the hrInfo
        HrInfo updatedHrInfo = hrInfoRepository.findById(hrInfo.getId()).get();
        // Disconnect from session so that the updates on updatedHrInfo are not directly saved in db
        em.detach(updatedHrInfo);
        updatedHrInfo
            .interviewsNumber(UPDATED_INTERVIEWS_NUMBER)
            .recrutmentTime(UPDATED_RECRUTMENT_TIME)
            .reviewedCvPercent(UPDATED_REVIEWED_CV_PERCENT)
            .hrDetails(UPDATED_HR_DETAILS)
            .vacanciesUrl(UPDATED_VACANCIES_URL)
            .hrSpecialistsNumber(UPDATED_HR_SPECIALISTS_NUMBER)
            .glassdoorRate(UPDATED_GLASSDOOR_RATE)
            .viadeoRate(UPDATED_VIADEO_RATE)
            .glassdoorUrl(UPDATED_GLASSDOOR_URL)
            .viadeoUrl(UPDATED_VIADEO_URL)
            .cooptationPremiumAmount(UPDATED_COOPTATION_PREMIUM_AMOUNT)
            .juniorSalary(UPDATED_JUNIOR_SALARY)
            .averageSalary(UPDATED_AVERAGE_SALARY)
            .signingIncentives(UPDATED_SIGNING_INCENTIVES)
            .averageContractDuration(UPDATED_AVERAGE_CONTRACT_DURATION);

        restHrInfoMockMvc.perform(put("/api/hr-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedHrInfo)))
            .andExpect(status().isOk());

        // Validate the HrInfo in the database
        List<HrInfo> hrInfoList = hrInfoRepository.findAll();
        assertThat(hrInfoList).hasSize(databaseSizeBeforeUpdate);
        HrInfo testHrInfo = hrInfoList.get(hrInfoList.size() - 1);
        assertThat(testHrInfo.getInterviewsNumber()).isEqualTo(UPDATED_INTERVIEWS_NUMBER);
        assertThat(testHrInfo.getRecrutmentTime()).isEqualTo(UPDATED_RECRUTMENT_TIME);
        assertThat(testHrInfo.getReviewedCvPercent()).isEqualTo(UPDATED_REVIEWED_CV_PERCENT);
        assertThat(testHrInfo.getHrDetails()).isEqualTo(UPDATED_HR_DETAILS);
        assertThat(testHrInfo.getVacanciesUrl()).isEqualTo(UPDATED_VACANCIES_URL);
        assertThat(testHrInfo.getHrSpecialistsNumber()).isEqualTo(UPDATED_HR_SPECIALISTS_NUMBER);
        assertThat(testHrInfo.getGlassdoorRate()).isEqualTo(UPDATED_GLASSDOOR_RATE);
        assertThat(testHrInfo.getViadeoRate()).isEqualTo(UPDATED_VIADEO_RATE);
        assertThat(testHrInfo.getGlassdoorUrl()).isEqualTo(UPDATED_GLASSDOOR_URL);
        assertThat(testHrInfo.getViadeoUrl()).isEqualTo(UPDATED_VIADEO_URL);
        assertThat(testHrInfo.getCooptationPremiumAmount()).isEqualTo(UPDATED_COOPTATION_PREMIUM_AMOUNT);
        assertThat(testHrInfo.getJuniorSalary()).isEqualTo(UPDATED_JUNIOR_SALARY);
        assertThat(testHrInfo.getAverageSalary()).isEqualTo(UPDATED_AVERAGE_SALARY);
        assertThat(testHrInfo.getSigningIncentives()).isEqualTo(UPDATED_SIGNING_INCENTIVES);
        assertThat(testHrInfo.getAverageContractDuration()).isEqualTo(UPDATED_AVERAGE_CONTRACT_DURATION);
    }

    @Test
    @Transactional
    public void updateNonExistingHrInfo() throws Exception {
        int databaseSizeBeforeUpdate = hrInfoRepository.findAll().size();

        // Create the HrInfo

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restHrInfoMockMvc.perform(put("/api/hr-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(hrInfo)))
            .andExpect(status().isBadRequest());

        // Validate the HrInfo in the database
        List<HrInfo> hrInfoList = hrInfoRepository.findAll();
        assertThat(hrInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteHrInfo() throws Exception {
        // Initialize the database
        hrInfoRepository.saveAndFlush(hrInfo);

        int databaseSizeBeforeDelete = hrInfoRepository.findAll().size();

        // Delete the hrInfo
        restHrInfoMockMvc.perform(delete("/api/hr-infos/{id}", hrInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<HrInfo> hrInfoList = hrInfoRepository.findAll();
        assertThat(hrInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(HrInfo.class);
        HrInfo hrInfo1 = new HrInfo();
        hrInfo1.setId(1L);
        HrInfo hrInfo2 = new HrInfo();
        hrInfo2.setId(hrInfo1.getId());
        assertThat(hrInfo1).isEqualTo(hrInfo2);
        hrInfo2.setId(2L);
        assertThat(hrInfo1).isNotEqualTo(hrInfo2);
        hrInfo1.setId(null);
        assertThat(hrInfo1).isNotEqualTo(hrInfo2);
    }
}
