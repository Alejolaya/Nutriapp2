package co.com.nutriapp.repository;

import co.com.nutriapp.domain.SugUnidadMedida;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SugUnidadMedida entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SugUnidadMedidaRepository extends JpaRepository<SugUnidadMedida, Long> {

}
