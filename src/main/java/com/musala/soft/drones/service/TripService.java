package com.musala.soft.drones.service;

import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.entity.Trip;

public interface TripService {

    Trip findCurrentTripByDroneOrReturnNew(Drone drone);

    Trip save(Trip currentTrip);

}
