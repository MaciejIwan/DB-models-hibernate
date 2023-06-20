package com.github.maciejiwan.investmens_tracking.repository;

import com.github.maciejiwan.investmens_tracking.entity.ConferenceParticipant;
import com.github.maciejiwan.investmens_tracking.entity.ConferenceRoom;
import com.github.maciejiwan.investmens_tracking.entity.Presentation;
import com.github.maciejiwan.investmens_tracking.enums.Country;
import com.github.maciejiwan.investmens_tracking.enums.Role;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest
@Transactional
class ConferenceParticipantRepositoryTest {

    @Autowired
    private ConferenceParticipantRepository participantRepository;

    @Autowired
    private ConferenceRoomRepository conferenceRoomRepository;

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
        assertEquals(Role.SCIENTIST, scientists.get(0).getRole());
    }

    @Test
    public void testFindAllParticipants() {
        ConferenceParticipant participant1 = createParticipant("John Doe", Role.SCIENTIST, Country.CHINA);
        ConferenceParticipant participant2 = createParticipant("Jane Smith", Role.SCIENTIST, Country.FRANCE);
        ConferenceParticipant participant3 = createParticipant("Adam Johnson", Role.ORGANIZER, Country.BRAZIL);

        participantRepository.save(participant1);
        participantRepository.save(participant2);
        participantRepository.save(participant3);

        List<ConferenceParticipant> participants = participantRepository.findAll();
        assertEquals(3, participants.size());
    }

    @Test
    public void testFindParticipantsByRole_1() {
        ConferenceParticipant scientist1 = createParticipant("John Doe", Role.SCIENTIST, Country.USA);
        ConferenceParticipant scientist2 = createParticipant("Jane Smith", Role.SCIENTIST, Country.CANADA);
        ConferenceParticipant student1 = createParticipant("Adam Johnson", Role.STUDENT, Country.USA);
        ConferenceParticipant organizer1 = createParticipant("Sarah Williams", Role.ORGANIZER, Country.AUSTRALIA);

        participantRepository.save(scientist1);
        participantRepository.save(scientist2);
        participantRepository.save(student1);
        participantRepository.save(organizer1);

        List<ConferenceParticipant> scientists = participantRepository.findScientists();
        assertEquals(2, scientists.size());

        List<ConferenceParticipant> students = participantRepository.findStudents();
        assertEquals(1, students.size());

        List<ConferenceParticipant> organizers = participantRepository.findOrganizers();
        assertEquals(1, organizers.size());
    }

    @Test
    public void testFindParticipantsByCountry_3() {
        ConferenceParticipant participant1 = createParticipant("John Doe", Role.SCIENTIST, Country.USA);
        ConferenceParticipant participant2 = createParticipant("Jane Smith", Role.STUDENT, Country.CANADA);
        ConferenceParticipant participant3 = createParticipant("Adam Johnson", Role.ORGANIZER, Country.USA);

        participantRepository.save(participant1);
        participantRepository.save(participant2);
        participantRepository.save(participant3);


        List<ConferenceParticipant> participantsFromUSA = participantRepository.findByCountry(Country.USA);
        assertEquals(2, participantsFromUSA.size());

        List<ConferenceParticipant> participantsFromCanada = participantRepository.findByCountry(Country.CANADA);
        assertEquals(1, participantsFromCanada.size());
    }

    @Test
    public void testFindAllTopics_4() {

        Presentation presentation1 = createPresentation( "Topic 1");
        Presentation presentation2 = createPresentation("Topic 2");
        Presentation presentation3 = createPresentation( "Topic 3");

        ConferenceParticipant participant1 = createParticipant("John Doe", Role.SCIENTIST, Country.USA);
        ConferenceParticipant participant2 = createParticipant("Jane Smith", Role.STUDENT, Country.CANADA);
        ConferenceParticipant participant3 = createParticipant("Adam Johnson", Role.ORGANIZER, Country.USA);

        participant1.addPresentation(presentation1);
        participant1.addPresentation(presentation2);
        participant2.addPresentation(presentation3);

        participantRepository.save(participant1);
        participantRepository.save(participant2);
        participantRepository.save(participant3);

        List<ConferenceParticipant> lis = participantRepository.findAll();
        List<String> topics = participantRepository.findAllTopics();
        assertEquals(3, topics.size());
    }

    @Test
    public void testFindParticipantWithMostPresentations_5() {
        ConferenceParticipant participant1 = createParticipant("John Doe", Role.SCIENTIST, Country.USA);
        ConferenceParticipant participant2 = createParticipant("Jane Smith", Role.STUDENT, Country.CANADA);

        Presentation presentation1 = createPresentation( "Topic 1");
        Presentation presentation2 = createPresentation( "Topic 2");
        Presentation presentation3 = createPresentation( "Topic 3");

        participant1.addPresentation(presentation1);
        participant1.addPresentation(presentation2);
        participant2.addPresentation(presentation3);

        participantRepository.save(participant1);
        participantRepository.save(participant2);

        List<ConferenceParticipant> participants = participantRepository.findAll();
        ConferenceParticipant participantWithMostPresentations = participantRepository.findParticipantWithMostPresentations();

        assertEquals(participant1.getId(), participantWithMostPresentations.getId());
        assertEquals(2, participantWithMostPresentations.getPresentations().size());
    }

    @Test
    public void testCountPresentationsByRoom_6() {
        ConferenceRoom room1 = createConferenceRoom("Room 1");
        ConferenceRoom room2 = createConferenceRoom("Room 2");

        Presentation presentation1 = createPresentation("Topic 1");
        Presentation presentation2 = createPresentation("Topic 2");
        Presentation presentation3 = createPresentation("Topic 3");

        room1.getPresentations().add(presentation1);
        room1.getPresentations().add(presentation2);
        room2.getPresentations().add(presentation3);

        conferenceRoomRepository.save(room1);
        conferenceRoomRepository.save(room2);

        List<Object[]> presentationsByRoom = participantRepository.countPresentationsByRoom();
        assertEquals(2, presentationsByRoom.size());
    }

    private ConferenceParticipant createParticipant(String name, Role role, Country country) {
        ConferenceParticipant participant = new ConferenceParticipant();
        participant.setName(name);
        participant.setRole(role);
        participant.setCountry(country);
        return participant;
    }

    private Presentation createPresentation(String topic) {
        Presentation presentation = new Presentation();
        presentation.setTopic(topic);
        return presentation;
    }

    private ConferenceRoom createConferenceRoom(String name) {
        ConferenceRoom room = new ConferenceRoom();
        room.setName(name);
        return room;
    }


}