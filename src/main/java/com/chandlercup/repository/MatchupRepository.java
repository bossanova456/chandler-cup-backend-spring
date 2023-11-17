package com.chandlercup.repository;

import com.chandlercup.model.Matchup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchupRepository extends JpaRepository<Matchup, Long> {
    Optional<List<Matchup>> findMatchupsBySeasonYearAndSeasonWeek(String seasonYear, String seasonWeek);
}
