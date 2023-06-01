package com.github.maciejiwan.investmens_tracking.entity;

import com.github.maciejiwan.investmens_tracking.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "ConferenceParticipant")
@Getter
@Setter
public class ConferenceParticipant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "conferenceRoom")
    private List<Presentation> presentations;

}