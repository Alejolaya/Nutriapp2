package co.com.nutriapp.repository;

import co.com.nutriapp.domain.SugMarca;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SugMarca entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SugMarcaRepository extends JpaRepository<SugMarca, Long> {

}
