package co.com.nutriapp.web.rest;

import co.com.nutriapp.NutriappApp;
import co.com.nutriapp.domain.SugMarca;
import co.com.nutriapp.repository.SugMarcaRepository;
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
 * Integration tests for the {@Link SugMarcaResource} REST controller.
 */
@SpringBootTest(classes = NutriappApp.class)
public class SugMarcaResourceIT {

    private static final String DEFAULT_NOMBRE_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_MARCA = "BBBBBBBBBB";

    @Autowired
    private SugMarcaRepository sugMarcaRepository;

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

    private MockMvc restSugMarcaMockMvc;

    private SugMarca sugMarca;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SugMarcaResource sugMarcaResource = new SugMarcaResource(sugMarcaRepository);
        this.restSugMarcaMockMvc = MockMvcBuilders.standaloneSetup(sugMarcaResource)
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
    public static SugMarca createEntity(EntityManager em) {
        SugMarca sugMarca = new SugMarca()
            .nombreMarca(DEFAULT_NOMBRE_MARCA);
        return sugMarca;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SugMarca createUpdatedEntity(EntityManager em) {
        SugMarca sugMarca = new SugMarca()
            .nombreMarca(UPDATED_NOMBRE_MARCA);
        return sugMarca;
    }

    @BeforeEach
    public void initTest() {
        sugMarca = createEntity(em);
    }

    @Test
    @Transactional
    public void createSugMarca() throws Exception {
        int databaseSizeBeforeCreate = sugMarcaRepository.findAll().size();

        // Create the SugMarca
        restSugMarcaMockMvc.perform(post("/api/sug-marcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugMarca)))
            .andExpect(status().isCreated());

        // Validate the SugMarca in the database
        List<SugMarca> sugMarcaList = sugMarcaRepository.findAll();
        assertThat(sugMarcaList).hasSize(databaseSizeBeforeCreate + 1);
        SugMarca testSugMarca = sugMarcaList.get(sugMarcaList.size() - 1);
        assertThat(testSugMarca.getNombreMarca()).isEqualTo(DEFAULT_NOMBRE_MARCA);
    }

    @Test
    @Transactional
    public void createSugMarcaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sugMarcaRepository.findAll().size();

        // Create the SugMarca with an existing ID
        sugMarca.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSugMarcaMockMvc.perform(post("/api/sug-marcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugMarca)))
            .andExpect(status().isBadRequest());

        // Validate the SugMarca in the database
        List<SugMarca> sugMarcaList = sugMarcaRepository.findAll();
        assertThat(sugMarcaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSugMarcas() throws Exception {
        // Initialize the database
        sugMarcaRepository.saveAndFlush(sugMarca);

        // Get all the sugMarcaList
        restSugMarcaMockMvc.perform(get("/api/sug-marcas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sugMarca.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreMarca").value(hasItem(DEFAULT_NOMBRE_MARCA.toString())));
    }
    
    @Test
    @Transactional
    public void getSugMarca() throws Exception {
        // Initialize the database
        sugMarcaRepository.saveAndFlush(sugMarca);

        // Get the sugMarca
        restSugMarcaMockMvc.perform(get("/api/sug-marcas/{id}", sugMarca.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sugMarca.getId().intValue()))
            .andExpect(jsonPath("$.nombreMarca").value(DEFAULT_NOMBRE_MARCA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSugMarca() throws Exception {
        // Get the sugMarca
        restSugMarcaMockMvc.perform(get("/api/sug-marcas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSugMarca() throws Exception {
        // Initialize the database
        sugMarcaRepository.saveAndFlush(sugMarca);

        int databaseSizeBeforeUpdate = sugMarcaRepository.findAll().size();

        // Update the sugMarca
        SugMarca updatedSugMarca = sugMarcaRepository.findById(sugMarca.getId()).get();
        // Disconnect from session so that the updates on updatedSugMarca are not directly saved in db
        em.detach(updatedSugMarca);
        updatedSugMarca
            .nombreMarca(UPDATED_NOMBRE_MARCA);

        restSugMarcaMockMvc.perform(put("/api/sug-marcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSugMarca)))
            .andExpect(status().isOk());

        // Validate the SugMarca in the database
        List<SugMarca> sugMarcaList = sugMarcaRepository.findAll();
        assertThat(sugMarcaList).hasSize(databaseSizeBeforeUpdate);
        SugMarca testSugMarca = sugMarcaList.get(sugMarcaList.size() - 1);
        assertThat(testSugMarca.getNombreMarca()).isEqualTo(UPDATED_NOMBRE_MARCA);
    }

    @Test
    @Transactional
    public void updateNonExistingSugMarca() throws Exception {
        int databaseSizeBeforeUpdate = sugMarcaRepository.findAll().size();

        // Create the SugMarca

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSugMarcaMockMvc.perform(put("/api/sug-marcas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugMarca)))
            .andExpect(status().isBadRequest());

        // Validate the SugMarca in the database
        List<SugMarca> sugMarcaList = sugMarcaRepository.findAll();
        assertThat(sugMarcaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSugMarca() throws Exception {
        // Initialize the database
        sugMarcaRepository.saveAndFlush(sugMarca);

        int databaseSizeBeforeDelete = sugMarcaRepository.findAll().size();

        // Delete the sugMarca
        restSugMarcaMockMvc.perform(delete("/api/sug-marcas/{id}", sugMarca.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SugMarca> sugMarcaList = sugMarcaRepository.findAll();
        assertThat(sugMarcaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SugMarca.class);
        SugMarca sugMarca1 = new SugMarca();
        sugMarca1.setId(1L);
        SugMarca sugMarca2 = new SugMarca();
        sugMarca2.setId(sugMarca1.getId());
        assertThat(sugMarca1).isEqualTo(sugMarca2);
        sugMarca2.setId(2L);
        assertThat(sugMarca1).isNotEqualTo(sugMarca2);
        sugMarca1.setId(null);
        assertThat(sugMarca1).isNotEqualTo(sugMarca2);
    }
}
