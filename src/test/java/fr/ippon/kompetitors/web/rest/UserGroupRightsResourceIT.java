package fr.ippon.kompetitors.web.rest;

import fr.ippon.kompetitors.Kompetitors2App;
import fr.ippon.kompetitors.config.TestSecurityConfiguration;
import fr.ippon.kompetitors.domain.UserGroupRights;
import fr.ippon.kompetitors.repository.UserGroupRightsRepository;
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
 * Integration tests for the {@link UserGroupRightsResource} REST controller.
 */
@SpringBootTest(classes = {Kompetitors2App.class, TestSecurityConfiguration.class})
public class UserGroupRightsResourceIT {

    private static final String DEFAULT_USER_GROUP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_GROUP_NAME = "BBBBBBBBBB";

    @Autowired
    private UserGroupRightsRepository userGroupRightsRepository;

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

    private MockMvc restUserGroupRightsMockMvc;

    private UserGroupRights userGroupRights;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserGroupRightsResource userGroupRightsResource = new UserGroupRightsResource(userGroupRightsRepository);
        this.restUserGroupRightsMockMvc = MockMvcBuilders.standaloneSetup(userGroupRightsResource)
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
    public static UserGroupRights createEntity(EntityManager em) {
        UserGroupRights userGroupRights = new UserGroupRights()
            .userGroupName(DEFAULT_USER_GROUP_NAME);
        return userGroupRights;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static UserGroupRights createUpdatedEntity(EntityManager em) {
        UserGroupRights userGroupRights = new UserGroupRights()
            .userGroupName(UPDATED_USER_GROUP_NAME);
        return userGroupRights;
    }

    @BeforeEach
    public void initTest() {
        userGroupRights = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserGroupRights() throws Exception {
        int databaseSizeBeforeCreate = userGroupRightsRepository.findAll().size();

        // Create the UserGroupRights
        restUserGroupRightsMockMvc.perform(post("/api/user-group-rights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGroupRights)))
            .andExpect(status().isCreated());

        // Validate the UserGroupRights in the database
        List<UserGroupRights> userGroupRightsList = userGroupRightsRepository.findAll();
        assertThat(userGroupRightsList).hasSize(databaseSizeBeforeCreate + 1);
        UserGroupRights testUserGroupRights = userGroupRightsList.get(userGroupRightsList.size() - 1);
        assertThat(testUserGroupRights.getUserGroupName()).isEqualTo(DEFAULT_USER_GROUP_NAME);
    }

    @Test
    @Transactional
    public void createUserGroupRightsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userGroupRightsRepository.findAll().size();

        // Create the UserGroupRights with an existing ID
        userGroupRights.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserGroupRightsMockMvc.perform(post("/api/user-group-rights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGroupRights)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroupRights in the database
        List<UserGroupRights> userGroupRightsList = userGroupRightsRepository.findAll();
        assertThat(userGroupRightsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUserGroupNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = userGroupRightsRepository.findAll().size();
        // set the field null
        userGroupRights.setUserGroupName(null);

        // Create the UserGroupRights, which fails.

        restUserGroupRightsMockMvc.perform(post("/api/user-group-rights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGroupRights)))
            .andExpect(status().isBadRequest());

        List<UserGroupRights> userGroupRightsList = userGroupRightsRepository.findAll();
        assertThat(userGroupRightsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllUserGroupRights() throws Exception {
        // Initialize the database
        userGroupRightsRepository.saveAndFlush(userGroupRights);

        // Get all the userGroupRightsList
        restUserGroupRightsMockMvc.perform(get("/api/user-group-rights?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userGroupRights.getId().intValue())))
            .andExpect(jsonPath("$.[*].userGroupName").value(hasItem(DEFAULT_USER_GROUP_NAME)));
    }
    
    @Test
    @Transactional
    public void getUserGroupRights() throws Exception {
        // Initialize the database
        userGroupRightsRepository.saveAndFlush(userGroupRights);

        // Get the userGroupRights
        restUserGroupRightsMockMvc.perform(get("/api/user-group-rights/{id}", userGroupRights.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userGroupRights.getId().intValue()))
            .andExpect(jsonPath("$.userGroupName").value(DEFAULT_USER_GROUP_NAME));
    }

    @Test
    @Transactional
    public void getNonExistingUserGroupRights() throws Exception {
        // Get the userGroupRights
        restUserGroupRightsMockMvc.perform(get("/api/user-group-rights/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserGroupRights() throws Exception {
        // Initialize the database
        userGroupRightsRepository.saveAndFlush(userGroupRights);

        int databaseSizeBeforeUpdate = userGroupRightsRepository.findAll().size();

        // Update the userGroupRights
        UserGroupRights updatedUserGroupRights = userGroupRightsRepository.findById(userGroupRights.getId()).get();
        // Disconnect from session so that the updates on updatedUserGroupRights are not directly saved in db
        em.detach(updatedUserGroupRights);
        updatedUserGroupRights
            .userGroupName(UPDATED_USER_GROUP_NAME);

        restUserGroupRightsMockMvc.perform(put("/api/user-group-rights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedUserGroupRights)))
            .andExpect(status().isOk());

        // Validate the UserGroupRights in the database
        List<UserGroupRights> userGroupRightsList = userGroupRightsRepository.findAll();
        assertThat(userGroupRightsList).hasSize(databaseSizeBeforeUpdate);
        UserGroupRights testUserGroupRights = userGroupRightsList.get(userGroupRightsList.size() - 1);
        assertThat(testUserGroupRights.getUserGroupName()).isEqualTo(UPDATED_USER_GROUP_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingUserGroupRights() throws Exception {
        int databaseSizeBeforeUpdate = userGroupRightsRepository.findAll().size();

        // Create the UserGroupRights

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUserGroupRightsMockMvc.perform(put("/api/user-group-rights")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userGroupRights)))
            .andExpect(status().isBadRequest());

        // Validate the UserGroupRights in the database
        List<UserGroupRights> userGroupRightsList = userGroupRightsRepository.findAll();
        assertThat(userGroupRightsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteUserGroupRights() throws Exception {
        // Initialize the database
        userGroupRightsRepository.saveAndFlush(userGroupRights);

        int databaseSizeBeforeDelete = userGroupRightsRepository.findAll().size();

        // Delete the userGroupRights
        restUserGroupRightsMockMvc.perform(delete("/api/user-group-rights/{id}", userGroupRights.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<UserGroupRights> userGroupRightsList = userGroupRightsRepository.findAll();
        assertThat(userGroupRightsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserGroupRights.class);
        UserGroupRights userGroupRights1 = new UserGroupRights();
        userGroupRights1.setId(1L);
        UserGroupRights userGroupRights2 = new UserGroupRights();
        userGroupRights2.setId(userGroupRights1.getId());
        assertThat(userGroupRights1).isEqualTo(userGroupRights2);
        userGroupRights2.setId(2L);
        assertThat(userGroupRights1).isNotEqualTo(userGroupRights2);
        userGroupRights1.setId(null);
        assertThat(userGroupRights1).isNotEqualTo(userGroupRights2);
    }
}
