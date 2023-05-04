package com.github.maciejiwan.investmens_tracking.repositories;

import com.github.maciejiwan.investmens_tracking.dtos.MovieDto;
import com.github.maciejiwan.investmens_tracking.dtos.ScreeningDto;
import com.github.maciejiwan.investmens_tracking.dtos.UserDto;
import com.github.maciejiwan.investmens_tracking.entities.Hall;
import com.github.maciejiwan.investmens_tracking.entities.Screening;
import com.github.maciejiwan.investmens_tracking.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;


import java.util.Date;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query(value = "SELECT new com.github.maciejiwan.investmens_tracking.dtos.MovieDto(s.movie.title) " +
            "FROM Screening s" +
            " WHERE s.hall.hallNumber = :hall")
    List<MovieDto> findMovieInHall(@Param("hall") int hallId, Pageable pageable);

    @Query("SELECT new com.github.maciejiwan.investmens_tracking.dtos.ScreeningDto(s.startDate, s.endDate, s.hall.hallNumber, s.movie) " +
            "FROM Screening s " +
            "WHERE s.hall.id = :hallId")
    List<ScreeningDto> findScreeningsByHallId(@Param("hallId") long hallId, Pageable pageable);

    @Query("SELECT new com.github.maciejiwan.investmens_tracking.dtos.ScreeningDto(s)" +
            " FROM Screening s " +
            "WHERE s.movie.id = :movieId")
    List<ScreeningDto> findScreeningsByMovieId(@Param("movieId") Long movieId, Pageable pageable);

    @Query("SELECT new com.github.maciejiwan.investmens_tracking.dtos.ScreeningDto(s.startDate, s.endDate, s.hall.hallNumber, s.movie) " +
            "FROM Screening s " +
            "WHERE s.movie.title = :title")
    List<ScreeningDto> findScreeningsByMovieTitle(@Param("title") String title, Pageable pageable);

    @Query("SELECT new com.github.maciejiwan.investmens_tracking.dtos.UserDto(u.name, u.email) " +
            "FROM Ticket t JOIN t.user u WHERE t.screening.id = :screeningId")
    List<UserDto> findUsersByScreeningId(@Param("screeningId") Long screeningId, Pageable pageable);

    @Query("SELECT new com.github.maciejiwan.investmens_tracking.dtos.ScreeningDto(s.startDate, s.endDate, s.hall.hallNumber) " +
            "FROM Screening s JOIN s.tickets t JOIN t.user u WHERE u.id = :userId")
    List<ScreeningDto> findScreeningsByUserId(@Param("userId") long userId, Pageable pageable);

    @Query("SELECT new com.github.maciejiwan.investmens_tracking.dtos.ScreeningDto(s.startDate, s.endDate, s.hall.hallNumber) " +
            "FROM Screening s JOIN s.tickets t JOIN t.user u WHERE u.email = :email")
    List<ScreeningDto> findScreeningsByEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT COUNT(DISTINCT s.hall) " +
            "FROM Screening s WHERE s.movie.id = :movieId")
    long countHallsByMovieId(@Param("movieId") Long movieId);


    @Query("SELECT t FROM Ticket t " +
            "WHERE t.user.id = :userId " +
            "AND t.purchaseDate BETWEEN :startDate AND :endDate")
    List<Ticket> findTicketsByUserIdAndPurchaseDateBetween(@Param("userId") Long userId,
                                                           @Param("startDate") Date startDate, @Param("endDate") Date endDate, Pageable pageable);

}
