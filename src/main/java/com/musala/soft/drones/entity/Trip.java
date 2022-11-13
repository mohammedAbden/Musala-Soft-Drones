package com.musala.soft.drones.entity;

import com.musala.soft.drones.enums.TripStatus;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.util.ObjectUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "trip")
public class Trip extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "drown_id", nullable = false)
    private Drone drone;

    @Enumerated(EnumType.STRING)
    private TripStatus status;

    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "trip",cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    private Set<Medication> tripItems = new HashSet<>();

    public void addMedicationItems(Set<Medication> items) {

        if (ObjectUtils.isEmpty(tripItems))
            tripItems = new HashSet<>();
        tripItems.addAll(items);
        items.forEach(medication -> medication.setTrip(this));
    }

}
