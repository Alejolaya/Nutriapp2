package co.com.nutriapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import co.com.nutriapp.domain.enumeration.EstadoSugerencia;

/**
 * A SugerenciaProducto.
 */
@Entity
@Table(name = "sugerencia_producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SugerenciaProducto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_alimento")
    private String nombreAlimento;

    @Column(name = "tamano_porcion")
    private Float tamanoPorcion;

    @Column(name = "medida_casera")
    private Float medidaCasera;

    @Column(name = "valor_energetico")
    private Float valorEnergetico;

    @Column(name = "calorias_grasa")
    private Float caloriasGrasa;

    @Column(name = "grasa_total")
    private Float grasaTotal;

    @Column(name = "grasa_saturada")
    private Float grasaSaturada;

    @Column(name = "grasa_insaturada")
    private Float grasaInsaturada;

    @Column(name = "grasa_trans")
    private Float grasaTrans;

    @Column(name = "colesterol")
    private Float colesterol;

    @Column(name = "sodio")
    private Float sodio;

    @Column(name = "carbohidrato")
    private Float carbohidrato;

    @Column(name = "fibra_dietaria")
    private Float fibraDietaria;

    @Column(name = "fibra_insoluble")
    private Float fibraInsoluble;

    @Column(name = "fibra_soluble")
    private Float fibraSoluble;

    @Column(name = "azucares")
    private Float azucares;

    @Column(name = "proteina")
    private Float proteina;

    @Column(name = "vitamina_a")
    private Float vitaminaA;

    @Column(name = "vitamina_c")
    private Float vitaminaC;

    @Column(name = "calcio")
    private Float calcio;

    @Column(name = "hierro")
    private Float hierro;

    @Column(name = "gluten")
    private Boolean gluten;

    @Column(name = "azucar")
    private Boolean azucar;

    @Column(name = "integral")
    private Boolean integral;

    @Column(name = "fecha_creacion")
    private Instant fechaCreacion;

    @Column(name = "fecha_ultima_modificacion")
    private Instant fechaUltimaModificacion;

    @Column(name = "estado_activo")
    private Boolean estadoActivo;

    @Column(name = "codigo_de_barras")
    private String codigoDeBarras;

    @Column(name = "imagen")
    private String imagen;

    @Column(name = "observaciones")
    private String observaciones;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_sugerencia")
    private EstadoSugerencia estadoSugerencia;

    @ManyToOne
    @JsonIgnoreProperties("sugerenciaProductos")
    private SugUnidadMedida unidadPorcion;

    @ManyToOne
    @JsonIgnoreProperties("sugerenciaProductos")
    private SugUnidadMedida unidadMedidaCasera;

    @ManyToOne
    @JsonIgnoreProperties("sugerenciaProductos")
    private SugMarca marca;

    @ManyToOne
    @JsonIgnoreProperties("sugerenciaProductos")
    private SugCategoria categoria;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sugerencia_producto_tags",
               joinColumns = @JoinColumn(name = "sugerencia_producto_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
    private Set<SugTags> tags = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "sugerencia_producto_nutrientes_adicionales",
               joinColumns = @JoinColumn(name = "sugerencia_producto_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "nutrientes_adicionales_id", referencedColumnName = "id"))
    private Set<SugNutrientesAdicionales> nutrientesAdicionales = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombreAlimento() {
        return nombreAlimento;
    }

    public SugerenciaProducto nombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
        return this;
    }

    public void setNombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public Float getTamanoPorcion() {
        return tamanoPorcion;
    }

    public SugerenciaProducto tamanoPorcion(Float tamanoPorcion) {
        this.tamanoPorcion = tamanoPorcion;
        return this;
    }

    public void setTamanoPorcion(Float tamanoPorcion) {
        this.tamanoPorcion = tamanoPorcion;
    }

    public Float getMedidaCasera() {
        return medidaCasera;
    }

    public SugerenciaProducto medidaCasera(Float medidaCasera) {
        this.medidaCasera = medidaCasera;
        return this;
    }

    public void setMedidaCasera(Float medidaCasera) {
        this.medidaCasera = medidaCasera;
    }

    public Float getValorEnergetico() {
        return valorEnergetico;
    }

    public SugerenciaProducto valorEnergetico(Float valorEnergetico) {
        this.valorEnergetico = valorEnergetico;
        return this;
    }

    public void setValorEnergetico(Float valorEnergetico) {
        this.valorEnergetico = valorEnergetico;
    }

    public Float getCaloriasGrasa() {
        return caloriasGrasa;
    }

    public SugerenciaProducto caloriasGrasa(Float caloriasGrasa) {
        this.caloriasGrasa = caloriasGrasa;
        return this;
    }

    public void setCaloriasGrasa(Float caloriasGrasa) {
        this.caloriasGrasa = caloriasGrasa;
    }

    public Float getGrasaTotal() {
        return grasaTotal;
    }

    public SugerenciaProducto grasaTotal(Float grasaTotal) {
        this.grasaTotal = grasaTotal;
        return this;
    }

    public void setGrasaTotal(Float grasaTotal) {
        this.grasaTotal = grasaTotal;
    }

    public Float getGrasaSaturada() {
        return grasaSaturada;
    }

    public SugerenciaProducto grasaSaturada(Float grasaSaturada) {
        this.grasaSaturada = grasaSaturada;
        return this;
    }

    public void setGrasaSaturada(Float grasaSaturada) {
        this.grasaSaturada = grasaSaturada;
    }

    public Float getGrasaInsaturada() {
        return grasaInsaturada;
    }

    public SugerenciaProducto grasaInsaturada(Float grasaInsaturada) {
        this.grasaInsaturada = grasaInsaturada;
        return this;
    }

    public void setGrasaInsaturada(Float grasaInsaturada) {
        this.grasaInsaturada = grasaInsaturada;
    }

    public Float getGrasaTrans() {
        return grasaTrans;
    }

    public SugerenciaProducto grasaTrans(Float grasaTrans) {
        this.grasaTrans = grasaTrans;
        return this;
    }

    public void setGrasaTrans(Float grasaTrans) {
        this.grasaTrans = grasaTrans;
    }

    public Float getColesterol() {
        return colesterol;
    }

    public SugerenciaProducto colesterol(Float colesterol) {
        this.colesterol = colesterol;
        return this;
    }

    public void setColesterol(Float colesterol) {
        this.colesterol = colesterol;
    }

    public Float getSodio() {
        return sodio;
    }

    public SugerenciaProducto sodio(Float sodio) {
        this.sodio = sodio;
        return this;
    }

    public void setSodio(Float sodio) {
        this.sodio = sodio;
    }

    public Float getCarbohidrato() {
        return carbohidrato;
    }

    public SugerenciaProducto carbohidrato(Float carbohidrato) {
        this.carbohidrato = carbohidrato;
        return this;
    }

    public void setCarbohidrato(Float carbohidrato) {
        this.carbohidrato = carbohidrato;
    }

    public Float getFibraDietaria() {
        return fibraDietaria;
    }

    public SugerenciaProducto fibraDietaria(Float fibraDietaria) {
        this.fibraDietaria = fibraDietaria;
        return this;
    }

    public void setFibraDietaria(Float fibraDietaria) {
        this.fibraDietaria = fibraDietaria;
    }

    public Float getFibraInsoluble() {
        return fibraInsoluble;
    }

    public SugerenciaProducto fibraInsoluble(Float fibraInsoluble) {
        this.fibraInsoluble = fibraInsoluble;
        return this;
    }

    public void setFibraInsoluble(Float fibraInsoluble) {
        this.fibraInsoluble = fibraInsoluble;
    }

    public Float getFibraSoluble() {
        return fibraSoluble;
    }

    public SugerenciaProducto fibraSoluble(Float fibraSoluble) {
        this.fibraSoluble = fibraSoluble;
        return this;
    }

    public void setFibraSoluble(Float fibraSoluble) {
        this.fibraSoluble = fibraSoluble;
    }

    public Float getAzucares() {
        return azucares;
    }

    public SugerenciaProducto azucares(Float azucares) {
        this.azucares = azucares;
        return this;
    }

    public void setAzucares(Float azucares) {
        this.azucares = azucares;
    }

    public Float getProteina() {
        return proteina;
    }

    public SugerenciaProducto proteina(Float proteina) {
        this.proteina = proteina;
        return this;
    }

    public void setProteina(Float proteina) {
        this.proteina = proteina;
    }

    public Float getVitaminaA() {
        return vitaminaA;
    }

    public SugerenciaProducto vitaminaA(Float vitaminaA) {
        this.vitaminaA = vitaminaA;
        return this;
    }

    public void setVitaminaA(Float vitaminaA) {
        this.vitaminaA = vitaminaA;
    }

    public Float getVitaminaC() {
        return vitaminaC;
    }

    public SugerenciaProducto vitaminaC(Float vitaminaC) {
        this.vitaminaC = vitaminaC;
        return this;
    }

    public void setVitaminaC(Float vitaminaC) {
        this.vitaminaC = vitaminaC;
    }

    public Float getCalcio() {
        return calcio;
    }

    public SugerenciaProducto calcio(Float calcio) {
        this.calcio = calcio;
        return this;
    }

    public void setCalcio(Float calcio) {
        this.calcio = calcio;
    }

    public Float getHierro() {
        return hierro;
    }

    public SugerenciaProducto hierro(Float hierro) {
        this.hierro = hierro;
        return this;
    }

    public void setHierro(Float hierro) {
        this.hierro = hierro;
    }

    public Boolean isGluten() {
        return gluten;
    }

    public SugerenciaProducto gluten(Boolean gluten) {
        this.gluten = gluten;
        return this;
    }

    public void setGluten(Boolean gluten) {
        this.gluten = gluten;
    }

    public Boolean isAzucar() {
        return azucar;
    }

    public SugerenciaProducto azucar(Boolean azucar) {
        this.azucar = azucar;
        return this;
    }

    public void setAzucar(Boolean azucar) {
        this.azucar = azucar;
    }

    public Boolean isIntegral() {
        return integral;
    }

    public SugerenciaProducto integral(Boolean integral) {
        this.integral = integral;
        return this;
    }

    public void setIntegral(Boolean integral) {
        this.integral = integral;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public SugerenciaProducto fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public SugerenciaProducto fechaUltimaModificacion(Instant fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
        return this;
    }

    public void setFechaUltimaModificacion(Instant fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Boolean isEstadoActivo() {
        return estadoActivo;
    }

    public SugerenciaProducto estadoActivo(Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
        return this;
    }

    public void setEstadoActivo(Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public SugerenciaProducto codigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
        return this;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public String getImagen() {
        return imagen;
    }

    public SugerenciaProducto imagen(String imagen) {
        this.imagen = imagen;
        return this;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public SugerenciaProducto observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public EstadoSugerencia getEstadoSugerencia() {
        return estadoSugerencia;
    }

    public SugerenciaProducto estadoSugerencia(EstadoSugerencia estadoSugerencia) {
        this.estadoSugerencia = estadoSugerencia;
        return this;
    }

    public void setEstadoSugerencia(EstadoSugerencia estadoSugerencia) {
        this.estadoSugerencia = estadoSugerencia;
    }

    public SugUnidadMedida getUnidadPorcion() {
        return unidadPorcion;
    }

    public SugerenciaProducto unidadPorcion(SugUnidadMedida sugUnidadMedida) {
        this.unidadPorcion = sugUnidadMedida;
        return this;
    }

    public void setUnidadPorcion(SugUnidadMedida sugUnidadMedida) {
        this.unidadPorcion = sugUnidadMedida;
    }

    public SugUnidadMedida getUnidadMedidaCasera() {
        return unidadMedidaCasera;
    }

    public SugerenciaProducto unidadMedidaCasera(SugUnidadMedida sugUnidadMedida) {
        this.unidadMedidaCasera = sugUnidadMedida;
        return this;
    }

    public void setUnidadMedidaCasera(SugUnidadMedida sugUnidadMedida) {
        this.unidadMedidaCasera = sugUnidadMedida;
    }

    public SugMarca getMarca() {
        return marca;
    }

    public SugerenciaProducto marca(SugMarca sugMarca) {
        this.marca = sugMarca;
        return this;
    }

    public void setMarca(SugMarca sugMarca) {
        this.marca = sugMarca;
    }

    public SugCategoria getCategoria() {
        return categoria;
    }

    public SugerenciaProducto categoria(SugCategoria sugCategoria) {
        this.categoria = sugCategoria;
        return this;
    }

    public void setCategoria(SugCategoria sugCategoria) {
        this.categoria = sugCategoria;
    }

    public Set<SugTags> getTags() {
        return tags;
    }

    public SugerenciaProducto tags(Set<SugTags> sugTags) {
        this.tags = sugTags;
        return this;
    }

    public SugerenciaProducto addTags(SugTags sugTags) {
        this.tags.add(sugTags);
        sugTags.getSugerenciaProductos().add(this);
        return this;
    }

    public SugerenciaProducto removeTags(SugTags sugTags) {
        this.tags.remove(sugTags);
        sugTags.getSugerenciaProductos().remove(this);
        return this;
    }

    public void setTags(Set<SugTags> sugTags) {
        this.tags = sugTags;
    }

    public Set<SugNutrientesAdicionales> getNutrientesAdicionales() {
        return nutrientesAdicionales;
    }

    public SugerenciaProducto nutrientesAdicionales(Set<SugNutrientesAdicionales> sugNutrientesAdicionales) {
        this.nutrientesAdicionales = sugNutrientesAdicionales;
        return this;
    }

    public SugerenciaProducto addNutrientesAdicionales(SugNutrientesAdicionales sugNutrientesAdicionales) {
        this.nutrientesAdicionales.add(sugNutrientesAdicionales);
        sugNutrientesAdicionales.getSugerenciaProductos().add(this);
        return this;
    }

    public SugerenciaProducto removeNutrientesAdicionales(SugNutrientesAdicionales sugNutrientesAdicionales) {
        this.nutrientesAdicionales.remove(sugNutrientesAdicionales);
        sugNutrientesAdicionales.getSugerenciaProductos().remove(this);
        return this;
    }

    public void setNutrientesAdicionales(Set<SugNutrientesAdicionales> sugNutrientesAdicionales) {
        this.nutrientesAdicionales = sugNutrientesAdicionales;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SugerenciaProducto)) {
            return false;
        }
        return id != null && id.equals(((SugerenciaProducto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SugerenciaProducto{" +
            "id=" + getId() +
            ", nombreAlimento='" + getNombreAlimento() + "'" +
            ", tamanoPorcion=" + getTamanoPorcion() +
            ", medidaCasera=" + getMedidaCasera() +
            ", valorEnergetico=" + getValorEnergetico() +
            ", caloriasGrasa=" + getCaloriasGrasa() +
            ", grasaTotal=" + getGrasaTotal() +
            ", grasaSaturada=" + getGrasaSaturada() +
            ", grasaInsaturada=" + getGrasaInsaturada() +
            ", grasaTrans=" + getGrasaTrans() +
            ", colesterol=" + getColesterol() +
            ", sodio=" + getSodio() +
            ", carbohidrato=" + getCarbohidrato() +
            ", fibraDietaria=" + getFibraDietaria() +
            ", fibraInsoluble=" + getFibraInsoluble() +
            ", fibraSoluble=" + getFibraSoluble() +
            ", azucares=" + getAzucares() +
            ", proteina=" + getProteina() +
            ", vitaminaA=" + getVitaminaA() +
            ", vitaminaC=" + getVitaminaC() +
            ", calcio=" + getCalcio() +
            ", hierro=" + getHierro() +
            ", gluten='" + isGluten() + "'" +
            ", azucar='" + isAzucar() + "'" +
            ", integral='" + isIntegral() + "'" +
            ", fechaCreacion='" + getFechaCreacion() + "'" +
            ", fechaUltimaModificacion='" + getFechaUltimaModificacion() + "'" +
            ", estadoActivo='" + isEstadoActivo() + "'" +
            ", codigoDeBarras='" + getCodigoDeBarras() + "'" +
            ", imagen='" + getImagen() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", estadoSugerencia='" + getEstadoSugerencia() + "'" +
            "}";
    }
}
