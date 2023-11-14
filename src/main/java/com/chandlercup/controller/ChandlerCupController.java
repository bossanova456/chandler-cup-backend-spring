package com.chandlercup.controller;

import com.chandlercup.model.Matchup;
import com.chandlercup.service.MatchupService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class ChandlerCupController {
    private final MatchupService matchupService;

//    @PostMapping("/matchups")
//    public ResponseEntity<Matchup> addMatchup(@RequestBody Matchup matchup) {
//        return ResponseEntity.ok(matchupService.addMatchup(matchup));
//    }
//
//    @GetMapping("/matchups/{matchupId}")
//    public ResponseEntity<Matchup> getMatchup(@RequestParam Long matchupId) {
//        return ResponseEntity.ok(matchupService.getMatchup(matchupId));
//    }
}
