package com.aviekinvlad.rpm365api.controller;

import com.aviekinvlad.rpm365api.dto.visit.VisitJsonViews;
import com.aviekinvlad.rpm365api.dto.visit.VisitRequest;
import com.aviekinvlad.rpm365api.entity.VisitEntity;
import com.aviekinvlad.rpm365api.service.visit.VisitService;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@RestController
@RequestMapping("${api.prefix}/${api.v1}/visit")
@RequiredArgsConstructor
public class VisitController {

    private final VisitService visitService;

    @SneakyThrows
    @PostMapping
    @JsonView(VisitJsonViews.Short.class)
    public ResponseEntity<VisitEntity> create(@Valid @RequestBody VisitRequest visitRequest) {
        VisitEntity visitEntity = visitService.create(visitRequest);
        return new ResponseEntity<>(visitEntity, HttpStatus.CREATED);
    }
}
