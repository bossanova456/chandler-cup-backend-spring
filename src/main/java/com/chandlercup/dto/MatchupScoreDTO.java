package com.chandlercup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchupScoreDTO {
    private Long matchupId;
    private Integer favoredTeamScore;
    private Integer underdogTeamScore;
    private boolean isFinal;
}
