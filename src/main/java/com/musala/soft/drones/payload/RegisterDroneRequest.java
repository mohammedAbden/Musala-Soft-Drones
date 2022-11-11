package com.musala.soft.drones.payload;

import com.musala.soft.drones.enums.Model;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterDroneRequest {

    private String serialNumber;

    private Model model;

    private Double weightLimit;

}
