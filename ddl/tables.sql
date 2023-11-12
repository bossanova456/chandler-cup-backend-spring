CREATE TABLE roles (
    role_id SERIAL PRIMARY KEY NOT NULL,
    description TEXT NOT NULL
);

CREATE TABLE users (
    user_id SERIAL PRIMARY KEY NOT NULL,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (role_id)
        REFERENCES roles (role_id)
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
    matchup_start TIMESTAMP NOT NULL,
    favored_team_score INT,
    underdog_team_score INT,
    last_updated TIMESTAMP NOT NULL,
    is_final BOOLEAN NOT NULL,
    FOREIGN KEY (favored_team_id)
        REFERENCES teams (team_id),
    FOREIGN KEY (underdog_team_id)
        REFERENCES teams (team_id)
);

CREATE TABLE seasons (
    season_id SERIAL PRIMARY KEY NOT NULL,
    season_year BIGINT NOT NULL
);

CREATE TABLE scores (
    score_id SERIAL PRIMARY KEY NOT NULL,
    user_id TEXT NOT NULL,
    season_id BIGINT NOT NULL,
    wins INT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (user_id),
    FOREIGN KEY (season_id)
        REFERENCES seasons (season_id)
);

CREATE TABLE picks (
    pick_id SERIAL PRIMARY KEY NOT NULL,
    user_id TEXT NOT NULL,
    matchup_id BIGINT NOT NULL,
    pick_team_id BIGINT NOT NULL,
    FOREIGN KEY (user_id)
        REFERENCES users (user_id),
    FOREIGN KEY (matchup_id)
        REFERENCES matchups (matchup_id),
    FOREIGN KEY (pick_team_id)
        REFERENCES teams (team_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS matchups_index ON matchups (matchup_week, favored_team_id, underdog_team_id)
    WHERE (matchup_week IS NOT NULL AND favored_team_id IS NOT NULL AND underdog_team_id IS NOT NULL);