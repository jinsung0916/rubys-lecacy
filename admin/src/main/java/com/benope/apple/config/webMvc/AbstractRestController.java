package com.benope.apple.config.webMvc;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

public abstract class AbstractRestController<REQ extends IdRequest, RES> {

    @GetMapping
    public ResponseEntity<?> get(
            @FilterParams REQ input,
            Pageable pageable
    ) {
        if(Objects.nonNull(input.getId())) {
            List<RES> results = findByIds(input.getId());
            return ResponseEntity.ok(results);
        } else {
            Page<RES> results = findAll(input, pageable);

            return ResponseEntity.ok()
                    .header("Content-Range", String.valueOf(results.getTotalElements()))
                    .body(results.toList());
        }
    }

    @GetMapping("/{id}")
    public RES getById(@PathVariable Long id) {
        return findById(id);
    }

    @PostMapping
    public RES post(@RequestBody REQ input) {
        return create(input);
    }

    @PutMapping("/{id}")
    public RES put(
            @PathVariable Long id,
            @RequestBody REQ input
    ) {
        return update(id, input);
    }

    @DeleteMapping("/{id}")
    public Long delete(@PathVariable Long id) {
        deleteById(id);
        return id;
    }

    protected abstract Page<RES> findAll(REQ input, Pageable pageable);

    protected abstract RES findById(Long id);

    protected abstract List<RES> findByIds(List<Long> ids);

    protected abstract RES create(REQ input);

    protected abstract RES update(Long id, REQ input);

    protected abstract void deleteById(Long id);

}
