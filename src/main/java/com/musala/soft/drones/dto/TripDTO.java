package com.musala.soft.drones.dto;

import com.musala.soft.drones.enums.TripStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class TripDTO {

    private Long id;

    private DroneDTO drone;

    private TripStatus status;

    private Set<MedicationDTO> tripItems;

}
