package co.com.nutriapp.web.rest;

import co.com.nutriapp.NutriappApp;
import co.com.nutriapp.domain.Producto;
import co.com.nutriapp.repository.ProductoRepository;
import co.com.nutriapp.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import java.util.ArrayList;
import java.util.List;

import static co.com.nutriapp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link ProductoResource} REST controller.
 */
@SpringBootTest(classes = NutriappApp.class)
public class ProductoResourceIT {

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

    @Autowired
    private ProductoRepository productoRepository;

    @Mock
    private ProductoRepository productoRepositoryMock;

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

    private MockMvc restProductoMockMvc;

    private Producto producto;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ProductoResource productoResource = new ProductoResource(productoRepository);
        this.restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
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
    public static Producto createEntity(EntityManager em) {
        Producto producto = new Producto()
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
            .codigoDeBarras(DEFAULT_CODIGO_DE_BARRAS);
        return producto;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Producto createUpdatedEntity(EntityManager em) {
        Producto producto = new Producto()
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
            .codigoDeBarras(UPDATED_CODIGO_DE_BARRAS);
        return producto;
    }

    @BeforeEach
    public void initTest() {
        producto = createEntity(em);
    }

    @Test
    @Transactional
    public void createProducto() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isCreated());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate + 1);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombreAlimento()).isEqualTo(DEFAULT_NOMBRE_ALIMENTO);
        assertThat(testProducto.getTamanoPorcion()).isEqualTo(DEFAULT_TAMANO_PORCION);
        assertThat(testProducto.getMedidaCasera()).isEqualTo(DEFAULT_MEDIDA_CASERA);
        assertThat(testProducto.getValorEnergetico()).isEqualTo(DEFAULT_VALOR_ENERGETICO);
        assertThat(testProducto.getCaloriasGrasa()).isEqualTo(DEFAULT_CALORIAS_GRASA);
        assertThat(testProducto.getGrasaTotal()).isEqualTo(DEFAULT_GRASA_TOTAL);
        assertThat(testProducto.getGrasaSaturada()).isEqualTo(DEFAULT_GRASA_SATURADA);
        assertThat(testProducto.getGrasaInsaturada()).isEqualTo(DEFAULT_GRASA_INSATURADA);
        assertThat(testProducto.getGrasaTrans()).isEqualTo(DEFAULT_GRASA_TRANS);
        assertThat(testProducto.getColesterol()).isEqualTo(DEFAULT_COLESTEROL);
        assertThat(testProducto.getSodio()).isEqualTo(DEFAULT_SODIO);
        assertThat(testProducto.getCarbohidrato()).isEqualTo(DEFAULT_CARBOHIDRATO);
        assertThat(testProducto.getFibraDietaria()).isEqualTo(DEFAULT_FIBRA_DIETARIA);
        assertThat(testProducto.getFibraInsoluble()).isEqualTo(DEFAULT_FIBRA_INSOLUBLE);
        assertThat(testProducto.getFibraSoluble()).isEqualTo(DEFAULT_FIBRA_SOLUBLE);
        assertThat(testProducto.getAzucares()).isEqualTo(DEFAULT_AZUCARES);
        assertThat(testProducto.getProteina()).isEqualTo(DEFAULT_PROTEINA);
        assertThat(testProducto.getVitaminaA()).isEqualTo(DEFAULT_VITAMINA_A);
        assertThat(testProducto.getVitaminaC()).isEqualTo(DEFAULT_VITAMINA_C);
        assertThat(testProducto.getCalcio()).isEqualTo(DEFAULT_CALCIO);
        assertThat(testProducto.getHierro()).isEqualTo(DEFAULT_HIERRO);
        assertThat(testProducto.isGluten()).isEqualTo(DEFAULT_GLUTEN);
        assertThat(testProducto.isAzucar()).isEqualTo(DEFAULT_AZUCAR);
        assertThat(testProducto.isIntegral()).isEqualTo(DEFAULT_INTEGRAL);
        assertThat(testProducto.getFechaCreacion()).isEqualTo(DEFAULT_FECHA_CREACION);
        assertThat(testProducto.getFechaUltimaModificacion()).isEqualTo(DEFAULT_FECHA_ULTIMA_MODIFICACION);
        assertThat(testProducto.isEstadoActivo()).isEqualTo(DEFAULT_ESTADO_ACTIVO);
        assertThat(testProducto.getCodigoDeBarras()).isEqualTo(DEFAULT_CODIGO_DE_BARRAS);
    }

    @Test
    @Transactional
    public void createProductoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = productoRepository.findAll().size();

        // Create the Producto with an existing ID
        producto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restProductoMockMvc.perform(post("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllProductos() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get all the productoList
        restProductoMockMvc.perform(get("/api/productos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(producto.getId().intValue())))
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
            .andExpect(jsonPath("$.[*].codigoDeBarras").value(hasItem(DEFAULT_CODIGO_DE_BARRAS.toString())));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllProductosWithEagerRelationshipsIsEnabled() throws Exception {
        ProductoResource productoResource = new ProductoResource(productoRepositoryMock);
        when(productoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        MockMvc restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProductoMockMvc.perform(get("/api/productos?eagerload=true"))
        .andExpect(status().isOk());

        verify(productoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllProductosWithEagerRelationshipsIsNotEnabled() throws Exception {
        ProductoResource productoResource = new ProductoResource(productoRepositoryMock);
            when(productoRepositoryMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));
            MockMvc restProductoMockMvc = MockMvcBuilders.standaloneSetup(productoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();

        restProductoMockMvc.perform(get("/api/productos?eagerload=true"))
        .andExpect(status().isOk());

            verify(productoRepositoryMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", producto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(producto.getId().intValue()))
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
            .andExpect(jsonPath("$.codigoDeBarras").value(DEFAULT_CODIGO_DE_BARRAS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProducto() throws Exception {
        // Get the producto
        restProductoMockMvc.perform(get("/api/productos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Update the producto
        Producto updatedProducto = productoRepository.findById(producto.getId()).get();
        // Disconnect from session so that the updates on updatedProducto are not directly saved in db
        em.detach(updatedProducto);
        updatedProducto
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
            .codigoDeBarras(UPDATED_CODIGO_DE_BARRAS);

        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedProducto)))
            .andExpect(status().isOk());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
        Producto testProducto = productoList.get(productoList.size() - 1);
        assertThat(testProducto.getNombreAlimento()).isEqualTo(UPDATED_NOMBRE_ALIMENTO);
        assertThat(testProducto.getTamanoPorcion()).isEqualTo(UPDATED_TAMANO_PORCION);
        assertThat(testProducto.getMedidaCasera()).isEqualTo(UPDATED_MEDIDA_CASERA);
        assertThat(testProducto.getValorEnergetico()).isEqualTo(UPDATED_VALOR_ENERGETICO);
        assertThat(testProducto.getCaloriasGrasa()).isEqualTo(UPDATED_CALORIAS_GRASA);
        assertThat(testProducto.getGrasaTotal()).isEqualTo(UPDATED_GRASA_TOTAL);
        assertThat(testProducto.getGrasaSaturada()).isEqualTo(UPDATED_GRASA_SATURADA);
        assertThat(testProducto.getGrasaInsaturada()).isEqualTo(UPDATED_GRASA_INSATURADA);
        assertThat(testProducto.getGrasaTrans()).isEqualTo(UPDATED_GRASA_TRANS);
        assertThat(testProducto.getColesterol()).isEqualTo(UPDATED_COLESTEROL);
        assertThat(testProducto.getSodio()).isEqualTo(UPDATED_SODIO);
        assertThat(testProducto.getCarbohidrato()).isEqualTo(UPDATED_CARBOHIDRATO);
        assertThat(testProducto.getFibraDietaria()).isEqualTo(UPDATED_FIBRA_DIETARIA);
        assertThat(testProducto.getFibraInsoluble()).isEqualTo(UPDATED_FIBRA_INSOLUBLE);
        assertThat(testProducto.getFibraSoluble()).isEqualTo(UPDATED_FIBRA_SOLUBLE);
        assertThat(testProducto.getAzucares()).isEqualTo(UPDATED_AZUCARES);
        assertThat(testProducto.getProteina()).isEqualTo(UPDATED_PROTEINA);
        assertThat(testProducto.getVitaminaA()).isEqualTo(UPDATED_VITAMINA_A);
        assertThat(testProducto.getVitaminaC()).isEqualTo(UPDATED_VITAMINA_C);
        assertThat(testProducto.getCalcio()).isEqualTo(UPDATED_CALCIO);
        assertThat(testProducto.getHierro()).isEqualTo(UPDATED_HIERRO);
        assertThat(testProducto.isGluten()).isEqualTo(UPDATED_GLUTEN);
        assertThat(testProducto.isAzucar()).isEqualTo(UPDATED_AZUCAR);
        assertThat(testProducto.isIntegral()).isEqualTo(UPDATED_INTEGRAL);
        assertThat(testProducto.getFechaCreacion()).isEqualTo(UPDATED_FECHA_CREACION);
        assertThat(testProducto.getFechaUltimaModificacion()).isEqualTo(UPDATED_FECHA_ULTIMA_MODIFICACION);
        assertThat(testProducto.isEstadoActivo()).isEqualTo(UPDATED_ESTADO_ACTIVO);
        assertThat(testProducto.getCodigoDeBarras()).isEqualTo(UPDATED_CODIGO_DE_BARRAS);
    }

    @Test
    @Transactional
    public void updateNonExistingProducto() throws Exception {
        int databaseSizeBeforeUpdate = productoRepository.findAll().size();

        // Create the Producto

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restProductoMockMvc.perform(put("/api/productos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(producto)))
            .andExpect(status().isBadRequest());

        // Validate the Producto in the database
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteProducto() throws Exception {
        // Initialize the database
        productoRepository.saveAndFlush(producto);

        int databaseSizeBeforeDelete = productoRepository.findAll().size();

        // Delete the producto
        restProductoMockMvc.perform(delete("/api/productos/{id}", producto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Producto> productoList = productoRepository.findAll();
        assertThat(productoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Producto.class);
        Producto producto1 = new Producto();
        producto1.setId(1L);
        Producto producto2 = new Producto();
        producto2.setId(producto1.getId());
        assertThat(producto1).isEqualTo(producto2);
        producto2.setId(2L);
        assertThat(producto1).isNotEqualTo(producto2);
        producto1.setId(null);
        assertThat(producto1).isNotEqualTo(producto2);
    }
}
