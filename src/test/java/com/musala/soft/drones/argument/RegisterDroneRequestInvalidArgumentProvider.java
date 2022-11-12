package com.musala.soft.drones.argument;

import com.musala.soft.drones.InputProvider;
import com.musala.soft.drones.exception.ErrorCode;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class RegisterDroneRequestInvalidArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {

        RegisterDroneRequest requestMissingWeightLimit = InputProvider.validRegisterDroneRequest();
        requestMissingWeightLimit.setWeightLimit(null);

        RegisterDroneRequest requestMissingModel = InputProvider.validRegisterDroneRequest();
        requestMissingModel.setModel(null);

        RegisterDroneRequest requestMissingSerialNumber = InputProvider.validRegisterDroneRequest();
        requestMissingSerialNumber.setSerialNumber(null);

        return Stream.of(
                Arguments.of(requestMissingWeightLimit, ErrorCode.WEIGHT_LIMIT_NOT_PROVIDED),
                Arguments.of(requestMissingModel, ErrorCode.MODEL_NOT_PROVIDED),
                Arguments.of(requestMissingSerialNumber, ErrorCode.SERIAL_NUMBER_NOT_PROVIDED)
        );
    }

}
