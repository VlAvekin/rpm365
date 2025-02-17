package com.aviekinvlad.rpm365api.dto.visit;

import com.aviekinvlad.rpm365api.dto.doctor.DoctorResponse;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitResponse {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm Z")
    private ZonedDateTime start;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm Z")
    private ZonedDateTime end;

    private DoctorResponse doctor;
}
