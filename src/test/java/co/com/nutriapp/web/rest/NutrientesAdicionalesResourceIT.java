package co.com.nutriapp.web.rest;

import co.com.nutriapp.NutriappApp;
import co.com.nutriapp.domain.NutrientesAdicionales;
import co.com.nutriapp.repository.NutrientesAdicionalesRepository;
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
 * Integration tests for the {@Link NutrientesAdicionalesResource} REST controller.
 */
@SpringBootTest(classes = NutriappApp.class)
public class NutrientesAdicionalesResourceIT {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final String DEFAULT_VALOR = "AAAAAAAAAA";
    private static final String UPDATED_VALOR = "BBBBBBBBBB";

    @Autowired
    private NutrientesAdicionalesRepository nutrientesAdicionalesRepository;

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

    private MockMvc restNutrientesAdicionalesMockMvc;

    private NutrientesAdicionales nutrientesAdicionales;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final NutrientesAdicionalesResource nutrientesAdicionalesResource = new NutrientesAdicionalesResource(nutrientesAdicionalesRepository);
        this.restNutrientesAdicionalesMockMvc = MockMvcBuilders.standaloneSetup(nutrientesAdicionalesResource)
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
    public static NutrientesAdicionales createEntity(EntityManager em) {
        NutrientesAdicionales nutrientesAdicionales = new NutrientesAdicionales()
            .descripcion(DEFAULT_DESCRIPCION)
            .valor(DEFAULT_VALOR);
        return nutrientesAdicionales;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static NutrientesAdicionales createUpdatedEntity(EntityManager em) {
        NutrientesAdicionales nutrientesAdicionales = new NutrientesAdicionales()
            .descripcion(UPDATED_DESCRIPCION)
            .valor(UPDATED_VALOR);
        return nutrientesAdicionales;
    }

    @BeforeEach
    public void initTest() {
        nutrientesAdicionales = createEntity(em);
    }

    @Test
    @Transactional
    public void createNutrientesAdicionales() throws Exception {
        int databaseSizeBeforeCreate = nutrientesAdicionalesRepository.findAll().size();

        // Create the NutrientesAdicionales
        restNutrientesAdicionalesMockMvc.perform(post("/api/nutrientes-adicionales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nutrientesAdicionales)))
            .andExpect(status().isCreated());

        // Validate the NutrientesAdicionales in the database
        List<NutrientesAdicionales> nutrientesAdicionalesList = nutrientesAdicionalesRepository.findAll();
        assertThat(nutrientesAdicionalesList).hasSize(databaseSizeBeforeCreate + 1);
        NutrientesAdicionales testNutrientesAdicionales = nutrientesAdicionalesList.get(nutrientesAdicionalesList.size() - 1);
        assertThat(testNutrientesAdicionales.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testNutrientesAdicionales.getValor()).isEqualTo(DEFAULT_VALOR);
    }

    @Test
    @Transactional
    public void createNutrientesAdicionalesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = nutrientesAdicionalesRepository.findAll().size();

        // Create the NutrientesAdicionales with an existing ID
        nutrientesAdicionales.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restNutrientesAdicionalesMockMvc.perform(post("/api/nutrientes-adicionales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nutrientesAdicionales)))
            .andExpect(status().isBadRequest());

        // Validate the NutrientesAdicionales in the database
        List<NutrientesAdicionales> nutrientesAdicionalesList = nutrientesAdicionalesRepository.findAll();
        assertThat(nutrientesAdicionalesList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllNutrientesAdicionales() throws Exception {
        // Initialize the database
        nutrientesAdicionalesRepository.saveAndFlush(nutrientesAdicionales);

        // Get all the nutrientesAdicionalesList
        restNutrientesAdicionalesMockMvc.perform(get("/api/nutrientes-adicionales?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(nutrientesAdicionales.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].valor").value(hasItem(DEFAULT_VALOR.toString())));
    }
    
    @Test
    @Transactional
    public void getNutrientesAdicionales() throws Exception {
        // Initialize the database
        nutrientesAdicionalesRepository.saveAndFlush(nutrientesAdicionales);

        // Get the nutrientesAdicionales
        restNutrientesAdicionalesMockMvc.perform(get("/api/nutrientes-adicionales/{id}", nutrientesAdicionales.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(nutrientesAdicionales.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.valor").value(DEFAULT_VALOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingNutrientesAdicionales() throws Exception {
        // Get the nutrientesAdicionales
        restNutrientesAdicionalesMockMvc.perform(get("/api/nutrientes-adicionales/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateNutrientesAdicionales() throws Exception {
        // Initialize the database
        nutrientesAdicionalesRepository.saveAndFlush(nutrientesAdicionales);

        int databaseSizeBeforeUpdate = nutrientesAdicionalesRepository.findAll().size();

        // Update the nutrientesAdicionales
        NutrientesAdicionales updatedNutrientesAdicionales = nutrientesAdicionalesRepository.findById(nutrientesAdicionales.getId()).get();
        // Disconnect from session so that the updates on updatedNutrientesAdicionales are not directly saved in db
        em.detach(updatedNutrientesAdicionales);
        updatedNutrientesAdicionales
            .descripcion(UPDATED_DESCRIPCION)
            .valor(UPDATED_VALOR);

        restNutrientesAdicionalesMockMvc.perform(put("/api/nutrientes-adicionales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedNutrientesAdicionales)))
            .andExpect(status().isOk());

        // Validate the NutrientesAdicionales in the database
        List<NutrientesAdicionales> nutrientesAdicionalesList = nutrientesAdicionalesRepository.findAll();
        assertThat(nutrientesAdicionalesList).hasSize(databaseSizeBeforeUpdate);
        NutrientesAdicionales testNutrientesAdicionales = nutrientesAdicionalesList.get(nutrientesAdicionalesList.size() - 1);
        assertThat(testNutrientesAdicionales.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testNutrientesAdicionales.getValor()).isEqualTo(UPDATED_VALOR);
    }

    @Test
    @Transactional
    public void updateNonExistingNutrientesAdicionales() throws Exception {
        int databaseSizeBeforeUpdate = nutrientesAdicionalesRepository.findAll().size();

        // Create the NutrientesAdicionales

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restNutrientesAdicionalesMockMvc.perform(put("/api/nutrientes-adicionales")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(nutrientesAdicionales)))
            .andExpect(status().isBadRequest());

        // Validate the NutrientesAdicionales in the database
        List<NutrientesAdicionales> nutrientesAdicionalesList = nutrientesAdicionalesRepository.findAll();
        assertThat(nutrientesAdicionalesList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteNutrientesAdicionales() throws Exception {
        // Initialize the database
        nutrientesAdicionalesRepository.saveAndFlush(nutrientesAdicionales);

        int databaseSizeBeforeDelete = nutrientesAdicionalesRepository.findAll().size();

        // Delete the nutrientesAdicionales
        restNutrientesAdicionalesMockMvc.perform(delete("/api/nutrientes-adicionales/{id}", nutrientesAdicionales.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<NutrientesAdicionales> nutrientesAdicionalesList = nutrientesAdicionalesRepository.findAll();
        assertThat(nutrientesAdicionalesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(NutrientesAdicionales.class);
        NutrientesAdicionales nutrientesAdicionales1 = new NutrientesAdicionales();
        nutrientesAdicionales1.setId(1L);
        NutrientesAdicionales nutrientesAdicionales2 = new NutrientesAdicionales();
        nutrientesAdicionales2.setId(nutrientesAdicionales1.getId());
        assertThat(nutrientesAdicionales1).isEqualTo(nutrientesAdicionales2);
        nutrientesAdicionales2.setId(2L);
        assertThat(nutrientesAdicionales1).isNotEqualTo(nutrientesAdicionales2);
        nutrientesAdicionales1.setId(null);
        assertThat(nutrientesAdicionales1).isNotEqualTo(nutrientesAdicionales2);
    }
}
