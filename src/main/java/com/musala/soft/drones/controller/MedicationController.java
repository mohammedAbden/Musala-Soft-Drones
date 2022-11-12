package com.musala.soft.drones.controller;

import com.musala.soft.drones.api.ApiResponse;
import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.dto.MedicationDTO;
import com.musala.soft.drones.payload.AddingMedicationRequest;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import com.musala.soft.drones.service.MedicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/medication")
@RequiredArgsConstructor
public class MedicationController {


    private final MedicationService medicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<MedicationDTO> addMedication(@RequestBody AddingMedicationRequest addingMedicationRequest) {

        final long start = System.currentTimeMillis();
        MedicationDTO medicationDTO = medicationService.addMedication(addingMedicationRequest);
        return ApiResponse.created(medicationDTO, (System.currentTimeMillis() - start) + " ms");

    }
}
