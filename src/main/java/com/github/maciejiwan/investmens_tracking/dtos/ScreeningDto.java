package com.github.maciejiwan.investmens_tracking.dtos;

import com.github.maciejiwan.investmens_tracking.entities.Movie;
import com.github.maciejiwan.investmens_tracking.entities.Screening;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@Data
public class ScreeningDto {
    private Date startTime;
    private Date endTime;
    private int hallNumber;
    private MovieDto movie;
    private List<ParticipantDto> participants;

    public ScreeningDto(Date startDate, Date endDate, int hallNumber, List<ParticipantDto> participants) {
        this.startTime = startDate;
        this.endTime = endDate;
        this.hallNumber = hallNumber;
        this.participants = participants;
    }
    public ScreeningDto(Date startDate, Date endDate, int hallNumber) {
        this.startTime = startDate;
        this.endTime = endDate;
        this.hallNumber = hallNumber;
    }

    public ScreeningDto(Date startDate, Date endDate, int hallNumber, Movie movie) {
        this.startTime = startDate;
        this.endTime = endDate;
        this.hallNumber = hallNumber;
        this.movie = new MovieDto(movie);
    }

    public ScreeningDto(Screening screening) {
        this.startTime = screening.getStartDate();
        this.endTime = screening.getEndDate();
        this.hallNumber = screening.getHall().getHallNumber();
        this.movie = new MovieDto(screening.getMovie());
    }
}
