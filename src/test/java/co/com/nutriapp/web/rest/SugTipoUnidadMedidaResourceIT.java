package co.com.nutriapp.web.rest;

import co.com.nutriapp.NutriappApp;
import co.com.nutriapp.domain.SugTipoUnidadMedida;
import co.com.nutriapp.repository.SugTipoUnidadMedidaRepository;
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
 * Integration tests for the {@Link SugTipoUnidadMedidaResource} REST controller.
 */
@SpringBootTest(classes = NutriappApp.class)
public class SugTipoUnidadMedidaResourceIT {

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    @Autowired
    private SugTipoUnidadMedidaRepository sugTipoUnidadMedidaRepository;

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

    private MockMvc restSugTipoUnidadMedidaMockMvc;

    private SugTipoUnidadMedida sugTipoUnidadMedida;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SugTipoUnidadMedidaResource sugTipoUnidadMedidaResource = new SugTipoUnidadMedidaResource(sugTipoUnidadMedidaRepository);
        this.restSugTipoUnidadMedidaMockMvc = MockMvcBuilders.standaloneSetup(sugTipoUnidadMedidaResource)
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
    public static SugTipoUnidadMedida createEntity(EntityManager em) {
        SugTipoUnidadMedida sugTipoUnidadMedida = new SugTipoUnidadMedida()
            .nombre(DEFAULT_NOMBRE);
        return sugTipoUnidadMedida;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SugTipoUnidadMedida createUpdatedEntity(EntityManager em) {
        SugTipoUnidadMedida sugTipoUnidadMedida = new SugTipoUnidadMedida()
            .nombre(UPDATED_NOMBRE);
        return sugTipoUnidadMedida;
    }

    @BeforeEach
    public void initTest() {
        sugTipoUnidadMedida = createEntity(em);
    }

    @Test
    @Transactional
    public void createSugTipoUnidadMedida() throws Exception {
        int databaseSizeBeforeCreate = sugTipoUnidadMedidaRepository.findAll().size();

        // Create the SugTipoUnidadMedida
        restSugTipoUnidadMedidaMockMvc.perform(post("/api/sug-tipo-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugTipoUnidadMedida)))
            .andExpect(status().isCreated());

        // Validate the SugTipoUnidadMedida in the database
        List<SugTipoUnidadMedida> sugTipoUnidadMedidaList = sugTipoUnidadMedidaRepository.findAll();
        assertThat(sugTipoUnidadMedidaList).hasSize(databaseSizeBeforeCreate + 1);
        SugTipoUnidadMedida testSugTipoUnidadMedida = sugTipoUnidadMedidaList.get(sugTipoUnidadMedidaList.size() - 1);
        assertThat(testSugTipoUnidadMedida.getNombre()).isEqualTo(DEFAULT_NOMBRE);
    }

    @Test
    @Transactional
    public void createSugTipoUnidadMedidaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sugTipoUnidadMedidaRepository.findAll().size();

        // Create the SugTipoUnidadMedida with an existing ID
        sugTipoUnidadMedida.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSugTipoUnidadMedidaMockMvc.perform(post("/api/sug-tipo-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugTipoUnidadMedida)))
            .andExpect(status().isBadRequest());

        // Validate the SugTipoUnidadMedida in the database
        List<SugTipoUnidadMedida> sugTipoUnidadMedidaList = sugTipoUnidadMedidaRepository.findAll();
        assertThat(sugTipoUnidadMedidaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSugTipoUnidadMedidas() throws Exception {
        // Initialize the database
        sugTipoUnidadMedidaRepository.saveAndFlush(sugTipoUnidadMedida);

        // Get all the sugTipoUnidadMedidaList
        restSugTipoUnidadMedidaMockMvc.perform(get("/api/sug-tipo-unidad-medidas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sugTipoUnidadMedida.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())));
    }
    
    @Test
    @Transactional
    public void getSugTipoUnidadMedida() throws Exception {
        // Initialize the database
        sugTipoUnidadMedidaRepository.saveAndFlush(sugTipoUnidadMedida);

        // Get the sugTipoUnidadMedida
        restSugTipoUnidadMedidaMockMvc.perform(get("/api/sug-tipo-unidad-medidas/{id}", sugTipoUnidadMedida.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sugTipoUnidadMedida.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSugTipoUnidadMedida() throws Exception {
        // Get the sugTipoUnidadMedida
        restSugTipoUnidadMedidaMockMvc.perform(get("/api/sug-tipo-unidad-medidas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSugTipoUnidadMedida() throws Exception {
        // Initialize the database
        sugTipoUnidadMedidaRepository.saveAndFlush(sugTipoUnidadMedida);

        int databaseSizeBeforeUpdate = sugTipoUnidadMedidaRepository.findAll().size();

        // Update the sugTipoUnidadMedida
        SugTipoUnidadMedida updatedSugTipoUnidadMedida = sugTipoUnidadMedidaRepository.findById(sugTipoUnidadMedida.getId()).get();
        // Disconnect from session so that the updates on updatedSugTipoUnidadMedida are not directly saved in db
        em.detach(updatedSugTipoUnidadMedida);
        updatedSugTipoUnidadMedida
            .nombre(UPDATED_NOMBRE);

        restSugTipoUnidadMedidaMockMvc.perform(put("/api/sug-tipo-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSugTipoUnidadMedida)))
            .andExpect(status().isOk());

        // Validate the SugTipoUnidadMedida in the database
        List<SugTipoUnidadMedida> sugTipoUnidadMedidaList = sugTipoUnidadMedidaRepository.findAll();
        assertThat(sugTipoUnidadMedidaList).hasSize(databaseSizeBeforeUpdate);
        SugTipoUnidadMedida testSugTipoUnidadMedida = sugTipoUnidadMedidaList.get(sugTipoUnidadMedidaList.size() - 1);
        assertThat(testSugTipoUnidadMedida.getNombre()).isEqualTo(UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void updateNonExistingSugTipoUnidadMedida() throws Exception {
        int databaseSizeBeforeUpdate = sugTipoUnidadMedidaRepository.findAll().size();

        // Create the SugTipoUnidadMedida

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSugTipoUnidadMedidaMockMvc.perform(put("/api/sug-tipo-unidad-medidas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugTipoUnidadMedida)))
            .andExpect(status().isBadRequest());

        // Validate the SugTipoUnidadMedida in the database
        List<SugTipoUnidadMedida> sugTipoUnidadMedidaList = sugTipoUnidadMedidaRepository.findAll();
        assertThat(sugTipoUnidadMedidaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSugTipoUnidadMedida() throws Exception {
        // Initialize the database
        sugTipoUnidadMedidaRepository.saveAndFlush(sugTipoUnidadMedida);

        int databaseSizeBeforeDelete = sugTipoUnidadMedidaRepository.findAll().size();

        // Delete the sugTipoUnidadMedida
        restSugTipoUnidadMedidaMockMvc.perform(delete("/api/sug-tipo-unidad-medidas/{id}", sugTipoUnidadMedida.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SugTipoUnidadMedida> sugTipoUnidadMedidaList = sugTipoUnidadMedidaRepository.findAll();
        assertThat(sugTipoUnidadMedidaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SugTipoUnidadMedida.class);
        SugTipoUnidadMedida sugTipoUnidadMedida1 = new SugTipoUnidadMedida();
        sugTipoUnidadMedida1.setId(1L);
        SugTipoUnidadMedida sugTipoUnidadMedida2 = new SugTipoUnidadMedida();
        sugTipoUnidadMedida2.setId(sugTipoUnidadMedida1.getId());
        assertThat(sugTipoUnidadMedida1).isEqualTo(sugTipoUnidadMedida2);
        sugTipoUnidadMedida2.setId(2L);
        assertThat(sugTipoUnidadMedida1).isNotEqualTo(sugTipoUnidadMedida2);
        sugTipoUnidadMedida1.setId(null);
        assertThat(sugTipoUnidadMedida1).isNotEqualTo(sugTipoUnidadMedida2);
    }
}
