
DROP TABLE IF EXISTS passnrun_db.NEWS;
DROP TABLE IF EXISTS passnrun_db.TACTIC_PLAYER;
DROP TABLE IF EXISTS passnrun_db.FINANCE;
DROP TABLE IF EXISTS passnrun_db.TEAM_PLAYER;
DROP TABLE IF EXISTS passnrun_db.PLAYER_PERFORMANCE;
DROP TABLE IF EXISTS passnrun_db.INJURY;
DROP TABLE IF EXISTS passnrun_db.BAN;
DROP TABLE IF EXISTS passnrun_db.PLAYER_SKILL;
DROP TABLE IF EXISTS passnrun_db.GAME_DETAIL;
DROP TABLE IF EXISTS passnrun_db.LEAGUE_TEAM;
DROP TABLE IF EXISTS passnrun_db.TEAM_MANAGER;
DROP TABLE IF EXISTS passnrun_db.TACTIC;
DROP TABLE IF EXISTS passnrun_db.GAME;
DROP TABLE IF EXISTS passnrun_db.LEAGUE;
DROP TABLE IF EXISTS passnrun_db.TEAM;
DROP TABLE IF EXISTS passnrun_db.MANAGER;
DROP TABLE IF EXISTS passnrun_db.SEASON;
DROP TABLE IF EXISTS passnrun_db.STADIUM;
DROP TABLE IF EXISTS passnrun_db.MODIFY_COUNT;
DROP TABLE IF EXISTS passnrun_db.PLAYER;

CREATE TABLE passnrun_db.PLAYER (
       ID INTEGER NOT NULL
     , NAME VARCHAR(64)
     , SURNAME VARCHAR(64)
     , POSITION VARCHAR(16)
     , BIRTHDATE DATE
     , CURRENT_TEAM INTEGER DEFAULT 0
     , FITNESS INTEGER
     , MORALE INTEGER
     , QUALITY INTEGER
     , LATEST_FORM VARCHAR(16)
     , PRIMARY KEY (ID)
);

CREATE TABLE passnrun_db.MODIFY_COUNT (
       ID INTEGER NOT NULL AUTO_INCREMENT
     , MANAGER_ID INTEGER
     , TEAM_ID INTEGER
     , LEAGUE_ID INTEGER
     , SEASON_ID INTEGER
     , MAX_NEWS INTEGER DEFAULT 0
     , MAX_GAMES INTEGER DEFAULT 0
     , MAX_FINANCE INTEGER DEFAULT 0
     , PRIMARY KEY (ID)
);

CREATE TABLE passnrun_db.STADIUM (
       ID INTEGER NOT NULL
     , NAME CHAR(128)
     , CAPACITY INTEGER
     , PRIMARY KEY (ID)
);

CREATE TABLE passnrun_db.SEASON (
       ID INTEGER NOT NULL
     , PRIMARY KEY (ID)
);

CREATE TABLE passnrun_db.MANAGER (
       ID INTEGER NOT NULL
     , FIRSTNAME VARCHAR(64)
     , LASTNAME VARCHAR(64)
     , NATIONALITY VARCHAR(64)
     , LANGUAGE VARCHAR(64)
     , BIRTHDATE DATE
     , REGISTER_DATE DATETIME
     , CURRENT_TEAM INTEGER DEFAULT 0
     , DEVICE VARCHAR(16)
     , DEVICE_ID VARCHAR(64)
     , PRIMARY KEY (ID)
);

CREATE TABLE passnrun_db.TEAM (
       ID INTEGER NOT NULL
     , NAME VARCHAR(64) NOT NULL
     , COLOR1 VARCHAR(16)
     , COLOR2 VARCHAR(16)
     , CURRENT_LEAGUE INTEGER DEFAULT 0
     , CURRENT_MANAGER INTEGER
     , STADIUM_ID INTEGER
     , PRIMARY KEY (ID)
     , INDEX (STADIUM_ID)
     , CONSTRAINT FK_TEAM_1 FOREIGN KEY (STADIUM_ID)
                  REFERENCES passnrun_db.STADIUM (ID)
);

CREATE TABLE passnrun_db.LEAGUE (
       ID INTEGER NOT NULL
     , NAME VARCHAR(16)
     , LEVEL INTEGER
     , SIZE INTEGER
     , RELEGATIONS INTEGER
     , PROMOTIONS INTEGER
     , PLAYOFFS INTEGER
     , PARENT_ID INTEGER
     , STATUS INTEGER
     , PRIMARY KEY (ID)
     , INDEX (PARENT_ID)
     , CONSTRAINT FK_LEAGUE_1 FOREIGN KEY (PARENT_ID)
                  REFERENCES passnrun_db.LEAGUE (ID)
);

