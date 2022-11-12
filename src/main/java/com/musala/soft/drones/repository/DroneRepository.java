package com.musala.soft.drones.repository;

import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.enums.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DroneRepository extends JpaRepository<Drone, Long> {

    List<Drone> findByStateIn(List<State> states);

    Optional<Drone> findBySerialNumber(String serialNumber);

}
