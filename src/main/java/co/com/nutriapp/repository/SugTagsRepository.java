package co.com.nutriapp.repository;

import co.com.nutriapp.domain.SugTags;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the SugTags entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SugTagsRepository extends JpaRepository<SugTags, Long> {

}
