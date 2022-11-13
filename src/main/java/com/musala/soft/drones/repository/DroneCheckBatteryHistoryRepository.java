package com.musala.soft.drones.repository;

import com.musala.soft.drones.entity.DroneCheckBatteryHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DroneCheckBatteryHistoryRepository extends JpaRepository<DroneCheckBatteryHistory, Long> {


}
