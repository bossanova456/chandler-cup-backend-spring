package com.chandlercup.service;

import com.chandlercup.exception.MatchupNotFoundException;
import com.chandlercup.exception.UserNotFoundException;
import com.chandlercup.model.Matchup;
import com.chandlercup.model.Pick;
import com.chandlercup.model.Team;
import com.chandlercup.model.WeeklyScore;
import com.chandlercup.repository.*;
import com.chandlercup.supplier.LocalDateTimeSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreService {
    private final WeeklyScoreRepository weeklyScoreRepository;
    private final SeasonScoreRepository seasonScoreRepository;
    private final MatchupRepository matchupRepository;
    private final PickRepository pickRepository;
    private final UserRepository userRepository;
    private final MatchupService matchupService;
    private final LocalDateTimeSupplier localDateTimeSupplier;

    // Should not record score in DB, only return the score value
    // TODO: call from other method that does record score in DB
    public WeeklyScore calculateWeeklyScore(Long userId, String seasonYear, String seasonWeek, boolean includeOngoingGames) {
        WeeklyScore weeklyScore =
            weeklyScoreRepository.findByUserIdAndSeasonYearAndSeasonWeek(
                userId, seasonYear, seasonWeek
            ).orElse(
                WeeklyScore.builder()
                    .user(
                        userRepository.findById(userId)
                            .orElseThrow(() -> new UserNotFoundException("Could not find user with ID " + userId))
                    )
                    .seasonYear(seasonYear)
                    .seasonWeek(seasonWeek)
                    .score(0)
                    .build()
            );

        List<Matchup> matchups =
            matchupRepository.findMatchupsBySeasonYearAndSeasonWeek(seasonYear, seasonWeek)
                .orElseThrow(() -> new MatchupNotFoundException("Could not find matchups for week " + seasonWeek));

        List<Pick> picks =
            pickRepository.findPicksByUserIdAndSeasonYearAndSeasonWeek(userId, seasonYear, seasonWeek)
                .orElse(Collections.emptyList());

        // Iterate through recorded matchup scores & calculate weekly score
        // Get team ID of winning teams (line is taken into consideration)
        // TODO: return separate values for complete vs ongoing games?
        List<Team> winningMatchupTeams = matchups.stream()
            .filter(
                matchup -> (includeOngoingGames || matchup.isFinal()) &&
                        // Ensure that we're only counting scores for games that have started
                        matchup.getMatchupStart().isBefore(localDateTimeSupplier.get())
            )
            .map(
                matchup -> matchup.getFavoredTeamScore() > matchup.getUnderdogTeamScore() + matchup.getMatchupLine() ?
                    matchup.getFavoredTeam() :
                    matchup.getUnderdogTeam()
            ).toList();

        int score = picks.stream()
            .filter(
                pick -> winningMatchupTeams.contains(pick.getPickTeam())
            ).toList().size();

        weeklyScore.setScore(score);

        return weeklyScore;
    }
}
