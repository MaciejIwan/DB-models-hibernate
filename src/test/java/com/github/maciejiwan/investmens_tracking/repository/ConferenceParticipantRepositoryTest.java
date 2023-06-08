package com.github.maciejiwan.investmens_tracking.repository;

import com.github.maciejiwan.investmens_tracking.entity.ConferenceParticipant;
import com.github.maciejiwan.investmens_tracking.entity.ConferenceRoom;
import com.github.maciejiwan.investmens_tracking.entity.Presentation;
import com.github.maciejiwan.investmens_tracking.entity.Topic;
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
    }

    @Test
    public void testFindAllParticipants() {
        ConferenceParticipant participant1 = createParticipant("John Doe", Role.SCIENTIST, "USA");
        ConferenceParticipant participant2 = createParticipant("Jane Smith", Role.SCIENTIST, "Canada");
        ConferenceParticipant participant3 = createParticipant("Adam Johnson", Role.ORGANIZER, "USA");

        participantRepository.save(participant1);
        participantRepository.save(participant2);
        participantRepository.save(participant3);

        List<ConferenceParticipant> participants = participantRepository.findAll();
        assertEquals(3, participants.size());
    }

    @Test
    public void testFindParticipantsByRole() {
        ConferenceParticipant scientist1 = createParticipant("John Doe", Role.SCIENTIST, "USA");
        ConferenceParticipant scientist2 = createParticipant("Jane Smith", Role.SCIENTIST, "Canada");
        ConferenceParticipant student1 = createParticipant("Adam Johnson", Role.STUDENT, "USA");
        ConferenceParticipant organizer1 = createParticipant("Sarah Williams", Role.ORGANIZER, "Australia");

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
    public void testFindParticipantsByCountry() {
        ConferenceParticipant participant1 = createParticipant("John Doe", Role.SCIENTIST, "USA");
        ConferenceParticipant participant2 = createParticipant("Jane Smith", Role.STUDENT, "Canada");
        ConferenceParticipant participant3 = createParticipant("Adam Johnson", Role.ORGANIZER, "USA");

        participantRepository.save(participant1);
        participantRepository.save(participant2);
        participantRepository.save(participant3);

        List<ConferenceParticipant> participantsFromUSA = participantRepository.findByCountry("USA");
        assertEquals(2, participantsFromUSA.size());

        List<ConferenceParticipant> participantsFromCanada = participantRepository.findByCountry("Canada");
        assertEquals(1, participantsFromCanada.size());
    }

    @Test
    public void testFindAllTopics() {
        Topic topic1 = createTopic("Topic 1");
        Topic topic2 = createTopic("Topic 2");
        Topic topic3 = createTopic("Topic 3");

        Presentation presentation1 = createPresentation( topic1);
        Presentation presentation2 = createPresentation(topic2);
        Presentation presentation3 = createPresentation( topic3);

        ConferenceParticipant participant1 = createParticipant("John Doe", Role.SCIENTIST, "USA");
        ConferenceParticipant participant2 = createParticipant("Jane Smith", Role.STUDENT, "Canada");
        ConferenceParticipant participant3 = createParticipant("Adam Johnson", Role.ORGANIZER, "USA");

        participant1.getPresentations().add(presentation1);
        participant1.getPresentations().add(presentation2);
        participant2.getPresentations().add(presentation3);

        participantRepository.save(participant1);
        participantRepository.save(participant2);
        participantRepository.save(participant3);

        List<Topic> topics = participantRepository.findAllTopics();
        assertEquals(3, topics.size());
    }

    @Test
    public void testFindParticipantWithMostPresentations() {
        ConferenceParticipant participant1 = createParticipant("John Doe", Role.SCIENTIST, "USA");
        ConferenceParticipant participant2 = createParticipant("Jane Smith", Role.STUDENT, "Canada");

        Presentation presentation1 = createPresentation( null);
        Presentation presentation2 = createPresentation( null);
        Presentation presentation3 = createPresentation( null);

        participant1.getPresentations().add(presentation1);
        participant1.getPresentations().add(presentation2);
        participant2.getPresentations().add(presentation3);

        participantRepository.save(participant1);
        participantRepository.save(participant2);

        ConferenceParticipant participantWithMostPresentations = participantRepository.findParticipantWithMostPresentations();
        assertEquals(participant1.getId(), participantWithMostPresentations.getId());
    }

    @Test
    public void testCountPresentationsByRoom() {
        ConferenceRoom room1 = createConferenceRoom("Room 1");
        ConferenceRoom room2 = createConferenceRoom("Room 2");

        Presentation presentation1 = createPresentation(null);
        Presentation presentation2 = createPresentation(null);
        Presentation presentation3 = createPresentation(null);

        room1.getPresentations().add(presentation1);
        room1.getPresentations().add(presentation2);
        room2.getPresentations().add(presentation3);

        conferenceRoomRepository.save(room1);
        conferenceRoomRepository.save(room2);

        List<Object[]> presentationsByRoom = participantRepository.countPresentationsByRoom();
        assertEquals(2, presentationsByRoom.size());
    }

    private ConferenceParticipant createParticipant(String name, Role role, String country) {
        ConferenceParticipant participant = new ConferenceParticipant();
        participant.setName(name);
        participant.setRole(role);
        participant.setCountry(country);
        return participant;
    }

    private Presentation createPresentation(Topic topic) {
        Presentation presentation = new Presentation();
        presentation.setTopic(topic);
        return presentation;
    }

    private Topic createTopic(String name) {
        Topic topic = new Topic();
        topic.setTitle(name);
        return topic;
    }

    private ConferenceRoom createConferenceRoom(String name) {
        ConferenceRoom room = new ConferenceRoom();
        room.setName(name);
        return room;
    }


}