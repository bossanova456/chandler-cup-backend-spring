package com.chandlercup.service;

import com.chandlercup.dto.MatchupDTO;
import com.chandlercup.dto.MatchupScoreDTO;
import com.chandlercup.model.Matchup;
import com.chandlercup.model.Team;
import com.chandlercup.repository.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.chandlercup.supplier.OffsetDateTimeSupplier;
import com.chandlercup.testdata.MockMatchupRepository;
import com.chandlercup.testdata.MockTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// TODO: Add tests for exception handling, scoring updates

public class MatchupServiceTest {
    private OffsetDateTimeSupplier offsetDateTimeSupplier;
    private OffsetDateTime updateDateTime;
    private MatchupRepository matchupRepository;
    private PickRepository pickRepository;
    private UserRepository userRepository;
    private WeeklyScoreRepository weeklyScoreRepository;
    private SeasonScoreRepository seasonScoreRepository;
    private TeamRepository teamRepository;
    private MatchupService matchupService;
    private ScoreService scoreService;
    private Team favoredTeam;
    private Team underdogTeam;
    private Matchup expectedMatchup;

    @BeforeEach
    public void setup() {
        matchupRepository = mock(MatchupRepository.class);
        teamRepository = mock(TeamRepository.class);
        pickRepository = mock(PickRepository.class);
        userRepository = mock(UserRepository.class);
        weeklyScoreRepository = mock(WeeklyScoreRepository.class);
        seasonScoreRepository = mock(SeasonScoreRepository.class);
        offsetDateTimeSupplier = mock(OffsetDateTimeSupplier.class);
        scoreService = spy(new ScoreService(weeklyScoreRepository, seasonScoreRepository, matchupRepository, pickRepository, userRepository, offsetDateTimeSupplier));
        matchupService = spy(new MatchupService(matchupRepository, teamRepository, scoreService, offsetDateTimeSupplier));

        favoredTeam = MockTeamRepository.getFavoredTeam();
        underdogTeam = MockTeamRepository.getUnderdogTeam();

        updateDateTime = OffsetDateTime.now();
        when(offsetDateTimeSupplier.get()).thenReturn(updateDateTime);

        expectedMatchup = MockMatchupRepository.getMatchup(favoredTeam, underdogTeam, updateDateTime);

        when(teamRepository.findById(favoredTeam.getTeamId())).thenReturn(Optional.of(favoredTeam));
        when(teamRepository.findById(underdogTeam.getTeamId())).thenReturn(Optional.of(underdogTeam));
        when(matchupRepository.findById(any())).thenReturn(Optional.of(expectedMatchup));
    }

    @Test
    public void testAddMatchup() {
        MatchupDTO matchupDto = MatchupDTO
            .builder()
            .matchupLine(1.5)
            .matchupStart(updateDateTime)
            .seasonYear("2023")
            .seasonWeek("1")
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
            .seasonWeek("1")
            .seasonYear("2023")
            .favoredTeamId(1L)
            .underdogTeamId(2L)
            .favoredTeamScore(10)
            .underdogTeamScore(7)
            .isFinal(true)
            .build();

        expectedMatchup.setMatchupLine(3.0);
        expectedMatchup.setMatchupStart(updateDateTime);
        expectedMatchup.setSeasonWeek("1");
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
