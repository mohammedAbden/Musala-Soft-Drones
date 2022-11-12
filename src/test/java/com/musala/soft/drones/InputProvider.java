package com.musala.soft.drones;

import com.musala.soft.drones.enums.Model;
import com.musala.soft.drones.payload.AddingMedicationRequest;
import com.musala.soft.drones.payload.RegisterDroneRequest;

import java.util.UUID;

public class InputProvider {

    public static RegisterDroneRequest validRegisterDroneRequest() {

        RegisterDroneRequest request = new RegisterDroneRequest();
        request.setModel(Model.LIGHT_WEIGHT);
        request.setSerialNumber(UUID.randomUUID().toString());
        request.setWeightLimit(500D);
        return request;
    }

    public static AddingMedicationRequest validAddingMedicationRequest() {

        AddingMedicationRequest request = new AddingMedicationRequest();
        request.setName("name1");
        request.setWeight(20D);
        request.setCode("ABC_123_CD");
        request.setImage("image1");
        return request;
    }

}
