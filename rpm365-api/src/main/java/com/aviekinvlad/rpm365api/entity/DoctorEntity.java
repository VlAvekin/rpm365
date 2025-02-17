package com.aviekinvlad.rpm365api.entity;

import com.aviekinvlad.rpm365api.dto.doctor.DoctorJsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.TimeZone;

/**
 * @author Vlad Aviekin
 * @date 15.02.2025
 */

@Data
@Entity
@Table(name = "doctor")
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @JsonView(DoctorJsonViews.Short.class)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonView(DoctorJsonViews.Short.class)
    private String lastName;

    @Column(name = "timezone", nullable = false)
    @JsonView(DoctorJsonViews.Short.class)
    private TimeZone timezone;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "doctor")
    private List<VisitEntity> visits;
}
