package com.musala.soft.drones.argument;

import com.musala.soft.drones.InputProvider;
import com.musala.soft.drones.exception.ErrorCode;
import com.musala.soft.drones.payload.AddingMedicationRequest;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;

import java.util.stream.Stream;

public class AddingMedicationRequestInvalidArgumentProvider implements ArgumentsProvider {

    @Override
    public Stream<? extends Arguments> provideArguments(final ExtensionContext extensionContext) {

        AddingMedicationRequest requestMissingName = InputProvider.validAddingMedicationRequest();
        requestMissingName.setName(null);

        AddingMedicationRequest requestInvalidRegexName = InputProvider.validAddingMedicationRequest();
        requestInvalidRegexName.setName("name.");

        AddingMedicationRequest requestMissingCode = InputProvider.validAddingMedicationRequest();
        requestMissingCode.setCode(null);

        AddingMedicationRequest requestInvalidRegexCode = InputProvider.validAddingMedicationRequest();
        requestInvalidRegexCode.setCode("a");

        AddingMedicationRequest requestMissingWeight = InputProvider.validAddingMedicationRequest();
        requestMissingWeight.setWeight(null);

        AddingMedicationRequest requestMissingImage = InputProvider.validAddingMedicationRequest();
        requestMissingImage.setImage(null);

        return Stream.of(
                Arguments.of(requestMissingName, ErrorCode.MEDICATION_NAME_NOT_PROVIDED),
                Arguments.of(requestInvalidRegexName, ErrorCode.MEDICATION_NAME_NOT_MATCH_CRITERIA),
                Arguments.of(requestMissingCode, ErrorCode.MEDICATION_CODE_NOT_PROVIDED),
                Arguments.of(requestInvalidRegexCode, ErrorCode.MEDICATION_CODE_NOT_MATCH_CRITERIA),
                Arguments.of(requestMissingWeight, ErrorCode.MEDICATION_WEIGHT_NOT_PROVIDED),
                Arguments.of(requestMissingImage, ErrorCode.MEDICATION_IMAGE_NOT_PROVIDED)
        );
    }

}
