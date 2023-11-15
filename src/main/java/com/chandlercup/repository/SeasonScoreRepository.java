package com.chandlercup.repository;

import com.chandlercup.model.SeasonScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeasonScoreRepository extends JpaRepository<SeasonScore, Long> {
}
