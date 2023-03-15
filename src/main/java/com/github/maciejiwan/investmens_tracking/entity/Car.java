package com.github.maciejiwan.investmens_tracking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Car {
    @Id
    @Column(name = "car_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String brand;
    private String model;
    private int releaseYear;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "model_part",
            joinColumns = {@JoinColumn(name = "model_id")},
            inverseJoinColumns = {@JoinColumn(name = "part_id")}
    )
    private List<Part> parts = new ArrayList<>();

}
