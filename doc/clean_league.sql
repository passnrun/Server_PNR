--ROLLBACK CREATE LEAGUE;
delete from LEAGUE;
delete from TACTIC_PLAYER;
delete from TACTIC;
delete from TEAM;
delete from STADIUM;
delete from PLAYER_SKILL;
delete from PLAYER;
update TEAM_TEMPLATE SET USED = NULL;

--ROLLBACK INITIATE LEAGUE;
delete from SEASON where ID > 0;
delete from TEAM_MANAGER where ID > 0;
delete from LEAGUE_TEAM where ID > 0;
delete from TEAM_PLAYER where ID > 0;
delete from PLAYER_PERFORMANCE;
delete from GAME_DETAIL where ID > 0;
delete from GAME where ID > 0;
update LEAGUE set STATUS = 0;

delete from MODIFY_COUNT;
delete from MANAGER;

--Clean Game Scores;
update GAME set SCORE1 = NULL, SCORE2=NULL, PERCENT_ATT=NULL;
delete from GAME_DETAIL;


