package com.aviekinvlad.rpm365api.dto.doctor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorResponse {

    private String firstName;
    private String lastName;
    private int totalPatients;
}
