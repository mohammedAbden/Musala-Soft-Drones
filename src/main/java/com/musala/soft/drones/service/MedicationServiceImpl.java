package com.musala.soft.drones.service;

import com.musala.soft.drones.dto.MedicationDTO;
import com.musala.soft.drones.entity.Medication;
import com.musala.soft.drones.mapper.MedicationMapper;
import com.musala.soft.drones.payload.AddingMedicationRequest;
import com.musala.soft.drones.repository.MedicationRepository;
import com.musala.soft.drones.validation.BusinessValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicationServiceImpl implements MedicationService {

    private final BusinessValidator<AddingMedicationRequest> validator;

    private final MedicationRepository medicationRepository;

    private final MedicationMapper medicationMapper;


    @Override
    public MedicationDTO addMedication(final AddingMedicationRequest request) {

        validator.doCheck(request);

        Medication drone = medicationMapper.mapAddingMedicationRequestToEntity(request);
        final Medication saveEntity = medicationRepository.save(drone);
        return medicationMapper.mapToDTO(saveEntity);
    }

}
