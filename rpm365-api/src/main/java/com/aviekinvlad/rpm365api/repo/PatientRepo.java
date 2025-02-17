package com.aviekinvlad.rpm365api.repo;

import com.aviekinvlad.rpm365api.entity.PatientEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Repository
public interface PatientRepo extends JpaRepository<PatientEntity, Long> {


    @Query(value = """
            from PatientEntity p
            left join fetch p.visits v
            left join fetch v.doctor d
            where (:firstName = '' or p.firstName = :firstName)
                        and (:#{#doctorIds == null || #doctorIds.size() < 1} = true or d.id IN :doctorIds)
            """)
    Page<PatientEntity> getAll(String firstName, Set<Long> doctorIds, Pageable pageable);

}
