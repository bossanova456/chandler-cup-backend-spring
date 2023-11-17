package com.chandlercup.specification;

import com.chandlercup.model.Pick;
import org.springframework.data.jpa.domain.Specification;

public class PickSpecifications {
    public static Specification<Pick> picksByUserId(Long userId) {
        return (root, query, cb) -> cb.equal(root.get("user").get("userId"), userId);
    }

    public static Specification<Pick> picksBySeasonYear(String seasonYear) {
        return (root, query, cb) -> cb.equal(root.get("seasonYear"), seasonYear);
    }

    public static Specification<Pick> picksBySeasonWeek(String seasonWeek) {
        return (root, query, cb) -> cb.equal(root.get("seasonWeek"), seasonWeek);
    }
}
