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

        RegisterDroneRequest requestWeightLimitLessZero = InputProvider.validRegisterDroneRequest();
        requestWeightLimitLessZero.setWeightLimit(-1D);

        RegisterDroneRequest requestWeightLimitMore500= InputProvider.validRegisterDroneRequest();
        requestWeightLimitMore500.setWeightLimit(501D);

        RegisterDroneRequest requestMissingModel = InputProvider.validRegisterDroneRequest();
        requestMissingModel.setModel(null);

        RegisterDroneRequest requestMissingSerialNumber = InputProvider.validRegisterDroneRequest();
        requestMissingSerialNumber.setSerialNumber(null);

        RegisterDroneRequest requestSerialNumberMoreThan100 = InputProvider.validRegisterDroneRequest();
        requestSerialNumberMoreThan100.setSerialNumber("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901");

        return Stream.of(
                Arguments.of(requestMissingWeightLimit, ErrorCode.WEIGHT_LIMIT_NOT_PROVIDED),
                Arguments.of(requestWeightLimitLessZero, ErrorCode.WEIGHT_LIMIT_SHOULD_BE_0_TO_500),
                Arguments.of(requestWeightLimitMore500, ErrorCode.WEIGHT_LIMIT_SHOULD_BE_0_TO_500),
                Arguments.of(requestMissingModel, ErrorCode.MODEL_NOT_PROVIDED),
                Arguments.of(requestMissingSerialNumber, ErrorCode.SERIAL_NUMBER_NOT_PROVIDED),
                Arguments.of(requestSerialNumberMoreThan100, ErrorCode.SERIAL_NUMBER_MAX_100)

        );
    }

}
