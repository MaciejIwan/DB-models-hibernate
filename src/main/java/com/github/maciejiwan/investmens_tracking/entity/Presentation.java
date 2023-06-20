package com.github.maciejiwan.investmens_tracking.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;


@Entity
@Getter
@Setter
@Table(name = "Presentation")
public class Presentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "topic")
    private String topic;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private ConferenceRoom conferenceRoom;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "participant_id")
    private ConferenceParticipant presenter;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Presentation presentation = (Presentation) o;
        return getId() != null && Objects.equals(getId(), presentation.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}