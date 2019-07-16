package co.com.nutriapp.repository;

import co.com.nutriapp.domain.SugNutrientesAdicionales;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SugNutrientesAdicionales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SugNutrientesAdicionalesRepository extends JpaRepository<SugNutrientesAdicionales, Long> {

}
