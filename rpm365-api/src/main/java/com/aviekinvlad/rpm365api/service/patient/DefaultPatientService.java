package com.aviekinvlad.rpm365api.service.patient;

import com.aviekinvlad.rpm365api.advice.ErrorDTOException;
import com.aviekinvlad.rpm365api.dto.doctor.DoctorResponse;
import com.aviekinvlad.rpm365api.dto.patient.PatientDataResponse;
import com.aviekinvlad.rpm365api.dto.patient.PatientResponse;
import com.aviekinvlad.rpm365api.dto.visit.VisitResponse;
import com.aviekinvlad.rpm365api.entity.DoctorEntity;
import com.aviekinvlad.rpm365api.entity.PatientEntity;
import com.aviekinvlad.rpm365api.entity.VisitEntity;
import com.aviekinvlad.rpm365api.repo.PatientRepo;
import com.aviekinvlad.rpm365api.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Service
@RequiredArgsConstructor
public class DefaultPatientService implements PatientService {

    private final PatientRepo patientRepo;
    private final MessageService messageService;

    @Override
    public PatientEntity getPatient(long id) throws ErrorDTOException {
        return patientRepo
                .findById(id)
                .orElseThrow(() -> new ErrorDTOException(messageService.massage("error.patient.empty", id)));
    }

    @Override
    public PatientDataResponse getAllPatients(Pageable pageable,
                                              String firstName,
                                              Set<Long> doctorIds) {

        Page<PatientEntity> patientEntityPage = patientRepo.getAll(firstName, doctorIds, pageable);

        return cast(patientEntityPage);
    }

    private PatientDataResponse cast(Page<PatientEntity> patientEntityPage) {
        PatientDataResponse patientDataResponse = new PatientDataResponse();

        List<PatientResponse> data = patientEntityPage.get()
                .map(this::patientEntityToPatientResponse)
                .toList();
        patientDataResponse.setData(data);

        patientDataResponse.setCount(data.size());

        return patientDataResponse;
    }

    private PatientResponse patientEntityToPatientResponse(PatientEntity patientEntity) {
        if (patientEntity == null) return null;

        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setFirstName(patientEntity.getFirstName());
        patientResponse.setLastName(patientEntity.getLastName());

        List<VisitResponse> lastVisits = patientEntity.getVisits().stream()
                .map(this::visitEntityToVisitResponse)
                .toList();

        patientResponse.setLastVisits(lastVisits);
        return patientResponse;
    }

    private VisitResponse visitEntityToVisitResponse(VisitEntity visitEntity) {
        if (visitEntity == null) return null;

        VisitResponse visitResponse = new VisitResponse();

        DoctorEntity doctor = visitEntity.getDoctor();
        if (doctor != null) {
            ZoneId zoneId = doctor.getTimezone().toZoneId();
            ZonedDateTime start = visitEntity.getStartDateTime().toInstant().atZone(zoneId);
            ZonedDateTime end = visitEntity.getEndDateTime().toInstant().atZone(zoneId);

            visitResponse.setStart(start);
            visitResponse.setEnd(end);
        }

        DoctorResponse doctorResponse = doctorEntityToDoctorResponse(doctor);
        visitResponse.setDoctor(doctorResponse);

        return visitResponse;
    }

    private DoctorResponse doctorEntityToDoctorResponse(DoctorEntity doctorEntity){
        if (doctorEntity == null) return null;

        DoctorResponse doctorResponse = new DoctorResponse();

        doctorResponse.setFirstName(doctorEntity.getFirstName());
        doctorResponse.setLastName(doctorEntity.getLastName());
        int size = doctorEntity.getVisits().size();
        doctorResponse.setTotalPatients(size);

        return doctorResponse;
    }
}
