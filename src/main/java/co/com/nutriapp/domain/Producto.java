package co.com.nutriapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

/**
 * A Producto.
 */
@Entity
@Table(name = "producto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Producto implements Serializable {

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

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private UnidadMedida unidadPorcion;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private UnidadMedida unidadMedidaCasera;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Marca marca;

    @ManyToOne
    @JsonIgnoreProperties("productos")
    private Categoria categoria;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "producto_tags",
               joinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "tags_id", referencedColumnName = "id"))
    private Set<Tags> tags = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "producto_nutrientes_adicionales",
               joinColumns = @JoinColumn(name = "producto_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "nutrientes_adicionales_id", referencedColumnName = "id"))
    private Set<NutrientesAdicionales> nutrientesAdicionales = new HashSet<>();

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

    public Producto nombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
        return this;
    }

    public void setNombreAlimento(String nombreAlimento) {
        this.nombreAlimento = nombreAlimento;
    }

    public Float getTamanoPorcion() {
        return tamanoPorcion;
    }

    public Producto tamanoPorcion(Float tamanoPorcion) {
        this.tamanoPorcion = tamanoPorcion;
        return this;
    }

    public void setTamanoPorcion(Float tamanoPorcion) {
        this.tamanoPorcion = tamanoPorcion;
    }

    public Float getMedidaCasera() {
        return medidaCasera;
    }

    public Producto medidaCasera(Float medidaCasera) {
        this.medidaCasera = medidaCasera;
        return this;
    }

    public void setMedidaCasera(Float medidaCasera) {
        this.medidaCasera = medidaCasera;
    }

    public Float getValorEnergetico() {
        return valorEnergetico;
    }

    public Producto valorEnergetico(Float valorEnergetico) {
        this.valorEnergetico = valorEnergetico;
        return this;
    }

    public void setValorEnergetico(Float valorEnergetico) {
        this.valorEnergetico = valorEnergetico;
    }

    public Float getCaloriasGrasa() {
        return caloriasGrasa;
    }

    public Producto caloriasGrasa(Float caloriasGrasa) {
        this.caloriasGrasa = caloriasGrasa;
        return this;
    }

    public void setCaloriasGrasa(Float caloriasGrasa) {
        this.caloriasGrasa = caloriasGrasa;
    }

    public Float getGrasaTotal() {
        return grasaTotal;
    }

    public Producto grasaTotal(Float grasaTotal) {
        this.grasaTotal = grasaTotal;
        return this;
    }

    public void setGrasaTotal(Float grasaTotal) {
        this.grasaTotal = grasaTotal;
    }

    public Float getGrasaSaturada() {
        return grasaSaturada;
    }

    public Producto grasaSaturada(Float grasaSaturada) {
        this.grasaSaturada = grasaSaturada;
        return this;
    }

    public void setGrasaSaturada(Float grasaSaturada) {
        this.grasaSaturada = grasaSaturada;
    }

    public Float getGrasaInsaturada() {
        return grasaInsaturada;
    }

    public Producto grasaInsaturada(Float grasaInsaturada) {
        this.grasaInsaturada = grasaInsaturada;
        return this;
    }

    public void setGrasaInsaturada(Float grasaInsaturada) {
        this.grasaInsaturada = grasaInsaturada;
    }

    public Float getGrasaTrans() {
        return grasaTrans;
    }

    public Producto grasaTrans(Float grasaTrans) {
        this.grasaTrans = grasaTrans;
        return this;
    }

    public void setGrasaTrans(Float grasaTrans) {
        this.grasaTrans = grasaTrans;
    }

    public Float getColesterol() {
        return colesterol;
    }

    public Producto colesterol(Float colesterol) {
        this.colesterol = colesterol;
        return this;
    }

    public void setColesterol(Float colesterol) {
        this.colesterol = colesterol;
    }

    public Float getSodio() {
        return sodio;
    }

    public Producto sodio(Float sodio) {
        this.sodio = sodio;
        return this;
    }

    public void setSodio(Float sodio) {
        this.sodio = sodio;
    }

    public Float getCarbohidrato() {
        return carbohidrato;
    }

    public Producto carbohidrato(Float carbohidrato) {
        this.carbohidrato = carbohidrato;
        return this;
    }

    public void setCarbohidrato(Float carbohidrato) {
        this.carbohidrato = carbohidrato;
    }

    public Float getFibraDietaria() {
        return fibraDietaria;
    }

    public Producto fibraDietaria(Float fibraDietaria) {
        this.fibraDietaria = fibraDietaria;
        return this;
    }

    public void setFibraDietaria(Float fibraDietaria) {
        this.fibraDietaria = fibraDietaria;
    }

    public Float getFibraInsoluble() {
        return fibraInsoluble;
    }

    public Producto fibraInsoluble(Float fibraInsoluble) {
        this.fibraInsoluble = fibraInsoluble;
        return this;
    }

    public void setFibraInsoluble(Float fibraInsoluble) {
        this.fibraInsoluble = fibraInsoluble;
    }

    public Float getFibraSoluble() {
        return fibraSoluble;
    }

    public Producto fibraSoluble(Float fibraSoluble) {
        this.fibraSoluble = fibraSoluble;
        return this;
    }

    public void setFibraSoluble(Float fibraSoluble) {
        this.fibraSoluble = fibraSoluble;
    }

    public Float getAzucares() {
        return azucares;
    }

    public Producto azucares(Float azucares) {
        this.azucares = azucares;
        return this;
    }

    public void setAzucares(Float azucares) {
        this.azucares = azucares;
    }

    public Float getProteina() {
        return proteina;
    }

    public Producto proteina(Float proteina) {
        this.proteina = proteina;
        return this;
    }

    public void setProteina(Float proteina) {
        this.proteina = proteina;
    }

    public Float getVitaminaA() {
        return vitaminaA;
    }

    public Producto vitaminaA(Float vitaminaA) {
        this.vitaminaA = vitaminaA;
        return this;
    }

    public void setVitaminaA(Float vitaminaA) {
        this.vitaminaA = vitaminaA;
    }

    public Float getVitaminaC() {
        return vitaminaC;
    }

    public Producto vitaminaC(Float vitaminaC) {
        this.vitaminaC = vitaminaC;
        return this;
    }

    public void setVitaminaC(Float vitaminaC) {
        this.vitaminaC = vitaminaC;
    }

    public Float getCalcio() {
        return calcio;
    }

    public Producto calcio(Float calcio) {
        this.calcio = calcio;
        return this;
    }

    public void setCalcio(Float calcio) {
        this.calcio = calcio;
    }

    public Float getHierro() {
        return hierro;
    }

    public Producto hierro(Float hierro) {
        this.hierro = hierro;
        return this;
    }

    public void setHierro(Float hierro) {
        this.hierro = hierro;
    }

    public Boolean isGluten() {
        return gluten;
    }

    public Producto gluten(Boolean gluten) {
        this.gluten = gluten;
        return this;
    }

    public void setGluten(Boolean gluten) {
        this.gluten = gluten;
    }

    public Boolean isAzucar() {
        return azucar;
    }

    public Producto azucar(Boolean azucar) {
        this.azucar = azucar;
        return this;
    }

    public void setAzucar(Boolean azucar) {
        this.azucar = azucar;
    }

    public Boolean isIntegral() {
        return integral;
    }

    public Producto integral(Boolean integral) {
        this.integral = integral;
        return this;
    }

    public void setIntegral(Boolean integral) {
        this.integral = integral;
    }

    public Instant getFechaCreacion() {
        return fechaCreacion;
    }

    public Producto fechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
        return this;
    }

    public void setFechaCreacion(Instant fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public Instant getFechaUltimaModificacion() {
        return fechaUltimaModificacion;
    }

    public Producto fechaUltimaModificacion(Instant fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
        return this;
    }

    public void setFechaUltimaModificacion(Instant fechaUltimaModificacion) {
        this.fechaUltimaModificacion = fechaUltimaModificacion;
    }

    public Boolean isEstadoActivo() {
        return estadoActivo;
    }

    public Producto estadoActivo(Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
        return this;
    }

    public void setEstadoActivo(Boolean estadoActivo) {
        this.estadoActivo = estadoActivo;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public Producto codigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
        return this;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public UnidadMedida getUnidadPorcion() {
        return unidadPorcion;
    }

    public Producto unidadPorcion(UnidadMedida unidadMedida) {
        this.unidadPorcion = unidadMedida;
        return this;
    }

    public void setUnidadPorcion(UnidadMedida unidadMedida) {
        this.unidadPorcion = unidadMedida;
    }

    public UnidadMedida getUnidadMedidaCasera() {
        return unidadMedidaCasera;
    }

    public Producto unidadMedidaCasera(UnidadMedida unidadMedida) {
        this.unidadMedidaCasera = unidadMedida;
        return this;
    }

    public void setUnidadMedidaCasera(UnidadMedida unidadMedida) {
        this.unidadMedidaCasera = unidadMedida;
    }

    public Marca getMarca() {
        return marca;
    }

    public Producto marca(Marca marca) {
        this.marca = marca;
        return this;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public Producto categoria(Categoria categoria) {
        this.categoria = categoria;
        return this;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Set<Tags> getTags() {
        return tags;
    }

    public Producto tags(Set<Tags> tags) {
        this.tags = tags;
        return this;
    }

    public Producto addTags(Tags tags) {
        this.tags.add(tags);
        tags.getProductos().add(this);
        return this;
    }

    public Producto removeTags(Tags tags) {
        this.tags.remove(tags);
        tags.getProductos().remove(this);
        return this;
    }

    public void setTags(Set<Tags> tags) {
        this.tags = tags;
    }

    public Set<NutrientesAdicionales> getNutrientesAdicionales() {
        return nutrientesAdicionales;
    }

    public Producto nutrientesAdicionales(Set<NutrientesAdicionales> nutrientesAdicionales) {
        this.nutrientesAdicionales = nutrientesAdicionales;
        return this;
    }

    public Producto addNutrientesAdicionales(NutrientesAdicionales nutrientesAdicionales) {
        this.nutrientesAdicionales.add(nutrientesAdicionales);
        nutrientesAdicionales.getProductos().add(this);
        return this;
    }

    public Producto removeNutrientesAdicionales(NutrientesAdicionales nutrientesAdicionales) {
        this.nutrientesAdicionales.remove(nutrientesAdicionales);
        nutrientesAdicionales.getProductos().remove(this);
        return this;
    }

    public void setNutrientesAdicionales(Set<NutrientesAdicionales> nutrientesAdicionales) {
        this.nutrientesAdicionales = nutrientesAdicionales;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Producto)) {
            return false;
        }
        return id != null && id.equals(((Producto) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Producto{" +
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
            "}";
    }
}
