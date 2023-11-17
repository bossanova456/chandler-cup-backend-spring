package com.chandlercup.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity(name = "season_scores")
public class SeasonScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seasonScoreId;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String seasonYear;
    @Column(name = "season_score")
    private Integer score;
    private OffsetDateTime lastUpdated;
}
