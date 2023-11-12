package com.chandlercup.service;

import com.chandlercup.model.Matchup;
import com.chandlercup.repository.MatchupRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MatchupService {
    private final MatchupRepository matchupRepository;

    public MatchupService(MatchupRepository matchupRepository) {
        this.matchupRepository = matchupRepository;
    }

    public Matchup addMatchup(Matchup matchup) {
        return matchupRepository.save(matchup);
    }

    public Matchup getMatchup(Long matchupId) {
        return matchupRepository.findById(matchupId).orElse(null);
    }

    public List<Matchup> getMatchupsByWeek(String week) {
        return matchupRepository.findMatchupsByMatchupWeek(week);
    }
}
