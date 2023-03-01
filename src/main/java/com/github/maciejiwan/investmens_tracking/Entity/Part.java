package com.github.maciejiwan.investmens_tracking.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Part {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String sector;
    private int floor;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "part", cascade = CascadeType.ALL)
    private List<Car> supportedCars;
    @OneToOne
    private Category category;
}
