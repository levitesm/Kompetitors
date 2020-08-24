package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.RegionList;
import fr.ippon.kompetitors.repository.RegionListRepository;
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
 * Integration tests for the {@link RegionListResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class RegionListResourceIT {

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private RegionListRepository regionListRepository;

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

    private MockMvc restRegionListMockMvc;

    private RegionList regionList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegionListResource regionListResource = new RegionListResource(regionListRepository);
        this.restRegionListMockMvc = MockMvcBuilders.standaloneSetup(regionListResource)
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
    public static RegionList createEntity(EntityManager em) {
        RegionList regionList = new RegionList()
            .region(DEFAULT_REGION)
            .country(DEFAULT_COUNTRY);
        return regionList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegionList createUpdatedEntity(EntityManager em) {
        RegionList regionList = new RegionList()
            .region(UPDATED_REGION)
            .country(UPDATED_COUNTRY);
        return regionList;
    }

    @BeforeEach
    public void initTest() {
        regionList = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegionList() throws Exception {
        int databaseSizeBeforeCreate = regionListRepository.findAll().size();

        // Create the RegionList
        restRegionListMockMvc.perform(post("/api/region-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionList)))
            .andExpect(status().isCreated());

        // Validate the RegionList in the database
        List<RegionList> regionListList = regionListRepository.findAll();
        assertThat(regionListList).hasSize(databaseSizeBeforeCreate + 1);
        RegionList testRegionList = regionListList.get(regionListList.size() - 1);
        assertThat(testRegionList.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testRegionList.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createRegionListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regionListRepository.findAll().size();

        // Create the RegionList with an existing ID
        regionList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionListMockMvc.perform(post("/api/region-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionList)))
            .andExpect(status().isBadRequest());

        // Validate the RegionList in the database
        List<RegionList> regionListList = regionListRepository.findAll();
        assertThat(regionListList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRegionLists() throws Exception {
        // Initialize the database
        regionListRepository.saveAndFlush(regionList);

        // Get all the regionListList
        restRegionListMockMvc.perform(get("/api/region-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regionList.getId().intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY)));
    }
    
    @Test
    @Transactional
    public void getRegionList() throws Exception {
        // Initialize the database
        regionListRepository.saveAndFlush(regionList);

        // Get the regionList
        restRegionListMockMvc.perform(get("/api/region-lists/{id}", regionList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regionList.getId().intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY));
    }

    @Test
    @Transactional
    public void getNonExistingRegionList() throws Exception {
        // Get the regionList
        restRegionListMockMvc.perform(get("/api/region-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegionList() throws Exception {
        // Initialize the database
        regionListRepository.saveAndFlush(regionList);

        int databaseSizeBeforeUpdate = regionListRepository.findAll().size();

        // Update the regionList
        RegionList updatedRegionList = regionListRepository.findById(regionList.getId()).get();
        // Disconnect from session so that the updates on updatedRegionList are not directly saved in db
        em.detach(updatedRegionList);
        updatedRegionList
            .region(UPDATED_REGION)
            .country(UPDATED_COUNTRY);

        restRegionListMockMvc.perform(put("/api/region-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegionList)))
            .andExpect(status().isOk());

        // Validate the RegionList in the database
        List<RegionList> regionListList = regionListRepository.findAll();
        assertThat(regionListList).hasSize(databaseSizeBeforeUpdate);
        RegionList testRegionList = regionListList.get(regionListList.size() - 1);
        assertThat(testRegionList.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testRegionList.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingRegionList() throws Exception {
        int databaseSizeBeforeUpdate = regionListRepository.findAll().size();

        // Create the RegionList

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionListMockMvc.perform(put("/api/region-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionList)))
            .andExpect(status().isBadRequest());

        // Validate the RegionList in the database
        List<RegionList> regionListList = regionListRepository.findAll();
        assertThat(regionListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegionList() throws Exception {
        // Initialize the database
        regionListRepository.saveAndFlush(regionList);

        int databaseSizeBeforeDelete = regionListRepository.findAll().size();

        // Delete the regionList
        restRegionListMockMvc.perform(delete("/api/region-lists/{id}", regionList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegionList> regionListList = regionListRepository.findAll();
        assertThat(regionListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionList.class);
        RegionList regionList1 = new RegionList();
        regionList1.setId(1L);
        RegionList regionList2 = new RegionList();
        regionList2.setId(regionList1.getId());
        assertThat(regionList1).isEqualTo(regionList2);
        regionList2.setId(2L);
        assertThat(regionList1).isNotEqualTo(regionList2);
        regionList1.setId(null);
        assertThat(regionList1).isNotEqualTo(regionList2);
    }
}
