package com.musala.soft.drones.entity;


import com.musala.soft.drones.enums.Model;
import com.musala.soft.drones.enums.State;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "drone")
public class Drone extends BaseEntity {

    @Column(name = "serial_number", unique = true, updatable = false, length = 100, nullable = false)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "model", nullable = false)
    private Model model;

    @Column(name = "weight_limit", nullable = false)
    private Double weightLimit;

    @Column(name = "battery_capacity", nullable = false)
    private Double batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

}
