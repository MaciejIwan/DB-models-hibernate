package com.github.maciejiwan.investmens_tracking.repository;

import com.github.maciejiwan.investmens_tracking.entity.ConferenceParticipant;
import com.github.maciejiwan.investmens_tracking.enums.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConferenceParticipantRepository extends JpaRepository<ConferenceParticipant, Long> {
    List<ConferenceParticipant> findAll();

    @Query("SELECT cp FROM ConferenceParticipant cp WHERE cp.role = 'SCIENTIST'")
    List<ConferenceParticipant> findScientists();

    @Query("SELECT cp FROM ConferenceParticipant cp WHERE cp.role = 'STUDENT'")
    List<ConferenceParticipant> findStudents();

    @Query("SELECT cp FROM ConferenceParticipant cp WHERE cp.role = 'ORGANIZER'")
    List<ConferenceParticipant> findOrganizers();

    @Query("SELECT cp FROM ConferenceParticipant cp WHERE cp.country = :country")
    List<ConferenceParticipant> findByCountry(Country country);

    @Query("SELECT DISTINCT p.topic FROM Presentation p")
    List<String> findAllTopics();

    @Query("SELECT cp FROM ConferenceParticipant cp JOIN cp.presentations p GROUP BY cp.id ORDER BY COUNT(p) DESC LIMIT 1")
    ConferenceParticipant findParticipantWithMostPresentations();

    @Query("SELECT cr.name, COUNT(p) FROM ConferenceRoom cr LEFT JOIN cr.presentations p GROUP BY cr.name")
    List<Object[]> countPresentationsByRoom();


}
