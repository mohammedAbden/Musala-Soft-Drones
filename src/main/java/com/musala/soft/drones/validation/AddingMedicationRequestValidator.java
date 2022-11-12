package com.musala.soft.drones.validation;

import com.musala.soft.drones.exception.ErrorCode;
import com.musala.soft.drones.exception.RuntimeBusinessException;
import com.musala.soft.drones.payload.AddingMedicationRequest;
import com.musala.soft.drones.util.RegexUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class AddingMedicationRequestValidator implements BusinessValidator<AddingMedicationRequest> {

    @Override
    public void doCheck(final AddingMedicationRequest req) throws RuntimeBusinessException {

        if (ObjectUtils.isEmpty(req.getName()))
            throw new RuntimeBusinessException(null, ErrorCode.MEDICATION_NAME_NOT_PROVIDED);

        if (!RegexUtils.validateRegex(req.getName(), RegexUtils.LETTERS_NUMBERS_UNDERSCORE_MINUS))
            throw new RuntimeBusinessException(null, ErrorCode.MEDICATION_NAME_NOT_MATCH_CRITERIA);

        if (ObjectUtils.isEmpty(req.getCode()))
            throw new RuntimeBusinessException(null, ErrorCode.MEDICATION_CODE_NOT_PROVIDED);

        if (!RegexUtils.validateRegex(req.getCode(), RegexUtils.UPPER_LETTERS_NUMBERS_UNDERSCORE))
            throw new RuntimeBusinessException(null, ErrorCode.MEDICATION_CODE_NOT_MATCH_CRITERIA);

        if (ObjectUtils.isEmpty(req.getWeight()))
            throw new RuntimeBusinessException(null, ErrorCode.MEDICATION_WEIGHT_NOT_PROVIDED);

        if (ObjectUtils.isEmpty(req.getImage()))
            throw new RuntimeBusinessException(null, ErrorCode.MEDICATION_IMAGE_NOT_PROVIDED);

    }

}
