package com.chandlercup.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "matchups")
public class Matchup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long matchupId;
    private String seasonYear;
    private String seasonWeek;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "favored_team_id")
    private Team favoredTeam;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "underdog_team_id")
    private Team underdogTeam;
    private Double matchupLine;
    private Integer favoredTeamScore;
    private Integer underdogTeamScore;
    private OffsetDateTime matchupStart;
    private OffsetDateTime lastUpdated;
    private boolean isFinal;

}
