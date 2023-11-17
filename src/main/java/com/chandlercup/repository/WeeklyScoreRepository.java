package com.chandlercup.repository;

import com.chandlercup.model.WeeklyScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WeeklyScoreRepository extends JpaRepository<WeeklyScore, Long>, JpaSpecificationExecutor<WeeklyScore> {
}
