package co.com.nutriapp.web.rest;

import co.com.nutriapp.NutriappApp;
import co.com.nutriapp.domain.SugerenciaProducto;
import co.com.nutriapp.repository.SugerenciaProductoRepository;
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
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static co.com.nutriapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import co.com.nutriapp.domain.enumeration.EstadoSugerencia;
/**
 * Integration tests for the {@Link SugerenciaProductoResource} REST controller.
 */
@SpringBootTest(classes = NutriappApp.class)
public class SugerenciaProductoResourceIT {

    private static final String DEFAULT_NOMBRE_ALIMENTO = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_ALIMENTO = "BBBBBBBBBB";

    private static final Float DEFAULT_TAMANO_PORCION = 1F;
    private static final Float UPDATED_TAMANO_PORCION = 2F;

    private static final Float DEFAULT_MEDIDA_CASERA = 1F;
    private static final Float UPDATED_MEDIDA_CASERA = 2F;

    private static final Float DEFAULT_VALOR_ENERGETICO = 1F;
    private static final Float UPDATED_VALOR_ENERGETICO = 2F;

    private static final Float DEFAULT_CALORIAS_GRASA = 1F;
    private static final Float UPDATED_CALORIAS_GRASA = 2F;

    private static final Float DEFAULT_GRASA_TOTAL = 1F;
    private static final Float UPDATED_GRASA_TOTAL = 2F;

    private static final Float DEFAULT_GRASA_SATURADA = 1F;
    private static final Float UPDATED_GRASA_SATURADA = 2F;

    private static final Float DEFAULT_GRASA_INSATURADA = 1F;
    private static final Float UPDATED_GRASA_INSATURADA = 2F;

    private static final Float DEFAULT_GRASA_TRANS = 1F;
    private static final Float UPDATED_GRASA_TRANS = 2F;

    private static final Float DEFAULT_COLESTEROL = 1F;
    private static final Float UPDATED_COLESTEROL = 2F;

    private static final Float DEFAULT_SODIO = 1F;
    private static final Float UPDATED_SODIO = 2F;

    private static final Float DEFAULT_CARBOHIDRATO = 1F;
    private static final Float UPDATED_CARBOHIDRATO = 2F;

    private static final Float DEFAULT_FIBRA_DIETARIA = 1F;
    private static final Float UPDATED_FIBRA_DIETARIA = 2F;

    private static final Float DEFAULT_FIBRA_INSOLUBLE = 1F;
    private static final Float UPDATED_FIBRA_INSOLUBLE = 2F;

    private static final Float DEFAULT_FIBRA_SOLUBLE = 1F;
    private static final Float UPDATED_FIBRA_SOLUBLE = 2F;

    private static final Float DEFAULT_AZUCARES = 1F;
    private static final Float UPDATED_AZUCARES = 2F;

    private static final Float DEFAULT_PROTEINA = 1F;
    private static final Float UPDATED_PROTEINA = 2F;

    private static final Float DEFAULT_VITAMINA_A = 1F;
    private static final Float UPDATED_VITAMINA_A = 2F;

    private static final Float DEFAULT_VITAMINA_C = 1F;
    private static final Float UPDATED_VITAMINA_C = 2F;

    private static final Float DEFAULT_CALCIO = 1F;
    private static final Float UPDATED_CALCIO = 2F;

    private static final Float DEFAULT_HIERRO = 1F;
    private static final Float UPDATED_HIERRO = 2F;

    private static final Boolean DEFAULT_GLUTEN = false;
    private static final Boolean UPDATED_GLUTEN = true;

    private static final Boolean DEFAULT_AZUCAR = false;
    private static final Boolean UPDATED_AZUCAR = true;

    private static final Boolean DEFAULT_INTEGRAL = false;
    private static final Boolean UPDATED_INTEGRAL = true;

    private static final Instant DEFAULT_FECHA_CREACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_CREACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Instant DEFAULT_FECHA_ULTIMA_MODIFICACION = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHA_ULTIMA_MODIFICACION = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Boolean DEFAULT_ESTADO_ACTIVO = false;
    private static final Boolean UPDATED_ESTADO_ACTIVO = true;

    private static final String DEFAULT_CODIGO_DE_BARRAS = "AAAAAAAAAA";
    private static final String UPDATED_CODIGO_DE_BARRAS = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGEN = "AAAAAAAAAA";
    private static final String UPDATED_IMAGEN = "BBBBBBBBBB";

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final EstadoSugerencia DEFAULT_ESTADO_SUGERENCIA = EstadoSugerencia.APROBADO;
    private static final EstadoSugerencia UPDATED_ESTADO_SUGERENCIA = EstadoSugerencia.RECHAZADO;

    private static final String DEFAULT_TAGS = "AAAAAAAAAA";
    private static final String UPDATED_TAGS = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_MARCA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_MARCA = "BBBBBBBBBB";

