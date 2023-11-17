package com.chandlercup.testdata;

import com.chandlercup.model.Team;

public class MockTeamRepository {
    public static Team getFavoredTeam() {
        return Team.builder()
                .teamId(1L)
                .teamName("Favored Team")
                .build();
    }

    public static Team getUnderdogTeam() {
        return Team.builder()
                .teamId(2L)
                .teamName("Underdog Team")
                .build();
    }
}
