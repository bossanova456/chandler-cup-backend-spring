package com.chandlercup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PickDTO {
    private Long matchupId;
    private Long pickTeamId;
    private Long userId;
    private String seasonYear;
    private String seasonWeek;
}
