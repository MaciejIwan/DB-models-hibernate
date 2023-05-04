package com.github.maciejiwan.investmens_tracking.dtos;

import com.github.maciejiwan.investmens_tracking.entities.Movie;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
public class MovieDto {
    private final String title;

    public MovieDto(String title) {
        this.title = title;
    }

    public MovieDto(Movie movie) {
        this.title = movie.getTitle();
    }
}
