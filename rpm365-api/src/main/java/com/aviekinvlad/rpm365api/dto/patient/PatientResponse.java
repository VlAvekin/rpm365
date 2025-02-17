package com.aviekinvlad.rpm365api.dto.patient;

import com.aviekinvlad.rpm365api.dto.visit.VisitResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {

    private String firstName;
    private String lastName;
    private List<VisitResponse> lastVisits;

}
