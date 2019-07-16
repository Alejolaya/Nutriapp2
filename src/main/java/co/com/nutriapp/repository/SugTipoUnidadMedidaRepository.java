package co.com.nutriapp.repository;

import co.com.nutriapp.domain.SugTipoUnidadMedida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SugTipoUnidadMedida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SugTipoUnidadMedidaRepository extends JpaRepository<SugTipoUnidadMedida, Long> {

}
