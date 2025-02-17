package com.aviekinvlad.rpm365api.repo;

import com.aviekinvlad.rpm365api.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Repository
public interface VisitRepo extends JpaRepository<VisitEntity, Long> {

    @Query(value = """
            from VisitEntity
            where patient.id = :patientId and (:startDateTime BETWEEN startDateTime AND endDateTime
                        or :endDateTime BETWEEN startDateTime AND endDateTime)
            """)
    List<VisitEntity> getPatientAppointment(
            long patientId,
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime);

    @Query(value = """
            from VisitEntity
            where doctor.id = :doctorId and (:startDateTime BETWEEN startDateTime AND endDateTime
                        or :endDateTime BETWEEN startDateTime AND endDateTime)
            """)
    List<VisitEntity> getDoctorAppointment(
            long doctorId,
            ZonedDateTime startDateTime,
            ZonedDateTime endDateTime);
}
