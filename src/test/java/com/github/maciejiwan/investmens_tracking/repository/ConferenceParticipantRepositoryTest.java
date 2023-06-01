package com.github.maciejiwan.investmens_tracking.repository;

import com.github.maciejiwan.investmens_tracking.entity.ConferenceParticipant;
import com.github.maciejiwan.investmens_tracking.enums.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@SpringJUnitConfig
class ConferenceParticipantRepositoryTest {

    @Autowired
    private ConferenceParticipantRepository participantRepository;

    @BeforeEach
    void setUp() {
    }


    @Test
    public void testFindScientists() {
        ConferenceParticipant scientist1 = new ConferenceParticipant();
        scientist1.setName("Scientist 1");
        scientist1.setRole(Role.SCIENTIST);
        participantRepository.save(scientist1);

        ConferenceParticipant scientist2 = new ConferenceParticipant();
        scientist2.setName("Scientist 2");
        scientist2.setRole(Role.SCIENTIST);
        participantRepository.save(scientist2);

        ConferenceParticipant student1 = new ConferenceParticipant();
        student1.setName("Student 1");
        student1.setRole(Role.STUDENT);
        participantRepository.save(student1);

        ConferenceParticipant organizer1 = new ConferenceParticipant();
        organizer1.setName("Organizer 1");
        organizer1.setRole(Role.ORGANIZER);
        participantRepository.save(organizer1);

        List<ConferenceParticipant> scientists = participantRepository.findScientists();
        assertEquals(2, scientists.size());
    }


}