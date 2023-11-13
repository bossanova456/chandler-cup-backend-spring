package com.chandlercup.controller;

import com.chandlercup.dto.MatchupDTO;
import com.chandlercup.dto.MatchupScoreDTO;
import com.chandlercup.model.Matchup;
import com.chandlercup.service.MatchupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/matchups")
@RequiredArgsConstructor
@Slf4j
public class MatchupController {

    private final MatchupService matchupService;

    @PostMapping("")
    public ResponseEntity<Matchup> addTeam(@RequestBody Matchup matchup) {
        return ResponseEntity.ok(matchupService.addMatchup(matchup));
    }

    @GetMapping("/{matchupId}")
    public ResponseEntity<Matchup> getTeam(@PathVariable Long matchupId) {
        return ResponseEntity.ok(matchupService.getMatchup(matchupId));
    }

    @PatchMapping("/{matchupId}")
    public ResponseEntity<Matchup> updateMatchup(@PathVariable Long matchupId, @RequestBody MatchupDTO matchupDto) {
        return ResponseEntity.ok(matchupService.updateMatchup(matchupId, matchupDto));
    }

    @PatchMapping("/{matchupId}/score")
    public ResponseEntity<Matchup> updateMatchupScore(@PathVariable Long matchupId, @RequestBody MatchupScoreDTO matchupScoreDto) {
        return ResponseEntity.ok(matchupService.updateMatchupScore(matchupId, matchupScoreDto));
    }

    @PatchMapping("/{matchupId}/final/{isFinal}")
    public ResponseEntity<Matchup> updateMatchupFinal(@PathVariable Long matchupId, @PathVariable boolean isFinal) {
        return ResponseEntity.ok(matchupService.updateMatchupFinal(matchupId, isFinal));
    }
}
