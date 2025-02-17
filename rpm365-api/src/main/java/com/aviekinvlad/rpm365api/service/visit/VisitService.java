package com.aviekinvlad.rpm365api.service.visit;

import com.aviekinvlad.rpm365api.advice.ErrorDTOException;
import com.aviekinvlad.rpm365api.dto.visit.VisitRequest;
import com.aviekinvlad.rpm365api.dto.visit.VisitResponse;
import com.aviekinvlad.rpm365api.entity.VisitEntity;
import jakarta.validation.Valid;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

public interface VisitService {

    VisitEntity create(VisitRequest visitRequest) throws ErrorDTOException;

}
