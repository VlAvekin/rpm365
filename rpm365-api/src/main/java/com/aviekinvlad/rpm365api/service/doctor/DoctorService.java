package com.aviekinvlad.rpm365api.service.doctor;

import com.aviekinvlad.rpm365api.advice.ErrorDTOException;
import com.aviekinvlad.rpm365api.entity.DoctorEntity;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

public interface DoctorService {

    DoctorEntity getDoctor(long id) throws ErrorDTOException;
}

