package co.com.nutriapp.web.rest;

import co.com.nutriapp.domain.SugTags;
import co.com.nutriapp.repository.SugTagsRepository;
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
 * REST controller for managing {@link co.com.nutriapp.domain.SugTags}.
 */
@RestController
@RequestMapping("/api")
public class SugTagsResource {

    private final Logger log = LoggerFactory.getLogger(SugTagsResource.class);

    private static final String ENTITY_NAME = "sugTags";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SugTagsRepository sugTagsRepository;

    public SugTagsResource(SugTagsRepository sugTagsRepository) {
        this.sugTagsRepository = sugTagsRepository;
    }

    /**
     * {@code POST  /sug-tags} : Create a new sugTags.
     *
     * @param sugTags the sugTags to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sugTags, or with status {@code 400 (Bad Request)} if the sugTags has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/sug-tags")
    public ResponseEntity<SugTags> createSugTags(@RequestBody SugTags sugTags) throws URISyntaxException {
        log.debug("REST request to save SugTags : {}", sugTags);
        if (sugTags.getId() != null) {
            throw new BadRequestAlertException("A new sugTags cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SugTags result = sugTagsRepository.save(sugTags);
        return ResponseEntity.created(new URI("/api/sug-tags/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /sug-tags} : Updates an existing sugTags.
     *
     * @param sugTags the sugTags to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sugTags,
     * or with status {@code 400 (Bad Request)} if the sugTags is not valid,
     * or with status {@code 500 (Internal Server Error)} if the sugTags couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/sug-tags")
    public ResponseEntity<SugTags> updateSugTags(@RequestBody SugTags sugTags) throws URISyntaxException {
        log.debug("REST request to update SugTags : {}", sugTags);
        if (sugTags.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SugTags result = sugTagsRepository.save(sugTags);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, sugTags.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /sug-tags} : get all the sugTags.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sugTags in body.
     */
    @GetMapping("/sug-tags")
    public List<SugTags> getAllSugTags() {
        log.debug("REST request to get all SugTags");
        return sugTagsRepository.findAll();
    }

    /**
     * {@code GET  /sug-tags/:id} : get the "id" sugTags.
     *
     * @param id the id of the sugTags to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sugTags, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/sug-tags/{id}")
    public ResponseEntity<SugTags> getSugTags(@PathVariable Long id) {
        log.debug("REST request to get SugTags : {}", id);
        Optional<SugTags> sugTags = sugTagsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(sugTags);
    }

    /**
     * {@code DELETE  /sug-tags/:id} : delete the "id" sugTags.
     *
     * @param id the id of the sugTags to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/sug-tags/{id}")
    public ResponseEntity<Void> deleteSugTags(@PathVariable Long id) {
        log.debug("REST request to delete SugTags : {}", id);
        sugTagsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
