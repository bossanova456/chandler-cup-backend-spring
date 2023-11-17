package com.chandlercup.specification;

import com.chandlercup.model.SeasonScore;
import org.springframework.data.jpa.domain.Specification;

public class SeasonScoreSpecifications {
    public static Specification<SeasonScore> seasonScoresByUserId(Long userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("userId"), userId);
    }

    public static Specification<SeasonScore> seasonScoresBySeasonYear(String seasonYear) {
        return (root, query, cb) -> cb.equal(root.get("seasonYear"), seasonYear);
    }
}
