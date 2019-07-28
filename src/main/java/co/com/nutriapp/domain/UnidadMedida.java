package co.com.nutriapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A UnidadMedida.
 */
@Entity
@Table(name = "unidad_medida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class UnidadMedida implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "abreviacion")
    private String abreviacion;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "valor_conversion")
    private Float valorConversion;

    @ManyToOne
    @JsonIgnoreProperties("unidadMedidas")
    private TipoUnidadMedida unidadBase;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAbreviacion() {
        return abreviacion;
    }

    public UnidadMedida abreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
        return this;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public UnidadMedida descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getValorConversion() {
        return valorConversion;
    }

    public UnidadMedida valorConversion(Float valorConversion) {
        this.valorConversion = valorConversion;
        return this;
    }

    public void setValorConversion(Float valorConversion) {
        this.valorConversion = valorConversion;
    }

    public TipoUnidadMedida getUnidadBase() {
        return unidadBase;
    }

    public UnidadMedida unidadBase(TipoUnidadMedida tipoUnidadMedida) {
        this.unidadBase = tipoUnidadMedida;
        return this;
    }

    public void setUnidadBase(TipoUnidadMedida tipoUnidadMedida) {
        this.unidadBase = tipoUnidadMedida;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UnidadMedida)) {
            return false;
        }
        return id != null && id.equals(((UnidadMedida) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "UnidadMedida{" +
            "id=" + getId() +
            ", abreviacion='" + getAbreviacion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", valorConversion=" + getValorConversion() +
            "}";
    }
}
