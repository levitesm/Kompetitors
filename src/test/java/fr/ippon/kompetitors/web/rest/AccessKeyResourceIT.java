package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.AccessKey;
import fr.ippon.kompetitors.repository.AccessKeyRepository;
import fr.ippon.kompetitors.service.AccessKeyService;
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
 * Integration tests for the {@link AccessKeyResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class AccessKeyResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private AccessKeyRepository accessKeyRepository;

    @Autowired
    private AccessKeyService accessKeyService;

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

    private MockMvc restAccessKeyMockMvc;

    private AccessKey accessKey;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AccessKeyResource accessKeyResource = new AccessKeyResource(accessKeyService);
        this.restAccessKeyMockMvc = MockMvcBuilders.standaloneSetup(accessKeyResource)
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
    public static AccessKey createEntity(EntityManager em) {
        AccessKey accessKey = new AccessKey()
            .name(DEFAULT_NAME)
            .description(DEFAULT_DESCRIPTION);
        return accessKey;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AccessKey createUpdatedEntity(EntityManager em) {
        AccessKey accessKey = new AccessKey()
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);
        return accessKey;
    }

    @BeforeEach
    public void initTest() {
        accessKey = createEntity(em);
    }

    @Test
    @Transactional
    public void createAccessKey() throws Exception {
        int databaseSizeBeforeCreate = accessKeyRepository.findAll().size();

        // Create the AccessKey
        restAccessKeyMockMvc.perform(post("/api/access-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessKey)))
            .andExpect(status().isCreated());

        // Validate the AccessKey in the database
        List<AccessKey> accessKeyList = accessKeyRepository.findAll();
        assertThat(accessKeyList).hasSize(databaseSizeBeforeCreate + 1);
        AccessKey testAccessKey = accessKeyList.get(accessKeyList.size() - 1);
        assertThat(testAccessKey.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testAccessKey.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
    }

    @Test
    @Transactional
    public void createAccessKeyWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = accessKeyRepository.findAll().size();

        // Create the AccessKey with an existing ID
        accessKey.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAccessKeyMockMvc.perform(post("/api/access-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessKey)))
            .andExpect(status().isBadRequest());

        // Validate the AccessKey in the database
        List<AccessKey> accessKeyList = accessKeyRepository.findAll();
        assertThat(accessKeyList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = accessKeyRepository.findAll().size();
        // set the field null
        accessKey.setName(null);

        // Create the AccessKey, which fails.

        restAccessKeyMockMvc.perform(post("/api/access-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessKey)))
            .andExpect(status().isBadRequest());

        List<AccessKey> accessKeyList = accessKeyRepository.findAll();
        assertThat(accessKeyList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAccessKeys() throws Exception {
        // Initialize the database
        accessKeyRepository.saveAndFlush(accessKey);

        // Get all the accessKeyList
        restAccessKeyMockMvc.perform(get("/api/access-keys?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(accessKey.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }
    
    @Test
    @Transactional
    public void getAccessKey() throws Exception {
        // Initialize the database
        accessKeyRepository.saveAndFlush(accessKey);

        // Get the accessKey
        restAccessKeyMockMvc.perform(get("/api/access-keys/{id}", accessKey.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(accessKey.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION));
    }

    @Test
    @Transactional
    public void getNonExistingAccessKey() throws Exception {
        // Get the accessKey
        restAccessKeyMockMvc.perform(get("/api/access-keys/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAccessKey() throws Exception {
        // Initialize the database
        accessKeyService.save(accessKey);

        int databaseSizeBeforeUpdate = accessKeyRepository.findAll().size();

        // Update the accessKey
        AccessKey updatedAccessKey = accessKeyRepository.findById(accessKey.getId()).get();
        // Disconnect from session so that the updates on updatedAccessKey are not directly saved in db
        em.detach(updatedAccessKey);
        updatedAccessKey
            .name(UPDATED_NAME)
            .description(UPDATED_DESCRIPTION);

        restAccessKeyMockMvc.perform(put("/api/access-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAccessKey)))
            .andExpect(status().isOk());

        // Validate the AccessKey in the database
        List<AccessKey> accessKeyList = accessKeyRepository.findAll();
        assertThat(accessKeyList).hasSize(databaseSizeBeforeUpdate);
        AccessKey testAccessKey = accessKeyList.get(accessKeyList.size() - 1);
        assertThat(testAccessKey.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testAccessKey.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void updateNonExistingAccessKey() throws Exception {
        int databaseSizeBeforeUpdate = accessKeyRepository.findAll().size();

        // Create the AccessKey

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAccessKeyMockMvc.perform(put("/api/access-keys")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(accessKey)))
            .andExpect(status().isBadRequest());

        // Validate the AccessKey in the database
        List<AccessKey> accessKeyList = accessKeyRepository.findAll();
        assertThat(accessKeyList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAccessKey() throws Exception {
        // Initialize the database
        accessKeyService.save(accessKey);

        int databaseSizeBeforeDelete = accessKeyRepository.findAll().size();

        // Delete the accessKey
        restAccessKeyMockMvc.perform(delete("/api/access-keys/{id}", accessKey.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AccessKey> accessKeyList = accessKeyRepository.findAll();
        assertThat(accessKeyList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AccessKey.class);
        AccessKey accessKey1 = new AccessKey();
        accessKey1.setId(1L);
        AccessKey accessKey2 = new AccessKey();
        accessKey2.setId(accessKey1.getId());
        assertThat(accessKey1).isEqualTo(accessKey2);
        accessKey2.setId(2L);
        assertThat(accessKey1).isNotEqualTo(accessKey2);
        accessKey1.setId(null);
        assertThat(accessKey1).isNotEqualTo(accessKey2);
    }
}
