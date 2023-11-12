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
    private Long favoredTeamId;
    private Long underdogTeamId;
    private Double matchupLine;
    private LocalDateTime matchupStart;
    private Integer favoredTeamScore;
    private Integer underdogTeamScore;
    @UpdateTimestamp
    private LocalDateTime lastUpdated;
    private boolean isFinal;

}