CREATE TABLE passnrun_db.GAME (
       ID INTEGER NOT NULL
     , TEAM1 INTEGER
     , TEAM2 INTEGER
     , SEASON_ID INTEGER
     , LEAGUE_ID INTEGER
     , WEEK INTEGER
     , SCORE1 INTEGER
     , SCORE2 INTEGER
     , ATTENDANCE INTEGER
     , PERCENT_ATT INTEGER
     , GAME_DATE DATETIME
     , PRIMARY KEY (ID)
     , INDEX (TEAM1)
     , CONSTRAINT FK_GAME_1 FOREIGN KEY (TEAM1)
                  REFERENCES passnrun_db.TEAM (ID)
     , INDEX (TEAM2)
     , CONSTRAINT FK_GAME_2 FOREIGN KEY (TEAM2)
                  REFERENCES passnrun_db.TEAM (ID)
     , INDEX (LEAGUE_ID)
     , CONSTRAINT FK_GAME_3 FOREIGN KEY (LEAGUE_ID)
                  REFERENCES passnrun_db.LEAGUE (ID)
);

CREATE TABLE passnrun_db.TACTIC (
       ID INTEGER NOT NULL
     , TEAM_ID INTEGER
     , FORMATION INTEGER
     , PRIMARY KEY (ID)
     , INDEX (TEAM_ID)
     , CONSTRAINT FK_TACTIC_1 FOREIGN KEY (TEAM_ID)
                  REFERENCES passnrun_db.TEAM (ID)
);

CREATE TABLE passnrun_db.TEAM_MANAGER (
       ID INTEGER NOT NULL
     , MANAGER_ID INTEGER NOT NULL
     , TEAM_ID INTEGER NOT NULL
     , SEASON_ID INTEGER NOT NULL
     , PLAYED INTEGER
     , WIN INTEGER
     , DRAW INTEGER
     , LOSS INTEGER
     , PRIMARY KEY (ID)
     , INDEX (MANAGER_ID)
     , CONSTRAINT FK_TEAMMANAGER_1 FOREIGN KEY (MANAGER_ID)
                  REFERENCES passnrun_db.MANAGER (ID)
     , INDEX (TEAM_ID)
     , CONSTRAINT FK_TEAMMANAGER_2 FOREIGN KEY (TEAM_ID)
                  REFERENCES passnrun_db.TEAM (ID)
);

CREATE TABLE passnrun_db.LEAGUE_TEAM (
       ID INTEGER NOT NULL
     , LEAGUE_ID INTEGER
     , TEAM_ID INTEGER
     , SEASON_ID INTEGER
     , PLAYED INTEGER
     , WON INTEGER
     , LOST INTEGER
     , DRAW INTEGER
     , FOR_GOAL INTEGER
     , AGAINST_GOAL INTEGER
     , POINTS INTEGER
     , PRIMARY KEY (ID)
     , INDEX (TEAM_ID)
     , CONSTRAINT FK_LEAGUETEAM_1 FOREIGN KEY (TEAM_ID)
                  REFERENCES passnrun_db.TEAM (ID)
     , INDEX (LEAGUE_ID)
     , CONSTRAINT FK_LEAGUETEAM_2 FOREIGN KEY (LEAGUE_ID)
                  REFERENCES passnrun_db.LEAGUE (ID)
);

CREATE TABLE passnrun_db.GAME_DETAIL (
       ID INTEGER NOT NULL
     , ACTION INTEGER
     , MINUTE INTEGER
     , GAME_ID INTEGER
     , TEAM INTEGER
     , PLAYER VARCHAR(64)
     , LOG_LEVEL INTEGER
     , LOG_MESSAGE TEXT
     , PRIMARY KEY (ID)
     , INDEX (GAME_ID)
     , CONSTRAINT FK_GAMEDETAIL_1 FOREIGN KEY (GAME_ID)
                  REFERENCES passnrun_db.GAME (ID)
);

CREATE TABLE passnrun_db.PLAYER_SKILL (
       ID INTEGER NOT NULL
     , HANDLING INTEGER
     , TACKLING INTEGER
     , PASSING INTEGER
     , CREATIVITY INTEGER
     , POSITIONING INTEGER
     , TEAMWORK INTEGER
     , HEADING INTEGER
     , SHOOTING INTEGER
     , DRIBBLING INTEGER
     , REFLEXES INTEGER
     , FINISHING INTEGER
     , CROSSING INTEGER
     , TECHNIQUE INTEGER
     , FIRST_TOUCH INTEGER
     , COMPOSURE INTEGER
     , AGILITY INTEGER
     , ACCELERATION INTEGER
     , STAMINA INTEGER
     , STRENGTH INTEGER
     , PRIMARY KEY (ID)
     , INDEX (ID)
     , CONSTRAINT FK_PLAYERSKILL_1 FOREIGN KEY (ID)
                  REFERENCES passnrun_db.PLAYER (ID)
);

