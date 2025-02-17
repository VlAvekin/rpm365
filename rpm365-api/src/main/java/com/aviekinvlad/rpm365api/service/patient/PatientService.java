package com.aviekinvlad.rpm365api.service.patient;

import com.aviekinvlad.rpm365api.advice.ErrorDTOException;
import com.aviekinvlad.rpm365api.dto.patient.PatientDataResponse;
import com.aviekinvlad.rpm365api.entity.PatientEntity;
import org.springframework.data.domain.Pageable;

import java.util.Set;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

public interface PatientService {

    PatientEntity getPatient(long id) throws ErrorDTOException;

    PatientDataResponse getAllPatients(Pageable pageable, String firstName, Set<Long> doctorIds);


}