    private static final String DEFAULT_NOMBRE_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_CATEGORIA = "AAAAAAAAAA";
    private static final String UPDATED_DESC_CATEGORIA = "BBBBBBBBBB";

    private static final String DEFAULT_NUTRIENTES_ADICIONALES = "AAAAAAAAAA";
    private static final String UPDATED_NUTRIENTES_ADICIONALES = "BBBBBBBBBB";

    private static final String DEFAULT_DESC_UNIDAD_MEDIDA = "AAAAAAAAAA";
    private static final String UPDATED_DESC_UNIDAD_MEDIDA = "BBBBBBBBBB";

    private static final Float DEFAULT_VALOR_UNIDAD_MEDIDA = 1F;
    private static final Float UPDATED_VALOR_UNIDAD_MEDIDA = 2F;

    @Autowired
    private SugerenciaProductoRepository sugerenciaProductoRepository;

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

    private MockMvc restSugerenciaProductoMockMvc;

    private SugerenciaProducto sugerenciaProducto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SugerenciaProductoResource sugerenciaProductoResource = new SugerenciaProductoResource(sugerenciaProductoRepository);
        this.restSugerenciaProductoMockMvc = MockMvcBuilders.standaloneSetup(sugerenciaProductoResource)
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
    public static SugerenciaProducto createEntity(EntityManager em) {
        SugerenciaProducto sugerenciaProducto = new SugerenciaProducto()
            .nombreAlimento(DEFAULT_NOMBRE_ALIMENTO)
            .tamanoPorcion(DEFAULT_TAMANO_PORCION)
            .medidaCasera(DEFAULT_MEDIDA_CASERA)
            .valorEnergetico(DEFAULT_VALOR_ENERGETICO)
            .caloriasGrasa(DEFAULT_CALORIAS_GRASA)
            .grasaTotal(DEFAULT_GRASA_TOTAL)
            .grasaSaturada(DEFAULT_GRASA_SATURADA)
            .grasaInsaturada(DEFAULT_GRASA_INSATURADA)
            .grasaTrans(DEFAULT_GRASA_TRANS)
            .colesterol(DEFAULT_COLESTEROL)
            .sodio(DEFAULT_SODIO)
            .carbohidrato(DEFAULT_CARBOHIDRATO)
            .fibraDietaria(DEFAULT_FIBRA_DIETARIA)
            .fibraInsoluble(DEFAULT_FIBRA_INSOLUBLE)
            .fibraSoluble(DEFAULT_FIBRA_SOLUBLE)
            .azucares(DEFAULT_AZUCARES)
            .proteina(DEFAULT_PROTEINA)
            .vitaminaA(DEFAULT_VITAMINA_A)
            .vitaminaC(DEFAULT_VITAMINA_C)
            .calcio(DEFAULT_CALCIO)
            .hierro(DEFAULT_HIERRO)
            .gluten(DEFAULT_GLUTEN)
            .azucar(DEFAULT_AZUCAR)
            .integral(DEFAULT_INTEGRAL)
            .fechaCreacion(DEFAULT_FECHA_CREACION)
            .fechaUltimaModificacion(DEFAULT_FECHA_ULTIMA_MODIFICACION)
            .estadoActivo(DEFAULT_ESTADO_ACTIVO)
            .codigoDeBarras(DEFAULT_CODIGO_DE_BARRAS)
            .imagen(DEFAULT_IMAGEN)
            .observaciones(DEFAULT_OBSERVACIONES)
            .estadoSugerencia(DEFAULT_ESTADO_SUGERENCIA)
            .tags(DEFAULT_TAGS)
            .nombreMarca(DEFAULT_NOMBRE_MARCA)
            .nombreCategoria(DEFAULT_NOMBRE_CATEGORIA)
            .descCategoria(DEFAULT_DESC_CATEGORIA)
            .nutrientesAdicionales(DEFAULT_NUTRIENTES_ADICIONALES)
            .descUnidadMedida(DEFAULT_DESC_UNIDAD_MEDIDA)
            .valorUnidadMedida(DEFAULT_VALOR_UNIDAD_MEDIDA);
        return sugerenciaProducto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SugerenciaProducto createUpdatedEntity(EntityManager em) {
        SugerenciaProducto sugerenciaProducto = new SugerenciaProducto()
            .nombreAlimento(UPDATED_NOMBRE_ALIMENTO)
            .tamanoPorcion(UPDATED_TAMANO_PORCION)
            .medidaCasera(UPDATED_MEDIDA_CASERA)
            .valorEnergetico(UPDATED_VALOR_ENERGETICO)
            .caloriasGrasa(UPDATED_CALORIAS_GRASA)
            .grasaTotal(UPDATED_GRASA_TOTAL)
            .grasaSaturada(UPDATED_GRASA_SATURADA)
            .grasaInsaturada(UPDATED_GRASA_INSATURADA)
            .grasaTrans(UPDATED_GRASA_TRANS)
            .colesterol(UPDATED_COLESTEROL)
            .sodio(UPDATED_SODIO)
            .carbohidrato(UPDATED_CARBOHIDRATO)
            .fibraDietaria(UPDATED_FIBRA_DIETARIA)
            .fibraInsoluble(UPDATED_FIBRA_INSOLUBLE)
            .fibraSoluble(UPDATED_FIBRA_SOLUBLE)
            .azucares(UPDATED_AZUCARES)
            .proteina(UPDATED_PROTEINA)
            .vitaminaA(UPDATED_VITAMINA_A)
            .vitaminaC(UPDATED_VITAMINA_C)
            .calcio(UPDATED_CALCIO)
            .hierro(UPDATED_HIERRO)
            .gluten(UPDATED_GLUTEN)
            .azucar(UPDATED_AZUCAR)
            .integral(UPDATED_INTEGRAL)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaUltimaModificacion(UPDATED_FECHA_ULTIMA_MODIFICACION)
            .estadoActivo(UPDATED_ESTADO_ACTIVO)
            .codigoDeBarras(UPDATED_CODIGO_DE_BARRAS)
            .imagen(UPDATED_IMAGEN)
            .observaciones(UPDATED_OBSERVACIONES)
            .estadoSugerencia(UPDATED_ESTADO_SUGERENCIA)
            .tags(UPDATED_TAGS)
            .nombreMarca(UPDATED_NOMBRE_MARCA)
            .nombreCategoria(UPDATED_NOMBRE_CATEGORIA)
            .descCategoria(UPDATED_DESC_CATEGORIA)
            .nutrientesAdicionales(UPDATED_NUTRIENTES_ADICIONALES)
            .descUnidadMedida(UPDATED_DESC_UNIDAD_MEDIDA)
            .valorUnidadMedida(UPDATED_VALOR_UNIDAD_MEDIDA);
        return sugerenciaProducto;
    }

    @BeforeEach
    public void initTest() {
        sugerenciaProducto = createEntity(em);
    }

    @Test
    @Transactional
    public void createSugerenciaProducto() throws Exception {
        int databaseSizeBeforeCreate = sugerenciaProductoRepository.findAll().size();

        // Create the SugerenciaProducto
        restSugerenciaProductoMockMvc.perform(post("/api/sugerencia-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugerenciaProducto)))
            .andExpect(status().isCreated());

        // Validate the SugerenciaProducto in the database
        List<SugerenciaProducto> sugerenciaProductoList = sugerenciaProductoRepository.findAll();
        assertThat(sugerenciaProductoList).hasSize(databaseSizeBeforeCreate + 1);
        SugerenciaProducto testSugerenciaProducto = sugerenciaProductoList.get(sugerenciaProductoList.size() - 1);
        assertThat(testSugerenciaProducto.getNombreAlimento()).isEqualTo(DEFAULT_NOMBRE_ALIMENTO);
        assertThat(testSugerenciaProducto.getTamanoPorcion()).isEqualTo(DEFAULT_TAMANO_PORCION);
        assertThat(testSugerenciaProducto.getMedidaCasera()).isEqualTo(DEFAULT_MEDIDA_CASERA);
        assertThat(testSugerenciaProducto.getValorEnergetico()).isEqualTo(DEFAULT_VALOR_ENERGETICO);
        assertThat(testSugerenciaProducto.getCaloriasGrasa()).isEqualTo(DEFAULT_CALORIAS_GRASA);
        assertThat(testSugerenciaProducto.getGrasaTotal()).isEqualTo(DEFAULT_GRASA_TOTAL);
        assertThat(testSugerenciaProducto.getGrasaSaturada()).isEqualTo(DEFAULT_GRASA_SATURADA);
        assertThat(testSugerenciaProducto.getGrasaInsaturada()).isEqualTo(DEFAULT_GRASA_INSATURADA);
        assertThat(testSugerenciaProducto.getGrasaTrans()).isEqualTo(DEFAULT_GRASA_TRANS);
        assertThat(testSugerenciaProducto.getColesterol()).isEqualTo(DEFAULT_COLESTEROL);
        assertThat(testSugerenciaProducto.getSodio()).isEqualTo(DEFAULT_SODIO);
        assertThat(testSugerenciaProducto.getCarbohidrato()).isEqualTo(DEFAULT_CARBOHIDRATO);
        assertThat(testSugerenciaProducto.getFibraDietaria()).isEqualTo(DEFAULT_FIBRA_DIETARIA);
        assertThat(testSugerenciaProducto.getFibraInsoluble()).isEqualTo(DEFAULT_FIBRA_INSOLUBLE);
        assertThat(testSugerenciaProducto.getFibraSoluble()).isEqualTo(DEFAULT_FIBRA_SOLUBLE);
        assertThat(testSugerenciaProducto.getAzucares()).isEqualTo(DEFAULT_AZUCARES);
        assertThat(testSugerenciaProducto.getProteina()).isEqualTo(DEFAULT_PROTEINA);
        assertThat(testSugerenciaProducto.getVitaminaA()).isEqualTo(DEFAULT_VITAMINA_A);
        assertThat(testSugerenciaProducto.getVitaminaC()).isEqualTo(DEFAULT_VITAMINA_C);
        assertThat(testSugerenciaProducto.getCalcio()).isEqualTo(DEFAULT_CALCIO);
        assertThat(testSugerenciaProducto.getHierro()).isEqualTo(DEFAULT_HIERRO);
        assertThat(testSugerenciaProducto.isGluten()).isEqualTo(DEFAULT_GLUTEN);
        assertThat(testSugerenciaProducto.isAzucar()).isEqualTo(DEFAULT_AZUCAR);
        assertThat(testSugerenciaProducto.isIntegral()).isEqualTo(DEFAULT_INTEGRAL);
        assertThat(testSugerenciaProducto.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testSugerenciaProducto.getFechaUltimaModificacion()).isEqualTo(DEFAULT_FECHA_ULTIMA_MODIFICACION);
        assertThat(testSugerenciaProducto.isEstadoActivo()).isEqualTo(DEFAULT_ESTADO_ACTIVO);
        assertThat(testSugerenciaProducto.getCodigoDeBarras()).isEqualTo(DEFAULT_CODIGO_DE_BARRAS);
        assertThat(testSugerenciaProducto.getImagen()).isEqualTo(DEFAULT_IMAGEN);
        assertThat(testSugerenciaProducto.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testSugerenciaProducto.getEstadoSugerencia()).isEqualTo(DEFAULT_ESTADO_SUGERENCIA);
        assertThat(testSugerenciaProducto.getTags()).isEqualTo(DEFAULT_TAGS);
        assertThat(testSugerenciaProducto.getNombreMarca()).isEqualTo(DEFAULT_NOMBRE_MARCA);
        assertThat(testSugerenciaProducto.getNombreCategoria()).isEqualTo(DEFAULT_NOMBRE_CATEGORIA);
        assertThat(testSugerenciaProducto.getDescCategoria()).isEqualTo(DEFAULT_DESC_CATEGORIA);
        assertThat(testSugerenciaProducto.getNutrientesAdicionales()).isEqualTo(DEFAULT_NUTRIENTES_ADICIONALES);
        assertThat(testSugerenciaProducto.getDescUnidadMedida()).isEqualTo(DEFAULT_DESC_UNIDAD_MEDIDA);
        assertThat(testSugerenciaProducto.getValorUnidadMedida()).isEqualTo(DEFAULT_VALOR_UNIDAD_MEDIDA);
    }

    @Test
    @Transactional
    public void createSugerenciaProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = sugerenciaProductoRepository.findAll().size();

        // Create the SugerenciaProducto with an existing ID
        sugerenciaProducto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restSugerenciaProductoMockMvc.perform(post("/api/sugerencia-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugerenciaProducto)))
            .andExpect(status().isBadRequest());

        // Validate the SugerenciaProducto in the database
        List<SugerenciaProducto> sugerenciaProductoList = sugerenciaProductoRepository.findAll();
        assertThat(sugerenciaProductoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllSugerenciaProductos() throws Exception {
        // Initialize the database
        sugerenciaProductoRepository.saveAndFlush(sugerenciaProducto);

        // Get all the sugerenciaProductoList
        restSugerenciaProductoMockMvc.perform(get("/api/sugerencia-productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(sugerenciaProducto.getId().intValue())))
            .andExpect(jsonPath("$.[*].nombreAlimento").value(hasItem(DEFAULT_NOMBRE_ALIMENTO.toString())))
            .andExpect(jsonPath("$.[*].tamanoPorcion").value(hasItem(DEFAULT_TAMANO_PORCION.doubleValue())))
            .andExpect(jsonPath("$.[*].medidaCasera").value(hasItem(DEFAULT_MEDIDA_CASERA.doubleValue())))
            .andExpect(jsonPath("$.[*].valorEnergetico").value(hasItem(DEFAULT_VALOR_ENERGETICO.doubleValue())))
            .andExpect(jsonPath("$.[*].caloriasGrasa").value(hasItem(DEFAULT_CALORIAS_GRASA.doubleValue())))
            .andExpect(jsonPath("$.[*].grasaTotal").value(hasItem(DEFAULT_GRASA_TOTAL.doubleValue())))
            .andExpect(jsonPath("$.[*].grasaSaturada").value(hasItem(DEFAULT_GRASA_SATURADA.doubleValue())))
            .andExpect(jsonPath("$.[*].grasaInsaturada").value(hasItem(DEFAULT_GRASA_INSATURADA.doubleValue())))
            .andExpect(jsonPath("$.[*].grasaTrans").value(hasItem(DEFAULT_GRASA_TRANS.doubleValue())))
            .andExpect(jsonPath("$.[*].colesterol").value(hasItem(DEFAULT_COLESTEROL.doubleValue())))
            .andExpect(jsonPath("$.[*].sodio").value(hasItem(DEFAULT_SODIO.doubleValue())))
            .andExpect(jsonPath("$.[*].carbohidrato").value(hasItem(DEFAULT_CARBOHIDRATO.doubleValue())))
            .andExpect(jsonPath("$.[*].fibraDietaria").value(hasItem(DEFAULT_FIBRA_DIETARIA.doubleValue())))
            .andExpect(jsonPath("$.[*].fibraInsoluble").value(hasItem(DEFAULT_FIBRA_INSOLUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].fibraSoluble").value(hasItem(DEFAULT_FIBRA_SOLUBLE.doubleValue())))
            .andExpect(jsonPath("$.[*].azucares").value(hasItem(DEFAULT_AZUCARES.doubleValue())))
            .andExpect(jsonPath("$.[*].proteina").value(hasItem(DEFAULT_PROTEINA.doubleValue())))
            .andExpect(jsonPath("$.[*].vitaminaA").value(hasItem(DEFAULT_VITAMINA_A.doubleValue())))
            .andExpect(jsonPath("$.[*].vitaminaC").value(hasItem(DEFAULT_VITAMINA_C.doubleValue())))
            .andExpect(jsonPath("$.[*].calcio").value(hasItem(DEFAULT_CALCIO.doubleValue())))
            .andExpect(jsonPath("$.[*].hierro").value(hasItem(DEFAULT_HIERRO.doubleValue())))
            .andExpect(jsonPath("$.[*].gluten").value(hasItem(DEFAULT_GLUTEN.booleanValue())))
            .andExpect(jsonPath("$.[*].azucar").value(hasItem(DEFAULT_AZUCAR.booleanValue())))
            .andExpect(jsonPath("$.[*].integral").value(hasItem(DEFAULT_INTEGRAL.booleanValue())))
            .andExpect(jsonPath("$.[*].fechaCreacion").value(hasItem(DEFAULT_FECHA_CREACION.toString())))
            .andExpect(jsonPath("$.[*].fechaUltimaModificacion").value(hasItem(DEFAULT_FECHA_ULTIMA_MODIFICACION.toString())))
            .andExpect(jsonPath("$.[*].estadoActivo").value(hasItem(DEFAULT_ESTADO_ACTIVO.booleanValue())))
            .andExpect(jsonPath("$.[*].codigoDeBarras").value(hasItem(DEFAULT_CODIGO_DE_BARRAS.toString())))
            .andExpect(jsonPath("$.[*].imagen").value(hasItem(DEFAULT_IMAGEN.toString())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())))
            .andExpect(jsonPath("$.[*].estadoSugerencia").value(hasItem(DEFAULT_ESTADO_SUGERENCIA.toString())))
            .andExpect(jsonPath("$.[*].tags").value(hasItem(DEFAULT_TAGS.toString())))
            .andExpect(jsonPath("$.[*].nombreMarca").value(hasItem(DEFAULT_NOMBRE_MARCA.toString())))
            .andExpect(jsonPath("$.[*].nombreCategoria").value(hasItem(DEFAULT_NOMBRE_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].descCategoria").value(hasItem(DEFAULT_DESC_CATEGORIA.toString())))
            .andExpect(jsonPath("$.[*].nutrientesAdicionales").value(hasItem(DEFAULT_NUTRIENTES_ADICIONALES.toString())))
            .andExpect(jsonPath("$.[*].descUnidadMedida").value(hasItem(DEFAULT_DESC_UNIDAD_MEDIDA.toString())))
            .andExpect(jsonPath("$.[*].valorUnidadMedida").value(hasItem(DEFAULT_VALOR_UNIDAD_MEDIDA.doubleValue())));
    }
    
    @Test
    @Transactional
    public void getSugerenciaProducto() throws Exception {
        // Initialize the database
        sugerenciaProductoRepository.saveAndFlush(sugerenciaProducto);

        // Get the sugerenciaProducto
        restSugerenciaProductoMockMvc.perform(get("/api/sugerencia-productos/{id}", sugerenciaProducto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(sugerenciaProducto.getId().intValue()))
            .andExpect(jsonPath("$.nombreAlimento").value(DEFAULT_NOMBRE_ALIMENTO.toString()))
            .andExpect(jsonPath("$.tamanoPorcion").value(DEFAULT_TAMANO_PORCION.doubleValue()))
            .andExpect(jsonPath("$.medidaCasera").value(DEFAULT_MEDIDA_CASERA.doubleValue()))
            .andExpect(jsonPath("$.valorEnergetico").value(DEFAULT_VALOR_ENERGETICO.doubleValue()))
            .andExpect(jsonPath("$.caloriasGrasa").value(DEFAULT_CALORIAS_GRASA.doubleValue()))
            .andExpect(jsonPath("$.grasaTotal").value(DEFAULT_GRASA_TOTAL.doubleValue()))
            .andExpect(jsonPath("$.grasaSaturada").value(DEFAULT_GRASA_SATURADA.doubleValue()))
            .andExpect(jsonPath("$.grasaInsaturada").value(DEFAULT_GRASA_INSATURADA.doubleValue()))
            .andExpect(jsonPath("$.grasaTrans").value(DEFAULT_GRASA_TRANS.doubleValue()))
            .andExpect(jsonPath("$.colesterol").value(DEFAULT_COLESTEROL.doubleValue()))
            .andExpect(jsonPath("$.sodio").value(DEFAULT_SODIO.doubleValue()))
            .andExpect(jsonPath("$.carbohidrato").value(DEFAULT_CARBOHIDRATO.doubleValue()))
            .andExpect(jsonPath("$.fibraDietaria").value(DEFAULT_FIBRA_DIETARIA.doubleValue()))
            .andExpect(jsonPath("$.fibraInsoluble").value(DEFAULT_FIBRA_INSOLUBLE.doubleValue()))
            .andExpect(jsonPath("$.fibraSoluble").value(DEFAULT_FIBRA_SOLUBLE.doubleValue()))
            .andExpect(jsonPath("$.azucares").value(DEFAULT_AZUCARES.doubleValue()))
            .andExpect(jsonPath("$.proteina").value(DEFAULT_PROTEINA.doubleValue()))
            .andExpect(jsonPath("$.vitaminaA").value(DEFAULT_VITAMINA_A.doubleValue()))
            .andExpect(jsonPath("$.vitaminaC").value(DEFAULT_VITAMINA_C.doubleValue()))
            .andExpect(jsonPath("$.calcio").value(DEFAULT_CALCIO.doubleValue()))
            .andExpect(jsonPath("$.hierro").value(DEFAULT_HIERRO.doubleValue()))
            .andExpect(jsonPath("$.gluten").value(DEFAULT_GLUTEN.booleanValue()))
            .andExpect(jsonPath("$.azucar").value(DEFAULT_AZUCAR.booleanValue()))
            .andExpect(jsonPath("$.integral").value(DEFAULT_INTEGRAL.booleanValue()))
            .andExpect(jsonPath("$.fechaCreacion").value(DEFAULT_FECHA_CREACION.toString()))
            .andExpect(jsonPath("$.fechaUltimaModificacion").value(DEFAULT_FECHA_ULTIMA_MODIFICACION.toString()))
            .andExpect(jsonPath("$.estadoActivo").value(DEFAULT_ESTADO_ACTIVO.booleanValue()))
            .andExpect(jsonPath("$.codigoDeBarras").value(DEFAULT_CODIGO_DE_BARRAS.toString()))
            .andExpect(jsonPath("$.imagen").value(DEFAULT_IMAGEN.toString()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()))
            .andExpect(jsonPath("$.estadoSugerencia").value(DEFAULT_ESTADO_SUGERENCIA.toString()))
            .andExpect(jsonPath("$.tags").value(DEFAULT_TAGS.toString()))
            .andExpect(jsonPath("$.nombreMarca").value(DEFAULT_NOMBRE_MARCA.toString()))
            .andExpect(jsonPath("$.nombreCategoria").value(DEFAULT_NOMBRE_CATEGORIA.toString()))
            .andExpect(jsonPath("$.descCategoria").value(DEFAULT_DESC_CATEGORIA.toString()))
            .andExpect(jsonPath("$.nutrientesAdicionales").value(DEFAULT_NUTRIENTES_ADICIONALES.toString()))
            .andExpect(jsonPath("$.descUnidadMedida").value(DEFAULT_DESC_UNIDAD_MEDIDA.toString()))
            .andExpect(jsonPath("$.valorUnidadMedida").value(DEFAULT_VALOR_UNIDAD_MEDIDA.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingSugerenciaProducto() throws Exception {
        // Get the sugerenciaProducto
        restSugerenciaProductoMockMvc.perform(get("/api/sugerencia-productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateSugerenciaProducto() throws Exception {
        // Initialize the database
        sugerenciaProductoRepository.saveAndFlush(sugerenciaProducto);

        int databaseSizeBeforeUpdate = sugerenciaProductoRepository.findAll().size();

        // Update the sugerenciaProducto
        SugerenciaProducto updatedSugerenciaProducto = sugerenciaProductoRepository.findById(sugerenciaProducto.getId()).get();
        // Disconnect from session so that the updates on updatedSugerenciaProducto are not directly saved in db
        em.detach(updatedSugerenciaProducto);
        updatedSugerenciaProducto
            .nombreAlimento(UPDATED_NOMBRE_ALIMENTO)
            .tamanoPorcion(UPDATED_TAMANO_PORCION)
            .medidaCasera(UPDATED_MEDIDA_CASERA)
            .valorEnergetico(UPDATED_VALOR_ENERGETICO)
            .caloriasGrasa(UPDATED_CALORIAS_GRASA)
            .grasaTotal(UPDATED_GRASA_TOTAL)
            .grasaSaturada(UPDATED_GRASA_SATURADA)
            .grasaInsaturada(UPDATED_GRASA_INSATURADA)
            .grasaTrans(UPDATED_GRASA_TRANS)
            .colesterol(UPDATED_COLESTEROL)
            .sodio(UPDATED_SODIO)
            .carbohidrato(UPDATED_CARBOHIDRATO)
            .fibraDietaria(UPDATED_FIBRA_DIETARIA)
            .fibraInsoluble(UPDATED_FIBRA_INSOLUBLE)
            .fibraSoluble(UPDATED_FIBRA_SOLUBLE)
            .azucares(UPDATED_AZUCARES)
            .proteina(UPDATED_PROTEINA)
            .vitaminaA(UPDATED_VITAMINA_A)
            .vitaminaC(UPDATED_VITAMINA_C)
            .calcio(UPDATED_CALCIO)
            .hierro(UPDATED_HIERRO)
            .gluten(UPDATED_GLUTEN)
            .azucar(UPDATED_AZUCAR)
            .integral(UPDATED_INTEGRAL)
            .fechaCreacion(UPDATED_FECHA_CREACION)
            .fechaUltimaModificacion(UPDATED_FECHA_ULTIMA_MODIFICACION)
            .estadoActivo(UPDATED_ESTADO_ACTIVO)
            .codigoDeBarras(UPDATED_CODIGO_DE_BARRAS)
            .imagen(UPDATED_IMAGEN)
            .observaciones(UPDATED_OBSERVACIONES)
            .estadoSugerencia(UPDATED_ESTADO_SUGERENCIA)
            .tags(UPDATED_TAGS)
            .nombreMarca(UPDATED_NOMBRE_MARCA)
            .nombreCategoria(UPDATED_NOMBRE_CATEGORIA)
            .descCategoria(UPDATED_DESC_CATEGORIA)
            .nutrientesAdicionales(UPDATED_NUTRIENTES_ADICIONALES)
            .descUnidadMedida(UPDATED_DESC_UNIDAD_MEDIDA)
            .valorUnidadMedida(UPDATED_VALOR_UNIDAD_MEDIDA);

        restSugerenciaProductoMockMvc.perform(put("/api/sugerencia-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedSugerenciaProducto)))
            .andExpect(status().isOk());

        // Validate the SugerenciaProducto in the database
        List<SugerenciaProducto> sugerenciaProductoList = sugerenciaProductoRepository.findAll();
        assertThat(sugerenciaProductoList).hasSize(databaseSizeBeforeUpdate);
        SugerenciaProducto testSugerenciaProducto = sugerenciaProductoList.get(sugerenciaProductoList.size() - 1);
        assertThat(testSugerenciaProducto.getNombreAlimento()).isEqualTo(UPDATED_NOMBRE_ALIMENTO);
        assertThat(testSugerenciaProducto.getTamanoPorcion()).isEqualTo(UPDATED_TAMANO_PORCION);
        assertThat(testSugerenciaProducto.getMedidaCasera()).isEqualTo(UPDATED_MEDIDA_CASERA);
        assertThat(testSugerenciaProducto.getValorEnergetico()).isEqualTo(UPDATED_VALOR_ENERGETICO);
        assertThat(testSugerenciaProducto.getCaloriasGrasa()).isEqualTo(UPDATED_CALORIAS_GRASA);
        assertThat(testSugerenciaProducto.getGrasaTotal()).isEqualTo(UPDATED_GRASA_TOTAL);
        assertThat(testSugerenciaProducto.getGrasaSaturada()).isEqualTo(UPDATED_GRASA_SATURADA);
        assertThat(testSugerenciaProducto.getGrasaInsaturada()).isEqualTo(UPDATED_GRASA_INSATURADA);
        assertThat(testSugerenciaProducto.getGrasaTrans()).isEqualTo(UPDATED_GRASA_TRANS);
        assertThat(testSugerenciaProducto.getColesterol()).isEqualTo(UPDATED_COLESTEROL);
        assertThat(testSugerenciaProducto.getSodio()).isEqualTo(UPDATED_SODIO);
        assertThat(testSugerenciaProducto.getCarbohidrato()).isEqualTo(UPDATED_CARBOHIDRATO);
        assertThat(testSugerenciaProducto.getFibraDietaria()).isEqualTo(UPDATED_FIBRA_DIETARIA);
        assertThat(testSugerenciaProducto.getFibraInsoluble()).isEqualTo(UPDATED_FIBRA_INSOLUBLE);
        assertThat(testSugerenciaProducto.getFibraSoluble()).isEqualTo(UPDATED_FIBRA_SOLUBLE);
        assertThat(testSugerenciaProducto.getAzucares()).isEqualTo(UPDATED_AZUCARES);
        assertThat(testSugerenciaProducto.getProteina()).isEqualTo(UPDATED_PROTEINA);
        assertThat(testSugerenciaProducto.getVitaminaA()).isEqualTo(UPDATED_VITAMINA_A);
        assertThat(testSugerenciaProducto.getVitaminaC()).isEqualTo(UPDATED_VITAMINA_C);
        assertThat(testSugerenciaProducto.getCalcio()).isEqualTo(UPDATED_CALCIO);
        assertThat(testSugerenciaProducto.getHierro()).isEqualTo(UPDATED_HIERRO);
        assertThat(testSugerenciaProducto.isGluten()).isEqualTo(UPDATED_GLUTEN);
        assertThat(testSugerenciaProducto.isAzucar()).isEqualTo(UPDATED_AZUCAR);
        assertThat(testSugerenciaProducto.isIntegral()).isEqualTo(UPDATED_INTEGRAL);
        assertThat(testSugerenciaProducto.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testSugerenciaProducto.getFechaUltimaModificacion()).isEqualTo(UPDATED_FECHA_ULTIMA_MODIFICACION);
        assertThat(testSugerenciaProducto.isEstadoActivo()).isEqualTo(UPDATED_ESTADO_ACTIVO);
        assertThat(testSugerenciaProducto.getCodigoDeBarras()).isEqualTo(UPDATED_CODIGO_DE_BARRAS);
        assertThat(testSugerenciaProducto.getImagen()).isEqualTo(UPDATED_IMAGEN);
        assertThat(testSugerenciaProducto.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testSugerenciaProducto.getEstadoSugerencia()).isEqualTo(UPDATED_ESTADO_SUGERENCIA);
        assertThat(testSugerenciaProducto.getTags()).isEqualTo(UPDATED_TAGS);
        assertThat(testSugerenciaProducto.getNombreMarca()).isEqualTo(UPDATED_NOMBRE_MARCA);
        assertThat(testSugerenciaProducto.getNombreCategoria()).isEqualTo(UPDATED_NOMBRE_CATEGORIA);
        assertThat(testSugerenciaProducto.getDescCategoria()).isEqualTo(UPDATED_DESC_CATEGORIA);
        assertThat(testSugerenciaProducto.getNutrientesAdicionales()).isEqualTo(UPDATED_NUTRIENTES_ADICIONALES);
        assertThat(testSugerenciaProducto.getDescUnidadMedida()).isEqualTo(UPDATED_DESC_UNIDAD_MEDIDA);
        assertThat(testSugerenciaProducto.getValorUnidadMedida()).isEqualTo(UPDATED_VALOR_UNIDAD_MEDIDA);
    }

    @Test
    @Transactional
    public void updateNonExistingSugerenciaProducto() throws Exception {
        int databaseSizeBeforeUpdate = sugerenciaProductoRepository.findAll().size();

        // Create the SugerenciaProducto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSugerenciaProductoMockMvc.perform(put("/api/sugerencia-productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(sugerenciaProducto)))
            .andExpect(status().isBadRequest());

        // Validate the SugerenciaProducto in the database
        List<SugerenciaProducto> sugerenciaProductoList = sugerenciaProductoRepository.findAll();
        assertThat(sugerenciaProductoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteSugerenciaProducto() throws Exception {
        // Initialize the database
        sugerenciaProductoRepository.saveAndFlush(sugerenciaProducto);

        int databaseSizeBeforeDelete = sugerenciaProductoRepository.findAll().size();

        // Delete the sugerenciaProducto
        restSugerenciaProductoMockMvc.perform(delete("/api/sugerencia-productos/{id}", sugerenciaProducto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<SugerenciaProducto> sugerenciaProductoList = sugerenciaProductoRepository.findAll();
        assertThat(sugerenciaProductoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SugerenciaProducto.class);
        SugerenciaProducto sugerenciaProducto1 = new SugerenciaProducto();
        sugerenciaProducto1.setId(1L);
        SugerenciaProducto sugerenciaProducto2 = new SugerenciaProducto();
        sugerenciaProducto2.setId(sugerenciaProducto1.getId());
        assertThat(sugerenciaProducto1).isEqualTo(sugerenciaProducto2);
        sugerenciaProducto2.setId(2L);
        assertThat(sugerenciaProducto1).isNotEqualTo(sugerenciaProducto2);
        sugerenciaProducto1.setId(null);
        assertThat(sugerenciaProducto1).isNotEqualTo(sugerenciaProducto2);
    }
}
