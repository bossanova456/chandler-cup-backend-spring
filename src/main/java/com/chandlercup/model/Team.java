package com.chandlercup.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.boot.jackson.JsonComponent;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@JsonComponent
@Table(name = "teams")
public class Team implements Serializable {
    @Id
    @Column(name = "team_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;
    private String teamName;
    private String teamRegion;
}
