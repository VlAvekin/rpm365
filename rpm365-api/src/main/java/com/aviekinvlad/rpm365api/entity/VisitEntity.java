package com.aviekinvlad.rpm365api.entity;

import com.aviekinvlad.rpm365api.dto.visit.VisitJsonViews;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

/**
 * @author Vlad Aviekin
 * @date 15.02.2025
 */

@Data
@Entity
@Table(name = "visit")
@NoArgsConstructor
public class VisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "start_date_time", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm Z")
    @JsonView(VisitJsonViews.Short.class)
    private ZonedDateTime startDateTime;

    @Column(name = "end_date_time", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm Z")
    @JsonView(VisitJsonViews.Short.class)
    private ZonedDateTime endDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonView(VisitJsonViews.Short.class)
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @JsonView(VisitJsonViews.Short.class)
    private DoctorEntity doctor;
}
