package com.github.maciejiwan.investmens_tracking.repositories;

import com.github.maciejiwan.investmens_tracking.dtos.MovieDto;
import com.github.maciejiwan.investmens_tracking.entities.Movie;
import com.github.maciejiwan.investmens_tracking.entities.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.stream.Collectors;

public interface ScreeningRepository extends JpaRepository<Screening, Long> {

    @Query(value = "SELECT new com.github.maciejiwan.investmens_tracking.dtos.MovieDto(s.movie.title) FROM Screening s WHERE s.hall.hallNumber = :hall")
    List<MovieDto> findMovieInHall(@Param("hall")int hallId);

}