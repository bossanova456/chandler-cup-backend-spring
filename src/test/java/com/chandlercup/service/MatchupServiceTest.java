package com.chandlercup.service;

import com.chandlercup.dto.MatchupDTO;
import com.chandlercup.dto.MatchupScoreDTO;
import com.chandlercup.model.Matchup;
import com.chandlercup.model.Team;
import com.chandlercup.repository.MatchupRepository;
import com.chandlercup.repository.TeamRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.chandlercup.supplier.LocalDateTimeSupplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MatchupServiceTest {
    private MatchupRepository matchupRepository;
    private TeamRepository teamRepository;
    private MatchupService matchupService;
    private LocalDateTimeSupplier localDateTimeSupplier;
    private LocalDateTime updateDateTime;
    private Team favoredTeam;
    private Team underdogTeam;
    private Matchup expectedMatchup;

    @BeforeEach
    public void setup() {
        matchupRepository = mock(MatchupRepository.class);
        teamRepository = mock(TeamRepository.class);
        localDateTimeSupplier = mock(LocalDateTimeSupplier.class);
        matchupService = spy(new MatchupService(matchupRepository, teamRepository, localDateTimeSupplier));

        favoredTeam = Team.builder()
            .teamId(1L)
            .teamName("Team 1")
            .teamRegion("City 1")
            .build();

        underdogTeam = Team.builder()
            .teamId(2L)
            .teamName("Team 2")
            .teamRegion("City 2")
            .build();

        updateDateTime = LocalDateTime.now();
        when(localDateTimeSupplier.get()).thenReturn(updateDateTime);

        expectedMatchup = Matchup.builder()
            .matchupLine(1.5)
            .matchupStart(updateDateTime)
            .matchupWeek("1")
            .favoredTeam(favoredTeam)
            .underdogTeam(underdogTeam)
            .lastUpdated(updateDateTime)
            .isFinal(false)
            .build();

        when(teamRepository.findById(1L)).thenReturn(Optional.of(favoredTeam));
        when(teamRepository.findById(2L)).thenReturn(Optional.of(underdogTeam));
        when(matchupRepository.findById(any())).thenReturn(Optional.of(expectedMatchup));
    }

    @Test
    public void testAddMatchup() {
        MatchupDTO matchupDto = MatchupDTO
            .builder()
            .matchupLine(1.5)
            .matchupStart(updateDateTime)
            .matchupWeek("1")
            .favoredTeamId(1L)
            .underdogTeamId(2L)
            .build();

        when(matchupRepository.save(expectedMatchup)).thenReturn(expectedMatchup);
        Matchup matchup = matchupService.addMatchup(matchupDto);

        verify(matchupRepository, times(1)).save(any());
        assertEquals(expectedMatchup, matchup);
    }

    @Test
    public void testUpdateMatchup() {
        MatchupDTO matchupDto = MatchupDTO
            .builder()
            .matchupLine(3.0)
            .matchupStart(updateDateTime)
            .matchupWeek("1")
            .favoredTeamId(1L)
            .underdogTeamId(2L)
            .favoredTeamScore(10)
            .underdogTeamScore(7)
            .isFinal(true)
            .build();

        expectedMatchup.setMatchupLine(3.0);
        expectedMatchup.setMatchupStart(updateDateTime);
        expectedMatchup.setMatchupWeek("1");
        expectedMatchup.setFavoredTeam(favoredTeam);
        expectedMatchup.setUnderdogTeam(underdogTeam);
        expectedMatchup.setFavoredTeamScore(10);
        expectedMatchup.setUnderdogTeamScore(7);
        expectedMatchup.setFinal(true);
        expectedMatchup.setLastUpdated(updateDateTime);

        when(matchupRepository.save(expectedMatchup)).thenReturn(expectedMatchup);
        Matchup matchup = matchupService.updateMatchup(1L, matchupDto);

        verify(matchupRepository, times(1)).save(any());
        assertEquals(expectedMatchup, matchup);
    }

    @Test
    public void testUpdateScore() {
        MatchupScoreDTO matchupScoreDto = MatchupScoreDTO
            .builder()
            .favoredTeamScore(10)
            .underdogTeamScore(7)
            .isFinal(true)
            .build();

        expectedMatchup.setFavoredTeamScore(10);
        expectedMatchup.setUnderdogTeamScore(7);
        when(matchupRepository.save(expectedMatchup)).thenReturn(expectedMatchup);

        Matchup matchup = matchupService.updateMatchupScore(1L, matchupScoreDto);
        verify(matchupRepository, times(1)).save(any());
        assertEquals(expectedMatchup, matchup);
    }

    @Test
    public void testUpdateFinal() {
        expectedMatchup.setFinal(true);
        when(matchupRepository.save(expectedMatchup)).thenReturn(expectedMatchup);

        Matchup matchup = matchupService.updateMatchupFinal(1L, true);
        verify(matchupRepository, times(1)).save(any());
        assertEquals(expectedMatchup, matchup);
    }
}
