package com.chandlercup.service;

import com.chandlercup.dto.PickDTO;
import com.chandlercup.exception.MatchupNotFoundException;
import com.chandlercup.exception.PickNotFoundException;
import com.chandlercup.exception.TeamNotFoundException;
import com.chandlercup.exception.UserNotFoundException;
import com.chandlercup.model.Pick;
import com.chandlercup.model.Team;
import com.chandlercup.model.User;
import com.chandlercup.repository.MatchupRepository;
import com.chandlercup.repository.PickRepository;
import com.chandlercup.repository.TeamRepository;
import com.chandlercup.repository.UserRepository;
import com.chandlercup.supplier.LocalDateTimeSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PickService {
    private final PickRepository pickRepository;
    private final MatchupRepository matchupRepository;
    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final LocalDateTimeSupplier localDateTimeSupplier;

    public Pick addPick(PickDTO pickDto) {
        return pickRepository.save(
            Pick.builder()
                .matchup(matchupRepository.findById(
                    pickDto.getMatchupId())
                        .orElseThrow(() -> new MatchupNotFoundException("Could not find matchup with ID: " + pickDto.getMatchupId()))
                )
                .pickTeam(teamRepository.findById(
                    pickDto.getPickTeamId())
                        .orElseThrow(() -> new TeamNotFoundException("Could not find pick team with ID: " + pickDto.getPickTeamId()))
                )
                .user(userRepository.findById(
                    pickDto.getUserId())
                        .orElseThrow(() -> new UserNotFoundException("Could not find user with ID: " + pickDto.getUserId()))
                )
                .seasonWeek(pickDto.getSeasonWeek())
                .seasonYear(pickDto.getSeasonYear())
                .lastUpdated(localDateTimeSupplier.get())
                .build()
        );
    }

    public Pick updatePick(Long pickId, PickDTO pickDto) {
        Pick pick = getPick(pickId);
        Team pickTeam = teamRepository.findById(
            pickDto.getPickTeamId())
                .orElseThrow(() -> new TeamNotFoundException("Could not find pick team with ID: " + pickDto.getPickTeamId()));
        User user = userRepository.findById(
            pickDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("Could not find user with ID: " + pickDto.getUserId()));

        pick.setPickTeam(pickTeam);
        pick.setUser(user);
        pick.setSeasonYear(pickDto.getSeasonYear());
        pick.setSeasonWeek(pick.getSeasonWeek());
        pick.setLastUpdated(localDateTimeSupplier.get());

        return pickRepository.save(pick);
    }

    public Pick getPick(Long pickId) {
        return pickRepository.findById(pickId)
            .orElseThrow(() -> new PickNotFoundException("Could not find pick with ID: " + pickId));
    }
}
