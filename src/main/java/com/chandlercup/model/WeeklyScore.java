package com.chandlercup.model;

import jakarta.persistence.*;
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
@Table(name = "weekly_scores")
public class WeeklyScore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long weeklyScoreId;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    private String seasonYear;
    private String seasonWeek;
    @Column(name = "weekly_score")
    private Integer score;
    private LocalDateTime lastUpdated;
}
