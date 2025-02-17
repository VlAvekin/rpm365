package com.aviekinvlad.rpm365api.service.patient;

import com.aviekinvlad.rpm365api.advice.ErrorDTOException;
import com.aviekinvlad.rpm365api.config.TestMassage;
import com.aviekinvlad.rpm365api.dto.doctor.DoctorResponse;
import com.aviekinvlad.rpm365api.dto.patient.PatientDataResponse;
import com.aviekinvlad.rpm365api.dto.patient.PatientResponse;
import com.aviekinvlad.rpm365api.dto.visit.VisitResponse;
import com.aviekinvlad.rpm365api.entity.DoctorEntity;
import com.aviekinvlad.rpm365api.entity.PatientEntity;
import com.aviekinvlad.rpm365api.entity.VisitEntity;
import com.aviekinvlad.rpm365api.repo.PatientRepo;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({
        "classpath:application-test.yml",
        "classpath:test.error.massage.properties",
        "classpath:test.validation.massage.properties"
})
@ActiveProfiles("test")
public class DefaultPatientServiceTest {

    @Autowired
    private TestMassage testMessage;
    @Autowired
    private DefaultPatientService patientService;

    @MockBean
    private PatientRepo patientRepo;

    @SneakyThrows
    @Test
    public void getPatientOk() {
        final long id = 1L;

        PatientEntity expected = new PatientEntity();
        expected.setId(id);

        final Optional<PatientEntity> data = Optional.of(expected);

        Mockito.when(patientRepo.findById(id)).thenReturn(data);

        final PatientEntity actual = patientService.getPatient(id);

        Mockito.verify(patientRepo, Mockito.times(1)).findById(id);

        assertEquals(expected, actual);
    }

    @Test
    public void getPatientThrow() {
        final long id = 1L;

        final String expected = testMessage.massage("test.error.patient.empty", id);

        ErrorDTOException actual = assertThrows(ErrorDTOException.class, () -> patientService.getPatient(id));

        assertEquals(expected, actual.getMessage());
    }

    @Test
    public void getAllPatientsZero() {
        Pageable pageable = PageRequest.of(0, 10);
        String firstName = "Tom";
        Set<Long> doctorIds = Set.of(1L, 2L);

        List<PatientEntity> patients = new ArrayList<>();
        Page<PatientEntity> data = new PageImpl<PatientEntity>(patients, pageable, patients.size());

        Mockito.when(patientRepo.getAll(firstName, doctorIds, pageable)).thenReturn(data);

        PatientDataResponse expected = new PatientDataResponse();
        expected.setData(new ArrayList<>());
        expected.setCount(0);

        PatientDataResponse actual = patientService.getAllPatients(pageable, firstName, doctorIds);
        assertEquals(expected, actual);
    }

    @Test
    public void getAllPatientsOne() {
        Pageable pageable = PageRequest.of(0, 10);
        String firstName = "Tom";
        Set<Long> doctorIds = Set.of(1L, 2L);

        List<PatientEntity> patients = List
                .of(new PatientEntity(1L, "Tom", "Cruise", new ArrayList<>()));
        Page<PatientEntity> data = new PageImpl<PatientEntity>(patients, pageable, patients.size());

        Mockito.when(patientRepo.getAll(firstName, doctorIds, pageable)).thenReturn(data);

        PatientDataResponse expected = new PatientDataResponse();
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setFirstName("Tom");
        patientResponse.setLastName("Cruise");
        patientResponse.setLastVisits(new ArrayList<>());

        expected.setData(List.of(patientResponse));
        expected.setCount(1);

        PatientDataResponse actual = patientService.getAllPatients(pageable, firstName, doctorIds);
        assertEquals(expected, actual);
    }

    @Test
    public void getAllPatientsOneVisit() {
        Pageable pageable = PageRequest.of(0, 10);
        String firstName = "Tom";
        Set<Long> doctorIds = Set.of(1L, 2L);

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T08:00:00+00:00");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T08:30:00+00:00");
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setStartDateTime(start);
        visitEntity.setEndDateTime(end);
        List<VisitEntity> visits = List.of(visitEntity);

        List<PatientEntity> patients = List
                .of(new PatientEntity(1L, "Tom", "Cruise", visits));
        Page<PatientEntity> data = new PageImpl<PatientEntity>(patients, pageable, patients.size());

        Mockito.when(patientRepo.getAll(firstName, doctorIds, pageable)).thenReturn(data);

        PatientDataResponse expected = new PatientDataResponse();
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setFirstName("Tom");
        patientResponse.setLastName("Cruise");

        VisitResponse visitResponse = new VisitResponse();
        List<VisitResponse> expectedVisits = List.of(visitResponse);
        patientResponse.setLastVisits(expectedVisits);

        expected.setData(List.of(patientResponse));
        expected.setCount(1);

        PatientDataResponse actual = patientService.getAllPatients(pageable, firstName, doctorIds);
        assertEquals(expected, actual);
    }

    @Test
    public void getAllPatientsOneVisitDoctor() {
        Pageable pageable = PageRequest.of(0, 10);
        String firstName = "Tom";
        Set<Long> doctorIds = Set.of(1L, 2L);

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T10:00+02:00[Europe/Kyiv]");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T10:30+02:00[Europe/Kyiv]");
        VisitEntity visitEntity = new VisitEntity();
        visitEntity.setStartDateTime(start);
        visitEntity.setEndDateTime(end);

        DoctorEntity doctor = new DoctorEntity();
        doctor.setFirstName("Gregory");
        doctor.setLastName("House");
        doctor.setTimezone(TimeZone.getTimeZone(ZoneId.of("Europe/Kyiv")));
        doctor.setVisits(new ArrayList<>());
        visitEntity.setDoctor(doctor);

        List<VisitEntity> visits = List.of(visitEntity);

        List<PatientEntity> patients = List
                .of(new PatientEntity(1L, "Tom", "Cruise", visits));
        Page<PatientEntity> data = new PageImpl<PatientEntity>(patients, pageable, patients.size());

        Mockito.when(patientRepo.getAll(firstName, doctorIds, pageable)).thenReturn(data);

        PatientDataResponse expected = new PatientDataResponse();
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setFirstName("Tom");
        patientResponse.setLastName("Cruise");

        VisitResponse visitResponse = new VisitResponse();
        visitResponse.setStart(start);
        visitResponse.setEnd(end);

        DoctorResponse expectedDoctor = new DoctorResponse();
        expectedDoctor.setFirstName("Gregory");
        expectedDoctor.setLastName("House");
        expectedDoctor.setTotalPatients(0);
        visitResponse.setDoctor(expectedDoctor);

        List<VisitResponse> expectedVisits = List.of(visitResponse);
        patientResponse.setLastVisits(expectedVisits);

        expected.setData(List.of(patientResponse));
        expected.setCount(1);

        PatientDataResponse actual = patientService.getAllPatients(pageable, firstName, doctorIds);
        assertEquals(expected, actual);
    }
}