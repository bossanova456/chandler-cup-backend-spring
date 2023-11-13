package com.chandlercup.service;

import com.chandlercup.dto.MatchupDTO;
import com.chandlercup.dto.MatchupScoreDTO;
import com.chandlercup.exception.MatchupNotFoundException;
import com.chandlercup.exception.TeamNotFoundException;
import com.chandlercup.model.Matchup;
import com.chandlercup.model.Team;
import com.chandlercup.repository.MatchupRepository;
import com.chandlercup.repository.TeamRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MatchupService {
    private final MatchupRepository matchupRepository;
    private final TeamRepository teamRepository;

    public Matchup addMatchup(Matchup matchup) {
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
        matchupToUpdate.setMatchupWeek(matchupDto.getMatchupWeek());
        matchupToUpdate.setFavoredTeam(favoredTeam);
        matchupToUpdate.setUnderdogTeam(underdogTeam);
        matchupToUpdate.setMatchupLine(matchupDto.getMatchupLine());
        matchupToUpdate.setFavoredTeamScore(matchupDto.getFavoredTeamScore());
        matchupToUpdate.setUnderdogTeamScore(matchupDto.getUnderdogTeamScore());
        matchupToUpdate.setFinal(matchupDto.isFinal());

        return matchupRepository.save(matchupToUpdate);
    }

    public Matchup updateMatchupScore(Long matchupId, MatchupScoreDTO matchupScoreDTO) {
        Matchup matchupToUpdate = matchupRepository.findById(matchupId).orElseThrow(() -> new MatchupNotFoundException("Could not find matchup with ID " + matchupId));

        matchupToUpdate.setFavoredTeamScore(matchupScoreDTO.getFavoredTeamScore());
        matchupToUpdate.setUnderdogTeamScore(matchupScoreDTO.getUnderdogTeamScore());
        matchupToUpdate.setFinal(matchupScoreDTO.isFinal());

        return matchupRepository.save(matchupToUpdate);
    }

    public Matchup updateMatchupFinal(Long matchupId, boolean isFinal) {
        Matchup matchupToUpdate = matchupRepository.findById(matchupId).orElseThrow(() -> new MatchupNotFoundException("Could not find matchup with ID " + matchupId));

        matchupToUpdate.setFinal(isFinal);

        return matchupRepository.save(matchupToUpdate);
    }
}
