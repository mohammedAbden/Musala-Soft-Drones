package com.musala.soft.drones.dto;

import com.musala.soft.drones.enums.Model;
import com.musala.soft.drones.enums.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DroneDTO {

    private Long id;

    private String serialNumber;

    private Model model;

    private Double weightLimit;

    private Double batteryCapacity;

    private State state;

}
