package co.com.nutriapp.repository;

import co.com.nutriapp.domain.SugerenciaProducto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SugerenciaProducto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SugerenciaProductoRepository extends JpaRepository<SugerenciaProducto, Long> {

}
