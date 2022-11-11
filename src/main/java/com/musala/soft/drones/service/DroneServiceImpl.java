package com.musala.soft.drones.service;

import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.mapper.DroneMapper;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import com.musala.soft.drones.repository.DroneRepository;
import com.musala.soft.drones.validation.BusinessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final BusinessValidator<RegisterDroneRequest> validator;

    private final DroneRepository droneRepository;

    private final DroneMapper droneMapper;

    @Override
    public DroneDTO registerDrone(final RegisterDroneRequest registerDroneRequest) {

        validator.doCheck(registerDroneRequest);

        Drone drone = droneMapper.mapRegisterDroneRequestToEntity(registerDroneRequest);
        final Drone saveDrone = droneRepository.save(drone);
        return droneMapper.mapToDTO(saveDrone);
    }

}
