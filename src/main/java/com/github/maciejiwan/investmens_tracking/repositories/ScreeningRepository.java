package com.github.maciejiwan.investmens_tracking.repositories;

import com.github.maciejiwan.investmens_tracking.dtos.MovieDto;
import com.github.maciejiwan.investmens_tracking.entities.Hall;
import com.github.maciejiwan.investmens_tracking.entities.Screening;
import com.github.maciejiwan.investmens_tracking.entities.Ticket;
import com.github.maciejiwan.investmens_tracking.entities.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query(value = "SELECT new com.github.maciejiwan.investmens_tracking.dtos.MovieDto(s.movie.title) FROM Screening s WHERE s.hall.hallNumber = :hall")
    List<MovieDto> findMovieInHall(@Param("hall") int hallId);

    @Query("SELECT s FROM Screening s WHERE s.hall.id = :hallId")
    List<Screening> findScreeningsByHallId(@Param("hallId") long hallId);

    @Query("SELECT s FROM Screening s WHERE s.movie.id = :movieId")
    List<Screening> findScreeningsByMovieId(@Param("movieId") Long movieId);

    @Query("SELECT s FROM Screening s WHERE s.movie.title = :title")
    List<Screening> findScreeningsByMovieTitle(@Param("title") String title);

    @Query("SELECT t.user FROM Ticket t WHERE t.screening.id = :screeningId")
    List<UserModel> findUsersByScreeningId(@Param("screeningId") Long screeningId);

    @Query("SELECT s FROM Screening s JOIN s.tickets t JOIN t.user u WHERE u.id = :userId")
    List<Screening> findScreeningsByUserId(@Param("userId") long userId);

    @Query("SELECT s FROM Screening s JOIN s.tickets t JOIN t.user u WHERE u.email = :email")
    List<Screening> findScreeningsByEmail(@Param("email") String email);

    @Query("SELECT DISTINCT s.hall FROM Screening s WHERE s.movie.id = :movieId")
    List<Hall> findHallsByMovieId(@Param("movieId") Long movieId);

    @Query("SELECT t FROM Ticket t WHERE t.user.id = :userId AND t.purchaseDate BETWEEN :startDate AND :endDate")
    List<Ticket> findTicketsByUserIdAndPurchaseDateBetween(@Param("userId") Long userId,
                                                           @Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
