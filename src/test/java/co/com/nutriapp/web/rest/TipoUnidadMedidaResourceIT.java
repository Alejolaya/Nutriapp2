package co.com.nutriapp.web.rest;

import co.com.nutriapp.NutriappApp;
import co.com.nutriapp.domain.TipoUnidadMedida;
import co.com.nutriapp.repository.TipoUnidadMedidaRepository;
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
 * Integration tests for the {@Link TipoUnidadMedidaResource} REST controller.
 */
@SpringBootTest(classes = NutriappApp.class)
public class TipoUnidadMedidaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private TipoUnidadMedidaRepository tipoUnidadMedidaRepository;

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

    private MockMvc restTipoUnidadMedidaMockMvc;

    private TipoUnidadMedida tipoUnidadMedida;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoUnidadMedidaResource tipoUnidadMedidaResource = new TipoUnidadMedidaResource(tipoUnidadMedidaRepository);
        this.restTipoUnidadMedidaMockMvc = MockMvcBuilders.standaloneSetup(tipoUnidadMedidaResource)
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
    public static TipoUnidadMedida createEntity(EntityManager em) {
        TipoUnidadMedida tipoUnidadMedida = new TipoUnidadMedida()
            .nombre(DEFAULT_NOMBRE);
        return tipoUnidadMedida;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TipoUnidadMedida createUpdatedEntity(EntityManager em) {
        TipoUnidadMedida tipoUnidadMedida = new TipoUnidadMedida()
            .nombre(UPDATED_NOMBRE);
        return tipoUnidadMedida;
    }

    @BeforeEach
    public void initTest() {
        tipoUnidadMedida = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoUnidadMedida() throws Exception {
        int databaseSizeBeforeCreate = tipoUnidadMedidaRepository.findAll().size();

        // Create the TipoUnidadMedida
        restTipoUnidadMedidaMockMvc.perform(post("/api/tipo-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadMedida)))
            .andExpect(status().isCreated());

        // Validate the TipoUnidadMedida in the database
        List<TipoUnidadMedida> tipoUnidadMedidaList = tipoUnidadMedidaRepository.findAll();
        assertThat(tipoUnidadMedidaList).hasSize(databaseSizeBeforeCreate + 1);
        TipoUnidadMedida testTipoUnidadMedida = tipoUnidadMedidaList.get(tipoUnidadMedidaList.size() - 1);
        assertThat(testTipoUnidadMedida.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createTipoUnidadMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoUnidadMedidaRepository.findAll().size();

        // Create the TipoUnidadMedida with an existing ID
        tipoUnidadMedida.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoUnidadMedidaMockMvc.perform(post("/api/tipo-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadMedida)))
            .andExpect(status().isBadRequest());

        // Validate the TipoUnidadMedida in the database
        List<TipoUnidadMedida> tipoUnidadMedidaList = tipoUnidadMedidaRepository.findAll();
        assertThat(tipoUnidadMedidaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllTipoUnidadMedidas() throws Exception {
        // Initialize the database
        tipoUnidadMedidaRepository.saveAndFlush(tipoUnidadMedida);

        // Get all the tipoUnidadMedidaList
        restTipoUnidadMedidaMockMvc.perform(get("/api/tipo-unidad-medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoUnidadMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getTipoUnidadMedida() throws Exception {
        // Initialize the database
        tipoUnidadMedidaRepository.saveAndFlush(tipoUnidadMedida);

        // Get the tipoUnidadMedida
        restTipoUnidadMedidaMockMvc.perform(get("/api/tipo-unidad-medidas/{id}", tipoUnidadMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoUnidadMedida.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTipoUnidadMedida() throws Exception {
        // Get the tipoUnidadMedida
        restTipoUnidadMedidaMockMvc.perform(get("/api/tipo-unidad-medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoUnidadMedida() throws Exception {
        // Initialize the database
        tipoUnidadMedidaRepository.saveAndFlush(tipoUnidadMedida);

        int databaseSizeBeforeUpdate = tipoUnidadMedidaRepository.findAll().size();

        // Update the tipoUnidadMedida
        TipoUnidadMedida updatedTipoUnidadMedida = tipoUnidadMedidaRepository.findById(tipoUnidadMedida.getId()).get();
        // Disconnect from session so that the updates on updatedTipoUnidadMedida are not directly saved in db
        em.detach(updatedTipoUnidadMedida);
        updatedTipoUnidadMedida
            .nombre(UPDATED_NOMBRE);

        restTipoUnidadMedidaMockMvc.perform(put("/api/tipo-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoUnidadMedida)))
            .andExpect(status().isOk());

        // Validate the TipoUnidadMedida in the database
        List<TipoUnidadMedida> tipoUnidadMedidaList = tipoUnidadMedidaRepository.findAll();
        assertThat(tipoUnidadMedidaList).hasSize(databaseSizeBeforeUpdate);
        TipoUnidadMedida testTipoUnidadMedida = tipoUnidadMedidaList.get(tipoUnidadMedidaList.size() - 1);
        assertThat(testTipoUnidadMedida.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = tipoUnidadMedidaRepository.findAll().size();

        // Create the TipoUnidadMedida

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTipoUnidadMedidaMockMvc.perform(put("/api/tipo-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoUnidadMedida)))
            .andExpect(status().isBadRequest());

        // Validate the TipoUnidadMedida in the database
        List<TipoUnidadMedida> tipoUnidadMedidaList = tipoUnidadMedidaRepository.findAll();
        assertThat(tipoUnidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteTipoUnidadMedida() throws Exception {
        // Initialize the database
        tipoUnidadMedidaRepository.saveAndFlush(tipoUnidadMedida);

        int databaseSizeBeforeDelete = tipoUnidadMedidaRepository.findAll().size();

        // Delete the tipoUnidadMedida
        restTipoUnidadMedidaMockMvc.perform(delete("/api/tipo-unidad-medidas/{id}", tipoUnidadMedida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<TipoUnidadMedida> tipoUnidadMedidaList = tipoUnidadMedidaRepository.findAll();
        assertThat(tipoUnidadMedidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoUnidadMedida.class);
        TipoUnidadMedida tipoUnidadMedida1 = new TipoUnidadMedida();
        tipoUnidadMedida1.setId(1L);
        TipoUnidadMedida tipoUnidadMedida2 = new TipoUnidadMedida();
        tipoUnidadMedida2.setId(tipoUnidadMedida1.getId());
        assertThat(tipoUnidadMedida1).isEqualTo(tipoUnidadMedida2);
        tipoUnidadMedida2.setId(2L);
        assertThat(tipoUnidadMedida1).isNotEqualTo(tipoUnidadMedida2);
        tipoUnidadMedida1.setId(null);
        assertThat(tipoUnidadMedida1).isNotEqualTo(tipoUnidadMedida2);
    }
}
