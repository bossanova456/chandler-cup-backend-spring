package com.chandlercup.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "picks")
public class Pick {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pickId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private String seasonYear;
    private String seasonWeek;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "matchup_id")
    private Matchup matchup;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pick_team_id")
    private Team pickTeam;
    private OffsetDateTime lastUpdated;
}
