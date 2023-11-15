CREATE TABLE roles (
    role_name TEXT PRIMARY KEY NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    role_name TEXT NOT NULL,
    FOREIGN KEY (role_name)
        REFERENCES roles (role_name)
);

CREATE TABLE teams (
    team_id SERIAL PRIMARY KEY NOT NULL,
    team_name TEXT NOT NULL,
    team_region TEXT NOT NULL
);

CREATE TABLE matchups (
    matchup_id SERIAL PRIMARY KEY NOT NULL,
    matchup_week TEXT NOT NULL,
    favored_team_id BIGINT NOT NULL,
    underdog_team_id BIGINT NOT NULL,
    matchup_line INT NOT NULL,
    favored_team_score INT,
    underdog_team_score INT,
    matchup_start TIMESTAMP NOT NULL,
    last_updated TIMESTAMP NOT NULL,
    is_final BOOLEAN NOT NULL,
    FOREIGN KEY (favored_team_id)
        REFERENCES teams (team_id),
    FOREIGN KEY (underdog_team_id)
        REFERENCES teams (team_id)
);

CREATE TABLE picks (
    pick_id SERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    season_year TEXT NOT NULL,
    season_week TEXT NOT NULL,
    matchup_id BIGINT NOT NULL,
    pick_team_id BIGINT NOT NULL,
    last_updated TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id)
       REFERENCES users (user_id),
    FOREIGN KEY (matchup_id)
       REFERENCES matchups (matchup_id),
    FOREIGN KEY (pick_team_id)
       REFERENCES teams (team_id)
);

CREATE TABLE seasons (
    season_id SERIAL PRIMARY KEY NOT NULL,
    season_year BIGINT NOT NULL
);

CREATE TABLE weekly_scores (
    weekly_score_id SERIAL PRIMARY KEY NOT NULL,
    season_year TEXT NOT NULL,
    season_week TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    matchup_id BIGINT NOT NULL,
    weekly_score INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (user_id),
    FOREIGN KEY (matchup_id)
        REFERENCES matchups (matchup_id)
);

CREATE TABLE season_scores (
    season_score_id SERIAL PRIMARY KEY NOT NULL,
    season_year TEXT NOT NULL,
    user_id BIGINT NOT NULL,
    season_score INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (user_id)
);

CREATE TABLE scores (
    score_id SERIAL PRIMARY KEY NOT NULL,
    user_id BIGINT NOT NULL,
    season_id BIGINT NOT NULL,
    wins INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (user_id),
    FOREIGN KEY (season_id)
        REFERENCES seasons (season_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS matchups_index ON matchups (matchup_week, favored_team_id, underdog_team_id)
    WHERE (matchup_week IS NOT NULL AND favored_team_id IS NOT NULL AND underdog_team_id IS NOT NULL);
CREATE UNIQUE INDEX IF NOT EXISTS picks_index ON picks (matchup_id, user_id, season_year, season_week)
    WHERE (matchup_id IS NOT NULL AND user_id IS NOT NULL AND season_year IS NOT NULL AND season_week IS NOT NULL);
CREATE UNIQUE INDEX IF NOT EXISTS weekly_scores_index ON weekly_scores (season_year, season_week, user_id, matchup_id)
    WHERE (season_year IS NOT NULL AND season_week IS NOT NULL AND user_id IS NOT NULL AND matchup_id IS NOT NULL);
CREATE UNIQUE INDEX IF NOT EXISTS season_scores_index ON season_scores (season_year, user_id)
    WHERE (season_year IS NOT NULL AND user_id IS NOT NULL);

INSERT INTO roles (role_name, description) VALUES ('admin', 'admin');
INSERT INTO roles (role_name, description) VALUES ('user', 'user');

INSERT INTO users (first_name, last_name, role_name) VALUES ('taylor', 'andrews', 'admin');

INSERT INTO teams (team_name, team_region) VALUES ('Falcons', 'Atlanta');
INSERT INTO teams (team_name, team_region) VALUES ('Ravens', 'Baltimore');
INSERT INTO teams (team_name, team_region) VALUES ('Bills', 'Buffalo');
INSERT INTO teams (team_name, team_region) VALUES ('Panthers', 'Carolina');
INSERT INTO teams (team_name, team_region) VALUES ('Bears', 'Chicago');
INSERT INTO teams (team_name, team_region) VALUES ('Bengals', 'Cincinnati');
INSERT INTO teams (team_name, team_region) VALUES ('Browns', 'Cleveland');
INSERT INTO teams (team_name, team_region) VALUES ('Cowboys', 'Dallas');
INSERT INTO teams (team_name, team_region) VALUES ('Broncos', 'Denver');
INSERT INTO teams (team_name, team_region) VALUES ('Lions', 'Detroit');
INSERT INTO teams (team_name, team_region) VALUES ('Packers', 'Green Bay');
INSERT INTO teams (team_name, team_region) VALUES ('Texans', 'Houston');
INSERT INTO teams (team_name, team_region) VALUES ('Colts', 'Indianapolis');
INSERT INTO teams (team_name, team_region) VALUES ('Jaguars', 'Jacksonville');
INSERT INTO teams (team_name, team_region) VALUES ('Chiefs', 'Kansas City');
INSERT INTO teams (team_name, team_region) VALUES ('Chargers', 'Los Angeles');
INSERT INTO teams (team_name, team_region) VALUES ('Rams', 'Los Angeles');
INSERT INTO teams (team_name, team_region) VALUES ('Dolphins', 'Miami');
INSERT INTO teams (team_name, team_region) VALUES ('Vikings', 'Minnesota');
INSERT INTO teams (team_name, team_region) VALUES ('Patriots', 'New England');
INSERT INTO teams (team_name, team_region) VALUES ('Saints', 'New Orleans');
INSERT INTO teams (team_name, team_region) VALUES ('Giants', 'New York');
INSERT INTO teams (team_name, team_region) VALUES ('Jets', 'New York');
INSERT INTO teams (team_name, team_region) VALUES ('Raiders', 'Las Vegas');
INSERT INTO teams (team_name, team_region) VALUES ('Eagles', 'Philadelphia');
INSERT INTO teams (team_name, team_region) VALUES ('Steelers', 'Pittsburgh');
INSERT INTO teams (team_name, team_region) VALUES ('49ers', 'San Francisco');
INSERT INTO teams (team_name, team_region) VALUES ('Seahawks', 'Seattle');
INSERT INTO teams (team_name, team_region) VALUES ('Buccaneers', 'Tampa Bay');
INSERT INTO teams (team_name, team_region) VALUES ('Titans', 'Tennessee');
INSERT INTO teams (team_name, team_region) VALUES ('Commanders', 'Washington');
INSERT INTO teams (team_name, team_region) VALUES ('Cardinals', 'Arizona');

INSERT INTO matchups (matchup_week, favored_team_id, underdog_team_id, matchup_line, favored_team_score, underdog_team_score, matchup_start, last_updated, is_final)
    VALUES ('1', 1, 2, 3.5, 7, 10, TO_TIMESTAMP('2023-11-11 04:00:00', 'YYYY-MM-DD HH:MI:SS'), TO_TIMESTAMP('2023-11-11 04:00:00', 'YYYY-MM-DD HH:MI:SS'), false);