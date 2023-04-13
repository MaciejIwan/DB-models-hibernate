package com.github.maciejiwan.investmens_tracking.repositories;

import com.github.maciejiwan.investmens_tracking.entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}