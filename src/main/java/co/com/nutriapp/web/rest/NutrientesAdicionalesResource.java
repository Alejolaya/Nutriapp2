package co.com.nutriapp.web.rest;

import co.com.nutriapp.domain.NutrientesAdicionales;
import co.com.nutriapp.repository.NutrientesAdicionalesRepository;
import co.com.nutriapp.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link co.com.nutriapp.domain.NutrientesAdicionales}.
 */
@RestController
@RequestMapping("/api")
public class NutrientesAdicionalesResource {

    private final Logger log = LoggerFactory.getLogger(NutrientesAdicionalesResource.class);

    private static final String ENTITY_NAME = "nutrientesAdicionales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final NutrientesAdicionalesRepository nutrientesAdicionalesRepository;

    public NutrientesAdicionalesResource(NutrientesAdicionalesRepository nutrientesAdicionalesRepository) {
        this.nutrientesAdicionalesRepository = nutrientesAdicionalesRepository;
    }

    /**
     * {@code POST  /nutrientes-adicionales} : Create a new nutrientesAdicionales.
     *
     * @param nutrientesAdicionales the nutrientesAdicionales to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new nutrientesAdicionales, or with status {@code 400 (Bad Request)} if the nutrientesAdicionales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/nutrientes-adicionales")
    public ResponseEntity<NutrientesAdicionales> createNutrientesAdicionales(@RequestBody NutrientesAdicionales nutrientesAdicionales) throws URISyntaxException {
        log.debug("REST request to save NutrientesAdicionales : {}", nutrientesAdicionales);
        if (nutrientesAdicionales.getId() != null) {
            throw new BadRequestAlertException("A new nutrientesAdicionales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        NutrientesAdicionales result = nutrientesAdicionalesRepository.save(nutrientesAdicionales);
        return ResponseEntity.created(new URI("/api/nutrientes-adicionales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /nutrientes-adicionales} : Updates an existing nutrientesAdicionales.
     *
     * @param nutrientesAdicionales the nutrientesAdicionales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated nutrientesAdicionales,
     * or with status {@code 400 (Bad Request)} if the nutrientesAdicionales is not valid,
     * or with status {@code 500 (Internal Server Error)} if the nutrientesAdicionales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/nutrientes-adicionales")
    public ResponseEntity<NutrientesAdicionales> updateNutrientesAdicionales(@RequestBody NutrientesAdicionales nutrientesAdicionales) throws URISyntaxException {
        log.debug("REST request to update NutrientesAdicionales : {}", nutrientesAdicionales);
        if (nutrientesAdicionales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        NutrientesAdicionales result = nutrientesAdicionalesRepository.save(nutrientesAdicionales);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, nutrientesAdicionales.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /nutrientes-adicionales} : get all the nutrientesAdicionales.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of nutrientesAdicionales in body.
     */
    @GetMapping("/nutrientes-adicionales")
    public List<NutrientesAdicionales> getAllNutrientesAdicionales() {
        log.debug("REST request to get all NutrientesAdicionales");
        return nutrientesAdicionalesRepository.findAll();
    }

    /**
     * {@code GET  /nutrientes-adicionales/:id} : get the "id" nutrientesAdicionales.
     *
     * @param id the id of the nutrientesAdicionales to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the nutrientesAdicionales, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/nutrientes-adicionales/{id}")
    public ResponseEntity<NutrientesAdicionales> getNutrientesAdicionales(@PathVariable Long id) {
        log.debug("REST request to get NutrientesAdicionales : {}", id);
        Optional<NutrientesAdicionales> nutrientesAdicionales = nutrientesAdicionalesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(nutrientesAdicionales);
    }

    /**
     * {@code DELETE  /nutrientes-adicionales/:id} : delete the "id" nutrientesAdicionales.
     *
     * @param id the id of the nutrientesAdicionales to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/nutrientes-adicionales/{id}")
    public ResponseEntity<Void> deleteNutrientesAdicionales(@PathVariable Long id) {
        log.debug("REST request to delete NutrientesAdicionales : {}", id);
        nutrientesAdicionalesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
