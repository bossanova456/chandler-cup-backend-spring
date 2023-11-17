package com.chandlercup.service;

import com.chandlercup.dto.MatchupDTO;
import com.chandlercup.dto.MatchupScoreDTO;
import com.chandlercup.exception.MatchupNotFoundException;
import com.chandlercup.exception.TeamNotFoundException;
import com.chandlercup.model.Matchup;
import com.chandlercup.model.Team;
import com.chandlercup.repository.MatchupRepository;
import com.chandlercup.repository.TeamRepository;
import com.chandlercup.supplier.OffsetDateTimeSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchupService {
    private final MatchupRepository matchupRepository;
    private final TeamRepository teamRepository;
    private final ScoreService scoreService;
    private final OffsetDateTimeSupplier offsetDateTimeSupplier;

    public Matchup addMatchup(MatchupDTO matchupDto) {
        Matchup matchup =
            Matchup.builder()
                .matchupLine(matchupDto.getMatchupLine())
                .matchupStart(matchupDto.getMatchupStart())
                .seasonYear(matchupDto.getSeasonYear())
                .seasonWeek(matchupDto.getSeasonWeek())
                .favoredTeam(
                        teamRepository.findById(matchupDto.getFavoredTeamId())
                                .orElseThrow(() -> new TeamNotFoundException("Could not find favored team with ID " + matchupDto.getFavoredTeamId()))
                )
                .underdogTeam(
                        teamRepository.findById(matchupDto.getUnderdogTeamId())
                                .orElseThrow(() -> new TeamNotFoundException("Could not find underdog team with ID " + matchupDto.getFavoredTeamId()))
                )
                .lastUpdated(offsetDateTimeSupplier.get())
                .build();

        return matchupRepository.save(matchup);
    }

    public Matchup getMatchup(Long matchupId) {
        return matchupRepository.findById(matchupId).orElse(null);
    }

    public Matchup updateMatchup(Long matchupId, MatchupDTO matchupDto) {
        Matchup matchupToUpdate = matchupRepository.findById(matchupId).orElseThrow(() -> new MatchupNotFoundException("Could not find matchup with ID " + matchupId));

        Team favoredTeam = teamRepository.findById(matchupDto.getFavoredTeamId()).orElseThrow(() -> new TeamNotFoundException("Could not find favored team"));
        Team underdogTeam = teamRepository.findById(matchupDto.getUnderdogTeamId()).orElseThrow(() -> new TeamNotFoundException("Could not find underdog team"));

        matchupToUpdate.setMatchupLine(matchupDto.getMatchupLine());
        matchupToUpdate.setMatchupStart(matchupDto.getMatchupStart());
        matchupToUpdate.setSeasonYear(matchupDto.getSeasonYear());
        matchupToUpdate.setSeasonWeek(matchupDto.getSeasonWeek());
        matchupToUpdate.setFavoredTeam(favoredTeam);
        matchupToUpdate.setUnderdogTeam(underdogTeam);
        matchupToUpdate.setMatchupLine(matchupDto.getMatchupLine());
        matchupToUpdate.setFavoredTeamScore(matchupDto.getFavoredTeamScore());
        matchupToUpdate.setUnderdogTeamScore(matchupDto.getUnderdogTeamScore());
        matchupToUpdate.setFinal(matchupDto.isFinal());
        matchupToUpdate.setLastUpdated(offsetDateTimeSupplier.get());

        Matchup updatedMatchup = matchupRepository.save(matchupToUpdate);

        // Update scores
        scoreService.updateWeeklyScores(matchupDto.getSeasonYear(), matchupDto.getSeasonWeek(), false);
        scoreService.updateSeasonScoresForAllUsers(matchupDto.getSeasonYear(), false);

        return updatedMatchup;
    }

    public Matchup updateMatchupScore(Long matchupId, MatchupScoreDTO matchupScoreDTO) {
        Matchup matchupToUpdate = matchupRepository.findById(matchupId).orElseThrow(() -> new MatchupNotFoundException("Could not find matchup with ID " + matchupId));

        return updateMatchupScore(
                matchupToUpdate,
                matchupScoreDTO.getFavoredTeamScore(),
                matchupScoreDTO.getUnderdogTeamScore(),
                matchupScoreDTO.isFinal()
        );
    }

    public Matchup updateMatchupScore(Matchup matchup, Integer favoredTeamScore, Integer underdogTeamScore, boolean isFinal) {
        matchup.setFavoredTeamScore(favoredTeamScore);
        matchup.setUnderdogTeamScore(underdogTeamScore);
        matchup.setFinal(isFinal);
        matchup.setLastUpdated(offsetDateTimeSupplier.get());

        Matchup updatedMatchup = matchupRepository.save(matchup);

        // Update score
        scoreService.updateWeeklyScores(matchup.getSeasonYear(), matchup.getSeasonWeek(), false);
        scoreService.updateSeasonScoresForAllUsers(matchup.getSeasonYear(), false);

        return updatedMatchup;
    }

    public Matchup updateMatchupFinal(Long matchupId, boolean isFinal) {
        Matchup matchupToUpdate = matchupRepository.findById(matchupId).orElseThrow(() -> new MatchupNotFoundException("Could not find matchup with ID " + matchupId));

        matchupToUpdate.setFinal(isFinal);
        matchupToUpdate.setLastUpdated(offsetDateTimeSupplier.get());

        Matchup updatedMatchup = matchupRepository.save(matchupToUpdate);

        // Update score
        scoreService.updateWeeklyScores(matchupToUpdate.getSeasonYear(), matchupToUpdate.getSeasonWeek(), false);
        scoreService.updateSeasonScoresForAllUsers(matchupToUpdate.getSeasonYear(), false);

        return updatedMatchup;
    }
}
