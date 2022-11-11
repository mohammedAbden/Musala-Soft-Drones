package com.musala.soft.drones.validation;

import com.musala.soft.drones.exception.RuntimeBusinessException;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import org.springframework.stereotype.Component;

@Component
public class RegisterDroneRequestValidator implements BusinessValidator<RegisterDroneRequest> {

    @Override
    public void doCheck(final RegisterDroneRequest object) throws RuntimeBusinessException {

    }

}
