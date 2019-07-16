package co.com.nutriapp.web.rest;

import co.com.nutriapp.domain.SugerenciaProducto;
import co.com.nutriapp.repository.SugerenciaProductoRepository;
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
 * REST controller for managing {@link co.com.nutriapp.domain.SugerenciaProducto}.
 */
@RestController
@RequestMapping("/api")
public class SugerenciaProductoResource {

    private final Logger log = LoggerFactory.getLogger(SugerenciaProductoResource.class);

    private static final String ENTITY_NAME = "sugerenciaProducto";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SugerenciaProductoRepository sugerenciaProductoRepository;

    public SugerenciaProductoResource(SugerenciaProductoRepository sugerenciaProductoRepository) {
        this.sugerenciaProductoRepository = sugerenciaProductoRepository;
    }

    /**
     * {@code POST  /sugerencia-productos} : Create a new sugerenciaProducto.
     *
     * @param sugerenciaProducto the sugerenciaProducto to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sugerenciaProducto, or with status {@code 400 (Bad Request)} if the sugerenciaProducto has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sugerencia-productos")
    public ResponseEntity<SugerenciaProducto> createSugerenciaProducto(@RequestBody SugerenciaProducto sugerenciaProducto) throws URISyntaxException {
        log.debug("REST request to save SugerenciaProducto : {}", sugerenciaProducto);
        if (sugerenciaProducto.getId() != null) {
            throw new BadRequestAlertException("A new sugerenciaProducto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SugerenciaProducto result = sugerenciaProductoRepository.save(sugerenciaProducto);
        return ResponseEntity.created(new URI("/api/sugerencia-productos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sugerencia-productos} : Updates an existing sugerenciaProducto.
     *
     * @param sugerenciaProducto the sugerenciaProducto to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sugerenciaProducto,
     * or with status {@code 400 (Bad Request)} if the sugerenciaProducto is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sugerenciaProducto couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sugerencia-productos")
    public ResponseEntity<SugerenciaProducto> updateSugerenciaProducto(@RequestBody SugerenciaProducto sugerenciaProducto) throws URISyntaxException {
        log.debug("REST request to update SugerenciaProducto : {}", sugerenciaProducto);
        if (sugerenciaProducto.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SugerenciaProducto result = sugerenciaProductoRepository.save(sugerenciaProducto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sugerenciaProducto.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sugerencia-productos} : get all the sugerenciaProductos.
     *
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sugerenciaProductos in body.
     */
    @GetMapping("/sugerencia-productos")
    public List<SugerenciaProducto> getAllSugerenciaProductos(@RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get all SugerenciaProductos");
        return sugerenciaProductoRepository.findAllWithEagerRelationships();
    }

    /**
     * {@code GET  /sugerencia-productos/:id} : get the "id" sugerenciaProducto.
     *
     * @param id the id of the sugerenciaProducto to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sugerenciaProducto, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sugerencia-productos/{id}")
    public ResponseEntity<SugerenciaProducto> getSugerenciaProducto(@PathVariable Long id) {
        log.debug("REST request to get SugerenciaProducto : {}", id);
        Optional<SugerenciaProducto> sugerenciaProducto = sugerenciaProductoRepository.findOneWithEagerRelationships(id);
        return ResponseUtil.wrapOrNotFound(sugerenciaProducto);
    }

    /**
     * {@code DELETE  /sugerencia-productos/:id} : delete the "id" sugerenciaProducto.
     *
     * @param id the id of the sugerenciaProducto to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sugerencia-productos/{id}")
    public ResponseEntity<Void> deleteSugerenciaProducto(@PathVariable Long id) {
        log.debug("REST request to delete SugerenciaProducto : {}", id);
        sugerenciaProductoRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
