package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ExternalUrls;
import fr.ippon.kompetitors.repository.ExternalUrlsRepository;
import fr.ippon.kompetitors.service.ExternalUrlsService;
import fr.ippon.kompetitors.service.dto.ExternalUrlsDTO;
import fr.ippon.kompetitors.service.mapper.ExternalUrlsMapper;
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
 * Integration tests for the {@link ExternalUrlsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ExternalUrlsResourceIT {

    private static final String DEFAULT_FACEBOOK_URL = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_URL = "BBBBBBBBBB";

    private static final String DEFAULT_TWITTER_URL = "AAAAAAAAAA";
    private static final String UPDATED_TWITTER_URL = "BBBBBBBBBB";

    private static final String DEFAULT_INSTAGRAM_URL = "AAAAAAAAAA";
    private static final String UPDATED_INSTAGRAM_URL = "BBBBBBBBBB";

    private static final String DEFAULT_YOUTUBE_URL = "AAAAAAAAAA";
    private static final String UPDATED_YOUTUBE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_LINKEDIN_URL = "AAAAAAAAAA";
    private static final String UPDATED_LINKEDIN_URL = "BBBBBBBBBB";

    private static final String DEFAULT_GITHUB_URL = "AAAAAAAAAA";
    private static final String UPDATED_GITHUB_URL = "BBBBBBBBBB";

    private static final String DEFAULT_BLOG_FEED = "AAAAAAAAAA";
    private static final String UPDATED_BLOG_FEED = "BBBBBBBBBB";

    private static final String DEFAULT_GOOGLE_ALERTS_FEED = "AAAAAAAAAA";
    private static final String UPDATED_GOOGLE_ALERTS_FEED = "BBBBBBBBBB";

    @Autowired
    private ExternalUrlsRepository externalUrlsRepository;

    @Autowired
    private ExternalUrlsMapper externalUrlsMapper;

    @Autowired
    private ExternalUrlsService externalUrlsService;

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

    private MockMvc restExternalUrlsMockMvc;

    private ExternalUrls externalUrls;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ExternalUrlsResource externalUrlsResource = new ExternalUrlsResource(externalUrlsService);
        this.restExternalUrlsMockMvc = MockMvcBuilders.standaloneSetup(externalUrlsResource)
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
    public static ExternalUrls createEntity(EntityManager em) {
        ExternalUrls externalUrls = new ExternalUrls()
            .facebookUrl(DEFAULT_FACEBOOK_URL)
            .twitterUrl(DEFAULT_TWITTER_URL)
            .instagramUrl(DEFAULT_INSTAGRAM_URL)
            .youtubeUrl(DEFAULT_YOUTUBE_URL)
            .linkedinUrl(DEFAULT_LINKEDIN_URL)
            .githubUrl(DEFAULT_GITHUB_URL)
            .blogFeed(DEFAULT_BLOG_FEED)
            .googleAlertsFeed(DEFAULT_GOOGLE_ALERTS_FEED);
        return externalUrls;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ExternalUrls createUpdatedEntity(EntityManager em) {
        ExternalUrls externalUrls = new ExternalUrls()
            .facebookUrl(UPDATED_FACEBOOK_URL)
            .twitterUrl(UPDATED_TWITTER_URL)
            .instagramUrl(UPDATED_INSTAGRAM_URL)
            .youtubeUrl(UPDATED_YOUTUBE_URL)
            .linkedinUrl(UPDATED_LINKEDIN_URL)
            .githubUrl(UPDATED_GITHUB_URL)
            .blogFeed(UPDATED_BLOG_FEED)
            .googleAlertsFeed(UPDATED_GOOGLE_ALERTS_FEED);
        return externalUrls;
    }

    @BeforeEach
    public void initTest() {
        externalUrls = createEntity(em);
    }

    @Test
    @Transactional
    public void createExternalUrls() throws Exception {
        int databaseSizeBeforeCreate = externalUrlsRepository.findAll().size();

        // Create the ExternalUrls
        ExternalUrlsDTO externalUrlsDTO = externalUrlsMapper.toDto(externalUrls);
        restExternalUrlsMockMvc.perform(post("/api/external-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalUrlsDTO)))
            .andExpect(status().isCreated());

        // Validate the ExternalUrls in the database
        List<ExternalUrls> externalUrlsList = externalUrlsRepository.findAll();
        assertThat(externalUrlsList).hasSize(databaseSizeBeforeCreate + 1);
        ExternalUrls testExternalUrls = externalUrlsList.get(externalUrlsList.size() - 1);
        assertThat(testExternalUrls.getFacebookUrl()).isEqualTo(DEFAULT_FACEBOOK_URL);
        assertThat(testExternalUrls.getTwitterUrl()).isEqualTo(DEFAULT_TWITTER_URL);
        assertThat(testExternalUrls.getInstagramUrl()).isEqualTo(DEFAULT_INSTAGRAM_URL);
        assertThat(testExternalUrls.getYoutubeUrl()).isEqualTo(DEFAULT_YOUTUBE_URL);
        assertThat(testExternalUrls.getLinkedinUrl()).isEqualTo(DEFAULT_LINKEDIN_URL);
        assertThat(testExternalUrls.getGithubUrl()).isEqualTo(DEFAULT_GITHUB_URL);
        assertThat(testExternalUrls.getBlogFeed()).isEqualTo(DEFAULT_BLOG_FEED);
        assertThat(testExternalUrls.getGoogleAlertsFeed()).isEqualTo(DEFAULT_GOOGLE_ALERTS_FEED);
    }

    @Test
    @Transactional
    public void createExternalUrlsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = externalUrlsRepository.findAll().size();

        // Create the ExternalUrls with an existing ID
        externalUrls.setId(1L);
        ExternalUrlsDTO externalUrlsDTO = externalUrlsMapper.toDto(externalUrls);

        // An entity with an existing ID cannot be created, so this API call must fail
        restExternalUrlsMockMvc.perform(post("/api/external-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalUrlsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExternalUrls in the database
        List<ExternalUrls> externalUrlsList = externalUrlsRepository.findAll();
        assertThat(externalUrlsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllExternalUrls() throws Exception {
        // Initialize the database
        externalUrlsRepository.saveAndFlush(externalUrls);

        // Get all the externalUrlsList
        restExternalUrlsMockMvc.perform(get("/api/external-urls?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(externalUrls.getId().intValue())))
            .andExpect(jsonPath("$.[*].facebookUrl").value(hasItem(DEFAULT_FACEBOOK_URL)))
            .andExpect(jsonPath("$.[*].twitterUrl").value(hasItem(DEFAULT_TWITTER_URL)))
            .andExpect(jsonPath("$.[*].instagramUrl").value(hasItem(DEFAULT_INSTAGRAM_URL)))
            .andExpect(jsonPath("$.[*].youtubeUrl").value(hasItem(DEFAULT_YOUTUBE_URL)))
            .andExpect(jsonPath("$.[*].linkedinUrl").value(hasItem(DEFAULT_LINKEDIN_URL)))
            .andExpect(jsonPath("$.[*].githubUrl").value(hasItem(DEFAULT_GITHUB_URL)))
            .andExpect(jsonPath("$.[*].blogFeed").value(hasItem(DEFAULT_BLOG_FEED)))
            .andExpect(jsonPath("$.[*].googleAlertsFeed").value(hasItem(DEFAULT_GOOGLE_ALERTS_FEED)));
    }
    
    @Test
    @Transactional
    public void getExternalUrls() throws Exception {
        // Initialize the database
        externalUrlsRepository.saveAndFlush(externalUrls);

        // Get the externalUrls
        restExternalUrlsMockMvc.perform(get("/api/external-urls/{id}", externalUrls.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(externalUrls.getId().intValue()))
            .andExpect(jsonPath("$.facebookUrl").value(DEFAULT_FACEBOOK_URL))
            .andExpect(jsonPath("$.twitterUrl").value(DEFAULT_TWITTER_URL))
            .andExpect(jsonPath("$.instagramUrl").value(DEFAULT_INSTAGRAM_URL))
            .andExpect(jsonPath("$.youtubeUrl").value(DEFAULT_YOUTUBE_URL))
            .andExpect(jsonPath("$.linkedinUrl").value(DEFAULT_LINKEDIN_URL))
            .andExpect(jsonPath("$.githubUrl").value(DEFAULT_GITHUB_URL))
            .andExpect(jsonPath("$.blogFeed").value(DEFAULT_BLOG_FEED))
            .andExpect(jsonPath("$.googleAlertsFeed").value(DEFAULT_GOOGLE_ALERTS_FEED));
    }

    @Test
    @Transactional
    public void getNonExistingExternalUrls() throws Exception {
        // Get the externalUrls
        restExternalUrlsMockMvc.perform(get("/api/external-urls/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateExternalUrls() throws Exception {
        // Initialize the database
        externalUrlsRepository.saveAndFlush(externalUrls);

        int databaseSizeBeforeUpdate = externalUrlsRepository.findAll().size();

        // Update the externalUrls
        ExternalUrls updatedExternalUrls = externalUrlsRepository.findById(externalUrls.getId()).get();
        // Disconnect from session so that the updates on updatedExternalUrls are not directly saved in db
        em.detach(updatedExternalUrls);
        updatedExternalUrls
            .facebookUrl(UPDATED_FACEBOOK_URL)
            .twitterUrl(UPDATED_TWITTER_URL)
            .instagramUrl(UPDATED_INSTAGRAM_URL)
            .youtubeUrl(UPDATED_YOUTUBE_URL)
            .linkedinUrl(UPDATED_LINKEDIN_URL)
            .githubUrl(UPDATED_GITHUB_URL)
            .blogFeed(UPDATED_BLOG_FEED)
            .googleAlertsFeed(UPDATED_GOOGLE_ALERTS_FEED);
        ExternalUrlsDTO externalUrlsDTO = externalUrlsMapper.toDto(updatedExternalUrls);

        restExternalUrlsMockMvc.perform(put("/api/external-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalUrlsDTO)))
            .andExpect(status().isOk());

        // Validate the ExternalUrls in the database
        List<ExternalUrls> externalUrlsList = externalUrlsRepository.findAll();
        assertThat(externalUrlsList).hasSize(databaseSizeBeforeUpdate);
        ExternalUrls testExternalUrls = externalUrlsList.get(externalUrlsList.size() - 1);
        assertThat(testExternalUrls.getFacebookUrl()).isEqualTo(UPDATED_FACEBOOK_URL);
        assertThat(testExternalUrls.getTwitterUrl()).isEqualTo(UPDATED_TWITTER_URL);
        assertThat(testExternalUrls.getInstagramUrl()).isEqualTo(UPDATED_INSTAGRAM_URL);
        assertThat(testExternalUrls.getYoutubeUrl()).isEqualTo(UPDATED_YOUTUBE_URL);
        assertThat(testExternalUrls.getLinkedinUrl()).isEqualTo(UPDATED_LINKEDIN_URL);
        assertThat(testExternalUrls.getGithubUrl()).isEqualTo(UPDATED_GITHUB_URL);
        assertThat(testExternalUrls.getBlogFeed()).isEqualTo(UPDATED_BLOG_FEED);
        assertThat(testExternalUrls.getGoogleAlertsFeed()).isEqualTo(UPDATED_GOOGLE_ALERTS_FEED);
    }

    @Test
    @Transactional
    public void updateNonExistingExternalUrls() throws Exception {
        int databaseSizeBeforeUpdate = externalUrlsRepository.findAll().size();

        // Create the ExternalUrls
        ExternalUrlsDTO externalUrlsDTO = externalUrlsMapper.toDto(externalUrls);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restExternalUrlsMockMvc.perform(put("/api/external-urls")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(externalUrlsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ExternalUrls in the database
        List<ExternalUrls> externalUrlsList = externalUrlsRepository.findAll();
        assertThat(externalUrlsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteExternalUrls() throws Exception {
        // Initialize the database
        externalUrlsRepository.saveAndFlush(externalUrls);

        int databaseSizeBeforeDelete = externalUrlsRepository.findAll().size();

        // Delete the externalUrls
        restExternalUrlsMockMvc.perform(delete("/api/external-urls/{id}", externalUrls.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ExternalUrls> externalUrlsList = externalUrlsRepository.findAll();
        assertThat(externalUrlsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExternalUrls.class);
        ExternalUrls externalUrls1 = new ExternalUrls();
        externalUrls1.setId(1L);
        ExternalUrls externalUrls2 = new ExternalUrls();
        externalUrls2.setId(externalUrls1.getId());
        assertThat(externalUrls1).isEqualTo(externalUrls2);
        externalUrls2.setId(2L);
        assertThat(externalUrls1).isNotEqualTo(externalUrls2);
        externalUrls1.setId(null);
        assertThat(externalUrls1).isNotEqualTo(externalUrls2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ExternalUrlsDTO.class);
        ExternalUrlsDTO externalUrlsDTO1 = new ExternalUrlsDTO();
        externalUrlsDTO1.setId(1L);
        ExternalUrlsDTO externalUrlsDTO2 = new ExternalUrlsDTO();
        assertThat(externalUrlsDTO1).isNotEqualTo(externalUrlsDTO2);
        externalUrlsDTO2.setId(externalUrlsDTO1.getId());
        assertThat(externalUrlsDTO1).isEqualTo(externalUrlsDTO2);
        externalUrlsDTO2.setId(2L);
        assertThat(externalUrlsDTO1).isNotEqualTo(externalUrlsDTO2);
        externalUrlsDTO1.setId(null);
        assertThat(externalUrlsDTO1).isNotEqualTo(externalUrlsDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(externalUrlsMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(externalUrlsMapper.fromId(null)).isNull();
    }
}
