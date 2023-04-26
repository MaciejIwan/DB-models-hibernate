package com.github.maciejiwan.investmens_tracking;

import com.github.maciejiwan.investmens_tracking.dtos.MovieDto;
import com.github.maciejiwan.investmens_tracking.entities.*;
import com.github.maciejiwan.investmens_tracking.enums.TicketType;
import com.github.maciejiwan.investmens_tracking.repositories.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.ClassBasedNavigableIterableAssert.assertThat;
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


    @BeforeEach
    public void setup() {

    }
    @Test
    void should_return_movies_in_given_hall_1() {
        Hall hall = hallRepository.save(Hall.builder().hallNumber(1).seatsCount(5).build());
        Hall hall2 = hallRepository.save(Hall.builder().hallNumber(2).seatsCount(10).build());

        Movie movie = movieRepository.save(Movie.builder().title("Movie 1").build());
        Movie movie2 = movieRepository.save(Movie.builder().title("Movie 2").build());
        Movie movie3 = movieRepository.save(Movie.builder().title("Movie 3").build());

        screeningRepository.save(Screening.builder().hall(hall).movie(movie).build());
        screeningRepository.save(Screening.builder().hall(hall).movie(movie2).build());
        screeningRepository.save(Screening.builder().hall(hall2).movie(movie3).build());

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
        // Given
        Hall hall = hallRepository.save(Hall.builder().hallNumber(1).seatsCount(50).build());
        Movie movie = movieRepository.save(Movie.builder().title("Movie 1").build());
        Screening screening1 = screeningRepository.save(Screening.builder().startDate(new Date()).endDate(new Date()).hall(hall).movie(movie).basicPrice(10).build());
        Screening screening2 = screeningRepository.save(Screening.builder().startDate(new Date()).endDate(new Date()).hall(hall).movie(movie).basicPrice(10).build());

        // When
        List<Screening> screenings = screeningRepository.findScreeningsByHallId(hall.getId());

        // Then
        assertEquals(2, screenings.size());
        assertTrue(screenings.contains(screening1));
        assertTrue(screenings.contains(screening2));
    }

    @Test
    void should_return_screenings_with_given_movie_id_3() {
        // Given
        Hall hall1 = hallRepository.save(Hall.builder().hallNumber(1).seatsCount(50).build());
        Hall hall2 = hallRepository.save(Hall.builder().hallNumber(2).seatsCount(100).build());
        Movie movie = movieRepository.save(Movie.builder().title("Movie 1").build());
        Screening screening1 = screeningRepository.save(Screening.builder().startDate(new Date()).endDate(new Date())
                .hall(hall1).movie(movie).basicPrice(10).build());
        Screening screening2 = screeningRepository.save(Screening.builder().startDate(new Date()).endDate(new Date())
                .hall(hall2).movie(movie).basicPrice(10).build());

        // When
        List<Screening> screenings = screeningRepository.findScreeningsByMovieId(movie.getId());

        // Then
        assertEquals(2, screenings.size());
        assertTrue(screenings.contains(screening1));
        assertTrue(screenings.contains(screening2));
    }

    @Test
    void should_return_screenings_with_given_movie_title_4() {
        String movieTitle = "The Matrix";

        Movie movie = Movie.builder().title(movieTitle).build();
        movieRepository.save(movie);

        Hall hall = Hall.builder().hallNumber(1).seatsCount(100).build();
        hallRepository.save(hall);

        Screening screening1 = Screening.builder().startDate(new Date()).endDate(new Date())
                .hall(hall).movie(movie).basicPrice(10).build();
        Screening screening2 = Screening.builder().startDate(new Date()).endDate(new Date())
                .hall(hall).movie(movie).basicPrice(10).build();
        screeningRepository.saveAll(Arrays.asList(screening1, screening2));

        List<Screening> screeningsByTitle = screeningRepository.findScreeningsByMovieTitle(movieTitle);

        assertEquals(2, screeningsByTitle.size());
        assertEquals(movieTitle, screeningsByTitle.get(0).getMovie().getTitle());
        assertEquals(movieTitle, screeningsByTitle.get(1).getMovie().getTitle());
    }

    @Test
    void should_return_users_lists_on_given_screen_5() {
        Hall hall = Hall.builder().hallNumber(1).seatsCount(100).build();
        hallRepository.save(hall);

        Movie movie = Movie.builder().title("The Matrix").build();
        movieRepository.save(movie);

        Screening screening = Screening.builder().startDate(new Date()).endDate(new Date())
                .hall(hall).movie(movie).basicPrice(10).build();
        screeningRepository.save(screening);

        UserModel user1 = UserModel.builder().name("John").surname("Doe").email("john.doe@example.com").build();
        UserModel user2 = UserModel.builder().name("Jane").surname("Doe").email("jane.doe@example.com").build();
        userRepository.saveAll(Arrays.asList(user1, user2));

        Ticket ticket1 = Ticket.builder().screening(screening).user(user1).type(TicketType.NORMAL).build();
        Ticket ticket2 = Ticket.builder().screening(screening).user(user2).type(TicketType.NORMAL).build();
        ticketRepository.saveAll(Arrays.asList(ticket1, ticket2));

        List<UserModel> usersByScreeningId = screeningRepository.findUsersByScreeningId(screening.getId());

        assertEquals(2, usersByScreeningId.size());
        assertTrue(usersByScreeningId.contains(user1));
        assertTrue(usersByScreeningId.contains(user2));
    }


