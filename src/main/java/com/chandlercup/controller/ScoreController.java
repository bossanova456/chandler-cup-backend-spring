package com.chandlercup.controller;

import com.chandlercup.exception.UserNotFoundException;
import com.chandlercup.model.SeasonScore;
import com.chandlercup.model.WeeklyScore;
import com.chandlercup.repository.UserRepository;
import com.chandlercup.service.ScoreService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/score")
@RequiredArgsConstructor
@Slf4j
public class ScoreController {
    private final ScoreService scoreService;
    private final UserRepository userRepository;

    @GetMapping("/calculateWeekly/{userId}/{seasonYear}/{seasonWeek}")
    public ResponseEntity<WeeklyScore> getWeeklyScore(
            @PathVariable Long userId,
            @PathVariable String seasonYear,
            @PathVariable String seasonWeek
    ) {
        return ResponseEntity.ok(
            scoreService.calculateWeeklyScore(
                WeeklyScore.builder()
                    .user(
                        userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found: " + userId))
                    )
                    .seasonYear(seasonYear)
                    .seasonWeek(seasonWeek)
                    .build(),
                false));
    }

    @GetMapping("/calculateSeason/{userId}/{seasonYear}")
    public ResponseEntity<SeasonScore> getSeasonScore(
            @PathVariable Long userId,
            @PathVariable String seasonYear
    ) {
        return ResponseEntity.ok(
            scoreService.calculateSeasonScore(
                SeasonScore.builder()
                    .user(
                        userRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User not found: " + userId))
                    )
                    .seasonYear(seasonYear)
                    .build(),
                false));
    }

    @PostMapping("/updateWeekly/{seasonYear}/{seasonWeek}")
    public ResponseEntity<List<WeeklyScore>> updateWeeklyScores(
            @PathVariable String seasonYear,
            @PathVariable String seasonWeek
    ) {
        return ResponseEntity.ok(scoreService.updateWeeklyScores(seasonYear, seasonWeek, false));
    }

    @PostMapping("/updateSeason/{seasonYear}")
    public ResponseEntity<List<SeasonScore>> updateSeasonScores(
            @PathVariable String seasonYear
    ) {
        return ResponseEntity.ok(scoreService.updateSeasonScoresForAllUsers(seasonYear, false));
    }
}
