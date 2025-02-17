package com.aviekinvlad.rpm365api.dto.patient;

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
public class PatientDataResponse {

    private List<PatientResponse> data;
    private int count;

}
