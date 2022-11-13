package com.musala.soft.drones.mapper;

import com.musala.soft.drones.dto.TripDTO;
import com.musala.soft.drones.entity.Trip;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class TripMapper {

    private final DroneMapper droneMapper;

    private final MedicationMapper medicationMapper;


    public TripDTO mapToDto(Trip entity) {

        final TripDTO dto = new TripDTO();
        BeanUtils.copyProperties(entity, dto);
        dto.setDrone(droneMapper.mapToDTO(entity.getDrone()));
        dto.setTripItems(entity.getTripItems().stream().map(medicationMapper::mapToDTO).collect(Collectors.toSet()));
        return dto;
    }

}
