package co.com.nutriapp.web.rest;

import co.com.nutriapp.domain.SugNutrientesAdicionales;
import co.com.nutriapp.repository.SugNutrientesAdicionalesRepository;
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
 * REST controller for managing {@link co.com.nutriapp.domain.SugNutrientesAdicionales}.
 */
@RestController
@RequestMapping("/api")
public class SugNutrientesAdicionalesResource {

    private final Logger log = LoggerFactory.getLogger(SugNutrientesAdicionalesResource.class);

    private static final String ENTITY_NAME = "sugNutrientesAdicionales";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SugNutrientesAdicionalesRepository sugNutrientesAdicionalesRepository;

    public SugNutrientesAdicionalesResource(SugNutrientesAdicionalesRepository sugNutrientesAdicionalesRepository) {
        this.sugNutrientesAdicionalesRepository = sugNutrientesAdicionalesRepository;
    }

    /**
     * {@code POST  /sug-nutrientes-adicionales} : Create a new sugNutrientesAdicionales.
     *
     * @param sugNutrientesAdicionales the sugNutrientesAdicionales to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sugNutrientesAdicionales, or with status {@code 400 (Bad Request)} if the sugNutrientesAdicionales has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sug-nutrientes-adicionales")
    public ResponseEntity<SugNutrientesAdicionales> createSugNutrientesAdicionales(@RequestBody SugNutrientesAdicionales sugNutrientesAdicionales) throws URISyntaxException {
        log.debug("REST request to save SugNutrientesAdicionales : {}", sugNutrientesAdicionales);
        if (sugNutrientesAdicionales.getId() != null) {
            throw new BadRequestAlertException("A new sugNutrientesAdicionales cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SugNutrientesAdicionales result = sugNutrientesAdicionalesRepository.save(sugNutrientesAdicionales);
        return ResponseEntity.created(new URI("/api/sug-nutrientes-adicionales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sug-nutrientes-adicionales} : Updates an existing sugNutrientesAdicionales.
     *
     * @param sugNutrientesAdicionales the sugNutrientesAdicionales to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sugNutrientesAdicionales,
     * or with status {@code 400 (Bad Request)} if the sugNutrientesAdicionales is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sugNutrientesAdicionales couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sug-nutrientes-adicionales")
    public ResponseEntity<SugNutrientesAdicionales> updateSugNutrientesAdicionales(@RequestBody SugNutrientesAdicionales sugNutrientesAdicionales) throws URISyntaxException {
        log.debug("REST request to update SugNutrientesAdicionales : {}", sugNutrientesAdicionales);
        if (sugNutrientesAdicionales.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SugNutrientesAdicionales result = sugNutrientesAdicionalesRepository.save(sugNutrientesAdicionales);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sugNutrientesAdicionales.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sug-nutrientes-adicionales} : get all the sugNutrientesAdicionales.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sugNutrientesAdicionales in body.
     */
    @GetMapping("/sug-nutrientes-adicionales")
    public List<SugNutrientesAdicionales> getAllSugNutrientesAdicionales() {
        log.debug("REST request to get all SugNutrientesAdicionales");
        return sugNutrientesAdicionalesRepository.findAll();
    }

    /**
     * {@code GET  /sug-nutrientes-adicionales/:id} : get the "id" sugNutrientesAdicionales.
     *
     * @param id the id of the sugNutrientesAdicionales to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sugNutrientesAdicionales, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sug-nutrientes-adicionales/{id}")
    public ResponseEntity<SugNutrientesAdicionales> getSugNutrientesAdicionales(@PathVariable Long id) {
        log.debug("REST request to get SugNutrientesAdicionales : {}", id);
        Optional<SugNutrientesAdicionales> sugNutrientesAdicionales = sugNutrientesAdicionalesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sugNutrientesAdicionales);
    }

    /**
     * {@code DELETE  /sug-nutrientes-adicionales/:id} : delete the "id" sugNutrientesAdicionales.
     *
     * @param id the id of the sugNutrientesAdicionales to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sug-nutrientes-adicionales/{id}")
    public ResponseEntity<Void> deleteSugNutrientesAdicionales(@PathVariable Long id) {
        log.debug("REST request to delete SugNutrientesAdicionales : {}", id);
        sugNutrientesAdicionalesRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
