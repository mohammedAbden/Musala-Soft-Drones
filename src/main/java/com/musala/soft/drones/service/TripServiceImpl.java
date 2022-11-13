package com.musala.soft.drones.service;

import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.entity.Trip;
import com.musala.soft.drones.enums.TripStatus;
import com.musala.soft.drones.exception.ErrorCode;
import com.musala.soft.drones.exception.RuntimeBusinessException;
import com.musala.soft.drones.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripServiceImpl implements TripService {

    private final TripRepository tripRepository;

    @Override
    public Trip findCurrentTripByDroneOrReturnNew(final Drone drone) {

        return tripRepository.findByDroneAndStatus(drone, TripStatus.LOADING).orElseThrow(
                () -> new RuntimeBusinessException(null, ErrorCode.NO_CURRENT_TRIP_FOR_DRONE));
    }

    @Override
    public Trip save(final Trip currentTrip) {

        return tripRepository.save(currentTrip);
    }

}
