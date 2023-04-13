package com.github.maciejiwan.investmens_tracking.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Screening {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private Date startDate;
    private Date endDate;

    @ManyToOne
    private Hall hall;

    @OneToMany(mappedBy = "screening")
    private List<Ticket> tickets;

    @OneToOne
    private Movie movie;

    private int basicPrice;



}
