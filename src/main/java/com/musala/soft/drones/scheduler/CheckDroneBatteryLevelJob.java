package com.musala.soft.drones.scheduler;

import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.entity.DroneCheckBatteryHistory;
import com.musala.soft.drones.repository.DroneCheckBatteryHistoryRepository;
import com.musala.soft.drones.service.DroneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckDroneBatteryLevelJob {

    private final DroneCheckBatteryHistoryRepository droneCheckBatteryHistoryRepository;

    private final DroneService droneService;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void scheduleCheckBatteryHistoryTask() {

        log.info("scheduleCheckBatteryHistoryTask at {}", LocalDateTime.now());

        List<Drone> drones = droneService.getAllDroneEntities();
        final List<DroneCheckBatteryHistory> logs = drones.stream().map(this::mapDroneToDroneCheckBatteryHistory).collect(Collectors.toList());
        droneCheckBatteryHistoryRepository.saveAll(logs);

    }


    private DroneCheckBatteryHistory mapDroneToDroneCheckBatteryHistory(Drone drone) {

        final DroneCheckBatteryHistory droneCheckBatteryHistory = new DroneCheckBatteryHistory();
        BeanUtils.copyProperties(drone, droneCheckBatteryHistory);
        droneCheckBatteryHistory.setDroneId(drone.getId());
        droneCheckBatteryHistory.setId(null);
        droneCheckBatteryHistory.setLogTime(LocalDateTime.now());
        return droneCheckBatteryHistory;
    }

}
