package co.com.nutriapp.repository;

import co.com.nutriapp.domain.SugerenciaProducto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the SugerenciaProducto entity.
 */
@Repository
public interface SugerenciaProductoRepository extends JpaRepository<SugerenciaProducto, Long> {

    @Query(value = "select distinct sugerenciaProducto from SugerenciaProducto sugerenciaProducto left join fetch sugerenciaProducto.tags left join fetch sugerenciaProducto.nutrientesAdicionales",
        countQuery = "select count(distinct sugerenciaProducto) from SugerenciaProducto sugerenciaProducto")
    Page<SugerenciaProducto> findAllWithEagerRelationships(Pageable pageable);

    @Query("select distinct sugerenciaProducto from SugerenciaProducto sugerenciaProducto left join fetch sugerenciaProducto.tags left join fetch sugerenciaProducto.nutrientesAdicionales")
    List<SugerenciaProducto> findAllWithEagerRelationships();

    @Query("select sugerenciaProducto from SugerenciaProducto sugerenciaProducto left join fetch sugerenciaProducto.tags left join fetch sugerenciaProducto.nutrientesAdicionales where sugerenciaProducto.id =:id")
    Optional<SugerenciaProducto> findOneWithEagerRelationships(@Param("id") Long id);

}
