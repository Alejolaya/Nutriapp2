package co.com.nutriapp.repository;

import co.com.nutriapp.domain.SugCategoria;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SugCategoria entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SugCategoriaRepository extends JpaRepository<SugCategoria, Long> {

}
