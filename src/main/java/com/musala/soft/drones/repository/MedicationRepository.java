package com.musala.soft.drones.repository;

import com.musala.soft.drones.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

    List<Medication> findByIdInAndTripIsNull(List<Long> medicationIds);

}
