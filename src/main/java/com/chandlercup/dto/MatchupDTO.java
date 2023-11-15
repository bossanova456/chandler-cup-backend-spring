package com.chandlercup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchupDTO {
    private Long matchupId;
    private String seasonYear;
    private String seasonWeek;
    private Long favoredTeamId;
    private Long underdogTeamId;
    private Double matchupLine;
    private LocalDateTime matchupStart;
    private Integer favoredTeamScore;
    private Integer underdogTeamScore;
    private String lastUpdated;
    private boolean isFinal;
}
