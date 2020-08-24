package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.AnnualAccount;
import fr.ippon.kompetitors.repository.AnnualAccountRepository;
import fr.ippon.kompetitors.service.AnnualAccountService;
import fr.ippon.kompetitors.service.dto.AnnualAccountDTO;
import fr.ippon.kompetitors.service.mapper.AnnualAccountMapper;
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
 * Integration tests for the {@link AnnualAccountResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class AnnualAccountResourceIT {

    private static final String DEFAULT_SIREN = "AAAAAAAAAA";
    private static final String UPDATED_SIREN = "BBBBBBBBBB";

    private static final Integer DEFAULT_YEAR = 1;
    private static final Integer UPDATED_YEAR = 2;

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_VALUE = 1L;
    private static final Long UPDATED_VALUE = 2L;

    @Autowired
    private AnnualAccountRepository annualAccountRepository;

    @Autowired
    private AnnualAccountMapper annualAccountMapper;

    @Autowired
    private AnnualAccountService annualAccountService;

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

    private MockMvc restAnnualAccountMockMvc;

    private AnnualAccount annualAccount;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnnualAccountResource annualAccountResource = new AnnualAccountResource(annualAccountService);
        this.restAnnualAccountMockMvc = MockMvcBuilders.standaloneSetup(annualAccountResource)
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
    public static AnnualAccount createEntity(EntityManager em) {
        AnnualAccount annualAccount = new AnnualAccount()
            .siren(DEFAULT_SIREN)
            .year(DEFAULT_YEAR)
            .code(DEFAULT_CODE)
            .value(DEFAULT_VALUE);
        return annualAccount;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AnnualAccount createUpdatedEntity(EntityManager em) {
        AnnualAccount annualAccount = new AnnualAccount()
            .siren(UPDATED_SIREN)
            .year(UPDATED_YEAR)
            .code(UPDATED_CODE)
            .value(UPDATED_VALUE);
        return annualAccount;
    }

    @BeforeEach
    public void initTest() {
        annualAccount = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnnualAccount() throws Exception {
        int databaseSizeBeforeCreate = annualAccountRepository.findAll().size();

        // Create the AnnualAccount
        AnnualAccountDTO annualAccountDTO = annualAccountMapper.toDto(annualAccount);
        restAnnualAccountMockMvc.perform(post("/api/annual-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountDTO)))
            .andExpect(status().isCreated());

        // Validate the AnnualAccount in the database
        List<AnnualAccount> annualAccountList = annualAccountRepository.findAll();
        assertThat(annualAccountList).hasSize(databaseSizeBeforeCreate + 1);
        AnnualAccount testAnnualAccount = annualAccountList.get(annualAccountList.size() - 1);
        assertThat(testAnnualAccount.getSiren()).isEqualTo(DEFAULT_SIREN);
        assertThat(testAnnualAccount.getYear()).isEqualTo(DEFAULT_YEAR);
        assertThat(testAnnualAccount.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testAnnualAccount.getValue()).isEqualTo(DEFAULT_VALUE);
    }

    @Test
    @Transactional
    public void createAnnualAccountWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = annualAccountRepository.findAll().size();

        // Create the AnnualAccount with an existing ID
        annualAccount.setId(1L);
        AnnualAccountDTO annualAccountDTO = annualAccountMapper.toDto(annualAccount);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnnualAccountMockMvc.perform(post("/api/annual-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnnualAccount in the database
        List<AnnualAccount> annualAccountList = annualAccountRepository.findAll();
        assertThat(annualAccountList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkSirenIsRequired() throws Exception {
        int databaseSizeBeforeTest = annualAccountRepository.findAll().size();
        // set the field null
        annualAccount.setSiren(null);

        // Create the AnnualAccount, which fails.
        AnnualAccountDTO annualAccountDTO = annualAccountMapper.toDto(annualAccount);

        restAnnualAccountMockMvc.perform(post("/api/annual-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountDTO)))
            .andExpect(status().isBadRequest());

        List<AnnualAccount> annualAccountList = annualAccountRepository.findAll();
        assertThat(annualAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkYearIsRequired() throws Exception {
        int databaseSizeBeforeTest = annualAccountRepository.findAll().size();
        // set the field null
        annualAccount.setYear(null);

        // Create the AnnualAccount, which fails.
        AnnualAccountDTO annualAccountDTO = annualAccountMapper.toDto(annualAccount);

        restAnnualAccountMockMvc.perform(post("/api/annual-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountDTO)))
            .andExpect(status().isBadRequest());

        List<AnnualAccount> annualAccountList = annualAccountRepository.findAll();
        assertThat(annualAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = annualAccountRepository.findAll().size();
        // set the field null
        annualAccount.setCode(null);

        // Create the AnnualAccount, which fails.
        AnnualAccountDTO annualAccountDTO = annualAccountMapper.toDto(annualAccount);

        restAnnualAccountMockMvc.perform(post("/api/annual-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountDTO)))
            .andExpect(status().isBadRequest());

        List<AnnualAccount> annualAccountList = annualAccountRepository.findAll();
        assertThat(annualAccountList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAnnualAccounts() throws Exception {
        // Initialize the database
        annualAccountRepository.saveAndFlush(annualAccount);

        // Get all the annualAccountList
        restAnnualAccountMockMvc.perform(get("/api/annual-accounts?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(annualAccount.getId().intValue())))
            .andExpect(jsonPath("$.[*].siren").value(hasItem(DEFAULT_SIREN)))
            .andExpect(jsonPath("$.[*].year").value(hasItem(DEFAULT_YEAR)))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE)))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE.intValue())));
    }
    
    @Test
    @Transactional
    public void getAnnualAccount() throws Exception {
        // Initialize the database
        annualAccountRepository.saveAndFlush(annualAccount);

        // Get the annualAccount
        restAnnualAccountMockMvc.perform(get("/api/annual-accounts/{id}", annualAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(annualAccount.getId().intValue()))
            .andExpect(jsonPath("$.siren").value(DEFAULT_SIREN))
            .andExpect(jsonPath("$.year").value(DEFAULT_YEAR))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAnnualAccount() throws Exception {
        // Get the annualAccount
        restAnnualAccountMockMvc.perform(get("/api/annual-accounts/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnnualAccount() throws Exception {
        // Initialize the database
        annualAccountRepository.saveAndFlush(annualAccount);

        int databaseSizeBeforeUpdate = annualAccountRepository.findAll().size();

        // Update the annualAccount
        AnnualAccount updatedAnnualAccount = annualAccountRepository.findById(annualAccount.getId()).get();
        // Disconnect from session so that the updates on updatedAnnualAccount are not directly saved in db
        em.detach(updatedAnnualAccount);
        updatedAnnualAccount
            .siren(UPDATED_SIREN)
            .year(UPDATED_YEAR)
            .code(UPDATED_CODE)
            .value(UPDATED_VALUE);
        AnnualAccountDTO annualAccountDTO = annualAccountMapper.toDto(updatedAnnualAccount);

        restAnnualAccountMockMvc.perform(put("/api/annual-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountDTO)))
            .andExpect(status().isOk());

        // Validate the AnnualAccount in the database
        List<AnnualAccount> annualAccountList = annualAccountRepository.findAll();
        assertThat(annualAccountList).hasSize(databaseSizeBeforeUpdate);
        AnnualAccount testAnnualAccount = annualAccountList.get(annualAccountList.size() - 1);
        assertThat(testAnnualAccount.getSiren()).isEqualTo(UPDATED_SIREN);
        assertThat(testAnnualAccount.getYear()).isEqualTo(UPDATED_YEAR);
        assertThat(testAnnualAccount.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testAnnualAccount.getValue()).isEqualTo(UPDATED_VALUE);
    }

    @Test
    @Transactional
    public void updateNonExistingAnnualAccount() throws Exception {
        int databaseSizeBeforeUpdate = annualAccountRepository.findAll().size();

        // Create the AnnualAccount
        AnnualAccountDTO annualAccountDTO = annualAccountMapper.toDto(annualAccount);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAnnualAccountMockMvc.perform(put("/api/annual-accounts")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(annualAccountDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AnnualAccount in the database
        List<AnnualAccount> annualAccountList = annualAccountRepository.findAll();
        assertThat(annualAccountList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAnnualAccount() throws Exception {
        // Initialize the database
        annualAccountRepository.saveAndFlush(annualAccount);

        int databaseSizeBeforeDelete = annualAccountRepository.findAll().size();

        // Delete the annualAccount
        restAnnualAccountMockMvc.perform(delete("/api/annual-accounts/{id}", annualAccount.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AnnualAccount> annualAccountList = annualAccountRepository.findAll();
        assertThat(annualAccountList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnualAccount.class);
        AnnualAccount annualAccount1 = new AnnualAccount();
        annualAccount1.setId(1L);
        AnnualAccount annualAccount2 = new AnnualAccount();
        annualAccount2.setId(annualAccount1.getId());
        assertThat(annualAccount1).isEqualTo(annualAccount2);
        annualAccount2.setId(2L);
        assertThat(annualAccount1).isNotEqualTo(annualAccount2);
        annualAccount1.setId(null);
        assertThat(annualAccount1).isNotEqualTo(annualAccount2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AnnualAccountDTO.class);
        AnnualAccountDTO annualAccountDTO1 = new AnnualAccountDTO();
        annualAccountDTO1.setId(1L);
        AnnualAccountDTO annualAccountDTO2 = new AnnualAccountDTO();
        assertThat(annualAccountDTO1).isNotEqualTo(annualAccountDTO2);
        annualAccountDTO2.setId(annualAccountDTO1.getId());
        assertThat(annualAccountDTO1).isEqualTo(annualAccountDTO2);
        annualAccountDTO2.setId(2L);
        assertThat(annualAccountDTO1).isNotEqualTo(annualAccountDTO2);
        annualAccountDTO1.setId(null);
        assertThat(annualAccountDTO1).isNotEqualTo(annualAccountDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(annualAccountMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(annualAccountMapper.fromId(null)).isNull();
    }
}
