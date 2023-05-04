package com.github.maciejiwan.investmens_tracking.repositories;

import com.github.maciejiwan.investmens_tracking.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT COUNT(t) FROM Ticket t WHERE t.screening.id = :id")
    long countTakenSeatsByScreeningId(@Param("id") long id);

    @Query("SELECT t FROM Ticket t JOIN t.user u JOIN t.screening s WHERE u.id = :userId AND s.startDate BETWEEN :startDate AND :endDate")
    List<Ticket> findTicketsByUserAndScreeningDateRange(@Param("userId") long userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

    @Query("SELECT COUNT(t) FROM Ticket t JOIN t.user u JOIN t.screening s WHERE u.id = :userId AND s.startDate BETWEEN :startDate AND :endDate")
    long countTicketsByUserAndScreeningDateRange(@Param("userId") long userId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}