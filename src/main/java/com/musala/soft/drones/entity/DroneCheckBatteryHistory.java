package com.musala.soft.drones.entity;


import com.musala.soft.drones.enums.Model;
import com.musala.soft.drones.enums.State;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "drone_history")
public class DroneCheckBatteryHistory extends BaseEntity {

    @Column(name = "serial_number", updatable = false, length = 100, nullable = false)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "model", updatable = false, nullable = false)
    private Model model;

    @Column(name = "weight_limit", updatable = false, nullable = false)
    private Double weightLimit;

    @Column(name = "battery_capacity", updatable = false, nullable = false)
    private Double batteryCapacity;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", updatable = false, nullable = false)
    private State state;

    @Column(name = "drone_id", updatable = false, nullable = false)
    private Long droneId;

    @Column(name = "log_time", updatable = false, nullable = false)
    private LocalDateTime logTime;

}
