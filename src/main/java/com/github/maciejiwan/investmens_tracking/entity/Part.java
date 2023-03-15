package com.github.maciejiwan.investmens_tracking.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Part {

    @Id
    @Column(name = "part_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long price;
    private String title;

    @ManyToMany(mappedBy = "parts")
    private List<Car> models = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
