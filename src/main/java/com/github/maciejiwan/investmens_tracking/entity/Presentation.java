package com.github.maciejiwan.investmens_tracking.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "Presentation")
public class Presentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private ConferenceRoom conferenceRoom;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private ConferenceParticipant presenter;
}