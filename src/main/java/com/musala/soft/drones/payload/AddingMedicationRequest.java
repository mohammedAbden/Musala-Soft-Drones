package com.musala.soft.drones.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddingMedicationRequest {

    private String name;

    private Double weight;

    private String code;

    private String image;

}
