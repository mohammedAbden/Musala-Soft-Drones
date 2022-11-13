package com.musala.soft.drones.mapper;

import com.musala.soft.drones.dto.DroneBatteryDTO;
import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.enums.State;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class DroneMapper {

    public Drone mapRegisterDroneRequestToEntity(RegisterDroneRequest request) {

        final Drone drone = new Drone();
        BeanUtils.copyProperties(request, drone);
        drone.setState(State.IDLE);
        drone.setBatteryCapacity(100D);
        return drone;
    }

    public DroneDTO mapToDTO(Drone drone) {

        final DroneDTO droneDTO = new DroneDTO();
        BeanUtils.copyProperties(drone, droneDTO);
        return droneDTO;
    }

    public DroneBatteryDTO mapToDroneBatteryDTO(Drone drone) {

        final DroneBatteryDTO droneDTO = new DroneBatteryDTO();
        BeanUtils.copyProperties(drone, droneDTO);
        return droneDTO;
    }

}
