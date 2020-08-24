package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.PrInfo;
import fr.ippon.kompetitors.repository.PrInfoRepository;
import fr.ippon.kompetitors.service.PrInfoService;
import fr.ippon.kompetitors.service.dto.PrInfoDTO;
import fr.ippon.kompetitors.service.mapper.PrInfoMapper;
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
 * Integration tests for the {@link PrInfoResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class PrInfoResourceIT {

    private static final LocalDate DEFAULT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_MARKETING_WORKFORCE = 1;
    private static final Integer UPDATED_MARKETING_WORKFORCE = 2;

    private static final Long DEFAULT_MARKETING_BUDGET = 1L;
    private static final Long UPDATED_MARKETING_BUDGET = 2L;

    private static final Integer DEFAULT_EXPERIENCE_FEEDBACK = 1;
    private static final Integer UPDATED_EXPERIENCE_FEEDBACK = 2;

    private static final Long DEFAULT_LINKED_IN_SUBSCRIBERS = 1L;
    private static final Long UPDATED_LINKED_IN_SUBSCRIBERS = 2L;

    private static final Float DEFAULT_LINKED_IN_ENGAGE_RATE = 1F;
    private static final Float UPDATED_LINKED_IN_ENGAGE_RATE = 2F;

    private static final Integer DEFAULT_LINKED_IN_POST_WEEK = 1;
    private static final Integer UPDATED_LINKED_IN_POST_WEEK = 2;

    private static final Integer DEFAULT_LINKED_IN_POST_DAY = 1;
    private static final Integer UPDATED_LINKED_IN_POST_DAY = 2;

    private static final Long DEFAULT_TWITTER_FOLLOWERS = 1L;
    private static final Long UPDATED_TWITTER_FOLLOWERS = 2L;

    private static final Integer DEFAULT_TWITTER_POST_WEEK = 1;
    private static final Integer UPDATED_TWITTER_POST_WEEK = 2;

    private static final Integer DEFAULT_TWITTER_POST_DAY = 1;
    private static final Integer UPDATED_TWITTER_POST_DAY = 2;

    private static final Long DEFAULT_INSTAGRAM_FOLLOWERS = 1L;
    private static final Long UPDATED_INSTAGRAM_FOLLOWERS = 2L;

    @Autowired
    private PrInfoRepository prInfoRepository;

    @Autowired
    private PrInfoMapper prInfoMapper;

    @Autowired
    private PrInfoService prInfoService;

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

    private MockMvc restPrInfoMockMvc;

    private PrInfo prInfo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrInfoResource prInfoResource = new PrInfoResource(prInfoService);
        this.restPrInfoMockMvc = MockMvcBuilders.standaloneSetup(prInfoResource)
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
    public static PrInfo createEntity(EntityManager em) {
        PrInfo prInfo = new PrInfo()
            .date(DEFAULT_DATE)
            .marketingWorkforce(DEFAULT_MARKETING_WORKFORCE)
            .marketingBudget(DEFAULT_MARKETING_BUDGET)
            .experienceFeedback(DEFAULT_EXPERIENCE_FEEDBACK)
            .linkedInSubscribers(DEFAULT_LINKED_IN_SUBSCRIBERS)
            .linkedInEngageRate(DEFAULT_LINKED_IN_ENGAGE_RATE)
            .linkedInPostWeek(DEFAULT_LINKED_IN_POST_WEEK)
            .linkedInPostDay(DEFAULT_LINKED_IN_POST_DAY)
            .twitterFollowers(DEFAULT_TWITTER_FOLLOWERS)
            .twitterPostWeek(DEFAULT_TWITTER_POST_WEEK)
            .twitterPostDay(DEFAULT_TWITTER_POST_DAY)
            .instagramFollowers(DEFAULT_INSTAGRAM_FOLLOWERS);
        return prInfo;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrInfo createUpdatedEntity(EntityManager em) {
        PrInfo prInfo = new PrInfo()
            .date(UPDATED_DATE)
            .marketingWorkforce(UPDATED_MARKETING_WORKFORCE)
            .marketingBudget(UPDATED_MARKETING_BUDGET)
            .experienceFeedback(UPDATED_EXPERIENCE_FEEDBACK)
            .linkedInSubscribers(UPDATED_LINKED_IN_SUBSCRIBERS)
            .linkedInEngageRate(UPDATED_LINKED_IN_ENGAGE_RATE)
            .linkedInPostWeek(UPDATED_LINKED_IN_POST_WEEK)
            .linkedInPostDay(UPDATED_LINKED_IN_POST_DAY)
            .twitterFollowers(UPDATED_TWITTER_FOLLOWERS)
            .twitterPostWeek(UPDATED_TWITTER_POST_WEEK)
            .twitterPostDay(UPDATED_TWITTER_POST_DAY)
            .instagramFollowers(UPDATED_INSTAGRAM_FOLLOWERS);
        return prInfo;
    }

    @BeforeEach
    public void initTest() {
        prInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrInfo() throws Exception {
        int databaseSizeBeforeCreate = prInfoRepository.findAll().size();

        // Create the PrInfo
        PrInfoDTO prInfoDTO = prInfoMapper.toDto(prInfo);
        restPrInfoMockMvc.perform(post("/api/pr-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the PrInfo in the database
        List<PrInfo> prInfoList = prInfoRepository.findAll();
        assertThat(prInfoList).hasSize(databaseSizeBeforeCreate + 1);
        PrInfo testPrInfo = prInfoList.get(prInfoList.size() - 1);
        assertThat(testPrInfo.getDate()).isEqualTo(DEFAULT_DATE);
        assertThat(testPrInfo.getMarketingWorkforce()).isEqualTo(DEFAULT_MARKETING_WORKFORCE);
        assertThat(testPrInfo.getMarketingBudget()).isEqualTo(DEFAULT_MARKETING_BUDGET);
        assertThat(testPrInfo.getExperienceFeedback()).isEqualTo(DEFAULT_EXPERIENCE_FEEDBACK);
        assertThat(testPrInfo.getLinkedInSubscribers()).isEqualTo(DEFAULT_LINKED_IN_SUBSCRIBERS);
        assertThat(testPrInfo.getLinkedInEngageRate()).isEqualTo(DEFAULT_LINKED_IN_ENGAGE_RATE);
        assertThat(testPrInfo.getLinkedInPostWeek()).isEqualTo(DEFAULT_LINKED_IN_POST_WEEK);
        assertThat(testPrInfo.getLinkedInPostDay()).isEqualTo(DEFAULT_LINKED_IN_POST_DAY);
        assertThat(testPrInfo.getTwitterFollowers()).isEqualTo(DEFAULT_TWITTER_FOLLOWERS);
        assertThat(testPrInfo.getTwitterPostWeek()).isEqualTo(DEFAULT_TWITTER_POST_WEEK);
        assertThat(testPrInfo.getTwitterPostDay()).isEqualTo(DEFAULT_TWITTER_POST_DAY);
        assertThat(testPrInfo.getInstagramFollowers()).isEqualTo(DEFAULT_INSTAGRAM_FOLLOWERS);
    }

    @Test
    @Transactional
    public void createPrInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prInfoRepository.findAll().size();

        // Create the PrInfo with an existing ID
        prInfo.setId(1L);
        PrInfoDTO prInfoDTO = prInfoMapper.toDto(prInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrInfoMockMvc.perform(post("/api/pr-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrInfo in the database
        List<PrInfo> prInfoList = prInfoRepository.findAll();
        assertThat(prInfoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPrInfos() throws Exception {
        // Initialize the database
        prInfoRepository.saveAndFlush(prInfo);

        // Get all the prInfoList
        restPrInfoMockMvc.perform(get("/api/pr-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].date").value(hasItem(DEFAULT_DATE.toString())))
            .andExpect(jsonPath("$.[*].marketingWorkforce").value(hasItem(DEFAULT_MARKETING_WORKFORCE)))
            .andExpect(jsonPath("$.[*].marketingBudget").value(hasItem(DEFAULT_MARKETING_BUDGET.intValue())))
            .andExpect(jsonPath("$.[*].experienceFeedback").value(hasItem(DEFAULT_EXPERIENCE_FEEDBACK)))
            .andExpect(jsonPath("$.[*].linkedInSubscribers").value(hasItem(DEFAULT_LINKED_IN_SUBSCRIBERS.intValue())))
            .andExpect(jsonPath("$.[*].linkedInEngageRate").value(hasItem(DEFAULT_LINKED_IN_ENGAGE_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].linkedInPostWeek").value(hasItem(DEFAULT_LINKED_IN_POST_WEEK)))
            .andExpect(jsonPath("$.[*].linkedInPostDay").value(hasItem(DEFAULT_LINKED_IN_POST_DAY)))
            .andExpect(jsonPath("$.[*].twitterFollowers").value(hasItem(DEFAULT_TWITTER_FOLLOWERS.intValue())))
            .andExpect(jsonPath("$.[*].twitterPostWeek").value(hasItem(DEFAULT_TWITTER_POST_WEEK)))
            .andExpect(jsonPath("$.[*].twitterPostDay").value(hasItem(DEFAULT_TWITTER_POST_DAY)))
            .andExpect(jsonPath("$.[*].instagramFollowers").value(hasItem(DEFAULT_INSTAGRAM_FOLLOWERS.intValue())));
    }
    
    @Test
    @Transactional
    public void getPrInfo() throws Exception {
        // Initialize the database
        prInfoRepository.saveAndFlush(prInfo);

        // Get the prInfo
        restPrInfoMockMvc.perform(get("/api/pr-infos/{id}", prInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(prInfo.getId().intValue()))
            .andExpect(jsonPath("$.date").value(DEFAULT_DATE.toString()))
            .andExpect(jsonPath("$.marketingWorkforce").value(DEFAULT_MARKETING_WORKFORCE))
            .andExpect(jsonPath("$.marketingBudget").value(DEFAULT_MARKETING_BUDGET.intValue()))
            .andExpect(jsonPath("$.experienceFeedback").value(DEFAULT_EXPERIENCE_FEEDBACK))
            .andExpect(jsonPath("$.linkedInSubscribers").value(DEFAULT_LINKED_IN_SUBSCRIBERS.intValue()))
            .andExpect(jsonPath("$.linkedInEngageRate").value(DEFAULT_LINKED_IN_ENGAGE_RATE.doubleValue()))
            .andExpect(jsonPath("$.linkedInPostWeek").value(DEFAULT_LINKED_IN_POST_WEEK))
            .andExpect(jsonPath("$.linkedInPostDay").value(DEFAULT_LINKED_IN_POST_DAY))
            .andExpect(jsonPath("$.twitterFollowers").value(DEFAULT_TWITTER_FOLLOWERS.intValue()))
            .andExpect(jsonPath("$.twitterPostWeek").value(DEFAULT_TWITTER_POST_WEEK))
            .andExpect(jsonPath("$.twitterPostDay").value(DEFAULT_TWITTER_POST_DAY))
            .andExpect(jsonPath("$.instagramFollowers").value(DEFAULT_INSTAGRAM_FOLLOWERS.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrInfo() throws Exception {
        // Get the prInfo
        restPrInfoMockMvc.perform(get("/api/pr-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrInfo() throws Exception {
        // Initialize the database
        prInfoRepository.saveAndFlush(prInfo);

        int databaseSizeBeforeUpdate = prInfoRepository.findAll().size();

        // Update the prInfo
        PrInfo updatedPrInfo = prInfoRepository.findById(prInfo.getId()).get();
        // Disconnect from session so that the updates on updatedPrInfo are not directly saved in db
        em.detach(updatedPrInfo);
        updatedPrInfo
            .date(UPDATED_DATE)
            .marketingWorkforce(UPDATED_MARKETING_WORKFORCE)
            .marketingBudget(UPDATED_MARKETING_BUDGET)
            .experienceFeedback(UPDATED_EXPERIENCE_FEEDBACK)
            .linkedInSubscribers(UPDATED_LINKED_IN_SUBSCRIBERS)
            .linkedInEngageRate(UPDATED_LINKED_IN_ENGAGE_RATE)
            .linkedInPostWeek(UPDATED_LINKED_IN_POST_WEEK)
            .linkedInPostDay(UPDATED_LINKED_IN_POST_DAY)
            .twitterFollowers(UPDATED_TWITTER_FOLLOWERS)
            .twitterPostWeek(UPDATED_TWITTER_POST_WEEK)
            .twitterPostDay(UPDATED_TWITTER_POST_DAY)
            .instagramFollowers(UPDATED_INSTAGRAM_FOLLOWERS);
        PrInfoDTO prInfoDTO = prInfoMapper.toDto(updatedPrInfo);

        restPrInfoMockMvc.perform(put("/api/pr-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prInfoDTO)))
            .andExpect(status().isOk());

        // Validate the PrInfo in the database
        List<PrInfo> prInfoList = prInfoRepository.findAll();
        assertThat(prInfoList).hasSize(databaseSizeBeforeUpdate);
        PrInfo testPrInfo = prInfoList.get(prInfoList.size() - 1);
        assertThat(testPrInfo.getDate()).isEqualTo(UPDATED_DATE);
        assertThat(testPrInfo.getMarketingWorkforce()).isEqualTo(UPDATED_MARKETING_WORKFORCE);
        assertThat(testPrInfo.getMarketingBudget()).isEqualTo(UPDATED_MARKETING_BUDGET);
        assertThat(testPrInfo.getExperienceFeedback()).isEqualTo(UPDATED_EXPERIENCE_FEEDBACK);
        assertThat(testPrInfo.getLinkedInSubscribers()).isEqualTo(UPDATED_LINKED_IN_SUBSCRIBERS);
        assertThat(testPrInfo.getLinkedInEngageRate()).isEqualTo(UPDATED_LINKED_IN_ENGAGE_RATE);
        assertThat(testPrInfo.getLinkedInPostWeek()).isEqualTo(UPDATED_LINKED_IN_POST_WEEK);
        assertThat(testPrInfo.getLinkedInPostDay()).isEqualTo(UPDATED_LINKED_IN_POST_DAY);
        assertThat(testPrInfo.getTwitterFollowers()).isEqualTo(UPDATED_TWITTER_FOLLOWERS);
        assertThat(testPrInfo.getTwitterPostWeek()).isEqualTo(UPDATED_TWITTER_POST_WEEK);
        assertThat(testPrInfo.getTwitterPostDay()).isEqualTo(UPDATED_TWITTER_POST_DAY);
        assertThat(testPrInfo.getInstagramFollowers()).isEqualTo(UPDATED_INSTAGRAM_FOLLOWERS);
    }

    @Test
    @Transactional
    public void updateNonExistingPrInfo() throws Exception {
        int databaseSizeBeforeUpdate = prInfoRepository.findAll().size();

        // Create the PrInfo
        PrInfoDTO prInfoDTO = prInfoMapper.toDto(prInfo);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrInfoMockMvc.perform(put("/api/pr-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(prInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PrInfo in the database
        List<PrInfo> prInfoList = prInfoRepository.findAll();
        assertThat(prInfoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrInfo() throws Exception {
        // Initialize the database
        prInfoRepository.saveAndFlush(prInfo);

        int databaseSizeBeforeDelete = prInfoRepository.findAll().size();

        // Delete the prInfo
        restPrInfoMockMvc.perform(delete("/api/pr-infos/{id}", prInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrInfo> prInfoList = prInfoRepository.findAll();
        assertThat(prInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrInfo.class);
        PrInfo prInfo1 = new PrInfo();
        prInfo1.setId(1L);
        PrInfo prInfo2 = new PrInfo();
        prInfo2.setId(prInfo1.getId());
        assertThat(prInfo1).isEqualTo(prInfo2);
        prInfo2.setId(2L);
        assertThat(prInfo1).isNotEqualTo(prInfo2);
        prInfo1.setId(null);
        assertThat(prInfo1).isNotEqualTo(prInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrInfoDTO.class);
        PrInfoDTO prInfoDTO1 = new PrInfoDTO();
        prInfoDTO1.setId(1L);
        PrInfoDTO prInfoDTO2 = new PrInfoDTO();
        assertThat(prInfoDTO1).isNotEqualTo(prInfoDTO2);
        prInfoDTO2.setId(prInfoDTO1.getId());
        assertThat(prInfoDTO1).isEqualTo(prInfoDTO2);
        prInfoDTO2.setId(2L);
        assertThat(prInfoDTO1).isNotEqualTo(prInfoDTO2);
        prInfoDTO1.setId(null);
        assertThat(prInfoDTO1).isNotEqualTo(prInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(prInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(prInfoMapper.fromId(null)).isNull();
    }
}
