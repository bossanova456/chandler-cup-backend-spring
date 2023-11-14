package com.chandlercup.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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
    private String matchupWeek;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "favored_team_id")
    private Team favoredTeam;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "underdog_team_id")
    private Team underdogTeam;
    private Double matchupLine;
    private Integer favoredTeamScore;
    private Integer underdogTeamScore;
    private LocalDateTime matchupStart;
    private LocalDateTime lastUpdated;
    private boolean isFinal;

}
