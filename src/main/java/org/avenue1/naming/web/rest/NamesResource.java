package org.avenue1.naming.web.rest;

import com.codahale.metrics.annotation.Timed;
import org.avenue1.naming.domain.Names;
import org.avenue1.naming.repository.NamesRepository;
import org.avenue1.naming.web.rest.errors.BadRequestAlertException;
import org.avenue1.naming.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Names.
 */
@RestController
@RequestMapping("/api")
public class NamesResource {

    private final Logger log = LoggerFactory.getLogger(NamesResource.class);

    private static final String ENTITY_NAME = "namingSvcNames";

    private final NamesRepository namesRepository;

    public NamesResource(NamesRepository namesRepository) {
        this.namesRepository = namesRepository;
    }

    /**
     * POST  /names : Create a new names.
     *
     * @param names the names to create
     * @return the ResponseEntity with status 201 (Created) and with body the new names, or with status 400 (Bad Request) if the names has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/names")
    @Timed
    public ResponseEntity<Names> createNames(@Valid @RequestBody Names names) throws URISyntaxException {
        log.debug("REST request to save Names : {}", names);
        if (names.getId() != null) {
            throw new BadRequestAlertException("A new names cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Names result = namesRepository.save(names);
        return ResponseEntity.created(new URI("/api/names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /names : Updates an existing names.
     *
     * @param names the names to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated names,
     * or with status 400 (Bad Request) if the names is not valid,
     * or with status 500 (Internal Server Error) if the names couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/names")
    @Timed
    public ResponseEntity<Names> updateNames(@Valid @RequestBody Names names) throws URISyntaxException {
        log.debug("REST request to update Names : {}", names);
        if (names.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Names result = namesRepository.save(names);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, names.getId().toString()))
            .body(result);
    }

    /**
     * GET  /names : get all the names.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of names in body
     */
    @GetMapping("/names")
    @Timed
    public List<Names> getAllNames() {
        log.debug("REST request to get all Names");
        return namesRepository.findAll();
    }

    /**
     * GET  /names/:id : get the "id" names.
     *
     * @param id the id of the names to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the names, or with status 404 (Not Found)
     */
    @GetMapping("/names/{id}")
    @Timed
    public ResponseEntity<Names> getNames(@PathVariable String id) {
        log.debug("REST request to get Names : {}", id);
        Optional<Names> names = namesRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(names);
    }

    /**
     * DELETE  /names/:id : delete the "id" names.
     *
     * @param id the id of the names to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/names/{id}")
    @Timed
    public ResponseEntity<Void> deleteNames(@PathVariable String id) {
        log.debug("REST request to delete Names : {}", id);

        namesRepository.deleteById(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
