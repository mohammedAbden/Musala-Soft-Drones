package com.musala.soft.drones.controller;

import com.musala.soft.drones.api.ApiResponse;
import com.musala.soft.drones.dto.DroneBatteryDTO;
import com.musala.soft.drones.dto.DroneDTO;
import com.musala.soft.drones.dto.TripDTO;
import com.musala.soft.drones.payload.LoadMedicationsRequest;
import com.musala.soft.drones.payload.RegisterDroneRequest;
import com.musala.soft.drones.service.DroneLoadMedicationFacade;
import com.musala.soft.drones.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("api/v1/drone")
@RequiredArgsConstructor
public class DroneController {

    private final DroneService droneService;

    private final DroneLoadMedicationFacade droneLoadMedicationFacade;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<DroneDTO> registerDrone(@RequestBody RegisterDroneRequest registerDroneRequest) {

        final long start = System.currentTimeMillis();
        DroneDTO drownDto = droneService.registerDrone(registerDroneRequest);
        return ApiResponse.created(drownDto, (System.currentTimeMillis() - start) + " ms");

    }

    @GetMapping("available")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Collection<DroneDTO>> getAvailableDrones() {

        final long start = System.currentTimeMillis();
        Collection<DroneDTO> availableDrones = droneService.getAvailableDrone();
        return ApiResponse.ok(availableDrones, (System.currentTimeMillis() - start) + " ms");

    }

    @GetMapping("{id}/battery-level")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<DroneBatteryDTO> getDroneBatterLevel(@PathVariable("id") Long id) {

        final long start = System.currentTimeMillis();
        DroneBatteryDTO batteryLevel = droneService.getDroneBatterLevel(id);
        return ApiResponse.ok(batteryLevel, (System.currentTimeMillis() - start) + " ms");

    }

    @PutMapping("{id}/medications")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<TripDTO> loadDroneWithMedication(@PathVariable("id") Long id,
                                                                @RequestBody LoadMedicationsRequest loadMedicationsRequest) {

        final long start = System.currentTimeMillis();
        TripDTO trip = droneLoadMedicationFacade.loadDroneWithMedication(id, loadMedicationsRequest);
        return ApiResponse.ok(trip, (System.currentTimeMillis() - start) + " ms");

    }

}