//    @Test
//    void should_return_screenings_with_given_movie_title_4() {
//    }
//    @Test
//    void should_return_users_lists_on_given_screen_5() {
//    }
@Test
void should_return_screenings_list_where_user_had_ticket_by_id_6() {
    // given
    UserModel user = UserModel.builder().email("john.doe@example.com").name("John Doe").build();
    userRepository.save(user);

    Movie movie = Movie.builder().title("The Matrix").build();
    movieRepository.save(movie);

    Hall hall = Hall.builder().hallNumber(1).seatsCount(50).build();
    hallRepository.save(hall);

    Date startDate = new Date();
    Date endDate = new Date(startDate.getTime() + 3600000); // Add one hour to start date

    Screening screening1 = Screening.builder()
            .startDate(startDate)
            .endDate(endDate)
            .hall(hall)
            .movie(movie)
            .basicPrice(10)
            .build();

    Screening screening2 = Screening.builder()
            .startDate(endDate)
            .endDate(new Date(endDate.getTime() + 3600000)) // Add one hour to end date
            .hall(hall)
            .movie(movie)
            .basicPrice(10)
            .build();

    screeningRepository.saveAll(Arrays.asList(screening1, screening2));

    Ticket ticket1 = Ticket.builder().user(user).screening(screening1).seatNumber(1).build();
    Ticket ticket2 = Ticket.builder().user(user).screening(screening2).seatNumber(2).build();

    ticketRepository.saveAll(Arrays.asList(ticket1, ticket2));

    // when
    long userId = 1L;
    List<Screening> screenings = screeningRepository.findScreeningsByUserId(userId);

    // then
    assertNotNull(screenings);
    assertEquals(2, screenings.size());
}


    @Test
    void should_return_screenings_list_where_user_had_ticket_by_username_7() {
        UserModel user = userRepository.save(UserModel.builder().email("john.doe").name("john").build());
        Movie movie = movieRepository.save(Movie.builder().title("The Matrix").build());
        Hall hall = hallRepository.save(Hall.builder().hallNumber(1).seatsCount(50).build());
        Date startDate1 = Date.from(Instant.parse("2022-01-01T00:00:00Z"));
        Date endDate1 = Date.from(Instant.parse("2022-01-01T02:00:00Z"));
        Date startDate2 = Date.from(Instant.parse("2022-01-02T00:00:00Z"));
        Date endDate2 = Date.from(Instant.parse("2022-01-02T02:00:00Z"));
        Date startDate3 = Date.from(Instant.parse("2022-01-03T00:00:00Z"));
        Date endDate3 = Date.from(Instant.parse("2022-01-03T02:00:00Z"));
        Screening screening1 = screeningRepository.save(Screening.builder()
                .startDate(startDate1)
                .endDate(endDate1)
                .hall(hall)
                .movie(movie)
                .basicPrice(10)
                .build());
        Screening screening2 = screeningRepository.save(Screening.builder()
                .startDate(startDate2)
                .endDate(endDate2)
                .hall(hall)
                .movie(movie)
                .basicPrice(10)
                .build());
        Screening screening3 = screeningRepository.save(Screening.builder()
                .startDate(startDate3)
                .endDate(endDate3)
                .hall(hall)
                .movie(movie)
                .basicPrice(10)
                .build());
        Ticket ticket1 = ticketRepository.save(Ticket.builder()
                .user(user)
                .screening(screening1)
                .seatNumber(1)
                .price(10)
                .build());
        Ticket ticket2 = ticketRepository.save(Ticket.builder()
                .user(user)
                .screening(screening2)
                .seatNumber(2)
                .price(10)
                .build());

        List<Screening> screenings = screeningRepository.findScreeningsByEmail(user.getEmail());
        assertNotNull(screenings);
        assertEquals(2, screenings.size());
    }


    @Test
    void should_return_taken_seats_in_given_screening_by_id_8() {
        Hall hall = hallRepository.save(Hall.builder().hallNumber(1).seatsCount(50).build());
        Movie movie = movieRepository.save(Movie.builder().title("Avengers").build());
        Screening screening = screeningRepository.save(Screening.builder()
                .startDate(new Date())
                .endDate(new Date())
                .hall(hall)
                .movie(movie)
                .basicPrice(10)
                .build());
        UserModel user1 = userRepository.save(UserModel.builder().name("user1").email("mail1").build());
        UserModel user2 = userRepository.save(UserModel.builder().name("user2").email("mail2").build());
        Ticket ticket1 = ticketRepository.save(Ticket.builder()
                .screening(screening)
                .user(user1)
                .seatNumber(10)
                .price(10)
                .build());
        Ticket ticket2 = ticketRepository.save(Ticket.builder()
                .screening(screening)
                .user(user2)
                .seatNumber(20)
                .price(10)
                .build());

        List<Integer> takenSeats = ticketRepository.findTakenSeatsByScreeningId(screening.getId());

        Assertions.assertThat(takenSeats).containsExactlyInAnyOrderElementsOf(Arrays.asList(10, 20));
    }

    @Test
    void should_return_list_of_halls_where_given_movie_was_plays_9() {
        Movie movie = movieRepository.save(Movie.builder().title("Avengers").build());

        Hall hall1 = hallRepository.save(Hall.builder().hallNumber(1).seatsCount(50).build());
        Hall hall2 = hallRepository.save(Hall.builder().hallNumber(2).seatsCount(60).build());

        Screening screening1 = screeningRepository.save(Screening.builder()
                .startDate(new Date())
                .endDate(new Date())
                .hall(hall1)
                .movie(movie)
                .basicPrice(10)
                .build());

        Screening screening2 = screeningRepository.save(Screening.builder()
                .startDate(new Date())
                .endDate(new Date())
                .hall(hall2)
                .movie(movie)
                .basicPrice(10)
                .build());

        List<Hall> halls = screeningRepository.findHallsByMovieId(movie.getId());

        Assertions.assertThat(halls).containsExactlyInAnyOrderElementsOf(Arrays.asList(hall1, hall2));
    }


    @Test
    void should_return_tickets_what_user_bought_between_given_datetime_range10() {
        UserModel user = userRepository.save(UserModel.builder().email("johndoe").name("joe").build());

        Hall hall = hallRepository.save(Hall.builder().hallNumber(1).seatsCount(50).build());

        Movie movie = movieRepository.save(Movie.builder().title("Avengers").build());

        // create screenings
        Screening screening1 = screeningRepository.save(Screening.builder()
                .startDate(Date.from(LocalDateTime.of(2023, 5, 1, 10, 0, 0).atZone(ZoneId.systemDefault()).toInstant()))
                .endDate(Date.from(LocalDateTime.of(2023, 5, 1, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant()))
                .hall(hall)
                .movie(movie)
                .basicPrice(10)
                .build());

        Screening screening2 = screeningRepository.save(Screening.builder()
                .startDate(Date.from(LocalDateTime.of(2023, 5, 2, 10, 0, 0).atZone(ZoneId.systemDefault()).toInstant()))
                .endDate(Date.from(LocalDateTime.of(2023, 5, 2, 12, 0, 0).atZone(ZoneId.systemDefault()).toInstant()))
                .hall(hall)
                .movie(movie)
                .basicPrice(10)
                .build());

        // create tickets
        Ticket ticket1 = ticketRepository.save(Ticket.builder().user(user).screening(screening1).seatNumber(10).price(10).build());
        Ticket ticket2 = ticketRepository.save(Ticket.builder().user(user).screening(screening2).seatNumber(20).price(10).build());

        // search for tickets between the given date range
        Date startDate = Date.from(LocalDateTime.of(2023, 5, 1, 0, 0, 0).atZone(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(LocalDateTime.of(2023, 5, 2, 23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());
        List<Ticket> tickets = ticketRepository.findTicketsByUserAndScreeningDateRange(user.getId(), startDate, endDate);

        Assertions.assertThat(tickets).containsExactlyInAnyOrderElementsOf(Arrays.asList(ticket1, ticket2));
    }


}
