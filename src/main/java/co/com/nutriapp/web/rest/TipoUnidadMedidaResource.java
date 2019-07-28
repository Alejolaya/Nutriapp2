package co.com.nutriapp.web.rest;

import co.com.nutriapp.domain.TipoUnidadMedida;
import co.com.nutriapp.repository.TipoUnidadMedidaRepository;
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
 * REST controller for managing {@link co.com.nutriapp.domain.TipoUnidadMedida}.
 */
@RestController
@RequestMapping("/api")
public class TipoUnidadMedidaResource {

    private final Logger log = LoggerFactory.getLogger(TipoUnidadMedidaResource.class);

    private static final String ENTITY_NAME = "tipoUnidadMedida";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TipoUnidadMedidaRepository tipoUnidadMedidaRepository;

    public TipoUnidadMedidaResource(TipoUnidadMedidaRepository tipoUnidadMedidaRepository) {
        this.tipoUnidadMedidaRepository = tipoUnidadMedidaRepository;
    }

    /**
     * {@code POST  /tipo-unidad-medidas} : Create a new tipoUnidadMedida.
     *
     * @param tipoUnidadMedida the tipoUnidadMedida to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new tipoUnidadMedida, or with status {@code 400 (Bad Request)} if the tipoUnidadMedida has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/tipo-unidad-medidas")
    public ResponseEntity<TipoUnidadMedida> createTipoUnidadMedida(@RequestBody TipoUnidadMedida tipoUnidadMedida) throws URISyntaxException {
        log.debug("REST request to save TipoUnidadMedida : {}", tipoUnidadMedida);
        if (tipoUnidadMedida.getId() != null) {
            throw new BadRequestAlertException("A new tipoUnidadMedida cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoUnidadMedida result = tipoUnidadMedidaRepository.save(tipoUnidadMedida);
        return ResponseEntity.created(new URI("/api/tipo-unidad-medidas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /tipo-unidad-medidas} : Updates an existing tipoUnidadMedida.
     *
     * @param tipoUnidadMedida the tipoUnidadMedida to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated tipoUnidadMedida,
     * or with status {@code 400 (Bad Request)} if the tipoUnidadMedida is not valid,
     * or with status {@code 500 (Internal Server Error)} if the tipoUnidadMedida couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/tipo-unidad-medidas")
    public ResponseEntity<TipoUnidadMedida> updateTipoUnidadMedida(@RequestBody TipoUnidadMedida tipoUnidadMedida) throws URISyntaxException {
        log.debug("REST request to update TipoUnidadMedida : {}", tipoUnidadMedida);
        if (tipoUnidadMedida.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TipoUnidadMedida result = tipoUnidadMedidaRepository.save(tipoUnidadMedida);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, tipoUnidadMedida.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /tipo-unidad-medidas} : get all the tipoUnidadMedidas.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of tipoUnidadMedidas in body.
     */
    @GetMapping("/tipo-unidad-medidas")
    public List<TipoUnidadMedida> getAllTipoUnidadMedidas() {
        log.debug("REST request to get all TipoUnidadMedidas");
        return tipoUnidadMedidaRepository.findAll();
    }

    /**
     * {@code GET  /tipo-unidad-medidas/:id} : get the "id" tipoUnidadMedida.
     *
     * @param id the id of the tipoUnidadMedida to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the tipoUnidadMedida, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/tipo-unidad-medidas/{id}")
    public ResponseEntity<TipoUnidadMedida> getTipoUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to get TipoUnidadMedida : {}", id);
        Optional<TipoUnidadMedida> tipoUnidadMedida = tipoUnidadMedidaRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(tipoUnidadMedida);
    }

    /**
     * {@code DELETE  /tipo-unidad-medidas/:id} : delete the "id" tipoUnidadMedida.
     *
     * @param id the id of the tipoUnidadMedida to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/tipo-unidad-medidas/{id}")
    public ResponseEntity<Void> deleteTipoUnidadMedida(@PathVariable Long id) {
        log.debug("REST request to delete TipoUnidadMedida : {}", id);
        tipoUnidadMedidaRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
