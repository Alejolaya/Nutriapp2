package co.com.nutriapp.web.rest;

import co.com.nutriapp.NutriappApp;
import co.com.nutriapp.domain.SugNutrientesAdicionales;
import co.com.nutriapp.repository.SugNutrientesAdicionalesRepository;
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
 * Integration tests for the {@Link SugNutrientesAdicionalesResource} REST controller.
 */
@SpringBootTest(classes = NutriappApp.class)
public class SugNutrientesAdicionalesResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private SugNutrientesAdicionalesRepository sugNutrientesAdicionalesRepository;

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

    private MockMvc restSugNutrientesAdicionalesMockMvc;

    private SugNutrientesAdicionales sugNutrientesAdicionales;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SugNutrientesAdicionalesResource sugNutrientesAdicionalesResource = new SugNutrientesAdicionalesResource(sugNutrientesAdicionalesRepository);
        this.restSugNutrientesAdicionalesMockMvc = MockMvcBuilders.standaloneSetup(sugNutrientesAdicionalesResource)
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
    public static SugNutrientesAdicionales createEntity(EntityManager em) {
        SugNutrientesAdicionales sugNutrientesAdicionales = new SugNutrientesAdicionales()
            .descripcion(DEFAULT_DESCRIPCION)
            .valor(DEFAULT_VALOR);
        return sugNutrientesAdicionales;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SugNutrientesAdicionales createUpdatedEntity(EntityManager em) {
        SugNutrientesAdicionales sugNutrientesAdicionales = new SugNutrientesAdicionales()
            .descripcion(UPDATED_DESCRIPCION)
            .valor(UPDATED_VALOR);
        return sugNutrientesAdicionales;
    }

    @BeforeEach
    public void initTest() {
        sugNutrientesAdicionales = createEntity(em);
    }

    @Test
    @Transactional
    public void createSugNutrientesAdicionales() throws Exception {
        int databaseSizeBeforeCreate = sugNutrientesAdicionalesRepository.findAll().size();

        // Create the SugNutrientesAdicionales
        restSugNutrientesAdicionalesMockMvc.perform(post("/api/sug-nutrientes-adicionales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugNutrientesAdicionales)))
            .andExpect(status().isCreated());

        // Validate the SugNutrientesAdicionales in the database
        List<SugNutrientesAdicionales> sugNutrientesAdicionalesList = sugNutrientesAdicionalesRepository.findAll();
        assertThat(sugNutrientesAdicionalesList).hasSize(databaseSizeBeforeCreate + 1);
        SugNutrientesAdicionales testSugNutrientesAdicionales = sugNutrientesAdicionalesList.get(sugNutrientesAdicionalesList.size() - 1);
        assertThat(testSugNutrientesAdicionales.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSugNutrientesAdicionales.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createSugNutrientesAdicionalesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sugNutrientesAdicionalesRepository.findAll().size();

        // Create the SugNutrientesAdicionales with an existing ID
        sugNutrientesAdicionales.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSugNutrientesAdicionalesMockMvc.perform(post("/api/sug-nutrientes-adicionales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugNutrientesAdicionales)))
            .andExpect(status().isBadRequest());

        // Validate the SugNutrientesAdicionales in the database
        List<SugNutrientesAdicionales> sugNutrientesAdicionalesList = sugNutrientesAdicionalesRepository.findAll();
        assertThat(sugNutrientesAdicionalesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSugNutrientesAdicionales() throws Exception {
        // Initialize the database
        sugNutrientesAdicionalesRepository.saveAndFlush(sugNutrientesAdicionales);

        // Get all the sugNutrientesAdicionalesList
        restSugNutrientesAdicionalesMockMvc.perform(get("/api/sug-nutrientes-adicionales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sugNutrientesAdicionales.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }
    
    @Test
    @Transactional
    public void getSugNutrientesAdicionales() throws Exception {
        // Initialize the database
        sugNutrientesAdicionalesRepository.saveAndFlush(sugNutrientesAdicionales);

        // Get the sugNutrientesAdicionales
        restSugNutrientesAdicionalesMockMvc.perform(get("/api/sug-nutrientes-adicionales/{id}", sugNutrientesAdicionales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sugNutrientesAdicionales.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSugNutrientesAdicionales() throws Exception {
        // Get the sugNutrientesAdicionales
        restSugNutrientesAdicionalesMockMvc.perform(get("/api/sug-nutrientes-adicionales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSugNutrientesAdicionales() throws Exception {
        // Initialize the database
        sugNutrientesAdicionalesRepository.saveAndFlush(sugNutrientesAdicionales);

        int databaseSizeBeforeUpdate = sugNutrientesAdicionalesRepository.findAll().size();

        // Update the sugNutrientesAdicionales
        SugNutrientesAdicionales updatedSugNutrientesAdicionales = sugNutrientesAdicionalesRepository.findById(sugNutrientesAdicionales.getId()).get();
        // Disconnect from session so that the updates on updatedSugNutrientesAdicionales are not directly saved in db
        em.detach(updatedSugNutrientesAdicionales);
        updatedSugNutrientesAdicionales
            .descripcion(UPDATED_DESCRIPCION)
            .valor(UPDATED_VALOR);

        restSugNutrientesAdicionalesMockMvc.perform(put("/api/sug-nutrientes-adicionales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSugNutrientesAdicionales)))
            .andExpect(status().isOk());

        // Validate the SugNutrientesAdicionales in the database
        List<SugNutrientesAdicionales> sugNutrientesAdicionalesList = sugNutrientesAdicionalesRepository.findAll();
        assertThat(sugNutrientesAdicionalesList).hasSize(databaseSizeBeforeUpdate);
        SugNutrientesAdicionales testSugNutrientesAdicionales = sugNutrientesAdicionalesList.get(sugNutrientesAdicionalesList.size() - 1);
        assertThat(testSugNutrientesAdicionales.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSugNutrientesAdicionales.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingSugNutrientesAdicionales() throws Exception {
        int databaseSizeBeforeUpdate = sugNutrientesAdicionalesRepository.findAll().size();

        // Create the SugNutrientesAdicionales

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSugNutrientesAdicionalesMockMvc.perform(put("/api/sug-nutrientes-adicionales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugNutrientesAdicionales)))
            .andExpect(status().isBadRequest());

        // Validate the SugNutrientesAdicionales in the database
        List<SugNutrientesAdicionales> sugNutrientesAdicionalesList = sugNutrientesAdicionalesRepository.findAll();
        assertThat(sugNutrientesAdicionalesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSugNutrientesAdicionales() throws Exception {
        // Initialize the database
        sugNutrientesAdicionalesRepository.saveAndFlush(sugNutrientesAdicionales);

        int databaseSizeBeforeDelete = sugNutrientesAdicionalesRepository.findAll().size();

        // Delete the sugNutrientesAdicionales
        restSugNutrientesAdicionalesMockMvc.perform(delete("/api/sug-nutrientes-adicionales/{id}", sugNutrientesAdicionales.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SugNutrientesAdicionales> sugNutrientesAdicionalesList = sugNutrientesAdicionalesRepository.findAll();
        assertThat(sugNutrientesAdicionalesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SugNutrientesAdicionales.class);
        SugNutrientesAdicionales sugNutrientesAdicionales1 = new SugNutrientesAdicionales();
        sugNutrientesAdicionales1.setId(1L);
        SugNutrientesAdicionales sugNutrientesAdicionales2 = new SugNutrientesAdicionales();
        sugNutrientesAdicionales2.setId(sugNutrientesAdicionales1.getId());
        assertThat(sugNutrientesAdicionales1).isEqualTo(sugNutrientesAdicionales2);
        sugNutrientesAdicionales2.setId(2L);
        assertThat(sugNutrientesAdicionales1).isNotEqualTo(sugNutrientesAdicionales2);
        sugNutrientesAdicionales1.setId(null);
        assertThat(sugNutrientesAdicionales1).isNotEqualTo(sugNutrientesAdicionales2);
    }
}
