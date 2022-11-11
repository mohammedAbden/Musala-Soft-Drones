package com.musala.soft.drones.service;

import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.payload.RegisterDroneRequest;

public interface DroneService {

    DroneDTO registerDrone(RegisterDroneRequest registerDroneRequest);

}
