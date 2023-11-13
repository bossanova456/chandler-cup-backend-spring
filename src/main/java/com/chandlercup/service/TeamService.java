package com.chandlercup.service;

import com.chandlercup.model.Team;
import com.chandlercup.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    public Team addTeam(Team team) {
        return teamRepository.save(team);
    }

    public Team getTeam(Long teamId) {
        return teamRepository.findById(teamId).orElse(null);
    }
}
