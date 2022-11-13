package com.musala.soft.drones.service;

import com.musala.soft.drones.dto.TripDTO;
import com.musala.soft.drones.payload.LoadMedicationsRequest;

public interface DroneLoadMedicationFacade {

    TripDTO loadDroneWithMedication(Long id, LoadMedicationsRequest loadMedicationsRequest);

    TripDTO getLoadedDroneWithMedication(Long id);

}
