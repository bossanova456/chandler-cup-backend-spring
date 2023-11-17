package com.chandlercup.repository;

import com.chandlercup.model.SeasonScore;
import com.chandlercup.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeasonScoreRepository extends JpaRepository<SeasonScore, Long>, JpaSpecificationExecutor<SeasonScore> {
    public Optional<SeasonScore> findByUserAndSeasonYear(User user, String seasonYear);
}
