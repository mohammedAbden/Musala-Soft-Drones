package com.musala.soft.drones.service;

import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.entity.Medication;
import com.musala.soft.drones.entity.Trip;
import com.musala.soft.drones.enums.Model;
import com.musala.soft.drones.enums.State;
import com.musala.soft.drones.enums.TripStatus;
import com.musala.soft.drones.repository.DroneRepository;
import com.musala.soft.drones.repository.MedicationRepository;
import com.musala.soft.drones.repository.TripRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MedicationServiceImplTest {

    @Autowired
    MedicationRepository medicationRepository;

    @Autowired
    DroneRepository droneRepository;

    @Autowired
    TripRepository tripRepository;

    @Autowired
    MedicationService medicationService;

    @Test
    void findValidMedicationForLoading() {
        //Given

        Drone d1 = new Drone();
        d1.setState(State.IDLE);
        d1.setBatteryCapacity(100D);
        d1.setSerialNumber("s1");
        d1.setModel(Model.LIGHT_WEIGHT);
        d1.setWeightLimit(500D);

        Drone drone = droneRepository.save(d1);

        Trip trip =new Trip();
        trip.setDrone(drone);
        trip.setStatus(TripStatus.LOADING);

        trip =tripRepository.save(trip);

        Medication medicationWithTrip = getMedication();
        medicationWithTrip.setTrip(trip);

        Medication med = medicationRepository.save(medicationWithTrip);
        Medication med2 = medicationRepository.save(getMedication());

        final List<Medication> validMedicationForLoading = medicationService.findValidMedicationForLoading(List.of(med.getId(), med2.getId()));

        assertAll(() -> {

            assertNotNull(validMedicationForLoading);
            assertFalse(validMedicationForLoading.isEmpty());
            assertEquals(1,validMedicationForLoading.size());
            assertEquals(med2.getId(),validMedicationForLoading.get(0).getId());
            assertNull(med2.getTrip());
            assertNotNull(med.getTrip());
        });

    }

    private Medication getMedication() {

        Medication m = new Medication();
        m.setCode("CODE");
        m.setImage("image");
        m.setName("NAME");
        m.setWeight(10D);
        return m;
    }

}