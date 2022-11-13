package com.musala.soft.drones.bootstrap;

import com.musala.soft.drones.entity.Drone;
import com.musala.soft.drones.enums.Model;
import com.musala.soft.drones.enums.State;
import com.musala.soft.drones.repository.DroneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class LoadDroneStartupRunner implements CommandLineRunner {

    private final DroneRepository droneRepository;

    @Override
    public void run(final String... args) throws Exception {

        droneRepository.saveAll(List.of(droneFactoryMethod("S1", State.IDLE, Model.HEAVY_WEIGHT, 500D, 100D),
                droneFactoryMethod("S2", State.IDLE, Model.CRUISER_WEIGHT, 400D, 100D),
                droneFactoryMethod("S3", State.IDLE, Model.MIDDLE_WEIGHT, 350D, 100D),
                droneFactoryMethod("S4", State.IDLE, Model.LIGHT_WEIGHT, 200D, 100D),
                droneFactoryMethod("S5", State.IDLE, Model.HEAVY_WEIGHT, 500D, 100D),
                droneFactoryMethod("S6", State.IDLE, Model.CRUISER_WEIGHT, 450D, 100D),
                droneFactoryMethod("S7", State.IDLE, Model.MIDDLE_WEIGHT, 300D, 100D),
                droneFactoryMethod("S8", State.IDLE, Model.LIGHT_WEIGHT, 200d, 100D),
                droneFactoryMethod("S9", State.IDLE, Model.HEAVY_WEIGHT, 500D, 100D),
                droneFactoryMethod("S10", State.IDLE, Model.MIDDLE_WEIGHT, 250D, 100D)));
    }

    private Drone droneFactoryMethod(String serial, State state, Model model, Double weightLimit, Double batteryCapacity) {

        Drone d1 = new Drone();
        d1.setSerialNumber(serial);
        d1.setState(state);
        d1.setModel(model);
        d1.setWeightLimit(weightLimit);
        d1.setBatteryCapacity(batteryCapacity);
        return d1;
    }

}
