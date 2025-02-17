package com.aviekinvlad.rpm365api.controller;

import com.aviekinvlad.rpm365api.dto.doctor.DoctorResponse;
import com.aviekinvlad.rpm365api.dto.patient.PatientDataResponse;
import com.aviekinvlad.rpm365api.dto.patient.PatientResponse;
import com.aviekinvlad.rpm365api.dto.visit.VisitResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource({
        "classpath:application-test.yml",
        "classpath:test.error.massage.properties",
        "classpath:test.validation.massage.properties"
})
@Sql(value = "/patient/patient-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(value = "/patient/patient-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @Test
    public void getSearch() {

        PatientDataResponse patientDataResponse = new PatientDataResponse();
        patientDataResponse.setCount(1);

        List<PatientResponse> patientResponses = new ArrayList<>();
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setFirstName("Jackie");
        patientResponse.setLastName("Chan");

        List<VisitResponse> lastVisits = new ArrayList<>();
        VisitResponse visitResponse = new VisitResponse();

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T10:00:00+02:00");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T10:30:00+02:00");
        visitResponse.setStart(start);
        visitResponse.setEnd(end);

        DoctorResponse doctorResponse = new DoctorResponse();
        doctorResponse.setFirstName("Lisa");
        doctorResponse.setLastName("Cuddy");
        doctorResponse.setTotalPatients(3);
        visitResponse.setDoctor(doctorResponse);

        lastVisits.add(visitResponse);
        patientResponse.setLastVisits(lastVisits);

        patientResponses.add(patientResponse);

        patientDataResponse.setData(patientResponses);

        String json = objectMapper.writeValueAsString(patientDataResponse);

        this.mockMvc.perform(get("/api/v1/patient").param("search", "Jackie"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

    }

    @SneakyThrows
    @Test
    public void getDoctorIds() {

        PatientDataResponse patientDataResponse = new PatientDataResponse();
        patientDataResponse.setCount(1);

        List<PatientResponse> patientResponses = new ArrayList<>();
        PatientResponse patientResponse = new PatientResponse();
        patientResponse.setFirstName("Ruth");
        patientResponse.setLastName("Negga");

        List<VisitResponse> lastVisits = new ArrayList<>();
        VisitResponse visitResponse = new VisitResponse();

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T15:00:00-05:00");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T15:25:00-05:00");
        visitResponse.setStart(start);
        visitResponse.setEnd(end);

        DoctorResponse doctorResponse = new DoctorResponse();
        doctorResponse.setFirstName("Remy");
        doctorResponse.setLastName("Hadley");
        doctorResponse.setTotalPatients(1);
        visitResponse.setDoctor(doctorResponse);

        lastVisits.add(visitResponse);
        patientResponse.setLastVisits(lastVisits);

        patientResponses.add(patientResponse);

        patientDataResponse.setData(patientResponses);

        String json = objectMapper.writeValueAsString(patientDataResponse);

        this.mockMvc.perform(get("/api/v1/patient").param("doctorIds", "4"))
                .andExpect(status().isOk())
                .andExpect(content().json(json));

    }

    @SneakyThrows
    @Test
    public void getDoctorIdsPageable() {
        this.mockMvc.perform(get("/api/v1/patient").param("page", "0").param("size", "4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(4));
    }
}