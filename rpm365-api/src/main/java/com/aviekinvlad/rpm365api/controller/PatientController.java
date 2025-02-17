package com.aviekinvlad.rpm365api.controller;

import com.aviekinvlad.rpm365api.dto.patient.PatientDataResponse;
import com.aviekinvlad.rpm365api.service.patient.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@RestController
@RequestMapping("${api.prefix}/${api.v1}/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public ResponseEntity<PatientDataResponse> get(
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") Set<Long> doctorIds,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        PatientDataResponse patients = patientService.getAllPatients(pageable, search, doctorIds);
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

}
