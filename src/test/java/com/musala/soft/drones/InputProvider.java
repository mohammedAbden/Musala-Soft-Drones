package com.musala.soft.drones;

import com.musala.soft.drones.enums.Model;
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

}
