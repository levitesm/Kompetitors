package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.ListTechPartners;
import fr.ippon.kompetitors.repository.ListTechPartnersRepository;
import fr.ippon.kompetitors.service.ListTechPartnersService;
import fr.ippon.kompetitors.service.dto.ListTechPartnersDTO;
import fr.ippon.kompetitors.service.mapper.ListTechPartnersMapper;
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
import org.springframework.util.Base64Utils;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;

import static fr.ippon.kompetitors.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link ListTechPartnersResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class ListTechPartnersResourceIT {

    private static final String DEFAULT_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_VALUE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    @Autowired
    private ListTechPartnersRepository listTechPartnersRepository;

    @Autowired
    private ListTechPartnersMapper listTechPartnersMapper;

    @Autowired
    private ListTechPartnersService listTechPartnersService;

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

    private MockMvc restListTechPartnersMockMvc;

    private ListTechPartners listTechPartners;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ListTechPartnersResource listTechPartnersResource = new ListTechPartnersResource(listTechPartnersService);
        this.restListTechPartnersMockMvc = MockMvcBuilders.standaloneSetup(listTechPartnersResource)
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
    public static ListTechPartners createEntity(EntityManager em) {
        ListTechPartners listTechPartners = new ListTechPartners()
            .value(DEFAULT_VALUE)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE);
        return listTechPartners;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ListTechPartners createUpdatedEntity(EntityManager em) {
        ListTechPartners listTechPartners = new ListTechPartners()
            .value(UPDATED_VALUE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        return listTechPartners;
    }

    @BeforeEach
    public void initTest() {
        listTechPartners = createEntity(em);
    }

    @Test
    @Transactional
    public void createListTechPartners() throws Exception {
        int databaseSizeBeforeCreate = listTechPartnersRepository.findAll().size();

        // Create the ListTechPartners
        ListTechPartnersDTO listTechPartnersDTO = listTechPartnersMapper.toDto(listTechPartners);
        restListTechPartnersMockMvc.perform(post("/api/list-tech-partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listTechPartnersDTO)))
            .andExpect(status().isCreated());

        // Validate the ListTechPartners in the database
        List<ListTechPartners> listTechPartnersList = listTechPartnersRepository.findAll();
        assertThat(listTechPartnersList).hasSize(databaseSizeBeforeCreate + 1);
        ListTechPartners testListTechPartners = listTechPartnersList.get(listTechPartnersList.size() - 1);
        assertThat(testListTechPartners.getValue()).isEqualTo(DEFAULT_VALUE);
        assertThat(testListTechPartners.getImage()).isEqualTo(DEFAULT_IMAGE);
        assertThat(testListTechPartners.getImageContentType()).isEqualTo(DEFAULT_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createListTechPartnersWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = listTechPartnersRepository.findAll().size();

        // Create the ListTechPartners with an existing ID
        listTechPartners.setId(1L);
        ListTechPartnersDTO listTechPartnersDTO = listTechPartnersMapper.toDto(listTechPartners);

        // An entity with an existing ID cannot be created, so this API call must fail
        restListTechPartnersMockMvc.perform(post("/api/list-tech-partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listTechPartnersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListTechPartners in the database
        List<ListTechPartners> listTechPartnersList = listTechPartnersRepository.findAll();
        assertThat(listTechPartnersList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = listTechPartnersRepository.findAll().size();
        // set the field null
        listTechPartners.setValue(null);

        // Create the ListTechPartners, which fails.
        ListTechPartnersDTO listTechPartnersDTO = listTechPartnersMapper.toDto(listTechPartners);

        restListTechPartnersMockMvc.perform(post("/api/list-tech-partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listTechPartnersDTO)))
            .andExpect(status().isBadRequest());

        List<ListTechPartners> listTechPartnersList = listTechPartnersRepository.findAll();
        assertThat(listTechPartnersList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllListTechPartners() throws Exception {
        // Initialize the database
        listTechPartnersRepository.saveAndFlush(listTechPartners);

        // Get all the listTechPartnersList
        restListTechPartnersMockMvc.perform(get("/api/list-tech-partners?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(listTechPartners.getId().intValue())))
            .andExpect(jsonPath("$.[*].value").value(hasItem(DEFAULT_VALUE)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64Utils.encodeToString(DEFAULT_IMAGE))));
    }
    
    @Test
    @Transactional
    public void getListTechPartners() throws Exception {
        // Initialize the database
        listTechPartnersRepository.saveAndFlush(listTechPartners);

        // Get the listTechPartners
        restListTechPartnersMockMvc.perform(get("/api/list-tech-partners/{id}", listTechPartners.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(listTechPartners.getId().intValue()))
            .andExpect(jsonPath("$.value").value(DEFAULT_VALUE))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64Utils.encodeToString(DEFAULT_IMAGE)));
    }

    @Test
    @Transactional
    public void getNonExistingListTechPartners() throws Exception {
        // Get the listTechPartners
        restListTechPartnersMockMvc.perform(get("/api/list-tech-partners/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateListTechPartners() throws Exception {
        // Initialize the database
        listTechPartnersRepository.saveAndFlush(listTechPartners);

        int databaseSizeBeforeUpdate = listTechPartnersRepository.findAll().size();

        // Update the listTechPartners
        ListTechPartners updatedListTechPartners = listTechPartnersRepository.findById(listTechPartners.getId()).get();
        // Disconnect from session so that the updates on updatedListTechPartners are not directly saved in db
        em.detach(updatedListTechPartners);
        updatedListTechPartners
            .value(UPDATED_VALUE)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE);
        ListTechPartnersDTO listTechPartnersDTO = listTechPartnersMapper.toDto(updatedListTechPartners);

        restListTechPartnersMockMvc.perform(put("/api/list-tech-partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listTechPartnersDTO)))
            .andExpect(status().isOk());

        // Validate the ListTechPartners in the database
        List<ListTechPartners> listTechPartnersList = listTechPartnersRepository.findAll();
        assertThat(listTechPartnersList).hasSize(databaseSizeBeforeUpdate);
        ListTechPartners testListTechPartners = listTechPartnersList.get(listTechPartnersList.size() - 1);
        assertThat(testListTechPartners.getValue()).isEqualTo(UPDATED_VALUE);
        assertThat(testListTechPartners.getImage()).isEqualTo(UPDATED_IMAGE);
        assertThat(testListTechPartners.getImageContentType()).isEqualTo(UPDATED_IMAGE_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void updateNonExistingListTechPartners() throws Exception {
        int databaseSizeBeforeUpdate = listTechPartnersRepository.findAll().size();

        // Create the ListTechPartners
        ListTechPartnersDTO listTechPartnersDTO = listTechPartnersMapper.toDto(listTechPartners);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restListTechPartnersMockMvc.perform(put("/api/list-tech-partners")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(listTechPartnersDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ListTechPartners in the database
        List<ListTechPartners> listTechPartnersList = listTechPartnersRepository.findAll();
        assertThat(listTechPartnersList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteListTechPartners() throws Exception {
        // Initialize the database
        listTechPartnersRepository.saveAndFlush(listTechPartners);

        int databaseSizeBeforeDelete = listTechPartnersRepository.findAll().size();

        // Delete the listTechPartners
        restListTechPartnersMockMvc.perform(delete("/api/list-tech-partners/{id}", listTechPartners.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ListTechPartners> listTechPartnersList = listTechPartnersRepository.findAll();
        assertThat(listTechPartnersList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListTechPartners.class);
        ListTechPartners listTechPartners1 = new ListTechPartners();
        listTechPartners1.setId(1L);
        ListTechPartners listTechPartners2 = new ListTechPartners();
        listTechPartners2.setId(listTechPartners1.getId());
        assertThat(listTechPartners1).isEqualTo(listTechPartners2);
        listTechPartners2.setId(2L);
        assertThat(listTechPartners1).isNotEqualTo(listTechPartners2);
        listTechPartners1.setId(null);
        assertThat(listTechPartners1).isNotEqualTo(listTechPartners2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ListTechPartnersDTO.class);
        ListTechPartnersDTO listTechPartnersDTO1 = new ListTechPartnersDTO();
        listTechPartnersDTO1.setId(1L);
        ListTechPartnersDTO listTechPartnersDTO2 = new ListTechPartnersDTO();
        assertThat(listTechPartnersDTO1).isNotEqualTo(listTechPartnersDTO2);
        listTechPartnersDTO2.setId(listTechPartnersDTO1.getId());
        assertThat(listTechPartnersDTO1).isEqualTo(listTechPartnersDTO2);
        listTechPartnersDTO2.setId(2L);
        assertThat(listTechPartnersDTO1).isNotEqualTo(listTechPartnersDTO2);
        listTechPartnersDTO1.setId(null);
        assertThat(listTechPartnersDTO1).isNotEqualTo(listTechPartnersDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(listTechPartnersMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(listTechPartnersMapper.fromId(null)).isNull();
    }
}
