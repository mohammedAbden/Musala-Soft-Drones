package com.musala.soft.drones.service;

import com.musala.soft.drones.dto.MedicationDTO;
import com.musala.soft.drones.payload.AddingMedicationRequest;

public interface MedicationService {

    MedicationDTO addMedication(AddingMedicationRequest addingMedicationRequest);

}
