package com.musala.soft.drones.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@Table(name = "medication")
public class Medication extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "weight", nullable = false)
    private Double weight;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "image", nullable = false)
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

}