CREATE TABLE passnrun_db.BAN (
       ID INTEGER NOT NULL
     , PLAYER_ID INTEGER
     , GAME_ID INTEGER
     , BANNED_FOR INTEGER
     , BAN_PAYED INTEGER
     , PRIMARY KEY (ID)
     , INDEX (PLAYER_ID)
     , CONSTRAINT FK_BAN_1 FOREIGN KEY (PLAYER_ID)
                  REFERENCES passnrun_db.PLAYER (ID)
     , INDEX (GAME_ID)
     , CONSTRAINT FK_BAN_2 FOREIGN KEY (GAME_ID)
                  REFERENCES passnrun_db.GAME (ID)
);

CREATE TABLE passnrun_db.INJURY (
       ID INTEGER NOT NULL
     , PLAYER_ID INTEGER
     , GAME_ID INTEGER
     , INJURED_FOR INTEGER
     , INJURED_PAYED INTEGER
     , PRIMARY KEY (ID)
     , INDEX (PLAYER_ID)
     , CONSTRAINT FK_INJURY_1 FOREIGN KEY (PLAYER_ID)
                  REFERENCES passnrun_db.PLAYER (ID)
     , INDEX (GAME_ID)
     , CONSTRAINT FK_INJURY_2 FOREIGN KEY (GAME_ID)
                  REFERENCES passnrun_db.GAME (ID)
);

CREATE TABLE passnrun_db.PLAYER_PERFORMANCE (
       ID INTEGER NOT NULL
     , PLAYER_ID INTEGER
     , GAME_ID INTEGER
     , SEASON_ID INTEGER
     , MINS INTEGER
     , ASSIST INTEGER
     , GOAL INTEGER
     , FORM INTEGER
     , MORALE INTEGER
     , PRIMARY KEY (ID)
     , INDEX (PLAYER_ID)
     , CONSTRAINT FK_PLAYERPERFORMANCE_1 FOREIGN KEY (PLAYER_ID)
                  REFERENCES passnrun_db.PLAYER (ID)
     , INDEX (GAME_ID)
     , CONSTRAINT FK_PLAYERPERFORMANCE_2 FOREIGN KEY (GAME_ID)
                  REFERENCES passnrun_db.GAME (ID)
);

CREATE TABLE passnrun_db.TEAM_PLAYER (
       ID INTEGER NOT NULL
     , PLAYER_ID INTEGER
     , TEAM_ID INTEGER
     , SEASON_ID INTEGER
     , PLAYED INTEGER
     , GOALS INTEGER
     , ASSISTS INTEGER
     , AGAINST INTEGER
     , YELLOW_CARD INTEGER
     , RED_CARD INTEGER
     , AVG_FORM INTEGER
     , PRIMARY KEY (ID)
     , INDEX (TEAM_ID)
     , CONSTRAINT FK_TEAMPLAYER_1 FOREIGN KEY (TEAM_ID)
                  REFERENCES passnrun_db.TEAM (ID)
     , INDEX (PLAYER_ID)
     , CONSTRAINT FK_TEAMPLAYER_2 FOREIGN KEY (PLAYER_ID)
                  REFERENCES passnrun_db.PLAYER (ID)
);

CREATE TABLE passnrun_db.FINANCE (
       ID INTEGER NOT NULL
     , TEAM_ID INTEGER
     , SEASON_ID INTEGER
     , WEEK INTEGER
     , RECTYPE INTEGER
     , AMOUNT INTEGER
     , PRIMARY KEY (ID)
     , INDEX (TEAM_ID)
     , CONSTRAINT FK_FINANCE_1 FOREIGN KEY (TEAM_ID)
                  REFERENCES passnrun_db.TEAM (ID)
);

CREATE TABLE passnrun_db.TACTIC_PLAYER (
       ID INTEGER NOT NULL
     , TACTIC_ID INTEGER
     , PLAYER_ID INTEGER
     , POSITION VARCHAR(16)
     , PRIMARY KEY (ID)
     , INDEX (TACTIC_ID)
     , CONSTRAINT FK_TACTIC_PLAYER_2 FOREIGN KEY (TACTIC_ID)
                  REFERENCES passnrun_db.TACTIC (ID)
     , INDEX (PLAYER_ID)
     , CONSTRAINT FK_TACTICPLAYER_2 FOREIGN KEY (PLAYER_ID)
                  REFERENCES passnrun_db.PLAYER (ID)
);

CREATE TABLE passnrun_db.NEWS (
       ID INTEGER NOT NULL
     , MANAGER_ID INTEGER NOT NULL
     , RECEIVE_DATE DATETIME
     , SUBJECT VARCHAR(128)
     , MUST_ANSWER INTEGER
     , BODY TEXT
     , PRIMARY KEY (ID)
     , INDEX (MANAGER_ID)
     , CONSTRAINT FK_NEWS_1 FOREIGN KEY (MANAGER_ID)
                  REFERENCES passnrun_db.MANAGER (ID)
);

