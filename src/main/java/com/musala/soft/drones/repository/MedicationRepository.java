package com.musala.soft.drones.repository;

import com.musala.soft.drones.entity.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {

}
