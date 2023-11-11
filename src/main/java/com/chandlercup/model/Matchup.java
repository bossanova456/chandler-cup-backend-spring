package com.chandlercup.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "matchups")
public class Matchup {
    @Id
    private String matchupId;
    private String matchupWeek;
    private String favoredTeamId;
    private String underdogTeamId;
    private Double matchupLine;
    private LocalDateTime matchupStart;
    private Integer favoredTeamScore;
    private Integer underdogTeamScore;
    private LocalDateTime lastUpdated;
    private boolean isFinal;

}
