package com.chandlercup.repository;

import com.chandlercup.model.Pick;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PickRepository extends JpaRepository<Pick, Long> {
    public Optional<List<Pick>> findPicksByUserIdAndSeasonYearAndSeasonWeek(Long userId, String seasonYear, String weekNumber);
}
