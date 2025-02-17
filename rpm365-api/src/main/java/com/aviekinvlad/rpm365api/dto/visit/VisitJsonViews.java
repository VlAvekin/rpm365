package com.aviekinvlad.rpm365api.dto.visit;

import com.aviekinvlad.rpm365api.dto.doctor.DoctorJsonViews;
import com.aviekinvlad.rpm365api.dto.patient.PatientJsonViews;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

public class VisitJsonViews {

    public interface Short extends PatientJsonViews.Short, DoctorJsonViews.Short {};

}
