package com.chandlercup.controller;

import com.chandlercup.dto.PickDTO;
import com.chandlercup.model.Pick;
import com.chandlercup.service.PickService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pick")
@RequiredArgsConstructor
@Slf4j
public class PickController {

    private final PickService pickService;

    @PostMapping("")
    public ResponseEntity<Pick> addPick(@RequestBody PickDTO pickDto) {
        return ResponseEntity.ok(pickService.addPick(pickDto));
    }

    @PatchMapping("/{pickId}")
    public ResponseEntity<Pick> updatePick(@PathVariable Long pickId, @RequestBody PickDTO pickDTO) {
        return ResponseEntity.ok(pickService.updatePick(pickId, pickDTO));
    }

    @GetMapping("/{pickId}")
    public ResponseEntity<Pick> getPick(@PathVariable Long pickId) {
        return ResponseEntity.ok(pickService.getPick(pickId));
    }
}
