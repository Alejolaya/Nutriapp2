package co.com.nutriapp.web.rest;

import co.com.nutriapp.NutriappApp;
import co.com.nutriapp.domain.SugCategoria;
import co.com.nutriapp.repository.SugCategoriaRepository;
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
 * Integration tests for the {@Link SugCategoriaResource} REST controller.
 */
@SpringBootTest(classes = NutriappApp.class)
public class SugCategoriaResourceIT {

    private static final String DEFAULT_NOMBRE_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private SugCategoriaRepository sugCategoriaRepository;

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

    private MockMvc restSugCategoriaMockMvc;

    private SugCategoria sugCategoria;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SugCategoriaResource sugCategoriaResource = new SugCategoriaResource(sugCategoriaRepository);
        this.restSugCategoriaMockMvc = MockMvcBuilders.standaloneSetup(sugCategoriaResource)
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
    public static SugCategoria createEntity(EntityManager em) {
        SugCategoria sugCategoria = new SugCategoria()
            .nombreCategoria(DEFAULT_NOMBRE_CATEGORIA)
            .descripcion(DEFAULT_DESCRIPCION);
        return sugCategoria;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SugCategoria createUpdatedEntity(EntityManager em) {
        SugCategoria sugCategoria = new SugCategoria()
            .nombreCategoria(UPDATED_NOMBRE_CATEGORIA)
            .descripcion(UPDATED_DESCRIPCION);
        return sugCategoria;
    }

    @BeforeEach
    public void initTest() {
        sugCategoria = createEntity(em);
    }

    @Test
    @Transactional
    public void createSugCategoria() throws Exception {
        int databaseSizeBeforeCreate = sugCategoriaRepository.findAll().size();

        // Create the SugCategoria
        restSugCategoriaMockMvc.perform(post("/api/sug-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugCategoria)))
            .andExpect(status().isCreated());

        // Validate the SugCategoria in the database
        List<SugCategoria> sugCategoriaList = sugCategoriaRepository.findAll();
        assertThat(sugCategoriaList).hasSize(databaseSizeBeforeCreate + 1);
        SugCategoria testSugCategoria = sugCategoriaList.get(sugCategoriaList.size() - 1);
        assertThat(testSugCategoria.getNombreCategoria()).isEqualTo(DEFAULT_NOMBRE_CATEGORIA);
        assertThat(testSugCategoria.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createSugCategoriaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sugCategoriaRepository.findAll().size();

        // Create the SugCategoria with an existing ID
        sugCategoria.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSugCategoriaMockMvc.perform(post("/api/sug-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugCategoria)))
            .andExpect(status().isBadRequest());

        // Validate the SugCategoria in the database
        List<SugCategoria> sugCategoriaList = sugCategoriaRepository.findAll();
        assertThat(sugCategoriaList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSugCategorias() throws Exception {
        // Initialize the database
        sugCategoriaRepository.saveAndFlush(sugCategoria);

        // Get all the sugCategoriaList
        restSugCategoriaMockMvc.perform(get("/api/sug-categorias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sugCategoria.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreCategoria").value(hasItem(DEFAULT_NOMBRE_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }
    
    @Test
    @Transactional
    public void getSugCategoria() throws Exception {
        // Initialize the database
        sugCategoriaRepository.saveAndFlush(sugCategoria);

        // Get the sugCategoria
        restSugCategoriaMockMvc.perform(get("/api/sug-categorias/{id}", sugCategoria.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sugCategoria.getId().intValue()))
            .andExpect(jsonPath("$.nombreCategoria").value(DEFAULT_NOMBRE_CATEGORIA.toString()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingSugCategoria() throws Exception {
        // Get the sugCategoria
        restSugCategoriaMockMvc.perform(get("/api/sug-categorias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSugCategoria() throws Exception {
        // Initialize the database
        sugCategoriaRepository.saveAndFlush(sugCategoria);

        int databaseSizeBeforeUpdate = sugCategoriaRepository.findAll().size();

        // Update the sugCategoria
        SugCategoria updatedSugCategoria = sugCategoriaRepository.findById(sugCategoria.getId()).get();
        // Disconnect from session so that the updates on updatedSugCategoria are not directly saved in db
        em.detach(updatedSugCategoria);
        updatedSugCategoria
            .nombreCategoria(UPDATED_NOMBRE_CATEGORIA)
            .descripcion(UPDATED_DESCRIPCION);

        restSugCategoriaMockMvc.perform(put("/api/sug-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSugCategoria)))
            .andExpect(status().isOk());

        // Validate the SugCategoria in the database
        List<SugCategoria> sugCategoriaList = sugCategoriaRepository.findAll();
        assertThat(sugCategoriaList).hasSize(databaseSizeBeforeUpdate);
        SugCategoria testSugCategoria = sugCategoriaList.get(sugCategoriaList.size() - 1);
        assertThat(testSugCategoria.getNombreCategoria()).isEqualTo(UPDATED_NOMBRE_CATEGORIA);
        assertThat(testSugCategoria.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingSugCategoria() throws Exception {
        int databaseSizeBeforeUpdate = sugCategoriaRepository.findAll().size();

        // Create the SugCategoria

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSugCategoriaMockMvc.perform(put("/api/sug-categorias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugCategoria)))
            .andExpect(status().isBadRequest());

        // Validate the SugCategoria in the database
        List<SugCategoria> sugCategoriaList = sugCategoriaRepository.findAll();
        assertThat(sugCategoriaList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSugCategoria() throws Exception {
        // Initialize the database
        sugCategoriaRepository.saveAndFlush(sugCategoria);

        int databaseSizeBeforeDelete = sugCategoriaRepository.findAll().size();

        // Delete the sugCategoria
        restSugCategoriaMockMvc.perform(delete("/api/sug-categorias/{id}", sugCategoria.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SugCategoria> sugCategoriaList = sugCategoriaRepository.findAll();
        assertThat(sugCategoriaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SugCategoria.class);
        SugCategoria sugCategoria1 = new SugCategoria();
        sugCategoria1.setId(1L);
        SugCategoria sugCategoria2 = new SugCategoria();
        sugCategoria2.setId(sugCategoria1.getId());
        assertThat(sugCategoria1).isEqualTo(sugCategoria2);
        sugCategoria2.setId(2L);
        assertThat(sugCategoria1).isNotEqualTo(sugCategoria2);
        sugCategoria1.setId(null);
        assertThat(sugCategoria1).isNotEqualTo(sugCategoria2);
    }
}
