package com.musala.soft.drones.validation;

import com.musala.soft.drones.exception.ErrorCode;
import com.musala.soft.drones.exception.RuntimeBusinessException;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class RegisterDroneRequestValidator implements BusinessValidator<RegisterDroneRequest> {

    @Override
    public void doCheck(final RegisterDroneRequest req) throws RuntimeBusinessException {

        if (ObjectUtils.isEmpty(req.getModel()))
            throw new RuntimeBusinessException(null, ErrorCode.MODEL_NOT_PROVIDED);

        if (ObjectUtils.isEmpty(req.getWeightLimit()))
            throw new RuntimeBusinessException(null, ErrorCode.WEIGHT_LIMIT_NOT_PROVIDED);

        if (req.getWeightLimit() < 0 || req.getWeightLimit() > 500)
            throw new RuntimeBusinessException(null, ErrorCode.WEIGHT_LIMIT_SHOULD_BE_0_TO_500);

        if (ObjectUtils.isEmpty(req.getSerialNumber()))
            throw new RuntimeBusinessException(null, ErrorCode.SERIAL_NUMBER_NOT_PROVIDED);

        if (req.getSerialNumber().length() > 100)
            throw new RuntimeBusinessException(null, ErrorCode.SERIAL_NUMBER_MAX_100);

    }

}
