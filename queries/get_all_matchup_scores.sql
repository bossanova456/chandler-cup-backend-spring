SELECT
    p.season_year,
    p.season_week,
    u.first_name,
    (SELECT team_name FROM teams WHERE team_id = m.favored_team_id) as favored_team_name,
    m.matchup_line,
    (SELECT team_name FROM teams WHERE team_id = m.underdog_team_id) as underdog_team_name,
    (SELECT team_name FROM teams WHERE team_id = p.pick_team_id) as pick_team_name,
    (SELECT team_name FROM teams WHERE team_id = (
        CASE
            WHEN favored_team_score > underdog_team_score THEN favored_team_id
            ELSE underdog_team_id
            END
        )) as winning_team_name,
    m.favored_team_score,
    m.underdog_team_score
FROM users u, picks p, matchups m
WHERE u.user_id = p.user_id
  AND p.matchup_id = m.matchup_id
  AND u.user_id = 1
  AND m.season_week = '2'
ORDER BY m.matchup_id