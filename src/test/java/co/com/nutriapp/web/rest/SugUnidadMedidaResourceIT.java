package co.com.nutriapp.web.rest;

import co.com.nutriapp.NutriappApp;
import co.com.nutriapp.domain.SugUnidadMedida;
import co.com.nutriapp.repository.SugUnidadMedidaRepository;
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
 * Integration tests for the {@Link SugUnidadMedidaResource} REST controller.
 */
@SpringBootTest(classes = NutriappApp.class)
public class SugUnidadMedidaResourceIT {

    private static final String DEFAULT_ABREVIACION = "AAAAAAAAAA";
    private static final String UPDATED_ABREVIACION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Float DEFAULT_VALOR_CONVERSION = 1F;
    private static final Float UPDATED_VALOR_CONVERSION = 2F;

    @Autowired
    private SugUnidadMedidaRepository sugUnidadMedidaRepository;

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

    private MockMvc restSugUnidadMedidaMockMvc;

    private SugUnidadMedida sugUnidadMedida;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SugUnidadMedidaResource sugUnidadMedidaResource = new SugUnidadMedidaResource(sugUnidadMedidaRepository);
        this.restSugUnidadMedidaMockMvc = MockMvcBuilders.standaloneSetup(sugUnidadMedidaResource)
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
    public static SugUnidadMedida createEntity(EntityManager em) {
        SugUnidadMedida sugUnidadMedida = new SugUnidadMedida()
            .abreviacion(DEFAULT_ABREVIACION)
            .descripcion(DEFAULT_DESCRIPCION)
            .valorConversion(DEFAULT_VALOR_CONVERSION);
        return sugUnidadMedida;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SugUnidadMedida createUpdatedEntity(EntityManager em) {
        SugUnidadMedida sugUnidadMedida = new SugUnidadMedida()
            .abreviacion(UPDATED_ABREVIACION)
            .descripcion(UPDATED_DESCRIPCION)
            .valorConversion(UPDATED_VALOR_CONVERSION);
        return sugUnidadMedida;
    }

    @BeforeEach
    public void initTest() {
        sugUnidadMedida = createEntity(em);
    }

    @Test
    @Transactional
    public void createSugUnidadMedida() throws Exception {
        int databaseSizeBeforeCreate = sugUnidadMedidaRepository.findAll().size();

        // Create the SugUnidadMedida
        restSugUnidadMedidaMockMvc.perform(post("/api/sug-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugUnidadMedida)))
            .andExpect(status().isCreated());

        // Validate the SugUnidadMedida in the database
        List<SugUnidadMedida> sugUnidadMedidaList = sugUnidadMedidaRepository.findAll();
        assertThat(sugUnidadMedidaList).hasSize(databaseSizeBeforeCreate + 1);
        SugUnidadMedida testSugUnidadMedida = sugUnidadMedidaList.get(sugUnidadMedidaList.size() - 1);
        assertThat(testSugUnidadMedida.getAbreviacion()).isEqualTo(DEFAULT_ABREVIACION);
        assertThat(testSugUnidadMedida.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testSugUnidadMedida.getValorConversion()).isEqualTo(DEFAULT_VALOR_CONVERSION);
    }

    @Test
    @Transactional
    public void createSugUnidadMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sugUnidadMedidaRepository.findAll().size();

        // Create the SugUnidadMedida with an existing ID
        sugUnidadMedida.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSugUnidadMedidaMockMvc.perform(post("/api/sug-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugUnidadMedida)))
            .andExpect(status().isBadRequest());

        // Validate the SugUnidadMedida in the database
        List<SugUnidadMedida> sugUnidadMedidaList = sugUnidadMedidaRepository.findAll();
        assertThat(sugUnidadMedidaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSugUnidadMedidas() throws Exception {
        // Initialize the database
        sugUnidadMedidaRepository.saveAndFlush(sugUnidadMedida);

        // Get all the sugUnidadMedidaList
        restSugUnidadMedidaMockMvc.perform(get("/api/sug-unidad-medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sugUnidadMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].abreviacion").value(hasItem(DEFAULT_ABREVIACION.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].valorConversion").value(hasItem(DEFAULT_VALOR_CONVERSION.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSugUnidadMedida() throws Exception {
        // Initialize the database
        sugUnidadMedidaRepository.saveAndFlush(sugUnidadMedida);

        // Get the sugUnidadMedida
        restSugUnidadMedidaMockMvc.perform(get("/api/sug-unidad-medidas/{id}", sugUnidadMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sugUnidadMedida.getId().intValue()))
            .andExpect(jsonPath("$.abreviacion").value(DEFAULT_ABREVIACION.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.valorConversion").value(DEFAULT_VALOR_CONVERSION.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSugUnidadMedida() throws Exception {
        // Get the sugUnidadMedida
        restSugUnidadMedidaMockMvc.perform(get("/api/sug-unidad-medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSugUnidadMedida() throws Exception {
        // Initialize the database
        sugUnidadMedidaRepository.saveAndFlush(sugUnidadMedida);

        int databaseSizeBeforeUpdate = sugUnidadMedidaRepository.findAll().size();

        // Update the sugUnidadMedida
        SugUnidadMedida updatedSugUnidadMedida = sugUnidadMedidaRepository.findById(sugUnidadMedida.getId()).get();
        // Disconnect from session so that the updates on updatedSugUnidadMedida are not directly saved in db
        em.detach(updatedSugUnidadMedida);
        updatedSugUnidadMedida
            .abreviacion(UPDATED_ABREVIACION)
            .descripcion(UPDATED_DESCRIPCION)
            .valorConversion(UPDATED_VALOR_CONVERSION);

        restSugUnidadMedidaMockMvc.perform(put("/api/sug-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSugUnidadMedida)))
            .andExpect(status().isOk());

        // Validate the SugUnidadMedida in the database
        List<SugUnidadMedida> sugUnidadMedidaList = sugUnidadMedidaRepository.findAll();
        assertThat(sugUnidadMedidaList).hasSize(databaseSizeBeforeUpdate);
        SugUnidadMedida testSugUnidadMedida = sugUnidadMedidaList.get(sugUnidadMedidaList.size() - 1);
        assertThat(testSugUnidadMedida.getAbreviacion()).isEqualTo(UPDATED_ABREVIACION);
        assertThat(testSugUnidadMedida.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testSugUnidadMedida.getValorConversion()).isEqualTo(UPDATED_VALOR_CONVERSION);
    }

    @Test
    @Transactional
    public void updateNonExistingSugUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = sugUnidadMedidaRepository.findAll().size();

        // Create the SugUnidadMedida

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSugUnidadMedidaMockMvc.perform(put("/api/sug-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugUnidadMedida)))
            .andExpect(status().isBadRequest());

        // Validate the SugUnidadMedida in the database
        List<SugUnidadMedida> sugUnidadMedidaList = sugUnidadMedidaRepository.findAll();
        assertThat(sugUnidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSugUnidadMedida() throws Exception {
        // Initialize the database
        sugUnidadMedidaRepository.saveAndFlush(sugUnidadMedida);

        int databaseSizeBeforeDelete = sugUnidadMedidaRepository.findAll().size();

        // Delete the sugUnidadMedida
        restSugUnidadMedidaMockMvc.perform(delete("/api/sug-unidad-medidas/{id}", sugUnidadMedida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SugUnidadMedida> sugUnidadMedidaList = sugUnidadMedidaRepository.findAll();
        assertThat(sugUnidadMedidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SugUnidadMedida.class);
        SugUnidadMedida sugUnidadMedida1 = new SugUnidadMedida();
        sugUnidadMedida1.setId(1L);
        SugUnidadMedida sugUnidadMedida2 = new SugUnidadMedida();
        sugUnidadMedida2.setId(sugUnidadMedida1.getId());
        assertThat(sugUnidadMedida1).isEqualTo(sugUnidadMedida2);
        sugUnidadMedida2.setId(2L);
        assertThat(sugUnidadMedida1).isNotEqualTo(sugUnidadMedida2);
        sugUnidadMedida1.setId(null);
        assertThat(sugUnidadMedida1).isNotEqualTo(sugUnidadMedida2);
    }
}
