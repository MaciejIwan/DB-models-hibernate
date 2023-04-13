package com.github.maciejiwan.investmens_tracking;

import com.github.maciejiwan.investmens_tracking.dtos.MovieDto;
import com.github.maciejiwan.investmens_tracking.entities.Hall;
import com.github.maciejiwan.investmens_tracking.entities.Movie;
import com.github.maciejiwan.investmens_tracking.entities.Screening;
import com.github.maciejiwan.investmens_tracking.repositories.HallRepository;
import com.github.maciejiwan.investmens_tracking.repositories.MovieRepository;
import com.github.maciejiwan.investmens_tracking.repositories.ScreeningRepository;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class Zadanie2 {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private HallRepository hallRepository;


    @BeforeEach
    public void setup() {
        Hall hall = hallRepository.save(Hall.builder().seatsCount(5).build());
        Movie movie = movieRepository.save(Movie.builder().title("Movie 1").build());
        screeningRepository.save(Screening.builder().hall(hall).movie(movie).build());
    }
    @Test
    void should_return_movies_in_given_hall() {
        // When
        List<MovieDto> movies = screeningRepository.findMovieInHallById(0L);

        // Then
        assertEquals(0, movies.size());
    }
}
