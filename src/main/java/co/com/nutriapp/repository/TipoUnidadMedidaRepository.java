package co.com.nutriapp.repository;

import co.com.nutriapp.domain.TipoUnidadMedida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TipoUnidadMedida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoUnidadMedidaRepository extends JpaRepository<TipoUnidadMedida, Long> {

}
