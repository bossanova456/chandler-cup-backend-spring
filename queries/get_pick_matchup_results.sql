SELECT
    picks.user_id,
    picks.matchup_id,
    winning_team_id,
    favored_team_score,
    underdog_team_score,
    is_final
FROM
   (
       SELECT
           matchup_id,
           season_year,
           season_week,
           favored_team_id,
           underdog_team_id,
           favored_team_score,
           underdog_team_score,
           is_final,
           CASE
               WHEN favored_team_score > underdog_team_score + matchup_line THEN favored_team_id
               ELSE underdog_team_id
               END AS winning_team_id
       FROM matchups
   ) as results, picks
WHERE picks.matchup_id = results.matchup_id
  AND picks.season_year = results.season_year
  AND picks.season_week = results.season_week
  AND picks.pick_team_id = results.winning_team_id
  AND is_final = true;