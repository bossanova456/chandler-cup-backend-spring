package com.chandlercup.repository;

import com.chandlercup.model.Matchup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchupRepository extends JpaRepository<Matchup, Long> {
    List<Matchup> findMatchupsByMatchupWeek(String week);
}
