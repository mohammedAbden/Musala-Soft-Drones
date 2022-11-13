package com.musala.soft.drones.service;

import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.entity.Trip;
import com.musala.soft.drones.enums.TripStatus;

import java.util.List;

public interface TripService {

    Trip findByDroneAndStatusIn(Drone drone, List<TripStatus> status);

    Trip save(Trip currentTrip);

}
