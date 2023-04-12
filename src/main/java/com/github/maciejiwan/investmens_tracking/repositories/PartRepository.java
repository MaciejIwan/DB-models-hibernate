package com.github.maciejiwan.investmens_tracking.repositories;

import com.github.maciejiwan.investmens_tracking.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByPriceBetween(int i, int i1);
}
