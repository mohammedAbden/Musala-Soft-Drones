package com.musala.soft.drones.repository;

import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.entity.Trip;
import com.musala.soft.drones.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TripRepository extends JpaRepository<Trip, Long> {


    Optional<Trip> findByDroneAndStatusIn(Drone drone, List<TripStatus> loading);

}
