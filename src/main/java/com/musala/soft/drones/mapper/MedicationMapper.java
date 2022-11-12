package com.musala.soft.drones.mapper;

import com.musala.soft.drones.dto.MedicationDTO;
import com.musala.soft.drones.entity.Medication;
import com.musala.soft.drones.payload.AddingMedicationRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class MedicationMapper {

    public Medication mapAddingMedicationRequestToEntity(AddingMedicationRequest request) {

        final Medication entity = new Medication();
        BeanUtils.copyProperties(request, entity);
        return entity;
    }

    public MedicationDTO mapToDTO(Medication entity) {

        final MedicationDTO medicationDTO = new MedicationDTO();
        BeanUtils.copyProperties(entity, medicationDTO);
        return medicationDTO;
    }

}
