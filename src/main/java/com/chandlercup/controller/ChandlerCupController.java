package com.chandlercup.controller;

import com.chandlercup.model.Matchup;
import com.chandlercup.service.MatchupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChandlerCupController {
    private final MatchupService matchupService;

    public ChandlerCupController(MatchupService matchupService) {
        this.matchupService = matchupService;
    }

    @PostMapping("/matchups")
    public ResponseEntity<Matchup> addMatchup(Matchup matchup) {
        return ResponseEntity.ok(matchupService.addMatchup(matchup));
    }

    @GetMapping("/matchups/{matchupId}")
    public ResponseEntity<Matchup> getMatchup(String matchupId) {
        return ResponseEntity.ok(matchupService.getMatchup(matchupId));
    }
}
