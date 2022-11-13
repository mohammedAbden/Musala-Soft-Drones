package com.musala.soft.drones.service;

import com.musala.soft.drones.dto.DroneBatteryDTO;
import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.payload.RegisterDroneRequest;

import java.util.Collection;

public interface DroneService {

    DroneDTO registerDrone(RegisterDroneRequest registerDroneRequest);

    Collection<DroneDTO> getAvailableDrone();

    DroneBatteryDTO getDroneBatterLevel(Long id);

}
