package com.musala.soft.drones.service;

import com.musala.soft.drones.dto.DroneBatteryDTO;
import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.enums.State;
import com.musala.soft.drones.exception.ErrorCode;
import com.musala.soft.drones.exception.RuntimeBusinessException;
import com.musala.soft.drones.mapper.DroneMapper;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import com.musala.soft.drones.repository.DroneRepository;
import com.musala.soft.drones.validation.BusinessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    private final BusinessValidator<RegisterDroneRequest> validator;

    private final DroneRepository droneRepository;

    private final DroneMapper droneMapper;

    @Override
    public DroneDTO registerDrone(final RegisterDroneRequest registerDroneRequest) {

        validator.doCheck(registerDroneRequest);
        droneRepository.findBySerialNumber(registerDroneRequest.getSerialNumber()).
                ifPresent(drone -> {
                    throw new RuntimeBusinessException(null, ErrorCode.DRONE_SERIAL_NUMBER_EXIST);
                });

        Drone drone = droneMapper.mapRegisterDroneRequestToEntity(registerDroneRequest);
        final Drone saveDrone = droneRepository.save(drone);
        return droneMapper.mapToDTO(saveDrone);
    }

    @Override
    public Collection<DroneDTO> getAvailableDrone() {

        return droneRepository.findByStateIn(List.of(State.IDLE, State.LOADING)).stream().map(droneMapper::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public DroneBatteryDTO getDroneBatterLevel(final Long id) {

        Drone drone = findById(id);
        return droneMapper.mapToDroneBatteryDTO(drone);
    }

    @Override
    public Drone findById(final Long id) {

        return droneRepository.findById(id)
                .orElseThrow(() -> new RuntimeBusinessException(null, ErrorCode.DRONE_NOT_EXIST));
    }

}
