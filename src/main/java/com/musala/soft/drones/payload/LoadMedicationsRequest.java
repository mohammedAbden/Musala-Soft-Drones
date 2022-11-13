package com.musala.soft.drones.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class LoadMedicationsRequest {

    private List<Long> medicationIds;

}
