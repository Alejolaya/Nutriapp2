package co.com.nutriapp.repository;

import co.com.nutriapp.domain.NutrientesAdicionales;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the NutrientesAdicionales entity.
 */
@SuppressWarnings("unused")
@Repository
public interface NutrientesAdicionalesRepository extends JpaRepository<NutrientesAdicionales, Long> {

}
