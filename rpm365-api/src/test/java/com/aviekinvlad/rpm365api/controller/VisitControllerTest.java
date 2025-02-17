package com.aviekinvlad.rpm365api.controller;

import com.aviekinvlad.rpm365api.config.TestMassage;
import com.aviekinvlad.rpm365api.dto.visit.VisitRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.ZonedDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles( "test" )
@TestPropertySource({
        "classpath:application-test.yml",
        "classpath:test.error.massage.properties",
        "classpath:test.validation.massage.properties"
})
@Sql(value = "/visit/create-visit-before.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(value = "/visit/create-visit-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
public class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TestMassage testMessage;

    @SneakyThrows
    @Test
    public void createOk() {
        VisitRequest visitRequest = new VisitRequest();
        visitRequest.setPatientId(1L);
        visitRequest.setDoctorId(1L);

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T10:10:00+02:00");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T10:20:00+02:00");
        visitRequest.setStart(start);
        visitRequest.setEnd(end);

        String data = objectMapper.writeValueAsString(visitRequest);

        this.mockMvc.perform(post("/api/v1/visit")
                        .content(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @SneakyThrows
    @Test
    public void createStartAfterEntTime() {
        VisitRequest visitRequest = new VisitRequest();
        visitRequest.setPatientId(1L);
        visitRequest.setDoctorId(1L);

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T10:30:00+02:00");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T10:20:00+02:00");
        visitRequest.setStart(start);
        visitRequest.setEnd(end);

        String data = objectMapper.writeValueAsString(visitRequest);

        this.mockMvc.perform(post("/api/v1/visit")
                        .content(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value(testMessage.massage("test.error.start.after.end")));
    }

    @SneakyThrows
    @Test
    public void createYouVisit() {
        VisitRequest visitRequest = new VisitRequest();
        visitRequest.setPatientId(1L);
        visitRequest.setDoctorId(1L);

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T12:10:00+02:00");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T12:20:00+02:00");
        visitRequest.setStart(start);
        visitRequest.setEnd(end);

        String data = objectMapper.writeValueAsString(visitRequest);

        this.mockMvc.perform(post("/api/v1/visit")
                        .content(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value(testMessage.massage("test.error.doctor.you.appointment")));
    }

    @SneakyThrows
    @Test
    public void createVisit() {
        VisitRequest visitRequest = new VisitRequest();
        visitRequest.setPatientId(2L);
        visitRequest.setDoctorId(1L);

        ZonedDateTime start = ZonedDateTime.parse("2025-02-16T12:10:00+02:00");
        ZonedDateTime end = ZonedDateTime.parse("2025-02-16T12:20:00+02:00");
        visitRequest.setStart(start);
        visitRequest.setEnd(end);

        String data = objectMapper.writeValueAsString(visitRequest);

        this.mockMvc.perform(post("/api/v1/visit")
                        .content(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error")
                        .value(testMessage.massage("test.error.doctor.already.appointment")));
    }

    @SneakyThrows
    @Test
    public void createValidNotNull() {
        this.mockMvc.perform(post("/api/v1/visit")
                        .content("{}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.start")
                        .value(testMessage.massage("test.validate.visit.start")))
                .andExpect(jsonPath("$.end")
                        .value(testMessage.massage("test.validate.visit.end")))
                .andExpect(jsonPath("$.patientId")
                        .value(testMessage.massage("test.validate.visit.patient")))
                .andExpect(jsonPath("$.doctorId")
                        .value(testMessage.massage("test.validate.visit.doctor")));
    }

    @SneakyThrows
    @Test
    public void createValidMin() {
        VisitRequest visitRequest = new VisitRequest();
        visitRequest.setDoctorId(-1L);
        visitRequest.setPatientId(-1L);

        String data = objectMapper.writeValueAsString(visitRequest);

        this.mockMvc.perform(post("/api/v1/visit")
                        .content(data)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.patientId")
                        .value(testMessage.massage("test.validate.visit.patient.min")))
                .andExpect(jsonPath("$.doctorId")
                        .value(testMessage.massage("test.validate.visit.doctor.min")));
    }
}