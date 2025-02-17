package com.aviekinvlad.rpm365api.service.visit;

import com.aviekinvlad.rpm365api.advice.ErrorDTOException;
import com.aviekinvlad.rpm365api.config.TestMassage;
import com.aviekinvlad.rpm365api.dto.visit.VisitRequest;
import com.aviekinvlad.rpm365api.entity.DoctorEntity;
import com.aviekinvlad.rpm365api.entity.PatientEntity;
import com.aviekinvlad.rpm365api.entity.VisitEntity;
import com.aviekinvlad.rpm365api.repo.PatientRepo;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource({
        "classpath:application-test.yml",
        "classpath:test.error.massage.properties",
        "classpath:test.validation.massage.properties"
})
@ActiveProfiles("test")
@Sql(value = "/visit/create-visit-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(value = "/visit/create-visit-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class DefaultVisitServiceTest {

    @Autowired
    private TestMassage testMessage;
    @Autowired
    private DefaultVisitService defaultVisitService;
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    public void createOk() {
        VisitRequest visitRequest = new VisitRequest();
        visitRequest.setPatientId(2L);
        visitRequest.setDoctorId(1L);

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T14:00:00+02:00");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T15:00:00+02:00");
        visitRequest.setStart(start);
        visitRequest.setEnd(end);

        VisitEntity actual = defaultVisitService.create(visitRequest);

        assertNotNull(actual);

    }

    @Test
    public void createStartAfterEntTime() {
        final String expected = testMessage.massage("test.error.start.after.end");

        VisitRequest visitRequest = new VisitRequest();
        visitRequest.setPatientId(1L);
        visitRequest.setDoctorId(1L);

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T12:00:00+02:00");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T11:00:00+02:00");
        visitRequest.setStart(start);
        visitRequest.setEnd(end);

        ErrorDTOException actual = assertThrows(ErrorDTOException.class, () -> defaultVisitService.create(visitRequest));

        assertEquals(expected, actual.getMessage());

    }

    @Test
    public void createYouVisit() {

        final String expected = testMessage.massage("test.error.doctor.you.appointment");

        VisitRequest visitRequest = new VisitRequest();
        visitRequest.setPatientId(1L);
        visitRequest.setDoctorId(1L);

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T12:00:00+02:00");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T12:20:00+02:00");
        visitRequest.setStart(start);
        visitRequest.setEnd(end);

        ErrorDTOException actual = assertThrows(ErrorDTOException.class, () -> defaultVisitService.create(visitRequest));

        assertEquals(expected, actual.getMessage());

    }

    @Test
    public void createVisit() {

        final String expected = testMessage.massage("test.error.doctor.already.appointment");

        VisitRequest visitRequest = new VisitRequest();
        visitRequest.setPatientId(2L);
        visitRequest.setDoctorId(1L);

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T12:00:00+02:00");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T12:20:00+02:00");
        visitRequest.setStart(start);
        visitRequest.setEnd(end);

        ErrorDTOException actual = assertThrows(ErrorDTOException.class, () -> defaultVisitService.create(visitRequest));

        assertEquals(expected, actual.getMessage());

    }
}