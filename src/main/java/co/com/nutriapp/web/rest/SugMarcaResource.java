package co.com.nutriapp.web.rest;

import co.com.nutriapp.domain.SugMarca;
import co.com.nutriapp.repository.SugMarcaRepository;
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
 * REST controller for managing {@link co.com.nutriapp.domain.SugMarca}.
 */
@RestController
@RequestMapping("/api")
public class SugMarcaResource {

    private final Logger log = LoggerFactory.getLogger(SugMarcaResource.class);

    private static final String ENTITY_NAME = "sugMarca";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SugMarcaRepository sugMarcaRepository;

    public SugMarcaResource(SugMarcaRepository sugMarcaRepository) {
        this.sugMarcaRepository = sugMarcaRepository;
    }

    /**
     * {@code POST  /sug-marcas} : Create a new sugMarca.
     *
     * @param sugMarca the sugMarca to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sugMarca, or with status {@code 400 (Bad Request)} if the sugMarca has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sug-marcas")
    public ResponseEntity<SugMarca> createSugMarca(@RequestBody SugMarca sugMarca) throws URISyntaxException {
        log.debug("REST request to save SugMarca : {}", sugMarca);
        if (sugMarca.getId() != null) {
            throw new BadRequestAlertException("A new sugMarca cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SugMarca result = sugMarcaRepository.save(sugMarca);
        return ResponseEntity.created(new URI("/api/sug-marcas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sug-marcas} : Updates an existing sugMarca.
     *
     * @param sugMarca the sugMarca to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sugMarca,
     * or with status {@code 400 (Bad Request)} if the sugMarca is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sugMarca couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sug-marcas")
    public ResponseEntity<SugMarca> updateSugMarca(@RequestBody SugMarca sugMarca) throws URISyntaxException {
        log.debug("REST request to update SugMarca : {}", sugMarca);
        if (sugMarca.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SugMarca result = sugMarcaRepository.save(sugMarca);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sugMarca.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sug-marcas} : get all the sugMarcas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sugMarcas in body.
     */
    @GetMapping("/sug-marcas")
    public List<SugMarca> getAllSugMarcas() {
        log.debug("REST request to get all SugMarcas");
        return sugMarcaRepository.findAll();
    }

    /**
     * {@code GET  /sug-marcas/:id} : get the "id" sugMarca.
     *
     * @param id the id of the sugMarca to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sugMarca, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sug-marcas/{id}")
    public ResponseEntity<SugMarca> getSugMarca(@PathVariable Long id) {
        log.debug("REST request to get SugMarca : {}", id);
        Optional<SugMarca> sugMarca = sugMarcaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sugMarca);
    }

    /**
     * {@code DELETE  /sug-marcas/:id} : delete the "id" sugMarca.
     *
     * @param id the id of the sugMarca to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sug-marcas/{id}")
    public ResponseEntity<Void> deleteSugMarca(@PathVariable Long id) {
        log.debug("REST request to delete SugMarca : {}", id);
        sugMarcaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
