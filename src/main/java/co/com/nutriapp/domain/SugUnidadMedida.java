package co.com.nutriapp.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A SugUnidadMedida.
 */
@Entity
@Table(name = "sug_unidad_medida")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class SugUnidadMedida implements Serializable {

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
    @JsonIgnoreProperties("sugUnidadMedidas")
    private SugTipoUnidadMedida unidadBase;

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

    public SugUnidadMedida abreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
        return this;
    }

    public void setAbreviacion(String abreviacion) {
        this.abreviacion = abreviacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public SugUnidadMedida descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getValorConversion() {
        return valorConversion;
    }

    public SugUnidadMedida valorConversion(Float valorConversion) {
        this.valorConversion = valorConversion;
        return this;
    }

    public void setValorConversion(Float valorConversion) {
        this.valorConversion = valorConversion;
    }

    public SugTipoUnidadMedida getUnidadBase() {
        return unidadBase;
    }

    public SugUnidadMedida unidadBase(SugTipoUnidadMedida sugTipoUnidadMedida) {
        this.unidadBase = sugTipoUnidadMedida;
        return this;
    }

    public void setUnidadBase(SugTipoUnidadMedida sugTipoUnidadMedida) {
        this.unidadBase = sugTipoUnidadMedida;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SugUnidadMedida)) {
            return false;
        }
        return id != null && id.equals(((SugUnidadMedida) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SugUnidadMedida{" +
            "id=" + getId() +
            ", abreviacion='" + getAbreviacion() + "'" +
            ", descripcion='" + getDescripcion() + "'" +
            ", valorConversion=" + getValorConversion() +
            "}";
    }
}
