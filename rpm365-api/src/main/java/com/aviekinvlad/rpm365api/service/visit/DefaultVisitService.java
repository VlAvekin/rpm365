package com.aviekinvlad.rpm365api.service.visit;

import com.aviekinvlad.rpm365api.advice.ErrorDTOException;
import com.aviekinvlad.rpm365api.dto.visit.VisitRequest;
import com.aviekinvlad.rpm365api.dto.visit.VisitResponse;
import com.aviekinvlad.rpm365api.entity.DoctorEntity;
import com.aviekinvlad.rpm365api.entity.PatientEntity;
import com.aviekinvlad.rpm365api.entity.VisitEntity;
import com.aviekinvlad.rpm365api.repo.VisitRepo;
import com.aviekinvlad.rpm365api.service.doctor.DoctorService;
import com.aviekinvlad.rpm365api.service.message.MessageService;
import com.aviekinvlad.rpm365api.service.patient.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.TimeZone;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Service
@RequiredArgsConstructor
public class DefaultVisitService implements VisitService {

    private final VisitRepo visitRepo;

    private final DoctorService doctorService;
    private final PatientService patientService;
    private final MessageService messageService;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.SERIALIZABLE)
    public VisitEntity create(VisitRequest visitRequest) throws ErrorDTOException {
        DoctorEntity doctor = doctorService.getDoctor(visitRequest.getDoctorId());
        PatientEntity patient = patientService.getPatient(visitRequest.getPatientId());

        validStartAfterEntTime(visitRequest);

        ZoneId zoneId = doctor.getTimezone().toZoneId();
        ZonedDateTime start = visitRequest.getStart().toInstant().atZone(zoneId);
        ZonedDateTime end = visitRequest.getEnd().toInstant().atZone(zoneId);

        isYouVisit(patient, start, end);
        isVisit(doctor, start, end);

        VisitEntity visit = new VisitEntity();
        visit.setDoctor(doctor);
        visit.setPatient(patient);
        visit.setStartDateTime(start);
        visit.setEndDateTime(end);

        return visitRepo.save(visit);
    }

    private void isYouVisit(PatientEntity patient,
                            ZonedDateTime start,
                            ZonedDateTime end) throws ErrorDTOException {

        List<VisitEntity> byMemberShipDateBetween = visitRepo
                .getPatientAppointment(patient.getId(), start, end);
        if (!byMemberShipDateBetween.isEmpty())
            throw new ErrorDTOException(messageService.massage("error.doctor.you.appointment"));
    }

    private void isVisit(DoctorEntity doctor,
                         ZonedDateTime start,
                         ZonedDateTime end) throws ErrorDTOException {

        List<VisitEntity> byMemberShipDateBetween = visitRepo
                .getDoctorAppointment(doctor.getId(), start, end);
        if (!byMemberShipDateBetween.isEmpty())
            throw new ErrorDTOException(messageService.massage("error.doctor.already.appointment"));
    }

    private void validStartAfterEntTime(VisitRequest visitRequest) throws ErrorDTOException {
        ZonedDateTime start = visitRequest.getStart();
        ZonedDateTime end = visitRequest.getEnd();

        if (start.isAfter(end) || start.equals(end))
            throw new ErrorDTOException(messageService.massage("error.start.after.end"));
    }

}
