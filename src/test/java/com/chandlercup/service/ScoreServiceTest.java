package com.chandlercup.service;

import com.chandlercup.repository.*;
import com.chandlercup.supplier.OffsetDateTimeSupplier;
import org.junit.jupiter.api.BeforeEach;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;

public class ScoreServiceTest {
    private WeeklyScoreRepository weeklyScoreRepository;
    private SeasonScoreRepository seasonScoreRepository;
    private MatchupRepository matchupRepository;
    private PickRepository pickRepository;
    private UserRepository userRepository;
    private OffsetDateTimeSupplier offsetDateTimeSupplier;
    private ScoreService scoreService;

    @BeforeEach
    public void setUp() {
        offsetDateTimeSupplier = mock(OffsetDateTimeSupplier.class);
        matchupRepository = mock(MatchupRepository.class);
        pickRepository = mock(PickRepository.class);
        userRepository = mock(UserRepository.class);
        weeklyScoreRepository = mock(WeeklyScoreRepository.class);
        seasonScoreRepository = mock(SeasonScoreRepository.class);
        
        scoreService = spy(new ScoreService(weeklyScoreRepository, seasonScoreRepository, matchupRepository, pickRepository, userRepository, offsetDateTimeSupplier));
    }
}
