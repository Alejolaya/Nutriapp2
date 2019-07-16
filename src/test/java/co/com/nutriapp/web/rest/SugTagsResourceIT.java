package co.com.nutriapp.web.rest;

import co.com.nutriapp.NutriappApp;
import co.com.nutriapp.domain.SugTags;
import co.com.nutriapp.repository.SugTagsRepository;
import co.com.nutriapp.web.rest.errors.ExceptionTranslator;

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

import static co.com.nutriapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link SugTagsResource} REST controller.
 */
@SpringBootTest(classes = NutriappApp.class)
public class SugTagsResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private SugTagsRepository sugTagsRepository;

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

    private MockMvc restSugTagsMockMvc;

    private SugTags sugTags;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SugTagsResource sugTagsResource = new SugTagsResource(sugTagsRepository);
        this.restSugTagsMockMvc = MockMvcBuilders.standaloneSetup(sugTagsResource)
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
    public static SugTags createEntity(EntityManager em) {
        SugTags sugTags = new SugTags()
            .descripcion(DEFAULT_DESCRIPCION);
        return sugTags;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SugTags createUpdatedEntity(EntityManager em) {
        SugTags sugTags = new SugTags()
            .descripcion(UPDATED_DESCRIPCION);
        return sugTags;
    }

    @BeforeEach
    public void initTest() {
        sugTags = createEntity(em);
    }

    @Test
    @Transactional
    public void createSugTags() throws Exception {
        int databaseSizeBeforeCreate = sugTagsRepository.findAll().size();

        // Create the SugTags
        restSugTagsMockMvc.perform(post("/api/sug-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugTags)))
            .andExpect(status().isCreated());

        // Validate the SugTags in the database
        List<SugTags> sugTagsList = sugTagsRepository.findAll();
        assertThat(sugTagsList).hasSize(databaseSizeBeforeCreate + 1);
        SugTags testSugTags = sugTagsList.get(sugTagsList.size() - 1);
        assertThat(testSugTags.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createSugTagsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sugTagsRepository.findAll().size();

        // Create the SugTags with an existing ID
        sugTags.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSugTagsMockMvc.perform(post("/api/sug-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugTags)))
            .andExpect(status().isBadRequest());

        // Validate the SugTags in the database
        List<SugTags> sugTagsList = sugTagsRepository.findAll();
        assertThat(sugTagsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSugTags() throws Exception {
        // Initialize the database
        sugTagsRepository.saveAndFlush(sugTags);

        // Get all the sugTagsList
        restSugTagsMockMvc.perform(get("/api/sug-tags?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sugTags.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getSugTags() throws Exception {
        // Initialize the database
        sugTagsRepository.saveAndFlush(sugTags);

        // Get the sugTags
        restSugTagsMockMvc.perform(get("/api/sug-tags/{id}", sugTags.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sugTags.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSugTags() throws Exception {
        // Get the sugTags
        restSugTagsMockMvc.perform(get("/api/sug-tags/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSugTags() throws Exception {
        // Initialize the database
        sugTagsRepository.saveAndFlush(sugTags);

        int databaseSizeBeforeUpdate = sugTagsRepository.findAll().size();

        // Update the sugTags
        SugTags updatedSugTags = sugTagsRepository.findById(sugTags.getId()).get();
        // Disconnect from session so that the updates on updatedSugTags are not directly saved in db
        em.detach(updatedSugTags);
        updatedSugTags
            .descripcion(UPDATED_DESCRIPCION);

        restSugTagsMockMvc.perform(put("/api/sug-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSugTags)))
            .andExpect(status().isOk());

        // Validate the SugTags in the database
        List<SugTags> sugTagsList = sugTagsRepository.findAll();
        assertThat(sugTagsList).hasSize(databaseSizeBeforeUpdate);
        SugTags testSugTags = sugTagsList.get(sugTagsList.size() - 1);
        assertThat(testSugTags.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingSugTags() throws Exception {
        int databaseSizeBeforeUpdate = sugTagsRepository.findAll().size();

        // Create the SugTags

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSugTagsMockMvc.perform(put("/api/sug-tags")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugTags)))
            .andExpect(status().isBadRequest());

        // Validate the SugTags in the database
        List<SugTags> sugTagsList = sugTagsRepository.findAll();
        assertThat(sugTagsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSugTags() throws Exception {
        // Initialize the database
        sugTagsRepository.saveAndFlush(sugTags);

        int databaseSizeBeforeDelete = sugTagsRepository.findAll().size();

        // Delete the sugTags
        restSugTagsMockMvc.perform(delete("/api/sug-tags/{id}", sugTags.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SugTags> sugTagsList = sugTagsRepository.findAll();
        assertThat(sugTagsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SugTags.class);
        SugTags sugTags1 = new SugTags();
        sugTags1.setId(1L);
        SugTags sugTags2 = new SugTags();
        sugTags2.setId(sugTags1.getId());
        assertThat(sugTags1).isEqualTo(sugTags2);
        sugTags2.setId(2L);
        assertThat(sugTags1).isNotEqualTo(sugTags2);
        sugTags1.setId(null);
        assertThat(sugTags1).isNotEqualTo(sugTags2);
    }
}
