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
        Hall hall = hallRepository.save(Hall.builder().hallNumber(1).seatsCount(5).build());
        Hall hall2 = hallRepository.save(Hall.builder().hallNumber(2).seatsCount(10).build());

        Movie movie = movieRepository.save(Movie.builder().title("Movie 1").build());
        Movie movie2 = movieRepository.save(Movie.builder().title("Movie 2").build());
        Movie movie3 = movieRepository.save(Movie.builder().title("Movie 3").build());

        screeningRepository.save(Screening.builder().hall(hall).movie(movie).build());
        screeningRepository.save(Screening.builder().hall(hall).movie(movie2).build());
        screeningRepository.save(Screening.builder().hall(hall2).movie(movie3).build());
    }
    @Test
    void should_return_movies_in_given_hall_1() {
        // Given
        int hallNumber = 1;

        // When
        List<MovieDto> movies = screeningRepository.findMovieInHall(hallNumber);

        // Then
        int expected = 2;
        assertEquals(expected, movies.size());
    }

    @Test
    void should_return_screenings_in_given_hall_2() {
    }
    @Test
    void should_return_screenings_with_given_movie_id_3() {
    }

    @Test
    void should_return_screenings_with_given_movie_title_4() {
    }
    @Test
    void should_return_users_lists_on_given_screen_5() {
    }
    @Test
    void should_return_screenings_list_where_user_had_ticket_by_id_6() {
    }
    @Test
    void should_return_screenings_list_where_user_had_ticket_by_username_7() {
    }

    @Test
    void should_return_taken_seats_in_given_screening_by_id_8() {
    }
    @Test
    void should_return_list_of_halls_where_given_movie_was_plays_9() {
    }
    @Test
    void should_return_tickets_what_user_bought_between_given_datetime_range10() {
    }
}
