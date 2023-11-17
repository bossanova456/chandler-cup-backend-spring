package com.chandlercup.testdata;

import com.chandlercup.model.Matchup;
import com.chandlercup.model.Team;
import com.chandlercup.supplier.OffsetDateTimeSupplier;

import java.time.OffsetDateTime;

public class MockMatchupRepository {
    public static Matchup getMatchup(OffsetDateTime lastUpdated) {
        return Matchup.builder()
                .matchupId(1L)
                .seasonYear("2021")
                .seasonWeek("1")
                .favoredTeam(MockTeamRepository.getFavoredTeam())
                .underdogTeam(MockTeamRepository.getUnderdogTeam())
                .matchupLine(3.5)
                .favoredTeamScore(28)
                .underdogTeamScore(21)
                .isFinal(false)
                .lastUpdated(lastUpdated)
                .build();
    }

    public static Matchup getMatchup(Team favoredTeam, Team underdogTeam, OffsetDateTime lastUpdated) {
        return Matchup.builder()
                .matchupId(1L)
                .seasonYear("2021")
                .seasonWeek("1")
                .favoredTeam(favoredTeam)
                .underdogTeam(underdogTeam)
                .matchupLine(3.5)
                .favoredTeamScore(28)
                .underdogTeamScore(21)
                .isFinal(false)
                .lastUpdated(lastUpdated)
                .build();
    }
}
