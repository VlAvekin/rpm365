package com.aviekinvlad.rpm365api.repo;

import com.aviekinvlad.rpm365api.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Vlad Aviekin
 * @date 16.02.2025
 */

@Repository
public interface DoctorRepo extends JpaRepository<DoctorEntity, Long> {
}
