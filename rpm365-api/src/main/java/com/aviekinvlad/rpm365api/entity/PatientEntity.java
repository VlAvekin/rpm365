package com.aviekinvlad.rpm365api.entity;

import com.aviekinvlad.rpm365api.dto.patient.PatientJsonViews;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Vlad Aviekin
 * @date 15.02.2025
 */

@Data
@Entity
@Table(name = "patient")
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "first_name", nullable = false)
    @JsonView(PatientJsonViews.Short.class)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @JsonView(PatientJsonViews.Short.class)
    private String lastName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "patient")
    private List<VisitEntity> visits;
}
