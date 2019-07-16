package co.com.nutriapp.web.rest;

import co.com.nutriapp.domain.SugUnidadMedida;
import co.com.nutriapp.repository.SugUnidadMedidaRepository;
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
 * REST controller for managing {@link co.com.nutriapp.domain.SugUnidadMedida}.
 */
@RestController
@RequestMapping("/api")
public class SugUnidadMedidaResource {

    private final Logger log = LoggerFactory.getLogger(SugUnidadMedidaResource.class);

    private static final String ENTITY_NAME = "sugUnidadMedida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SugUnidadMedidaRepository sugUnidadMedidaRepository;

    public SugUnidadMedidaResource(SugUnidadMedidaRepository sugUnidadMedidaRepository) {
        this.sugUnidadMedidaRepository = sugUnidadMedidaRepository;
    }

    /**
     * {@code POST  /sug-unidad-medidas} : Create a new sugUnidadMedida.
     *
     * @param sugUnidadMedida the sugUnidadMedida to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sugUnidadMedida, or with status {@code 400 (Bad Request)} if the sugUnidadMedida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sug-unidad-medidas")
    public ResponseEntity<SugUnidadMedida> createSugUnidadMedida(@RequestBody SugUnidadMedida sugUnidadMedida) throws URISyntaxException {
        log.debug("REST request to save SugUnidadMedida : {}", sugUnidadMedida);
        if (sugUnidadMedida.getId() != null) {
            throw new BadRequestAlertException("A new sugUnidadMedida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SugUnidadMedida result = sugUnidadMedidaRepository.save(sugUnidadMedida);
        return ResponseEntity.created(new URI("/api/sug-unidad-medidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sug-unidad-medidas} : Updates an existing sugUnidadMedida.
     *
     * @param sugUnidadMedida the sugUnidadMedida to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sugUnidadMedida,
     * or with status {@code 400 (Bad Request)} if the sugUnidadMedida is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sugUnidadMedida couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sug-unidad-medidas")
    public ResponseEntity<SugUnidadMedida> updateSugUnidadMedida(@RequestBody SugUnidadMedida sugUnidadMedida) throws URISyntaxException {
        log.debug("REST request to update SugUnidadMedida : {}", sugUnidadMedida);
        if (sugUnidadMedida.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SugUnidadMedida result = sugUnidadMedidaRepository.save(sugUnidadMedida);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sugUnidadMedida.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sug-unidad-medidas} : get all the sugUnidadMedidas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sugUnidadMedidas in body.
     */
    @GetMapping("/sug-unidad-medidas")
    public List<SugUnidadMedida> getAllSugUnidadMedidas() {
        log.debug("REST request to get all SugUnidadMedidas");
        return sugUnidadMedidaRepository.findAll();
    }

    /**
     * {@code GET  /sug-unidad-medidas/:id} : get the "id" sugUnidadMedida.
     *
     * @param id the id of the sugUnidadMedida to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sugUnidadMedida, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sug-unidad-medidas/{id}")
    public ResponseEntity<SugUnidadMedida> getSugUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to get SugUnidadMedida : {}", id);
        Optional<SugUnidadMedida> sugUnidadMedida = sugUnidadMedidaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sugUnidadMedida);
    }

    /**
     * {@code DELETE  /sug-unidad-medidas/:id} : delete the "id" sugUnidadMedida.
     *
     * @param id the id of the sugUnidadMedida to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sug-unidad-medidas/{id}")
    public ResponseEntity<Void> deleteSugUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to delete SugUnidadMedida : {}", id);
        sugUnidadMedidaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
