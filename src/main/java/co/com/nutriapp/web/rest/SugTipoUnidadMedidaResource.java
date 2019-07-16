package co.com.nutriapp.web.rest;

import co.com.nutriapp.domain.SugTipoUnidadMedida;
import co.com.nutriapp.repository.SugTipoUnidadMedidaRepository;
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
 * REST controller for managing {@link co.com.nutriapp.domain.SugTipoUnidadMedida}.
 */
@RestController
@RequestMapping("/api")
public class SugTipoUnidadMedidaResource {

    private final Logger log = LoggerFactory.getLogger(SugTipoUnidadMedidaResource.class);

    private static final String ENTITY_NAME = "sugTipoUnidadMedida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SugTipoUnidadMedidaRepository sugTipoUnidadMedidaRepository;

    public SugTipoUnidadMedidaResource(SugTipoUnidadMedidaRepository sugTipoUnidadMedidaRepository) {
        this.sugTipoUnidadMedidaRepository = sugTipoUnidadMedidaRepository;
    }

    /**
     * {@code POST  /sug-tipo-unidad-medidas} : Create a new sugTipoUnidadMedida.
     *
     * @param sugTipoUnidadMedida the sugTipoUnidadMedida to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sugTipoUnidadMedida, or with status {@code 400 (Bad Request)} if the sugTipoUnidadMedida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sug-tipo-unidad-medidas")
    public ResponseEntity<SugTipoUnidadMedida> createSugTipoUnidadMedida(@RequestBody SugTipoUnidadMedida sugTipoUnidadMedida) throws URISyntaxException {
        log.debug("REST request to save SugTipoUnidadMedida : {}", sugTipoUnidadMedida);
        if (sugTipoUnidadMedida.getId() != null) {
            throw new BadRequestAlertException("A new sugTipoUnidadMedida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SugTipoUnidadMedida result = sugTipoUnidadMedidaRepository.save(sugTipoUnidadMedida);
        return ResponseEntity.created(new URI("/api/sug-tipo-unidad-medidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sug-tipo-unidad-medidas} : Updates an existing sugTipoUnidadMedida.
     *
     * @param sugTipoUnidadMedida the sugTipoUnidadMedida to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sugTipoUnidadMedida,
     * or with status {@code 400 (Bad Request)} if the sugTipoUnidadMedida is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sugTipoUnidadMedida couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sug-tipo-unidad-medidas")
    public ResponseEntity<SugTipoUnidadMedida> updateSugTipoUnidadMedida(@RequestBody SugTipoUnidadMedida sugTipoUnidadMedida) throws URISyntaxException {
        log.debug("REST request to update SugTipoUnidadMedida : {}", sugTipoUnidadMedida);
        if (sugTipoUnidadMedida.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SugTipoUnidadMedida result = sugTipoUnidadMedidaRepository.save(sugTipoUnidadMedida);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sugTipoUnidadMedida.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sug-tipo-unidad-medidas} : get all the sugTipoUnidadMedidas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sugTipoUnidadMedidas in body.
     */
    @GetMapping("/sug-tipo-unidad-medidas")
    public List<SugTipoUnidadMedida> getAllSugTipoUnidadMedidas() {
        log.debug("REST request to get all SugTipoUnidadMedidas");
        return sugTipoUnidadMedidaRepository.findAll();
    }

    /**
     * {@code GET  /sug-tipo-unidad-medidas/:id} : get the "id" sugTipoUnidadMedida.
     *
     * @param id the id of the sugTipoUnidadMedida to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sugTipoUnidadMedida, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sug-tipo-unidad-medidas/{id}")
    public ResponseEntity<SugTipoUnidadMedida> getSugTipoUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to get SugTipoUnidadMedida : {}", id);
        Optional<SugTipoUnidadMedida> sugTipoUnidadMedida = sugTipoUnidadMedidaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sugTipoUnidadMedida);
    }

    /**
     * {@code DELETE  /sug-tipo-unidad-medidas/:id} : delete the "id" sugTipoUnidadMedida.
     *
     * @param id the id of the sugTipoUnidadMedida to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sug-tipo-unidad-medidas/{id}")
    public ResponseEntity<Void> deleteSugTipoUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to delete SugTipoUnidadMedida : {}", id);
        sugTipoUnidadMedidaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
