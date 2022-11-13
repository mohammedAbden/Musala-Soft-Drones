package com.musala.soft.drones.service;

import com.musala.soft.drones.dto.MedicationDTO;
import com.musala.soft.drones.entity.Medication;
import com.musala.soft.drones.payload.AddingMedicationRequest;

import java.util.List;

public interface MedicationService {

    MedicationDTO addMedication(AddingMedicationRequest addingMedicationRequest);

    List<Medication> findValidMedicationForLoading(List<Long> medicationIds);

}
