package com.chandlercup.specification;

import com.chandlercup.model.WeeklyScore;
import org.springframework.data.jpa.domain.Specification;

public class WeeklyScoreSpecifications {
    public static Specification<WeeklyScore> weeklyScoresByUserId(Long userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("userId"), userId);
    }

    public static Specification<WeeklyScore> weeklyScoresBySeasonYear(String seasonYear) {
        return (root, query, cb) -> cb.equal(root.get("seasonYear"), seasonYear);
    }

    public static Specification<WeeklyScore> weeklyScoresBySeasonWeek(String seasonWeek) {
        return (root, query, cb) -> cb.equal(root.get("seasonWeek"), seasonWeek);
    }
}
