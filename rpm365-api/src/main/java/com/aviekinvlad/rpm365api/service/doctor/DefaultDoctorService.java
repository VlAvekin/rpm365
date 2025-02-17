package com.aviekinvlad.rpm365api.service.doctor;

import com.aviekinvlad.rpm365api.advice.ErrorDTOException;
import com.aviekinvlad.rpm365api.entity.DoctorEntity;
import com.aviekinvlad.rpm365api.repo.DoctorRepo;
import com.aviekinvlad.rpm365api.service.message.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Service
@RequiredArgsConstructor
public class DefaultDoctorService implements DoctorService {

    private final DoctorRepo doctorRepo;
    private final MessageService messageService;

    @Override
    public DoctorEntity getDoctor(long id) throws ErrorDTOException {
        return doctorRepo
                .findById(id)
                .orElseThrow(() -> new ErrorDTOException(messageService.massage("error.doctor.empty", id)));
    }
}
