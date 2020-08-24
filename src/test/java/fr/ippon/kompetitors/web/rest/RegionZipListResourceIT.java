package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.RegionZipList;
import fr.ippon.kompetitors.repository.RegionZipListRepository;
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
 * Integration tests for the {@link RegionZipListResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class RegionZipListResourceIT {

    private static final String DEFAULT_REGION = "AAAAAAAAAA";
    private static final String UPDATED_REGION = "BBBBBBBBBB";

    private static final String DEFAULT_ZIP = "AAAAAAAAAA";
    private static final String UPDATED_ZIP = "BBBBBBBBBB";

    @Autowired
    private RegionZipListRepository regionZipListRepository;

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

    private MockMvc restRegionZipListMockMvc;

    private RegionZipList regionZipList;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RegionZipListResource regionZipListResource = new RegionZipListResource(regionZipListRepository);
        this.restRegionZipListMockMvc = MockMvcBuilders.standaloneSetup(regionZipListResource)
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
    public static RegionZipList createEntity(EntityManager em) {
        RegionZipList regionZipList = new RegionZipList()
            .region(DEFAULT_REGION)
            .zip(DEFAULT_ZIP);
        return regionZipList;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RegionZipList createUpdatedEntity(EntityManager em) {
        RegionZipList regionZipList = new RegionZipList()
            .region(UPDATED_REGION)
            .zip(UPDATED_ZIP);
        return regionZipList;
    }

    @BeforeEach
    public void initTest() {
        regionZipList = createEntity(em);
    }

    @Test
    @Transactional
    public void createRegionZipList() throws Exception {
        int databaseSizeBeforeCreate = regionZipListRepository.findAll().size();

        // Create the RegionZipList
        restRegionZipListMockMvc.perform(post("/api/region-zip-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionZipList)))
            .andExpect(status().isCreated());

        // Validate the RegionZipList in the database
        List<RegionZipList> regionZipListList = regionZipListRepository.findAll();
        assertThat(regionZipListList).hasSize(databaseSizeBeforeCreate + 1);
        RegionZipList testRegionZipList = regionZipListList.get(regionZipListList.size() - 1);
        assertThat(testRegionZipList.getRegion()).isEqualTo(DEFAULT_REGION);
        assertThat(testRegionZipList.getZip()).isEqualTo(DEFAULT_ZIP);
    }

    @Test
    @Transactional
    public void createRegionZipListWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = regionZipListRepository.findAll().size();

        // Create the RegionZipList with an existing ID
        regionZipList.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRegionZipListMockMvc.perform(post("/api/region-zip-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionZipList)))
            .andExpect(status().isBadRequest());

        // Validate the RegionZipList in the database
        List<RegionZipList> regionZipListList = regionZipListRepository.findAll();
        assertThat(regionZipListList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllRegionZipLists() throws Exception {
        // Initialize the database
        regionZipListRepository.saveAndFlush(regionZipList);

        // Get all the regionZipListList
        restRegionZipListMockMvc.perform(get("/api/region-zip-lists?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(regionZipList.getId().intValue())))
            .andExpect(jsonPath("$.[*].region").value(hasItem(DEFAULT_REGION)))
            .andExpect(jsonPath("$.[*].zip").value(hasItem(DEFAULT_ZIP)));
    }
    
    @Test
    @Transactional
    public void getRegionZipList() throws Exception {
        // Initialize the database
        regionZipListRepository.saveAndFlush(regionZipList);

        // Get the regionZipList
        restRegionZipListMockMvc.perform(get("/api/region-zip-lists/{id}", regionZipList.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(regionZipList.getId().intValue()))
            .andExpect(jsonPath("$.region").value(DEFAULT_REGION))
            .andExpect(jsonPath("$.zip").value(DEFAULT_ZIP));
    }

    @Test
    @Transactional
    public void getNonExistingRegionZipList() throws Exception {
        // Get the regionZipList
        restRegionZipListMockMvc.perform(get("/api/region-zip-lists/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRegionZipList() throws Exception {
        // Initialize the database
        regionZipListRepository.saveAndFlush(regionZipList);

        int databaseSizeBeforeUpdate = regionZipListRepository.findAll().size();

        // Update the regionZipList
        RegionZipList updatedRegionZipList = regionZipListRepository.findById(regionZipList.getId()).get();
        // Disconnect from session so that the updates on updatedRegionZipList are not directly saved in db
        em.detach(updatedRegionZipList);
        updatedRegionZipList
            .region(UPDATED_REGION)
            .zip(UPDATED_ZIP);

        restRegionZipListMockMvc.perform(put("/api/region-zip-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRegionZipList)))
            .andExpect(status().isOk());

        // Validate the RegionZipList in the database
        List<RegionZipList> regionZipListList = regionZipListRepository.findAll();
        assertThat(regionZipListList).hasSize(databaseSizeBeforeUpdate);
        RegionZipList testRegionZipList = regionZipListList.get(regionZipListList.size() - 1);
        assertThat(testRegionZipList.getRegion()).isEqualTo(UPDATED_REGION);
        assertThat(testRegionZipList.getZip()).isEqualTo(UPDATED_ZIP);
    }

    @Test
    @Transactional
    public void updateNonExistingRegionZipList() throws Exception {
        int databaseSizeBeforeUpdate = regionZipListRepository.findAll().size();

        // Create the RegionZipList

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRegionZipListMockMvc.perform(put("/api/region-zip-lists")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(regionZipList)))
            .andExpect(status().isBadRequest());

        // Validate the RegionZipList in the database
        List<RegionZipList> regionZipListList = regionZipListRepository.findAll();
        assertThat(regionZipListList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRegionZipList() throws Exception {
        // Initialize the database
        regionZipListRepository.saveAndFlush(regionZipList);

        int databaseSizeBeforeDelete = regionZipListRepository.findAll().size();

        // Delete the regionZipList
        restRegionZipListMockMvc.perform(delete("/api/region-zip-lists/{id}", regionZipList.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<RegionZipList> regionZipListList = regionZipListRepository.findAll();
        assertThat(regionZipListList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RegionZipList.class);
        RegionZipList regionZipList1 = new RegionZipList();
        regionZipList1.setId(1L);
        RegionZipList regionZipList2 = new RegionZipList();
        regionZipList2.setId(regionZipList1.getId());
        assertThat(regionZipList1).isEqualTo(regionZipList2);
        regionZipList2.setId(2L);
        assertThat(regionZipList1).isNotEqualTo(regionZipList2);
        regionZipList1.setId(null);
        assertThat(regionZipList1).isNotEqualTo(regionZipList2);
    }
}
