package com.aviekinvlad.rpm365api.dto.visit;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.ZonedDateTimeSerializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitRequest {

    @NotNull(message = "{validate.visit.start}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm Z")
    @JsonProperty(value = "start")
    private ZonedDateTime start;

    @NotNull(message = "{validate.visit.end}")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm Z")
    @JsonProperty(value = "end")
    private ZonedDateTime end;

    @NotNull(message = "{validate.visit.patient}")
    @Min(value = 1, message = "{validate.visit.patient.min}")
    @JsonProperty(value = "patientId")
    private Long patientId;

    @NotNull(message = "{validate.visit.doctor}")
    @Min(value = 1, message = "{validate.visit.doctor.min}")
    @JsonProperty(value = "doctorId")
    private Long doctorId;

}
