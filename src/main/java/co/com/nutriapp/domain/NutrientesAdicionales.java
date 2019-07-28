package co.com.nutriapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A NutrientesAdicionales.
 */
@Entity
@Table(name = "nutrientes_adicionales")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class NutrientesAdicionales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "valor")
    private String valor;

    @ManyToOne
    @JsonIgnoreProperties("nutrientesAdicionales")
    private UnidadMedida unidadPorcion;

    @ManyToMany(mappedBy = "nutrientesAdicionales")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JsonIgnore
    private Set<Producto> productos = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public NutrientesAdicionales descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getValor() {
        return valor;
    }

    public NutrientesAdicionales valor(String valor) {
        this.valor = valor;
        return this;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public UnidadMedida getUnidadPorcion() {
        return unidadPorcion;
    }

    public NutrientesAdicionales unidadPorcion(UnidadMedida unidadMedida) {
        this.unidadPorcion = unidadMedida;
        return this;
    }

    public void setUnidadPorcion(UnidadMedida unidadMedida) {
        this.unidadPorcion = unidadMedida;
    }

    public Set<Producto> getProductos() {
        return productos;
    }

    public NutrientesAdicionales productos(Set<Producto> productos) {
        this.productos = productos;
        return this;
    }

    public NutrientesAdicionales addProducto(Producto producto) {
        this.productos.add(producto);
        producto.getNutrientesAdicionales().add(this);
        return this;
    }

    public NutrientesAdicionales removeProducto(Producto producto) {
        this.productos.remove(producto);
        producto.getNutrientesAdicionales().remove(this);
        return this;
    }

    public void setProductos(Set<Producto> productos) {
        this.productos = productos;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof NutrientesAdicionales)) {
            return false;
        }
        return id != null && id.equals(((NutrientesAdicionales) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "NutrientesAdicionales{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", valor='" + getValor() + "'" +
            "}";
    }
}
