package com.chandlercup.service;

import com.chandlercup.dto.PickDTO;
import com.chandlercup.model.Matchup;
import com.chandlercup.model.Pick;
import com.chandlercup.model.Team;
import com.chandlercup.model.User;
import com.chandlercup.repository.MatchupRepository;
import com.chandlercup.repository.PickRepository;
import com.chandlercup.repository.TeamRepository;
import com.chandlercup.repository.UserRepository;
import com.chandlercup.supplier.OffsetDateTimeSupplier;
import com.chandlercup.testdata.MockMatchupRepository;
import com.chandlercup.testdata.MockTeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.OffsetDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class PickServiceTest {
    private PickRepository pickRepository;
    private MatchupRepository matchupRepository;
    private TeamRepository teamRepository;
    private UserRepository userRepository;
    private PickService pickService;
    private OffsetDateTimeSupplier offsetDateTimeSupplier;
    private Team favoredTeam;
    private Team underdogTeam;
    private Pick expectedPick;

    @BeforeEach
    public void setup() {
        pickRepository = mock(PickRepository.class);
        matchupRepository = mock(MatchupRepository.class);
        teamRepository = mock(TeamRepository.class);
        userRepository = mock(UserRepository.class);
        offsetDateTimeSupplier = mock(OffsetDateTimeSupplier.class);
        pickService = spy(new PickService(pickRepository, matchupRepository, teamRepository, userRepository, offsetDateTimeSupplier));

        favoredTeam = MockTeamRepository.getFavoredTeam();
        underdogTeam = MockTeamRepository.getUnderdogTeam();

        when(teamRepository.findById(favoredTeam.getTeamId())).thenReturn(Optional.of(favoredTeam));
        when(teamRepository.findById(underdogTeam.getTeamId())).thenReturn(Optional.of(underdogTeam));

        OffsetDateTime updateDateTime = OffsetDateTime.now();
        when(offsetDateTimeSupplier.get()).thenReturn(updateDateTime);

        Matchup matchup = MockMatchupRepository.getMatchup(favoredTeam, underdogTeam, updateDateTime);

        when(matchupRepository.findById(any())).thenReturn(Optional.of(matchup));

        User user = User.builder()
                .userId(1L)
                .firstName("User 1")
                .lastName("Last Name")
                .build();

        expectedPick = Pick.builder()
                .matchup(matchup)
                .pickTeam(favoredTeam)
                .seasonWeek("1")
                .seasonYear("2023")
                .user(user)
                .lastUpdated(updateDateTime)
                .build();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        when(pickRepository.findById(any())).thenReturn(Optional.of(expectedPick));
    }

    @Test
    public void testAddPick() {
        PickDTO pickDTO = PickDTO
                .builder()
                .matchupId(1L)
                .pickTeamId(1L)
                .seasonWeek("1")
                .seasonYear("2023")
                .build();

        when(pickRepository.save(expectedPick)).thenReturn(expectedPick);
        Pick pick = pickService.addPick(pickDTO);

        verify(pickRepository, times(1)).save(pick);
        assertEquals(expectedPick, pick);
    }

    @Test
    public void testUpdatePick() {
        PickDTO pickDTO = PickDTO
                .builder()
                .matchupId(1L)
                .pickTeamId(2L)
                .seasonWeek("1")
                .seasonYear("2023")
                .build();

        expectedPick.setPickTeam(underdogTeam);
        when(pickRepository.save(expectedPick)).thenReturn(expectedPick);

        Pick pick = pickService.updatePick(1L, pickDTO);
        verify(pickRepository, times(1)).save(pick);
        assertEquals(expectedPick, pick);
    }
}
