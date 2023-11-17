package com.chandlercup.service;

import com.chandlercup.exception.MatchupNotFoundException;
import com.chandlercup.model.*;
import com.chandlercup.repository.*;
import com.chandlercup.specification.PickSpecifications;
import com.chandlercup.specification.WeeklyScoreSpecifications;
import com.chandlercup.supplier.OffsetDateTimeSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ScoreService {
    private final WeeklyScoreRepository weeklyScoreRepository;
    private final SeasonScoreRepository seasonScoreRepository;
    private final MatchupRepository matchupRepository;
    private final PickRepository pickRepository;
    private final UserRepository userRepository;
    private final OffsetDateTimeSupplier offsetDateTimeSupplier;

    // Should not record score in DB, only return the score value
    // TODO: call from other method that does record score in DB
    // TODO: this should do the update in the same method
    public WeeklyScore calculateWeeklyScore(WeeklyScore weeklyScore, boolean includeOngoingGames) {
        // TODO: specifications for matchups too?
        List<Matchup> matchups =
            matchupRepository.findMatchupsBySeasonYearAndSeasonWeek(
                weeklyScore.getSeasonYear(),
                weeklyScore.getSeasonWeek()
            ).orElseThrow(
                () -> new MatchupNotFoundException("Could not find matchups for week " + weeklyScore.getSeasonWeek())
            );

        List<Pick> picks =
            pickRepository.findAll(
                PickSpecifications.picksByUserId(weeklyScore.getUser().getUserId())
                    .and(PickSpecifications.picksBySeasonYear(weeklyScore.getSeasonYear()))
                    .and(PickSpecifications.picksBySeasonWeek(weeklyScore.getSeasonWeek()))
            );

        // Iterate through recorded matchup scores & calculate weekly score
        // Get team ID of winning teams (line is taken into consideration)
        // TODO: return separate values for complete vs ongoing games?
        List<Team> winningMatchupTeams = matchups.stream()
            .filter(
                matchup -> (includeOngoingGames || matchup.isFinal()) &&
                        // Ensure that we're only counting scores for games that have started
                        matchup.getMatchupStart().isBefore(offsetDateTimeSupplier.get())
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

    public List<WeeklyScore> updateWeeklyScores(String seasonYear, String seasonWeek, boolean includeOngoingGames) {
        List<WeeklyScore> weeklyScores =
            weeklyScoreRepository.findAll(
                WeeklyScoreSpecifications.weeklyScoresBySeasonYear(seasonYear)
                    .and(WeeklyScoreSpecifications.weeklyScoresBySeasonWeek(seasonWeek))
            );

        weeklyScores.addAll(
            userRepository.findAll().stream()
                .filter(
                    user -> weeklyScores.stream()
                        .noneMatch(
                            weeklyScore -> weeklyScore.getUser().getUserId().equals(user.getUserId())
                        )
                ).map(
                    user -> WeeklyScore.builder()
                        .user(user)
                        .seasonYear(seasonYear)
                        .seasonWeek(seasonWeek)
                        .score(0)
                        .build()
                ).toList()
        );

        weeklyScores.forEach(
            weeklyScore -> {
                weeklyScore.setScore(
                    calculateWeeklyScore(
                        weeklyScore,
                        includeOngoingGames
                    ).getScore()
                );
                weeklyScore.setLastUpdated(offsetDateTimeSupplier.get());
            }
        );

        return weeklyScoreRepository.saveAll(weeklyScores);
    }

    // TODO: add functionality to include totals with ongoing games
    public SeasonScore calculateSeasonScore(SeasonScore seasonScore, boolean includeOngoingGames) {
        // Get all recorded weekly scores for the season
        List<WeeklyScore> weeklyScores = getWeeklyScoresByUserId(
            seasonScore.getUser().getUserId(),
            seasonScore.getSeasonYear()
        );

        int score = weeklyScores.stream()
            .mapToInt(WeeklyScore::getScore)
            .sum();

        seasonScore.setScore(score);
        seasonScore.setLastUpdated(offsetDateTimeSupplier.get());

        return seasonScore;
    }

    // Update all existing season scores, and add new season scores for users that don't have one
    // TODO: what defines missing season scores? should we add a season score for every user,
    //  or only users that have picks/weekly scores?
    public List<SeasonScore> updateSeasonScoresForAllUsers(String seasonYear, boolean includeOngoingGames) {
        List<SeasonScore> seasonScores =
            weeklyScoreRepository.findAll(
                    WeeklyScoreSpecifications.weeklyScoresBySeasonYear(seasonYear)
                ).stream().map(
                    WeeklyScore::getUser
                ).distinct()
                .map(
                    // Get existing season score for each user or create new one
                    user -> seasonScoreRepository.findByUserAndSeasonYear(user, seasonYear)
                        .orElse(
                            SeasonScore.builder()
                                .user(user)
                                .seasonYear(seasonYear)
                                .score(0)
                                .build()
                        )
                ).map(
                    // Calculate new season score for each user
                    seasonScore -> calculateSeasonScore(seasonScore, includeOngoingGames)
                ).toList();

        return seasonScoreRepository.saveAll(seasonScores);
    }

    public List<WeeklyScore> getWeeklyScores(String seasonYear, String seasonWeek) {
        return weeklyScoreRepository.findAll(
            WeeklyScoreSpecifications.weeklyScoresBySeasonYear(seasonYear)
                .and(WeeklyScoreSpecifications.weeklyScoresBySeasonWeek(seasonWeek))
        );
    }

    public List<WeeklyScore> getWeeklyScoresByUserId(Long userId, String seasonYear) {
        return weeklyScoreRepository.findAll(
            WeeklyScoreSpecifications.weeklyScoresBySeasonYear(seasonYear)
                .and(WeeklyScoreSpecifications.weeklyScoresByUserId(userId))
        );
    }
}
