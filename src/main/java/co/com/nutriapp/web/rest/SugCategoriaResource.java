package co.com.nutriapp.web.rest;

import co.com.nutriapp.domain.SugCategoria;
import co.com.nutriapp.repository.SugCategoriaRepository;
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
 * REST controller for managing {@link co.com.nutriapp.domain.SugCategoria}.
 */
@RestController
@RequestMapping("/api")
public class SugCategoriaResource {

    private final Logger log = LoggerFactory.getLogger(SugCategoriaResource.class);

    private static final String ENTITY_NAME = "sugCategoria";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SugCategoriaRepository sugCategoriaRepository;

    public SugCategoriaResource(SugCategoriaRepository sugCategoriaRepository) {
        this.sugCategoriaRepository = sugCategoriaRepository;
    }

    /**
     * {@code POST  /sug-categorias} : Create a new sugCategoria.
     *
     * @param sugCategoria the sugCategoria to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sugCategoria, or with status {@code 400 (Bad Request)} if the sugCategoria has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sug-categorias")
    public ResponseEntity<SugCategoria> createSugCategoria(@RequestBody SugCategoria sugCategoria) throws URISyntaxException {
        log.debug("REST request to save SugCategoria : {}", sugCategoria);
        if (sugCategoria.getId() != null) {
            throw new BadRequestAlertException("A new sugCategoria cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SugCategoria result = sugCategoriaRepository.save(sugCategoria);
        return ResponseEntity.created(new URI("/api/sug-categorias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sug-categorias} : Updates an existing sugCategoria.
     *
     * @param sugCategoria the sugCategoria to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sugCategoria,
     * or with status {@code 400 (Bad Request)} if the sugCategoria is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sugCategoria couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sug-categorias")
    public ResponseEntity<SugCategoria> updateSugCategoria(@RequestBody SugCategoria sugCategoria) throws URISyntaxException {
        log.debug("REST request to update SugCategoria : {}", sugCategoria);
        if (sugCategoria.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SugCategoria result = sugCategoriaRepository.save(sugCategoria);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sugCategoria.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sug-categorias} : get all the sugCategorias.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sugCategorias in body.
     */
    @GetMapping("/sug-categorias")
    public List<SugCategoria> getAllSugCategorias() {
        log.debug("REST request to get all SugCategorias");
        return sugCategoriaRepository.findAll();
    }

    /**
     * {@code GET  /sug-categorias/:id} : get the "id" sugCategoria.
     *
     * @param id the id of the sugCategoria to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sugCategoria, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sug-categorias/{id}")
    public ResponseEntity<SugCategoria> getSugCategoria(@PathVariable Long id) {
        log.debug("REST request to get SugCategoria : {}", id);
        Optional<SugCategoria> sugCategoria = sugCategoriaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sugCategoria);
    }

    /**
     * {@code DELETE  /sug-categorias/:id} : delete the "id" sugCategoria.
     *
     * @param id the id of the sugCategoria to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sug-categorias/{id}")
    public ResponseEntity<Void> deleteSugCategoria(@PathVariable Long id) {
        log.debug("REST request to delete SugCategoria : {}", id);
        sugCategoriaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
