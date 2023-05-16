package com.github.maciejiwan.investmens_tracking;

import com.github.maciejiwan.investmens_tracking.dtos.MovieDto;
import com.github.maciejiwan.investmens_tracking.dtos.ScreeningDto;
import com.github.maciejiwan.investmens_tracking.dtos.UserDto;
import com.github.maciejiwan.investmens_tracking.entities.*;
import com.github.maciejiwan.investmens_tracking.enums.TicketType;
import com.github.maciejiwan.investmens_tracking.repositories.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class Zadanie2 {

    @Autowired
    private ScreeningRepository screeningRepository;
    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private HallRepository hallRepository;
    @Autowired
    private UserModelRepository userRepository;
    @Autowired
    private TicketRepository ticketRepository;

    Hall hall1, hall2;
    Movie movie1, movie2, movie3;
    Screening screening1, screening2, screening3, screening4;
    UserModel user1, user2;
    Ticket ticket1, ticket2, ticket3;

    @BeforeEach
    public void setup() {
        hall1 = Hall.builder().hallNumber(1).seatsCount(5).build();
        hall2 = Hall.builder().hallNumber(2).seatsCount(10).build();
        hallRepository.saveAll(Arrays.asList(hall1, hall2));

        movie1 = Movie.builder().title("Movie 1").build();
        movie2 = Movie.builder().title("Movie 2").build();
        movie3 = Movie.builder().title("Movie 3").build();
        movieRepository.saveAll(Arrays.asList(movie1, movie2, movie3));

        screening1 = Screening.builder().hall(hall1).movie(movie1).build();
        screening2 = Screening.builder().startDate(Date.from(LocalDateTime.of(2023, 5, 1, 10, 0, 0).atZone(ZoneId.systemDefault()).toInstant())).endDate(Date.from(LocalDateTime.of(2023, 5, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant())).hall(hall1).movie(movie2).build();
        screening3 = Screening.builder().startDate(Date.from(LocalDateTime.of(2023, 5, 2, 10, 0, 0).atZone(ZoneId.systemDefault()).toInstant())).endDate(Date.from(LocalDateTime.of(2023, 5, 2, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant())).hall(hall2).movie(movie3).build();
        screening4 = Screening.builder().hall(hall1).movie(movie3).build();
        screeningRepository.saveAll(Arrays.asList(screening1, screening2, screening3, screening4));

        user1 = UserModel.builder().name("John").surname("Doe").email("john.doe@example.com").build();
        user2 = UserModel.builder().name("Jane").surname("Doe").email("jane.doe@example.com").build();
        userRepository.saveAll(Arrays.asList(user1, user2));

        ticket1 = Ticket.builder().screening(screening3).user(user1).type(TicketType.NORMAL).build();
        ticket2 = Ticket.builder().screening(screening3).user(user2).type(TicketType.NORMAL).build();
        ticket3 = Ticket.builder().screening(screening2).user(user1).type(TicketType.NORMAL).build();
        ticketRepository.saveAll(Arrays.asList(ticket1, ticket2, ticket3));
    }

    @Test
    void should_return_movies_in_given_hall_1() {
        // Given
        int hallNumber = hall1.getHallNumber();

        // When
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<MovieDto> movies = screeningRepository.findMovieInHall(hallNumber, pageRequest);

        // Then
        int expected = 3;
        assertEquals(expected, movies.size());
    }

    @Test
    void should_return_screenings_in_given_hall_2() {
        // Given
        long hallId = hall1.getId();

        // When
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<ScreeningDto> screenings = screeningRepository.findScreeningsByHallId(hallId, pageRequest);

        // Then
        assertEquals(3, screenings.size());
        assertEquals(screening1.getMovie().getTitle(), screenings.get(0).getMovie().getTitle());
        assertEquals(screening2.getMovie().getTitle(), screenings.get(1).getMovie().getTitle());
    }

    @Test
    void should_return_screenings_with_given_movie_id_3() {
        // Given
        long movieId = movie3.getId();

        // When
        PageRequest pageRequest = PageRequest.of(0, 5);

        List<ScreeningDto> screenings = screeningRepository.findScreeningsByMovieId(movieId, pageRequest);

        // Then
        assertEquals(2, screenings.size());
        assertEquals(new ScreeningDto(screening3), screenings.get(0));
        assertEquals(new ScreeningDto(screening4), screenings.get(1));
    }

    @Test
    void should_return_screenings_with_given_movie_title_4() {
        //given
        String givenTitle = movie3.getTitle();

        //when
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<ScreeningDto> screenings = screeningRepository.findScreeningsByMovieTitle(givenTitle, pageRequest);

        //then
        assertEquals(2, screenings.size());
        assertEquals(givenTitle, screenings.get(0).getMovie().getTitle());
        assertEquals(givenTitle, screenings.get(1).getMovie().getTitle());
    }

    @Test
    void should_return_users_lists_on_given_screen_5() {
        //given
        long givenScreenId = screening3.getId();

        //when
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<UserDto> usersByScreeningId = screeningRepository.findUsersByScreeningId(givenScreenId, pageRequest);

        //then
        assertEquals(2, usersByScreeningId.size());
        assertTrue(usersByScreeningId.contains(new UserDto(user1)));
        assertTrue(usersByScreeningId.contains(new UserDto(user2)));
    }


    @Test
    void should_return_screenings_list_where_user_had_ticket_by_id_6() {
        // given
        long givenUserId = user1.getId();

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<ScreeningDto> screenings = screeningRepository.findScreeningsByUserId(givenUserId, pageRequest);

        // then
        assertNotNull(screenings);
        assertEquals(2, screenings.size());
    }


    @Test
    void should_return_screenings_list_where_user_had_ticket_by_username_7() {
        // given
        String givenUserEmail = user1.getEmail();

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        List<ScreeningDto> screenings = screeningRepository.findScreeningsByEmail(givenUserEmail, pageRequest);

        // then
        assertNotNull(screenings);
        assertEquals(2, screenings.size());
    }


    @Test
    void should_return_count_of_taken_seats_in_given_screening_by_id_8() {
        // given
        long givenScreenId = screening3.getId();

        // when
        long actualTakenSeats = ticketRepository.countTakenSeatsByScreeningId(givenScreenId);
        long expectedTakenSeats = 2;

        // then
        Assertions.assertThat(actualTakenSeats).isEqualTo(expectedTakenSeats);
    }

    @Test
    void should_return_list_of_halls_where_given_movie_was_plays_9() {
        // given
        long givenMovieId = movie3.getId();
        long expectedCount = 2;

        // when
        PageRequest pageRequest = PageRequest.of(0, 5);
        long actualCount = screeningRepository.countHallsByMovieId(givenMovieId);

        // then
        Assertions.assertThat(actualCount).isEqualTo(expectedCount);
    }


    @Test
    void should_return_tickets_what_user_bought_between_given_datetime_range10() {
        //given
        long expectedCount = 2;
        Date startDate = Date.from(LocalDateTime.of(2023, 5, 1, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(LocalDateTime.of(2023, 5, 2, 23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        //when
        long actualCount = ticketRepository.countTicketsByUserAndScreeningDateRange(user1.getId(), startDate, endDate);

        //then
        Assertions.assertThat(actualCount).isEqualTo(expectedCount);
    }


}
