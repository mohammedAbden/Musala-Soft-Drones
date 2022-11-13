package com.musala.soft.drones.service;

import com.musala.soft.drones.dto.TripDTO;
import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.entity.Medication;
import com.musala.soft.drones.entity.Trip;
import com.musala.soft.drones.enums.State;
import com.musala.soft.drones.enums.TripStatus;
import com.musala.soft.drones.exception.ErrorCode;
import com.musala.soft.drones.exception.RuntimeBusinessException;
import com.musala.soft.drones.mapper.TripMapper;
import com.musala.soft.drones.payload.LoadMedicationsRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DroneLoadMedicationFacadeImpl implements DroneLoadMedicationFacade {

    private final List<State> validState = List.of(State.IDLE, State.LOADING);

    //TODO Configured
    private final Double batteryLevelThreshold = 25D;

    private final DroneService droneService;

    private final MedicationService medicationService;

    private final TripService tripService;

    private final TripMapper tripMapper;

    @Override
    @Transactional
    public TripDTO loadDroneWithMedication(final Long id, final LoadMedicationsRequest loadMedicationsRequest) {

        Drone drone = droneService.findById(id);

        validateDroneStateValidForLoading(drone);

        validateDroneBatteryLevel(drone);

        List<Medication> validMedication = validateMedicationExistAndNotLoadedBefore(loadMedicationsRequest);

        Double medicationWeight = validMedication.stream().map(Medication::getWeight).reduce(0D, Double::sum);

        Trip currentTrip = getCurrentTrip(drone);

        Double loadedMedicationsWeight = currentTrip.getTripItems().stream().map(Medication::getWeight).reduce(0D, Double::sum);

        validateWeight(medicationWeight, loadedMedicationsWeight, drone);

        currentTrip.addMedicationItems(new HashSet<>(validMedication));

        return tripMapper.mapToDto(tripService.save(currentTrip));
    }

    private void validateWeight(final Double medicationWeight, final Double loadedMedicationsWeight, final Drone drone) {

        if (medicationWeight + loadedMedicationsWeight > drone.getWeightLimit())
            throw new RuntimeBusinessException(null, ErrorCode.MEDICATION_WEIGHT_MORE_THAN_DRONE_LIMIT);

    }

    private Trip getCurrentTrip(final Drone drone) {

        if (State.IDLE.equals(drone.getState())) {
            Trip newTrip = new Trip();
            newTrip.setDrone(drone);
            newTrip.setStatus(TripStatus.LOADING);
            drone.setState(State.LOADING);
            return newTrip;
        }
        return tripService.findCurrentTripByDroneOrReturnNew(drone);

    }

    private List<Medication> validateMedicationExistAndNotLoadedBefore(final LoadMedicationsRequest loadMedicationsRequest) {

        List<Medication> medications = medicationService.findValidMedicationForLoading(loadMedicationsRequest.getMedicationIds());
        if (medications.size() != loadMedicationsRequest.getMedicationIds().size())
            throw new RuntimeBusinessException(null, ErrorCode.MEDICATION_LOADED_BEFORE);

        return medications;
    }

    private void validateDroneBatteryLevel(final Drone drone) {

        if (drone.getBatteryCapacity() < batteryLevelThreshold)
            throw new RuntimeBusinessException(null, ErrorCode.DRONE_BATTERY_LEVEL_LESS_THAN_THRESHOLD);

    }

    private void validateDroneStateValidForLoading(final Drone drone) {

        if (!validState.contains(drone.getState()))
            throw new RuntimeBusinessException(null, ErrorCode.DRONE_NOT_IN_VALID_STATE_TO_LOAD);

    }

}
